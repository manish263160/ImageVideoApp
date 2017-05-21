package com.imagevideoapp.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.imagevideoapp.Enums.RESPONSE_CODES;
import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.ResponseObject;
import com.imagevideoapp.models.User;

public class GenUtilitis {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String AMOUNT_PATTERN = "^((\\d+)|(\\d+\\.\\d{1,2}))$";  //match a number with optional '-' and decimal.
	
	private static final Logger logger = Logger.getLogger(GenUtilitis.class);
	
	private static Cipher cipher;
	private static SecretKey secretKey;
	
	static{
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static ResponseObject getSuccessResponseObject(Object obj) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setData(obj);
		responseObject.setStatus(RESPONSE_CODES.SUCCESS.getDescription());
		responseObject.setStatusCode(RESPONSE_CODES.SUCCESS.getCode());
		
		return responseObject;
	}
	
	public static ResponseObject getSuccessResponseObject(Object obj, String message, int successCode) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setData(obj);
		responseObject.setStatus(RESPONSE_CODES.SUCCESS.getDescription());
		responseObject.setStatusCode(successCode);
		responseObject.setMessage(message);
		return responseObject;
	}
	
	public static ResponseObject getFailureResponseObject(Object obj, String message, int failureCode, String desc) {
		ResponseObject responseObject = new ResponseObject();
		responseObject.setData(obj);
		responseObject.setStatus(RESPONSE_CODES.FAIL.getDescription());
		responseObject.setStatusCode(failureCode);
		responseObject.setMessage(message);
		responseObject.setDescription(desc);
		return responseObject;
	}
	
	public static GenericException getErrorObject(RESPONSE_CODES respoCode) {
		GenericException exception = new GenericException();
		exception.setMessage(respoCode.getMessage());
		exception.setStatus(RESPONSE_CODES.FAIL.getDescription());
		exception.setStatusCode(respoCode.getCode());
		exception.setDescription(respoCode.getDescription());
		logger.error( "getErrorObject() exception: "+exception.getMessage());
		return exception;
	}
	
	public static String generateHash(String toHash) {
		MessageDigest md = null;
		byte[] hash = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			hash = md.digest(toHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return convertToHex(hash);
	}
	 
	private static String convertToHex(byte[] raw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < raw.length; i++) {
			sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public static JSONObject getBody(HttpServletRequest request) throws GenericException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		body = stringBuilder.toString();

		JSONObject json = null;
		if (!body.isEmpty()) {
			try {
				json = new JSONObject(body);
			} catch (JSONException e) {
				e.printStackTrace();
				throw GenUtilitis.getErrorObject(RESPONSE_CODES.INCORRECT_JSON_FORMAT);
			}
		}
		return json;
	}
	
	public static boolean isValidAmount(String str) {
		boolean isValid = false;
		if (!StringUtils.isEmpty(str)) {
			Pattern pattern = Pattern.compile(AMOUNT_PATTERN);
			Matcher matcher = pattern.matcher(str);
			isValid = matcher.matches();
		}
		return isValid;
	}
	
	public static boolean isValidEmail(String str) {
		boolean isValid = false;
		if (!StringUtils.isEmpty(str)) {
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(str);
			isValid = matcher.matches();
		}
		return isValid;
	}
	
	public static int daysBetween(long t1, long t2) {
		return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
	} 
	
	/**
	 * Return Random alphanumeric no. based on count
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomAlphaNumeric(int count) {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	public static File uploadFile(String path, String fileName, MultipartFile file) {
		logger.info( "uploadFile() start");
		InputStream inputStream = null;
		OutputStream outputStream = null;

		File newFile = null;
		try {
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			inputStream = file.getInputStream();
			newFile = new File(path+ fileName);
			if(null!=newFile)
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[(int) (file.getSize())];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
			newFile=null;
		}finally{
			try{
				if(outputStream != null){
					outputStream.close();
				}
			}catch(Exception e){
				logger.error("uploadFile() exception: "+e.getMessage());
			}
		}
		return newFile;
	}
	

	/**
	 * 
	 * @return logged in user
	 */
	public static User getLoggedInUser() {
		User user = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication != null) {
				user = (User) authentication.getPrincipal();
			}
		} catch(Exception e) {
			logger.error("getLoggedInUser(): User not logged in");
		}
		return user; 
	}
	
	/**
	 * Check for object is null...
	 */
	public boolean isNullObject(Object obj){
		boolean rtrn=false;
		if(obj ==null){
			rtrn=true;
		}
		return rtrn;
	}
	
	public static void resizeImage(File file,String fileExtension,int width,int height) {
		logger.info("resizeFile() start");
		try {
			fileExtension=fileExtension.replaceFirst("\\.", "");
			BufferedImage originalImage = ImageIO.read(file);
			BufferedImage scaled = getScaledInstance(originalImage,width,height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
			/*int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizedImage = new BufferedImage(width,height,type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0,width,height, null);
			g.dispose();*/
			//ImageIO.write(resizedImage,fileExtension,file);
			//if(fileExtension.equals("jpg"))
			   writeImage(scaled, new FileOutputStream(file), fileExtension,0.85f);
			//else
			//ImageIO.write(scaled,fileExtension,file);

		} catch (Exception e) {
			logger.error("resizeImage() exception: "+e.getMessage());
		}
		
	}
	public static void generateVideoFrame(String filePath,String fileName,String uploadPath, String fileExtension){
		try{
			FFmpegFrameGrabber g = new FFmpegFrameGrabber(filePath);
			if(fileExtension != null){
				fileExtension=fileExtension.replace(".", "");
				g.setFormat(fileExtension);
			}else{
				g.setFormat("mp4");
			}				
			g.start();

			for (int i = 0 ; i < 1 ; i++) {
				Java2DFrameConverter j = new Java2DFrameConverter();
				BufferedImage resizedImage = j.getBufferedImage(g.grabImage()); 
			    ImageIO.write(resizedImage, "jpg", new File(uploadPath+fileName+".jpg"));
			}

			g.stop();	
		}catch(Exception e){
			logger.error("generateVideoFrame() exception: "+e);
		}
		
	}
	
	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
		try{
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}
   if(w>targetWidth && h>targetHeight)
		{
	      do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}
		} while (w != targetWidth || h != targetHeight);
	}
			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h,null);
			g2.dispose();

			ret = tmp;
		
		return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	public static void writeImage(BufferedImage bufferedImage, OutputStream outputStream, String fileExtension,float quality) throws IOException {
		try{
		Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(fileExtension);
		ImageWriter imageWriter = iterator.next();
		ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
		if (imageWriteParam .canWriteCompressed())
			{
			imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			imageWriteParam.setCompressionQuality(quality);
			}
		ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
		imageWriter.setOutput(imageOutputStream);
		IIOImage iioimage = new IIOImage(bufferedImage, null, null);
		imageWriter.write(null, iioimage, imageWriteParam);
		imageOutputStream.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*public static Boolean checkSwearWord(String msgOrString){
		List<String> badWords =ApplicationProperties.getAllSwearWords();
		String regex = "(?i)\\b("+String.join("|", badWords)+")\\b";
		Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(msgOrString);
	    Boolean isSwearWord=m.find();
	    logger.info("checkSwearWord() : "+isSwearWord);
	    return  isSwearWord;
	}*/
	
	
}
