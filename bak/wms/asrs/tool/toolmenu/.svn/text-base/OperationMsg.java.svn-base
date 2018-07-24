// $Id: OperationMsg.java,v 1.2 2006/10/30 04:05:23 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu ;

//#CM53936
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.StringTokenizer;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;

//#CM53937
/**<en>
 * This class preserves the operation message.
 * This is used to notifiy the users with message when storage/retrieval was set up via screen.
 * Each display should set the OperationMsg isntance with contents and font color of the message 
 * and preserve the instance by the name 'opmsg" in the session.
 * It is possible to display the message on MessageCtrl.jsp by using InputDataChecker to check the
 * input data of set items and by preserving the InputDataChecker instance by the name "opmsg".
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>OperationMsg(String msg, Locale locale)constructor added</TD></TR>
 * <TR><TD>2002/06/03</TD><TD>kawashima</TD><TD>OperationMsg(String msg, Locale locale, String default_Msg)constructor added</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $
 * @author  $Author: suresh $
 </en>*/
public class OperationMsg
{
	//#CM53938
	// Class fields --------------------------------------------------
	//#CM53939
	/**<en>
	 * Message color code (information) Blue
	 </en>*/
	public static final int INFO_MSG = 0;
	//#CM53940
	/**<en>
	 * Message color cod (caution) Orange
	 </en>*/
	public static final int ATNT_MSG = 1;
	//#CM53941
	/**<en>
	 * Message color cod (warning)  Green
	 </en>*/
	public static final int WARN_MSG = 2;
	//#CM53942
	/**<en>
	 * Message color cod (error) Red
	 </en>*/
	public static final int ERR_MSG  = 3;

	//#CM53943
	// Class variables -----------------------------------------------
	//#CM53944
	/**<en>
	 * Preserve the operation message.
	 </en>*/
	protected String 	operationmsg 	= "";

	//#CM53945
	/**<en>
	 * Preserve the color of the message.
	 </en>*/
	protected int		msgcolor 		= 0;

	//#CM53946
	/**<en>
	 * If displaying the confirm(MessageBox) of JavaScript in order to request the
	 * confirmation for users, please set this flag 'true'.
	 </en>*/
	protected boolean	showmsgbox 		= false;

