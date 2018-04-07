//#CM699081
//$Id: SystemParameter.java,v 1.4 2006/11/21 04:22:40 suresh Exp $

//#CM699082
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

//#CM699083
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>SystemParameter</CODE> class to pass a parameter between the screen and the schedule in the System package (except the screen to set field items for loading or reporting data).<BR>
 * Allow this class to maintain the field items to be used in each screen for system package. Use a field item depending on the screen.<BR>
 * <BR>
 * <DIR>
 * Allow the <CODE>SystemParameter</CODE> class to maintain the following field items.<BR>
 * <BR>
 * [Textbox or Label] <BR>
 * <DIR>
 *     Worker Code <BR>
 *     Password <BR>
 *     Receiving plan data storage folder <BR>
 *     Receiving plan data file name <BR>
 *     Storage plan data storage folder <BR>
 *     Storage plan data file name <BR>
 *     Picking plan data storage folder <BR>
 *     Picking plan data file name <BR>
 *     Sorting plan data storage folder <BR>
 *     Sorting plan data file name <BR>
 *     Shipping plan data storage folder <BR>
 *     Shipping plan data file name <BR>
 *     Receiving result data storage folder <BR>
 *     Receiving result data file name <BR>
 *     Storage result data storage folder <BR>
 *     Storage result data file name <BR>
 *     Picking result data storage folder <BR>
 *     Picking result data file name <BR>
 *     Sorting result data storage folder <BR>
 *     Sorting result data file name <BR>
 *     Shipping result data storage folder <BR>
 *     Shipping result data file name <BR>
 *     Stock relocation result data storage folder <BR>
 *     Stock relocation result data file name <BR>
 *     Inventory Check result data storage folder <BR>
 *     Inventory Check result data file name <BR>
 *     Planned Date or Planned Work Date <BR>
 *     Work date <BR>
 *     Person Name or Worker Name <BR>
 *     Phonetic transcriptions in kana <BR>
 *     Memo 1 <BR>
 *     Memo 2 <BR>
 *     Added date <BR>
 *     Name of Add Process <BR>
 *     Last Update Date <BR>
 *     Last Update Date (string type)<BR>
 *     Last update process name <BR>
 *     Start work date <BR>
 *     End Work Date <BR>
 *     Start Time <BR>
 *     End Time <BR>
 *     Work Time <BR>
 *     Work Quantity <BR>
 *     Work Count <BR>
 *     Work Quantity/h <BR>
 *     Work Count/h <BR>
 *     RFT Machine No.. <BR>
 *     Starting date/time of searching (date) <BR>
 *     Starting date/time of searching (time) <BR>
 *     End date/time of searching (date) <BR>
 *     End date/time of searching (time) <BR>
 *     Date/Time <BR>
 *     Sent/Received <BR>
 *     Communication Statement <BR>
 *     Content <BR>
 *     Class where error occurred. <BR>
 *     Message <BR>
 *     RFT status. <BR>
 *     Consignor Code <BR>
 *     Terminal Type <BR>
 *     Worker Code (preparatory)<BR>
 * </DIR>
 * [Checkbox] <BR>
 * <DIR>
 *     Receiving plan data <BR>
 *     Storage plan data <BR>
 *     Picking plan data <BR>
 *     Sorting Plan data <BR>
 *     Shipping plan data <BR>
 *     Decide the Storage Location automatically. <BR>
 *     Receiving Result_Selected <BR>
 *     Report the Receiving Result_Work in Process. <BR>
 *     Report the Receiving Result_Not Worked. <BR>
 *     Storage Result_Selected <BR>
 *     Report the Storage Result_Work in Process. <BR>
 *     Report the Storage Result_Not Worked. <BR>
 *     Picking Result_Selected <BR>
 *     Report the Picking Result_Work in Process. <BR>
 *     Report the Picking Result_Not Worked. <BR>
 *     Sorting Result_Selected <BR>
 *     Report the Sorting Result_Work in Process. <BR>
 *     Report the Sorting Result_Not Worked. <BR>
 *     Shipping Result_Selected <BR>
 *     Report the Shipping Result_Work in Process. <BR>
 *     Report the Shipping Result_Not Worked. <BR>
 *     Stock relocation result data <BR>
 *     Inventory Check result data <BR>
 *     Unplanned storage result data <BR>
 *     Unplanned picking result data <BR>
 * </DIR>
 * [Radio Button] <BR>
 * <DIR>
 *     Setting of field item for loading data <BR>
 *     Setting for reporting data <BR>
 *     Status "Not Worked" <BR>
 *     Add ON/OFF Status <BR>
 *     Aggregation Conditions <BR>
 *     Display condition (Communication Trace Log) <BR>
 *     Display condition (Message log) <BR>
 * </DIR>
 * [Pulldown Menu] <BR>
 * <DIR>
 *     Sex <BR>
 *     Job Title <BR>
 *     Access Privileges <BR>
 *     Work type <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2006/11/21 04:22:40 $
 * @author  $Author: suresh $
 */
public class SystemParameter extends Parameter
{
	//#CM699084
	// Class feilds ------------------------------------------------
	//#CM699085
	/**
	 * Setting of field item for loading data (Shipping)
	 */
	public static final String SELECTDEFINELOADDATA_INSTOCK = "32";

	//#CM699086
	/**
	 * Setting of field item for loading data (Storage)
	 */
	public static final String SELECTDEFINELOADDATA_STORAGE = "33";

	//#CM699087
	/**
	 * Setting of field item for loading data (Picking)
	 */
	public static final String SELECTDEFINELOADDATA_RETRIEVAL = "34";

	//#CM699088
	/**
	 * Setting of field item for loading data (Sorting)
	 */
	public static final String SELECTDEFINELOADDATA_SORTING = "35";

	//#CM699089
	/**
	 * Setting of field item for loading data (Picking)
	 */
	public static final String SELECTDEFINELOADDATA_SHIPPING = "36";

	//#CM699090
	/**
	 * Setting for reporting data (Shipping)
	 */
	public static final String SELECTDEFINEREPORTDATA_INSTOCK = "37";

	//#CM699091
	/**
	 * Setting for reporting data (Storage)
	 */
	public static final String SELECTDEFINEREPORTDATA_STORAGE = "38";

	//#CM699092
	/**
	 * Setting for reporting data (Picking)
	 */
	public static final String SELECTDEFINEREPORTDATA_RETRIEVAL = "39";

	//#CM699093
	/**
	 * Setting for reporting data (Sorting)
	 */
	public static final String SELECTDEFINEREPORTDATA_SORTING = "40";

	//#CM699094
	/**
	 * Setting for reporting data (Picking)
	 */
	public static final String SELECTDEFINEREPORTDATA_SHIPPING = "41";

	//#CM699095
	/**
	 * Setting for reporting data (Stock Relocation)
	 */
	public static final String SELECTDEFINEREPORTDATA_MOVEMENT = "42";

	//#CM699096
	/**
	 * Setting for reporting data (Inventory Check)
	 */
	public static final String SELECTDEFINEREPORTDATA_INVENTORY = "43";

	//#CM699097
	/**
	 * Setting for reporting data (Unplanned Storage)
	 */
	public static final String SELECTDEFINEREPORTDATA_NOPLANSTORAGE = "44";

	//#CM699098
	/**
	 * Setting for reporting data (Unplanned Picking)
	 */
	public static final String SELECTDEFINEREPORTDATA_NOPLANRETRIEVAL = "45";

	//#CM699099
	/**
	 * Setting of field item for loading data (Consignor Master)
	 */
	public static final String SELECTDEFINELOADDATA_CONSIGNOR = "58";

	//#CM699100
	/**
	 * Setting of field item for loading data (Supplier Master)
	 */
	public static final String SELECTDEFINELOADDATA_SUPPLIER = "59";

	//#CM699101
	/**
	 * Setting of field item for loading data (Customer Master)
	 */
	public static final String SELECTDEFINELOADDATA_CUSTOMER = "60";

	//#CM699102
	/**
	 * Setting of field item for loading data (Item Master)
	 */
	public static final String SELECTDEFINELOADDATA_ITEM = "61";

	//#CM699103
	/**
	 * Status "Not Worked" (Delete)
	 */
	public static final String SELECTUNWORKINGINFORMATION_DELETE = "0";

	//#CM699104
	/**
	 * Status "Not Worked" (Hold-over the work status)
	 */
	public static final String SELECTUNWORKINGINFORMATION_CARRYOVER = "1";

	//#CM699105
	/**
	 * Add ON/OFF Status (enabled)
	 */
	public static final String SELECTSTATUS_ENABLE = "46";

	//#CM699106
	/**
	 * Add ON/OFF Status (pended to use)
	 */
	public static final String SELECTSTATUS_DISABLE = "47";

	//#CM699107
	/**
	 * Aggregation Conditions (Display the total within a period)
	 */
	public static final String SELECTAGGREGATECONDITION_TERM = "48";

	//#CM699108
	/**
	 * Aggregation Conditions(Display the daily total.)
	 */
	public static final String SELECTAGGREGATECONDITION_DAILY = "49";

	//#CM699109
	/**
	 * Aggregation Conditions (Display the detail)
	 */
	public static final String SELECTAGGREGATECONDITION_DETAIL = "50";

	//#CM699110
	/**
	 * Display condition (Communication Trace Log) (All)
	 */
	public static final String SELECTDISPLAYCONDITION_COMMUNICATIONLOG_ALL = "51";

	//#CM699111
	/**
	 * Display condition (Communication Trace Log) (Sent)
	 */
	public static final String SELECTDISPLAYCONDITION_COMMUNICATIONLOG_SEND = "52";

	//#CM699112
	/**
	 * Display condition (Communication Trace Log) (Received)
	 */
	public static final String SELECTDISPLAYCONDITION_COMMUNICATIONLOG_RECEIVE = "53";

	//#CM699113
	/**
	 * Display condition (Message log) (All)
	 */
	public static final String SELECTDISPLAYCONDITION_MESSAGELOG_ALL = "51";

	//#CM699114
	/**
	 * Display condition (Message log) (Guidance)
	 */
	public static final String SELECTDISPLAYCONDITION_MESSAGELOG_INFORMATION = "54";

	//#CM699115
	/**
	 * Display condition (Message log) (Caution)
	 */
	public static final String SELECTDISPLAYCONDITION_MESSAGELOG_ATTENTION = "55";

	//#CM699116
	/**
	 * Display condition (Message log) (Warning)
	 */
	public static final String SELECTDISPLAYCONDITION_MESSAGELOG_WARNING = "56";

	//#CM699117
	/**
	 * Display condition (Message log) (Error)
	 */
	public static final String SELECTDISPLAYCONDITION_MESSAGELOG_ERROR = "57";

	//#CM699118
	/**
	 * Sex (Male)
	 */
	public static final String SELECTSEX_MALE = "0";

	//#CM699119
	/**
	 * Sex (Female)
	 */
	public static final String SELECTSEX_FEMALE = "1";

	//#CM699120
	/**
	 * Job Title (Administrator)
	 */
	public static final String SELECTWORKERJOBTYPE_ADMINISTRATOR = "0";

