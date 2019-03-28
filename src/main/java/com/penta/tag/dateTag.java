//package com.pentachord.util.tag;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.tagext.TagSupport;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.pentachord.util.function.FuncString;
//
///**
// * @Title : 날짜 형식을 바꿔주는 taglib
// * @date : 2017. 01. 23.
// * @author : 펜타코드
// */
//public class dateTag extends TagSupport {
//	private static final long serialVersionUID = 1L;
//	protected final Log logger = LogFactory.getLog(getClass());
//	
//	private String value;
//	private String valuePattern;
//	private String pattern;
//
//	public void setValue(String value) {
//		this.value = value;
//	}
//	public void setValuePattern(String valuePattern) {
//		this.valuePattern = valuePattern;
//	}
//	public void setPattern(String pattern) {
//		this.pattern = pattern;
//	}
//	
//	public dateTag() {
//		super();
//		init();
//	}
//	private void init() {
//		setValue(null);
//		setValuePattern(null);
//		setPattern(null);
//	}
//	
//	@Override
//	public int doStartTag() throws JspException {
//		StringBuffer buffer = new StringBuffer();
//		try {
//			SimpleDateFormat simpleFromFormat = new SimpleDateFormat(valuePattern);
//			SimpleDateFormat simpleToFormat = new SimpleDateFormat(pattern);
//			if (!FuncString.isEmpty(value)) {
//				Date date = simpleFromFormat.parse(value);
//				buffer.append(simpleToFormat.format(date));
//			}
//			
//			pageContext.getOut().write(buffer.toString());
//		} catch (ParseException e) {
//			logger.debug("Tag Error [ERROR:3.1]");
//		} catch (IOException e) {
//			logger.debug("Tag Error [ERROR:2.1]");
//		} catch (Exception exception) {
//			logger.debug("Tag Error [ERROR:1.1]");
//		}
//		
//		return super.doStartTag();
//	}
//	
//	@Override
//	public int doEndTag() throws JspException {
//		init();
//		return super.doEndTag();
//	}
//}
