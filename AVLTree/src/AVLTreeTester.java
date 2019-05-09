import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class AVLTreeTester {

	@Test
	public void testIsEmpty() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

		assertTrue(tree.isEmpty());

		tree.add(5, "a");
		assertFalse(tree.isEmpty());

		tree.remove(5);
		assertTrue(tree.isEmpty());

		tree = makeBigTree();
		assertFalse(tree.isEmpty());

		while(!tree.isEmpty())
			tree.removeMin();
		assertTrue(tree.isEmpty());

		tree = makeBigTree();
		assertFalse(tree.isEmpty());

		while(!tree.isEmpty())
			tree.removeMax();
		assertTrue(tree.isEmpty());
	}

	@Test
	public void testSize() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

		assertEquals(0, tree.size());

		for (int i = 0; i < 50; i++) {
			tree.add(i, randomWord());
		}
		assertEquals(50, tree.size());

		for (int i = 0; i < 20; i++)
			tree.removeMax();
		assertEquals(30, tree.size());

		tree.clear();
		assertEquals(0, tree.size());

		tree.add(1, "a");
		tree.add(1, "b");
		assertEquals(1, tree.size());
	}

	@Test
	public void testHeight() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();

		assertEquals(0, tree.height());

		tree.add(1, "a");
		assertEquals(1, tree.height());

		tree = makeBigTree();

		while (!tree.isEmpty()) {
			// If height of AVL tree is h, maximum number of nodes can be 2^(h+1) – 1.
			assertTrue(tree.size() <= Math.pow(2, tree.height()+1) - 1);

			// If there are n nodes in AVL tree, minimum height of AVL tree is floor(log2n)
			assertTrue(tree.height() >= (Math.floor(Math.log(tree.size()) / Math.log(2))));

			tree.removeMax();
		}
		
		assertEquals(tree.height(), 0);
	}

	@Test
	public void testGet() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		
		assertEquals(tree.get(1), null);
		
		tree.add(1, "a");
		assertEquals(tree.get(1), "a");
		assertEquals(tree.get(2), null);
		
		tree.add(1, "b");
		assertEquals(tree.get(1), "b");

		tree.removeMax();
		assertEquals(tree.get(1), null);
		
		tree.clear();		
		for (int i = 0; i < 1000; i++) {
			assertEquals(tree.get(i), null);
			String word = randomWord();
			tree.add(i, word);
			assertEquals(tree.get(i), word);
		}
		
		
		for (int i = 0; i < 1000; i++) {
			assertFalse(tree.get(i) == null);
			tree.remove(i);
			assertEquals(tree.get(i), null);
		}
		
		for (int i = 0; i < 1000; i++) {
			assertEquals(tree.get(i), null);
		}
	}

	@Test
	public void testContains() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		
		assertFalse(tree.contains(1));
		
		tree.add(1, "a");
		assertTrue(tree.contains(1));
		
		tree.remove(2);
		assertTrue(tree.contains(1));
		assertFalse(tree.contains(2));
		
		tree.clear();
		for (int i = 0; i < 1000; i++) {
			assertFalse(tree.contains(i));
			tree.add(i, randomWord());
			assertTrue(tree.contains(i));
		}
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < i; j++)
				assertFalse(tree.contains(j));
			for (int j = i; j < 1000; j++)
				assertTrue(tree.contains(j));
			tree.remove(i);
		}
	}

	@Test
	public void testAdd() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		
		assertFalse(tree.contains(1));
		
		tree.add(1, "a");
		assertEquals(tree.get(1), "a");
		
		tree.add(1, "b");
		assertEquals(tree.get(1), "b");
		
		tree.add(1, null);
		assertEquals(tree.size(), 0);
	}

	@Test
	public void testRemove() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		
		tree.add(1, "a");
		assertFalse(tree.isEmpty());
		
		tree.remove(2);
		assertFalse(tree.isEmpty());
		
		tree.remove(1);
		assertTrue(tree.isEmpty());
		
		tree.clear();
		for (int i = 0; i < 1000; i++) {
			tree.add(i, randomWord());
		}
		for (int i = 0; i < 1000; i++) {
			assertEquals(tree.size(), 1000 - i);
			tree.remove(i);
		}
		assertTrue(tree.isEmpty());
	}

	@Test
	public void testDeleteMin() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		Random rnd = new Random();
		
		tree.add(2, "b");
		tree.add(5, "e");
		tree.add(3, "c");
		tree.add(4, "d");
		tree.add(1, "a");
		assertTrue(tree.contains(1));
		
		tree.removeMin();
		assertFalse(tree.contains(1));
		
		tree.clear();
		for (int i = 0; i < 1000; i++) {
			tree.add(rnd.nextInt(), "a");
		}
		while (tree.size() > 1) {
			Integer oldMin = tree.min();
			tree.removeMin();
			assertFalse(oldMin.equals(tree.min()));
		}
	}

	@Test
	public void testDeleteMax() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		Random rnd = new Random();
		
		tree.add(2, "b");
		tree.add(5, "e");
		tree.add(3, "c");
		tree.add(4, "d");
		tree.add(1, "a");
		assertTrue(tree.contains(5));
		
		tree.removeMax();
		assertFalse(tree.contains(5));
		
		tree.clear();
		for (int i = 0; i < 1000; i++) {
			tree.add(rnd.nextInt(), "a");
		}
		while (tree.size() > 1) {
			Integer oldMax = tree.max();
			tree.removeMax();
			assertFalse(oldMax.equals(tree.max()));
		}
	}

	@Test
	public void testMin() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		Random rnd = new Random();
		
		for (int i = 0; i < 1000; i++) {
			boolean cont = true;
			while(cont) {
				int randInt = rnd.nextInt(1000);
				if (!tree.contains(randInt)) {
					tree.add(randInt, "a");
					cont = false;
				}
			}
		}
		for (int i = 0; i < 1000; i++) {
			assertTrue(tree.min().equals(i));
			tree.remove(i);
		}
	}

	@Test
	public void testMax() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		Random rnd = new Random();
		
		for (int i = 0; i < 1000; i++) {
			boolean cont = true;
			while(cont) {
				int randInt = rnd.nextInt(1000);
				if (!tree.contains(randInt)) {
					tree.add(randInt, "a");
					cont = false;
				}
			}
		}
		for (int i = 999; i >= 0; i--) {
			assertTrue(tree.max().equals(i));
			tree.remove(i);
		}
	}

	@Test
	public void testClear() {
		AVLTree<Integer, String> tree = new AVLTree<Integer, String>();
		
		assertTrue(tree.isEmpty());
		
		tree = makeBigTree();
		assertFalse(tree.isEmpty());
		
		tree.clear();
		assertTrue(tree.isEmpty());
	}
	
	
	
	
	
	

	private AVLTree<Integer, String> makeBigTree() {
		AVLTree<Integer, String> tree = new AVLTree<>();
		Random rnd = new Random();

		for (int i = 0; i < 1000; i++) {
			tree.add(rnd.nextInt(10000), randomWord());
		}
		return tree;
	}

	private String randomWord() {
		Random rnd = new Random();
		String word = "";
		int len = rnd.nextInt(8) + 3;
		for (int i = 0; i < len; i++) {
			word += (char)(rnd.nextInt(26) + 'a');
		}
		return word;
	}

}
