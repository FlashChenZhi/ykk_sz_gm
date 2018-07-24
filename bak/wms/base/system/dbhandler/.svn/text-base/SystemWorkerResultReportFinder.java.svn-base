//#CM699900
//$Id: SystemWorkerResultReportFinder.java,v 1.2 2006/10/30 06:39:16 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;
//#CM699901
/*
 * Created on 2004/08/20
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultReportFinder;
import jp.co.daifuku.wms.base.system.entity.SystemWorkerResult;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM699902
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * Allow this class to search for the DNWORKERRESULT table through the database.
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>suresh kayamboo</TD><TD></TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/08/31
 */
public class SystemWorkerResultReportFinder extends WorkerResultReportFinder
{
	
    //#CM699903
    // Class filelds -----------------------------------------------
	//#CM699904
	/**
	 *  Variable to maintain the search conditions tentatively in this class.
	 */
	private SearchKey wSkey ;
	
	
	//#CM699905
	// Class method --------------------------------------------------
	//#CM699906
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:16 $");
	}

	//#CM699907
	// Constructors --------------------------------------------------
	//#CM699908
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemWorkerResultReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM699909
	// Public methods ------------------------------------------------
	//#CM699910
	/**
	 * Search for the DNWORKERRESULT.
	 * @param key Key for search
	 * @param flag Aggregation Conditions
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int search(SearchKey key, String flag) throws ReadWriteException
	{
		close();
		open();
		int count = 0 ;
    	
		ResultSet rsCount  = null ;

		try
		{
        	
			//#CM699911
			//SQL statements
			String SEARCH_COUNT_SQL = "SELECT COUNT({0}) COUNT FROM DNWORKERRESULT {1} {2} {3} " ;
			String SEARCH_SQL = "" ;
			String WS = "WORK_START_TIME" ;
			String WE = "WORK_END_TIME" ;
        	
        	
			String DETAIL =
				", TRUNC(WORK_TIME / (24*60*60)) AS DAYS, "
					+ " TRUNC(MOD(WORK_TIME, 24*60*60) / (60*60)) AS HRS, "
					+ " TRUNC(MOD(WORK_TIME, 60*60) / 60) AS MIN , "
					+ " ROUND(MOD(WORK_TIME, 60)) AS SEC";

			String TERM_TIME =
				", TRUNC(SUM(WORK_TIME) / (24*60*60)) AS DAYS, "
					+ " TRUNC(MOD(SUM(WORK_TIME), 24*60*60) / (60*60)) AS HRS, "
					+ " TRUNC(MOD(SUM(WORK_TIME), 60*60) / 60) AS MIN , "
					+ " ROUND(MOD(SUM(WORK_TIME), 60)) AS SEC";
        	
        	
			//#CM699912
			//MIN(substr(to_char(work_starttime, 'yyyymmddhh24miss'),9,8)) AS STARTTIME
			String DAILY_TIME = TERM_TIME + " , MIN(SUBSTR(TO_CHAR(" + WS + ", 'yyyymmddhh24miss'),9,8)) AS STARTTIME, " +  
								" MAX(SUBSTR(TO_CHAR(" + WE + ",'yyyymmddhh24miss'),9,8)) AS ENDTIME "			;
        	
			String DETAIL_TIME = DETAIL + ", SUBSTR(TO_CHAR(" + WS + ", 'YYYYMMDDHH24MISS'),9,8) AS STARTTIME, " +
								" SUBSTR(TO_CHAR(" + WE + ", 'YYYYMMDDHH24MISS'),9,8) AS ENDTIME " ;
        	
			if((flag != null) && !flag.equals(""))
			{
				if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
				{
					SEARCH_SQL = "SELECT {0} " + TERM_TIME + " FROM DNWORKERRESULT {1} {2} {3} " ;
				}
				else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
				{
					SEARCH_SQL = "SELECT {0} " + DAILY_TIME + " FROM DNWORKERRESULT {1} {2} {3} " ;
				}
				else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
				{
					SEARCH_SQL = "SELECT {0}" +  DETAIL_TIME + " FROM DNWORKERRESULT {1} {2} {3}" ;        			
				}
        			
			}
        	
			//#CM699913
			//Set the Search key for wSkey.
			wSkey = key ;
			Object[] fmtObj = new Object[4] ;
			Object[] cntObj = new Object[4];
        	
			//#CM699914
			// Compile the conditions for obtaining data (for COUNT).
			if ( key.getCollectConditionForCount() != null )
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				//#CM699915
				// Obtain all the field items if the Conditions for Obtaining Data is None.
				cntObj[0] = " * " ;
			}
			
			//#CM699916
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;				
			} else
			{
				//#CM699917
				// Obtain all the field items if the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;				
			}

			//#CM699918
			// Compile the search conditions.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = "WHERE " + key.getReferenceCondition();
				cntObj[1] = "WHERE " + key.getReferenceCondition();
			}

			//#CM699919
			// Compile the aggregation conditions,	
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM699920
			// Compile the loading sequence.	
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
			}
			                       
			String SQL_STRING_CNT = "";
            
			SQL_STRING_CNT = SimpleFormat.format(SEARCH_COUNT_SQL, cntObj) ;
			DEBUG.MSG("HANDLER", "search SEARCH COUNT SQL [ " + SQL_STRING_CNT + "]" );    
            
			rsCount = p_Statement.executeQuery(SQL_STRING_CNT);
                                  
			while (rsCount.next())
			{           	
				count = rsCount.getInt("COUNT");                
			}
			String SQL_STRING = SimpleFormat.format(SEARCH_SQL, fmtObj) ;
			DEBUG.MSG("HANDLER", "search SEARCH SQL [ " + SQL_STRING + "]" );
			p_ResultSet = p_Statement.executeQuery(SQL_STRING);
			isNextFlag = true;                

		}
		catch (SQLException e)
		{
			//#CM699921
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), this.getClass().getName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkerResult")) ;
		}
    	
		return count ;
	}
	
	//#CM699922
	/**
	 * Return the result from searching through database in the form of entity array.
	 * @param count Count of the obtained search result
	 * @param flag  Aggregation Conditions
	 * @return Array of entity
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws InvalidStatusException Announce if error occurs within the designated range of the search result.
	 */
	public Parameter[] getParamsEntities(int count, String flag) throws ReadWriteException
	{
		Vector vec = new Vector();
		SystemWorkerResult[] wkrResultTemp = null ;
		Timestamp tStamp = null ;
		boolean wNotAllFlag = false;
		
		SystemParameter sysParam[] = null ;

		try
		{
			//#CM699923
			// Determine for presence of any conditions for obtaining data.
			//#CM699924
			// Obtain all the field items if the conditions for obtaining data is not designated.
			if (wSkey.getCollectCondition() != null)
			{
				wNotAllFlag = true;
			}
			//#CM699925
			// Count the data that have been read this time.
			int readCount = 0;
			//#CM699926
			// Flag to determine whether to close the ResultSet or not. Close after reading the end line.
			boolean endFlag = true;
						
			while (p_ResultSet.next())
			{
				SystemWorkerResult workerResult = new SystemWorkerResult() ;
                				
				if(!wNotAllFlag)
                {	
                	workerResult.setWorkDate(DBFormat.replace(p_ResultSet.getString("WORK_DATE"))) ;
                
                	tStamp = p_ResultSet.getTimestamp("WORK_START_TIME") ;
                	if(tStamp !=null)
                	{
                		workerResult.setWorkStartTime(new java.util.Date(tStamp.getTime()));
                	}
				
                	tStamp = p_ResultSet.getTimestamp("WORK_END_TIME") ;
                	if(tStamp !=null)
                	{
                		workerResult.setWorkEndTime(new java.util.Date(tStamp.getTime()));
                	}
                					
                	workerResult.setWorkerCode(DBFormat.replace(p_ResultSet.getString("WORKER_CODE"))) ;
                	workerResult.setWorkerName(DBFormat.replace(p_ResultSet.getString("WORKER_NAME"))) ;
                	workerResult.setTerminalNo(DBFormat.replace(p_ResultSet.getString("TERMINAL_NO"))) ;
                	workerResult.setJobType(DBFormat.replace(p_ResultSet.getString("JOB_TYPE"))) ;
                	workerResult.setWorkQty(p_ResultSet.getInt("WORK_QTY")) ;
                	workerResult.setWorkCnt(p_ResultSet.getInt("WORK_CNT")) ;
                	
                	if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
                	{
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;
                    	workerResult.setHrs(p_ResultSet.getString("HRS")) ;
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;
                	}
                	else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
                	{
                		workerResult.setStartTime(p_ResultSet.getString("STARTTIME")) ;
                		workerResult.setEndTime(p_ResultSet.getString("ENDTIME")) ;
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;
                    	workerResult.setHrs(p_ResultSet.getString("HRS")) ;
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;
                	}
                	else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
                	{
                		workerResult.setStartTime(p_ResultSet.getString("STARTTIME")) ;
                		workerResult.setEndTime(p_ResultSet.getString("ENDTIME")) ;
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;
                		workerResult.setHrs(p_ResultSet.getString("HRS")) ;                        	
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;                        	
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;                        	
                	}              	
                }				
				else
                {   
					
                	if (wSkey.checkCollection("WORK_DATE") == true)
					{
                		workerResult.setWorkDate(DBFormat.replace(p_ResultSet.getString("WORK_DATE"))) ;
					}
					if (wSkey.checkCollection("WORK_START_TIME") == true)
					{
						tStamp = p_ResultSet.getTimestamp("WORK_START_TIME") ;
						if(tStamp !=null)
						{
							workerResult.setWorkStartTime(new java.util.Date(tStamp.getTime())) ;
						}
					}
					if (wSkey.checkCollection("WORK_END_TIME") == true)
					{
						tStamp = p_ResultSet.getTimestamp("WORK_END_TIME") ;
						if(tStamp !=null)
						{
							workerResult.setWorkEndTime(new java.util.Date(tStamp.getTime())) ;
						}
					}
					if (wSkey.checkCollection("WORKER_CODE") == true)
					{
						workerResult.setWorkerCode(DBFormat.replace(p_ResultSet.getString("WORKER_CODE"))) ;
					}
					if (wSkey.checkCollection("WORKER_NAME") == true)
					{
						workerResult.setWorkerName(DBFormat.replace(p_ResultSet.getString("WORKER_NAME"))) ;
					}
					if (wSkey.checkCollection("TERMINAL_NO") == true)
					{
						workerResult.setTerminalNo(DBFormat.replace(p_ResultSet.getString("TERMINAL_NO"))) ;
					}
					if (wSkey.checkCollection("JOB_TYPE") == true)
					{
						workerResult.setJobType(DBFormat.replace(p_ResultSet.getString("JOB_TYPE"))) ;
					}
					
					if (wSkey.checkCollection("WORK_QTY") == true)
					{
						workerResult.setWorkQty(p_ResultSet.getInt("WORK_QTY"));
					}
					if (wSkey.checkCollection("WORK_CNT") == true)
					{
						workerResult.setWorkCnt(p_ResultSet.getInt("WORK_CNT"));
					}												

                	if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
                	{               		
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;                    		
                    	workerResult.setHrs(p_ResultSet.getString("HRS")) ;                        	
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;                        	
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;
                	}
                	else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
                	{
                		workerResult.setStartTime(p_ResultSet.getString("STARTTIME")) ;                    		
                		workerResult.setEndTime(p_ResultSet.getString("ENDTIME")) ;
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;                    		
                    	workerResult.setHrs(p_ResultSet.getString("HRS")) ;                        	
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;                        	
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;      
                    }
                	else if(flag.equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
                	{                    		
                		workerResult.setStartTime(p_ResultSet.getString("STARTTIME")) ;
                		workerResult.setEndTime(p_ResultSet.getString("ENDTIME")) ;
                		workerResult.setDays(p_ResultSet.getString("DAYS")) ;                    		
                    	workerResult.setHrs(p_ResultSet.getString("HRS")) ;                        	
                    	workerResult.setMin(p_ResultSet.getString("MIN")) ;
                    	workerResult.setSec(p_ResultSet.getString("SEC")) ;
                    	
                	}
                }
                vec.addElement(workerResult);
                readCount++ ;
                
                if (count <= readCount)
				{
					endFlag = false;
					break;
				}
                
            }
			
			//#CM699927
			//  Close after reading the end line.
			if ( endFlag )
			{				
				isNextFlag = false ;
				close();
			}
			
            wkrResultTemp = new SystemWorkerResult[vec.size()];
            vec.copyInto(wkrResultTemp) ;
            
			SystemDBCommon systemCommon = new SystemDBCommon();
			sysParam = (SystemParameter[])systemCommon.convertToParams(wkrResultTemp) ;
            
		}
		catch (SQLException e)
		{
			//#CM699928
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkerResult"));
		}

		return sysParam ;
	}
}
//#CM699929
//end of class
