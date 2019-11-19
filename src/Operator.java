import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Operator extends User{
    Operator(String name,String password,String role){
        super(name,password,role);
    }
    @Override
    public void showMenu() {
        System.out.println("****欢迎进入档案录入员菜单****");
        System.out.println("1.上传文件");
        System.out.println("2.下载文件");
        System.out.println("3.文件列表");
        System.out.println("4.修改密码");
        System.out.println("5.退  出");
        System.out.println("**********************");
        System.out.print("请选择菜单：");
    }

    @Override
    public void choose(int choice) {
        try {
            Scanner scanner=new Scanner(System.in);
            switch (choice) {
                case 1: {
                    System.out.print("请输入文件名：");
                    uploadFile(scanner.nextLine());
                    break;
                }
                case 2: {
                    showFileList();
                    System.out.print("请输入文件名：");
                    downloadFile(scanner.nextLine());
                    break;
                }
                case 3: {
                    showFileList();
                    break;
                }
                case 4: {
                    System.out.print("请输入新密码：");
                    DataProcessing.updateUser(getName(),scanner.nextLine(),getRole());
                    System.out.println("修改成功");
                    break;
                }
                case 5:
                    System.exit(0);
                    break;
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean uploadFile(String filename){
        System.out.println("上传文件... ...");
        return true;
    }
}
