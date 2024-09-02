  package com.fpinjava.recursion.exercise04_07;

import java.util.List;

import com.fpinjava.common.Function;
import com.fpinjava.common.*;
import java.util.ArrayList;

public class ComposeAll {

  public static <T> Function<T, T> composeAll(List<Function<T, T>> lst) {
    return x -> {
        T y = x;
        for(Function<T,T> f : lst){
            y = f.apply(y);
        }
        
        return y;
    };
  }
  public static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldLeft(lst, x, a-> b-> b.apply(a));
  }

  public static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> lst) {
    return x -> CollectionUtilities.foldRight(lst, x, a -> a::apply);
  }
  
  static Function<Integer, Integer> add  = x -> x +1;
  static Function<Integer , Integer> multiply_5  = x -> x * 5;
  public static void main(String [] args){
      
      List<Function<Integer, Integer>> lst = new ArrayList<>();
      /*for(int i=0; i< 3000; i++){
          if(i% 2 == 0)
              lst.add(add);
          else
              lst.add(multiply_5);
      }*/
      
      lst.add(add);
      lst.add(multiply_5);
      
      
      System.out.println(composeAll(lst).apply(0));
      System.out.println(composeAllViaFoldLeft(lst).apply(0));
      System.out.println(composeAllViaFoldRight(lst).apply(0));
  } 
}
