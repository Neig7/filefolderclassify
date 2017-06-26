package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;


/**
 * Created by ken on 2017/06/26.
 */
public class ErrorDialog {

    public static void showErrorException(Throwable ex){

        String title = "Errorが発生";
        String header = "エラーが発生";
        String message = "次の例外エラーが発生しました :" + ex.getMessage();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.getDialogPane().setHeaderText(header);
        alert.getDialogPane().setContentText(message);
        alert.showAndWait();


    }
}
