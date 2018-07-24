//#CM705100
//$Id: Palette.java,v 1.6 2006/11/16 02:15:41 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705101
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Palette;

//#CM705102
/**
 * Entity class of PALETTE
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.6 $, $Date: 2006/11/16 02:15:41 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Palette
		extends AbstractEntity
{
	//#CM705103
	//------------------------------------------------------------
	//#CM705104
	// class variables (prefix '$')
	//#CM705105
	//------------------------------------------------------------
	//#CM705106
	//	private String	$classVar ;

//#CM705107
/**<en>
	 * Field for stock status (reserved for storage)
	 </en>*/
	public static final int STORAGE_PLAN = 1;

	//#CM705108
	/**<jp>
	 * Stock Status (Loaded)
	 </jp>*/
	//#CM705109
	/**<en>
	 * Field for stock status (loaded)
	 </en>*/
	public static final int REGULAR = 2;

	//#CM705110
	/**<jp>
	 * Stock Status (reserved for retrieval)
	 </jp>*/
	//#CM705111
	/**<en>
	 * Field for stock tatus (reserved for retrieval)
	 </en>*/
	public static final int RETRIEVAL_PLAN = 3;

	//#CM705112
	/**<jp>
	 * Stock Status (being retrieved)
	 </jp>*/
	//#CM705113
	/**<en>
	 * Field for status of stock (being retrieved)
	 </en>*/
	public static final int RETRIEVAL = 4;

	//#CM705114
	/**<jp>
	 * Stock Status (irregular)
	 </jp>*/
	//#CM705115
	/**<en>
	 * Field for stock status (irregular)
	 </en>*/
	public static final int IRREGULAR = 5;

	//#CM705116
	/**<jp>
	 * Allocation Status (not allocated)
	 </jp>*/
	//#CM705117
	/**<en>
	 * Field for allocation status (not allocated)
	 </en>*/
	public static final int NOT_ALLOCATED = 0;

	//#CM705118
	/**<jp>
	 * Allocation Status (allocated)
	 </jp>*/
	//#CM705119
	/**<en>
	 * Field for allocation status (allocated)
	 </en>*/
	public static final int ALLOCATED = 1;

	//#CM705120
	/**<jp>
	 * Empty palette status (normal)
	 </jp>*/
	//#CM705121
	/**<en>
	 * Field for the status of empty pallet (normal)
	 </en>*/
	public static final int NORMAL = 0;

	//#CM705122
	/**<jp>
	 * Empty palette status (empty)
	 </jp>*/
	//#CM705123
	/**<en>
	 * Field for the status of empty pallet (empty)
	 </en>*/
	public static final int STATUS_EMPTY = 1;

	//#CM705124
	/**
	 * Palette type ID
	 * Set 0 while submitting because there is no PaletteType table in eWareNavi.
	 */
	public static final int PALETTE_TYPE_ID = 0;
	//#CM705125
	//------------------------------------------------------------
	//#CM705126
	// fields (upper case only)
	//#CM705127
	//------------------------------------------------------------
	//#CM705128
	/*
	 *  * Table name : PALETTE
	 * palette id :                    PALETTEID           number
	 * current station number :        CURRENTSTATIONNUMBERvarchar2(16)
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * palette type id :               PALETTETYPEID       number
	 * stock status :                  STATUS              number
	 * allocation status :             ALLOCATION          number
	 * empty palette status :          EMPTY               number
	 * updatation date :               REFIXDATE           date
	 * filling rate :                  RATE                number
	 * load height :                   HEIGHT              number
	 * palette info :                  BCDATA              varchar2(30)
	 */

	//#CM705129
	/**Table name definition*/

	public static final String TABLE_NAME = "PALETTE";

	//#CM705130
	/** Column Definition (PALETTEID) */

	public static final FieldName PALETTEID = new FieldName("PALETTEID");

	//#CM705131
	/** Column Definition (CURRENTSTATIONNUMBER) */

	public static final FieldName CURRENTSTATIONNUMBER = new FieldName("CURRENTSTATIONNUMBER");

	//#CM705132
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM705133
	/** Column Definition (PALETTETYPEID) */

	public static final FieldName PALETTETYPEID = new FieldName("PALETTETYPEID");

	//#CM705134
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM705135
	/** Column Definition (ALLOCATION) */

	public static final FieldName ALLOCATION = new FieldName("ALLOCATION");

	//#CM705136
	/** Column Definition (EMPTY) */

	public static final FieldName EMPTY = new FieldName("EMPTY");

	//#CM705137
	/** Column Definition (REFIXDATE) */

	public static final FieldName REFIXDATE = new FieldName("REFIXDATE");

	//#CM705138
	/** Column Definition (RATE) */

	public static final FieldName RATE = new FieldName("RATE");

	//#CM705139
	/** Column Definition (HEIGHT) */

	public static final FieldName HEIGHT = new FieldName("HEIGHT");

	//#CM705140
	/** Column Definition (BCDATA) */

	public static final FieldName BCDATA = new FieldName("BCDATA");


	//#CM705141
	//------------------------------------------------------------
	//#CM705142
	// properties (prefix 'p_')
	//#CM705143
	//------------------------------------------------------------
	//#CM705144
	//	private String	p_Name ;


	//#CM705145
	//------------------------------------------------------------
	//#CM705146
	// instance variables (prefix '_')
	//#CM705147
	//------------------------------------------------------------
	//#CM705148
	//	private String	_instanceVar ;

	//#CM705149
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705150
	//------------------------------------------------------------
	//#CM705151
	// constructors
	//#CM705152
	//------------------------------------------------------------

	//#CM705153
	/**
	 * Prepare class name list and generate instance
	 */
	public Palette()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM705154
	//------------------------------------------------------------
	//#CM705155
	// accessors
	//#CM705156
	//------------------------------------------------------------

	//#CM705157
	/**
	 * Set value to palette id
	 * @param arg palette id to be set
	 */
	public void setPaletteId(int arg)
	{
		setValue(PALETTEID, new Integer(arg));
	}

	//#CM705158
	/**
	 * Fetch palette id
	 * @return palette id
	 */
	public int getPaletteId()
	{
		return getBigDecimal(Palette.PALETTEID).intValue();
	}

	//#CM705159
	/**
	 * Set value to current station number
	 * @param arg current station number to be set
	 */
	public void setCurrentStationNumber(String arg)
	{
		setValue(CURRENTSTATIONNUMBER, arg);
	}

	//#CM705160
	/**
	 * Fetch current station number
	 * @return current station number
	 */
	public String getCurrentStationNumber()
	{
		return getValue(Palette.CURRENTSTATIONNUMBER).toString();
	}

	//#CM705161
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM705162
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(Palette.WHSTATIONNUMBER).toString();
	}

	//#CM705163
	/**
	 * Set value to palette type id
	 * @param arg palette type id to be set
	 */
	public void setPaletteTypeId(int arg)
	{
		setValue(PALETTETYPEID, new Integer(arg));
	}

	//#CM705164
	/**
	 * Fetch palette type id
	 * @return palette type id
	 */
	public int getPaletteTypeId()
	{
		return getBigDecimal(Palette.PALETTETYPEID).intValue();
	}

	//#CM705165
	/**
	 * Set value to stock status
	 * @param arg stock status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM705166
	/**
	 * Fetch stock status
	 * @return stock status
	 */
	public int getStatus()
	{
		return getBigDecimal(Palette.STATUS).intValue();
	}

	//#CM705167
	/**
	 * Set value to allocation status
	 * @param arg allocation status to be set
	 */
	public void setAllocation(int arg)
	{
		setValue(ALLOCATION, new Integer(arg));
	}

	//#CM705168
	/**
	 * Fetch allocation status
	 * @return allocation status
	 */
	public int getAllocation()
	{
		return getBigDecimal(Palette.ALLOCATION).intValue();
	}

	//#CM705169
	/**
	 * Set value to empty palette status
	 * @param arg empty palette status to be set
	 */
	public void setEmpty(int arg)
	{
		setValue(EMPTY, new Integer(arg));
	}

	//#CM705170
	/**
	 * Fetch empty palette status
	 * @return empty palette status
	 */
	public int getEmpty()
	{
		return getBigDecimal(Palette.EMPTY).intValue();
	}

	//#CM705171
	/**
	 * Set value to updatation date
	 * @param arg updatation date to be set
	 */
	public void setRefixDate(Date arg)
	{
		setValue(REFIXDATE, arg);
	}

	//#CM705172
	/**
	 * Fetch updatation date
	 * @return updatation date
	 */
	public Date getRefixDate()
	{
		return (Date)getValue(Palette.REFIXDATE);
	}

	//#CM705173
	/**
	 * Set value to filling rate
	 * @param arg filling rate to be set
	 */
	public void setRate(int arg)
	{
		setValue(RATE, new Integer(arg));
	}

	//#CM705174
	/**
	 * Fetch filling rate
	 * @return filling rate
	 */
	public int getRate()
	{
		return getBigDecimal(Palette.RATE).intValue();
	}

	//#CM705175
	/**
	 * Set value to load height
	 * @param arg load height to be set
	 */
	public void setHeight(int arg)
	{
		setValue(HEIGHT, new Integer(arg));
	}

	//#CM705176
	/**
	 * Fetch load height
	 * @return load height
	 */
	public int getHeight()
	{
		return getBigDecimal(Palette.HEIGHT).intValue();
	}

	//#CM705177
	/**
	 * Set value to palette info
	 * @param arg palette info to be set
	 */
	public void setBcData(String arg)
	{
		setValue(BCDATA, arg);
	}

	//#CM705178
	/**
	 * Fetch palette info
	 * @return palette info
	 */
	public String getBcData()
	{
		return getValue(Palette.BCDATA).toString();
	}


	//#CM705179
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705180
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(REFIXDATE, "");

		setValue(PALETTEID, new Integer(0));
		setValue(PALETTETYPEID, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(ALLOCATION, new Integer(0));
		setValue(EMPTY, new Integer(0));
		setValue(RATE, new Integer(0));
		setValue(HEIGHT, new Integer(0));
	}

	/**
	 * <BR>
	 * <BR>
	 * @return
	 */
	public String[] getAutoUpdateColumnArray()
	{
		String prefix = TABLE_NAME + "." ;

		Vector autoColumn = new Vector();
		autoColumn.add(prefix + REFIXDATE);

		String[] autoColumnArray = new String[autoColumn.size()];
		autoColumn.copyInto(autoColumnArray);

		return autoColumnArray;

	}
	//------------------------------------------------------------
	//#CM705181
	// package methods
	//#CM705182
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705183
	//------------------------------------------------------------


	//#CM705184
	//------------------------------------------------------------
	//#CM705185
	// protected methods
	//#CM705186
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705187
	//------------------------------------------------------------


	//#CM705188
	//------------------------------------------------------------
	//#CM705189
	// private methods
	//#CM705190
	//------------------------------------------------------------
	//#CM705191
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + PALETTEID);
		lst.add(prefix + CURRENTSTATIONNUMBER);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + PALETTETYPEID);
		lst.add(prefix + STATUS);
		lst.add(prefix + ALLOCATION);
		lst.add(prefix + EMPTY);
		lst.add(prefix + REFIXDATE);
		lst.add(prefix + RATE);
		lst.add(prefix + HEIGHT);
		lst.add(prefix + BCDATA);
	}


	//#CM705192
	//------------------------------------------------------------
	//#CM705193
	// utility methods
	//#CM705194
	//------------------------------------------------------------
	//#CM705195
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Palette.java,v 1.6 2006/11/16 02:15:41 suresh Exp $" ;
	}
}
