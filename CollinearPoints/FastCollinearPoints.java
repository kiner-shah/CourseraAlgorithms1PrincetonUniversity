/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
public class FastCollinearPoints {
    private int lineSegments;
    private final LineSegment[] segmentsArray;
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if(points == null || points.length == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int N = points.length;
        for(int i = 0; i < N; i++) {
            if(points[i] == null)
                throw new java.lang.IllegalArgumentException();    
            for(int j = i + 1; j < N; j++) {
                if(points[j] == null)
                    throw new java.lang.IllegalArgumentException();
                if(points[j].compareTo(points[i]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        lineSegments = 0;
        ArrayList<LineSegment> lst = new ArrayList<>();
        for(int i = 0; i < N; i++) {
//            StdOut.println("For point " + points[i].toString());
            if(N - 1 > 0) {
                Point[] auxiliaryPoints = new Point[N - 1];
                int k = 0;
                for(int j = 0; j < N; j++) {
                    if(i != j) auxiliaryPoints[k++] = points[j];
                }
                Comparator<Point> compare = points[i].slopeOrder();
                Arrays.sort(auxiliaryPoints, compare);
    //            for(Point pp : auxiliaryPoints) StdOut.print(pp.toString() + " "); StdOut.println();
                int cnt = 2;
                Point maxPoint = points[i];
                Point minPoint = points[i];
                for(int j = 1; j < N - 1; j++) {
    //                StdOut.println(auxiliaryPoints[j - 1].toString() + " " + points[i].slopeTo(auxiliaryPoints[j - 1]) + " " + 
    //                        auxiliaryPoints[j].toString() + " " + points[i].slopeTo(auxiliaryPoints[j]) + " " + 
    //                        Double.compare(points[i].slopeTo(auxiliaryPoints[j]), points[i].slopeTo(auxiliaryPoints[j - 1])));
                    if(Double.compare(points[i].slopeTo(auxiliaryPoints[j]), points[i].slopeTo(auxiliaryPoints[j - 1])) == 0) {
                        cnt++;
                        if(maxPoint.compareTo(auxiliaryPoints[j - 1]) < 0)
                            maxPoint = auxiliaryPoints[j - 1];
                        if(minPoint.compareTo(auxiliaryPoints[j - 1]) > 0)
                            minPoint = auxiliaryPoints[j - 1];
                    }
                    else {
    //                    StdOut.println("CNT = " + cnt);
                        if(maxPoint.compareTo(auxiliaryPoints[j - 1]) < 0)
                            maxPoint = auxiliaryPoints[j - 1];
                        if(minPoint.compareTo(auxiliaryPoints[j - 1]) > 0)
                            minPoint = auxiliaryPoints[j - 1];
                        if(cnt >= 4) {
    //                        for(Point pp : lineSegmentPnts) StdOut.println(pp.toString());
                            if(points[i].compareTo(minPoint) == 0) {
                                lst.add(new LineSegment(minPoint, maxPoint));
                                lineSegments++;
                            }
                        }
                        maxPoint = points[i];
                        minPoint = points[i];
                        cnt = 2;
                    }
                }
                if(maxPoint.compareTo(auxiliaryPoints[N - 2]) < 0)
                    maxPoint = auxiliaryPoints[N - 2];
                if(minPoint.compareTo(auxiliaryPoints[N - 2]) > 0)
                    minPoint = auxiliaryPoints[N - 2];
                if(cnt >= 4) {
    //                for(Point pp : lineSegmentPnts) StdOut.println(pp.toString());
                    if(points[i].compareTo(minPoint) == 0) {
                        lst.add(new LineSegment(minPoint, maxPoint));
                        lineSegments++;
                    }
                }
            }
        }
        if(lineSegments > 0)
            segmentsArray = lst.toArray(new LineSegment[lineSegments]);
        else
            segmentsArray = new LineSegment[0];
//        numberOfSegments();
    }
    public int numberOfSegments() {       // the number of line segments
//        StdOut.println("LINE SEGMENTS = " + lineSegments);
        return lineSegments;
    }
    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(segmentsArray, lineSegments);
    }
//    public static void main(String[] args) {    // Sample client
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
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}
