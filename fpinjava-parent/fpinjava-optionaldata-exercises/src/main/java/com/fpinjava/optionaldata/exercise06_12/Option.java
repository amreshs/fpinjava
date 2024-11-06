package com.fpinjava.optionaldata.exercise06_12;


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

    @Override
    public boolean equals(Object obj) {
      return obj instanceof None ||this == none();
    }

    @Override
    public int hashCode(){
      return 0;
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

    @Override
    public boolean equals(Object obj){
      return (this == obj || obj instanceof Some) && ((Some<?>)obj).value.equals(this.value);
    }

    @Override
    public int hashCode() {
      return this.value.hashCode();
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

  public static <A, B> Option<List<B>> traverse(List<A> lst,
                                                Function<A, Option<B>> f) {
    return lst.foldLeft(Option.some(List.list()), lt->itm->map2(lt, f.apply(itm), a->b->a.cons(b)));
  }

  public static <A> Option<List<A>> sequence(List<Option<A>> lst) {
    //return lst.foldLeft(Option.some(List.list()), lt->itm->map2(lt, itm, a->b->a.cons(b)));
    return traverse(lst, x -> x);
  }

  public static void main(String[] args) {
    List<String> lst = List.list("11", "22", "33", "44", "35", "10");

    Function<Integer,Function<String, Integer>> parseInteger = radix -> str -> Integer.parseInt(str, radix);
    Function<String, Integer> parseInt16 = parseInteger.apply(16);

    Function<String, Option<Integer>> parseInt16Option = Option.hlift(parseInt16);
    Option<List<Integer>> lstIntOpt = traverse(lst, parseInt16Option);

    System.out.println(lstIntOpt);
  }
}
