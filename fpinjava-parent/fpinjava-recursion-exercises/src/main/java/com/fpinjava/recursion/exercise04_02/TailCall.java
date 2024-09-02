/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpinjava.recursion.exercise04_02;

/**
 *
 * @author amreshkumar.sharma
 */
import com.fpinjava.common.Supplier;

public abstract class TailCall<T> {
    public abstract T eval();
    public abstract Boolean isSuspend();
    public abstract TailCall<T> resume();
    
    public static class Result<T> extends TailCall<T>{
        private final T t;
        
        private Result(T t){
            this.t = t;
        }
        
        @Override
        public T eval(){
            return t;
        }
    
        @Override
        public Boolean isSuspend(){
            return false;
        }
        
        @Override
        public TailCall<T> resume(){
            throw new IllegalStateException("Resume Call is not implemented for Result");
        }
    }
    
    public static class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;
        
        private Suspend(Supplier<TailCall<T>> resume){
            this.resume = resume;
        }
        
        @Override
        public T eval(){
            TailCall<T> iter = this;
            while(iter.isSuspend())
                iter = iter.resume();
        
            return iter.eval();
        }
        
        @Override
        public Boolean isSuspend(){
            return true;
        }
        
        @Override
        public TailCall<T> resume(){
            return resume.get();
        }
    }
    
    public static<T> Result<T> res(T t){
        return new Result<>(t);
    }
    
    public static<T> TailCall<T> sus(Supplier<TailCall<T>> resume){
        return new Suspend<>(resume);
    }
}

