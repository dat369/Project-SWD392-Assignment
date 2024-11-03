package pktest;

import static org.junit.Assert.assertEquals;

import pkproject.model.Location;
import pkproject.model.WareHouse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WareHouseTest {
    private WareHouse warehouse;

    @Before
    public void setUp() throws Exception {
        warehouse = new WareHouse(new Location(0, 0), new Location(5, 5));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInitialPackages() {
        assertEquals(0, warehouse.getPackages());
    } 
    
    @Test
    public void testGeneratePackage() throws InterruptedException {
        // Kiểm tra số lượng gói hàng ban đầu
        assertEquals(0, warehouse.getPackages());
        System.out.println("Số lượng gói hàng ban đầu: " + warehouse.getPackages());
        
        for(int i = 0;i<warehouse.getSpawnRate();i++) {
        	warehouse.generatePackage();
        }
        
        System.out.println("Kiểm tra thành công: Số lượng gói hàng hiện tại: " + warehouse.getPackages());
        assertEquals(1, warehouse.getPackages()); 
    }



    @Test
    public void testPathGeneration() {
        assertEquals(warehouse.getPath().size(), warehouse.getBackPath().size());
    }

    @Test
    public void testSetPackages() {
        warehouse.setPackages(5);
        assertEquals(5, warehouse.getPackages());
    }
}
