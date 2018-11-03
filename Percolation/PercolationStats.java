/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class PercolationStats {
    private static final double MULTIPLY_FACTOR = 1.96;
    /**
     * @param args command-line arguments consisting of N, the grid size and T, no. of trials
     */
    public static void main(String[] args) {        // test client (described below)
        int n = java.lang.Integer.parseInt(args[0]);
        int t = java.lang.Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
    private final double mean1, stddev1;
    private final int trialsSize;
    /**
     * @param n size of the grid
     * @param trials    no. of trials (experiments) to be performed
     */
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        trialsSize = trials;
        int i = 0;
        double[] trialOutcomes = new double[trials];
        while (i < trials) {
            Percolation percolationObj = new Percolation(n);
            while (!percolationObj.percolates()) {
                int index = StdRandom.uniform(1, n * n + 1);
                // StdOut.println(maxN + " " + index);
                int row = index / n + 1;
                int col = index % n;
                if (col == 0) { 
                    col = n; 
                    row--; 
                }
                // if((row >= 1 && col >= 1) && (row <= gridSize && col <= gridSize))
                    percolationObj.open(row, col);
            }
            // StdOut.println(percolationObj.numberOfOpenSites());
            trialOutcomes[i] = (double) percolationObj.numberOfOpenSites() / (n * n);
            i++;
        }
        mean1 = StdStats.mean(trialOutcomes);
        stddev1 = StdStats.stddev(trialOutcomes);
    }
    /**
     * @return mean of the outcomes of the experiments
     */
    public double mean() {                          // sample mean of percolation threshold
        return mean1;
    }
    /**
     * @return standard deviation of the outcomes of the experiments
     */
    public double stddev() {                        // sample standard deviation of percolation threshold
        return stddev1;
    }
    /**
     * @return 95% confidence interval lower bound of the outcomes of the experiments
     */
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean1 - (PercolationStats.MULTIPLY_FACTOR * stddev1) / java.lang.Math.sqrt(trialsSize);
    }
    /**
     * @return 95% confidence interval upper bound of the outcomes of the experiments
     */
    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return mean1 + (PercolationStats.MULTIPLY_FACTOR * stddev1) / java.lang.Math.sqrt(trialsSize);
    }
}
