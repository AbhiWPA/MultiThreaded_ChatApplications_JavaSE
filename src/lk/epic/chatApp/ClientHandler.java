package lk.epic.chatApp;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    private Socket socket;
    private ArrayList<ClientHandler> clientHandlers;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String message;
    private long threadNum;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers){
        this.socket = socket;
        this.clientHandlers = clientHandlers;
    }


    @Override
    public void run() {

        try {

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {

                this.threadNum = Thread.currentThread().getId();

                message = dataInputStream.readUTF().toString();

                if (message.equals("exit")) {

                    for (ClientHandler clientHandler : clientHandlers) {
                        if (clientHandler.socket == this.socket) {
                            clientHandlers.remove(clientHandler);
                            break;
                        }
                    }
                    
                    sendToServer(this.dataOutputStream, this.threadNum, this.message);
                    break;
                    
                }

                sendToServer(this.dataOutputStream, this.threadNum, this.message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendToServer(DataOutputStream dataOutputStream, long threadNum, String message) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(threadNum+"Client Says : "+message);
        System.out.print("Type message for "+threadNum+" client : ");
        dataOutputStream.writeUTF("Client "+threadNum+" : "+bufferedReader.readLine());

    }
}
