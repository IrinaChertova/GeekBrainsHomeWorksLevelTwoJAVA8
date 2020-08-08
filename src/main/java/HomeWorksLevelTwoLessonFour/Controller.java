package HomeWorksLevelTwoLessonFour;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    TextArea mainTextArea;

    @FXML
    TextField textField;


    public void btnOneClickAction(ActionEvent actionEvent) {
        String message = textField.getText();
        mainTextArea.appendText("-"+ message+"\n");
        textField.clear();
    }

}
