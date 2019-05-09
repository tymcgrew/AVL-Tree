/*
 * An implementation of an AVL self-balancing binary search tree that stores key-value pairs.
 * Search (get), insert (add), and delete (remove) are O(log(n)) time complexity
 * 
 * @author Tyler McGrew
 * 
 * 
 * toTreeString() that prints tree for visual inspection borrowed from:
 * @author MightyPork
 * https://stackoverflow.com/a/29704252
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AVLTree<K extends Comparable<K>, T extends Comparable<T>> implements BinarySearchTreeInterface<K, T> {


	private class Node {
		Node left, right;
		K key;
		T val;
		int height;

		public Node(K key, T val) {
			this.key = key;
			this.val = val;
			height = 1;
		}

		public String getKeyString() {
			return key.toString();
		}
		
		public String getValString() {
			return val.toString();
		}
		
		@Override
		public String toString() {
			return key.toString() + ": " + val.toString();
		}
	}




	Node root;


	public AVLTree() {
		root = null;
	}


	@Override
	public boolean isEmpty() {
		return root == null;
	}



	@Override
	public int size() {
		return size(root);
	}
	private int size(Node root) {
		if (root == null) {
			return 0;
		}
		return 1 + size(root.left) + size(root.right);
	}



	@Override
	public int height() {
		return height(root);
	}
	private int height(Node root) {
		if (root == null)
			return 0;
		return root.height;
	}



	private int getBalance(Node root) {
		if (root == null)
			return 0;
		return height(root.left) - height(root.right);
	}



	@Override
	public T get(K key) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}
		return get(key, root);
	}
	private T get(K key, Node root) {
		if (root == null)
			return null;
		if (key.equals(root.key))
			return root.val;
		if (key.compareTo(root.key) < 0)
			return get(key, root.left);
		return get(key, root.right);
	}



	@Override
	public boolean contains(K key) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}
		return contains(key, root);
	}
	private boolean contains(K key, Node root) {
		if (root == null)
			return false;
		if (key.equals(root.key))
			return true;
		if (key.compareTo(root.key) < 0)
			return contains(key, root.left);
		return contains(key, root.right);
	}


	
	private Node rotateRight(Node A) {
		Node B = A.left;
		Node C = B.right;
		
		B.right = A;
		A.left = C;
		
		A.height = 1 + Math.max(height(A.left), height(A.right));
		B.height = 1 + Math.max(height(B.left), height(B.right));
		
		return B;
	}
	private Node rotateLeft(Node A) {
		Node B = A.right;
		Node C = B.left;
		
		B.left = A;
		A.right = C;
		
		A.height = 1 + Math.max(height(A.left), height(A.right));
		B.height = 1 + Math.max(height(B.left), height(B.right));
		
		return B;
	}
	
		
	
	@Override
	public void add(K key, T val) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}
		if (val == null) {
			remove(key);
			return;
		}
		root = add(root, new Node(key, val));
	}
	private Node add(Node root, Node newNode) {	
		// Standard insert operation here
		if (root == null)
			return newNode;

		if (newNode.key.compareTo(root.key) < 0)
			root.left = add(root.left, newNode);
		else if (newNode.key.compareTo(root.key) > 0)
			root.right = add(root.right, newNode);
		else {
			root.val = newNode.val;
			return root;
		}

		// Update height
		root.height = 1 + Math.max(height(root.left), height(root.right));

		// Update balance
		int balance = getBalance(root);

		// Balance if needed
		if (balance > 1 && getBalance(root.left) >= 0)                // left-left case
			return rotateRight(root);
		
		if (balance < -1 && getBalance(root.right) <= 0)              // right-right case
			return rotateLeft(root);
		
		if (balance > 1 && getBalance(root.left) < 0) {              // left-right case
			root.left = rotateLeft(root.left);
			return rotateRight(root);
		}
		
		if (balance < -1 && getBalance(root.right) > 0) {            // right-left case;
			root.right = rotateRight(root.right);
			return rotateLeft(root);
		}
		
		return root;
	}



	@Override
	public void remove(K key) {
		if (key == null)
			throw new IllegalArgumentException("Key is null");
		root = remove(root, key);
	}
	private Node remove(Node root, K key) {
		// Standard Delete Operation
		if (root == null)
			return root;
		if (key.compareTo(root.key) < 0)
			root.left = remove(root.left, key);
		else if (key.compareTo(root.key) > 0)
			root.right = remove(root.right, key);
		
																	// This is the node to be deleted
		else {                                                          
			if (root.left == null && root.right != null)       		// right child only case
				root = root.right;
			else if (root.right == null && root.left != null)     	// left child only case
				root = root.left;
			else if (root.left == null && root.right == null)     	// no children (leaf node) case
				root = null;
			
			else {                                                	// two children case
				Node leftMax = minNode(root.left);         				// find max of left subtree
				root.key = leftMax.key;									// replace root with key and value of max of left subtree
				root.val = leftMax.val;
				root.left = remove(root.left, leftMax.key);				// remove max of left subtree
			}
		}
		
		if (root == null)                                     		// if root is now null (tree had only root to begin with)
			return root;												// return root (null)
		
		// Update height
		root.height = 1 + Math.max(height(root.left), height(root.right));
		
		// Update balance
		int balance = getBalance(root);
		
		// Balance if needed
		if (balance > 1 && getBalance(root.left) >= 0)                // left-left case
			return rotateRight(root);
		
		if (balance < -1 && getBalance(root.right) <= 0)              // right-right case
			return rotateLeft(root);
		
		if (balance > 1 && getBalance(root.left) < 0) {              // left-right case
			root.left = rotateLeft(root.left);
			return rotateRight(root);
		}
		
		if (balance < -1 && getBalance(root.right) > 0) {            // right-left case;
			root.right = rotateRight(root.right);
			return rotateLeft(root);
		}
		
		return root;
	}
	private Node minNode(Node root) {
		Node current = root;
		while (current.right != null)
			current = current.right;
		return current;
	}


	@Override
	public void removeMin() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is empty");
		remove(getMinKey(root));
	}



	@Override
	public void removeMax() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is empty");
		remove(getMaxKey(root));
	}



	@Override
	public K min() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is empty");
		return getMinKey(root);
	}
	private K getMinKey(Node root) {
		if (root == null)
			return null;
		K left = getMinKey(root.left);
		K right = getMinKey(root.right);
		if (left == null && right == null)
			return root.key;
		if (left == null)
			return (right.compareTo(root.key) < 0)? right : root.key;
		if (right == null)
			return (left.compareTo(root.key) < 0)? left : root.key;
		K lrmin = (left.compareTo(right) < 0)? left : right;
		return (lrmin.compareTo(root.key) < 0)? lrmin : root.key;
	}



	@Override
	public K max() {
		if (isEmpty())
			throw new NoSuchElementException("Tree is empty");
		return getMaxKey(root);
	}
	private K getMaxKey(Node root) {
		if (root == null)
			return null;
		K left = getMaxKey(root.left);
		K right = getMaxKey(root.right);
		if (left == null && right == null)
			return root.key;
		if (left == null)
			return (right.compareTo(root.key) > 0)? right : root.key;
		if (right == null)
			return (left.compareTo(root.key) > 0)? left : root.key;
		K lrmax = (left.compareTo(right) > 0)? left : right;
		return (lrmax.compareTo(root.key) > 0)? lrmax : root.key;
	}
	
	
	
	public void clear() {
		root = null;
	}
	
	
	
	@Override
	public String toString() {
		return inOrderTraversal();
	}
	private String inOrderTraversal() {
		return inOrderTraversal(root);
	}
	private String inOrderTraversal(Node root) {
		if (root == null)
			return "";
		return inOrderTraversal(root.left) + root + "\n" + inOrderTraversal(root.right);
	}


	
	public void printTree() {
		toTreeString(root, 0);
	}
	public void printKeyTree() {
		toTreeString(root, 1);
	}
	public void printValTree() {
		toTreeString(root, 2);
	}
	/**
	 * Binary tree printer
	 * 
	 * @author MightyPork
	 * https://stackoverflow.com/a/29704252
	 */
	private void toTreeString(Node root, int ctrl)
	{
		List<List<String>> lines = new ArrayList<List<String>>();

		List<Node> level = new ArrayList<Node>();
		List<Node> next = new ArrayList<Node>();

		level.add(root);
		int nn = 1;

		int widest = 0;

		while (nn != 0) {
			List<String> line = new ArrayList<String>();

			nn = 0;

			for (Node n : level) {
				if (n == null) {
					line.add(null);

					next.add(null);
					next.add(null);
				} else {
					String aa;
					if (ctrl == 0)
						aa = n.toString();
					else if (ctrl == 1)
						aa = n.getKeyString();
					else
						aa = n.getValString();
					line.add(aa);
					if (aa.length() > widest) widest = aa.length();

					next.add(n.left);
					next.add(n.right);

					if (n.left != null) nn++;
					if (n.right != null) nn++;
				}
			}

			if (widest % 2 == 1) widest++;

			lines.add(line);

			List<Node> tmp = level;
			level = next;
			next = tmp;
			next.clear();
		}

		int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
		for (int i = 0; i < lines.size(); i++) {
			List<String> line = lines.get(i);
			int hpw = (int) Math.floor(perpiece / 2f) - 1;

			if (i > 0) {
				for (int j = 0; j < line.size(); j++) {

					// split node
					char c = ' ';
					if (j % 2 == 1) {
						if (line.get(j - 1) != null) {
							c = (line.get(j) != null) ? '┴' : '┘';
						} else {
							if (j < line.size() && line.get(j) != null) c = '└';
						}
					}
					System.out.print(c);

					// lines and spaces
					if (line.get(j) == null) {
						for (int k = 0; k < perpiece - 1; k++) {
							System.out.print(" ");
						}
					} else {

						for (int k = 0; k < hpw; k++) {
							System.out.print(j % 2 == 0 ? " " : "─");
						}
						System.out.print(j % 2 == 0 ? "┌" : "┐");
						for (int k = 0; k < hpw; k++) {
							System.out.print(j % 2 == 0 ? "─" : " ");
						}
					}
				}
				System.out.println();
			}

			// print line of numbers
			for (int j = 0; j < line.size(); j++) {

				String f = line.get(j);
				if (f == null) f = "";
				int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
				int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

				// a number
				for (int k = 0; k < gap1; k++) {
					System.out.print(" ");
				}
				System.out.print(f);
				for (int k = 0; k < gap2; k++) {
					System.out.print(" ");
				}
			}
			System.out.println();

			perpiece /= 2;

		}
		return;
	}

}
