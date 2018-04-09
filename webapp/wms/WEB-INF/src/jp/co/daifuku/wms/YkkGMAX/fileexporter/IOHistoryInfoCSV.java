package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.math.BigDecimal;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistoryInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistoryInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.DBFlags;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class IOHistoryInfoCSV implements IExportable
{
	private final String IO_HISTORY_INFO_HEAD = "IO_HISTORY_INFO_HEAD";
	private Page page = null;

	public IOHistoryInfoCSV(Page page)
	{
		this.page = page;
	}

	private IOHistoryInfoHead getIOHistoryInfoHead()
	{
		return (IOHistoryInfoHead) page.getSession().getAttribute(
				IO_HISTORY_INFO_HEAD);
	}

	public String generateHead()
	{
		return "发生时间,作业区分,物料编号,品名1,品名2,品名3,颜色号,生产票号,箱子编号,货位编号,作业数量,出库指示No,生产开始日,生产线1,订单号,单位重量,作业站台,用户号码,开始站台,最终站台";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre.getIOHistoryInfoListSQL(getIOHistoryInfoHead());
	}

	private IOHistoryInfoEntity entity = new IOHistoryInfoEntity();
	private StringBuffer line = null;

	public String makeLine2(ResultSetProxy resultSetProxy) throws SQLException
	{
		entity.setTime(resultSetProxy.getString("sakuseihiji"));
		entity.setItemCode(resultSetProxy.getString("zaikey"));
		entity.setItemName1(resultSetProxy.getString("zkname"));
		entity.setItemName2(resultSetProxy.getString("zkname2"));
		entity.setItemName3(resultSetProxy.getString("zkname3"));
		entity.setColor(resultSetProxy.getString("color_code"));
		entity.setTicketNo(resultSetProxy.getString("ticket_no"));
		entity.setBucketNo(resultSetProxy.getString("bucket_no"));
		entity.setLocationNo(resultSetProxy.getString("syozaikey"));
		entity.setRetrievalNo(resultSetProxy.getString("retrieval_no"));
		entity.setProductStartDate(resultSetProxy.getString("start_date"));
		entity.setLine1(resultSetProxy.getString("section"));
		entity.setOrderNo(resultSetProxy.getString("order_no"));
		entity.setMeasureUnitWeight(new BigDecimal(resultSetProxy
				.getString("measure_unit_weight")));
		entity.setStNo(resultSetProxy.getString("nyusyustno"));
		entity.setUserId(resultSetProxy.getString("userid"));
		entity.setUserName(resultSetProxy.getString("username"));
		entity.setIncreaseDecreaseFlag(resultSetProxy.getString("sakukbn"));
		entity.setStartStation(resultSetProxy.getString("startstno"));
		entity.setEndStation(resultSetProxy.getString("endstno"));

		try
		{
			entity.setWorkCount(Integer.parseInt(resultSetProxy
					.getString("nyusyusu")));
		} catch (Exception ex)
		{
			entity.setWorkCount(0);
		}
		if (resultSetProxy.getString("sagyokbn").equals(
				DBFlags.Sagyokbn.CYCLECOUNT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.MAINTENANCE)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.NOT_EQUAL))
		{
			entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(resultSetProxy
					.getString("sagyokbn"))
					+ DBFlags.Sakukbn.parseDBToPage(resultSetProxy
							.getString("sakukbn")));
		} else if (resultSetProxy.getString("sagyokbn").equals(
				DBFlags.Sagyokbn.PICKOUT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.FORBID_IN)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.ALLOW_IN))
		{
			entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(resultSetProxy
					.getString("sagyokbn")));
		} else
		{
			entity.setWorkType(DBFlags.SagyokbnAndNyusyukbn.parseDBToPage(
					resultSetProxy.getString("sagyokbn"), resultSetProxy
							.getString("nyusyukbn")));
			if (resultSetProxy.getString("nyusyukbn").equals(
					DBFlags.Nyusyukbn.STOCKIN))
			{
				entity.setStNo(resultSetProxy.getString("startstno"));
			} else
			{
				entity.setStNo(resultSetProxy.getString("endstno"));
			}
		}

		line = new StringBuffer();
		String c = ",";
		line
				.append(StringUtils.formatDateAndTimeFromDBToPage(entity
						.getTime()));
		line.append(c);
		line.append(entity.getWorkType());
        line.append(c);
		line.append(entity.getItemCode());
		line.append(c);
		line.append(entity.getItemName1());
		line.append(c);
		line.append(entity.getItemName2());
		line.append(c);
		line.append(entity.getItemName3());
		line.append(c);
		line.append(entity.getColor());
		line.append(c);
		line.append(entity.getTicketNo());
		line.append(c);
		line.append(entity.getBucketNo());
		line.append(c);
		line.append(StringUtils.formatLocationNoFromDBToPage(entity
				.getLocationNo()));
		line.append(c);

		String token = "";
		if (entity.getIncreaseDecreaseFlag().equals("1"))
		{
			token = "+";
		} else if (entity.getIncreaseDecreaseFlag().equals("2"))
		{
			token = "-";
		}

		line.append(token);
		line.append(String.valueOf(entity.getWorkCount()));
		line.append(c);
		line.append(entity.getRetrievalNo());
		line.append(c);
        line.append(StringUtils.formatDateFromDBToPage(entity.getProductStartDate()));
        line.append(c);
        line.append(entity.getLine1());
        line.append(c);
		line.append(entity.getOrderNo());
		line.append(c);
		line.append(entity.getMeasureUnitWeight().toString());
		line.append(c);
		line.append(entity.getStNo());
		line.append(c);
		line.append(entity.getUserId());
		line.append(c);
		line.append(entity.getStartStation());
		line.append(c);
		line.append(entity.getEndStation());
		return line.toString();
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		StringBuffer line = new StringBuffer();
		String c = ",";
		line.append(StringUtils.formatDateAndTimeFromDBToPage(resultSetProxy
				.getString("sakuseihiji")));
		line.append(c);

		if (resultSetProxy.getString("sagyokbn").equals(
				DBFlags.Sagyokbn.CYCLECOUNT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.MAINTENANCE)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.NOT_EQUAL))
		{
			line.append(DBFlags.Sagyokbn.parseDBToPage(resultSetProxy
					.getString("sagyokbn"))
					+ DBFlags.Sakukbn.parseDBToPage(resultSetProxy
							.getString("sakukbn")));
		} else if (resultSetProxy.getString("sagyokbn").equals(
				DBFlags.Sagyokbn.PICKOUT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.FORBID_IN)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.ALLOW_IN))
		{
			line.append(DBFlags.Sagyokbn.parseDBToPage(resultSetProxy
					.getString("sagyokbn")));
		} else
		{
			line.append(DBFlags.SagyokbnAndNyusyukbn.parseDBToPage(
					resultSetProxy.getString("sagyokbn"), resultSetProxy
							.getString("nyusyukbn")));
			if (resultSetProxy.getString("nyusyukbn").equals(
					DBFlags.Nyusyukbn.STOCKIN))
			{
				entity.setStNo(resultSetProxy.getString("startstno"));
			} else
			{
				entity.setStNo(resultSetProxy.getString("endstno"));
			}
		}

        line.append(c);
		line.append(resultSetProxy.getString("zaikey"));
		line.append(c);
		line.append(resultSetProxy.getString("zkname"));
		line.append(c);
		line.append(resultSetProxy.getString("zkname2"));
		line.append(c);
		line.append(resultSetProxy.getString("zkname3"));
		line.append(c);
		line.append(resultSetProxy.getString("color_code"));
		line.append(c);
		line.append(resultSetProxy.getString("ticket_no"));
		line.append(c);
		line.append(resultSetProxy.getString("bucket_no"));
		line.append(c);
		line.append(StringUtils.formatLocationNoFromDBToPage(resultSetProxy
				.getString("syozaikey")));
		line.append(c);

		if (resultSetProxy.getString("sakukbn").equals("1"))
		{
			line.append("+");
		} else if (resultSetProxy.getString("sakukbn").equals("2"))
		{
			line.append("-");
		}

		try
		{
			line.append(String.valueOf(Integer.parseInt(resultSetProxy
					.getString("nyusyusu"))));
		} catch (Exception ex)
		{
			line.append("0");
		}

		line.append(c);
		line.append(resultSetProxy.getString("retrieval_no"));
		line.append(c);
        if(!StringUtils.IsNullOrEmpty(resultSetProxy.getString("start_date"))) {
            line.append(StringUtils.formatDateFromDBToPage(resultSetProxy.getString("start_date")));
        }
        line.append(c);
        line.append(resultSetProxy.getString("section"));
        line.append(c);
		line.append(resultSetProxy.getString("order_no"));
		line.append(c);
		line.append(resultSetProxy.getString("measure_unit_weight"));
		line.append(c);

		if (resultSetProxy.getString("sagyokbn").equals(
				DBFlags.Sagyokbn.CYCLECOUNT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.MAINTENANCE)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.NOT_EQUAL)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.PICKOUT)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.FORBID_IN)
				|| resultSetProxy.getString("sagyokbn").equals(
						DBFlags.Sagyokbn.ALLOW_IN))
		{
			line.append(resultSetProxy.getString("nyusyustno"));

		} else if (resultSetProxy.getString("nyusyukbn").equals(
				DBFlags.Nyusyukbn.STOCKIN))
		{
			line.append(resultSetProxy.getString("startstno"));

		} else
		{
			line.append(resultSetProxy.getString("endstno"));
		}

		line.append(c);
		line.append(resultSetProxy.getString("userid"));
		line.append(c);
		line.append(resultSetProxy.getString("startstno"));
		line.append(c);
		line.append(resultSetProxy.getString("endstno"));
		return line.toString();
	}

	public int getMaxLine()
	{
		return 50000;
	}
}
