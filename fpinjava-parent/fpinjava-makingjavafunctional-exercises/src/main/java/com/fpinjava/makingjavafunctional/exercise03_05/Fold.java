package com.fpinjava.makingjavafunctional.exercise03_05;

//import com.fpinjava.makingjavafunctional.exercise03_04;
import com.fpinjava.common.Function;
import com.fpinjava.makingjavafunctional.exercise03_03.CollectionUtilities;
import java.util.*;

public class Fold {
    public static Function<Integer,Function<Integer,Integer>> add = x -> y -> x + y;
    public static Function<String, Function<Integer, String>> leftOper = s -> i -> "(" + s + ")" + " + " + i;

    public static Integer fold(List<Integer> lst, Integer identity, Function<Integer, Function<Integer, Integer>> f){
        Integer newIdentity = identity;
        //if(!lst.isEmpty())
            //newIdentity = fold(CollectionUtilities.tail(lst), f.apply(identity).apply(CollectionUtilities.head(lst)), f);
            for(Integer l : lst)
                newIdentity += f.apply(identity).apply(l);
           
        return newIdentity;
    }
    
    public static <T,U> U foldLeft( List<T> lst, 
                                    U identity, 
                                    Function<U, Function<T,U>> f){
        U newIdentity = identity;
        for(T l : lst)
            newIdentity = f.apply(newIdentity).apply(l);

        return newIdentity;
    }
    public static void main(String[] args){
        List<Integer> lst = Arrays.asList(1, 2, 3, 4);//("1", "2","3", "4");

        //List<String> lst_tail = CollectionUtilities.tail(lst);

        Integer addedValue = fold(lst, 0, add);
        System.out.println("Value after fold left " + addedValue);
        
        String leftOpStr = foldLeft(lst, "0", leftOper);
        System.out.println(leftOpStr);
    }
}

    

