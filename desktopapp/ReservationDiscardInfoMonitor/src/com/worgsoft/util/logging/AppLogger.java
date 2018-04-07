package com.worgsoft.util.logging;

import com.worgsoft.util.common.Application;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 18:58:50
 * To change this template use File | Settings | File Templates.
 */
public class AppLogger
{
    private static Logger developmentLogger = null;
    private static Logger applicationLogger = null;

    static
    {
        try
        {

            PropertyConfigurator.configure(Application.getConfigurationPath() + "log4j.properties");

            applicationLogger = LogManager.exists("application_logger");
            developmentLogger = LogManager.exists("development_logger");

            if (applicationLogger == null)
            {
                System.out
                        .println("logger for application info was not found. if you want to log application info, please config a logger naming 'application_logger'.");
            }

            if (developmentLogger == null)
            {
                System.out
                        .println("logger for development info was not found. if you want to log development info, please config a logger naming 'development_logger'.");
            }
        }
        catch (Exception ex)
        {
            System.out.println("error occurs when initiating logger." + ex.getMessage());
        }
    }

    public static void logDebugMessage(String message)
    {
        if (developmentLogger != null)
        {
            developmentLogger.log(Level.DEBUG, "[DEBUG] " + message);
        }
    }

    public static void logErrorMessage(String message)
    {
        if (applicationLogger != null)
        {
            applicationLogger.log(Level.ERROR, "[ERROR] " + message);
        }
    }

    public static void logInfoMessage(String message)
    {
        if (applicationLogger != null)
        {
            applicationLogger.log(Level.INFO, "[INFO] " + message);
        }
    }

    public static void logWarnMessage(String message)
    {
        if (applicationLogger != null)
        {
            applicationLogger.log(Level.WARN, "[WARN] " + message);
        }
    }
}
