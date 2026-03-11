import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class BoardTest {
	
	@Test //Test 13
    public void testConstructorInitializesBoardResourcesToNineteen() {
        Board board = new Board();

        assertEquals(19, board.getResources(Resource.WOOD));
        assertEquals(19, board.getResources(Resource.BRICK));
        assertEquals(19, board.getResources(Resource.WHEAT));
        assertEquals(19, board.getResources(Resource.SHEEP));
        assertEquals(19, board.getResources(Resource.ORE));
    }

    @Test //Test 14
    public void testUpdateResourcesDecreasesBoardResourceCount() {
        Board board = new Board();

        board.updateResources(Resource.WOOD, -3);

        assertEquals(16, board.getResources(Resource.WOOD));
    }

    @Test //Test 15
    public void testCheckResourcesReturnsTrueWhenResourceStaysNonNegative() {
        Board board = new Board();

        assertTrue(board.checkResources(Resource.BRICK, -5));
    }

    @Test //Test 16
    public void testCheckResourcesReturnsFalseWhenResourceWouldBeNegative() {
        Board board = new Board();

        assertFalse(board.checkResources(Resource.BRICK, -20));
    }

    @Test //Test 17
    public void testCheckResourcesReturnsFalseForNullResource() {
        Board board = new Board();

        assertFalse(board.checkResources(null, -1));
    }

    @Test //Test 18
    public void testAddRoadAddsRoadToBoardRoadList() {
        Board board = new Board();
        Player player = new Player(1, board);

        Node start = new Node(Arrays.asList(1));
        Node end = new Node(Arrays.asList(0));
        Road road = new Road(start, end, player);

        board.addRoad(road);

        assertEquals(1, board.getRoads().size());
        assertTrue(board.getRoads().contains(road));
    }

    @Test //Test 19
    public void testGetNodesByIndexMatchesNodeArray() {
        Board board = new Board();

        assertSame(board.getNodes()[0], board.getNodes(0));
    }

}
