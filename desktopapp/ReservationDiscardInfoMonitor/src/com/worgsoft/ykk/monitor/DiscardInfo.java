package com.worgsoft.ykk.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 16:12:13
 * To change this template use File | Settings | File Templates.
 */
public class DiscardInfo
{
    private String retrievalNo;
    private String serialNo;
    private long expectedDiscardQty = 0;
    private long actualDiscardQty = 0;
    public boolean isProcessed = false;

    public String getRetrievalNo()
    {
        return retrievalNo;
    }

    public String getSerialNo()
    {
        return serialNo;
    }

    public long getExpectedDiscardQty()
    {
        return expectedDiscardQty;
    }

    public void setActualDiscardQty(long actualDiscardQty)
    {
        this.actualDiscardQty = actualDiscardQty;
    }

    public void setRetrievalNo(String retrievalNo)
    {
        this.retrievalNo = retrievalNo;
    }

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public void setExpectedDiscardQty(long expectedDiscardQty)
    {
        this.expectedDiscardQty = expectedDiscardQty;
    }

    public long getActualDiscardQty()
    {
        return actualDiscardQty;
    }
}
