package jp.co.daifuku.wms.YkkGMAX.Exceptions;

public class BaseYKKException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String resourceKey;

    public void setResourceKey(String resourceKey)
	{
	   this.resourceKey = resourceKey;
	    
	}
    
    public String getResourceKey()
    {
	return resourceKey;
    }
}
