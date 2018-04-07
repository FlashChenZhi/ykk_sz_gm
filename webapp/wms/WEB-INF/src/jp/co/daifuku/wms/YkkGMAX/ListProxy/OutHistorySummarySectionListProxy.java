package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.OutHistorySummarySectionEntity;
import jp.co.daifuku.wms.YkkGMAX.Inquiry.OutHistorySummarySection;

import java.text.DecimalFormat;

/**
 * Created by zhangming on 2015/11/12.
 */
public class OutHistorySummarySectionListProxy {
    public OutHistorySummarySectionListProxy(ListCell list)
    {
        this.list = list;
    }

    private ListCell list;

    private int NO_COLUMN = 1;

    private int SECTION_COLUMN = 2;

    private int BOX_COLUMN = 3;

    public int getNO_COLUMN() {
        return NO_COLUMN;
    }

    public int getSECTION_COLUMN() {
        return SECTION_COLUMN;
    }

    public int getBOX_COLUMN() {
        return BOX_COLUMN;
    }

    public String getNo()
    {
        return list.getValue(NO_COLUMN);
    }

    public void setNo(String no)
    {
        list.setValue(NO_COLUMN, no);
    }

    public String getSection()
    {
        return list.getValue(SECTION_COLUMN);
    }

    public void setSection(String section)
    {
        list.setValue(SECTION_COLUMN, section);
    }

    public int getBox()
    {
        String workCount = list.getValue(BOX_COLUMN);
        if (workCount != null)
        {
            try
            {
                return Integer.parseInt(list.getValue(BOX_COLUMN)
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

    public void setBox(int count)
    {
        list.setValue(BOX_COLUMN, DecimalFormat.getIntegerInstance().format(count));
    }

    public void setRowValueByEntity(OutHistorySummarySectionEntity entity)
    {
        setNo(entity.getNo());
        setSection(entity.getSection());
        setBox(entity.getBox());
    }
}
