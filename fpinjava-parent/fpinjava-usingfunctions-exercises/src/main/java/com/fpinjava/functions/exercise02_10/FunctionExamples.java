package com.fpinjava.functions.exercise02_10;
//import static org.junit.Assert.assertEquals;

public class FunctionExamples {

  public static <A, B, C> Function<A, Function<B, C>> curry(Function<Tuple<A, B>, C> f) {
      return a -> b -> f.apply(new Tuple<>(a,b));
  }
  
  public static Function<Tuple<Integer, Double>, String> tupleFun = t-> t.toString();
      
  
  public static void main(String[] args){
      Tuple tp = new Tuple<>(100, 125.5);
      
      String str = tupleFun.apply(tp);
      System.out.println(str);
      
      Function<Integer, Function<Double, String>> f = curry(tupleFun);
      
      str = f.apply(100).apply(125.5);
      System.out.println(str);
      
      //assertEquals(f.apply(100).apply(125.5), tupleFun.apply(tp));
      
  }
}
