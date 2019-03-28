//package com.pentachord.util.function;
//
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.BufferedInputStream;
//import java.io.Closeable;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.HashMap;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.apache.tools.zip.ZipEntry;
//import org.apache.tools.zip.ZipOutputStream;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.pentachord.cmm.vo.fileVO;
//import com.pentachord.util.MultipartUtility;
//
///**
// * @Title : 파일 관련 함수
// * @date : 2017. 01. 23.
// * @author : 펜타코드
// */
//public class FuncFile {
//	
//	
//	final static String noimagePath = getRealpath("/images/common/");
//	final static String noimageName = "no_img.jpg";
//
//	/**
//	 * 경우별 사용 가능한 확장자 출력
//	 * 
//	 * @param index 1. 이미지, 2. 문서, 3. 데이터
//	 * @return
//	 */
//	public static String dotAllowList(int index) {
//		ArrayList<String> allowList;
//		configUtil config = configUtil.getInstance();
//		if (index==1)
//			allowList = new ArrayList<String>( Arrays.asList( config.getString("attach.allow_image") ) );
//		else if (index==2)
//			allowList = new ArrayList<String>( Arrays.asList( config.getString("attach.allow_text") ) );
//		else
//			allowList = new ArrayList<String>( Arrays.asList( config.getString("attach.allow_data") ) );
//		
//		return FuncString.implode(allowList, ", ");
//	}
//	
//	/**
//	 * 파일 확장자 체크
//	 * @param filename
//	 * @param option  1 : 이미지 체크, 2: 문서 체크, : 3 : 자료체크, 4 : 허용않되는 자료 체크(결과 반대) // 1~3까지는 화이트 리스트 4는 블랙리스트
//	 * @return
//	 */
//	public static Boolean dotname_check(String filename, int option) {
//		return dotname_check(filename, option, null);
//	}
//	
//	/**
//	 * 파일 확장자 체크
//	 * @param filename
//	 * @param option -1 : 원하는 파일 체크, 1 : 이미지 체크, 2: 문서 체크, : 3 : 자료체크, 4 : 허용않되는 자료 체크(결과 반대) // 1~3까지는 화이트 리스트 4는 블랙리스트
//	 * @return
//	 */
//	public static Boolean dotname_check(String filename, int option, String etcOption) {
//		String ext = cutDotname(filename);
//		configUtil config = configUtil.getInstance();
//		Boolean check = false;
//		if (option == 1)
//			check = config.getString("attach.allow_image").contains(ext.toLowerCase());
//		else if (option == 2)
//			check = config.getString("attach.allow_text").contains(ext.toLowerCase());
//		else if (option == 3)
//			check = config.getString("attach.allow_data").contains(ext.toLowerCase());
//		else if (option == 4)
//			check = config.getString("attach.not_allow").contains(ext.toLowerCase());
//		else if (option == -1)
//			check = etcOption.contains(ext.toLowerCase());
//		
//		return check;
//	}
//	
//	/**
//	 * 가상경로를 실제경로로 반환
//	 */
//	public static String getRealpath(String url) {
//		String path = FuncHttp.getHttpRequest().getSession().getServletContext().getRealPath(url);	
//		return path;
//	}
//	
//	/**
//	 * 경로를 업로드 경로로 반환
//	 * @param url
//	 * @return
//	 */
//	public static String getUploadPath(String url) {
//		configUtil config = configUtil.getInstance();
//		String path = config.getString("attach.upfile_dir") + url;
//		path = path.replace(config.getString("attach.upfile_url"), "");
//		
//		return path;
//	}
//
//	/**
//	 * 파일 이름에서 확장자만 추출
//	 * @param filename
//	 * @return
//	 */
//	public static String cutDotname(String filename) {
//		return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
//	}
//	
//	/**
//	 * 파일 이름을 새로 생성
//	 * @param orgName
//	 * @param tailstr
//	 * @return
//	 */
//	@SuppressWarnings("finally")
//	public static String newFileRename(String orgName, String tailstr) {
//		String ext = cutDotname(orgName);
//		String newFilename = "";
//		try { 
//			//newFilename = FuncSecurity.encryptMD5(FuncString.RandomStringCreate(3)).substring(15) + tailstr + "." + ext;
//			newFilename = FuncString.RandomStringCreate(19) + tailstr + "." + ext;
//		} finally {
//			return newFilename;
//		}
//	}
//	
//	/**
//	 * Path의 디렉토리 생성 
//	 * @param path
//	 */
//	private static void makeDirs(String path) {
//		File dirFile = new File(path);
//		if(!dirFile.exists()) {
//			// 디렉토리 속성 설정
//			dirFile.setExecutable(false);
//			dirFile.setReadable(true);
//			dirFile.setWritable(true);
//			
//			dirFile.mkdirs();
//		}
//	}
//
//	/**
//	 * 파일 업로드
//	 * 성공이면 1|파일네임, 실패면 -1|실패사유
//	 * @param file
//	 * @param newFilename
//	 * @param dir
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static JSONObject FileUpload(MultipartFile file, String newFilename, String dir) {
//		JSONObject json = new JSONObject();
//		File f = new File(dir + "/" + newFilename);
//		f = isFileCheck(dir, newFilename, f);
//		//if (file.getSize() > 0 && file.getSize() < commonVars.getMaxFileSize("Byte")) {
//		if (file.getSize() > 0) {
//			makeDirs(dir);
//			
//			try {
//				file.transferTo(f);
//			} catch (IOException e) {
//				json.put("status", "-1");
//				json.put("message", "Upload Fail ERROR1.1");
//				return json;
//			} catch(IllegalStateException e) {
//				json.put("status", "-1");
//				json.put("message", "Upload Fail ERROR1.2");
//				return json;
//			} catch(Exception e) {
//				//e.printStackTrace();
//				json.put("status", "-1");
//				json.put("message", "Upload Fail ERROR1.3");
//				return json;
//			}
//		}
//		/*else {
//			return "-1|" + (new DecimalFormat("#,##0").format(commonVars.getMaxFileSize("MB") ))  + "MB 이상은 업로드할 수 없습니다.";
//		}*/
//		
//		json.put("status", "1");
//		json.put("filename", newFilename);
//		
//		return json;
//	}
//	
//	/**
//	 * 파일을 전송합니다.
//	 * @param sendUrl
//	 * @param file
//	 * @param newFilename
//	 * @param dir
//	 * @return
//	 */
//	public static JSONObject sendAttach(String sendUrl, File file, String newFilename, String dir) {
//		
//		JSONObject json = null;
//		try {
//			MultipartUtility multipartUtility = new MultipartUtility(sendUrl);
//			multipartUtility.addFormField("filename", newFilename);
//			multipartUtility.addFormField("filepath", dir);
//			multipartUtility.addFilePart("fileattach", file);
//			
//			String s_response = multipartUtility.finish();
//			json = new JSONObject(s_response);
//		} catch(JSONException e) {	
//			json = new JSONObject();
//			json.put("status", "FAIL");
//		} catch(IOException e) {
//			json = new JSONObject();
//			 json.put("status", "FAIL");
//		} catch(Exception e) {
//			json = new JSONObject();
//			json.put("status", "FAIL");
//		}
//		
//		return  json;
//	}
//	
//	/**
//	 * dir 안에 있는 filename의 파일을 삭제
//	 */
//	public static void FileDelete(String Filename, String dir) {
//		File f = new File(dir + "/" + Filename);
//		if (f.exists())
//			f.delete();
//	}
//
//	/**
//	 * 해당 디렉토리에 중복되는 파일명이 있으면 다시 생성
//	 * @param dir
//	 * @param filename
//	 * @param f
//	 * @return
//	 */
//	public static File isFileCheck(String dir, String filename, File f) {
//		while (f.isFile())  {
//			//filename =  filename.substring(0, filename.lastIndexOf(".")-1) + FuncString.RandomStringCreate(3) + "." + cutDotname(filename);
//			filename =  FuncString.RandomStringCreate(19) + "." + cutDotname(filename);
//			f = new File(dir + "/" + filename);
//		}
//		return f;
//	}
//	
//	/**
//	 * Form에서 첨부된 파일로 Thumbnail을 생성합니다
//	 * 성공이면 status : OK, 실패하면 status : FAIL
//	 * @param file
//	 * @param newFilename
//	 * @param dir
//	 * @param width
//	 * @param height
//	 * @return
//	 * @throws IOException
//	 */
//	@SuppressWarnings({ "null", "unchecked" })
//	public static JSONObject makeThumb(MultipartFile file, String newFilename, String dir, int width) throws IOException {
//		JSONObject json = new JSONObject();
//		
//		Graphics2D g = null;
//		
//		float w = width;
//		float h = 0;
//		
//		try {
//		
//			File f = new File(dir + "/" + newFilename);
//			if (file==null) {
//				json.put("status", "FAIL");
//				json.put("message", file.getOriginalFilename() + "의 파일이 없습니다.");
//				return json;
//			}
//			
//			f = isFileCheck(dir, newFilename, f);
//			
//			makeDirs(dir);
//			
//			HashMap<String, Float> imgSizeMap = getImageSize(file);
//			float imgWidth = imgSizeMap.get("width");
//			float imgHeight = imgSizeMap.get("height");
//			float img_ratio = imgWidth / imgHeight;
//			
//			if (h>0) {
//				float target_ratio = w / h;
//				
//				if ( img_ratio >= target_ratio) {
//					if (w >= imgWidth)
//						w = imgWidth;
//					h = w / img_ratio;						
//				} else {
//					if (h >= imgHeight)
//						h = imgHeight;
//					w = img_ratio * h;
//				}
//			} else {
//				if (w > imgWidth) 
//					w = imgWidth;
//				h = w / img_ratio;
//			}
//			
//			w = Math.round(w);
//			h = Math.round(h);
//			
//			if (imgWidth>w || imgHeight>h) {
//				/*
//				Toolkit kit = Toolkit.getDefaultToolkit();
//				MediaTracker mtracker = new MediaTracker(new Container()); 
//				Image img = kit.createImage(file.getBytes());
//				mtracker.addImage(img, 1); 
//				try {
//					mtracker.waitForID(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				BufferedImage original = toBufferedImage(img);
//				*/
//				BufferedImage original = ImageIO.read(file.getInputStream());
//				BufferedImage thumb = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_3BYTE_BGR);
//				
//				g = thumb.createGraphics();
//				//안티안셀리어싱 사용
//				//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//				g.drawImage(original, 0, 0, (int)w, (int)h, null);
//				
//				ImageIO.write(thumb, cutDotname(file.getOriginalFilename()), f);
//			} else {
//				FileUpload(file, newFilename, dir);
//			}
//		} finally {
//			if (g!=null) {
//				g.dispose();
//			}
//		}
//		
//		
//		json.put("status", "1");
//		json.put("filename", newFilename);
//		return json;
//	}
//	
//	/**
//	 * 저장되어 있는 파일을 가지고 Thumbnail을 생성합니다
//	 * 성공이면 status : OK, 실패하면 status : FAIL
//	 * @param originFile
//	 * @param thumbFile
//	 * @param i_width
//	 * @return
//	 * @throws IOException
//	 */
//	public static JSONObject makeThumb(File originFile, File thumbFile, int i_width) throws IOException {
//		JSONObject resultJson = new JSONObject();
//		
//		Graphics2D g = null;
//		FileInputStream inputStream = null;
//		FileOutputStream outputStream = null;
//		
//		float width = i_width;
//		float height = 0;
//		
//		try {
//			if (originFile==null || originFile.length() <1 || !originFile.isFile()) {
//				throw new NullPointerException("파일이 없습니다.");
//			}
//			
//			HashMap<String, Float> imgSize = getImageSize(originFile);
//			float img_ratio = imgSize.get("width") / imgSize.get("height");
//			if (height>0) {
//				float target_ratio = width / height;
//				
//				if ( img_ratio >= target_ratio) {
//					if (width >= imgSize.get("width"))
//						width = imgSize.get("width");
//					height = width / img_ratio;						
//				}
//				else {
//					if (height >= imgSize.get("height"))
//						height = imgSize.get("height");
//					width = img_ratio * height;
//				}
//			} else {
//				if (width > imgSize.get("width")) 
//					width = imgSize.get("height");
//				height = width / img_ratio;
//			}
//			
//			width = Math.round(width);
//			height = Math.round(height);
//			
//			BufferedImage original = ImageIO.read( new FileInputStream(originFile) );
//			BufferedImage thumb = null;
//			
//			int imgWidth = 0, imgHeight = 0;
//			if (imgSize.get(0)>width || imgSize.get(1)>height) { //썸네일 생성
//				imgWidth = (int) width;
//				imgHeight = (int) height;
//				thumb = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_3BYTE_BGR);
//				
//				g = thumb.createGraphics();
//				//안티안셀리어싱 사용
//				//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//				g.drawImage(original, 0, 0, imgWidth, imgHeight, null);
//				String dotName = cutDotname(thumbFile.getName());
//				
//				ImageIO.write(thumb, dotName, thumbFile);
//			} else { //파일 복사
//				inputStream = new FileInputStream(originFile);
//				outputStream = new FileOutputStream(thumbFile);
//				int readBuffer = 0;
//	            byte [] buffer = new byte[512];
//	            while((readBuffer = inputStream.read(buffer)) != -1) {
//	            	outputStream.write(buffer, 0, readBuffer);
//	            }				
//			}
//			
//			resultJson.put("result", "OK");
//			resultJson.put("message", "");
//			resultJson.put("filename", thumbFile.getName());
//		} catch(NullPointerException e) {
//			resultJson.put("result", "FAIL");
//			if (FuncString.isNotEmpty(e.getMessage())) {
//				resultJson.put("message", e.getMessage());
//			} else {
//				resultJson.put("message", e.toString());
//			}
//			resultJson.put("filename", "");
//		} catch(IOException e) {
//			resultJson.put("result", "FAIL");
//			resultJson.put("filename", "");
//		} catch(Exception e) { 
//			resultJson.put("result", "FAIL");
//			resultJson.put("message", e.toString());
//			resultJson.put("filename", "");
//		} finally {
//			if (g!=null) {
//				g.dispose();
//			}
//			
//			closeable_close(inputStream, outputStream);
//		}
//		
//		return resultJson;
//	}
//	
//	/**
//	 * Image를 BufferedImage로 변환
//	 * @param img
//	 * @return
//	private static BufferedImage toBufferedImage(Image img) {
//		if (img instanceof BufferedImage) {
//			return (BufferedImage) img;
//		}
//		
//		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2D = bimage.createGraphics();
//		g2D.drawImage(img, 0, 0, null);
//		g2D.dispose();
//		
//		return bimage;
//	}
//	*/
//	
//	/**
//	 * 이미지 사이즈(Width, Height)를 구함 (Multipart)
//	 * 배열 0 : width, 1 ; height 
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public static HashMap<String, Float> getImageSize(MultipartFile file) throws IOException {
//		Image image = ImageIO.read(file.getInputStream());
//		
//		HashMap<String, Float> imgSizeMap = getImageSize(image);
//		
//		/*
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		MediaTracker mtracker = new MediaTracker(new Container()); 
//		Image img = kit.createImage(file.getBytes());
//		mtracker.addImage(img, 1); 
//		try {
//			mtracker.waitForID(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		*/
//		return imgSizeMap;
//	}
//	
//	/**
//	 * 이미지 사이즈(Width, Height)를 구함 (File)
//	 * 배열 0 : width, 1 ; height 
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public static HashMap<String, Float> getImageSize(File file) throws IOException {
//		Image image = ImageIO.read( new FileInputStream(file) );
//		HashMap<String, Float> imgSizeMap = getImageSize(image);
//		
//		return imgSizeMap;
//	}
//	
//	private static HashMap<String, Float> getImageSize(Image image) {
//		HashMap<String, Float> imgSizeMap = new HashMap<>();
//		
//		String sWidth = String.valueOf(image.getWidth(null));
//		String sHeight = String.valueOf(image.getHeight(null));
//		
//		imgSizeMap.put("width", Float.valueOf(sWidth));
//		imgSizeMap.put("height", Float.valueOf(sHeight));
//		
//		return imgSizeMap;
//	}
//	
//	private static void closeable_close(Closeable... resources) {
//		if (resources!=null && resources.length>0) {
//			for (Closeable closeable : resources) {
//				if (closeable != null) {
//					try {
//						closeable.close();
//					} catch(IOException e) {
//						
//					}
//				}
//			}
//		}
//	}
//	
//	/**
//	 * 년도/월일의 폴더명 반환
//	 * @return
//	 */
//	public static String getAttDIR() {
//		Calendar c = Calendar.getInstance();
//		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
//		if (Integer.parseInt(month)<10)
//			month = "0" + month;
//		String day = String.valueOf(c.get(Calendar.DATE));
//		if (Integer.parseInt(day) <10)
//			day = "0" + day;
//		return c.get(Calendar.YEAR) + "/" + month + day;
//	}
//	
//	/**
//	 * 파일 다운로드
//	 * 성공시 true, 실패시 false
//	 * @param response
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public static Boolean FileDownload(HttpServletResponse response, fileVO file) throws IOException {
//		File f = new File(file.getFile_path(), file.getFile_name());
//		String OrgName = URLEncoder.encode( file.getFile_orgname().replace(" ", "_"), "UTF-8" );
//		if (f.exists()) {
//			ServletOutputStream outSteam = null;
//			InputStream inputStream = null;
//			try {
//				outSteam = response.getOutputStream();
//				inputStream = new FileInputStream(f);
//				
//				response.setContentType("application/octet-stream");
//				response.setHeader("Content-Disposition", "attachment;filename=\""+ OrgName +"\"");
//				response.setHeader("Content-Transfer-Encoding:", "binary");
//				
//				//스프링 유틸리티 사용
//				FileCopyUtils.copy(inputStream, outSteam);
//				
//				/* 직접 구현
//				int realByte = 0;
//				byte[] outByte = new byte[4096];
//				while ( (realByte = inputStream.read(outByte)) != -1) {
//					outSteam.write(outByte, 0, realByte);
//				}
//				*/
//			} catch(IOException e) {
//				throw new IOException();
//			} catch (Exception e) {
//				//e.printStackTrace();
//				throw new IOException();
//			} finally {
//				if (outSteam != null) {
//					outSteam.flush();
//					outSteam.close();
//				}
//				if (inputStream != null) {
//					inputStream.close();
//				}
//			}
//		} else {
//			throw new IOException();
//		}
//
//		return true;
//	}
//	
//	public static Boolean ImagePrint(HttpServletResponse response, fileVO file) throws IOException {
//		
//		File f = null;
//		String OrgName = null;
//		
//		if(FuncString.isEmpty(file.getFile_path()) || FuncString.isEmpty(file.getFile_name())) {
//			f = new File(noimagePath, noimageName);
//			OrgName = URLEncoder.encode( noimageName , "UTF-8" );
//			
//		}else{
//
//			f = new File(file.getFile_path(), file.getFile_name());
//			OrgName = URLEncoder.encode( file.getFile_orgname().replace(" ", "_"), "UTF-8" );
//			
//		}
//		
//		if (f.exists()) {
//			
//			FileInputStream fInputStream = null;
//			BufferedInputStream bInputStream = null;
//			ByteArrayOutputStream bOutputStream = null;
//			
//			try {
//				fInputStream = new FileInputStream(f);
//				bInputStream = new BufferedInputStream(fInputStream);
//				bOutputStream = new ByteArrayOutputStream();
//				
//				int iByte;
//				while ((iByte = bInputStream.read()) != -1) {
//					bOutputStream.write(iByte);
//				}
//				
//				String extension = cutDotname(OrgName);
//				String type = "";
//				if ("jpg".equals(extension)) {
//					type = "image/jpeg";
//				} else {
//					type = "image/" + extension;
//				}
//				
//				response.setHeader("Content-Type", type);
//				response.setContentLength(bOutputStream.size());
//				
//				bOutputStream.writeTo(response.getOutputStream());
//				
//				response.getOutputStream().flush();
//				//response.getOutputStream().close();
//			} catch(IOException e) {
//				throw new IOException();
//			} finally {
//				if (bOutputStream != null) {
//					bOutputStream.flush();
//					bOutputStream.close();
//				}
//				if (bInputStream != null) {
//					bInputStream.close();
//				}
//			}
//			
//		} else {
//			throw new IOException();
//		}
//		
//		return true;
//	}
//	
//	/**
//	 * 파일들을 모아서 zip으로 다운로드
//	 * @param response
//	 * @param zipFileName
//	 * @param files
//	 * @return
//	 * @throws IOException 
//	 */
//	public static Boolean ZipFileDownload(HttpServletResponse response, String zipFileName, fileVO[] files) throws IOException {
//		ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
//		ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrOutputStream);
//		zipOutputStream.setEncoding("euc-kr");
//		byte[] bytes = new byte[2048];
//		
//		BufferedInputStream bufferInputStream = null;
//		FileInputStream inputStream = null;
//		
//		try {
//			for (fileVO file : files) {
//				inputStream = new FileInputStream(file.getFile_path() + File.separator + file.getFile_name());
//				bufferInputStream = new BufferedInputStream(inputStream);
//				
//				zipOutputStream.putNextEntry(new ZipEntry(FuncString.RandomStringCreate(3) + "_" + file.getFile_orgname()));
//				int nRead = 0;
//				while ((nRead = bufferInputStream.read(bytes)) != -1) {
//					zipOutputStream.write(bytes, 0, nRead);
//				}
//				
//				zipOutputStream.closeEntry();
//				bufferInputStream.close();
//				inputStream.close();
//			}
//			
//			zipOutputStream.flush();
//			byteArrOutputStream.flush();
//			zipOutputStream.close();
//			byteArrOutputStream.close();
//		} catch(IOException e){
//			return false;
//		} catch(Exception e) {
//			return false;
//		} finally {
//			if (bufferInputStream != null) {
//				bufferInputStream.close();
//			}
//			if (inputStream != null) {
//				inputStream.close();
//			}
//			if (zipOutputStream != null) {
//				zipOutputStream.close();
//			}
//			if (byteArrOutputStream != null) {
//				byteArrOutputStream.close();
//			}
//		}
//		
//		ServletOutputStream sos = null;
//		try {
//			byte[] zip = byteArrOutputStream.toByteArray();
//			
//			sos = response.getOutputStream();
//	        response.setContentType("application/zip");
//	        response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName + ".zip");
//
//	        sos.write(zip);
//	        sos.flush();
//		} catch(IOException e) {
//			return false;
//		} catch(Exception e) {
//			//e.printStackTrace();
//			return false;
//		} finally {
//			if (sos != null) {
//				sos.close();
//			}
//			if (byteArrOutputStream != null) {
//				byteArrOutputStream.close();
//			}
//		}
//		
//		return true;
//	}
//	
//	/* *
//	 * Excel 다운로드, 출력하지 않은 데이터는 "-"
//	 * 성공시 true, 실패시 false
//	 * 
//	 * @param response
//	 * @param file_name 파일이름
//	 * @param sheet_name 엑셀 시트 이름
//	 * @param col_names 엑셀 상단 제목
//	 * @param col_variables 엑셀 출력 순서(변수)
//	 * @param item_list 데이터
//	 * @return
//	public static Boolean excelDownload(HttpServletResponse response, String file_name, String sheet_name, String sheet_title,  String[] col_names, String[] col_variables, List<?> item_list) {
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.createSheet( sheet_name );
//		XSSFRow row = null;
//		XSSFCell cell = null;
//		XSSFCellStyle style = null;
//		
//		int cell_index = 0;
//		int row_index = 0;
//		
//		//제목
//		row = sheet.createRow(row_index++);
//		for (String s : col_names) {
//			style = workbook.createCellStyle();
//			cell = row.createCell(cell_index);
//			cell.setCellValue( sheet_title );
//			
//			//셀 스타일
//			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
//			style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//			if (cell_index == 0) {
//				style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//			} else if (cell_index == (col_names.length-1)) {
//				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//				style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//			} else {
//				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//			}
//			cell.setCellStyle(style);
//			
//			cell_index = cell_index + 1;
//		}
//		
//		//내용
//		if (item_list.size()>0) {
//		
//			Boolean isMap = false;
//			if (item_list.get(0).getClass().getSimpleName().toLowerCase().contains("map")) {
//				isMap = true;
//			}
//			
//			for (Object o : item_list) {
//				row = sheet.createRow(row_index++);
//				cell_index = 0;
//				
//				if (!isMap) { //vo 형태의 데이터 리스트
//					for (int idx=0; idx<col_variables.length; idx++) {
//						
//						for (Field field : o.getClass().getDeclaredFields()) {
//						
//							if (col_variables[idx].equals(field.getName())) {
//								cell = row.createCell(cell_index);
//								field.setAccessible(true);
//								
//								try {
//									Object data = field.get(o);
//									if (data == null) {
//										cell.setCellValue("");
//									} else {
//										if (!data.equals("-")) {
//											cell.setCellValue( data.toString() );
//										}
//									}
//									
//									style = workbook.createCellStyle();
//									if (cell_index==0) {
//										style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//									} else if (cell_index == (col_names.length-1)) {
//										style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//									}
//									if (row_index == (item_list.size()+1)) {
//										style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
//									}
//									cell.setCellStyle(style);
//									
//									cell_index++;
//								} catch (IllegalArgumentException e) {
//									return false;
//								} catch (IllegalAccessException e) {
//									return false;
//								} catch (Exception e) {
//									//e.printStackTrace();
//									return false;
//								}
//								
//								break;
//							}
//						}
//						
//					}
//				} else { //map형태의 리스트
//					
//					Iterator<?> iterator = null;
//					Entry<?, ?> entry = null;
//					
//					for (int idx=0; idx<col_variables.length; idx++) {
//					
//						iterator = ((HashMap<?, ?>) o).entrySet().iterator();
//					
//						while(iterator.hasNext()) {
//							
//							entry = (Entry<?, ?>) iterator.next();
//							
//							if (col_variables[idx].equals(entry.getKey())) {
//								cell = row.createCell(cell_index);
//								cell.setCellValue( entry.getValue().toString() );
//								
//								style = workbook.createCellStyle();
//								if (cell_index==0) {
//									style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//								} else if (cell_index == (col_names.length-1)) {
//									style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//								}
//								if (row_index == (item_list.size()+1)) {
//									style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
//								}
//								cell.setCellStyle(style);
//								
//								cell_index++;
//								
//								break;
//							}
//						}
//						
//					}
//				}
//			}
//			
//			for (int index=0; index< col_names.length; index++) {
//				sheet.autoSizeColumn(index);					
//			}
//			
//		}
//		/* else {
//			row = sheet.createRow(row_index);
//			cell = row.createCell(0);
//			cell.setCellValue("검색 결과가 없습니다.");
//			
//			sheet.addMergedRegion( new CellRangeAddress(1, 1, 0, col_names.length-1) );
//			
//			style = workbook.createCellStyle();
//			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//			cell.setCellStyle(style);
//			
//			sheet.autoSizeColumn(0, true);
//		}* /
//		
//		try {
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file_name, "UTF-8") + ".xls");
//			
//	        workbook.write(response.getOutputStream());
//		} catch(IOException e) {
//			return false;
//		} catch(Exception e) {
//			return false;
//		}
//		
//		return true;
//	}
//	*/
//
//	/* *
//	 * 업로드된 엑셀 데이터를 HashMap으로 컨버팅합니다.
//	 * @param startRow
//	 * @param fileVO
//	 * @return
//	 * @throws IOException 
//	@SuppressWarnings("unchecked")
//	public static JSONObject excelUpload(int startRow, int startCol, MultipartFile file) throws IOException {
//		
//		JSONObject resultJSON = new JSONObject();
//		
//		if (!dotname_check(file.getOriginalFilename(), -1, ".xls.xlsx")) {
//			resultJSON.put("status", "fail");
//			resultJSON.put("message", "올바른 엑셀파일은 업로드해주세요.");
//			return resultJSON;
//		}
//		
//		/*
//		String excelFileName = newFileRename(file.getOriginalFilename(), "_menu");
//		String excelDirName = configUtil.getInstance().getString("attach.upfile_url") + "/" + FuncFile.getAttDIR();
//		JSONObject uploadFile = FileUpload(file, excelFileName, excelDirName);
//		if (uploadFile.get("status").equals("-1")) {
//			resultJSON.put("status", "fail");
//			resultJSON.put("message", "엑셀파일 업로드에 실패했습니다.");
//			return resultJSON;
//		}
//		* /
//		
//		List<HashMap<String, String>> excelRowList = null;
//		HashMap<String, String> excelRow = null;
//		Workbook book = null;
//		try {
//			excelRowList = new ArrayList<HashMap<String,String>>();
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			book = WorkbookFactory.create(file.getInputStream());
//			Sheet sheet = book.getSheetAt(0);
//			//FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();
//			
//			Row row = null;
//			Cell cell = null;
//			
//			for(int idx=0; idx<=sheet.getPhysicalNumberOfRows(); idx++) {
//				if (idx < startRow-1) 
//					continue;
//				
//				row = sheet.getRow(idx);
//				excelRow = new HashMap<String, String>();
//				for (int idy=0; idy<=row.getLastCellNum(); idy++) {
//					if (idy<startCol-1) 
//						continue;
//					
//					cell = row.getCell(idy);
//					if (cell != null) {
//						String value = new String();
//						
//						switch (cell.getCellType()) {
//							case Cell.CELL_TYPE_NUMERIC:
//								if (HSSFDateUtil.isCellDateFormatted(cell)) {
//									value = sdf.format(cell.getDateCellValue());
//								} else {
//									value = String.valueOf(cell.getNumericCellValue());
//								}
//								break;
//							case Cell.CELL_TYPE_STRING:
//								value = String.valueOf(cell.getRichStringCellValue());
//								break;
//							case Cell.CELL_TYPE_FORMULA:
//								value = String.valueOf(cell.getRichStringCellValue());
//							default:
//								break;
//						}
//						
//						excelRow.put("cell_"+idy, value);
//					} else {
//						
//					}
//				}
//				
//				excelRowList.add(excelRow);
//			}
//			
//		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
//			resultJSON.put("status", "fail");
//			resultJSON.put("message", "데이터 변환도중 에러가 발생했습니다.");
//			return resultJSON;
//		} catch (Exception e) {
//			resultJSON.put("status", "fail");
//			resultJSON.put("message", "데이터 변환도중 에러가 발생했습니다.");
//			return resultJSON;
//		} finally {
//			if (book != null) {
//				book.close();
//			}
//		}
//		
//		resultJSON.put("status", "OK");
//		resultJSON.put("data", excelRowList);
//		
//		return resultJSON;
//	}
//	 */
//	
//	/*
//	byte로 되어있는 글자를 
//	image로 변환합니다.
//	try {
//		File txt = new File("E:\\", "test.txt");
//		FileReader fReader = new FileReader(txt);
//		char[] cArray = new char[(int)txt.length()];
//		fReader.read(cArray);
//		
//		//base64 to Byte
//		String sChar = new String(cArray);
//		byte[] byteArray = Base64.decodeBase64(sChar);
//		
//		/*
//		Hex 코드 to Byte
//		String sChar = new String(cArray);
//		String[] hexStringArray = sChar.split("-");
//		byte[] byteArray = new byte[hexStringArray.length];
//		for (int idx=0; idx<hexStringArray.length; idx++) {
//
//			byteArray[idx] = (byte) ( Integer.parseInt(hexStringArray[idx], 16) );
//		}
//		* /
//		
//		ByteArrayInputStream inputStream = new ByteArrayInputStream( byteArray );
//		BufferedImage bufferedImage = ImageIO.read(inputStream);
//
//		ImageIO.write(bufferedImage, "jpg", new File("E:\\", "test.jpg"));
//	} catch(Exception e) {
//		e.printStackTrace();
//	}
//	*/
//}
