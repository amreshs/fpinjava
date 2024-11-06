package com.fpinjava.optionaldata.exercise06_07;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.TailCall;
import com.fpinjava.optionaldata.exercise06_06.Option;

import static com.fpinjava.common.TailCall.*;

public class Variance {

  static Function<List<Double>, Double> sum_ = ds -> ds.foldLeft(0.0, x -> y -> x + y); // Implement this function
  static Double sum(List<Double> lst){
    return sum_.apply(lst);
  }

  static Function<List<Double>, Option<Double>> mean_ = ds -> ds.isEmpty()? Option.none(): Option.some(sum_.apply(ds)/ds.length()); // Implement this function
  static Option<Double> mean(List<Double> lst){
    return mean_.apply(lst);
  }
  //static Function<List<Double>, Option<Double>> variance = ds -> ds.isEmpty()? Option.none() : Option.some(ds.foldLeft(0.0, x -> y -> x + Math.pow((y - mean.apply(ds).getOrElse(() -> 0.0)), 2))/ds.length()); // Implement this function
  static Function<List<Double>, Option<Double>> variance_ = ds -> mean_.apply(ds).flatMap(m -> mean_.apply(ds.map(x -> Math.pow(x-m, 2))));
  static Option<Double> variance(List<Double> lst){
    //return variance_.apply(lst);
    return mean(lst).flatMap(m -> mean(lst.map(x -> Math.pow(x-m, 2))));
  }

  public static void main(String[] args) {
    List<Double> lst = List.list(61.0, 62.45, 63.67, 64.56, 69.12, 64.09);
    System.out.println(Variance.variance(lst));

    List<Double> lstEmpty = List.list();
    System.out.println(Variance.variance(lstEmpty));
  }
}



