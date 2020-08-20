package com.example.batchoperation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;


public final class FileUtil {

	/**
     * 复制文件
     * 通过该方式复制文件文件越大速度越是明显
     *
     * @param file       需要处理的文件
     * @param targetFile 目标文件
     * @return 是否成功
     */
    public final static boolean copy(File file, String targetFile) {
        Integer BUFFER_SIZE = 1024 * 1024 * 10;
        try{
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(new File(targetFile));
            FileChannel in = fin.getChannel();
            FileChannel out = fout.getChannel();
            //设定缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (in.read(buffer) != -1) {
                //准备写入，防止其他读取，锁住文件
                buffer.flip();
                out.write(buffer);
                //准备读取。将缓冲区清理完毕，移动文件内部指针
                buffer.clear();
            }
            out.close();
            in.close();
            fout.close();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


	/**
	 * 保存文件。（保留原有文件名）
	 * @param fin 输入文件流
	 * @param destPath 保存路径
	 * @param destFileName 文件名
	 */
	public static void saveFile(InputStream fin, String destPath, String destFileName) throws IOException{

		destPath = StringUtil.attachDirectoryMark(destPath);
		@SuppressWarnings("unused")
		int bytesum = 0;
		int byteread = 0;

		FileOutputStream fs = new FileOutputStream(destPath + destFileName);
		byte[] buffer = new byte[1024*1024*10];
		while ((byteread = fin.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		fin.close();
	}
	
/**
 * 设置文件夹或者 文件 的读写权限
 * @param destPath
 * @throws IOException
 */
public static void changeFolderPermission(String destPath) throws IOException {
	File destFolder = new File(destPath);	
    Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    perms.add(PosixFilePermission.OWNER_EXECUTE);
    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    perms.add(PosixFilePermission.GROUP_EXECUTE);
    perms.add(PosixFilePermission.OTHERS_READ);
    perms.add(PosixFilePermission.OTHERS_EXECUTE);
    
    try {
        Path path = Paths.get(destFolder.getAbsolutePath());
        Files.setPosixFilePermissions(path, perms);
    } catch (Exception e) {
        System.out.println("Change folder " + destFolder.getAbsolutePath() + " permission failed."+e);
    }
}

	/**
	 * 求文件的后缀名
	 * @param fileName 文件名（带后缀）
	 * @return 文件的后缀名
	 */
	public static String getFileExt(String fileName) {
		
		// 用逗号分隔
		String[] tmpArr = fileName.split("\\.");
		if (ValidUtil.valid(tmpArr) && tmpArr.length >= 2) {
			return "." + tmpArr[tmpArr.length - 1];
		}

		return null;
	}
	
	/**
	 * 求文件名称包含后缀名
	 * @param path 文件路径
	 * @return 文件名称 
	 */
	public static String getFileNameOfPath(String path) {
		
		path = path.trim();  
		return path.substring(path.lastIndexOf("/")+1); 
	}
	
	/**
	 * 确保保存路径有效，如果路径不存在，创建之。
	 * @param destPath 文件的保存路径
	 */
	public static void confirmFolderExists(String destPath) {
		
		File destFolder = new File(destPath);
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}		
	}
	
	/**
	 * 确保保存路径有效，如果路径不存在，创建之。
	 * @param fileFullPath 文件的保存路径
	 */
	public static void createEmptyFile(String fileFullPath) {
		
		File file = new File(fileFullPath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * @param filePath
	 * @param resp
	 * @throws Exception
	 */
	public static void downloadResource(String filePath, HttpServletResponse resp) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType("application/octet-stream; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment;filename=" +
				new String(file.getName().getBytes("GBK"), "ISO-8859-1"));

		InputStream stream = null;
		OutputStream os = null;
		try {
			stream = new FileInputStream(file);

			os = resp.getOutputStream();

			int readBytes = 0;

			byte buffer[] = new byte[8192];

			while ((readBytes = stream.read(buffer, 0, 8192)) != -1) {

				os.write(buffer, 0, readBytes);

			}
			os.flush();
			os.close();

			stream.close();

		} catch (Exception ex) {
			if (os != null) {
				os.close();
			}
			if (stream != null) {
				stream.close();
			}
			throw ex;
		}finally{
			if (os != null) {
				os.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}
}