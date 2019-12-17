import javapractice.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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

        userManagement.add(addUser);
        userManagement.add(updateUser);
        userManagement.add(deleteUser);

        docManagement.add(uploadDoc);
        docManagement.add(downloadDoc);
        selfManagement.add(updateSelf);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                super.windowClosed(event);
                try {
                    Message message = new Message(">>>LINK_CLOSE", DataProcessing.linkedUser.getName(),null,null,Message.CLOSE);
                    DataProcessing.sendMessage(message);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });

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
        addUser.addActionListener(event->{
            EventQueue.invokeLater(()->{
                UserInfoWindow userInfoWindow=new UserInfoWindow(UserInfoWindow.ADD_USER);
                userInfoWindow.setVisible(true);
            });
        });
        updateUser.addActionListener(event->{
            EventQueue.invokeLater(()->{
                UserInfoWindow userInfoWindow=new UserInfoWindow(UserInfoWindow.UPDATE_USER);
                userInfoWindow.setVisible(true);
            });
        });
        deleteUser.addActionListener(event->{
            EventQueue.invokeLater(()->{
                UserInfoWindow userInfoWindow=new UserInfoWindow(UserInfoWindow.DELETE_USER);
                userInfoWindow.setVisible(true);
            });
        });
    }
}
