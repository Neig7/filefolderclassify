package sample;


import FileInfo.FileInfoData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

    @FXML
    private CheckBox chkjpeg;

    @FXML
    private CheckBox chkpng;

    @FXML
    private CheckBox chkjpg;

    @FXML
    private CheckBox chkraw;

    @FXML
    private CheckBox chkall;

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
        chkall.setSelected(true);
    }

    @FXML
    protected void onChoose(ActionEvent evt){
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

                //選択したフォルダの中のすべての拡張子をピックアップ
                //
//                String kaku[] = ;

            }
        } catch (Exception ex){
            new ErrorDialog().showErrorException(ex);
        }
    }

    @FXML
    protected void onOKbtn(ActionEvent evt){
        File trgtDir = new File(dirTxF.getText());

        File files[] = trgtDir.listFiles();
        //ターゲットディレクトリのファイルの数
        int fileNum = files.length;
        allFileNumTxF.setText(String.valueOf(fileNum));
//        System.out.println("filesLength is "+ fileNum);

        //check boxの数
        int CHKNUM = 1;
        int j = 0;
//ERROR        String chkString[] = new String[CHKNUM];
        String[] chkString = new String[CHKNUM];



        if(chkall.isSelected()){
            chkString[j] = "*";
            j++;
        }
        if(chkjpeg.isSelected()){
            chkString[j] = "jpeg";
            j++;
            chkString[j] = "JPEG";
            j++;
        }
        if(chkjpg.isSelected()){
            chkString[j] = "jpg";
            j++;
            chkString[j] = "JPG";
            j++;
        }
        if(chkpng.isSelected()){
            chkString[j] = "png";
            j++;
            chkString[j] = "PNG";
            j++;
        }
        if(chkraw.isSelected()){
            chkString[j] = "raw";
            j++;
            chkString[j] = "RAW";
            j++;
        }


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


        //ファイルフィルタリングのために使うインスタンス
        FileInfoData fid = new FileInfoData();

        FilenameFilter picFilter2 = fid.createFilter("jpg","jpeg");
        File onlyPicFiles[] = fid.createFiltedFiles(dirTxF.getText(),picFilter2);
        int picFileNum2 = onlyPicFiles.length;
        System.out.println(picFileNum +"前　比較　後"+picFileNum2);



        FileInfoData fid2 = new FileInfoData();

        //これのchkStringがよくないはず
        //chkStirngにnull[なんでもないもの]が入っているはず
        FilenameFilter picFilter3 = fid2.createFilter(chkString);

        File chkedPicFiles[] = fid2.createFiltedFiles(dirTxF.getText(),picFilter3);
        int picFileNum3 = chkedPicFiles.length;
        System.out.println(picFileNum3 +" チェックボックスでフィルタリングした数");


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
