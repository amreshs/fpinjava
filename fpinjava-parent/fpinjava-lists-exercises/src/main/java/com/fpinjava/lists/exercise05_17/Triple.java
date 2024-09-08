package com.fpinjava.lists.exercise05_17;

import com.fpinjava.lists.exercise05_16.List;

public class Triple {

  public static List<Integer> triple(List<Integer> lst) {
    return lst.reverse().foldLeft(List.list(), x->y-> x.cons(y*3));
  }
  
  public static void main(String [] args){
      List<Integer> lst = List.list(1, 2, 3);
      
      System.out.println(lst);
      System.out.println(triple(lst).toString());
      
  }
}
