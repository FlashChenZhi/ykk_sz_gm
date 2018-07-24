package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.ItemViewEntity;

public class ItemViewListProxy
{
	public ItemViewListProxy(ListCell list)
	{
		this.list = list;
	}
	
	private ListCell list;
	
	private int NO_COLUMN = 1;

	private int ITEM_CODE_COLUMN = 2;
	
	private int ITEM_NAME_1_COLUMN = 3;
	
	private int ITEM_NAME_2_COLUMN = 4;
	
	private int ITEM_NAME_3_COLUMN = 5;

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
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

	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
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

	public void setRowValueByEntity(ItemViewEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setItemCode(entity.getItemCode());
		setItemName1(entity.getItemName1());
		setItemName2(entity.getItemName2());
		setItemName3(entity.getItemName3());
	}
}
