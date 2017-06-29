package sos.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;


public class FileUtil
{
	public static boolean createNewFile(String path) throws IOException
	{
		File file = new File(path);
		createParentPath(file);
		return file.createNewFile();
	}
	
	public static boolean createParentPath(File file)
	{
		File parent = file.getParentFile();
		return parent.mkdirs();
	}
	
	public static boolean rename(File srcFile, String newName)
	{
		File file = new File(srcFile.getParent()+File.separator+newName);
		createParentPath(file);
		return srcFile.renameTo(file);
	}
	
	public static boolean cutFile(File srcFile, String descDir)
	{
		File file = new File(descDir+File.separator+srcFile.getName());
		createParentPath(file);
		return srcFile.renameTo(file);
	}
	
	public static boolean renameTo(File srcFile, File descFile)
	{
		createParentPath(descFile);
		return srcFile.renameTo(descFile);
	}
	
	public static boolean copyFile(File srcFile, String descPath)
	{
		boolean flag = true;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try
		{
			fis = IOUtil.getFileInputStream(srcFile.getAbsolutePath());
			File fosFile = new File(descPath);
			createParentPath(fosFile);
			fos = IOUtil.getFileOutputStream(descPath, false);
			byte[] b = new byte[4*1024];
			while(fis.read(b)!=-1)
			{
				fos.write(b);
			}
		}
		catch(Exception e)
		{
			flag = false;
		}
		finally
		{
			IOUtil.close(fis);
			IOUtil.close(fos);
		}
		return flag;
	}
	
	public static boolean deleteDir(File file)
	{
		boolean flag = true;
		if(file.isDirectory())
		{
			File[] files = file.listFiles();
			for(int i=0;i<files.length;i++)
			{
				if(files[i].isDirectory())
				{
					flag = deleteDir(files[i]);
				}
				else
				{
					flag = files[i].delete();
				}
				if(!flag)
				{
					break;
				}
			}
			flag = file.delete();
		}
		return flag;
	}
	
	/**
	 * 压缩字符串
	 * @param s 待压缩的字符串内容
	 * @return
	 */
	public static byte[] compressString(String s){
		try{
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			GZIPOutputStream gzos = new GZIPOutputStream(baos);
			gzos.write(s.getBytes());
			gzos.close();
			return baos.toByteArray();
		}catch(Exception ex){}
		return null;
	}
	
	/**
	 * 解压流
	 * @param b 待解压的内容
	 * @return
	 */
	public static byte[] decompressString(byte[] b){
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			GZIPInputStream gzis = new GZIPInputStream(bais);
			b = new byte[1024];
			for(int i=-1; (i=gzis.read(b, 0, 1024))!=-1;){
				baos.write(b, 0, i);
			}
			return baos.toByteArray();
		}catch(Exception ex){}
		return null;
	}
	
	/**
	 * 生成文件
	 * 
	 * @param value
	 *            文件内容
	 * @param filePath
	 *            文件路径
	 */
	public static void buildFile(String value, String filePath) throws Exception {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
			fileWriter.write(value);
		} finally {
			IOUtils.closeQuietly(fileWriter);
		}
	}
}
