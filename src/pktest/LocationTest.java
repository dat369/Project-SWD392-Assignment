package pktest;

import static org.junit.Assert.assertEquals;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pkproject.model.Location;

@Generated(value = "org.junit-tools-1.1.0")
public class LocationTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	private Location createTestSubject() {
		return new Location(0.0, 0.0);
	}

	 @Test
	    public void testLocationCreation() {
	        Location loc = new Location(5, 10);
	        assertEquals(5, loc.getPositionX(), 0.01);
	        assertEquals(10, loc.getPositionY(), 0.01);
	    }

	    @Test
	    public void testSetPosition() {
	        Location loc = new Location(0, 0);
	        loc.setPositionX(3);
	        loc.setPositionY(4);
	        assertEquals(3, loc.getPositionX(), 0.01);
	        assertEquals(4, loc.getPositionY(), 0.01);
	    }
}