	//#CM699121
	/**
	 * Job Title (Worker)
	 */
	public static final String SELECTWORKERJOBTYPE_WORKER = "1";

	//#CM699122
	/**
	 * Access Privileges (System Administrator)
	 */
	public static final String SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR = "0";

	//#CM699123
	/**
	 * Access Privileges (Administrator)
	 */
	public static final String SELECTACCESSAUTHORITY_ADMINISTRATOR = "1";

	//#CM699124
	/**
	 * Access Privileges (Worker)
	 */
	public static final String SELECTACCESSAUTHORITY_WORKER = "2";

	//#CM699125
	/**
	 * Work Type (All)
	 */
	public static final String SELECTWORKDETAIL_ALL = "0";

	//#CM699126
	/**
	 * Work Type (Shipping)
	 */
	public static final String SELECTWORKDETAIL_INSTOCK = "1";

	//#CM699127
	/**
	 * Work Type (Storage)
	 */
	public static final String SELECTWORKDETAIL_STORAGE = "2";

	//#CM699128
	/**
	 * Work Type (Picking)
	 */
	public static final String SELECTWORKDETAIL_RETRIEVAL = "3";

	//#CM699129
	/**
	 * Work Type (Sorting)
	 */
	public static final String SELECTWORKDETAIL_SORTING = "4";

	//#CM699130
	/**
	 * Work Type (Picking)
	 */
	public static final String SELECTWORKDETAIL_SHIPPING = "5";

	//#CM699131
	/**
	 * Work Type (Inventory Check)
	 */
	public static final String SELECTWORKDETAIL_INVENTORY = "6";

	//#CM699132
	/**
	 * Work Type (Relocation for Storage)
	 */
	public static final String SELECTWORKDETAIL_MOVEMENT_STORAGE = "7";

	//#CM699133
	/**
	 * Work Type (Relocation for Retrieval)
	 */
	public static final String SELECTWORKDETAIL_MOVEMENT_RETRIEVAL = "8";

	//#CM699134
	/**
	 * Work Type (Unplanned Storage)
	 */
	public static final String SELECTWORKDETAIL_UNSCHEDULEDSTORAGE = "9";

	//#CM699135
	/**
	 * Work Type (Unplanned Picking)
	 */
	public static final String SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL = "10";

	//#CM699136
	/**
	 * Work Type (Maintenance)
	 */
	public static final String SELECTWORKDETAIL_MAINTENANCE = "11";

	//#CM699137
	/**
	 * Work Type (Increase in Maintenance)
	 */
	public static final String SELECTWORKDETAIL_MAINTENANCE_INCREASE = "12";

	//#CM699138
	/**
	 * Work Type (Decrease in Maintenance)
	 */
	public static final String SELECTWORKDETAIL_MAINTENANCE_DECREASE = "13";

	//#CM699139
	/**
	 * Work Type (Increase in Inventory Check)
	 */
	public static final String SELECTWORKDETAIL_INVENTORY_INCREASE = "14";

	//#CM699140
	/**
	 * Work Type (Decrease in Inventory Check)
	 */
	public static final String SELECTWORKDETAIL_INVENTORY_DECREASE = "15";

	//#CM699141
	/**
	 * Search flag (Plan)
	 */
	public static final String SEARCHFLAG_PLAN = "1";

	//#CM699142
	/**
	 * Search flag (Result)
	 */
	public static final String SEARCHFLAG_RESULT = "2";

	//#CM699143
	/**
	 * Search flag (inventory information)
	 */
	public static final String SEARCHFLAG_STOCK = "0";

	//#CM699144
	/**
	 * Search flag (Shortage information)
	 */
	public static final String SEARCHFLAG_SHORTAGE = "1";

	//#CM699145
	/**
	 * Range flag (Start)
	 */
	public static final String RANGE_START = "0";

	//#CM699146
	/**
	 * Range flag (End)
	 */
	public static final String RANGE_END = "1";

	//#CM699147
	/**
	 * View by: Item
	 */
	public static final String DISP_TYPE_ITEM = "0";

	//#CM699148
	/**
	 * View by: Plan data
	 */
	public static final String DISP_TYPE_PLAN = "1";

	/**
	 * Lack Amount Remnant : Installment
	 */
	public static final String LACK_AMOUNT_REMNANT = "1";

	//#CM699149
	/**
	 * Shortage Qty: Shortage
	 */
	public static final String LACK_AMOUNT_SHORTAGE = "2";

	//#CM699150
	/**
	 * Max number of characters in a file path
	 */
	public static final int FILE_PATHS_MAXLENGTH = 255;

	/**
	 * Work status : Cancel
	 */
	public static final String RFT_WORK_CANSEL = "1";

	/**
	 * Work status : Confirm
	 */
	public static final String RFT_WORK_COMFIRM = "2";

	//#CM699151
	// Class variables -----------------------------------------------
	//#CM699152
	/**
	 * Worker Code
	 */
	private String wWorkerCode;

	//#CM699153
	/**
	 * Password
	 */
	private String wPassword;

	//#CM699154
	/**
	 * Receiving plan data storage folder
	 */
	private String wFolder_LoadInstockData;

	//#CM699155
	/**
	 * Receiving plan data file name
	 */
	private String wFileName_LoadInstockData;

	//#CM699156
	/**
	 * Storage plan data storage folder
	 */
	private String wFolder_LoadStorageData;

	//#CM699157
	/**
	 * Storage plan data file name
	 */
	private String wFileName_LoadStorageData;

	//#CM699158
	/**
	 * Picking plan data storage folder
	 */
	private String wFolder_LoadRetrievalData;

	//#CM699159
	/**
	 * Picking plan data file name
	 */
	private String wFileName_LoadRetrievalData;

	//#CM699160
	/**
	 * Sorting plan data storage folder
	 */
	private String wFolder_LoadSortingData;

	//#CM699161
	/**
	 * Sorting plan data file name
	 */
	private String wFileName_LoadSortingData;

	//#CM699162
	/**
	 * Shipping plan data storage folder
	 */
	private String wFolder_LoadShippingData;

	//#CM699163
	/**
	 * Shipping plan data file name
	 */
	private String wFileName_LoadShippingData;

	//#CM699164
	/**
	 * Receiving result data storage folder
	 */
	private String wFolder_ReportInstockData;

	//#CM699165
	/**
	 * Receiving result data file name
	 */
	private String wFileName_ReportInstockData;

	//#CM699166
	/**
	 * Storage result data storage folder
	 */
	private String wFolder_ReportStorageData;

	//#CM699167
	/**
	 * Storage result data file name
	 */
	private String wFileName_ReportStorageData;

	//#CM699168
	/**
	 * Picking result data storage folder
	 */
	private String wFolder_ReportRetrievalData;

	//#CM699169
	/**
	 * Picking result data file name
	 */
	private String wFileName_ReportRetrievalData;

	//#CM699170
	/**
	 * Sorting result data storage folder
	 */
	private String wFolder_ReportSortingData;

	//#CM699171
	/**
	 * Sorting result data file name
	 */
	private String wFileName_ReportSortingData;

	//#CM699172
	/**
	 * Shipping result data storage folder
	 */
	private String wFolder_ReportShippingData;

	//#CM699173
	/**
	 * Shipping result data file name
	 */
	private String wFileName_ReportShippingData;

	//#CM699174
	/**
	 * Stock relocation result data storage folder
	 */
	private String wFolder_ReportMovementData;

	//#CM699175
	/**
	 * Stock relocation result data file name
	 */
	private String wFileName_ReportMovementData;

	//#CM699176
	/**
	 * Inventory Check result data storage folder
	 */
	private String wFolder_ReportInventoryData;

	//#CM699177
	/**
	 * Inventory Check result data file name
	 */
	private String wFileName_ReportInventoryData;

	//#CM699178
	/**
	 * Inventory information data storage folder
	 */
	private String wFolder_ReportDataStock;

	//#CM699179
	/**
	 * Inventory information data file name
	 */
	private String wFileName_ReportDataStock;

	//#CM699180
	/**
	 * Unplanned storage data storage folder
	 */
	private String wFolder_ReportNoPlanStorageData;

	//#CM699181
	/**
	 * Unplanned storage data file name
	 */
	private String wFileName_ReportNoPlanStorageData;

	//#CM699182
	/**
	 * Unplanned picking data storage folder
	 */
	private String wFolder_ReportNoPlanRetrievalData;

	//#CM699183
	/**
	 * Unplanned picking data file name
	 */
	private String wFileName_ReportNoPlanRetrievalData;

	//#CM699184
	/**
	 * Consignor master data storage folder
	 */
	private String wFolder_LoadMasterConsignorData;

	//#CM699185
	/**
	 * Consignor master data file name
	 */
	private String wFileName_LoadMasterConsignorData;

	//#CM699186
	/**
	 * Supplier master data storage folder
	 */
	private String wFolder_LoadMasterSupplierData;

	//#CM699187
	/**
	 * Supplier master data file name
	 */
	private String wFileName_LoadMasterSupplierData;

	//#CM699188
	/**
	 * Customer master data storage folder
	 */
	private String wFolder_LoadMasterCustomerData;

	//#CM699189
	/**
	 * Customer master data file name
	 */
	private String wFileName_LoadMasterCustomerData;

	//#CM699190
	/**
	 * Item master data storage folder
	 */
	private String wFolder_LoadMasterItemjData;

	//#CM699191
	/**
	 * Item master data file name
	 */
	private String wFileName_LoadMasterItemData;

	//#CM699192
	/**
	 * Planned Date or Planned Work Date
	 */
	private String wPlanDate;

	//#CM699193
	/**
	 * Work date
	 */
	private String wWorkDate;

	//#CM699194
	/**
	 * Person Name or Worker Name
	 */
	private String wWorkerName;

	//#CM699195
	/**
	 * Phonetic transcriptions in kana
	 */
	private String wFurigana;

	//#CM699196
	/**
	 * Memo 1
	 */
	private String wMemo1;

	//#CM699197
	/**
	 * Memo 2
	 */
	private String wMemo2;

	//#CM699198
	/**
	 * Added date
	 */
	private java.util.Date wRegistDate;

	//#CM699199
	/**
	 * Name of Add Process
	 */
	private String wRegistPName;

	//#CM699200
	/**
	 * Last Update Date
	 */
	private java.util.Date wLastUpdateDate;

	//#CM699201
	/**
	 * Last Update Date (string type)
	 */
	private String wLastUpdateDateString;

	//#CM699202
	/**
	 * Last update process name
	 */
	private String wLastUpdatePName;

	//#CM699203
	/**
	 * Start work date
	 */
	private String wFromWorkDate;

	//#CM699204
	/**
	 * End Work Date
	 */
	private String wToWorkDate;

	//#CM699205
	/**
	 * Start Time
	 */
	private String wWorkStartTime;

	//#CM699206
	/**
	 * End Time
	 */
	private String wWorkEndTime;

	//#CM699207
	/**
	 * Work Time
	 */
	private String wWorkTime;

	//#CM699208
	/**
	 * Work Quantity
	 */
	private int wWorkQty;

	//#CM699209
	/**
	 * Work Count
	 */
	private int wWorkCnt;

