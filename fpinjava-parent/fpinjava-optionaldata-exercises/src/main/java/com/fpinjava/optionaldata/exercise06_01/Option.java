package com.fpinjava.optionaldata.exercise06_01;
import com.fpinjava.lists.exercise05_21.List;
import com.fpinjava.common.Function;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A getOrThrow();
  public abstract A getOrElse(A defaultValue);

  private static class None<A> extends Option<A> {

    private None() {}

    @Override
    public A getOrThrow() {
      throw new IllegalStateException("getOrThrow called on None");
    }

    @Override
    public A getOrElse(A defaultValue) {
      return defaultValue;
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

    @Override
    public A getOrElse(A defaultValue) {
        return value;
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

  public static <B extends Comparable<B>> Function<B, Function<List<B>, B>> max(){
      return x0-> lst -> lst.isEmpty()? x0 : lst.tail().foldLeft(lst.head(), acc->y -> acc.compareTo(y)> 0 ? acc : y);
  }

  static <A extends Comparable<A>> A max(A x0, List<A> xs) {
    return xs.isEmpty()
            ? x0
            : xs.tail().foldLeft(xs.head(), acc -> y -> acc.compareTo(y) < 0 ? y : acc);
  }

  static <A extends Comparable<A>> Option<A> max(List<A> xs) {
    return xs.isEmpty()? Option.none() : Option.some(xs.foldLeft(xs.head(), acc ->y -> acc.compareTo(y) < 0 ? y :acc));
  }
  public static void main(String[] args){
      List<Integer> lst = List.list(1, 2, 3, 4, 5);
      System.out.println(lst);
      //max().apply(lst.head()).apply(lst);
      System.out.println(max(lst.head(),lst));
      System.out.println(max(List.<Integer>list(7, 3, 2, 4, 1,5,6)).getOrElse(0));
      System.out.println(max(List.<Integer>list()).getOrElse(0));
  }
}
