package com.fpinjava.makingjavafunctional.exercise03_02;

import java.util.regex.Pattern;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Function;
import com.fpinjava.makingjavafunctional.exercise03_01.Effect;
import com.fpinjava.makingjavafunctional.exercise03_01.Result;


public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>>{
    
    private Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier){
        super(booleanSupplier, resultSupplier);
    }
    
    public static <T> Case<T> mcase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier){
        return new Case<>(booleanSupplier, resultSupplier);
    }
    
    public static <T> DefaultCase<T>  mcase(Supplier<Result<T>> resultSupplier){
        return new DefaultCase<>(()->true, resultSupplier);
    }
    
    private static class DefaultCase<T> extends Case<T>{
        private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier){
           super(booleanSupplier, resultSupplier);
        }
    }

    @SafeVarargs
    public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>...nCases){
        
        for(Case<T> aCase : nCases)
            if(aCase._1.get()) return aCase._2.get();
    
        return defaultCase._2.get();
    }
    
    static Pattern emailPattern = 
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    
    static Function<String, Result<String>> emailChecker = s -> match(
       mcase(() -> Result.success(s)),
       mcase(() -> s == null,() -> Result.failure("The email id can't be null")),
       mcase(() -> s == "", () -> Result.failure("The email id can't be blank")),
       mcase(() -> !emailPattern.matcher(s).matches(), () -> Result.failure("Email id " +s + " is invalid"))
    );
    
    public static Effect<String> success = s -> System.out.println("Email is sent to "+s);
    public static Effect<String> failure = s -> System.err.println("The failure as "+s);
    
    public static void main(String [] args){
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("abc.pqr.xyz").bind(success, failure);
        emailChecker.apply("john.doe@acm.com").bind(success, failure);
    }
};
/*
public class Case<T> { // Case class should extend Tuple

  private Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
    // Call super constructor
  }

  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
    throw new RuntimeException("To be implemented.");
  }

  public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
    throw new RuntimeException("To be implemented.");
  }

  private static class DefaultCase<T> extends Case<T> {

    private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
      super(booleanSupplier, resultSupplier);
    }
  }

  @SafeVarargs
  public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
    throw new RuntimeException("To be implemented.");
  }
}
*/
