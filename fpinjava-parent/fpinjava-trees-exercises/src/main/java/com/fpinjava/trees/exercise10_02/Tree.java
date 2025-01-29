package com.fpinjava.trees.exercise10_02;


public abstract class Tree<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  private static Tree EMPTY = new Empty();

  abstract Tree<A> left();
  abstract Tree<A> right();

  public abstract A value();
  public abstract Tree<A> insert(A a);

  public abstract boolean member(A a);

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
      return (value.compareTo(this.value())< 0)? left().member(value) : //empty will return false
              (value.compareTo(this.value())>0)? right().member(value): //empty will return false
                      true; //value matched
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

  public static <A extends Comparable<A>> void inOrder(Tree<A> tree) {
    if(tree == EMPTY) return;

    inOrder(tree.left());
    System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
    System.out.println(tree.value());
    inOrder(tree.right());
  }

  public static void main(String[] args) {
   T<Integer> tree = new T<>(
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
                            empty(), 18, empty()
                    )
            )
    );

   System.out.println(tree.member(7));
  }
}
