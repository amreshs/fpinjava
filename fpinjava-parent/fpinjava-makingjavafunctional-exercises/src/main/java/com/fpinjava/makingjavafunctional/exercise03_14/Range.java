package com.fpinjava.makingjavafunctional.exercise03_14;

import com.fpinjava.makingjavafunctional.exercise03_13.CollectionUtilities;

import java.util.List;


public class Range {
  /*public static<T> List<T> prepend(T t, List<T> lst){
    return CollectionUtilities.foldLeft(lst, CollectionUtilities.list(t), a -> b -> CollectionUtilities.append(a, b));
}*/

  public static List<Integer> range(Integer start, Integer end) {
     return (end <= start)? CollectionUtilities.list() : CollectionUtilities.prepend(start, range(start+1, end));
  }

    public static void main(String [] args){
      range(5, 11).forEach(System.out::println);
}
}
