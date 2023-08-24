package com.helpCenter.elasticSearch.aggregators;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IntervalDataResponseAggregation {

	String date;
	long dataCount;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getDataCount() {
		return dataCount;
	}

	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
	}

	public IntervalDataResponseAggregation(long date, long dataCount) {
		super();
		this.date = convertTimestampToDateTime(date);
		this.dataCount = dataCount;
	}

	@Override
	public String toString() {
		return "IntervalDataResponseAggregation [date=" + date + ", dataCount=" + dataCount + "]";
	}

	public static String convertTimestampToDateTime(long timestampInMillis) {
		Instant instant = Instant.ofEpochMilli(timestampInMillis);
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTime.format(formatter);
	}
}
