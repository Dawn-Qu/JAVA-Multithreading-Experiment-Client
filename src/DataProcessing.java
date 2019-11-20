import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;

public class DataProcessing {

	private static boolean connectToDB=false;
	static String USERS_FILE="users";
	static String DOCS_FILE="docs";
	static String SERVER_FILE_DIRECTORY="E:\\JAVA practice\\ServerFiles\\";
	static String DIRECTORY_TO_UPLOAD="E:\\JAVA practice\\FilesToUpload\\";
	static String DOWNLOAD_DIRECTORY="E:\\JAVA practice\\Download\\";

	static Hashtable<String, User> users;
	static Hashtable<String, Doc> docs;

	static {
		users = new Hashtable<String, User>();
//		users.put("jack", new Operator("jack","123","operator"));
//		users.put("rose", new Browser("rose","123","browser"));
//		users.put("kate", new Administrator("kate","123","administrator"));

//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		docs = new Hashtable<String,Doc>();
//		docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));

		Init();

	}



	public static  void Init(){
		// connect to database
		try {
			ObjectInputStream usersIn = new ObjectInputStream(new FileInputStream(USERS_FILE));
			ObjectInputStream docsIn=new ObjectInputStream(new FileInputStream(DOCS_FILE));
			users=(Hashtable<String, User>) usersIn.readObject();
			docs=(Hashtable<String, Doc>) docsIn.readObject();
			usersIn.close();
			docsIn.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}
		// update database connection status
//		if (Math.random()>0.2)
//			connectToDB = true;
//		else
//			connectToDB = false;
	}

	public static void save(){

		try {
			ObjectOutputStream usersOut=new ObjectOutputStream(new FileOutputStream(USERS_FILE));
			ObjectOutputStream docsOut=new ObjectOutputStream(new FileOutputStream(DOCS_FILE));
			usersOut.writeObject(users);
			docsOut.writeObject(docs);
			usersOut.close();
			docsOut.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	public static Doc searchDoc(String ID) throws SQLException {
		if (docs.containsKey(ID)) {
			Doc temp =docs.get(ID);
			return temp;
		}
		return null;
	}

	public static Enumeration<Doc> getAllDocs() throws SQLException{
		Enumeration<Doc> e  = docs.elements();
		return e;
	}

	public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
		Doc doc;

		if (docs.containsKey(ID))
			return false;
		else{
			doc = new Doc(ID,creator,timestamp,description,filename);
			docs.put(ID, doc);
			save();
			return true;
		}
	}

	public static User searchUser(String name) throws SQLException{
//		if ( !connectToDB )
//			throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		if (users.containsKey(name)) {
			return users.get(name);
		}
		return null;
	}

	public static User searchUser(String name, String password) throws SQLException {
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		if (users.containsKey(name)) {
			User temp =users.get(name);
			if ((temp.getPassword()).equals(password))
				return temp;
		}
		return null;
	}

	public static Enumeration<User> getAllUser() throws SQLException{
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

		Enumeration<User> e  = users.elements();
		return e;
	}



	public static boolean updateUser(String name, String password, String role) throws SQLException{
		User user;
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Update" );

		if (users.containsKey(name)) {
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name,password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name,password, role);
			else
				user = new Browser(name,password, role);
			users.put(name, user);
			save();
			return true;
		}else
			return false;
	}

	public static boolean insertUser(String name, String password, String role) throws SQLException{
		User user;

//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Insert" );

		if (users.containsKey(name))
			return false;
		else{
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name,password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name,password, role);
			else
				user = new Browser(name,password, role);
			users.put(name, user);
			save();
			return true;
		}
	}

	public static boolean deleteUser(String name) throws SQLException{
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Delete" );

		if (users.containsKey(name)){
			users.remove(name);
			save();
			return true;
		}else
			return false;

	}

	public static void disconnectFromDB() {
		if ( connectToDB ){
			// close Statement and Connection
			try{
//				if (Math.random()>0.5)
//					throw new SQLException( "Error in disconnecting DB" );
//			}catch ( SQLException sqlException ){
//			    sqlException.printStackTrace();
			}finally{
				connectToDB = false;
			}
		}
   }

   public static void uploadFile(String filename,String creator)throws IOException {
	   File file = new File(DIRECTORY_TO_UPLOAD + filename);
	   File destiny = new File(SERVER_FILE_DIRECTORY + filename);
	   Files.copy(file.toPath(), destiny.toPath());
	   docs.put(file.getName(), new Doc("0000", creator, new Timestamp(System.currentTimeMillis()), null, filename));
	   save();
   }
   public static void downloadFile(String filename)throws IOException{
		File file=new File(SERVER_FILE_DIRECTORY+filename);
		File destiny=new File(DOWNLOAD_DIRECTORY+filename);
		Files.copy(file.toPath(),destiny.toPath());
   }
}
