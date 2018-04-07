package com.worgsoft.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PropertyLoader
{
    public static String load(BaseProperty property) throws Exception
    {
        String fileName = property.getFileName();
        String propName = property.getPropertyName();
        fileName = Application.getConfigurationPath() + fileName;
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
            // AppLogger.logErrorMessage(message);
            throw new Exception(message);
        }
        catch (IOException e)
        {
            String message = "failed to read property file \""
                    + file.getAbsolutePath() + "\".";
            // AppLogger.logErrorMessage(message);
            throw new Exception(message);
        }

        if (p.getProperty(propName) == null)
        {
            String message = "property \"" + propName
                    + "\" doesn't exist in file \"" + file.getAbsolutePath()
                    + "\".";
            // AppLogger.logErrorMessage(message);
            throw new Exception(message);
        }

        return p.getProperty(propName).trim();
    }
}
