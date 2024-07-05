package com.fpinjava.makingjavafunctional.exercise03_01;


import java.util.regex.Pattern;

import com.fpinjava.common.Function;
import com.fpinjava.makingjavafunctional.exercise03_01.Effect;
import com.fpinjava.makingjavafunctional.exercise03_01.Result;


/*public class EmailValidation {

  static Pattern emailPattern =
      Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

  static Function<String, Result<String>> emailChecker = s -> {
    throw new RuntimeException("To be implemented");
  };

  public static void main(String... args) {
    emailChecker.apply("this.is@my.email").bind(success, failure);
    emailChecker.apply(null).bind(success, failure);
    emailChecker.apply("").bind(success, failure);
    emailChecker.apply("john.doe@acme.com").bind(success, failure);
  }

  static Effect<String> success = null; // To be implemented
  
  static Effect<String> failure = null; // To be implemented
}*/

public class EmailValidation {
    static Pattern emailPattern = 
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    public static Function<String,Result<String>> emailChecker = s -> {
        if(s == null){
            return Result.failure("The email address can't be null");
        }
        else if(s.length() == 0){
            return Result.failure("The email address can't be blank");
        }
        else if(emailPattern.matcher(s).matches()){
            return Result.success("The email is sent to "+s);
        }
        else{
            return Result.failure("Invalid email address "+s);
        }
    };

    public static void main(String[] args){
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("abc.xyz.pqr").bind(success, failure);
        emailChecker.apply("john.doe@acm.com").bind(success, failure);
    }

    public static Effect<String> success = s -> System.out.println(s);
    public static Effect<String> failure = s -> System.err.println(s);
};