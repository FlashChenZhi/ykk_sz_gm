package jp.co.daifuku.wms.YkkGMAX.Utils.Propertyloader;

public class DBProperty extends BaseProperty
{
	private DBProperty(String propertyName) 
	{
		super("DB_SETTING", propertyName);
	}

	public static final DBProperty USER = new DBProperty("USER");
	public static final DBProperty PASSWORD = new DBProperty("PASSWORD");
	public static final DBProperty CONNECT_STRING = new DBProperty("CONNECT_STRING");

}
