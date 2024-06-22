/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpinjava.functions.demo_using_functions;

import com.fpinjava.functions.exercise02_05.Function;
//import com.fpinjava.functions.exercise02_06.Function;
//import com.fpinjava.functions.exercise02_04.FunctionExamples;
//import com.fpinjava.functions.exercise02_04;

/**
 *
 * @author amreshkumar.sharma
 */
public class MainFuctions {
    public static void main(String[] args) {
        Integer x = Function.<Integer, Integer, Integer>higherCompose().apply(Function.square).apply(Function.triple).apply(4);
        System.out.println("Value calculated = "+ x);
        
        Double db = Function.<Integer,String,Double>higherCompose().apply(Function.stringToDouble).apply(Function.numberToString).apply(4);
        System.out.println("Value calculated = "+ db);
    }
}
