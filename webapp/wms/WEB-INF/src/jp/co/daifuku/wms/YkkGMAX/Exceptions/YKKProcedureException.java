package jp.co.daifuku.wms.YkkGMAX.Exceptions;

public class YKKProcedureException extends BaseYKKException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String returnCode;
    private String returnMessage;

    public void setReturnCode(String returnCode)
    {
	this.returnCode = returnCode;
	
    }
    
    public String getReturnCode()
    {
	return returnCode;
    }

    public void setReturnMessage(String returnMessage)
    {
	this.returnMessage = returnMessage;
	
    }
    
    public String getReturnMessage()
    {
	return returnMessage;
    }

}
