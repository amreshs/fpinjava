package com.fpinjava.advancedtrees.exercise11_09;

public class Point implements Comparable<Point>{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        return x < o.x? -1 : (x == o.x ? 0 : 1);
    }
}
