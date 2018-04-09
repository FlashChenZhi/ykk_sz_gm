package jp.co.daifuku.wms.base.utility;

//#CM686620
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.WmsParam;
//#CM686621
/**
 * This class reads the data from the csv file for data load
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:40 $
 */
public class DataLoadCsvReader
{

	//****************************************************************************
	//#CM686622
	//private field
	//****************************************************************************

	//#CM686623
	/** data loading type for receiving **/

	public static final int LOADTYPE_INSTOCKRECEIVE = 0;

	//#CM686624
	/** data loading type for storage **/

	public static final int LOADTYPE_STRAGESUPPORT = 1;

	//#CM686625
	/** data loading type picking **/

	public static final int LOADTYPE_RETRIEVALSUPPORT = 2;

	//#CM686626
	/** data loading type shipping **/

	public static final int LOADTYPE_SHIPPINGINSPECTION = 3;

	//#CM686627
	/** data loading type sorting **/

	public static final int LOADTYPE_PICKINGSUPPORT = 4;
	
	//#CM686628
	/** data loading type consignor master **/

	public static final int LOADTYPE_MASTERCONSIGNOR = 5;
	
	//#CM686629
	/** data loading type supplier master **/

	public static final int LOADTYPE_MASTERSUPPLIER = 6;
	
	//#CM686630
	/** data loading type customer master **/

	public static final int LOADTYPE_MASTERCUSTOMER = 7;
	
	//#CM686631
	/** data loading type item master **/

	public static final int LOADTYPE_MASTERITEM = 8;
	

	//#CM686632
	/** enabled flag disable **/

	public static final int ENABLE_DISABLE = 0;

	//#CM686633
	/** enabled flag enable **/

	public static final int ENABLE_ENABLE = 1;

	//#CM686634
	/** enabled flag  require enable **/

	public static final int ENABLE_MANDATORY = 2;

	//#CM686635
	/** environment setting key for data loading **/

	private static final String[] LOADTYPE_KEY =
		{
			"INSTOCK_RECEIVE",
			"STRAGE_SUPPORT",
			"RETRIEVAL_SUPPORT",
			"SHIPPING_INSPECTION",
			"PICKING_SUPPORT" ,
			"MASTER_CONSIGNOR",
			"MASTER_SUPPLIER",
			"MASTER_CUSTOMER",
			"MASTER_ITEM"
		};

	//#CM686636
	/** data loading folder section name **/

	private static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

	//#CM686637
	/** data loading file name section name **/

	private static final String DATALOAD_FILENAME = "DATALOAD_FILENAME";

	//#CM686638
	/** enabled flag section name for data load item setting **/

	private static final String[] DATALOAD_INSTOCKRECEIVE_ENABLE =
		{
			"DATALOAD_INSTOCKRECEIVE_ENABLE",
			"DATALOAD_STRAGESUPPORT_ENABLE",
			"DATALOAD_RETRIEVALSUPPORT_ENABLE",
			"DATALOAD_SHIPPINGINSPECTION_ENABLE",
			"DATALOAD_PICKINGSUPPORT_ENABLE" ,
			"DATALOAD_MASTERCONSIGNOR_ENABLE",
			"DATALOAD_MASTERSUPPLIER_ENABLE",
			"DATALOAD_MASTERCUSTOMER_ENABLE",
			"DATALOAD_MASTERITEM_ENABLE"
		};

	//#CM686639
	/** width section name for data load item setting **/

	private static final String[] DATALOAD_INSTOCKRECEIVE_FIGURE =
		{
			"DATALOAD_INSTOCKRECEIVE_FIGURE",
			"DATALOAD_STRAGESUPPORT_FIGURE",
			"DATALOAD_RETRIEVALSUPPORT_FIGURE",
			"DATALOAD_SHIPPINGINSPECTION_FIGURE",
			"DATALOAD_PICKINGSUPPORT_FIGURE" ,
			"DATALOAD_MASTERCONSIGNOR_FIGURE",
			"DATALOAD_MASTERSUPPLIER_FIGURE",
			"DATALOAD_MASTERCUSTOMER_FIGURE",
			"DATALOAD_MASTERITEM_FIGURE"
		};

