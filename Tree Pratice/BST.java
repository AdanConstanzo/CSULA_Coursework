public class BST{

	public int value;
	public BST left = null;
	public BST right = null;
	public BST parent = null;

	public BST(int value) {
		this.value = value;
	}

	// prints in order with recursion.
	// put root as BST
	public  static void IN_ORDER_PRINT(BST x) {
		if(x != null) {
			IN_ORDER_PRINT(x.left);
			System.out.println(x.value);
			IN_ORDER_PRINT(x.right);
		}
	}

	// does binary tree-seach
	// Time complexity based on height of tree.
	// return BST node.
	public static BST TREE_SEARCH(BST x, int k) {
		if(x == null || x.value == k) {
			return x;
		}
		if(k < x.value) {
			return TREE_SEARCH(x.left,k);
		} else {
			return TREE_SEARCH(x.right,k);
		}
	}

	// Itterative approach to Tree Search.
	// Uses less memory.
	public static BST Iterative_TREE_SEARCH(BST x, int k) {

		while (x != null && k != x.value) {
			System.out.println("hit");
			if (k < x.value) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return x;	
	}


	public static void insertTree (BST x, int num) {
		BST node = new BST(num);

		BST track = null;

		// itterating through tree
		while (x != null) {
			track = x;
			if (node.value <= x.value) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		if (node.value <= track.value) {
			track.left = node;
		} else {
			track.right = node;
		}

	}

	public static BST getMin (BST x) {

		while ( x.left != null ){
			x = x.left;
		}

		return x;
	}

}