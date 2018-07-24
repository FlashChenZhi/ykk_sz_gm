package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.StorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.StorageInfoHead;

public class StorageInfoListProxy
{
	public StorageInfoListProxy( FixedListCell head,ListCell list)
	{
		this.head = head;
		this.list = list;
	}
	public StorageInfoListProxy(ListCell list)
	{
		this.head = null;
		this.list = list;
	}
	private ListCell list;
	
	private FixedListCell head;
	
	private int HEAD_ITEM_CODE_ROW = 1;
	
	private int HEAD_ITEM_CODE_COLUMN = 1;
	
	private int HEAD_COLOR_CODE_ROW = 2;
	
	private int HEAD_COLOR_CODE_COLUMN = 1;
	
	private int NO_COLUMN = 1;
	
	private int ITEM_CODE_COLUMN = 2;

	private int ITEM_NAME_1_COLUMN = 3;

	private int ITEM_NAME_2_COLUMN = 9;

	private int ITEM_NAME_3_COLUMN = 10;

	private int COLOR_COLUMN = 4;
	
	private int AUTO_COUNT_COLUMN = 5;
	
	private int FLAT_COUNT_COLUMN = 6;
	
	private int TOTAL_INSTOCK_COUNT_COLUMN = 7;
	
	private int NOT_STOCKIN_COUNT_COLUMN = 8;
	
	public int getAUTO_COUNT_COLUMN()
	{
		return AUTO_COUNT_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getFLAT_COUNT_COLUMN()
	{
		return FLAT_COUNT_COLUMN;
	}

	public int getHEAD_COLOR_CODE_COLUMN()
	{
		return HEAD_COLOR_CODE_COLUMN;
	}

	public int getHEAD_COLOR_CODE_ROW()
	{
		return HEAD_COLOR_CODE_ROW;
	}

	public int getHEAD_ITEM_CODE_COLUMN()
	{
		return HEAD_ITEM_CODE_COLUMN;
	}

	public int getHEAD_ITEM_CODE_ROW()
	{
		return HEAD_ITEM_CODE_ROW;
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

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getNOT_STOCKIN_COUNT_COLUMN()
	{
		return NOT_STOCKIN_COUNT_COLUMN;
	}

	public int getTOTAL_INSTOCK_COUNT_COLUMN()
	{
		return TOTAL_INSTOCK_COUNT_COLUMN;
	}
	
	public void setHeadItemCode(String itemCode)
	{
		head.setCurrentRow(HEAD_ITEM_CODE_ROW);
		
		head.setValue(HEAD_ITEM_CODE_COLUMN, itemCode);
		
	}
	
	public String getHeadItemCode()
	{
		head.setCurrentRow(HEAD_ITEM_CODE_ROW);
		
		return head.getValue(HEAD_ITEM_CODE_COLUMN);
	}
	
	public void setHeadColorCode(String colorCode)
	{
		head.setCurrentRow(HEAD_COLOR_CODE_ROW);
		
		head.setValue(HEAD_COLOR_CODE_COLUMN, colorCode);
		
	}
	
	public String getHeadColorCode()
	{
		head.setCurrentRow(HEAD_COLOR_CODE_ROW);
		
		return head.getValue(HEAD_COLOR_CODE_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
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
	
	public long getAutoCount()
	{
		String autoCount = list.getValue(AUTO_COUNT_COLUMN);
		if (autoCount != null)
		{
			try
			{
				return Long.parseLong(list.getValue(AUTO_COUNT_COLUMN)
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

	public void setAutoCount(long autoCount)
	{
		list.setValue(AUTO_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(autoCount));
	}
	
	public long getFlatCount()
	{
		String flatCount = list.getValue(FLAT_COUNT_COLUMN);
		if (flatCount != null)
		{
			try
			{
				return Long.parseLong(list.getValue(FLAT_COUNT_COLUMN)
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

	public void setFlatCount(long flatCount)
	{
		list.setValue(FLAT_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(flatCount));
	}
	
	public long getTotalInstockCount()
	{
		String totalInstockCount = list.getValue(TOTAL_INSTOCK_COUNT_COLUMN);
		if (totalInstockCount != null)
		{
			try
			{
				return Long.parseLong(list.getValue(TOTAL_INSTOCK_COUNT_COLUMN)
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

	public void setTotalInstockCount(long totalInstockCount)
	{
		list.setValue(TOTAL_INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(totalInstockCount));
	}
	
	public long getNotStockinCount()
	{
		String notStockinCount = list.getValue(NOT_STOCKIN_COUNT_COLUMN);
		if (notStockinCount != null)
		{
			try
			{
				return Long.parseLong(list.getValue(NOT_STOCKIN_COUNT_COLUMN)
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

	public void setNotStockinCount(long notStockinCount)
	{
		list.setValue(NOT_STOCKIN_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(notStockinCount));
	}
	
	public void setHeadVlaue(StorageInfoHead head)
	{
		setHeadItemCode(head.getItemCode());
		setHeadColorCode(head.getColorCode());
	}
	
	public void setRowValueByEntity(StorageInfoEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setAutoCount(entity.getAutoCount());
		setFlatCount(entity.getFlatCount());
		setTotalInstockCount(entity.getTotalInstockCount());
		setNotStockinCount(entity.getNotStockinCount());
	}
}
