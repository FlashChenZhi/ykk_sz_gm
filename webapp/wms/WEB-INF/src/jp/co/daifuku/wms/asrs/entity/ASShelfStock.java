//#CM41832
//$Id: ASShelfStock.java,v 1.2 2006/10/26 08:24:08 suresh Exp $
package jp.co.daifuku.wms.asrs.entity ;

//#CM41833
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM41834
/**
 * The entity class which adds the Shelf item to Stock.
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2006/01/11</td><td nowrap>yoshino</td>
 * <td>Class created.</td></tr>
 * <tr><td nowrap>2006/01/16</td><td nowrap>Y.Okamura</td>
 * <td>Class name change</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:24:08 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public class ASShelfStock extends Stock
{
	//#CM41835
	/** Column definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM41836
	/** Column definition (NBANK) */

	public static final FieldName NBANK = new FieldName("NBANK");

	//#CM41837
	/** Column definition (NBAY) */

	public static final FieldName NBAY = new FieldName("NBAY");

	//#CM41838
	/** Column definition (NLEVEL) */

	public static final FieldName NLEVEL = new FieldName("NLEVEL");

	//#CM41839
	/** Column definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM41840
	/** Column definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM41841
	/** Column definition (PRESENCE) */

	public static final FieldName PRESENCE = new FieldName("PRESENCE");

	//#CM41842
	/** Column definition (HARDZONEID) */

	public static final FieldName HARDZONEID = new FieldName("HARDZONEID");

	//#CM41843
	/** Column definition (SOFTZONEID) */

	public static final FieldName SOFTZONEID = new FieldName("SOFTZONEID");

	//#CM41844
	/** Column definition (PARENTSTATIONNUMBER) */

	public static final FieldName PARENTSTATIONNUMBER = new FieldName("PARENTSTATIONNUMBER");

	//#CM41845
	/** Column definition (ACCESSNGFLAG) */

	public static final FieldName ACCESSNGFLAG = new FieldName("ACCESSNGFLAG");

	//#CM41846
	/** Column definition (PRIORITY) */

	public static final FieldName PRIORITY = new FieldName("PRIORITY");

	//#CM41847
	/** Column definition (PAIRSTATIONNUMBER) */

	public static final FieldName PAIRSTATIONNUMBER = new FieldName("PAIRSTATIONNUMBER");

	//#CM41848
	/** Column definition (SIDE) */

	public static final FieldName SIDE = new FieldName("SIDE");

	//#CM41849
	/**
	 * Prepare the column name list and generate the instance.
	 */
	public ASShelfStock()
	{
		super() ;
		setInitCreateColumn();
	}

	//#CM41850
	/**
	 * Set the value at station No.
	 * @param arg Station No to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM41851
	/**
	 * Acquire station No.
	 * @return Station No
	 */
	public String getStationNumber()
	{
		return getValue(Shelf.STATIONNUMBER).toString();
	}

	//#CM41852
	/**
	 * Set the value in the bank.
	 * @param arg Bank to be set
	 */
	public void setNBank(int arg)
	{
		setValue(NBANK, new Integer(arg));
	}

	//#CM41853
	/**
	 * Acquire the bank.
	 * @return Bank
	 */
	public int getNBank()
	{
		return getBigDecimal(Shelf.NBANK).intValue();
	}

	//#CM41854
	/**
	 * Set the value in bay.
	 * @param arg Bay to be set
	 */
	public void setNBay(int arg)
	{
		setValue(NBAY, new Integer(arg));
	}

	//#CM41855
	/**
	 * Acquire bay.
	 * @return Bay
	 */
	public int getNBay()
	{
		return getBigDecimal(Shelf.NBAY).intValue();
	}

	//#CM41856
	/**
	 * Set the value at the level.
	 * @param arg Level to be set
	 */
	public void setNLevel(int arg)
	{
		setValue(NLEVEL, new Integer(arg));
	}

	//#CM41857
	/**
	 * Acquire Level.
	 * @return Level
	 */
	public int getNLevel()
	{
		return getBigDecimal(Shelf.NLEVEL).intValue();
	}

	//#CM41858
	/**
	 * Set the value at Warehouse station No.
	 * @param arg Warehouse Station No to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM41859
	/**
	 * Acquire Warehouse station No.
	 * @return Warehouse Station No
	 */
	public String getWHStationNumber()
	{
		return getValue(Shelf.WHSTATIONNUMBER).toString();
	}

	//#CM41860
	/**
	 * Set the value in the status.
	 * @param arg Status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM41861
	/**
	 * Acquire Status
	 * @return Status
	 */
	public int getStatus()
	{
		return getBigDecimal(Shelf.STATUS).intValue();
	}

	//#CM41862
	/**
	 * Set the value in Shelf status.
	 * @param arg Shelf status to be set
	 */
	public void setPresence(int arg)
	{
		setValue(PRESENCE, new Integer(arg));
	}

	//#CM41863
	/**
	 * Acquire Shelf status
	 * @return Shelf status
	 */
	public int getPresence()
	{
		return getBigDecimal(Shelf.PRESENCE).intValue();
	}

	//#CM41864
	/**
	 * Set the value in a hard zone.
	 * @param arg Hard zone to be set
	 */
	public void setHardZoneID(int arg)
	{
		setValue(HARDZONEID, new Integer(arg));
	}

	//#CM41865
	/**
	 * Acquire hard zone.
	 * @return Hard zone
	 */
	public int getHardZoneID()
	{
		return getBigDecimal(Shelf.HARDZONEID).intValue();
	}

	//#CM41866
	/**
	 * Set the value in a soft zone.
	 * @param arg Soft zone to be set
	 */
	public void setSoftZoneID(int arg)
	{
		setValue(SOFTZONEID, new Integer(arg));
	}

	//#CM41867
	/**
	 * Acquire soft zone
	 * @return Soft zone
	 */
	public int getSoftZoneID()
	{
		return getBigDecimal(Shelf.SOFTZONEID).intValue();
	}

	//#CM41868
	/**
	 * Set the value at Parent station No.
	 * @param arg Parent Station No to be set
	 */
	public void setParentStationNumber(String arg)
	{
		setValue(PARENTSTATIONNUMBER, arg);
	}

	//#CM41869
	/**
	 * Acquire Parent station No.
	 * @return Parent Station No
	 */
	public String getParentStationNumber()
	{
		return getValue(Shelf.PARENTSTATIONNUMBER).toString();
	}

	//#CM41870
	/**
	 * Set the value in the shelf flag which cannot be accessed.
	 * @param arg Shelf flag which cannot be accessed to be set
	 */
	public void setAccessNgFlag(int arg)
	{
		setValue(ACCESSNGFLAG, new Integer(arg));
	}

	//#CM41871
	/**
	 * Acquire Shelf flag which cannot be accessed
	 * @return Shelf flag which cannot be accessed
	 */
	public int getAccessNgFlag()
	{
		return getBigDecimal(Shelf.ACCESSNGFLAG).intValue();
	}

	//#CM41872
	/**
	 * Set the value in order of the empty shelf retrieval.
	 * @param arg The order of empty shelf retrieval to be set
	 */
	public void setPriority(int arg)
	{
		setValue(PRIORITY, new Integer(arg));
	}

	//#CM41873
	/**
	 * Acquire order of empty shelf retrieval
	 * @return The order of empty shelf retrieval
	 */
	public int getPriority()
	{
		return getBigDecimal(Shelf.PRIORITY).intValue();
	}

	//#CM41874
	/**
	 * Set the value at Bay station No.
	 * @param arg Bay Station No to be set
	 */
	public void setPairStationNumber(String arg)
	{
		setValue(PAIRSTATIONNUMBER, arg);
	}

	//#CM41875
	/**
	 * Acquire Bay station No.
	 * @return Bay Station No
	 */
	public String getPairStationNumber()
	{
		return getValue(Shelf.PAIRSTATIONNUMBER).toString();
	}

	//#CM41876
	/**
	 * Set the value in this side and the interior shelf division.
	 * @param arg This side and interior shelf division to be set
	 */
	public void setSide(int arg)
	{
		setValue(SIDE, new Integer(arg));
	}

	//#CM41877
	/**
	 * Acquire this side and interior shelf division
	 * @return this side and interior shelf division
	 */
	public int getSide()
	{
		return getBigDecimal(Shelf.SIDE).intValue();
	}

	//#CM41878
	//------------------------------------------------------------
	//#CM41879
	// private methods
	//#CM41880
	//------------------------------------------------------------
	//#CM41881
	/**
	 * Prepare the column name list.(for SearchKey and AlterKey)
	 * To match with Column definition.
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = "" ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + NBANK);
		lst.add(prefix + NBAY);
		lst.add(prefix + NLEVEL);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + STATUS);
		lst.add(prefix + PRESENCE);
		lst.add(prefix + HARDZONEID);
		lst.add(prefix + SOFTZONEID);
		lst.add(prefix + PARENTSTATIONNUMBER);
		lst.add(prefix + ACCESSNGFLAG);
		lst.add(prefix + PRIORITY);
		lst.add(prefix + PAIRSTATIONNUMBER);
		lst.add(prefix + SIDE);
	}
}
