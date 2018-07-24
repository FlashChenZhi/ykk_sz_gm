//#CM643095
//$Id: LogViewerParam.java,v 1.2 2006/11/07 05:51:54 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643096
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileInputStream;
import java.util.Properties;
import java.util.StringTokenizer;

//#CM643097
/** Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * Environmental configuration file class<BR>
 * The class to acquire information from an environmental configuration file of the java property file. <BR>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:51:54 $
 * @author  $Author: suresh $
 */
public class LogViewerParam {

	//#CM643098
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:51:54 $";
	}

	//#CM643099
	/**
	 * Path of LogViewer
	 */
	public static final String LogViewerPath = "C:\\daifuku\\wms\\bin\\LogViewer";

	//#CM643100
	/**
	 * Configuration Path of LogViewer
	 */
	public static final String ConfPath = LogViewerPath + "\\" + "conf";

	//#CM643101
	/**
	 * Length on side of Window
	 */
	public static final int DefaultWindowWidth = 1024;

	//#CM643102
	/**
	 * Height of the window
	 */
	public static final int DefaultWindowHeight = 768;

	//#CM643103
	/**
	 * Message Length
	 */
	public static final int DefaultTraceMessageWidth = 3500;
	
	//#CM643104
	/**
	 * Path of Default resource
	 */
	public static String resourcePath;

	//#CM643105
	/**
	 * The maximum number of display lines of Communication trace log list inquiry screen
	 */
	public static int MaxLineCnt;

	//#CM643106
	/**
	 * Communication Trace log File name(For RFT)
	 */
	public static String TraceLogFileName;
	
	//#CM643107
	/**
	 * Transmission Trace log File name(For AS21)
	 */
	public static String SendTraceLogFileName;

	//#CM643108
	/**
	 * Reception Trace log File name(For AS21)
	 */
	public static String RecvTraceLogFileName;

	//#CM643109
	/**
	 * File name of ID item configuration file
	 */
	public static String IDFileName;
	
	//#CM643110
	/**
	 * Screen background color
	 */
	public static int[] BackColor;
	
	//#CM643111
	/**
	 * Size of window(width)
	 */
	public static int WindowWidth;
	
	//#CM643112
	/**
	 * Window Size(Height)
	 */
	public static int WindowHeight;
    
    //#CM643113
    /**
     * Number of ID digits
     */
    public static int IdFigure;

    //#CM643114
    /**
     * Detailed screen STX display Character string
     */
    public static String DisplaySTX;

    //#CM643115
    /**
     * Detailed screen ETX conversion Character string
     */
    public static String DisplayETX;

    //#CM643116
    /**
     * Path name where trace log of RFT is put
     */
    public static String RftLogPath;
    
    //#CM643117
    /**
     * Width of Detailed display screen Content column display 
     */
    public static int TraceMessageWidth;

    //#CM643118
    /**
     * Property information
     */
    protected static Properties prop = new Properties();

	//#CM643119
	/**
	 * Read the configuration file, and initialize each parameter. 
	 * @throws Exception It is notified when failing in ResourceReading of file. 
	 */
	public static void initialize() throws Exception
	{
		resourcePath = ConfPath + "\\" + OperationMode.getPropertyFileName();
	    try
		{
			prop.load(new FileInputStream(resourcePath));

		    MaxLineCnt = getIntParam("MaxLineCnt");
		    if (OperationMode.mode == OperationMode.RFT)
		    {
				TraceLogFileName = getParam("TraceLogFileName");
		    }
		    else
		    {
				SendTraceLogFileName = getParam("SendTraceLogFileName");
				RecvTraceLogFileName = getParam("RecvTraceLogFileName");
		    }
			IDFileName = getParam("IDFileName");
			BackColor = getRgbParam("BackColor");
			WindowWidth = getIntParam("WindowWidth");
			WindowHeight = getIntParam("WindowHeight");
            IdFigure = getIntParam("IdFigure");
            DisplaySTX = getParam("DisplaySTX");
            DisplayETX = getParam("DisplayETX");
            RftLogPath = getParam("RftLogPath");
            TraceMessageWidth = getIntParam("TraceMessageWidth");
		}
		catch (Exception e)
		{
		    MaxLineCnt = TraceLogFile.MAX_DISPLAY;
		    if (OperationMode.mode == OperationMode.RFT)
		    {
				TraceLogFileName = TraceLogFile.TraceLogFileName;
				IdFigure = TraceLogFile.DefaultIdFigure;
		    }
		    else
		    {
				SendTraceLogFileName = As21TraceLogFile.SendTraceLogFileName;
				RecvTraceLogFileName = As21TraceLogFile.RecvTraceLogFileName;
				IdFigure = As21TraceLogFile.DefaultIdFigure;
		    }
			IDFileName = IdLayout.IdLayoutFileName;
			BackColor = getRgbParam("BackColor");
			WindowWidth = DefaultWindowWidth;
			WindowHeight = DefaultWindowHeight;
            DisplaySTX = "[";
            DisplayETX = "]";
			RftLogPath = ".";
            TraceMessageWidth = DefaultTraceMessageWidth;
			throw new Exception("6006015");
		}
	}

	//#CM643120
	/**
	 * Acquire the content of the parameter from the key in the Character string expression. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of character string of parameter
	 */
	private static String getParam(String key)
	{
	    return prop.getProperty(key);
	}

	//#CM643121
	/**
	 * Acquire the content of the parameter from the key in the numeric representation. 
	 * @param key  Key to acquire parameter
	 * @return   Numeric representation of parameter
	 */
	private static int getIntParam(String key)
	{
		return Integer.parseInt(getParam(key)) ;
	}

	//#CM643122
	/**
	 * Acquire the content of the parameter from the key in the numeric representation. 
	 * @param key  Key to acquire parameter
	 * @return   Numeric representation of parameter
	 */
	private static int[] getRgbParam(String key)
	{
        int[] intRGB = new int[3];

        try
        {
            String strColor = getParam(key);
            //#CM643123
            // Generation of StringTokenizer Object
            StringTokenizer tokColor = new StringTokenizer(strColor, ",");

            int i = 0;
            while (tokColor.hasMoreTokens())
            {
                intRGB[i] = Integer.parseInt(tokColor.nextToken());
                if (intRGB[i] < 0 || intRGB[i] > 255)
                {
                    throw new Exception();
                }
                i ++;
            }
            
            if (i != 3)
            {
            	throw new Exception();
            }
        }
        catch (Exception e)
        {
            //#CM643124
            // The basic background color is set for the failure in the int conversion or 0<255. 
            intRGB[0] = 218;
            intRGB[1] = 217;
            intRGB[2] = 240;
        }

        return intRGB;
	}
}
