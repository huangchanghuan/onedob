package com.sandra.util;

import org.apache.batik.ext.awt.image.codec.tiff.TIFFDecodeParam;
import org.apache.batik.ext.awt.image.codec.tiff.TIFFImageDecoder;
import org.apache.batik.ext.awt.image.codec.util.SeekableStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Hashtable;

/**
 * 图片操作类
 * @author pine
 * @date   2009-10-29
 */
public class ImageUtil {
	
	/**
	 * 根据传入的缩放比例对图片进行缩放
	 * 支持JPG/PNG/BMP/GIF，不支持TIF 	 
	 * @param imagePath		源路径	
	 * @param resultPath	目标路径
	 * @param zoomPercent	缩放百分比	
	 * @return ret          压缩成功返回true；否则返回false
	 */
	public static boolean imageZoom(String imagePath, String resultPath, int zoomPercent) {
		FileOutputStream out = null;
		boolean ret = false;
		
		try {  
			BufferedImage src = ImageIO.read(new File(imagePath)); 		//读入文件
		    int width = src.getWidth(); 								//得到源图宽   
		    int height = src.getHeight(); 								//得到源图长 
		    width = width*zoomPercent/100;								//缩放后的图宽
		    height = height*zoomPercent/100;							//缩放后的图长
		                     
		    //根据缩放比例获取新的图像实例   
		    Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  		 //缩放图像
		    BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//缩放大小
		    
		    Graphics g = tag.getGraphics();
		    g.drawImage(image, 0, 0, null); 							//绘制缩小后的图 		    
		    g.dispose();   
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(tag,"JPEG",bos);								//输出到bos
		        
		    out = new FileOutputStream(resultPath);
		    out.write(bos.toByteArray());  								//写文件
		    ret = true;
		} catch (Exception e) {
		    e.printStackTrace();   
		} finally{
			if(out != null) { try{out.close();}catch(Exception e){} }
		}	
		return ret;
	}
	
	/**
	 * 根据传入的缩放比例对图片进行缩放
	 * 支持JPG/PNG/BMP/GIF，不支持TIF 
	 * @param file			源文件	
	 * @param resultPath	目标路径
	 * @param zoomPercent	缩放百分比	
	 * @return ret          压缩成功返回true；否则返回false
	 */
	public static boolean imageZoom(File file, String resultPath, int zoomPercent) {
		FileOutputStream out = null;
		boolean ret = false;
		
		try {  
			BufferedImage src = ImageIO.read(file); 		//读入文件
		    int width = src.getWidth(); 								//得到源图宽   
		    int height = src.getHeight(); 								//得到源图长 
		    width = width*zoomPercent/100;								//缩放后的图宽
		    height = height*zoomPercent/100;							//缩放后的图长
		                     
		    //根据缩放比例获取新的图像实例   
		    Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  		 //缩放图像
		    BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//缩放大小
		    
		    Graphics g = tag.getGraphics();
		    g.drawImage(image, 0, 0, null); 							//绘制缩小后的图 		    
		    g.dispose();   
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(tag,"JPEG",bos);								//输出到bos
		        
		    out = new FileOutputStream(resultPath);
		    out.write(bos.toByteArray());  								//写文件
		    ret = true;
		} catch (Exception e) {
		    e.printStackTrace();   
		} finally{
			if(out != null) { try{out.close();}catch(Exception e){} }
		}	
		return ret;
	}
	
	/**
	 * 根据传入的缩放比例对图片进行缩放
	 * 支持TIF 
	 * @param imagePath		源路径	
	 * @param resultPath	目标路径
	 * @param zoomPercent	缩放百分比	
	 * @return ret          压缩成功返回true；否则返回false
	 */
	public static boolean tiffZoom(String imagePath, String resultPath, int zoomPercent) {
		InputStream tmpImage = null;
		FileOutputStream out = null;
		boolean ret = false;
		
		try{
			tmpImage = new FileInputStream(new File(imagePath));
	        SeekableStream ss = SeekableStream.wrapInputStream(tmpImage, true);
	        TIFFDecodeParam param = new TIFFDecodeParam();
	        TIFFImageDecoder decoder = new TIFFImageDecoder(ss,param);
	        RenderedImage ri = decoder.decodeAsRenderedImage();
	        BufferedImage src = convertRenderedImageToBufferedImage(ri);
	        
	        int width = src.getWidth(); 								//得到源图宽   
		    int height = src.getHeight(); 								//得到源图长 
		    width = width*zoomPercent/100;								//缩放后的图宽
		    height = height*zoomPercent/100;							//缩放后的图长
		                     
		    //根据缩放比例获取新的图像实例   
		    Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  		 //缩放图像
		    BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//缩放大小
		    
		    Graphics g = tag.getGraphics();
		    g.drawImage(image, 0, 0, null); 							//绘制缩小后的图 		    
		    g.dispose();   
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(tag,"JPEG",bos);								//输出到bos
		        
		    out = new FileOutputStream(resultPath);
		    out.write(bos.toByteArray());  								//写文件	        	
	        ret = true;
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }finally{
	    	if(tmpImage != null) { try{tmpImage.close();}catch(Exception e){} }
			if(out != null) { try{out.close();}catch(Exception e){} }
		}	
	    return ret;
	}
	
