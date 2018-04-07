package com.worgsoft.ykk.monitor;

import com.worgsoft.util.logging.AppLogger;
import com.worgsoft.ykk.persistence.Session;
import com.worgsoft.ykk.persistence.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 14:42:39
 * To change this template use File | Settings | File Templates.
 */
public class ReservationDiscardInfoMonitor implements Runnable
{

    public void run()
    {
        AppLogger.logInfoMessage("ReservationDiscardInfoMonitor is running");

        try
        {
            while (true)
            {
                processDiscardInfo();
                Thread.sleep(3000);
            }
        }
        catch (Exception ex)
        {
            AppLogger.logErrorMessage(ex.getMessage());
        }
        finally
        {
            AppLogger.logInfoMessage("ReservationDiscardInfoMonitor is stopped");
            System.exit(0);
        }
    }

    private void processDiscardInfo()
    {
        Session session = null;
        try
        {
            session = SessionFactory.getSession();
            List discardInfoList = session.getDiscardInfoList();
            if (discardInfoList == null || discardInfoList.size() == 0)
            {
                return;
            }
            List reservationInfoList = new ArrayList();
            for (int i = 0; i < discardInfoList.size(); i++)
            {
                DiscardInfo discardInfo = (DiscardInfo) discardInfoList.get(i);
                ReservationInfo reservationInfo = session.getReservationInfo(discardInfo.getRetrievalNo(), discardInfo.getSerialNo());
                if (reservationInfo != null)
                {
                    discardInfo.isProcessed = true;

                    long expectRetrievalQty = reservationInfo.getExpectedRetrievalQty();
                    long actualRetrievalQty = reservationInfo.getActualRetrievalQty();
                    long expectDiscardQty = discardInfo.getExpectedDiscardQty();

                    long actualDiscardQty = expectRetrievalQty - actualRetrievalQty < expectDiscardQty ? expectRetrievalQty - actualRetrievalQty : expectDiscardQty;
                    expectRetrievalQty -= actualDiscardQty;

                    discardInfo.setActualDiscardQty(actualDiscardQty);
                    reservationInfo.setExpectedRetrievalQty(expectRetrievalQty);

                    reservationInfoList.add(reservationInfo);
                }
            }

            for (int i = 0; i < discardInfoList.size(); i++)
            {
                DiscardInfo discardInfo = (DiscardInfo) discardInfoList.get(i);
                if (discardInfo.isProcessed)
                {
                    session.saveDiscardInfo(discardInfo);
                }
            }

            for (int i = 0; i < reservationInfoList.size(); i++)
            {
                session.saveReservationInfo((ReservationInfo) reservationInfoList.get(i));
            }

            session.commit();
        }
        catch (Exception ex1)
        {
            AppLogger.logErrorMessage(ex1.getMessage());

            try
            {
                if (session != null)
                {
                    session.rollback();
                }
            }
            catch (Exception ex2)
            {
                AppLogger.logErrorMessage(ex2.getMessage());
            }

        }
        finally
        {
            try
            {
                if (session != null)
                {
                    session.close();
                }
            }
            catch (Exception ex)
            {
                AppLogger.logErrorMessage( ex.getMessage());
            }
        }
    }
}
