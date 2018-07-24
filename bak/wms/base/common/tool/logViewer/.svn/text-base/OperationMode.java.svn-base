package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643258
/**
 * The Operation mode class. 
 */
public class OperationMode
{
	public static final int RFT = 1;
	public static final int AS21 = 2;
	public static final String ARG_RFT = "-rft";
	public static final String ARG_AS21 = "-as21";
	public static final String RFT_PROPERTIES_FILE = "RftLogViewer.properties";
	public static final String AS21_PROPERTIES_FILE = "As21LogViewer.properties";
	
	protected static int mode = RFT;
	
    //#CM643259
    /**
     * Set Operation mode. 
     * 
     * @param args Operation mode
     */
	public static void setMode(String arg)
	{
		if (arg.equals(ARG_RFT))
		{
			mode = RFT;
		}
		else if (arg.equals(ARG_AS21))
		{
			mode = AS21;
		}
	}

    //#CM643260
    /**
     * Acquire properties File name. 
     * 
     * @return File name
     */
	public static String getPropertyFileName()
	{
		if (mode == RFT)
		{
			return RFT_PROPERTIES_FILE;
		}
		else if (mode == AS21)
		{
			return AS21_PROPERTIES_FILE;
		}
		
		return null;
	}

    //#CM643261
    /**
     * Acquire Rft Title machine No panel. 
     * 
     * @return Rft Title machine No panel
     */
	public static LvRftNo getRftNoInstance()
	{
		return getRftNoInstance(true, "");
	}
	
    //#CM643262
    /**
     * Acquire Rft Title machine No Component. 
     * 
     * @param enabled Possible/Not possible to Input
     * @param text Rft Title machine No
     * @return Rft Title machine No Component
     */
	public static LvRftNo getRftNoInstance(boolean enabled, String text)
	{
		if (mode == AS21)
		{
			return new LvHostname(enabled, text);
		}
		else
		{
			return new LvRftNo(enabled, text);
		}
	}

    //#CM643263
    /**
     * Acquire Instance of communication trace log. 
     * 
     * @return Instance of communication trace log
     */
	public static TraceLogFile getTraceLogFileInstance()
	{
		if (mode == AS21)
		{
			return new As21TraceLogFile();
		}
		else
		{
			return new TraceLogFile();
		}
	}
}
