package huffman;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 	This class is a simple implementation of the Huffman's algorithm for text compression.
 */
public class Huffman {

	private Huffman() {}

	/**
	 * Given a <code>String</code> this method determines the frequency of all characters.
	 * @param input <code>String</code> to be examined.
	 * @return <code>Map</code> of characters and their respective frequencies.
	 */
	static Map<Character, Integer> getFrequencies(String input) {
		return input.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
	}

	/**
	 * Given the text and the frequency tree, this method produces a Huffman encoding of the text.
	 * @param text <code>String</code> to be encoded.
	 * @param root <code>Node</code> of the tree.
	 * @param leaves A <code>Map</code> of characters and their corresponding leaves.
	 * @return <code>String</code> encoded text.
	 */
	public static String encode(String text, Node root, Map<Character, Node> leaves) {
		return text.chars()
				.mapToObj(letter -> leaves.get((char) letter))
				.map(leaf -> trackUp(root, leaf))
				.collect(Collectors.joining(""));
	}

	private static String trackUp(Node root, Node leaf) {
		StringBuilder sb = new StringBuilder();
		while (leaf != root) {
			sb.append(leaf.isLeft() ? 0 : 1);
			leaf = leaf.goUp();
		}
		return sb.reverse().toString();
	}

	/**
	 * Given a coded <code>String</code> and the <code>Node</code> the root of the "frequencies tree"
	 * this function would decode it back to the original.
	 * @param code compressed string
	 * @param root frequency tree
	 * @return decoded text
	 */
	public static String decode(String code, Node root) {
		Node node = root;
		char[] codeArr = code.toCharArray();
		String text = "";
		for (char c : codeArr) {
			int i = Character.getNumericValue(c);
			node = (i == 0 ? node.goLeft() : node.goRight());
			if (node.isLeaf()) {
				text = text.concat(String.valueOf(node.getChar()));
				node = root;
			}
		}
		//code.chars().map(c -> (int)c).map(dir -> dir == 0 ? node.goLeft() : node.goRight()).
		return text;
	}
}
