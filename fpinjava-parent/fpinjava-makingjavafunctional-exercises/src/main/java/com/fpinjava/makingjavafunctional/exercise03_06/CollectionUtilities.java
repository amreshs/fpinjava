package com.fpinjava.makingjavafunctional.exercise03_06;

import com.fpinjava.common.Function;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.add;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.fold;
import static com.fpinjava.makingjavafunctional.exercise03_05.Fold.leftOper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionUtilities{
    public static<T> List<T> list(){
        return Collections.emptyList();
    }
    
    public static<T> List<T> list(T t){
        return Collections.singletonList(t);
    }

    public static<T> List<T> list(List<T> lt){
        return Collections.unmodifiableList(new ArrayList<>(lt));
    }
    
    public static<T> List<T> list(T...t){
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
    }
    
    public static<T> T head(List<T> lt){
        if(lt == null || lt.isEmpty()){
            throw new IllegalStateException("Head of list can't be null");
        }
    
        return lt.get(0);
    }
    
    private static<T> List<T> copy(List<T> lt){
        if(lt == null || lt.isEmpty()){
            throw new IllegalStateException("Head of list can't be null");
        }
        
        return new ArrayList<>(lt);
    }
    
    public static <T> List<T> tail(List<T> lt){
        if(lt == null || lt.isEmpty()){
            throw new IllegalStateException("List can't be null or empty");
        }
        
        List<T> workList = copy(lt);
        workList.remove(0);
        
        return Collections.unmodifiableList(workList);
    }
    
      public static <T,U> U foldLeft( List<T> lst, 
                                    U identity, 
                                    Function<U, Function<T,U>> f){
        U newIdentity = identity;
        for(T l : lst)
            newIdentity = f.apply(newIdentity).apply(l);

        return newIdentity;
    }
    
    public static<T> List<T> append(List<T> lt, T t){
        if(lt == null){
            throw new IllegalStateException("List can't be null");
        }
        
        List<T> workList = copy(lt);
        workList.add(t);
        
        return Collections.unmodifiableList(workList);
    }
    
    public static Function<String, Function<Integer, String>> leftOper = s -> i -> "(" + s + "+" + i + ")";
    public static void main(String[] args){
        List<Integer> lst = Arrays.asList(1, 2, 3, 4);//("1", "2","3", "4");

        //List<String> lst_tail = CollectionUtilities.tail(lst);
        
        Integer addedValue = fold(lst, 0, add);
        System.out.println("Value after fold left " + addedValue);
        
        
        String leftOpStr = foldLeft(lst, "0", leftOper);
        System.out.println(leftOpStr);
    }
    
}
