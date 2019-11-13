import java.io.IOException;
import java.sql.SQLException;

public abstract class User {
	private String name;
	private String password;
	private String role;
	
	User(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}

	public abstract void choose(int choice);

	public boolean changeSelfInfo(String password) throws SQLException {
		//写用户信息到存储
		if (DataProcessing.update(name, password, role)){
			this.password=password;
			System.out.println("修改成功");
			return true;
		}else
			return false;
	}
	
	public abstract void showMenu();

	public boolean downloadFile(String filename) throws IOException{
		double ranValue=Math.random();
		if (ranValue>0.5)
			throw new IOException( "Error in accessing file" );
		System.out.println("下载文件... ...");
		return true;
	}

	public void showFileList() throws SQLException{
		double ranValue=Math.random();
		if (ranValue>0.5)
			throw new SQLException( "Error in accessing file DB" );
		System.out.println("列表... ...");
	}


	public void exitSystem(){
		System.out.println("系统退出, 谢谢使用 ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String toString(){
		return name+" "+role;
	}

}
