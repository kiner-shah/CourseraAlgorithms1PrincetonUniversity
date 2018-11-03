/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> pointSet;
    private int pointSetSize;
    
    public PointSET() {                              // construct an empty set of points 
        pointSet = new TreeSet<>();
        pointSetSize = 0;
    }
    public boolean isEmpty() {                     // is the set empty? 
        return pointSet.isEmpty();
    }
    public int size() {                        // number of points in the set 
        return pointSetSize;
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty() || !contains(p)) {
            pointSet.add(p);
            pointSetSize++;
        }
    }
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return pointSet.contains(p);
    }
    public void draw() {                        // draw all points to standard draw 
        for (Point2D p : pointSet) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary) 
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        ArrayList<Point2D> lst = new ArrayList<>();
        for (Point2D q : pointSet) {
            if (rect.contains(q)) {
                lst.add(q);
            }
        }
        return lst;
    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        double minDist = Double.POSITIVE_INFINITY;
        Point2D target = null;
        for (Point2D q : pointSet) {
            double dist = p.distanceSquaredTo(q);   // compare squares instead of square root and avoid extra computation
            if (dist < minDist) {
                target = q;
                minDist = dist;
            }
        }
        return target;
    }

//    public static void main(String[] args) {                 // unit testing of the methods (optional)
//        
//    }
}
