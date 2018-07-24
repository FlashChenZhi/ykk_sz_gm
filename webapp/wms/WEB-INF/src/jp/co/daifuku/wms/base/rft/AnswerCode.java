// $Id: AnswerCode.java,v 1.2 2006/11/14 06:08:55 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700021
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM700022
/**
 * Define the value of the response flag in the response message of the RFT communication. <BR>
 * However, a different value according to the message might be used, and, in that case, 
 * the definition is overwritten in each message mounting class. <BR>
 * Therefore, do not refer directly to the value of this Interface as long as there are no special
 *  circumstances, and refer as a member of the mounting class. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:55 $
 * @author  $Author: suresh $
 */
public interface AnswerCode
{
	//#CM700023
	// Class fields --------------------------------------------------
	//#CM700024
	/**
	 * Field which shows normal response flag
	 */
	public static final String ANS_CODE_NORMAL = "0";

	//#CM700025
	/**
	 * Field shown while working in the other end of response flag
	 */
	public static final String ANS_CODE_WORKING = "1";

	//#CM700026
	/**
	 * Field which shows work completion ending of response flag
	 *  (For response od requested telegram) 
	 */
	public static final String ANS_CODE_COMPLETION = "2";
	//#CM700027
	/**
	 * Field which shows error updated in the other end
	 *  (For response of results transmission telegram) 
	 */
	public static final String ANS_CODE_UPDATE_FINISH = "9";

	//#CM700028
	/**
	 * Field shown while processing update at date
	 */
	public static final String ANS_CODE_DAILY_UPDATING = "5";

	//#CM700029
	/**
	 * Field shown while maintaining it
	 */
	public static final String ANS_CODE_MAINTENANCE = "6";

	//#CM700030
	/**
	 * Field which shows two or more response flag correspondence
	 */
	public static final String ANS_CODE_SOME_DATA = "7";

	//#CM700031
	/**
	 * Field which shows pertinent data none of response flag
	 */
	public static final String ANS_CODE_NULL = "8";

	//#CM700032
	/**
	 * Field which shows error of response flag
	 */
	public static final String ANS_CODE_ERROR = "9";

	//#CM700033
	/**
	 * Field where response flag shows of stock and exist
	 */
	public static final String ANS_CODE_STOCK_OK = "3";

	//#CM700034
	/**
	 * Field which shows that there is data file while working
	 */
	public static final String ANS_CODE_WORKING_DATAFILE_EXISTS = "7";
	
	 
	
	//#CM700035
	/**
	 * Error details interface
	 */
	public interface ErrorDetails
	{
		//#CM700036
		/**
		 * Field which shows detailed error
		 */
		public static final String NORMAL = "00";

		//#CM700037
		/**
		 * Field which shows detailed stock qty overflow error(OverflowException)
		 */
		public static final String OVERFLOW = "10";
		
		//#CM700038
		/**
		 * Field which shows detailed input location invalidity error(ShelfInvalidityException)
		 */
		public static final String SHELF_INVALIDITY = "11";
		
		//#CM700039
		/**
		 * Field which shows object data details not found error(NotFoundException)
		 */
		public static final String NULL = "20";
		
		//#CM700040
		/**
		 * Field which shows error that updated in the other terminal(UpdateByOtherTerminalException)
		 */
		public static final String UPDATE_FINISH = "21";
		
		//#CM700041
		/**
		 * Field which shows detailed internal error(Exception)
		 */
		public static final String INTERNAL_ERROR = "30";
		
		//#CM700042
		/**
		 * Field which shows parameter error(InvalidDefineException)
		 */
		public static final String PARAMETER_ERROR = "31";
		
		//#CM700043
		/**
		 * Field which shows detailed DB access error(ReadWriteException,SQLException)
		 */
		public static final String DB_ACCESS_ERROR = "32";
		
		//#CM700044
		/**
		 * Field which shows DB unique Key violation error(DataExistsException)
		 */
		public static final String DB_UNIQUE_KEY_ERROR = "33";
		
		//#CM700045
		/**
		 * Field which shows detailed class generation error(IllegalAccessException)
		 */
		public static final String INSTACIATE_ERROR = "34";
		
		//#CM700046
		/**
		 * Field which shows detailed file I/O error(IOException)
		 */
		public static final String I_O_ERROR = "35";
		
		//#CM700047
		/**
		 * Field which shows detailed illegal DB setting error(InvalidStatusException)
		 */
		public static final String DB_INVALID_VALUE = "36";

		//#CM700048
		/**
		 * Field which shows error of detailed common logic(ScheduleException)
		 */
		public static final String SCHEDULE_ERROR = "37";
		
		//#CM700049
		/**
		 * Field which shows detailed consolidating processing overflow error(OverflowException)
		 */
		public static final String COLLECTION_OVERFLOW = "38";	
		
		//#CM700050
		/**
		 * Field where detailed error shows that response telegram cannot be acquired
		 */
		public static final String CANNOT_GET_RESPONSE_ID = "39";
		
		//#CM700051
		/**
		 * Field which shows start reception when detailed error is started
		 */
		public static final String ALREADY_STARTED = "51";
		
		//#CM700052
		/**
		 * Field which shows end reception when detailed error is not started
		 */
		public static final String NON_START_FINISH = "52";
		
		//#CM700053
		/**
		 * Break reception when detailed error is taken a break
		 */
		public static final String ALREADY_RESTED = "53";
		
		//#CM700054
		/**
		 * Restart reception when detailed error is not taken a rest
		 */
		public static final String ERROR_NON_REST_RESTART = "54";
		
		//#CM700055
		/**
		 * A detailed specified worker for the error is a field shown while working with another machine. 
		 */
		public static final String WORKING_OTHER_TERMINAL = "55";
		
		//#CM700056
		/**
		 * A detailed specified machine for the error is a field shown while another worker is working. 
		 */
		public static final String WORKING_OTHER_WORKER ="56";
			
		//#CM700057
		/**
		 * Field which shows detailed illegal line - number error
		 */
		public static final String LINE_NO_INCONSISTENCY ="57";

		//#CM700058
		/**
		 * Field which shows terminal No. title of unregistration (terminal title No. title injustice)
		 */
		public static final String TERMINAL_UNREGISTRATION ="58";
	}
}
//#CM700059
//end of interface
