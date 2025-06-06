package com.fpinjava.trees.exercise10_04;


import com.fpinjava.common.List;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

public abstract class Tree<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  private static Tree EMPTY = new Empty();

  abstract Tree<A> left();
  abstract Tree<A> right();

  public abstract A value();
  public abstract Tree<A> insert(A a);
  public abstract boolean member(A a);
  public abstract int size();
  public abstract int height();

  private static class Empty<A extends Comparable<A>> extends Tree<A> {

    @Override
    public A value() {
      throw new IllegalStateException("value() called on empty");
    }

    @Override
    Tree<A> left() {
      throw new IllegalStateException("left() called on empty");
    }

    @Override
    Tree<A> right() {
      throw new IllegalStateException("right() called on empty");
    }

    @Override
    public Tree<A> insert(A value) {
      return new T<>(empty(), value, empty());
    }

    @Override
    public boolean member(A a) {
      return false;
    }

    @Override
    public int size() {
      return 0;
    }

    @Override
    public int height() {
      return -1;
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  private static class T<A extends Comparable<A>> extends Tree<A> {

    private final Tree<A> left;
    private final Tree<A> right;
    private final A value;

    private T(Tree<A> left, A value, Tree<A> right) {
      this.left = left;
      this.right = right;
      this.value = value;
    }

    @Override
    public A value() {
      return value;
    }

    @Override
    Tree<A> left() {
      return left;
    }

    @Override
    Tree<A> right() {
      return right;
    }

    @Override
    public Tree<A> insert(A value) {
      return value.compareTo(this.value) < 0
          ? new T<>(left.insert(value), this.value, right)
          : value.compareTo(this.value) > 0
              ? new T<>(left, this.value, right.insert(value))
              : new T<>(this.left, value, this.right);
    }

    @Override
    public boolean member(A value) {
      return value.compareTo(this.value) < 0
          ? left.member(value)
          : value.compareTo(this.value) <= 0 || right.member(value);
    }

    @Override
    public int size() {
      return 1 + left.size() + right.size();
    }

    @Override
    public int height() {
      return 1 + Math.max(left.height() , right.height());
    }

    @Override
    public String toString() {
      return String.format("(T %s %s %s)", left, value, right);
    }
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Tree<A> empty() {
    return EMPTY;
  }

  public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
    return list.foldLeft(empty(), t -> t::insert);
  }

  @SafeVarargs
  public static <A extends Comparable<A>> Tree<A> tree(A... as) {
    return tree(List.list(as));
  }

  public static <A extends Comparable<A>> void inOrder(Tree<A> tree) {
    if(tree == EMPTY) return;

    inOrder(tree.left());
    System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
    System.out.println(tree.value());
    inOrder(tree.right());
  }

  public static void main(String[] args) {
    T<Integer> tree = new Tree.T<>(
            new T<>(
                    new T<>(
                            empty(), 2, empty()
                    ),
                    5 ,
                    new T<>(
                            empty(), 7, empty()
                    )
            ),
            10,
            new T<>(
                    new T<>(
                            empty(), 13, empty()
                    ),
                    15,
                    new T<>(
                            empty(), 18, new T<>(empty(), 20, empty())
                    )
            )
    );

    System.out.println("Tree Size = " + tree.size());
    System.out.println("Tree Height = " + tree.height());
  }
}
