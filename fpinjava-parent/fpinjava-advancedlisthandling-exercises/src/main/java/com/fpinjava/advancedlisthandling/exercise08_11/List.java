package com.fpinjava.advancedlisthandling.exercise08_11;

import com.fpinjava.common.Function;
import com.fpinjava.common.Result;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;

import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;


public abstract class List<A> {

  public abstract A head();
  public abstract List<A> tail();
  public abstract boolean isEmpty();
  public abstract List<A> setHead(A h);
  public abstract List<A> drop(int n);
  public abstract List<A> dropWhile(Function<A, Boolean> f);
  public abstract List<A> reverse();
  public abstract List<A> init();
  public abstract int length();
  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);
  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);
  public abstract <B> List<B> map(Function<A, B> f);
  public abstract List<A> filter(Function<A, Boolean> f);
  public abstract <B> List<B> flatMap(Function<A, List<B>> f);
  public abstract Result<A> headOption();

  public Result<A> lastOption() {
    return foldLeft(Result.empty(), x -> Result::success);
  }

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  public <A1, A2> Tuple<List<A1>, List<A2>> unzip(Function<A, Tuple<A1, A2>> f) {
    return foldRight(new Tuple<>(list(), list()), a -> tCont ->{ Tuple<A1, A2> tpl = f.apply(a);
      return new Tuple<>(tCont._1.cons(tpl._1), tCont._2.cons(tpl._2));} );
  }

  @SuppressWarnings("rawtypes")
  public static final List NIL = new Nil();

  private List() {}

  private static class Nil<A> extends List<A> {

    private Nil() {}

    public A head() {
      throw new IllegalStateException("head called en empty list");
    }

    public List<A> tail() {
      throw new IllegalStateException("tail called en empty list");
    }

    public boolean isEmpty() {
      return true;
    }

    @Override
    public List<A> setHead(A h) {
      throw new IllegalStateException("setHead called en empty list");
    }

    public String toString() {
      return "[NIL]";
    }

    @Override
    public List<A> drop(int n) {
      return this;
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return this;
    }

    @Override
    public List<A> reverse() {
      return this;
    }

    @Override
    public List<A> init() {
      throw new IllegalStateException("init called on an empty list");
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return identity;
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
      return identity;
    }

    @Override
    public <B> List<B> map(Function<A, B> f) {
      return list();
    }

    @Override
    public List<A> filter(Function<A, Boolean> f) {
      return this;
    }

    @Override
    public <B> List<B> flatMap(Function<A, List<B>> f) {
      return list();
    }

    @Override
    public Result<A> headOption() {
      return Result.empty();
    }
  }

  private static class Cons<A> extends List<A> {

    private final A head;
    private final List<A> tail;
    private final int length;

    private Cons(A head, List<A> tail) {
      this.head = head;
      this.tail = tail;
      this.length = tail.length() + 1;
    }

    public A head() {
      return head;
    }

    public List<A> tail() {
      return tail;
    }

    public boolean isEmpty() {
      return false;
    }

    @Override
    public List<A> setHead(A h) {
      return new Cons<>(h, tail());
    }

    public String toString() {
      return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }

    @Override
    public List<A> drop(int n) {
      return n <= 0
          ? this
          : drop_(this, n).eval();
    }

    private TailCall<List<A>> drop_(List<A> list, int n) {
      return n <= 0 || list.isEmpty()
          ? ret(list)
          : sus(() -> drop_(list.tail(), n - 1));
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return dropWhile_(this, f).eval();
    }

    private TailCall<List<A>> dropWhile_(List<A> list, Function<A, Boolean> f) {
      return !list.isEmpty() && f.apply(list.head())
          ? sus(() -> dropWhile_(list.tail(), f))
          : ret(list);
    }

    @Override
    public List<A> reverse() {
      return reverse_(list(), this).eval();
    }

    private TailCall<List<A>> reverse_(List<A> acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> reverse_(new Cons<>(list.head(), acc), list.tail()));
    }

    @Override
    public List<A> init() {
      return reverse().tail().reverse();
    }

    @Override
    public int length() {
      return length;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft_(identity, this, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B acc, List<A> list, Function<B, Function<A, B>> f) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> foldLeft_(f.apply(acc).apply(list.head()), list.tail(), f));
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
      return reverse().foldLeft(identity, x -> y -> f.apply(y).apply(x));
    }

    @Override
    public <B> List<B> map(Function<A, B> f) {
      return foldRight(list(), h -> t -> new Cons<>(f.apply(h),t));
    }

    @Override
    public List<A> filter(Function<A, Boolean> f) {
      return foldRight(list(), h -> t -> f.apply(h) ? new Cons<>(h,t) : t);
    }

    @Override
    public <B> List<B> flatMap(Function<A, List<B>> f) {
      return foldRight(list(), h -> t -> concat(f.apply(h), t));
    }

    @Override
    public Result<A> headOption() {
      return Result.success(head);
    }
  }

  @SuppressWarnings("unchecked")
  public static <A> List<A> list() {
    return NIL;
  }

  @SafeVarargs
  public static <A> List<A> list(A... a) {
    List<A> n = list();
    for (int i = a.length - 1; i >= 0; i--) {
      n = new Cons<>(a[i], n);
    }
    return n;
  }

  public static <A, B> B foldRight(List<A> list, B n, Function<A, Function<B, B>> f ) {
    return list.foldRight(n, f);
  }

  public static <A> List<A> concat(List<A> list1, List<A> list2) {
    return foldRight(list1, list2, x -> y -> new Cons<>(x, y));
  }

  public static <A> List<A> flatten(List<List<A>> list) {
    return foldRight(list, List.<A>list(), x -> y -> concat(x, y));
  }

  public static <A> List<A> flattenResult(List<Result<A>> list) {
    return flatten(list.foldRight(list(), x -> y -> y.cons(x.map(List::list).getOrElse(list()))));
  }

  public static <A, B> Result<List<B>> traverse(List<A> list, Function<A, Result<B>> f) {
    return list.foldRight(Result.success(List.list()), x -> y -> Result.map2(f.apply(x), y, a -> b -> b.cons(a)));
  }

  public static <A> Result<List<A>> sequence(List<Result<A>> list) {
    return traverse(list, x -> x);
  }

  public static <A, B, C> List<C> zipWith(List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return zipWith_(list(), list1, list2, f).eval().reverse();
  }

  private static <A, B, C> TailCall<List<C>> zipWith_(List<C> acc, List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return list1.isEmpty() || list2.isEmpty()
        ? ret(acc)
        : sus(() -> zipWith_(
            new Cons<>(f.apply(list1.head()).apply(list2.head()), acc),
            list1.tail(), list2.tail(), f));
  }

  public static <A, B, C> List<C> product(List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return list1.flatMap(a -> list2.map(b -> f.apply(a).apply(b)));
  }

  public static <A1, A2> Tuple<List<A1>, List<A2>> unzip(List<Tuple<A1, A2>> list) {
    return list.foldRight(new Tuple<>(list(), list()), t -> tl -> new Tuple<>(tl._1.cons(t._1), tl._2.cons(t._2)));
  }

  public static void main(String[] args) {
    List lst = List.list("Hello_1", "World_2", "of FP_3");
    //System.out.println(lst.unzip( s-> { String[] str = s.split("_"); return new Tuple<>(str[0], Integer.parseInt(str[1]));}));
  }
}
