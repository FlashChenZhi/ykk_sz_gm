package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.RetrievalOrderPrintEntity;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-18
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public class RetrievalOrderPrintListProxy
{
    private final ListCell head;

    private final ListCell list;

    public RetrievalOrderPrintListProxy(ListCell head, ListCell list)
    {
        this.head = head;
        this.list = list;
    }

    public RetrievalOrderPrintListProxy(ListCell head)
    {
        this.head = head;
        this.list = null;
    }
    
    private final int HEAD_SELECT_COLUMN = 1;

    private final int HEAD_TICKETNO_COLUMN = 2;

    private final int HEAD_ITEMNO_COLUMN = 3;

    private final int HEAD_ITEMNAME_COLUMN = 4;

    private final int HEAD_COLORCODE_COLUMN = 5;

    private final int HEAD_WHENNEXTWORKBEGIN_COLUMN = 6;

    private final int HEAD_STOCKOUTCOUNT_COLUMN = 7;

    private final int HEAD_BOXNO_COLUMN = 8;

    private final int HEAD_STOCKOUTTIME_COLUMN = 9;
    
    private final int HEAD_SECTION_COLUMN = 10;
    
    private final int HEAD_LINE_COLUMN = 11;
    
    private final int HEAD_RETRIEVALNO_COLUMN = 12;
    
    private final int HEAD_MEASUREUNITWEIGHT_COLUMN = 13;

    private final int SELECT_COLUMN = 1;

    private final int TICKETNO_COLUMN = 2;

    private final int ITEMNO_COLUMN = 3;

    private final int ITEMNAME_COLUMN = 4;

    private final int COLORCODE_COLUMN = 5;

    private final int WHENNEXTWORKBEGIN_COLUMN = 6;

    private final int STOCKOUTCOUNT_COLUMN = 7;

    private final int BOXNO_COLUMN = 8;

    private final int STOCKOUTTIME_COLUMN = 9;

    private final int SECTION_COLUMN = 10;

    private final int LINE_COLUMN = 11;

    private final int RETRIEVALNO_COLUMN = 12;

    private final int MEASUREUNITWEIGHT_COLUMN = 13;

    public int getHEAD_MEASUREUNITWEIGHT_COLUMN()
    {
        return HEAD_MEASUREUNITWEIGHT_COLUMN;
    }

    public int getMEASUREUNITWEIGHT_COLUMN()
    {
        return MEASUREUNITWEIGHT_COLUMN;
    }

    public int getHEAD_SECTION_COLUMN()
    {
        return HEAD_SECTION_COLUMN;
    }

    public int getHEAD_LINE_COLUMN()
    {
        return HEAD_LINE_COLUMN;
    }

    public int getHEAD_RETRIEVALNO_COLUMN()
    {
        return HEAD_RETRIEVALNO_COLUMN;
    }

    public int getSECTION_COLUMN()
    {
        return SECTION_COLUMN;
    }

    public int getLINE_COLUMN()
    {
        return LINE_COLUMN;
    }

    public int getRETRIEVALNO_COLUMN()
    {
        return RETRIEVALNO_COLUMN;
    }

    public int getHEAD_SELECT_COLUMN()
    {
        return HEAD_SELECT_COLUMN;
    }

    public int getHEAD_TICKETNO_COLUMN()
    {
        return HEAD_TICKETNO_COLUMN;
    }

    public int getHEAD_ITEMNO_COLUMN()
    {
        return HEAD_ITEMNO_COLUMN;
    }

    public int getHEAD_ITEMNAME_COLUMN()
    {
        return HEAD_ITEMNAME_COLUMN;
    }

    public int getHEAD_COLORCODE_COLUMN()
    {
        return HEAD_COLORCODE_COLUMN;
    }

    public int getHEAD_WHENNEXTWORKBEGIN_COLUMN()
    {
        return HEAD_WHENNEXTWORKBEGIN_COLUMN;
    }

    public int getHEAD_STOCKOUTCOUNT_COLUMN()
    {
        return HEAD_STOCKOUTCOUNT_COLUMN;
    }

    public int getHEAD_BOXNO_COLUMN()
    {
        return HEAD_BOXNO_COLUMN;
    }

    public int getHEAD_STOCKOUTTIME_COLUMN()
    {
        return HEAD_STOCKOUTTIME_COLUMN;
    }

    public int getSELECT_COLUMN()
    {
        return SELECT_COLUMN;
    }

    public int getTICKETNO_COLUMN()
    {
        return TICKETNO_COLUMN;
    }

    public int getITEMNO_COLUMN()
    {
        return ITEMNO_COLUMN;
    }

    public int getITEMNAME_COLUMN()
    {
        return ITEMNAME_COLUMN;
    }

    public int getCOLORCODE_COLUMN()
    {
        return COLORCODE_COLUMN;
    }

    public int getWHENNEXTWORKBEGIN_COLUMN()
    {
        return WHENNEXTWORKBEGIN_COLUMN;
    }

    public int getSTOCKOUTCOUNT_COLUMN()
    {
        return STOCKOUTCOUNT_COLUMN;
    }

    public int getBOXNO_COLUMN()
    {
        return BOXNO_COLUMN;
    }

    public int getSTOCKOUTTIME_COLUMN()
    {
        return STOCKOUTTIME_COLUMN;
    }
    
    public boolean getHeadSelect()
    {
        return head.getChecked(HEAD_SELECT_COLUMN);
    }
    
    public void setHeadSelect(boolean select)
    {
        head.setChecked(HEAD_SELECT_COLUMN,select);
    }
    
    public String getHeadTicketNo()
    {
        return head.getValue(HEAD_TICKETNO_COLUMN);
    }
    
    public void setHeadTicketNo(String ticketNo)
    {
        head.setValue(HEAD_TICKETNO_COLUMN,ticketNo);
    }

    public String getHeadItemNo()
    {
        return head.getValue(HEAD_ITEMNO_COLUMN);
    }

    public void setHeadItemNo(String itemNo)
    {
        head.setValue(HEAD_ITEMNO_COLUMN,itemNo);
    }

    public String getHeadItemName()
    {
        return head.getValue(HEAD_ITEMNAME_COLUMN);
    }

    public void setHeadItemName(String itemName)
    {
        head.setValue(HEAD_ITEMNAME_COLUMN,itemName);
    }

    public String getHeadColorCode()
    {
        return head.getValue(HEAD_COLORCODE_COLUMN);
    }

    public void setHeadColorCode(String colorCode)
    {
        head.setValue(HEAD_COLORCODE_COLUMN,colorCode);
    }

    public String getHeadWhenNextWorkBegin()
    {
        return StringUtils.formatDateFromPageToDB(head.getValue(HEAD_WHENNEXTWORKBEGIN_COLUMN));
    }
    
    public void setHeadWhenNextWorkBegin(String whenNextWorkBegin)
    {
        head.setValue(HEAD_WHENNEXTWORKBEGIN_COLUMN,StringUtils.formatDateFromDBToPage(whenNextWorkBegin));
    }

    public int getHeadStockoutCount()
    {
        String workCount = head.getValue(HEAD_STOCKOUTCOUNT_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(head.getValue(HEAD_STOCKOUTCOUNT_COLUMN)
                        .replaceAll(",", "")
                );
            }
            catch (Exception ex)
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public void setHeadStockoutCount(int stockoutCount)
    {
        head.setValue(HEAD_STOCKOUTCOUNT_COLUMN,DecimalFormat.getIntegerInstance().format(stockoutCount));
    }
    
    public BigDecimal getHeadMeasureUnitWeight()
    {
        String unit = head.getValue(HEAD_MEASUREUNITWEIGHT_COLUMN);
        if(unit != null)
        {
            try
            {
                return new BigDecimal(unit);
            }
            catch (Exception ex)
            {
                return new BigDecimal(0);
            }
        }
        else
        {
            return new BigDecimal(0);
        }
    }

    public void setHeadMeasureUnitWeight(BigDecimal unit)
    {
        head.setValue(HEAD_MEASUREUNITWEIGHT_COLUMN,new DecimalFormat("###,###.#######").format(unit));
    }

    public String getHeadBoxNo()
    {
        return head.getValue(HEAD_BOXNO_COLUMN);
    }
    
    public void setHeadBoxNo(String boxNo)
    {
        head.setValue(HEAD_BOXNO_COLUMN,boxNo);
    }

    public String getHeadStockoutTime()
    {
        return StringUtils.formatDateAndTimeFromPageToDB(head.getValue(HEAD_STOCKOUTTIME_COLUMN));
    }

    public void setHeadStockoutTime(String stockoutTime)
    {
        head.setValue(HEAD_STOCKOUTTIME_COLUMN,StringUtils.formatDateAndTimeFromDBToPage(stockoutTime));
    }

    public String getHeadSection()
    {
        return head.getValue(HEAD_SECTION_COLUMN);
    }

    public void setHeadSection(String section)
    {
        head.setValue(HEAD_SECTION_COLUMN,section);
    }

    public String getHeadLine()
    {
        return head.getValue(HEAD_LINE_COLUMN);
    }

    public void setHeadLine(String line)
    {
        head.setValue(HEAD_LINE_COLUMN,line);
    }

    public String getHeadRetrievalNo()
    {
        return head.getValue(HEAD_RETRIEVALNO_COLUMN);
    }

    public void setHeadRetrievalNo(String retrievalNo)
    {
        head.setValue(HEAD_RETRIEVALNO_COLUMN,retrievalNo);
    }

    public boolean getSelect()
    {
        return list.getChecked(SELECT_COLUMN);
    }

    public void setSelect(boolean select)
    {
        list.setChecked(SELECT_COLUMN,select);
    }

    public String getTicketNo()
    {
        return list.getValue(TICKETNO_COLUMN);
    }

    public void setTicketNo(String ticketNo)
    {
        list.setValue(TICKETNO_COLUMN,ticketNo);
    }

    public String getItemNo()
    {
        return list.getValue(ITEMNO_COLUMN);
    }

    public void setItemNo(String itemNo)
    {
        list.setValue(ITEMNO_COLUMN,itemNo);
    }

    public String getItemName()
    {
        return list.getValue(ITEMNAME_COLUMN);
    }

    public void setItemName(String itemName)
    {
        list.setValue(ITEMNAME_COLUMN,itemName);
    }

    public String getColorCode()
    {
        return list.getValue(COLORCODE_COLUMN);
    }

    public void setColorCode(String colorCode)
    {
        list.setValue(COLORCODE_COLUMN,colorCode);
    }

    public String getWhenNextWorkBegin()
    {
        return StringUtils.formatDateFromPageToDB(list.getValue(WHENNEXTWORKBEGIN_COLUMN));
    }

    public void setWhenNextWorkBegin(String whenNextWorkBegin)
    {
        list.setValue(WHENNEXTWORKBEGIN_COLUMN,StringUtils.formatDateFromDBToPage(whenNextWorkBegin));
    }

    public String getSection()
    {
        return list.getValue(SECTION_COLUMN);
    }

    public void setSection(String section)
    {
        list.setValue(SECTION_COLUMN,section);
    }

    public String getLine()
    {
        return list.getValue(LINE_COLUMN);
    }

    public void setLine(String line)
    {
        list.setValue(LINE_COLUMN,line);
    }

    public String getRetrievalNo()
    {
        return list.getValue(RETRIEVALNO_COLUMN);
    }

    public void setRetrievalNo(String retrievalNo)
    {
        list.setValue(RETRIEVALNO_COLUMN,retrievalNo);
    }

    public int getStockoutCount()
    {
        String workCount = list.getValue(STOCKOUTCOUNT_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(STOCKOUTCOUNT_COLUMN)
                        .replaceAll(",", ""));
            }
            catch (Exception ex)
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public void setStockoutCount(int stockoutCount)
    {
        list.setValue(STOCKOUTCOUNT_COLUMN,DecimalFormat.getIntegerInstance().format(stockoutCount));
    }

    public BigDecimal getMeasureUnitWeight()
    {
        String unit = list.getValue(MEASUREUNITWEIGHT_COLUMN);
        if(unit != null)
        {
            try
            {
                return new BigDecimal(unit);
            }
            catch (Exception ex)
            {
                return new BigDecimal(0);
            }
        }
        else
        {
            return new BigDecimal(0);
        }
    }

    public void setMeasureUnitWeight(BigDecimal unit)
    {
        list.setValue(MEASUREUNITWEIGHT_COLUMN,new DecimalFormat("###,###.#######").format(unit));
    }

    public String getBoxNo()
    {
        return list.getValue(BOXNO_COLUMN);
    }

    public void setBoxNo(String boxNo)
    {
        list.setValue(BOXNO_COLUMN,boxNo);
    }

    public String getStockoutTime()
    {
        return StringUtils.formatDateAndTimeFromPageToDB(list.getValue(STOCKOUTTIME_COLUMN));
    }

    public void setStockoutTime(String stockoutTime)
    {
        list.setValue(STOCKOUTTIME_COLUMN,StringUtils.formatDateAndTimeFromDBToPage(stockoutTime));
    }

    public void setHeadRowValueByEntity(RetrievalOrderPrintEntity entity)
    {
        setHeadSelect(entity.isSelect());
        setHeadTicketNo(entity.getTicketNo());
        setHeadItemNo(entity.getItemNo());
        setHeadItemName(entity.getItemName());
        setHeadColorCode(entity.getColorCode());
        setHeadWhenNextWorkBegin(entity.getWhenNextWorkBegin());
        setHeadStockoutCount(entity.getStockoutCount());
        setHeadBoxNo(entity.getBoxNo());
        setHeadStockoutTime(entity.getStockoutTime());
        setHeadSection(entity.getSection());
        setHeadLine(entity.getLine());
        setHeadRetrievalNo(entity.getRetrievalNo());
        setHeadMeasureUnitWeight(entity.getMeasureUnitWeight());
    }

    public  RetrievalOrderPrintEntity getHeadRetrievalOrderPrintEntity()
    {
        RetrievalOrderPrintEntity entity = new RetrievalOrderPrintEntity();

        entity.setSelect(getHeadSelect());
        entity.setTicketNo(getHeadTicketNo());
        entity.setItemNo(getHeadItemNo());
        entity.setItemName(getHeadItemName());
        entity.setColorCode(getHeadColorCode());
        entity.setWhenNextWorkBegin(getHeadWhenNextWorkBegin());
        entity.setStockoutCount(getHeadStockoutCount());
        entity.setBoxNo(getHeadBoxNo());
        entity.setStockoutTime(getHeadStockoutTime());
        entity.setSection(getHeadSection());
        entity.setLine(getHeadLine());
        entity.setRetrievalNo(getHeadRetrievalNo());
        entity.setMeasureUnitWeight(getHeadMeasureUnitWeight());

        return entity;
    }

    public void setRowValueByEntity(RetrievalOrderPrintEntity entity)
    {
        setSelect(entity.isSelect());
        setTicketNo(entity.getTicketNo());
        setItemNo(entity.getItemNo());
        setItemName(entity.getItemName());
        setColorCode(entity.getColorCode());
        setWhenNextWorkBegin(entity.getWhenNextWorkBegin());
        setStockoutCount(entity.getStockoutCount());
        setBoxNo(entity.getBoxNo());
        setStockoutTime(entity.getStockoutTime());
        setSection(entity.getSection());
        setLine(entity.getLine());
        setRetrievalNo(entity.getRetrievalNo());
        setMeasureUnitWeight(entity.getMeasureUnitWeight());
    }

    public RetrievalOrderPrintEntity getRetrievalOrderPrintEntity()
    {
        RetrievalOrderPrintEntity entity = new RetrievalOrderPrintEntity();

        entity.setSelect(getSelect());
        entity.setTicketNo(getTicketNo());
        entity.setItemNo(getItemNo());
        entity.setItemName(getItemName());
        entity.setColorCode(getColorCode());
        entity.setWhenNextWorkBegin(getWhenNextWorkBegin());
        entity.setStockoutCount(getStockoutCount());
        entity.setBoxNo(getBoxNo());
        entity.setStockoutTime(getStockoutTime());
        entity.setSection(getSection());
        entity.setLine(getLine());
        entity.setRetrievalNo(getRetrievalNo());
        entity.setMeasureUnitWeight(getMeasureUnitWeight());

        return entity;
    }
}
