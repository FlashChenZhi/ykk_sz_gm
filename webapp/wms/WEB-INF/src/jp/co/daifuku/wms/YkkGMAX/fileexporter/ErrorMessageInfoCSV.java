package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.ErrorMessageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.ErrorMessageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class ErrorMessageInfoCSV implements IExportable
{
	private final String ERROR_MESSAGE_INFO_HEAD = "ERROR_MESSAGE_INFO_HEAD";
	private Page page;

	public ErrorMessageInfoCSV(Page page)
	{
		this.page = page;
	}

	public ErrorMessageInfoHead getErrorMessageInfoHead()
	{
		return (ErrorMessageInfoHead) page.getSession().getAttribute(
				ERROR_MESSAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "发生时间,受信区分,错误信息";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre
				.getErrorMessageInfoListSQL(getErrorMessageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		ErrorMessageInfoEntity entity = new ErrorMessageInfoEntity();

		entity.setTime(resultSetProxy.getString("register_date")
				+ resultSetProxy.getString("register_time"));
		entity.setMessageType(resultSetProxy.getString("host_table"));
		entity.setErrorMessage(resultSetProxy.getString("err_data"));

		String line = StringUtils.formatDateAndTimeFromDBToPage(entity
				.getTime())
				+ ",";
		line += entity.getMessageType() + ",";
		line += entity.getErrorMessage();
		return line;
	}

	public int getMaxLine()
	{
		return 10000;
	}
}
