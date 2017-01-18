package com.cs.test.utils.excel;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by admin on 2017/1/17.
 */
@Data
public class ExcelDemo {
	@ExcelColumn(value="ID", col=1)
	private int id;
	@ExcelColumn(value="名称", col=2)
	private String name;

	public static void main(String[] args){
		long t1 = System.currentTimeMillis();
		ExcelUtils.writeExcel(".\\test.xlsx", IntStream.rangeClosed(1,10).mapToObj(i->{
			ExcelDemo excelDemo = new ExcelDemo();
			excelDemo.setId(i);
			excelDemo.setName(String.format("name-%s", i));
			return excelDemo;
		}).collect(Collectors.toList()), ExcelDemo.class);

		long t2 = System.currentTimeMillis();
		System.out.println(String.format("write over! cost:%sms", (t2-t1)));


		List<ExcelDemo> excelDemos = ExcelUtils.readExcel(".\\test.xlsx", ExcelDemo.class);
		excelDemos.stream().forEach(b->System.out.println(JSON.toJSONString(b, true)));

		long t3 = System.currentTimeMillis();
		System.out.println(String.format("read over! cost:%sms", (t3-t2)));
	}
}
