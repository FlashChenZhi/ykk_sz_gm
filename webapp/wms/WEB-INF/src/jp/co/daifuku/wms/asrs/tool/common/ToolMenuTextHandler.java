// $Id: ToolMenuTextHandler.java,v 1.2 2006/10/30 01:40:54 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;


//#CM46842
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;

//#CM46843
/**<en>
 * This class is used in menu setting tool.<BR>
 * This class operates the MenuText files.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/15</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:54 $
 * @author  $Author: suresh $
 </en>*/
public class ToolMenuTextHandler
{
	//#CM46844
	// Class fields --------------------------------------------------

	//#CM46845
	// Class variables -----------------------------------------------
	//#CM46846
	/**<en>
	 * Section delimiter (start)
	 </en>*/
	private static final String SEC_START = "[";
	//#CM46847
	/**<en>
	 * Section delimiter (end)
	 </en>*/
	private static final String SEC_END = "]";
	//#CM46848
	/**<en>
	 * Comment characters 
	 </en>*/
	private static final String COMMENT_KEY = "#";
	//#CM46849
	/**<en>
	 * Retource file path
	 </en>*/
	private String wFileName;
	//#CM46850
	/**<en>
	 * KEY delimiter. Default is "=".
	 </en>*/
	private String wSeparate = ToolMenuText.wSeparate;

	//#CM46851
	/**<en>
	 * Vector for the storage of file contents
	 </en>*/
	private Vector lines = new Vector();

