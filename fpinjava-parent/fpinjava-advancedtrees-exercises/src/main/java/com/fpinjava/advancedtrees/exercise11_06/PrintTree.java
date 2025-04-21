package com.fpinjava.advancedtrees.exercise11_06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintTree {

    public static <A extends Comparable<A>> void makeInorderTree(Heap<A> root,
                                                                 Integer height, Integer row, Integer cols,
                                                                 List<List<String>> ans, A dv) {
        if (root.isEmpty()) {
            return;
        }

        Integer offset = new Integer((int) Math.pow(2, height - row - 1));

        if(!root.isEmpty()) {
            makeInorderTree(root.left().getOrElse(Heap.empty()), height, Integer.valueOf(row + 1), Integer.valueOf(cols - offset), ans, dv);
        }


        ans.get(row).set(cols, String.valueOf(root.head().getOrElse(dv)));

        if(!root.isEmpty()) {
            makeInorderTree(root.right().getOrElse(Heap.empty()), height, Integer.valueOf(row + 1), Integer.valueOf(cols + offset), ans, dv);
        }
    }

    public static<A extends Comparable<A>> List<List<String>> treeToMatrix(Heap<A> root, A dv) {
        Integer height = root.height(root);
        Integer rows = Integer.valueOf(height + 1);

        Integer cols = new Integer((int) Math.pow(2, height+1) -1 );

        List<List<String>> ans = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>(Collections.nCopies(cols, ""));
            ans.add(row);
        }

        makeInorderTree(root, height, Integer.valueOf(0), Integer.valueOf(cols/2), ans, dv);

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
