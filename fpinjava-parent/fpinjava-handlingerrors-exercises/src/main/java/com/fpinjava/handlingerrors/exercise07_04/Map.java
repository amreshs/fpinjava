package com.fpinjava.handlingerrors.exercise07_04;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Map<T, U> {
    private final ConcurrentMap<T, U> map = new ConcurrentHashMap<>();

    public static<T,U> Map<T,U> empty(){
        return new Map<>();
    }

    public Map<T,U> add(Map<T,U> m, T t, U u){
        m.map.put(t, u);
        return m;
    }

    public Result<U> get(T t){
        return this.map.containsKey(t)?
                Result.success(this.map.get(t)):
                Result.failure(String.format("Key %s is not present",t));
    }

    public Map<T, U> put(T t, U u){
        add(this,t, u);
        return this;
    }

    public Map<T, U> removeKey(T t){
        this.map.remove(t);
        return this;
    }
}
