package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.FlatStockoutEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class FlatStockoutListProxy
{
	public FlatStockoutListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int SELECT_COLUMN = 1;

	private int START_DATE_COLUMN = 2;

	private int RETRIEVAL_NO_COLUMN = 3;

	private int COLOR_COLUMN = 4;

	private int STOCKOUT_NECESSARY_QTY_COLUMN = 5;

	private int WAVES_RETRIEVAL_QTY_COLUMN = 6;

	private int MANAGEMENT_RETRIEVAL_QTY_COLUMN = 7;

	private int OUT_QTY_COLUMN = 8;

	private int SECTION_COLUMN = 9;

	private int ITEM_CODE_COLUMN = 10;

	private int RETRIEVAL_PLAN_KEY_COLUMN = 11;

	public int getSECTION_COLUMN()
	{
		return SECTION_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getSELECT_COLUMN()
	{
		return SELECT_COLUMN;
	}

	public int getSTART_DATE_COLUMN()
	{
		return START_DATE_COLUMN;
	}

	public int getRETRIEVAL_NO_COLUMN()
	{
		return RETRIEVAL_NO_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
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

	public int getRETRIEVAL_PLAN_KEY_COLUMN()
	{
		return RETRIEVAL_PLAN_KEY_COLUMN;
	}

	public void setStartDate(String startDate)
	{
		list.setValue(START_DATE_COLUMN, startDate);
	}

	public String getStartDate()
	{
		return list.getValue(START_DATE_COLUMN);
	}

	public void setRetrievalNo(String retrievalNo)
	{
		list.setValue(RETRIEVAL_NO_COLUMN, retrievalNo);
	}

	public String getRetrievalNo()
	{
		return list.getValue(RETRIEVAL_NO_COLUMN);
	}

	public void setColor(String color)
	{
		list.setValue(COLOR_COLUMN, color);
	}

	public String getColor()
	{
		return list.getValue(COLOR_COLUMN);
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

	public String getSection()
	{
		return list.getValue(SECTION_COLUMN);
	}

	public void setSection(String section)
	{
		list.setValue(SECTION_COLUMN, section);
	}

	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}

	public String getRetrievalPlankey()
	{
		return list.getValue(RETRIEVAL_PLAN_KEY_COLUMN);
	}

	public void setRetrievalPlankey(String RetrievalPlankey)
	{
		list.setValue(RETRIEVAL_PLAN_KEY_COLUMN, RetrievalPlankey);
	}

	public void setRowValue(FlatStockoutEntity entity)
	{
		setStartDate(StringUtils.formatDateFromDBToPage(entity.getStartDate())
				+ " "
				+ StringUtils.formatStartTimingFlagFromDBToPage(entity
						.getStartDateTiming()));
		setRetrievalNo(entity.getRetrievalNo());
		setColor(entity.getColor());
		setStockoutNecessaryQty(entity.getStockoutNecessaryQty());
		setWavesRetrievalQty(entity.getWavesRetrievalQty());
		setManagementRetrievalQty(entity.getManagementRetrievalQty());
		setOutQty(entity.getOutQty());
		setRetrievalPlankey(entity.getRetrievalPlankey());
		setSection(entity.getSection());
		setItemCode(entity.getItemCode());
		list.setCellEnabled(getSELECT_COLUMN(), entity
				.getManagementRetrievalQty() != entity.getOutQty());

	}

	public FlatStockoutEntity getRowValue()
	{
		FlatStockoutEntity entity = new FlatStockoutEntity();

		String[] startDate = StringUtils
				.formatDateAndTimingFlagFromPageToDB(getStartDate());
		if (startDate.length >= 2)
		{
			entity.setStartDate(StringUtils
					.formatDateFromPageToDB(startDate[0]));
			entity.setStartDateTiming(StringUtils
					.formatStartTimingFlagFromPageToDB(startDate[1]));
		}
		else if (startDate.length >= 1)
		{
			if (startDate[0].length() == 2)
			{
				entity.setStartDate("");
				entity.setStartDateTiming(StringUtils
						.formatStartTimingFlagFromPageToDB(startDate[0]));
			}
			else
			{
				entity.setStartDate(StringUtils
						.formatDateFromPageToDB(startDate[0]));
				entity.setStartDateTiming(StringUtils
						.formatStartTimingFlagFromPageToDB(""));
			}
		}
		else
		{
			entity.setStartDate("");
			entity.setStartDateTiming(StringUtils
					.formatStartTimingFlagFromPageToDB(""));
		}

		entity.setRetrievalNo(getRetrievalNo());
		entity.setColor(getColor());
		entity.setStockoutNecessaryQty(getStockoutNecessaryQty());
		entity.setWavesRetrievalQty(getWavesRetrievalQty());
		entity.setManagementRetrievalQty(getManagementRetrievalQty());
		entity.setOutQty(getOutQty());
		entity.setRetrievalPlankey(getRetrievalPlankey());
		entity.setSection(getSection());
		entity.setItemCode(getItemCode());

		return entity;
	}
}
