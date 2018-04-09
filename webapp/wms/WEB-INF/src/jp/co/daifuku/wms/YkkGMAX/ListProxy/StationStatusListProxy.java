package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.StationStatusEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class StationStatusListProxy
{
	public StationStatusListProxy(ListCell list)
	{
		this.list = list;
	}


	
	private ListCell list;

	private int CHECK_COLUMN = 1;

	private int STATION_NO_COLUMN = 2;

	private int STATION_NAME_COLUMN = 3;

	private int TRANSFER_MODE_COLUMN = 4;

	private int WORK_MODE_COLUMN = 5;

	private int MACHINE_STATUS_COLUMN = 6;

	private int WORK_STATUS_COLUMN = 7;

	private int WORK_COUNT_COLUMN = 8;

	public int getCHECK_COLUMN()
	{
		return CHECK_COLUMN;
	}

	public int getMACHINE_STATUS_COLUMN()
	{
		return MACHINE_STATUS_COLUMN;
	}

	public int getSTATION_NAME_COLUMN()
	{
		return STATION_NAME_COLUMN;
	}

	public int getSTATION_NO_COLUMN()
	{
		return STATION_NO_COLUMN;
	}

	public int getTRANSFER_MODE_COLUMN()
	{
		return TRANSFER_MODE_COLUMN;
	}

	public int getWORK_COUNT_COLUMN()
	{
		return WORK_COUNT_COLUMN;
	}

	public int getWORK_MODE_COLUMN()
	{
		return WORK_MODE_COLUMN;
	}

	public int getWORK_STATUS_COLUMN()
	{
		return WORK_STATUS_COLUMN;
	}

	public boolean getCheck()
	{
		return list.getChecked(CHECK_COLUMN);
	}

	public void setCheck(boolean check)
	{
		list.setChecked(CHECK_COLUMN, check);
	}

	public String getStationNo()
	{
		return list.getValue(STATION_NO_COLUMN);
	}

	public void setStationNo(String stationNo)
	{
		list.setValue(STATION_NO_COLUMN, stationNo);
	}

	public String getStationName()
	{
		return list.getValue(STATION_NAME_COLUMN);
	}

	public void setStationName(String stationName)
	{
		list.setValue(STATION_NAME_COLUMN, stationName);
	}

	public String getTransferMode()
	{
		return list.getValue(TRANSFER_MODE_COLUMN);
	}

	public void setTransferMode(String transferMode)
	{
		list.setValue(TRANSFER_MODE_COLUMN, transferMode);
	}

	public String getWorkMode()
	{
		return list.getValue(WORK_MODE_COLUMN);
	}

	public void setWorkMode(String workMode)
	{
		list.setValue(WORK_MODE_COLUMN, workMode);
	}

	public String getMachineStatus()
	{
		return list.getValue(MACHINE_STATUS_COLUMN);
	}

	public void setMachineStatus(String machineStatus)
	{
		list.setValue(MACHINE_STATUS_COLUMN, machineStatus);
	}

	public String getWorkStatus()
	{
		return list.getValue(WORK_STATUS_COLUMN);
	}

	public void setWorkStatus(String workStatus)
	{
		list.setValue(WORK_STATUS_COLUMN, workStatus);
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
		list.setValue(WORK_COUNT_COLUMN, DecimalFormat
				.getIntegerInstance().format(workCount));
	}
	
	public void setRowValue() throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			
			List stationList = centre.getStationStatusList();
			
			for(int i = 0 ; i < stationList.size() ; i++)
			{
				StationStatusEntity entity = (StationStatusEntity)stationList.get(i);
				
				setStationNo(entity.getStationNo());
				setStationName(entity.getStationName());
				setTransferMode(entity.getTransferMode());
				setWorkMode(entity.getWorkMode());
				setMachineStatus(entity.getMachineStatus());
				setWorkStatus(entity.getWorkStatus());
				setWorkCount(entity.getWorkCount());
			}
		}
		finally
		{
		    if (conn != null)
		    {
			try
			{
			    conn.close();
			}
			catch (SQLException sqlEx)
			{
			    DebugPrinter.print(DebugLevel.ERROR, sqlEx.getMessage());
			    YKKDBException ex = new YKKDBException();
			    ex.setResourceKey("7200002");
			    throw ex;		    
			}
		    }
		}
	}
}
