/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */

import java.util.Arrays;
import java.util.ArrayList;

public class Board {
    private final int[][] board;
//    private final int[][] referenceBoard;
    private final int N;
    private final boolean isGoalBoard;
    private int zposx, zposy;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; i++)
            board[i] = Arrays.copyOf(blocks[i], N);
        boolean zposFound = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {      
                if (!zposFound && board[i][j] == 0) {
                    zposx = i;
                    zposy = j;
                    zposFound = true;
                    break;
                }
            }
            if (zposFound) break;
        }
        int hammingD = hamming();
        int manhattanD = manhattan();
        isGoalBoard = (hammingD == 0 || manhattanD == 0);
    }
    public int dimension() {                // board dimension n
        return N;
    }
    public int hamming() {                   // number of blocks out of place
        int count = 0, temp = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1)
                    temp = 0;
                if (board[i][j] != temp)
                    count++;
                temp++;
            }
        }
        if (board[N - 1][N - 1] == 0)
            return count;
        else
            return count - 1;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0)
                    continue;
                int y = board[i][j] % N, x;
                if (y == 0) { 
                    x = board[i][j] / N - 1; 
                    y = N - 1; 
                }
                else {
                    x = board[i][j] / N;
                    --y;
                }
//                edu.princeton.cs.algs4.StdOut.println(board[i][j] + " " + x + " " + i + " " + y + " " + j);
                sum += Math.abs(x - i) + Math.abs(y - j);
            }
        }
//        edu.princeton.cs.algs4.StdOut.println("======================");
        return sum;
    }
    public boolean isGoal() {               // is this board the goal board?
        return isGoalBoard;
    }
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        Board twinBoard = new Board(board);
        int x1, x2, y1, y2;
        if (zposx - 1 >= 0) {
            x1 = zposx - 1;
            y1 = zposy;
        }
        else {
            x1 = zposx + 1;
            y1 = zposy;
        }
        if (zposy + 1 < N) {
            x2 = zposx;
            y2 = zposy + 1;
        }
        else {
            x2 = zposx;
            y2 = zposy - 1;
        }
        int temp = twinBoard.board[x1][y1];
        twinBoard.board[x1][y1] = twinBoard.board[x2][y2];
        twinBoard.board[x2][y2] = temp;
        return twinBoard;
    }
    public boolean equals(Object y) {       // does this board equal y?
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        
        Board b = (Board) y;
        if (b.dimension() != this.dimension())
            return false;
        boolean flag = true;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(this.board[i][j] != b.board[i][j]) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    private void exch(int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }
    public Iterable<Board> neighbors() {    // all neighboring boards
        ArrayList<Board> pq = new ArrayList<>();
        if (zposx - 1 >= 0) {
            exch(zposx, zposy, zposx - 1, zposy);
            pq.add(new Board(board));
            exch(zposx, zposy, zposx - 1, zposy);
        }
        if (zposx + 1 < N) {
            exch(zposx, zposy, zposx + 1, zposy);
            pq.add(new Board(board));
            exch(zposx, zposy, zposx + 1, zposy);
        }
        if (zposy - 1 >= 0) {
            exch(zposx, zposy, zposx, zposy - 1);
            pq.add(new Board(board));
            exch(zposx, zposy, zposx, zposy - 1);
        }
        if (zposy + 1 < N) {
            exch(zposx, zposy, zposx, zposy + 1);
            pq.add(new Board(board));
            exch(zposx, zposy, zposx, zposy + 1);
        }
        return pq;
    }
    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(N));
        sb.append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", board[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

//    public static void main(String[] args) {// unit tests (not graded)
//        
//    }
}
