package com.cs.poi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/1/6.
 */
public class ExcelUtil {

	public static <T> List<T> readExcel(String path, Class<T> cls){
		List<T> dataList = new ArrayList<>();

		Workbook workbook = null;
		try {
			if(path.endsWith("xlsx")){
				FileInputStream is = new FileInputStream(new File(path));
				workbook = new XSSFWorkbook(is);
			}
			if(path.endsWith("xls")){
				FileInputStream is = new FileInputStream(new File(path));
				workbook = new HSSFWorkbook(is);
			}
			if(workbook != null){
				//类映射
				Map<String, List<Field>> classMap = new HashMap<>();
				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields) {
					ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
					if(annotation != null){
						String value = annotation.value();
						if(!classMap.containsKey(value)){
							classMap.put(value, new ArrayList<>());
						}
						field.setAccessible(true);
						classMap.get(value).add(field);
					}
				}
				Map<Integer, List<Field>> reflectionMap = new HashMap<>();
				Sheet sheet = workbook.getSheetAt(0);
				AtomicInteger ai = new AtomicInteger();
				sheet.forEach(row->{
					int i = ai.incrementAndGet();
					AtomicInteger aj = new AtomicInteger();
					if(i == 1){//首行  提取注解
						row.forEach(cell -> {
							int j = aj.incrementAndGet();
							String cellValue = getCellValue(cell);
							if(classMap.containsKey(cellValue)){
								reflectionMap.put(j, classMap.get(cellValue));
							}
						});
					}else{
						try {
							T t = cls.newInstance();
							row.forEach(cell -> {
								int j = aj.incrementAndGet();

								if(reflectionMap.containsKey(j)){
									String cellValue = getCellValue(cell);
									List<Field> fieldList = reflectionMap.get(j);
									for (Field field : fieldList) {
										try {
											field.set(t, cellValue);
										}catch (Exception e){
											e.printStackTrace();
										}
									}
								}
							});
							dataList.add(t);
						}catch (Exception e){
							e.printStackTrace();
						}
					}
					//System.out.println();
				});
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(workbook != null){
				try {
					workbook.close();
				}catch (Exception e){
				}
			}
		}
		return dataList;
	}

	public static String getCellValue(Cell cell){
		if(cell.getCellType() == cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue()).trim();
		}else
		if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
			return String.valueOf(cell.getNumericCellValue()).trim();
		}else{
			return String.valueOf(cell.getStringCellValue()).trim();
		}

	}

	public static <T> void writeExcel(String path, List<T> dataList, Class<T> cls) {
		Type genericSuperclass = dataList.getClass().getGenericSuperclass();
		Type type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
		System.out.println("list type-->"+type.getTypeName());
		System.out.println("cls type-->"+cls.getName());


		Field[] fields = cls.getDeclaredFields();
		List<Field> fieldList = Arrays.stream(fields).filter(field->{
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if(annotation != null && annotation.sort() > 0) {
				field.setAccessible(true);
				return true;
			}
			return false;
		}).sorted(Comparator.comparing(field->{
			int sort = 0;
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if(annotation != null){
				sort = annotation.sort();
			}
			return sort;
		})).collect(Collectors.toList());



		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("demo sheet");

		AtomicInteger ai = new AtomicInteger();

		{
			Row row = sheet.createRow(ai.getAndIncrement());
			AtomicInteger aj = new AtomicInteger();

			//写入头部
			fieldList.forEach(field->{
				ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
				String columnName = "";
				if(annotation != null){
					columnName = annotation.value();
				}
				Cell cell = row.createCell(aj.getAndIncrement());

				cell.setCellValue(columnName);
			});
		}
		
		if(CollectionUtils.isNotEmpty(dataList)){
			dataList.forEach(t->{
				Row row = sheet.createRow(ai.getAndIncrement());
				AtomicInteger aj = new AtomicInteger();

				fieldList.forEach(field->{
					Object value = "";
					try {
						value = field.get(t);
					}catch (Exception e){
						e.printStackTrace();
					}
					Cell cell = row.createCell(aj.getAndIncrement());

					cell.setCellValue(value.toString());
				});
			});

		}

		//冻结窗格
		wb.getSheet("demo sheet").createFreezePane(0,1,0,1);

		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			wb.write(new FileOutputStream(file));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
