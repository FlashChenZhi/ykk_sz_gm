// $Id: CompressToZipFile.java,v 1.2 2006/11/07 05:57:28 suresh Exp $
package jp.co.daifuku.wms.base.common.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM642836
/*
 * Copyright 2002-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM642837
/**
 * Class which compresses FTP history file
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD></TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:57:28 $
 * @author  $Author: suresh $
 */
public class CompressToZipFile
{
	//#CM642838
	// Class fields --------------------------------------------------
    //#CM642839
    /**
     * Field which comes to be well known class name
     */
    private static final String CLASS_NAME = "CompressToZipFile";
    //#CM642840
    /**
     * Extension. Field which shows zip
     */
    private static final String zipExtension = ".zip";
 
	//#CM642841
	// Class variables -----------------------------------------------
    
    //#CM642842
    /**
     * Date format
     */
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    
	//#CM642843
	// Class method --------------------------------------------------
	//#CM642844
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 05:57:28 $");
	}

	//#CM642845
	// Constructors --------------------------------------------------
	
	//#CM642846
	// Public methods ------------------------------------------------

	//#CM642847
	/**
	 * Class which compresses FTP history file.<BR>
	 * Compress files (history file that the RFT server made) other than Zip which exists in a specified folder into 
	 * the zip form file of Name yyyyMMddhhmmss.zip. <BR>
	 * After compression, delete the compressed file.
	 * <BR>
	 * <DIR>
	 * </DIR>
	 * 
	 * @param	args	(Unused)
	 */
	public static void main(String[] args)
	{
		//#CM642848
		// Path for FTP file backup
		String histPath = WmsParam.FTP_FILE_HISTORY_PATH;

		//#CM642849
		//Files other than Zip on a specified folder are compressed. (After compression, the former file is Deleted. )
		makeZipFile(histPath);
	}

	//#CM642850
	/**
	 * Compress it into the zip form file of Name yyyyMMddhhmmss.zip in a specified folder.  <BR>
	 * <OL>
	 * <LI>Sleep for one second, make File name again, and make it to File name which does not exist when yyyyMMddhhmmss.zip exists. <BR>
	 * <LI>The extension is off the subject and the file of ". zip" is compressing off the subject.  <BR>
	 * <LI>The folder and the size are off the subject and the file of 0 is compressing off the subject. <BR>
	 * <LI>After compression, delete the compressed file. <BR>
	 * </OL>
	 * 
	 * @param pathName
	 *            Absolute path of send and recv
	 * @return flag <code>ture</code> Or, <code>false</code>
	 */
	public static boolean makeZipFile(String pathName)
	{
		//#CM642851
		// Directory existence check. It comes off processing when there is no object folder. 
		File file = new File(pathName);
		if (!(file.exists() && file.isDirectory()))
		{
			return false;
		}
		File fileList[] = file.listFiles();

		//#CM642852
		// File existence check. Make it again one second later when same File name exists. 
		boolean fileExits = true;
		String zipFileName;
		do
		{
			//#CM642853
			// The System date is acquired. 
			Date dt = new Date();
			String workingDate = DATE_FORMAT.format(dt);
			zipFileName = workingDate + zipExtension;
			File ZipFileObj = new File(pathName + zipFileName);
			if (! ZipFileObj.exists())
			{
				fileExits = false;
			}
			else
			{
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					fileExits = false;
				}
			}
		}while(fileExits);
		
		boolean responseFlag = false;
		try
		{
			int compCount = 0;
		
			//#CM642854
			// ZipOutputStream is generated. 
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(	pathName + zipFileName));
			BufferedInputStream bis = null;
	
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = null;
	
			//#CM642855
			// Compress the history folder into the ZIP form. 
			for (int i = 0; i < fileList.length; i++)
			{
				if(! fileList[i].getName().endsWith(zipExtension))
				{
					File hisFile = new File(fileList[i].getAbsolutePath());
					if(hisFile.isFile() && hisFile.length() > 0)
					{
						int len = (int) hisFile.length();
						bis = new BufferedInputStream(new FileInputStream(hisFile.getAbsolutePath()), len);
						//#CM642856
						// Acquire byte Array from the file. 
						byte buf[] = new byte[len];
						bis.read(buf, 0, len);
						ZipEntry target = new ZipEntry(fileList[i].getName());
						zos.putNextEntry(target);
						zos.write(buf, 0, buf.length);
						zos.closeEntry();
						bis.close();
		
						compCount++;
					}
				}
			}
			if (compCount > 0)
			{
				zos.finish();
			}
			else
			{
				//#CM642857
				// Made as dummy. (Should be able to close normally)
				ZipEntry target = new ZipEntry("dmy");
				zos.putNextEntry(target);
				byte buf[] = new byte[0];
				zos.write(buf, 0, 0);
				zos.closeEntry();
				zos.finish();
			}
			byte[] bufResult = baos.toByteArray();
			bos = new BufferedOutputStream(zos);
			bos.write(bufResult, 0, bufResult.length);
	
			bos.close();
			zos.close();
			baos.close();
			
			//#CM642858
			// Delete the file for compression and the Zip file when there is no Deletion object file.
			if (compCount > 0)
			{
				for (int i = 0; i < fileList.length; i++)
				{
					if(! fileList[i].getName().endsWith(zipExtension))
					{
						if(fileList[i].isFile() && fileList[i].length() > 0)
						{
							fileList[i].delete();
						}
					}
				}
				responseFlag = true;
			}
			else
			{
				File zipFile = new File(pathName + zipFileName);
				zipFile.delete();
				responseFlag = false;
			}
		}
		catch (IOException e)
		{
		    RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR, CLASS_NAME, e);
			return false;
		}

		return responseFlag;
	}
	//#CM642859
	// Package methods -----------------------------------------------
	//#CM642860
	// Protected methods ---------------------------------------------
	//#CM642861
	// Private methods -----------------------------------------------
}
//#CM642862
//end of class
