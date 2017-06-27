package FileInfo;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Neig on 2017/06/19.
 */
public class FileInfoData {

    public FilenameFilter createFilter(String...args){
        FilenameFilter rtnFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                //args[]配列の中のひとつとでもマッチしたらYESになるように処理する
                //ほんとうか？？？？？？
                for(int i=0;i<args.length;i++) {
                    if (name.endsWith(args[i])) {
                        return true;
                    }
                }
                return false;
            }
        };
        return rtnFilter;
    }

    //files配列を受け取ってそれをfilterでフィルタリングしたものを返す
    //できなさそう
/*
    public File[] createFiltedFiles(File[] files,FilenameFilter filter){

        File[] filtedFiles = files;
        return filtedFiles;
    }
  */
    //パスを受け取ってそのパスのファイルをfilterでフィルタリングして作成したファイルリストを返す
    public File[] createFilredFiles(String path, FilenameFilter filter){
        File[] filtedFiles = new File(path).listFiles(filter);
        return filtedFiles;
    }

}


