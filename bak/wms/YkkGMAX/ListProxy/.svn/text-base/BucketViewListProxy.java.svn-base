package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.BucketViewEntity;

public class BucketViewListProxy
{
	public BucketViewListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int NO_COLUMN = 1;

	private int BUCKET_NO_COLUMN = 2;

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getBUCKET_NO_COLUMN()
	{
		return BUCKET_NO_COLUMN;
	}

	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}

	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}

	public String getBucketNo()
	{
		return list.getValue(BUCKET_NO_COLUMN);
	}

	public void setBucketNo(String bucketNo)
	{
		list.setValue(BUCKET_NO_COLUMN, bucketNo);
	}

	public void setRowValueByEntity(BucketViewEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setBucketNo(entity.getBucketNo());
	}
}
