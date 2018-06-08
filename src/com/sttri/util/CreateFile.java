package com.sttri.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 文件操作工具类
 * 
 * @author WDQ
 * @version 1.0 2011年11月18日9:44:23 function list: 文件操作 Histroy(历史修改记录):
 * 
 */
public class CreateFile {
	/**
	 * @param filePath(文件完整路径)
	 *            fileName(文件名称) fileContent(文件内容) fileType(文件类型)
	 * @exception IOException
	 *                Histroy(历史修改记录):
	 *                Description(方法描述):根据文件路径和文件名称创建文件，并将内容写入创建的文件中
	 *                Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table
	 *                Accessed：被访问的表 Table Update：被修改的表
	 */
	public static boolean FileOperation(String filePath, String fileName,
			String fileContent, String fileType) {
		boolean sign = false;
		// 创建文件夹
		sign = CreateFile.createFolder(filePath);
		if (sign) {
			String pathTemp = "";
			pathTemp = filePath + File.separator + fileName + fileType;
			File file = new File(pathTemp);
			try {
				// 文件不存在
				if (!file.exists()) {
					sign = createFile(pathTemp);// 创建文件
					if (sign) {// 文件创建成功
						writeFile(pathTemp, fileContent);
					}
				} else {
					sign = deleteFile(pathTemp);// 删除文件
					sign = createFile(pathTemp);// 创建文件
					if (sign) {// 文件创建成功
						writeFile(pathTemp, fileContent);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return sign;
	}

	/**
	 * @param filePath(文件完整路径)
	 *            fileName(文件名称) fileContent[](文件内容-字节) fileType(文件类型)
	 * @exception IOException
	 *                Histroy(历史修改记录):
	 *                Description(方法描述):根据文件路径和文件名称创建文件，并将内容写入创建的文件中
	 *                Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table
	 *                Accessed：被访问的表 Table Update：被修改的表
	 */
	public static boolean FileXmlOperation(String filePath, String fileName,
			byte fileContent[], String fileType) {
		boolean sign = false;
		sign = CreateFile.createFolder(filePath);
		if (sign) {
			String pathTemp = "";
			pathTemp = filePath + File.separator + fileName + fileType;
			File file = new File(pathTemp);
			try {
				// 文件不存在
				if (!file.exists()) {
					sign = createFile(pathTemp);// 创建文件
					if (sign) {// 文件创建成功
						writeToBytes(fileContent, pathTemp);
					}
				} else {
					sign = deleteFile(pathTemp);// 删除文件
					sign = createFile(pathTemp);// 创建文件
					if (sign) {// 文件创建成功
						writeToBytes(fileContent, pathTemp);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sign;
	}

	/**
	 * @param fileName(文件完整路径)
	 * @throws IOException
	 *             histroy(历史修改记录): Description(方法描述):根据文件完整路径创建文件
	 *             Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table Accessed：被访问的表
	 *             Table Update：被修改的表
	 */
	public static boolean createFile(String fileName) throws IOException {
		boolean flag = false;
		File filename = new File(fileName);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}

	/**
	 * @param path(文件夹路径)
	 * @throws IOException
	 *             histroy(历史修改记录): Description(方法描述):根据路径创建文件夹
	 *             Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table Accessed：被访问的表
	 *             Table Update：被修改的表
	 */
	public static boolean createFolder(String path) {
		boolean flag = false;
		File filePath = new File(path);
		if (!filePath.exists()) {
			flag = filePath.mkdirs();
		} else {
			flag = filePath.exists();
		}
		return flag;
	}

	/**
	 * @param filePath(文件完整路径)fileContent(文件内容)
	 * @throws IOException
	 *             histroy(历史修改记录): Description(方法描述):根据文件完整路径将文件内容写入文件
	 *             Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table Accessed：被访问的表
	 *             Table Update：被修改的表
	 */
	public static boolean writeFile(String filePath, String fileContent)
			throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = fileContent + "\r\n";
		String temp = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		Writer pw = null;
		try {
			File file = new File(filePath);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			// 保存该文件原有的内容
			for (int i = 1; (temp = br.readLine()) != null; i++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);
			fos = new FileOutputStream(file);
			pw = new OutputStreamWriter(fos, "utf-8");
			pw.write(buf.toString());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	// 判断文件的有效性
	public static boolean getFile(String downLoadPath, String fileSavePath) {
		boolean returnValue = false;
		String fileName = downLoadPath.substring(
				downLoadPath.lastIndexOf("/") + 1, downLoadPath.length());
		String path = fileSavePath + File.separator + fileName;
		File file = new File(path);
		if (file.isFile()) {
			returnValue = true;
		}
		return returnValue;
	}

	/**
	 * @param bytes(文件内容的字节表示)
	 *            fileName(文件名称)
	 * @exception IOException
	 *                Histroy(历史修改记录): Description(方法描述):根据文件名称，将字节写入文件
	 *                Calls：被本方法调用的方法清单 Called By：调用本方法的方法清单 Table
	 *                Accessed：被访问的表 Table Update：被修改的表
	 */
	public static void writeToBytes(byte bytes[], String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName, true);
			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException iex) {
			}
		}
	}

	/**
	 * 读取文件内容
	 */
	public static String readFile(String fileName) {
		String output = "";
		File file = new File(fileName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
					BufferedReader input = new BufferedReader(new FileReader(
							file));
					StringBuffer buffer = new StringBuffer();
					String text;

					while ((text = input.readLine()) != null)
						buffer.append(text + "/n");

					output = buffer.toString();
				} catch (IOException ioException) {
					System.err.println("读取文件错误!");

				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents:/n";
				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "/n";
				}
			}
		} else {
			System.err.println("文件不存在!");
		}
		return output;
	}

}
