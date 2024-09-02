package com.fpinjava.recursion.exercise04_03;

import java.util.List;

import com.fpinjava.common.Function;
import static com.fpinjava.common.CollectionUtilities.*;
import com.fpinjava.recursion.exercise04_02.TailCall;
import java.util.Arrays;

public class FoldLeft {


    public static <T, U> U foldLeftRecursive(List<T> ts, U identity, Function<U, Function<T, U>> f) {
        return ts.isEmpty()? identity : foldLeftRecursive(tail(ts), f.apply(identity).apply(head(ts)), f);
    }
  
   
  public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
    return FoldLeft.foldLeft_(ts, identity, f).eval();
  }
 
  public static <T, U> TailCall<U> foldLeft_(List<T> lst, U identity, Function<U, Function<T, U>> fun){ 
       if(lst.isEmpty()){  
           return TailCall.res(identity);
       }
       else{
           return TailCall.sus(() -> FoldLeft.foldLeft_(tail(lst), fun.apply(identity).apply(head(lst)), fun));
       }
   }
  
 public static  Double foldLeftWithLambda(List<Integer> ts, Double identity, Function<Double, Function<Integer, Double>> f) {
    //return ts.isEmpty()? identity : foldLeft(tail(ts), f.apply(identity).apply(head(ts)), f);
    return FoldLeft.foldLeftLambda_.apply(ts).apply(identity).apply(f).eval();
    //return identity;
  }
 public static  Function<List<Integer>, Function<Double, Function<Function<Double, Function<Integer, Double>>, TailCall<Double>>>> foldLeftLambda_= 
     lst -> iden -> fun -> lst.isEmpty()?  TailCall.res(iden) : TailCall.sus(() -> FoldLeft.foldLeftLambda_.apply(tail(lst)).apply(fun.apply(iden).apply(head(lst))).apply(fun));
   /*
   public static <T, U> Function<List<T>, Function<U, Function<Function<T, U>, TailCall<U>>>> foldLeft_= 
     lst -> iden -> fun -> lst.isEmpty()?  TailCall.res(iden) : TailCall.sus(() -> FoldLeft.foldLeft_.apply(tail(lst)).apply(fun.apply(iden).apply(head(lst))).apply(fun));
  */
  public static Function<Double, Function<Integer, Double>> add = a -> b -> a + b;
  public static void main(String [] args){
      List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5);
      
      Double agr = foldLeft(lst , 0.0, add);
      System.out.println("Total output = "+agr);
  }
}
