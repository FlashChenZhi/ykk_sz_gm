package jp.co.daifuku.wms.YkkGMAX.Utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StringUtils
{

	public static String convertNullToEmpty(String stringValue)
	{
		return (stringValue == null) ? " " : stringValue;
	}

	public static String formatStringFromDatabase(String stringValue)
	{
		String value = trimRight(convertNullToEmpty(stringValue));
		if(value.length() > 0)
		{
			return value;
		}
		else
		{
			return " ";
		}
	}

	public static String trimRight(String stringValue)
	{
		if (stringValue == null)
			return null;

		String tempString = new String(stringValue);
		tempString = tempString.trim();
		if (tempString.length() == stringValue.length())
		{
			return stringValue;
		}

		int length = tempString.length();
		int index = stringValue.indexOf(tempString);
		return stringValue.substring(0, index + length);
	}

	public static boolean IsNullOrEmpty(String value)
	{
		if (value == null || value.trim().equalsIgnoreCase(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static String surroundWithSingleQuotes(String value)
	{
		return "'" + value + "'";
	}

	public static String surroundWithPercentageMarks(String value)
	{
		return "%" + convertNullToEmpty(value) + "%";
	}

	public static String SinglePercentageMark = "%";

	public static String formatDateFromDBToPage(String date)
	{
		try
		{
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6, 8);

			return year + "/" + month + "/" + day;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	public static String formatStartTimingFlagFromDBToPage(String flag)
	{
		if (flag.equals(DBFlags.StartTimingFlag.AM))
		{
			return "AM";
		}
		else if (flag.equals(DBFlags.StartTimingFlag.PM))
		{
			return "PM";
		}
		else
		{
			return "";
		}
	}

	public static String formatCompleteTimingFlagFromDBToPage(String flag)
	{
		if (flag.equals(DBFlags.CompleteTimingFlag.AM))
		{
			return "AM";
		}
		else if (flag.equals(DBFlags.CompleteTimingFlag.PM))
		{
			return "PM";
		}
		else
		{
			return "";
		}
	}

	public static String formatHikiFlg(String flag)
	{
		if (flag.equals(DBFlags.HikiFlg.UNUSED))
		{
			return "";
		}
		else
		{
			return "分配";
		}
	}

	public static String[] formatDateAndTimingFlagFromPageToDB(
			String dateAndTimingFlag)
	{
		return dateAndTimingFlag.split(" ");
	}

	public static String formatDateFromPageToDB(String date)
	{
		return date.replaceAll("/", "");
	}

	public static String formatStartTimingFlagFromPageToDB(String flag)
	{
		if (flag != null)
		{
			if (flag.equals("AM"))
			{
				return DBFlags.StartTimingFlag.AM;
			}
			else if (flag.equals("PM"))
			{
				return DBFlags.StartTimingFlag.PM;
			}
			else
			{
				return DBFlags.StartTimingFlag.UNKNOWN;
			}
		}
		else
		{
			return DBFlags.StartTimingFlag.UNKNOWN;
		}
	}

	public static String formatCompleteTimingFlagFromPageToDB(String flag)
	{
		if (flag != null)
		{
			if (flag.equals("AM"))
			{
				return DBFlags.StartTimingFlag.AM;
			}
			else if (flag.equals("PM"))
			{
				return DBFlags.StartTimingFlag.PM;
			}
			else
			{
				return DBFlags.StartTimingFlag.UNKNOWN;
			}
		}
		else
		{
			return DBFlags.StartTimingFlag.UNKNOWN;
		}
	}

	public static String formatLocationNoFromDBToPage(String locationNo)
	{
		if (locationNo.length() == 10)
		{
			return locationNo.substring(2, 4) + "-"
					+ locationNo.substring(5, 7) + "-"
					+ locationNo.substring(8, 10);
		}
		else
		{
			return locationNo;
		}
	}

	public static String formatLocationNoFromPageToDB(String locationNo)
	{
		if(locationNo.equals("平库"))
		{
			return "";
		}
		locationNo = locationNo.replaceAll("-", "");
		if (locationNo.length() == 6)
		{
			return "10" + locationNo.substring(0, 2) + "0"
					+ locationNo.substring(2, 4) + "0"
					+ locationNo.substring(4, 6);
		}
		else
		{
			return locationNo;
		}
	}

	public static String formatDateAndTimeFromPageToDB(String dateAndTime)
	{
		dateAndTime = dateAndTime.replaceAll("/", "");
		dateAndTime = dateAndTime.replaceAll(":", "");
		dateAndTime = dateAndTime.replaceAll(" ", "");

		return dateAndTime;
	}

	public static String formatDateAndTimeFromDBToPage(String dateAndTime)
	{
		if (dateAndTime.length() == 14)
		{
			return dateAndTime.substring(0, 4) + "/"
					+ dateAndTime.substring(4, 6) + "/"
					+ dateAndTime.substring(6, 8) + " "
					+ dateAndTime.substring(8, 10) + ":"
					+ dateAndTime.substring(10, 12) + ":"
					+ dateAndTime.substring(12, 14);
		}
		else
		{
			return "";
		}
	}

	public static class stStatus
	{
		public static String NoReadWaiting = "NoRead等待";

		public static String NoData = "无到达报告";

		public static String Normal = "正常";
	}

	public final static String Circle = "○";

	public final static String EmptyString = "";

	public static final String DispResourceName = "DispResource";
	
	public static final String ToMark = "→";

	public static final String nextRow = "\\r\\n";
	
	public static String formatTimeFormPageToDB(String time)
	{
		return time.replaceAll(":", "");
	}

	public static String formatTimeFromDBToPage(String time)
	{
		if (time.length() == 6)
		{
			return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
					+ time.substring(4, 6);
		}
		else
		{
			return "";
		}
	}

	public static String SplitDateFromDateTime(String dateTime)
	{
		if (dateTime.length() == 14)
		{
			return dateTime.substring(0, 8);
		}
		else
		{
			return "";
		}
	}

	public static String SplitTimeFromDateTime(String dateTime)
	{
		if (dateTime.length() == 14)
		{
			return dateTime.substring(8);
		}
		else
		{
			return "";
		}
	}

	public static String getCurrentDate()
	{
		Calendar cal = new GregorianCalendar();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		return StringPadder.leftPad(String.valueOf(year), "0", 4)
				+ StringPadder.leftPad(String.valueOf(month), "0", 2)
				+ StringPadder.leftPad(String.valueOf(day), "0", 2);

	}

	public static String getCurrentTime()
	{
		Calendar cal = new GregorianCalendar();

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		return StringPadder.leftPad(String.valueOf(hour), "0", 2)
				+ StringPadder.leftPad(String.valueOf(min), "0", 2)
				+ StringPadder.leftPad(String.valueOf(sec), "0", 2);
	}

	public static String getDateFromDateTime(String dateTime)
	{
		if(dateTime.length() == 14)
		{
			return dateTime.substring(0,8);
		}
		return "";
	}
	public static String getTimeFromDateTime(String dateTime)
	{
		if(dateTime.length() == 14)
		{
			return dateTime.substring(8);
		}
		return "";
	}
}
