package com.coolwin.util;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.File;

/**
 * doc docx格式转换
 */
public class DocConverter {
	private static final int environment = 1;// 环境 1：windows 2:linux
	private String fileString;// (只涉及pdf2swf路径问题)
	private String fileName;
	private File pdfFile;
	private File docFile;
	public DocConverter(String fileString) {
		ini(fileString);
	}
	/**
	 * 初始化
	 *
	 * @param fileString
	 */
	private void ini(String fileString) {
		this.fileString = fileString;
		fileName = fileString.substring(0, fileString.lastIndexOf("."));
		docFile = new File(fileString);
		pdfFile = new File(docFile.getParent()+"\\temp\\"+fileName.substring(fileName.lastIndexOf("/")) + ".pdf");
	}

	/**
	 * 转为PDF
	 *
	 */
	private void doc2pdf() throws Exception {
		if (docFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
				try {
					connection.connect();
					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
					converter.convert(docFile, pdfFile);
					// close the connection
					connection.disconnect();
				} catch (java.net.ConnectException e) {
					e.printStackTrace();
					throw e;
				} catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} else {
				LoggerUtil.getLogger(DocConverter.class).info("****已经转换为pdf，不需要再进行转化****");
			}
		} else {
			LoggerUtil.getLogger(DocConverter.class).info("****swf转换器异常，需要转换的文档不存在，无法转换****");
		}
	}

	/**
	 * 转换主方法
	 */
	@SuppressWarnings("unused")
	public boolean conver() {
		if (pdfFile.exists()) {
			return true;
		}
		if (environment == 1) {
			LoggerUtil.getLogger(DocConverter.class).info("****swf转换器开始工作，当前设置运行环境windows****");
		} else {
			LoggerUtil.getLogger(DocConverter.class).info("****swf转换器开始工作，当前设置运行环境linux****");
		}
		try {
			doc2pdf();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (pdfFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
