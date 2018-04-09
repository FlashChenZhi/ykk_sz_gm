package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class LocationStorageInfoCSV implements IExportable
{
	private final String LOCATION_STORAGE_INFO_HEAD = "LOCATION_STORAGE_INFO_HEAD";

	private final Page page;

	public LocationStorageInfoCSV(Page page)
	{
		this.page = page;
	}

	public LocationStorageInfoHead getLocationStorageInfoHead()
	{
		return (LocationStorageInfoHead) page.getSession().getAttribute(
				LOCATION_STORAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "货位,物料编号,品名,颜色,生产票号,计量报告状态,箱子编号,在库数,入库时间";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre
				.getLocationStorageInfoListSQL(getLocationStorageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		LocationStorageInfoEntity entity = new LocationStorageInfoEntity();

		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setItemName1(resultSetProxy.getString("zkname1"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		entity.setColor(resultSetProxy.getString("color_code"));
		entity.setTicketNo(resultSetProxy.getString("ticket_no"));
		entity.setWeightReportFlag(DBFlags.WeightReportCompleteFlag
				.parseDBToPage(resultSetProxy
						.getString("weight_report_complete_flag")));
		entity.setBucketNo(resultSetProxy.getString("bucket_no"));

		entity.setStockinDateTime(resultSetProxy.getString("nyukohiji"));

        int planQty = Integer.parseInt(resultSetProxy.getString("plan_qty"));

        if (resultSetProxy.getString("weight_report_complete_flag").equals(
                DBFlags.WeightReportCompleteFlag.UNCOMPLETED
        ) && planQty != 0){
			try
			{
				entity.setInstockCount(Integer.parseInt(resultSetProxy
						.getString("plan_qty")));
			} catch (Exception ex)
			{
				entity.setInstockCount(0);
			}
			entity.setLocationNo("预定入库仓库");
		} else if (!resultSetProxy.getString("storage_place_flag").equals(
				DBFlags.StoragePlaceFlag.AUTO)
				&& (resultSetProxy.getString("weight_report_complete_flag")
						.equals(DBFlags.WeightReportCompleteFlag.COMPLETED) || resultSetProxy
						.getString("weight_report_complete_flag").equals(
								DBFlags.WeightReportCompleteFlag.REPORTING)))
		{
			try
			{
				entity.setInstockCount(Integer.parseInt(resultSetProxy
						.getString("zaikosu")));
			} catch (Exception ex)
			{
				entity.setInstockCount(0);
			}
			entity.setLocationNo("平置");
		}

		else
		{
			try
			{
				entity.setInstockCount(Integer.parseInt(resultSetProxy
						.getString("zaikosu")));

			} catch (Exception ex)
			{
				entity.setInstockCount(0);
			}
			entity.setLocationNo(resultSetProxy.getString("syozaikey"));

			String zaijyoflg = resultSetProxy.getString("zaijyoflg");
			String accessflg = resultSetProxy.getString("accessflg");
			String tanaflg = resultSetProxy.getString("tanaflg");

			if (zaijyoflg.equals(DBFlags.ZaijyoFlg.ERRO_LOCATION))
			{
				entity.setLocationStatus("异常货位");
			} else if (tanaflg.equals(DBFlags.Tanaflg.FORBID_LOCAT))
			{
				entity.setLocationStatus("禁止货位");
			} else if (accessflg.equals(DBFlags.AccessFlag.UNASSIGNABLE))
			{
				entity.setLocationStatus("访问不可货位");
			} else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
					|| zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
					|| zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUTING))
			{
				entity.setLocationStatus("作业货位");
			} else if (tanaflg.equals(DBFlags.Tanaflg.USED_LOCAT))
			{
				entity.setLocationStatus("实货位");
			} else
			{
				entity.setLocationStatus("空货位");
			}

		}

		String line = "";
		if (!StringUtils.IsNullOrEmpty(entity.getLocationStatus()))
		{
			line = StringUtils.formatLocationNoFromDBToPage(entity
					.getLocationNo())
					+ ":" + entity.getLocationStatus() + ",";
		} else
		{
			line = StringUtils.formatLocationNoFromDBToPage(entity
					.getLocationNo())
					+ ",";
		}

		line += entity.getItemCode() + ",";
		line += entity.getItemName1() + ",";
		line += entity.getColor() + ",";
		line += entity.getTicketNo() + ",";
		line += entity.getWeightReportFlag() + ",";
		line += entity.getBucketNo() + ",";
		line += String.valueOf(entity.getInstockCount()) + ",";
		line += StringUtils.formatDateFromDBToPage(entity.getStockinDateTime());

		return line;
	}

	public int getMaxLine()
	{
		return 65000;
	}

}
