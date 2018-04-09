//#CM705196
//$Id: ReStoring.java,v 1.4 2006/11/16 02:15:40 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705197
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;


import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;

import jp.co.daifuku.wms.base.entity.ReStoring;

//#CM705198
/**
 * Entity class of RESTORING
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
 * @version $Revision: 1.4 $, $Date: 2006/11/16 02:15:40 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ReStoring
		extends AbstractEntity
{
	//#CM705199
	//------------------------------------------------------------
	//#CM705200
	// class variables (prefix '$')
	//#CM705201
	//------------------------------------------------------------
	//#CM705202
	//	private String	$classVar ;
//#CM705203
/**
	 * Field which shows processing flag (Unprocessed)
	 */
	public static final int UNPROCESSED = 0 ;

	//#CM705204
	/**
	 * Field which shows processing flag (Processed)
	 */
	public static final int PROCESSED   = 1 ;

	//#CM705205
	//------------------------------------------------------------
	//#CM705206
	// fields (upper case only)
	//#CM705207
	//------------------------------------------------------------
	//#CM705208
	/*
	 *  * Table name : RESTORING
	 * work number :                   WORKNUMBER          varchar2(16)
	 * creation date :                 CREATEDATE          date
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * station number :                STATIONNUMBER       varchar2(16)
	 * palette type id :               PALETTETYPEID       number
	 * item code :                     ITEMKEY             varchar2(40)
	 * lot number :                    LOTNUMBER           varchar2(60)
	 * stock qty :                     QUANTITY            number
	 * storage date :                  INCOMMINGDATE       varchar2(7)
	 * inventory check date :          INVENTORYCHECKDATE  varchar2(7)
	 * last modified date :            LASTCONFIRMDATE     varchar2(7)
	 * re-storage type :               RESTORING           number
	 * barcode info :                  BCDATA              varchar2(30)
	 * process flag :                  PROCESSINGFLAG      number
	 */

	//#CM705209
	/**Table name definition*/

	public static final String TABLE_NAME = "RESTORING";

	//#CM705210
	/** Column Definition (WORKNUMBER) */

	public static final FieldName WORKNUMBER = new FieldName("WORKNUMBER");

	//#CM705211
	/** Column Definition (CREATEDATE) */

	public static final FieldName CREATEDATE = new FieldName("CREATEDATE");

	//#CM705212
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM705213
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM705214
	/** Column Definition (PALETTETYPEID) */

	public static final FieldName PALETTETYPEID = new FieldName("PALETTETYPEID");

	//#CM705215
	/** Column Definition (ITEMKEY) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM705216
	/** Column Definition (LOTNUMBER) */

	public static final FieldName LOTNUMBER = new FieldName("LOTNUMBER");

	//#CM705217
	/** Column Definition (QUANTITY) */

	public static final FieldName QUANTITY = new FieldName("QUANTITY");

	//#CM705218
	/** Column Definition (INCOMMINGDATE) */

	public static final FieldName INCOMMINGDATE = new FieldName("INCOMMINGDATE");

	//#CM705219
	/** Column Definition (INVENTORYCHECKDATE) */

	public static final FieldName INVENTORYCHECKDATE = new FieldName("INVENTORYCHECKDATE");

	//#CM705220
	/** Column Definition (LASTCONFIRMDATE) */

	public static final FieldName LASTCONFIRMDATE = new FieldName("LASTCONFIRMDATE");

	//#CM705221
	/** Column Definition (RESTORING) */

	public static final FieldName RESTORING = new FieldName("RESTORING");

	//#CM705222
	/** Column Definition (BCDATA) */

	public static final FieldName BCDATA = new FieldName("BCDATA");

	//#CM705223
	/** Column Definition (PROCESSINGFLAG) */

	public static final FieldName PROCESSINGFLAG = new FieldName("PROCESSINGFLAG");


	//#CM705224
	//------------------------------------------------------------
	//#CM705225
	// properties (prefix 'p_')
	//#CM705226
	//------------------------------------------------------------
	//#CM705227
	//	private String	p_Name ;


	//#CM705228
	//------------------------------------------------------------
	//#CM705229
	// instance variables (prefix '_')
	//#CM705230
	//------------------------------------------------------------
	//#CM705231
	//	private String	_instanceVar ;

	//#CM705232
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705233
	//------------------------------------------------------------
	//#CM705234
	// constructors
	//#CM705235
	//------------------------------------------------------------

	//#CM705236
	/**
	 * Prepare class name list and generate instance
	 */
	public ReStoring()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM705237
	//------------------------------------------------------------
	//#CM705238
	// accessors
	//#CM705239
	//------------------------------------------------------------

	//#CM705240
	/**
	 * Set value to work number
	 * @param arg work number to be set
	 */
	public void setWorkNumber(String arg)
	{
		setValue(WORKNUMBER, arg);
	}

	//#CM705241
	/**
	 * Fetch work number
	 * @return work number
	 */
	public String getWorkNumber()
	{
		return getValue(ReStoring.WORKNUMBER).toString();
	}

	//#CM705242
	/**
	 * Set value to creation date
	 * @param arg creation date to be set
	 */
	public void setCreateDate(Date arg)
	{
		setValue(CREATEDATE, arg);
	}

	//#CM705243
	/**
	 * Fetch creation date
	 * @return creation date
	 */
	public Date getCreateDate()
	{
		return (Date)getValue(ReStoring.CREATEDATE);
	}

	//#CM705244
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM705245
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(ReStoring.WHSTATIONNUMBER).toString();
	}

	//#CM705246
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM705247
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(ReStoring.STATIONNUMBER).toString();
	}

	//#CM705248
	/**
	 * Set value to palette type id
	 * @param arg palette type id to be set
	 */
	public void setPaletteTypeID(int arg)
	{
		setValue(PALETTETYPEID, new Integer(arg));
	}

	//#CM705249
	/**
	 * Fetch palette type id
	 * @return palette type id
	 */
	public int getPaletteTypeID()
	{
		return getBigDecimal(ReStoring.PALETTETYPEID).intValue();
	}

	//#CM705250
	/**
	 * Set value to item code
	 * @param arg item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM705251
	/**
	 * Fetch item code
	 * @return item code
	 */
	public String getItemCode()
	{
		return getValue(ReStoring.ITEMCODE).toString();
	}

	//#CM705252
	/**
	 * Set value to lot number
	 * @param arg lot number to be set
	 */
	public void setLotNumber(String arg)
	{
		setValue(LOTNUMBER, arg);
	}

	//#CM705253
	/**
	 * Fetch lot number
	 * @return lot number
	 */
	public String getLotNumber()
	{
		return getValue(ReStoring.LOTNUMBER).toString();
	}

	//#CM705254
	/**
	 * Set value to stock qty
	 * @param arg stock qty to be set
	 */
	public void setQuantity(int arg)
	{
		setValue(QUANTITY, new Integer(arg));
	}

	//#CM705255
	/**
	 * Fetch stock qty
	 * @return stock qty
	 */
	public int getQuantity()
	{
		return getBigDecimal(ReStoring.QUANTITY).intValue();
	}

	//#CM705256
	/**
	 * Set value to storage date
	 * @param arg storage date to be set
	 */
	public void setInCommingDate(String arg)
	{
		setValue(INCOMMINGDATE, arg);
	}

	//#CM705257
	/**
	 * Fetch storage date
	 * @return storage date
	 */
	public String getInCommingDate()
	{
		return getValue(ReStoring.INCOMMINGDATE).toString();
	}

	//#CM705258
	/**
	 * Set value to inventory check date
	 * @param arg inventory check date to be set
	 */
	public void setInventoryCheckDate(String arg)
	{
		setValue(INVENTORYCHECKDATE, arg);
	}

	//#CM705259
	/**
	 * Fetch inventory check date
	 * @return inventory check date
	 */
	public String getInventoryCheckDate()
	{
		return getValue(ReStoring.INVENTORYCHECKDATE).toString();
	}

	//#CM705260
	/**
	 * Set value to last modified date
	 * @param arg last modified date to be set
	 */
	public void setLastConfirmDate(String arg)
	{
		setValue(LASTCONFIRMDATE, arg);
	}

	//#CM705261
	/**
	 * Fetch last modified date
	 * @return last modified date
	 */
	public String getLastConfirmDate()
	{
		return getValue(ReStoring.LASTCONFIRMDATE).toString();
	}

	//#CM705262
	/**
	 * Set value to re-storage type
	 * @param arg re-storage type to be set
	 */
	public void setReStoring(int arg)
	{
		setValue(RESTORING, new Integer(arg));
	}

	//#CM705263
	/**
	 * Fetch re-storage type
	 * @return re-storage type
	 */
	public int getReStoring()
	{
		return getBigDecimal(ReStoring.RESTORING).intValue();
	}

	//#CM705264
	/**
	 * Set value to barcode info
	 * @param arg barcode info to be set
	 */
	public void setBcData(String arg)
	{
		setValue(BCDATA, arg);
	}

	//#CM705265
	/**
	 * Fetch barcode info
	 * @return barcode info
	 */
	public String getBcData()
	{
		return getValue(ReStoring.BCDATA).toString();
	}

	//#CM705266
	/**
	 * Set value to process flag
	 * @param arg process flag to be set
	 */
	public void setProcessingFlag(int arg)
	{
		setValue(PROCESSINGFLAG, new Integer(arg));
	}

	//#CM705267
	/**
	 * Fetch process flag
	 * @return process flag
	 */
	public int getProcessingFlag()
	{
		return getBigDecimal(ReStoring.PROCESSINGFLAG).intValue();
	}


	//#CM705268
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705269
	public void setInitCreateColumn()
	{
		setValue(PALETTETYPEID, new Integer(0));
		setValue(QUANTITY, new Integer(0));
		setValue(RESTORING, new Integer(0));
		setValue(PROCESSINGFLAG, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM705270
	// package methods
	//#CM705271
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705272
	//------------------------------------------------------------


	//#CM705273
	//------------------------------------------------------------
	//#CM705274
	// protected methods
	//#CM705275
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705276
	//------------------------------------------------------------


	//#CM705277
	//------------------------------------------------------------
	//#CM705278
	// private methods
	//#CM705279
	//------------------------------------------------------------
	//#CM705280
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + WORKNUMBER);
		lst.add(prefix + CREATEDATE);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + PALETTETYPEID);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + LOTNUMBER);
		lst.add(prefix + QUANTITY);
		lst.add(prefix + INCOMMINGDATE);
		lst.add(prefix + INVENTORYCHECKDATE);
		lst.add(prefix + LASTCONFIRMDATE);
		lst.add(prefix + RESTORING);
		lst.add(prefix + BCDATA);
		lst.add(prefix + PROCESSINGFLAG);
	}


	//#CM705281
	//------------------------------------------------------------
	//#CM705282
	// utility methods
	//#CM705283
	//------------------------------------------------------------
	//#CM705284
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ReStoring.java,v 1.4 2006/11/16 02:15:40 suresh Exp $" ;
	}
}
