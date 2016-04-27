package com.commonfunction;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by wjh on 2016/3/19.
 */
public class UploadFile {

    public static String uploadfile(String logoPathDir, MultipartFile multipartFile, HttpServletRequest request)throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        /** 得到文件保存目录的真实路径* */
        String logoRealPathDir = request.getSession().getServletContext()
                .getRealPath(logoPathDir);
        /** 根据真实路径创建目录* */
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists())
            logoSaveFile.mkdirs();
        /** 获取文件的后缀* */
        String suffix = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf("."));
        /** 使用UUID生成文件名称* */
        String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
        /** 拼成完整的文件保存路径加文件* */
        String fileName = logoRealPathDir + File.separator + logImageName;
        fileName = fileName.replaceAll("\\\\", "/");
        String uploadFileName = logoPathDir + File.separator + logImageName;
        uploadFileName = uploadFileName.replaceAll("\\\\", "/");
        File file = new File(fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException e) {
            throw new ServiceException(Errorcode.SYSTEM_ERROR);
        } catch (IOException e) {
            throw new ServiceException(Errorcode.SYSTEM_ERROR);
        }
        /** 打印出上传到服务器的文件的绝对路径* */
        System.out.println("****************" + fileName + "**************");
        return uploadFileName;
    }
}
