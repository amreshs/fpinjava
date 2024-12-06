package com.fpinjava.handlingerrors.exercise07_02;


import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_21.List;

public abstract class Either<E, A> {

  public abstract <B> Either<E, B> map(Function<A, B> f);

  public abstract <B> Either<E, B> flatMap(Function<A, Either<E, B>> f);

  private static class Left<E, A> extends Either<E, A> {

    private final E value;

    private Left(E value) {
      this.value = value;
    }

    public <B> Either<E, B> map(Function<A, B> f) {
      return new Left<>(value);
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      return new Left<>(value);
    }

    @Override
    public String toString() {
      return String.format("Left(%s)", value);
    }
  }

  private static class Right<E, A> extends Either<E, A> {

    private final A value;

    private Right(A value) {
      this.value = value;
    }

    public <B> Either<E, B> map(Function<A, B> f) {
      return new Right<>(f.apply(value));
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      return f.apply(value);
    }

    @Override
    public String toString() {
      return String.format("Right(%s)", value);
    }
  }

  public static <E, A> Either<E, A> left(E value) {
    return new Left<>(value);
  }

  public static <E, A> Either<E, A> right(A value) {
    return new Right<>(value);
  }

  public static <A extends Comparable<A>> Either<String, A> max(List<A> xs){
    return xs.isEmpty()? Either.left("Empty List"): Either.right(xs.tail().foldLeft(xs.head(), x-> y-> x.compareTo(y)<0?y:x));
  }

  public static Function<Integer, Either<String,String>> intToEWord= i ->{
    switch(i){
      case 0: return right("zero");
      case 1: return right("one");
      case 2: return right("two");
      case 3: return right("three");
      case 4: return right("four");
      case 5: return right("five");
      case 6: return right("six");
      case 7: return right("seven");
      case 8: return right("eight");
      case 9: return right("nine");
      default: return left("unknown");
    }

  };

  public static void main(String[] args) {
    List<Integer> lst = List.list(1, 2, 3, 4, 50);
    System.out.println(lst);
    System.out.println(max(lst).flatMap(intToEWord));
  }
}
