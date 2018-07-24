package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.UseRateInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class UseRateInfoListProxy
{
	public UseRateInfoListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int RM_NO_COLUMN = 1;

	private int USED_LOCAT_COLUMN = 2;

	private int EMPTY_LOCAT_COLUMN = 3;

	private int ERROR_LOCAT_COLUMN = 4;

	private int FORBID_LOCAT_COLUMN = 5;

	private int CANNOT_CALL_LOCAT_COLUMN = 6;

	private int TOTAL_LOCAT_COLUMN = 7;

	private int USE_RATE_COLUMN = 8;

	public int getCANNOT_CALL_LOCAT_COLUMN()
	{
		return CANNOT_CALL_LOCAT_COLUMN;
	}

	public int getEMPTY_LOCAT_COLUMN()
	{
		return EMPTY_LOCAT_COLUMN;
	}

	public int getERROR_LOCAT_COLUMN()
	{
		return ERROR_LOCAT_COLUMN;
	}

	public int getFORBID_LOCAT_COLUMN()
	{
		return FORBID_LOCAT_COLUMN;
	}

	public int getRM_NO_COLUMN()
	{
		return RM_NO_COLUMN;
	}

	public int getTOTAL_LOCAT_COLUMN()
	{
		return TOTAL_LOCAT_COLUMN;
	}

	public int getUSE_RATE_COLUMN()
	{
		return USE_RATE_COLUMN;
	}

	public int getUSED_LOCAT_COLUMN()
	{
		return USED_LOCAT_COLUMN;
	}

	public void setRMNo(String rmNo)
	{
		list.setValue(RM_NO_COLUMN, rmNo);
	}

	public String getRMNo()
	{
		return list.getValue(RM_NO_COLUMN);
	}

	public void setUsedLocat(BigDecimal usedLocat)
	{
		list.setValue(USED_LOCAT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(usedLocat));
	}

	public BigDecimal getUsedLocat()
	{
		String usedLocat = list.getValue(USED_LOCAT_COLUMN);
		if (usedLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(USED_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setEmptyLocat(BigDecimal emptyLocat)
	{
		list.setValue(EMPTY_LOCAT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(emptyLocat));
	}

	public BigDecimal getEmptyLocat()
	{
		String emptyLocat = list.getValue(EMPTY_LOCAT_COLUMN);
		if (emptyLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(EMPTY_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setErrorLocat(BigDecimal errorLocat)
	{
		list.setValue(ERROR_LOCAT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(errorLocat));
	}

	public BigDecimal getErrorLocat()
	{
		String errorLocat = list.getValue(ERROR_LOCAT_COLUMN);
		if (errorLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(ERROR_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setForbidLocat(BigDecimal forbidLocat)
	{
		list.setValue(FORBID_LOCAT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(forbidLocat));
	}

	public BigDecimal getForbidLocat()
	{
		String forbidLocat = list.getValue(FORBID_LOCAT_COLUMN);
		if (forbidLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(FORBID_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setCannotCallLocat(BigDecimal cannotCallLocat)
	{
		list.setValue(CANNOT_CALL_LOCAT_COLUMN, DecimalFormat
				.getIntegerInstance().format(cannotCallLocat));
	}

	public BigDecimal getCannotCallLocat()
	{
		String cannotCallLocat = list.getValue(CANNOT_CALL_LOCAT_COLUMN);
		if (cannotCallLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(CANNOT_CALL_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setTotalLocat(BigDecimal totalLocat)
	{
		list.setValue(TOTAL_LOCAT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(totalLocat));
	}

	public BigDecimal getTotalLocat()
	{
		String totalLocat = list.getValue(TOTAL_LOCAT_COLUMN);
		if (totalLocat != null)
		{
			try
			{
				return new BigDecimal(list.getValue(TOTAL_LOCAT_COLUMN)
						.replaceAll(",", ""));
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

	public void setUseRate(BigDecimal useRate)
	{
		list.setValue(USE_RATE_COLUMN, String.valueOf(useRate)
				+ StringUtils.SinglePercentageMark);
	}

	public BigDecimal getUseRate()
	{
		String useRate = list.getValue(USE_RATE_COLUMN);
		if (useRate != null)
		{
			try
			{
				return new BigDecimal(useRate.substring(0,
						useRate.length() - 1));
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
	
	public void setRowValueByEntity(UseRateInfoEntity entity,int rmNo)
	{
		if(rmNo == 0)
		{
			setRMNo("全部");
		}
		else
		{
			setRMNo("RM" + String.valueOf(rmNo));
		}
		setUsedLocat(entity.getUsedLocat());
		setEmptyLocat(entity.getEmptyLocat());
		setErrorLocat(entity.getErrorLocat());
		setForbidLocat(entity.getForbidLocat());
		setCannotCallLocat(entity.getCanNotCallLocat());
		setTotalLocat(entity.getTotalLocat());
		setUseRate(entity.getUseRate());
	}
}
