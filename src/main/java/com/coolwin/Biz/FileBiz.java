package com.coolwin.Biz;

import com.coolwin.entity.Picture;
import com.coolwin.entity.appentity.AppResult;
import com.coolwin.util.DocConverter;
import com.coolwin.util.FileUtil;
import com.coolwin.util.GsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/5/5.
 */
@Component
public class FileBiz extends BaseBiz{

    public String  upload(HttpServletRequest request){
        String root =  request.getSession().getServletContext().getRealPath("/");
        String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +"/picture";
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("picture");
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        List<Picture> pictures = FileUtil.uploadImage(root,contextpath,files,"commodity");
        if(pictures.size()>0){
            return GsonUtil.objectToJson(pictures.get(0));
        }
        return "上传图片过多";
    }
    public String  uploadtemplatepic(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("pic");
        String root =  request.getSession().getServletContext().getRealPath("/");
        String type = request.getParameter("type");
        if(type==null || !type.equals("1")){
            FileUtil.uploadImageNoThumbnails(root,files,"template",""+files.size(),""+System.currentTimeMillis());
        }else{
            FileUtil.uploadImageNoThumbnails(root,files,"template","lunbo",""+System.currentTimeMillis());
        }
        return "uploadsuccess";
    }
    public String tohtml(HttpServletRequest request,String fileName){
        String root =  request.getSession().getServletContext().getRealPath("/");
        //获取文件上传路径
        String saveDirectory =root+"..\\file";
        File oldtempfile = new File(saveDirectory+File.separator+fileName);
        if (!oldtempfile.exists()) {
            return "";
        }
        String path =  request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +"/file/temp/";
        //获取需要转换的文件名,将路径名中的'\'替换为'/'
        String converfilename = saveDirectory.replaceAll("\\\\", "/")+"/"+fileName;
        String exName= fileName.substring(fileName.lastIndexOf(".")+1);
        if(exName.equals("txt")){
            FileUtil.txt2pdf(oldtempfile.getAbsolutePath(),
                    oldtempfile.getParent()+"/temp/"+oldtempfile.getName().substring(0,oldtempfile.getName().lastIndexOf("."))+".pdf");
        }else{
            //调用转换类DocConverter,并将需要转换的文件传递给该类的构造方法
            DocConverter d = new DocConverter(converfilename);
            //调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;
            d.conver();
        }
        return GsonUtil.objectToJson(path+fileName.substring(0,fileName.lastIndexOf("."))+".pdf");
    }
    public String downFile(HttpServletRequest request){
        String root =  request.getSession().getServletContext().getRealPath("/");
        String contextpath = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort()+"/file";
        List<String> filelist =  FileUtil.traverseFolder(contextpath,root+"..\\file",root+"..\\file");
        AppResult appResult = new AppResult();
        if (filelist.size()==0) {
            appResult.setStateValue(1,"目录不存在,或文件列表为空",null,"/shopindex");
            return GsonUtil.objectToJson(appResult);
        }else{
            appResult.setData(filelist);
            appResult.setStateValue(0,null,null,"/shopindex");
            return GsonUtil.objectToJson(appResult);
        }
    }
}
