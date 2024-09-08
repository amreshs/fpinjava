package com.fpinjava.lists.exercise05_01;

import com.fpinjava.common.Function;

public abstract class List<A>{
    public abstract A head();
    public abstract List<A> tail();
    public abstract boolean isEmpty();
    
    public List<A> cons(A a){
        return new Cons<>(a, this);
    }
    
    @SuppressWarnings("rawtypes")
    private static final List Nil = new Nil();
    
    private List() {};
    
    public static class Nil<A> extends List<A>{
        
        private Nil(){}
        
        public A head(){
            throw new IllegalStateException("Head is called on empty list");
        }
    
        public List<A> tail(){
            throw new IllegalStateException("Tail is called on empty list");
        }
        
        public boolean isEmpty(){
            return true;
        }
    }
    
    public static class Cons<A> extends List<A>{
        private final A head;
        private final List<A> tail;
        
        private Cons(A head, List<A> tail){
            this.head = head;
            this.tail = tail;
        }
        
        public A head(){
            return this.head;
        }
    
        public List<A> tail(){
            return this.tail;
        }
        
        public boolean isEmpty(){
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <A> List<A> list(){
        return Nil;
    }
    
    @SafeVarargs
    public static <A> List<A> list(A...a){
        List<A> n = list();
        for(int i = a.length-1; i >=0; i--){
            n = new Cons<>(a[i], n);
        }
        return n;
    }
    
    public static Function<List<Integer>, String> changeToString = lst -> lst.isEmpty()? "" : lst.head()+"," + List.changeToString.apply(lst.tail());
    
    public static void main(String[] args){
        List<Integer> lst = list(1, 2, 3, 4, 5);
        lst = lst.cons(0);
        System.out.println(List.changeToString.apply(lst));
    }
} 