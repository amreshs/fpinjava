package com.fpinjava.io.exercise13_09;

public class Main {

  public static void main(String... args) {
    IO program = IO.forever(IO.unit("Hi again!")
                              .flatMap(s -> Console.printLine(s)));
    program.run();
  }
}
