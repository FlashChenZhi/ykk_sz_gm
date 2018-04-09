package jp.co.daifuku.wms.YkkGMAX.DBHandler;

import java.util.ArrayList;

public class ResultPairList extends ArrayList
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Object getValueByIndex(int index)
    {
	for(int i=0; i<this.size(); ++i)
	{
	    ResultPair pair = (ResultPair)this.get(i);
	    if(pair.getIndex() == index)
	    {
		return pair.getObject();
	    }
	}
	return null;
    }
}
