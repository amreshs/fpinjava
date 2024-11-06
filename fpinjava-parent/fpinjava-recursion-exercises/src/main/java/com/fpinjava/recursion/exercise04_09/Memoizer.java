/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpinjava.recursion.exercise04_09;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import com.fpinjava.common.Function;
/**
 *
 * @author amreshkumar.sharma
 */
public class Memoizer<T, U> {
    private final Map<T, U> cache = new ConcurrentHashMap<>();
    
    public static <T, U>Function<T, U> memoize(Function<T,U> function){
        return new Memoizer<T, U>().doMemoize(function);
    }
    
    private Function<T, U> doMemoize(Function<T,U> function){
        return input -> cache.computeIfAbsent(input, function::apply);
    }

    private static Integer longCalculate(Integer x){
        try{
            Thread.sleep(1_000);
        }
        catch(InterruptedException ignored){
        }
        return x*2;
    } 

    private static Function<Integer, Integer> f = Memoizer::longCalculate;
    private static Function<Integer, Integer> g = memoize(f);
    
    public static void automaticMemoizationExample(){
        long startTime = System.currentTimeMillis();
        Integer result1 = g.apply(1);
        long time1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        Integer result2 = g.apply(1);
        long time2 = System.currentTimeMillis() - startTime;
        
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(time1);
        System.out.println(time2);
    }
    
    public static void main(String[] args){
        automaticMemoizationExample();
    }
}


