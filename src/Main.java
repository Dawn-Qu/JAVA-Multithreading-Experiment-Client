import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void showMenuLogIn(){
        System.out.println("****欢迎进入档案系统****");
        System.out.println("1.登陆");
        System.out.println("2.退出");
        System.out.println("**********************");
        System.out.print("请选择菜单：");
    }

    public static User logIn(){
        User user;
        String name;
        String password;
        Scanner scanner=new Scanner(System.in);
        int choice=scanner.nextInt();
        scanner.nextLine();
        try {
            switch (choice) {
                case 1:

                    System.out.print("请输入用户名：");
                    name = scanner.nextLine();
                    System.out.print("请输入密码：");
                    password = scanner.nextLine();
                    user = DataProcessing.searchUser(name, password);
                    if (user == null) {
                        System.out.println("密码错误");
                        break;
                    }

                    while (true) {
                        user.showMenu();
                        choice = scanner.nextInt();
                        user.choose(choice);
                    }

                    //break;
                case 2:
                    System.exit(0);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        /*catch(IOException e){
            System.out.println(e.getMessage());
        }*/
        System.exit(1);
        return null;
    }

    public static void main(String[] args){
        showMenuLogIn();
        User user=logIn();
    }
}
