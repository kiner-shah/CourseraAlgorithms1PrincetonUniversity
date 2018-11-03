/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int lineSegments;
    private final LineSegment[] segmentsArray;
    public BruteCollinearPoints(Point[] original_points) {    // finds all line segments containing 4 points
        if(original_points == null || original_points.length == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int N = original_points.length;
        for(int i = 0; i < N; i++) {
            if(original_points[i] == null)
                throw new java.lang.IllegalArgumentException();
            for(int j = i + 1; j < N; j++) {
                if(original_points[j] == null)
                    throw new java.lang.IllegalArgumentException();
                if(original_points[j].compareTo(original_points[i]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        lineSegments = 0;
        ArrayList<LineSegment> lst = new ArrayList<>();
        Point[] points = Arrays.copyOf(original_points, N);
        Arrays.sort(points);
        
        for(int i = 0; i < N - 3; i++) {
            for(int j = i + 1; j < N - 2; j++) {
                for(int k = j + 1; k < N - 1; k++) {
                    for(int l = k + 1; l < N; l++) {
                        if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                            && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            lineSegments++;
                            
                            lst.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
        if(lineSegments > 0)
            segmentsArray = lst.toArray(new LineSegment[lineSegments]);
        else
            segmentsArray = new LineSegment[0];
    }
    public int numberOfSegments() {       // the number of line segments
        return lineSegments;
    }
    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(segmentsArray, lineSegments);
    }
//    public static void main(String[] args) {
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}
