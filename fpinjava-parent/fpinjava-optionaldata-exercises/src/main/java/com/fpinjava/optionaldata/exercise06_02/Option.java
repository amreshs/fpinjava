package com.fpinjava.optionaldata.exercise06_02;

import com.fpinjava.common.Supplier;
import com.fpinjava.lists.exercise05_21.List;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A getOrThrow();

  /*
   After solving the exercise, uncomment the corresponding lines in
   exercise 06_2.OptionTest
   */
  public abstract A getOrElse(Supplier<A> defaultValue);

  private static class None<A> extends Option<A> {

    private None() {}

    @Override
    public A getOrThrow() {
      throw new IllegalStateException("getOrThrow called on None");
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return defaultValue.get();
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
    public A getOrThrow() {
      return this.value;
    }

    public A getOrElse(Supplier<A> defaultValue) {
      return this.value;
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

  public static <A> A getDefault() {
    throw new IllegalStateException("getDefault called on None");
  }

  public static<A extends Comparable<A>> Option<A> min(List<A> lst){
    return (lst.isEmpty() ? Option.none() : Option.some(lst.tail().foldLeft(lst.head(), acc->y-> acc.compareTo(y) < 0? acc : y)));
  }
  public static void min(String[] args){
    List<Integer> lst = List.list(10,6,3,7,5,6);
    System.out.println(min(lst).getOrElse(() -> Option.getDefault()));
    min(List.<Integer>list()).getOrElse(() -> Option.getDefault());
  }
}


