package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.PendingTransferDataCencelOrPrioritizeEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.RetrievalPlankeySortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class PendingTransferDataCencelOrPrioritizeListProxy
{
	private final String PENDING_TRANSFER_DATA_CANCEL_OR_PRIORITIZE_LIST = "PENDING_TRANSFER_DATA_CANCEL_OR_PRIORITIZE_LIST";

	public PendingTransferDataCencelOrPrioritizeListProxy(ListCell list,
			Page page)
	{
		this.list = list;
		this.page = page;

	}

	private ListCell list;

	private Page page;

	private int SELECT_COLUMN = 1;

	private int WHEN_NEXT_WORK_BEGIN_COLUMN = 2;

	private int RETRIEVAL_NO_COLUMN = 3;

	private int ITEM_NAME_1_COLUMN = 4;

	private int ITEM_NAME_2_COLUMN = 12;

	private int ITEM_NAME_3_COLUMN = 13;

	private int COLOR_COLUMN = 5;

	private int STOCKOUT_TRANSFER_QTY_COLUMN = 6;

	private int STARTED_BUCKET_COUNT_COLUMN = 7;

	private int WAITING_BUCKET_COUNT_COLUMN = 8;

	private int ITEM_CODE_COLUMN = 9;

	private int MC_KEY_COLUMN = 10;

	private int RETRIEVAL_PLAN_KEY_COLUMN = 11;

	public int getRETRIEVAL_PLAN_KEY_COLUMN()
	{
		return RETRIEVAL_PLAN_KEY_COLUMN;
	}

	public int getSELECT_COLUMN()
	{
		return SELECT_COLUMN;
	}

	public int getWHEN_NEXT_WORK_BEGIN_COLUMN()
	{
		return WHEN_NEXT_WORK_BEGIN_COLUMN;
	}

	public int getRETRIEVAL_NO_COLUMN()
	{
		return RETRIEVAL_NO_COLUMN;
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

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getSTOCKOUT_TRANSFER_QTY_COLUMN()
	{
		return STOCKOUT_TRANSFER_QTY_COLUMN;
	}

	public int getSTARTED_BUCKET_COUNT_COLUMN()
	{
		return STARTED_BUCKET_COUNT_COLUMN;
	}

	public int getWAITING_BUCKET_COUNT_COLUMN()
	{
		return WAITING_BUCKET_COUNT_COLUMN;
	}

	public int getMC_KEY_COLUMN()
	{
		return MC_KEY_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public boolean getSelected()
	{
		return list.getChecked(SELECT_COLUMN);
	}

	public void setSelected(boolean isSelected)
	{
		list.setChecked(SELECT_COLUMN, isSelected);
	}

	public String getWhenNextWorkBegin()
	{
		return list.getValue(WHEN_NEXT_WORK_BEGIN_COLUMN);
	}

	public void setWhenNextWorkBegin(String whenNextWorkBegin)
	{
		list.setValue(WHEN_NEXT_WORK_BEGIN_COLUMN, whenNextWorkBegin);
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

	public int getStockoutTransferQty()
	{
		String stockoutCount = list.getValue(STOCKOUT_TRANSFER_QTY_COLUMN);
		if (stockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						STOCKOUT_TRANSFER_QTY_COLUMN).replaceAll(",", ""));
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

	public void setStockoutTransferQty(int stockoutTransferQty)
	{
		list.setValue(STOCKOUT_TRANSFER_QTY_COLUMN, DecimalFormat
				.getIntegerInstance().format(stockoutTransferQty));
	}

	public int getStartedBucketCount()
	{
		String enableToStockoutCount = list
				.getValue(STARTED_BUCKET_COUNT_COLUMN);
		if (enableToStockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						STARTED_BUCKET_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setStartedBucketCount(int startedBucketCount)
	{
		list.setValue(STARTED_BUCKET_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(startedBucketCount));
	}

	public int getWaitingBucketCount()
	{
		String totalCountInstock = list.getValue(WAITING_BUCKET_COUNT_COLUMN);
		if (totalCountInstock != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						WAITING_BUCKET_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setWaitingBucketCount(int waitingBucketCount)
	{
		list.setValue(WAITING_BUCKET_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(waitingBucketCount));
	}

	public String getRetrievalNo()
	{
		return list.getValue(RETRIEVAL_NO_COLUMN);
	}

	public void setRetrievalNo(String retrievalNo)
	{
		list.setValue(RETRIEVAL_NO_COLUMN, retrievalNo);
	}

	public String getMcKey()
	{
		return list.getValue(MC_KEY_COLUMN);
	}

	public void setMcKey(String mcKey)
	{
		list.setValue(MC_KEY_COLUMN, mcKey);
	}

	public String getRetrievalPlanKey()
	{
		return list.getValue(RETRIEVAL_PLAN_KEY_COLUMN);
	}

	public void setRetrievalPlanKey(String retrievalPlanKey)
	{
		list.setValue(RETRIEVAL_PLAN_KEY_COLUMN, retrievalPlanKey);
	}

	public PendingTransferDataCencelOrPrioritizeEntity getPendingTransferDataCencelOrPrioritizeEntity()
	{
		PendingTransferDataCencelOrPrioritizeEntity entity = new PendingTransferDataCencelOrPrioritizeEntity();

		String[] whenNextWorkBegin = StringUtils
				.formatDateAndTimingFlagFromPageToDB(getWhenNextWorkBegin());
		if (whenNextWorkBegin.length >= 2)
		{
			entity.setWhenNextWorkBegin(StringUtils
					.formatDateFromPageToDB(whenNextWorkBegin[0]));
			entity.setWhenNextWorkBeginTiming(StringUtils
					.formatStartTimingFlagFromPageToDB(whenNextWorkBegin[1]));
		}
		else if (whenNextWorkBegin.length >= 1)
		{
			if (whenNextWorkBegin[0].length() == 2)
			{
				entity.setWhenNextWorkBegin("");
				entity
						.setWhenNextWorkBeginTiming(StringUtils
								.formatStartTimingFlagFromPageToDB(whenNextWorkBegin[0]));
			}
			else
			{
				entity.setWhenNextWorkBegin(StringUtils
						.formatDateFromPageToDB(whenNextWorkBegin[0]));
				entity.setWhenNextWorkBeginTiming(StringUtils
						.formatStartTimingFlagFromPageToDB(""));
			}
		}
		else
		{
			entity.setWhenNextWorkBegin("");
			entity.setWhenNextWorkBeginTiming(StringUtils
					.formatStartTimingFlagFromPageToDB(""));
		}

		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setColor(getColor());
		entity.setStockoutTransferQty(getStockoutTransferQty());
		entity.setStartedBucketCount(getStartedBucketCount());
		entity.setWaitingBucketCount(getWaitingBucketCount());
		entity.setRetrievalNo(getRetrievalNo());
		entity.setMcKey(getMcKey());
		entity.setRetrievalPlankey(getRetrievalPlanKey());
		return entity;
	}

	public void setRowValueByEntity(
			PendingTransferDataCencelOrPrioritizeEntity entity)
	{
		setWhenNextWorkBegin(StringUtils.formatDateFromDBToPage(entity
				.getWhenNextWorkBegin())
				+ " "
				+ StringUtils.formatStartTimingFlagFromDBToPage(entity
						.getWhenNextWorkBeginTiming()));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setStockoutTransferQty(entity.getStockoutTransferQty());
		setStartedBucketCount(entity.getStartedBucketCount());
		setWaitingBucketCount(entity.getWaitingBucketCount());
		setRetrievalNo(entity.getRetrievalNo());
		setMcKey(entity.getMcKey());
		setRetrievalPlanKey(entity.getRetrievalPlankey());

		if (RetrievalPlankeySortableHandler.contain((ArrayList) page
				.getSession().getAttribute(
						PENDING_TRANSFER_DATA_CANCEL_OR_PRIORITIZE_LIST),
				entity.getRetrievalPlankey()))
		{
			setSelected(true);

		}
		else
		{
			setSelected(false);
		}
		//
		// int startDate = 0;
		// String[] whenNextWorkBegin = StringUtils
		// .formatDateAndTimingFlagFromPageToDB(getWhenNextWorkBegin());
		// if (whenNextWorkBegin.length >= 2)
		// {
		// startDate = Integer.parseInt((StringUtils
		// .formatDateFromPageToDB(whenNextWorkBegin[0])));
		// }
		// else if (whenNextWorkBegin.length >= 1)
		// {
		// if (whenNextWorkBegin[0].length() == 10)
		// {
		// startDate = Integer.parseInt(StringUtils
		// .formatDateFromPageToDB(whenNextWorkBegin[0]));
		// }
		// }
		// if (startDate != 0
		// && startDate < Integer.parseInt(StringUtils.getCurrentDate()))
		// {
		// list.addHighlight(list.getCurrentRow(), ControlColor.Red);
		// }
	}
}
