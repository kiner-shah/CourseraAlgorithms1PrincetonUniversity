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
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;
import java.awt.Color;
import java.util.ArrayList;

public class KdTree {
    private static final boolean VERTICAL = false;
    private static final boolean HORIZONTAL = true;
    
    private int treeSize;
    private Node root;
    
    private class Node {
        Point2D point;
        Node left;
        Node right;
        RectHV rect;
        
        Node(Point2D p, Node lt, Node rt, RectHV rect) {
            this.point = p;
            this.left = lt;
            this.right = rt;
            this.rect = rect;
        }
    }
    /* private class Rectangle {
        private double xmin, xmax, ymin, ymax;
        Rectangle(double x1, double x2, double y1, double y2) {
            this.xmin = x1;
            this.xmax = x2;
            this.ymin = y1;
            this.ymax = y2;
        }
        void setXMin(double x) { xmin = x; }
        void setXMax(double x) { xmax = x; }
        void setYMin(double y) { ymin = y; }
        void setYMax(double y) { ymax = y; }
        double getXMin() { return xmin; }
        double getXMax() { return xmax; }
        double getYMin() { return ymin; }
        double getYMax() { return ymax; }
        double distanceSquaredTo(Point2D p) {
            double px = p.x();
            double py = p.y();
            double dx = 0, dy = 0;
            double rx1 = xmin;
            double rx2 = xmax;
            double ry1 = ymin;
            double ry2 = ymax;
            if (px < rx1) dx = px - rx1;
            else if (px > rx2) dx = px - rx2;
            if (py < ry1) dy = py - ry1;
            else if (py > ry2) dy = py - ry2;
            
            return dx*dx + dy*dy;
        }
        boolean intersects(Rectangle that) {
            return that.getXMin() <= this.getXMax() && that.getXMax() >= this.getXMin()
                    && that.getYMin() <= this.getYMax() && that.getYMax() >= this.getYMin();
        }
        String rectToString() {
            return (getXMin() + " " + getYMin() + " " + getXMax() + " " + getYMax());
        }
    }  */ 
    public KdTree() {                              // construct an empty set of points
        root = null;
        treeSize = 0;
    }
    public boolean isEmpty() {                     // is the set empty? 
        return treeSize == 0;
    }
    public int size() {                        // number of points in the set 
        return treeSize;
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!contains(p)) {
//            StdOut.println(p.toString());
//            Rectangle rect = new Rectangle(0.0, 1.0, 0.0, 1.0);
            /* if (root == null) {
                rect = new Rectangle(0.0, 1.0, 0.0, 1.0);
            }
            else {
                rect = new Rectangle(root.rect.xmin(), root.rect.xmax(), root.rect.ymin(), root.rect.ymax());
            } */
            root = insert(root, VERTICAL, 0.0, 1.0, 0.0, 1.0, p);
            ++treeSize;
        }
    }
    private Node insert(Node node, boolean hv, double xmin, double xmax, double ymin, double ymax, Point2D p) {
        if (node == null) {
            RectHV rectHV = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, null, null, rectHV);
        }
        double xmax1 = xmax;
        double xmin1 = xmin;
        double ymax1 = ymax;
        double ymin1 = ymin;
        
        if (!hv) {
            double x1 = p.x();
            double x2 = node.point.x();
            if (Double.compare(x1, x2) < 0) {
//                xmax = node.point.x();
//                StdOut.println("R1 " + rect1.equals(rect));
                node.left = insert(node.left, HORIZONTAL, xmin1, x2, ymin1, ymax1, p);
            }
            else if (Double.compare(x1, x2) >= 0) {
//                xmin = node.point.x();
//                StdOut.println("R2 " + rect1.equals(rect));
                node.right = insert(node.right, HORIZONTAL, x2, xmax1, ymin1, ymax1, p);
            }
        }
        else {
            double y1 = p.y();
            double y2 = node.point.y();
            if (Double.compare(y1, y2) < 0) {
//                ymax = node.point.y();
//                StdOut.println("R3 " + rect1.equals(rect));
                node.left = insert(node.left, VERTICAL, xmin1, xmax1, ymin1, y2, p);
            }
            else if (Double.compare(y1, y2) >= 0) {
//                ymin = node.point.y();
//                StdOut.println("R4 " + rect1.equals(rect));
                node.right = insert(node.right, VERTICAL, xmin1, xmax1, y2, ymax1, p);
            }
        }
        return node;
    }
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty())
            return false;
        return contains(root, p, VERTICAL);
    }
    private boolean contains(Node node, Point2D p, boolean hv) {
        if (node != null) {
            if (node.point.equals(p))
                return true;
            if (!hv) {
                double x1 = p.x();
                double x2 = node.point.x();
//                StdOut.println(p.toString() + " " + node.point.toString());
                if (Double.compare(x1, x2) < 0)
                    return contains(node.left, p, HORIZONTAL);
                else if (Double.compare(x1, x2) >= 0)
                    return contains(node.right, p, HORIZONTAL);
            }
            else {
                double y1 = p.y();
                double y2 = node.point.y();
                if (Double.compare(y1, y2) < 0)
                    return contains(node.left, p, VERTICAL);
                else if (Double.compare(y1, y2) >= 0)
                    return contains(node.right, p, VERTICAL);
            }
        }
        return false;
    }
    public void draw() {                        // draw all points to standard draw 
        draw(root, VERTICAL);
    }
    private void draw(Node node, boolean hv) {
        if (node != null) {
            if (!hv) {
                Point2D p1 = new Point2D(node.point.x(), node.rect.ymin());
                Point2D p2 = new Point2D(node.point.x(), node.rect.ymax());
                StdDraw.setPenRadius();
                StdDraw.setPenColor();
                node.point.draw();
                StdDraw.setPenColor(Color.red);
                p1.drawTo(p2);
                draw(node.left, HORIZONTAL);
                draw(node.right, HORIZONTAL);
            }
            else {
                Point2D p1 = new Point2D(node.rect.xmin(), node.point.y());
                Point2D p2 = new Point2D(node.rect.xmax(), node.point.y());
                StdDraw.setPenRadius();
                StdDraw.setPenColor();
                node.point.draw();
                StdDraw.setPenColor(Color.blue);
                p1.drawTo(p2);
                draw(node.left, VERTICAL);
                draw(node.right, VERTICAL);
            }
        }
    }
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle (or on the boundary) 
        // StdOut.println(rect.toString());
        if (rect == null)
            throw new java.lang.IllegalArgumentException();
        if (isEmpty())
            return new ArrayList<>();
        
