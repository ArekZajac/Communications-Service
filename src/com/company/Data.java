package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

class Data {

    static boolean stayLogged = false;
    static String loggedName = null;
    static String loggedPassword = null;
    static String serverConnection = null;
    static int serverPort = 0;
    static boolean messageTimes = false;
    static boolean notifications = false;
    static boolean flashing = false;
    static boolean BRMode = false;

    static void getData() {
        java.util.ArrayList<String> list = null;
        try {
            FileInputStream stream = new FileInputStream(getDocumentsPath() + "\\dataFile.txt");
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            list = new java.util.ArrayList<>();

            while ((strLine = br.readLine()) != null) {
                list.add(strLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stayLogged = Boolean.parseBoolean(dataStrip(list.get(0)));
        loggedName = (dataStrip(list.get(1)));
        loggedPassword = (dataStrip(list.get(2)));
        serverConnection = (dataStrip(list.get(3)));
        serverPort = Integer.parseInt((dataStrip(list.get(4))));
        messageTimes = Boolean.parseBoolean(dataStrip(list.get(5)));
        notifications = Boolean.parseBoolean(dataStrip(list.get(6)));
        flashing = Boolean.parseBoolean(dataStrip(list.get(7)));
        BRMode = Boolean.parseBoolean(dataStrip(list.get(8)));
    }

    private static String dataStrip(String input) {
        return input.replaceAll(".*=", "");
    }

    static void setData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(getDocumentsPath() + "\\dataFile.txt"));
        writer.write("stayLogged=" + stayLogged);
        writer.newLine();
        writer.write("loggedName=" + loggedName);
        writer.newLine();
        writer.write("loggedPassword=" + loggedPassword);
        writer.newLine();
        writer.write("serverConnection=" + serverConnection);
        writer.newLine();
        writer.write("serverPort=" + serverPort);
        writer.newLine();
        writer.write("messageTimes=" + messageTimes);
        writer.newLine();
        writer.write("notifications=" + notifications);
        writer.newLine();
        writer.write("flashing=" + flashing);
        writer.newLine();
        writer.write("BRMode=" + BRMode);
        writer.close();
    }

//    public static String getTime() {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        return sdf.format(cal.getTime());
//    }

    static String getDocumentsPath() {
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        return String.valueOf(fw.getDefaultDirectory());
    }

    static String encrypt(String original, int shift) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            int c = original.charAt(i) + shift;
            if (c > 126) {
                c -= 95;
            } else if (c < 32) {
                c += 95;
            }
            encrypted.append((char) c);
        }
        return encrypted.toString();
    }

    static String decrypt(String encrypted, int shift) {
        return encrypt(encrypted, shift);
    }
}