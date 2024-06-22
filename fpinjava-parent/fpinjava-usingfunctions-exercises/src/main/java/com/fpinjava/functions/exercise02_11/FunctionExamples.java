package com.fpinjava.functions.exercise02_11;


public class FunctionExamples {

  public static Function<Integer,Function<Double,Double>> addTax = pr -> txr -> pr + pr/100 * txr;

  
  public static Function<Double, Double> addTaxSwapArg(Integer pr){ 
      return addTax.apply(pr);
  }
  
  public static Function<Double,Function<Integer,Double>>  partialFunWithSwappedArg = txr -> pr -> addTaxSwapArg(pr).apply(txr);
    
    
  public static <T, U, V> Function<U, Function<T, V>> reverseArgs(Function<T, Function<U, V>> f) {
      
      Function<U, Function<T, V>> fun= u -> t -> f.apply(t).apply(u);
      return fun;
  }
  
 public static void main(String[] args){
      Double txCalc = addTax.apply(100).apply(20.0);
      System.out.println("Tax calculated with addTax = "+txCalc);
      
      txCalc = partialFunWithSwappedArg.apply(20.0).apply(100);
      System.out.println("Tax calculated with addTax = "+txCalc);
      
      txCalc = reverseArgs(addTax).apply(20.0).apply(100);
      System.out.println("Tax calculated with addTax = "+txCalc);
  }
}
