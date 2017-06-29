package com.onedob.util;

import com.onedob.util.pojo.QRCodeImageObj;
import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class QRCodeUtil {

	/** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param imgPath ͼƬ·�� 
     */  
    public void encoderQRCode(String content, String imgPath) {
        this.encoderQRCode(content, imgPath, "png", 7);  
    }  
      
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param output ����� 
     */  
    public void encoderQRCode(String content, OutputStream output) {
        this.encoderQRCode(content, output, "png", 7);  
    }  
      
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param imgPath ͼƬ·�� 
     * @param imgType ͼƬ���� 
     */  
    public void encoderQRCode(String content, String imgPath, String imgType) {
        this.encoderQRCode(content, imgPath, imgType, 7);  
    }  
      
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param output ����� 
     * @param imgType ͼƬ���� 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType) {
        this.encoderQRCode(content, output, imgType, 7);  
    }  
  
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param imgPath ͼƬ·�� 
     * @param imgType ͼƬ���� 
     * @param size ��ά��ߴ� 
     */  
    public void encoderQRCode(String content, String imgPath, String imgType, int size) {
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
              
            File imgFile = new File(imgPath);
            // ���ɶ�ά��QRCodeͼƬ  
            ImageIO.write(bufImg, imgType, imgFile);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ 
     * @param content �洢���� 
     * @param output ����� 
     * @param imgType ͼƬ���� 
     * @param size ��ά��ߴ� 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType, int size) {
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);
            // ���ɶ�ά��QRCodeͼƬ  
            ImageIO.write(bufImg, imgType, output);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * ���ɶ�ά��(QRCode)ͼƬ�Ĺ������� 
     * @param content �洢���� 
     * @param imgType ͼƬ���� 
     * @param size ��ά��ߴ� 
     * @return 
     */  
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {
        BufferedImage bufImg = null;
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // ���ö�ά���Ŵ��ʣ���ѡL(7%)��M(15%)��Q(25%)��H(30%)���Ŵ���Խ�߿ɴ洢����ϢԽ�٣����Զ�ά�������ȵ�Ҫ��ԽС  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            // �������ö�ά��ߴ磬ȡֵ��Χ1-40��ֵԽ��ߴ�Խ�󣬿ɴ洢����ϢԽ��  
            qrcodeHandler.setQrcodeVersion(size);  
            // ������ݵ��ֽ����飬���ñ����ʽ  
            byte[] contentBytes = content.getBytes("utf-8");  
            // ͼƬ�ߴ�  
            int imgSize = 67 + 12 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            // ���ñ�����ɫ  
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, imgSize, imgSize);  
  
            // �趨ͼ����ɫ> BLACK  
            gs.setColor(Color.BLACK);
            // ����ƫ�����������ÿ��ܵ��½�������  
            int pixoff = 2;  
            // �������> ��ά��  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
            }  
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return bufImg;  
    }  
      
    /** 
     * ������ά�루QRCode�� 
     * @param imgPath ͼƬ·�� 
     * @return 
     */  
    public String decoderQRCode(String imgPath) {
        // QRCode ��ά��ͼƬ���ļ�  
        File imageFile = new File(imgPath);
        BufferedImage bufImg = null;
        String content = null;
        try {  
            bufImg = ImageIO.read(imageFile);
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new QRCodeImageObj(bufImg)), "utf-8");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();  
        }  
        return content;  
    }  
      
    /** 
     * ������ά�루QRCode�� 
     * @param input ������ 
     * @return 
     */  
    public String decoderQRCode(InputStream input) {
        BufferedImage bufImg = null;
        String content = null;
        try {  
            bufImg = ImageIO.read(input);
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new QRCodeImageObj(bufImg)), "utf-8");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());
            dfe.printStackTrace();  
        }  
        return content;  
    }  
  
    public static void main(String[] args) {
        String imgPath = "G:/TDDOWNLOAD/Michael_QRCode.png";
        String encoderContent = "Hello ���СС,welcome to QRCode!" + "\nMyblog [ http://sjsky.iteye.com ]" + "\nEMail [ sjsky007@gmail.com ]";
        QRCodeUtil handler = new QRCodeUtil();  
        handler.encoderQRCode(encoderContent, imgPath, "png");  
//      try {  
//          OutputStream output = new FileOutputStream(imgPath);  
//          handler.encoderQRCode(content, output);  
//      } catch (Exception e) {  
//          e.printStackTrace();  
//      }  
        System.out.println("========encoder success");
          
          
        String decoderContent = handler.decoderQRCode(imgPath);
        System.out.println("����������£�");
        System.out.println(decoderContent);
        System.out.println("========decoder success!!!");
    } 
}
