package javapractice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;

public class Message implements Serializable {

    private int m_mode;//0代表普通消息,1代表客户端向服务器上传，2代表服务器从客户端下载
    private String m_content;
    private String m_filename;
    private byte[] m_filedata;
    private String m_username;
    public static final int NORMAL=0;
    public static final int UPLOAD=1;
    public static final int DOWNLOAD=2;
    public static final int CLOSE=3;
    public Message(String content,String username){
        m_mode=NORMAL;
        m_content=content;
        m_username=username;
    }
    public Message(String content,String username,String filename,byte[] filedata,int mode){
        m_mode=mode;
        m_content=content;
        m_filedata=filedata;
        m_filename=filename;
        m_username=username;
    }

    public String getContent(){
        return m_content;
    }
    public int getMode(){
        return m_mode;
    }
    public String getFilename(){
        return m_filename;
    }
    public byte[] getFiledata(){
        return m_filedata;
    }
    public String getUsername(){
        return m_username;
    }
}
