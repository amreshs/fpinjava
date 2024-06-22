package com.fpinjava.functions.exercise02_12;

public class FunctionExamples {

  /*
   * Note: The same implementation may be used for all four cases.
   */

  public static final Function<Integer, Integer> factorial0 = n -> n <= 1? 1 : n * FunctionExamples.factorial0.apply(n-1);

  public static Function<Integer, Integer> factorial1;
  static {
    factorial1 = n -> n <= 1? 1 : n * factorial1.apply(n-1);
  }

  public final Function<Integer, Integer> factorial2 = n -> n <=1? 1 : n * this.factorial2.apply(n-1);

  public Function<Integer, Integer> factorial3;
  {
    factorial3 = n -> n <=1 ? 1 : n * factorial2.apply(n-1);
  }
  
  public static void main(String [] args){
      System.out.println("Caculated factorial value for 10 = " + factorial0.apply(10));
      System.out.println("Caculated factorial value for 11 = " + factorial1.apply(11));
      /*System.out.println("Caculated factorial value for 12 = " + factorial2.apply(12));
      
      Integer factVal = factorial3.apply(10);
      System.out.println("Caculated factorial value for 13 = " + factVal);
      */
  }
}
