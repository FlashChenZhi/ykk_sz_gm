package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import java.text.DecimalFormat;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.WorkMaintenanceEntity;

public class WorkMaintenanceListProxy
{
	public WorkMaintenanceListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int NO_COLUMN = 1;

	private int RETRIEVAL_NO_COLUMN = 2;

	private int SECTION_EXTERNAL_CODE_COLUMN = 3;

	private int LINE_PRNO_COLUMN = 4;

	private int WORK_COUNT_COLUMN = 5;

	public int getLINE_PRNO_COLUMN()
	{
		return LINE_PRNO_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getRETRIEVAL_NO_COLUMN()
	{
		return RETRIEVAL_NO_COLUMN;
	}

	public int getSECTION_EXTERNAL_CODE_COLUMN()
	{
		return SECTION_EXTERNAL_CODE_COLUMN;
	}

	public int getWORK_COUNT_COLUMN()
	{
		return WORK_COUNT_COLUMN;
	}
	
	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}
	
	public void setRetrievalNo(String retrievalNo)
	{
		list.setValue(RETRIEVAL_NO_COLUMN, retrievalNo);
	}
	
	public String getRetrievalNo()
	{
		return list.getValue(RETRIEVAL_NO_COLUMN);
	}
	
	public void setSectionExternalCode(String sectionExternalCode)
	{
		list.setValue(SECTION_EXTERNAL_CODE_COLUMN, sectionExternalCode);
	}
	
	public String getSectionExternalCode()
	{
		return list.getValue(SECTION_EXTERNAL_CODE_COLUMN);
	}
	
	public void setLinePrno(String linePrno)
	{
		list.setValue(LINE_PRNO_COLUMN, linePrno);
	}
	
	public String getLinePrno()
	{
		return list.getValue(LINE_PRNO_COLUMN);
	}
	
	public void setWorkCount(int workCount)
	{
		list.setValue(WORK_COUNT_COLUMN, DecimalFormat.getIntegerInstance()
				.format(workCount));
	}
	
	public int getWorkCount()
	{
		String workCount = list.getValue(WORK_COUNT_COLUMN);
		if (workCount != null)
		{
			try
			{
				return Integer.parseInt(list.getValue(WORK_COUNT_COLUMN)
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
	
	public void setRowValueByEntity(WorkMaintenanceEntity entity,int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setRetrievalNo(entity.getRetrievalNo());
		setSectionExternalCode(entity.getSectionExternalCode());
		setLinePrno(entity.getLinePrno());
		setWorkCount(entity.getWorkCount());
	}
}
