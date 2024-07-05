package com.fpinjava.makingjavafunctional.exercise03_04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.fpinjava.common.Function;

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
            throw new IllegalStateException("Head of list can't be null");
        }
        
        List<T> workList = copy(lt);
        workList.remove(0);
        
        return Collections.unmodifiableList(workList);
    }
    
    public static Function<Integer,Function<Integer,Integer>> add = x -> y -> x + y;
    
    public static Integer FoldInt(List<Integer> lst, Integer identity, Function<Integer, Function<Integer, Integer>> f){
        Integer newIdentity = identity;
        if(!lst.isEmpty())
            newIdentity = FoldInt(tail(lst), f.apply(identity).apply(head(lst)), f);
            return newIdentity;
    }
    public static void main(String[] args){
        List<Integer> lst = Arrays.asList(1, 2, 3, 4);//("1", "2","3", "4");
        
        //List<String> lst_tail = CollectionUtilities.tail(lst);
        
        Integer addedValue = FoldInt(lst, 0, add);
       
        System.out.println("Value after fold left " + addedValue);
    }
}
