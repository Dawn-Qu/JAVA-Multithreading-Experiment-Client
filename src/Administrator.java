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
    public void choose(int choice){

        switch(choice){
            case 1:
                System.out.println("修改成功");
                break;
            case 2:
                System.out.println("删除成功");
                break;
            case 3:
                System.out.println("新增成功");
                break;
            case 4:
                Enumeration e=DataProcessing.getAllUser();
                while(e.hasMoreElements()){
                    System.out.println(e.nextElement());
                }
                break;
            case 5:
                System.out.println("下载成功");
                break;
            case 6:
                System.out.println("文件列表");
                break;
            case 7:
                System.out.println("修改（本人）密码成功");
                break;
            case 8:
                System.exit(0);
                break;
            default:

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
    public void showUserList(){
        Enumeration<User> cursor=DataProcessing.getAllUser();
        while(cursor.hasMoreElements()){
            System.out.println(cursor.nextElement());
        }
    }


}
