package com.fpinjava.recursion.corecursion;

import java.math.BigInteger;
import java.util.List;
//import java.util.Map;

import com.fpinjava.common.CollectionUtilities;
import com.fpinjava.common.CollectionUtilities.*;
import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;
import com.fpinjava.recursion.exercise04_09.Memoization;
//import com.fpinjava.common.List;
//import com.fpinjava.common.Map;

public class Corecursion {

  public static String fiboCorecursive(int number) {
    Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ZERO, BigInteger.ONE);
    Function<Tuple<BigInteger, BigInteger>,Tuple<BigInteger, BigInteger>> f = x -> new Tuple<>(x._2, x._1.add(x._2));
    List<BigInteger> lst = CollectionUtilities.map(com.fpinjava.common.List.iterate(seed, f, number + 1), x -> x._1);
    return Memoization.makeString(lst, ", ");
   }
}
