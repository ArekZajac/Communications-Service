package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

class Server implements Runnable{

    static JFrame frame = new JFrame("Communications Service Server");
    static JTextArea consoleArea = new JTextArea(16, 50);

    public static int port = 9002;
    public static HashSet<String> usernames = new HashSet<>();
    public static HashSet<PrintWriter> writers = new HashSet<>();

    public void run(){
        try {
            main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPort() {
        port = Integer.parseInt(JOptionPane.showInputDialog(frame, "Select a port to listen at:", "Communications Service Server", JOptionPane.PLAIN_MESSAGE));
    }

    public static void showText(String text){
        consoleArea.append(text + "\n");
    }

    public static void main() throws Exception {

        System.out.println("1");

        consoleArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(consoleArea), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        getPort();

        showText("Communications-Service-Server Running...");
        ServerSocket socketListener = new ServerSocket(port);
        try {
            while (true) {
                new Controller(socketListener.accept()).start();
            }
        } finally {
            socketListener.close();
        }


    }

    public static class Controller extends Thread {
        Socket socket;
        String username;
        PrintWriter output;
        BufferedReader input;


        public Controller(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                output = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    output.println("#SUBMITNAME#");
                    username = input.readLine();
                    if (username == null) {
                        return;
                    }
                    synchronized (usernames) {
                        if (!usernames.contains(username)) {
                            usernames.add(username);
                            break;
                        }
                    }
                }

                output.println("#ACCEPTEDNAME#");
                showText("Currently Connected: " + usernames);
                writers.add(output);

                while (true) {
                    String in = input.readLine();
                    if (in == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("#MESSAGE# " + in);
                        showText("#MESSAGE# " + in);
                    }
                }

            } catch (Exception e) {
                showText(String.valueOf(e));
            } finally {
                if (username != null) {
                    usernames.remove(username);
                }
                if (output != null) {
                    writers.remove(output);
                }
                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
    }
}