package com.wsct.util.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wsct.util.web.WebUtil;

/**
 * 导出CSV文件
 * @author chykong
 *
 */
public class CSVUtil {
	private static Logger logger = LoggerFactory.getLogger("sysLog");

	/**
	   * 生成为CVS文件 
	   * @param outPutPath 
	   * @param fileName 要导出生成的文件名
	   * @return
	   */
	public static File createCSVFile(String outPutPath, String fileName, String[][] data) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			//定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					csvFileOutputStream.write(WebUtil.getSafeStr(data[i][j]));
					if (j != data[i].length - 1)
						csvFileOutputStream.write(",");
				}
				csvFileOutputStream.newLine();
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 下载文件
	 * @param response
	 * @param csvFilePath CVS文件的全路径，包含文件名
	 * @param fileName      要导出生成的文件名
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName) {
		InputStream in = null;
		try {
			response.setContentType("application/csv;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			in = new FileInputStream(csvFilePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			response.setCharacterEncoding("UTF-8");
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * 删除该目录filePath下的所有文件
	 * @param filePath
	 *      文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * @param filePath
	 *     文件目录路径
	 * @param fileName
	 *     文件名称
	 */
	public static void deleteFile(String filePath, String fileName) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return;
					}
				}
			}
		}
	}

	/**
	 * 测试数据
	 * @param args
	 */
	public static void main(String[] args) {

		String[][] data = new String[2][2];
		data[0][0] = "序号";
		data[0][1] = "内容";

		data[1][0] = "1";
		data[1][1] = "测试";

		String path = "c:/export/";
		String fileName = "文件导出";
		File file = CSVUtil.createCSVFile(path, fileName, data);
		String fileName2 = file.getName();
		System.out.println("文件名称：" + fileName2);
	}
}
