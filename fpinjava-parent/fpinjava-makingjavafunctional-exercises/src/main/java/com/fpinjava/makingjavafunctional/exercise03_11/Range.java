package com.fpinjava.makingjavafunctional.exercise03_11;

import java.util.List;
import java.util.ArrayList;


public class Range {

  public static List<Integer> range(int start, int end) {
    List<Integer> lst = new ArrayList<>();
    
    int temp = start;
    while(temp < end){
        lst.add(temp);
        temp = temp+1;
    }
    
    return lst;
  }
}
