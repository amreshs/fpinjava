package com.fpinjava.optionaldata.exercise06_11;


import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_21.List;
import com.fpinjava.common.Supplier;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  protected abstract A getOrThrow();

  public abstract A getOrElse(Supplier<A> defaultValue);

  public abstract <B> Option<B> map(Function<A, B> f);

  public <B> Option<B> flatMap(Function<A, Option<B>> f) {
    return map(f).getOrElse(Option::none);
  }

  public Option<A> orElse(Supplier<Option<A>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  public Option<A> filter(Function<A, Boolean> f) {
    return flatMap(x -> f.apply(x)
        ? this
        : none());
  }

  private static class None<A> extends Option<A> {

    private None() {}

    @Override
    protected A getOrThrow() {
      throw new IllegalStateException("getOrThrow called on None");
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public <B> Option<B> map(Function<A, B> f) {
      return none();
    }

    @Override
    public String toString() {
      return "None";
    }
  }

  private static class Some<A> extends Option<A> {

    private final A value;

    private Some(A a) {
      value = a;
    }

    @Override
    protected A getOrThrow() {
      return this.value;
    }

    public A getOrElse(Supplier<A> defaultValue) {
      return this.value;
    }

    public <B> Option<B> map(Function<A, B> f) {
      return new Some<>(f.apply(this.value));
    }

    @Override
    public String toString() {
      return String.format("Some(%s)", this.value);
    }
  }

  public static <A> Option<A> some(A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  public static <A> Option<A> none() {
    return none;
  }

  public static <A, B> Function<Option<A>, Option<B>> lift(Function<A, B> f) {
    return x -> {
      try {
        return x.map(f);
      } catch (Exception e) {
        return Option.none();
      }
    };
  }

  public static <A, B> Function<A, Option<B>> hlift(Function<A, B> f) {
    return x -> {
      try {
        return Option.some(x).map(f);
      } catch (Exception e) {
        return Option.none();
      }
    };
  }

  public static <A, B, C> Option<C> map2(Option<A> a,
                                         Option<B> b,
                                         Function<A, Function<B, C>> f) {
    return a.flatMap(ax -> b.map(bx -> f.apply(ax).apply(bx)));
  }

  public static <A, B, C, D> Option<D> map3(Option<A> a,
                                            Option<B> b,
                                            Option<C> c,
                                            Function<A, Function<B, Function<C, D>>> f) {
    return a.flatMap(ax -> b.flatMap(bx -> c.map(cx -> f.apply(ax).apply(bx).apply(cx))));
  }

  public static <A> Option<List<A>> sequence(List<Option<A>> lst) {
    //return lst.foldLeft(Option.some(List.list()),lstN->itm->map2(lstN,itm,x->y->x.cons(y)));
    return lst.isEmpty()? Option.some(List.list()): lst.head().flatMap(hd -> sequence(lst.tail()).map(lt -> lt.cons(hd)));
  }

  public static void main(String[] args) {
    List<String> lst = List.list("11", "22", "33", "44", "35", "10");
    List<Option<String>>lst2 = lst.map(str->Option.some(str));
    List<Option<String>>lst3 = lst.map(str->Option.none());
    Option<List<String>> optLst = sequence(lst2);
    System.out.println(optLst);

    Option<List<String>> optLst2 = optLst.map(x -> x.cons(null));
    Option<List<String>> optLst3 = sequence(lst3);
    System.out.println(optLst3);

    Function<Integer, Function<String, Integer>> parseString = radix -> str-> Integer.parseInt(str, radix);
    Function<String,Integer> parseInt16 = str -> Integer.parseInt(str, 16);
    Function<String, Option<Integer>> parseInt16Option = Option.hlift(parseInt16);

    Option<List<Integer>> optionList = sequence(lst.map(parseInt16Option));
    System.out.println(optionList);
  }

}
