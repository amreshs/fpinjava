package com.fpinjava.makingjavafunctional.exercise03_01;

public interface Result<T>{

    static <T> Result<T> success(T t){
        return new Success <> (t);
    }

    public static Result<String> failure(String errMessage){
        return new Failure<>(errMessage);
    }

    public void bind(Effect<T> success, Effect<String> error);
    public class Success<T> implements Result<T>{

        private final T value;

        Success(T t){
            this.value = t;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> errorMessage){
            success.apply(value);
        }
    };

    public class Failure<T> implements Result<T>{

        private final String errorMessage;

        Failure(String errMsg){
            this.errorMessage = errMsg;
        }

        @Override
        public void bind(Effect<T> success, Effect<String> failure){
            failure.apply(errorMessage);
        }
    };
};

/*public interface Result<T> {

  void bind(Effect<T> success, Effect<String> failure);

  static <T> Result<T> failure(String message) {
    return new Failure<>(message);
  }

  static <T> Result<T> success(T value) {
    return new Success<>(value);
  }

  class Success<T> implements Result<T> {

    private final T value;

    private Success(T t) {
      value = t;
    }

    @Override
    public void bind(Effect<T> success, Effect<String> failure) {
      throw new RuntimeException("To be implemented.");
    }
  }

  class Failure<T> implements Result<T> {

    private final String errorMessage;

    private Failure(String s) {
      this.errorMessage = s;
    }

    @Override
    public void bind(Effect<T> success, Effect<String> failure) {
      throw new RuntimeException("To be implemented.");
    }
  }

}
*/
