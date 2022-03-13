package GUI;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public MyTextField(int column) {
        this.setColumns(column);
        this.setFont(new Font("", Font.PLAIN, 18));
    }
}
