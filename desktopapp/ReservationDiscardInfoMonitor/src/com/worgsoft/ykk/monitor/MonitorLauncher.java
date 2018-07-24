package com.worgsoft.ykk.monitor;

import com.worgsoft.util.logging.AppLogger;


/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 18:23:42
 * To change this template use File | Settings | File Templates.
 */
public class MonitorLauncher
{
    private static final int TIME_INVERVAL = 500;

    public static void main(String[] args)
    {
        try
        {
            ReservationDiscardInfoMonitor monitor = new ReservationDiscardInfoMonitor();
            new Thread(monitor).start();
            Thread.sleep(TIME_INVERVAL);
        }
        catch (Exception ex)
        {
            AppLogger.logErrorMessage( ex.getMessage());
        }
    }
}
