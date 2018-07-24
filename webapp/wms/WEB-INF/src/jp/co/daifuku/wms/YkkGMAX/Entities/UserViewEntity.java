package jp.co.daifuku.wms.YkkGMAX.Entities;

public class UserViewEntity
{
	private String userID = "";
	
	private String roleName = "";
	
	private String userName = "";
	
	private String password = "";
	
	private String authorization = "";

	public String getAuthorization()
	{
		return authorization;
	}

	public void setAuthorization(String authorization)
	{
		this.authorization = authorization;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
}
