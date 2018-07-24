package com.worgsoft.util.common;

public class Application
{
	private final static String slash = System.getProperties().getProperty(
			"os.name").indexOf("Windows") != -1 ? "\\" : "/";

	public static String getConfigurationPath()
	{
		return getStartPath() + "configuration" + slash;
	}

	public static String getStartPath()
	{
		return System.getProperty("user.dir") + slash;
	}
}
