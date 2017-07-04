package com.coolwin.util;

import com.coolwin.entity.Picture;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.List;

/**
 * Created by dell on 2017/5/12.
 */
public class FileUtil {
    public static final String  ROOTPATH = "../picture";
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
                File file = new File(fpath+UUID.randomUUID()+suffix);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    f.transferTo(file);
                    upload.add(outurl+"/"+file.getAbsolutePath().replace(root,"").replace("\\","/"));
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
                File file = new File(fpath+ UUID.randomUUID()+suffix);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    files.get(i).transferTo(file);
                    picture.originUrl = outurl+"/"+file.getAbsolutePath().replace(root,"").replace("\\","/");
                    File outfile = new File(fpath+"s_"+file.getName());
                    Thumbnails.of(file)
                            .size(320, 480)
                            .toFile(outfile);
                    picture.smallUrl = outurl+"/"+outfile.getAbsolutePath().replace(root,"").replace("\\","/");
                    FileInputStream fileInputStream = new FileInputStream(outfile);
                    BufferedImage sourceImg = ImageIO.read(fileInputStream);
                    picture.width = sourceImg.getWidth();
                    picture.height = sourceImg.getHeight();
                    sourceImg.flush();
                    upload.add(picture);
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return upload;
    }
    public static void uploadImageNoThumbnails(String root,  List<MultipartFile> files, String... path){
        String filepath = root+File.separator+ROOTPATH+File.separator;
        if (path!=null) {
            for (String s : path) {
                filepath+=s+File.separator;
            }
        }
        String fpath = filepath+File.separator;
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i)==null) {
                continue;
            }
            String name = files.get(i).getOriginalFilename();
            String suffix = name.substring(name.lastIndexOf("."));
            if (!files.get(i).isEmpty()) {
                File file = new File(fpath+ UUID.randomUUID()+suffix);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    files.get(i).transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static  void txt2pdf(String txtPath, String pdfPath) {
        File file = new File(pdfPath);
        if(file.exists()){
            return;
        }
        Document document = new Document(PageSize.A4);

        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = new FileInputStream(txtPath);
            //读取文本内容
            reader = new BufferedReader(new InputStreamReader(is));
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            /** 新建一个字体,iText的方法
             * STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
             * UniGB-UCS2-H   是编码，在iTextAsian.jar 中以cmap为后缀
             * H 代表文字版式是 横版， 相应的 V 代表 竖版
             */
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
                    "UniGB-UCS2-H", false);
            Font fontChinese = new Font(bfChinese, 30, Font.NORMAL, Color.BLACK);
//		 打开文档，将要写入内容
            document.open();
            String line=reader.readLine();
            while(line!=null){
                Paragraph pg = new Paragraph(line,fontChinese);
                document.add(pg);
                line=reader.readLine();
            }
            document.close();
            reader.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String> traverseFolder(String root,String path,String replaypath) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return list;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        if(file2.getName().equals("temp")){
                            continue;
                        }
                        list.addAll(traverseFolder(root,file2.getAbsolutePath(),replaypath)) ;
                    } else {
                        list.add(root+file2.getAbsolutePath().replace(replaypath,"").replace("\\","/"));
                    }
                }
            }
        } else {
            return list;
        }
        return list;
    }
}
