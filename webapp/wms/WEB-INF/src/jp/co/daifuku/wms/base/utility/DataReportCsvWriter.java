package jp.co.daifuku.wms.base.utility;

//#CM686839
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM686840
/**
 * This class creates the data report csv file
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:41 $
 */
public class DataReportCsvWriter 
{
	
	//****************************************************************************
	//#CM686841
	//private field

	//****************************************************************************
	
    //#CM686842
    /** Data report type -  Receiving **/

    public static final int REPORTTYPE_INSTOCKRECEIVE = 0;
    
    //#CM686843
    /** Data report type -  Storage **/

    public static final int REPORTTYPE_STRAGESUPPOR = 1;

    //#CM686844
    /** Data report type -  Picking **/

    public static final int REPORTTYPE_RETRIEVALSUPPORT = 2;

    //#CM686845
    /** Data report type -  Shipping **/

    public static final int REPORTTYPE_SHIPPINGINSPECTION = 3;

    //#CM686846
    /** Data report type -  Sorting **/

    public static final int REPORTTYPE_PICKINGSUPPORT = 4;

    //#CM686847
    /** Data report type -  Inventory relocation **/

    public static final int REPORTTYPE_MOVINGSUPPOR = 5;

    //#CM686848
    /** Data report type -  Inventory check **/

    public static final int REPORTTYPE_STOCKTAKINGSUPPOR = 6;

	//#CM686849
	/** Data report type -  Unplanned storage **/

	public static final int REPORTTYPE_NOPLANSTRAGESUPPOR = 7;

	//#CM686850
	/** Data report type -  Unplanned picking **/

	public static final int REPORTTYPE_NOPLANRETRIEVALSUPPORT = 8;
	
	//#CM686851
	/** Data report type -  Unplanned picking **/

	public static final int REPORTTYPE_STOCK = 9;	

    //#CM686852
    /** Data report type - Environment key **/

    private static final String[] REPORTTYPE_KEY = {
                        "INSTOCK_RECEIVE" ,
                        "STRAGE_SUPPORT" ,
                        "RETRIEVAL_SUPPORT" ,
                        "SHIPPING_INSPECTION" ,
                        "PICKING_SUPPORT" ,
                        "MOVING_SUPPORT" ,
                        "STOCKTAKING_SUPPORT" , 
		                "NOPLANSTRAGE_SUPPORT" ,
		                "NOPLANRETRIEVAL_SUPPORT",
		                "STOCK_SUPPORT"
                         };                        
    
	//#CM686853
	/** Data report type - Folder section name **/

	private static final String DATAREPORT_FOLDER = "REPORTDATA_FOLDER";
	
	//#CM686854
	/** Data report type - File name section name**/

	private static final String DATAREPORT_FILENAME = "REPORTDATA_FILENAME";

    //#CM686855
    /** Data report type - enabled flag section name for item field setting **/

	private static final String[] DATAREPORT_ENABLE = {
                        "DATAREPORT_INSTOCKRECEIVE_ENABLE" ,
                        "DATAREPORT_STRAGESUPPORT_ENABLE" ,
                        "DATAREPORT_RETRIEVALSUPPORT_ENABLE" ,
                        "DATAREPORT_SHIPPINGINSPECTION_ENABLE" ,
                        "DATAREPORT_PICKINGSUPPORT_ENABLE" ,
                        "DATAREPORT_MOVINGSUPPORT_ENABLE" ,
                        "DATAREPORT_STOCKTAKINGSUPPORT_ENABLE", 
	                    "DATAREPORT_NOPLANSTRAGESUPPORT_ENABLE",
	                    "DATAREPORT_NOPLANRETRIEVALSUPPORT_ENABLE", 
						"DATAREPORT_STOCK_ENABLE"} ;

	//#CM686856
	/** Data report type -width size section name for field item setting **/    

	private static final String[] DATAREPORT_FIGURE = {
                        "DATAREPORT_INSTOCKRECEIVE_FIGURE" , 
                        "DATAREPORT_STRAGESUPPORT_FIGURE" , 
                        "DATAREPORT_RETRIEVALSUPPORT_FIGURE" , 
                        "DATAREPORT_SHIPPINGINSPECTION_FIGURE" , 
                        "DATAREPORT_PICKINGSUPPORT_FIGURE" , 
                        "DATAREPORT_MOVINGSUPPORT_FIGURE" , 
                        "DATAREPORT_STOCKTAKINGSUPPORT_FIGURE" ,
	                    "DATAREPORT_NOPLANSTRAGESUPPORT_FIGURE",
	                    "DATAREPORT_NOPLANRETRIEVALSUPPORT_FIGURE", 
						"DATAREPORT_STOCK_FIGURE"}; 

    //#CM686857
    /** Data report type - position section name for field item setting **/

    private static final String[] DATAREPORT_POSITION = {
                        "DATAREPORT_INSTOCKRECEIVE_POSITION" ,
                        "DATAREPORT_STRAGESUPPORT_POSITION" ,
                        "DATAREPORT_RETRIEVALSUPPORT_POSITION" ,
                        "DATAREPORT_SHIPPINGINSPECTION_POSITION" ,
                        "DATAREPORT_PICKINGSUPPORT_POSITION" ,
                        "DATAREPORT_MOVINGSUPPORT_POSITION" ,
                        "DATAREPORT_STOCKTAKINGSUPPORT_POSITION" ,
	                    "DATAREPORT_NOPLANSTRAGESUPPORT_POSITION" ,
	                    "DATAREPORT_NOPLANRETRIEVALSUPPORT_POSITION", 
						"DATAREPORT_STOCK_POSITION" };
    
