package jp.co.daifuku.wms.YkkGMAX.Entities;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-18
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class RetrievalOrderPrintHead
{
    private String timeFrom = "";

    private String timeTo = "";

    private String item = "";
    
    private String section = "";
    
    private String line = "";

    private String orderBy = "";
    
    private String nextWorkBeginDate = "";
    
    private String colorCode;

    public String getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(String _colorCode)
    {
        colorCode = _colorCode;
    }

    public String getNextWorkBeginDate()
    {
        return nextWorkBeginDate;
    }

    public void setNextWorkBeginDate(String _nextWorkBeginDate)
    {
        nextWorkBeginDate = _nextWorkBeginDate;
    }

    public String getTimeFrom()
    {
        return timeFrom;
    }

    public void setTimeFrom(String _timeFrom)
    {
        timeFrom = _timeFrom;
    }

    public String getTimeTo()
    {
        return timeTo;
    }

    public void setTimeTo(String _timeTo)
    {
        timeTo = _timeTo;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String _item)
    {
        item = _item;
    }

    public String getSection()
    {
        return section;
    }

    public void setSection(String _section)
    {
        section = _section;
    }

    public String getLine()
    {
        return line;
    }

    public void setLine(String _line)
    {
        line = _line;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String _orderBy)
    {
        orderBy = _orderBy;
    }
}
