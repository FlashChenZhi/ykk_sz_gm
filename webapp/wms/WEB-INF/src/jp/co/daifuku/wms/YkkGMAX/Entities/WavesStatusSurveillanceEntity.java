package jp.co.daifuku.wms.YkkGMAX.Entities;

public class WavesStatusSurveillanceEntity
{
	private String workFile = "";

	private String batch = "";

	private String checkDate = "";

	private String trz00 = "";

	private String try00 = "";

	private String trx00 = "";

	private String retrievalCancel = "";

	public String getWorkFile()
	{
		return workFile;
	}

	public void setWorkFile(String workFile)
	{
		this.workFile = workFile;
	}

	public String getBatch()
	{
		return batch;
	}

	public void setBatch(String batch)
	{
		this.batch = batch;
	}

	public String getCheckDate()
	{
		return checkDate;
	}

	public void setCheckDate(String checkDate)
	{
		this.checkDate = checkDate;
	}

	public String getTrz00()
	{
		return trz00;
	}

	public void setTrz00(String trz00)
	{
		this.trz00 = trz00;
	}

	public String getTry00()
	{
		return try00;
	}

	public void setTry00(String try00)
	{
		this.try00 = try00;
	}

	public String getTrx00()
	{
		return trx00;
	}

	public void setTrx00(String trx00)
	{
		this.trx00 = trx00;
	}

	public String getRetrievalCancel()
	{
		return retrievalCancel;
	}

	public void setRetrievalCancel(String retrievalCancel)
	{
		this.retrievalCancel = retrievalCancel;
	}
}
