package sos.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class FileUploadUtil {
	protected static final Logger logger = Logger.getLogger(FileUploadUtil.class);
	
	/**上传文件 
	 * @param postUrl 上传服务请求URL
	 * @param filePath 上传文件本地路径
	 * @return JSONObject 服务返回JSON对象
	 * @throws Exception
	 */
	public static JSONObject uploadFile(String postUrl,String filePath,String parentDir) throws Exception{
		JSONObject json = null;
		StringBuffer buffer = new StringBuffer();
		File file = new File(filePath);
		if(file.exists()){
			String reqUrl = postUrl+"?parentDir="+parentDir;
			System.out.println(reqUrl);
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
			
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");

			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			
			byte[] head = sb.toString().getBytes("utf-8");
			OutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(head);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8"); 
			out.write(foot);
			out.flush();
			out.close();
			
			InputStream inputStream = con.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream = null;
			con.disconnect();
			json = JSONObject.fromObject(buffer.toString());
			
		}
		return json;
	}
	
	public static void main(String[] args) {
		try {
			String url = "http://127.0.0.1:8080/ssres/upload";
			String filePath = "E:/桌面/文件夹/科比图片/2e2eb9389b504fc2b112a52ae4dde71190ef6d4b.jpg";
			System.out.println(FileUploadUtil.uploadFile(url, filePath,"test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
