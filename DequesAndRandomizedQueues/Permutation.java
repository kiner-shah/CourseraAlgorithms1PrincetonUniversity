
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
//        int k_1 = k;
//        int k_2 = k;
//        java.io.FileInputStream is = null;
//        try {
//            is = new java.io.FileInputStream(new java.io.File("C:\\Users\\user\\Documents\\NetBeansProjects\\DequeAndRandomizedQueue\\src\\duplicates.txt"));
//        }
//        catch(Exception e) { e.printStackTrace(); }
//        System.setIn(is);
//        Deque<String> dq = new Deque<>();
        RandomizedQueue<String> ranq = new RandomizedQueue<>();
        int curCount = 0;
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(curCount < k) ranq.enqueue(s);
            else {
                int probIndex = StdRandom.uniform(curCount + 1);
                if(probIndex < k) {
                    ranq.dequeue();
                    ranq.enqueue(s);
                }
            }
            curCount++;
        }
        for(String x : ranq) {
            StdOut.println(x);
        }
    }
}
