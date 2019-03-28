//package com.pentachord.util.tag;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.tagext.TagSupport;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.pentachord.system.code.service.codeSearchVO;
//import com.pentachord.system.code.service.codeService;
//import com.pentachord.system.code.service.codeVO;
//import com.pentachord.util.function.FuncString;
//
//public class codeRdoChkTag extends TagSupport {
//	private static final long serialVersionUID = 1L;
//	protected final Log logger = LogFactory.getLog(getClass());
//	
//	private static codeService codeService;
//	
//	private String common_code;
//	private String input_type;
//	private String input_name;
//	private String input_save_value;
//	private String input_print_value;
//	private String select_value;
//	public String getCommon_code() {
//		return common_code;
//	}
//	public void setCommon_code(String common_code) {
//		this.common_code = common_code;
//	}
//	public String getInput_name() {
//		return input_name;
//	}
//	public String getInput_type() {
//		return input_type;
//	}
//	public void setInput_type(String input_type) {
//		this.input_type = input_type;
//	}
//	public void setInput_name(String input_name) {
//		this.input_name = input_name;
//	}
//	public String getInput_print_value() {
//		return input_print_value;
//	}
//	public void setInput_print_value(String input_print_value) {
//		this.input_print_value = input_print_value;
//	}
//	public String getInput_save_value() {
//		return input_save_value;
//	}
//	public void setInput_save_value(String input_save_value) {
//		this.input_save_value = input_save_value;
//	}
//	public String getSelect_value() {
//		return select_value;
//	}
//	public void setSelect_value(String select_value) {
//		this.select_value = select_value;
//	}
//	public codeRdoChkTag() {
//		super();
//		init();
//	}
//	private void init() {
//		setCommon_code(null);
//		setInput_print_value(null);
//		setInput_save_value(null);
//		setSelect_value(null);
//	}
//	@Override
//	public int doStartTag() throws JspException {
//		if (codeService==null) {
//			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
//			codeService = (codeService) ctx.getBean("codeService");
//		}
//		
//		StringBuffer buffer = new StringBuffer();
//		try {
//			codeSearchVO searchVO = new codeSearchVO();
//			searchVO.setDelete_yn("N");
//			searchVO.setUse_yn("Y");
//			searchVO.setChild_common_code(common_code);
//			searchVO.setDepths((common_code.length()/3) +1);
//			
//			Integer input_index = 0;
//			String input_id = null;
//			String save_value = null;
//			String print_value = null;
//			String selected = null;
//			String[] arr_selectValue = FuncString.isEmpty(this.select_value, "").split(",");
//			
//			List<codeVO> codeList = this.codeService.select_codeList(searchVO);
//			if (codeList!=null && codeList.size() >0) {
//				for (codeVO codeVO : codeList) {
//					
//					if ("etc_1".equals(input_save_value)) {
//						save_value = codeVO.getEtc_1();
//					} else if ("etc_2".equals(input_save_value)) {
//						save_value = codeVO.getEtc_2();
//					} else {
//						save_value = codeVO.getCommon_code();
//					}
//					
//					if ("etc_1".equals(print_value)) {
//						print_value = codeVO.getEtc_1();
//					} else if ("etc_2".equals(print_value)) {
//						print_value = codeVO.getEtc_2();
//					} else {
//						print_value = codeVO.getName();
//					}
//					
//					input_id = String.format("%s_%s", input_name, save_value);
//					
//					for (String value : arr_selectValue) {
//						value = value.trim();
//						if (value.equals(save_value)) {
//							selected = "checked=\"checked\"";
//							break;
//						} else {
//							selected = "";
//						}
//					}
//					
//					
//					buffer.append( String.format("<span class=\"%s_style\">", input_type) );
//					buffer.append( String.format("<input type=\"%s\" name=\"%s\" value=\"%s\" id=\"%s\" %s />", input_type, input_name, save_value, input_id, selected) );
//					buffer.append( String.format("<label for=\"%s\" class=\"%s_img\"></label>", input_id, input_type) );
//					buffer.append( String.format("<label for=\"%s\">%s</label>", input_id, print_value) );
//					buffer.append( "</span>" );
//					
//					input_index = input_index +1;
//				}
//			}
//			
//			pageContext.getOut().write(buffer.toString());
//		} catch (IOException e) {
//			logger.debug("Tag Error [ERROR:2.1]");
//		} catch (Exception exception) {
//			logger.debug("Tag Error [ERROR:1.1]");
//		}
//		
//		
//		return super.doStartTag();
//	}
//	@Override
//	public int doEndTag() throws JspException {
//		init();
//		return super.doEndTag();
//	}
//}
