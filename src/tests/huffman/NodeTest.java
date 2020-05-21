package huffman;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NodeTest {
    // TODO : this 'test' ain't doing much ...
    @Test
    public void testTreeBuilding() {
        assertTrue("", true);
    }

    @Test
    public void makeLeafTest () {
        Node n1 = Node.makeLeaf('Q', 7);
        assertTrue(n1.isRoot());
        assertTrue(n1.isLeaf());
        Node n2 = null;
        Node newNode = n1.combine(null);
        assertTrue("", Character.MIN_VALUE == newNode.getChar());
        assertTrue("", 7 == newNode.getCount());
        assertTrue("", 'Q' == n1.getChar());
        assertTrue("", 7 == n1.getCount());

        n2 = Node.makeLeaf('B', 11);

        newNode = n1.combine(n2);

        assertTrue("", Character.MIN_VALUE == newNode.getChar());
        assertTrue("", 18 == newNode.getCount());
        assertTrue("", newNode.goLeft() == n2);
        assertTrue("", newNode.goRight() == n1);
    }
}
