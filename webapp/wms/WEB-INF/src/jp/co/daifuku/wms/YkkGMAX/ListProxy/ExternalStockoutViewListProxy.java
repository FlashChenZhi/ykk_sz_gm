package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.ExternalStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.RetrievalPlankeySortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ExternalStockoutViewListProxy
{
	private final ListCell list;

	private final Page page;

	private final int SELECT_COLUMN = 1;

	private final int MEMO_COLUMN = 2;

	private final int RETRIEVAL_NO_COLUMN = 3;

	private final int ITEM_CODE_COLUMN = 4;

	private final int ITEM_NAME_1_COLUMN = 5;

	private final int ITEM_NAME_2_COLUMN = 18;

	private final int ITEM_NAME_3_COLUMN = 19;

	private final int COLOR_COLUMN = 6;

	private final int STOCKOUT_NECESSARY_QTY_COLUMN = 7;

	private final int MANAGEMENT_RETRIEVAL_QTY_COLUMN = 8;

	private final int OUT_QTY_COLUMN = 9;

	private final int STOCKOUT_COUNT_COLUMN = 10;

	private final int ENABLE_TO_STOCKOUT_COUNT_COLUMN = 11;

	private final int TOTAL_COUNT_INSTOCK_COLUMN = 12;

	private final int WAVES_RETRIEVAL_QTY_COLUMN = 13;
	
	private final int EXTERNAL_CODE_COLUMN = 14;
	
	private final int RETRIEVAL_PLANKEY_COLUMN = 15;

	private final int WHEN_NEXT_WORK_BEGIN_COLUMN = 16;

	private final int WHEN_THIS_WORK_FINISH_IN_PLAN_COLUMN = 17;

	private final String EXTERNAL_STOCKOUT_VIEW_LIST = "EXTERNAL_STOCKOUT_VIEW_LIST";

	public ExternalStockoutViewListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}

	public int getSTOCKOUT_NECESSARY_QTY_COLUMN()
	{
		return STOCKOUT_NECESSARY_QTY_COLUMN;
	}

	public int getWAVES_RETRIEVAL_QTY_COLUMN()
	{
		return WAVES_RETRIEVAL_QTY_COLUMN;
	}

	public int getMANAGEMENT_RETRIEVAL_QTY_COLUMN()
	{
		return MANAGEMENT_RETRIEVAL_QTY_COLUMN;
	}

	public int getOUT_QTY_COLUMN()
	{
		return OUT_QTY_COLUMN;
	}

	public boolean isSelected()
	{
		return list.getChecked(SELECT_COLUMN);
	}

	public int getSELECT_COLUMN()
	{
		return SELECT_COLUMN;
	}

	public String getColor()
	{
		return list.getValue(COLOR_COLUMN);
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getENABLE_TO_STOCKOUT_COUNT_COLUMN()
	{
		return ENABLE_TO_STOCKOUT_COUNT_COLUMN;
	}

	public int getEnableToStockoutCount()
	{
		String enableToStockoutCount = list
				.getValue(ENABLE_TO_STOCKOUT_COUNT_COLUMN);
		if (enableToStockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						ENABLE_TO_STOCKOUT_COUNT_COLUMN).replaceAll(",", ""));
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

	public int getEXTERNAL_CODE_COLUMN()
	{
		return EXTERNAL_CODE_COLUMN;
	}

	public String getExternalCode()
	{
		return list.getValue(EXTERNAL_CODE_COLUMN);
	}

	public ExternalStockoutEntity getExternalStockoutEntity()
	{
		ExternalStockoutEntity entity = new ExternalStockoutEntity();

		entity.setMemo(getMemo());
		entity.setRetrievalNo(getRetrievalNo());
		entity.setExternalCode(getExternalCode());
		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setColor(getColor());
		entity.setStockoutCount(getStockoutCount());
		entity.setEnableToStockoutCount(getEnableToStockoutCount());
		entity.setTotalCountInstock(getTotalCountInstock());
		entity.setExternalStockoutDetailList(null);
		entity.setRetrievalPlankey(getRetrievalPlankey());
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
		String[] whenThisWorkFinishInPlan = StringUtils
				.formatDateAndTimingFlagFromPageToDB(getWhenThisWorkFinishInPlan());
		if (whenThisWorkFinishInPlan.length >= 2)
		{
			entity.setWhenThisWorkFinishInPlan(StringUtils
					.formatDateFromPageToDB(whenThisWorkFinishInPlan[0]));
			entity
					.setWhenThisWorkFinishInPlanTiming(StringUtils
							.formatCompleteTimingFlagFromPageToDB(whenThisWorkFinishInPlan[1]));
		}
		else if (whenThisWorkFinishInPlan.length >= 1)
		{
			if (whenThisWorkFinishInPlan[0].length() == 2)
			{
				entity.setWhenThisWorkFinishInPlan("");
				entity
						.setWhenThisWorkFinishInPlanTiming(StringUtils
								.formatCompleteTimingFlagFromPageToDB(whenThisWorkFinishInPlan[0]));
			}
			else
			{
				entity.setWhenThisWorkFinishInPlan(StringUtils
						.formatDateFromPageToDB(whenThisWorkFinishInPlan[0]));
				entity.setWhenThisWorkFinishInPlanTiming(StringUtils
						.formatCompleteTimingFlagFromPageToDB(""));
			}
		}
		else
		{
			entity.setWhenThisWorkFinishInPlan("");
			entity.setWhenThisWorkFinishInPlanTiming(StringUtils
					.formatCompleteTimingFlagFromPageToDB(""));
		}
		return entity;
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

	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public String getItemName1()
	{
		return list.getValue(ITEM_NAME_1_COLUMN);
	}

	public String getItemName2()
	{
		return list.getValue(ITEM_NAME_2_COLUMN);
	}

	public String getItemName3()
	{
		return list.getValue(ITEM_NAME_3_COLUMN);
	}

	public String getMemo()
	{
		String memo = list.getValue(MEMO_COLUMN);
		if (memo != null)
		{
			return memo;
		}
		else
		{
			return "";
		}
	}

	public int getMEMO_COLUMN()
	{
		return MEMO_COLUMN;
	}

	public int getRETRIEVAL_NO_COLUMN()
	{
		return RETRIEVAL_NO_COLUMN;
	}

	public int getRETRIEVAL_PLANKEY_COLUMN()
	{
		return RETRIEVAL_PLANKEY_COLUMN;
	}

	public String getRetrievalNo()
	{
		return list.getValue(RETRIEVAL_NO_COLUMN);
	}

	public String getRetrievalPlankey()
	{
		return list.getValue(RETRIEVAL_PLANKEY_COLUMN);
	}

	public int getSTOCKOUT_COUNT_COLUMN()
	{
		return STOCKOUT_COUNT_COLUMN;
	}

	public int getStockoutCount()
	{
		String stockoutCount = list.getValue(STOCKOUT_COUNT_COLUMN);
		if (stockoutCount != null)
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

	public int getTOTAL_COUNT_INSTOCK_COLUMN()
	{
		return TOTAL_COUNT_INSTOCK_COLUMN;
	}

	public int getTotalCountInstock()
	{
		String totalCountInstock = list.getValue(TOTAL_COUNT_INSTOCK_COLUMN);
		if (totalCountInstock != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						TOTAL_COUNT_INSTOCK_COLUMN).replaceAll(",", ""));
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

	public int getWHEN_NEXT_WORK_BEGIN_COLUMN()
	{
		return WHEN_NEXT_WORK_BEGIN_COLUMN;
	}

	public int getWHEN_THIS_WORK_FINISH_IN_PLAN_COLUMN()
	{
		return WHEN_THIS_WORK_FINISH_IN_PLAN_COLUMN;
	}

	public String getWhenNextWorkBegin()
	{
		return list.getValue(WHEN_NEXT_WORK_BEGIN_COLUMN);
	}

	public String getWhenThisWorkFinishInPlan()
	{
		return list.getValue(WHEN_THIS_WORK_FINISH_IN_PLAN_COLUMN);
	}

	public void setSelected(boolean isSelected)
	{
		list.setChecked(SELECT_COLUMN, isSelected);
	}

	public void setColor(String color)
	{
		list.setValue(COLOR_COLUMN, color);
	}

	public void setEnableToStockoutCount(int enableToStockoutCount)
	{
		list.setValue(ENABLE_TO_STOCKOUT_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(enableToStockoutCount));
	}

	public void setExternalCode(String exceptionCode)
	{
		list.setValue(EXTERNAL_CODE_COLUMN, exceptionCode);
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}

	public void setItemName1(String itemName1)
	{
		list.setValue(ITEM_NAME_1_COLUMN, itemName1);
	}

	public void setItemName2(String itemName2)
	{
		list.setValue(ITEM_NAME_2_COLUMN, itemName2);
	}

	public void setItemName3(String itemName3)
	{
		list.setValue(ITEM_NAME_3_COLUMN, itemName3);
	}

	public void setMemo(String memo)
	{
		list.setValue(MEMO_COLUMN, memo);
	}

	public void setRetrievalNo(String retrievalNo)
	{
		list.setValue(RETRIEVAL_NO_COLUMN, retrievalNo);
	}

	public void setRetrievalPlankey(String RetrievalPlankey)
	{
		list.setValue(RETRIEVAL_PLANKEY_COLUMN, RetrievalPlankey);
	}

	public long getStockoutNecessaryQty()
	{
		String stockoutNecessaryQty = list
				.getValue(STOCKOUT_NECESSARY_QTY_COLUMN);
		if (stockoutNecessaryQty != null)
		{
			try
			{
				return Long.parseLong(list.getValue(
						STOCKOUT_NECESSARY_QTY_COLUMN).replaceAll(",", ""));
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

	public void setStockoutNecessaryQty(long stockoutNecessaryQty)
	{
		list.setValue(STOCKOUT_NECESSARY_QTY_COLUMN, DecimalFormat
				.getIntegerInstance().format(stockoutNecessaryQty));
	}

	public long getWavesRetrievalQty()
	{
		String wavesRetrievalQty = list.getValue(WAVES_RETRIEVAL_QTY_COLUMN);
		if (wavesRetrievalQty != null)
		{
			try
			{
				return Long.parseLong(list.getValue(WAVES_RETRIEVAL_QTY_COLUMN)
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

	public void setWavesRetrievalQty(long wavesRetrievalQty)
	{
		list.setValue(WAVES_RETRIEVAL_QTY_COLUMN, DecimalFormat
				.getIntegerInstance().format(wavesRetrievalQty));
	}

	public long getManagementRetrievalQty()
	{
		String managementRetrievalQty = list
				.getValue(MANAGEMENT_RETRIEVAL_QTY_COLUMN);
		if (managementRetrievalQty != null)
		{
			try
			{
				return Long.parseLong(list.getValue(
						MANAGEMENT_RETRIEVAL_QTY_COLUMN).replaceAll(",", ""));
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

	public void setManagementRetrievalQty(long managementRetrievalQty)
	{
		list.setValue(MANAGEMENT_RETRIEVAL_QTY_COLUMN, DecimalFormat
				.getIntegerInstance().format(managementRetrievalQty));
	}

	public long getOutQty()
	{
		String outQty = list.getValue(OUT_QTY_COLUMN);
		if (outQty != null)
		{
			try
			{
				return Long.parseLong(list.getValue(OUT_QTY_COLUMN).replaceAll(
						",", ""));
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

	public void setOutQty(long outQty)
	{
		list.setValue(OUT_QTY_COLUMN, DecimalFormat.getIntegerInstance()
				.format(outQty));
	}

	public void setStockoutCount(int stockoutCount)
	{
		list.setValue(STOCKOUT_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(stockoutCount));
	}

	public void setTotalCountInstock(int totalCountInstock)
	{
		list.setValue(TOTAL_COUNT_INSTOCK_COLUMN, DecimalFormat
				.getIntegerInstance().format(totalCountInstock));
	}

	public void setWhenNextWorkBegin(String whenNextWorkBegin)
	{
		list.setValue(WHEN_NEXT_WORK_BEGIN_COLUMN, whenNextWorkBegin);
	}

	public void setWhenThisWorkFinishInPlan(String whenThisWorkFinishInPlan)
	{
		list.setValue(WHEN_THIS_WORK_FINISH_IN_PLAN_COLUMN,
				whenThisWorkFinishInPlan);
	}

	public ExternalStockoutEntity getRowValue()
	{
		ExternalStockoutEntity entity = new ExternalStockoutEntity();

		entity.setMemo(getMemo());
		entity.setRetrievalNo(getRetrievalNo());
		entity.setExternalCode(getExternalCode());
		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setColor(getColor());
		entity.setStockoutNecessaryQty(getStockoutNecessaryQty());
		entity.setWavesRetrievalQty(getWavesRetrievalQty());
		entity.setManagementRetrievalQty(getManagementRetrievalQty());
		entity.setOutQty(getOutQty());
		entity.setStockoutCount(getStockoutCount());
		entity.setEnableToStockoutCount(getEnableToStockoutCount());
		entity.setTotalCountInstock(getTotalCountInstock());
		entity.setRetrievalPlankey(getRetrievalPlankey());
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
		String[] whenThisWorkFinishInPlan = StringUtils
				.formatDateAndTimingFlagFromPageToDB(getWhenThisWorkFinishInPlan());
		if (whenThisWorkFinishInPlan.length >= 2)
		{
			entity.setWhenThisWorkFinishInPlan(StringUtils
					.formatDateFromPageToDB(whenThisWorkFinishInPlan[0]));
			entity
					.setWhenThisWorkFinishInPlanTiming(StringUtils
							.formatCompleteTimingFlagFromPageToDB(whenThisWorkFinishInPlan[1]));
		}
		else if (whenThisWorkFinishInPlan.length >= 1)
		{
			if (whenThisWorkFinishInPlan[0].length() == 2)
			{
				entity.setWhenThisWorkFinishInPlan("");
				entity
						.setWhenThisWorkFinishInPlanTiming(StringUtils
								.formatCompleteTimingFlagFromPageToDB(whenThisWorkFinishInPlan[0]));
			}
			else
			{
				entity.setWhenThisWorkFinishInPlan(StringUtils
						.formatDateFromPageToDB(whenThisWorkFinishInPlan[0]));
				entity.setWhenThisWorkFinishInPlanTiming(StringUtils
						.formatCompleteTimingFlagFromPageToDB(""));
			}
		}
		else
		{
			entity.setWhenThisWorkFinishInPlan("");
			entity.setWhenThisWorkFinishInPlanTiming(StringUtils
					.formatCompleteTimingFlagFromPageToDB(""));
		}

		return entity;
	}

	public void setRowValue(ExternalStockoutEntity entity)
	{
		setMemo(entity.getMemo());
		setRetrievalNo(entity.getRetrievalNo());
		setExternalCode(entity.getExternalCode());
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setStockoutNecessaryQty(entity.getStockoutNecessaryQty());
		setWavesRetrievalQty(entity.getWavesRetrievalQty());
		setManagementRetrievalQty(entity.getManagementRetrievalQty());
		setOutQty(entity.getOutQty());
		setStockoutCount(entity.getStockoutCount());
		setEnableToStockoutCount(entity.getEnableToStockoutCount());
		setTotalCountInstock(entity.getTotalCountInstock());
		setRetrievalPlankey(entity.getRetrievalPlankey());
		setWhenNextWorkBegin(StringUtils.formatDateFromDBToPage(entity
				.getWhenNextWorkBegin())
				+ " "
				+ StringUtils.formatStartTimingFlagFromDBToPage(entity
						.getWhenNextWorkBeginTiming()));
		setWhenThisWorkFinishInPlan(StringUtils.formatDateFromDBToPage(entity
				.getWhenThisWorkFinishInPlan())
				+ " "
				+ StringUtils.formatStartTimingFlagFromDBToPage(entity
						.getWhenThisWorkFinishInPlanTiming()));
		if (RetrievalPlankeySortableHandler.contain((ArrayList) page
				.getSession().getAttribute(EXTERNAL_STOCKOUT_VIEW_LIST), entity
				.getRetrievalPlankey()))
		{
			setSelected(true);
		}
		else
		{
			setSelected(false);
		}
		list.setCellEnabled(SELECT_COLUMN, !entity.isRetrieved());
	}

}
