package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationUpEntity;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.IOHistorySummaryStation;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * Created by zhangming on 2015/11/12.
 */
public class IOHistorySummaryStationUpListProxy {
    public IOHistorySummaryStationUpListProxy(ListCell list)
    {
        this.list = list;
    }

    private ListCell list;

    private int UNIT_BOX_COLUMN = 1;

    private int ST1101_COLUMN = 2;

    private int ST1102_COLUMN = 3;

    private int ST2101_COLUMN = 4;

    private int ST2105_COLUMN = 5;

    public int getUNIT_BOX_COLUMN() {
        return UNIT_BOX_COLUMN;
    }

    public int getST1101_COLUMN() {
        return ST1101_COLUMN;
    }

    public int getST1102_COLUMN() {
        return ST1102_COLUMN;
    }

    public int getST2101_COLUMN() {
        return ST2101_COLUMN;
    }

    public int getST2105_COLUMN() {
        return ST2105_COLUMN;
    }

    public String getUnitBox()
    {
        return list.getValue(UNIT_BOX_COLUMN);
    }

    public void setUnitBox(String unitBox)
    {
        list.setValue(UNIT_BOX_COLUMN, unitBox);
    }

    public int getST1101()
    {
        String workCount = list.getValue(ST1101_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1101_COLUMN)
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

    public void setST1101(int count)
    {
        list.setValue(ST1101_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST1102()
    {
        String workCount = list.getValue(ST1102_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1102_COLUMN)
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

    public void setST1102(int count)
    {
        list.setValue(ST1102_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2101()
    {
        String workCount = list.getValue(ST2101_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2101_COLUMN)
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

    public void setST2101(int count)
    {
        list.setValue(ST2101_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2105()
    {
        String workCount = list.getValue(ST2105_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2105_COLUMN)
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

    public void setST2105(int count)
    {
        list.setValue(ST2105_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public void setRowValueByEntity(IOHistorySummaryStationUpEntity entity)
    {
        setUnitBox(entity.getUnitBox());
        setST1101(entity.getSt1101());
        setST1102(entity.getSt1102());
        setST2101(entity.getSt2101());
        setST2105(entity.getSt2105());
    }
}
