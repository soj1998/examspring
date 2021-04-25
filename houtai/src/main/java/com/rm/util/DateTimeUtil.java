package com.rm.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

	public static LocalDate getDayNow() {
		LocalDate today = LocalDate.now();
		return today;
	}
	
	public static String getDayNowString() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter fmTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String rs = today.format(fmTime);
		return rs;
	}
	
	public static LocalDateTime getTimeNow() {
		LocalDateTime today = LocalDateTime.now();
		return today;
	}
	
	public static String getTimeNowString() {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter fmTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String rs = today.format(fmTime);
		return rs;
	}
}
