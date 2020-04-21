package huffman;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * 	This class is a simple implementation of the Huffman's algorithm for text compression.
 */
public class Huffman {

	String input = "Gallia est omnis divisa in partes tres; unam partem incolunt Belgae,"
			+ "aliam Aquitani, tertiam qui ipsorum linguā Celtae, nostrā Galli, appellantur.";

	/**
	 * Given a <code>String</code> this method determines the frequency of all characters.
	 * @param input <code>String</code> to be examined.
	 * @return <code>Map</code> of characters and their respective frequencies.
	 */
	private static Map<Character, Integer> getFrequencies(String input) {
		return input.chars().mapToObj(c -> (char) c)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(c -> 1)));
	}

	@Test
	public void testGetFrequencies () {
		String example = "This is a test.";
		Map<Character, Integer> expected = new HashMap<>();
		expected.put('a', 1);
		expected.put('t', 2);
		expected.put('h', 1);
		expected.put('i', 2);
		expected.put('s', 3);
		expected.put('e', 1);
		expected.put(' ', 3);
		expected.put('T', 1);
		expected.put('.', 1);
 		Map<Character, Integer> frequencies = getFrequencies(example);
		assertThat(frequencies, IsMapContaining.hasEntry('t', 2));
		assertThat(frequencies, is(expected));
		assertThat(frequencies.size(), is(9));
	}


	@Test
	public void examineTree() {
		System.out.println(input);
		Map<Character, Integer> freqMap = getFrequencies(input);
		Node root = Node.buildTree(freqMap);
		Map<Character, Node> leaves = Node.getLeaves();
		String encoded = encode(input, root, leaves);
		System.out.println(encoded);
		String decoded = decode(encoded, root);
		System.out.println(decoded);
		assertEquals("", input, decoded);
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

	private String decode(String code, Node root) {
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
