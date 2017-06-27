package sample;


import FileInfo.FileInfoData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable {
//public class Controller{

    @FXML
    private Button okbtn;

    @FXML
    private CheckBox Chkbox;

    @FXML
    private TextField dirTxF;

    @FXML
    private TextField extenTxF;

    @FXML
    private Button chsebtn;

    @FXML
    private ListView<String> fileLV;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField allFileNumTxF;

    @FXML
    private TextField picFileNumTxF;

    //DirChooser
    private DirectoryChooser DCer = new DirectoryChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //フォルダ選択ダイアログのタイトル設定
        DCer.setTitle("フォルダ選択");
        //ディレクトリ選択のテキストをフォーカスする
        dirTxF.requestFocus();
        //決定ボタンの活性化

        okbtn.disableProperty().bind(
                Bindings.or(
                        dirTxF.textProperty().isEmpty(),
                        extenTxF.textProperty().isEmpty()
                ));
    }

    @FXML
    protected void onCheese(ActionEvent evt){
        try{
            String homeDir = dirTxF.textProperty().get();
            if(homeDir != null && !homeDir.isEmpty()){
                File initDir = new File(homeDir);

                if(initDir.isDirectory()){
                    DCer.setInitialDirectory(initDir);
                }
            }
            File selectedDir = DCer.showDialog(((Button) evt.getSource()).getScene().getWindow());

            if(selectedDir != null){
                dirTxF.setText(selectedDir.getAbsolutePath());
                dirTxF.requestFocus();
            }
        } catch (Exception ex){
            new ErrorDialog().showErrorException(ex);
        }
    }

    @FXML
    protected void onOKbtn2(ActionEvent evt){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("実行します");

        StringBuffer buf = new StringBuffer();
        buf.append("dir=").append(dirTxF.getText()).append("\r\n")
                .append("namePattern=").append(extenTxF.getText()).append("\r\n")
                .append(Chkbox.isSelected() ? "Yes" : "No");
        alert.setContentText(buf.toString());

        alert.showAndWait();
    }

    @FXML
    protected void onOKbtn(ActionEvent evt){
        File trgtDir = new File(dirTxF.getText());

        File files[] = trgtDir.listFiles();
        //ターゲットディレクトリのファイルの数
        int fileNum = files.length;
        allFileNumTxF.setText(String.valueOf(fileNum));
//        System.out.println("filesLength is "+ fileNum);


        //ここから
        ///全ファイルの中からjpg,jpeg形式のフィルタリングするフィルタの設定
        FilenameFilter picFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //拡張子の指定
                if(name.endsWith("jpg") || name.endsWith("jpeg")){
                    return true;
                }else{
                    return false;
                }
            }
        };

        //File[] picFiltedFiles = trgtDir.listFiles(picFilter);
        File[] picFiltedFiles = new File(dirTxF.getText()).listFiles(picFilter);
        int picFileNum = picFiltedFiles.length;
        picFileNumTxF.setText(String.valueOf(picFileNum));
        //ここまで

        FileInfoData fid = new FileInfoData();
        FilenameFilter picFilter2 = fid.createFilter("jpg","jpeg");
        File onlyPicFiles[] = fid.createFilredFiles(dirTxF.getText(),picFilter2);
        int picFileNum2 = onlyPicFiles.length;
        System.out.println(picFileNum +"前　比較　後"+picFileNum2);


        ////

        ObservableList<String> fileslist = FXCollections.observableArrayList();

        for(int i = 0; i < files.length;i++){
            //System.out.println("File "+ (1 + i) + "->" + files[i]);
            fileslist.add(files[i].toString());
        }

        fileLV.setItems(fileslist);

    }


    @FXML
    protected void onCancel(ActionEvent evt){
        ((Stage) ((Button) evt.getSource()).getScene().getWindow()).close();
    }


}
