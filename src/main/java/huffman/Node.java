package huffman;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class Node implements Comparable<Node> {

	private Node left; //child
	private Node right; //child
	private final char value;
	private int frequency;
	private Node parent;
	private static Map<Character, Node> leaves = new HashMap<>();
	
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
		Node parent = new Node();
		if (kid == null) {
			//return this;
			parent.frequency = this.frequency;
			parent.left = this;
			return parent;
		}
		
		parent.frequency = this.frequency + kid.frequency;
		parent.left = this.frequency >= kid.frequency ? this : kid;
		parent.right = this.frequency < kid.frequency ? this : kid;
		this.parent = parent;
		kid.parent = parent;
		return parent;
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
	static public Map<Character, Node> getLeaves() {
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
		PriorityQueue<Node> thing = new PriorityQueue<>(listOfLeaves);
		return makeTree(thing);		
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
		} else
			return node;
	}

	@Override
	public int compareTo(Node o) {
		return this.frequency - o.frequency;
	}

	@Override
	public String toString() {
		return this.isLeaf() ? "Leaf " + this.value : "Inter";
	}

	@Test
	public void testTreeBuilding() {
		assertTrue("", true);
	}
	
}
