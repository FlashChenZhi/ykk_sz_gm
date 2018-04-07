package jp.co.daifuku.wms.YkkGMAX.fileexporter;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.YkkGMAX.Entities.MessageInfoEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.MessageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

public class MessageInfoCSV implements IExportable
{
	private final String MESSAGE_INFO_HEAD = "MESSAGE_INFO_HEAD";
	private Page page;

	public MessageInfoCSV(Page page)
	{
		this.page = page;
	}

	public MessageInfoHead getMessageInfoHead()
	{
		return (MessageInfoHead) page.getSession().getAttribute(
				MESSAGE_INFO_HEAD);
	}

	public String generateHead()
	{
		return "发生时间,受信区分,通信日志";
	}

	public String getNativeSQL()
	{
		return ASRSInfoCentre.getMessageInfoListSQL(getMessageInfoHead());
	}

	public String makeLine(ResultSetProxy resultSetProxy) throws SQLException
	{
		MessageInfoEntity entity = new MessageInfoEntity();

		entity.setTime(resultSetProxy.getString("transfer_datetime"));
		entity.setMessageType(resultSetProxy.getString("host_table"));
		entity.setMessage(resultSetProxy.getString("data"));

		String line = StringUtils.formatDateAndTimeFromDBToPage(entity
				.getTime())
				+ ",";
		line += entity.getMessageType() + ",";
		line += entity.getMessage();
		return line;
	}

	public int getMaxLine()
	{
		return 10000;
	}
}
