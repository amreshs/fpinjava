package com.fpinjava.lists.exercise05_12;

import com.fpinjava.lists.exercise05_10.List;

public class Reverse {

  public static <A> List<A> reverseViaFoldLeft(List<A> lst) {
      return lst.foldLeft(List.list(), x->y-> x.cons(y));
  }
  
  public static void main(String [] args){
      List<String> lst = List.list("ABC", "PQR", "XYZ");
      List<String> lst2 = List.list("Amit", "Monica", "Aakanksha", "Aastha", "ABARAKADABARA");
      System.out.println(lst);
      System.out.println(reverseViaFoldLeft(lst).toString());
      System.out.println(reverseViaFoldLeft(lst2).toString());
      //System.out.println(lst2.init().toString());
      
  }
}
