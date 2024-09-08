package com.fpinjava.lists.exercise05_07;

import com.fpinjava.lists.exercise05_06.List;

public class Sum {

  public static Integer sum(List<Integer> ints) {
        return ints.isEmpty()? 0 : ints.head() +  sum(ints.tail());
  }
  
  public static void main(String[] args){
      List<Integer> lst = List.list(1, 2, 3, 4, 5);
      System.out.println("Sum of list ="+sum(lst));
  }
}
