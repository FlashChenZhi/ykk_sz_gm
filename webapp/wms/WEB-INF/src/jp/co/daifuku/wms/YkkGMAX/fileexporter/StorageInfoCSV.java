package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.StorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.StorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;

public class StorageInfoCSV implements IExportable
{
	private final String STORAGE_INFO_HEAD = "STORAGE_INFO_HEAD";
	private Page page;

	public StorageInfoCSV(Page page)
	{
		this.page = page;
	}

	public StorageInfoHead getStorageInfoHead()
	{
		return (StorageInfoHead) page.getSession().getAttribute(
				STORAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "物料编号,品名1,品名2,品名3,颜色,自动仓库,平置,在库总数,预定入库量";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre.getStorageInfoListSQL(getStorageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		StorageInfoEntity entity = new StorageInfoEntity();

		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setColor(resultSetProxy.getString("color_code"));
		entity.setItemName1(resultSetProxy.getString("zkname1"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		entity.setAutoCount(Long.parseLong(resultSetProxy
				.getString("auto_total")));
		entity.setFlatCount(Long.parseLong(resultSetProxy
				.getString("flat_total")));
		try
		{
			entity.setTotalInstockCount(Long.parseLong(resultSetProxy
					.getString("all_total")));
		} catch (Exception ex)
		{
			entity.setTotalInstockCount(0);
		}
		try
		{
			entity.setNotStockinCount(Long.parseLong(resultSetProxy
					.getString("not_in_total")));
		} catch (Exception ex)
		{
			entity.setNotStockinCount(0);
		}

		String line = entity.getItemCode() + ",";

		line += entity.getItemName1() + ",";
		line += entity.getItemName2() + ",";
		line += entity.getItemName3() + ",";
		line += entity.getColor() + ",";
		line += String.valueOf(entity.getAutoCount()) + ",";
		line += String.valueOf(entity.getFlatCount()) + ",";
		line += String.valueOf(entity.getTotalInstockCount()) + ",";
		line += String.valueOf(entity.getNotStockinCount()) + ",";
		return line;
	}

	public int getMaxLine()
	{
		return 65000;
	}
}
