package com.fpinjava.functions.exercise02_09;

public class FunctionExamples {

  private static String format = "%s, %s, %s, %s";

  public static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> f() {
    return x -> y -> z->w->String.format(format, x, y, z, w);
  }
  
   public static void main(String [] args){
     Function<Integer, Function<Double, Function<String, Function<String, String>>>> fun = f();    
     System.out.println(fun.apply(10).apply(20.5).apply("Aastha").apply("15-June-2024"));
   }
}
