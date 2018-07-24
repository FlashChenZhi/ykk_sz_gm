package com.worgsoft.ykk.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 16:14:19
 * To change this template use File | Settings | File Templates.
 */
public class ReservationInfo
{
    private long expectedRetrievalQty = 0;
    private long actualRetrievalQty = 0;
    private String retrievalNo;
    private String serialNo;

    public long getExpectedRetrievalQty()
    {
        return expectedRetrievalQty;
    }

    public long getActualRetrievalQty()
    {
        return actualRetrievalQty;
    }

    public void setExpectedRetrievalQty(long expectedRetrievalQty)
    {
        this.expectedRetrievalQty = expectedRetrievalQty;
    }

    public void setRetrievalNo(String retrievalNo)
    {
        this.retrievalNo = retrievalNo;
    }

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public void setActualRetrievalQty(long actualRetrievalQty)
    {
        this.actualRetrievalQty = actualRetrievalQty;
    }

    public String getRetrievalNo()
    {
        return retrievalNo;
    }

    public String getSerialNo()
    {
        return serialNo;
    }
}
