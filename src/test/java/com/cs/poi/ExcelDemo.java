package com.cs.poi;

import lombok.Data;

import java.util.List;

public class ExcelDemo {
	    public static void main(String[] args) throws Exception{
			List<ExcelBean> excelBeans = ExcelUtil.readExcel("d:/本周开发需求.xlsx", ExcelBean.class);
			//List<ExcelBean> excelBeans = ExcelUtil.readExcel("d:/本周开发需求.xls", ExcelBean.class);
			excelBeans.forEach(System.out::println);
			ExcelUtil.writeExcel("d:/demoexcel.xlsx", excelBeans, ExcelBean.class);
/*			List<ExcelBean> dataList = new ArrayList<ExcelBean>() {};
			dataList.addAll(excelBeans);
			ExcelUtil.writeExcel("d:/demoexcel.xlsx", dataList, ExcelBean.class);*/
	    }

	    @Data
	    static class ExcelBean{
	    	@ExcelColumn(value = "需求名称", sort = 1)
	    	private String name;
	    	@ExcelColumn(value = "完成天数", sort = 2)
	    	private String day;
	    	@ExcelColumn("完成天数")
	    	private String day2;

	    	private String other;
			@Override
			public String toString() {
				return "ExcelBean{" + "name='" + name + '\'' + ", day='" + day + '\'' + ", day2='" + day2 + '\'' + ", other='" + other + '\'' + '}';
			}
		}
}
