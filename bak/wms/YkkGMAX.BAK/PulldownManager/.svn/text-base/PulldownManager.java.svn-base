package jp.co.daifuku.wms.YkkGMAX.PulldownManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.PullDown;
import jp.co.daifuku.wms.YkkGMAX.Entities.PrinterNoAndNameEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalStationEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.StationEntity;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class PulldownManager
{

	public static void FillBankPullDown(PullDown pul) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List bankList = centre.getBank();

			boolean selected = true;
			for (int i = 0; i < bankList.size(); i++)
			{
				String bank = (String) bankList.get(i);
				pul.addItem(bank, "", bank, selected);
				selected = false;
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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}

	}

	public static void FillErrorMessagePullDown(PullDown pul)
			throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List typeList = centre.getErrorMessageDivision();

			pul.addItem("全部", "", "全部", true);
			for (int i = 0; i < typeList.size(); i++)
			{
				String type = (String) typeList.get(i);
				pul.addItem(type, "", type, false);
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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	// public static void FillMadeLine(PullDown pul) throws YKKDBException,
	// YKKSQLException
	// {
	// Connection conn = null;
	// try
	// {
	// conn = ConnectionManager.getConnection();
	// ASRSInfoCentre centre = new ASRSInfoCentre(conn);
	// List lineList = centre.getMadeLine();
	//
	// boolean selected = true;
	// for (int i = 0; i < lineList.size(); i++)
	// {
	// String line = (String) lineList.get(i);
	// pul.addItem(line, "", line, selected);
	// selected = false;
	//
	// }
	// }
	// finally
	// {
	// if (conn != null)
	// {
	// try
	// {
	// conn.close();
	// }
	// catch (SQLException e)
	// {
	// DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
	// YKKDBException ex = new YKKDBException();
	// ex.setResourceKey("7200002");
	// throw ex;
	// }
	// }
	// }
	//
	// }
	//
	// public static void FillMadeSection(PullDown pul) throws YKKDBException,
	// YKKSQLException
	// {
	// Connection conn = null;
	// try
	// {
	// conn = ConnectionManager.getConnection();
	// ASRSInfoCentre centre = new ASRSInfoCentre(conn);
	// List sectionList = centre.getMadeSection();
	//
	// boolean selected = true;
	// for (int i = 0; i < sectionList.size(); i++)
	// {
	// String section = (String) sectionList.get(i);
	// pul.addItem(section, "", section, selected);
	// selected = false;
	//
	// }
	// }
	// finally
	// {
	// if (conn != null)
	// {
	// try
	// {
	// conn.close();
	// }
	// catch (SQLException e)
	// {
	// DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
	// YKKDBException ex = new YKKDBException();
	// ex.setResourceKey("7200002");
	// throw ex;
	// }
	// }
	// }
	//
	// }

	public static void FillMessagePullDown(PullDown pul) throws YKKDBException,
			YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List typeList = centre.getMessageDivision();

			pul.addItem("全部", "", "全部", true);
			for (int i = 0; i < typeList.size(); i++)
			{
				String type = (String) typeList.get(i);
				pul.addItem(type, "", type, false);
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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillPickingStationPullDown(PullDown pul)
			throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List stationNoList = centre.getPickingStationNo();

			boolean selected = true;
			for (int i = 0; i < stationNoList.size(); i++)
			{
				StationEntity station = (StationEntity) stationNoList.get(i);
				pul.addItem(station.getStno(), "", station.getStno() + ":"
						+ station.getStname(), selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillPrintStationPullDown(PullDown pul)
			throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List printerList = centre.getPrinterNoAndName();

			boolean selected = true;
			for (int i = 0; i < printerList.size(); i++)
			{
				PrinterNoAndNameEntity entity = (PrinterNoAndNameEntity) printerList
						.get(i);
				pul.addItem(entity.getPrinterNo(), "", entity.getPrinterNo()
						+ ":" + entity.getPrinterName(), selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillRejectStationPullDown(PullDown pul)
			throws YKKDBException, YKKSQLException
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List stationNoList = centre.getRejectStationNo();

			boolean selected = true;
			for (int i = 0; i < stationNoList.size(); i++)
			{
				StationEntity station = (StationEntity) stationNoList.get(i);
				pul.addItem(station.getStno(), "", station.getStno() + ":"
						+ station.getStname(), selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillStationPullDown(PullDown pul, boolean hasAllItem)
			throws YKKDBException, YKKSQLException
	{
		if (hasAllItem)
		{
			pul.addItem("", "", "全部", true);
		}
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List stationNoList = centre.getStationNo();

			boolean selected = !hasAllItem;
			for (int i = 0; i < stationNoList.size(); i++)
			{
				StationEntity station = (StationEntity) stationNoList.get(i);
				pul.addItem(station.getStno(), "", station.getStno() + ":"
						+ station.getStname(), selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillRetrievalStationPullDown(PullDown pul,
			boolean hasAllItem) throws YKKDBException, YKKSQLException
	{
		if (hasAllItem)
		{
			pul.addItem("", "", "无指定", true);
		}
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List retrievalStationList = centre.getRetrievalStation();

			boolean selected = !hasAllItem;
			for (int i = 0; i < retrievalStationList.size(); i++)
			{
				RetrievalStationEntity retrievalStation = (RetrievalStationEntity) retrievalStationList
						.get(i);
				pul.addItem(retrievalStation.getRetrievalStation(), "",
						retrievalStation.getRetrievalStation() + ":"
								+ retrievalStation.getRetrievalStationName(),
						selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}

	public static void FillIOHistoryRetrievalStationPullDown(PullDown pul,
			boolean hasAllItem) throws YKKDBException, YKKSQLException
	{
		if (hasAllItem)
		{
			pul.addItem("", "", "全部", true);
		}
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection();
			ASRSInfoCentre centre = new ASRSInfoCentre(conn);
			List retrievalStationList = centre.getIOHistoryRetrievalStation();

			boolean selected = !hasAllItem;
			for (int i = 0; i < retrievalStationList.size(); i++)
			{
				RetrievalStationEntity retrievalStation = (RetrievalStationEntity) retrievalStationList
						.get(i);
				pul.addItem(retrievalStation.getRetrievalStation(), "",
						retrievalStation.getRetrievalStationName(), selected);
				selected = false;

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
				catch (SQLException e)
				{
					DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
					YKKDBException ex = new YKKDBException();
					ex.setResourceKey("7200002");
					throw ex;
				}
			}
		}
	}
}
