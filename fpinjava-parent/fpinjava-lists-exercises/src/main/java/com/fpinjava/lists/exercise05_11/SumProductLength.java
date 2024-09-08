package com.fpinjava.lists.exercise05_11;

import com.fpinjava.lists.exercise05_10.List;

public class SumProductLength {

  public static Integer sumViaFoldLeft(List<Integer> lst) {
    return lst.foldLeft(0, x->y->x+y);
  }

  public static Double productViaFoldLeft(List<Double> lst) {
    return lst.foldLeft(1.0, x->y->x*y);
  }

  public static <A> Integer lengthViaFoldLeft(List<A> lst) {
    return lst.foldLeft(0, x->y->x+1);
  }
  
  public static void main(String[] args){
      List<Integer> lst = List.list(1, 2, 3, 4, 5);
      List<Double> lstDbl = List.list(1.0, 2.0, 3.0, 4.0, 5.0);
      System.out.println("Sum of list ="+sumViaFoldLeft(lst));
      System.out.println("Product of list ="+productViaFoldLeft(lstDbl));
      System.out.println("Length of list ="+lengthViaFoldLeft(lst));
  }
}
