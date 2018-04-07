package jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DebugPrinter
{
	private static DebugLevel outputLevel = DebugLevel.DEBUG;

	private static String generateStackTraceInfo(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String stackTraceTitle = "----- StackTrace -----";//PropertyLoader.load(
		// CommonProperty
		// .STACKTRACE_TITLE);
		e.printStackTrace(pw);
		return "\n	" + stackTraceTitle + "\n	" + sw.toString();
	}

	public static void print(DebugLevel level, String errorMessage)
	{
		if (level.isHigherThan(outputLevel) || level == outputLevel)
		{
			String messageHeader = "[" + level + "]: ";
			// int currentHeaderLength = messageHeader.length();
			// int totalHeaderLength = 10;
			// for(int i = 0; i < totalHeaderLength - currentHeaderLength;
			// ++i)
			// {
			// messageHeader += " ";
			// }
			// String stackTraceInfo = e == null ? "" :
			// generateStackTraceInfo(e);
			String outputMessage = messageHeader + errorMessage;// +
			// stackTraceInfo;
			System.out.println(outputMessage);
		}

		String osName = System.getProperties().getProperty("os.name");
		if (osName.indexOf("Windows") != -1)
		{
			PropertyConfigurator
					.configure("D:\\daifuku\\wms\\tomcat-5.0.16\\webapps\\wms\\WEB-INF\\classes\\log4j_windows.properties");
		} else
		{
			PropertyConfigurator
					.configure("/con/wms/tomcat-5.0.16/webapps/wms/WEB-INF/classes/log4j_unix.properties");
		}

		if (level == DebugLevel.ERROR)
		{
			Logger log = Logger.getLogger("error");
			log.info(errorMessage);
		}

		if (level == DebugLevel.INFO)
		{
			Logger log = Logger.getLogger("info");
			log.info(errorMessage);
		}

		if (level == DebugLevel.DEBUG)
		{
			Logger log = Logger.getLogger("debug");
			log.info(errorMessage);
		}
	}

	// public static void print(DebugLevel level, AUOCommonException e)
	// {
	// String message = e == null ? "" : e.getMessage();
	// String stackTraceInfo = e == null ? "" : generateStackTraceInfo(e);
	// print(level, message + stackTraceInfo);
	// }

	public static void print(DebugLevel level, String errorMessage, Exception e)
	{
		String stackTraceInfo = e == null ? "" : generateStackTraceInfo(e);
		print(level, errorMessage + stackTraceInfo);
	}

	public static void setOutputLevel(DebugLevel debugLevel)
	{
		outputLevel = debugLevel;
		System.out.println("set output level to " + outputLevel);
	}

	private DebugPrinter()
	{
	}
}
