package com.fpinjava.lists.exercise05_18;

import com.fpinjava.lists.exercise05_16.List;

public class DoubleToString {

  public static List<String> doubleToString(List<Double> lst) {
    return List.foldRight(lst, List.list(), h->t->t.cons(Double.toString(h)));
  }
  
  public static void main(String [] args){
      List<Double> lst = List.list(1.0, 2.0, 3.0);
      
      System.out.println(lst);
      System.out.println(doubleToString(lst));
      
  }
}
