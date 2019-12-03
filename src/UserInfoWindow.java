import javafx.event.ActionEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.SQLException;
import java.util.Enumeration;

public class UserInfoWindow extends JFrame{
    public static final int ADD_USER=0;
    public static final int UPDATE_USER=1;
    public static final int DELETE_USER=2;
    private JTabbedPane tab;
    private JTable table;
    private AddUserJanel addUserJanel;
    private UpdateUserJanel updateUserJanel;
    private JPanel deleteUserPanel;
    private JScrollPane scrollPane;
    private JButton buttonDelete;
    private JButton buttonBack;
    private static final String[] columnNames=new String[]{"用户名","口令","角色"};
    private Object[][] data;
    private DefaultTableModel model;

    public UserInfoWindow(int tabFlag){
        initData();
        model=new DefaultTableModel(data,columnNames);
        table=new JTable(model);
        buttonDelete=new JButton("删除");
        buttonBack=new JButton("返回");
        addUserJanel=new AddUserJanel();
        updateUserJanel=new UpdateUserJanel();
        deleteUserPanel=new JPanel();
        scrollPane=new JScrollPane(table);
        tab=new JTabbedPane();

        add(tab);
        tab.addTab("增加用户",addUserJanel);
        tab.addTab("修改用户",updateUserJanel);
        tab.addTab("删除用户",deleteUserPanel);
        tab.setSelectedIndex(tabFlag);



        deleteUserPanel.add(scrollPane);
        deleteUserPanel.add(buttonDelete);
        deleteUserPanel.add(buttonBack);


        setSize(640,480);

        buttonDelete.addActionListener(event->{
            try {
                int row = table.getSelectedRow();
                String userName = String.valueOf(table.getValueAt(row, 0));
                DataProcessing.deleteUser(userName);
                refreshTable();
                updateUserJanel.refreshNameBox();
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        });
        buttonBack.addActionListener(event->{
            dispose();
        });
        addUserJanel.getButtonCancel().addActionListener(event->{
            dispose();
        });
        updateUserJanel.getButtonCancel().addActionListener(event->{
            dispose();
        });
        addUserJanel.getButtonApply().addActionListener(event->{
            try {
                boolean success = DataProcessing.insertUser(addUserJanel.getEditName().getText(), String.valueOf(addUserJanel.getEditPassword().getPassword()), String.valueOf(addUserJanel.getBoxRole().getSelectedItem()));
                if(!success){
                    JOptionPane.showMessageDialog(this,"该用户名已存在");
                }
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
            refreshTable();
            updateUserJanel.refreshNameBox();
        });
        updateUserJanel.getButtonApply().addActionListener(event->{
            try {
                boolean success = DataProcessing.updateUser(String.valueOf(updateUserJanel.getEditName().getSelectedItem()), String.valueOf(updateUserJanel.getEditPassword().getPassword()), String.valueOf(updateUserJanel.getBoxRole().getSelectedItem()));
                if(!success){
                    JOptionPane.showMessageDialog(this,"该用户不存在");
                }
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
            refreshTable();
        });
    }

    private void initData(){
        try {
            //初始化data
            Enumeration<User> enumeration=DataProcessing.getAllUser();
            int length=0;
            while(enumeration.hasMoreElements()){
                enumeration.nextElement();
                length++;
            }
            enumeration=DataProcessing.getAllUser();
            data=new Object[length][3];
            int index=0;
            while(enumeration.hasMoreElements()){
                User userTmp=enumeration.nextElement();
                data[index][0]=userTmp.getName();
                data[index][1]=userTmp.getPassword();
                data[index][2]=userTmp.getRole();
                index++;
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"数据库异常",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshTable(){
        initData();
        model.setDataVector(data,columnNames);
        model.fireTableDataChanged();
//        table.revalidate();
//        table.repaint();
//        for(int i=0;i<data.length;++i){
//            for(int j=0;j<3;++j){
//                table.setValueAt(data[i][j],i,j);
//            }
//        }
    }
}

class UserPanel extends JPanel {
    protected JLabel name;
    protected JLabel password;
    protected JLabel role;
    protected JTextField editName;
    protected JPasswordField editPassword;
    protected JComboBox<String> boxRole;
    protected JButton buttonApply;
    protected JButton buttonCancel;
    public UserPanel(){
        name=new JLabel("用户名");
        password=new JLabel("口令");
        role=new JLabel("角色");
        editName=new JTextField();
        editPassword=new JPasswordField();
        boxRole=new JComboBox<String>(new String[]{"administrator","operator","browser"});
        buttonApply=new JButton();
        buttonCancel=new JButton("取消");

        setLayout(new GridLayout(4,2,100,100));
        add(name);
        add(editName);
        add(password);
        add(editPassword);
        add(role);
        add(boxRole);
        add(buttonApply);
        add(buttonCancel);

    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public JButton getButtonApply(){
        return buttonApply;
    }

    public JComboBox<String> getBoxRole() {
        return boxRole;
    }

    public JPasswordField getEditPassword() {
        return editPassword;
    }

    public JTextField getEditName() {
        return editName;
    }
}

class AddUserJanel extends UserPanel{
    public AddUserJanel(){
        super();
        buttonApply.setText("增加");

    }
}

class UpdateUserJanel extends JPanel{
    protected JLabel name;
    protected JLabel password;
    protected JLabel role;
    protected String[] names;
    protected DefaultComboBoxModel<String> model;
    protected JComboBox<String> editName;
    protected JPasswordField editPassword;
    protected JComboBox<String> boxRole;
    protected JButton buttonApply;
    protected JButton buttonCancel;
    public UpdateUserJanel(){
        initNameModel();
        editName=new JComboBox<String>(model);

        name=new JLabel("用户名");
        password=new JLabel("口令");
        role=new JLabel("角色");
        editPassword=new JPasswordField();
        boxRole=new JComboBox<String>(new String[]{"administrator","operator","browser"});
        buttonApply=new JButton();
        buttonCancel=new JButton("取消");

        setLayout(new GridLayout(4,2,100,100));
        add(name);
        add(editName);
        add(password);
        add(editPassword);
        add(role);
        add(boxRole);
        add(buttonApply);
        add(buttonCancel);
        boxRole.setEnabled(false);
        buttonApply.setText("修改");
    }

    public void refreshNameBox(){
        initNames();
        model=new DefaultComboBoxModel<String>(names);
        editName.setModel(model);
    }
    public void initNames(){
        try {
            Enumeration<User> enumeration = DataProcessing.getAllUser();
            int length=0;
            while(enumeration.hasMoreElements()){
                length++;
                enumeration.nextElement();
            }
            enumeration=DataProcessing.getAllUser();
            names=new String[length];
            int index=0;
            while (enumeration.hasMoreElements()){
                User user=enumeration.nextElement();
                names[index]=user.getName();
                index++;
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    public void initNameModel(){
        initNames();
        model=new DefaultComboBoxModel<String>(names);
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public JButton getButtonApply(){
        return buttonApply;
    }

    public JComboBox<String> getBoxRole() {
        return boxRole;
    }

    public JComboBox<String> getEditName() {
        return editName;
    }

    public JPasswordField getEditPassword() {
        return editPassword;
    }
}
