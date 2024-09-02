package com.fpinjava.recursion.exercise04_04;

import java.util.List;
import com.fpinjava.common.Function;
import static com.fpinjava.common.CollectionUtilities.*;
import com.fpinjava.recursion.exercise04_02.TailCall;

public class Range {

  public static List<Integer> range(Integer start, Integer end) {
      List<Integer> lst = list();
      return range_.apply(start).apply(end).apply(lst).eval();
  }
  
  public static Function<Integer, Function<Integer, Function<List<Integer>, TailCall<List<Integer>>>>> range_ = 
          start -> end -> lst -> (start == end)? TailCall.res(lst) : TailCall.sus(() -> Range.range_.apply(start+1).apply(end).apply(append(lst, start)));  

  public static void main(String [] args){
      List<Integer> lst = range(0, 100);
      
      for(int i =0; i < lst.size(); i++)
        System.out.println("Total output = " + lst.get(i));
  }

}


