package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class TicketNoItemStorageInfoListProxy
{
	public TicketNoItemStorageInfoListProxy( FixedListCell head,ListCell list)
	{
		this.head = head;
		this.list = list;
	}
	public TicketNoItemStorageInfoListProxy(ListCell list)
	{
		this.head = null;
		this.list = list;
	}
	private ListCell list;
	
	private FixedListCell head;
	
	private int HEAD_DEPO_ROW = 1;
	
	private int HEAD_DEPO_COLUMN = 1;
	
	private int HEAD_DIVISION_ROW = 2;
	
	private int HEAD_DIVISION_COLUMN = 1;
	
	private int HEAD_TICKET_NO_RANGE_ROW = 3;
	
	private int HEAD_TICKET_NO_RANGE_COLUMN = 1;
	
	private int HEAD_ITEM_CODE_ROW = 4;
	
	private int HEAD_ITEM_CODE_COLUMN = 1;
	
	private int HEAD_COLOR_CODE_ROW = 5;
	
	private int HEAD_COLOR_CODE_COLUMN = 1;
	
	private int HEAD_BUCKET_NO_ROW = 6;
	
	private int HEAD_BUCKET_NO_COLUMN = 1;
	
	private int NO_COLUMN = 1;
	
	private int TICKET_NO_COLUMN = 2;
	
	private int ITEM_CODE_COLUMN = 3;

	private int ITEM_NAME_1_COLUMN = 4;

	private int ITEM_NAME_2_COLUMN = 10;

	private int ITEM_NAME_3_COLUMN = 11;

	private int COLOR_COLUMN = 5;
	
	private int LOCATION_NO_COLUMN = 6;
	
	private int BUCKET_NO_COLUMN = 7;
	
	private int INSTOCK_COUNT_COLUMN = 8;
	
	private int MESSAGE_DATE_TIME_COLUMN = 9;

	public int getHEAD_BUCKET_NO_COLUMN()
	{
		return HEAD_BUCKET_NO_COLUMN;
	}
	
	public int getHEAD_BUCKET_NO_ROW()
	{
		return HEAD_BUCKET_NO_ROW;
	}
	
	public int getBUCKET_NO_COLUMN()
	{
		return BUCKET_NO_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getHEAD_COLOR_CODE_COLUMN()
	{
		return HEAD_COLOR_CODE_COLUMN;
	}

	public int getHEAD_COLOR_CODE_ROW()
	{
		return HEAD_COLOR_CODE_ROW;
	}

	public int getHEAD_DEPO_COLUMN()
	{
		return HEAD_DEPO_COLUMN;
	}

	public int getHEAD_DEPO_ROW()
	{
		return HEAD_DEPO_ROW;
	}

	public int getHEAD_DIVISION_COLUMN()
	{
		return HEAD_DIVISION_COLUMN;
	}

	public int getHEAD_DIVISION_ROW()
	{
		return HEAD_DIVISION_ROW;
	}

	public int getHEAD_ITEM_CODE_COLUMN()
	{
		return HEAD_ITEM_CODE_COLUMN;
	}

	public int getHEAD_ITEM_CODE_ROW()
	{
		return HEAD_ITEM_CODE_ROW;
	}

	public int getHEAD_TICKET_NO_RANGE_COLUMN()
	{
		return HEAD_TICKET_NO_RANGE_COLUMN;
	}

	public int getHEAD_TICKET_NO_RANGE_ROW()
	{
		return HEAD_TICKET_NO_RANGE_ROW;
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

	public int getMESSAGE_DATE_TIME_COLUMN()
	{
		return MESSAGE_DATE_TIME_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}
	
	public void setHeadDepo(String depo)
	{
		head.setCurrentRow(HEAD_DEPO_ROW);
		
		head.setValue(HEAD_DEPO_COLUMN, depo);
		
	}
	
	public String getHeadDepo()
	{
		head.setCurrentRow(HEAD_DEPO_ROW);
		
		return head.getValue(HEAD_DEPO_COLUMN);
	}
	
	public void setHeadDivision(String division)
	{
		head.setCurrentRow(HEAD_DIVISION_ROW);
		
		head.setValue(HEAD_DIVISION_COLUMN, division);
		
	}
	
	public String getHeadLocationStatus()
	{
		head.setCurrentRow(HEAD_DIVISION_ROW);
		
		return head.getValue(HEAD_DIVISION_COLUMN);
	}
	
	public void setHeadTicketNoRange(String ticketNo)
	{
		head.setCurrentRow(HEAD_TICKET_NO_RANGE_ROW);
		
		head.setValue(HEAD_TICKET_NO_RANGE_COLUMN, ticketNo);
		
	}
	
	public String getHeadTicketNoRange()
	{
		head.setCurrentRow(HEAD_TICKET_NO_RANGE_ROW);
		
		return head.getValue(HEAD_TICKET_NO_RANGE_COLUMN);
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
	
	public void setHeadBucketNo(String bucketNo)
	{
		head.setCurrentRow(HEAD_BUCKET_NO_ROW);
		
		head.setValue(HEAD_BUCKET_NO_COLUMN, bucketNo);
	}
	
	public String getHeadBucketNo()
	{
		head.setCurrentRow(HEAD_BUCKET_NO_ROW);
		
		return head.getValue(HEAD_BUCKET_NO_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public String getMessageDateTime()
	{
		return list.getValue(MESSAGE_DATE_TIME_COLUMN);
	}
	
	public void setMessageDateTime(String messageDateTime)
	{
		list.setValue(MESSAGE_DATE_TIME_COLUMN, messageDateTime);
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
	
	public String getLocationNo()
	{
		return list.getValue(LOCATION_NO_COLUMN);
	}
	
	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_NO_COLUMN, locationNo);
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

	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(instockCount));
	}
	
	public void setHeadValue(TicketNoItemStorageInfoHead head)
	{
		setHeadDepo(head.getDepo());
		setHeadDivision(head.getDivision());
		String ticketNoRange = "";
		if(head.isRangeSet())
		{
			ticketNoRange = head.getTicketNoFrom() + "～" + head.getTicketNoTo();
		}
		else
		{
			ticketNoRange = head.getTicketNoFrom();
		}
		setHeadTicketNoRange(ticketNoRange);
		setHeadItemCode(head.getItemCode());
		setHeadColorCode(head.getColorCode());
		setHeadBucketNo(head.getBucketNo());
		
	}
	
	public void setRowValueByEntity(TicketNoItemStorageInfoEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setMessageDateTime(StringUtils.formatDateAndTimeFromDBToPage(entity.getMessageDateTime()));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setColor(entity.getColor());
		setTicketNo(entity.getTicketNo());
		setBucketNo(entity.getBucketNo());
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity.getLocationNo()));
		setInstockCount(entity.getInstockCount());
	}

}