	//#CM686640
	/** position section name for data load item setting **/

	private static final String[] DATALOAD_INSTOCKRECEIVE_POSITION =
		{
			"DATALOAD_INSTOCKRECEIVE_POSITION",
			"DATALOAD_STRAGESUPPORT_POSITION",
			"DATALOAD_RETRIEVALSUPPORT_POSITION",
			"DATALOAD_SHIPPINGINSPECTION_POSITION",
			"DATALOAD_PICKINGSUPPORT_POSITION" ,
			"DATALOAD_MASTERCONSIGNOR_POSITION",
			"DATALOAD_MASTERSUPPLIER_POSITION",
			"DATALOAD_MASTERCUSTOMER_POSITION",
			"DATALOAD_MASTERITEM_POSITION"
		};

	private static final String[] DATA_LOADER =
		{
			WmsParam.HOSTDATA_LOADER_INSTOCK,
			WmsParam.HOSTDATA_LOADER_STORAGE,
			WmsParam.HOSTDATA_LOADER_RETRIEVAL,
			WmsParam.HOSTDATA_LOADER_PICKING,
			WmsParam.HOSTDATA_LOADER_SHIPPING,
			WmsParam.HOSTDATA_LOADER_CONSIGNOER,
			WmsParam.HOSTDATA_LOADER_SUPPLIER,
			WmsParam.HOSTDATA_LOADER_CUSTOMER,
			WmsParam.HOSTDATA_LOADER_ITEM
		};

	//#CM686641
	/** enabled flag of each item **/

	private Hashtable Enable = new Hashtable();

	//#CM686642
	/** enabled size of each item **/

	private Hashtable Figure = new Hashtable();

	//#CM686643
	/** enabled position of each item **/

	private Hashtable Position = new Hashtable();

	//#CM686644
	/** class to read csv file **/

	private CsvFileReader CFR = new CsvFileReader();

	//#CM686645
	/** one record of csv buffer **/

	private String[] LineData;

	//#CM686646
	/** EOF flag **/

	private boolean EOF;

	//#CM686647
	/** plan date **/

	private String PlanDate;

	//#CM686648
	/** plan date unique key **/

	private String PlanDateKey;

	//#CM686649
	/** load plan date **/

	private String LoadedPlanDate;
	
	//#CM686650
	/** consignor code **/

	private String Consignor;

	//#CM686651
	/** to save the file being read **/

	private String LoadedFileName = "";

	//#CM686652
	/** to save error message **/

	private String ErrorCode = "";

	//#CM686653
	/** required field count **/

	private int NeedItems = 0;

