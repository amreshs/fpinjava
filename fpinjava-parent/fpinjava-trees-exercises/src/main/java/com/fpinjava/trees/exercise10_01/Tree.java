package com.fpinjava.trees.exercise10_01;


public abstract class Tree<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  private static Tree EMPTY = new Empty();

  abstract Tree<A> left();
  abstract Tree<A> right();

  public abstract A value();

  public abstract Tree<A> insert(A a);

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
    public Tree<A> insert(A insertedValue) {
      return new T<>(empty(), insertedValue, empty());
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
    public Tree<A> insert(A insertedValue) {
      return  (insertedValue.compareTo(this.value()) < 0)? new T <>(left.insert(insertedValue),this.value(), this.right()):
              (insertedValue.compareTo(this.value()) > 0)? new T<>(this.left(), this.value(), right.insert(insertedValue)):
              new T<> (this.left(), insertedValue, this.right());
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
  public static <A extends Comparable<A>> void preOrder(Tree<A> tree) {
    if(tree == EMPTY) return;

    System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
    preOrder(tree.left());
    preOrder(tree.right());
  }

  public static <A extends Comparable<A>> void inOrder(Tree<A> tree) {
    if(tree == EMPTY) return;

    inOrder(tree.left());
    System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
    System.out.println(tree.value());
    inOrder(tree.right());
  }

  public static <A extends Comparable<A>> void postOrder(Tree<A> tree) {
    if(tree == EMPTY) return;

    postOrder(tree.left());
    postOrder(tree.right());
    System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
  }

  public static void main(String[] args) {
    T<Integer> root = new T<>(
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
    //System.out.println("Preorder traversal");
    //preOrder(root);

    System.out.println("Inorder traversal");
    inOrder(root);

    //System.out.println("Postorder traversal");
    //postOrder(root);

    Tree<Integer> nwRoot = root.insert(7);
    System.out.println("Inorder traversal");
    inOrder(nwRoot);
  }
}
