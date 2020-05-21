package huffman;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class HuffmanTest {
    private static final Logger aLOGGER = Logger.getLogger(HuffmanTest.class.getName());

    private static final String INPUT = "Gallia est omnis divisa in partes tres; unam partem incolunt Belgae,"
            + "aliam Aquitani, tertiam qui ipsorum linguā Celtae, nostrā Galli, appellantur.";

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
        Map<Character, Integer> frequencies = Huffman.getFrequencies(example);
        assertThat(frequencies, IsMapContaining.hasEntry('t', 2));
        assertThat(frequencies, is(expected));
        assertThat(frequencies.size(), is(9));
    }

    @Test
    public void examineTree() {
        aLOGGER.log(Level.INFO, INPUT);
        Map<Character, Integer> freqMap = Huffman.getFrequencies(INPUT);
        Node root = Node.buildTree(freqMap);
        Map<Character, Node> leaves = Node.getLeaves();
        String encoded = Huffman.encode(INPUT, root, leaves);
        aLOGGER.log(Level.INFO, encoded);
        String decoded = Huffman.decode(encoded, root);
        aLOGGER.log(Level.INFO, decoded);
        assertEquals("", INPUT, decoded);
    }
}
