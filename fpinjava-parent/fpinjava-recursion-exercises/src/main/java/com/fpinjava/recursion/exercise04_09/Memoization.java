package com.fpinjava.recursion.exercise04_09;

import java.util.List;

import com.fpinjava.common.CollectionUtilities;
import com.fpinjava.common.Function;
import com.fpinjava.recursion.exercise04_02.TailCall;
import java.math.BigInteger;
import java.util.Arrays;
//import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Memoization {

  public static String fibo(int number) {
      List<BigInteger> lst = Arrays.asList();
      List<BigInteger> lst_fnl = fibo_.apply(BigInteger.ZERO).apply(BigInteger.ONE).apply(BigInteger.valueOf(number)).apply(lst).eval();
      return makeString(lst_fnl, ",");
  }
  
  public static Function<BigInteger, Function<BigInteger, Function<BigInteger, Function<List<BigInteger>,TailCall<List<BigInteger>>>>>> fibo_= 
          acc1 -> acc2 -> num -> lst-> (num == BigInteger.ZERO)? TailCall.res(lst) : TailCall.sus(() -> Memoization.fibo_.apply(acc2).apply(acc2.add(acc1)).apply(num.subtract(BigInteger.ONE)).apply(CollectionUtilities.append(lst, acc2)));

  public static <T> String makeString(List<BigInteger> lst, String separator) {
    //StringBuilder strgBuild = new StringBuilder("");
    return makeString_.apply(lst).apply(separator); 
  }
  
  public static String fiboCorecursive(int number){
      Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ZERO, BigInteger.ONE);
      
      Function<Tuple<BigInteger, BigInteger>, Tuple<BigInteger, BigInteger>> fun = x -> new Tuple<>(x._2, x._1.add(x._2));
      
      List<BigInteger> lst = map(List.iterate(seed, fun, number+1), x -> x._1); 
      return makeString(lst, ",");
      
  }
  /*
  public static Function<List<BigInteger>, Function<String, Function<StringBuilder,String>>> makeString_= 
        lst -> separator -> strBld -> lst.isEmpty()? strBld.toString() : Memoization.makeString_.apply(CollectionUtilities.tail(lst)).apply(separator).apply(strBld.append(CollectionUtilities.head(lst)).append(separator));
  */
  public static Function<List<BigInteger>, Function<String, String>> makeString_= 
        lst -> separator -> lst.isEmpty()? "" : CollectionUtilities.tail(lst).isEmpty()? "" + CollectionUtilities.head(lst) : CollectionUtilities.foldLeft(lst, "", x -> y -> x + separator + y);

  public static void main(String[] args){
      int x = 191;
      System.out.println("The Fabbonaci number of "+ x +" is "+ fibo(x));
      System.out.println("The Fabbonaci number of "+ x +" is "+ fiboCorecursive(x));
  }
}
