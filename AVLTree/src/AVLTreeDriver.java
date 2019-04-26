import java.util.Random;

public class AVLTreeDriver {

	static AVLTree<Integer, String> tree;
	
	public static void main(String[] args) {
				
		tree = new AVLTree<Integer, String>();
		
		
		
		fillRand(tree);
		System.out.println("Random Tree, printing key tree:\n");
		tree.printKeyTree();
		
		
		tree.clear();
		System.out.println("\n\n");
		
		
		fillFull(tree);
		System.out.println("Full Tree with height of four, printing key-value pair tree:\n");
		tree.printTree();
		
		System.out.println("\n\n");
		System.out.println("Same tree, printing value tree");
		tree.printValTree();
		
		System.out.println("\n\n");
		System.out.println("Standard toString() uses inOrder traversal to print key-value pairs in order:");
		System.out.println(tree);
		
	}
	
	public static void fillFull(AVLTree<Integer, String> tree) {
		tree.add(8, "eight");		
		tree.add(4, "four");
		tree.add(12, "twelve");
		tree.add(2, "two");
		tree.add(6, "six");
		tree.add(10, "ten");
		tree.add(14, "fourteen");
		tree.add(1, "one");
		tree.add(3, "three");
		tree.add(5, "five");
		tree.add(7, "seven");
		tree.add(9, "nine");
		tree.add(11, "eleven");
		tree.add(13, "thirteen");
		tree.add(15, "fifteen");
	}
	
	public static void fillRand(AVLTree<Integer, String> tree) {
		Random rnd = new Random();
		for (int i = 0; i < 20; i++) {
			tree.add(rnd.nextInt(100), "Value");
		}
	}
	


}
