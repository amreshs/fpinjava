package com.fpinjava.lists.exercise05_08;

import com.fpinjava.lists.exercise05_06.List;

public class Product {

  public static Double product(List<Double> ints) {
    return ints.isEmpty()? 1.0 : (ints.head() == 0.0)? 0.0 : ints.head()* product(ints.tail());
  }
  
  public static void main(String[] args){
      List<Double> lst = List.list(1.0, 2.0, 3.0, 4.0, 5.0);
      System.out.println("Product of list ="+product(lst));
  }
}
