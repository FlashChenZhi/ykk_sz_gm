package jp.co.daifuku.wms.YkkGMAX.resident;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.ui.control.MessageFactory;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
import jp.co.daifuku.wms.YkkGMAX.resident.StockoutAutoRequestProcessor;
import jp.co.daifuku.wms.base.common.WmsParam;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */
public class AutoStockoutDaemon {
    public static void main(String[] args) {
        while (true) {
            Connection conn = null;
            List list;

            try {
                conn = WmsParam.getConnection();

                ASRSInfoCentre centre = new ASRSInfoCentre(conn);

                if(centre.isAuto()) {

                    list = centre.getStockoutList();

                    if (!list.isEmpty()) {


                        for (int i = 0; i < list.size(); i++) {
                            List l = new ArrayList();
                            l.add(list.get(i));

                            ProcessorInvoker pi = new ProcessorInvoker(new Message() {
                                public String getId() {
                                    return null;
                                }

                                public void setMsgResourceKey(String s) {

                                }

                                public String getMsgResourceKey() {
                                    return null;
                                }

                                public void setMsgParameter(List list) {

                                }

                                public List getMsgParameter() {
                                    return null;
                                }

                                public void setCssClass(String s) {

                                }

                                public String getCssClass() {
                                    return null;
                                }

                                public void setBalloon(boolean b) {

                                }

                                public boolean getBalloon() {
                                    return false;
                                }
                            });

                            pi.addProcessor(new StockoutAutoRequestProcessor(new ArrayList(l),
                                    "auto", "1"));

                            pi.run();
                        }

                    }
                }
            }  catch (YKKSQLException sqlEx) {
                String msgString = MessageResources.getText(sqlEx.getResourceKey());
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                sqlEx.printStackTrace();
            } catch (SQLException dbEx) {
                String msgString = MessageResources.getText("7200003");
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                dbEx.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(180000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
