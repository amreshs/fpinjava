package com.fpinjava.makingjavafunctional.exercise03_03;

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
            throw new IllegalStateException("Head of list can't be null");
        }
        
        List<T> workList = copy(lt);
        
        workList.remove(0);
        
        return Collections.unmodifiableList(workList);
    }
}

/*
public class CollectionUtilities {

  public static <T> List<T > list() {
    throw new RuntimeException("To be implemented");
  }

  public static <T> List<T > list(T t) {
    throw new RuntimeException("To be implemented");
  }
  public static <T> List<T > list(List<T> ts) {
    throw new RuntimeException("To be implemented");
  }

  @SafeVarargs
  public static <T> List<T > list(T... t) {
    throw new RuntimeException("To be implemented");
  }
}
*/