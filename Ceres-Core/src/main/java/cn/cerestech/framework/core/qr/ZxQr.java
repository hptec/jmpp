package cn.cerestech.framework.core.qr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
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

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月1日
 */
public class ZxQr {
	private static final Logger LOG = LogManager.getLogger();
	
	private String _encoding = "GB2312";
	private BarcodeFormat _qr_format = BarcodeFormat.QR_CODE;

	private Map<EncodeHintType, Object> _hints = Maps.newHashMap();
	private int _bar_color =  MatrixToImageConfig.BLACK;
	private int _blank_color =  MatrixToImageConfig.WHITE;
	private int _w = 200,_h=200;
	
	
	private String _qr_content;
	private BufferedImage _qr_img;
	
	private ZxQr(){
	}
	
	public static ZxQr of(){
		return new ZxQr();
	}
	
	public static ZxQr of(String content){
		return of().content(content);
	}
	
	public ZxQr hint(EncodeHintType type, Object obj){
		this._hints.put(type, obj);
		return this;
	}
	
	public ZxQr qrImg(BufferedImage _qr_img){
		this._qr_img = _qr_img;
		return this;
	}
	
	public ZxQr qrImg(String qrImgAbsolutePath){
		try {
			this._qr_img = ImageIO.read(new File(qrImgAbsolutePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public ZxQr format(BarcodeFormat format){
		this._qr_format = format;
		return this;
	}
	
	public ZxQr size(int w, int h){
		this._w = w;
		this._h = h;
		return this;
	}
	
	public ZxQr color(int bar_color, int blank_color){
		this._bar_color = bar_color;
		this._blank_color = blank_color;
		return this;
	}
	
	public ZxQr content(String qrCnt){
		this._qr_content = qrCnt;
		return this;
	}
	
	/**
	 * 生成二维码
	 */
	public ZxQr encode(){
		this._qr_content = Strings.nullToEmpty(this._qr_content);
		this._qr_format = this._qr_format == null? BarcodeFormat.QR_CODE:this._qr_format;
		this._bar_color = this._bar_color==-1? MatrixToImageConfig.BLACK : this._bar_color;
		this._blank_color = this._blank_color==-1? MatrixToImageConfig.WHITE : this._blank_color;
		
		if(this._hints == null || this._hints.size() ==0 ){
			this._hints = Maps.newHashMap();
		}
		//设置编码格式
		if(this._hints.get(EncodeHintType.CHARACTER_SET) == null){
			this.hint(EncodeHintType.CHARACTER_SET, this._encoding);
		}
		//边框间距
		if(this._hints.get(EncodeHintType.MARGIN) == null){
			this.hint(EncodeHintType.MARGIN, 0);
		}
		
		MultiFormatWriter writer = new MultiFormatWriter();
		try {
			BitMatrix martrix = writer.encode(this._qr_content, this._qr_format, this._w, this._h, this._hints);
			
			try {
				MatrixToImageConfig config = new MatrixToImageConfig(this._bar_color, this._blank_color);
				BufferedImage img = MatrixToImageWriter.toBufferedImage(martrix, config);
				this._qr_img = img;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public BufferedImage getQrImg(){
		if(this._qr_img == null){
			this.encode();
		}
		return this._qr_img;
	}
	
	public void saveQrImg(String destQrImgFileAbsolutePath){
		BufferedImage bufImg = getQrImg();
		if(bufImg != null){
			try {
				ImageIO.write(bufImg, Files.getFileExtension(destQrImgFileAbsolutePath), new File(destQrImgFileAbsolutePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将二维码覆盖到
	 * @param backGroundImg
	 * @param xstart
	 * @param ystart
	 * @param drawBackGround
	 * @return
	 */
	public BufferedImage coverToImg(BufferedImage backGroundImg, int xstart, int ystart, boolean drawBackGround){
		try {
			if(backGroundImg == null){
				LOG.warn("无背景图片");
				return null;	
			}
			if(this._qr_content == null && this._qr_img == null){
				LOG.warn("覆盖的二维码内容为空");
				return backGroundImg;
			}
			if(this._qr_img == null){
				this.getQrImg();
			}
			if(this._qr_img == null){
				LOG.warn("生成二维码失败！");
				return backGroundImg;
			}
			
			int dh = this._qr_img.getHeight();
			int dw = this._qr_img.getWidth();
			
			int bh = backGroundImg.getHeight();
			int bw = backGroundImg.getWidth();
			
			for (int h = 0; h < dh; h++) {
				for (int w = 0; w < dw; w++) {
					if((w+xstart < bw && h+ystart < bh) && (drawBackGround || (!drawBackGround && this._qr_img.getRGB(w, h) != -1))){
						backGroundImg.setRGB(w+xstart, h+ystart, this._qr_img.getRGB(w, h));;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return backGroundImg;
	}
	
	public String decode(){
		if(this._qr_img == null){
			LOG.warn("未设置解码图片");
			return null;
		}
		
		LuminanceSource source = new BufferedImageLuminanceSource(this._qr_img);
		BinaryBitmap bitMap = new BinaryBitmap(new HybridBinarizer(source));
		
		MultiFormatReader reader = new MultiFormatReader();
		try {
			Result res = reader.decodeWithState(bitMap);
			String text = res.getText();
			this._qr_content = text;
			return text;
		} catch (NotFoundException e) {
			LOG.warn("二维码解码错误："+e.getMessage());
		}
		return null;
	}
	
}
