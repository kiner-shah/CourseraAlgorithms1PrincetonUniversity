/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/*public */class Percolation1 { // Leads to (98 / 100) marks.
    private boolean[][] grid;
    private boolean[][] full;
    private final int gridSize;
    private final int arrayLastIndex;
    private int noOfOpenSites;
    private final WeightedQuickUnionUF instanceWQFUnionFind;
    private final WeightedQuickUnionUF instance2WQFUnionFind; // second instance required to avoid backswash problem
    
    /**
     * @param n size of grid
     */
    /*public */Percolation1(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        grid = new boolean[n + 1][n + 1];
        full = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = false;
                full[i][j] = false;
            }
        }
        gridSize = n;
        arrayLastIndex = n * n + 1;
        noOfOpenSites = 0;
        instanceWQFUnionFind = new WeightedQuickUnionUF(n * n + 2);
        instance2WQFUnionFind = new WeightedQuickUnionUF(n * n + 2);
    }
    /**
     * @brief   helper function for encoding from 2-d to 1-d
     * @param row   row index in the grid
     * @param col   column index in the grid
     * @return encoded index
     */
    private int encodeIndices(int row, int col) {
        return (row - 1) * gridSize + col;
    }
    /**
     * @param row   row index of cell to be opened
     * @param col   column index of cell to be opened
     */
    public void open(int row, int col) {    // open site (row, col) if it is not open already
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
//            StdOut.println(row + " " + col);
            throw new java.lang.IllegalArgumentException();
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            noOfOpenSites++;
            int index1 = (row - 1) * gridSize + col;
            int index2;
            if (row - 1 >= 1 && grid[row - 1][col]) {
                index2 = encodeIndices(row - 1, col); // (row - 2) * gridSize + col;
                instanceWQFUnionFind.union(index1, index2);
                instance2WQFUnionFind.union(index1, index2);
            }
            if (row + 1 <= gridSize && grid[row + 1][col]) {
                index2 = encodeIndices(row + 1, col); // row * gridSize + col;
                instanceWQFUnionFind.union(index1, index2);
                instance2WQFUnionFind.union(index1, index2);
            }
            if (col - 1 >= 1 && grid[row][col - 1]) {
                index2 = encodeIndices(row, col - 1); // (row - 1) * gridSize + col - 1;
                instanceWQFUnionFind.union(index1, index2);
                instance2WQFUnionFind.union(index1, index2);
            }
            if (col + 1 <= gridSize && grid[row][col + 1]) {
                index2 = encodeIndices(row, col + 1); // (row - 1) * gridSize + col + 1;
                instanceWQFUnionFind.union(index1, index2);
                instance2WQFUnionFind.union(index1, index2);
            }
            if (row == 1) { 
                instanceWQFUnionFind.union(index1, 0); 
                instance2WQFUnionFind.union(index1, 0); 
                full[row][col] = true;
            }
            if (row == gridSize) { 
                instanceWQFUnionFind.union(index1, arrayLastIndex); 
            }
        }
    }
    /**
     * @param row   row index of cell to be checked for openness
     * @param col   column index of cell to be checked for openness
     * @return boolean value indicating whether cell is open or closed
     */
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new java.lang.IllegalArgumentException();
        return grid[row][col];
    }
    /**
     * @param row   row index of cell to be checked for fullness
     * @param col   column index of cell to be checked for fullness
     * @return boolean value indicating whether cell is full or not
     */
    public boolean isFull(int row, int col) {  // is site (row, col) full?
//        StdOut.println(row + " " + col + " " + full[row][col]);
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new java.lang.IllegalArgumentException();
        if (full[row][col]) return true;
        if (!isOpen(row, col)) return false;
        int index1 = encodeIndices(row, col); // (row - 1) * gridSize + col;
        if (row < gridSize) full[row][col] = instance2WQFUnionFind.connected(0, index1);
        else {
            full[row][col] = instance2WQFUnionFind.connected(index1, 0);
        }
//        if(full[row][col] && (row == 18 || row == 19 || row == 20) && (col == 1 || col == 2 || col == 3))
//            StdOut.println(row + " " + col + " " + index1 + " " + full[row][col]);
        return full[row][col];
    }
    /**
     * @return  integer value indicating the number of open sites
     */
    public int numberOfOpenSites() {       // number of open sites
        return noOfOpenSites;
    }
    /**
     * @return boolean value indicating whether the top and bottom rows of the grid are connected
     */
    public boolean percolates() {              // does the system percolate?
        return instanceWQFUnionFind.connected(0, arrayLastIndex);
    }

//    public static void main(String[] args) {   // test client (optional)
//        Percolation p = new Percolation(5);
//        
//    }
}



