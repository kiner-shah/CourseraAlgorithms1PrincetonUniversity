/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kiner Shah
 */
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item s[];
    private int size_random_que;
    public RandomizedQueue() {  // construct an empty randomized queue
        size_random_que = 0;
        s = (Item[]) new Object[1];
    }
    public boolean isEmpty() {  // is the randomized queue empty?
        return size_random_que == 0;
    }
    public int size() { // return the number of items on the randomized queue
        return size_random_que;
    }
    public void enqueue(Item item) {    // add the item
        if(item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if(size_random_que == s.length) resize(s.length * 2);
        s[size_random_que] = item;
        size_random_que++;
    }
    private void resize(int capacity) {
        final Item[] copy = (Item[]) new Object[capacity];
        for(int i = 0; i < size_random_que; i++)
            copy[i] = s[i];
        s = copy;
    }
    public Item dequeue() { // remove and return a random item
        if(isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int indexToRemove = StdRandom.uniform(size_random_que);
        Item item = s[indexToRemove];
        // Cheat -> put the element at tail to indexToRemove
        s[indexToRemove] = s[size_random_que - 1];
        s[size_random_que - 1] = null;
        size_random_que--;
        if(size_random_que > 0 && s.length / 4 == size_random_que)
            resize(s.length / 2);
        return item;
    }
    public Item sample() {  // return a random item (but do not remove it)
        if(isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int indexToReturn = StdRandom.uniform(size_random_que);
        return s[indexToReturn];
    }
    @Override
    public Iterator<Item> iterator() {  // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = size_random_que;
        final private Item[] copy;
        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[size_random_que];
            for(int k = 0; k < size_random_que; k++)
                copy[k] = s[k];
            StdRandom.shuffle(copy);
        }
        @Override
        public Item next() {
            if(i == 0) 
                throw new java.util.NoSuchElementException();
            return copy[--i];
        }
        @Override
        public boolean hasNext() {
            return i > 0;
        }
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
    }
//    public static void main(String[] args) {  // unit testing (optional)
//        
//    }
}
