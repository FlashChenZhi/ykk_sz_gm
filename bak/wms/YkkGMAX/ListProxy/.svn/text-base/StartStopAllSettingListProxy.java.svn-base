package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.StartStopAllSettingEntity;

public class StartStopAllSettingListProxy
{
	public StartStopAllSettingListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;
	
	private int CHECK_BOX_COLUMN = 1;
	
	private int CONTROLLER_NO_COLUMN = 2;
	
	private int SYSTEM_STATUS_COLUMN = 3;
	
	private int WORK_COUNT_COLUMN = 4;

	public int getCHECK_BOX_COLUMN()
	{
		return CHECK_BOX_COLUMN;
	}

	public int getCONTROLLER_NO_COLUMN()
	{
		return CONTROLLER_NO_COLUMN;
	}

	public int getSYSTEM_STATUS_COLUMN()
	{
		return SYSTEM_STATUS_COLUMN;
	}

	public int getWORK_COUNT_COLUMN()
	{
		return WORK_COUNT_COLUMN;
	}
	
	public void setCheck(boolean check)
	{
		list.setChecked(CHECK_BOX_COLUMN, check);
	}
	
	public boolean getCheck()
	{
		return list.getChecked(CHECK_BOX_COLUMN);
	}
	
	public void setControllerNo(String controllerNo)
	{
		list.setValue(CONTROLLER_NO_COLUMN, controllerNo);
	}
	
	public String getControllerNo()
	{
		return list.getValue(CONTROLLER_NO_COLUMN);
	}
	
	public void setSystemStatus(String systemStatus)
	{
		list.setValue(SYSTEM_STATUS_COLUMN, systemStatus);
	}
	
	public String getSystemStatus()
	{
		return list.getValue(SYSTEM_STATUS_COLUMN);
	}
	
	public int getWorkCount()
	{
		String workCount = list.getValue(WORK_COUNT_COLUMN);
		if (workCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(WORK_COUNT_COLUMN)
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

	public void setWorkCount(int workCount)
	{
		list.setValue(WORK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(workCount));
	}
	
	public void setRowValueByEntity(StartStopAllSettingEntity entity)
	{
		setCheck(true);
		setControllerNo(entity.getControllerNo());
		setSystemStatus(entity.getSystemStatus());
		setWorkCount(entity.getWorkCount());
	}
}
