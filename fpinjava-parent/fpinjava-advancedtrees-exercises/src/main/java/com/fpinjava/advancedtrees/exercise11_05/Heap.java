package com.fpinjava.advancedtrees.exercise11_05;


import com.fpinjava.common.Result;

import java.util.NoSuchElementException;

public abstract class Heap<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  protected static final Heap EMPTY = new Empty();

  protected abstract Result<Heap<A>> left();

  protected abstract Result<Heap<A>> right();

  protected abstract int rank();

  public abstract Result<A> head();

  public abstract int length();

  public abstract boolean isEmpty();

  public Heap<A> add(A element) {
    return merge(this, heap(element));
  }

  public static class Empty<A extends Comparable<A>> extends Heap<A> {

    private Empty() {}

    @Override
    protected int rank() {
      return 0;
    }

    @Override
    public Result<A> head() {
      return Result.failure(new NoSuchElementException("head() called on empty heap"));
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    protected Result<Heap<A>> left() {
      return Result.success(empty());
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(empty());
    }

    @Override
    public boolean isEmpty() {
      return true;
    }
  }

  public static class H<A extends Comparable<A>> extends Heap<A> {

    private final int length;
    private final int rank;
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;

    private H(int length, int rank, Heap<A> left, A head, Heap<A> right) {
      this.length = length;
      this.rank = rank;
      this.head = head;
      this.left = left;
      this.right = right;
    }

    @Override
    protected int rank() {
      return this.rank;
    }

    @Override
    public Result<A> head() {
      return Result.success(this.head);
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
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Heap<A> empty() {
    return EMPTY;
  }

  public static <A extends Comparable<A>> Heap<A> heap(A element) {
    return new H<>(1, 1,empty(), element, empty());
  }

  public static <A extends Comparable<A>> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
    return first.rank() >= second.rank()
            ? new H<>(first.length()+ second.length()+1, second.rank()+1, first, head, second)
            : new H<>(first.length()+second.length()+1, first.rank()+1, second, head, first);
  }

  public static <A extends Comparable<A>> Heap<A> merge(Heap<A> first, Heap<A> second) {
    return first.head().flatMap(fh -> second.head().flatMap(sh -> fh.compareTo(sh) <= 0
            ?first.left().flatMap(fl -> first.right().map(fr -> heap(fh, fl, merge(fr, second))))
            :second.left().flatMap(sl -> second.right().map(sr -> heap(sh, sl, merge(first, sr))))))
            .getOrElse(first.isEmpty()?second:first);
  }

  public Integer height(Heap<A> heap) {
    return heap.isEmpty()
            ? -1
            : Math.max(height(heap.left().getOrElse(() -> empty())),height(heap.right().getOrElse(() -> empty())))+1;
  }

  public static void main(String[] args) {
    Heap<Integer> root1 = new H<>(1, 1, empty(), 17 , empty());
    root1 = root1.add(26).add(8).add(3).add(14).add(23).add(10).add(21);

    Heap<Integer> root2 = new H<>(1, 1, empty(), 18, empty());
    root2 = root2.add(12).add(24).add(33).add(6).add(37).add(7).add(18);

    //Heap<Integer> rootFinal = merge(root1, root2);

    System.out.println(root1);
    PrintTree.print2DArray(PrintTree.treeToMatrix(root1, -1));

    System.out.println(root2);
    PrintTree.print2DArray(PrintTree.treeToMatrix(root2, -1));

    Heap<Integer> rootFinal = merge(root1, root2);
    System.out.println(rootFinal);
    PrintTree.print2DArray(PrintTree.treeToMatrix(rootFinal, -1));
  }
}
