import javapractice.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private static final int DEFAULT_WIDTH=640;
    private static final int DEFAULT_HEIGHT=400;
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JTextField editUserName;
    private JPasswordField editPassword;
    private JButton buttonApply;
    private JButton buttonCancel;

    public LoginWindow(){
        setLayout(null);

        labelUserName=new JLabel("用户名：");
        labelUserName.setBounds(80,75,200,25);
        labelUserName.setFont(new Font("Dialog",Font.PLAIN,16));

        labelPassword=new JLabel("密码：");
        labelPassword.setBounds(80,150,200,25);
        labelPassword.setFont(new Font("Dialog",Font.PLAIN,16));

        editUserName=new JTextField();
        editUserName.setBounds(320,75,200,25);
        editUserName.setFont(new Font("Dialog",Font.PLAIN,16));

        editPassword=new JPasswordField();
        editPassword.setBounds(320,150,200,25);
        editPassword.setFont(new Font("Dialog",Font.PLAIN,16));

        buttonApply=new JButton("确定");
        buttonApply.setBounds(100,300,120,25);
        buttonApply.setFont(new Font("Dialog",Font.PLAIN,16));

        buttonCancel=new JButton("取消");
        buttonCancel.setBounds(420,300,120,25);
        buttonCancel.setFont(new Font("Dialog",Font.PLAIN,16));

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        setTitle("系统登录");
        add(labelUserName);
        add(labelPassword);
        add(editUserName);
        add(editPassword);
        add(buttonApply);
        add(buttonCancel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                super.windowClosed(event);
            }
        });

        buttonApply.addActionListener(event->{
            try {
                User user=DataProcessing.searchUser(editUserName.getText(), new String(editPassword.getPassword()));
                if(user==null){//密码错误
                    JOptionPane.showMessageDialog(this,"用户名或密码错误","用户名或密码错误",JOptionPane.ERROR_MESSAGE);
                }
                else{//密码正确
                    //进入主界面
                    DataProcessing.linkServer(editUserName.getText());
                    JOptionPane.showMessageDialog(this,"登陆成功");
                    setVisible(false);
                    EventQueue.invokeLater(()->{
                        MainWindow mainWindow=new MainWindow(user);
                        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainWindow.setVisible(true);
                    });
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage(),"数据库异常",JOptionPane.ERROR_MESSAGE);
            }catch(IOException e){
                JOptionPane.showMessageDialog(this,e.getMessage(),"输入输出异常",JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonCancel.addActionListener(event->{
            dispose();
        });
    }
}
