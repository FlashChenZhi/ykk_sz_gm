package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class WorkViewListProxy
{
	public WorkViewListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int NO_COLUMN = 1;

	private int TRANSFER_TYPE_COLUMN = 2;

	private int STATUS_COLUMN = 3;

	private int MCKEY_COLUMN = 4;

	private int STATION_NO_COLUMN = 5;

	private int MOTO_SAKI_STATION_NO_COLUMN = 6;

	private int LOCATION_NO_COLUMN = 7;

	private int BUCKET_NO_COLUMN = 8;

	private int ITEM_CODE_COLUMN = 9;

	private int ITEM_NAME_1_COLUMN = 10;

	private int ITEM_NAME_2_COLUMN = 16;

	private int ITEM_NAME_3_COLUMN = 17;

	private int TRANSFER_COUNT_COLUMN = 11;
	
	private int DISPATCH_DETAIL_COLUMN = 12;
	
	private int INSTOCK_COUNT_COLUMN = 13;
	
	private int TICKET_NO_COLUMN = 14;
	
	private int COLOR_CODE_COLUMN = 15;

	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}
	
	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public int getDISPATCH_DETAIL_COLUMN()
	{
		return DISPATCH_DETAIL_COLUMN;
	}

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
	}
	
	public int getBUCKET_NO_COLUMN()
	{
		return BUCKET_NO_COLUMN;
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

	public int getMCKEY_COLUMN()
	{
		return MCKEY_COLUMN;
	}

	public int getMOTO_SAKI_STATION_NO_COLUMN()
	{
		return MOTO_SAKI_STATION_NO_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getSTATION_NO_COLUMN()
	{
		return STATION_NO_COLUMN;
	}

	public int getSTATUS_COLUMN()
	{
		return STATUS_COLUMN;
	}

	public int getTRANSFER_COUNT_COLUMN()
	{
		return TRANSFER_COUNT_COLUMN;
	}

	public int getTRANSFER_TYPE_COLUMN()
	{
		return TRANSFER_TYPE_COLUMN;
	}

	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}

	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}

	public void setTransferType(String transferType)
	{
		list.setValue(TRANSFER_TYPE_COLUMN, transferType);
	}

	public String getTransferType()
	{
		return list.getValue(TRANSFER_TYPE_COLUMN);
	}

	public void setStatus(String status)
	{
		list.setValue(STATUS_COLUMN, status);
	}

	public String getStatus()
	{
		return list.getValue(STATUS_COLUMN);
	}

	public void setMckey(String mckey)
	{
		list.setValue(MCKEY_COLUMN, mckey);
	}

	public String getMckey()
	{
		return list.getValue(MCKEY_COLUMN);
	}

	public void setStationNo(String stationNo)
	{
		list.setValue(STATION_NO_COLUMN, stationNo);
	}

	public String getStationNo()
	{
		return list.getValue(STATION_NO_COLUMN);
	}

	public void setMotoSakiStationNo(String motoSakiStationNo)
	{
		list.setValue(MOTO_SAKI_STATION_NO_COLUMN, motoSakiStationNo);
	}

	public String getMotoSakiStationNo()
	{
		return list.getValue(MOTO_SAKI_STATION_NO_COLUMN);
	}

	public void setLocationNo(String locationNo)
	{
		list.setValue(LOCATION_NO_COLUMN, locationNo);
	}

	public String getLocationNo()
	{
		return list.getValue(LOCATION_NO_COLUMN);
	}

	public void setBucketNo(String bucketNo)
	{
		list.setValue(BUCKET_NO_COLUMN, bucketNo);
	}

	public String getBucketNo()
	{
		return list.getValue(BUCKET_NO_COLUMN);
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

	public int getTransferCount()
	{
		String transferCount = list.getValue(TRANSFER_COUNT_COLUMN);
		if (transferCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(TRANSFER_COUNT_COLUMN)
						.replaceAll(",", ""));
			} catch (Exception ex)
			{
				return 0;
			}
		} else
		{
			return 0;
		}
	}

	public void setTransferCount(int transferCount)
	{
		list.setValue(TRANSFER_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(transferCount));
	}

	public void setDispatchDetail(String dispatchDetail)
	{
		list.setValue(DISPATCH_DETAIL_COLUMN, dispatchDetail);
	}
	
	public String getDispatchDetail()
	{
		return list.getValue(DISPATCH_DETAIL_COLUMN);
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
			} catch (Exception ex)
			{
				return 0;
			}
		} else
		{
			return 0;
		}
	}

	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(instockCount));
	}
	
	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}
	
	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}
	
	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}
	
	public void setColorCode(String colorCode)
	{
		list.setValue(COLOR_CODE_COLUMN, colorCode);
	}

	public void setRowValueByEntity(WorkViewEntity entity,
			int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTransferType(entity.getTransferType());
		setStatus(entity.getStatus());
		setMckey(entity.getMckey());
		setStationNo(entity.getStationNo());
		setMotoSakiStationNo(entity.getMotoStationNo() + StringUtils.ToMark + entity.getSakiStationNo());
		setLocationNo(StringUtils.formatLocationNoFromDBToPage(entity
				.getLocationNo()));
		setBucketNo(entity.getBucketNo());
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
		setTransferCount(entity.getTransferCount());
		setDispatchDetail(entity.getDispatchDetail());
		setInstockCount(entity.getInstockCount());
		setTicketNo(entity.getTicketNo());
		setColorCode(entity.getColorCode());
	}
}