	//#CM699210
	/**
	 * Work Quantity/h
	 */
	private long wWorkQtyPerHour;

	//#CM699211
	/**
	 * Work Count/h
	 */
	private long wWorkCntPerHour;

	//#CM699212
	/**
	 * RFT Machine No..
	 */
	private String wRftNo;

	//#CM699213
	/**
	 * Starting date/time of searching (date)
	 */
	private String wFromFindDate;

	//#CM699214
	/**
	 * Starting date/time of searching (time)
	 */
	private String wFromFindTime;

	//#CM699215
	/**
	 * End date/time of searching (date)
	 */
	private String wToFindDate;

	//#CM699216
	/**
	 * End date/time of searching (time)
	 */
	private String wToFindTime;

	//#CM699217
	/**
	 * Date/Time
	 */
	private String wMessageLogDate;

	//#CM699218
	/**
	 * Sent/Received
	 */
	private String wCommunication;

	//#CM699219
	/**
	 * Communication Statement
	 */
	private String wCommunicationMessage;

	//#CM699220
	/**
	 * Content
	 */
	private String wError;

	//#CM699221
	/**
	 * Class where error occurred.
	 */
	private String wErrorClass;

	//#CM699222
	/**
	 * Message
	 */
	private String wMessage;

	//#CM699223
	/**
	 * RFT status.
	 */
	private String wRftStatus;

	//#CM699224
	/**
	 * Work Type
	 */
	private String wJobType;


	//#CM699225
	/**
	 * Receiving plan data
	 */
	private boolean wSelectLoadInstockData;

	//#CM699226
	/**
	 * Storage plan data
	 */
	private boolean wSelectLoadStorageData;

	//#CM699227
	/**
	 * Picking plan data
	 */
	private boolean wSelectLoadRetrievalData;

	//#CM699228
	/**
	 * Sorting Plan data
	 */
	private boolean wSelectLoadSortingData;

	//#CM699229
	/**
	 * Shipping plan data
	 */
	private boolean wSelectLoadShippingData;

	//#CM699230
	/**
	 * Consignor master data
	 */
	private boolean wSelectLoadConsignorMasterData;

	//#CM699231
	/**
	 * Supplier master data
	 */
	private boolean wSelectLoadSupplierMasterData;

	//#CM699232
	/**
	 * Customer master data
	 */
	private boolean wSelectLoadCustomerMasterData;

	//#CM699233
	/**
	 * Item master data
	 */
	private boolean wSelectLoadItemMasterData;

	//#CM699234
	/**
	 * Decide the Storage Location automatically.
	 */
	private boolean wSelectLoadIdmAuto;

	//#CM699235
	/**
	 * Receiving Result_Selected
	 */
	private boolean wSelectReportInstockData;

	//#CM699236
	/**
	 * Report the Receiving Result_Work in Process.
	 */
	private boolean wSelectReportInstockData_InProgress;

	//#CM699237
	/**
	 * Report the Receiving Result_Not Worked.
	 */
	private boolean wSelectReportInstockData_Unworking;

	//#CM699238
	/**
	 * Storage Result_Selected
	 */
	private boolean wSelectReportStorageData;

	//#CM699239
	/**
	 * Report the Storage Result_Work in Process.
	 */
	private boolean wSelectReportStorageData_InProgress;

	//#CM699240
	/**
	 * Report the Storage Result_Not Worked.
	 */
	private boolean wSelectReportStorageData_Unworking;

	//#CM699241
	/**
	 * Picking Result_Selected
	 */
	private boolean wSelectReportRetrievalData;

	//#CM699242
	/**
	 * Report the Picking Result_Work in Process.
	 */
	private boolean wSelectReportRetrievalData_InProgress;

	//#CM699243
	/**
	 * Report the Picking Result_Not Worked.
	 */
	private boolean wSelectReportRetrievalData_Unworking;

	//#CM699244
	/**
	 * Sorting Result_Selected
	 */
	private boolean wSelectReportSortingData;

	//#CM699245
	/**
	 * Report the Sorting Result_Work in Process.
	 */
	private boolean wSelectReportSortingData_InProgress;

	//#CM699246
	/**
	 * Report the Sorting Result_Not Worked.
	 */
	private boolean wSelectReportSortingData_Unworking;

	//#CM699247
	/**
	 * Shipping Result_Selected
	 */
	private boolean wSelectReportShippingData;

	//#CM699248
	/**
	 * Report the Shipping Result_Work in Process.
	 */
	private boolean wSelectReportShippingData_InProgress;

	//#CM699249
	/**
	 * Report the Shipping Result_Not Worked.
	 */
	private boolean wSelectReportShippingData_Unworking;

	//#CM699250
	/**
	 * Stock relocation result data
	 */
	private boolean wSelectReportMovementData;

	//#CM699251
	/**
	 * Inventory Check result data
	 */
	private boolean wSelectReportInventoryData;

	//#CM699252
	/**
	 * Unplanned storage result data
	 */
	private boolean wSelectReportNoPlanStorageData;

	//#CM699253
	/**
	 * Unplanned picking result data
	 */
	private boolean wSelectReportNoPlanRetrievalData;

	//#CM699254
	/**
	 * Inventory information data
	 */
	private boolean wSelectReportDataStock;

	//#CM699255
	/**
	 * Setting of field item for loading data
	 */
	private String wSelectDefineLoadData;

	//#CM699256
	/**
	 * Setting for reporting data
	 */
	private String wSelectDefineReportData;

	//#CM699257
	/**
	 * Status "Not Worked"
	 */
	private String wSelectUnworkingInformation;

	//#CM699258
	/**
	 * Add ON/OFF Status
	 */
	private String wSelectStatus;

	//#CM699259
	/**
	 * Aggregation Conditions
	 */
	private String wSelectAggregateCondition;

	//#CM699260
	/**
	 * Display condition (Communication Trace Log)
	 */
	private String wSelectDisplayCondition_CommunicationLog;

	//#CM699261
	/**
	 * Display condition (Message log)
	 */
	private String wSelectDisplayCondition_MessageLog;

	//#CM699262
	/**
	 * Sex
	 */
	private String wSelectSex;

	//#CM699263
	/**
	 * Job Title
	 */
	private String wSelectWorkerJobType;

	//#CM699264
	/**
	 * Access Privileges
	 */
	private String wSelectAccessAuthority;

	//#CM699265
	/**
	 * Work type
	 */
	private String wSelectWorkDetail;

	//#CM699266
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM699267
	/**
	 * Unit of View
	 */
	private String wDispType;

	//#CM699268
	/**
	 * Consignor Name
	 */
	private String wConsignorName;

	//#CM699269
	/**
	 * Item Code
	 */
	private String wItemCode;

	//#CM699270
	/**
	 * Item Name
	 */
	private String wItemName;

	//#CM699271
	/**
	 * Packed Qty per Case
	 */
	private int wEnteringQty;

	//#CM699272
	/**
	 * Packed qty per bundle
	 */
	private int wBundleEnteringQty;

	//#CM699273
	/**
	 * Planned Case Qty
	 */
	private int wPlanCaseQty;

	//#CM699274
	/**
	 * Planned Piece Qty
	 */
	private int wPlanPieceQty;

	//#CM699275
	/**
	 * Allocatable Case Qty
	 */
	private int wEnableCaseQty;

	//#CM699276
	/**
	 * Allocatable Piece Qty
	 */
	private int wEnablePieceQty;

	//#CM699277
	/**
	 * Shortage Case Qty
	 */
	private int wShortageCaseQty;

	//#CM699278
	/**
	 * Shortage Piece Qty
	 */
	private int wShortagePieceQty;

	//#CM699279
	/**
	 * Case Picking Location
	 */
	private String wCaseLocation;

	//#CM699280
	/**
	 * Piece Picking Location
	 */
	private String wPieceLocation;

	//#CM699281
	/**
	 * Case Order No.
	 */
	private String wCaseOrderNo;

	//#CM699282
	/**
	 * Piece Order No.
	 */
	private String wPieceOrderNo;

	//#CM699283
	/**
	 * Customer Name
	 */
	private String wCustomerName;

	//#CM699284
	/**
	 * Shipping Ticket No
	 */
	private String wShippingTicketNo;

	//#CM699285
	/**
	 * Shipping ticket line No.
	 */
	private int wShippingTicketLineNo;

	//#CM699286
	/**
	 * Added Date/Time (Array)
	 */
	private java.util.Date[] wRegistDateArray;

	//#CM699287
	/**
	 * Terminal Type
	 */
	private String wTerminalType;

	//#CM699288
	/**
	 * Worker Code (preparatory)
	 */
	private String wWorkerCodeB;

	//#CM699289
	/**
	 * Worker Code to be displayed
	 */
	private String wDisplayWorkerCode;

	//#CM699290
	/**
	 * Work in progress
	 * 1: Cancel
	 * 2: Submit
	 */
	private String wWorkOnTheWay;

	//#CM699291
	/**
	 * For Shortage Qty
	 * 1: Pending/Additional delivery
	 * 2: Shortage
	 */
	private String wLackAmount;

