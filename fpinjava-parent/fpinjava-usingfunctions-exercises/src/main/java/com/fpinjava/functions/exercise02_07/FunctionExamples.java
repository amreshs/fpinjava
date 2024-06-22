package com.fpinjava.functions.exercise02_07;

public class FunctionExamples {

  public static <A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f) {
    return f.apply(a);
  }
  
    /*
  public static <A, B, C> Function<Function<A,B>, Function<Function<C, A>, Function<C, B>>> partialA(A a, Function<A, Function<B, C>> f) {
    return f1 -> f2 ->c -> f1.apply(f2.apply(a));
  }
    */
  
  public static Function<Double, Function<Double, Double>> addTax = txr-> pr-> pr + pr *txr;  
  
  public static Function<Integer, Function<Integer, Integer>> mult10 = x -> y -> x *y;  
  public static void main(String[] args){
      Integer val = 100;
      Function<Integer, Integer> fun = FunctionExamples.partialA(val, mult10);
      
      Integer finalVal = fun.apply(2);
      finalVal = mult10.apply(50).apply(2);

      System.out.println(finalVal);
      
      Function<Double, Double> fun2 = FunctionExamples.partialA(0.125, addTax);
      Double ttx = fun2.apply(100.0);
  
      System.out.println(ttx);
  }
}
