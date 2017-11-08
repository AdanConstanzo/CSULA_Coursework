public class node{
	public int value;
	public node left;
	public node right;
	public node parent;
	public node (int value) {
		this.value = value;
		left = right = parent = null;
	}
}