import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

public class FileWindow extends JFrame{
    public static final int DOWNLOAD=0;
    public static final int UPLOAD=1;
    private static final int DEFAULT_WIDTH=640;
    private static final int DEFAULT_HEIGHT=480;
    private JTabbedPane tab;
    private JTable table;
    private JPanel uploadPanel;
    private JPanel downloadPanel;
    private JScrollPane scrrolPane;
    private final String[] columns={"档案号","创建者","时间","文件名","描述"};
    private Object[][] data;
    private JButton buttonDownload;
    private JButton buttonDownloadBack;
    private JButton buttonOpen;
    private JButton buttonUpload;
    private JButton buttonCancel;
    private User m_user;
    public FileWindow(int flag,User user){
        m_user=user;
        initData();
        tab=new JTabbedPane();
        table=new JTable(data,columns);
        uploadPanel=new JPanel();
        downloadPanel=new JPanel();
        scrrolPane=new JScrollPane(table);
        buttonDownload=new JButton("下载");
        buttonDownloadBack =new JButton("返回");
        buttonOpen=new JButton("打开");
        buttonUpload=new JButton("上传");
        buttonCancel=new JButton("取消");

        tab.addTab("下载文件",downloadPanel);
        tab.addTab("上传文件",uploadPanel);
        add(tab);

        initDownloadPanel();
        initUploadPanel();

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        tab.setSelectedIndex(flag);



    }

    private void initData(){
        try {
            //初始化data
            Enumeration<Doc> enumeration=DataProcessing.getAllDocs();
            int length=0;
            while(enumeration.hasMoreElements()){
                enumeration.nextElement();
                length++;
            }
            enumeration=DataProcessing.getAllDocs();
            data=new Object[length][5];
            int index=0;
            while(enumeration.hasMoreElements()){
                Doc doc=enumeration.nextElement();
                data[index][0]=doc.getID();
                data[index][1]=doc.getCreator();
                data[index][2]=doc.getTimestamp().toString();
                data[index][3]=doc.getFilename();
                data[index][4]=doc.getDescription();
                index++;
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"数据库异常",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initDownloadPanel(){
        downloadPanel.add(scrrolPane);
        downloadPanel.add(buttonDownload);
        downloadPanel.add(buttonDownloadBack);
        buttonDownload.addActionListener(event->{
            int row=table.getSelectedRow();
            String filename=table.getValueAt(row,3).toString();
            try {
                JFileChooser chooser=new JFileChooser();
                chooser.showSaveDialog(this);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File file=chooser.getSelectedFile();
                if(file!=null) {
                    DataProcessing.downloadFile(filename, file);
                }
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        });
        buttonDownloadBack.addActionListener(event->{
            dispose();
        });
    }

    private void initUploadPanel(){
        JLabel docID=new JLabel("档案名");
        JLabel docDescribe=new JLabel("档案描述");
        JLabel docFilename=new JLabel("档案文件名");
        JTextField editDocID=new JTextField();
        JTextArea editDocDescribe=new JTextArea();
        JTextField editFilename=new JTextField();

        uploadPanel.add(docID);
        uploadPanel.add(docDescribe);
        uploadPanel.add(docFilename);
        uploadPanel.add(editDocID);
        uploadPanel.add(editDocDescribe);
        uploadPanel.add(editFilename);
        uploadPanel.add(buttonOpen);
        uploadPanel.add(buttonUpload);
        uploadPanel.add(buttonCancel);

        uploadPanel.setLayout(null);
        docID.setBounds(50,50, 100, 25);
        docDescribe.setBounds(50,100, 100, 25);
        docFilename.setBounds(50,240, 100, 25);
        editDocID.setBounds(200,50,200,25);
        editDocDescribe.setBounds(200,100,200,100);
        editFilename.setBounds(200,240,200,25);
        buttonOpen.setBounds(420,240,75,25);
        buttonUpload.setBounds(75,300,75,25);
        buttonCancel.setBounds(170,300,75,25);

        buttonOpen.addActionListener(event->{
            JFileChooser chooser=new JFileChooser();
            chooser.showOpenDialog(this);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File file=chooser.getSelectedFile();
            editFilename.setText(file.getAbsolutePath());
        });

        buttonUpload.addActionListener(event -> {
            try {
                DataProcessing.uploadFile(editDocID.getText(), editDocDescribe.getText(), new File(editFilename.getText()), m_user.getName());
            }
            catch(IOException e){
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(this,e.getMessage());

            }
            dispose();
        });

        buttonCancel.addActionListener(event->{
            dispose();
        });
    }
}
