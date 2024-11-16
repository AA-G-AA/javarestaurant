package RestaurantSys;


import java.io.*;
import java.util.*;


public class Hotel implements Serializable {
    private static final String SAVE_FILE = "hotel_data.ser";
    private List<Room> rooms;
    private Holder holder;
    private Map<String, List<Room>> roomsByType = new HashMap<>();

    public Hotel() {
        rooms = new ArrayList<>();
        holder = new Holder(); // 初始化 Holder
        initializeRooms();
    }

    private void initializeRooms() {
        int singleroomNumber = 101;
        int doubleroomNumber = 201;

        for (RoomType roomType : holder.getRoomTypes()) {
            int currentnum = 0;
            if (roomType.getType().contains("单人")) {
                currentnum = singleroomNumber;
                singleroomNumber += 10; // 为下一批单人间预留编号
            } else if (roomType.getType().contains("双人")) {
                currentnum = doubleroomNumber;
                doubleroomNumber += 10; // 为下一批双人间预留编号
            }

            List<Room> roomList = new ArrayList<>(); // 创建一个临时列表用于存储当前类型的房间

            for (int i = 0; i < 10; i++) {
                Room room;
                if (roomType.getType().contains("单人")) {
                    room = new SingleRoom(currentnum + i, roomType);
                } else {
                    room = new DoubleRoom(currentnum + i, roomType);
                }
                rooms.add(room);
                roomList.add(room); // 添加到临时列表
            }

            // 更新房间类型的映射
            roomsByType.put(roomType.getType(), roomList);
        }
    }

    public void ShowRoom(){
        System.out.println("房间类型:");
        for (RoomType roomType : holder.getRoomTypes()) {
            System.out.println(roomType);
        }
        ShowRoomDetails();
    }

    private void ShowRoomDetails() {
        boolean ct = true;
        Scanner sc = new Scanner(System.in);
        while (ct) {
            System.out.println("输入你要查看的房间类型号:(1:豪华单人间;2:高级单人间;3:豪华双人间;4:高级双人间)");
            int num;
            // 添加异常处理
            try {
                num = sc.nextInt();
            } catch (Exception e) {
                System.out.println("请输入有效的房间类型号。");
                sc.next(); // 清除错误输入
                continue; // 继续下次循环
            }


            String roomTypeName = "";
            switch (num) {
                case 1:
                    roomTypeName = "豪华单人间";
                    break;
                case 2:
                    roomTypeName = "高级单人间";
                    break;
                case 3:
                    roomTypeName = "豪华双人间";
                    break;
                case 4:
                    roomTypeName = "高级双人间";
                    break;
                default:
                    System.out.println("无效的房间类型号。");
                    continue;
            }

            List<Room> roomlist = roomsByType.get(roomTypeName);
            if (roomlist != null && !roomlist.isEmpty()) {
                    Room room = roomlist.get(0);
                    System.out.println("房间类型: " + room.getType());
                    System.out.println("是否有空调: " + (room.isHasAC() ? "有" : "无"));
                    System.out.println("是否有早餐: " + (room.isHasBreakfast() ? "有" : "无"));
                    System.out.println("每日价格: " + room.getPricePerDay());

                }
            System.out.println("还需要继续查询吗? y/n");
            String s = sc.next();
            if (!s.equalsIgnoreCase("y")) {
                ct = false;
            }
        }
    }

    public void ShowAvailableRoom(){
        System.out.println("空闲房间:");
        for (Room room : rooms) {
            if (!room.isOccupied()){
                System.out.println(room.getRoomNumber()+" "+room.getType());
            }
        }
    }
    public void showBooking(){
        System.out.println("房间类型:");
        int count=1;
        for (RoomType roomType : holder.getRoomTypes()) {
            System.out.println((count++)+" "+roomType);
        }
        Booking();
    }

