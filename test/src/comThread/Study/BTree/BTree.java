package comThread.Study.BTree;

import java.util.Arrays;

public class BTree {
	private class Node {
		Comparable data;
		private Node left;
		private Node right;

		public Node(Comparable data) {
			this.data = data;
		}

		public void addNode(Node node) {
			if (this.data.compareTo(node.data) < 0) {
				if (this.right == null) {
					this.right = node;
				} else {
					this.right.addNode(node);
				}
			} else {
				if (this.left == null) {
					this.left = node;
				} else {
					this.left.addNode(node);
				}
			}
		}

		public void toArarryNode() {
			if (this.left != null) {
				this.left.toArarryNode();
			}
			retData[foot++] = this.data;
			if (this.right != null) {
				this.right.toArarryNode();
			}
			// retData[foot++] =this.data;
		}

		public boolean contain(Comparable para) {
			// if(this.data.equals(para)){
			// return true;
			// }else{
			// if(this.left!=null){
			// this.left.contain(para);
			// }
			// if(this.right!=null){
			// this.right.contain(para);
			// }
			// }

			if (this.left != null) {
				if (this.data.equals(para)) {
					return true;
				} else {
					this.left.contain(para);
				}
			}
			if (this.right != null) {
				this.right.contain(para);
			}
			return false;
		}
	}

	private Node root;
	private int count;
	private Object[] retData;
	private int foot;

	public void add(Comparable data) {
		Node newNode = new Node(data);
		if (root == null) {
			root = newNode;
		} else {
			root.addNode(newNode);
		}
		this.count++;
	}

	public Object[] toArarry() {
		if (root == null) {
			return null;
		}
		this.foot = 0;
		retData = new Object[this.count];
		this.root.toArarryNode();
		return retData;
	}

	public boolean contain(Comparable para) {
		// if(root ==null){
		// return false;
		// }else{
		// return root.contain(para);
		// }

		if (root == null) {
			return false;
		} else if (root.data.equals(para)) {
			return true;
		} else {
			return root.contain(para);
		}
	}

	public static void main(String[] args) {
		Book book1 = new Book("111", 14);
		Book book2 = new Book("111", 13);
		Book book3 = new Book("111", 3);
		Book book4 = new Book("111", 2);
		Book book5 = new Book("111", 4);
		Book book6 = new Book("111", 12);
		Book book7 = new Book("111", 15);
		Book book8 = new Book("111", 19);
		// Book book9 = new Book("111",15);
		// Book book10 = new Book("111",16);
		BTree tree = new BTree();
		tree.add(book1);
		tree.add(book2);
		tree.add(book3);
		tree.add(book4);
		tree.add(book5);
		tree.add(book6);
		tree.add(book7);
		// tree.add(book8);
		// tree.add(book9);
		// tree.add(book10);
		Object obs[] = tree.toArarry();
		System.out.println(Arrays.toString(obs));
		System.out.println(tree.contain(book8));
	}
}
