package com.cs.test.utils.excel;

import com.cs.test.utils.DateFormatter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/1/17.
 */
public class ExcelUtils {
	
	private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	private final static String EXCEL2003 = "xls";
	private final static String EXCEL2007 = "xlsx";


	public static <T> List<T> readExcel(String path, Class<T> cls){
		List<T> dataList = new ArrayList<>();

		Workbook workbook = null;
		try {
			if(path.endsWith(EXCEL2007)){
				FileInputStream is = new FileInputStream(new File(path));
				workbook = new XSSFWorkbook(is);
			}
			if(path.endsWith(EXCEL2003)){
				FileInputStream is = new FileInputStream(new File(path));
				workbook = new HSSFWorkbook(is);
			}
			if(workbook != null){
				//类映射  注解 value-->bean columns
				Map<String, List<Field>> classMap = new HashMap<>();
				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields) {
					ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
					if(annotation != null){
						String value = annotation.value();
						if(StringUtils.isBlank(value)){
							continue;
						}
						if(!classMap.containsKey(value)){
							classMap.put(value, new ArrayList<>());
						}
						field.setAccessible(true);
						classMap.get(value).add(field);
					}
				}
				//索引-->columns
				Map<Integer, List<Field>> reflectionMap = new HashMap<>();
				Sheet sheet = workbook.getSheetAt(0);//默认读取第一个sheet

				boolean firstRow = true;
				for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);

					if(firstRow){//首行  提取注解
						for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
							Cell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							if(classMap.containsKey(cellValue)){
								reflectionMap.put(j, classMap.get(cellValue));
							}
						}
						firstRow = false;
					}else{
						if(row == null){//忽略空白行
							continue;
						}

						try {
							T t = cls.newInstance();
							boolean allBlank = true;//判断是否为空白行
							for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
								if(reflectionMap.containsKey(j)){
									Cell cell = row.getCell(j);
									String cellValue = getCellValue(cell);
									if(StringUtils.isNotBlank(cellValue)){
										allBlank = false;
									}
									List<Field> fieldList = reflectionMap.get(j);
									for (Field field : fieldList) {
										try {
											handleField(t, cellValue, field);
										}catch (Exception e){
											log.error(String.format("reflect field:%s value:%s exception!", field.getName(), cellValue), e);
										}
									}
								}
							}
							if(!allBlank){
								dataList.add(t);
							}else{
								log.warn(String.format("row:%s is blank ignore!", i));
							}
						}catch (Exception e){
							log.error(String.format("parse row:%s exception!", i), e);
						}
					}
				}
			}
		}catch (Exception e){
			log.error(String.format("parse excel exception!"), e);
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

	private static <T> void handleField(T t, String value, Field field) throws Exception {
		Class<?> type = field.getType();
		if(type == null || type == void.class || StringUtils.isBlank(value)){
			return;
		}
		if(type == Object.class){
			field.set(t, value);
		}else if(type.getSuperclass() == null || type.getSuperclass() == Number.class){//数字类型
			if(type == int.class || type == Integer.class){
				field.set(t, NumberUtils.toInt(value));
			}else if(type == long.class || type == Long.class){
				field.set(t, NumberUtils.toLong(value));
			}else if(type == byte.class || type == Byte.class){
				field.set(t, NumberUtils.toByte(value));
			}else if(type == short.class || type == Short.class){
				field.set(t, NumberUtils.toShort(value));
			}else if(type == double.class || type == Double.class){
				field.set(t, NumberUtils.toDouble(value));
			}else if(type == float.class || type == Float.class){
				field.set(t, NumberUtils.toFloat(value));
			}else if(type == char.class || type == Character.class){
				field.set(t, CharUtils.toChar(value));
			}else if(type == boolean.class){
				field.set(t, BooleanUtils.toBoolean(value));
			}else if(type == BigDecimal.class){
				field.set(t, new BigDecimal(value));
			}
		} else if(type == Boolean.class){
			field.set(t, BooleanUtils.toBoolean(value));
		} else if(type == Date.class){
			field.set(t, DateFormatter.parser(value));
		} else if(type == String.class){
			field.set(t, value);
		}else{
			Constructor<?> constructor = type.getConstructor(String.class);
			field.set(t, constructor.newInstance(value));
		}
	}

	private static String getCellValue(Cell cell){
		if(cell == null){
			return "";
		}
		if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
			if(DateUtil.isCellDateFormatted(cell)){
				return DateFormatter.longDate(DateUtil.getJavaDate(cell.getNumericCellValue()));
			}else{
				return new BigDecimal(cell.getNumericCellValue()).toString();
			}
		}else if(cell.getCellType() == cell.CELL_TYPE_STRING){
			return StringUtils.trimToEmpty(cell.getStringCellValue());
		}else if(cell.getCellType() == cell.CELL_TYPE_FORMULA){
			return  StringUtils.trimToEmpty(cell.getCellFormula());
		}else if(cell.getCellType() == cell.CELL_TYPE_BLANK){
			return "";
		}else if(cell.getCellType() == cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}else if(cell.getCellType() == cell.CELL_TYPE_ERROR){
			return "ERROR";
		}else{
			return cell.toString().trim();
		}

	}

	public static <T> void writeExcel(String path, List<T> dataList, Class<T> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<Field> fieldList = Arrays.stream(fields).filter(field->{
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if(annotation != null && annotation.col() > 0) {
				field.setAccessible(true);
				return true;
			}
			return false;
		}).sorted(Comparator.comparing(field->{
			int col = 0;
			ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
			if(annotation != null){
				col = annotation.col();
			}
			return col;
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

				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

				Font font = wb.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(columnName);
			});
		}

		if(CollectionUtils.isNotEmpty(dataList)){
			dataList.forEach(t->{
				Row row = sheet.createRow(ai.getAndIncrement());
				AtomicInteger aj = new AtomicInteger();

				fieldList.forEach(field->{
					Class<?> type = field.getType();

					Object value = "";
					try {
						value = field.get(t);
					}catch (Exception e){
						e.printStackTrace();
					}
					Cell cell = row.createCell(aj.getAndIncrement());

					if(value != null){
						if(type == Date.class){
							cell.setCellValue(DateFormatter.longDate((Date) value));
						}else{
							cell.setCellValue(value.toString());
						}
					}
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
