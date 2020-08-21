package HomeWorkLevelTwoLessonSeven.client.service;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    @FXML
    TextArea mainTextArea;

    @FXML
    TextField textField;

    @FXML
    VBox chatWindow;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isAuthorized;

    public void btnOneClickAction(ActionEvent actionEvent) {
        String message = textField.getText();
        mainTextArea.appendText("-"+ message+"\n");
        textField.clear();
    }

    public void ClientService()  {

        try {
            socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            setAutorized(false);
            Thread t1 = new Thread(() -> {
                try {
                    while (true) {
                        String strMsg = dis.readUTF();
                        if (strMsg.startsWith("/authOk")) {
                            setAutorized(true);
                            break;
                        }
                        mainTextArea.appendText(strMsg + "\n");
                    }
                    while (true) {
                        String strMsg = dis.readUTF();
                        if (strMsg.equals("/exit")) {
                            break;
                        }
                        mainTextArea.appendText(strMsg + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t1.setDaemon(true);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAutorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if(!isAuthorized){
            chatWindow.setVisible(false);
            chatWindow.setManaged(false);
        }
        else {
            chatWindow.setVisible(true);
            chatWindow.setManaged(true);
        }
    }


}
