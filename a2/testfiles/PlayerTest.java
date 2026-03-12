RegexTestimport static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	@Test //Test 1
	public void testConstructorInitializesResourcesToZero() {
	    Player p = new Player(1, null);

	    assertEquals(0, p.getResources(Resource.WOOD));
	    assertEquals(0, p.getResources(Resource.BRICK));
	    assertEquals(0, p.getResources(Resource.WHEAT));
	    assertEquals(0, p.getResources(Resource.SHEEP));
	    assertEquals(0, p.getResources(Resource.ORE));
	}
	
	@Test //Test 2
    public void testGetPlayerNumberReturnsCorrectValue() {
        Player p = new Player(3, null);
        assertEquals(3, p.getPlayerNumber());
    }

    @Test //Test 3
    public void testUpdateResourcesIncreasesResource() {
        Player p = new Player(1, null);
        p.updateResources(Resource.WOOD, 3);
        assertEquals(3, p.getResources(Resource.WOOD));
    }

    @Test //Test 4
    public void testUpdateResourcesDecreasesResource() {
        Player p = new Player(1, null);
        p.updateResources(Resource.BRICK, 5);
        p.updateResources(Resource.BRICK, -2);
        assertEquals(3, p.getResources(Resource.BRICK));
    }

    @Test //Test 5
    public void testGetTotalResourcesReturnsCorrectSum() {
        Player p = new Player(1, null);

        p.updateResources(Resource.WOOD, 2);
        p.updateResources(Resource.BRICK, 1);
        p.updateResources(Resource.WHEAT, 3);
        p.updateResources(Resource.SHEEP, 4);
        p.updateResources(Resource.ORE, 5);

        assertEquals(15, p.getTotalResources());
    }
    
    @Test //Test 6
    public void testAddRoadAddsRoadToPlayerList() {
        Player p = new Player(1, null);

        Node n1 = new Node(java.util.Arrays.asList(1));
        Node n2 = new Node(java.util.Arrays.asList(0));

        Road road = new Road(n1, n2, p);
        p.addRoad(road);

        assertEquals(1, p.getRoads().size());
        assertTrue(p.getRoads().contains(road));
    }
    
    @Test //Test 7 
    public void testAddCityBuildingIncreasesCityCount() {
        Player p = new Player(1, null);

        Node node = new Node(java.util.Arrays.asList(1, 2));
        City city = new City(p, node);

        p.addBuilding(city);

        assertEquals(1, p.getCityCount());
        assertEquals(0, p.getSettlementCount());
    }

}
