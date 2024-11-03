package pkproject.controller;

import java.util.ArrayList;
import java.util.List;

import pkproject.model.AGV;
import pkproject.model.Location;
import pkproject.model.MapObject;
import pkproject.model.WareHouse;
import pkproject.view.MapView;

public class AGVController {
    private List<AGV> agvList;
    private List<WareHouse> warehouse;
    private MapView mapView;
    private Location home;

    public AGVController(List<WareHouse> warehouse, List<AGV> agvList, MapView mapView, Location home) {
        this.agvList = agvList;
        this.warehouse = warehouse;
        this.mapView = mapView;
        this.home = home;
        mapView.setAGVList(agvList);
    }

    public AGVController() {
        // Constructor rỗng
    }

    public List<AGV> getAgvList() {
        return agvList;
    }

    public void setAgvList(List<AGV> agvList) {
        this.agvList = agvList;
    }

    public void run() {
        for (AGV agv : agvList) {
            agv.move();
        }
        for (WareHouse ware : warehouse) {
            ware.generatePackage();
        }
        draw();
    }

    public void draw() {
        List<MapObject> updatedObjects = new ArrayList<>();

        // Tính tổng số gói hàng tại Delivery location từ tất cả các AGV
        int totalDeliveryPackages = 0;
        for (AGV agv : agvList) {
            totalDeliveryPackages += agv.getDeliveryPackages(); // Lấy số gói hàng từ AGV
        }

        // Tính số lượng AGV
        int totalAGVs = agvList.size();

        // Thêm vị trí delivery vào danh sách các đối tượng
        updatedObjects.add(new MapObject("Delivery location: "+ totalDeliveryPackages, home));

        // Thêm AGVs vào danh sách các đối tượng, bao gồm cả số gói hàng mà mỗi AGV đang cầm theo
        for (AGV agv : agvList) {
            int packagesInAGV = agv.getPackages();
            updatedObjects.add(new MapObject("AGV " + agv.getId() + " (" + packagesInAGV + ")", agv.getPosition()));
        }

        // Thêm Warehouses vào danh sách các đối tượng
        for (WareHouse ware : warehouse) {
            if (ware.getPackages() >= 0) {
                updatedObjects.add(new MapObject("Receiving location: " + ware.getPackages(), ware.getWareHouse()));
            }
        }

        // Cập nhật các đối tượng trong MapView và vẽ lại
        mapView.updateObjects(updatedObjects);
    }



}
