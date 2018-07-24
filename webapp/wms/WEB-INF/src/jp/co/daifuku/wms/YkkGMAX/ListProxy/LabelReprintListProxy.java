package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.LabelReprintEntity;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.LabelKeySortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class LabelReprintListProxy
{
	private final String REPRINT_LIST = "REPRINT_LIST";

	public LabelReprintListProxy(ListCell list, Page page)
	{
		this.list = list;
		this.page = page;
	}
	
	private ListCell list;
	
	private Page page;
	
	private int ALL_COLUMN = 1;
	
	private int PRINT_TIME_COLUMN = 2;
	
	private int TICKET_NO_COLUMN = 3;
	
	private int BUCKET_NO_COLUMN = 4;
	
	private int ITEM_CODE_COLUMN = 5;
	
	private int COLOR_CODE_COLUMN = 6;
	
	private int SECTION_EXTERNAL_CODE_COLUMN = 7;
	
	private int LINE_PRNO_COLUMN = 8;
	
	private int STOCKOUT_COUNT_COLUMN = 9;
	
	private int STOCKOUT_WEIGHT_COLUMN = 10;
	
	private int LABEL_KEY_COLUMN = 11;

	public int getALL_COLUMN()
	{
		return ALL_COLUMN;
	}

	public int getBUCKET_NO_COLUMN()
	{
		return BUCKET_NO_COLUMN;
	}

	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getLABEL_KEY_COLUMN()
	{
		return LABEL_KEY_COLUMN;
	}

	public int getLINE_PRNO_COLUMN()
	{
		return LINE_PRNO_COLUMN;
	}

	public int getPRINT_TIME_COLUMN()
	{
		return PRINT_TIME_COLUMN;
	}

	public int getSECTION_EXTERNAL_CODE_COLUMN()
	{
		return SECTION_EXTERNAL_CODE_COLUMN;
	}

	public int getSTOCKOUT_WEIGHT_COLUMN()
	{
		return STOCKOUT_WEIGHT_COLUMN;
	}

	public int getSTOCKOUT_COUNT_COLUMN()
	{
		return STOCKOUT_COUNT_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}
	
	public boolean getAll()
	{
		return list.getChecked(ALL_COLUMN);
	}
	
	public void setAll(boolean all)
	{
		list.setChecked(ALL_COLUMN, all);
	}
	
	public String getPrintTime()
	{
		return list.getValue(PRINT_TIME_COLUMN);
	}
	
	public void setPrintTime(String printTime)
	{
		list.setValue(PRINT_TIME_COLUMN, printTime);
	}
	
	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}
	
	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}
	
	public String getBucketNo()
	{
		return list.getValue(BUCKET_NO_COLUMN);
	}
	
	public void setBucketNo(String bucketNo)
	{
		list.setValue(BUCKET_NO_COLUMN, bucketNo);
	}
	
	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}
	
	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}
	
	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}
	
	public void setColorCode(String colorCode)
	{
		list.setValue(COLOR_CODE_COLUMN, colorCode);
	}
	
	public String getSectionExternalCode()
	{
		return list.getValue(SECTION_EXTERNAL_CODE_COLUMN);
	}
	
	public void setSectionExternalCode(String sectionExternalCode)
	{
		list.setValue(SECTION_EXTERNAL_CODE_COLUMN, sectionExternalCode);
	}
	
	public String getLinePrno()
	{
		return list.getValue(LINE_PRNO_COLUMN);
	}
	
	public void setLinePrno(String linePrno)
	{
		list.setValue(LINE_PRNO_COLUMN, linePrno);
	}

	public void setStockoutCount(int stockoutCount)
	{
		list.setValue(STOCKOUT_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(stockoutCount));
	}

	public int getStockoutCount()
	{
		String stockoutCount = list.getValue(STOCKOUT_COUNT_COLUMN);
		if (stockoutCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						STOCKOUT_COUNT_COLUMN).replaceAll(",", ""));
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

	public void setStockoutWeight(int stockoutWeight)
	{
		list.setValue(STOCKOUT_WEIGHT_COLUMN, DecimalFormat
				.getIntegerInstance().format(stockoutWeight));
	}

	public int getStockoutWeight()
	{
		String stockoutWeight = list.getValue(STOCKOUT_WEIGHT_COLUMN);
		if (stockoutWeight != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(
						STOCKOUT_WEIGHT_COLUMN).replaceAll(",", ""));
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
	
	public String getLabelKey()
	{
		return list.getValue(LABEL_KEY_COLUMN);
	}
	
	public void setLabelKey(String labelKey)
	{
		list.setValue(LABEL_KEY_COLUMN, labelKey);
	}
	
	public LabelReprintEntity getLabelReprintEntity()
	{
		LabelReprintEntity entity = new LabelReprintEntity();
		
		entity.setPrintTime(StringUtils.formatDateAndTimeFromPageToDB(getPrintTime()));
		entity.setTicketNo(getTicketNo());
		entity.setBucketNo(getBucketNo());
		entity.setItemCode(getItemCode());
		entity.setColorCode(getColorCode());
		entity.setSectionExternalCode(getSectionExternalCode());
		entity.setLinePrno(getLinePrno());
		entity.setStockoutCount(getStockoutCount());
		entity.setStockoutWeight(getStockoutWeight());
		entity.setLabelKey(getLabelKey());
		
		return entity;
	}
	
	public void setRowValueByEntity(LabelReprintEntity entity)
	{
		setPrintTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getPrintTime()));
		setTicketNo(entity.getTicketNo());
		setBucketNo(entity.getBucketNo());
		setItemCode(entity.getItemCode());
		setColorCode(entity.getColorCode());
		setSectionExternalCode(entity.getSectionExternalCode());
		setLinePrno(entity.getLinePrno());
		setStockoutCount(entity.getStockoutCount());
		setStockoutWeight(entity.getStockoutWeight());
		setLabelKey(entity.getLabelKey());
		
		if(LabelKeySortableHandler.contain((ArrayList)page.getSession().getAttribute(REPRINT_LIST), entity.getLabelKey()))
		{
			setAll(true);
		}
		else
		{
			setAll(false);
		}
	}
}
