package GUI;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {
    public MyLabel(String title) {
        this.setText(title);
        this.setFont(new Font("", Font.PLAIN, 20));
        this.setPreferredSize(new Dimension(150, 30));
    }
}
