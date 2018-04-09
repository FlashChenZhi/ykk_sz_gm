package jp.co.daifuku.wms.base.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Vector;

//******************************************************************************
//#CM686771
/**
 * This abstract class is used for plan data loading<BR>
 * Derive data format and generate the class
 * A constructor without argument is mandatory
 *
 * @author  
 * @version $Revision: 1.2 $
 */
public class DataLoader implements FilenameFilter {
    //#CM686772
    /** file path */

    protected String filePath = null;

    //#CM686773
    /** to store file list  */

    protected String[] dataFiles;

    //#CM686774
    /** index for file list */

    protected int dataFileIndex;

    //#CM686775
    /** to store data */

    protected String[] readData;

    //#CM686776
    /** current line no. */

    protected int lineNo = 0; 
  
    //#CM686777
    /** file name of the current file */

    protected String filename = "";

	//#CM686778
	/** file size of the current file */

	protected long filesize = 0; 
  
    //#CM686779
    /** initial vector size to read file */

    final protected int vectorInitialSize = 1000;

    //#CM686780
    /** folder **/

    protected String filter;

	//#CM686781
	/** folder that contains the file name to read */

    protected FilenameFilter fnameFilter;

    //#CM686782
    /**
     * create instance
     */
    DataLoader ()
    {
		fnameFilter = this;
    }

    //#CM686783
    /**
     * create instance
     * @param f file name
     */
    DataLoader (String f)
    {
		fnameFilter = this;
		filename = f;
    }

    //****************************************************************************
    //#CM686784
    /**
     * Return the current file name
     * @return	file name
     */
    //****************************************************************************
    public String  getFileName()  {
		return filename;
    }
		
	//****************************************************************************
	//#CM686785
	/**
	 * Return the current file size
	 * @return	file size
	 */
	//****************************************************************************
	public long  getFileSize()  {
		return filesize;
	}
		
    //****************************************************************************
    //#CM686786
    /**
     * Return the current file line no.
     * @return	file name
     */
    //****************************************************************************
    public String  getLineNo()  {
		return String.valueOf (lineNo);
    }

    //****************************************************************************
    //#CM686787
    /**
     * set the file name in the specified path name
     * <P>
     * @param argFilePath                 input csv file path (separator at the end)
     *         argFileName                 input csv file name (wild card [*] can be used)
     * @throws NullPointerException    if file open error occurs
     * @throws FileNotFoundException   if the specified file can't be found
     */
    //****************************************************************************
    public void open(String argFilePath , final String argFileName)
    		throws NullPointerException, FileNotFoundException
    {
		//#CM686788
		// save file path
		filePath = argFilePath;
	
		//#CM686789
		// fetch file list
		File file = new File(argFilePath);
		filter = argFileName;
		dataFiles = file.list(fnameFilter);
		if (dataFiles == null)
		{
			throw (new FileNotFoundException());
		}
		//#CM686790
		// fetch file size
		for (int i = 0; i < dataFiles.length ;i++)
		{
			File wfile = new File(argFilePath + dataFiles[0].toString());
			filesize = wfile.length();
			if (filesize <= 0) break;
		}

		//#CM686791
		// read the first file
		dataFileIndex = 0;
    }

    //****************************************************************************
    //#CM686792
    /**
     * read one line data from the data file
     * <P>
     * @return String[]   one line record as a String array (null when EOF)
	 * @throws	FileNotFoundExeption if the specified file can't be found
	 * @throws CsvIllegalDataException if the item width of the specified key overflows
	 * @throws IOExeption if the file input/output fails
     */
    //****************************************************************************
    public String[] readData() throws FileNotFoundException , IOException, CsvIllegalDataException
    {
		return null;
    }

    //****************************************************************************
    //#CM686793
    /**
     * read plan data file to memory
     * <P>
	 * @throws	FileNotFoundExeption if the specified file can't be found
	 * @throws IOExeption if the file input/output fails
     */
    //****************************************************************************
    protected void readDataFile() throws FileNotFoundException, IOException
    {
		//#CM686794
		// read file
		filename = filePath + dataFiles[dataFileIndex++] ;
		FileReader fr = new FileReader (filename);
		BufferedReader br = new BufferedReader (fr);
		Vector vec = new Vector (vectorInitialSize);
		String buf = "";

		while ((buf = br.readLine()) != null) { 
	    	vec.add (buf);
		}

		fr.close ();
		br.close ();
		readData = new String[vec.size()];
		vec.copyInto (readData);

		//#CM686795
		// initialize current line
		lineNo = 0;
    }

    //****************************************************************************
    //#CM686796
    /**
     * plan data folder function
     * <P>
     * @param		dir		directory to search
     * 				name	file name
     * @return		whether condition is met or not
     * @see		java.io.FilenameFilter
     */
    //****************************************************************************
    public boolean accept (File dir, String name)
    {
		//#CM686797
		// wrong if the file name length is different
		if (name.length() != filter.length())
	    	return false;

		//#CM686798
		// check whether the file name matches with the specified one
		for (int i=0; i < name.length(); i++) {
	    	if (filter.charAt(i) != '*' && 
				Character.toLowerCase(filter.charAt(i)) != Character.toLowerCase(name.charAt(i))) {
				return false;
	    	}
		}
		return true;	
    }
}
