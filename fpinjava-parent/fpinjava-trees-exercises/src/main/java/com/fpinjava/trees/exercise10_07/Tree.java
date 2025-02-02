package com.fpinjava.trees.exercise10_07;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;

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
  public abstract Result<A> max();
  public abstract Result<A> min();
  public abstract Tree<A> remove(A a);
  public abstract boolean isEmpty();
  public abstract Tree<A> merge(Tree<A> a);

  /**
   * Merges two subtrees with the particularity that all elements of one
   * are either greater or smaller than all elements of the other.
   *
   * This is an optimized merge for removal of the value, when we need to merge
   * the remaining right and left tree.
   */
  protected abstract Tree<A> removeMerge(Tree<A> ta);

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
    public Result<A> max() {
      return Result.empty();
    }

    @Override
    public Result<A> min() {
      return Result.empty();
    }

    @Override
    public Tree<A> remove(A a) {
      return this;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public Tree<A> merge(Tree<A> a) {
      return a;
    }

    @Override
    protected Tree<A> removeMerge(Tree<A> ta) {
      return ta;
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
    private final int height;
    private final int size;

    private T(Tree<A> left, A value, Tree<A> right) {
      this.left = left;
      this.right = right;
      this.value = value;
      this.height = 1 + Math.max(left.height(), right.height());
      this.size = 1 + left.size() + right.size();
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
      return size;
    }

    @Override
    public int height() {
      return height;
    }

    @Override
    public Result<A> max() {
      return right.max().orElse(() -> Result.success(value));
    }

    @Override
    public Result<A> min() {
      return left.min().orElse(() -> Result.success(value));
    }

    @Override
    public Tree<A> remove(A a) {
      if (a.compareTo(this.value) < 0) {
        return new T<>(left.remove(a), value, right);
      } else if (a.compareTo(this.value) > 0) {
        return new T<>(left, value, right.remove(a));
      } else {
        return left.removeMerge(right);
      }
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public Tree<A> merge(Tree<A> a) {
      if(a.isEmpty()) return this;

      if(a.value().compareTo(this.value) > 0) {
        return new T<>(left, value, right.merge(new T<>(empty(), a.value(), a.right()))).merge(a.left());
      }
      else if(a.value().compareTo(this.value) < 0) {
        return new T<>(left.merge( new T<> (a.left(), a.value(), empty())), value, right).merge(a.right());
      }
      else
        return new T<>(left.merge(a.left()), value, right.merge(a.right()));
    }

    protected Tree<A> removeMerge(Tree<A> ta) {
      if (ta.isEmpty()) {
        return this;
      }
      if (ta.value().compareTo(value) < 0) {
        return new T<>(left.removeMerge(ta), value, right);
      } else if (ta.value().compareTo(value) > 0) {
        return new T<>(left, value, right.removeMerge(ta));
      }
      throw new IllegalStateException("Shouldn't be merging two subtrees with the same value");
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
    //System.out.println(System.identityHashCode(tree)+ " " + tree.value() );
    System.out.println(tree.value());
    inOrder(tree.right());
  }

  public static void main(String[] args) {
    T<Integer> tree = new T<>(
            new T<>(
                    new T<>(
                            empty(), 0, empty()
                    ),
                    1 ,
                    new T<>(
                            empty(), 2, empty()
                    )
            ),
            3,
            new T<>(
                    new T<>(
                            empty(), 4, empty()
                    ),
                    5,
                    new T<>(
                            empty(), 6, empty()
                    )
            )
    );

    T<Integer> tree2 = new T<>(
            new T<>(
                    new T<>(
                            empty(), 1, empty()
                    ),
                    3 ,
                    new T<>(
                            empty(), 4, empty()
                    )
            ),
            5,
            new T<>(
                    new T<>(
                            empty(), 6, empty()
                    ),
                    7,
                    new T<>(
                            empty(), 8, empty()
                    )
            )
    );

    inOrder(tree.merge(tree2));
  }
}
