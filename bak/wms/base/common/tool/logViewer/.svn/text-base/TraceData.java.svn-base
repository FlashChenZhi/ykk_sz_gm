package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643264
/**
 * File name    :CommunicationData.java
 * <PRE>
 * <B>Revision history:</B>
 *      Ver.1.00  2006/02/09 tsukoi Name(MTB)
 * </PRE>
 * It becomes an object displayed in the communication trace log list display. <BR>
 * Maintain trace information. 
 * <p>
 * <table border="1">
 * <CAPTION>Item of communication trace log file data</CAPTION>
 * <TR><TH>Processing date</TH>        <TH>Length</TH>       <TH>Content</TH></TR>
 * <tr><td>Processing time</td>        <td>16 byte</td>    <td></td></tr>
 * <tr><td>Sending and receiving flag</td>      <td>40 byte</td>    <td></td></tr>
 * <tr><td>ID No.</td>        <td>2 byte</td>     <td>CR + LF</td></tr>
 * <tr><td>Transmission</td>            <td>2 byte</td>     <td>CR + LF</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:55:57 $
 * @author  $Author: suresh $
 */

public class TraceData {
    
    //#CM643265
    // Class variables -----------------------------------------------
    //#CM643266
    /**
     * Processing date
     */
    private String processDate;

    //#CM643267
    /**
     * Processing time
     */
    private String processTime;

    //#CM643268
    /**
     * Transmission/reception
     */
    private String sendRecvDivision;

    //#CM643269
    /**
     * ID No
     */
    private String idNo;

    //#CM643270
    /**
     * Transmission
     */
    private byte[] message;

    //#CM643271
    // Public methods ------------------------------------------------
    
    //#CM643272
    /**
     * Constructor
     */
    public TraceData()
    {
        super();
    }

    //#CM643273
    /**
     * Set Processing date. 
     * @param value Set Processing date
     */
    public void setProcessDate(String value)
    {
        processDate = value ;
    }

    //#CM643274
    /**
     * Acquire Processing date. 
     * @return Processing date
     */
    public String getProcessDate()
    {
        return processDate ;
    }

    //#CM643275
    /**
     * Set Processing time. 
     * @param value Set Processing time
     */
    public void setProcessTime(String value)
    {
        processTime = value ;
    }

    //#CM643276
    /**
     * Acquire Processing time. 
     * @return Processing time
     */
    public String getProcessTime()
    {
        return processTime ;
    }

    //#CM643277
    /**
     * Set Transmission/reception. 
     * @param value Set Transmission/reception
     */
    public void setSendRecvDivision(String value)
    {
        sendRecvDivision = value ;
    }

    //#CM643278
    /**
     * Acquire Transmission/reception. 
     * @return Transmission/reception
     */
    public String getSendRecvDivision()
    {
        return sendRecvDivision ;
    }
    
    //#CM643279
    /**
     * Set IDNo.
     * @param value Set IDNo
     */
    public void setIdNo(String value)
    {
        idNo = value;
    }
    
    //#CM643280
    /**
     * Acquire IDNO.
     * @return idNo
     */
    public String getIdNo()
    {
        return idNo;
    }

    //#CM643281
    /**
     * Set Transmission Content.
     * @param value Set Transmission Content
     */
    public void setCommunicateMessage(byte[] value)
    {
        message = value ;
    }
    
    //#CM643282
    /**
     * Acquire Transmission. 
     * @return Transmission
     */
    public byte[] getCommunicateMessage()
    {
        return message;
    }
    
    //#CM643283
    /**
     * Acquire Transmission in the Character string type. 
     * @return Transmission(Character string)
     */
    public String getStringMessage()
    {
        return new String(message);
    }
}
