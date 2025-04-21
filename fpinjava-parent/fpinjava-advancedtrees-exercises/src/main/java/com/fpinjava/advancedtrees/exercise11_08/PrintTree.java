package com.fpinjava.advancedtrees.exercise11_08;

import com.fpinjava.common.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintTree {

    public static <A> void makeInorderTree(Heap<A> root,
                                                                 Integer height, Integer row, Integer cols,
                                                                 List<List<String>> ans, A dv, int multFact)  {
        if (root.isEmpty()) {
            return;
        }

        Integer offset = Integer.valueOf((int) Math.pow(2, height - row - 1) * multFact);

        if(!root.isEmpty()) {
            makeInorderTree(root.left().getOrElse(Heap.empty(Result.empty())), height, Integer.valueOf(row + 1), Integer.valueOf(cols - offset), ans, dv, multFact);
        }


        ans.get(row).set(cols, String.valueOf(root.head().getOrElse(dv)));

        if(!root.isEmpty()) {
            makeInorderTree(root.right().getOrElse(Heap.empty(Result.empty())), height, Integer.valueOf(row + 1), Integer.valueOf(cols + offset), ans, dv, multFact);
        }
    }

    public static<A> List<List<String>> treeToMatrix(Heap<A> root, A dv, int multFact) {
        Integer height = Integer.valueOf(root.height());
        Integer rows = Integer.valueOf(height + 1);

        Integer cols = Integer.valueOf((int) Math.pow(2, height+1) -1 * multFact);

        List<List<String>> ans = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>(Collections.nCopies(cols, ""));
            ans.add(row);
        }

        makeInorderTree(root, height, Integer.valueOf(0) , Integer.valueOf(cols/2), ans, dv,multFact);

        return ans;
    }

    static void print2DArray(List<List<String>> ans){
        for(List<String> row : ans){
            for(String cell : row){
                if(cell.isEmpty())
                    System.out.print("  ");
                else
                    System.out.print(cell);
            }
            System.out.println();
        }
    }
}