public class Percolation {  // (100 / 100) marks
    private boolean[][] grid;
    private boolean[] topConnected;
    private boolean[] bottomConnected;
    private final int gridSize;
    private final int gridSizeSquare;
    private int noOfOpenSites;
    private boolean connectTopWithBottom;
    private final WeightedQuickUnionUF instanceWQFUnionFind;
    /**
     * @param n size of grid
     */
    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) 
            throw new java.lang.IllegalArgumentException();
        grid = new boolean[n][n];
        gridSize = n;
        gridSizeSquare = n * n;
        topConnected = new boolean[gridSizeSquare];
        bottomConnected = new boolean[gridSizeSquare];
        noOfOpenSites = 0;
        instanceWQFUnionFind = new WeightedQuickUnionUF(gridSizeSquare);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        for(int i = 0; i < gridSizeSquare; i++) {
            topConnected[i] = false;
            bottomConnected[i] = false;
        }
        connectTopWithBottom = false;
    }
    /**
     * @brief   helper function for encoding from 2-d to 1-d
     * @param row   row index in the grid
     * @param col   column index in the grid
     * @return encoded index
     */
    private int encodeIndices(int row, int col) {
        return row * gridSize + col;
    }
    /**
     * @param i   row index of cell to be opened
     * @param j   column index of cell to be opened
     */
    public void open(int i, int j) {    // open site (row, col) if it is not open already
        if (i < 1 || i > gridSize || j < 1 || j > gridSize) {
//            StdOut.println(row + " " + col);
            throw new java.lang.IllegalArgumentException();
        }
        /*
        * Be careful: the root may change on each union operation. Always use the latest root.
        */
        boolean connectedToTop = false, connectedToBottom = false;
        int row = i - 1, col = j - 1;
        if (!grid[row][col]) {
            grid[row][col] = true;
            noOfOpenSites++;
            int index1 = encodeIndices(row, col);
            int index2, root_index2, root_index1;
            if (row - 1 >= 0 && grid[row - 1][col]) {
                index2 = index1 - gridSize;
                root_index1 = instanceWQFUnionFind.find(index1);
                root_index2 = instanceWQFUnionFind.find(index2);
//                edu.princeton.cs.algs4.StdOut.println("1 " + index1 + " " + index2 + " " + root_index1 + " " + root_index2);
                if (topConnected[root_index1] || topConnected[root_index2])
                    connectedToTop = true;
                if (bottomConnected[root_index1] || bottomConnected[root_index2])
                    connectedToBottom = true;
                instanceWQFUnionFind.union(index1, index2);
            }
            if (row + 1 < gridSize && grid[row + 1][col]) {
                index2 = index1 + gridSize;
                root_index1 = instanceWQFUnionFind.find(index1);
                root_index2 = instanceWQFUnionFind.find(index2);
//                edu.princeton.cs.algs4.StdOut.println("2 " + index1 + " " + index2 + " " + root_index1 + " " + root_index2);
                if (topConnected[root_index1] || topConnected[root_index2])
                    connectedToTop = true;
                if (bottomConnected[root_index1] || bottomConnected[root_index2])
                    connectedToBottom = true;
                instanceWQFUnionFind.union(index1, index2);
            }
            if (col - 1 >= 0 && grid[row][col - 1]) {
                index2 = index1 - 1;
                root_index1 = instanceWQFUnionFind.find(index1);
                root_index2 = instanceWQFUnionFind.find(index2);
//                edu.princeton.cs.algs4.StdOut.println("3 " + index1 + " " + index2 + " " + root_index1 + " " + root_index2);
                if (topConnected[root_index1] || topConnected[root_index2])
                    connectedToTop = true;
                if (bottomConnected[root_index1] || bottomConnected[root_index2])
                    connectedToBottom = true;
                instanceWQFUnionFind.union(index1, index2);
            }
            if (col + 1 < gridSize && grid[row][col + 1]) {
                index2 = index1 + 1;
                root_index1 = instanceWQFUnionFind.find(index1);
                root_index2 = instanceWQFUnionFind.find(index2);
//                edu.princeton.cs.algs4.StdOut.println("4 " + index1 + " " + index2 + " " + root_index1 + " " + root_index2);
                if (topConnected[root_index1] || topConnected[root_index2])
                    connectedToTop = true;
                if (bottomConnected[root_index1] || bottomConnected[root_index2])
                    connectedToBottom = true;
                instanceWQFUnionFind.union(index1, index2);
            }
            if (row == 0) { 
                connectedToTop = true;
            }
            if (row == gridSize - 1) { 
                connectedToBottom = true;
            }
            topConnected[instanceWQFUnionFind.find(index1)] = connectedToTop;
            bottomConnected[instanceWQFUnionFind.find(index1)] = connectedToBottom;
            if (connectedToTop && connectedToBottom) {
                connectTopWithBottom = true;
            }
        }
    }
    /**
     * @param row   row index of cell to be checked for openness
     * @param col   column index of cell to be checked for openness
     * @return boolean value indicating whether cell is open or closed
     */
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new java.lang.IllegalArgumentException();
//        edu.princeton.cs.algs4.StdOut.println("isOpen " + (row - 1) + ", " + (col - 1) + " " + grid[row - 1][col - 1]);
        return grid[row - 1][col - 1];
    }
    /**
     * @param i   row index of cell to be checked for fullness
     * @param j   column index of cell to be checked for fullness
     * @return boolean value indicating whether cell is full or not
     */
    public boolean isFull(int i, int j) {  // is site (row, col) full?
        if (i < 1 || i > gridSize || j < 1 || j > gridSize)
            throw new java.lang.IllegalArgumentException();
//        edu.princeton.cs.algs4.StdOut.println("isFull " + (i - 1) + ", " + (j - 1) + " " + grid[i - 1][j - 1]);
        return topConnected[instanceWQFUnionFind.find(encodeIndices(i - 1, j - 1))];
    }
    /**
     * @return  integer value indicating the number of open sites
     */
    public int numberOfOpenSites() {       // number of open sites
        return noOfOpenSites;
    }
    /**
     * @return boolean value indicating whether the top and bottom rows of the grid are connected
     */
    public boolean percolates() {              // does the system percolate?
        return connectTopWithBottom;
    }
}