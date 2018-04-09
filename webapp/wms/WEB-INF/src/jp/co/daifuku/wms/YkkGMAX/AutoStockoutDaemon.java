package jp.co.daifuku.wms.YkkGMAX;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.YkkGMAX.resident.ProcessorInvoker;
import jp.co.daifuku.wms.YkkGMAX.resident.StockoutAutoRequestProcessor;

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
                conn = ConnectionManager.getConnection();

                ASRSInfoCentre centre = new ASRSInfoCentre(conn);

                list = centre.getStockoutList();

                if (!list.isEmpty()) {
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

                    pi.addProcessor(new StockoutAutoRequestProcessor(new ArrayList(list),
                            "auto", "0"));

                    pi.run();
                }
            } catch (YKKDBException dbEx) {
                String msgString = MessageResources.getText(dbEx.getResourceKey());
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                dbEx.printStackTrace();
            } catch (YKKSQLException sqlEx) {
                String msgString = MessageResources.getText(sqlEx.getResourceKey());
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                sqlEx.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
