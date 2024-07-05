package com.fpinjava.makingjavafunctional.exercise03_07;

import com.fpinjava.common.Function;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.add;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.fold;
import static com.fpinjava.makingjavafunctional.exercise03_06.CollectionUtilities.foldLeft;
import static com.fpinjava.makingjavafunctional.exercise03_06.CollectionUtilities.leftOper;

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

  public static Function<Integer, Function<String, String>> rightOpr = i -> s -> "(" + i + "+" + s + ")";
  
  public static <T, U> U foldRight(List<T> ts,
                                   U identity,
                                   Function<T, Function<U, U>> f) {
    U newIdentity = identity;
    for(int indx = ts.size(); indx > 0; indx--)
        newIdentity = f.apply(ts.get(indx-1)).apply(newIdentity);
  
    return newIdentity;
  }

  public static <T> List<T> append(List<T> list, T t) {
    List<T> ts = copy(list);
    ts.add(t);
    return Collections.unmodifiableList(ts);
  }
  
     public static void main(String[] args){
        List<Integer> lst = Arrays.asList(1, 2, 3, 4);//("1", "2","3", "4");

        //List<String> lst_tail = CollectionUtilities.tail(lst);
        
        Integer addedValue = fold(lst, 0, add);
        System.out.println("Value after fold left " + addedValue);
        
        
        String leftOpStr = foldRight(lst, "0", rightOpr);
        System.out.println(leftOpStr);
    }

}
