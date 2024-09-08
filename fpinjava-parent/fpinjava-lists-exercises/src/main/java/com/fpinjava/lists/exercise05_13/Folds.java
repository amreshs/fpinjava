package com.fpinjava.lists.exercise05_13;

import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_10.List;

public class Folds {

  public static <A, B> B foldRightViaFoldLeft(List<A> lst, B identity, Function<A, Function<B, B>> f) {
    return lst.reverse().foldLeft(identity, x->y->f.apply(y).apply(x));
  }

  public static <A, B> B foldLeftViaFoldRight(List<A> lst, B identity, Function<B, Function<A, B>> f) {
    return List.foldRight(lst.reverse(), identity, x->y->f.apply(y).apply(x));
  }
}
