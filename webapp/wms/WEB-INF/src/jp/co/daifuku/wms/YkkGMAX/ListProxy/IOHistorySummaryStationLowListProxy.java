package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationLowEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationMidEntity;

import java.text.DecimalFormat;

/**
 * Created by zhangming on 2015/11/12.
 */
public class IOHistorySummaryStationLowListProxy {
    public IOHistorySummaryStationLowListProxy(ListCell list)
    {
        this.list = list;
    }

    private ListCell list;

    private int UNIT_BOX_COLUMN = 1;

    private int ST1202_COLUMN = 2;

    private int ST1212_COLUMN = 3;

    private int ST1215_COLUMN = 4;

    private int ST1218_COLUMN = 5;

    private int ST2206_COLUMN = 6;

    private int ST2209_COLUMN = 7;

    public int getUNIT_BOX_COLUMN() {
        return UNIT_BOX_COLUMN;
    }

    public int getST1202_COLUMN() {
        return ST1202_COLUMN;
    }

    public int getST1212_COLUMN() {
        return ST1212_COLUMN;
    }

    public int getST1215_COLUMN() {
        return ST1215_COLUMN;
    }

    public int getST1218_COLUMN() {
        return ST1218_COLUMN;
    }

    public int getST2206_COLUMN() {
        return ST2206_COLUMN;
    }

    public int getST2209_COLUMN() {
        return ST2209_COLUMN;
    }

    public String getUnitBox()
    {
        return list.getValue(UNIT_BOX_COLUMN);
    }

    public void setUnitBox(String unitBox)
    {
        list.setValue(UNIT_BOX_COLUMN, unitBox);
    }

    public int getST1202()
    {
        String workCount = list.getValue(ST1202_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1202_COLUMN)
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

    public void setST1202(int count)
    {
        list.setValue(ST1202_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST1212()
    {
        String workCount = list.getValue(ST1212_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1212_COLUMN)
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

    public void setST1212(int count)
    {
        list.setValue(ST1212_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST1215()
    {
        String workCount = list.getValue(ST1215_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1215_COLUMN)
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

    public void setST1215(int count)
    {
        list.setValue(ST1215_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST1218()
    {
        String workCount = list.getValue(ST1218_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1218_COLUMN)
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

    public void setST1218(int count)
    {
        list.setValue(ST1218_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2206()
    {
        String workCount = list.getValue(ST2206_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2206_COLUMN)
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

    public void setST2206(int count)
    {
        list.setValue(ST2206_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2209()
    {
        String workCount = list.getValue(ST2209_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2209_COLUMN)
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

    public void setST2209(int count)
    {
        list.setValue(ST2209_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public void setRowValueByEntity(IOHistorySummaryStationLowEntity entity)
    {
        setUnitBox(entity.getUnitBox());
        setST1202(entity.getSt1202());
        setST1212(entity.getSt1212());
        setST1215(entity.getSt1215());
        setST1218(entity.getSt1218());
        setST2206(entity.getSt2206());
        setST2209(entity.getSt2209());
    }
}
