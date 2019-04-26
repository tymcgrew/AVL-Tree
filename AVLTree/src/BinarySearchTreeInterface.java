/*
 * Interface for a sorted binary tree with no repeated nodes
 * 
 * Modified from the following interface:
 * https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/AVLTreeST.java.html
 */


public interface BinarySearchTreeInterface<K, T> {

	/**
     * Checks if the tree is empty.
     * 
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty();
    

    /**
     * Returns the number of key-value pairs in the tree
     * 
     * @return the number of key-value pairs in the tree
     */
    public int size();
    

    /**
     * Returns the height of the AVL tree. It is assumed that the
     * height of an empty tree is 0 and the height of a tree with just one node is 1.
     * 
     * @return the height of the AVL tree
     */
    public int height();
    

    /**
     * Returns the value associated with the given key.
     * 
     * @param key the key
     * @return the value associated with the given key if the key is in the
     *         tree and null if the key if not
     * @throws IllegalArgumentException if key is null
     */
    public T get(K key);
    

    /**
     * Checks if the tree contains the given key.
     * 
     * @param key the key
     * @return true if the tree contains key
     *         and false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(K key);
    

    /**
     * Inserts the specified key-value pair into the tree, overwriting
     * the old value with the new value if the tree already contains the
     * specified key. Deletes the specified key (and its associated value) from
     * this tree if the specified value is null.
     * 
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if key is null
     */
    public void add(K key, T val);
    

    /**
     * Removes the specified key and its associated value from the tree
     * (if the key is in the tree).
     * 
     * @param key the key
     * @throws IllegalArgumentException if key is null
     */
    public void remove(K key);
    

    /**
     * Removes the smallest key and associated value from the tree.
     * 
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMin();
    

    /**
     * Removes the largest key and associated value from the tree.
     * 
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMax();
    

    /**
     * Returns the smallest key in the tree.
     * 
     * @return the smallest key in the tree
     * @throws NoSuchElementException if the tree is empty
     */
    public T min();

    
    /**
     * Returns the largest key in the tree.
     * 
     * @return the largest key in the tree
     * @throws NoSuchElementException if the tree is empty
     */
    public T max();

	
}
