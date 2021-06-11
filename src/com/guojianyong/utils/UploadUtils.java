package com.guojianyong.utils;

import javax.servlet.http.Part;
import java.io.IOException;


public class UploadUtils {


    /**
     * 用于上传一个文件，返回该文件的文件名(写入photo文件夹)
     * @param part
     * @return
     * @throws IOException
     */
    public static String toPhotoName(Part part) throws IOException {
        String head = part.getHeader("Content-Disposition");
        String filename = UUIDUtils.getUUID() + head.substring(head.lastIndexOf("."), head.lastIndexOf("\""));
//        String realPath = getServletContext().getRealPath("/img");
        part.write("photo/"+filename);
        return filename;
    }


    /**
     * 用于上传一个文件，返回该文件的文件名(写入file文件夹)
     * @param part
     * @return
     * @throws IOException
     */
    public static String toFileName(Part part) throws IOException {
        String head = part.getHeader("Content-Disposition");
        String filename = UUIDUtils.getUUID() + head.substring(head.lastIndexOf("."), head.lastIndexOf("\""));
        part.write("file/"+filename);
        return filename;
    }

}
