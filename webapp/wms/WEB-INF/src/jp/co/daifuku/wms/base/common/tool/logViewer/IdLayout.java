//#CM643019
//$Id: IdLayout.java,v 1.2 2006/11/07 05:51:08 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643020
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//#CM643021
/**
 * Designer :  <BR>
 * Maker :   <BR>
 * <BR>
 * <pre>
 * ID item configuration file class<BR>
 * ID item configuration file definition class of XML form<BR>
 * </pre>
 * <BR>
 * <BR>
 * Information is acquired from ID item configuration file of the XML form. (<CODE>GetIdColumns() </CODE> method)<BR>
 * <DIR>
 * Acquire ID item information from the XML form file. <BR>
 * Store Transmission information in Transmission information class(ColumnInfo). <BR>
 * <BR>
 * <DIR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:51:08 $
 * @author  $Author: suresh $
 */
public class IdLayout 
{
	//#CM643022
	/**
	 * File name of default ID definition file
	 */
	protected final static String IdLayoutFileName = "id.xml";

	//#CM643023
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:51:08 $";
	}

	//#CM643024
	/**
	 * XML document
	 */
	protected static Document doc = null;
	
	//#CM643025
	/**
	 * Read the ID definition file of the XML form and generate the DOM tree. <BR>
	 * Keep maintaining it to the end of the application once you generate the DOM tree.
	 * 
	 * @throws ParserConfigurationException		It is notified when the initialization error of the parser occurs. 
	 * @throws SAXException		It is notified when the SAX error occurs. 
	 * @throws IOException		It is notified when the I/O error occurs. 
	 * @throws Exception 	It reports on all Exception. 
	 */
	public static void load() throws ParserConfigurationException, SAXException, IOException, Exception
	{
		if (doc == null)
		{
            //#CM643026
            // Read only when XML is not read. 
			FileInputStream oStream = null;
			
			try
			{
				//#CM643027
				// The document factory is generated. 
				DocumentBuilderFactory oFactory = DocumentBuilderFactory.newInstance();
				//#CM643028
				// Document builder is generated. 
				DocumentBuilder oBuilder = oFactory.newDocumentBuilder();
				
				//#CM643029
				// Object of DOM is acquired. 
				oStream = new FileInputStream(LogViewerParam.ConfPath + "\\" + LogViewerParam.IDFileName );
				doc = oBuilder.parse(oStream);
			}
			finally
			{
				if (oStream != null)
				{
					oStream.close();
				}
			}
		}
	}
	
	//#CM643030
	/**
	 * Decompose Transmission message handed over from the id.xml file Name, Length, 
	 * Comment, and after it acquires it and store it 
	 * in Transmission item information class by each line. <BR>
	 * Store the data stored by each line in 
	 * Transmission information class and return it. 
	 * 
	 * @param record Transmission information
	 * @param Id Information on id.xml file
	 * @return IdData[] Transmission information class
	 * @throws Exception It reports on all Exception.
	 */
	public IdData[] getIdColumns (byte[] record, String Id) throws Exception
	{
		//#CM643031
		// Transmission item data class
		ColumnInfo colinfolist[] = null;

		//#CM643032
		// Transmission information class
		IdData idData[] = null;

        try
		{
        	//#CM643033
        	// Read the XML file. 
        	load();

			//#CM643034
			// Obtain the route element. 
			Element root = doc.getDocumentElement();

			//#CM643035
			// The list of specification ID element is acquired. 
			String elementName = "id" + Id;
			NodeList list = root.getElementsByTagName(elementName);

			//#CM643036
			// Specification ID element is acquired. 
			Element element = (Element) list.item(0);

			//#CM643037
			// The list of the item element is acquired. 
			NodeList paramList = element.getElementsByTagName("item");

			//#CM643038
			// The number and Transmission item information of the item element are declared with Array. 
			colinfolist = new ColumnInfo[paramList.getLength()];
			
			//#CM643039
			// The number and Transmission information of the item element are declared with Array. 
			idData = new IdData[paramList.getLength()];

			//#CM643040
			// Starting position for Transmission decomposition
			int start = 0;

			//#CM643041
			// Only the number of item elements loops. 
			for (int j = 0; j < paramList.getLength(); j ++) 
			{
			    //#CM643042
			    // The item element is acquired. 
			    Element paramElement = (Element)paramList.item(j);

			    //#CM643043
			    // XML information maintenance
			    ColumnInfo colinfo = new ColumnInfo();

			    //#CM643044
			    // Transmission information maintenance
			    IdData data = new IdData();

				//#CM643045
				// The value of the name attribute is acquired. 
				colinfo.setName(paramElement.getAttribute("name"));

				//#CM643046
				// Child node (Comment) is acquired. 
				if (paramList.item(j).getFirstChild() == null)
				{
					//#CM643047
					// Set ""for null. 
					colinfo.setComment ("");
				}
				else
				{
					//#CM643048
					// Child node Is acquired (Comment), except for null. 
					colinfo.setComment (paramList.item(j).getFirstChild().getNodeValue());
				}

				//#CM643049
				// Length of decomposing Transmission message is acquired. 
				int length = Integer.parseInt(paramElement.getAttribute("size"));

				//#CM643050
				// Transmission message is decomposed and the specified item taking is acquired. 
				colinfo.setValue(checkChar(record, start, length));
				
				//#CM643051
				// Each information is stored in Transmission item information class. 
				colinfolist[j] = colinfo;

				//#CM643052
				// Line unit information is stored in Transmission information class. 
				data.setTelegramData(colinfolist[j]);

				idData[j] = data;

				start += length;
			}
        }
	    catch (ParserConfigurationException e)
		{
            String msg = MessageResourceFile.getText("6006003");
            throw new Exception(msg);
        }
	    catch (SAXException e)
		{
            String msg = MessageResourceFile.getText("6006002");
            throw new Exception(msg);
        }
        catch (IOException e)
		{
            //#CM643053
            //The I/O error occurs. 
            String param[] = new String[1];
            param[0] = LogViewerParam.IDFileName;
            String msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }
        catch (NullPointerException e)
        {
            String param[] = new String[1];
            param[0] = "id" + Id;
            String msg = MessageResourceFile.getText("6006004");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }

        return idData;
    }

	//#CM643054
	/**
	 * half-width and normal-width are examined by one byte of Transmission. <BR>
	 * TelegramCount improves counting and it maintains for the following 
	 * Transmission message decomposition in case of half-width. <BR>
	 * Return decomposing Transmission message. <BR>
	 * 
	 * @param record Transmission
	 * @param offset Starting position
	 * @param length Ending position
	 * @return String Transmission message which has been divided
	 */
	public String checkChar(byte[] record, int offset, int length)
	{
		byte[] buf = new byte[length];
		for (int i = 0; i < length; i ++)
		{
			if (offset + i >= record.length)
			{
				buf[i] = 0x20;
			}
			else
			{
	            buf[i] = record[offset + i];
			}
		}
		return new String(buf);
	}
}
