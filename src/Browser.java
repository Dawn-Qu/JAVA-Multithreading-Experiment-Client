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
}
