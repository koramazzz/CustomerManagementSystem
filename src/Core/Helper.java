package Core;

import javax.swing.*;
import java.lang.reflect.Field;

public class Helper {

    public static void setTheme(){

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if (info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field){

        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields){

        for (JTextField field : fields){
            if (isFieldEmpty(field)) return true;
            }
        return false;
    }

    public static boolean isMailValid(String mail){

        if (mail == null || mail.trim().isEmpty()) return false;

        if (!mail.contains("@")) return false;

        String[] parts = mail.split("@");

        if (parts.length != 2) return false;

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;

        if (!parts[1].contains(".")) return false;

        return true;
    }

    public static void showMsg(String message){
        String msg;
        String title;

        switch (message) {
            case "fill" -> {
                msg = "Please fill all the blanks!";
                title = "Error!";
            }
            case "error" -> {
                msg = "An error occurred!";
                title = "Error!";
            }
            case "done" -> {
                msg = "Transaction successful";
                title = "Result";
            }
            default -> {
                msg = message;
                title = "Message";
            }
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
}

