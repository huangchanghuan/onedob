package com.onedob.util;

import java.io.*;

public class IOUtil
{
/*	public static void createParentPath(File file)
	{
		File parentFile = file.getParentFile();
		if(parentFile!=null)
		{
			if(!parentFile.exists()||!parentFile.isDirectory())
			{
				createParentPath(parentFile);
				parentFile.mkdir();
			}
		}
	}*/
	
	public static BufferedReader getBufferedReader(String filePath) throws FileNotFoundException
	{
		InputStream is = getFileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		return br;
	}
	
	public static PrintWriter getPrintWriter(String filePath, boolean append) throws FileNotFoundException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		PrintWriter pw = new PrintWriter(fos);
		return pw;
	}
	
	public static ObjectInputStream getObjectInputStream(String filePath) throws IOException
	{
		FileInputStream fis = getFileInputStream(filePath);
		ObjectInputStream ois = new ObjectInputStream(fis);
		return ois;
	}
	
	public static ObjectOutputStream getObjectOutputStream(String filePath, boolean append) throws IOException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		return oos;
	}
	
	public static FileInputStream getFileInputStream(String filePath) throws FileNotFoundException
	{
		FileInputStream fis = new FileInputStream(filePath);
		return fis;
	}
	
	public static FileOutputStream getFileOutputStream(String filePath, boolean append) throws FileNotFoundException
	{
		FileOutputStream fos = new FileOutputStream(filePath,append);
		return fos;
	}
	
	public static BufferedInputStream getBufferedInputStream(String filePath) throws FileNotFoundException
	{
		FileInputStream fis = getFileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		return bis;
	}
	
	public static BufferedOutputStream getBufferedOutputStream(String filePath, boolean append) throws FileNotFoundException
	{
		FileOutputStream fos = getFileOutputStream(filePath,append);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		return bos;
	}
	
	public static DataInputStream getDataInputStream(String filePath, boolean buffered) throws FileNotFoundException
	{
		DataInputStream dis = null;
		if(buffered)
		{
			BufferedInputStream bis = getBufferedInputStream(filePath);
			dis = new DataInputStream(bis);
		}
		else
		{
			FileInputStream fis = getFileInputStream(filePath);
			dis = new DataInputStream(fis);
		}
		return dis;
	}
	
	public static DataOutputStream getDataOutputStream(String filePath, boolean append, boolean buffered) throws FileNotFoundException
	{
		DataOutputStream dos = null;
		if(buffered)
		{
			BufferedOutputStream bos = getBufferedOutputStream(filePath,append);
			dos = new DataOutputStream(bos);
		}
		else
		{
			FileOutputStream fos = getFileOutputStream(filePath,append);
			dos = new DataOutputStream(fos);
		}
		return dos;
	}
	
	public static RandomAccessFile getRandomAccessFile(String name, String mode) throws FileNotFoundException
	{
		RandomAccessFile raf = new RandomAccessFile(name, mode);
		return raf;
	}
	
	public static void close(Object o)
	{
		if(o!=null)
		{
			try
			{
				if(o instanceof BufferedReader)
				{
					((BufferedReader)o).close();
				}
				else if(o instanceof PrintWriter)
				{
					((PrintWriter)o).close();
				}
				else if(o instanceof ObjectInputStream)
				{
					((ObjectInputStream)o).close();
				}
				else if(o instanceof ObjectOutputStream)
				{
					((ObjectOutputStream)o).close();
				}
				else if(o instanceof FileInputStream)
				{
					((FileInputStream)o).close();
				}
				else if(o instanceof FileOutputStream)
				{
					((FileOutputStream)o).close();
				}
				else if(o instanceof BufferedInputStream)
				{
					((BufferedInputStream)o).close();
				}
				else if(o instanceof BufferedOutputStream)
				{
					((BufferedOutputStream)o).close();
				}
				else if(o instanceof DataInputStream)
				{
					((DataInputStream)o).close();
				}
				else if(o instanceof DataOutputStream)
				{
					((DataOutputStream)o).close();
				}
				else if(o instanceof RandomAccessFile)
				{
					((RandomAccessFile)o).close();
				}
			}
			catch(Exception e){}
		}
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	} 
}
