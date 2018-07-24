package jp.co.daifuku.wms.YkkGMAX;

import jp.co.daifuku.wms.YkkGMAX.Entities.LocationStorageInfoHead;
import jp.co.daifuku.wms.YkkGMAX.Utils.ASRSInfoCentre;
import jp.co.daifuku.wms.YkkGMAX.Utils.StringUtils;
import jp.co.daifuku.wms.base.common.WmsParam;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */
public class Test {
    public static void main(String[] args) {
        LocationStorageInfoHead head = new LocationStorageInfoHead();
        ArrayList locationStatus = new ArrayList();

        locationStatus.add("实货位");

        head.setLocationStatus(locationStatus);
        ArrayList weightReportFlags = new ArrayList();

        weightReportFlags.add("报告中");
        head.setDepo(" ");
        head.setWeightReportFlag(weightReportFlags);
        head.setLocationNoFrom(StringUtils
                .formatLocationNoFromPageToDB("00-00-00"));


        Connection conn = null;
        try {
            conn = WmsParam.getConnection();

            ASRSInfoCentre centre = new ASRSInfoCentre(conn);

            List locationStorageInfoList = centre.getLocationStorageInfoList(
                    head, 1, 100);

            conn.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }
}
