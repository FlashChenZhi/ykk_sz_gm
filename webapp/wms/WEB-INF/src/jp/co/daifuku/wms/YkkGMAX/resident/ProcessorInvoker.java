<<<<<<< HEAD
package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.base.common.WmsParam;

public class ProcessorInvoker
{

    private final ArrayList processorList = new ArrayList();

    private final Message message;

    public ProcessorInvoker(Message message)
    {
	this.message = message;
    }

    public void addProcessor(BasicProcessor processor)
    {
	this.processorList.add(processor);
    }

    private String generateScheduleNo(Connection conn) throws YKKDBException,
	    YKKSQLException, YKKProcedureException
    {
	ASRSInfoCentre center = new ASRSInfoCentre(conn);
	String schduleNo = center.generateScheduleNo();
	return schduleNo;
    }

    public boolean run()
    {
	Connection conn = null;
	String returnCode = "0";
	String returnMessage = "";

	try
	{
        try {
            conn = ConnectionManager.getConnection();
        }catch (YKKDBException dbEx){
            conn = WmsParam.getConnection();
        }
	    for (int i = 0; i < processorList.size(); i++)
	    {
		BasicProcessor processor = (BasicProcessor) processorList
			.get(i);
		String scheduleNo = generateScheduleNo(conn);
		processor.run(conn, scheduleNo);
	    }
	    conn.commit();
	    message.setMsgResourceKey("7400002");
	    return true;
	}
	catch (YKKDBException dbEx)
	{
	    returnCode = dbEx.getResourceKey();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, MessageResources
		    .getText(returnCode));
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }

	    return false;
	}
	catch (YKKSQLException sqlEx)
	{
	    returnCode = sqlEx.getResourceKey();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, MessageResources
		    .getText(returnCode));
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	catch (YKKProcedureException pEx)
	{
	    returnCode = pEx.getReturnCode();
	    returnMessage = pEx.getReturnMessage();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, returnMessage);
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	catch (Exception e)
	{
	    DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
	    returnCode = "7600001";
	    message.setMsgResourceKey(returnCode);
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	finally
	{
	    if (conn != null)
	    {
		try
		{
		    for (int i = 0; i < processorList.size(); i++)
		    {
			BasicProcessor processor = (BasicProcessor) processorList
				.get(i);
			processor.afterProcedure(conn);
		    }
		    conn.commit();
		    conn.close();
		}
		catch (SQLException ex)
		{
		    DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		    message.setMsgResourceKey("7200002");
		    return false;
		}
		catch (YKKSQLException ex)
		{
		    returnCode = ex.getResourceKey();
		    message.setMsgResourceKey(returnCode);
		    DebugPrinter.print(DebugLevel.ERROR, MessageResources
			    .getText(returnCode));
		    return false;
		}
	    }
	}

    }

}
=======
package jp.co.daifuku.wms.YkkGMAX.resident;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ProcessorInvoker
{

    private final ArrayList processorList = new ArrayList();

    private final Message message;

    public ProcessorInvoker(Message message)
    {
	this.message = message;
    }

    public void addProcessor(BasicProcessor processor)
    {
	this.processorList.add(processor);
    }

    private String generateScheduleNo(Connection conn) throws YKKDBException,
	    YKKSQLException, YKKProcedureException
    {
	ASRSInfoCentre center = new ASRSInfoCentre(conn);
	String schduleNo = center.generateScheduleNo();
	return schduleNo;
    }

    public boolean run()
    {
	Connection conn = null;
	String returnCode = "0";
	String returnMessage = "";

	try
	{
	    conn = ConnectionManager.getConnection();
	    for (int i = 0; i < processorList.size(); i++)
	    {
		BasicProcessor processor = (BasicProcessor) processorList
			.get(i);
		String scheduleNo = generateScheduleNo(conn);
		processor.run(conn, scheduleNo);
	    }
	    conn.commit();
	    message.setMsgResourceKey("7400002");
	    return true;
	}
	catch (YKKDBException dbEx)
	{
	    returnCode = dbEx.getResourceKey();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, MessageResources
		    .getText(returnCode));
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }

	    return false;
	}
	catch (YKKSQLException sqlEx)
	{
	    returnCode = sqlEx.getResourceKey();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, MessageResources
		    .getText(returnCode));
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	catch (YKKProcedureException pEx)
	{
	    returnCode = pEx.getReturnCode();
	    returnMessage = pEx.getReturnMessage();
	    message.setMsgResourceKey(returnCode);
	    DebugPrinter.print(DebugLevel.ERROR, returnMessage);
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	catch (Exception e)
	{
	    DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
	    returnCode = "7600001";
	    message.setMsgResourceKey(returnCode);
	    try
	    {
		if (conn != null)
		{
		    conn.rollback();
		}
	    }
	    catch (SQLException ex)
	    {
		DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		message.setMsgResourceKey("7200002");
		return false;
	    }
	    return false;
	}
	finally
	{
	    if (conn != null)
	    {
		try
		{
		    for (int i = 0; i < processorList.size(); i++)
		    {
			BasicProcessor processor = (BasicProcessor) processorList
				.get(i);
			processor.afterProcedure(conn);
		    }
		    conn.commit();
		    conn.close();
		}
		catch (SQLException ex)
		{
		    DebugPrinter.print(DebugLevel.ERROR, ex.getMessage());
		    message.setMsgResourceKey("7200002");
		    return false;
		}
		catch (YKKSQLException ex)
		{
		    returnCode = ex.getResourceKey();
		    message.setMsgResourceKey(returnCode);
		    DebugPrinter.print(DebugLevel.ERROR, MessageResources
			    .getText(returnCode));
		    return false;
		}
	    }
	}

    }

}
>>>>>>> 86f31489e26519cdd393a5d2cfa4c1d9333ee3b3
