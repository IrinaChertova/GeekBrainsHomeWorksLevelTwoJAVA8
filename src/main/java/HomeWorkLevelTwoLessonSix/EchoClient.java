package HomeWorkLevelTwoLessonSix;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189; //8189

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String textToSendToServer;

    public EchoClient() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() throws IOException
    {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = dis.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        } System.out.println("Сервер:" + strFromServer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                System.out.print("Введите текст для отправки на сервер:");
                Scanner sc = new Scanner(System.in);
                textToSendToServer = sc.nextLine();
                if (textToSendToServer != "") {
                    sendMessageToServer();
                } else if (textToSendToServer.equals("")) {

                } else {
                    break;
                }
            }}).start();
    }

    public void closeConnection() {
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToServer() {
        if (socket.isClosed() || dos == null) {
            System.out.println("ошибка при отправке сообщения");
            return;
        }

        try {
            dos.writeUTF(textToSendToServer);
            dos.flush();
            if (textToSendToServer.equalsIgnoreCase("/end")) {
             closeConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String... args) {
        new EchoClient();
    }



}
