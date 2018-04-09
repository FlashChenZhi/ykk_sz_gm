package jp.co.daifuku.wms.YkkGMAX.PageController;

import java.util.List;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

public interface IPageable
{
	Pager getPager();

	List getList(int beginningPos, int length) throws YKKDBException,
			YKKSQLException;

	ListCell getListCell();

	void setRowValue(ListCell listCell, int rowNum, Object object);

	int getTotalCount() throws YKKDBException, YKKSQLException;
}
