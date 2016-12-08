package com.cs.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateFormatter {
	
	//private static SimpleDateFormat formatter;
	static Logger logger = Logger.getLogger(DateFormatter.class);
	public static TimeZone CUSTOM_TIMEZONE = TimeZone.getDefault();
	
	public static String yyyyMMddHHmmss="yyyyMMddHHmmss";
	
	public static String yyyy_MM_dd_HH_mm_ss="yyyy-MM-dd HH:mm:ss";

	public DateFormatter() {
	}

	public static Date localToUTC(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime()
				- CUSTOM_TIMEZONE.getOffset(date.getTime()));

		return c.getTime();
	}

	public static Date utcToLocal(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime()
				+ CUSTOM_TIMEZONE.getOffset(date.getTime()));
		return c.getTime();
	}

	public static String shortDate(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(aDate);
	}

	public static String mailDate(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return formatter.format(aDate);
	}
	
	public static String longDate(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}

	public static String shortDateGB(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy'MM'dd'");
		return formatter.format(aDate);
	}

	public static String longDateGB(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}

	public static String formatDate(Date aDate, String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		return formatter.format(aDate);

	}
	
	public static String formatyyMMdd(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		return formatter.format(aDate);
	}
	public static String formatHHmmss(Date aDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		return formatter.format(aDate);
	}
	
	/**
	 * 得到当前 时间  字符
	 * @return
	 */
	public static String getNowString() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}
	
	/***
	 * 得到当前时间
	 * @return
	 */
	public static String getNotyyyyMMddHHmmss() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}
	
	/***
	 * 得到当前时间
	 * @return
	 */
	public static String getNotyyyyMMddHHmmssSSS() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}
	
	/***
	 * 得到当前时间
	 * @return
	 */
	public static String getyyMMdd() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}
	
	/***
	 * 得到当前时间
	 * @return
	 */
	public static String getNotyyyy_MM_dd() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}
	
	/***
	 * 得到当前时间
	 * @return
	 */
	public static String getNotyyyyMMdd() {
		Date aDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		formatter.setTimeZone(CUSTOM_TIMEZONE);
		return formatter.format(aDate);
	}

	public static String LDAPDate(Date aDate) {
		return formatDate(aDate, "yyyyMMddHHmm'Z'");
	}

	public static Date StringToDate(String yyyyMMddHHmmss) {
		//return formatDate(aDate, "yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date date = null;
        try {
            date = format.parse(yyyyMMddHHmmss);   
        } catch (Exception e) {
            e.printStackTrace();  
        }  
       // date = java.sql.Date.valueOf(yyyyMMddHHmmss);  
        return date;  
	}
	
	public static Date StringToDate(String dateStr, String formatStr){
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDate(String yyyymmdd) {
		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		int year = Integer.parseInt(yyyymmdd
				.substring(0, yyyymmdd.indexOf("-")));
		int month = Integer.parseInt(yyyymmdd.substring(
				yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
		int day = Integer.parseInt(yyyymmdd
				.substring(yyyymmdd.lastIndexOf("-") + 1));
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.getTime();
	}

	public static Date getDateFull(String yyyymmdd) {
		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		int year = Integer.parseInt(yyyymmdd
				.substring(0, yyyymmdd.indexOf("-")));
		int month = Integer.parseInt(yyyymmdd.substring(
				yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
		int day = Integer.parseInt(yyyymmdd
				.substring(yyyymmdd.lastIndexOf("-") + 1));
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 24, 60, 60);
		return cal.getTime();

	}

	public static Date parser(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseryyyyMMdd(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseryyyyMMdd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String dStr=sdf.format(date);
			return sdf.parse(dStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getDateFromyyMMdd(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			try {
				return sdf.parse(date);
			} catch (ParseException e1) {
				logger.error("Date str:"+date,e1);
			}
		}
		return null;
	}


	public static Date parser(String strDate, String formatter) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		sdf.setTimeZone(CUSTOM_TIMEZONE);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get Date with only date num. baoxy add
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static Date getDateOnly(String yyyymmdd) {
		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		int year = Integer.parseInt(yyyymmdd
				.substring(0, yyyymmdd.indexOf("-")));
		int month = Integer.parseInt(yyyymmdd.substring(
				yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
		int day = Integer.parseInt(yyyymmdd
				.substring(yyyymmdd.lastIndexOf("-") + 1));
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}
	

	public static Date addMonth(Date myDate, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		boolean isEndDayOfMonth_old = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		cal.add(GregorianCalendar.MONTH, amount);
		boolean isEndDayOfMonth_new = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
			cal.set(GregorianCalendar.DATE, cal
					.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	public static Date addDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	/***
	 * 添加分钟
	 */
	public static Date addMin(Date d, int min){
		long time = d.getTime();
		time=time+(min*60*1000);
		return new Date(time);
	}

	/***
	 * 添加小时
	 * @param d
	 * @param housr
	 * @return
	 */
	public static Date addHoursDate(Date d , int housr){
		Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + housr);
        return now.getTime();
	}
	
	
	public static Date addYear(Date myDate, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		boolean isEndDayOfMonth_old = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		cal.add(GregorianCalendar.YEAR, amount);
		boolean isEndDayOfMonth_new = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
			cal.set(GregorianCalendar.DATE, cal
					.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	public static Date getFirstDay(Date date) {
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/*
	 * the mapping from jdk is : 1-Sun; 2-Mon;3-Tues; 4-Weds; 5-Thur;6-Fri;
	 * 7-Sat;
	 */
	public static int getWeekDay(Date myDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		return cal.get(GregorianCalendar.DAY_OF_WEEK);
	}

	/*
	 * the mapping from vas2005 is : 1-Mon;2-Tues; 3-Weds; 4-Thur;5-Fri;
	 * 6-Sat;7-Sun;
	 */
	public static int getConvertWeekDay(Date myDate) {
		int day = getWeekDay(myDate);
		int result = day - 1;
		if (result == 0)
			result = 7;
		return result;
	}

	public static int getTimeFromDate(Date myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		int result = Integer.parseInt(sdf.format(myDate));
		return result;
	}
	
	public static String getMonthFirstDay(String format){
		//"yyyy-MM-dd HH:mm:ss"
		SimpleDateFormat df = new SimpleDateFormat(format);
	    Date date = new Date();
	    Date da = new Date(date.getYear(),date.getMonth(),01);
	    return df.format(da);
	}
	
	public static void main(String[] args) throws ParseException {
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		System.out.println("今天的日期：" + df.format(d));
		System.out.println("两天前的日期
				+ df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000)));
		System.out.println("三天后的日期
				+ df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000)));
		System.out.println("60分前的日期："
				+ df.format(new Date(d.getTime() -  60*60 * 1000)));
		*/
		
		Date de= DateFormatter.parser("2014-08-14");
		Date preDate= DateFormatter.getDateFromyyMMdd(String.valueOf("140618"));
		Date nowDate=new Date(preDate.getTime()+3600*24*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		//System.out.println(DateFormatter.formatyyMMdd(nowDate));
		System.out.println(DateFormatter.getMonthEndTime(DateFormatter.parseryyyyMMdd("2017-1-14")));
		//System.out.println((new Date(DateFormatter.getMorning().toString()).getTime()));
	}
	
	//变化oldTime的时day是正数就是增加天数是负数就是减少天数,minute是正数就是增加分钟数是负数就是减少分钟数,format为时间格
	public static String changeDate(String oldTime, Integer day, Integer minute, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			if(day!=null){
				Date d =df.parse(oldTime);
				oldTime=df.format(new Date(d.getTime() + day * 24 * 60 * 60 * 1000L));
			}
			if(minute!=null){
				Date d =df.parse(oldTime);
				oldTime=df.format(new Date(d.getTime() +  minute* 60 * 1000));
			}
			return oldTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldTime;
	}
	
	  /**  
     * 某个时间点的下个月的第一 
     * @param day  
     * @return  
     */  
    public static Date firstDayInNextMonth(Date day){
        Calendar c = Calendar.getInstance();
        c.setTime(day);   
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();   
    }   
  
    /**  
     * 获取某天在星期中的排序 
     * 星期-> 星期对应为：1 -> 7  
     * @param date  
     * @return  
     */  
    public static int getDateInWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        return c.get(Calendar.DAY_OF_WEEK);
    }   
  
    /**  
     * 获取指定日期后n天的凌晨  
     * @param date  
     * @param n  
     * @return  
     */  
    public static Date getDateNextDay(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        c.add(Calendar.DATE, n);
        return c.getTime();   
    }   
       
    /**  
     * 获取下n个月后的日期  
     * @param n 月份偏移 
     * @return  
     */  
    public static Date getDateNextMonth(int n) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + n);// 设置时间向前进n个月
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();   
    }   
  
    /**  
     * 获取今天在本月中的号 
     * @return  
     */  
    public static int getDateOfMoth() {   
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }   
       
    /**  
     * 本月的所有日期集 
     * @return  
     */  
    public static List<Date> getDatesInMonth() {
  
        List<Date> dates = new ArrayList<Date>();
  
        Calendar c = Calendar.getInstance();
  
        // 设置代表的日期为1  
        c.set(Calendar.DATE, 1);
  
        // 获得当前月的日期  
        int maxDay = c.getActualMaximum(Calendar.DATE);
  
        for (int i = 1; i <= maxDay; i++) {   
            c.set(Calendar.DATE, i);
            dates.add(c.getTime());   
        }   
  
        return dates;   
    }   
       
    /**  
     * 获取某个时间的月 
     * @param date  
     * @return  
     */  
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        return c.get(Calendar.MONTH) + 1;
    }   
  
    /**  
     * 获取本月  
     * @return  
     */  
    public static Date getMonthEnd() {
        int length = getDateOfMoth();   
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, length);
        c.set(Calendar.HOUR, 24);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();   
    }   
  
    /**  
     * 获取某个时间月份的最后一 
     * @param date   
     * @return  
     */  
    public static Date getMonthEnd(Date date){
        if(date == null){   
            return null;   
        }   
        Calendar start = Calendar.getInstance();
        start.setTime(date);   
        start.set(Calendar.MONTH, start.get(Calendar.MONTH)+1);
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start.getTime();        
    } 
    
    /**  
     * 获取某个时间月份的第凌晨  
     * @param date   
     * @return  
     */  
    public static Date getMonthStart(Date date){
        if(date == null){   
            return null;   
        }   
        Calendar start = Calendar.getInstance();
        start.setTime(date);   
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        return start.getTime();        
    }   
       
    /**  
     * 获取今天凌晨  
     * @return  
     */  
    public static Date getMorning() {
        return getMorning(new Date());
    }
    
  
    /**  
     * 获取指定时间当天的凌 
     * @param date 给定时间当天的凌 
     * @return  
     */  
    public static Date getMorning(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();   
    }
  
    /**  
     * 获取当前时间N天后的凌 
     */  
    public static Date getMorningNextDate(int n) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + n); //设置时间向前进n
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();   
    }   
       
    /**  
     * 系统当前时间过N个月后的时间  
     * @param nextStep 月份偏移 
     * @return  
     */  
    public static Date getNextMonth(int nextStep){
        Calendar c = Calendar.getInstance();
        int m = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, m + nextStep);
        return c.getTime();   
    }   
  
    /**  
     * 计算给定时间推进月对应的时间  
     * @param date 给定时间  
     * @return 某时间过月后的时 
     */  
    public static Date getNextMonthToday(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        return c.getTime();   
    }   
       
    /**  
     * 获取系统当前时间的年 
     * @return  
     */  
    public static int getYear() {   
        return Calendar.getInstance().get(Calendar.YEAR);
    }   
       
    /**  
     * 获取给定时间的年 
     * @param date 时间  
     * @return 时间的年 
     */  
    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);   
        return c.get(Calendar.YEAR);
    }   
  
    /**  
     * 获取某年分的结束的时 
     * @param year 年份  
     * @return 指定年份的最后一天结 
     */  
    public static Date getYearEnd(int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.DECEMBER);
        c.set(Calendar.DAY_OF_MONTH, 31);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();   
    }   
  
    /**  
     * 获取某年份的第一天凌 
     * @param year 年份  
     * @return 指定年份的第凌晨  
     */  
    public static Date getYearStart(int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH,1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();   
    } 
    
    public static String getWeekId(String week) {
		if (StringUtils.equals("周一", week)) {
			return "1";
		} else if (StringUtils.equals("周二", week)) {
			return "2";
		} else if (StringUtils.equals("周三", week)) {
			return "3";
		} else if (StringUtils.equals("周四", week)) {
			return "4";
		} else if (StringUtils.equals("周五", week)) {
			return "5";
		} else if (StringUtils.equals("周六", week)) {
			return "6";
		} else if (StringUtils.equals("周日", week)) {
			return "7";
		}

		return "0";
	}
    
	public static Integer getCH_weekday(String dateStr, String formatStr){
		Date date = DateFormatter.parser(dateStr, formatStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(weekday==0){
			weekday=7;
		}
		return weekday;
	}
	
	public static String getOrderyyMM(long orderId) {
		String yyMM = "";
		try {
			String yy = String.valueOf(orderId).substring(2, 4);
			String mm = String.valueOf(orderId).substring(4, 6);
			/**
			 * 只接受16(+)年1-12月的订单号
			 */
			if(NumberUtils.toInt(yy) >= 16 && NumberUtils.toInt(mm) >= 1 && NumberUtils.toInt(mm) <= 12) {
				yyMM = yy+mm;
			}
		} catch (Exception e) {
			logger.error(String.format("getOrderyyMM from orderId:%s exception, reset to default value!", orderId));
			yyMM = "";
		}
		
		return yyMM;
	}
	
	public static int getDiffDay(Date fromDate, Date endDate){
		return getDiffDay(fromDate, endDate, 0);
	}
	
	public static int getDiffDay(Date fromDate, Date endDate, int defaultValue){
		if(fromDate == null || endDate ==  null){
			return defaultValue;
		}
		
		return (int) ((endDate.getTime()-fromDate.getTime())/(24L*60*60*1000));
	}
	
	public static int getDiffHour(Date fromDate, Date endDate){
		if(fromDate == null || endDate ==  null){
			return 0;
		}
		
		return (int) ((endDate.getTime()-fromDate.getTime())/(60*60*1000));
		
	}
	
	public static int getDiffMinute(Date fromDate, Date endDate){
		if(fromDate == null || endDate ==  null){
			return 0;
		}
		
		return (int) ((endDate.getTime()-fromDate.getTime())/(60*1000));
		
	}
	
	public static Date getMonthEndTime(Date date){
		if (date == null) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(parser(formatDate(date, "yyyy-MM"), "yyyy-MM"));
		start.add(Calendar.MONTH, 1);
		start.add(Calendar.MILLISECOND, -1);
		return start.getTime();
	}
	
}

