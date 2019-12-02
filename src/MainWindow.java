import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static final int DEFAULT_WIDTH=1024;
    private static final int DEFAULT_HEIGHT=768;
    private JMenuBar menubar;
    private JMenu userManagement;
    private JMenu docManagement;
    private JMenu selfManagement;
    private JMenuItem updateUser;
    private JMenuItem deleteUser;
    private JMenuItem addUser;
    private JMenuItem uploadDoc;
    private JMenuItem downloadDoc;
    private JMenuItem updateSelf;

    public MainWindow(User user){
        menubar=new JMenuBar();

        userManagement=new JMenu("用户管理");
        docManagement=new JMenu("档案管理");
        selfManagement=new JMenu("个人信息管理");

        updateUser=new JMenuItem("修改用户");
        deleteUser=new JMenuItem("删除用户");
        addUser=new JMenuItem("新增用户");
        uploadDoc=new JMenuItem("上传文件");
        downloadDoc=new JMenuItem("下载文件");
        updateSelf=new JMenuItem("信息修改");

        menubar.add(userManagement);
        menubar.add(docManagement);
        menubar.add(selfManagement);

        userManagement.add(updateUser);
        userManagement.add(deleteUser);
        userManagement.add(addUser);
        docManagement.add(uploadDoc);
        docManagement.add(downloadDoc);
        selfManagement.add(updateSelf);

        setJMenuBar(menubar);
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        if(user.getRole().equals("browser")){
            userManagement.setEnabled(false);
            uploadDoc.setEnabled(false);
        }
        else if(user.getRole().equals("operator")){
            userManagement.setEnabled(false);
        }

        downloadDoc.addActionListener(event->{
            EventQueue.invokeLater(()->{
                FileWindow fileWindow=new FileWindow(FileWindow.DOWNLOAD,user);
                fileWindow.setVisible(true);
            });

        });
        uploadDoc.addActionListener(event->{
            EventQueue.invokeLater(()->{
                FileWindow fileWindow=new FileWindow(FileWindow.UPLOAD,user);
                fileWindow.setVisible(true);
            });

        });
        updateSelf.addActionListener(event->{
            EventQueue.invokeLater(()->{
                SelfInfoWindow selfInfoWindow=new SelfInfoWindow(user);
                selfInfoWindow.setVisible(true);
            });

        });
    }
}
