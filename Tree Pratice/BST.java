public class BST {

	public node root;

	public BST(node root){
		this.root = root;
	}

	public void IN_ORDER_PRINT(node a){
		if(a != null) {
			IN_ORDER_PRINT(a.left);
			System.out.println(a.value);
			IN_ORDER_PRINT(a.right);
		}
	}

	public  node FIND (node a,int value) {
		while (a != null && value != a.value) {
			if (value < a.value) {
				a = a.left;
			} else {
				a = a.right;
			}
		}
		return a;
	}

	public node MAXIMUM(node a){
		while (a.right != null) {
			a = a.right;
		}
		return a;
	}

	public node MINIMUM (node a) {
		while (a.left != null) {
			a = a.left;
		}
		return a;
	}

	public node TREE_SUCCESSOR (node a) {
		if (a.right != null) {
			return MINIMUM(a.right);
		}
		node temp = a.parent;
		while (temp != null && a == temp.right) {
			a = temp;
			temp = temp.parent;
		}
		return temp;
	}

	public void IterateInsert(node root,int a){
		node temp = new node(a);
		node track = null;
		while (root != null) {
			track = root;
			if (a < root.value) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		temp.parent = track;
		if(a < track.value) {
			track.left = temp;
		} else {
			track.right = temp;
		}
	}

	public void TRANSPLANT(BST T,node u, node v){
		if (u.parent == null) {
			T.root = v;
		} else if (u == u.left.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}

		if (v != null) {
			v.parent = u.parent;
		}
	}

	public void TREE_DELETE (BST T, node z) {
		if (z.left == null) {
			TRANSPLANT(T,z,z.right);
		} else if (z.right == null) {
			TRANSPLANT(T,z,z.left);
		} else {
			node y = MINIMUM(z.right);
			if (y.parent != z) {
				TRANSPLANT(T,z,y);
				y.right = z.right;
				y.right.parent = y;
			}
			TRANSPLANT(T,z,y);
			y.left = z.left;
			y.left.parent = y;
		}
	}

}
