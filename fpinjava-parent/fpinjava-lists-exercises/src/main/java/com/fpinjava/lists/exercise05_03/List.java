package com.fpinjava.lists.exercise05_03;
import com.fpinjava.common.TailCall;


public abstract class List<A> {

  public abstract A head();
  public abstract List<A> tail();
  public abstract boolean isEmpty();
  public abstract List<A> setHead(A h);

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  @SuppressWarnings("rawtypes")
  public static final List NIL = new Nil();

  private List() {}

  private static class Nil<A> extends List<A> {

    private Nil() {}

    public A head() {
      throw new IllegalStateException("head called en empty list");
    }

    public List<A> tail() {
      throw new IllegalStateException("tail called en empty list");
    }

    public boolean isEmpty() {
      return true;
    }

    @Override
    public List<A> setHead(A h) {
      throw new IllegalStateException("setHead called en empty list");
    }

    public String toString() {
      return "[To be implemented]";
    }
  }

  private static class Cons<A> extends List<A> {

    private final A head;
    private final List<A> tail;

    private Cons(A head, List<A> tail) {
      this.head = head;
      this.tail = tail;
    }

    public A head() {
      return head;
    }

    public List<A> tail() {
      return tail;
    }

    public boolean isEmpty() {
      return false;
    }

    @Override
    public List<A> setHead(A h) {
      return new Cons<>(h, tail());
    }

    @Override
    public String toString() {
      return String.format("[%sNil]", toString(new StringBuilder(), this).eval());
    }
    
    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> lst){
        return lst.isEmpty()? TailCall.ret(acc) : TailCall.sus(() -> toString(acc.append(lst.head()).append(","), lst.tail()));
    }
}

  @SuppressWarnings("unchecked")
  public static <A> List<A> list() {
    return NIL;
  }

  @SafeVarargs
  public static <A> List<A> list(A... a) {
    List<A> n = list();
    for (int i = a.length - 1; i >= 0; i--) {
      n = new Cons<>(a[i], n);
    }
    return n;
  }
  
  public static void main(String[] args){
        List<Integer> lst = list(1, 2, 3, 4, 5);
        lst = lst.setHead(0);
        System.out.println(lst.toString());
    }
}
