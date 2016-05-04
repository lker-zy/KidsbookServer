package com.xuewen.kidsbook.mis.action;

/**
 * Created by root on 16-2-20.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
/*
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
*/


/*
@Action("imageUpload")
@InterceptorRefs(value = { @InterceptorRef("fileUploadStack") })
@Results({ @Result(name = "jsonType", type = "json") })
*/
public class ImageUploadAction extends CommonJsonAction {

    private static final long serialVersionUID = 1L;
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * 需要上传的文件
     */
    private File upload;

    /**
     * 上传文件的类型
     */
    private String uploadContentType;

    /**
     * 文件名
     */
    private String uploadFileName;

    /**
     * 上传之后的文件名
     */
    private String storageFileName;

    /**
     * 文件上传的路径
     */
    public String path = ServletActionContext.getServletContext().getRealPath(
            File.separator + "WEB-INF" + File.separator + "file");

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 新文件上传
     *
     * @return
     */
    public String upload() {
        List<Map<String, Object>> files = new ArrayList<>();
        Map<String, Object> fileJsonMap = new HashMap<>();

        String imageId = string2MD5(uploadFileName);

        fileJsonMap.put("url", "http://www.baidu.com");
        fileJsonMap.put("size", 1000);
        fileJsonMap.put("name", "test upload file");
        fileJsonMap.put("imageId", imageId);

        files.add(fileJsonMap);
        retJsonMap.put("files", files);

        try {
            // 将Struts2自动封装的文件名赋给要写入的文件
            //storageFileName = uploadFileName;
            storageFileName = imageId;
            // 创建要写入的文件
            File storageFile = new File(path + File.separator + storageFileName);
            copy(upload, storageFile);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 上传文件的主要方法
     *
     * @param src
     * @param dst
     * @return
     */
    public boolean copy(File src, File dst) {
        System.out.println("src file name is : " + src.getName());
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src),
                        BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