	//#CM53947
	// Class method --------------------------------------------------
	//#CM53948
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:05:23 $") ;
	}

	//#CM53949
	// Constructors --------------------------------------------------
	//#CM53950
	/**<en>
	 * Default constructor
	 </en>*/
	public OperationMsg()
	{
    }

	//#CM53951
	/**<en>
	 * Please create the instance by setting the messag and message color as parameters.
	 * @param msg         :message 
	 * @param msgcolorNo  :message color
	 </en>*/
	public OperationMsg(String msg, int msgcolorNo)
	{
    	this(msg, msgcolorNo, false);
    }

	//#CM53952
	/**<en>
	 * Please create the instance by setting the messag and message color as parameters.
	 * @param msg         :message 
	 * @param msgcolorNo  :message color
	 * @param flag  :if requesting users to confirm by displaying confirm(MessageBox) of JavaScript,
	 * please set the Flag 'true'.
	 </en>*/
	public OperationMsg(String msg, int msgcolorNo, boolean flag)
	{
    	this.operationmsg   = msg;
		this.msgcolor 		= msgcolorNo;
		this.showmsgbox 	= flag;
    }

	//#CM53953
	/**<en>
	 * Set value of contents (operationmsg) and the color(msgcolor) of the message by
	 * specifing the message no. 
	 * @param msg  :the string which was made by concatenating the message<I>, message no.<I> and </I> parameter</I>,
	 * </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
	 * @param locale :specify the locale.
	 * @see jp.co.daifuku.common.LogMessage
	 * @see jp.co.daifuku.common.MessageResource
	 </en>*/
	public OperationMsg(String msg, Locale locale)
	{
		setMessageInfo(msg, locale);
    }

	//#CM53954
	/**<en>
	 * Set the valud of message contents (operationmsg) and the color (msgcolor), by 
	 * specifing the message no.<BR>
	 * If there are possibility  that the exceptions of Java may be caught, plese use this constructor.<BR>
	 * In that case, the message no. (+ parameter) specified by default_Msg should be set to
	 * the contents of the message.
	 * @param msg  :the string which was made by concatenating the message<I>, message no.<I> and </I> parameter</I>,
	 * </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
	 * @param locale :specify the locale.
	 * @param default_Msg :The string made by connecting the <I>message no.<I> which will be displayed 
	 * when Java exception is caught and the <I>parameter<I>, using delimiter of <CODE>MessageResource</CODE>.
	 * @see jp.co.daifuku.common.LogMessage
	 * @see jp.co.daifuku.common.MessageResource
	 </en>*/
	public OperationMsg(String msg, Locale locale, String default_Msg)
	{
		MessageResource mr = new MessageResource( locale );

		//#CM53955
		//<en> In case of Java exception,</en>
		if(!mr.isMessage(msg) )
		{
			msg = default_Msg;
		}
		setMessageInfo(msg, locale);
    }

	//#CM53956
	// Public methods ------------------------------------------------
	//#CM53957
	/**<en>
	 *	Get the message to be displayed.
	 *  @return messeage
	 </en>*/
	public String getValue()
	{
		return operationmsg;
  	}

	//#CM53958
	/**<en>
	 *	Get the color of the message to display.
	 *  @return messeage color
	 </en>*/
	public String getMsgColor()
	{
		//#CM53959
		/*<en> information </en>*/

		if (msgcolor == INFO_MSG) {
			return "INFORMATION";
		//#CM53960
		/*<en> caution </en>*/

		} else if (msgcolor == ATNT_MSG) {
			return "ATTENTION";
		//#CM53961
		/*<en> worning </en>*/

		} else if (msgcolor == WARN_MSG) {
			return "WARNING";
		//#CM53962
		/*<en> error (trouble) </en>*/

		} else if (msgcolor == ERR_MSG) {
			return "ERROR";
		//#CM53963
		/* Program Error */

		} else {
			return "PURPLE";
		}
  	}

	//#CM53964
	/**<en>
	 *	Flag to be used when requesting user to confirm by displaying JavaScript confirm(MessageBox).
	 *  @return :return true if requesting user to confirm by displaying JavaScript confirm(MessageBox).
	 </en>*/
	public boolean getShowMsgbox()
	{
		return showmsgbox;
  	}

	//#CM53965
	/**<en>
	 * Set the message to display.
	 * @param msg         :message
	 * @param msgcolorNo  :message color
	 </en>*/
	public void setValue(String msg, int msgcolorNo)
	{
    	//#CM53966
    	/*<en> Set blank if null. </en>*/

    	if (operationmsg == null) {
			operationmsg = "";
		}

		//#CM53967
		/*<en> Set the message. </en>*/

		operationmsg    = msg;
		//#CM53968
		/*<en> Set the MSG COLOR. </en>*/

		msgcolor 		= msgcolorNo;
  	}

	//#CM53969
	/**<en>
	 * Set the MSG to display.
	 * @param msg         :message
	 * @param msgcolorNo  :mesage color
	 * @param flag  :Please set this Flag 'true' if requesting user to confirm by displaying JavaScript 
	 * confirm(MessageBox).
	 </en>*/
	public void setValue(String msg, int msgcolorNo, boolean flag)
	{
    	//#CM53970
    	/*<en> Set blank if null.</en>*/

    	if (operationmsg == null) {
			operationmsg = "";
		}

		//#CM53971
		/*<en> Set the MSG. </en>*/

		operationmsg    = msg;
		//#CM53972
		/*<en> Set the MSG COLOR. </en>*/

		msgcolor 		= msgcolorNo;
		//#CM53973
		/*<en> Set the Flag true if requesting user to confirm by displaying </en>*/

		//#CM53974
		/*<en> JavaScript confirm(MessageBox). </en>*/

		showmsgbox      = flag;
  	}

	//#CM53975
	// Package methods -----------------------------------------------

	//#CM53976
	// Protected methods ---------------------------------------------

	//#CM53977
	// Private methods -----------------------------------------------
	//#CM53978
	/**<en>
	 * Set the valud of contents(operationmsg) and the color of the message(msgcolor) by specifing the 
	 * message no.
	 * @param msg  :the string which was made by concatenating the message<I>, message no.<I>
	 * and </I> parameter</I>, </I> parameter</I>, using delimiters of <CODE>MessageResource</CODE>.
	 * @param locale :locale to be specified
	 </en>*/
	private void setMessageInfo(String msg, Locale locale)
	{
		int msgnum = 0;
		if (msg.indexOf(MessageResource.DELIM) > 0)
		{
			StringTokenizer stk = new StringTokenizer(msg, MessageResource.DELIM, false) ;
			//#CM53979
			//<en> message no.</en>
     		msgnum = Integer.parseInt( stk.nextToken() ) ;
		}
		else if(msg == null || msg.equals(""))
		{
			//#CM53980
			//<en>6126021 = Null was set for the message no.</en>
			msgnum = 6126021;
		} 
		else
		{
			//#CM53981
			//<en> message no.</en>
			msgnum = Integer.parseInt( msg ) ;
		}

		String facility = LogMessage.getFacility(msgnum);
		if (facility != null)
		{
			if (facility == LogMessage.F_INFO)
			{
				this.msgcolor 		= INFO_MSG;
			}
			else if (facility == LogMessage.F_NOTICE)
			{
				this.msgcolor 		= ATNT_MSG;
			}
			else if (facility == LogMessage.F_WARN)
			{
				this.msgcolor 		= WARN_MSG;
			}
			else if (facility == LogMessage.F_ERROR)
			{
				this.msgcolor 		= ERR_MSG;
			}
		}
		this.operationmsg   = MessageResource.getMessage(msg);
	}


}
