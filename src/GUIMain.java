import javapractice.Message;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginWindow.setVisible(true);
        });
    }
}
