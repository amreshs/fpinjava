package com.fpinjava.recursion.exercise04_05;

import java.util.List;

import com.fpinjava.recursion.exercise04_02.TailCall;
import com.fpinjava.common.Function;
import static com.fpinjava.common.CollectionUtilities.*;
import java.util.Arrays;

public class FoldRight {

  public static <T, U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f) {
      //return (ts.isEmpty())? identity : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
      return foldRight_(reverse(ts), identity, f);
  }
  
  public static <T, U> U foldRight_(List<T> ts, U identity, Function<T, Function<U, U>> f) {
      return ts.isEmpty()? identity : FoldRight.foldRight_(tail(ts), f.apply(head(ts)).apply(identity), f);
  }
  
  public static <T, U> U foldRight(U identity, List<T> ts, Function<T, Function<U, U>> f) {
      return (ts.isEmpty())? identity : foldRight(f.apply(head(ts)).apply(identity), tail(ts), f);
  }
  
  public static Function<Integer, Function<String, String>> funText = 
          i -> s -> "(" + i + s + ")"; 
  public static void main(String [] args){
      List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5);
      String str = foldRight(lst, "", funText);
      
      System.out.println(str);
  }
}
