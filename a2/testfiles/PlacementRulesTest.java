import static org.junit.Assert.*;

import org.junit.Test;

public class PlacementRulesTest {
	
    @Test
    public void testConstructorInitializesResourcesToZero() {
        Player p = new Player(1, null);
        assertEquals(0, p.getResources(Resource.WOOD));
        assertEquals(0, p.getResources(Resource.BRICK));
        assertEquals(0, p.getResources(Resource.WHEAT));
        assertEquals(0, p.getResources(Resource.SHEEP));
        assertEquals(0, p.getResources(Resource.ORE));
    }

    @Test
    public void testGetPlayerNumberReturnsCorrectValue() {
        Player p = new Player(3, null);
        assertEquals(3, p.getPlayerNumber());
    }

    @Test
    public void testUpdateResourcesIncreasesResource() {
        Player p = new Player(1, null);
        p.updateResources(Resource.WOOD, 3);
        assertEquals(3, p.getResources(Resource.WOOD));
    }

    @Test
    public void testUpdateResourcesDecreasesResource() {
        Player p = new Player(1, null);
        p.updateResources(Resource.BRICK, 5);
        p.updateResources(Resource.BRICK, -2);
        assertEquals(3, p.getResources(Resource.BRICK));
    }

    @Test
    public void testGetTotalResourcesReturnsCorrectSum() {
        Player p = new Player(1, null);
        p.updateResources(Resource.WOOD, 2);
        p.updateResources(Resource.BRICK, 1);
        p.updateResources(Resource.WHEAT, 3);
        p.updateResources(Resource.SHEEP, 4);
        p.updateResources(Resource.ORE, 5);

        assertEquals(15, p.getTotalResources());
    }

}