    //#CM686858
    /** enabled flag of each item **/

    protected Hashtable Enable = new Hashtable();

    //#CM686859
    /** enabled size of each item **/

	protected Hashtable Figure = new Hashtable();

    //#CM686860
    /** enabled position of each item **/

	protected Hashtable Position = new Hashtable();
    
    //#CM686861
    /** csv writer class **/

    private CsvFileWriter csvfile;
    
    //#CM686862
    /** one record of csv buffer **/

	protected Hashtable LineData = new Hashtable();
    
    //#CM686863
    /** maximum csv count **/

    private int CsvMax = 0;
    
	//****************************************************************************
	//#CM686864
	//constructor
	//****************************************************************************

	
	//****************************************************************************
	//#CM686865
	/**
	* Open csv file and prepare for writing
	* <P>
    * @param ReportDataType     data type to process
    * @throws IOException if the file input/output fails
    * @throws ReadWriteException if abnormal error occurs in database access
    */
	//****************************************************************************
	public DataReportCsvWriter(int ReportDataType ) throws  java.io.IOException , ReadWriteException  {
        
        //#CM686866
        // load environment info details
        IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
        String ReportPath = IO.get( DATAREPORT_FOLDER , REPORTTYPE_KEY[ ReportDataType ] );
        String ReportFile = IO.get( DATAREPORT_FILENAME , REPORTTYPE_KEY[ ReportDataType ] );
        
        //#CM686867
        // fetch width of each item

        String[] Keys = IO.getKeys( DATAREPORT_ENABLE[ ReportDataType ] );
        
        for ( int Cnt = 0 ; Cnt < Keys.length ; Cnt++ ){
            Enable.put( Keys[ Cnt ] , IO.get( DATAREPORT_ENABLE[ ReportDataType ] , Keys[ Cnt ] ) );
            Figure.put( Keys[ Cnt ] , IO.get( DATAREPORT_FIGURE[ ReportDataType ] , Keys[ Cnt ] ) );
            Position.put( Keys[ Cnt ] , IO.get( DATAREPORT_POSITION[ ReportDataType ] , Keys[ Cnt ] ) );
            if ( Integer.parseInt( IO.get( DATAREPORT_POSITION[ ReportDataType ] , Keys[ Cnt ] ) ) > CsvMax ){
                CsvMax = Integer.parseInt( IO.get( DATAREPORT_POSITION[ ReportDataType ] , Keys[ Cnt ] ) );
            }
        }

        //#CM686868
        // open csv file
        csvfile = new CsvFileWriter();
        csvfile.open( ReportPath + ReportFile ); 
        System.out.println(" CSVFILE = " + ReportPath + ReportFile  );
        
    }	

	//****************************************************************************
	//#CM686869
	//public method
	//****************************************************************************

	//****************************************************************************
	//#CM686870
	/**
	* set items to csv
	* <P>
    * @param ColKey         item key
    * @param val            value to set
    * @throws CSVItemNotFoundException if the item specified in the csv or environment info does not exist
    */
	//****************************************************************************
    public void setValue( String ColKey , Object val) throws CSVItemNotFoundException {
        
        //#CM686871
        // error if key is not found in environment info
        if (Enable.get(ColKey) == null)  throw new CSVItemNotFoundException(ColKey);

        //#CM686872
        // if the item is disabled, do nothing

        if ( Integer.parseInt((String)Enable.get(ColKey)) == 0) return;
        
        //#CM686873
        // add to line data
        LineData.put(new Integer((String)Position.get(ColKey)),val);
    }

	//****************************************************************************
	//#CM686874
	/**
	* write one line
	* <P>
    * @throws IOException if the file input/output fails
    */
	//****************************************************************************
    public void writeLine()  throws java.io.IOException  {


        //#CM686875
        // prepare array
        Object[] csv = new Object[CsvMax];
        
        //#CM686876
        // set to array
        Enumeration h = LineData.keys();
        while( h.hasMoreElements() ){
            Object next = h.nextElement();
            csv[ ((Integer)next).intValue() - 1 ] = LineData.get(next); 
        }
        
        //#CM686877
        // replace any null with space in array
        for ( int cnt = 0 ; cnt < CsvMax ; cnt++ ){
            if ( csv[ cnt ] == null) csv[ cnt ] = "";            
        }

        //#CM686878
        // write
        csvfile.writeCsv( csv );
        
        //#CM686879
        // initialize buffer
        LineData = new Hashtable();
        
    }

	//****************************************************************************
	//#CM686880
	/**
	* complete writing and close buffer
	* <P>
    * @throws IOException if the file input/output fails
    */ 
     //****************************************************************************
    public void close() throws java.io.IOException 
    {
        csvfile.flush();
        csvfile.close();        
	}
    
	//****************************************************************************
	/**
	* ファイルを削除します。
	* <P>
	* @throws IOException ファイルの入出力に失敗した場合に通知されます。
	*/ 
	 //****************************************************************************
	public void delete() throws java.io.IOException 
	{
		csvfile.delete();
	}
}
//#CM686881
//end of class

