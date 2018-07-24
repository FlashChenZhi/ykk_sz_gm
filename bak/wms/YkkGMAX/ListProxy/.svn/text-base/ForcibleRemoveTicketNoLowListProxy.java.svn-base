package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.wms.YkkGMAX.Entities.ForcibleRemoveTicketNoEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.ListHandler.SystemIdSortableHandler;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ForcibleRemoveTicketNoLowListProxy
{
	private final ListCell list;

	private final int ALL_COLUMN = 1;

	private final int TICKET_NO_COLUMN = 2;

	private final int ITEM_CODE_COLUMN = 3;

	private final int COLOR_CODE_COLUMN = 4;

	private final int INSTOCK_COUNT_COLUMN = 5;

	private final int SYSTEM_ID_COLUMN = 6;

	public ForcibleRemoveTicketNoLowListProxy(ListCell list)
	{
		this.list = list;
	}

	public boolean getAll()
	{
		return list.getChecked(ALL_COLUMN);
	}

	public int getALL_COLUMN()
	{
		return ALL_COLUMN;
	}

	public int getCOLOR_CODE_COLUMN()
	{
		return COLOR_CODE_COLUMN;
	}

	public String getColorCode()
	{
		return list.getValue(COLOR_CODE_COLUMN);
	}

	public ForcibleRemoveTicketNoEntity getforcibleRemoveTicketNoUpEntity()
	{
		ForcibleRemoveTicketNoEntity entity = new ForcibleRemoveTicketNoEntity();

		entity.setTicketNo(getTicketNo());
		entity.setItemCode(getItemCode());
		entity.setColorCode(getColorCode());
		entity.setInstockCount(getInstockCount());
		entity.setSystemId(getSystemId());

		return entity;
	}

	public int getINSTOCK_COUNT_COLUMN()
	{
		return INSTOCK_COUNT_COLUMN;
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

	public int getITEM_CODE_COLUMN()
	{
		return ITEM_CODE_COLUMN;
	}

	public String getItemCode()
	{
		return list.getValue(ITEM_CODE_COLUMN);
	}

	public int getSYSTEM_ID_COLUMN()
	{
		return SYSTEM_ID_COLUMN;
	}

	public String getSystemId()
	{
		return list.getValue(SYSTEM_ID_COLUMN);
	}

	public int getTICKET_NO_COLUMN()
	{
		return TICKET_NO_COLUMN;
	}

	public String getTicketNo()
	{
		return list.getValue(TICKET_NO_COLUMN);
	}

	public void InputForcibleRemoveTicketNoLowList(List inputList,
			ArrayList ticketNoToRemoveList)
	{
		for (int j = 0; j < inputList.size(); j++)
		{
			ForcibleRemoveTicketNoEntity entity = (ForcibleRemoveTicketNoEntity) inputList
					.get(j);
			boolean hasInputed = false;
			for (int i = 1; i < list.getMaxRows(); i++)
			{
				list.setCurrentRow(i);
				if (getSystemId().equals(entity.getSystemId()))
				{
					hasInputed = true;
					break;
				}
			}
			if (hasInputed)
			{
				continue;
			}

			list.addRow();
			list.setCurrentRow(list.getMaxRows() - 1);
			setRowValueByEntity(entity);
			SystemIdSortableHandler.insert(ticketNoToRemoveList, this
					.getforcibleRemoveTicketNoUpEntity());
		}
	}

	public boolean InputForcibleRemoveTicketNoLowList(String ticketNo,
			ArrayList ticketNoToRemoveList, Message message)
			throws YKKDBException, YKKSQLException
	{
		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection();

			ASRSInfoCentre centre = new ASRSInfoCentre(conn);

			List inputList = centre.getForcibleRemoveTicketNoLowList(ticketNo);

			if (inputList.size() <= 0)
			{
				message.setMsgResourceKey("7000027");
				return false;
			}
			InputForcibleRemoveTicketNoLowList(inputList, ticketNoToRemoveList);
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
		return true;
	}

	public void setAll(boolean all)
	{
		list.setChecked(ALL_COLUMN, all);
	}

	public void setColorCode(String color)
	{
		list.setValue(COLOR_CODE_COLUMN, color);
	}

	public void setInstockCount(int instockCount)
	{
		list.setValue(INSTOCK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(instockCount));
	}

	public void setItemCode(String itemCode)
	{
		list.setValue(ITEM_CODE_COLUMN, itemCode);
	}

	public void setRowValueByEntity(ForcibleRemoveTicketNoEntity entity)
	{
		setTicketNo(entity.getTicketNo());
		setItemCode(entity.getItemCode());
		setColorCode(entity.getColorCode());
		setInstockCount(entity.getInstockCount());
		setSystemId(entity.getSystemId());
		setAll(true);
	}

	public void setSystemId(String systemId)
	{
		list.setValue(SYSTEM_ID_COLUMN, systemId);
	}

	public void setTicketNo(String ticketNo)
	{
		list.setValue(TICKET_NO_COLUMN, ticketNo);
	}
}
