package com.fpinjava.recursion.exercise04_08;

import java.util.List;
import java.util.ArrayList;

import com.fpinjava.common.Function;
import com.fpinjava.common.CollectionUtilities;


public class ComposeAll {

  static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldLeft(CollectionUtilities.reverse(lst), x, a -> b -> b.apply(a));
  }

  static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldRight(lst, x, a -> a::apply);
  }

  static <T> Function<T, T> andThenAllViaFoldLeft(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldLeft(lst, x, a -> b -> b.apply(a));
  }

  static <T> Function<T, T> andThenAllViaFoldRight(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldRight(CollectionUtilities.reverse(lst), x, a -> a::apply);
  }
  
  static Function<Integer, Integer> add  = x -> x +1;
  static Function<Integer , Integer> multiply_5  = x -> x * 5;
  public static void main(String [] args){
      
      List<Function<Integer, Integer>> lst = new ArrayList<>();
      
      lst.add(add);
      lst.add(multiply_5);
      
      System.out.println(composeAllViaFoldLeft(lst).apply(0));
      System.out.println(composeAllViaFoldRight(lst).apply(0));
      System.out.println(andThenAllViaFoldLeft(lst).apply(0));
      System.out.println(andThenAllViaFoldRight(lst).apply(0));
  } 
}
