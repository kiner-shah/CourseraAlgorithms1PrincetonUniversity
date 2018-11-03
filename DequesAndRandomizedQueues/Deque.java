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

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size_deque;
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    public Deque() {    // construct an empty deque
        size_deque = 0;
        first = null;
        last = null;
    }
    public boolean isEmpty() {  // is the deque empty?
        return size_deque == 0;
    }
    public int size() { // return the number of items on the deque
        return size_deque;
    }
    public void addFirst(Item item) {   // add the item to the front
        if(item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if(isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.prev = null;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.prev = null;
            oldFirst.prev = first;
        }
        size_deque++;
    }
    public void addLast(Item item) {    // add the item to the end
        if(item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if(isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = null;
            first = last;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;
        }
        size_deque++;
    }
    public Item removeFirst() { // remove and return the item from the front
        if(isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = null;
        if(first == last) {
            item = first.item;
            first = null;
            last = null;
        }
        else {
            item = first.item;
            first = first.next;
            first.prev = null;
        }
        size_deque--;
        return item;
    }
    public Item removeLast() {  // remove and return the item from the end
        if(isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = null;
        if(first == last) {
            item = first.item;
            first = null;
            last = null;
        }
        else {
            item = last.item;
            last = last.prev;
            last.next = null;
        }
        size_deque--;
        if(item == null)
            throw new java.lang.NullPointerException("removeLast(): item is null");
        return item;
    }
    @Override
    public Iterator<Item> iterator() {  // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public Item next() {
            if(current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        @Override
        public boolean hasNext() {
            return current != null;
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
