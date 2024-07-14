package com.fpinjava.makingjavafunctional.exercise03_10;

import com.fpinjava.common.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionUtilities {

  public static <T> List<T > list() {
    return Collections.emptyList();
  }

  public static <T> List<T > list(T t) {
    return Collections.singletonList(t);
  }

  public static <T> List<T > list(List<T> ts) {
    return Collections.unmodifiableList(new ArrayList<>(ts));
  }

  @SafeVarargs
  public static <T> List<T > list(T... t) {
    return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
  }

  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  private static <T> List<T > copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return Collections.unmodifiableList(workList);
  }

  public static <T, U> U foldLeft(List<T> ts,
                                  U identity,
                                  Function<U, Function<T, U>> f) {
    U result = identity;
    for (T t : ts) {
      result = f.apply(result).apply(t);
    }
    return result;
  }

  public static <T, U> U foldRight(List<T> ts, U identity,
                                   Function<T, Function<U, U>> f) {
    return ts.isEmpty()
        ? identity
        : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
  }

  public static <T> List<T> append(List<T> list, T t) {
    List<T> ts = copy(list);
    ts.add(t);
    return Collections.unmodifiableList(ts);
  }

  public static <T> List<T> prepend(T t, List<T> list) {
    return foldLeft(list, list(t), a -> b -> append(a, b));
  }

  public static <T> List<T> reverse(List<T> list) {
    return foldLeft(list, list(), x -> y -> prepend(y, x));
  }

  public static <T> List<T> reverse2(List<T> list) {
    return foldLeft(list, list(), x -> y -> foldLeft(x, list(y), a -> b -> append(a, b)));
  }

  public static <T, U> U mapAccessories(List<T> t, U lstIdentity, Function<T, U> f){
      //return foldLeft(t, lstIdentity, a -> b -> a.append(f.apply(b)));
      
      throw new RuntimeException("To be implemented");
  }
  public static <T, U> List<U> mapViaFoldLeft(List<T> lst, Function<T, U> f) {
     //Function<List<U>, Function<T,U>> wrapper = a -> b -> a.append(f.apply(b));
     //return foldLeft(lst, list(), a -> b -> foldLeft(list(f.apply(b)), a, x -> y -> append(x, y)));
     return foldLeft(lst, list(), a -> b -> append(a, f.apply(b)));
  }

  public static <T> List<T> prepend(List<T> lst, T t){
      return foldLeft(lst, list(t), a -> b -> append(a, b));
  }
  
  public static <T, U> List<U> mapViaFoldRight(List<T> lst, Function<T, U> f) {
    return foldRight(lst, list(), a -> b -> prepend(b, f.apply(a)));
  }

  public static void main(String [] args){
      Function<Integer, Double> fun = a-> a *10.1; 
  
      List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5);//("1", "2","3", "4");
       
      System.out.println("Map implementation using foldLeft");
      List<Double> lstRv = CollectionUtilities.mapViaFoldLeft(lst, fun);
      for(Double d:lstRv)
            System.out.println(d);
      
      
      System.out.println("Map implementation using foldRight");
      lstRv = CollectionUtilities.mapViaFoldRight(lst, fun);
      for(Double d:lstRv)
            System.out.println(d);
  }

}


