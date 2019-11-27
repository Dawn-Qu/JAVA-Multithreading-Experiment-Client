import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private static int DEFAULT_WIDTH=640;
    private static int DEFAULT_HEIGHT=400;
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
    }
}
