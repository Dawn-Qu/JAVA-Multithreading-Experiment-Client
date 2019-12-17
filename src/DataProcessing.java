import javapractice.Message;

import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;

public class DataProcessing {

    private static boolean connectToDB = false;
    static Connection connection;
    static String USERS_FILE = "users";
    static String DOCS_FILE = "docs";
    static String SERVER_FILE_DIRECTORY = "E:\\JAVA practice\\ServerFiles\\";
    static String DIRECTORY_TO_UPLOAD = "E:\\JAVA practice\\FilesToUpload\\";
    static String DOWNLOAD_DIRECTORY = "E:\\JAVA practice\\Download\\";
    static String driverName = "com.mysql.cj.jdbc.Driver";               // 加载数据库驱动类
    static String URL = "jdbc:mysql://111.229.228.57:3306/document?serverTimezone=GMT%2B8";       // 声明数据库的URL
//    static String URL = "jdbc:mysql://127.0.0.1:3306/document?serverTimezone=GMT%2B8";
    static String USER = "root";                                      // 数据库用户
    static String PASSWORD = "root";
    static User linkedUser;
    public static final int SERVER_PORT=12345;
    public static final String LOCAL_HOST="127.0.0.1";
    public static final String SERVER_HOST="111.229.228.57";
    static Socket socket;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    static Hashtable<String, User> users;
    static Hashtable<String, Doc> docs;

    static {
        users = new Hashtable<String, User>();
//		users.put("jack", new Operator("jack","123","operator"));
//		users.put("rose", new Browser("rose","123","browser"));
//		users.put("kate", new Administrator("kate","123","administrator"));

//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        docs = new Hashtable<String, Doc>();
//		docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));

        //Init();
        try {
            initFromDb();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public static void initFromDb() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);   // 建立数据库连接
        refreshDataFromDb();
    }

    public static void linkServer(String username)throws IOException,SQLException {
        socket=new Socket(SERVER_HOST,SERVER_PORT);
        oos=new ObjectOutputStream(socket.getOutputStream());
        ois =new ObjectInputStream(socket.getInputStream());
        linkedUser =searchUser(username);
        Message message=new Message(">>>USER_LINK",username);
        sendMessage(message);
    }

    public static void refreshDataFromDb() throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM user_info";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            String pwd = resultSet.getString("password");
            String role = resultSet.getString("role");
            User user;
            if (role.equalsIgnoreCase("administrator")) {
                user = new Administrator(username, pwd, role);
            } else if (role.equalsIgnoreCase("browser")) {
                user = new Browser(username, pwd, role);
            } else {
                user = new Operator(username, pwd, role);
            }
            users.put(username, user);
        }
        sql = "SELECT * FROM doc_info";
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String id = resultSet.getString("Id");
            String creator = resultSet.getString("creator");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            String description = resultSet.getString("description");
            String filename = resultSet.getString("filename");
            Doc doc;
            doc = new Doc(id, creator, timestamp, description, filename);
            docs.put(id, doc);
        }
    }

    public static void Init() {
        // connect to database
        try {
            ObjectInputStream usersIn = new ObjectInputStream(new FileInputStream(USERS_FILE));
            ObjectInputStream docsIn = new ObjectInputStream(new FileInputStream(DOCS_FILE));
            users = (Hashtable<String, User>) usersIn.readObject();
            docs = (Hashtable<String, Doc>) docsIn.readObject();
            usersIn.close();
            docsIn.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // update database connection status
//		if (Math.random()>0.2)
//			connectToDB = true;
//		else
//			connectToDB = false;
    }

    public static void save() {

        try {
            ObjectOutputStream usersOut = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
            ObjectOutputStream docsOut = new ObjectOutputStream(new FileOutputStream(DOCS_FILE));
            usersOut.writeObject(users);
            docsOut.writeObject(docs);
            usersOut.close();
            docsOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Doc searchDoc(String ID) throws SQLException {
        if (docs.containsKey(ID)) {
            Doc temp = docs.get(ID);
            return temp;
        }
        return null;
    }

    public static Enumeration<Doc> getAllDocs() throws SQLException {
        Enumeration<Doc> e = docs.elements();
        return e;
    }

    public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException {
        Doc doc;

        if (docs.containsKey(ID))
            return false;
        else {
            doc = new Doc(ID, creator, timestamp, description, filename);
            docs.put(ID, doc);
//            save();
            String sql="INSERT INTO doc_info (Id,creator,timestamp,description,filename) VALUES (?,?,?,?,?);";
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,ID);
            pstmt.setString(2,creator);
            pstmt.setTimestamp(3,timestamp);
            pstmt.setString(4,description);
            pstmt.setString(5,filename);
            pstmt.executeUpdate();
            return true;
        }
    }

    public static User searchUser(String name) throws SQLException {
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
            User temp = users.get(name);
            if ((temp.getPassword()).equals(password))
                return temp;
        }
        return null;
    }

    public static Enumeration<User> getAllUser() throws SQLException {
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Query" );

        Enumeration<User> e = users.elements();
        return e;
    }


    public static boolean updateUser(String name, String password, String role) throws SQLException,IOException {
        User user;
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Update" );

        if (users.containsKey(name)) {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name, password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name, password, role);
            else
                user = new Browser(name, password, role);
            users.put(name, user);
//            save();
            String sql="UPDATE user_info SET password=?,role=? WHERE username=?;";
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,password);
            pstmt.setString(2,role);
            pstmt.setString(3,name);
            pstmt.executeUpdate();
            Message message=new Message(">>>UPDATE_USER",user.getName());
//            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(message);
//            oos.flush();
//            oos.close();
            sendMessage(message);
            return true;
        } else
            return false;
    }

    public static boolean insertUser(String name, String password, String role) throws SQLException,IOException {
        User user;

//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Insert" );

        if (users.containsKey(name))
            return false;
        else {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name, password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name, password, role);
            else
                user = new Browser(name, password, role);
            users.put(name, user);
//            save();
            String sql="INSERT INTO user_info (username,password,role) VALUES (?,?,?);";
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,password);
            pstmt.setString(3,role);
            pstmt.executeUpdate();
            Message message=new Message(">>>ADD_USER",linkedUser.getName());
