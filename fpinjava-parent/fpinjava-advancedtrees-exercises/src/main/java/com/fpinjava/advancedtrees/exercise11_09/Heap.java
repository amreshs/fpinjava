package com.fpinjava.advancedtrees.exercise11_09;


import com.fpinjava.common.Result;

import java.util.Comparator;
import java.util.NoSuchElementException;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public abstract class Heap<A> {

  protected abstract Result<Heap<A>> left();
  protected abstract Result<Heap<A>> right();
  protected abstract int rank();
  protected abstract Result<Comparator<A>> comparator();

  public abstract Result<A> get(int index);
  public abstract Result<A> head();
  public abstract Result<Heap<A>> tail();
  public abstract int length();
  public abstract boolean isEmpty();
  public abstract int height();

  public abstract Heap<A> insert(A a);

  public Heap<A> add(A element) {
    return merge(this, heap(element, this.comparator()));
  }

  public static class Empty<A> extends Heap<A> {

    private final Result<Comparator<A>> comparator;

    private Empty(Result<Comparator<A>> comparator) {
      this.comparator = comparator;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
      return this.comparator;
    }

    @Override
    protected int rank() {
      return 0;
    }

    @Override
    public Result<A> get(int index) {
      throw new IllegalStateException("To be implemented");
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
      return Result.success(empty(this.comparator));
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(empty(this.comparator));
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public int height() {
      return 0;
    }

    @Override
    public Heap<A> insert(A a) {
      return heap(a, this, this);
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  public static class H<A> extends Heap<A> {

    private final int length;
    private final int rank;
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;
    private final Result<Comparator<A>> comparator;

    private H(int length, int rank, A head, Heap<A> left, Heap<A> right, Result<Comparator<A>> comparator) {
      this.length = length;
      this.rank = rank;
      this.head = head;
      this.left = left;
      this.right = right;
      this.comparator = comparator;
    }

    protected Result<Comparator<A>> comparator() {
      return this.comparator;
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
    public int height() {
      return 1 + Math.max(left.height(), right.height());
    }

    @Override
    public Heap<A> insert(A a) {
      int cmp = compare(head, a, comparator);
      return heap(cmp <0
              ?head
              :a, left, right.insert(cmp>= 0?head:a));
    }

    @Override
    public String toString() {
      return String.format("(H, %s, %s, %s)", left, head, right);
    }
  }

  public static <A extends Comparable<A>> Heap<A> empty() {
    return empty(Result.empty());
  }

  public static <A> Heap<A> empty(Comparator<A> comparator) {
    return empty(Result.success(comparator));
  }

  public static <A> Heap<A> empty(Result<Comparator<A>> comparator) {
    return new Empty<>(comparator);
  }

  public static <A extends Comparable<A>> Heap<A> heap(A element) {
    return heap(element, Result.empty());
  }

  public static <A> Heap<A> heap(A element, Result<Comparator<A>> comparator) {
    Heap<A> empty = empty(comparator);
    return new H<>(1, 1, element, empty, empty, comparator);
  }

  public static <A> Heap<A> heap(A element, Comparator<A> comparator) {
    Heap<A> empty = empty(comparator);
    return new H<>(1, 1, element, empty, empty, Result.success(comparator));
  }

  protected static <A> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
    Result<Comparator<A>> comparator = first.comparator().orElse(second::comparator);
    return first.rank() >= second.rank()
        ? new H<>(first.length() + second.length() + 1, second.rank() + 1, head, first, second, comparator)
        : new H<>(first.length() + second.length() + 1, first.rank() + 1, head, second, first, comparator);
  }

  public static <A> Heap<A> merge(Heap<A> first, Heap<A> second) {
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
  public static <A> int compare(A first, A second, Result<Comparator<A>> comparator) {
    return comparator.map(comp -> Integer.valueOf(comp.compare(first, second))).getOrElse(() -> Integer.valueOf(((Comparable<A>) first).compareTo(second)));
  }

  public static <A> A min(A a1, A a2, Result<Comparator<A>> comparator) {
    return compare(a1, a2, comparator) < 0
        ? a1
        : a2;
  }

  public static void main(String[] args) {
    Heap<Integer> root1_ins = empty();
    root1_ins = root1_ins.insert(Integer.valueOf(17) ).insert(Integer.valueOf(26)).insert(Integer.valueOf(8))
            .insert(Integer.valueOf(3)).insert(Integer.valueOf(14)).insert(Integer.valueOf(23))
            .insert(Integer.valueOf(10)).insert(Integer.valueOf(21));

    Heap<Integer> root1_add = empty();
    root1_add = root1_add.add(Integer.valueOf(17) ).add(Integer.valueOf(26)).add(Integer.valueOf(8))
            .add(Integer.valueOf(3)).add(Integer.valueOf(14)).add(Integer.valueOf(23))
            .add(Integer.valueOf(10)).add(Integer.valueOf(21));


    /*
    Heap<Integer> root2 = new Heap.H<>(1, 1, empty(), 18, empty());
    root2 = root2.insert(12).add(24).add(33).add(6).add(37).add(7).add(18);
    */
    //Heap<Integer> rootFinal = merge(root1, root2);

    PrintTree.print2DArray(PrintTree.treeToMatrix(root1_ins, -1));
    PrintTree.print2DArray(PrintTree.treeToMatrix(root1_add, -1));

    List<Tuple<Integer, Integer>> points = List.list(1,2,2,2,6,7,5,0,5,1).zipWithPosition();
    Heap<Point> heap = points.foldLeft(empty(), h->t->h.insert(new Point(t._1, t._2)));
    List<Point> lp = List.unfold(heap, hp -> hp.head().flatMap(h -> hp.tail().map(t-> new Tuple<>(h, t))));

    System.out.println(points);
    System.out.println(lp);


    //Result<Heap<Integer>> root1_ins_tail = root1_ins.tail();
    //PrintTree.print2DArray(PrintTree.treeToMatrix(root1_ins_tail.getOrElse(empty()), -1));

    /*
    PrintTree.print2DArray(PrintTree.treeToMatrix(root2, -1));

    Result<Heap<Integer>> root2_tail = root2.tail();
    PrintTree.print2DArray(PrintTree.treeToMatrix(root2_tail.getOrElse(empty()), -1));
     */

  }
}
