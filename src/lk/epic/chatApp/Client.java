package lk.epic.chatApp;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{

    private static final int port = 6500;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static Socket socket;

    private static String message;
    private static String reply;

    private static int count = 0;
    private int id;

    public static void main(String[] args) {
        Thread thread = new Thread(new Client());
        thread.start();
    }

    public Client() {
        synchronized (Client.class) {
            id = ++count;
        }
    }

    @Override
    public void run() {

        try {

            socket = new Socket("localhost",port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Enter Message : ");
                message = bufferedReader.readLine();
                dataOutputStream.writeUTF(message);

                reply = dataInputStream.readUTF();
                String replyMessage = reply.split(" : ")[1];
                System.out.println("Server Says : " + replyMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
