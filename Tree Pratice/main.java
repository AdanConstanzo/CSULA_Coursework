class main {
	public static void main(String[] args) {
		node root = new node(6);
		int[] arr = {4,5,2,1,7,7,8};
		BST bst = new BST(root);

		for (int i = 0; i < arr.length; i++) {
			bst.IterateInsert(root,arr[i]);
		}

		bst.TREE_DELETE(bst,bst.FIND(root,6));

		System.out.println(root.value);


	}// end of void main
} // end of class main
