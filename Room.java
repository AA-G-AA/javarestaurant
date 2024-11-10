package RestaurantSys;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract class Room implements Serializable {
    protected int roomNumber;
    protected String type;
    protected boolean hasAC;
    protected boolean hasBreakfast;
    protected double pricePerDay;
    protected boolean occupied;
    protected List<Customer> customers;
    private List<FoodOrder> foodOrders; // 添加订单列表
    

    public Room(int roomNumber,String type, boolean hasAC, boolean hasBreakfast, double pricePerDay) {
        this.roomNumber=roomNumber;
        this.type = type;
        this.hasAC = hasAC;
        this.hasBreakfast = hasBreakfast;
        this.pricePerDay = pricePerDay;
        this.occupied = false;
        this.customers = new ArrayList<>(); // 初始化客户列表
        this.foodOrders = new ArrayList<>(); // 初始化订单列表
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isHasAC() {
        return hasAC;
    }

    public boolean isHasBreakfast() {
        return hasBreakfast;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean isOccupied(){
        return this.occupied;
    }
    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    @Override
    public String toString() {
        String acStatus = hasAC ? "是" : "否";
        String breakfastStatus = hasBreakfast ? "是" : "否";

        return "房间类型 " + roomNumber + " - " + type +
                " (空调: " + acStatus + ", 早餐: " + breakfastStatus +
                ", 价格: " + String.format("%.2f", pricePerDay) + ")";
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHasAC(boolean hasAC) {
        this.hasAC = hasAC;
    }

    public void setHasBreakfast(boolean hasBreakfast) {
        this.hasBreakfast = hasBreakfast;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer); // 添加单个客户
    }


}
class Holder {
    private List<RoomType> roomTypes;

    public Holder() {
        roomTypes = new ArrayList<>();
        initializeRoomTypes();
    }

    private void initializeRoomTypes() {
        roomTypes.add(new RoomType("豪华单人间", true, true, 2500));
        roomTypes.add(new RoomType("高级单人间", true, false, 2000));
        roomTypes.add(new RoomType("豪华双人间", true, true, 4000));
        roomTypes.add(new RoomType("高级双人间", true, false, 3500));
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }


}

class RoomType {
    private String type;
    private boolean hasAC;
    private boolean hasBreakfast;
    private double pricePerDay;

    public RoomType(String type, boolean hasAC, boolean hasBreakfast, double pricePerDay) {
        this.type = type;
        this.hasAC = hasAC;
        this.hasBreakfast = hasBreakfast;
        this.pricePerDay = pricePerDay;
    }

    public String getType() {
        return type;
    }

    public boolean isHasAC() {
        return hasAC;
    }

    public boolean isHasBreakfast() {
        return hasBreakfast;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
    @Override
    public String toString() {
        String acStatus = hasAC ? "是" : "否";
        String breakfastStatus = hasBreakfast ? "是" : "否";

        return "房间类型: " + type ;
    }
}

class SingleRoom extends Room {
    public SingleRoom(int roomNumber, RoomType roomType) {
        super(roomNumber, roomType.getType(), roomType.isHasAC(), roomType.isHasBreakfast(), roomType.getPricePerDay());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(int roomNumber, RoomType roomType) {
        super(roomNumber, roomType.getType(), roomType.isHasAC(), roomType.isHasBreakfast(), roomType.getPricePerDay());
    }
}
