package pktest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import pkproject.model.AGV;
import pkproject.model.Location;
import pkproject.model.WareHouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AGVTest {

	 private AGV agv;
	    private WareHouse warehouse;
	    private Location initialPosition;

	    @Before
	    public void setUp() {
	        // Tạo vị trí khởi tạo và kho
	        initialPosition = new Location(10.0, 10.0);
	        warehouse = new WareHouse(new Location(0.0, 0.0), new Location(20.0, 20.0));

	        // Tạo đường đi cho kho (giả sử nó có dạng danh sách các tọa độ)
	        List<double[]> path = new ArrayList<>();
	        path.add(new double[]{10.0, 10.0});
	        path.add(new double[]{11.0, 10.0}); // Vị trí mà AGV sẽ di chuyển đến
	        path.add(new double[]{12.0, 10.0}); // Tiếp tục di chuyển
	        warehouse.setPath(path); 

	        // Khởi tạo AGV
	        agv = new AGV(1, initialPosition, 1.0, warehouse);
	    }

	    @After
	    public void tearDown() {
	    }

	    @Test
	    public void testInitialPosition() {
	        assertEquals(10.0, agv.getPosition().getPositionX(), 0.01);
	        assertEquals(10.0, agv.getPosition().getPositionY(), 0.01);
	    }

	    @Test
	    public void testMoveTowardsWarehouse() {
	        agv.move(); // Di chuyển 1 bước
	        assertEquals(11.0, agv.getPosition().getPositionX(), 0.01);
	        assertEquals(10.0, agv.getPosition().getPositionY(), 0.01);
	    }

	    @Test
	    public void testTakePackage() {
	        warehouse.setPackages(1); // Giả sử kho có 1 gói hàng
	        agv.move(); // Di chuyển tới kho
	        agv.move(); // Di chuyển vào kho để lấy gói hàng

	       for(int i =0;i<agv.getTimeAction();i++) {
	    	   agv.takePackage(); // Thực hiện lấy gói hàng
	       }

	        assertEquals(0, warehouse.getPackages()); // Gói hàng trong kho phải giảm còn 0
	        assertEquals(1, agv.getPackages()); // AGV phải có 1 gói hàng
	    }

	    @Test
	    public void testDropPackage() {
	    	int numDeliverPackage = agv.getDeliveryPackages();
	    	warehouse.setPackages(1); // Giả sử kho có 1 gói hàng
	        agv.move(); // Di chuyển tới kho
	        agv.move(); // Di chuyển vào kho để lấy gói hàng

	       for(int i =0;i<agv.getTimeAction();i++) {
	    	   agv.takePackage(); // Thực hiện lấy gói hàng
	       }

	        assertEquals(0, warehouse.getPackages()); // Gói hàng trong kho phải giảm còn 0
	        assertEquals(1, agv.getPackages()); // AGV phải có 1 gói hàng
	        
	        
	        // Di chuyển ra ngoài và thả gói hàng
	        agv.setDirection(-1); // Thay đổi hướng
	        agv.move(); // Di chuyển ra ngoài
	       
		       for(int i =0;i<agv.getTimeAction();i++) {
		    	   agv.dropPackage(); // Thả gói hàng
		       }

	        // Kiểm tra kết quả sau khi thả gói hàng
	        assertEquals(0, warehouse.getPackages()); // Kho vẫn phải không còn gói hàng
	        assertEquals(0, agv.getPackages()); // AGV không còn gói hàng
	        assertEquals(numDeliverPackage+1, agv.getDeliveryPackages()); 
	    }
	    @Test
	    public void testTakeAndDropPackages() {
	    	 warehouse.setPackages(1);

		     for(int i =0;i<agv.getTimeAction();i++) {
		    	 agv.takePackage(); // Thực hiện lấy gói hàng
		      }

	         assertEquals(1, agv.getPackages()); // AGV should now have 1 package

	         for(int i =0;i<agv.getTimeAction();i++) {
		    	   agv.dropPackage(); // Thả gói hàng
		       }
	         assertEquals(0, agv.getPackages()); // AGV should now have 0 packages
	    }

	    @Test
	    public void testReverseDirection() {
	        agv.move(); // Di chuyển tới kho
	        agv.move(); // Di chuyển vào kho
	        agv.setDirection(-1); // Đổi hướng
	        agv.move(); // Di chuyển ra ngoài
	        assertEquals(10.0, agv.getPosition().getPositionX(), 0.01); // Về vị trí ban đầu
	        assertEquals(10.0, agv.getPosition().getPositionY(), 0.01);
	    }
}
