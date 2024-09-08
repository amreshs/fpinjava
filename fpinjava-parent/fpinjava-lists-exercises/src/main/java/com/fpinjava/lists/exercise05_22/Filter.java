package com.fpinjava.lists.exercise05_22;

import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_21.List;

public class Filter {

  public static <A> List<A> filterViaFlatMap(List<A> lst, Function<A, Boolean> p) {
      return lst.flatMap(x -> p.apply(x)? List.list(x): List.list());
  }
  
  public static void main(String [] args){
      List<Double> lst = List.list(1.0, 2.0, 3.0);
      
      System.out.println(lst);
      System.out.println(lst.filter(x-> x%3==0));
  }
}
