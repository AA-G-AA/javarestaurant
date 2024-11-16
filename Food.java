package RestaurantSys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Food implements Serializable {
    private static Map<Integer, FoodItem> menu = new HashMap<>();

    static {
        // 初始化菜单
        menu.put(1, new FoodItem("面条", 10));
        menu.put(2, new FoodItem("米饭", 2));
        menu.put(3, new FoodItem("宫保鸡丁", 38));
        menu.put(4, new FoodItem("鱼香肉丝", 35));
        menu.put(5, new FoodItem("麻婆豆腐", 28));
        menu.put(6, new FoodItem("红烧肉", 45));
        menu.put(7, new FoodItem("清蒸鲈鱼", 60));

        menu.put(8, new FoodItem("牛排", 120));
        menu.put(9, new FoodItem("意大利面", 80));
        menu.put(10, new FoodItem("披萨", 90));
        menu.put(11, new FoodItem("沙拉", 50));
        menu.put(12, new FoodItem("汉堡", 60));
    }

    protected static void start(){
        System.out.println("========================");
        System.out.println("菜单");
        System.out.println("========================");
        for (Map.Entry<Integer, FoodItem> entry : menu.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }
    protected static void getOrder(Room room) {
        Scanner sc=new Scanner(System.in);
        boolean ct=true;
        List<FoodOrder> fo = room.getFoodOrders(); // 获取房间的订单列表

        while(ct) {
            System.out.println("请输入菜品编号:");
            int ordernum = Integer.parseInt(sc.nextLine());
            FoodItem fi = menu.get(ordernum);
            if (fi!=null){
                System.out.println("请输入菜品数量");
                int countnum = Integer.parseInt(sc.nextLine());
                if (countnum>=1){
                    FoodOrder f1=new FoodOrder(fi,countnum);
                    fo.add(f1);
                    System.out.println("你点的是"+f1);
                }else {
                    System.out.println("数量有误,请重新输入");
                    continue;
                }
            }else {
                System.out.println("没有这个菜,请重新输入");
                continue;
            }

            System.out.println("还需要加点其他吗? y/n");
            String s = sc.next();
            if (!s.equalsIgnoreCase("y")) {
                System.out.println("所有订单:");

                for (FoodOrder foodOrder : fo) {
                    System.out.println(foodOrder);
                }
                ct = false;
            }
            sc.nextLine(); // 清除输入缓冲区
        }
    }
}

class FoodItem implements Serializable {

    private String name;
    private int price;


    public FoodItem() {
    }

    public FoodItem(String name, int price) {

        this.name = name;
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name+" "+"\t"+"价格:  "+price+" ¥";
    }
}

class FoodOrder implements Serializable {
    private FoodItem foodItem;
    private int count;

    public FoodOrder() {
    }

    public FoodOrder(FoodItem foodItem, int count) {
        this.foodItem = foodItem;
        this.count = count;
    }

    /**
     * 获取
     * @return foodItem
     */
    public FoodItem getFoodItem() {
        return foodItem;
    }

    /**
     * 设置
     * @param foodItem
     */
    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    /**
     * 获取
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return foodItem.getName() +" x " + count ;
    }
}