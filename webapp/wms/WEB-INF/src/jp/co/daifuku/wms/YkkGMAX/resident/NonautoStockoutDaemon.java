package jp.co.daifuku.wms.YkkGMAX.resident;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.DBHandler;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;
import jp.co.daifuku.wms.YkkGMAX.Utils.ConnectionManager;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;
import jp.co.daifuku.wms.base.common.WmsParam;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */
public class NonautoStockoutDaemon {
    public static void main(String[] args) {
        while (true) {
            Connection conn = null;

            try {
                conn = WmsParam.getConnection();

                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR) - 1);
                String date = new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
                String sqlStr = "update fnsyotei set fnsyotei.cmt = '0' where (fnsyotei.cmt like '1%' or fnsyotei.cmt like '2%') and fnsyotei.create_datetime < '" + date + "'";

                DBHandler handler = new DBHandler(conn);
                handler.executeUpdate(sqlStr);

            } catch (YKKSQLException sqlEx) {
                String msgString = MessageResources.getText(sqlEx.getResourceKey());
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                sqlEx.printStackTrace();
            } catch (SQLException dbEx) {
                String msgString = MessageResources.getText("7200003");
                DebugPrinter.print(DebugLevel.ERROR, msgString);
                dbEx.printStackTrace();
            }finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
