import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Administrator extends User {
    Administrator(String name,String password,String role){
        super(name,password,role);

    }
    @Override
    public void showMenu() {
        System.out.println("****欢迎进入系统管理员菜单****");
        System.out.println("1.修改用户");
        System.out.println("2.删除用户");
        System.out.println("3.新增用户");
        System.out.println("4.列出用户");
        System.out.println("5.下载文件");
        System.out.println("6.文件列表");
        System.out.println("7.修改（本人）密码");
        System.out.println("8.退  出");
        System.out.println("**********************");
        System.out.print("请选择菜单：");
    }
    @Override
    public void choose(int choice){
        Scanner scanner=new Scanner(System.in);
        try {
            switch (choice) {
                case 1: {
                    String name, password;
                    System.out.print("请输入用户名:");
                    name = scanner.nextLine();
                    System.out.print("请输入密码:");
                    password = scanner.nextLine();
                    DataProcessing.updateUser(name,password,DataProcessing.searchUser(name).getRole());
                    break;
                }
                case 2: {
                    System.out.print("请输入用户名：");
                    deleteOtherUser(DataProcessing.searchUser(scanner.nextLine()));
                    break;
                }
                case 3: {
                    String name, password, role;
                    System.out.print("请输入用户名:");
                    name = scanner.nextLine();
                    System.out.print("请输入密码:");
                    password = scanner.nextLine();
                    System.out.print("请输入角色:");
                    role = scanner.nextLine();
                    DataProcessing.insertUser(name, password, role);
                    break;
                }
                case 4: {
                    Enumeration e = DataProcessing.getAllUser();
                    while (e.hasMoreElements()) {
                        System.out.println(e.nextElement());
                    }
                    break;
                }
                case 5: {
                    showFileList();
                    System.out.print("请输入文件名：");
                    downloadFile(scanner.nextLine());
                    System.out.println("下载成功");
                    break;
                }
                case 6: {
                    showUserList();
                    break;
                }
                case 7: {
                    System.out.print("请输入新密码：");
                    DataProcessing.updateUser(getName(),scanner.nextLine(),getRole());
                    System.out.println("修改成功!");
                    break;
                }
                case 8:
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
    public boolean changeOtherUserInfo(User user){
        System.out.println("修改成功");
        return true;
    }
    public boolean deleteOtherUser(User user){
        System.out.println("删除成功");
        return true;
    }
    public boolean addNewUser(User user){
        System.out.println("新增用户成功");
        return true;
    }
    public void showUserList() {
        try {
            Enumeration<User> cursor = DataProcessing.getAllUser();
            while (cursor.hasMoreElements()) {
                System.out.println(cursor.nextElement());
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



}
