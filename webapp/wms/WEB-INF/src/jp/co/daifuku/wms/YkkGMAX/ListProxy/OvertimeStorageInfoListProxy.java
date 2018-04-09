package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class OvertimeStorageInfoListProxy
{
	public OvertimeStorageInfoListProxy( FixedListCell head,ListCell list)
	{
		this.head = head;
		this.list = list;
	}
	public OvertimeStorageInfoListProxy(ListCell list)
	{
		this.head = null;
		this.list = list;
	}
	private ListCell list;
	
	private FixedListCell head;
	
	private int HEAD_DEPOSITORY_TYPE_ROW = 1;
	
	private int HEAD_DEPOSITORY_TYPE_COLUMN = 1;
	
	private int HEAD_BENCHMARK_DATE_ROW = 2;
	
	private int HEAD_BENCHMARK_DATE_COLUMN = 1;
	
	private int HEAD_BENCHMARK_OBJECT_ROW = 3;
	
	private int HEAD_BENCHMARK_OBJECT_COLUMN = 1;
	
	private int HEAD_ORDER_BY_ROW = 4;
	
	private int HEAD_ORDER_BY_COLUMN = 1;
	
	private int NO_COLUMN = 1;
	
	private int DATE_TIME_COLUMN = 2;
	
	private int ITEM_CODE_COLUMN = 3;

	private int ITEM_NAME_1_COLUMN = 4;

	private int ITEM_NAME_2_COLUMN = 10;

	private int ITEM_NAME_3_COLUMN = 11;

	private int COLOR_COLUMN = 5;
	
	private int TICKET_NO_COLUMN = 6;
	
	private int BUCKET_NO_COLUMN = 7;
	
	private int LOCATION_NO_COLUMN = 8;
	
	private int INSTOCK_COUNT_COLUMN = 9;

	public int getBUCKET_NO_COLUMN()
	{
		return BUCKET_NO_COLUMN;
	}

	public int getCOLOR_COLUMN()
	{
		return COLOR_COLUMN;
	}

	public int getDATE_TIME_COLUMN()
	{
		return DATE_TIME_COLUMN;
	}

	public int getHEAD_BENCHMARK_DATE_COLUMN()
	{
		return HEAD_BENCHMARK_DATE_COLUMN;
	}

	public int getHEAD_BENCHMARK_DATE_ROW()
	{
		return HEAD_BENCHMARK_DATE_ROW;
	}

	public int getHEAD_BENCHMARK_OBJECT_COLUMN()
	{
		return HEAD_BENCHMARK_OBJECT_COLUMN;
	}

	public int getHEAD_BENCHMARK_OBJECT_ROW()
	{
		return HEAD_BENCHMARK_OBJECT_ROW;
	}

	public int getHEAD_DEPOSITORY_TYPE_COLUMN()
	{
		return HEAD_DEPOSITORY_TYPE_COLUMN;
	}

	public int getHEAD_DEPOSITORY_TYPE_ROW()
	{
		return HEAD_DEPOSITORY_TYPE_ROW;
	}

	public int getHEAD_ORDER_BY_COLUMN()
	{
		return HEAD_ORDER_BY_COLUMN;
	}

	public int getHEAD_ORDER_BY_ROW()
	{
		return HEAD_ORDER_BY_ROW;
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

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}
	
	public void setHeadDepositoryType(String depositoryType)
	{
		head.setCurrentRow(HEAD_DEPOSITORY_TYPE_ROW);
		
		head.setValue(HEAD_DEPOSITORY_TYPE_COLUMN, depositoryType);
		
	}
	
	public String getHeadDepositoryType()
	{
		head.setCurrentRow(HEAD_DEPOSITORY_TYPE_ROW);
		
		return head.getValue(HEAD_DEPOSITORY_TYPE_COLUMN);
	}
	
	public void setHeadBenchmarkDate(String benchmarkDate)
	{
		head.setCurrentRow(HEAD_BENCHMARK_DATE_ROW);
		
		head.setValue(HEAD_BENCHMARK_DATE_COLUMN, benchmarkDate);
	}
	
	public String getHeadBnechmarkDate()
	{
		head.setCurrentRow(HEAD_BENCHMARK_DATE_ROW);
		
		return head.getValue(HEAD_BENCHMARK_DATE_COLUMN);
	}
	
	public void setHeadBenchmarkObject(String benchmarkObject)
	{
		head.setCurrentRow(HEAD_BENCHMARK_OBJECT_ROW);
		
		head.setValue(HEAD_BENCHMARK_OBJECT_COLUMN, benchmarkObject);
	}
	
	public String getHeadBnechmarkObject()
	{
		head.setCurrentRow(HEAD_BENCHMARK_OBJECT_ROW);
		
		return head.getValue(HEAD_BENCHMARK_OBJECT_COLUMN);
	}
	
	public void setHeadOrderBy(String orderBy)
	{
		head.setCurrentRow(HEAD_ORDER_BY_ROW);
		
		head.setValue(HEAD_ORDER_BY_COLUMN, orderBy);
	}
	
	public String getHeadOrderBy()
	{
		head.setCurrentRow(HEAD_ORDER_BY_ROW);
		
		return head.getValue(HEAD_ORDER_BY_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public String getDateTime()
	{
		return list.getValue(DATE_TIME_COLUMN);
	}
	
	public void setDateTime(String dateTime)
	{
		list.setValue(DATE_TIME_COLUMN, dateTime);
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
	
	public void setHeadValue(OvertimeStorageInfoHead head)
	{
		setHeadDepositoryType(DBFlags.StoragePlaceFlag.parseDBToPage(head.getDepositoryType()));
		setHeadBenchmarkDate(StringUtils.formatDateFromDBToPage(head.getBenchmarkDate()));
		if (head.getBenchmarkObject().equals("1"))
		{
			setHeadBenchmarkObject("受信日时");
		}
		else if (head.getBenchmarkObject().equals("2"))
		{
			setHeadBenchmarkObject("入库日时");
		}
		else
		{
			setHeadBenchmarkObject("最新更新日时");
		}
		if (head.getOrderBy().equals("1"))
		{
			setHeadOrderBy("长期滞留时间");
		}
		else
		{
			setHeadOrderBy("物料编号");
		}
	}
	
	public void setRowValueByEntity(OvertimeStorageInfoEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setDateTime(StringUtils.formatDateFromDBToPage(entity.getDateTime()));
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
