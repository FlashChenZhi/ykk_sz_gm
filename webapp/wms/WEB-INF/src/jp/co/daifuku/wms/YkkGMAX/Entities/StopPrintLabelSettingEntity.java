package jp.co.daifuku.wms.YkkGMAX.Entities;

public class StopPrintLabelSettingEntity
{
	private String unify_ticket_printflg = "";

	private String ticket_printflg = "";

	private String cart_ticket_printflg = "";

	public String getCart_ticket_printflg()
	{
		return cart_ticket_printflg;
	}

	public void setCart_ticket_printflg(String cart_ticket_printflg)
	{
		this.cart_ticket_printflg = cart_ticket_printflg;
	}

	public String getTicket_printflg()
	{
		return ticket_printflg;
	}

	public void setTicket_printflg(String ticket_printflg)
	{
		this.ticket_printflg = ticket_printflg;
	}

	public String getUnify_ticket_printflg()
	{
		return unify_ticket_printflg;
	}

	public void setUnify_ticket_printflg(String unify_ticket_printflg)
	{
		this.unify_ticket_printflg = unify_ticket_printflg;
	}

}
