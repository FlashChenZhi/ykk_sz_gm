package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenancePopupEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class WorkMaintenancePopupCSV implements IExportable
{

	private final String TRANSFER_TYPE = "TRANSFER_TYPE";

	private final String STATION = "STATION";

	private final String DIVISION = "DIVISION";

	private Page page;

	public WorkMaintenancePopupCSV(Page page)
	{
		this.page = page;
	}

	private String getDivision()
	{
		return (String) page.getSession().getAttribute(DIVISION);
	}

	private String getStation()
	{
		return (String) page.getSession().getAttribute(STATION);
	}

	private String getTransferType()
	{
		return (String) page.getSession().getAttribute(TRANSFER_TYPE);
	}

	public String generateHead()
	{
		return "搬送区分,状态,MCKEY,站台,搬送源站台/目标站台,货位编号,箱子编号,物料编号,品名1,品名2,品名3,搬送个数";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre.getWorkMaintenancePopupListSQL(getTransferType(),
				getStation(), getDivision());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		WorkMaintenancePopupEntity entity = new WorkMaintenancePopupEntity();

		entity.setTransferType(DBFlags.Nyusyukbn.parseDBToPage(resultSetProxy
				.getString("nyusyukbn")));
		entity.setStatus(DBFlags.HjyotaiFlg.parseDBToPage(resultSetProxy
				.getString("hjyotaiflg")));
		entity.setMckey(resultSetProxy.getString("mckey"));
		entity.setStationNo(resultSetProxy.getString("startstno"));

		Connection connection = null;
		try
		{
			connection = ConnectionManager.getConnection();
			ASRSInfoCentre c = new ASRSInfoCentre(connection);
			entity.setStationName(c.getStNameByStno(resultSetProxy
					.getString("startstno")));
		} catch (Exception ex)
		{
		}
		finally
		{
			if(connection != null)
			{
				connection.close();
			}
		}

		entity.setMotoStationNo(resultSetProxy.getString("motostno"));
		entity.setSakiStationNo(resultSetProxy.getString("sakistno"));
		entity.setLocationNo(resultSetProxy.getString("syozaikey"));
		entity.setBucketNo(resultSetProxy.getString("bucket_no"));
		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setItemName1(resultSetProxy.getString("zkname1"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		try
		{
			entity.setTransferCount(Integer.parseInt(resultSetProxy
					.getString("SUM(nyusyusu)")));
		} catch (Exception ex)
		{
			entity.setTransferCount(0);
		}
		entity.setDispatchDetail(DBFlags.DispatchDetail
				.parseDBToPage(resultSetProxy.getString("sijisyosai")));
		try
		{
			entity.setInstockCount(Integer.parseInt(resultSetProxy
					.getString("zaikosu")));
		} catch (Exception ex)
		{
			entity.setInstockCount(0);
		}
		entity.setTicketNo(resultSetProxy.getString("ticket_no"));
		entity.setColorCode(resultSetProxy.getString("color_code"));

		String line = entity.getTransferType() + ",";
		line += entity.getStatus() + ",";
		line += entity.getMckey() + ",";
		line += entity.getStationNo() + ":" + entity.getStationName() + ",";
		line += entity.getMotoStationNo() + StringUtils.ToMark
				+ entity.getSakiStationNo() + ",";
		line += StringUtils
				.formatLocationNoFromDBToPage(entity.getLocationNo())
				+ ",";
		line += entity.getBucketNo() + ",";
		line += entity.getItemCode() + ",";
		line += entity.getItemName1() + ",";
		line += entity.getItemName2() + ",";
		line += entity.getItemName3() + ",";
		line += entity.getTransferCount() + ",";
		return line;
	}

	public int getMaxLine()
	{
		return 10000;
	}

}
