package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.FlatStockoutDetailEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class FlatStockoutPopupLowListProxy
{
	private ListCell list = null;

	public FlatStockoutPopupLowListProxy(ListCell list)
	{
		this.list = list;
	}

	private int SELECT_COLUMN = 1;

	private int TICKET_NO_COLUMN = 2;

	private int STORAGE_COUNT_COLUMN = 3;

	private int STOCKOUT_COUNT_COLUMN = 4;

	private int STORAGE_REMAIN_COUNT_COLUMN = 5;

	private int STOCKOUT_REMAIN_COUNT_COLUMN = 6;

	private int RECEPTION_DATETIME_COLUMN = 7;

	private int SYSTEM_ID_COLUMN = 8;

	private int INSTOCK_COUNT_COLUMN = 9;

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}

	public int getSELECT_COLUMN()
	{
		return SELECT_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public int getSTORAGE_COUNT_COLUMN()
	{
		return STORAGE_COUNT_COLUMN;
	}

	public int getSTOCKOUT_COUNT_COLUMN()
	{
		return STOCKOUT_COUNT_COLUMN;
	}

	public int getSTORAGE_REMAIN_COUNT_COLUMN()
	{
		return STORAGE_REMAIN_COUNT_COLUMN;
	}

	public int getSTOCKOUT_REMAIN_COUNT_COLUMN()
	{
		return STOCKOUT_REMAIN_COUNT_COLUMN;
	}

	public int getRECEPTION_DATETIME_COLUMN()
	{
		return RECEPTION_DATETIME_COLUMN;
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public void setSelected(boolean selected)
	{
		list.setChecked(SELECT_COLUMN, selected);
	}

	public boolean isSelected()
	{
		return list.getChecked(SELECT_COLUMN);
	}

	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}

	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}

	public int getStorageCount()
	{
		String storageCount = list.getValue(STORAGE_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(STORAGE_COUNT_COLUMN)
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

	public void setStorageCount(long storageCount)
	{
		list.setValue(STORAGE_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(storageCount));
	}

	public int getInstockCount()
	{
		String storageCount = list.getValue(INSTOCK_COUNT_COLUMN);
		if (storageCount != null)
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

	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(instockCount));
	}

	public int getStockoutCount()
	{
		String storageCount = list.getValue(STOCKOUT_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(STOCKOUT_COUNT_COLUMN)
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

	public void setStockoutCount(long stockoutCount)
	{
		list.setValue(STOCKOUT_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(stockoutCount));
	}

	public int getStorageRemainCount()
	{
		String storageCount = list.getValue(STORAGE_REMAIN_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						STORAGE_REMAIN_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setStorageRemainCount(long storageRemainCount)
	{
		list.setValue(STORAGE_REMAIN_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(storageRemainCount));
	}

	public long getStockoutRemainCount()
	{
		String storageCount = list.getValue(STOCKOUT_REMAIN_COUNT_COLUMN);
		if (storageCount != null)
		{
			try
			{
				return Long.parseLong(list.getValue(
						STOCKOUT_REMAIN_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setStockoutRemainCount(long stockoutRemainCount)
	{
		list.setValue(STOCKOUT_REMAIN_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(stockoutRemainCount));
	}

	public void setReceptionDatetime(String receptionDatetime)
	{
		list.setValue(RECEPTION_DATETIME_COLUMN, receptionDatetime);
	}

	public String getReceptionDatetime()
	{
		return list.getValue(RECEPTION_DATETIME_COLUMN);
	}

	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}

	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}

	public FlatStockoutDetailEntity getRowValue()
	{
		FlatStockoutDetailEntity entity = new FlatStockoutDetailEntity();

		entity.setReceptionDatetime(getReceptionDatetime());
		entity.setStockoutCount(getStockoutCount());
		entity.setStockoutRemainCount(getStockoutRemainCount());
		entity.setStorageCount(getStorageCount());
		entity.setStorageRemainCount(getStorageRemainCount());
		entity.setSystemId(getSystemId());
		entity.setTicketNo(getTicketNo());
		entity.setInstockCount(getInstockCount());

		return entity;
	}

	public long setRowValue(FlatStockoutDetailEntity entity,
			long stockoutRemainCount)
	{
		setSelected(true);
		setTicketNo(entity.getTicketNo());
		setStorageCount(entity.getStorageCount());
		setReceptionDatetime(StringUtils.formatDateAndTimeFromDBToPage(entity
				.getReceptionDatetime()));
		setSystemId(entity.getSystemId());
		setInstockCount(entity.getInstockCount());

		return setCounts(entity.getStorageCount(), stockoutRemainCount);
	}

	public long setCounts(int storageCount, long stockoutRemainCount)
	{
		if (stockoutRemainCount >= storageCount)
		{
			setStockoutCount(storageCount);
			setStorageRemainCount(0);
			stockoutRemainCount = stockoutRemainCount - storageCount;
		}
		else
		{

			setStockoutCount(stockoutRemainCount);
			setStorageRemainCount(storageCount - stockoutRemainCount);
			stockoutRemainCount = 0;
		}
		setStockoutRemainCount(stockoutRemainCount);

		return stockoutRemainCount;
	}
}