	//#CM46852
	// Public Class method -------------------------------------------
	//#CM46853
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:54 $") ;
	}

	//#CM46854
	// Private Class method ------------------------------------------

	//#CM46855
	// Constructors --------------------------------------------------
	//#CM46856
	/**<en>
	 * Retrieve the file name and delimiters, then read the file.
	 * This is used when there is no "=" between the KEY item and the value.
	 * @param fname :file name
	 * @param sepa  :delimiter which delimits the KEY and the value
	 </en>*/
	public ToolMenuTextHandler(String fname) throws ReadWriteException{
		wFileName = fname;
		read();
	}
	//#CM46857
	// Public methods ------------------------------------------------

	//#CM46858
	/**<en>
	 * Search the key of specified section, then return the set value.
	 * @param section :name of the section to search
     * @return        :set value
	 </en>*/
	public ArrayList find(String section) {
		boolean insideSection = false;
		ArrayList list    = new ArrayList();
		String tempkey    = "";
		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					//#CM46859
					//<en> It could not be found after moving to a different section.</en>
					//#CM46860
					//<en> Defines that there is only one corresponding section.</en>
					if(str.indexOf(SEC_START)==0) break;

					//#CM46861
					//<en> Search "=".</en>
					int index = str.indexOf(wSeparate);
					if(index >= 0) {
						//#CM46862
						//<en> Retrieve the key which is leading "=".</en>
						String key = str.substring(0, index).trim();
						//#CM46863
						//<en> Disregard if it is a category.</en>
						if (key.equals(section))
							continue;
						//#CM46864
						//<en> If the Key is found in the section, return that value.</en>
						if(key.substring(3,5).equals("00"))
						{
							list.add(find(section, key));
						}
					}
				}
				else {
					//#CM46865
					//<en> Search the section delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						//#CM46866
						//<en> Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();

						//#CM46867
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		return list;
	}


	//#CM46868
	/**<en>
	 * Search the key of the specified section. then return the set value.
	 * @param section :name of the section to search
	 * @param key     :function KEY
     * @return        :list of the line of buttons which correspond to the function keys specified 
	 </en>*/
	public ArrayList find(String section, String key) {
		boolean insideSection = false;
		ArrayList list = new ArrayList();
		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					//#CM46869
					//<en> Search "=".</en>
					int index = str.indexOf(wSeparate);
					if(index >= 0) {
						//#CM46870
						//<en> Retrieve the Key which is leading "=".</en>
						String tempkey = str.substring(0, index).trim();

						//#CM46871
						//<en> If the Key is found in the section, return that value.</en>
						if(tempkey.substring(0,3).equals(key.substring(0,3))) {
							list.add(convertMenuText(str));
						}
					}
					//#CM46872
					//<en> It could not be found after moving to a different section.</en>
					//#CM46873
					//<en> Defines that there is only one corresponding section.</en>
					if(str.indexOf(SEC_START)==0) break;
				}
				else {
					//#CM46874
					//<en> Search the section delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						//#CM46875
						//<en>  Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();

						//#CM46876
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		return list;
	}

	//#CM46877
	/**<en>
	 * Search the key of the specified section. then return the set value.
	 * @param section :name of the section to search
	 * @param key     :KEY to search
     * @return        :set value
	 </en>*/
	public ToolMenuText findMenuText(String section, String key) {
		boolean insideSection = false;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					//#CM46878
					//<en> Search "=".</en>
					int index = str.indexOf(wSeparate);
					if(index >= 0) {
						//#CM46879
						//<en> Retrieve the Key which is leading "=".</en>
						String tempkey = str.substring(0, index).trim();

						//#CM46880
						//<en> If the Key is found in the section, return that value.</en>
						if(tempkey.equals(key)) {
							return convertMenuText(str);
						}
					}
					//#CM46881
					//<en> It could not be found after moving to a different section.</en>
					//#CM46882
					//<en> Defines that there is only one corresponding section.</en>
					if(str.indexOf(SEC_START)==0) return null;
				}
				else {
					//#CM46883
					//<en> Search the section delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						//#CM46884
						//<en> Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();

						//#CM46885
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		return null;
	}


	//#CM46886
	/**<en>
	 * Modify the set value. If there is no KEY, add a new key.
	 * @param section 	:section
	 * @param list      :contents of registration
	 </en>*/
	public void set(String section, String sectionname, ArrayList list)  throws ReadWriteException{
		boolean insideSection = false;
		//#CM46887
		//<en> Firstly, if the section(or category) already exists, clear what it contains.</en>
		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);
			if(str.length()>0) {
				if(insideSection) {
					//#CM46888
					//<en> Break when moved to a different section.</en>
					if(str.indexOf(SEC_START)==0)
						break;
					//#CM46889
					//<en> Continue if the key is the name of a category.</en>
					int index = str.indexOf(wSeparate);
					if(index >= 0) {
						//#CM46890
						//<en> Retrieve the Key which is leading "=".</en>
						String tempkey = str.substring(0, index).trim();
						if (tempkey.equals(section))
							continue;
					}
				//#CM46891
				//	lines.remove(i);
				//#CM46892
				// replace
					lines.set(i, "");
				}
				else {
					//#CM46893
					//<en> Search the section delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END) == str.length()-1) {
						//#CM46894
						//<en> Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();
						//#CM46895
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		//#CM46896
		//<en> If there is no corresponding section, create one.</en>
		if(!insideSection)
		{
			lines.add(SEC_START + section + SEC_END);
			lines.add(section+wSeparate+sectionname);

		}
		//#CM46897
		//<en> Register.</en>
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			ArrayList bbb = (ArrayList)iterator.next();
			Iterator iterator3 = bbb.iterator();
			while (iterator3.hasNext())
			{
				ToolMenuText menutext = (ToolMenuText)iterator3.next();
				set(section, menutext.getKey(), menutext.toString());
			}
		}
	}


	//#CM46898
	/**<en>
	 * Modify the set value. If there is no KEY, add a new key.
	 * @param section 	:section
	 * @param key 		:search KEY
	 * @param value 	:contents of modification
	 </en>*/
	public void set(String section, String key, String value)  throws ReadWriteException{
		boolean insideSection = false;
		int i = 0;
		for(i = 0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					//#CM46899
					//<en> Search "=".</en>
					int index = str.indexOf(wSeparate);

					if(index >= 0) {
						//#CM46900
						//<en> Search the key which is leading "="</en>
						String tempkey = str.substring(0, index).trim();

						//#CM46901
						//<en> If the key is found in the section, replace the values.</en>
						if(tempkey.equals(key)) {
							//#CM46902
							// replace
							lines.set(i, key+wSeparate+value);
							break;
						}
					}

					//#CM46903
					//<en> When moved to a different section, insert the value to the previous line.</en>
					if(str.indexOf(SEC_START)==0) {
						//#CM46904
						// insert prev
						lines.add(i, key+wSeparate+value);
						lines.add(i + 1, "");
						break;
					}
				}
				else {
					//#CM46905
					//<en> Search the selection delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						//#CM46906
						//<en> Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();
						//#CM46907
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		//#CM46908
		//<en> If the corresponding section was found, and if it was the last section,</en>
		if (i == lines.size() && insideSection)
		{
			lines.add(key+wSeparate+value);
		}
		//#CM46909
		//<en> If there is no corresponding section, create one.</en>
		if(!insideSection)
		{
			lines.add(SEC_START + section + SEC_END);
			lines.add(key+wSeparate+value);

		}
//#CM46910
//		flush();
	}

	//#CM46911
	/**<en>
	 * Return a list of categories.
	 * @return :the array of category list
	 </en>*/
	public String[] getCategorys() {
		Vector secVec = new Vector();
		String[] sectionstr;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);
			if(str.length()>0) {
				if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
					secVec.addElement( str.substring(1, str.length()-1).trim() );	
				}
			}
		}

		if(secVec==null) return null;

		sectionstr = new String[secVec.size()];
		secVec.copyInto(sectionstr);

		return sectionstr;
	}

	//#CM46912
	/**<en>
	 * Return a list of KEYs of the specified section.
	 * @param section :section
	 * @return :the array of KEY list of the section
	 </en>*/
	public String[] getKeys(String section) {
		Vector keyVec = new Vector();
		String[] keystr;
		boolean insideSection = false;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					//#CM46913
					//<en> Search "=".</en>
					int index = str.indexOf(wSeparate);

					//#CM46914
					//<en> If there is no comment but "=" exists,</en>
					if(str.indexOf(COMMENT_KEY) != 0 && index >= 0) {
						//#CM46915
						//<en> Retrieve the Key which is leading "=",</en>
						String tempkey = str.substring(0, index).trim();

						//#CM46916
						//<en> If the key was found in the section, set to Vector.</en>
						keyVec.addElement( tempkey );	

					}
					//#CM46917
					//<en> Terminate when moved to a different section.</en>
					if(str.indexOf(SEC_START)==0) {
						if(keyVec==null) return null;

						keystr = new String[keyVec.size()];
						keyVec.copyInto(keystr);

						return keystr;
					}
				}
				else
				{
					//#CM46918
					//<en> Search the section delimiter.</en>
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						//#CM46919
						//<en> Retrieve the name of the section. (the space will be ommitted.)</en>
						String tempsection = str.substring(1, str.length()-1).trim();

						//#CM46920
						//<en> If corresponding section is found,</en>
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}

		if(keyVec==null) return null;

		keystr = new String[keyVec.size()];
		keyVec.copyInto(keystr);

		return keystr;
	}

	//#CM46921
	/**<en>
	 * Write in the resource file as INI file.
	 * It is necessary that this method should be called always after calling set().
	 </en>*/
	public void flush() throws ReadWriteException {
		try {
	//#CM46922
	//		BufferedWriter writer = new BufferedWriter(new FileWriter(wFileName));


			BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(
					new FileOutputStream(wFileName),
					"UTF-8"));


			for (int i = 0; i < lines.size(); i++) {
				String str = (String)lines.elementAt(i);
				if (str.length() != 0)
				{
					writer.write(str);
					writer.newLine();
				}
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ReadWriteException(e.getMessage());
		}
	}


	//#CM46923
	// Package methods -----------------------------------------------

	//#CM46924
	// Protected methods ---------------------------------------------
	//#CM46925
	/**<en>
	 * Mapping of result set.
	 </en>*/
	protected ToolMenuText convertMenuText(String line)
	{
		Vector vec = new Vector();
		ToolMenuText menu = new ToolMenuText(line);
		return menu;

	}
	//#CM46926
	// Private methods -----------------------------------------------
	//#CM46927
	/**<en>
	 * Read the resource file as INI file.
	 * If Java -version 1.3.0_02 is used, <CODE>ready()</CODE> method of <CODE>BufferedReader</CODE>
	 * will regard the last line of the file (EOF) as a line to read, and returns true.<BR>
	 * As a result, <CODE>readLine()</CODE> will return null and add null to the <CODE>Vector</CODE>;
	 * therfore if the string is operated, which was stored in the <CODE>Vector</CODE> lastly, 
	 * NullPointerException will occur.<BR>
	 * Therefore, if returning null by <CODE>readLine()</CODE>, ensure not to add to <CODE>Vector</CODE>.
	 </en>*/
	private void read() throws ReadWriteException
	{
		lines.clear();
		try
		{
		//#CM46928
		//	BufferedReader reader = new BufferedReader(new FileReader(fname));
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(wFileName),
					"UTF-8"));

			while(reader.ready())
			{
				String str = reader.readLine();
				if (str != null)
				{
					lines.add(str);
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ReadWriteException(e.getMessage());
		}
	}


	//#CM46929
	// Debug methods -------------------------------------------------

}
//#CM46930
//end of class