//            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(message);
//            oos.flush();
//            oos.close();
            sendMessage(message);
            return true;
        }
    }

    public static boolean deleteUser(String name) throws SQLException,IOException {
//		if ( !connectToDB )
//	        throw new SQLException( "Not Connected to Database" );
//
//		double ranValue=Math.random();
//		if (ranValue>0.5)
//			throw new SQLException( "Error in excecuting Delete" );

        if (users.containsKey(name)) {
            users.remove(name);
//            save();
            PreparedStatement pstmt=connection.prepareStatement("DELETE FROM user_info WHERE username=?;");
            pstmt.setString(1,name);
            pstmt.executeUpdate();
            Message message=new Message(">>>DELETE_USER", linkedUser.getName());
//            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(message);
//            oos.flush();
//            oos.close();
            sendMessage(message);
            return true;
        } else
            return false;

    }

    public static void disconnectFromDB() {
        if (connectToDB) {
            // close Statement and Connection
            try {
                connection.close();
			}catch ( SQLException sqlException ){
			    sqlException.printStackTrace();
            } finally {
                connectToDB = false;
            }
        }
    }

    public static void uploadFile(String filename, String creator) throws IOException,SQLException {
        File file = new File(DIRECTORY_TO_UPLOAD + filename);
//        File destiny = new File(SERVER_FILE_DIRECTORY + filename);
//        Files.copy(file.toPath(), destiny.toPath());

        FileInputStream fis=new FileInputStream(file);
        long length=file.length();
        byte[] bytes=new byte[(int)length];
        fis.read(bytes);
        Message message=new Message(">>>UPLOAD_FILE",creator,file.getName(),bytes,Message.UPLOAD);
//        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject(message);
//        oos.flush();
//        oos.close();
        sendMessage(message);
        fis.close();
        insertDoc("0000", creator, new Timestamp(System.currentTimeMillis()), null, filename);
        //save();
    }

    public static void uploadFile(String fileID, String description, File file, String creator) throws IOException,SQLException {
//        File destiny = new File(SERVER_FILE_DIRECTORY + file.getName());
//        Files.copy(file.toPath(), destiny.toPath());
        FileInputStream fis=new FileInputStream(file);
        long length=file.length();
        byte[] bytes=new byte[(int)length];
        fis.read(bytes);
        Message message=new Message(">>>UPLOAD_FILE",creator,file.getName(),bytes,Message.UPLOAD);
//        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject(message);
//        oos.flush();
//        oos.close();
        sendMessage(message);
        fis.close();
        //docs.put(file.getName(), new Doc(fileID, creator, new Timestamp(System.currentTimeMillis()), description, file.getName()));
        insertDoc(fileID,creator,new Timestamp(System.currentTimeMillis()),description,file.getName());
        //save();
    }

    public static Message downloadFile(String filename) throws IOException,ClassNotFoundException {
        //File file = new File(SERVER_FILE_DIRECTORY + filename);
        File downloadFile = new File(DOWNLOAD_DIRECTORY + filename);
        //Files.copy(file.toPath(), destiny.toPath());
        Message request=new Message(">>>DOWNLOAD_FILE", linkedUser.getName(),filename,null,Message.DOWNLOAD);
        sendMessage(request);
        Message message;
        message=(Message) ois.readObject();
        byte[] data=message.getFiledata();
        if(!downloadFile.exists()){
            boolean isCreatedSuccess= downloadFile.createNewFile();
            if(!isCreatedSuccess){
                throw new IOException("File creating fail");
            }
        }
        FileOutputStream fos=new FileOutputStream(downloadFile);
        fos.write(data);
        fos.close();
        ois.close();
        return message;
    }

    public static Message downloadFile(String filename, File downloadFile) throws IOException,ClassNotFoundException{
//        File file = new File(SERVER_FILE_DIRECTORY + filename);
//        Files.copy(file.toPath(), downloadDir.toPath());
        Message request=new Message(">>>DOWNLOAD_FILE", linkedUser.getName(),filename,null,Message.DOWNLOAD);
        sendMessage(request);
        Message message;
        message=(Message) ois.readObject();
        byte[] data=message.getFiledata();

        if(!downloadFile.exists()){
            boolean isCreatedSuccess= downloadFile.createNewFile();
            if(!isCreatedSuccess){
                throw new IOException("File creating fail");
            }
        }
        FileOutputStream fos=new FileOutputStream(downloadFile);
        fos.write(data);
        fos.close();
        ois.close();
        return message;
    }

    public static void sendMessage(Message message)throws IOException{
        //ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        oos.flush();
        //oos.close();
    }
}
