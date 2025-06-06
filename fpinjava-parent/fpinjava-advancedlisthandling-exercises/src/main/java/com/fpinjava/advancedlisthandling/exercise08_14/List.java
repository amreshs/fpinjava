package com.fpinjava.advancedlisthandling.exercise08_14;

import com.fpinjava.common.Function;
import com.fpinjava.common.Result;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

import java.util.Objects;

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
  public abstract <B> B foldLeft(B identity, B zero, Function<B, Function<A, B>> f);
  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);
  public abstract <B> List<B> map(Function<A, B> f);
  public abstract List<A> filter(Function<A, Boolean> f);
  public abstract <B> List<B> flatMap(Function<A, List<B>> f);
  public abstract Result<A> headOption();

  public Result<A> lastOption() {
    return foldLeft(Result.empty(), x -> value -> Result.success(value));
  }

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  public <A1, A2> Tuple<List<A1>, List<A2>> unzip(Function<A, Tuple<A1, A2>> f) {
    return this.foldRight(new Tuple<>(list(), list()), a -> tl -> {
      Tuple<A1, A2> t = f.apply(a);
      return new Tuple<>(tl._1.cons(t._1), tl._2.cons(t._2));
    });
  }

  public Result<A> getAt_(int index) {
    return index < 0 || index >= length()
        ? Result.failure("Index out of bound")
        : getAt(this, index).eval();
  }

  private static <A> TailCall<Result<A>> getAt(List<A> list, int index) {
      return index == 0
                ? TailCall.ret(Result.success(list.head()))
                : TailCall.sus(() -> getAt(list.tail(), index - 1));
  }

  public Result<A> getAt(int index) {
    Tuple<Result<A>, Integer> identity = new Tuple<>(Result.failure("Index out of bound"), index);
    Tuple<Result<A>, Integer> rt = index < 0 || index >= length()
        ? identity
        : foldLeft(identity, ta -> a -> ta._2 < 0 ? ta : new Tuple<>(Result.success(a), ta._2 - 1));
    return rt._1;
  }

  public Result<A> getAt__(int index) {
    class Tuple<T, U> {

      public final T _1;
      public final U _2;

      public Tuple(T t, U u) {
        this._1 = Objects.requireNonNull(t);
        this._2 = Objects.requireNonNull(u);
      }

      @Override
      public String toString() {
        return String.format("(%s,%s)", _1,  _2);
      }

      @Override
      public boolean equals(Object o) {
        if (!(o.getClass() == this.getClass()))
          return false;
        else {
          @SuppressWarnings("rawtypes")
          Tuple that = (Tuple) o;
          return _2.equals(that._2);
        }
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1.hashCode();
        result = prime * result + _2.hashCode();
        return result;
      }
    }
    Tuple<Result<A>, Integer> zero = new Tuple<>(Result.failure("Index out of bound"), -1);
    Tuple<Result<A>, Integer> identity = new Tuple<>(Result.failure("Index out of bound"), index);
    Tuple<Result<A>, Integer> rt = index < 0 || index >= length()
        ? identity
        : foldLeft(identity, zero, ta -> a -> ta._2 < 0 ? ta : new Tuple<>(Result.success(a), ta._2 - 1));
    return rt._1;
  }

  public Tuple<List<A>, List<A>> splitAt(int index) {
    Tuple<List<A>, List<A>> acc = new Tuple<>(list(), this);
    index = index < 0 ? 0 : index >= length() ? length() - 1 : index;

    return splitAt_(index,  acc).eval();
  }

  private TailCall<Tuple<List<A>, List<A>>> splitAt_(int index, Tuple<List<A>, List<A>> acc) {
    return (index < 0 || acc._2.length() == 0) ? ret(new Tuple<>(acc._1.reverse(), acc._2)) : sus(() -> splitAt_(index-1,new Tuple<>(acc._1.cons(acc._2.head()),acc._2.tail())));
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
    public <B> B foldLeft(B identity, B zero, Function<B, Function<A, B>> f) {
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
      return foldLeft(identity, this, f).eval();
    }

    private <B> TailCall<B> foldLeft(B acc, List<A> list, Function<B, Function<A, B>> f) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> foldLeft(f.apply(acc).apply(list.head()), list.tail(), f));
    }

  @Override
  public <B> B foldLeft(B identity, B zero, Function<B, Function<A, B>> f) {
    return foldLeft(identity, zero, this, f).eval();
  }

  private <B> TailCall<B> foldLeft(B acc, B zero, List<A> list, Function<B, Function<A, B>> f) {
    return list.isEmpty() || acc.equals(zero)
        ? ret(acc)
        : sus(() -> foldLeft(f.apply(acc).apply(list.head()), zero, list.tail(), f));
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
    return flatten(list.foldRight(list(), x -> y -> y.cons(x.map(a -> list(a)).getOrElse(list()))));
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
    List lst = List.list(new Tuple<>("Hello", 1), new Tuple("World", 2), new Tuple("of FP", 3), new Tuple("!", 4));
    Tuple tpl = lst.splitAt(1);
    System.out.println(lst.splitAt(1));
    System.out.println(tpl._1);
    System.out.println(tpl._2);
    //System.out.println(lst.getAt(3));
  }
}
