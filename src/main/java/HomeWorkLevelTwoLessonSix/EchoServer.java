package HomeWorkLevelTwoLessonSix;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    private static DataInputStream dis;
    private static DataOutputStream dos;

    public static void main(String[] args) {
        new Thread(()->{
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");

            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String msg = dis.readUTF();
                if (msg.equalsIgnoreCase("/end")) {
                    break;
                }
                dos.writeUTF("Echo: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }).start();
        new Thread(()-> {
            while (true) {
            try {
                System.out.println("введите сообщение для оправки Клиенту:");
                Scanner sc = new Scanner(System.in);
                String textToSend = sc.nextLine();
                if (textToSend != "") {
                    dos.writeUTF(textToSend);
                } else if (textToSend.equals("")){

                } else {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }).start();
    }
}
