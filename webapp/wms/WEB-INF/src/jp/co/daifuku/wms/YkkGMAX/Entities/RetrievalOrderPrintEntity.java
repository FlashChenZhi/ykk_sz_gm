package jp.co.daifuku.wms.YkkGMAX.Entities;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-18
 * Time: 下午1:05
 * To change this template use File | Settings | File Templates.
 */
public class RetrievalOrderPrintEntity
{
    private boolean select;
    
    private String itemNo;
    
    private String itemName;
    
    private String ticketNo;

    private String colorCode;

    private String whenNextWorkBegin;

    private int stockoutCount;

    private String boxNo;
    
    private String stockoutTime;
    
    private BigDecimal measureUnitWeight;
    
    private String section;
    
    private String line;

    private String retrievalNo;

    public String getRetrievalNo()
    {
        return retrievalNo;
    }

    public void setRetrievalNo(String _retrievalNo)
    {
        retrievalNo = _retrievalNo;
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

    public BigDecimal getMeasureUnitWeight()
    {
        return measureUnitWeight;
    }

    public void setMeasureUnitWeight(BigDecimal _measureUnitWeight)
    {
        measureUnitWeight = _measureUnitWeight;
    }

    public boolean isSelect()
    {
        return select;
    }

    public void setSelect(boolean _select)
    {
        select = _select;
    }

    public String getItemNo()
    {
        return itemNo;
    }

    public void setItemNo(String _itemNo)
    {
        itemNo = _itemNo;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String _itemName)
    {
        itemName = _itemName;
    }

    public String getTicketNo()
    {
        return ticketNo;
    }

    public void setTicketNo(String _ticketNo)
    {
        ticketNo = _ticketNo;
    }

    public String getColorCode()
    {
        return colorCode;
    }

    public void setColorCode(String _colorCode)
    {
        colorCode = _colorCode;
    }

    public String getWhenNextWorkBegin()
    {
        return whenNextWorkBegin;
    }

    public void setWhenNextWorkBegin(String _whenNextWorkBegin)
    {
        whenNextWorkBegin = _whenNextWorkBegin;
    }

    public int getStockoutCount()
    {
        return stockoutCount;
    }

    public void setStockoutCount(int _stockoutCount)
    {
        stockoutCount = _stockoutCount;
    }

    public String getBoxNo()
    {
        return boxNo;
    }

    public void setBoxNo(String _boxNo)
    {
        boxNo = _boxNo;
    }

    public String getStockoutTime()
    {
        return stockoutTime;
    }

    public void setStockoutTime(String _stockoutTime)
    {
        stockoutTime = _stockoutTime;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetrievalOrderPrintEntity entity = (RetrievalOrderPrintEntity) o;

        if (stockoutCount != entity.stockoutCount) return false;
        if (boxNo != null ? !boxNo.equals(entity.boxNo) : entity.boxNo != null) return false;
        if (colorCode != null ? !colorCode.equals(entity.colorCode) : entity.colorCode != null) return false;
        if (itemName != null ? !itemName.equals(entity.itemName) : entity.itemName != null) return false;
        if (itemNo != null ? !itemNo.equals(entity.itemNo) : entity.itemNo != null) return false;
        if (line != null ? !line.equals(entity.line) : entity.line != null) return false;
        if (measureUnitWeight != null ? !measureUnitWeight.equals(entity.measureUnitWeight) : entity.measureUnitWeight != null)
            return false;
        if (retrievalNo != null ? !retrievalNo.equals(entity.retrievalNo) : entity.retrievalNo != null) return false;
        if (section != null ? !section.equals(entity.section) : entity.section != null) return false;
        if (stockoutTime != null ? !stockoutTime.equals(entity.stockoutTime) : entity.stockoutTime != null)
            return false;
        if (ticketNo != null ? !ticketNo.equals(entity.ticketNo) : entity.ticketNo != null) return false;
        if (whenNextWorkBegin != null ? !whenNextWorkBegin.equals(entity.whenNextWorkBegin) : entity.whenNextWorkBegin != null)
            return false;

        return true;
    }

    public int hashCode()
    {
        int result = itemNo != null ? itemNo.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (ticketNo != null ? ticketNo.hashCode() : 0);
        result = 31 * result + (colorCode != null ? colorCode.hashCode() : 0);
        result = 31 * result + (whenNextWorkBegin != null ? whenNextWorkBegin.hashCode() : 0);
        result = 31 * result + stockoutCount;
        result = 31 * result + (boxNo != null ? boxNo.hashCode() : 0);
        result = 31 * result + (stockoutTime != null ? stockoutTime.hashCode() : 0);
        result = 31 * result + (measureUnitWeight != null ? measureUnitWeight.hashCode() : 0);
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + (line != null ? line.hashCode() : 0);
        result = 31 * result + (retrievalNo != null ? retrievalNo.hashCode() : 0);
        return result;
    }
}
