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
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;
import java.util.ArrayList;

public class Solver {
    private final boolean solvable;
    private SearchNode solNode;
    
    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
//        int hammingDist;
        int manhattanDist;
        SearchNode predeccesor;
        
        public SearchNode(Board b, int m, SearchNode p) {
            this.board = b;
            this.predeccesor = p;
            this.moves = m;
//            this.hammingDist = this.board.hamming();
            this.manhattanDist = this.board.manhattan();
        }
        @Override
        public int compareTo(SearchNode that) {
            return (this.manhattanDist + this.moves) - (that.manhattanDist + that.moves);
        }
    }
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pq_twin = new MinPQ<>();
        
        pq.insert(new SearchNode(initial, 0, null));
        pq_twin.insert(new SearchNode(initial.twin(), 0, null));
        
        boolean solFound = false;
        while (!pq.isEmpty()) {
            SearchNode curBoard = pq.delMin();
//            StdOut.println(curBoard.board);
//            StdOut.println(curBoard.board.manhattan() + " " + curBoard.moves);
            SearchNode curTwinBoard = pq_twin.delMin();
//            StdOut.println(curTwinBoard.board);
//            StdOut.println(curTwinBoard.board.manhattan() + " " + curTwinBoard.moves);
            if (curBoard.board.isGoal()) {
                solNode = curBoard;
                solFound = true;
//                while (!pq.isEmpty())
//                    pq.delMin();
//                while (!pq_twin.isEmpty())
//                    pq_twin.delMin();
                break;
            }
            if (curTwinBoard.board.isGoal()) {
                solNode = curTwinBoard;
                solFound = false;
//                while (!pq_twin.isEmpty())
//                    pq_twin.delMin();
//                while (!pq.isEmpty())
//                    pq.delMin();
                break;
            }
            Iterator<Board> curNeighbors = curBoard.board.neighbors().iterator();
            while (curNeighbors.hasNext()) {
                Board tempNeighbor = curNeighbors.next();
                /* boolean repeatFlag = false;
                SearchNode refBoard = curBoard;
                while (true) {
                    if (tempNeighbor.equals(refBoard.board)) {
                        repeatFlag = true;
                        break;
                    }
                    if (refBoard.predeccesor == null)
                        break;
                    refBoard = refBoard.predeccesor;
                }
                if (!repeatFlag)
                    pq.insert(new SearchNode(tempNeighbor, curBoard.moves + 1, curBoard)); */
                if (curBoard.predeccesor != null 
                        && tempNeighbor.equals(curBoard.predeccesor.board))
                    continue;
                pq.insert(new SearchNode(tempNeighbor, curBoard.moves + 1, curBoard));    
            }
            Iterator<Board> curTwinNeighbors = curTwinBoard.board.neighbors().iterator();
            while (curTwinNeighbors.hasNext()) {
                Board tempNeighbor = curTwinNeighbors.next();
                /* boolean repeatFlag = false;
                SearchNode refBoard = curTwinBoard;
                while (true) {
                    if (tempNeighbor.equals(refBoard.board)) {
                        repeatFlag = true;
                        break;
                    }
                    if (refBoard.predeccesor == null)
                        break;
                    refBoard = refBoard.predeccesor;
                }
                if (!repeatFlag)
                    pq_twin.insert(new SearchNode(tempNeighbor, curTwinBoard.moves + 1, curTwinBoard)); */
                if (curTwinBoard.predeccesor != null 
                        && tempNeighbor.equals(curTwinBoard.predeccesor.board))
                    continue;
                pq_twin.insert(new SearchNode(tempNeighbor, curTwinBoard.moves + 1, curTwinBoard));    
            }
        }
        solvable = solFound;
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return solvable;
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!solvable) {
            return -1;
        }
        return solNode.moves;
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!solvable) {
            return null;
        }
        ArrayList<Board> lst = new ArrayList<>();
        SearchNode node = solNode;
        while (true) {
            lst.add(0, node.board);
            if (node.predeccesor == null)
                break;
            node = node.predeccesor;
        }
        return lst;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
