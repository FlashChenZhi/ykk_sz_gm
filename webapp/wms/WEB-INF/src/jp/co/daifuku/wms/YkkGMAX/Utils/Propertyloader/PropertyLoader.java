package jp.co.daifuku.wms.YkkGMAX.Utils.Propertyloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public final class PropertyLoader
{
    public static String load(BaseProperty property)
    {

	String fileName = property.getFileName();
	String propName = property.getPropertyName();
	String osName = System.getProperties().getProperty("os.name");
	if (osName.indexOf("Windows") != -1)
	{
	    fileName = "C:\\daifuku\\wms\\tomcat-5.0.16\\webapps\\wms\\WEB-INF\\classes\\"
		    + fileName;
	}
	else
	{
	    fileName = "/con/wms/tomcat-5.0.16/webapps/wms/WEB-INF/classes/"
		    + fileName;
	}
	File file = new File(fileName);
	FileInputStream in = null;
	Properties p = null;
	try
	{
	    in = new FileInputStream(file);
	    p = new Properties();
	    p.load(in);
	    in.close();
	}
	catch (FileNotFoundException e)
	{
	    String message = "failed to find property file \""
		    + file.getAbsolutePath() + "\".";
	    DebugPrinter.print(DebugLevel.ERROR, message, e);
	}
	catch (IOException e)
	{
	    String message = "failed to read property file \""
		    + file.getAbsolutePath() + "\".";
	    DebugPrinter.print(DebugLevel.ERROR, message, e);
	}

	return p == null ? null : p.getProperty(propName).trim();
    }

    private PropertyLoader()
    {
    }
}
