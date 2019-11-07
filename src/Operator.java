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
    public void choose() {

    }

    public boolean uploadFile(String filename){
        System.out.println("上传文件... ...");
        return true;
    }
}
