package com.fpinjava.functions.exercise02_05;

public interface Function<T, U> {

  public static final Function<Integer, Integer> triple = x -> x * 3;

  public static final Function<Integer, Integer> square = x -> x * x;
  
  public static final Function<Integer, String> numberToString = x -> "" + x;
  public static final Function<String, Double> stringToDouble = x -> Double.parseDouble(x) *100.0; 
    
  U apply(T arg);

  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
     // return f1 -> f2 -> arg-> f1.apply(f2.apply(arg));
      
      return (Function<U,V> f1) -> (Function<T, U> f2) -> (T arg) -> f1.apply(f2.apply(arg));
  }
}
