package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.UnmanagedStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class UnmanagedStockoutPopupListProxy
{
	private final String UNMANAGED_STOCKOUT_DETAIL_LIST = "UNMANAGED_STOCKOUT_DETAIL_LIST";

	public UnmanagedStockoutPopupListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}
	
	private ListCell list;
	
	private Page page;
	
	private int ALL_COLUMN = 1;
	
	private int LOCATION_NO_COLUMN = 2;
	
	private int ITEM_CODE_COLUMN = 3;
	
	private int ITEM_NAME_1_COLUMN = 4;
	
	private int ITEM_NAME_2_COLUMN = 10;

	private int ITEM_NAME_3_COLUMN = 11;

	private int COLOR_COLUMN = 5;
	
	private int INSTOCK_COUNT_COLUMN = 6;
	
	private int STOCKIN_TIME_COLUMN = 7;
	
	private int ORIGINAL_LOCATION_NO_COLUMN = 8;
	
	private int SYSTEM_ID_COLUMN = 9;

	public int getALL_COLUMN()
	{
		return ALL_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getITEM_NAME_1_COLUMN()
	{
		return ITEM_NAME_1_COLUMN;
	}

	public int getITEM_NAME_2_COLUMN()
	{
		return ITEM_NAME_2_COLUMN;
	}

	public int getITEM_NAME_3_COLUMN()
	{
		return ITEM_NAME_3_COLUMN;
	}

	public int getLOCATION_NO_COLUMN()
	{
		return LOCATION_NO_COLUMN;
	}

	public int getORIGINAL_LOCATION_NO_COLUMN()
	{
		return ORIGINAL_LOCATION_NO_COLUMN;
	}

	public int getSTOCKIN_TIME_COLUMN()
	{
		return STOCKIN_TIME_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}
	
	public boolean getAll()
	{
		return list.getChecked(ALL_COLUMN);
	}
	
	public void setAll(boolean all)
	{
		list.setChecked(ALL_COLUMN, all);
	}
	
	public String getLocationNo()
	{
		return list.getValue(LOCATION_NO_COLUMN);
	}
	
	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_NO_COLUMN, locationNo);
	}
	
	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}

	public String getItemName1()
	{
		return list.getValue(ITEM_NAME_1_COLUMN);
	}

	public void setItemName1(String itemName1)
	{
		list.setValue(ITEM_NAME_1_COLUMN, itemName1);
	}

	public String getItemName2()
	{
		return list.getValue(ITEM_NAME_2_COLUMN);
	}

	public void setItemName2(String itemName2)
	{
		list.setValue(ITEM_NAME_2_COLUMN, itemName2);
	}

	public String getItemName3()
	{
		return list.getValue(ITEM_NAME_3_COLUMN);
	}

	public void setItemName3(String itemName3)
	{
		list.setValue(ITEM_NAME_3_COLUMN, itemName3);
	}

	public String getColor()
	{
		return list.getValue(COLOR_COLUMN);
	}

	public void setColor(String color)
	{
		list.setValue(COLOR_COLUMN, color);
	}
	
	public int getInstockCount()
	{
		String instockCount = list.getValue(INSTOCK_COUNT_COLUMN);
		if (instockCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						INSTOCK_COUNT_COLUMN).replaceAll(",", ""));
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
	
	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(instockCount));
	}
	
	public String getStockinTime()
	{
		return list.getValue(STOCKIN_TIME_COLUMN);
	}
	
	public void setStockinTime(String stockinTime)
	{
		list.setValue(STOCKIN_TIME_COLUMN, stockinTime);
	}
	
	public String getOriginalLocationNo()
	{
		return list.getValue(ORIGINAL_LOCATION_NO_COLUMN);
	}
	
	public void setOriginalLocationNo(String locationNo)
	{
		list.setValue(ORIGINAL_LOCATION_NO_COLUMN, locationNo);
	}
	
	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}
	
	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}
	
	public UnmanagedStockoutDetailEntity getUnmanagedStockoutDetailEntity()
	{
		UnmanagedStockoutDetailEntity entity = new UnmanagedStockoutDetailEntity();
		
		entity.setLocationNo(StringUtils.formatLocationNoFromPageToDB(getLocationNo()));
		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setColor(getColor());
		entity.setInstockCount(getInstockCount());
		entity.setStockinDateTime(StringUtils.formatDateAndTimeFromPageToDB(getStockinTime()));
		entity.setOriginalLocationNo(getOriginalLocationNo());
		entity.setSystemId(getSystemId());
		
		return entity;
	}
	
	public void setRowValueByEntity(UnmanagedStockoutDetailEntity entity)
	{
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity.getLocationNo()));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setInstockCount(entity.getInstockCount());
		setStockinTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getStockinDateTime()));
		setOriginalLocationNo(entity.getOriginalLocationNo());
		setSystemId(entity.getSystemId());
		if(SystemIdSortableHandler.contain((ArrayList)page.getSession().getAttribute(UNMANAGED_STOCKOUT_DETAIL_LIST), entity.getSystemId()))
		{
			setAll(true);
		}
		else
		{
			setAll(false);
		}
	}
}
