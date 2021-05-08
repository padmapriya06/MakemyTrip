package com.utils;

import java.util.Date;

public class DateUtils {
	//Method getting the Timestamp
    public static String getTimeStamp(){
		Date date = new Date();
		return date.toString().replaceAll(":", "_").replaceAll(" ", "_");
			}
}