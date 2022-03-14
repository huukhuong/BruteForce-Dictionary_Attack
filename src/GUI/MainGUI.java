package GUI;

import IO.ReadWriteFile;
import Ultils.Values;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {

    public MainGUI() {
        this.setTitle("Brute force/Dictionary attacking");
        this.setSize(500, 500);
        this.setResizable(false);

        addControls();
        addEvents();
    }

    private JFileChooser fileChooserEmail;
    private JFileChooser fileChooserPassword;
    private JTextField txtUrl, txtEmailField, txtPasswordField;
    private JButton btnAttack;

    private void addControls() {
        Container con = getContentPane();

        JPanel pnMain = new MyPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        JPanel pnTitle = new MyPanel();
        JLabel lblTitle = new JLabel("" +
                "<html>" +
                "<div style='text-align: center; color: blue; font-size: 20px; font-weight: bold'>" +
                "Brute force/Dictionary attack demo" +
                "</div>" +
                "</html>"
        );
        pnTitle.add(lblTitle);
        pnMain.add(pnTitle);

        JPanel pnUrl = new MyPanel();
        JLabel lblUrl = new MyLabel("Url to attack");
        txtUrl = new MyTextField(20);
        txtUrl.setText(Values.LOGIN_URL);
        pnUrl.add(lblUrl);
        pnUrl.add(txtUrl);
        pnMain.add(pnUrl);

        JPanel pnEmail = new MyPanel();
        JLabel lblEmail = new MyLabel("Email field");
        txtEmailField = new MyTextField(20);
        txtEmailField.setText(Values.EMAIL_FIELD);
        pnEmail.add(lblEmail);
        pnEmail.add(txtEmailField);
        pnMain.add(pnEmail);

        JPanel pnPassword = new MyPanel();
        JLabel lblPassword = new MyLabel("Password field");
        txtPasswordField = new MyTextField(20);
        txtPasswordField.setText(Values.PASSWORD_FIELD);
        pnPassword.add(lblPassword);
        pnPassword.add(txtPasswordField);
        pnMain.add(pnPassword);

        JPanel pnDescription = new MyPanel();
        JLabel lblDescription = new JLabel("Paste your dictionary into `passwords.txt` and `emails.txt`");
        lblDescription.setFont(new Font("", Font.PLAIN, 14));
        lblDescription.setForeground(Color.RED);
        pnDescription.add(lblDescription);
        pnMain.add(pnDescription);
        
        JPanel pnButton = new MyPanel();
        btnAttack = new JButton("Attack to this website");
        btnAttack.setFont(new Font("", Font.PLAIN, 20));
        btnAttack.setPreferredSize(new Dimension(300, 50));
        pnButton.add(btnAttack);
        pnMain.add(pnButton);

        con.add(pnMain);
    }

    private void addEvents() {
        btnAttack.addActionListener(ev -> {
            Values.LOGIN_URL = txtUrl.getText();
            Values.EMAIL_FIELD = txtEmailField.getText();
            Values.PASSWORD_FIELD = txtPasswordField.getText();
            ArrayList<String> emails = ReadWriteFile.getAllEmails();
            ArrayList<String> passwords = new ArrayList<>();

            int emailSize = emails.size();
            int sizeOfElement = 10;
            int index = 0;
            for (int i = 0; i < emailSize; i += sizeOfElement) {
                List<String> splitEmails = emails.subList(i, i + sizeOfElement);
                new AttackGUI(splitEmails, passwords, index++).showWindow();
            }
            disposeFrame();
        });
    }

    private void disposeFrame() {
        this.dispose();
    }

    public void showWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