	//#CM699292
	// Class method --------------------------------------------------
	//#CM699293
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2006/11/21 04:22:40 $");
	}

	//#CM699294
	// Constructors --------------------------------------------------
	//#CM699295
	/**
	 * Initialize this class.
	 */
	public SystemParameter()
	{
	}

	//#CM699296
	// Public methods ------------------------------------------------

	//#CM699297
	/**
	 * Sent/Received
	 * @return	Sent/Received
	 */
	public String getCommunication()
	{
		return wCommunication;
	}

	//#CM699298
	/**
	 * Communication Statement
	 * @return	Communication Statement
	 */
	public String getCommunicationMessage()
	{
		return wCommunicationMessage;
	}

	//#CM699299
	/**
	 * Content
	 * @return	Content
	 */
	public String getError()
	{
		return wError;
	}

	//#CM699300
	/**
	 * Class where error occurred.
	 * @return	Class where error occurred.
	 */
	public String getErrorClass()
	{
		return wErrorClass;
	}

	//#CM699301
	/**
	 * Receiving plan data file name
	 * @return	Receiving plan data file name
	 */
	public String getFileName_LoadInstockData()
	{
		return wFileName_LoadInstockData;
	}

	//#CM699302
	/**
	 * Sorting plan data file name
	 * @return	Sorting plan data file name
	 */
	public String getFileName_LoadSortingData()
	{
		return wFileName_LoadSortingData;
	}

	//#CM699303
	/**
	 * Picking plan data file name
	 * @return	Picking plan data file name
	 */
	public String getFileName_LoadRetrievalData()
	{
		return wFileName_LoadRetrievalData;
	}

	//#CM699304
	/**
	 * Shipping plan data file name
	 * @return	Shipping plan data file name
	 */
	public String getFileName_LoadShippingData()
	{
		return wFileName_LoadShippingData;
	}

	//#CM699305
	/**
	 * Storage plan data file name
	 * @return	Storage plan data file name
	 */
	public String getFileName_LoadStorageData()
	{
		return wFileName_LoadStorageData;
	}

	//#CM699306
	/**
	 * Receiving result data file name
	 * @return	Receiving result data file name
	 */
	public String getFileName_ReportInstockData()
	{
		return wFileName_ReportInstockData;
	}

	//#CM699307
	/**
	 * Inventory Check result data file name
	 * @return	Inventory Check result data file name
	 */
	public String getFileName_ReportInventoryData()
	{
		return wFileName_ReportInventoryData;
	}

	//#CM699308
	/**
	 * Stock relocation result data file name
	 * @return	Stock relocation result data file name
	 */
	public String getFileName_ReportMovementData()
	{
		return wFileName_ReportMovementData;
	}

	//#CM699309
	/**
	 * Sorting result data file name
	 * @return	Sorting result data file name
	 */
	public String getFileName_ReportSortingData()
	{
		return wFileName_ReportSortingData;
	}

	//#CM699310
	/**
	 * Picking result data file name
	 * @return	Picking result data file name
	 */
	public String getFileName_ReportRetrievalData()
	{
		return wFileName_ReportRetrievalData;
	}

	//#CM699311
	/**
	 * Shipping result data file name
	 * @return	Shipping result data file name
	 */
	public String getFileName_ReportShippingData()
	{
		return wFileName_ReportShippingData;
	}

	//#CM699312
	/**
	 * Storage result data file name
	 * @return	Storage result data file name
	 */
	public String getFileName_ReportStorageData()
	{
		return wFileName_ReportStorageData;
	}

	//#CM699313
	/**
	 * Inventory information data file name
	 * @return	Inventory information data file name
	 */
	public String getFileName_ReportDataStock()
	{
		return wFileName_ReportDataStock;
	}

	//#CM699314
	/**
	 * Unplanned storage result data file name
	 * @return	Unplanned storage result data file name
	 */
	public String getFileName_ReportNoPlanStorageData()
	{
		return wFileName_ReportNoPlanStorageData;
	}

	//#CM699315
	/**
	 * Unplanned picking result data file name
	 * @return	Unplanned picking result data file name
	 */
	public String getFileName_ReportNoPlanRetrievalData()
	{
		return wFileName_ReportNoPlanRetrievalData;
	}

	//#CM699316
	/**
	 * Consignor master data file name
	 * @return	Consignor master data file name
	 */
	public String getFileName_LoadMasterConsignorData()
	{
		return wFileName_LoadMasterConsignorData;
	}

	//#CM699317
	/**
	 * Supplier master data file name
	 * @return	Supplier master data file name
	 */
	public String getFileName_LoadMasterSupplierData()
	{
		return wFileName_LoadMasterSupplierData;
	}

	//#CM699318
	/**
	 * Customer master data file name
	 * @return	Customer master data file name
	 */
	public String getFileName_LoadMasterCustomerData()
	{
		return wFileName_LoadMasterCustomerData;
	}

	//#CM699319
	/**
	 * Item master data file name
	 * @return	Item master data file name
	 */
	public String getFileName_LoadMasterItemData()
	{
		return wFileName_LoadMasterItemData;
	}
	//#CM699320
	/**
	 * Receiving plan data storage folder
	 * @return	Receiving plan data storage folder
	 */
	public String getFolder_LoadInstockData()
	{
		return wFolder_LoadInstockData;
	}

	//#CM699321
	/**
	 * Sorting plan data storage folder
	 * @return	Sorting plan data storage folder
	 */
	public String getFolder_LoadSortingData()
	{
		return wFolder_LoadSortingData;
	}

	//#CM699322
	/**
	 * Picking plan data storage folder
	 * @return	Picking plan data storage folder
	 */
	public String getFolder_LoadRetrievalData()
	{
		return wFolder_LoadRetrievalData;
	}

	//#CM699323
	/**
	 * Shipping plan data storage folder
	 * @return	Shipping plan data storage folder
	 */
	public String getFolder_LoadShippingData()
	{
		return wFolder_LoadShippingData;
	}

	//#CM699324
	/**
	 * Storage plan data storage folder
	 * @return	Storage plan data storage folder
	 */
	public String getFolder_LoadStorageData()
	{
		return wFolder_LoadStorageData;
	}

	//#CM699325
	/**
	 * Receiving result data storage folder
	 * @return	Receiving result data storage folder
	 */
	public String getFolder_ReportInstockData()
	{
		return wFolder_ReportInstockData;
	}

	//#CM699326
	/**
	 * Inventory Check result data storage folder
	 * @return	Inventory Check result data storage folder
	 */
	public String getFolder_ReportInventoryData()
	{
		return wFolder_ReportInventoryData;
	}

	//#CM699327
	/**
	 * Stock relocation result data storage folder
	 * @return	Stock relocation result data storage folder
	 */
	public String getFolder_ReportMovementData()
	{
		return wFolder_ReportMovementData;
	}

	//#CM699328
	/**
	 * Sorting result data storage folder
	 * @return	Sorting result data storage folder
	 */
	public String getFolder_ReportSortingData()
	{
		return wFolder_ReportSortingData;
	}

	//#CM699329
	/**
	 * Picking result data storage folder
	 * @return	Picking result data storage folder
	 */
	public String getFolder_ReportRetrievalData()
	{
		return wFolder_ReportRetrievalData;
	}

	//#CM699330
	/**
	 * Shipping result data storage folder
	 * @return	Shipping result data storage folder
	 */
	public String getFolder_ReportShippingData()
	{
		return wFolder_ReportShippingData;
	}

	//#CM699331
	/**
	 * Storage result data storage folder
	 * @return	Storage result data storage folder
	 */
	public String getFolder_ReportStorageData()
	{
		return wFolder_ReportStorageData;
	}

	//#CM699332
	/**
	 * Inventory information data storage folder
	 * @return	Inventory information data storage folder
	 */
	public String getFolder_ReportDataStock()
	{
		return wFolder_ReportDataStock;
	}

	//#CM699333
	/**
	 * Unplanned storage result data storage folder
	 * @return	Unplanned storage result data storage folder
	 */
	public String getFolder_ReportNoPlanStorageData()
	{
		return wFolder_ReportNoPlanStorageData;
	}

	//#CM699334
	/**
	 * Unplanned picking result data storage folder
	 * @return	Unplanned picking result data storage folder
	 */
	public String getFolder_ReportNoPlanRetrievalData()
	{
		return wFolder_ReportNoPlanRetrievalData;
	}

	//#CM699335
	/**
	 * Consignor master data storage folder
	 * @return	Consignor master data storage folder
	 */
	public String getFolder_LoadMasterConsignorData()
	{
		return wFolder_LoadMasterConsignorData;
	}

	//#CM699336
	/**
	 * Supplier master data storage folder
	 * @return	Supplier master data storage folder
	 */
	public String getFolder_LoadMasterSupplierData()
	{
		return wFolder_LoadMasterSupplierData;
	}

	//#CM699337
	/**
	 * Customer master data storage folder
	 * @return	Customer master data storage folder
	 */
	public String getFolder_LoadMasterCustomerData()
	{
		return wFolder_LoadMasterCustomerData;
	}

	//#CM699338
	/**
	 * Item master data storage folder
	 * @return	Item master data storage folder
	 */
	public String getFolder_LoadMasterItemjData()
	{
		return wFolder_LoadMasterItemjData;
	}

	//#CM699339
	/**
	 * Starting date/time of searching (date)
	 * @return	Starting date/time of searching (date)
	 */
	public String getFromFindDate()
	{
		return wFromFindDate;
	}

	//#CM699340
	/**
	 * Starting date/time of searching (time)
	 * @return	Starting date/time of searching (time)
	 */
	public String getFromFindTime()
	{
		return wFromFindTime;
	}

	//#CM699341
	/**
	 * Start work date
	 * @return	Start work date
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate;
	}

	//#CM699342
	/**
	 * Phonetic transcriptions in kana
	 * @return	Phonetic transcriptions in kana
	 */
	public String getFurigana()
	{
		return wFurigana;
	}

	//#CM699343
	/**
	 * Last Update Date
	 * @return	Last Update Date
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM699344
	/**
	 * Last Update Date (string type)
	 * @return	Last update date in string type
	 */
	public String getWLastUpdateDateString()
	{
		return wLastUpdateDateString;
	}

	//#CM699345
	/**
	 * Memo 1
	 * @return	Memo 1
	 */
	public String getMemo1()
	{
		return wMemo1;
	}

	//#CM699346
	/**
	 * Memo 2
	 * @return	Memo 2
	 */
	public String getMemo2()
	{
		return wMemo2;
	}

	//#CM699347
	/**
	 * Message
	 * @return	Message
	 */
	public String getMessage()
	{
		return wMessage;
	}

	//#CM699348
	/**
	 * Date/Time
	 * @return	Date/Time
	 */
	public String getMessageLogDate()
	{
		return wMessageLogDate;
	}

	//#CM699349
	/**
	 * Password
	 * @return	Password
	 */
	public String getPassword()
	{
		return wPassword;
	}

	//#CM699350
	/**
	 * Planned Date or Planned Work Date
	 * @return	Planned Date or Planned Work Date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	//#CM699351
	/**
	 * Added date
	 * @return	Added date
	 */
	public java.util.Date getRegistDate()
	{
		return wRegistDate;
	}

	//#CM699352
	/**
	 * RFT status.
	 * @return	RFT status.
	 */
	public String getRftStatus()
	{
		return wRftStatus;
	}

	//#CM699353
	/**
	 * Work division
	 * @return	Work division
	 */
	public String getJobType()
	{
		return wJobType;
	}

	//#CM699354
	/**
	 * Access Privileges
	 * @return	Access Privileges
	 */
	public String getSelectAccessAuthority()
	{
		return wSelectAccessAuthority;
	}

	//#CM699355
	/**
	 * Aggregation Conditions
	 * @return	Aggregation Conditions
	 */
	public String getSelectAggregateCondition()
	{
		return wSelectAggregateCondition;
	}

	//#CM699356
	/**
	 * Setting of field item for loading data
	 * @return	Setting of field item for loading data
	 */
	public String getSelectDefineLoadData()
	{
		return wSelectDefineLoadData;
	}

	//#CM699357
	/**
	 * Setting for reporting data
	 * @return	Setting for reporting data
	 */
	public String getSelectDefineReportData()
	{
		return wSelectDefineReportData;
	}

	//#CM699358
	/**
	 * Display condition (Communication Trace Log)
	 * @return	Display condition (Communication Trace Log)
	 */
	public String getSelectDisplayCondition_CommunicationLog()
	{
		return wSelectDisplayCondition_CommunicationLog;
	}

	//#CM699359
	/**
	 * Display condition (Message log)
	 * @return	Display condition (Message log)
	 */
	public String getSelectDisplayCondition_MessageLog()
	{
		return wSelectDisplayCondition_MessageLog;
	}

	//#CM699360
	/**
	 * Job Title
	 * @return	Job Title
	 */
	public String getSelectWorkerJobType()
	{
		return wSelectWorkerJobType;
	}

	//#CM699361
	/**
	 * Receiving plan data
	 * @return	Receiving plan data
	 */
	public boolean getSelectLoadInstockData()
	{
		return wSelectLoadInstockData;
	}

	//#CM699362
	/**
	 * Sorting Plan data
	 * @return	Sorting Plan data
	 */
	public boolean getSelectLoadSortingData()
	{
		return wSelectLoadSortingData;
	}

	//#CM699363
	/**
	 * Picking plan data
	 * @return	Picking plan data
	 */
	public boolean getSelectLoadRetrievalData()
	{
		return wSelectLoadRetrievalData;
	}

	//#CM699364
	/**
	 * Shipping plan data
	 * @return	Shipping plan data
	 */
	public boolean getSelectLoadShippingData()
	{
		return wSelectLoadShippingData;
	}

	//#CM699365
	/**
	 * Storage plan data
	 * @return	Storage plan data
	 */
	public boolean getSelectLoadStorageData()
	{
		return wSelectLoadStorageData;
	}

	//#CM699366
	/**
	 * Consignor master data
	 * @return	Consignor master data
	 */
	public boolean getSelectLoadConsignorMasterData()
	{
		return wSelectLoadConsignorMasterData;
	}

	//#CM699367
	/**
	 * Supplier master data
	 * @return	Supplier master data
	 */
	public boolean getSelectLoadSupplierMasterData()
	{
		return wSelectLoadSupplierMasterData;
	}

	//#CM699368
	/**
	 * Customer master data
	 * @return	Customer master data
	 */
	public boolean getSelectLoadCustomerMasterData()
	{
		return wSelectLoadCustomerMasterData;
	}

	//#CM699369
	/**
	 * Item master data
	 * @return	Item master data
	 */
	public boolean getSelectLoadItemMasterData()
	{
		return wSelectLoadItemMasterData;
	}

	//#CM699370
	/**
	 * Automated decision-making of Storage Location
	 * @return	Automated decision-making of Storage Location
	 */
	public boolean getSelectLoadIdmAuto()
	{
		return wSelectLoadIdmAuto;
	}

	//#CM699371
	/**
	 * Receiving Result_Selected
	 * @return	Receiving Result_Selected
	 */
	public boolean getSelectReportInstockData()
	{
		return wSelectReportInstockData;
	}

	//#CM699372
	/**
	 * Report the Receiving Result_Work in Process.
	 * @return	Report the Receiving Result_Work in Process.
	 */
	public boolean getSelectReportInstockData_InProgress()
	{
		return wSelectReportInstockData_InProgress;
	}

	//#CM699373
	/**
	 * Report the Receiving Result_Not Worked.
	 * @return	Report the Receiving Result_Not Worked.
	 */
	public boolean getSelectReportInstockData_Unworking()
	{
		return wSelectReportInstockData_Unworking;
	}

	//#CM699374
	/**
	 * Inventory Check result data
	 * @return	Inventory Check result data
	 */
	public boolean getSelectReportInventoryData()
	{
		return wSelectReportInventoryData;
	}

	//#CM699375
	/**
	 * Stock relocation result data
	 * @return	Stock relocation result data
	 */
	public boolean getSelectReportMovementData()
	{
		return wSelectReportMovementData;
	}

	//#CM699376
	/**
	 * Unplanned storage result data
	 * @return	Unplanned storage result data
	 */
	public boolean getSelectReportNoPlanStorageData()
	{
		return wSelectReportNoPlanStorageData;
	}

	//#CM699377
	/**
	 * Unplanned picking result data
	 * @return	Unplanned picking result data
	 */
	public boolean getSelectReportNoPlanRetrievalData()
	{
		return wSelectReportNoPlanRetrievalData;
	}

	//#CM699378
	/**
	 * Sorting Result_Selected
	 * @return	Sorting Result_Selected
	 */
	public boolean getSelectReportSortingData()
	{
		return wSelectReportSortingData;
	}

	//#CM699379
	/**
	 * Report the Sorting Result_Work in Process.
	 * @return	Report the Sorting Result_Work in Process.
	 */
	public boolean getSelectReportSortingData_InProgress()
	{
		return wSelectReportSortingData_InProgress;
	}

	//#CM699380
	/**
	 * Report the Sorting Result_Not Worked.
	 * @return	Report the Sorting Result_Not Worked.
	 */
	public boolean getSelectReportSortingData_Unworking()
	{
		return wSelectReportSortingData_Unworking;
	}

	//#CM699381
	/**
	 * Picking Result_Selected
	 * @return	Picking Result_Selected
	 */
	public boolean getSelectReportRetrievalData()
	{
		return wSelectReportRetrievalData;
	}

	//#CM699382
	/**
	 * Report the Picking Result_Work in Process.
	 * @return	Report the Picking Result_Work in Process.
	 */
	public boolean getSelectReportRetrievalData_InProgress()
	{
		return wSelectReportRetrievalData_InProgress;
	}

	//#CM699383
	/**
	 * Report the Picking Result_Not Worked.
	 * @return	Report the Picking Result_Not Worked.
	 */
	public boolean getSelectReportRetrievalData_Unworking()
	{
		return wSelectReportRetrievalData_Unworking;
	}

	//#CM699384
	/**
	 * Shipping Result_Selected
	 * @return	Shipping Result_Selected
	 */
	public boolean getSelectReportShippingData()
	{
		return wSelectReportShippingData;
	}

	//#CM699385
	/**
	 * Report the Shipping Result_Work in Process.
	 * @return	Report the Shipping Result_Work in Process.
	 */
	public boolean getSelectReportShippingData_InProgress()
	{
		return wSelectReportShippingData_InProgress;
	}

	//#CM699386
	/**
	 * Report the Shipping Result_Not Worked.
	 * @return	Report the Shipping Result_Not Worked.
	 */
	public boolean getSelectReportShippingData_Unworking()
	{
		return wSelectReportShippingData_Unworking;
	}

	//#CM699387
	/**
	 * Storage Result_Selected
	 * @return	Storage Result_Selected
	 */
	public boolean getSelectReportStorageData()
	{
		return wSelectReportStorageData;
	}

	//#CM699388
	/**
	 * Report the Storage Result_Work in Process.
	 * @return	Report the Storage Result_Work in Process.
	 */
	public boolean getSelectReportStorageData_InProgress()
	{
		return wSelectReportStorageData_InProgress;
	}

	//#CM699389
	/**
	 *  Report the Storage Result_Not Worked.
	 * @return	 Report the Storage Result_Not Worked.
	 */
	public boolean getSelectReportStorageData_Unworking()
	{
		return wSelectReportStorageData_Unworking;
	}

	//#CM699390
	/**
	 * Inventory information data
	 * @return	Inventory information data
	 */
	public boolean getSelectReportDataStock()
	{
		return wSelectReportDataStock;
	}

	//#CM699391
	/**
	 * Sex
	 * @return	Sex
	 */
	public String getSelectSex()
	{
		return wSelectSex;
	}

	//#CM699392
	/**
	 * Add ON/OFF Status
	 * @return	Add ON/OFF Status
	 */
	public String getSelectStatus()
	{
		return wSelectStatus;
	}

	//#CM699393
	/**
	 * Status "Not Worked"
	 * @return	Status "Not Worked"
	 */
	public String getSelectUnworkingInformation()
	{
		return wSelectUnworkingInformation;
	}

	//#CM699394
	/**
	 * Work type
	 * @return	Work type
	 */
	public String getSelectWorkDetail()
	{
		return wSelectWorkDetail;
	}

	//#CM699395
	/**
	 * RFT Machine No..
	 * @return	RFT Machine No..
	 */
	public String getRftNo()
	{
		return wRftNo;
	}

	//#CM699396
	/**
	 * End date/time of searching (date)
	 * @return	End date/time of searching (date)
	 */
	public String getToFindDate()
	{
		return wToFindDate;
	}

	//#CM699397
	/**
	 * End date/time of searching (time)
	 * @return	End date/time of searching (time)
	 */
	public String getToFindTime()
	{
		return wToFindTime;
	}

	//#CM699398
	/**
	 * End Work Date
	 * @return	End Work Date
	 */
	public String getToWorkDate()
	{
		return wToWorkDate;
	}

	//#CM699399
	/**
	 * Work Count
	 * @return	Work Count
	 */
	public int getWorkCnt()
	{
		return wWorkCnt;
	}

	//#CM699400
	/**
	 * Work Count/h
	 * @return	Work Count/h
	 */
	public long getWorkCntPerHour()
	{
		return wWorkCntPerHour;
	}

	//#CM699401
	/**
	 * Work date
	 * @return	Work date
	 */
	public String getWorkDate()
	{
		return wWorkDate;
	}

	//#CM699402
	/**
	 * End Time
	 * @return	End Time
	 */
	public String getWorkEndTime()
	{
		return wWorkEndTime;
	}

	//#CM699403
	/**
	 * Worker Code
	 * @return	Worker Code
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	//#CM699404
	/**
	 * Worker Code (preparatory)
	 * @return	Worker Code (preparatory)
	 */
	public String getWorkerCodeB()
	{
		return wWorkerCodeB;
	}

	//#CM699405
	/**
	 * Person Name or Worker Name
	 * @return	Person Name or Worker Name
	 */
	public String getWorkerName()
	{
		return wWorkerName;
	}

	//#CM699406
	/**
	 * Work Quantity
	 * @return	Work Quantity
	 */
	public int getWorkQty()
	{
		return wWorkQty;
	}

	//#CM699407
	/**
	 * Work Quantity/h
	 * @return	Work Quantity/h
	 */
	public long getWorkQtyPerHour()
	{
		return wWorkQtyPerHour;
	}

	//#CM699408
	/**
	 * Start Time
	 * @return	Start Time
	 */
	public String getWorkStartTime()
	{
		return wWorkStartTime;
	}

	//#CM699409
	/**
	 * Work Time
	 * @return	Work Time
	 */
	public String getWorkTime()
	{
		return wWorkTime;
	}

	//#CM699410
	/**
	 * Last update process name
	 * @return	Last update process name
	 */
	public String getLastUpdatePName()
	{
		return wLastUpdatePName;
	}

	//#CM699411
	/**
	 * Name of Add Process
	 * @return	Name of Add Process
	 */
	public String getRegistPName()
	{
		return wRegistPName;
	}

	//#CM699412
	/**
	 * Sent/Received
	 * @param arg	Sent/Received
	 */
	public void setCommunication(String arg)
	{
		wCommunication = arg;
	}

	//#CM699413
	/**
	 * Communication Statement
	 * @param arg	Communication Statement
	 */
	public void setCommunicationMessage(String arg)
	{
		wCommunicationMessage = arg;
	}

	//#CM699414
	/**
	 * Content
	 * @param arg	Content
	 */
	public void setError(String arg)
	{
		wError = arg;
	}

	//#CM699415
	/**
	 * Class where error occurred.
	 * @param arg	Class where error occurred.
	 */
	public void setErrorClass(String arg)
	{
		wErrorClass = arg;
	}

	//#CM699416
	/**
	 * Receiving plan data file name
	 * @param arg	Receiving plan data file name
	 */
	public void setFileName_LoadInstockData(String arg)
	{
		wFileName_LoadInstockData = arg;
	}

	//#CM699417
	/**
	 *  Sorting plan data file name
	 * @param arg	 Sorting plan data file name
	 */
	public void setFileName_LoadSortingData(String arg)
	{
		wFileName_LoadSortingData = arg;
	}

	//#CM699418
	/**
	 * Picking plan data file name
	 * @param arg	Picking plan data file name
	 */
	public void setFileName_LoadRetrievalData(String arg)
	{
		wFileName_LoadRetrievalData = arg;
	}

	//#CM699419
	/**
	 * Shipping plan data file name
	 * @param arg	Shipping plan data file name
	 */
	public void setFileName_LoadShippingData(String arg)
	{
		wFileName_LoadShippingData = arg;
	}

	//#CM699420
	/**
	 * Storage plan data file name
	 * @param arg	Storage plan data file name
	 */
	public void setFileName_LoadStorageData(String arg)
	{
		wFileName_LoadStorageData = arg;
	}

	//#CM699421
	/**
	 * Receiving result data file name
	 * @param arg	Receiving result data file name
	 */
	public void setFileName_ReportInstockData(String arg)
	{
		wFileName_ReportInstockData = arg;
	}

	//#CM699422
	/**
	 * Inventory Check result data file name
	 * @param arg	Inventory Check result data file name
	 */
	public void setFileName_ReportInventoryData(String arg)
	{
		wFileName_ReportInventoryData = arg;
	}

	//#CM699423
	/**
	 * Stock relocation result data file name
	 * @param arg	Stock relocation result data file name
	 */
	public void setFileName_ReportMovementData(String arg)
	{
		wFileName_ReportMovementData = arg;
	}

	//#CM699424
	/**
	 * Sorting result data file name
	 * @param arg	Sorting result data file name
	 */
	public void setFileName_ReportSortingData(String arg)
	{
		wFileName_ReportSortingData = arg;
	}

	//#CM699425
	/**
	 * Picking result data file name
	 * @param arg	Picking result data file name
	 */
	public void setFileName_ReportRetrievalData(String arg)
	{
		wFileName_ReportRetrievalData = arg;
	}

	//#CM699426
	/**
	 * Shipping result data file name
	 * @param arg	Shipping result data file name
	 */
	public void setFileName_ReportShippingData(String arg)
	{
		wFileName_ReportShippingData = arg;
	}

	//#CM699427
	/**
	 * Storage result data file name
	 * @param arg	Storage result data file name
	 */
	public void setFileName_ReportStorageData(String arg)
	{
		wFileName_ReportStorageData = arg;
	}

	//#CM699428
	/**
	 * Inventory information data file name
	 * @param arg	Inventory information data file name
	 */
	public void setFileName_ReportDataStock(String arg)
	{
		wFileName_ReportDataStock = arg;
	}

	//#CM699429
	/**
	 * Unplanned storage result data file name
	 * @param arg	Unplanned storage result data file name
	 */
	public void setFileName_ReportNoPlanStorageData(String arg)
	{
		wFileName_ReportNoPlanStorageData = arg;
	}

	//#CM699430
	/**
	 * Unplanned picking result data file name
	 * @param arg	Unplanned picking result data file name
	 */
	public void setFileName_ReportNoPlanRetrievalData(String arg)
	{
		wFileName_ReportNoPlanRetrievalData = arg;
	}

	//#CM699431
	/**
	 * Consignor master data file name
	 * @param arg	Consignor master data file name
	 */
	public void setFileName_LoadMasterConsignorData(String arg)
	{
	    wFileName_LoadMasterConsignorData = arg;
	}

	//#CM699432
	/**
	 * Supplier master data file name
	 * @param arg	Supplier master data file name
	 */
	public void setFileName_LoadMasterSupplierData(String arg)
	{
	    wFileName_LoadMasterSupplierData = arg;
	}

	//#CM699433
	/**
	 * Customer master data file name
	 * @param arg	Customer master data file name
	 */
	public void setFileName_LoadMasterCustomerData(String arg)
	{
	    wFileName_LoadMasterCustomerData = arg;
	}

	//#CM699434
	/**
	 * Item master data file name
	 * @param arg	Item master data file name
	 */
	public void setFileName_LoadMasterItemData(String arg)
	{
	    wFileName_LoadMasterItemData = arg;
	}

	//#CM699435
	/**
	 * Receiving plan data storage folder
	 * @param arg	Receiving plan data storage folder
	 */
	public void setFolder_LoadInstockData(String arg)
	{
		wFolder_LoadInstockData = arg;
	}

	//#CM699436
	/**
	 * Sorting plan data storage folder
	 * @param arg	Sorting plan data storage folder
	 */
	public void setFolder_LoadSortingData(String arg)
	{
		wFolder_LoadSortingData = arg;
	}

	//#CM699437
	/**
	 * Picking plan data storage folder
	 * @param arg	Picking plan data storage folder
	 */
	public void setFolder_LoadRetrievalData(String arg)
	{
		wFolder_LoadRetrievalData = arg;
	}

	//#CM699438
	/**
	 * Shipping plan data storage folder
	 * @param arg	Shipping plan data storage folder
	 */
	public void setFolder_LoadShippingData(String arg)
	{
		wFolder_LoadShippingData = arg;
	}

	//#CM699439
	/**
	 * Storage plan data storage folder
	 * @param arg	Storage plan data storage folder
	 */
	public void setFolder_LoadStorageData(String arg)
	{
		wFolder_LoadStorageData = arg;
	}

	//#CM699440
	/**
	 * Receiving result data storage folder
	 * @param arg	Receiving result data storage folder
	 */
	public void setFolder_ReportInstockData(String arg)
	{
		wFolder_ReportInstockData = arg;
	}

	//#CM699441
	/**
	 * Inventory Check result data storage folder
	 * @param arg	Inventory Check result data storage folder
	 */
	public void setFolder_ReportInventoryData(String arg)
	{
		wFolder_ReportInventoryData = arg;
	}

	//#CM699442
	/**
	 * Stock relocation result data storage folder
	 * @param arg	Stock relocation result data storage folder
	 */
	public void setFolder_ReportMovementData(String arg)
	{
		wFolder_ReportMovementData = arg;
	}

	//#CM699443
	/**
	 * Sorting result data storage folder
	 * @param arg	Sorting result data storage folder
	 */
	public void setFolder_ReportSortingData(String arg)
	{
		wFolder_ReportSortingData = arg;
	}

	//#CM699444
	/**
	 * Picking result data storage folder
	 * @param arg	Picking result data storage folder
	 */
	public void setFolder_ReportRetrievalData(String arg)
	{
		wFolder_ReportRetrievalData = arg;
	}

	//#CM699445
	/**
	 * Shipping result data storage folder
	 * @param arg	Shipping result data storage folder
	 */
	public void setFolder_ReportShippingData(String arg)
	{
		wFolder_ReportShippingData = arg;
	}

	//#CM699446
	/**
	 * Storage result data storage folder
	 * @param arg	Storage result data storage folder
	 */
	public void setFolder_ReportStorageData(String arg)
	{
		wFolder_ReportStorageData = arg;
	}

	//#CM699447
	/**
	 * Inventory information data storage folder
	 * @param arg	Inventory information data storage folder
	 */
	public void setFolder_ReportDataStock(String arg)
	{
		wFolder_ReportDataStock = arg;
	}

	//#CM699448
	/**
	 * Unplanned storage result data storage folder
	 * @param arg	Unplanned storage result data storage folder
	 */
	public void setFolder_ReportNoPlanStorageData(String arg)
	{
		wFolder_ReportNoPlanStorageData = arg;
	}

	//#CM699449
	/**
	 * Unplanned picking result data storage folder
	 * @param arg	Unplanned picking result data storage folder
	 */
	public void setFolder_ReportNoPlanRetrievalData(String arg)
	{
		wFolder_ReportNoPlanRetrievalData = arg;
	}

	//#CM699450
	/**
	 * Consignor master data storage folder
	 * @param arg	Consignor master data storage folder
	 */
	public void setFolder_LoadMasterConsignorData(String arg)
	{
	    wFolder_LoadMasterConsignorData = arg;
	}

	//#CM699451
	/**
	 * Supplier master data storage folder
	 * @param arg	Supplier master data storage folder
	 */
	public void setFolder_LoadMasterSupplierData(String arg)
	{
	    wFolder_LoadMasterSupplierData = arg;
	}

	//#CM699452
	/**
	 * Customer master data storage folder
	 * @param arg	Customer master data storage folder
	 */
	public void setFolder_LoadMasterCustomerData(String arg)
	{
	    wFolder_LoadMasterCustomerData = arg;
	}

	//#CM699453
	/**
	 * Item master data storage folder
	 * @param arg	Item master data storage folder
	 */
	public void setFolder_LoadMasterItemjData(String arg)
	{
	    wFolder_LoadMasterItemjData = arg;
	}

	//#CM699454
	/**
	 * Starting date/time of searching (date)
	 * @param arg	Starting date/time of searching (date)
	 */
	public void setFromFindDate(String arg)
	{
		wFromFindDate = arg;
	}

	//#CM699455
	/**
	 * Starting date/time of searching (time)
	 * @param arg	Starting date/time of searching (time)
	 */
	public void setFromFindTime(String arg)
	{
		wFromFindTime = arg;
	}

	//#CM699456
	/**
	 * Start work date
	 * @param arg	Start work date
	 */
	public void setFromWorkDate(String arg)
	{
		wFromWorkDate = arg;
	}

	//#CM699457
	/**
	 * Phonetic transcriptions in kana
	 * @param arg	Phonetic transcriptions in kana
	 */
	public void setFurigana(String arg)
	{
		wFurigana = arg;
	}

	//#CM699458
	/**
	 * Last Update Date
	 * @param arg	Last Update Date
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	//#CM699459
	/**
	 * Last Update Date (string type)
	 * @param arg	Last update date in string type
	 */
	public void setWLastUpdateDateString(String arg)
	{
		wLastUpdateDateString = arg;
	}

	//#CM699460
	/**
	 * Memo 1
	 * @param arg	Memo 1
	 */
	public void setMemo1(String arg)
	{
		wMemo1 = arg;
	}

	//#CM699461
	/**
	 * Memo 2
	 * @param arg	Memo 2
	 */
	public void setMemo2(String arg)
	{
		wMemo2 = arg;
	}

	//#CM699462
	/**
	 * Message
	 * @param arg	Message
	 */
	public void setMessage(String arg)
	{
		wMessage = arg;
	}

	//#CM699463
	/**
	 * Date/Time
	 * @param arg	Date/Time
	 */
	public void setMessageLogDate(String arg)
	{
		wMessageLogDate = arg;
	}

	//#CM699464
	/**
	 * Password
	 * @param arg	Password
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	//#CM699465
	/**
	 * Planned Date or Planned Work Date
	 * @param arg	Planned Date or Planned Work Date
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	//#CM699466
	/**
	 * Added date
	 * @param arg	Added date
	 */
	public void setRegistDate(java.util.Date arg)
	{
		wRegistDate = arg;
	}

	//#CM699467
	/**
	 * RFT status.
	 * @param arg	RFT status.
	 */
	public void setRftStatus(String arg)
	{
		wRftStatus = arg;
	}

	//#CM699468
	/**
	 * Work division
	 * @param arg	Work division
	 */
	public void setJobType(String arg)
	{
		wJobType = arg;
	}

	//#CM699469
	/**
	 * Access Privileges
	 * @param arg	Access Privileges
	 */
	public void setSelectAccessAuthority(String arg)
	{
		wSelectAccessAuthority = arg;
	}

	//#CM699470
	/**
	 * Aggregation Conditions
	 * @param arg	Aggregation Conditions
	 */
	public void setSelectAggregateCondition(String arg)
	{
		wSelectAggregateCondition = arg;
	}

	//#CM699471
	/**
	 * Setting of field item for loading data
	 * @param arg	Setting of field item for loading data
	 */
	public void setSelectDefineLoadData(String arg)
	{
		wSelectDefineLoadData = arg;
	}

	//#CM699472
	/**
	 * Setting for reporting data
	 * @param arg	Setting for reporting data
	 */
	public void setSelectDefineReportData(String arg)
	{
		wSelectDefineReportData = arg;
	}

	//#CM699473
	/**
	 * Display condition (Communication Trace Log)
	 * @param arg	Display condition (Communication Trace Log)
	 */
	public void setSelectDisplayCondition_CommunicationLog(String arg)
	{
		wSelectDisplayCondition_CommunicationLog = arg;
	}

	//#CM699474
	/**
	 * Display condition (Message log)
	 * @param arg	Display condition (Message log)
	 */
	public void setSelectDisplayCondition_MessageLog(String arg)
	{
		wSelectDisplayCondition_MessageLog = arg;
	}

	//#CM699475
	/**
	 * Job Title
	 * @param arg	Job Title
	 */
	public void setSelectWorkerJobType(String arg)
	{
		wSelectWorkerJobType = arg;
	}

	//#CM699476
	/**
	 * Receiving plan data
	 * @param arg	Receiving plan data
	 */
	public void setSelectLoadInstockData(boolean arg)
	{
		wSelectLoadInstockData = arg;
	}

	//#CM699477
	/**
	 * Sorting Plan data
	 * @param arg	Sorting Plan data
	 */
	public void setSelectLoadSortingData(boolean arg)
	{
		wSelectLoadSortingData = arg;
	}

	//#CM699478
	/**
	 * Picking plan data
	 * @param arg	Picking plan data
	 */
	public void setSelectLoadRetrievalData(boolean arg)
	{
		wSelectLoadRetrievalData = arg;
	}

	//#CM699479
	/**
	 * Shipping plan data
	 * @param arg	Shipping plan data
	 */
	public void setSelectLoadShippingData(boolean arg)
	{
		wSelectLoadShippingData = arg;
	}

	//#CM699480
	/**
	 * Consignor master data
	 * @param arg	Consignor master data
	 */
	public void setSelectLoadConsignorMasterData(boolean arg)
	{
	    wSelectLoadConsignorMasterData = arg;
	}

	//#CM699481
	/**
	 * Supplier master data
	 * @param arg	Supplier master data
	 */
	public void setSelectLoadSupplierMasterData(boolean arg)
	{
	    wSelectLoadSupplierMasterData = arg;
	}

	//#CM699482
	/**
	 * Customer master data
	 * @param arg	Customer master data
	 */
	public void setSelectLoadCustomerMasterData(boolean arg)
	{
	    wSelectLoadCustomerMasterData = arg;
	}

	//#CM699483
	/**
	 * Item master data
	 * @param arg	Item master data
	 */
	public void setSelectLoadItemMasterData(boolean arg)
	{
	    wSelectLoadItemMasterData = arg;
	}

	//#CM699484
	/**
	 * Storage plan data
	 * @param arg	Storage plan data
	 */
	public void setSelectLoadStorageData(boolean arg)
	{
		wSelectLoadStorageData = arg;
	}

	//#CM699485
	/**
	 * Decide the Storage Location automatically.
	 * @param arg	Decide the Storage Location automatically.
	 */
	public void setSelectLoadIdmAuto(boolean arg)
	{
		wSelectLoadIdmAuto = arg;
	}

	//#CM699486
	/**
	 * Receiving Result_Selected
	 * @param arg	Receiving Result_Selected
	 */
	public void setSelectReportInstockData(boolean arg)
	{
		wSelectReportInstockData = arg;
	}

	//#CM699487
	/**
	 * Report the Receiving Result_Work in Process.
	 * @param arg	Report the Receiving Result_Work in Process.
	 */
	public void setSelectReportInstockData_InProgress(boolean arg)
	{
		wSelectReportInstockData_InProgress = arg;
	}

	//#CM699488
	/**
	 * Report the Receiving Result_Not Worked.
	 * @param arg	Report the Receiving Result_Not Worked.
	 */
	public void setSelectReportInstockData_Unworking(boolean arg)
	{
		wSelectReportInstockData_Unworking = arg;
	}

	//#CM699489
	/**
	 * Inventory Check result data
	 * @param arg	Inventory Check result data
	 */
	public void setSelectReportInventoryData(boolean arg)
	{
		wSelectReportInventoryData = arg;
	}

	//#CM699490
	/**
	 * Stock relocation result data
	 * @param arg	Stock relocation result data
	 */
	public void setSelectReportMovementData(boolean arg)
	{
		wSelectReportMovementData = arg;
	}

	//#CM699491
	/**
	 * Unplanned storage result data
	 * @param arg	Unplanned storage result data
	 */
	public void setSelectReportNoPlanStorageData(boolean arg)
	{
		wSelectReportNoPlanStorageData = arg;
	}

	//#CM699492
	/**
	 * Unplanned picking result data
	 * @param arg	Unplanned picking result data
	 */
	public void setSelectReportNoPlanRetrievalData(boolean arg)
	{
		wSelectReportNoPlanRetrievalData = arg;
	}

	//#CM699493
	/**
	 * Sorting Result_Selected
	 * @param arg	Sorting Result_Selected
	 */
	public void setSelectReportSortingData(boolean arg)
	{
		wSelectReportSortingData = arg;
	}

	//#CM699494
	/**
	 * Report the Sorting Result_Work in Process.
	 * @param arg	Report the Sorting Result_Work in Process.
	 */
	public void setSelectReportSortingData_InProgress(boolean arg)
	{
		wSelectReportSortingData_InProgress = arg;
	}

	//#CM699495
	/**
	 * Report the Sorting Result_Not Worked.
	 * @param arg	Report the Sorting Result_Not Worked.
	 */
	public void setSelectReportSortingData_Unworking(boolean arg)
	{
		wSelectReportSortingData_Unworking = arg;
	}

	//#CM699496
	/**
	 * Picking Result_Selected
	 * @param arg	Picking Result_Selected
	 */
	public void setSelectReportRetrievalData(boolean arg)
	{
		wSelectReportRetrievalData = arg;
	}

	//#CM699497
	/**
	 * Report the Picking Result_Work in Process.
	 * @param arg	Report the Picking Result_Work in Process.
	 */
	public void setSelectReportRetrievalData_InProgress(boolean arg)
	{
		wSelectReportRetrievalData_InProgress = arg;
	}

	//#CM699498
	/**
	 * Report the Picking Result_Not Worked.
	 * @param arg	Report the Picking Result_Not Worked.
	 */
	public void setSelectReportRetrievalData_Unworking(boolean arg)
	{
		wSelectReportRetrievalData_Unworking = arg;
	}

	//#CM699499
	/**
	 * Shipping Result_Selected
	 * @param arg	Shipping Result_Selected
	 */
	public void setSelectReportShippingData(boolean arg)
	{
		wSelectReportShippingData = arg;
	}

	//#CM699500
	/**
	 * Report the Shipping Result_Work in Process.
	 * @param arg	Report the Shipping Result_Work in Process.
	 */
	public void setSelectReportShippingData_InProgress(boolean arg)
	{
		wSelectReportShippingData_InProgress = arg;
	}

	//#CM699501
	/**
	 * Report the Shipping Result_Not Worked.
	 * @param arg	Report the Shipping Result_Not Worked.
	 */
	public void setSelectReportShippingData_Unworking(boolean arg)
	{
		wSelectReportShippingData_Unworking = arg;
	}

	//#CM699502
	/**
	 * Storage Result_Selected
	 * @param arg	Storage Result_Selected
	 */
	public void setSelectReportStorageData(boolean arg)
	{
		wSelectReportStorageData = arg;
	}

	//#CM699503
	/**
	 * Report the Storage Result_Work in Process.
	 * @param arg	Report the Storage Result_Work in Process.
	 */
	public void setSelectReportStorageData_InProgress(boolean arg)
	{
		wSelectReportStorageData_InProgress = arg;
	}

	//#CM699504
	/**
	 * Report the Storage Result_Not Worked.
	 * @param arg	Report the Storage Result_Not Worked.
	 */
	public void setSelectReportStorageData_Unworking(boolean arg)
	{
		wSelectReportStorageData_Unworking = arg;
	}

	//#CM699505
	/**
	 * inventory information_Selected
	 * @param arg	inventory information_Selected
	 */
	public void setSelectReportDataStock(boolean arg)
	{
		wSelectReportDataStock = arg;
	}

	//#CM699506
	/**
	 * Sex
	 * @param arg	Sex
	 */
	public void setSelectSex(String arg)
	{
		wSelectSex = arg;
	}

	//#CM699507
	/**
	 * Add ON/OFF Status
	 * @param arg	Add ON/OFF Status
	 */
	public void setSelectStatus(String arg)
	{
		wSelectStatus = arg;
	}

	//#CM699508
	/**
	 * Status "Not Worked"
	 * @param arg	Status "Not Worked"
	 */
	public void setSelectUnworkingInformation(String arg)
	{
		wSelectUnworkingInformation = arg;
	}

	//#CM699509
	/**
	 * Work type
	 * @param arg	Work type
	 */
	public void setSelectWorkDetail(String arg)
	{
		wSelectWorkDetail = arg;
	}

	//#CM699510
	/**
	 * RFT Machine No..
	 * @param arg	RFT Machine No..
	 */
	public void setRftNo(String arg)
	{
		wRftNo = arg;
	}

	//#CM699511
	/**
	 * End date/time of searching (date)
	 * @param arg	End date/time of searching (date)
	 */
	public void setToFindDate(String arg)
	{
		wToFindDate = arg;
	}

	//#CM699512
	/**
	 * End date/time of searching (time)
	 * @param arg	End date/time of searching (time)
	 */
	public void setToFindTime(String arg)
	{
		wToFindTime = arg;
	}

	//#CM699513
	/**
	 * End Work Date
	 * @param arg	End Work Date
	 */
	public void setToWorkDate(String arg)
	{
		wToWorkDate = arg;
	}

	//#CM699514
	/**
	 * Work Count
	 * @param arg	Work Count
	 */
	public void setWorkCnt(int arg)
	{
		wWorkCnt = arg;
	}

	//#CM699515
	/**
	 * Work Count/h
	 * @param arg	Work Count/h
	 */
	public void setWorkCntPerHour(long arg)
	{
		wWorkCntPerHour = arg;
	}

	//#CM699516
	/**
	 * Work date
	 * @param arg	Work date
	 */
	public void setWorkDate(String arg)
	{
		wWorkDate = arg;
	}

	//#CM699517
	/**
	 * End Time
	 * @param arg	End Time
	 */
	public void setWorkEndTime(String arg)
	{
		wWorkEndTime = arg;
	}

	//#CM699518
	/**
	 * Worker Code
	 * @param arg	Worker Code
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	//#CM699519
	/**
	 * Worker Code (preparatory)
	 * @param arg	Worker Code (preparatory)
	 */
	public void setWorkerCodeB(String arg)
	{
		wWorkerCodeB = arg;
	}

	//#CM699520
	/**
	 * Person Name or Worker Name
	 * @param arg	Person Name or Worker Name
	 */
	public void setWorkerName(String arg)
	{
		wWorkerName = arg;
	}

	//#CM699521
	/**
	 * Work Quantity
	 * @param arg	Work Quantity
	 */
	public void setWorkQty(int arg)
	{
		wWorkQty = arg;
	}

	//#CM699522
	/**
	 * Work Quantity/h
	 * @param arg	Work Quantity/h
	 */
	public void setWorkQtyPerHour(long arg)
	{
		wWorkQtyPerHour = arg;
	}

	//#CM699523
	/**
	 * Start Time
	 * @param arg	Start Time
	 */
	public void setWorkStartTime(String arg)
	{
		wWorkStartTime = arg;
	}

	//#CM699524
	/**
	 * Work Time
	 * @param arg	Work Time
	 */
	public void setWorkTime(String arg)
	{
		wWorkTime = arg;
	}

	//#CM699525
	/**
	 * Last update process name
	 * @param arg	Last update process name
	 */
	public void setLastUpdatePName(String arg)
	{
		wLastUpdatePName = arg;
	}

	//#CM699526
	/**
	 * Name of Add Process
	 * @param arg	Name of Add Process
	 */
	public void setRegistPName(String arg)
	{
		wRegistPName = arg;
	}

	//#CM699527
	/**
	 * Set the Consignor code.
	 * @param arg	Consignor Code
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	//#CM699528
	/**
	 * Obtain the Consignor code.
	 * @return	Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM699529
	/**
	 * Set the Unit of View.
	 * @param arg	Unit of View
	 */
	public void setDispType(String arg)
	{
		wDispType = arg;
	}

	//#CM699530
	/**
	 * Obtain the unit of View.
	 * @return	Unit of View
	 */
	public String getDispType()
	{
		return wDispType;
	}

	//#CM699531
	/**
	 * Set the Consignor name.
	 * @param arg	Consignor Name
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg;
	}

	//#CM699532
	/**
	 * Obtain the Consignor Name.
	 * @return	Consignor Name
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	//#CM699533
	/**
	 * Set the item code.
	 * @param arg	Item Code
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg;
	}

	//#CM699534
	/**
	 * Obtain the item code.
	 * @return	Item Code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM699535
	/**
	 * Set the item name.
	 * @param arg	Item Name
	 */
	public void setItemName(String arg)
	{
		wItemName = arg;
	}

	//#CM699536
	/**
	 * Obtain the item name.
	 * @return	Item Name
	 */
	public String getItemName()
	{
		return wItemName;
	}

	//#CM699537
	/**
	 * Set the Packed qty per case.
	 * @param arg	Packed Qty per Case
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg;
	}

	//#CM699538
	/**
	 * Obtain the Packed qty per case.
	 * @return	Packed Qty per Case
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	//#CM699539
	/**
	 * Set the Packed qty per bundle.
	 * @param arg	Packed qty per bundle
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg;
	}

	//#CM699540
	/**
	 * Obtain the Packed qty per bundle.
	 * @return	Packed qty per bundle
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	//#CM699541
	/**
	 * Set the planned packed qty per case.
	 * @param arg	Planned packed qty per case
	 */
	public void setPlanCaseQty(int arg)
	{
		wPlanCaseQty = arg;
	}

	//#CM699542
	/**
	 * Obtain the Planned pcked qty per case.
	 * @return	Planned packed qty per case
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	//#CM699543
	/**
	 * Set the Planned packed qty per piece .
	 * @param arg	Planned packed qty per piece
	 */
	public void setPlanPieceQty(int arg)
	{
		wPlanPieceQty = arg;
	}

	//#CM699544
	/**
	 * Obtain the Planned packed qty per piece .
	 * @return	Planned packed qty per piece
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	//#CM699545
	/**
	 * Set the Allocatable Packed Qty per Case.
	 * @param arg	Allocatable Packed Qty per Case
	 */
	public void setEnableCaseQty(int arg)
	{
		wEnableCaseQty = arg;
	}

	//#CM699546
	/**
	 * Obtain the Allocatable Packed Qty per Case.
	 * @return	Allocatable Packed Qty per Case
	 */
	public int getEnableCaseQty()
	{
		return wEnableCaseQty;
	}

	//#CM699547
	/**
	 * Set the Allocatable Packed Qty per Piece.
	 * @param arg	Allocatable Packed Qty per Piece
	 */
	public void setEnablePieceQty(int arg)
	{
		wEnablePieceQty = arg;
	}

	//#CM699548
	/**
	 * Obtain the Allocatable Packed Qty per Piece.
	 * @return	Allocatable Packed Qty per Piece
	 */
	public int getEnablePieceQty()
	{
		return wEnablePieceQty;
	}

	//#CM699549
	/**
	 * Set the Shortage Packed Qty per Case.
	 * @param arg	Shortage Packed Qty per Case
	 */
	public void setShortageCaseQty(int arg)
	{
		wShortageCaseQty = arg;
	}

	//#CM699550
	/**
	 * Obtain the Shortage Packed Qty per Case.
	 * @return	Shortage Packed Qty per Case
	 */
	public int getShortageCaseQty()
	{
		return wShortageCaseQty;
	}

	//#CM699551
	/**
	 * Set the shortage packed qty per piece.
	 * @param arg	Shortage packed qty per piece
	 */
	public void setShortagePieceQty(int arg)
	{
		wShortagePieceQty = arg;
	}

	//#CM699552
	/**
	 * Obtain the shortage packed qty per piece.
	 * @return	Shortage packed qty per piece
	 */
	public int getShortagePieceQty()
	{
		return wShortagePieceQty;
	}

	//#CM699553
	/**
	 * Set the Case Picking Location.
	 * @param arg	Case Picking Location
	 */
	public void setCaseLocation(String arg)
	{
		wCaseLocation = arg;
	}

	//#CM699554
	/**
	 * Obtain the Case Picking Location.
	 * @return	Case Picking Location
	 */
	public String getCaseLocation()
	{
		return wCaseLocation;
	}

	//#CM699555
	/**
	 * Set the Piece Picking Location.
	 * @param arg	Piece Picking Location
	 */
	public void setPieceLocation(String arg)
	{
		wPieceLocation = arg;
	}

	//#CM699556
	/**
	 * Obtain the Piece Picking Location.
	 * @return	Piece Picking Location
	 */
	public String getPieceLocation()
	{
		return wPieceLocation;
	}

	//#CM699557
	/**
	 * Set the Case Order No.
	 * @param arg	Case Order No.
	 */
	public void setCaseOrderNo(String arg)
	{
		wCaseOrderNo = arg;
	}

	//#CM699558
	/**
	 * Obtain the Case Order No.
	 * @return	Case Order No.
	 */
	public String getCaseOrderNo()
	{
		return wCaseOrderNo;
	}

	//#CM699559
	/**
	 * Set the Piece Order No.
	 * @param arg	Piece Order No.
	 */
	public void setPieceOrderNo(String arg)
	{
		wPieceOrderNo = arg;
	}

	//#CM699560
	/**
	 * Obtain the Piece Order No.
	 * @return	Piece Order No.
	 */
	public String getPieceOrderNo()
	{
		return wPieceOrderNo;
	}

	//#CM699561
	/**
	 * Set the customer name.
	 * @param arg	Customer Name
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	//#CM699562
	/**
	 * Obtain the customer name.
	 * @return	Customer Name
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	//#CM699563
	/**
	 * Set the Customer Ticket No.
	 * @param arg	Customer Ticket No.
	 */
	public void setShippingTicketNo(String arg)
	{
		wShippingTicketNo = arg;
	}

	//#CM699564
	/**
	 * Obtain the Customer Ticket No.
	 * @return	Customer Ticket No.
	 */
	public String getShippingTicketNo()
	{
		return wShippingTicketNo;
	}

	//#CM699565
	/**
	 * Set the Customer Ticket Line No.
	 * @param arg	Customer Ticket Line No.
	 */
	public void setShippingTicketLineNo(int arg)
	{
		wShippingTicketLineNo = arg;
	}

	//#CM699566
	/**
	 * Obtain the Customer Ticket Line No.
	 * @return	Customer Ticket Line No.
	 */
	public int getShippingTicketLineNo()
	{
		return wShippingTicketLineNo;
	}

	//#CM699567
	/**
	 * Set the Added Date/Time (Array).
	 * @param arg	Added Date/Time (Array)
	 */
	public void setRegistDateArray(java.util.Date[] arg)
	{
		wRegistDateArray = arg;
	}

	//#CM699568
	/**
	 * Obtain the Added Date/Time (Array).
	 * @return	Added Date/Time (Array)
	 */
	public java.util.Date[] getRegistDateArray()
	{
		return wRegistDateArray;
	}

	//#CM699569
	/**
	 * Obtain the Terminal Type.
	 * @return	Terminal Type
	 */
	public String getTerminalType()
	{
		return wTerminalType;
	}
	//#CM699570
	/**
	 * Set the Terminal Type.
	 * @param terminalType	Terminal Type
	 */
	public void setTerminalType(String terminalType)
	{
		wTerminalType = terminalType;
	}
	//#CM699571
	/**
	 * Obtain the Worker Code to be displayed.
	 *
	 * @return	Worker Code to be displayed
	 */
	public String getDisplayWorkerCode() {
		return wDisplayWorkerCode;
	}
	//#CM699572
	/**
	 * Set the Worker Code to be displayed.
	 * @param displayWorkerCode		Worker Code to be displayed
	 */
	public void setDisplayWorkerCode(String displayWorkerCode) {
		wDisplayWorkerCode = displayWorkerCode;
	}
	//#CM699573
	/**
	 * Obtain the selected value for shortage qty.
	 * @return 1: Pending/Additional delivery, 2: Shortage
	 */
	public String getLackAmount()
	{
		return wLackAmount;
	}
	//#CM699574
	/**
	 * Obtain the value of the shortage qty.
	 * @param lackAmount 1: Pending/Additional delivery, 2: Shortage
	 */
	public void setLackAmount(String lackAmount)
	{
		wLackAmount = lackAmount;
	}

	//#CM699575
	/**
	 * Obtain the selected value of data with work processing.
	 * @return	1: Cancel, 2: Submit
	 */
	public String getWorkOnTheWay()
	{
		return wWorkOnTheWay;
	}

	//#CM699576
	/**
	 * Set a value of work in progress.
	 * @param workOnTheWay 1: Cancel, 2: Submit
	 */
	public void setWorkOnTheWay(String workOnTheWay)
	{
		wWorkOnTheWay = workOnTheWay;
	}
}
//#CM699577
//end of class
