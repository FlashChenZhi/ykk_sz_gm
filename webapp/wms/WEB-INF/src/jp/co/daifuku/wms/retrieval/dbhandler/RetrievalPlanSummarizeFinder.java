//#CM709131
/*
 * Created on (Date): 2005/12/12
 *
 * To change the template of this generated comment to be inserted
 * Window > Setting > Java > Generating code > Code and Comment
 */
package jp.co.daifuku.wms.retrieval.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;

//#CM709132
/**
 * Designer : Y.Okamura<BR>
 * Maker : Y.Okamura<BR>
 * <BR>
 * Search through the Picking Plan Info.
 * Describe the SQL statement not supported by RetrievalPlanFinder.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/06/02</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:10 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanSummarizeFinder extends RetrievalPlanFinder
{
	//#CM709133
	// Class filelds -------------------------------------------------

	//#CM709134
	// Class valiables -----------------------------------------------

	//#CM709135
	// Class methods -------------------------------------------------
	//#CM709136
	/**
	 * Return the revision of this class.
	 * @return String of revision
	 */
	public static String getVersion()
	{
		return "$Id: RetrievalPlanSummarizeFinder.java,v 1.3 2007/02/07 04:16:10 suresh Exp $" ;
	}

	//#CM709137
	// Constructors --------------------------------------------------
	//#CM709138
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalPlanSummarizeFinder(Connection conn)
	{
		super(conn);
	}
	
	//#CM709139
	// Public methods ------------------------------------------------
	//#CM709140
	/**
	 * Search for the Order No. through the Picking Plan Info.<BR>
	 * Aggregate the Case Order No. and the Piece Order No. into UNION by division, and search for data in the ascending order.<BR>
	 * <BR>
	 * Require to limit the number of search condition (COLLECT conditions) allowed to designate up to one (1). If the number of designated search condition exceeds one, disable to operate.
	 * Designating nothing for Conditions for Obtaining Data obtains a value of either Case Order or Piece Order other than null.
	 * In the event of null for both, obtain null typically. Therefore, to avoid it, designate the WHERE condition to "is not null".
	 * Generate a nested SQL to make UNION for the number of elements of search key array.
	 * Generate the following SQL.<BR>
	 * <BR>
	 * select case_order_no from<BR>
	 * (<BR>
	 * <DIR>
	 *  select distinct(m_order_no) case_order_no from <BR>
	 *  (<BR>
	 *   <DIR>
	 *   (select dnretrievalplan.case_order_no m_order_no from dnretrievalplan)<BR>
	 *   union // -->Designate the Piece Order for the condition to obtain data.<BR>
	 *   (select dnretrievalplan.piece_order_no m_order_no from dnretrievalplan)<BR>
	 *   union // -->If no value is designated for the Conditions for Obtaining Data:<BR>
	 *   (select decode(case_order_no, null, piece_order_no, case_order_no) m_order_no from dnretrievalplan
	 *   where dnretrievalplan.case_order_no is not null or dnretrievalplan.piece_order_no is not null)<BR>
	 *   </DIR>
	 *   <BR>
	 *  )<BR>
	 * </DIR>
	 * )<BR>
	 * order by case_order_no<BR>
	 * 
	 * @param key []  search conditions
	 * @return Count of corresponding data
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	/**
	 * Search for the Order No. through the Picking Plan Info.<BR>
	 * Aggregate the Case Order No. and the Piece Order No. into UNION by division, and search for data in the ascending order.<BR>
	 * <BR>
	 * Require to limit the number of search condition (COLLECT conditions) allowed to designate up to one (1). If the number of designated search condition exceeds one, disable to operate.
	 * Designating nothing for Conditions for Obtaining Data obtains a value of either Case Order or Piece Order other than null.
	 * In the event of null for both, obtain null typically. Therefore, to avoid it, designate the WHERE condition to "is not null".
	 * Generate a nested SQL to make UNION for the number of elements of search key array.
	 * Generate the following SQL.<BR>
	 * <BR>
	 * select case_order_no from<BR>
	 * (<BR>
	 * <DIR>
	 *  select distinct(m_order_no) case_order_no from <BR>
	 *  (<BR>
	 *   <DIR>
	 *   (select dnretrievalplan.case_order_no m_order_no from dnretrievalplan)<BR>
	 *   union // -->Designate the Piece Order for the condition to obtain data.<BR>
	 *   (select dnretrievalplan.piece_order_no m_order_no from dnretrievalplan)<BR>
	 *   union // -->If no value is designated for the Conditions for Obtaining Data:<BR>
	 *   (select decode(case_order_no, null, piece_order_no, case_order_no) m_order_no from dnretrievalplan
	 *   where dnretrievalplan.case_order_no is not null or dnretrievalplan.piece_order_no is not null)<BR>
	 *   </DIR>
	 *   <BR>
	 *  )<BR>
	 * </DIR>
	 * )<BR>
	 * order by case_order_no<BR>
	 * 
	 * @param key []  search conditions
	 * @return Count of corresponding data
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int searchOrderNo(RetrievalPlanSearchKey[] key) throws ReadWriteException
	{
		try
		{
			close();
			open();
			ResultSet countret  = null ;
			
			String countSQL =	" SELECT COUNT(*) COUNT FROM ";
			String findSQL =	" SELECT CASE_ORDER_NO FROM ";
			String nest = 		" (SELECT DISTINCT(M_ORDER_NO) CASE_ORDER_NO FROM ( ";
			String orderBy = 	"  ) ) ORDER BY CASE_ORDER_NO ";
			
			//#CM709141
			// SQL to be nested
			String temp = " ( SELECT {0} FROM DNRETRIEVALPLAN {1} ) ";
			String unionSQL = "";
			for (int i = 0; i < key.length; i++)
			{
				Object[]  fmtObj = new Object[2];
			
				//#CM709142
				// Compile the conditions for obtaining data.
				if (key[i].getCollectCondition() != null)
				{
					fmtObj[0] = key[i].getCollectCondition();
				}
				else
				{
					//#CM709143
					// Regard no designation for the condition to obtain data as "None" designated.
					fmtObj[0] = "DECODE( CASE_ORDER_NO, null, PIECE_ORDER_NO, CASE_ORDER_NO)";
				}
				//#CM709144
				// Assign alias name.
				fmtObj[0] = fmtObj[0] + " M_ORDER_NO";
			
				//#CM709145
				// Compile the search conditions.			
				if (key[i].getReferenceCondition() != null)
				{
					fmtObj[1] = "WHERE " + key[i].getReferenceCondition();
				}
				
				unionSQL += SimpleFormat.format(temp, fmtObj);
				
				if (i == 0)
				{
					temp = " UNION " + temp;
				}
			
			}
			
			countSQL = countSQL + nest + unionSQL + orderBy;
			int count = 0;
DEBUG.MSG("HANDLER", "RetrievalPlanSummarizeFinder.searchOrder COUNT SQL[" + countSQL + "]") ;
			countret = p_Statement.executeQuery(countSQL);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM709146
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				findSQL = findSQL + nest + unionSQL + orderBy;
DEBUG.MSG("HANDLER", "RetrievalPlanSummarizeFinder.searchOrder SQL[" + findSQL + "]") ;
				p_ResultSet = p_Statement.executeQuery(findSQL);
			}
			else
			{
				p_ResultSet = null;
			}
			
			return count;
			
		}
		catch (SQLException e)
		{
			//#CM709147
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "RetrievalPlanSummarizeFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnRetrievalPlan")) ;
		}
		
	}

	//#CM709148
	/**
	 * Search for the Location through the Picking Plan Info.<BR>
	 * Aggregate the Case Location and the Piece Location into UNION by division, and search for data in the ascending order.<BR>
	 * <BR>
	 * Require to limit the number of search condition (COLLECT conditions) allowed to designate up to one (1). If the number of designated search condition exceeds one, disable to operate.
	 * Designating nothing for Conditions for Obtaining Data obtains a value of either Case Location or Piece Location other than null.
	 * In the event of null for both, obtain null typically. Therefore, to avoid it, designate the WHERE condition to "is not null".
	 * Generate a nested SQL to make UNION for the number of elements of search key array.
	 * Generate the following SQL.<BR>
	 * <BR>
	 * select case_location_no from<BR>
	 * (<BR>
	 *  <DIR>
	 *  select distinct(m_location_no) case_location_no from <BR>
	 *  (<BR>
	 *   <DIR>
	 *   (select dnretrievalplan.case_location_no m_location_no from dnretrievalplan)<BR>
	 *   union // -->Designate the Piece Location for the condition to obtain data.<BR>
	 *   (select dnretrievalplan.piece_location_no m_location_no from dnretrievalplan)<BR>
	 *   union // -->If no value is designated for the Conditions for Obtaining Data:<BR>
	 *   (select decode(case_location_no, null, piece_location_no, case_location_no) m_location_no from dnretrievalplan
	 *   where dnretrievalplan.case_location_no is not null or dnretrievalplan.piece_location_no is not null)<BR>
	 *   </DIR>
	 *   <BR>
	 *  )<BR>
	 * </DIR>
	 * )<BR>
	 * order by case_location_no<BR>
	 * 
	 * @param key []  search conditions
	 * @return Count of corresponding data
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	/**
	 * Search for the Location through the Picking Plan Info.<BR>
	 * Aggregate the Case Location and the Piece Location into UNION by division, and search for data in the ascending order.<BR>
	 * <BR>
	 * Require to limit the number of search condition (COLLECT conditions) allowed to designate up to one (1). If the number of designated search condition exceeds one, disable to operate.
	 * Designating nothing for Conditions for Obtaining Data obtains a value of either Case Location or Piece Location other than null.
	 * In the event of null for both, obtain null typically. Therefore, to avoid it, designate the WHERE condition to "is not null".
	 * Generate a nested SQL to make UNION for the number of elements of search key array.
	 * Generate the following SQL.<BR>
	 * <BR>
	 * select case_location_no from<BR>
	 * (<BR>
	 *  <DIR>
	 *  select distinct(m_location_no) case_location_no from <BR>
	 *  (<BR>
	 *   <DIR>
	 *   (select dnretrievalplan.case_location_no m_location_no from dnretrievalplan)<BR>
	 *   union // -->Designate the Piece Location for the condition to obtain data.<BR>
	 *   (select dnretrievalplan.piece_location_no m_location_no from dnretrievalplan)<BR>
	 *   union // -->If no value is designated for the Conditions for Obtaining Data:<BR>
	 *   (select decode(case_location_no, null, piece_location_no, case_location_no) m_location_no from dnretrievalplan
	 *   where dnretrievalplan.case_location_no is not null or dnretrievalplan.piece_location_no is not null)<BR>
	 *   </DIR>
	 *   <BR>
	 *  )<BR>
	 * </DIR>
	 * )<BR>
	 * order by case_location_no<BR>
	 * 
	 * @param key []  search conditions
	 * @return Count of corresponding data
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int searchLocationNo(RetrievalPlanSearchKey[] key) throws ReadWriteException
	{
		try
		{
			close();
			open();
			ResultSet countret  = null ;
			
			String countSQL =	" SELECT COUNT(*) COUNT FROM ";
			String findSQL =	" SELECT CASE_LOCATION FROM ";
			String nest = 		" (SELECT DISTINCT(M_LOCATION) CASE_LOCATION FROM ( ";
			String orderBy = 	"  ) ) ORDER BY CASE_LOCATION ";
			
			//#CM709149
			// SQL to be nested
			String temp = " ( SELECT {0} FROM DNRETRIEVALPLAN {1} ) ";
			String unionSQL = "";
			for (int i = 0; i < key.length; i++)
			{
				Object[]  fmtObj = new Object[2];
			
				//#CM709150
				// Compile the conditions for obtaining data.
				if (key[i].getCollectCondition() != null)
				{
					fmtObj[0] = key[i].getCollectCondition();
				}
				else
				{
					//#CM709151
					// Regard no designation for the condition to obtain data as "None" designated.
					fmtObj[0] = "DECODE( CASE_LOCATION, null, PIECE_LOCATION, CASE_LOCATION)";
				}
				fmtObj[0] = fmtObj[0] + " M_LOCATION";
			
				//#CM709152
				// Compile the search conditions.			
				if (key[i].getReferenceCondition() != null)
				{
					fmtObj[1] = "WHERE " + key[i].getReferenceCondition();
				}
				
				unionSQL += SimpleFormat.format(temp, fmtObj);
				
				if (i == 0)
				{
					temp = " UNION " + temp;
				}
			
			}
			
			countSQL = countSQL + nest + unionSQL + orderBy;
			int count = 0;
DEBUG.MSG("HANDLER", "RetrievalPlanSummarizeFinder.searchLocation COUNT SQL[" + countSQL + "]") ;
			countret = p_Statement.executeQuery(countSQL);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM709153
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				findSQL = findSQL + nest + unionSQL + orderBy;
DEBUG.MSG("HANDLER", "RetrievalPlanSummarizeFinder.searchLocation SQL[" + findSQL + "]") ;
				p_ResultSet = p_Statement.executeQuery(findSQL);
			}
			else
			{
				p_ResultSet = null;
			}
			
			return count;
			
		}
		catch (SQLException e)
		{
			//#CM709154
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "RetrievalPlanSummarizeFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnRetrievalPlan")) ;
		}
		
	}

}
