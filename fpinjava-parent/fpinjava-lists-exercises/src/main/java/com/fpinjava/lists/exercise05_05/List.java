package com.fpinjava.lists.exercise05_05;

import com.fpinjava.common.Function;
import com.fpinjava.common.TailCall;

import static com.fpinjava.common.TailCall.*;


public abstract class List<A> {

  public abstract A head();
  public abstract List<A> tail();
  public abstract boolean isEmpty();
  public abstract List<A> setHead(A h);
  public abstract List<A> drop(int n);
  public abstract List<A> dropWhile(Function<A, Boolean> f);
  public abstract List<A> concat(List<A> lst2);

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
      return "[NIL]";
    }

    @Override
    public List<A> drop(int n) {
      return this;
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return this;
    }
    
    @Override
    public List<A> concat(List<A> lst2){
        return lst2;
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

    public String toString() {
      return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }

    @Override
    public List<A> drop(int n) {
      return n <= 0
          ? this
          : drop_(this, n).eval();
    }

    private TailCall<List<A>> drop_(List<A> list, int n) {
      return n <= 0 || list.isEmpty()
          ? ret(list)
          : sus(() -> drop_(list.tail(), n - 1));
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return dropWhile(this, f).eval();
    }
    
    private TailCall<List<A>> dropWhile(List<A> lst, Function<A, Boolean> f) {
      return lst.isEmpty() || !f.apply(lst.head())? TailCall.ret(lst) : TailCall.sus(() -> dropWhile(lst.tail(), f));
    }
    
    @Override
    public List<A> concat(List<A> lst2){
        return concat(this, lst2);
    }
    
    private List<A> concat(List<A> lst1, List<A> lst2){
        return lst1.isEmpty()? lst2 : new Cons(lst1.head(),concat(lst1.tail(), lst2));
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
  
  public static Function<String, Boolean> blankCheck = str -> !str.isBlank(); 
  
  public static void main(String [] args){
      List<String> lst = list("ABC", "PQR", "");
      List<String> lst2 = list("Amit", "Monica", "Aakanksha", "Aastha");
      System.out.println(lst);
      //System.out.println(lst.dropWhile(blankCheck));
      System.out.println((lst.concat(lst2)).toString());
      
  }
}