	//#CM686654
	/**
	 * delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//****************************************************************************
	//#CM686655
	//constructor
	//****************************************************************************
	public DataLoadCsvReader()
	{
		
	}

	//****************************************************************************
	//#CM686656
	/**
	* Fetch the environment info from the WMSParam properties file
	* <P>
	* @param LoadDataType       data type to process
	* @param PDate              plan date to process
	* @param PDateKey           plan date of environment info key
	* @throws ScheduleException if unexpected error occurs in schedule process
	* @throws FileNotFoundException if the specified file can't be found
	* @throws IOException if the file input/output fails
	* @throws ClassNotFoundException if the specified class definition is not found
	* @throws IllegalAccessException if instance generation failed
	* @throws InstantiationException while the application tries to generate instance using newInstance method but could not do so since <BR>
                                         instance exist as a class instance or as a abstract class, throw exception <BR>
	*/
	//****************************************************************************
	public DataLoadCsvReader(int LoadDataType, String PDate, String PDateKey)
		throws
			ScheduleException,
			FileNotFoundException,
			java.io.IOException,
			ClassNotFoundException,
			IllegalAccessException,
			InstantiationException
	{

		final String ClassPath = "jp.co.daifuku.wms.base.utility";
		String loaderName = "";

		try
		{
			//#CM686657
			// store plan date
			PlanDate = PDate;
			PlanDateKey = PDateKey;

			//#CM686658
			// fetch environment information
			WmsParam WP = new WmsParam();
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get(DATALOAD_FOLDER, LOADTYPE_KEY[LoadDataType]);
			String DataLoadFile = IO.get(DATALOAD_FILENAME, LOADTYPE_KEY[LoadDataType]);

			//#CM686659
			// fetch width of each item
			String[] Keys = IO.getKeys(DATALOAD_INSTOCKRECEIVE_ENABLE[LoadDataType]);
			for (int Cnt = 0; Cnt < Keys.length; Cnt++)
			{
				Enable.put(
					Keys[Cnt],
					IO.get(DATALOAD_INSTOCKRECEIVE_ENABLE[LoadDataType], Keys[Cnt]));
				Figure.put(
					Keys[Cnt],
					IO.get(DATALOAD_INSTOCKRECEIVE_FIGURE[LoadDataType], Keys[Cnt]));
				Position.put(
					Keys[Cnt],
					IO.get(DATALOAD_INSTOCKRECEIVE_POSITION[LoadDataType], Keys[Cnt]));
				if (NeedItems
					< Integer.parseInt(
						IO.get(DATALOAD_INSTOCKRECEIVE_POSITION[LoadDataType], Keys[Cnt])))
				{
					NeedItems =
						Integer.parseInt(
							IO.get(DATALOAD_INSTOCKRECEIVE_POSITION[LoadDataType], Keys[Cnt]));
				}
			}

			//#CM686660
			// open load data file
			loaderName = DATA_LOADER[LoadDataType];
			Class cl = Class.forName(ClassPath + "." + loaderName);

			//#CM686661
			// file check
			java.io.File file = new java.io.File(DataLoadPath + DataLoadFile);
			if (!file.exists())
			{
				throw new FileNotFoundException();
			}
			//#CM686662
			// history file making (file name = date (YYYYMMDD) + time (HHmmSS) + load file
			if (!HistoryFileCreate(DataLoadPath, DataLoadFile))
			{
				throw new IOException();
			}

			DataLoader loader = (DataLoader) cl.newInstance();
			CFR.open(DataLoadPath, DataLoadFile);
		}
		catch (ClassNotFoundException e)
		{
			//#CM686663
			// instance generation failed. ClassName={0} exception : {1}
			Object[] msg = new Object[1];
			msg[0] = ClassPath + "." + loaderName;
			RmiMsgLogClient.write(new TraceHandler(6006003, e), "DataLoadCsvReader", msg);
			throw new ClassNotFoundException();
		}
		catch (IllegalAccessException e)
		{
			//#CM686664
			// instance generation failed. ClassName={0} exception : {1}
			Object[] msg = new Object[1];
			msg[0] = ClassPath + "." + loaderName;
			RmiMsgLogClient.write(new TraceHandler(6006003, e), "DataLoadCsvReader", msg);
			throw new IllegalAccessException();
		}
		catch (ReadWriteException e)
		{
			//#CM686665
			// Environment info file can't be found. {0}{0}
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), "DataLoadCsvReader", msg);
			throw new ScheduleException();
		}
		catch (InstantiationException e)
		{
			//#CM686666
			// instance generation failed. ClassName={0} exception : {1}
			Object[] msg = new Object[1];
			msg[0] = ClassPath + "." + loaderName;
			RmiMsgLogClient.write(new TraceHandler(6006003, e), "DataLoadCsvReader", msg);
			throw new InstantiationException();
		}
	}

	//****************************************************************************
	//#CM686667
	//Access method
	//****************************************************************************

	//****************************************************************************
	//#CM686668
	/**
	* check whether a field is enabled or disabled
	* <P>
	* @param ColKey         field item key
	* @return boolean       true if enable. false if disable
	* @throws CsvIllegalDataException if the item width of the specified key overflows
	*/
	//****************************************************************************
	public boolean getEnable(String ColKey) throws CsvIllegalDataException
	{

		if (Enable.get(ColKey) == null)
		{
			//#CM686669
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686670
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ ColKey));
		}

		return (Integer.parseInt((String) Enable.get(ColKey)) != 0);

	}
	//****************************************************************************
	//#CM686671
	/**
	* fetch item value
	* <P>
	* @param ColKey			item key
	* @return String		item value
	* @throws CsvIllegalDataException		if the item width of the specified key overflows
	*/
	//****************************************************************************
	public String getValue(String ColKey) throws CsvIllegalDataException
	{
		if (Figure.get(ColKey) == null)
		{
			//#CM686672
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686673
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ ColKey));
		}
		if (Position.get(ColKey) == null)
		{
			//#CM686674
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686675
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ ColKey));
		}

		//#CM686676
		// if the specified position does not exist in the csv
		if (LineData.length < Integer.parseInt((String) Position.get(ColKey)))
		{
			//#CM686677
			// 6027011 = Data loading item definition file can't be read.
			ErrorCode = "6027011";

			//#CM686678
			// 6026009 = There is no item in the corresponding position. File:{0} Line:{1} Item:{2}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026009"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey));
		}

		//#CM686679
		// fetch item value
		String Value = LineData[Integer.parseInt((String) Position.get(ColKey)) - 1];

		//#CM686680
		// if the enable flag is enable (required), item value becomes mandatory
		if (Integer.parseInt((String) Enable.get(ColKey)) == ENABLE_MANDATORY && Value.equals(""))
		{
			//#CM686681
			// 6003100 = Please input {0}.
			ErrorCode = "6003100";

			//#CM686682
			// 6026010 = Data whose mandatory item is not entered is found. File:{0} Line:{1} Item:{2}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026010"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey));
		}

		//#CM686683
		// if the item value exceeds the set width
		if (Value.getBytes().length > Integer.parseInt((String) Figure.get(ColKey)))
		{
			//#CM686684
			// 6003102 = {0} exceeds the length which can be input.
			ErrorCode = "6003102";
			
			//#CM686685
			// 6026080 = Data in invalid length is found. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026080"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));

		}

		//#CM686686
		// check for restricted characters
		if (isPatternMatching(Value))
		{
			//#CM686687
			// 6003101 = The character which can't be used as {0} is contained.
			ErrorCode = "6003101";
			
			//#CM686688
			//6026081 = Data including forbidden characters is found. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026081"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));
		}
		
		return Value;
	}

	//****************************************************************************
	//#CM686689
	/**
	* fetch item value
	* <P>
	* @param ColKey			item key
	* @return Int			item value
	* @throws CsvIllegalDataException		if the item width of the specified key overflows
	*/
	//****************************************************************************
	public int getIntValue(String ColKey) throws CsvIllegalDataException
	{

		if (Figure.get(ColKey) == null)
		{
			//#CM686690
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686691
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ ColKey));
		}
		if (Position.get(ColKey) == null)
		{
			//#CM686692
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686693
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ ColKey));
		}

		//#CM686694
		// if the specified position does not exist in the csv
		if (LineData.length < Integer.parseInt((String) Position.get(ColKey)))
		{
			//#CM686695
			// 6027011 = Data loading item definition file can't be read.
			ErrorCode = "6027011";

			//#CM686696
			// 6026009 = There is no item in the corresponding position. File:{0} Line:{1} Item:{2}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026009"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey));
		}

		//#CM686697
		// fetch item value
		String Value = LineData[Integer.parseInt((String) Position.get(ColKey)) - 1];

		//#CM686698
		// if the enable flag is enable (required), item value becomes mandatory
		if (Integer.parseInt((String) Enable.get(ColKey)) == ENABLE_MANDATORY && Value.equals(""))
		{
			//#CM686699
			// 6003100 = Please input {0}.
			ErrorCode = "6003100";

			//#CM686700
			// 6026010 = Data whose mandatory item is not entered is found. File:{0} Line:{1} Item:{2}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026010"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey));
		}

		//#CM686701
		// replace with  0 if empty value is set in a number field
		if (Value.equals(""))
		{
			Value = "0";
		}

		//#CM686702
		// if the item value exceeds the set width
		if (Value.length() > Integer.parseInt((String) Figure.get(ColKey)))
		{
			//#CM686703
			// 6003102 = {0} exceeds the length which can be input.
			ErrorCode = "6003102";
			
			//#CM686704
			// 6026080 = Data in invalid length is found. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026080"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));
		}

		try
		{
			int intvalue = Integer.parseInt(Value.trim());

			//#CM686705
			// 0 or less is abnormal
			if (intvalue < 0)
			{
				//#CM686706
				// 6023001 = Enter 0 or more for {0} id value.
				ErrorCode = "6023001";
								
				//#CM686707
				// 6026087 = Data of negative value is entered in the integer item. File:{0} Line:{1} Item:{2} Value:{3}
				throw new CsvIllegalDataException(
					MessageResource.getMessage(
						"6026087"
							+ wDelim
							+ CFR.getFileName()
							+ wDelim
							+ CFR.getLineNo()
							+ wDelim
							+ ColKey
							+ wDelim
							+ Value));
			}
			return intvalue;
		}
		catch (NumberFormatException e)
		{
			//#CM686708
			// 6023064 = Please enter integer number for {0}.
			ErrorCode = "6023064";
			
			//#CM686709
			// 6026083 = Invalid data is found in an integer item. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026083"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));
		}
	}

	//****************************************************************************
	//#CM686710
	/**
	* fetch item value
	* <P>
	* @param ColKey			item key
	* @return String		item value
	* @throws CsvIllegalDataException		if the item width of the specified key overflows
	*/
	//****************************************************************************
	public String getDateValue(String ColKey) throws CsvIllegalDataException
	{
		//#CM686711
		// fetch item value
		String Value = getValue(ColKey);

		//#CM686712
		// pass empty as it is
		if (Value.equals(""))
		{
			return Value;
		}

		DateUtil dutil = new DateUtil();
		//#CM686713
		// date - if the enable flag is enable (required), item value becomes mandatory
		if (!dutil.DateChk(Value))
		{
			//#CM686714
			// 6023065 = Invalid date for {0}.
			ErrorCode = "6023065";
			
			//#CM686715
			// 6026082 = Invalid data is found in a date item. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026082"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));
		}
		
		return Value;
	}
	//****************************************************************************
	//#CM686716
	/**
	* fetch item value
	* <P>
	* @param ColKey			item key
	* @return String		item value
    * @throws CsvIllegalDataException		if the item width of the specified key overflows
	*/
	//****************************************************************************
	public String getAsciiValue(String ColKey) throws CsvIllegalDataException
	{
		//#CM686717
		// fetch item value
		String Value = getValue(ColKey);

		//#CM686718
		// ascii code check
		if (!isAsciiChar(Value))
		{
			//#CM686719
			// 6023067 = Please enter single byte alphanumeric for {0}.
			ErrorCode = "6023067";
			
			//#CM686720
			// 6026084 = Invalid data is found in a single-byte alphanumeric item. File:{0} Line:{1} Item:{2} Value:{3}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026084"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ CFR.getLineNo()
						+ wDelim
						+ ColKey
						+ wDelim
						+ Value));
		}
		
		return Value;
	}

	//****************************************************************************
	//#CM686721
	//public method
	//****************************************************************************

	//****************************************************************************
	//#CM686722
	/**
	* fetch next one line corresponding to plan date
	* <P>
	* @return boolean  flag to decide whether next data exist or not
	* @throws NumberFormatException throw error if it is not a integer value
	* @throws FileNotFoundException if the specified file can't be found
	* @throws IOException if the file input/output fails
	* @throws InvalidDefineException if the specified value is abnormal (empty, illegal characters)
	* @throws CsvIllegalDataException if the item width of the specified key overflows
	*/
	//****************************************************************************
	public boolean MoveNext() throws NumberFormatException, FileNotFoundException, IOException, InvalidDefineException, CsvIllegalDataException
	{
		//#CM686723
		// if complete until the last, false
		if (EOF)
			return false;

		//#CM686724
		// error if plan date item key is not found in the environment info file
		if (Position.get(PlanDateKey) == null)
		{
			//#CM686725
			// 6027011 = Data load item definition file cannot be read properly.
			ErrorCode = "6027011";			
			
			//#CM686726
			// 6026008 = data loading item definition file can't be read. load file :{0} item:{1}
			throw new CsvIllegalDataException(
				MessageResource.getMessage(
					"6026008"
						+ wDelim
						+ CFR.getFileName()
						+ wDelim
						+ PlanDateKey));
		}

		//#CM686727
		// look for next specified plan date data
		while ((LineData = CFR.readCsv()) != null)
		{
			//#CM686728
			// error if the pland date does not exist in csv
			if (LineData.length < Integer.parseInt((String) Position.get(PlanDateKey)))
			{
				//#CM686729
				// 6003100 = Please input {0}.
				ErrorCode = "6003100";

				//#CM686730
				// 6026010 = Data whose mandatory item is not entered is found. File:{0} Line:{1} Item:{2}
				throw new CsvIllegalDataException(
					MessageResource.getMessage(
						"6026010"
							+ wDelim
							+ CFR.getFileName()
							+ wDelim
							+ CFR.getLineNo()
							+ wDelim
							+ PlanDateKey));
			}

			//#CM686731
			// error if the item qty of the csv is lesser than minimum qty
			if (LineData.length < NeedItems)
			{
				//#CM686732
				// 6023326 = Error data was found in the Load Plan Data. The process was aborted. See log.
				ErrorCode = "6023326";
				
				//#CM686733
				// 6026006=Error data was found in data format. File: {0} Line: {1}
				throw new CsvIllegalDataException(
					MessageResource.getMessage(
						"6026006" + wDelim + CFR.getFileName() + wDelim + CFR.getLineNo()));
			}

			String pd = LineData[Integer.parseInt((String) Position.get(PlanDateKey)) - 1];

			//#CM686734
			// error if same date occurs twice in a file
			if (CFR.getFileName().equals(LoadedFileName))
			{
				if (!LoadedPlanDate.equals(pd))
				{
					//#CM686735
					// 6023327 = Data containing a different date was found in the Load Plan Data. The process was aborted. See log.
					ErrorCode = "6023327";
					
					//#CM686736
					// 6026086 = Data with different date is found. File:{0} Line:{1} Item:{2} Value:{3}
					throw new CsvIllegalDataException(
						MessageResource.getMessage(
							"6026086"
								+ wDelim
								+ CFR.getFileName()
								+ wDelim
								+ CFR.getLineNo()
								+ wDelim
								+ PlanDateKey
								+ wDelim
								+ pd));
				}
			}
			else
			{

				LoadedPlanDate = pd;
				LoadedFileName = CFR.getFileName();

			}

			if (LineData[Integer.parseInt((String) Position.get(PlanDateKey))
				- 1].equals(PlanDate))
			{
				return true;
			}
		}

		//#CM686737
		// set EOF if search is complete until the end
		EOF = true;

		return false;

	}

	//****************************************************************************
	//#CM686738
	/**
	* fetch data until data becomes extinct
	* <P>
	* @return boolean  flag to decide whether next data exist or not
	* @throws NumberFormatException throw error if it is not a integer value
	* @throws FileNotFoundException if the specified file can't be found
	* @throws IOException if the file input/output fails
	* @throws InvalidDefineException if the specified value is abnormal (empty, illegal characters)
	* @throws CsvIllegalDataException if the item width of the specified key overflows
	*/
	//****************************************************************************
	public boolean DataNext() throws NumberFormatException, FileNotFoundException, IOException, InvalidDefineException, CsvIllegalDataException
	{
		//#CM686739
		// if complete until the last, false
		if (EOF)
			return false;

		//#CM686740
		// from fetch file qty
		while ((LineData = CFR.readCsv()) != null)
		{
			//#CM686741
			// fetch item value
			String Value = LineData[Integer.parseInt((String) Position.get(PlanDateKey)) - 1];

			//#CM686742
			// error if consignor code item key is not found in the environment info
			if (Integer.parseInt((String) Enable.get(PlanDateKey)) == ENABLE_MANDATORY && Value.indexOf(",") == 0)
			{
				//#CM686743
				// 6003100 = Please input {0}.
				ErrorCode = "6003100";

				//#CM686744
				// 6026010 = Data whose mandatory item is not entered is found. File:{0} Line:{1} Item:{2}
				throw new CsvIllegalDataException(
					MessageResource.getMessage(
						"6026010"
							+ wDelim
							+ CFR.getFileName()
							+ wDelim
							+ CFR.getLineNo()
							+ wDelim
							+ PlanDateKey));
			}

			//#CM686745
			// error if the item qty of the csv is lesser than minimum qty
			if (LineData.length < NeedItems)
			{
				//#CM686746
				// 6023326 = Error data was found in the Load Plan Data. The process was aborted. See log.
				ErrorCode = "6023326";
				
				//#CM686747
				// 6026006=Error data was found in data format. File: {0} Line: {1}
				throw new CsvIllegalDataException(
					MessageResource.getMessage(
						"6026006" + wDelim + CFR.getFileName() + wDelim + CFR.getLineNo()));
			}

			return true;			
		}

		//#CM686748
		// set EOF if search is complete until the end
		EOF = true;
		
		return false;
	}
	
	
	//****************************************************************************
	//#CM686749
	/**
	* Return the csv file being read
	* @return csv file name
	*/
	//****************************************************************************
	public String getFileName()
	{

		return CFR.getFileName();

	}

	//****************************************************************************
	//#CM686750
	/**
	* Return the csv file size being read
	* @return csv file size
	*/
	//****************************************************************************
	public long getFileSize()
	{

		return CFR.getFileSize();

	}

	//****************************************************************************
	//#CM686751
	/**
	* Return the csv file line size being read
	* @return csv file line count
	*/
	//****************************************************************************
	public String getLineNo()
	{

		return CFR.getLineNo();

	}

	//#CM686752
	// Private methods -----------------------------------------------

	//****************************************************************************
	//#CM686753
	/**
	* Return error number when error occurs
	* @return error number
	*/
	//****************************************************************************
	public String getErrorCode()
	{

		return ErrorCode;

	}
	//#CM686754
	/***
	* distinguish whether ascii character or not (alphanumeric + space)
	* 
	*		ASCII CHARACTER<br>
	*		JIS  SJIS EUC   +0 +1 +2 +3 +4 +5 +6 +7 +8 +9 +A +B +C +D +E +F<BR>
	* 		20   20   20       !  "  #  $  %  &  '  (  )  *  +  ,  -  .  /<BR> 
	*		30   30   30    0  1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?<BR>
	*		40   40   40    @  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O<BR>
	*		50   50   50    P  Q  R  S  T  U  V  W  X  Y  Z  [  \  ]  ^  _<BR>
	*		60   60   60    `  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o<BR>
	*		70   70   70    p  q  r  s  t  u  v  w  x  y  z  {  |  }  ~   <BR>
	* @param  arg  input character string
	* @return boolean return true if ascii character
	*/
	private boolean isAsciiChar(String arg)
	{
		for (int i = 0; i < arg.length(); i++)
		{
			char charData = arg.charAt(i);
			if ((charData < ' ') || (charData > '~'))
			{
				return false;
			}
		}
		return true;
	}


	//#CM686755
	/**
	 * Create history file
	 * @param fromPath file path
	 * @param fromFile file name
	 * @return return true if the process ends normally
	 * @throws IOException if the file input/output fails
	 */
	private boolean HistoryFileCreate(String fromPath, String fromFile) throws IOException
	{
		DataInputStream disObj;
		byte byData[] = new byte[4096];

		String strBuff;

		String toPath = WmsParam.HISTRY_HOSTDATA_PATH;

		//#CM686756
		/* object creation */

		BufferedReader brInFile = new BufferedReader(new FileReader(fromPath + fromFile));

		String Fromfile = fromPath + fromFile;
		String Tofile = toPath + fromFile;
		String datestring = DateUtil.getSystemDateTime();
		int last = Tofile.lastIndexOf(".");
		StringBuffer stBuf = new StringBuffer(Tofile);
		String toFile = stBuf.insert(last, datestring).toString();

		PrintWriter pwOutFile = new PrintWriter(new BufferedWriter(new FileWriter(toFile)));

		//#CM686757
		/* read process */

		while ((strBuff = brInFile.readLine()) != null)
		{
			pwOutFile.println(strBuff);
		}

		//#CM686758
		/* close process */

		brInFile.close();
		pwOutFile.close();

		return true;

	}
	//#CM686759
	/**
	 * verify whether the specified string contains any restricted chaaracters defined by the system<BR>
	 * restricted characters definition is specified in CommonParam
	 * @param pattern specify the target string
	 * @return true if the string contains restricted characters. else false
	 */
	private static boolean isPatternMatching(String pattern)
	{
		return (isPatternMatching(pattern, WmsParam.NG_PARAMETER_TEXT));
	}
	//#CM686760
	/**
	 * verify whether the specified string contains any restricted chaaracters defined by the system<BR>
	 * restricted characters definition is specified in CommonParam
	 * @param pattern specify the target string
	 * @param ngshars restricted characters
	 * @return true if the string contains restricted characters. else false
	 */
	private static boolean isPatternMatching(String pattern, String ngshars)
	{
		if (pattern != null && !pattern.equals(""))
		{
			for (int i = 0; i < ngshars.length(); i++)
			{
				if (pattern.indexOf(ngshars.charAt(i)) > -1)
				{
					return true;
				}
			}
		}
		return false;
	}

	//#CM686761
	/**
	 * fetch value of one record in csv buffer
	 * @return lineData 
	 */
	public String[] getLineData()
	{
		return LineData;
	}
	
	//#CM686762
	/**
	 * set value of one record in csv buffer
	 * @param lineData lineData one record of csv buffer
	 */
	public void setLineData(String[] lineData)
	{
		LineData = lineData;
	}
	
	
	//#CM686763
	/**
	 * fetch the width list to be used
	 * @return Figure width list
	 */
	public Hashtable getFigure()
	{
		return Figure;
	}
	
	//#CM686764
	/**
	 * set the width list to be used
	 * @param figure
	 */
	public void setFigure(Hashtable figure)
	{
		Figure = figure;
	}
	
	//#CM686765
	/**
	 * fetch the use enable/disable item list
	 * @return Enable use enable/disable list
	 */
	public Hashtable getEnable()
	{
		return Enable;
	}
	
	//#CM686766
	/**
	 * set the use enable/disable item list
	 * @param enable
	 */
	public void setEnable(Hashtable enable)
	{
		Enable = enable;
	}

	//#CM686767
	/**
	 * fetch item position list
	 * @return Position item position list
	 */
	public Hashtable getPosition()
	{
		return Position;
	}
	
	//#CM686768
	/**
	 * set item position list
	 * @param position
	 */
	public void setPosition(Hashtable position)
	{
		Position = position;
	}
	
	//#CM686769
	/**
	 * set file name for log output
	 * @param fileName
	 */
	public void setFileName(String fileName)
	{
		CFR.filename = fileName;
	}

}
//#CM686770
//end of class
