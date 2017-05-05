package com.android.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	/**
	 * 打开文件，默认编码字符串.
	 * @param path  文件完整路径
	 * @return
	 */
	public static String readFileToString(String path) {
		byte[] data = readFileToByte(path);
		if (data != null) {
			return new String(data);
		}
		return null;
	}
	
    /**
     * 通过文件路径初始化文件和文件夹
     * @param filePath
     * @throws IOException
     */
    private static void initFileByPath(String filePath) throws IOException {
    	File file = new File(filePath);
		if (!file.exists()) {
			file.getParentFile().mkdir();
			file.createNewFile();
		}
    }
	/**
	 * 打开文件，返回直接数组.
	 * @param path  文件完整路径
	 * @return
	 */
	public static byte[] readFileToByte(String path) {
		File file = new File(path);
		if (file.exists()) {
			FileInputStream fis = null;
			ByteArrayOutputStream bos = null;
			try {
				fis = new FileInputStream(file);
				bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = fis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				byte[] data = bos.toByteArray();
				return data;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (fis != null) fis.close();
					if (bos != null) bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 检查指定路径文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean isExists(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * 创建目录，可以创建多级目录, 文件夹路径分隔符必须是"/".
	 * @param path 
	 */
	public static boolean createDir(String path) {
		File file = new File(path);
		if (file.exists()) 
			return true;
		return file.mkdirs();
	}

	/**
	 * 保存文件, 如果路径不存在，自动创建路径
	 * @param path  文件路径
	 * @param data
	 */
	public static boolean saveFile(String path, byte[] data) {
		return saveFile(path, data, false);
	}
	/**
	 * 保存文件, 如果路径不存在，自动创建路径
	 * @param path   文件路径
	 * @param append 如果文件存在，true代表数据附加在之前的数据后面， false代表之前的数据被覆盖. 
	 * @param data
	 */
	public static boolean saveFile(String path, byte[] data, boolean append) {
		if (data != null) {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) return false;
			}
			try {
				if (!file.exists()) file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file, append);
				fos.write(data);
				fos.flush();
				fos.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 删除文件,如果是目录，则删除整个目录。
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) deleteFiles(file);
			file.delete();
		}
	}
	/**
	 * 删除指定目录下的所有文件。
	 * @param path
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		deleteFiles(file);
	}
	
	/**
	 * 删除目录下的所有文件。
	 * @param file
	 */
	public static void deleteFiles(File file) {
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				file2.delete();
			}
		}
	}
	public static String getRootDir(String defDir) {
		if (SystemUtil.isExtStoreAva())
			return Environment.getExternalStorageDirectory().getAbsolutePath() + defDir;
		return "";
	}
	
	/**保存Bitmap到sdcard
	 * @param b
	 */
	public static void saveBitmap(Bitmap b, String filePath){
		try {
			FileOutputStream fout = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				OutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	final static int BUFFER_SIZE = 4096;

	/**
	 * 将InputStream转换成byte数组
	 * @param in InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException{

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while((count = in.read(data,0,BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	/**
	 * 将byte数组转换成InputStream
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteTOInputStream(byte[] in) throws Exception{

		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}
}
