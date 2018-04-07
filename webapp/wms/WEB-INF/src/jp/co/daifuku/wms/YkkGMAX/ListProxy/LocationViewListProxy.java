package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class LocationViewListProxy
{
	public LocationViewListProxy(ListCell emptyList,ListCell list)
	{
		this.emptyList = emptyList;
		this.list = list;
	}
	
	private ListCell emptyList;
	
	private ListCell list;
	
	private int EMPTY_NO_COLUMN = 1;
	
	private int EMPTY_LOCATION_COLUMN = 2;
	
	private int NO_COLUMN = 1;
	
	private int LOCATION_COLUMN = 2;
	
	private int LOCATION_STATUS_COLUMN = 3;

	public int getEMPTY_LOCATION_COLUMN()
	{
		return EMPTY_LOCATION_COLUMN;
	}

	public int getEMPTY_NO_COLUMN()
	{
		return EMPTY_NO_COLUMN;
	}

	public int getLOCATION_COLUMN()
	{
		return LOCATION_COLUMN;
	}

	public int getLOCATION_STATUS_COLUMN()
	{
		return LOCATION_STATUS_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}
	
	public void setEmptyNo(String no)
	{
		emptyList.setValue(EMPTY_NO_COLUMN, no);
	}
	
	public String getEmptyNo()
	{
		return emptyList.getValue(EMPTY_NO_COLUMN);
	}
	
	public void setEmptyLocationNo(String locationNo)
	{
		emptyList.setValue(EMPTY_LOCATION_COLUMN, locationNo);
	}
	
	public String getEmptyLocationNo()
	{
		return emptyList.getValue(EMPTY_LOCATION_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN,no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_COLUMN, locationNo);
	}
	
	public String getLocationNo()
	{
		return list.getValue(LOCATION_COLUMN);
	}
	
	public void setLocationStatus(String locationStatus)
	{
		list.setValue(LOCATION_STATUS_COLUMN, locationStatus);
	}
	
	public String getLocationStatus()
	{
		return list.getValue(LOCATION_STATUS_COLUMN);
	}
	
	public void setEmptyRowValue(LocationViewEntity entity,int rowNum)
	{
		setEmptyNo(String.valueOf(rowNum));
		setEmptyLocationNo(StringUtils.formatLocationNoFromDBToPage(entity.getLocationNo()));
	}
	
	public void setRowValue(LocationViewEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity.getLocationNo()));
		setLocationStatus(entity.getLocationStatus());
	}
}
