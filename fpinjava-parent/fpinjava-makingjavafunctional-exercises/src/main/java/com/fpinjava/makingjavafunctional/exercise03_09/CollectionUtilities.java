package com.fpinjava.makingjavafunctional.exercise03_09;

import com.fpinjava.common.Function;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.add;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.fold;
import static com.fpinjava.makingjavafunctional.exercise03_07.CollectionUtilities.rightOpr;
import static com.fpinjava.makingjavafunctional.exercise03_08.CollectionUtilities.foldRight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionUtilities {

  public static <T> List<T > list() {
    return Collections.emptyList();
  }

  public static <T> List<T > list(T t) {
    return Collections.singletonList(t);
  }

  public static <T> List<T > list(List<T> ts) {
    return Collections.unmodifiableList(new ArrayList<>(ts));
  }

  @SafeVarargs
  public static <T> List<T > list(T... t) {
    return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
  }

  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  private static <T> List<T > copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return Collections.unmodifiableList(workList);
  }

  public static <T, U> U foldLeft(List<T> ts,
                                  U identity,
                                  Function<U, Function<T, U>> f) {
    U result = identity;
    for (T t : ts) {
      result = f.apply(result).apply(t);
    }
    return result;
  }

  public static <T, U> U foldRight(List<T> ts, U identity,
                                   Function<T, Function<U, U>> f) {
    return ts.isEmpty()
        ? identity
        : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
  }

  public static <T> List<T> append(List<T> list, T t) {
    List<T> ts = copy(list);
    ts.add(t);
    return Collections.unmodifiableList(ts);
  }

  public static <T> List<T> prepend(T t, List<T> lst) {
    return foldLeft(lst, list(t), a->b->append(a,b));
  }

  public static <T> List<T> reverse(List<T> lst) {
    //return foldLeft(lst, list(), a->b->prepend(a,b));
    return foldLeft(lst, list(), a -> b -> foldLeft(a, list(b), x -> y -> append(x,y) ));
  }
  
  public static <T> List<T> reverseUsingFoldRight(List<T> lst) {
    //return foldLeft(lst, list(), a->b->prepend(a,b));
    return foldRight(lst, list(), a -> b -> append(b,a));
  }
  
  public static void main(String[] args){
        List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5);//("1", "2","3", "4");

        //List<String> lst_tail = CollectionUtilities.tail(lst);
        
        System.out.println("Reverse Using FoldLeft");
        List<Integer> lstRv = CollectionUtilities.reverse(lst);
        for(Integer i:lstRv)
            System.out.println(i);
    
        System.out.println("Reverse Using FoldRight");
        lstRv = CollectionUtilities.reverse(lst);
        for(Integer i:lstRv)
            System.out.println(i);
  }
}