	/**
	 * 根据传入的缩放比例对图片进行缩放
	 * 支持TIF 
	 * @param file			源文件	
	 * @param resultPath	目标路径
	 * @param zoomPercent	缩放百分比	
	 * @return ret          压缩成功返回true；否则返回false
	 */
	public static boolean tiffZoom(File file, String resultPath, int zoomPercent) {
		InputStream tmpImage = null;
		FileOutputStream out = null;
		boolean ret = false;
		
		try{
			tmpImage = new FileInputStream(file);
	        SeekableStream ss = SeekableStream.wrapInputStream(tmpImage, true);
	        TIFFDecodeParam param = new TIFFDecodeParam();
	        TIFFImageDecoder decoder = new TIFFImageDecoder(ss,param);
	        RenderedImage ri = decoder.decodeAsRenderedImage();
	        BufferedImage src = convertRenderedImageToBufferedImage(ri);
	        
	        int width = src.getWidth(); 								//得到源图宽   
		    int height = src.getHeight(); 								//得到源图长 
		    width = width*zoomPercent/100;								//缩放后的图宽
		    height = height*zoomPercent/100;							//缩放后的图长
		                     
		    //根据缩放比例获取新的图像实例   
		    Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  		 //缩放图像
		    BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//缩放大小
		    
		    Graphics g = tag.getGraphics();
		    g.drawImage(image, 0, 0, null); 							//绘制缩小后的图 		    
		    g.dispose();   
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(tag,"JPEG",bos);								//输出到bos
		        
		    out = new FileOutputStream(resultPath);
		    out.write(bos.toByteArray());  								//写文件	        	
	        ret = true;
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }finally{
	    	if(tmpImage != null) { try{tmpImage.close();}catch(Exception e){} }
			if(out != null) { try{out.close();}catch(Exception e){} }
		}	
	    return ret;
	}
	
	public static BufferedImage convertRenderedImageToBufferedImage(final RenderedImage ri){
	    // Return as-is if already a BufferedImage.
	    if(ri instanceof BufferedImage)
	        { return (BufferedImage) ri; }
	
	    // Extract basic metadata.
	    final ColorModel cm = ri.getColorModel();
	    final int width = ri.getWidth();
	    final int height = ri.getHeight();
	    final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	
	    // Extract properties if any.
	    final String[] keys = ri.getPropertyNames();
	    Hashtable properties = null;
	    if(keys != null)
	        {
	        properties = new Hashtable(keys.length * 2 + 1);
	        for(int i = 0; i < keys.length; i++)
	            { properties.put(keys[i], ri.getProperty(keys[i])); }
	        }
	
	    // Construct BufferedImage and copy (raster) data in.
	    final WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
	    final BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
	    ri.copyData(raster);
	
	    return(result);
    }	
	
	public static void main(String[] args) {
		boolean ret = false;
		//目前支持这五种形式：JPG/PNG/BMP/GIF/TIF/ 
		File file = null; 	//图片路径
		String resultPath = "D:\\test\\";
		int zoomPercent = 50;								//缩放比例(%)
		
//		file = new File("D:\\test\\a.jpg");
//		ret = ImageUtil.imageZoom(file, resultPath + "a_mid.jpg", 50);
//		ret = ImageUtil.imageZoom(file, resultPath + "a_low.jpg", 20);
		
		file = new File("D:\\test\\f.tif");
		ret = ImageUtil.tiffZoom(file, resultPath + "f_mid.tif", 50);
		ret = ImageUtil.tiffZoom(file, resultPath + "f_low.tif", 20);
		
		System.out.println("ret: " + ret);
	}
}
