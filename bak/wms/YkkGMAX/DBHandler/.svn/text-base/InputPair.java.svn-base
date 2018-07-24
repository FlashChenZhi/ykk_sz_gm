package jp.co.daifuku.wms.YkkGMAX.DBHandler;

import java.sql.Types;

public class InputPair
{
    Object objValue;
    String strValue;
    private int type;
    private int intValue;
    private int index;
    
    public InputPair(int index, String value)
    {
	this.index = index;
	strValue = value;
	objValue = (Object)value;
	type = Types.LONGVARCHAR;
    }
    
    public InputPair(int index, int value)
    {
	this.index = index;
	intValue = value;
	objValue = (Object)new Integer(value);
	type = Types.INTEGER;
    }
    
    public Object getObject()
    {
	return objValue;
    }
    
    public int getIndex()
    {
	return index;
    }
    
    public int getType()
    {
	return type;
    }
    
    public String getString()
    {
	return strValue;
    }

    public int getInteger()
    {
	return intValue;
    }

}
