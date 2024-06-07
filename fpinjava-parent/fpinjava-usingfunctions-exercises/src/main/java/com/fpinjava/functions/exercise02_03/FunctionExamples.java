package com.fpinjava.functions.exercise02_03;


public class FunctionExamples {

  public static final Function<Integer, Function<Integer, Integer>> add = x-> y-> x+y; // To be implemented

  public static final BinaryOperator add2 =  x-> y-> x + y; // To be implemented

  public static final BinaryOperator mult =  x-> y-> x * y; // To be implemented
}
