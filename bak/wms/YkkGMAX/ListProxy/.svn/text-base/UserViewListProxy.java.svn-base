package jp.co.daifuku.wms.YkkGMAX.ListProxy;

import jp.co.daifuku.bluedog.ui.control.ListCell;
import jp.co.daifuku.wms.YkkGMAX.Entities.UserViewEntity;

public class UserViewListProxy
{
	public UserViewListProxy(ListCell list)
	{
		this.list = list;
	}

	private ListCell list;

	private int NO_COLUMN = 1;
	
	private int ROLE_NAME_COLUMN = 2;

	private int USER_ID_COLUMN = 3;
	
	private int USER_NAME_COLUMN = 4;

	public int getROLE_NAME_COLUMN()
	{
		return ROLE_NAME_COLUMN;
	}

	public int getNO_COLUMN()
	{
		return NO_COLUMN;
	}

	public int getUSER_ID_COLUMN()
	{
		return USER_ID_COLUMN;
	}

	public int getUSER_NAME_COLUMN()
	{
		return USER_NAME_COLUMN;
	}
	
	public String getNo()
	{
		return list.getValue(NO_COLUMN);
	}

	public void setNo(String no)
	{
		list.setValue(NO_COLUMN, no);
	}
	
	public String getRoleName()
	{
		return list.getValue(ROLE_NAME_COLUMN);
	}

	public void setRoleName(String roleName)
	{
		list.setValue(ROLE_NAME_COLUMN, roleName);
	}
	
	public String getUserID()
	{
		return list.getValue(USER_ID_COLUMN);
	}

	public void setUserID(String userID)
	{
		list.setValue(USER_ID_COLUMN, userID);
	}
	
	public String getUserName()
	{
		return list.getValue(USER_NAME_COLUMN);
	}

	public void setUserName(String userName)
	{
		list.setValue(USER_NAME_COLUMN, userName);
	}
	public void setRowValueByEntity(UserViewEntity entity, int rowNum)
	{
		setNo(String.valueOf(rowNum));
		setRoleName(entity.getRoleName());
		setUserID(entity.getUserID());
		setUserName(entity.getUserName());
	}
}
