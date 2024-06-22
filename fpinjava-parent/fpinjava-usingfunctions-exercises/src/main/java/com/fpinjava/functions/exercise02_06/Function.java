package com.fpinjava.functions.exercise02_06;


public interface Function<T, U> {
  U apply(T arg);

  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return f -> g -> x -> f.apply(g.apply(x));
  }

  public static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
    return f -> g -> x -> g.apply(f.apply(x));
  }
  
  public static final Function<Integer, String> intToString= n -> "" + n;
  public static final Function<String, Double> stringToDouble = s -> Double.parseDouble(s)*100+0.25;
  
  public static final Function<Integer, Integer> triple = x -> x * 3;
  public static final Function<Integer, Integer> square = x -> x * x;
  
  public static void main(String[] args){
        Double db = Function.<Integer, String, Double>higherAndThen().apply(n-> "" + n).apply(s -> Double.parseDouble(s)+100.0 +0.25).apply(4);
        //Integer num = Function.<Integer, Integer, Integer>higherAndThen().apply(Function.square).apply(Function.triple).apply(4);
        System.out.println("The number is "+db);
  }
}
