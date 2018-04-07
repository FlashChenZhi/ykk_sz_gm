package com.worgsoft.util.common;

import com.worgsoft.util.common.BaseProperty;

public class DBProperty extends BaseProperty
{
	private DBProperty(String propertyName) 
	{
		super("db_setting", propertyName);
	}

	public static final DBProperty USER = new DBProperty("user");
	public static final DBProperty PASSWORD = new DBProperty("password");
	public static final DBProperty CONNECT_STRING = new DBProperty("connect.string");

}
