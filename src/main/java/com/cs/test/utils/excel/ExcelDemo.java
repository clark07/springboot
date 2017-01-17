package com.cs.test.utils.excel;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/1/17.
 */
@Data
public class ExcelDemo {

	@ExcelColumn(value="商品编码", col=1)
	private long code;
	@ExcelColumn(value="商品名称", col=2)
	private String name;

	@ExcelColumn(value="商品属性", col=3)
	private int feature;
	@ExcelColumn(value="品牌名称", col=4)
	private String brandName;
	@ExcelColumn(value="分类名称", col=5)
	private String categoryIdPath;
	@ExcelColumn(value="单品/多品", col=6)
	private Integer saleUnit;
	//@ExcelColumn(value="流水号", col=1)
	@ExcelColumn(value="保质期", col=7)
	private Integer shelfLife;
	@ExcelColumn(value="单位", col=8)
	private String unit;
	@ExcelColumn(value="规格", col=9)
	private String specification;
	@ExcelColumn(value="箱柜", col=10)
	private String cartonSize;
	@ExcelColumn(value="采购类型", col=11)
	private Integer purchaseType;
	@ExcelColumn(value="商品类型", col=12)
	private Integer goodsType;
	@ExcelColumn(value="废弃状态", col=13)
	private int discardStatus;
	@ExcelColumn(value="条形码", col=14)
	private String barCode;
	@ExcelColumn(value="商品副标题", col=15)
	private String title;
	@ExcelColumn(value="关键词", col=16)
	private String keyword;
	@ExcelColumn(value="备注", col=17)
	private String remark;
	@ExcelColumn(value="大圣说", col=18)
	private String bossSay;
	@ExcelColumn(value="商品视频", col=19)
	private String videoUrl;
	@ExcelColumn(value="视频缩略图", col=20)
	private String videoImage;
	@ExcelColumn(value="缩略图", col=21)
	private String thumbnail;
	@ExcelColumn(value="商品描述", col=22)
	private String detail;
	@ExcelColumn(value="图文详情", col=23)
	private String images;

	@ExcelColumn(value="创建时间", col=24)
	private Date createTime;
	@ExcelColumn(value="更新时间", col=25)
	private Date lastModifiedTime;






	    public static void main(String[] args){
		long st = System.currentTimeMillis();
		List<ExcelDemo> excelDemos = ExcelUtils.readExcel("C:\\Users\\admin\\Downloads\\商品信息.xlsx", ExcelDemo.class);
			//List<ExcelDemo> excelDemos = ExcelUtils.readExcel("C:\\Users\\admin\\Desktop\\test.xlsx", ExcelDemo.class);

			//excelDemos.stream().forEach(b->System.out.println(JSON.toJSONString(b, true)));
			long et = System.currentTimeMillis();
			System.out.println((et-st)+"ms");
			//System.out.println(JSON.toJSONString(excelDemos, true))
			//
			//
			ExcelUtils.writeExcel("C:\\Users\\admin\\Desktop\\test.xlsx", excelDemos.stream().limit(200).collect(Collectors.toList()), ExcelDemo.class);
		}

	public static boolean IsNumer(Class<?> t) {
		if (t == null) {
			return false;
		}
		if (t == Object.class) {
			return false;
		}
		if (t == void.class) {
			return false;
		}
		Class<?> superClass = t.getSuperclass();
		if (superClass == null) {
			return true;
		}
		return superClass == Number.class;
	}
}
