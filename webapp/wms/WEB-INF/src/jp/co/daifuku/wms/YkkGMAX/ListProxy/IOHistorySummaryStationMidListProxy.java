package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationMidEntity;
import jp.co.daifuku.wms.YkkGMAX.Entities.IOHistorySummaryStationUpEntity;

import java.text.DecimalFormat;

/**
 * Created by zhangming on 2015/11/12.
 */
public class IOHistorySummaryStationMidListProxy {
    public IOHistorySummaryStationMidListProxy(ListCell list)
    {
        this.list = list;
    }

    private ListCell list;

    private int UNIT_BOX_COLUMN = 1;

    private int ST1205_COLUMN = 2;

    private int ST2204_COLUMN = 3;

    private int ST2216_COLUMN = 4;

    private int ST2217_COLUMN = 5;

    private int ST2214_COLUMN = 6;

    public int getUNIT_BOX_COLUMN() {
        return UNIT_BOX_COLUMN;
    }

    public int getST1205_COLUMN() {
        return ST1205_COLUMN;
    }

    public int getST2204_COLUMN() {
        return ST2204_COLUMN;
    }

    public int getST2216_COLUMN() {
        return ST2216_COLUMN;
    }

    public int getST2217_COLUMN() {
        return ST2217_COLUMN;
    }

    public int getST2214_COLUMN() {
        return ST2214_COLUMN;
    }

    public String getUnitBox()
    {
        return list.getValue(UNIT_BOX_COLUMN);
    }

    public void setUnitBox(String unitBox)
    {
        list.setValue(UNIT_BOX_COLUMN, unitBox);
    }

    public int getST1205()
    {
        String workCount = list.getValue(ST1205_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST1205_COLUMN)
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

    public void setST1205(int count)
    {
        list.setValue(ST1205_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2204()
    {
        String workCount = list.getValue(ST2204_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2204_COLUMN)
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

    public void setST2204(int count)
    {
        list.setValue(ST2204_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2216()
    {
        String workCount = list.getValue(ST2216_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2216_COLUMN)
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

    public void setST2216(int count)
    {
        list.setValue(ST2216_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2217()
    {
        String workCount = list.getValue(ST2217_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2217_COLUMN)
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

    public void setST2217(int count)
    {
        list.setValue(ST2217_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public int getST2214()
    {
        String workCount = list.getValue(ST2214_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(ST2214_COLUMN)
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

    public void setST2214(int count)
    {
        list.setValue(ST2214_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public void setRowValueByEntity(IOHistorySummaryStationMidEntity entity)
    {
        setUnitBox(entity.getUnitBox());
        setST1205(entity.getSt1205());
        setST2204(entity.getSt2204());
        setST2216(entity.getSt2216());
        setST2217(entity.getSt2217());
        setST2214(entity.getSt2214());
    }
}
