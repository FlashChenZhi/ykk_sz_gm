package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class WorkMaintenancePopupListProxy
{
	public WorkMaintenancePopupListProxy(FixedListCell head, ListCell list)
	{
		this.head = head;
		this.list = list;
	}

	public WorkMaintenancePopupListProxy(ListCell list)
	{
		this.list = list;
		this.head = null;
	}

	private ListCell list;

	private FixedListCell head;

	private int HEAD_TRANSFER_TYPE_ROW = 1;

	private int HEAD_TRANSFER_TYPE_COLUMN = 1;

	private int HEAD_STATION_ROW = 2;

	private int HEAD_STATION_COLUMN = 1;

	private int HEAD_DIVISION_ROW = 3;

	private int HEAD_DIVISION_COLUMN = 1;

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

	public int getHEAD_DIVISION_COLUMN()
	{
		return HEAD_DIVISION_COLUMN;
	}

	public int getHEAD_DIVISION_ROW()
	{
		return HEAD_DIVISION_ROW;
	}

	public int getHEAD_STATION_COLUMN()
	{
		return HEAD_STATION_COLUMN;
	}

	public int getHEAD_STATION_ROW()
	{
		return HEAD_STATION_ROW;
	}

	public int getHEAD_TRANSFER_TYPE_COLUMN()
	{
		return HEAD_TRANSFER_TYPE_COLUMN;
	}

	public int getHEAD_TRANSFER_TYPE_ROW()
	{
		return HEAD_TRANSFER_TYPE_ROW;
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

	public void setHeadTransferType(String transferType)
	{
		head.setCurrentRow(HEAD_TRANSFER_TYPE_ROW);

		head.setValue(HEAD_TRANSFER_TYPE_COLUMN, transferType);
	}

	public String getHeadTransferType()
	{
		head.setCurrentRow(HEAD_TRANSFER_TYPE_ROW);

		return head.getValue(HEAD_TRANSFER_TYPE_COLUMN);
	}

	public void setHeadStation(String station)
	{
		head.setCurrentRow(HEAD_STATION_ROW);

		head.setValue(HEAD_STATION_COLUMN, station);
	}

	public String getHeadStation()
	{
		head.setCurrentRow(HEAD_STATION_ROW);

		return head.getValue(HEAD_STATION_COLUMN);
	}

	public void setHeadDivision(String division)
	{
		head.setCurrentRow(HEAD_DIVISION_ROW);

		head.setValue(HEAD_DIVISION_COLUMN, division);
	}

	public String getHeadDivision()
	{
		head.setCurrentRow(HEAD_DIVISION_ROW);

		return head.getValue(HEAD_DIVISION_COLUMN);
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
	
	public WorkMaintenancePopupEntity getWorkMaintenancePopupEntity()
	{
		WorkMaintenancePopupEntity entity = new WorkMaintenancePopupEntity();

		entity.setTransferType(getTransferType());
		entity.setStatus(getStatus());
		entity.setMckey(getMckey());
		String[] stationNo = getStationNo().split(":");
		if (stationNo.length >= 2)
		{
			entity.setStationNo(stationNo[0]);
			entity.setStationName(stationNo[1]);
		}

		String[] motoSakiStationNo = getMotoSakiStationNo().split(
				StringUtils.ToMark);
		if (motoSakiStationNo.length >= 2)
		{
			entity.setMotoStationNo(motoSakiStationNo[0]);
			entity.setSakiStationNo(motoSakiStationNo[1]);
		}
		entity.setLocationNo(StringUtils
				.formatLocationNoFromPageToDB(getLocationNo()));
		entity.setBucketNo(getBucketNo());
		entity.setItemCode(getItemCode());
		entity.setItemName1(getItemName1());
		entity.setItemName2(getItemName2());
		entity.setItemName3(getItemName3());
		entity.setTransferCount(getTransferCount());
		entity.setDispatchDetail(getDispatchDetail());
		entity.setInstockCount(getInstockCount());
		entity.setTicketNo(getTicketNo());
		entity.setColorCode(getColorCode());
		
		return entity;
	}

	public void setHeadValue(WorkMaintenancePopupHead head)
	{
		if(head.getTransferType().equals("1"))
		{
			setHeadTransferType("入出库");
		}
		else if(head.getTransferType().equals("2"))
		{
			setHeadTransferType("入库");
		}
		else
		{
			setHeadTransferType("出库(直行)");
		}
		if(head.getDivision().equals("1"))
		{
			setHeadDivision("最终站台");
		}
		else
		{
			setHeadDivision("当前站台");
		}
		setHeadStation(head.getStation());
		
	}

	public void setRowValueByEntity(WorkMaintenancePopupEntity entity,
			int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTransferType(entity.getTransferType());
		setStatus(entity.getStatus());
		setMckey(entity.getMckey());
		setStationNo(entity.getStationNo() + ":" + entity.getStationName());
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
