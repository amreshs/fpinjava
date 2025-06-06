package com.fpinjava.trees.exercise10_08;


import com.fpinjava.common.Function;
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

  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g);

  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g);

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
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g) {
      return identity;
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g) {
      return identity;
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
      if (a.isEmpty()) {
        return this;
      }
      if (a.value().compareTo(this.value) > 0) {
        return new T<>( left, value, right.merge(new T<>(empty(), a.value(), a.right()))).merge(a.left());
      }
      if (a.value().compareTo(this.value) < 0) {
        return new T<>( left.merge(new T<>(a.left(), a.value(), empty())), value, right).merge(a.right());
      }
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
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g) {
      // In order right:
      //return g.apply(f.apply(right.foldLeft(identity, f, g)).apply(this.value)).apply(left.foldLeft(identity, f, g));
      // In order left:
      //return g.apply(f.apply(left.foldLeft(identity, f, g)).apply(this.value)).apply(right.foldLeft(identity, f, g));
      // Post order left:
      //return g.apply(left.foldLeft(identity, f, g)).apply(f.apply(right.foldLeft(identity, f, g)).apply(this.value));
      // Post order right:
      return g.apply(right.foldLeft(identity, f, g)).apply(f.apply(left.foldLeft(identity, f, g)).apply(this.value));
      // Post order right
      //return f.apply(g.apply(right.foldLeft(identity, f, g)).apply(left.foldLeft(identity, f, g))).apply(this.value);
      // Post order left
      //return f.apply(g.apply(left.foldLeft(identity, f, g)).apply(right.foldLeft(identity, f, g))).apply(this.value);
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g) {
      // In order right:
      //return g.apply(right.foldRight(identity, f, g)).apply(f.apply(this.value).apply(left.foldRight(identity, f, g)));
      // In order left
      //return g.apply(left.foldRight(identity, f, g)).apply(f.apply(this.value).apply(right.foldRight(identity, f, g)));
      // Pre order right
      //return g.apply(f.apply(this.value).apply(right.foldRight(identity, f, g))).apply(left.foldRight(identity, f, g));
      // Pre order left
      return g.apply(f.apply(this.value).apply(left.foldRight(identity, f, g))).apply(right.foldRight(identity, f, g));
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
    return list.foldLeft(empty(), t -> a -> t.insert(a));
  }

  @SafeVarargs
  public static <A extends Comparable<A>> Tree<A> tree(A... as) {
    return tree(List.list(as));
  }
}
