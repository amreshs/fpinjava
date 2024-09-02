package com.fpinjava.recursion.exercise04_06;

import java.util.List;
import java.util.ArrayList;

import com.fpinjava.common.Function;
import static com.fpinjava.common.Function.*;
import com.fpinjava.recursion.exercise04_05.FoldRight;


public class ComposeAll {

  static <T> Function<T, T> composeAll(List<Function<T, T>> lst) {
      return FoldRight.foldRight(lst, identity(), (x -> y -> x.compose(y)));
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
      
      lst.add(multiply_5);
      lst.add(add);
      
      System.out.println(composeAll(lst).apply(0));
  } 
}
