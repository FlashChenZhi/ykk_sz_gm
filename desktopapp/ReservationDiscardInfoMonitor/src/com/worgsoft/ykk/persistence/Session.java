package com.worgsoft.ykk.persistence;

import com.worgsoft.ykk.monitor.DiscardInfo;
import com.worgsoft.ykk.monitor.ReservationInfo;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 15:47:34
 * To change this template use File | Settings | File Templates.
 */
public class Session
{
    private Connection conn;

    public Session(Connection conn)
    {
        this.conn = conn;
    }

    public List getDiscardInfoList() throws Exception
    {
        String queryString = "select retrieval_no, serial_no, cancel_qty - cancel_alloc_qty as expected_discard_qty  from fnsyotei_cancel where proc_flag = '0' order by create_datetime for update";
        SqlExecutor executor = new SqlExecutor(conn);
        executor.executeQuery(queryString);

        List recordSetRowList = executor.getRecordSet().getRowList();

        if (recordSetRowList.size() == 0)
        {
            return null;
        }

        List discardInfoList = new ArrayList(recordSetRowList.size());
        Iterator it = recordSetRowList.iterator();
        while (it.hasNext())
        {
            RecordSetRow row = (RecordSetRow) it.next();

            DiscardInfo discardInfo = new DiscardInfo();

            discardInfo.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            discardInfo.setSerialNo(row.getValueByColumnName("serial_no"));
            discardInfo.setExpectedDiscardQty(Long.parseLong(row.getValueByColumnName("expected_discard_qty")));

            discardInfoList.add(discardInfo);
        }

        return discardInfoList;
    }

    public ReservationInfo getReservationInfo(String retrievalNo, String serialNo) throws Exception
    {
        String queryString = "select retrieval_qty, retrieval_alloc_qty from fnsyotei where retrieval_no = '@RetrievalNo' and serial_no = '@SerialNo' for update";

        queryString = StringUtils.replace(queryString, "@RetrievalNo", retrievalNo);
        queryString = StringUtils.replace(queryString, "@SerialNo", serialNo);

        SqlExecutor executor = new SqlExecutor(conn);
        executor.executeQuery(queryString);

        List recordSetRowList = executor.getRecordSet().getRowList();

        if (recordSetRowList.size() == 0)
        {
            return null;
        }

        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setRetrievalNo(retrievalNo);
        reservationInfo.setSerialNo(serialNo);

        Iterator it = recordSetRowList.iterator();
        while (it.hasNext())
        {
            RecordSetRow row = (RecordSetRow) it.next();

            reservationInfo.setExpectedRetrievalQty(Long.parseLong(row.getValueByColumnName("retrieval_qty")));
            reservationInfo.setActualRetrievalQty(Long.parseLong(row.getValueByColumnName("retrieval_alloc_qty")));
        }

        return reservationInfo;
    }

    public void commit() throws Exception
    {
        conn.commit();
    }

    public void rollback() throws Exception
    {
        conn.rollback();
    }

    public void close() throws Exception
    {
        conn.close();
    }

    public void saveDiscardInfo(DiscardInfo discardInfo) throws Exception
    {

        String updateString = "update fnsyotei_cancel set cancel_alloc_qty = cancel_alloc_qty + @ActualDiscardQty, proc_flag = '2' where retrieval_no = '@RetrievalNo' and serial_no = '@SerialNo'";
        updateString = StringUtils.replace(updateString, "@ActualDiscardQty", String.valueOf(discardInfo.getActualDiscardQty()));
        updateString = StringUtils.replace(updateString, "@RetrievalNo", discardInfo.getRetrievalNo());
        updateString = StringUtils.replace(updateString, "@SerialNo", discardInfo.getSerialNo());

        SqlExecutor executor = new SqlExecutor(conn);
        executor.executeUpdate(updateString);
    }

    public void saveReservationInfo(ReservationInfo reservationInfo) throws Exception
    {
        String updateString = "update fnsyotei set retrieval_qty = @ExpectedRetrievalQty, proc_flag = '@ProcessedFlag' where retrieval_no = '@RetrievalNo' and serial_no = '@SerialNo'";
        updateString = StringUtils.replace(updateString, "@ExpectedRetrievalQty", String.valueOf(reservationInfo.getExpectedRetrievalQty()));
        updateString = StringUtils.replace(updateString, "@ProcessedFlag", reservationInfo.getExpectedRetrievalQty() == reservationInfo.getActualRetrievalQty() ? "1" : "0");
        updateString = StringUtils.replace(updateString, "@RetrievalNo", reservationInfo.getRetrievalNo());
        updateString = StringUtils.replace(updateString, "@SerialNo", reservationInfo.getSerialNo());

        SqlExecutor executor = new SqlExecutor(conn);
        executor.executeUpdate(updateString);
    }
}
