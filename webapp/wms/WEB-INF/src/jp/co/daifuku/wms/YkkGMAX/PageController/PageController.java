package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public class PageController
{
	private IPageable p = null;

	private IPageable pl = null;

	private Message message = null;

	private boolean shallCheckCount = true;

	private int maxCount = 8000;

	public PageController(IPageable pu, IPageable pl, Message message)
	{
		this.p = pu;
		this.pl = pl;
		this.message = message;
	}

	public PageController(IPageable pu, IPageable pl, Message message,
			int maxCount)
	{
		this.p = pu;
		this.pl = pl;
		this.message = message;
		this.maxCount = maxCount;
	}

	public PageController(IPageable pu, IPageable pl, Message message,
			boolean shallCheckCount)
	{
		this.p = pu;
		this.pl = pl;
		this.message = message;
		this.shallCheckCount = shallCheckCount;
	}

	public PageController(IPageable p, Message message)
	{
		this.p = p;
		this.message = message;
	}

	public PageController(IPageable p, Message message, boolean shallCheckCount)
	{
		this.p = p;
		this.message = message;
		this.shallCheckCount = shallCheckCount;
	}

	public void clear()
	{
		p.getPager().setIndex(0);
		p.getPager().setMax(0);
		p.getPager().setPage(0);
		UpdatePagerLowByPagerUp();
	}

	public void init() throws YKKSQLException, YKKDBException
	{
		p.getPager().setIndex(0);
		p.getPager().setMax(p.getTotalCount());
		p.getPager().setPage(30);
	}

	public void setCountPerPage(int countPerPage)
	{
		p.getPager().setPage(countPerPage);
	}

	private void setList(int rowNum, int length) throws YKKDBException,
			YKKSQLException
	{
		p.getPager().setIndex(rowNum);

		ListCell listCell = p.getListCell();

		List list = p.getList(rowNum, length);
		Iterator it = list.iterator();
		while (it.hasNext())
		{
			listCell.addRow();
			listCell.setCurrentRow(listCell.getMaxRows() - 1);
			p.setRowValue(listCell, rowNum, it.next());
			rowNum++;
		}
	}

	public void setTotalCount(int totalCount)
	{
		p.getPager().setMax(totalCount);
	}

	private void turnPage(String actionName) throws YKKDBException,
			YKKSQLException
	{
		ListCell listCell = p.getListCell();
		listCell.clearRow();
		int currentIndex = p.getPager().getIndex();
		// int totalCount = p.getPager().getMax();
		int countPerPage = p.getPager().getPage();
		p.getPager().setIndex(0);
		p.getPager().setMax(0);
		UpdatePagerLowByPagerUp();
		int totalCount = p.getTotalCount();

		if (shallCheckCount && totalCount > maxCount)
		{
			message.setMsgResourceKey("7000031" + "\t" + totalCount + "\t"
					+ maxCount);
			// return;
		}
		totalCount = totalCount > maxCount ? maxCount : totalCount;
		p.getPager().setMax(totalCount);
		int rowNum = 0;

		if (actionName.equalsIgnoreCase("first"))
		{
			if (totalCount < 1)
				return;
			rowNum = 1;
		}

		if (actionName.equalsIgnoreCase("previous"))
		{
			rowNum = currentIndex - countPerPage;
			if (rowNum < 0)
				return;
		}

		if (actionName.equalsIgnoreCase("next"))
		{
			rowNum = currentIndex + countPerPage;
			if (rowNum - 1 >= totalCount)
				return;
		}

		if (actionName.equalsIgnoreCase("last"))
		{
			rowNum = totalCount % countPerPage == 0 ? totalCount - countPerPage
					+ 1 : (totalCount / countPerPage) * countPerPage + 1;
		}

		int length = countPerPage;
		if (maxCount - rowNum < countPerPage)
		{
			length = maxCount % countPerPage;
		}

		setList(rowNum, length);
	}

	public void turnToFirstPage() throws YKKDBException, YKKSQLException
	{
		turnPage("first");
		UpdatePagerLowByPagerUp();
	}

	public void turnToLastPage() throws YKKDBException, YKKSQLException
	{
		turnPage("last");
		UpdatePagerLowByPagerUp();
	}

	public void turnToNextPage() throws YKKDBException, YKKSQLException
	{
		turnPage("next");
		UpdatePagerLowByPagerUp();
	}

	public void turnToPreviousPage() throws YKKDBException, YKKSQLException
	{
		turnPage("previous");
		UpdatePagerLowByPagerUp();
	}

	private void UpdatePagerLowByPagerUp()
	{
		if (pl != null)
		{
			pl.getPager().setIndex(p.getPager().getIndex());
			pl.getPager().setMax(p.getPager().getMax());
			pl.getPager().setPage(p.getPager().getPage());
		}
	}
}
