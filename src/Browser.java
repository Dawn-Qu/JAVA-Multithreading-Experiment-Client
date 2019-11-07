import java.util.Enumeration;

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
        switch(choice){
            case 1:
                System.out.println("下载成功");
                break;
            case 2:
                System.out.println("文件列表");
                break;
            case 3:
                System.out.println("修改成功");
                break;
            case 4:
                System.exit(0);
                break;
            default:

        }
    }
}
