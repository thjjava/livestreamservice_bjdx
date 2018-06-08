package com.sttri.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZIPUtil {

	public static void main(String[] args) throws Exception {
		unzip("g:/ceshi.zip", "g://ceshi");
	}

	static String getSuffixName(String name) {
		return name.substring(0, name.lastIndexOf("."));
	}

	public static void unzip(String zipFilePath, String unzipDirectory)
			throws Exception {
		// 创建文件对象
		File file = new File(zipFilePath);
		// 创建zip文件对象
		ZipFile zipFile = new ZipFile(file);
		// 创建本zip文件解压目录
		File unzipFile = new File(unzipDirectory);
		if (unzipFile.exists())
			unzipFile.delete();
		unzipFile.mkdirs();
		// 得到zip文件条目枚举对象
		Enumeration<?> zipEnum = zipFile.entries();
		// 定义输入输出流对象
		InputStream input = null;
		OutputStream output = null;
		// 循环读取条目
		System.out.println("name\t\t\tsize\t\t\tcompressedSize\t\t\tisDirectory");
		while (zipEnum.hasMoreElements()) {
			// 得到当前条目
			ZipEntry entry = (ZipEntry) zipEnum.nextElement();
			String entryName = new String(entry.getName());
			System.out.println(entryName + "\t\t\t" + entry.getSize()
					+ "\t\t\t" + entry.getCompressedSize() + "\t\t\t\t\t\t\t"
					+ entry.isDirectory());

			// 若当前条目为目录则创建
			if (entry.isDirectory())
				new File(unzipFile.getAbsolutePath() + File.separator + entryName).mkdir();
			else { // 若当前条目为文件则解压到相应目录
				input = zipFile.getInputStream(entry);
				output = new FileOutputStream(new File(unzipFile.getAbsolutePath()
						+ File.separator + entryName));
				byte[] buffer = new byte[1024 * 8];
				int readLen = 0;
				while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1)
					output.write(buffer, 0, readLen);
				input.close();
				output.flush();
				output.close();
			}
		}
	}

	// 返回条目的注释字符串；如果没有，则返回 null
	// System.out.println(entry.getComment());
	// 返回未压缩条目数据的 CRC-32 校验和；如果未知，则返回 -1
	// System.out.println(entry.getCrc());
	// 返回条目的压缩方法；如果未指定，则返回 -1
	// System.out.println(entry.getMethod());
	// 返回条目的额外字段数据；如果没有，则返回 null
	// System.out.println(entry.getExtra());
	// 返回条目的修改时间；如果未指定，则返回 -1
	// System.out.println(entry.getTime());

}