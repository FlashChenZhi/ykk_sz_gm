//#CM642923
//$Id: ColumnInfo.java,v 1.2 2006/11/07 05:48:32 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM642924
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642925
/**
 * <pre>
 * Transmission item information class<br>
 * Acquire the item name, the comment, and TransmissionContent. <br>
 * Maintain Transmission item information. <br>
 * </pre>
 * @author hota
 * @version 
 */
public class ColumnInfo 
{
	
	//#CM642926
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:48:32 $";
	}

	//#CM642927
	/**
	 * Item name
	 */
	protected String name;

	//#CM642928
	/**
	 * Comment (Tooltip)
	 */
	protected String comment;
	
	//#CM642929
	/**
	 * Content(Transmission flag)
	 */
	protected String value;
    
    //#CM642930
    /**
     * [
     */
    private final String STX_CHAR= "[";
    
    //#CM642931
    /**
     * ]
     */
    private final String ETX_CHAR = "]";
	
	
	//#CM642932
	/**
	 * Constructor
	 */
	public ColumnInfo()
	{
		super();
	}

	//#CM642933
	/**
	 * Acquire Item name. 
	 * @return  Item name
	 */
	public String getName()
	{
		return name;
	}
	
	//#CM642934
	/**
	 * Maintain Item name. 
	 * @param name Item name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	//#CM642935
	/**
	 * Acquire Comment. 
	 * @return  Comment 
	 */
	public String getComment()
	{
		return comment;
	}

	//#CM642936
	/**
	 * Maintain Comment. 
	 * @param comment Comment 
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	//#CM642937
	/**
	 * Acquire Transmission Content. 
	 * @return TransmissionContent
	 */
	public String getValue()
	{
        if (value.equals(STX_CHAR))
        {
            return LogViewerParam.DisplaySTX;
        }
        else if (value.equals(ETX_CHAR))
        {
            return LogViewerParam.DisplayETX;
        }
        else
        {
            return value;
        }
	}

	//#CM642938
	/**
	 * Maintain Transmission Content. 
	 * @param TransmissionContent
	 */
	public void setValue(String value)
	{
        this.value = value;
	}
}
