import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class SelfInfoWindow extends JFrame{
    private JLabel name;
    private JLabel currentPassword;
    private JLabel newPassword;
    private JLabel certainNewPassword;
    private JLabel role;
    private JTextField editName;
    private JPasswordField editCurrentPassword;
    private JPasswordField editNewPassword;
    private JPasswordField editCertainNewPassword;
    private JTextField editRole;
    private JButton buttonUpdate;
    private JButton buttonCancel;
    private User m_user;

    public SelfInfoWindow(User user){
        m_user=user;

        name=new JLabel("用户名");
        currentPassword=new JLabel("原口令");
        newPassword=new JLabel("新口令");
        certainNewPassword=new JLabel("确认新口令");
        role=new JLabel("角色");
        editName=new JTextField();
        editCurrentPassword=new JPasswordField();
        editNewPassword=new JPasswordField();
        editCertainNewPassword=new JPasswordField();
        editRole=new JTextField();
        buttonUpdate=new JButton("修改");
        buttonCancel=new JButton("取消");

        setLayout(null);

        setSize(640,480);

        name.setBounds(50,50, 100,25);
        currentPassword.setBounds(50,100, 100,25);
        newPassword.setBounds(50,150, 100,25);
        certainNewPassword.setBounds(50,200, 100,25);
        role.setBounds(50,250, 100,25);

        editName.setBounds(200,50, 200,25 );
        editCurrentPassword.setBounds(200,100,200,25);
        editNewPassword.setBounds(200,150,200,25);
        editCertainNewPassword.setBounds(200,200,200,25);
        editRole.setBounds(200,250,200,25);

        buttonUpdate.setBounds(320-85,400,75,25);
        buttonCancel.setBounds(320+85,400,75,25);

        editName.setText(user.getName());
        editRole.setText(user.getRole());
        editName.setEditable(false);
        editRole.setEditable(false);

        add(name);
        add(currentPassword);
        add(newPassword);
        add(certainNewPassword);
        add(role);
        add(editName);
        add(editCurrentPassword);
        add(editNewPassword);
        add(editCertainNewPassword);
        add(editRole);
        add(buttonUpdate);
        add(buttonCancel);

        buttonUpdate.addActionListener(event->{
            try {
                if (DataProcessing.searchUser(editName.getText(), String.valueOf(editCurrentPassword.getPassword()))==null){
                    JOptionPane.showMessageDialog(this,"原密码输入错误");
                }
                else if(!String.valueOf(editNewPassword.getPassword()).equals(String.valueOf(editCertainNewPassword.getPassword()))){
                    JOptionPane.showMessageDialog(this,"新密码与确认新密码不同");
                }
                else
                {
                    DataProcessing.updateUser(editName.getText(), String.valueOf(editNewPassword.getPassword()), editRole.getText());
                    JOptionPane.showMessageDialog(this,"修改成功");
                    dispose();
                }
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        });
        buttonCancel.addActionListener(event->{
            dispose();
        });
    }
}
