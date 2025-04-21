package com.fpinjava.advancedtrees.exercise11_08;


import com.fpinjava.common.Result;

import java.util.Comparator;
import java.util.NoSuchElementException;

public abstract class Heap<A> {

  @SuppressWarnings("rawtypes")
  //protected static final Heap EMPTY = new Empty();

  protected abstract Result<Heap<A>> left();
  protected abstract Result<Heap<A>> right();
  protected abstract int rank();

  public abstract Result<A> get(int index);
  public abstract Result<A> head();
  public abstract Result<Heap<A>> tail();
  public abstract int length();
  public abstract boolean isEmpty();

  public Heap<A> add(A element) {
    return merge(this, heap(element,this.comparator()));
  }

  protected abstract Result<Comparator<A>> comparator();

  public int height() {
    return isEmpty()
            ? -1
            : Math.max(left().getOrElse(() -> empty(Result.empty())).height(),
                       right().getOrElse(() -> empty(Result.empty())).height())+1;
  }
  public static class Empty<A> extends Heap<A> {

    private final Result<Comparator<A>> comparator;

    private Empty(Result<Comparator<A>> comparator) {
      this.comparator = comparator;
    }
    @Override
    protected int rank() {
      return 0;
    }

    @Override
    public Result<A> get(int index) {
      return Result.failure(new NoSuchElementException("Index out of range"));
    }

    @Override
    public Result<A> head() {
      return Result.failure(new NoSuchElementException("Method head() called on empty heap"));
    }

    @Override
    public Result<Heap<A>> tail() {
      return Result.failure(new NoSuchElementException("Method tail() called on empty heap"));
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public Result<Heap<A>> left() {
      return Result.success(empty(this.comparator()));
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(empty(this.comparator()));
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
      return this.comparator;
    }
  }

  public static class H<A> extends Heap<A> {

    private final int length;
    private final int rank;
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;
    private final Result<Comparator<A>> comparator;

    private H(int length, int rank, Heap<A> left, A head, Heap<A> right, Result<Comparator<A>> comparator) {
      this.length = length;
      this.rank = rank;
      this.head = head;
      this.left = left;
      this.right = right;
      this.comparator = comparator;
    }

    @Override
    protected int rank() {
      return this.rank;
    }

    @Override
    public Result<A> get(int index) {
      return index == 0
          ? head()
          : tail().flatMap(x -> x.get(index - 1));
    }

    @Override
    public Result<A> head() {
      return Result.success(this.head);
    }

    @Override
    public Result<Heap<A>> tail() {
      return Result.success(Heap.merge(left, right));
    }

    @Override
    public int length() {
      return this.length;
    }

    @Override
    protected Result<Heap<A>> left() {
      return Result.success(this.left);
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(this.right);
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
      return this.comparator;
    }
  }

  @SuppressWarnings("unchecked")
  public static <A> Heap<A> empty(Comparator<A> comparator) {
    return empty(Result.success(comparator));
  }

  public static <A> Heap<A> empty(Result<Comparator<A>> comparator) {
    return new Empty<>(comparator);
  }

  public static <A extends Comparable<A>> Heap<A> empty(){
    return empty(Result.empty());
  }

  public static <A extends Comparable<A>> Heap<A> heap(A element) {
    Heap<A> empty = empty(Result.empty());
    return new H<>(1, 1, empty, element, empty, Result.empty());
  }

  public static <A> Heap<A> heap(A element, Comparator<A> comparator) {
    Heap<A> empty = empty(Result.empty());
    return heap(element, Result.success(comparator));
  }

  public static <A> Heap<A> heap(A element, Result<Comparator<A>> comparator) {
    Heap<A> empty = empty(Result.empty());
    return new H<>(1, 1, empty, element, empty,comparator);
  }

  protected static <A> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
    Result<Comparator<A>> comparator = first.comparator().orElse(second::comparator);
    return first.rank() >= second.rank()
        ? new H<>(first.length() + second.length() + 1, second.rank() + 1, first, head, second, comparator)
        : new H<>(first.length() + second.length() + 1, first.rank() + 1, second, head, first, comparator);
  }

  public static<A> Heap<A> merge(Heap<A> first, Heap<A> second){
    Result<Comparator<A>> comparator = first.comparator().orElse(second::comparator);
    return merge(first, second, comparator);
  }

  public static <A> Heap<A> merge(Heap<A> first, Heap<A> second, Result<Comparator<A>> comparator) {
    return first.head().flatMap(fh -> second.head().flatMap(sh -> compare(fh, sh, comparator) <= 0
        ? first.left().flatMap(fl -> first.right().map(fr -> heap(fh, fl, merge(fr, second, comparator))))
        : second.left().flatMap(sl -> second.right().map(sr -> heap(sh, sl, merge(first, sr, comparator))))))
        .getOrElse(first.isEmpty()
            ? second
            : first);
  }
  @SuppressWarnings("unchecked")
  public static<A> int compare(A first, A second, Result<Comparator<A>> comparator) {
      return comparator.map(comp -> comp.compare(first, second)).getOrElse(() -> ((Comparable<A>) first).compareTo(second));
  }

  public static void main(String[] args) {
    Person one = new Person("A",17);
    Heap<Person> root1 = new Heap.H<>(1, 1, empty((Comparator) one), one,
            empty((Comparator) one), Result.success((Comparator) one));

    root1 = root1.add(new Person("B",26)).add(new Person("C", 8)).add(new Person("D",3))
            .add(new Person("E",14)).add(new Person("F",23))
            .add(new Person("G",10)).add(new Person("I",21));

    System.out.println(root1.get(4).getOrElse(new Person("NA", -1)));

    Person two = new Person("Z",18);
    Heap<Person> root2 = new Heap.H<>(1, 1, empty((Comparator) two), two,
            empty((Comparator) two), Result.success((Comparator) two));

    root1 = root1.add(new Person("Y",12)).add(new Person("X", 24)).add(new Person("W",33))
            .add(new Person("V",6)).add(new Person("U",37))
            .add(new Person("T",18));


    System.out.println(root1.get(5).getOrElse(new Person("NA", -1)));

    Heap<Person> rootFinal = merge(root1, root2);
    PrintTree.print2DArray(PrintTree.treeToMatrix(rootFinal, new Person("NA", -1), 7));
  }
}
