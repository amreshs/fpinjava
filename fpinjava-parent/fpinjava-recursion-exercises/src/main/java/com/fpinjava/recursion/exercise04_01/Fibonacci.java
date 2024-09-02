package com.fpinjava.recursion.exercise04_01;

import java.math.BigInteger;
import com.fpinjava.common.Function;

public class Fibonacci {
  
  public static BigInteger fib(int x) {
    return fib_.apply(BigInteger.ZERO).apply(BigInteger.ONE).apply(BigInteger.valueOf(x));
  }
  
  public static Function<BigInteger,Function<BigInteger, Function<BigInteger, BigInteger>>> fib_ = 
          acc1 -> acc2 -> num -> num.equals(BigInteger.ZERO) ? acc1 : Fibonacci.fib_.apply(acc2).apply(acc1.add(acc2)).apply( num.subtract( BigInteger.ONE));
  
  public static void main(String [] args){
      int x = 10;
      System.out.println("The Fabbonaci number of "+ x +" "+ fib(4000));
    } 
}
