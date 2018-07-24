package jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter;

public class DebugLevel {
	
	private String debugLevel = "";
	
	private DebugLevel(String debugLevel)
	{
		this.debugLevel = debugLevel;
		
	}
	
	public static final DebugLevel DEBUG	= new DebugLevel("DEBUG");
	public static final DebugLevel INFO 	= new DebugLevel("INFO");
	public static final DebugLevel CAUTION 	= new DebugLevel("CAUTION");
	public static final DebugLevel WARNING 	= new DebugLevel("WARNING");
	public static final DebugLevel ERROR 	= new DebugLevel("ERROR");
	
	public String toString()
	{
		return debugLevel;
	}
	
	public boolean isHigherThan(DebugLevel debugLevel)
	{
		int currentPriority = getDebugLevelPriority(this);
		int compareToPriority = getDebugLevelPriority(debugLevel);
		return currentPriority > compareToPriority ? true : false;		
	}
	
	private int getDebugLevelPriority(DebugLevel debugLevel)
	{		
		DebugLevel[] debugLevelPriority = {DebugLevel.DEBUG,  DebugLevel.INFO, 
										DebugLevel.CAUTION, DebugLevel.WARNING, 
										DebugLevel.ERROR};
		
		int priority = 0;
		for(; priority < debugLevelPriority.length; ++priority)
		{
			if(debugLevelPriority[priority] == debugLevel)
			{
				break;
			}
		}
		return priority;
	}
}
