package com.fpinjava.recursion.exercise04_02;

import java.math.BigInteger;
import com.fpinjava.common.Function;
import com.fpinjava.recursion.exercise04_02.TailCall;


public class Fibonacci {

  public static BigInteger fib(int x) {
     return fib_.apply(BigInteger.ZERO).apply(BigInteger.ONE).apply(BigInteger.valueOf(x)).eval();
  }
  
  public static Function<BigInteger, Function<BigInteger, Function<BigInteger, TailCall<BigInteger>>>> fib_ = 
          acc1 -> acc2 -> num -> (num == BigInteger.ZERO)? TailCall.res(acc1) : TailCall.sus(() -> Fibonacci.fib_.apply(acc2).apply(acc1.add(acc2)).apply(num.subtract(BigInteger.ONE)));

  public static void main(String[] args){
      int x = 10;
      System.out.println("The Fabbonaci number of "+ x +" "+ fib(10000));
  }


}
