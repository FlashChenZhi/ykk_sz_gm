package jp.co.daifuku.tools.display.web ;
import jp.co.daifuku.bluedog.webapp.ActionEvent;


public class ProgressBusiness extends Progress
{
	
	public void page_Initialize(ActionEvent e) throws Exception
	{
		//When the page_Initialize method at the parent screen set forcus, it should remove this reference.  
		httpRequest.setAttribute(jp.co.daifuku.bluedog.webapp.Constants.KEY_FOCUS_TAG_SUPPORT, null);
	}

	
	public void page_Load(ActionEvent e) throws Exception
	{

	}

	public void msg_Server(ActionEvent e) throws Exception
	{
	}

	/** <jp>
	 * 
	 </jp>*/
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
	}

}
