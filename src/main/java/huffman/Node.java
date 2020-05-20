package huffman;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the binary search tree, storing the characters
 * and their frequencies in the original input <code>String</code>
 */
public class Node implements Comparable<Node> {
	/** left child */
	private Node left;
	/** right child */
	private Node right;
	/** stored character */
	private final char value;
	/** frequency of this character in the original text */
	private int frequency;
	/** parent node */
	private Node parent;
	/** map of leaves (character -> tree node) */
	private static final  Map<Character, Node> leaves = new HashMap<>();
	
	public Node() {
		this.value = Character.MIN_VALUE;
	}
	
	private Node (char value, int frequency) {
		this.value = value;
		this.frequency = frequency;
	}
	
	public static Node makeLeaf(char value, int frequency) {
		Node leaf =  new Node (value, frequency);
		leaves.put(value, leaf);
		return leaf;
	}
	
	public Node combine(Node kid) {
		Node temp = new Node();
		if (kid == null) {
			temp.frequency = this.frequency;
			temp.left = this;
			return temp;
		}
		
		temp.frequency = this.frequency + kid.frequency;
		temp.left = this.frequency >= kid.frequency ? this : kid;
		temp.right = this.frequency < kid.frequency ? this : kid;
		this.parent = temp;
		kid.parent = temp;
		return temp;
	}

	public boolean isRoot() {
		return parent == null;
	}
	public Node goLeft() {
		return this.left;
	}
	public Node goRight() {
		return this.right;
	}
	public int getCount() {
		return this.frequency;
	}
	public char getChar() {
		return this.value;
	}
	public boolean isLeft() {
		if (this.isRoot())
			return false;
		return parent.left == this;
	}
	public boolean isLeaf() {
		return this.left == null && this.right == null;
	}
	public static Map<Character, Node> getLeaves() {
		return leaves;
	}
	
	
	public Node goUp() {
		return parent;
	}
	
	public static Node buildTree(Map<Character, Integer> freqMap) {
		
		List<Node> listOfLeaves = freqMap
				.entrySet().stream()
				.map(e -> makeLeaf(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
		PriorityQueue<Node> priorityQ = new PriorityQueue<>(listOfLeaves);
		return makeTree(priorityQ);
	}
	
	private static Node makeTree(PriorityQueue<Node> queue) {
		while(queue.size() > 1) {
			Node a = queue.poll(); 
			Node b = queue.poll();
			Node c = a.combine(b); 
			queue.add(c);
		}
		Node node =  queue.poll();
		if (node.isLeaf()) {
			Node root = new Node();
			node.parent = root;
			root.left = node;
			root.right = node;
			root.frequency = node.getCount();
			return root;
		}
		return node;
	}

	@Override
	public int compareTo(Node o) {
		return this.frequency - o.frequency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node node = (Node) o;
		return value == node.value &&
				frequency == node.frequency &&
				Objects.equals(left, node.left) &&
				Objects.equals(right, node.right) &&
				Objects.equals(parent, node.parent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right, value, frequency, parent);
	}

	@Override
	public String toString() {
		return this.isLeaf() ? "Leaf " + this.value : "Internal";
	}


	
}
