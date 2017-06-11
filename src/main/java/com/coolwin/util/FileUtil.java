package com.coolwin.util;

import com.coolwin.entity.Picture;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/5/12.
 */
public class FileUtil {
    public static final String  ROOTPATH = "picture";
    public static List<String> upload(String root,String outurl,List<MultipartFile> files,String... path){
        String timeFileDirectory =DataUtil.format(new Date());
        String filepath = root+File.separator+ROOTPATH+File.separator;
        if (path!=null) {
            for (String s : path) {
                filepath+=s+File.separator;
            }
        }
        List<String> upload = new ArrayList<>();
        String fpath = filepath+timeFileDirectory+File.separator;
        //java8 迭代,上传文件到指定目录下
        files.forEach( f ->{
            String name = f.getOriginalFilename();
            LoggerUtil.getLogger(FileUtil.class).info("name="+name);
            String suffix = name.substring(name.lastIndexOf("."));
            if (!f.isEmpty()) {
                File file = new File(fpath+System.currentTimeMillis()+suffix);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    f.transferTo(file);
                    upload.add(outurl+File.separator+file.getAbsolutePath().replace(root,""));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return upload;
    }
    public static List<Picture> uploadImage(String root, String outurl, List<MultipartFile> files, String... path){
        String timeFileDirectory =DataUtil.format(new Date());
        String filepath = root+File.separator+ROOTPATH+File.separator;
        if (path!=null) {
            for (String s : path) {
                filepath+=s+File.separator;
            }
        }
        List<Picture> upload = new ArrayList<>();
        String fpath = filepath+timeFileDirectory+File.separator;
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i)==null) {
                continue;
            }
            Picture picture = new Picture();
            picture.key = "picture"+i;
            String name = files.get(i).getOriginalFilename();
            String suffix = name.substring(name.lastIndexOf("."));
            if (!files.get(i).isEmpty()) {
                File file = new File(fpath+System.currentTimeMillis()+suffix);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    files.get(i).transferTo(file);
                    picture.originUrl = outurl+File.separator+file.getAbsolutePath().replace(root,"");
                    File outfile = new File(fpath+"s_"+file.getName());
                    Thumbnails.of(file)
                            .size(320, 480)
                            .toFile(outfile);
                    picture.smallUrl = outurl+File.separator+outfile.getAbsolutePath().replace(root,"");
                    BufferedImage sourceImg = ImageIO.read(new FileInputStream(outfile));
                    picture.width = sourceImg.getWidth();
                    picture.height = sourceImg.getHeight();
                    upload.add(picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return upload;
    }
}
