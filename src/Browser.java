import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Browser extends User {
    Browser(String name,String password,String role){
        super(name,password,role);
    }

    @Override
    public void showMenu() {
        System.out.println("****欢迎进入档案浏览员菜单****");
        System.out.println("1.下载文件");
        System.out.println("2.文件列表");
        System.out.println("3.修改密码");
        System.out.println("4.退  出");
        System.out.println("**********************");
        System.out.print("请选择菜单：");
    }

    @Override
    public void choose(int choice) {
        try {
            Scanner scanner=new Scanner(System.in);
            switch (choice) {
                case 1: {
                    showFileList();
                    System.out.print("请输入文件名：");
                    downloadFile(scanner.nextLine());
                    System.out.println("下载成功");
                    break;
                }
                case 2: {
                    showFileList();
                    break;
                }
                case 3: {
                    System.out.print("请输入新密码：");
                    DataProcessing.update(getName(),scanner.nextLine(),getRole());
                    System.out.println("修改成功");
                    break;
                }
                case 4:
                    System.exit(0);
                    break;
                default:

            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
