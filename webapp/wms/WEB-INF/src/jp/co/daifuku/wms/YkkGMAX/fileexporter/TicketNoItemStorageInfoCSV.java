package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.TicketNoItemStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class TicketNoItemStorageInfoCSV implements IExportable
{
	private final String TICKET_NO_ITEM_STORAGE_INFO_HEAD = "TICKET_NO_ITEM_STORAGE_INFO_HEAD";
	private Page page;

	public TicketNoItemStorageInfoCSV(Page page)
	{
		this.page = page;
	}

	public TicketNoItemStorageInfoHead getTicketNoItemStorageInfoHead()
	{
		return (TicketNoItemStorageInfoHead) page.getSession().getAttribute(
				TICKET_NO_ITEM_STORAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "生产票号,物料编号,品名1,品名2,品名3,颜色,货位编号,箱子编号,在库数,入库时间";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre
				.getTicketNoItemStorageInfoListSQL(getTicketNoItemStorageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		TicketNoItemStorageInfoEntity entity = new TicketNoItemStorageInfoEntity();

		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setColor(resultSetProxy.getString("color_code"));
		entity.setItemName1(resultSetProxy.getString("zkname1"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		entity.setTicketNo(resultSetProxy.getString("ticket_no"));
        int planQty = Integer.parseInt(resultSetProxy.getString("plan_qty"));

        if (resultSetProxy.getString("weight_report_complete_flag").equals(
                DBFlags.WeightReportCompleteFlag.UNCOMPLETED
        ) && planQty != 0){
			entity.setLocationNo("未入库仓库");
		} else if (resultSetProxy.getString("storage_place_flag").equals(
				DBFlags.StoragePlaceFlag.FLAT))
		{
			entity.setLocationNo("平库");
		} else
		{
			entity.setLocationNo(resultSetProxy.getString("syozaikey"));
		}
		entity.setBucketNo(resultSetProxy.getString("bucket_no"));
		entity.setMessageDateTime(resultSetProxy.getString("nyukohiji"));
		try
		{
            if (resultSetProxy.getString("weight_report_complete_flag").equals(
                    DBFlags.WeightReportCompleteFlag.UNCOMPLETED
            ) && planQty != 0){
				entity.setInstockCount(Integer.parseInt(resultSetProxy
						.getString("plan_qty")));
			} else
			{
				entity.setInstockCount(Integer.parseInt(resultSetProxy
						.getString("zaikosu")));
			}
		} catch (Exception ex)
		{
			entity.setInstockCount(0);
		}

		String line = entity.getTicketNo() + ",";
		line += entity.getItemCode() + ",";
		line += entity.getItemName1() + ",";
		line += entity.getItemName2() + ",";
		line += entity.getItemName3() + ",";
		line += entity.getColor() + ",";
		line += StringUtils
				.formatLocationNoFromDBToPage(entity.getLocationNo())
				+ ",";
		line += entity.getBucketNo() + ",";
		line += String.valueOf(entity.getInstockCount()) + ",";
		line += StringUtils.formatDateAndTimeFromDBToPage(entity
				.getMessageDateTime())
				+ ",";
		return line;
	}

	public int getMaxLine()
	{
		return 65000;
	}
}
