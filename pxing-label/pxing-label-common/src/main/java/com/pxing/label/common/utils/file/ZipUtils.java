package com.pxing.label.common.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 @author l
 @date 2020-11-30  18:50
 */
public class ZipUtils {
    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 将压缩文件下载到本地
     */
    public static void doZip(String zipFilename, Map<String, InputStream> isMap, String localPath) throws IOException {
        log.info("==> 开始下载zip文件{}到：{}", zipFilename, localPath);
        doZip(zipFilename, isMap, new FileOutputStream(localPath));
    }

    /**
     * 通过浏览器下载压缩文件
     */
    public static void doZip(String zipFilename, Map<String, InputStream> isMap, HttpServletResponse response) throws IOException {
        log.info("==> 通过浏览器下载压缩文件：{}", zipFilename);
        setResponse(zipFilename, response);
        doZip(zipFilename, isMap, response.getOutputStream());
    }

    /**
     * 将多个文件打包成压缩文件
     * @param zipFilename 压缩包的文件名
     * @param isMap   文件集合
     * @param os  输出流
     */
    private static void doZip(String zipFilename, Map<String, InputStream> isMap, OutputStream os) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(os);
            for (Map.Entry<String, InputStream> isEntry : isMap.entrySet()) {
                InputStream is = isEntry.getValue();
                if (is != null) {
                    zos.putNextEntry(new ZipEntry(isEntry.getKey()));
                    int len;
                    byte[] buff = new byte[1024];
                    while (-1 != (len = is.read(buff, 0, buff.length))) {
                        zos.write(buff, 0, len);
                    }
                    zos.closeEntry();
                    is.close();
                }
            }
            zos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(zos!=null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("<== 下载{}文件结束", zipFilename);
    }

    /**
     * 设置下载文件的response信息
     * @param filename 下载文件名
     */
    private static void setResponse(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        // 清除首部的空白行（针对jsp文件编译后生成html时产生的空白行）
        response.reset();
        // 自动判断下载文件类型
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
    }
}

