package cn.cerestech.framework.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;

import com.google.common.io.Files;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Images {

	private BufferedImage image = null;
	private String type;

	public Images(byte[] bytes, String type) {
		try {
			image = ImageIO.read(new ByteArrayInputStream(bytes));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		this.type = type;
	}

	public Images(BufferedImage image, String type) {
		this.image = image;
		this.type = type;
	}

	public byte[] toBytes() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

	public String toImageData() {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("data:image/");
			buffer.append(type);
			buffer.append(";base64,");
			buffer.append(Base64.getEncoder().encodeToString(toBytes()));
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	public Images jpgZip(float quality) {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		try {
//			/* 输出到文件流 */
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image);
//			/* 压缩质量 */
//			jep.setQuality(quality, true);
//			encoder.encode(image, jep);
//			/* 近JPEG编码 */
//		} catch (Exception e) {
//		}
//		byte[] bytes = output.toByteArray();
//		return new Images(bytes, type);
//	}

	/**
	 * 拉伸图片大小
	 * 
	 * @param imgFilePath
	 * @param w
	 * @param h
	 * @return
	 */
	public Images stretchTo(Integer w, Integer h) {
		BufferedImage to = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2d = to.createGraphics();  
        to = g2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);  
        g2d.dispose();  
        g2d = to.createGraphics();  

        Image from = image.getScaledInstance(w, h, image.SCALE_AREA_AVERAGING);  
        g2d.drawImage(from, 0, 0, null);
        g2d.dispose();
        return new Images(to, type);
	}
	
	public static void main(String[] args) throws Exception {
		String maskPath = "/Users/bird/Desktop/水印(1).png";
		String src = "/filestorage/UserPost/220/1420543122733.jpg";
		String text = "一起夺宝";
//		Font font = new Font("宋体", Font.BOLD, 48);
//		Files.write(new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(text, font, 1.0f).toBytes()
//				, new File("/Users/bird/Desktop/texta.jpg"));
//		Files.write(new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(text, font, 0.7f).toBytes()
//				, new File("/Users/bird/Desktop/textb.jpg"));
//		Files.write(new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(text, font, 0.5f).toBytes()
//				, new File("/Users/bird/Desktop/textc.jpg"));
//		Files.write(new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(text, font, 0.2f).toBytes()
//				, new File("/Users/bird/Desktop/textd.jpg"));
		
		
//		
		Images img = new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(maskPath, 1.0f);
		Files.write(img.toBytes(), new File("/Users/bird/Desktop/aaa.jpg"));
//		
//		Images imgb = new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(maskPath, 0.5f);
//		Files.write(imgb.toBytes(), new File("/Users/bird/Desktop/bb.jpg"));
//		
//		Images imgc = new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(maskPath, 0.3f);
//		Files.write(imgc.toBytes(), new File("/Users/bird/Desktop/cc.jpg"));
//		
//		Images imgd = new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(maskPath, 0.2f);
//		Files.write(imgd.toBytes(), new File("/Users/bird/Desktop/dd.jpg"));
//		
//		Images imge = new Images(ImageIO.read(new File(src)), Files.getFileExtension(src)).mask(maskPath, 0.1f);
//		Files.write(imge.toBytes(), new File("/Users/bird/Desktop/ee.jpg"));
		
		//Files.write(new Images(ImageIO.read(new File(maskPath)), Files.getFileExtension(maskPath)).stretchTo(50, 50).toBytes(), new File("/Users/bird/Desktop/mm.png"));
	}

	/**
	 * 根据正中进行对称拉伸裁剪。会自动匹配和计算最合适的大小进行裁剪。
	 * 
	 * @param image
	 * @param w
	 * @param h
	 * @return
	 * @throws IOException
	 */
	public Images stretchCenterClip(Integer w, Integer h) {
		Integer ow = image.getWidth();
		Integer oh = image.getHeight();
		// 按照给定的长宽最短的那条边进行缩放
		BigDecimal scale = BigDecimal.ONE;// 缩放比例
		if (ow > oh) {
			scale = new BigDecimal(h).divide(new BigDecimal(oh), 5, BigDecimal.ROUND_UP);
		} else {
			scale = new BigDecimal(w).divide(new BigDecimal(ow), 5, BigDecimal.ROUND_UP);
		}
		// 决定伸缩后的宽高
		int wNew = new BigDecimal(ow).multiply(scale).setScale(0, BigDecimal.ROUND_UP).intValue();
		int hNew = new BigDecimal(oh).multiply(scale).setScale(0, BigDecimal.ROUND_UP).intValue();
		// 伸缩图片
		Images targetImages = stretchTo(wNew, hNew);

		// 以中心点为准裁剪图形
		return targetImages.clip((wNew - w) / 2, (hNew - h) / 2, w, h);
	}

	/*
	 * 图片裁剪通用接口
	 */

	public Images clip(int x, int y, int w, int h) {

		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = dst.createGraphics();
		g.drawRenderedImage(image, AffineTransform.getTranslateInstance(-x, -y));
		g.dispose();

		return new Images(dst, type);

	}

	/**
	 * 返回图片大小 KB
	 * 
	 * @param f
	 * @return
	 */
	public int size() {
		return toBytes().length;
	}

	/**
	 * 压缩图片质量，不改变宽高
	 * 
	 * @param srcFilePath
	 * @param ratio
	 * @return
	 */
	public Images compress(float ratio) {
		ByteArrayOutputStream bf = new ByteArrayOutputStream();
		try {
			ImageWriter imgWriter = ImageIO.getImageWritersByFormatName(type).next();

			ImageWriteParam imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);// 指定写图片的方式为
			// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
			imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

			// 这里指定压缩的程度，参数qality是取值0~1范围内，
			imgWriteParams.setCompressionQuality(ratio);
			imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

			ColorModel colorModel = ColorModel.getRGBdefault();
			// 指定压缩时使用的色彩模式
			imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

			imgWriter.reset();
			// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造

			imgWriter.setOutput(ImageIO.createImageOutputStream(bf));
			// 调用write方法，就可以向输入流写图片
			imgWriter.write(null, new IIOImage(image, null, null), imgWriteParams);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new Images(bf.toByteArray(), type);
	}
	
	public Images mask(String maskText, Font font, float alpha){
		if(font == null){
			font = new Font("宋体", Font.BOLD, 24);
		}
		
		int length = maskText.length();
		for (int i = 0; i < maskText.length(); i++) {
			if (String.valueOf(maskText.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		int act =  (length % 2 == 0) ? length / 2 : length / 2 + 1;//实际存储字符数
		
		int fontSize = font.getSize();
		
		int imgw = image.getWidth();
		int imgh = image.getHeight();
		
		if(fontSize >= imgh){
			fontSize = imgh *3 / 4; 
		}
		int actLen = act * fontSize;
		
		if (imgw <= actLen) {
			actLen = imgw * 3 / 4;
		}
		//实际文字像素值
		fontSize = actLen / act;
		//计算偏离
		int offsetx = (imgw - actLen)/2;
		int offsety = (imgh - fontSize)/2;
		//重置font 的文字大小
		font = new Font(font.getName(), font.getStyle(), fontSize);
		//打码
		Graphics2D g = image.createGraphics();
		
		g.setFont(font);
		g.setColor(Color.white);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		
		g.drawString(maskText, offsetx, offsety);
		
		return new Images(image, type);
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Images mask(String maskImgPath, float alpha) throws IOException{
		BufferedImage mask = ImageIO.read(new File(maskImgPath));
		int mw = mask.getWidth();
		int mh = mask.getHeight();
		
		int sw = image.getWidth();
		int sh = image.getHeight();
		
		
		int maskw = mw, maskh =mh;
		Images maskImg = new Images(mask, Files.getFileExtension(maskImgPath));
		if(mw > sw || mh > sh){
			if(sw*1.0/(1.0*mw) > sh*1.0/(1.0*mh)){//按照原图片高度进行缩放水印
				maskh = sh * 2 / 3;
				maskw = new BigDecimal(maskw * (maskh*1.0/sh)).intValue();
			}else{//按照原图片宽度进行缩放水印图片
				maskw = sw * 2 /3;
				maskh = new BigDecimal(maskh * (maskw*1.0/sw)).intValue();
			}
			
			//压缩水印
			maskImg = maskImg.stretchTo(maskw, maskh);
		}
		
		return mask(maskImg, (sw-maskw)/2, (sh-maskh)/2, alpha);
	}
	
	public Images mask(Images maskImg, int offsetx, int offsety, float alpha){
		ImageIcon icon = new ImageIcon(maskImg.image);
		
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		// 水印文件
		g.drawImage(icon.getImage(), offsetx, offsety , maskImg.image.getWidth(), maskImg.image.getHeight(), null);
		//
		g.dispose();
		
		return new Images(image, type);
	}

}
