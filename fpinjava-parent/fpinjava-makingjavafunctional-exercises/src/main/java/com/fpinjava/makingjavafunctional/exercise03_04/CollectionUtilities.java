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
            throw new IllegalStateException("List can't be null or empty");
        }
        
        List<T> workList = copy(lt);
        workList.remove(0);
        
        return Collections.unmodifiableList(workList);
    }
    
    public static<T> List<T> append(List<T> lt, T t){
        if(lt == null){
            throw new IllegalStateException("List can't be null");
        }
        
        List<T> workList = copy(lt);
        workList.add(t);
        
        return Collections.unmodifiableList(workList);
    }
}
