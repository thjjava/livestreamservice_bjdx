/**
 * <p> * Title: 健康点评网统一后台管理平台* </p>
 * <p> * Description: 上海磐康科技有限公司--统一后台管理* </p>
 * <p> * Copyright: Copyright (c) 2010-2011 * </p>
 * <p> * Company: 上海磐康科技有限公司 * </p>
 * @author 叶明（开发）
 * @version 0.1
 */
package com.sttri.util;


/**
 * 程序创建时间：Dec 13, 2010 - 9:26:56 AM
 * 程序开发者：叶明(guming123416@163.com)
 * 程序修改者：
 * 程序修改时间：Dec 13, 2010 - 9:26:56 AM
 * 程序作用：
 * 1、
 * 2、
 * 程序修改：
 * 1、
 * 2、
 * @version 0.1
 */
public class ExcelUtil {
	/**
	 * 获取Excel表中设备集合
	 * @param path
	 * @return
	public static List<DevVo> getByExcel(String path){
		List<DevVo> list = new ArrayList<DevVo>();
		InputStream stream;
		try {
			stream = new FileInputStream(new File(path));
			Workbook wb=Workbook.getWorkbook(stream);
			Sheet sheet=wb.getSheet(0);
			int count=sheet.getRows();
			for(int i=1;i<count;i++) {
				DevVo devVo = new DevVo();
				Cell id= sheet.getCell(0,i);
				String devNo=id.getContents().trim();
				devVo.setDevNo(devNo);
				if("".equals(devNo)){
					break;
				}
				id=sheet.getCell(1,i);
				String devKey=id.getContents().trim();
				devVo.setDevKey(devKey);
				id=sheet.getCell(2,i);
				String devName=id.getContents().trim();
				devVo.setDevName(devName);
				id=sheet.getCell(3,i);
				String devVer=id.getContents().trim();
				devVo.setDevVer(devVer);
				list.add(devVo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	 */
	
	public static void main(String[] args) {
		//getByExcel("C:/Users/Administrator/Desktop/test.xls");
	}
}
