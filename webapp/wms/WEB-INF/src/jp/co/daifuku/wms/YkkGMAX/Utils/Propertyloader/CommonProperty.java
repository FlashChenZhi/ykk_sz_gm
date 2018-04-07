package jp.co.daifuku.wms.YkkGMAX.Utils.Propertyloader;

public class CommonProperty extends BaseProperty
{
	private CommonProperty(String propertyName) 
	{
		super("COMMON_SETTING", propertyName);
	}
	
	public static final CommonProperty STACKTRACE_TITLE = new CommonProperty("STACKTRACE_TITLE");

}
