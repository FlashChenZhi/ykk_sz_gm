package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalQtyMaintenanceEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.RetrievalPlankeySortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class RetrievalQtyMaintenanceListProxy
{

	private ListCell list = null;

	private Page page = null;

	public RetrievalQtyMaintenanceListProxy(ListCell list, Page page)
	{
		this.page = page;

		this.list = list;
	}

	private int RETRIEVAL_NO_COLUMN = 1;

	private int SECTION_COLUMN = 2;

	private int START_DATE_COLUMN = 3;

	private int COLOR_COLUMN = 4;

	private int STOCKOUT_NECESSARY_QTY_COLUMN = 5;

	private int WAVES_RETRIEVAL_QTY_COLUMN = 6;

	private int MANAGEMENT_RETRIEVAL_QTY_COLUMN = 7;

	private int OUT_QTY_COLUMN = 8;

	private int STORAGE_QTY_COLUMN = 9;

	private int RETRIEVAL_PLAN_KEY_COLUMN = 10;

	private int ORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN = 11;

	private final String RETRIEVAL_QTY_MAINTENANCE_LIST = "RETRIEVAL_QTY_MAINTENANCE_LIST";

	public int getORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN()
	{
		return ORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN;
	}

	public int getRETRIEVAL_NO_COLUMN()
	{
		return RETRIEVAL_NO_COLUMN;
	}

	public int getSECTION_COLUMN()
	{
		return SECTION_COLUMN;
	}

	public int getSTART_DATE_COLUMN()
	{
		return START_DATE_COLUMN;
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

	public int getSTORAGE_QTY_COLUMN()
	{
		return STORAGE_QTY_COLUMN;
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

	public void setOriginalManagementRetrievalQty(
			long originalManagementRetrievalQty)
	{
		list.setValue(ORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN, DecimalFormat
				.getIntegerInstance().format(originalManagementRetrievalQty));
	}

	public long getOriginalManagementRetrievalQty()
	{
		String originalManagementRetrievalQty = list
				.getValue(ORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN);
		if (originalManagementRetrievalQty != null)
		{
			try
			{
				return Long.parseLong(list.getValue(
						ORIGINAL_MANAGEMENT_RETRIEVAL_QTY_COLUMN).replaceAll(
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

	public int getStorageQty()
	{
		String storageQty = list.getValue(STORAGE_QTY_COLUMN);
		if (storageQty != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(STORAGE_QTY_COLUMN)
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

	public void setStorageQty(long storageQty)
	{
		list.setValue(STORAGE_QTY_COLUMN, DecimalFormat.getIntegerInstance()
				.format(storageQty));
	}

	public String getSection()
	{
		return list.getValue(SECTION_COLUMN);
	}

	public void setSection(String section)
	{
		list.setValue(SECTION_COLUMN, section);
	}

	public String getRetrievalPlankey()
	{
		return list.getValue(RETRIEVAL_PLAN_KEY_COLUMN);
	}

	public void setRetrievalPlankey(String RetrievalPlankey)
	{
		list.setValue(RETRIEVAL_PLAN_KEY_COLUMN, RetrievalPlankey);
	}

	public void setRowValue(RetrievalQtyMaintenanceEntity entity)
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
		setStorageQty(entity.getStorageQty());
		setOriginalManagementRetrievalQty(entity
				.getOriginalManagementRetrievalQty());

		if (RetrievalPlankeySortableHandler.contain((ArrayList) page
				.getSession().getAttribute(RETRIEVAL_QTY_MAINTENANCE_LIST),
				entity.getRetrievalPlankey()))
		{
			setManagementRetrievalQty(((RetrievalQtyMaintenanceEntity) RetrievalPlankeySortableHandler
					.get((ArrayList) page.getSession().getAttribute(
							RETRIEVAL_QTY_MAINTENANCE_LIST), entity
							.getRetrievalPlankey()))
					.getManagementRetrievalQty());
		}
		int startDate = 0;
		String[] whenNextWorkBegin = StringUtils
				.formatDateAndTimingFlagFromPageToDB(getStartDate());
		if (whenNextWorkBegin.length >= 2)
		{
			startDate = Integer.parseInt((StringUtils
					.formatDateFromPageToDB(whenNextWorkBegin[0])));
		}
		else if (whenNextWorkBegin.length >= 1)
		{
			if (whenNextWorkBegin[0].length() == 10)
			{
				startDate = Integer.parseInt(StringUtils
						.formatDateFromPageToDB(whenNextWorkBegin[0]));
			}
		}
		if (entity.isRetrieved())
		{
			list.addHighlight(list.getCurrentRow(), ControlColor.Green);
		}
		else if (entity.getStockoutNecessaryQty() == entity.getManagementRetrievalQty())
		{
			list.addHighlight(list.getCurrentRow(), ControlColor.Yellow);
		}
		else if (startDate != 0
				&& startDate < Integer.parseInt(StringUtils.getCurrentDate()))
		{
			list.addHighlight(list.getCurrentRow(), ControlColor.Red);
		}
		 
		// list.setCellReadOnly(MANAGEMENT_RETRIEVAL_QTY_COLUMN, entity
		// .isRetrieved());

	}

	public RetrievalQtyMaintenanceEntity getRowValue()
	{
		RetrievalQtyMaintenanceEntity entity = new RetrievalQtyMaintenanceEntity();

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
		entity.setStorageQty(getStorageQty());
		entity
				.setOriginalManagementRetrievalQty(getOriginalManagementRetrievalQty());

		return entity;
	}

}