    public static Hotel loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SAVE_FILE))) {
            //System.out.println("导入数据成功");
            return (Hotel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Hotel();
        }
    }

    public void orderMeal() {
        Scanner sc = new Scanner(System.in);
        boolean ct = true;
        while (ct) {
            System.out.println("输入房间号:");
            int num;
            try {
                num = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException N) {
                System.out.println("请输入正确房间号");
                continue; // 继续下次循环
            } catch (Exception e) {
                System.out.println("发生错误，请重试。");
                continue;
            }
            boolean found = false;
            for (Room room : rooms) {
                if (room.getRoomNumber() == num) {
                    found = true;
                    if (!room.occupied) {
                        System.out.println("此房未预定,请重新输入房间号");
                        break;
                    }
                    if (room.getType().equals("高级单人间") || room.getType().equals("高级双人间")) {
                        System.out.println("此房间无早餐选择");
                        break;
                    }
                    Food.start();
                    Food.getOrder(room); // 调用 getOrder 方法并将订单添加到对应房间的订单列表中

                    System.out.println("点餐完成");
                    System.out.println("还需要继续点餐吗? y/n");

                    String s = sc.next();
                    if (!s.equalsIgnoreCase("y")) {
                        ct = false;
                    }
                    sc.nextLine(); // 清除输入缓冲区
                    break; // 找到预定房间后跳出循环
                }
            }
            if (!found) {
                System.out.println("未找到房间号: " + num);
                break;
            }
        }
    }

    private List<Integer> getRoomNumbersAvailableByType(String roomTypeName) {
        List<Integer> roomNumbers = new ArrayList<>();
        List<Room> roomsOfType = roomsByType.get(roomTypeName);

        if (roomsOfType != null) {
            for (Room room : roomsOfType) {
                if (!room.isOccupied()) {
                    roomNumbers.add(room.getRoomNumber());
                }
            }
        }

        return roomNumbers;
    }

    private void Booking() {
        boolean ct = true;
        while (ct) {
            System.out.println("选择你预定的房间类型:(1:豪华单人间;2:高级单人间;3:豪华双人间;4:高级双人间)");
            Scanner sc = new Scanner(System.in);
            int roomtypenum;
            try {
                roomtypenum = sc.nextInt();
                sc.nextLine(); // 消耗掉换行符
            } catch (Exception e){
                System.out.println("请输入正确的房间类型");
                sc.next(); // 清除错误输入
                continue; // 继续下次循环
            }
            boolean found = false;
            String roomTypeName = "";
            switch (roomtypenum){
                case 1:
                    roomTypeName = "豪华单人间";
                    break;
                case 2:
                    roomTypeName = "高级单人间";
                    break;
                case 3:
                    roomTypeName = "豪华双人间";
                    break;
                case 4:
                    roomTypeName = "高级双人间";
                    break;
                default:
                    System.out.println("无效的房间类型号。");
                    continue;
            }

            System.out.println("空闲房间号:");
            List<Integer> roomNumbersAvailableByType = getRoomNumbersAvailableByType(roomTypeName);
            roomNumbersAvailableByType.stream().forEach(s -> System.out.print(s + " "));

            System.out.println("\n输入房间号");
            int roomnum;
            try{
                roomnum = Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException N){
                System.out.println("请输入正确房间号");
                continue; // 继续下次循环
            }catch (Exception e) {
                System.out.println("发生错误，请重试。");
                continue;
            }
            boolean roomFound = false; // 用于跟踪是否找到房间
            List<Room> rm = roomsByType.get(roomTypeName);
            if (roomTypeName.contains("单人")){
                if (rm != null) {
                    for (Room room : rm) {
                        if (room.getRoomNumber() == roomnum) {
                            roomFound = true;
                            if (!room.isOccupied()) {
                                System.out.println("预订人姓名:");
                                String name = sc.nextLine().trim();
                                System.out.println("预订人电话");
                                String phone = sc.nextLine().trim();
                                System.out.println("预订人性别");
                                String gender = sc.nextLine().trim();
                                Customer cus=new Customer(name,phone,gender);
                                room.addCustomer(cus);
                                room.setOccupied(true);
                                System.out.println("房间 " + roomnum + " 预定成功");
                                saveToFile();
                            } else {
                                System.out.println("房间 " + roomnum + " 已被预定");
                            }
                            break; // 找到后退出循环
                        }
                    }
                }
            }else{
                if (rm != null) {
                    for (Room room : rm) {
                        if (room.getRoomNumber() == roomnum) {
                            roomFound = true;
                            if (!room.isOccupied()) {
                                System.out.println("预订人1姓名:");
                                String name1 = sc.nextLine().trim();
                                System.out.println("预订人1电话:");
                                String phone1 = sc.nextLine().trim();
                                System.out.println("预订人1性别:");
                                String gender1 = sc.nextLine().trim();

                                System.out.println("预订人2姓名:");
                                String name2 = sc.nextLine().trim();
                                System.out.println("预订人2电话:");
                                String phone2 = sc.nextLine().trim();
                                System.out.println("预订人2性别:");
                                String gender2 = sc.nextLine().trim();
                                Customer customer1 = new Customer(name1, phone1, gender1);
                                Customer customer2 = new Customer(name2, phone2, gender2);
                                room.addCustomer(customer1);
                                room.addCustomer(customer2);
                                room.setOccupied(true);
                                System.out.println("房间 " + roomnum + " 预定成功");
                                saveToFile();
                            } else {
                                System.out.println("房间 " + roomnum + " 已被预定");
                            }
                            break; // 找到后退出循环
                        }
                    }
                }
            }


            if (!roomFound) {
                System.out.println("房间 " + roomnum + " 不存在");
            }

            System.out.println("还需要继续预定吗? y/n");
            String s = sc.next();
            if (!s.equalsIgnoreCase("y")) {
                ct = false;
            }
        }
    }

    public void checkout() {
        System.out.println("输入房间号:");
        Scanner sc=new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        for (Room room : rooms) {
            if(room.getRoomNumber()==num){
                if (!room.isOccupied()){
                    System.out.println("此房间未入住");
                    break;
                }
                System.out.println("住户"+room.getCustomers());
                System.out.println("是否退房 y/n");
                String s = sc.next();
                if (s.equalsIgnoreCase("y")){
                    System.out.println("**************");
                    System.out.println("bill");
                    System.out.println("**************");
                    System.out.println();
                    double pricePerDay = room.getPricePerDay();
                    System.out.println("住房消费:"+pricePerDay);
                    System.out.println();
                    System.out.println("**************");
                    System.out.println("用餐消费");
                    List<FoodOrder> orders = room.getFoodOrders();
                    int ordercount=0;
                    if (orders.isEmpty()){
                        System.out.println("无餐饮消费");
                    }else {
                        for (FoodOrder order : orders) {
                            ordercount+=order.getFoodItem().getPrice()*order.getCount();
                            System.out.println(order.getFoodItem().getName()+" "+"\t" +
                                    "x" + order.getCount() + " " + "\t" + (order.getFoodItem().getPrice() * order.getCount()));
                        }
                    }
                    System.out.println("==========================");
                    System.out.println("总消费:"+" "+(ordercount+pricePerDay));
                    System.out.println("--------------------");
                    room.setOccupied(false);
                    saveToFile();
                    System.out.println("退房成功");
                }
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(this);
            //System.out.println("保存成功");
        } catch (IOException e) {
            System.err.println("保存失败: " + e.getMessage());
        }
    }
}


