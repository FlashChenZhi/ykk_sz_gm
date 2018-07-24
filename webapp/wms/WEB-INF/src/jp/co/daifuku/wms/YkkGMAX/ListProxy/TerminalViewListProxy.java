package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.math.BigDecimal;
import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.TerminalViewEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class TerminalViewListProxy
{
	public TerminalViewListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int NO_COLUMN = 1;

	private int TERMINAL_NO_COLUMN = 2;

	private int SECTION_COLUMN = 3;

	private int LINE_COLUMN = 4;

	private int UNIT_WEIGHT_UPPER_COLUMN = 5;

	private int UNIT_WEIGHT_LOWER_COLUMN = 6;

	private int STORAGE_UPPER_COLUMN = 7;

	private int STORAGE_LOWER_COLUMN = 8;

	private int SHIP_UPPER_COLUMN = 9;

	private int SHIP_LOWER_COLUMN = 10;

	public int getLINE_COLUMN()
	{
		return LINE_COLUMN;
	}

	public int getSECTION_COLUMN()
	{
		return SECTION_COLUMN;
	}

	public int getSHIP_LOWER_COLUMN()
	{
		return SHIP_LOWER_COLUMN;
	}

	public int getSHIP_UPPER_COLUMN()
	{
		return SHIP_UPPER_COLUMN;
	}

	public int getSTORAGE_LOWER_COLUMN()
	{
		return STORAGE_LOWER_COLUMN;
	}

	public int getSTORAGE_UPPER_COLUMN()
	{
		return STORAGE_UPPER_COLUMN;
	}

	public int getUNIT_WEIGHT_LOWER_COLUMN()
	{
		return UNIT_WEIGHT_LOWER_COLUMN;
	}

	public int getUNIT_WEIGHT_UPPER_COLUMN()
	{
		return UNIT_WEIGHT_UPPER_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getTERMINAL_NO_COLUMN()
	{
		return TERMINAL_NO_COLUMN;
	}

	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}

	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}

	public String getTerminalNo()
	{
		return list.getValue(TERMINAL_NO_COLUMN);
	}

	public void setTerminalNo(String terminalNo)
	{
		list.setValue(TERMINAL_NO_COLUMN, terminalNo);
	}

	public String getSection()
	{
		return list.getValue(SECTION_COLUMN);
	}

	public void setSection(String section)
	{
		list.setValue(SECTION_COLUMN, section);
	}

	public String getLine()
	{
		return list.getValue(LINE_COLUMN);
	}

	public void setLine(String line)
	{
		list.setValue(LINE_COLUMN, line);
	}

	public BigDecimal getUnitWeightUpper()
	{
		String unitWeightUpper = list.getValue(UNIT_WEIGHT_UPPER_COLUMN);
		if (unitWeightUpper != null)
		{
			try
			{
				return new BigDecimal(unitWeightUpper.substring(0,
						unitWeightUpper.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setUnitWeightUpper(BigDecimal unitWeightUpper)
	{
		list.setValue(UNIT_WEIGHT_UPPER_COLUMN, String.valueOf(unitWeightUpper)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getUnitWeightLower()
	{
		String unitWeightLower = list.getValue(UNIT_WEIGHT_LOWER_COLUMN);
		if (unitWeightLower != null)
		{
			try
			{
				return new BigDecimal(unitWeightLower.substring(0,
						unitWeightLower.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setUnitWeightLower(BigDecimal unitWeightLower)
	{
		list.setValue(UNIT_WEIGHT_LOWER_COLUMN, String.valueOf(unitWeightLower)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getStorageUpper()
	{
		String storageUpper = list.getValue(STORAGE_UPPER_COLUMN);
		if (storageUpper != null)
		{
			try
			{
				return new BigDecimal(storageUpper.substring(0, storageUpper
						.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setStorageUpper(BigDecimal storageUpper)
	{
		list.setValue(STORAGE_UPPER_COLUMN, String.valueOf(storageUpper)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getStorageLowerr()
	{
		String storageLower = list.getValue(STORAGE_LOWER_COLUMN);
		if (storageLower != null)
		{
			try
			{
				return new BigDecimal(storageLower.substring(0, storageLower
						.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setStorageLower(BigDecimal storageLower)
	{
		list.setValue(STORAGE_LOWER_COLUMN, String.valueOf(storageLower)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getShipUpper()
	{
		String shipUpper = list.getValue(SHIP_UPPER_COLUMN);
		if (shipUpper != null)
		{
			try
			{
				return new BigDecimal(shipUpper.substring(0,
						shipUpper.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setShipUpper(BigDecimal shipUpper)
	{
		list.setValue(SHIP_UPPER_COLUMN, String.valueOf(shipUpper)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getShipLowerr()
	{
		String shipLower = list.getValue(SHIP_LOWER_COLUMN);
		if (shipLower != null)
		{
			try
			{
				return new BigDecimal(shipLower.substring(0,
						shipLower.length() - 1));
			}
			catch (Exception ex)
			{
				return new BigDecimal(0);
			}
		}
		else
		{
			return new BigDecimal(0);
		}
	}

	public void setShipLower(BigDecimal shipLower)
	{
		list.setValue(SHIP_LOWER_COLUMN, String.valueOf(shipLower)
				+ StringUtils.SinglePercentageMark);
	}

	public void setRowValue(TerminalViewEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setTerminalNo(entity.getTerminalNo());
		setSection(entity.getSection());
		setLine(entity.getLine());
		setUnitWeightUpper(entity.getUnitWeightUpper());
		setUnitWeightLower(entity.getUnitWeightLower());
		setStorageUpper(entity.getStorageUpper());
		setStorageLower(entity.getStorageLower());
		setShipUpper(entity.getShipUpper());
		setShipLower(entity.getShipLower());
	}

	public TerminalViewEntity getRowEntity()
	{
		TerminalViewEntity entity = new TerminalViewEntity();

		entity.setTerminalNo(getTerminalNo());
		entity.setSection(getSection());
		entity.setLine(getLine());

		return entity;
	}
}
