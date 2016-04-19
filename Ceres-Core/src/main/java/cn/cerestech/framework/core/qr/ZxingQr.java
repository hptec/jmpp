package cn.cerestech.framework.core.qr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class ZxingQr {
	public static final Integer CODE_MARGIN = 1;
	public static final String IMG_FORMAT = "PNG";
	/**
	 * 生成二维码
	 * @param imageColor: 条纹颜色 0xFFFFFF  前两位表示透明度
	 * @param blankColor: 空白颜色 0xFFFFFF  前两位表示透明度
	 */
	public static BufferedImage encode(String content ,BarcodeFormat format,  int imageColor, int blankColor, int width, int height, Map<EncodeHintType, Object> hints){
		content = Strings.nullToEmpty(content);
		format = format == null? BarcodeFormat.QR_CODE:format;
		imageColor = imageColor==-1? MatrixToImageConfig.BLACK : imageColor;
		blankColor = blankColor==-1? MatrixToImageConfig.WHITE : blankColor;
		
		if(hints == null || hints.size() ==0 ){
			hints = Maps.newHashMap();
			hints.put(EncodeHintType.MARGIN, CODE_MARGIN);
		}
		
		MultiFormatWriter writer = new MultiFormatWriter();
		try {
			BitMatrix martrix = writer.encode(content, format, width, height, hints);
			
			try {
				MatrixToImageConfig config = new MatrixToImageConfig(imageColor,blankColor);
				BufferedImage img = MatrixToImageWriter.toBufferedImage(martrix, config);
				return img;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encode2Byte(String content , int width, int height){
		BufferedImage img = encode(content, BarcodeFormat.QR_CODE, -1, -1, width, height, null);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, IMG_FORMAT, out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encode2Base64ImageData(String content , int width, int height){
		byte[] bytes = encode2Byte(content, width, height);
		StringBuffer buffer = new StringBuffer();
		buffer.append("data:image/");
		buffer.append(IMG_FORMAT);
		buffer.append(";base64,");
		buffer.append(new Base64().encodeToString(bytes));
		return buffer.toString();
	}
	
	public static BufferedImage encode(String content, int width, int height){
		return encode(content, BarcodeFormat.QR_CODE, -1, -1, width, height, null);
	}
	
	/**
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @param margin: 边框的值
	 * @return
	 */
	public static BufferedImage encode(String content, int width, int height, int margin){
		Map<EncodeHintType, Object> hints = Maps.newHashMap();
		hints.put(EncodeHintType.MARGIN, new Integer(margin));
		return encode(content, BarcodeFormat.QR_CODE, -1, -1, width, height, hints);
	}
	
	/**
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @param hints
	 * @return
	 */
	public static BufferedImage encode(String content, int width, int height, Map<EncodeHintType, Object> hints){
		return encode(content, BarcodeFormat.QR_CODE, -1, -1, width, height, hints);
	}
	
	public static void encode(String content , int width, int height, File toFile, String imgFormat){
		BufferedImage img = encode(content, null, -1, -1, width, height, null);
		try {
			ImageIO.write(img, imgFormat, toFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void encode(String content , int width, int height, File toFile){
		encode(content, width, height, toFile,IMG_FORMAT);
	}
	
	public static void encode(String content , int width, int height, String imgFormat , OutputStream out){
		BufferedImage img = encode(content, null, -1, -1, width, height, null);
		try {
			ImageIO.write(img, imgFormat, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void encode(String content , int width, int height, OutputStream out){
		encode(content , width, height, IMG_FORMAT , out);
	}
	
	/* DECODE*********************************/
	/**
	 * 解析二维码
	 */
	public static String decode(BinaryBitmap bitMap){
		MultiFormatReader reader = new MultiFormatReader();
		try {
			Result res = reader.decodeWithState(bitMap);
			String text = res.getText();
			System.out.println(text);
			return text;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String decode(File imgFile){
		try {
			BufferedImage img = ImageIO.read(imgFile); 
			LuminanceSource source = new BufferedImageLuminanceSource(img);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			return decode(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decode(String imgFilePath){
		return decode(new File(imgFilePath));
	}
	
}
