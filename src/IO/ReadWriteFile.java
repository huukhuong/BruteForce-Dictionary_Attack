package IO;

import Models.User;
import Ultils.Values;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadWriteFile {

    public static ArrayList<String> getAllEmails() {
        ArrayList<String> emails = new ArrayList<>();
        try {
            File myObj = new File(Values.INPUT_USER_FILE);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String email = myReader.nextLine();
                if (email.length() > 0)
                    emails.add(email);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return emails;
    }

    public static ArrayList<String> getAllPasswords() {
        ArrayList<String> passwords = new ArrayList<>();
        try {
            File myObj = new File(Values.INPUT_PASSWORD_FILE);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String password = myReader.nextLine();
                if (password.length() > 0)
                    passwords.add(password);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return passwords;
    }

    public static void writeResult(User user) {
        try {
            FileWriter myWriter = new FileWriter(Values.OUTPUT_FILE, true);
            myWriter.write(user.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
