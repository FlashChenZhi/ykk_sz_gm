<<<<<<< HEAD
package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class LocationStorageInfoListProxy
{
    private final ListCell list;

    private final FixedListCell head;

    private final int HEAD_DEPO_ROW = 1;

    private final int HEAD_DEPO_COLUMN = 1;

    private final int HEAD_LOCATION_STATUS_ROW = 2;

    private final int HEAD_LOCATION_STATUS_COLUMN = 1;

    private final int HEAD_WEIGHT_REPORT_FLAG_ROW = 3;

    private final int HEAD_WEIGHT_REPORT_FLAG_COLUMN = 1;

    private final int HEAD_LOCATION_NO_RANGE_ROW = 4;

    private final int HEAD_LOCATION_NO_RANGE_COLUMN = 1;

    private final int NO_COLUMN = 1;

    private final int LOCATION_NO_COLUMN = 2;

    private final int ITEM_CODE_COLUMN = 3;

    private final int ITEM_NAME_COLUMN = 4;

    private final int COLOR_COLUMN = 5;

    private final int TICKET_NO_COLUMN = 6;

    private final int WEIGHT_REPORT_FLAG_COLUMN = 7;

    private final int BUCKET_NO_COLUMN = 8;

    private final int INSTOCK_COUNT_COLUMN = 9;

    private final int STOCKIN_DATE_TIME_COLUMN = 10;

    private final int MEMO_COLUMN = 11;

    public LocationStorageInfoListProxy(FixedListCell head, ListCell list)
    {
	this.head = head;
	this.list = list;
    }

    public LocationStorageInfoListProxy(ListCell list)
    {
	this.head = null;
	this.list = list;
    }

    public int getBUCKET_NO_COLUMN()
    {
	return BUCKET_NO_COLUMN;
    }

    public String getBucketNo()
    {
	return list.getValue(BUCKET_NO_COLUMN);
    }

    public String getColor()
    {
	return list.getValue(COLOR_COLUMN);
    }

    public int getCOLOR_COLUMN()
    {
	return COLOR_COLUMN;
    }

    public int getHEAD_DEPO_COLUMN()
    {
	return HEAD_DEPO_COLUMN;
    }

    public int getHEAD_DEPO_ROW()
    {
	return HEAD_DEPO_ROW;
    }

    public int getHEAD_LOCATION_NO_RANGE_COLUMN()
    {
	return HEAD_LOCATION_NO_RANGE_COLUMN;
    }

    public int getHEAD_LOCATION_NO_RANGE_ROW()
    {
	return HEAD_LOCATION_NO_RANGE_ROW;
    }

    public int getHEAD_LOCATION_STATUS_COLUMN()
    {
	return HEAD_LOCATION_STATUS_COLUMN;
    }

    public int getHEAD_LOCATION_STATUS_ROW()
    {
	return HEAD_LOCATION_STATUS_ROW;
    }

    public int getHEAD_WEIGHT_REPORT_FLAG_COLUMN()
    {
	return HEAD_WEIGHT_REPORT_FLAG_COLUMN;
    }

    public int getHEAD_WEIGHT_REPORT_FLAG_ROW()
    {
	return HEAD_WEIGHT_REPORT_FLAG_ROW;
    }

    public int getMEMO_COLUMN() {
        return MEMO_COLUMN;
    }

    public String getHeadDepo()
    {
	head.setCurrentRow(HEAD_DEPO_ROW);

	return head.getValue(HEAD_DEPO_COLUMN);
    }

    public String getHeadLocationNoRange()
    {
	head.setCurrentRow(HEAD_LOCATION_NO_RANGE_ROW);

	return head.getValue(HEAD_LOCATION_NO_RANGE_COLUMN);
    }

    public String getHeadLocationStatus()
    {
	head.setCurrentRow(HEAD_LOCATION_STATUS_ROW);

	return head.getValue(HEAD_LOCATION_STATUS_COLUMN);
    }

    public String getHeadWeightReportFlag()
    {
	head.setCurrentRow(HEAD_WEIGHT_REPORT_FLAG_ROW);

	return head.getValue(HEAD_WEIGHT_REPORT_FLAG_COLUMN);
    }

    public int getINSTOCK_COUNT_COLUMN()
    {
	return INSTOCK_COUNT_COLUMN;
    }

    public int getInstockCount()
    {
	String instockCount = list.getValue(INSTOCK_COUNT_COLUMN);
	if (instockCount != null)
	{
	    try
	    {
		return Integer.parseInt(list.getValue(INSTOCK_COUNT_COLUMN)
			.replaceAll(",", ""));
	    }
	    catch (Exception ex)
	    {
		return 0;
	    }
	}
	else
	{
	    return 0;
	}
    }

    public int getITEM_CODE_COLUMN()
    {
	return ITEM_CODE_COLUMN;
    }

    public int getITEM_NAME_COLUMN()
    {
	return ITEM_NAME_COLUMN;
    }

    public String getItemCode()
    {
	return list.getValue(ITEM_CODE_COLUMN);
    }

    public String getItemNAME()
    {
	return list.getValue(ITEM_NAME_COLUMN);
    }

    public int getLOCATION_NO_COLUMN()
    {
	return LOCATION_NO_COLUMN;
    }

    public String getLocationNo()
    {
	return list.getValue(LOCATION_NO_COLUMN);
    }

    public String getNo()
    {
	return list.getValue(NO_COLUMN);
    }

    public int getNO_COLUMN()
    {
	return NO_COLUMN;
    }

    public int getSTOCKIN_DATE_TIME_COLUMN()
    {
	return STOCKIN_DATE_TIME_COLUMN;
    }

    public String getStockinDateTime()
    {
	return list.getValue(STOCKIN_DATE_TIME_COLUMN);
    }

    public int getTICKET_NO_COLUMN()
    {
	return TICKET_NO_COLUMN;
    }

    public String getTicketNo()
    {
	return list.getValue(TICKET_NO_COLUMN);
    }

    public int getWEIGHT_REPORT_FLAG_COLUMN()
    {
	return WEIGHT_REPORT_FLAG_COLUMN;
    }

    public String getWeightReportFlag()
    {
	return list.getValue(WEIGHT_REPORT_FLAG_COLUMN);
    }

    public void setBucketNo(String bucketNo)
    {
	list.setValue(BUCKET_NO_COLUMN, bucketNo);
    }

    public void setColor(String color)
    {
	list.setValue(COLOR_COLUMN, color);
    }

    public String getMemo()
    {
        return list.getValue(MEMO_COLUMN);
    }

    public void setMemo(String memo)
    {
        list.setValue(MEMO_COLUMN, memo);
    }

    public void setHeadDepo(String depo)
    {
	head.setCurrentRow(HEAD_DEPO_ROW);

	head.setValue(HEAD_DEPO_COLUMN, depo);

    }

    public void setHeadLocationNoRange(String locationNo)
    {
	head.setCurrentRow(HEAD_LOCATION_NO_RANGE_ROW);

	head.setValue(HEAD_LOCATION_NO_RANGE_COLUMN, locationNo);

    }

    public void setHeadLocationStatus(String locationStatus)
    {
	head.setCurrentRow(HEAD_LOCATION_STATUS_ROW);

	head.setValue(HEAD_LOCATION_STATUS_COLUMN, locationStatus);

    }

    public void setHeadValue(LocationStorageInfoHead head)
    {
	setHeadDepo(head.getDepo());
	String locationStatus = "";
	for (int i = 0; i < head.getLocationStatus().size(); i++)
	{
	    locationStatus += (String) head.getLocationStatus().get(i);
	    if (i + 1 < head.getLocationStatus().size())
	    {
		locationStatus += "、";
	    }

	}
	setHeadLocationStatus(locationStatus);
	String weightReportFlags = "";
	for (int i = 0; i < head.getWeightReportFlag().size(); i++)
	{
	    weightReportFlags += (String) head.getWeightReportFlag().get(i);
	    if (i + 1 < head.getWeightReportFlag().size())
	    {
		weightReportFlags += "、";
	    }

	}
	setHeadWeightReportFlag(weightReportFlags);
	if (head.isRangeSet())
	{
	    setHeadLocationNoRange(StringUtils
		    .formatLocationNoFromDBToPage(head.getLocationNoFrom())
		    + "～"
		    + StringUtils.formatLocationNoFromDBToPage(head
			    .getLocationNoTo()));
	}
	else
	{
	    setHeadLocationNoRange(StringUtils
		    .formatLocationNoFromDBToPage(head.getLocationNoFrom()));
	}
    }

    public void setHeadWeightReportFlag(String weightReportFlag)
    {
	head.setCurrentRow(HEAD_WEIGHT_REPORT_FLAG_ROW);

	head.setValue(HEAD_WEIGHT_REPORT_FLAG_COLUMN, weightReportFlag);
    }

    public void setInstockCount(int instockCount)
    {
	list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
		.format(instockCount));
    }

    public void setItemCode(String itemCode)
    {
	list.setValue(ITEM_CODE_COLUMN, itemCode);
    }

    public void setItemName(String itemName)
    {
	list.setValue(ITEM_NAME_COLUMN, itemName);
    }

    public void setLocationNo(String locationNo)
    {
	list.setValue(LOCATION_NO_COLUMN, locationNo);
    }

    public void setNo(String no)
    {
	list.setValue(NO_COLUMN, no);
    }

    public void setRowValueByEntity(LocationStorageInfoEntity entity, int rowNum)
    {
	setNo(String.valueOf(rowNum));
	setStockinDateTime(StringUtils.formatDateAndTimeFromDBToPage(entity
		.getStockinDateTime()));
	setItemCode(entity.getItemCode());
	setItemName(entity.getItemName1());
	setColor(entity.getColor());
	setTicketNo(entity.getTicketNo());
	setWeightReportFlag(entity.getWeightReportFlag());
	setBucketNo(entity.getBucketNo());
	if (!StringUtils.IsNullOrEmpty(entity.getLocationStatus()))
	{
	    setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
		    .getLocationNo())
		    + ":" + entity.getLocationStatus());
	}
	else
	{
	    setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
		    .getLocationNo()));
	}
	setInstockCount(entity.getInstockCount());
        setMemo(entity.getMemo());
    }

    public void setStockinDateTime(String dateTime)
    {
	list.setValue(STOCKIN_DATE_TIME_COLUMN, dateTime);
    }

    public void setTicketNo(String ticketNo)
    {
	list.setValue(TICKET_NO_COLUMN, ticketNo);
    }

    public void setWeightReportFlag(String weightReportFlag)
    {
	list.setValue(WEIGHT_REPORT_FLAG_COLUMN, weightReportFlag);
    }

}
=======
package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class LocationStorageInfoListProxy
{
    private final ListCell list;

    private final FixedListCell head;

    private final int HEAD_DEPO_ROW = 1;

    private final int HEAD_DEPO_COLUMN = 1;

    private final int HEAD_LOCATION_STATUS_ROW = 2;

    private final int HEAD_LOCATION_STATUS_COLUMN = 1;

    private final int HEAD_WEIGHT_REPORT_FLAG_ROW = 3;

    private final int HEAD_WEIGHT_REPORT_FLAG_COLUMN = 1;

    private final int HEAD_LOCATION_NO_RANGE_ROW = 4;

    private final int HEAD_LOCATION_NO_RANGE_COLUMN = 1;

    private final int NO_COLUMN = 1;

    private final int LOCATION_NO_COLUMN = 2;

    private final int ITEM_CODE_COLUMN = 3;

    private final int ITEM_NAME_COLUMN = 4;

    private final int COLOR_COLUMN = 5;

    private final int TICKET_NO_COLUMN = 6;

    private final int WEIGHT_REPORT_FLAG_COLUMN = 7;

    private final int BUCKET_NO_COLUMN = 8;

    private final int INSTOCK_COUNT_COLUMN = 9;

    private final int STOCKIN_DATE_TIME_COLUMN = 10;

    public LocationStorageInfoListProxy(FixedListCell head, ListCell list)
    {
	this.head = head;
	this.list = list;
    }

    public LocationStorageInfoListProxy(ListCell list)
    {
	this.head = null;
	this.list = list;
    }

    public int getBUCKET_NO_COLUMN()
    {
	return BUCKET_NO_COLUMN;
    }

    public String getBucketNo()
    {
	return list.getValue(BUCKET_NO_COLUMN);
    }

    public String getColor()
    {
	return list.getValue(COLOR_COLUMN);
    }

    public int getCOLOR_COLUMN()
    {
	return COLOR_COLUMN;
    }

    public int getHEAD_DEPO_COLUMN()
    {
	return HEAD_DEPO_COLUMN;
    }

    public int getHEAD_DEPO_ROW()
    {
	return HEAD_DEPO_ROW;
    }

    public int getHEAD_LOCATION_NO_RANGE_COLUMN()
    {
	return HEAD_LOCATION_NO_RANGE_COLUMN;
    }

    public int getHEAD_LOCATION_NO_RANGE_ROW()
    {
	return HEAD_LOCATION_NO_RANGE_ROW;
    }

    public int getHEAD_LOCATION_STATUS_COLUMN()
    {
	return HEAD_LOCATION_STATUS_COLUMN;
    }

    public int getHEAD_LOCATION_STATUS_ROW()
    {
	return HEAD_LOCATION_STATUS_ROW;
    }

    public int getHEAD_WEIGHT_REPORT_FLAG_COLUMN()
    {
	return HEAD_WEIGHT_REPORT_FLAG_COLUMN;
    }

    public int getHEAD_WEIGHT_REPORT_FLAG_ROW()
    {
	return HEAD_WEIGHT_REPORT_FLAG_ROW;
    }

    public String getHeadDepo()
    {
	head.setCurrentRow(HEAD_DEPO_ROW);

	return head.getValue(HEAD_DEPO_COLUMN);
    }

    public String getHeadLocationNoRange()
    {
	head.setCurrentRow(HEAD_LOCATION_NO_RANGE_ROW);

	return head.getValue(HEAD_LOCATION_NO_RANGE_COLUMN);
    }

    public String getHeadLocationStatus()
    {
	head.setCurrentRow(HEAD_LOCATION_STATUS_ROW);

	return head.getValue(HEAD_LOCATION_STATUS_COLUMN);
    }

    public String getHeadWeightReportFlag()
    {
	head.setCurrentRow(HEAD_WEIGHT_REPORT_FLAG_ROW);

	return head.getValue(HEAD_WEIGHT_REPORT_FLAG_COLUMN);
    }

    public int getINSTOCK_COUNT_COLUMN()
    {
	return INSTOCK_COUNT_COLUMN;
    }

    public int getInstockCount()
    {
	String instockCount = list.getValue(INSTOCK_COUNT_COLUMN);
	if (instockCount != null)
	{
	    try
	    {
		return Integer.parseInt(list.getValue(INSTOCK_COUNT_COLUMN)
			.replaceAll(",", ""));
	    }
	    catch (Exception ex)
	    {
		return 0;
	    }
	}
	else
	{
	    return 0;
	}
    }

    public int getITEM_CODE_COLUMN()
    {
	return ITEM_CODE_COLUMN;
    }

    public int getITEM_NAME_COLUMN()
    {
	return ITEM_NAME_COLUMN;
    }

    public String getItemCode()
    {
	return list.getValue(ITEM_CODE_COLUMN);
    }

    public String getItemNAME()
    {
	return list.getValue(ITEM_NAME_COLUMN);
    }

    public int getLOCATION_NO_COLUMN()
    {
	return LOCATION_NO_COLUMN;
    }

    public String getLocationNo()
    {
	return list.getValue(LOCATION_NO_COLUMN);
    }

    public String getNo()
    {
	return list.getValue(NO_COLUMN);
    }

    public int getNO_COLUMN()
    {
	return NO_COLUMN;
    }

    public int getSTOCKIN_DATE_TIME_COLUMN()
    {
	return STOCKIN_DATE_TIME_COLUMN;
    }

    public String getStockinDateTime()
    {
	return list.getValue(STOCKIN_DATE_TIME_COLUMN);
    }

    public int getTICKET_NO_COLUMN()
    {
	return TICKET_NO_COLUMN;
    }

    public String getTicketNo()
    {
	return list.getValue(TICKET_NO_COLUMN);
    }

    public int getWEIGHT_REPORT_FLAG_COLUMN()
    {
	return WEIGHT_REPORT_FLAG_COLUMN;
    }

    public String getWeightReportFlag()
    {
	return list.getValue(WEIGHT_REPORT_FLAG_COLUMN);
    }

    public void setBucketNo(String bucketNo)
    {
	list.setValue(BUCKET_NO_COLUMN, bucketNo);
    }

    public void setColor(String color)
    {
	list.setValue(COLOR_COLUMN, color);
    }

    public void setHeadDepo(String depo)
    {
	head.setCurrentRow(HEAD_DEPO_ROW);

	head.setValue(HEAD_DEPO_COLUMN, depo);

    }

    public void setHeadLocationNoRange(String locationNo)
    {
	head.setCurrentRow(HEAD_LOCATION_NO_RANGE_ROW);

	head.setValue(HEAD_LOCATION_NO_RANGE_COLUMN, locationNo);

    }

    public void setHeadLocationStatus(String locationStatus)
    {
	head.setCurrentRow(HEAD_LOCATION_STATUS_ROW);

	head.setValue(HEAD_LOCATION_STATUS_COLUMN, locationStatus);

    }

    public void setHeadValue(LocationStorageInfoHead head)
    {
	setHeadDepo(head.getDepo());
	String locationStatus = "";
	for (int i = 0; i < head.getLocationStatus().size(); i++)
	{
	    locationStatus += (String) head.getLocationStatus().get(i);
	    if (i + 1 < head.getLocationStatus().size())
	    {
		locationStatus += "、";
	    }

	}
	setHeadLocationStatus(locationStatus);
	String weightReportFlags = "";
	for (int i = 0; i < head.getWeightReportFlag().size(); i++)
	{
	    weightReportFlags += (String) head.getWeightReportFlag().get(i);
	    if (i + 1 < head.getWeightReportFlag().size())
	    {
		weightReportFlags += "、";
	    }

	}
	setHeadWeightReportFlag(weightReportFlags);
	if (head.isRangeSet())
	{
	    setHeadLocationNoRange(StringUtils
		    .formatLocationNoFromDBToPage(head.getLocationNoFrom())
		    + "～"
		    + StringUtils.formatLocationNoFromDBToPage(head
			    .getLocationNoTo()));
	}
	else
	{
	    setHeadLocationNoRange(StringUtils
		    .formatLocationNoFromDBToPage(head.getLocationNoFrom()));
	}
    }

    public void setHeadWeightReportFlag(String weightReportFlag)
    {
	head.setCurrentRow(HEAD_WEIGHT_REPORT_FLAG_ROW);

	head.setValue(HEAD_WEIGHT_REPORT_FLAG_COLUMN, weightReportFlag);
    }

    public void setInstockCount(int instockCount)
    {
	list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
		.format(instockCount));
    }

    public void setItemCode(String itemCode)
    {
	list.setValue(ITEM_CODE_COLUMN, itemCode);
    }

    public void setItemName(String itemName)
    {
	list.setValue(ITEM_NAME_COLUMN, itemName);
    }

    public void setLocationNo(String locationNo)
    {
	list.setValue(LOCATION_NO_COLUMN, locationNo);
    }

    public void setNo(String no)
    {
	list.setValue(NO_COLUMN, no);
    }

    public void setRowValueByEntity(LocationStorageInfoEntity entity, int rowNum)
    {
	setNo(String.valueOf(rowNum));
	setStockinDateTime(StringUtils.formatDateAndTimeFromDBToPage(entity
		.getStockinDateTime()));
	setItemCode(entity.getItemCode());
	setItemName(entity.getItemName1());
	setColor(entity.getColor());
	setTicketNo(entity.getTicketNo());
	setWeightReportFlag(entity.getWeightReportFlag());
	setBucketNo(entity.getBucketNo());
	if (!StringUtils.IsNullOrEmpty(entity.getLocationStatus()))
	{
	    setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
		    .getLocationNo())
		    + ":" + entity.getLocationStatus());
	}
	else
	{
	    setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
		    .getLocationNo()));
	}
	setInstockCount(entity.getInstockCount());
    }

    public void setStockinDateTime(String dateTime)
    {
	list.setValue(STOCKIN_DATE_TIME_COLUMN, dateTime);
    }

    public void setTicketNo(String ticketNo)
    {
	list.setValue(TICKET_NO_COLUMN, ticketNo);
    }

    public void setWeightReportFlag(String weightReportFlag)
    {
	list.setValue(WEIGHT_REPORT_FLAG_COLUMN, weightReportFlag);
    }

}
>>>>>>> 86f31489e26519cdd393a5d2cfa4c1d9333ee3b3
