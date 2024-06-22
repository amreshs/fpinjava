package com.fpinjava.functions.exercise02_08;

public class FunctionExamples {

  public static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
      return a -> f.apply(a).apply(b);
  }
  
  public static Function<Integer, Function<Double, String>> intToStringDouble = i -> d -> ""+(i*d);
  public static void main(String [] args){
     Function<Integer, String> fun = FunctionExamples.partialB(30.3, intToStringDouble);
     
     System.out.println(fun.apply(100));
     
     System.out.println(FunctionExamples.func(10, 20.5, "Aastha", "15-June-2024"));
   }
  
   public static <A, B, C, D> String func(A a, B b, C c, D d){
       Function<A, Function<B, Function<C, Function<D, String>>>> fun  = x -> y -> z->w->String.format("%s %s %s %s", x, y, z, w);
       return fun.apply(a).apply(b).apply(c).apply(d);
   }
}
