package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.util.ArrayList;

//#CM643284
/**
 * File name    :CommunicationData.java
 * <PRE>
 * <B>Revision history:</B>
 *      Ver.1.00  2006/02/09 tsukoi Name(MTB)
 * </PRE>
 * It becomes an object displayed in the communication trace log list display. <BR>
 * Maintain the trace information list. 
 * <p>
 * <table border="1">
 * <CAPTION>Item of communication trace log file list</CAPTION>
 * <TR><TH>Processing date</TH>     	<TH>Length</TH>		<TH>Content</TH></TR>
 * <tr><td>Processing time</td>		<td>16 byte</td>	<td></td></tr>
 * <tr><td>Sending and receiving flag</td>		<td>40 byte</td>	<td></td></tr>
 * <tr><td>ID No.</td>		<td>2 byte</td>		<td>CR + LF</td></tr>
 * <tr><td>Transmission</td>			<td>2 byte</td>		<td>CR + LF</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:56:13 $
 * @author  $Author: suresh $
 */

public class TraceList {
    
    //#CM643285
    // Class variables -----------------------------------------------
    //#CM643286
    /**
     * Communication trace log data
     */
	protected ArrayList traceData;
	
    //#CM643287
    /**
     * Terminal No.
     */
	protected String rftNo;

    //#CM643288
    // Public methods ------------------------------------------------
    //#CM643289
    /**
     * Constructor
     */
    public TraceList()
    {
        super();
        traceData = new ArrayList();
    }

    //#CM643290
    /**
     * Constructor
     * @param size Size of Communication trace log data list
     */
    public TraceList(int size)
    {
        traceData = new ArrayList(size);
    }

    //#CM643291
    /**
     * Acquire the size of Communication trace log data. 
     * @return Communication trace log data size.
     */
    public int getSize()
    {
    	return traceData.size();
    }
    
    //#CM643292
    /**
     * Addition does data to the Communication trace log data list. 
     * @param data Communication trace log data
     */
    public void add(TraceData data)
    {
    	traceData.add(data);
    }
    
    //#CM643293
    /**
     * Addition does data to the Communication trace log data list. 
     * @param index Index of acquired communication trace log
     * @return Log data of communication trace
     */
    public TraceData getTraceData(int index)
    {
    	return (TraceData) traceData.get(index);
    }
    
    //#CM643294
    /**
     * Set Terminal No.
     * @param rftNo Terminal No. to be set.
     */
    public void setRftNo(String rftNo)
    {
    	this.rftNo = rftNo;
    }

    //#CM643295
    /**
     * Acquire Terminal No.
     * @param Terminal No.
     */
    public String getRftNo()
    {
    	return rftNo;
    }
}