//        double xmin = rect.xmin();
//        double xmax = rect.xmax();
//        double ymin = rect.ymin();
//        double ymax = rect.ymax();
        
        ArrayList<Point2D> lst = new ArrayList<>();
        Queue<Node> lst1 = new Queue<>();
        lst1.enqueue(root);
        while (!lst1.isEmpty()) {
            Node node = lst1.dequeue();
//            StdOut.println("P " + node.point.toString());
            if (rect.contains(node.point)) {
                lst.add(node.point);
            }
            if (node.left != null 
                    && node.left.rect.intersects(rect)) {
                    lst1.enqueue(node.left);
            }
            if (node.right != null 
                    && node.right.rect.intersects(rect)) {
                    lst1.enqueue(node.right);
            }
        }
        return lst;
    }
//    private boolean containsHelper(double x, double y, double xmin, double xmax, double ymin, double ymax) {
//        return x >= xmin && x <= xmax && y >= ymin && y <= ymax;
//    }
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (root == null)
            return null;
        NearestPoint np = new NearestPoint(null, Double.POSITIVE_INFINITY);
        nearest(root, p, np, VERTICAL);
        return np.minPoint;
    }
    private class NearestPoint {
        Point2D minPoint;
        double minDistance;
        NearestPoint(Point2D point, double dist) {
            this.minPoint = point;
            this.minDistance = dist;
        }
    }
    private void nearest(Node node, Point2D p, NearestPoint np, boolean hv) {
        if (node.point.equals(p)) {
            np.minPoint = node.point;
            np.minDistance = 0;
            return;
        }
        double dist = node.point.distanceSquaredTo(p);
        if (np.minDistance > dist) {
            np.minDistance = dist;
            np.minPoint = node.point;
        }
        if (!hv) {
            if (p.x() < node.point.x()) {
                if (node.left != null)
                    nearest(node.left, p, np, HORIZONTAL);
                if (node.right != null && node.right.rect.distanceSquaredTo(p) < np.minDistance) {
//                    StdOut.println(node.right.rect.getXMin() + " " + node.right.rect.getXMax() + " " + node.right.rect.getYMin() + " " + node.right.rect.getYMax());
                    nearest(node.right, p, np, HORIZONTAL);
                }
//                StdOut.println("REACHED1 " + (node.right != null));
//                StdOut.println(node.right.rect.getXMin() + " " + node.right.rect.getXMax() + " " + node.right.rect.getYMin() + " " + node.right.rect.getYMax());
            }
            else if (p.x() >= node.point.x()) {
                if (node.right != null)
                    nearest(node.right, p, np, HORIZONTAL);
                if (node.left != null && node.left.rect.distanceSquaredTo(p) < np.minDistance) {
//                    StdOut.println(node.left.rect.getXMin() + " " + node.left.rect.getXMax() + " " + node.left.rect.getYMin() + " " + node.left.rect.getYMax());
                    nearest(node.left, p, np, HORIZONTAL);
                }
//                StdOut.println("REACHED2 " + (node.left != null));
//                StdOut.println(node.left.rect.getXMin() + " " + node.left.rect.getXMax() + " " + node.left.rect.getYMin() + " " + node.left.rect.getYMax());
            }
        }
        else {
            if (p.y() < node.point.y()) {
                if (node.left != null)
                    nearest(node.left, p, np, VERTICAL);
                if (node.right != null && node.right.rect.distanceSquaredTo(p) < np.minDistance) {
//                    StdOut.println(node.right.rect.getXMin() + " " + node.right.rect.getXMax() + " " + node.right.rect.getYMin() + " " + node.right.rect.getYMax());
                    nearest(node.right, p, np, VERTICAL);
                }
//                StdOut.println("REACHED3 " + (node.right != null));
            }
            else if (p.y() >= node.point.y()) {
                if (node.right != null)
                    nearest(node.right, p, np, VERTICAL);
                if (node.left != null && node.left.rect.distanceSquaredTo(p) < np.minDistance) {
//                    StdOut.println(node.left.rect.getXMin() + " " + node.left.rect.getXMax() + " " + node.left.rect.getYMin() + " " + node.left.rect.getYMax());
                    nearest(node.left, p, np, VERTICAL);
                }
//                StdOut.println("REACHED4 " + (node.left != null));
            }
        }
    }
    public static void main(String[] args) {                 // unit testing of the methods (optional)
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.372, 0.497));
        tree.insert(new Point2D(0.564, 0.413));
        tree.insert(new Point2D(0.226, 0.577));
        tree.insert(new Point2D(0.144, 0.179));
        tree.insert(new Point2D(0.083, 0.51));
        tree.insert(new Point2D(0.32, 0.708));
        tree.insert(new Point2D(0.417, 0.362));
        tree.insert(new Point2D(0.862, 0.825));
        tree.insert(new Point2D(0.785, 0.725));
        tree.insert(new Point2D(0.499, 0.208));
//        tree.insert(new Point2D(0.625, 0.0));
//        tree.insert(new Point2D(0.125, 0.25));
//        tree.insert(new Point2D(0.25, 0.625));
//        tree.insert(new Point2D(0.375, 0.0));
        Point2D pp = new Point2D(0.332, 0.167);
//        StdDraw.clear();
//        tree.draw();
//        pp.draw();
//        StdDraw.show();
        StdOut.println(tree.range(new RectHV(0.51, 0.38, 0.79, 0.99)));
        StdOut.println(tree.nearest(pp) + " " + tree.size());
    }
}
/*
      A  0.7, 0.2
      B  0.5, 0.4
      C  0.2, 0.3
      D  0.4, 0.7
      E  0.9, 0.6
*/
