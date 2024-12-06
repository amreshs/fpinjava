package com.fpinjava.optionaldata.exercise06_04;


import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;
import com.fpinjava.lists.exercise05_21.List;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  protected abstract A getOrThrow();

  public abstract A getOrElse(Supplier<A> defaultValue);

  public abstract <B> Option<B> map(Function<A, B> f);

  public <B> Option<B> flatMap(Function<A, Option<B>> f) {
    return map(f).getOrElse(Option::none);
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

  public static Option<Integer> convertToInteger(String a){
    return Option.some(Integer.parseInt(a)*200);
  }
  public static void main(String[] args) {
    List<Integer> lst = List.<Integer>list(21, 34, 56, 43, 78);
    List<Option<String>>lstOption = lst.map(x -> Option.some(x.toString()));
    List<Option<String>> lstNill = List.<Option<String>>list(Option.none());
    lstOption = List.concat(lstNill, lstOption);
    System.out.println(lstOption.map(x -> x.flatMap(y -> Option.some(Integer.parseInt(y)*100))));
    System.out.println(lstOption.map(x -> x.flatMap(Option::convertToInteger)));
  }
}
