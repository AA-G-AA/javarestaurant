package RestaurantSys;
import java.util.Scanner;

public class Main {
    private static Hotel hotel;
    public static void main(String[] args) {
        hotel = Hotel.loadFromFile();

        while(true){
            Menu.Mainshow();
            Scanner sc=new Scanner(System.in);
            int num=Integer.parseInt(sc.nextLine());
            switch (num) {
                case 1:
                    // 显示所有房间
                    hotel.ShowRoom();
                    break;
                case 2:
                    // 显示可用房间
                    hotel.ShowAvailableRoom();
                    break;
                case 3:
                    // 预订房间
                    hotel.showBooking();
                    break;
                case 4:
                    // 点餐服务
                    hotel.orderMeal();
                    break;
                case 5:
                    // 退房结算
                    hotel.checkout();
                    break;
                case 6:
                    System.out.println("退出");
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }
}

