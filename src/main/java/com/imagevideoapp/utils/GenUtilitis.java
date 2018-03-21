/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.imagevideoapp.utils;

import com.imagevideoapp.Enums.RESPONSE_CODES;
import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.models.ResponseObject;
import com.imagevideoapp.models.User;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.ServletInputStream;
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

public class GenUtilitis {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String AMOUNT_PATTERN = "^((\\d+)|(\\d+\\.\\d{1,2}))$";
	private static final Logger logger = Logger.getLogger(GenUtilitis.class);
	private static Cipher cipher;
	private static SecretKey secretKey;

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
		logger.error("getErrorObject() exception: " + exception.getMessage());
		return exception;
	}

	public static String generateHash(String toHash) {
		MessageDigest md = null;
		byte[] hash = null;

		try {
			md = MessageDigest.getInstance("SHA-512");
			hash = md.digest(toHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException arg3) {
			arg3.printStackTrace();
		} catch (UnsupportedEncodingException arg4) {
			arg4.printStackTrace();
		}

		return convertToHex(hash);
	}

	private static String convertToHex(byte[] raw) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < raw.length; ++i) {
			sb.append(Integer.toString((raw[i] & 255) + 256, 16).substring(1));
		}

		return sb.toString();
	}

	public static JSONObject getBody(HttpServletRequest request) throws GenericException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			ServletInputStream json = request.getInputStream();
			if (json != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(json));
				char[] e = new char[128];
				boolean bytesRead = true;

				int bytesRead1;
				while ((bytesRead1 = bufferedReader.read(e)) > 0) {
					stringBuilder.append(e, 0, bytesRead1);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException arg16) {
			arg16.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException arg14) {
					arg14.printStackTrace();
				}
			}

		}

		body = stringBuilder.toString();
		JSONObject json1 = null;
		if (!body.isEmpty()) {
			try {
				json1 = new JSONObject(body);
			} catch (JSONException arg15) {
				arg15.printStackTrace();
				throw getErrorObject(RESPONSE_CODES.INCORRECT_JSON_FORMAT);
			}
		}

		return json1;
	}

	public static boolean isValidAmount(String str) {
		boolean isValid = false;
		if (!StringUtils.isEmpty(str)) {
			Pattern pattern = Pattern.compile("^((\\d+)|(\\d+\\.\\d{1,2}))$");
			Matcher matcher = pattern.matcher(str);
			isValid = matcher.matches();
		}

		return isValid;
	}

	public static boolean isValidEmail(String str) {
		boolean isValid = false;
		if (!StringUtils.isEmpty(str)) {
			Pattern pattern = Pattern.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher matcher = pattern.matcher(str);
			isValid = matcher.matches();
		}

		return isValid;
	}

	public static int daysBetween(long t1, long t2) {
		return (int) ((t2 - t1) / 86400000L);
	}

	public static String getRandomAlphaNumeric(int count) {
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();

		while (count-- != 0) {
			int character = (int) (Math.random() * (double) "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length());
			builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(character));
		}

		return builder.toString();
	}

	public static File uploadFile(String path, String fileName, MultipartFile file) {
		logger.debug("uploadFile() start");
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		File newFile = null;

		try {
			File e = new File(path);
			if (!e.exists()) {
				e.mkdirs();
			}

			inputStream = file.getInputStream();
			newFile = new File(path + fileName);
			if (null != newFile && !newFile.exists()) {
				newFile.createNewFile();
			}

			outputStream = new FileOutputStream(newFile);
			boolean read = false;
			byte[] bytes = new byte[(int) file.getSize()];

			int read1;
			while ((read1 = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read1);
			}
		} catch (Exception arg16) {
			arg16.printStackTrace();
			newFile = null;
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception arg15) {
				logger.error("uploadFile() exception: " + arg15.getMessage());
			}

		}

		return newFile;
	}

	public static User getLoggedInUser() {
		User user = null;

		try {
			Authentication e = SecurityContextHolder.getContext().getAuthentication();
			if (e != null) {
				user = (User) e.getPrincipal();
			}
		} catch (Exception arg1) {
			logger.error("getLoggedInUser(): User not logged in");
		}

		return user;
	}

	public boolean isNullObject(Object obj) {
		boolean rtrn = false;
		if (obj == null) {
			rtrn = true;
		}

		return rtrn;
	}

	public static void resizeImage(File file, String fileExtension, int width, int height) {
		logger.debug("resizeFile() start");

		try {
			fileExtension = fileExtension.replaceFirst("\\.", "");
			BufferedImage e = ImageIO.read(file);
			BufferedImage scaled = getScaledInstance(e, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR,
					true);
			writeImage(scaled, new FileOutputStream(file), fileExtension, 0.85F);
		} catch (Exception arg5) {
			logger.error("resizeImage() exception: " + arg5.getMessage());
		}

	}

	public static void generateVideoFrame(String filePath, String fileName, String uploadPath, String fileExtension) {
		try {
			FFmpegFrameGrabber e = new FFmpegFrameGrabber(filePath);
			if (fileExtension != null) {
				fileExtension = fileExtension.replace(".", "");
				e.setFormat(fileExtension);
			} else {
				e.setFormat("mp4");
			}

			e.start();

			for (int i = 0; i < 1; ++i) {
				Java2DFrameConverter j = new Java2DFrameConverter();
				BufferedImage resizedImage = j.getBufferedImage(e.grabImage());
				ImageIO.write(resizedImage, "jpg", new File(uploadPath + fileName + ".jpg"));
			}

			e.stop();
		} catch (Exception arg7) {
			logger.error("generateVideoFrame() exception: " + arg7);
		}

	}

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		try {
			int e = img.getTransparency() == 1 ? 1 : 2;
			int w;
			int h;
			if (higherQuality) {
				w = img.getWidth();
				h = img.getHeight();
			} else {
				w = targetWidth;
				h = targetHeight;
			}

			if (w > targetWidth && h > targetHeight) {
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

			BufferedImage tmp = new BufferedImage(w, h, e);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(img, 0, 0, w, h, (ImageObserver) null);
			g2.dispose();
			return tmp;
		} catch (Exception arg10) {
			arg10.printStackTrace();
			return null;
		}
	}

	public static void writeImage(BufferedImage bufferedImage, OutputStream outputStream, String fileExtension,
			float quality) throws IOException {
		try {
			Iterator e = ImageIO.getImageWritersByFormatName(fileExtension);
			ImageWriter imageWriter = (ImageWriter) e.next();
			ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
			if (imageWriteParam.canWriteCompressed()) {
				imageWriteParam.setCompressionMode(2);
				imageWriteParam.setCompressionQuality(quality);
			}

			MemoryCacheImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
			imageWriter.setOutput(imageOutputStream);
			IIOImage iioimage = new IIOImage(bufferedImage, (List) null, (IIOMetadata) null);
			imageWriter.write((IIOMetadata) null, iioimage, imageWriteParam);
			imageOutputStream.flush();
		} catch (Exception arg8) {
			arg8.printStackTrace();
		}

	}

	public static boolean fileFolderdeteUtils(File directory) throws IOException {
		if (!directory.exists()) {
			logger.debug("Directory does not exist.");
			return false;
		} else {
			try {
				delete(directory);
				return true;
			} catch (IOException arg1) {
				arg1.printStackTrace();
				return false;
			}
		}
	}

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
				logger.debug("Directory is deleted : " + file.getAbsolutePath());
			} else {
				String[] files = file.list();
				String[] arg1 = files;
				int arg2 = files.length;

				for (int arg3 = 0; arg3 < arg2; ++arg3) {
					String temp = arg1[arg3];
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}

				if (file.list().length == 0) {
					file.delete();
					logger.debug("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			file.delete();
			logger.debug("File is deleted : " + file.getAbsolutePath());
		}

	}

	static {
		try {
			KeyGenerator e = KeyGenerator.getInstance("AES");
			e.init(128);
			secretKey = e.generateKey();
		} catch (NoSuchAlgorithmException arg0) {
			arg0.printStackTrace();
		}

	}
}