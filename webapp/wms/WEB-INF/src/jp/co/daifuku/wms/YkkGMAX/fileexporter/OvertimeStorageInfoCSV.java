package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.OvertimeStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class OvertimeStorageInfoCSV implements IExportable
{
	private final String OVERTIME_STORAGE_INFO_HEAD = "OVERTIME_STORAGE_INFO_HEAD";
	private Page page;

	public OvertimeStorageInfoCSV(Page page)
	{
		this.page = page;
	}

	public OvertimeStorageInfoHead getOvertimeStorageInfoHead()
	{
		return (OvertimeStorageInfoHead) page.getSession().getAttribute(
				OVERTIME_STORAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "时间,物料编号,品名1,品名2,品名3,颜色,生产票号,箱子编号,货位编号,在库数";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre
				.getOvertimeStorageInfoListSQL(getOvertimeStorageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		OvertimeStorageInfoEntity entity = new OvertimeStorageInfoEntity();
		OvertimeStorageInfoHead head = getOvertimeStorageInfoHead();

		if (head.getBenchmarkObject().equals("1"))
		{
			entity.setDateTime(StringUtils.getDateFromDateTime(resultSetProxy
					.getString("nyukohiji")));
		} else if (head.getBenchmarkObject().equals("2"))
		{
			entity.setDateTime(StringUtils.getDateFromDateTime(resultSetProxy
					.getString("reception_datetime")));
		} else
		{
			entity.setDateTime(StringUtils.getDateFromDateTime(resultSetProxy
					.getString("koshinhiji")));
		}
		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setItemName1(resultSetProxy.getString("zkname1"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		entity.setColor(resultSetProxy.getString("color_code"));
		entity.setTicketNo(resultSetProxy.getString("ticket_no"));
		entity.setBucketNo(resultSetProxy.getString("bucket_no"));

		if (resultSetProxy.getString("storage_place_flag").equals(
				DBFlags.StoragePlaceFlag.FLAT))
		{
			entity.setLocationNo("平库");
		} else
		{
			Connection connection = null;
			try
			{
				connection = ConnectionManager.getConnection();
				ASRSInfoCentre c = new ASRSInfoCentre(connection);
				entity.setLocationNo(c.getLocationNoBySystemid(resultSetProxy
						.getString("systemid")));
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
		}
		try
		{
			entity.setInstockCount(Integer.parseInt(resultSetProxy
					.getString("zaikosu")));
		} catch (Exception ex)
		{
			entity.setInstockCount(0);
		}
		String line = StringUtils.formatDateFromDBToPage(entity.getDateTime())
				+ ",";

		line += entity.getItemCode() + ",";
		line += entity.getItemName1() + ",";
		line += entity.getItemName2() + ",";
		line += entity.getItemName3() + ",";
		line += entity.getColor() + ",";
		line += entity.getTicketNo() + ",";
		line += entity.getBucketNo() + ",";
		line += StringUtils
				.formatLocationNoFromDBToPage(entity.getLocationNo())
				+ ",";
		line += String.valueOf(entity.getInstockCount());
		return line;
	}

	public int getMaxLine()
	{
		return 10000;
	}
}
