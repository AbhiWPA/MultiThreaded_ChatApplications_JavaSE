package lk.epic.chatApp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int port = 6500;
    private static ServerSocket serverSocket;

    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static String cMessage;

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) {
        new Thread(() -> {

            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server is start from port : "+port);

                while (true){
                    Socket socket = serverSocket.accept();
                    System.out.println("Client is connected : "+socket.getInetAddress());

                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    ClientHandler client = new ClientHandler(socket, clientHandlers);

                    clientHandlers.add(client);

                    client.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }



}
