package com.onedob.util.pojo;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

public class QRCodeImageObj implements QRCodeImage {

	BufferedImage bufImg;
    
    public QRCodeImageObj(BufferedImage bufImg) {
        this.bufImg = bufImg;  
    }  
      
    public int getHeight() {  
        return bufImg.getHeight();  
    }  
  
    public int getPixel(int x, int y) {  
        return bufImg.getRGB(x, y);  
    }  
  
    public int getWidth() {  
        return bufImg.getWidth();  
    }  
}
