package jp.co.daifuku.wms.YkkGMAX.Utils;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.wms.YkkGMAX.DBHandler.*;
import jp.co.daifuku.wms.YkkGMAX.Entities.*;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKProcedureException;
import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKSQLException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ASRSInfoCentre {

    private Connection conn = null;

    public ASRSInfoCentre(Connection conn) {
        this.conn = conn;
    }

    public void addBucket(BucketViewEntity entity) throws YKKSQLException {
        String columnList = "bucket_no,packing_weight,height_flag,lastupdate_datetime";
        String sqlString = "INSERT INTO FMBUCKET ("
                + columnList
                + ") VALUES ( "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo())
                + ","
                + String.valueOf(entity.getPackingWeight())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity.getHightFlag())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity
                        .getLastUpdateDateTime()
        ) + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

    }

    public void addFnrange(String terminal, String line1, String line2,
                           ErrorRangeEntity entity) throws YKKSQLException {
        if (StringUtils.IsNullOrEmpty(terminal)) {
            terminal = " ";
        }
        if (StringUtils.IsNullOrEmpty(line1)) {
            line1 = " ";
        }
        if (StringUtils.IsNullOrEmpty(line2)) {
            line2 = " ";
        }
        String columnList = "termno,made_section,made_line,unit_weight_upper, unit_weight_lower, storage_upper, storage_lower, ship_upper, ship_lower";
        String sqlString = "INSERT INTO FNRANGE ("
                + columnList
                + ")VALUES("
                + StringUtils.surroundWithSingleQuotes(terminal)
                + ","
                + StringUtils.surroundWithSingleQuotes(line1)
                + ","
                + StringUtils.surroundWithSingleQuotes(line2)
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getUnitWeightUpper()
                )
        )
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getUnitWeightLower()
                )
        )
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getStorageUpper()
                )
        )
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getStorageLower()
                )
        )
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getShipUpper()
                )
        )
                + ","
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getShipLower()
                )
        ) + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void addItem(ItemViewEntity entity) throws YKKSQLException {
        if (entity.getManageItemFlag().equals(
                DBFlags.ManageItemFlag.WITHOUTMANAGE
        )) {
            String columnList = "zaikey,zkname1,zkname2,zkname3,manage_item_flag,bag_flag";

            String sqlString = " INSERT INTO FMZKEY ("
                    + columnList
                    + ")VALUES("
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName1()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName2()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName3()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getManageItemFlag()
            ) + ","
                    + StringUtils.surroundWithSingleQuotes(entity.getBagFlag())
                    + ")";

            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);
        }
    }

    public boolean addLocation(LocationViewEntity entity, String userid,
                               Message message) throws YKKSQLException, YKKProcedureException {
        if (getBucketNoInFmbucketCount(entity) == 0) {
            message.setMsgResourceKey("7000020");
            return true;
        }
        if (getBucketNoInFnzaikoCountForAdd(entity) != 0) {
            message.setMsgResourceKey("7000021");
            return true;
        }

        String time = StringUtils.getCurrentDate()
                + StringUtils.getCurrentTime();

        if (entity.getManageDivision().equals(DBFlags.ManageItemFlag.INMANAGE)) {
            if (!isFitFnzaikoStatus(entity)
                    && !StringUtils.IsNullOrEmpty(entity.getItemCode())) {
                message.setMsgResourceKey("7000022");
                return true;
            }
            String sqlString = "UPDATE FNAKITANA SET tanaflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    + " WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            );

            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            sqlString = "UPDATE FNZAIKO SET storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                    + " ,bucket_no = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + " ,nyukohiji = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getStockinDate()
                            + entity.getStockinTime()
            )
                    + " ,koshinhiji = "
                    + StringUtils.surroundWithSingleQuotes(time)
                    + " ,memo = "
                    + StringUtils.surroundWithSingleQuotes(entity.getMemo())
                    + " WHERE ticket_no = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getTicketNo());

            handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            sqlString = "INSERT INTO FNLOCAT (syozaikey,sokokbn,bucket_no,konsaisu,zaijyoflg,shikiflg,hikiflg,accessflg,systemid,ailestno) VALUES ("
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + ","
                    + " '1' ,"
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + ","
                    + " 1, '2','0','0','0',"
                    + "(SELECT systemid FROM FNZAIKO WHERE ticket_no = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getTicketNo())
                    + " AND ROWNUM = 1) ,"
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(Integer.parseInt(entity.getLocationNo()
                                                    .substring(2, 4)
                                    ) + 9000
                            )
            ) + ")";

            handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            insertInmanageFnjiseki(entity, getInstockCount(entity), userid,
                    "1", "1"
            );

        } else {
            if (!isFitFmzkeyStatus(entity)
                    && !StringUtils.IsNullOrEmpty(entity.getItemCode())) {
                message.setMsgResourceKey("7000023");
                return true;
            }

            String systemid = generateSystemid();

            String sqlString = "UPDATE FNAKITANA SET tanaflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    + " WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            );

            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            sqlString = "INSERT INTO FNLOCAT (syozaikey,sokokbn,bucket_no,konsaisu,zaijyoflg,shikiflg,hikiflg,accessflg,systemid,ailestno) VALUES ("
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + ","
                    + " '1' ,"
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + ","
                    + " 1, '2','0','0','0',"
                    + StringUtils.surroundWithSingleQuotes(systemid)
                    + " ,"
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(Integer.parseInt(entity.getLocationNo()
                                                    .substring(2, 4)
                                    ) + 9000
                            )
            ) + ")";

            handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            sqlString = "INSERT INTO FNZAIKO (zaikey,color_code,depot_code,zaikosu,nyoteisu,skanosu,sainyusu,nyukohiji,kakuninhiji,koshinhiji,sainyukbn,tsumikbn,kensakbn,systemid,weight_report_complete_flag,storage_place_flag,bucket_no,memo,manage_item_flag,reception_datetime,userid,username,remeasure_flag)VALUES("
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getColorCode()
            )
                    + ","
                    + "'02',"
                    + String.valueOf(entity.getInstockCount())
                    + ","
                    + "'0',"
                    + String.valueOf(entity.getInstockCount())
                    + ","
                    + "'0',"
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getStockinDate()
                            + entity.getStockinTime()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(time)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(time)
                    + ","
                    + "'0',"
                    + "'0',"
                    + "'0',"
                    + StringUtils.surroundWithSingleQuotes(systemid)
                    + ",'2',"
                    + "'0',"
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity.getMemo())
                    + ",'1',"
                    + StringUtils.surroundWithSingleQuotes(time)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(userid)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(getUserName(userid))
                    + ",'0')";
            handler = new DBHandler(conn);
            handler.executeUpdate(sqlString, true);

            insertUnmanageFnjiseki(entity, String.valueOf(entity
                                    .getInstockCount()
                    ), userid, "1", "1"
            );

        }
        return false;

    }

    public void addUser(UserViewEntity entity) throws YKKSQLException {
        String columnList = "userid,password,roleid,pwdchangeinterval,sameuserloginmax,failedloginattempts";
        String sqlString = "INSERT INTO LOGINUSER ("
                + columnList
                + ")VALUES("
                + StringUtils.surroundWithSingleQuotes(entity.getUserID())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity.getPassword())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity
                        .getAuthorization()
        ) + ",'-1','100','-1')";

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        columnList = "userid,username";
        sqlString = "INSERT INTO USERATTRIBUTE (" + columnList + ")VALUES("
                + StringUtils.surroundWithSingleQuotes(entity.getUserID())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity.getUserName())
                + ")";
        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void deleteBucket(BucketViewEntity entity) throws YKKSQLException {
        String sqlString = "DELETE FMBUCKET WHERE bucket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void deleteFnrange(String terminal, String line1, String line2)
            throws YKKSQLException {
        String sqlString = "DELETE FNRANGE WHERE ";

        if (StringUtils.IsNullOrEmpty(terminal)) {
            sqlString += " made_section = "
                    + StringUtils.surroundWithSingleQuotes(line1)
                    + " AND made_line = "
                    + StringUtils.surroundWithSingleQuotes(line2);
        } else {
            sqlString += " termno = "
                    + StringUtils.surroundWithSingleQuotes(terminal);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void deleteItem(ItemViewEntity entity) throws YKKSQLException {
        String sqlString = "DELETE FMZKEY WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode());

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

    }

    public boolean deleteLocation(LocationViewEntity entity, String userid,
                                  Message message) throws YKKSQLException {
        if (entity.getManageDivision().equals(DBFlags.ManageItemFlag.INMANAGE)) {
            insertInmanageFnjiseki(entity, String.valueOf(entity
                                    .getInstockCount()
                    ), userid, "2", "2"
            );
        } else {
            insertUnmanageFnjiseki(entity, String.valueOf(entity
                                    .getInstockCount()
                    ), userid, "2", "2"
            );
        }
        String sqlString = "";
        if (entity.getManageDivision().equals(DBFlags.ManageItemFlag.INMANAGE)) {
            sqlString = "UPDATE FNZAIKO SET storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                    + " ,bucket_no = ' ' WHERE ticket_no = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getTicketNo())
                    + " AND manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);
        } else {
            sqlString = "DELETE FNZAIKO WHERE systemid = (SELECT FNZAIKO.systemid FROM FNZAIKO,FNLOCAT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            ) + ")";
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        sqlString = "DELETE FNLOCAT WHERE syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo());
        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        sqlString = "UPDATE FNAKITANA SET tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                + " WHERE syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo());
        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
        return false;
    }

    public int deleteRetrieval(String retrievalPlankey, long retrievalQty,
                               long retrievalAllocQty, long necessaryQty) throws YKKSQLException {
        String sqlString = "UPDATE FNSYOTEI SET fnsyotei.proc_flag = '1' , retrieval_alloc_qty = retrieval_qty WHERE retrieval_plankey = "
                + StringUtils.surroundWithSingleQuotes(retrievalPlankey)
                + " AND retrieval_qty = "
                + String.valueOf(retrievalQty)
                + " AND retrieval_alloc_qty = "
                + String.valueOf(retrievalAllocQty)
                + " AND necessary_qty = "
                + String.valueOf(necessaryQty);
        DBHandler handler = new DBHandler(conn);
        return handler.executeUpdate(sqlString, true);
    }

    public void deleteUser(UserViewEntity entity) throws YKKSQLException {
        String sqlString = "DELETE LOGINUSER WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(entity.getUserID());

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        sqlString = "DELETE USERATTRIBUTE WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(entity.getUserID());
        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void forbidLocationSetting(String bankNo, String fromBayNo,
                                      String toBayNo, String fromLevelNo, String toLevelNo,
                                      boolean rangeSet, boolean isForbid) throws YKKSQLException {
        String sqlString = "UPDATE FNAKITANA SET tanaflg = ";
        if (isForbid) {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
//					+ " ,accessflg = "
//					+ StringUtils
//							.surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                    + " WHERE tanaflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT);
        } else {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
//					+ " ,accessflg = "
//					+ StringUtils
//							.surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                    + " WHERE tanaflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT);
        }
        sqlString += " AND bankno = "
                + StringUtils.surroundWithSingleQuotes(bankNo)
                + " AND accessflg <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE);
        if (rangeSet) {
            sqlString += " AND bayno BETWEEN "
                    + StringUtils.surroundWithSingleQuotes(fromBayNo) + " AND "
                    + StringUtils.surroundWithSingleQuotes(toBayNo)
                    + " AND levelno BETWEEN "
                    + StringUtils.surroundWithSingleQuotes(fromLevelNo)
                    + " AND " + StringUtils.surroundWithSingleQuotes(toLevelNo);
        } else {
            sqlString += " AND bayno = "
                    + StringUtils.surroundWithSingleQuotes(fromBayNo)
                    + " AND levelno = "
                    + StringUtils.surroundWithSingleQuotes(fromLevelNo);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void forceStopController(String controllerNo) throws YKKSQLException {
        String sqlString = "UPDATE FNAREA SET systemflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Systemflg.OFFLINE)
                + " WHERE arcno = "
                + StringUtils.surroundWithSingleQuotes(controllerNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        sqlString = "UPDATE   fnstation SET   into_flag = '1'  WHERE   stno IN ('1202', '2206', '2209', '3202', '3205')";

        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

    }

    public String generateLabelKey() throws YKKSQLException,
            YKKProcedureException {
        DBHandler handler = new DBHandler(conn);
        String schedule = "";

        List inputPairs = new ArrayList();
        inputPairs.add(new InputPair(1, schedule));

        List outputPairs = new ArrayList();
        outputPairs.add(new OutputPair(1, schedule));

        ResultPairList list = handler.callProcedure("generate_label_key",
                inputPairs, outputPairs
        );
        String labelKey = (String) list.getValueByIndex(1);
        labelKey = labelKey.trim();
        return labelKey;
    }

    public String generateScheduleNo() throws YKKSQLException,
            YKKProcedureException {
        DBHandler handler = new DBHandler(conn);
        String schedule = "";

        List inputPairs = new ArrayList();
        inputPairs.add(new InputPair(1, schedule));

        List outputPairs = new ArrayList();
        outputPairs.add(new OutputPair(1, schedule));

        ResultPairList list = handler.callProcedure("generate_schedule_no",
                inputPairs, outputPairs
        );
        String scheduleNo = (String) list.getValueByIndex(1);
        scheduleNo = scheduleNo.trim();
        return scheduleNo;

    }

    public String generateSystemid() throws YKKSQLException,
            YKKProcedureException {
        DBHandler handler = new DBHandler(conn);
        String schedule = "";

        List inputPairs = new ArrayList();
        inputPairs.add(new InputPair(1, schedule));

        List outputPairs = new ArrayList();
        outputPairs.add(new OutputPair(1, schedule));

        ResultPairList list = handler.callProcedure("generate_system_id",
                inputPairs, outputPairs
        );
        String systemid = (String) list.getValueByIndex(1);
        systemid = systemid.trim();
        return systemid;

    }

    // private RetrievalStationEntity getStation(String stno)
    // throws YKKSQLException
    // {
    // String columnList = "pick_stno,unit_stno";
    //
    // String queryString = "SELECT " + columnList
    // + " FROM FNRETRIEVAL_ST WHERE retrieval_station = "
    // + StringUtils.surroundWithSingleQuotes(stno);
    //
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString);
    // List returnList = new ArrayList();
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // RetrievalStationEntity entity = new RetrievalStationEntity();
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // entity.setPickStation(row.getValueByColumnName("pick_stno"));
    // entity.setUnitStation(row.getValueByColumnName("unit_stno"));
    //
    // returnList.add(entity);
    // }
    //
    // if (returnList.size() > 0)
    // {
    // return (RetrievalStationEntity) returnList.get(0);
    // }
    // else
    // {
    // return null;
    // }
    // }

    public List getBank() throws YKKSQLException {
        String columnList = "bankno";
        String queryString = "SELECT " + columnList
                + " FROM FNAKITANA GROUP BY " + columnList + " ORDER BY bankno";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("bankno"));
        }
        return returnList;
    }

    public List getBucketMasterList(String bucketNo) throws YKKSQLException {
        String columnList = "bucket_no,packing_weight,height_flag,lastupdate_datetime";

        String queryString = "SELECT " + columnList
                + " FROM FMBUCKET WHERE bucket_no = "
                + StringUtils.surroundWithSingleQuotes(bucketNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            BucketViewEntity entity = new BucketViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            try {
                entity.setPackingWeight(new BigDecimal(row
                                .getValueByColumnName("packing_weight")
                        )
                );
            } catch (Exception ex) {
                entity.setPackingWeight(new BigDecimal(0));
            }
            entity.setHightFlag(row.getValueByColumnName("height_flag"));
            entity.setLastUpdateDateTime(row
                            .getValueByColumnName("lastupdate_datetime")
            );

            returnList.add(entity);
        }

        return returnList;

    }

    private int getBucketNoInFmbucketCount(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FMBUCKET WHERE bucket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    private int getBucketNoInFnzaikoCountForAdd(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO,FNLOCAT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND FNZAIKO.bucket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    private int getBucketNoInFnzaikoCountForModi(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO,FNLOCAT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND FNZAIKO.bucket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo())
                + " AND FNLOCAT.syozaikey <> "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public int getBucketViewCount(String bucketNo, String looseSearchMode)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FMBUCKET WHERE bucket_no LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(bucketNo
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(bucketNo)
            );
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getBucketViewList(String bucketNo, String looseSearchMode,
                                  int beginningPos, int count) throws YKKSQLException {
        String columnList = "bucket_no";

        String queryString = "SELECT " + columnList
                + " FROM FMBUCKET WHERE bucket_no LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(bucketNo
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(bucketNo)
            );
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            BucketViewEntity entity = new BucketViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setBucketNo(row.getValueByColumnName("bucket_no"));

            returnList.add(entity);
        }

        return returnList;
    }

    public int getEmptyLocationViewCount(List locationStatus,
                                         String manageItemFlag) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getEmptyLocationViewList(List locationStatus,
                                         String manageItemFlag, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "syozaikey";
        String queryString = "SELECT "
                + columnList
                + " FROM FNAKITANA WHERE tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            LocationViewEntity entity = new LocationViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setLocationNo(row.getValueByColumnName("syozaikey"));

            returnList.add(entity);
        }
        return returnList;
    }

    private int getEnableToStockoutCount(String itemCode, String colorCode)
            throws YKKSQLException {
        String queryString = "SELECT SUM(skanosu) FROM (SELECT fnzaiko.systemid, fnzaiko.skanosu, fnzaiko.ticket_no, fnzaiko.nyukohiji FROM FNZAIKO,FNLOCAT,FNUNIT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND skanosu > '0' "
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) "
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(colorCode)
                + " group by fnzaiko.systemid, fnzaiko.skanosu, fnzaiko.ticket_no, fnzaiko.nyukohiji ) WHERE 0 = (SELECT COUNT(*) FROM FNFORBIDRETRIEVAL WHERE FNFORBIDRETRIEVAL.zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " AND (FNFORBIDRETRIEVAL.color_code = "
                + StringUtils.surroundWithSingleQuotes(colorCode)
                + " OR TRIM(FNFORBIDRETRIEVAL.color_code) IS NULL) AND FNFORBIDRETRIEVAL.from_ticketno <= ticket_no AND FNFORBIDRETRIEVAL.to_ticketno >= ticket_no AND FNFORBIDRETRIEVAL.from_stock_datetime <= nyukohiji AND FNFORBIDRETRIEVAL.to_stock_datetime >= nyukohiji)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        int enableToStockoutCount = 0;
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                enableToStockoutCount = Integer.parseInt(row
                                .getValueByColumnName("SUM(skanosu)")
                );
            } catch (Exception ex) {
                enableToStockoutCount = 0;
            }
        }
        return enableToStockoutCount;
    }

    public List getErrorMessageDivision() throws YKKSQLException {
        String columnList = "host_table";
        String queryString = "SELECT " + columnList
                + " FROM FNHOST_ERR GROUP BY " + columnList;

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("host_table"));
        }
        return returnList;
    }

    public int getErrorMessageInfoCount(ErrorMessageInfoHead head)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHOST_ERR WHERE register_date || register_time BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getErrorMessageInfoListSQL(ErrorMessageInfoHead head) {
        String columnList = "register_date,register_time,host_table,err_data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHOST_ERR WHERE register_date || register_time BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY register_date DESC,register_time DESC,host_table";

        return queryString;
    }

    public List getErrorMessageInfoList(ErrorMessageInfoHead head)
            throws YKKSQLException {
        String columnList = "register_date,register_time,host_table,err_data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHOST_ERR WHERE register_date || register_time BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY register_date DESC,register_time DESC,host_table";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ErrorMessageInfoEntity entity = new ErrorMessageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTime(row.getValueByColumnName("register_date")
                            + row.getValueByColumnName("register_time")
            );
            entity.setMessageType(row.getValueByColumnName("host_table"));
            entity.setErrorMessage(row.getValueByColumnName("err_data"));

            returnList.add(entity);
        }
        return returnList;
    }

    public List getErrorMessageInfoList(ErrorMessageInfoHead head,
                                        int beginningPos, int count) throws YKKSQLException {
        String columnList = "register_date,register_time,host_table,err_data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHOST_ERR WHERE register_date || register_time BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY register_date DESC,register_time DESC,host_table";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ErrorMessageInfoEntity entity = new ErrorMessageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTime(row.getValueByColumnName("register_date")
                            + row.getValueByColumnName("register_time")
            );
            entity.setMessageType(row.getValueByColumnName("host_table"));
            entity.setErrorMessage(row.getValueByColumnName("err_data"));

            returnList.add(entity);
        }
        return returnList;
    }

    public ErrorRangeEntity getErrorRange(String terminalNo, String section,
                                          String line, boolean isStockin) throws YKKSQLException {
        String columnList = "unit_weight_upper,unit_weight_lower,storage_upper,storage_lower,ship_upper,ship_lower";
        String queryString = "SELECT " + columnList + " FROM FNRANGE WHERE ";
        if (isStockin) {
            queryString += " made_section = "
                    + StringUtils.surroundWithSingleQuotes(section)
                    + " AND made_line = "
                    + StringUtils.surroundWithSingleQuotes(line);
        } else {
            queryString += " termno = "
                    + StringUtils.surroundWithSingleQuotes(terminalNo);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ErrorRangeEntity entity = new ErrorRangeEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setUnitWeightUpper(new BigDecimal(row
                                .getValueByColumnName("unit_weight_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setUnitWeightUpper(new BigDecimal(0));
            }
            try {
                entity.setUnitWeightLower(new BigDecimal(row
                                .getValueByColumnName("unit_weight_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setUnitWeightLower(new BigDecimal(0));
            }
            try {
                entity.setStorageUpper(new BigDecimal(row
                                .getValueByColumnName("storage_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setStorageUpper(new BigDecimal(0));
            }
            try {
                entity.setStorageLower(new BigDecimal(row
                                .getValueByColumnName("storage_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setStorageLower(new BigDecimal(0));
            }
            try {
                entity.setShipUpper(new BigDecimal(row
                                .getValueByColumnName("ship_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setShipUpper(new BigDecimal(0));
            }
            try {
                entity.setShipLower(new BigDecimal(row
                                .getValueByColumnName("ship_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setShipLower(new BigDecimal(0));
            }

            returnList.add(entity);
        }

        if (returnList.size() > 0) {
            return (ErrorRangeEntity) returnList.get(0);
        } else {
            return null;
        }
    }

    public int getErrorRangeCount(String terminalNo, String section,
                                  String line, boolean isStockin) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNRANGE WHERE ";
        if (isStockin) {
            queryString += " made_section = "
                    + StringUtils.surroundWithSingleQuotes(section)
                    + " AND made_line = "
                    + StringUtils.surroundWithSingleQuotes(line);
        } else {
            queryString += " termno = "
                    + StringUtils.surroundWithSingleQuotes(terminalNo);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public int getExceptionStockoutDetailCount(
            ExceptionStockoutEntity exceptionStockoutEntity)
            throws YKKSQLException {
        String columnList = "FMZKEY.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNLOCAT.syozaikey,FNZAIKO.ticket_no,FNZAIKO.made_section,FNZAIKO.made_line,FNZAIKO.reception_datetime,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid";

        String queryString = "SELECT COUNT(*) FROM (SELECT * FROM FMZKEY,FNZAIKO,FNLOCAT,FNUNIT WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno ";
        if (exceptionStockoutEntity.getStockoutMode() == 1) {
            queryString += " AND FNZAIKO.reception_datetime <= "
                    + StringUtils
                    .surroundWithSingleQuotes(exceptionStockoutEntity
                                    .getStockinDate()
                                    + exceptionStockoutEntity.getStockinTime()
                    );

        } else if (exceptionStockoutEntity.getStockoutMode() == 2) {
            queryString += " AND FNZAIKO.zaikosu <= "
                    + StringUtils
                    .surroundWithSingleQuotes(String
                                    .valueOf(exceptionStockoutEntity
                                                    .getInstockCount()
                                    )
                    );

            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                            .getItemCode()
            )) {
                queryString += " AND FNZAIKO.zaikey = "
                        + StringUtils
                        .surroundWithSingleQuotes(exceptionStockoutEntity
                                        .getItemCode()
                        );
            }
        } else if (exceptionStockoutEntity.getStockoutMode() == 3) {
            // queryString += " AND FNLOCAT.zaijyoflg NOT IN ( "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
            // + " ) ";
            queryString += " AND FNZAIKO.weight_report_complete_flag = '2'";
            queryString += " AND FNLOCAT.zaijyoflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK);
            if (!exceptionStockoutEntity.isSearchItemCode()) {
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getLocationNoFrom()
                )) {
                    queryString += " AND FNLOCAT.syozaikey >= "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getLocationNoFrom()
                            );
                }
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getLocationNoTo()
                )) {
                    queryString += " AND FNLOCAT.syozaikey <= "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getLocationNoTo()
                            );

                }
            } else {
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getItemCode()
                )) {
                    queryString += " AND FNZAIKO.zaikey = "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getItemCode()
                            );
                }
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getColorCode())) {
                    queryString += " AND FNZAIKO.color_code = "
                            + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getColorCode());
                }
            }
        } else if (exceptionStockoutEntity.getStockoutMode() == 4) {
            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getItemCode())) {
                queryString += " AND FNZAIKO.zaikey = "
                        + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getItemCode());
            }
            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getColorCode());
            }
        } else {
            // queryString += " AND FNLOCAT.zaijyoflg NOT IN ( "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
            // + " ) ";
            queryString += " AND FNLOCAT.zaijyoflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK);
            queryString += " AND FNLOCAT.syozaikey ";
            if (exceptionStockoutEntity.isAfterThisLocation()) {

                queryString += " >= ";
            } else {
                queryString += " = ";
            }
            queryString += StringUtils
                    .surroundWithSingleQuotes(exceptionStockoutEntity
                                    .getLocationNo()
                    );
        }
        queryString += " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.hikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.HikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNUNIT.unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND FNZAIKO.manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND FNZAIKO.skanosu > 0 ";

        queryString += " GROUP BY " + columnList + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getExceptionStockoutDetailList(
            ExceptionStockoutEntity exceptionStockoutEntity, int beginningPos,
            int count) throws YKKSQLException {
        String columnList = "FMZKEY.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNLOCAT.syozaikey,FNZAIKO.ticket_no,FNZAIKO.made_section,FNZAIKO.made_line,FNZAIKO.nyukohiji,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid,FNZAIKO.sainyukbn";

        String queryString = "SELECT "
                + columnList
                + " FROM FMZKEY,FNZAIKO,FNLOCAT,FNUNIT WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno ";
        if (exceptionStockoutEntity.getStockoutMode() == 1) {
            queryString += " AND FNZAIKO.reception_datetime <= "
                    + StringUtils
                    .surroundWithSingleQuotes(exceptionStockoutEntity
                                    .getStockinDate()
                                    + exceptionStockoutEntity.getStockinTime()
                    );

        } else if (exceptionStockoutEntity.getStockoutMode() == 2) {
            queryString += " AND FNZAIKO.zaikosu <= "
                    + StringUtils
                    .surroundWithSingleQuotes(String
                                    .valueOf(exceptionStockoutEntity
                                                    .getInstockCount()
                                    )
                    );

            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                            .getItemCode()
            )) {
                queryString += " AND FNZAIKO.zaikey = "
                        + StringUtils
                        .surroundWithSingleQuotes(exceptionStockoutEntity
                                        .getItemCode()
                        );
            }
        } else if (exceptionStockoutEntity.getStockoutMode() == 3) {
            // queryString += " AND FNLOCAT.zaijyoflg NOT IN ( "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
            // + " ) ";
            queryString += " AND FNZAIKO.weight_report_complete_flag = '2'";
            queryString += " AND FNLOCAT.zaijyoflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK);
            if (!exceptionStockoutEntity.isSearchItemCode()) {
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getLocationNoFrom()
                )) {
                    queryString += " AND FNLOCAT.syozaikey >= "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getLocationNoFrom()
                            );
                }
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getLocationNoTo()
                )) {
                    queryString += " AND FNLOCAT.syozaikey <= "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getLocationNoTo()
                            );

                }
            } else {
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity
                                .getItemCode()
                )) {
                    queryString += " AND FNZAIKO.zaikey = "
                            + StringUtils
                            .surroundWithSingleQuotes(exceptionStockoutEntity
                                            .getItemCode()
                            );
                }
                if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getColorCode())) {
                    queryString += " AND FNZAIKO.color_code = "
                            + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getColorCode());
                }
            }
        } else if (exceptionStockoutEntity.getStockoutMode() == 4) {
            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getItemCode())) {
                queryString += " AND FNZAIKO.zaikey = "
                        + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getItemCode());
            }
            if (!StringUtils.IsNullOrEmpty(exceptionStockoutEntity.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(exceptionStockoutEntity.getColorCode());
            }
        } else {
            // queryString += " AND FNLOCAT.zaijyoflg NOT IN ( "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
            // + " , "
            // + StringUtils
            // .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
            // + " ) ";
            queryString += " AND FNLOCAT.zaijyoflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK);
            queryString += " AND FNLOCAT.syozaikey ";
            if (exceptionStockoutEntity.isAfterThisLocation()) {

                queryString += " >= ";
            } else {
                queryString += " = ";
            }
            queryString += StringUtils
                    .surroundWithSingleQuotes(exceptionStockoutEntity
                                    .getLocationNo()
                    );
        }
        queryString += " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.hikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.HikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNUNIT.unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND FNZAIKO.manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND FNZAIKO.skanosu > 0 ";
        queryString += " GROUP BY " + columnList + " ORDER BY sainyukbn desc,";

        if (exceptionStockoutEntity.getOrderMode() == 1) {
            // queryString += " ORDER BY syozaikey";
            queryString += " syozaikey";
        } else if (exceptionStockoutEntity.getOrderMode() == 2) {
            // queryString += " BY zaikey, color_code";
            queryString += "  zaikey, color_code";
        } else if (exceptionStockoutEntity.getOrderMode() == 3) {
            // queryString += " ORDER BY nyukohiji";
            queryString += " nyukohiji";
        } else {
            // queryString += " ORDER BY made_section,made_line";
            queryString += " made_section,made_line";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ExceptionStockoutDetailEntity entity = new ExceptionStockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setLine1(row.getValueByColumnName("made_section"));
            entity.setLine2(row.getValueByColumnName("made_line"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            entity.setMemo(exceptionStockoutEntity.getMemo());

            returnList.add(entity);
        }

        return returnList;
    }

    public int getExternalStockoutDetailCount(String itemNo, String color)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM (SELECT fnzaiko.systemid, fnzaiko.ticket_no,fnzaiko.nyukohiji FROM FNLOCAT,FNZAIKO,FNUNIT WHERE FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) " + " AND FNZAIKO.skanosu > 0 ";
        queryString += " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo);
        queryString += " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(color);
        queryString += " group by fnzaiko.systemid, fnzaiko.ticket_no, fnzaiko.nyukohiji) WHERE 0 = (SELECT COUNT(*) FROM FNFORBIDRETRIEVAL WHERE FNFORBIDRETRIEVAL.zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo)
                + " AND (FNFORBIDRETRIEVAL.color_code = "
                + StringUtils.surroundWithSingleQuotes(color)
                + " OR TRIM(FNFORBIDRETRIEVAL.color_code) IS NULL) AND FNFORBIDRETRIEVAL.from_ticketno <= ticket_no AND FNFORBIDRETRIEVAL.to_ticketno >= ticket_no AND FNFORBIDRETRIEVAL.from_stock_datetime <= nyukohiji AND FNFORBIDRETRIEVAL.to_stock_datetime >= nyukohiji)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }
        return Integer.parseInt(count);
    }

    public List getExternalStockoutDetailList(String itemNo, String color,
                                              int beginningPos, int count) throws YKKSQLException {
        String columnList = "nyukohiji,syozaikey,hikiflg,skanosu,memo,ticket_no,FNZAIKO.systemid,FNZAIKO.sainyukbn";

        String queryString = "SELECT * FROM (SELECT "
                + columnList
                + " FROM FNLOCAT,FNZAIKO,FNUNIT WHERE FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) " + " AND FNZAIKO.skanosu > 0 ";
        queryString += " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo);
        queryString += " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(color);
        queryString += " GROUP BY "
                + columnList
                + ") WHERE 0 = (SELECT COUNT(*) FROM FNFORBIDRETRIEVAL WHERE FNFORBIDRETRIEVAL.zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo)
                + " AND (FNFORBIDRETRIEVAL.color_code = "
                + StringUtils.surroundWithSingleQuotes(color)
                + " OR TRIM(FNFORBIDRETRIEVAL.color_code) IS NULL) AND FNFORBIDRETRIEVAL.from_ticketno <= ticket_no AND FNFORBIDRETRIEVAL.to_ticketno >= ticket_no AND FNFORBIDRETRIEVAL.from_stock_datetime <= nyukohiji AND FNFORBIDRETRIEVAL.to_stock_datetime >= nyukohiji)";
        queryString += " ORDER BY memo DESC, sainyukbn DESC, nyukohiji ASC";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ExternalStockoutDetailEntity entity = new ExternalStockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStockinDate(row.getValueByColumnName("nyukohiji"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setStatus(StringUtils.formatHikiFlg(row
                                    .getValueByColumnName("hikiflg")
                    )
            );
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setEnableToStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("skanosu")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }
            entity.setRemark(row.getValueByColumnName("memo"));

            returnList.add(entity);
        }
        return returnList;
    }

    public List getExternalStockoutList(String no, int status)
            throws YKKSQLException {
        String columnList = "FNSYOTEI.retrieval_no,FNSYOTEI.retrieval_plankey,FNSYOTEI.customer_code,FNSYOTEI.zaikey,FNSYOTEI.color_code,FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty,FNSYOTEI.start_date, FNSYOTEI.start_timing_flag, FNSYOTEI.complete_date, FNSYOTEI.complete_timing_flag,FNSYOTEI.necessary_qty,FNSYOTEI.allocation_qty,FNSYOTEI.retrieval_qty	,FNSYOTEI.retrieval_alloc_qty";
        String queryString = "SELECT " + columnList + " FROM FNSYOTEI ";
        queryString += " WHERE retrieval_station IN('23','25','26') AND proc_flag =  "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        if (status == 1 && !no.equals("99999999999999")) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(no);
        } else if (status == 2) {
            queryString += " AND order_no = "
                    + StringUtils.surroundWithSingleQuotes(no);
        }
        queryString += " GROUP BY " + columnList;

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ExternalStockoutEntity entity = new ExternalStockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setExternalCode(row.getValueByColumnName("customer_code"));
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setColor(row.getValueByColumnName("color_code"));
            try {
                entity
                        .setStockoutCount(Integer
                                        .parseInt(row
                                                        .getValueByColumnName("FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty")
                                        )
                        );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }
            entity.setEnableToStockoutCount(getEnableToStockoutCount(row
                                    .getValueByColumnName("zaikey"), row
                                    .getValueByColumnName("color_code")
                    )
            );
            entity.setMemo(getHasMemo(row.getValueByColumnName("zaikey"), row
                                    .getValueByColumnName("color_code")
                    )
            );
            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setWhenThisWorkFinishInPlan(row
                            .getValueByColumnName("complete_date")
            );
            entity.setWhenThisWorkFinishInPlanTiming(row
                            .getValueByColumnName("complete_timing_flag")
            );
            try {
                entity.setStockoutNecessaryQty(Integer.parseInt(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Integer.parseInt(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }

            setItemName(entity);
            setTotlaCountInstock(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public int getForbidItemViewCount(String itemCode, String looseSearchMode)
            throws YKKSQLException {
        String columnList = "zaikey,from_ticketno,to_ticketno,color_code,from_stock_datetime,to_stock_datetime";
        String queryString = "SELECT COUNT(*) FROM (SELECT * FROM FNFORBIDRETRIEVAL WHERE zaikey LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(itemCode
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemCode)
            );
        }
        queryString += " GROUP BY " + columnList + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getForbidItemViewList(String itemCode, String looseSearchMode,
                                      int beginningPos, int count) throws YKKSQLException {
        String columnList = "zaikey,from_ticketno,to_ticketno,color_code,from_stock_datetime,to_stock_datetime";
        String queryString = "SELECT " + columnList
                + " FROM FNFORBIDRETRIEVAL WHERE zaikey LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(itemCode
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemCode)
            );
        }
        queryString += " GROUP BY " + columnList;

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ForbidItemViewEntity entity = new ForbidItemViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setTicketNoFrom(row.getValueByColumnName("from_ticketno"));
            entity.setTicketNoTo(row.getValueByColumnName("to_ticketno"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setStockinDateTimeFrom(row
                            .getValueByColumnName("from_stock_datetime")
            );
            entity.setStockinDateTimeTo(row
                            .getValueByColumnName("to_stock_datetime")
            );

            returnList.add(entity);
        }

        return returnList;
    }

    public List getForcibleRemoveTicketNoLowList(String ticketNo)
            throws YKKSQLException {
        String columnList = "FNZAIKO.ticket_no,FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid";

        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO WHERE FNZAIKO.weight_report_complete_flag IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + ")"
                + " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.ticket_no = "
                + StringUtils.surroundWithSingleQuotes(ticketNo);

        queryString += " ORDER BY reception_datetime";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ForcibleRemoveTicketNoEntity entity = new ForcibleRemoveTicketNoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }

        return returnList;
    }

    public int getForcibleRemoveTicketNoUpCount(String itemCode,
                                                String colorCode) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO WHERE FNZAIKO.weight_report_complete_flag IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + ")"
                + " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.zaikey LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(itemCode)
        )
                + " AND FNZAIKO.color_code LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(colorCode)
        );

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getForcibleRemoveTicketNoUpList(String itemCode,
                                                String colorCode, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNZAIKO.reception_datetime,FNZAIKO.ticket_no,FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid";

        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO WHERE FNZAIKO.weight_report_complete_flag IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + ")"
                + " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.zaikey LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(itemCode)
        )
                + " AND FNZAIKO.color_code LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(colorCode)
        );

        queryString += " ORDER BY reception_datetime";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ForcibleRemoveTicketNoEntity entity = new ForcibleRemoveTicketNoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setReceptionDateTime(row
                            .getValueByColumnName("reception_datetime")
            );
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }

        return returnList;
    }

    private String getHasMemo(String itemCode, String colorCode)
            throws YKKSQLException {
        String HAS_MEMO = "有";
        String columnList = "memo";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(colorCode)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils.IsNullOrEmpty(row.getValueByColumnName("memo"))) {
                return HAS_MEMO;
            }
        }
        return "";
    }

    private String getInstockCount(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT zaikosu FROM FNZAIKO WHERE ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("zaikosu"));
        }

        if (returnList.size() > 0) {
            return (String) returnList.get(0);
        } else {
            return "";
        }
    }

    private String getInstockCount(String locationNo) throws YKKSQLException {
        String queryString = "SELECT zaikosu FROM FNZAIKO,FNLOCAT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND syozaikey = "
                + StringUtils.surroundWithSingleQuotes(locationNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("zaikosu"));
        }

        if (returnList.size() > 0) {
            return (String) returnList.get(0);
        } else {
            return "";
        }
    }

    public int getIOHistoryInfoCount(IOHistoryInfoHead head)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getIOHistoryInfoListSQL(IOHistoryInfoHead head) {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.nyusyukbn, FNJISEKI.nyusyustno, FNJISEKI.sagyokbn,FNJISEKI.sakukbn,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.zkname2,FNJISEKI.zkname3,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.syozaikey,FNJISEKI.nyusyusu,FNJISEKI.retrieval_no,FNJISEKI.userid,FNJISEKI.username,FNJISEKI.startstno,FNJISEKI.endstno,fnjiseki.order_no,fnjiseki.measure_unit_weight,start_date,fnjiseki.section ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,sakuseihiji";
        } else {
            queryString += "sakuseihiji";
        }

        return queryString;
    }

    public List getIOHistoryInfoList2(IOHistoryInfoHead head)
            throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.nyusyukbn, FNJISEKI.nyusyustno, FNJISEKI.sagyokbn,FNJISEKI.sakukbn,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.zkname2,FNJISEKI.zkname3,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.syozaikey,FNJISEKI.nyusyusu,FNJISEKI.retrieval_no,FNJISEKI.userid,FNJISEKI.username,FNJISEKI.startstno,FNJISEKI.endstno,fnjiseki.order_no,fnjiseki.measure_unit_weight";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,sakuseihiji";
        } else {
            queryString += "sakuseihiji";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistoryInfoEntity entity = new IOHistoryInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setTime(row.getValueByColumnName("sakuseihiji"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setOrderNo(row.getValueByColumnName("order_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setStNo(row.getValueByColumnName("nyusyustno"));
            entity.setUserId(row.getValueByColumnName("userid"));
            entity.setUserName(row.getValueByColumnName("username"));
            entity.setIncreaseDecreaseFlag(row.getValueByColumnName("sakukbn"));
            entity.setStartStation(row.getValueByColumnName("startstno"));
            entity.setEndStation(row.getValueByColumnName("endstno"));

            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
            if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.CYCLECOUNT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.MAINTENANCE
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.NOT_EQUAL
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                                + DBFlags.Sakukbn.parseDBToPage(row
                                        .getValueByColumnName("sakukbn")
                        )
                );
            } else if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.PICKOUT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.FORBID_IN
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.ALLOW_IN
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                );
            } else {
                entity.setWorkType(DBFlags.SagyokbnAndNyusyukbn.parseDBToPage(
                                row.getValueByColumnName("sagyokbn"), row
                                        .getValueByColumnName("nyusyukbn")
                        )
                );
                if (row.getValueByColumnName("nyusyukbn").equals(
                        DBFlags.Nyusyukbn.STOCKIN
                )) {
                    entity.setStNo(row.getValueByColumnName("startstno"));
                } else {
                    entity.setStNo(row.getValueByColumnName("endstno"));
                }
            }
            returnList.add(entity);
        }
        return returnList;
    }

    public List getIOHistoryInfoList(IOHistoryInfoHead head)
            throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.nyusyukbn, FNJISEKI.nyusyustno, FNJISEKI.sagyokbn,FNJISEKI.sakukbn,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.zkname2,FNJISEKI.zkname3,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.syozaikey,FNJISEKI.nyusyusu,FNJISEKI.retrieval_no,FNJISEKI.userid,FNJISEKI.username,FNJISEKI.startstno,FNJISEKI.endstno,fnjiseki.order_no,fnjiseki.measure_unit_weight";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,sakuseihiji";
        } else {
            queryString += "sakuseihiji";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistoryInfoEntity entity = new IOHistoryInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setTime(row.getValueByColumnName("sakuseihiji"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setOrderNo(row.getValueByColumnName("order_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setStNo(row.getValueByColumnName("nyusyustno"));
            entity.setUserId(row.getValueByColumnName("userid"));
            entity.setUserName(row.getValueByColumnName("username"));
            entity.setIncreaseDecreaseFlag(row.getValueByColumnName("sakukbn"));
            entity.setStartStation(row.getValueByColumnName("startstno"));
            entity.setEndStation(row.getValueByColumnName("endstno"));

            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
            if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.CYCLECOUNT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.MAINTENANCE
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.NOT_EQUAL
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                                + DBFlags.Sakukbn.parseDBToPage(row
                                        .getValueByColumnName("sakukbn")
                        )
                );
            } else if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.PICKOUT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.FORBID_IN
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.ALLOW_IN
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                );
            } else {
                entity.setWorkType(DBFlags.SagyokbnAndNyusyukbn.parseDBToPage(
                                row.getValueByColumnName("sagyokbn"), row
                                        .getValueByColumnName("nyusyukbn")
                        )
                );
                if (row.getValueByColumnName("nyusyukbn").equals(
                        DBFlags.Nyusyukbn.STOCKIN
                )) {
                    entity.setStNo(row.getValueByColumnName("startstno"));
                } else {
                    entity.setStNo(row.getValueByColumnName("endstno"));
                }
            }
            returnList.add(entity);
        }
        return returnList;
    }

    public List getIOHistoryInfoList(IOHistoryInfoHead head, int beginningPos,
                                     int count) throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.nyusyukbn,FNJISEKI.nyusyustno,FNJISEKI.sagyokbn,FNJISEKI.sakukbn,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.zkname2,FNJISEKI.zkname3,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.syozaikey,FNJISEKI.nyusyusu,FNJISEKI.retrieval_no,FNJISEKI.userid,FNJISEKI.username,FNJISEKI.startstno,FNJISEKI.endstno,fnjiseki.order_no,fnjiseki.measure_unit_weight,fnjiseki.start_date,fnjiseki.section ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,sakuseihiji";
        } else {
            queryString += "sakuseihiji";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistoryInfoEntity entity = new IOHistoryInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setTime(row.getValueByColumnName("sakuseihiji"));
            entity.setProductStartDate(row.getValueByColumnName("start_date"));
            entity.setLine1(row.getValueByColumnName("section"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setOrderNo(row.getValueByColumnName("order_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setStNo(row.getValueByColumnName("nyusyustno"));
            entity.setUserId(row.getValueByColumnName("userid"));
            entity.setUserName(row.getValueByColumnName("username"));
            entity.setIncreaseDecreaseFlag(row.getValueByColumnName("sakukbn"));
            entity.setStartStation(row.getValueByColumnName("startstno"));
            entity.setEndStation(row.getValueByColumnName("endstno"));
            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
            if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.CYCLECOUNT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.MAINTENANCE
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.NOT_EQUAL
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                                + DBFlags.Sakukbn.parseDBToPage(row
                                        .getValueByColumnName("sakukbn")
                        )
                );
            } else if (row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.PICKOUT
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.FORBID_IN
            )
                    || row.getValueByColumnName("sagyokbn").equals(
                    DBFlags.Sagyokbn.ALLOW_IN
            )) {
                entity.setWorkType(DBFlags.Sagyokbn.parseDBToPage(row
                                        .getValueByColumnName("sagyokbn")
                        )
                );
            } else {
                entity.setWorkType(DBFlags.SagyokbnAndNyusyukbn.parseDBToPage(
                                row.getValueByColumnName("sagyokbn"), row
                                        .getValueByColumnName("nyusyukbn")
                        )
                );
                if (row.getValueByColumnName("nyusyukbn").equals(
                        DBFlags.Nyusyukbn.STOCKIN
                )) {
                    entity.setStNo(row.getValueByColumnName("startstno"));
                } else {
                    entity.setStNo(row.getValueByColumnName("endstno"));
                }
            }
            returnList.add(entity);
        }
        return returnList;
    }

    public int getItemMasterCount(String itemCode) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FMZKEY WHERE zaikey = ";

        queryString += StringUtils.surroundWithSingleQuotes(itemCode);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getItemMasterList(String itemCode, boolean isInManage)
            throws YKKSQLException {
        String columnList = "zaikey,zkname1,zkname2,zkname3,master_unit_weight,limit_qty,measure_qty,jogensuj,kagensuj,measure_flag,remove_convent_flag,bag_flag";
        String queryString = "SELECT " + columnList
                + " FROM FMZKEY WHERE zaikey = ";

        queryString += StringUtils.surroundWithSingleQuotes(itemCode);

        if (isInManage) {
            queryString += " AND manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);
        } else {
            queryString += " AND manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ItemViewEntity entity = new ItemViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            try {
                entity.setMasterUnit(new BigDecimal(row
                                .getValueByColumnName("master_unit_weight")
                        )
                );
            } catch (Exception ex) {
                entity.setMasterUnit(new BigDecimal(0));
            }
            try {
                entity.setLimitQty(Integer.parseInt(row
                                        .getValueByColumnName("limit_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setLimitQty(0);
            }
            try {
                entity.setUnitQty(Integer.parseInt(row
                                        .getValueByColumnName("measure_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setUnitQty(0);
            }
            try {
                entity.setAutoDepoMaxCount(Integer.parseInt(row
                                        .getValueByColumnName("jogensuj")
                        )
                );
            } catch (Exception ex) {
                entity.setAutoDepoMaxCount(0);
            }
            try {
                entity.setAutoDepoMinCount(Integer.parseInt(row
                                        .getValueByColumnName("kagensuj")
                        )
                );
            } catch (Exception ex) {
                entity.setAutoDepoMinCount(0);
            }
            entity.setUnitFlag(row.getValueByColumnName("measure_flag"));
            entity.setRemoveConventFlag(row
                            .getValueByColumnName("remove_convent_flag")
            );
            entity.setBagFlag(row.getValueByColumnName("bag_flag"));
            returnList.add(entity);
        }

        return returnList;
    }

    public List getItemNameList(String itemCode) throws YKKSQLException {
        String columnList = "zkname1,zkname2,zkname3";

        String queryString = "SELECT " + columnList
                + " FROM FMZKEY WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " ORDER BY manage_item_flag";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ItemViewEntity entity = new ItemViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            returnList.add(entity);
        }

        return returnList;
    }

    public int getItemViewCount(String itemCode, String itemName1,
                                String itemName2, String itemName3, String looseSearchMode,
                                String manageItemFlag) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FMZKEY WHERE zaikey LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(itemCode
                            + StringUtils.SinglePercentageMark
            )
                    + " AND zkname1 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName1
                            + StringUtils.SinglePercentageMark
            )

                    + " AND zkname2 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName2
                            + StringUtils.SinglePercentageMark
            )

                    + " AND zkname3 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName3
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemCode)
            )
                    + " AND zkname1 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName1)
            )
                    + " AND zkname2 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName2)
            )
                    + " AND zkname3 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName3)
            );
        }
        if (!StringUtils.IsNullOrEmpty(manageItemFlag)) {
            queryString += " AND manage_item_flag = "
                    + StringUtils.surroundWithSingleQuotes(manageItemFlag);
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getItemViewList(String itemCode, String itemName1,
                                String itemName2, String itemName3, String looseSearchMode,
                                String manageItemFlag, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "zaikey,zkname1,zkname2,zkname3";
        String queryString = "SELECT " + columnList
                + " FROM FMZKEY WHERE zaikey LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(itemCode
                            + StringUtils.SinglePercentageMark
            )
                    + " AND zkname1 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName1
                            + StringUtils.SinglePercentageMark
            )

                    + " AND zkname2 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName2
                            + StringUtils.SinglePercentageMark
            )

                    + " AND zkname3 LIKE "
                    + StringUtils.surroundWithSingleQuotes(itemName3
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemCode)
            )
                    + " AND zkname1 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName1)
            )
                    + " AND zkname2 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName2)
            )
                    + " AND zkname3 LIKE "
                    + StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(itemName3)
            );
        }
        if (!StringUtils.IsNullOrEmpty(manageItemFlag)) {
            queryString += " AND manage_item_flag = "
                    + StringUtils.surroundWithSingleQuotes(manageItemFlag);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ItemViewEntity entity = new ItemViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            returnList.add(entity);
        }

        return returnList;
    }

    public int getLabelReprintCount(String printNo, String labelType,
                                    String printTimeFrom, String printTimeTo) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNLABEL WHERE printing_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.PritingFlag.PRINTED)
                + " AND printer_no = "
                + StringUtils.surroundWithSingleQuotes(printNo)
                + " AND label_type = "
                + StringUtils.surroundWithSingleQuotes(labelType);

        if (!StringUtils.IsNullOrEmpty(printTimeFrom)
                && !StringUtils.IsNullOrEmpty(printTimeTo)) {
            queryString += " AND update_datetime BETWEEN "
                    + StringUtils.surroundWithSingleQuotes(printTimeFrom)
                    + " AND "
                    + StringUtils.surroundWithSingleQuotes(printTimeTo);
        } else if (!StringUtils.IsNullOrEmpty(printTimeFrom)) {
            queryString += " AND update_datetime >= "
                    + StringUtils.surroundWithSingleQuotes(printTimeFrom);
        } else if (!StringUtils.IsNullOrEmpty(printTimeTo)) {
            queryString += " AND update_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(printTimeTo);
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    // public List getMadeLine() throws YKKSQLException
    // {
    // String columnList = "made_line";
    // String queryString = "SELECT " + columnList + " FROM FNRANGE GROUP BY
    // "
    // + columnList;
    //
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString);
    // List returnList = new ArrayList();
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // returnList.add(row.getValueByColumnName("made_line"));
    // }
    //
    // return returnList;
    // }
    //
    // public List getMadeSection() throws YKKSQLException
    // {
    // String columnList = "made_section";
    // String queryString = "SELECT " + columnList + " FROM FNRANGE GROUP BY
    // "
    // + columnList;
    //
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString);
    // List returnList = new ArrayList();
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // returnList.add(row.getValueByColumnName("made_section"));
    // }
    //
    // return returnList;
    // }

    public List getLabelReprintList(String printNo, String labelType,
                                    String printTimeFrom, String printTimeTo, int beginningPos,
                                    int count) throws YKKSQLException {
        String columnList = "update_datetime,ticket_no,bucket_no,zaikey,color_code,section,line,customer_code,pr_no,retrieval_weight,retrieval_qty,label_key";

        String queryString = "SELECT "
                + columnList
                + " FROM FNLABEL WHERE printing_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.PritingFlag.PRINTED)
                + " AND printer_no = "
                + StringUtils.surroundWithSingleQuotes(printNo)
                + " AND label_type = "
                + StringUtils.surroundWithSingleQuotes(labelType);

        if (!StringUtils.IsNullOrEmpty(printTimeFrom)
                && !StringUtils.IsNullOrEmpty(printTimeTo)) {
            queryString += " AND update_datetime BETWEEN "
                    + StringUtils.surroundWithSingleQuotes(printTimeFrom)
                    + " AND "
                    + StringUtils.surroundWithSingleQuotes(printTimeTo);
        } else if (!StringUtils.IsNullOrEmpty(printTimeFrom)) {
            queryString += " AND update_datetime >= "
                    + StringUtils.surroundWithSingleQuotes(printTimeFrom);
        } else if (!StringUtils.IsNullOrEmpty(printTimeTo)) {
            queryString += " AND update_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(printTimeTo);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            LabelReprintEntity entity = new LabelReprintEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setPrintTime(row.getValueByColumnName("update_datetime"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setSectionExternalCode(row.getValueByColumnName("section")
                            + "/" + row.getValueByColumnName("customer_code")
            );
            entity.setLinePrno(row.getValueByColumnName("line") + "/"
                            + row.getValueByColumnName("pr_no")
            );
            entity.setLabelKey(row.getValueByColumnName("label_key"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }
            try {
                entity.setStockoutWeight(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_weight")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutWeight(0);
            }
            returnList.add(entity);
        }

        return returnList;
    }

    public LocationViewEntity getLocationMaintenanceEntity(String locationNo,
                                                           boolean isInManage) throws YKKSQLException {
        String columnList = "FNZAIKO.ticket_no,FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FNZAIKO.memo,FNZAIKO.bucket_no,FMZKEY.zkname1";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.syozaikey = "
                + StringUtils.surroundWithSingleQuotes(locationNo);
        if (isInManage) {
            queryString += " AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);
        } else {
            queryString += " AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            LocationViewEntity entity = new LocationViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname1"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setStockinDate(StringUtils.getDateFromDateTime(row
                                    .getValueByColumnName("nyukohiji")
                    )
            );
            entity.setStockinTime(StringUtils.getTimeFromDateTime(row
                                    .getValueByColumnName("nyukohiji")
                    )
            );
            entity.setMemo(row.getValueByColumnName("memo"));

            returnList.add(entity);
        }

        if (returnList.size() > 0) {
            return (LocationViewEntity) returnList.get(0);
        } else {
            return new LocationViewEntity();
        }
    }

    public String getLocationNoBySystemid(String systemid)
            throws YKKSQLException {
        if (!StringUtils.IsNullOrEmpty(systemid)) {
            String columnList = "syozaikey";
            String queryString = "SELECT " + columnList
                    + " FROM FNLOCAT WHERE systemid = "
                    + StringUtils.surroundWithSingleQuotes(systemid);

            DBHandler handler = new DBHandler(conn);
            handler.executeQuery(queryString);
            List returnList = new ArrayList();
            RecordSet recordSet = handler.getRecordSet();
            List rowList = recordSet.getRowList();
            Iterator it = rowList.iterator();
            while (it.hasNext()) {
                RecordSetRow row = (RecordSetRow) it.next();

                returnList.add(row.getValueByColumnName("syozaikey"));
            }
            if (returnList.size() > 0) {
                return returnList.get(0).toString();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public int getLocationStatus(String bankno, String bayno, String levelno)
            throws YKKSQLException {
        int status = 0;
        String columnList = "FNAKITANA.tanaflg,FNAKITANA.accessflg,FNLOCAT.zaijyoflg";
        String queryString = "SELECT "
                + columnList
                + " FROM FNAKITANA,FNLOCAT WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNAKITANA.bankno = "
                + StringUtils.surroundWithSingleQuotes(bankno)
                + " AND FNAKITANA.bayno = "
                + StringUtils.surroundWithSingleQuotes(bayno)
                + " AND FNAKITANA.levelno = "
                + StringUtils.surroundWithSingleQuotes(levelno);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            String zaijyoflg = row.getValueByColumnName("zaijyoflg");
            String accessflg = row.getValueByColumnName("accessflg");
            String tanaflg = row.getValueByColumnName("tanaflg");

            if (zaijyoflg.equals(DBFlags.ZaijyoFlg.ERRO_LOCATION)) {
                status = 4;
            } else if (tanaflg.equals(DBFlags.Tanaflg.FORBID_LOCAT)) {
                status = 7;
            } else if (accessflg.equals(DBFlags.AccessFlag.UNASSIGNABLE)) {
                status = 8;
            } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKIN_ORDER)) {
                status = 2;
            } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)) {
                status = 3;
            } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUTING)) {
                status = 6;
            } else if (tanaflg.equals(DBFlags.Tanaflg.USED_LOCAT)) {
                status = 5;
            } else {
                status = 1;
            }
            break;
        }
        return status;
    }

    public HashMap getLocationStatus(String bankno, String startBayNo,
                                     String endBayNo, String startLevelNo, String endLevelNo)
            throws YKKSQLException {
        String columnList = " lpad(fnakitana.bankno, 3, '0') as bankno, lpad(fnakitana.bayno, 3, '0') as bayno, lpad(fnakitana.levelno, 3, '0') as levelno, FNAKITANA.tanaflg,FNAKITANA.accessflg,FNLOCAT.zaijyoflg";
        String queryString = "SELECT "
                + columnList
                + " FROM FNAKITANA,FNLOCAT WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNAKITANA.bankno = "
                + StringUtils.surroundWithSingleQuotes(bankno)
                + " AND FNAKITANA.bayno BETWEEN "
                + StringUtils.surroundWithSingleQuotes(startBayNo) + " AND "
                + StringUtils.surroundWithSingleQuotes(endBayNo)
                + " AND FNAKITANA.levelno BETWEEN "
                + StringUtils.surroundWithSingleQuotes(startLevelNo) + " AND "
                + StringUtils.surroundWithSingleQuotes(endLevelNo);
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();

        HashMap hm = new HashMap();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            String bankNo = row.getValueByColumnName("bankno");
            String bayNo = row.getValueByColumnName("bayno");
            String levelNo = row.getValueByColumnName("levelno");
            String zaijyoflg = row.getValueByColumnName("zaijyoflg");
            String accessflg = row.getValueByColumnName("accessflg");
            String tanaflg = row.getValueByColumnName("tanaflg");

            int status = getStatus(zaijyoflg, accessflg, tanaflg);
            String key = bankNo + bayNo + levelNo;
            hm.put(key, String.valueOf(status));

        }
        return hm;
    }

    public int getLocationStorageInfoCount(LocationStorageInfoHead head)
            throws YKKSQLException {
        String queryString = "";

        if (head.getDepo().equals("平置")) {

            queryString = "SELECT COUNT(*) FROM FNZAIKO WHERE storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                    + " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);

                queryString += ")";
            }

        } else if (head.getDepo().equals("未入库仓库")) {
            queryString += " SELECT COUNT(*) FROM FNZAIKO WHERE FNZAIKO.weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);

                queryString += ")";
            }
        } else {

            String column1 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNAKITANA.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            String column2 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNLOCAT.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            queryString = "SELECT COUNT(*) FROM ((SELECT "
                    + column1
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNLOCAT.systemid = FNZAIKO.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR ";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }

                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += ")";

            queryString += " UNION (SELECT "
                    + column2
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR ";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }

                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += "))";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getLocationStorageInfoListSQL(
            LocationStorageInfoHead head) {
        String columnList = "";

        String queryString = "";

        if (head.getDepo().equals("平置")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString = "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                    + " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";

        } else if (head.getDepo().equals("未入库仓库")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString += "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNZAIKO.weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";
        } else {
            columnList = "zaikey,color_code,ticket_no,bucket_no,zaikosu,nyukohiji,zkname1,zkname2,zkname3,weight_report_complete_flag,storage_place_flag,tanaflg,accessflg,syozaikey,zaijyoflg,plan_qty";

            String column1 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNAKITANA.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            String column2 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNLOCAT.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            queryString = "SELECT "
                    + columnList
                    + " FROM ((SELECT "
                    + column1
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNLOCAT.systemid = FNZAIKO.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }

            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += ")";

            queryString += " UNION (SELECT "
                    + column2
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += "))";
            queryString += " ORDER BY syozaikey,zaikey,color_code";
        }

        return queryString;
    }

    public List getLocationStorageInfoList(LocationStorageInfoHead head)
            throws YKKSQLException {
        String columnList = "";

        String queryString = "";

        if (head.getDepo().equals("平置")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString = "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                    + " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";

        } else if (head.getDepo().equals("未入库仓库")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString += "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNZAIKO.weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";
        } else {
            columnList = "zaikey,color_code,ticket_no,bucket_no,zaikosu,nyukohiji,zkname1,zkname2,zkname3,weight_report_complete_flag,storage_place_flag,tanaflg,accessflg,syozaikey,zaijyoflg,plan_qty";

            String column1 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNAKITANA.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            String column2 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNLOCAT.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            queryString = "SELECT "
                    + columnList
                    + " FROM ((SELECT "
                    + column1
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNLOCAT.systemid = FNZAIKO.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }

            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += ")";

            queryString += " UNION (SELECT "
                    + column2
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += "))";
            queryString += " ORDER BY syozaikey,zaikey,color_code";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            LocationStorageInfoEntity entity = new LocationStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity
                    .setWeightReportFlag(DBFlags.WeightReportCompleteFlag
                                    .parseDBToPage(row
                                                    .getValueByColumnName("weight_report_complete_flag")
                                    )
                    );
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));

            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));

            int planQty = Integer.parseInt(row
                    .getValueByColumnName("plan_qty"));

            if (row.getValueByColumnName("weight_report_complete_flag").equals(
                    DBFlags.WeightReportCompleteFlag.UNCOMPLETED
            ) && planQty != 0) {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("plan_qty")
                            )
                    );
                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo("预定入库仓库");
            } else if (!row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.AUTO
            )
                    && (
                    row.getValueByColumnName("weight_report_complete_flag")
                            .equals(DBFlags.WeightReportCompleteFlag.COMPLETED) || row
                            .getValueByColumnName("weight_report_complete_flag")
                            .equals(DBFlags.WeightReportCompleteFlag.REPORTING)
            )) {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );
                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo("平置");
            } else {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );

                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo(row.getValueByColumnName("syozaikey"));

                String zaijyoflg = row.getValueByColumnName("zaijyoflg");
                String accessflg = row.getValueByColumnName("accessflg");
                String tanaflg = row.getValueByColumnName("tanaflg");

                if (zaijyoflg.equals(DBFlags.ZaijyoFlg.ERRO_LOCATION)) {
                    entity.setLocationStatus("异常货位");
                } else if (tanaflg.equals(DBFlags.Tanaflg.FORBID_LOCAT)) {
                    entity.setLocationStatus("禁止货位");
                } else if (accessflg.equals(DBFlags.AccessFlag.UNASSIGNABLE)) {
                    entity.setLocationStatus("访问不可货位");
                } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                        || zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                        || zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUTING)) {
                    entity.setLocationStatus("作业货位");
                } else if (tanaflg.equals(DBFlags.Tanaflg.USED_LOCAT)) {
                    entity.setLocationStatus("实货位");
                } else {
                    entity.setLocationStatus("空货位");
                }

            }

            returnList.add(entity);
        }

        return returnList;
    }

    public List getLocationStorageInfoList(LocationStorageInfoHead head,
                                           int beginningPos, int count) throws YKKSQLException {
        String columnList = "";

        String queryString = "";

        if (head.getDepo().equals("平置")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString = "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                    + " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";

        } else if (head.getDepo().equals("未入库仓库")) {
            columnList = "FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.nyukohiji,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.weight_report_complete_flag,FNZAIKO.storage_place_flag,FNZAIKO.plan_qty";

            queryString += "SELECT "
                    + columnList
                    + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNZAIKO.weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }
                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
            }
            queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";
        } else {
            columnList = "zaikey,color_code,ticket_no,bucket_no,zaikosu,nyukohiji,zkname1,zkname2,zkname3,weight_report_complete_flag,storage_place_flag,tanaflg,accessflg,syozaikey,zaijyoflg,plan_qty";

            String column1 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNAKITANA.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            String column2 = "FNZAIKO.zaikey as zaikey ,FNZAIKO.color_code as color_code ,FNZAIKO.ticket_no as ticket_no ,FNZAIKO.bucket_no as bucket_no ,FNZAIKO.zaikosu as zaikosu ,FNZAIKO.nyukohiji as nyukohiji ,FMZKEY.zkname1 as zkname1 ,FMZKEY.zkname2 as zkname2 ,FMZKEY.zkname3 as zkname3 ,FNZAIKO.weight_report_complete_flag as weight_report_complete_flag ,FNZAIKO.storage_place_flag as storage_place_flag,FNLOCAT.syozaikey AS syozaikey,FNAKITANA.tanaflg AS tanaflg,FNAKITANA.accessflg AS accessflg,FNZAIKO.plan_qty AS plan_qty,FNLOCAT.zaijyoflg AS zaijyoflg ";

            queryString = "SELECT "
                    + columnList
                    + " FROM ((SELECT "
                    + column1
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey(+) AND FNLOCAT.systemid = FNZAIKO.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }

                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }

                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";

                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += ")";

            queryString += " UNION (SELECT "
                    + column2
                    + " FROM FNAKITANA, FNLOCAT, FNZAIKO, FMZKEY WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

            if (head.getLocationStatus().size() > 0) {
                queryString += " AND (";

                if (head.getLocationStatus().contains("实货位")) {
                    // queryString += " FNAKITANA.tanaflg = "
                    // + StringUtils
                    // .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)

                            + " OR ";
                }
                if (head.getLocationStatus().contains("空货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("作业货位")) {
                    queryString += " ( FNLOCAT.zaijyoflg IN ("
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                            + ","
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                            + ") AND FNAKITANA.syozaikey <> ' ' ) OR";
                }
                if (head.getLocationStatus().contains("异常货位")) {
                    queryString += " FNLOCAT.zaijyoflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("禁止货位")) {
                    queryString += " FNAKITANA.tanaflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT)
                            + " OR ";
                }
                if (head.getLocationStatus().contains("访问不可货位")) {
                    queryString += " FNAKITANA.accessflg = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE)
                            + " OR ";
                }
                queryString += " (TRIM(FNAKITANA.tanaflg) IS NULL AND TRIM(FNAKITANA.accessflg) IS NULL AND TRIM(FNLOCAT.zaijyoflg) IS NULL)";
                queryString += ")";
            }
            if (head.getWeightReportFlag().size() > 0) {
                queryString += " AND (";
                if (head.getWeightReportFlag().contains("未报告")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告中")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.REPORTING)
                            + " OR ";
                }
                if (head.getWeightReportFlag().contains("报告完成")) {
                    queryString += " weight_report_complete_flag = "
                            + StringUtils
                            .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                            + " OR ";
                }

                queryString = queryString
                        .substring(0, queryString.length() - 3);
                queryString += ")";
                // queryString += " TRIM(weight_report_complete_flag) IS NULL
                // )";
            }
            if (head.isRangeSet()) {
                if (StringUtils.IsNullOrEmpty(head.getLocationNoTo())) {
                    queryString += " AND (FNAKITANA.syozaikey >= "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                } else {
                    queryString += " AND (FNAKITANA.syozaikey BETWEEN "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoFrom()
                    )
                            + " AND "
                            + StringUtils.surroundWithSingleQuotes(head
                                    .getLocationNoTo()
                    )
                            + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
                }
            } else {
                queryString += " AND (FNAKITANA.syozaikey = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getLocationNoFrom()
                )
                        + " OR TRIM(FNAKITANA.syozaikey) IS NULL )";
            }
            if (head.getDepo().equals("自动仓库")) {
                queryString += " AND (FNZAIKO.weight_report_complete_flag <> "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                        + " OR TRIM(FNZAIKO.weight_report_complete_flag) IS NULL ) "
                        + " AND (storage_place_flag = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                        + " OR TRIM(storage_place_flag) IS NULL ) ";

            }
            queryString += "))";
            queryString += " ORDER BY syozaikey,zaikey,color_code";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            LocationStorageInfoEntity entity = new LocationStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity
                    .setWeightReportFlag(DBFlags.WeightReportCompleteFlag
                                    .parseDBToPage(row
                                                    .getValueByColumnName("weight_report_complete_flag")
                                    )
                    );
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));

            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));

            int planQty = Integer.parseInt(row
                    .getValueByColumnName("plan_qty"));

            if (row.getValueByColumnName("weight_report_complete_flag").equals(
                    DBFlags.WeightReportCompleteFlag.UNCOMPLETED
            ) && planQty != 0) {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("plan_qty")
                            )
                    );
                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo("预定入库仓库");
            } else if (!row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.AUTO
            )
                    && (
                    row.getValueByColumnName("weight_report_complete_flag")
                            .equals(DBFlags.WeightReportCompleteFlag.COMPLETED) || row
                            .getValueByColumnName("weight_report_complete_flag")
                            .equals(DBFlags.WeightReportCompleteFlag.REPORTING)
            )) {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );
                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo("平置");
            } else {
                try {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );

                } catch (Exception ex) {
                    entity.setInstockCount(0);
                }
                entity.setLocationNo(row.getValueByColumnName("syozaikey"));

                String zaijyoflg = row.getValueByColumnName("zaijyoflg");
                String accessflg = row.getValueByColumnName("accessflg");
                String tanaflg = row.getValueByColumnName("tanaflg");

                if (zaijyoflg.equals(DBFlags.ZaijyoFlg.ERRO_LOCATION)) {
                    entity.setLocationStatus("异常货位");
                } else if (tanaflg.equals(DBFlags.Tanaflg.FORBID_LOCAT)) {
                    entity.setLocationStatus("禁止货位");
                } else if (accessflg.equals(DBFlags.AccessFlag.UNASSIGNABLE)) {
                    entity.setLocationStatus("访问不可货位");
                } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                        || zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                        || zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUTING)) {
                    entity.setLocationStatus("作业货位");
                } else if (tanaflg.equals(DBFlags.Tanaflg.USED_LOCAT)) {
                    entity.setLocationStatus("实货位");
                } else {
                    entity.setLocationStatus("空货位");
                }

            }

            returnList.add(entity);
        }

        return returnList;
    }

    public int getLocationViewCount(List locationStatus, String manageItemFlag)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNLOCAT,FNZAIKO,FNAKITANA WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey AND FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + "AND (";

        if (locationStatus.contains("USED")) {
            queryString += " FNAKITANA.tanaflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                    + " OR ";
        }
        if (locationStatus.contains("ERROR")) {
            queryString += " FNLOCAT.zaijyoflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                    + " OR ";
        }
        if (locationStatus.contains("WORK")) {
            queryString += " FNLOCAT.zaijyoflg IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                    + ") AND FNLOCAT.syozaikey <> ' ' OR ";
        }
        queryString = queryString.substring(0, queryString.length() - 3) + ")";

        queryString += " AND FNZAIKO.manage_item_flag = "
                + StringUtils.surroundWithSingleQuotes(manageItemFlag);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getLocationViewList(List locationStatus, String manageItemFlag,
                                    int beginningPos, int count) throws YKKSQLException {
        if (locationStatus.contains("USED") || locationStatus.contains("ERROR")
                || locationStatus.contains("WORK")) {
            String columnList = "FNLOCAT.syozaikey,FNLOCAT.zaijyoflg,FNAKITANA.tanaflg";

            String queryString = "SELECT "
                    + columnList
                    + " FROM FNLOCAT,FNZAIKO,FNAKITANA WHERE FNLOCAT.syozaikey = FNAKITANA.syozaikey AND FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.accessflg = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                    + " AND (";

            if (locationStatus.contains("USED")) {
                queryString += " FNAKITANA.tanaflg = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                        + " OR ";
            }
            if (locationStatus.contains("ERROR")) {
                queryString += " FNLOCAT.zaijyoflg = "
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                        + " OR ";
            }
            if (locationStatus.contains("WORK")) {
                queryString += " FNLOCAT.zaijyoflg IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKIN_ORDER)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STOCKOUTING)
                        + ") AND FNLOCAT.syozaikey <> ' ' OR ";
            }
            queryString = queryString.substring(0, queryString.length() - 3)
                    + ")";
            queryString += " AND FNZAIKO.manage_item_flag = "
                    + StringUtils.surroundWithSingleQuotes(manageItemFlag);

            DBHandler handler = new DBHandler(conn);
            handler.executeQuery(queryString, beginningPos, count);
            List returnList = new ArrayList();
            RecordSet recordSet = handler.getRecordSet();
            List rowList = recordSet.getRowList();
            Iterator it = rowList.iterator();
            while (it.hasNext()) {
                LocationViewEntity entity = new LocationViewEntity();
                RecordSetRow row = (RecordSetRow) it.next();

                entity.setLocationNo(row.getValueByColumnName("syozaikey"));
                if (row.getValueByColumnName("tanaflg").equals(
                        DBFlags.Tanaflg.USED_LOCAT
                )) {
                    entity.setLocationStatus("实货位");
                } else if (row.getValueByColumnName("zaijyoflg").equals(
                        DBFlags.ZaijyoFlg.ERRO_LOCATION
                )) {
                    entity.setLocationStatus("异常货位");
                } else {
                    entity.setLocationStatus("作业货位");
                }
                returnList.add(entity);
            }
            return returnList;
        } else {
            return new ArrayList();
        }
    }

    public List getMessageDivision() throws YKKSQLException {
        String columnList = "host_table";
        String queryString = "SELECT " + columnList
                + " FROM FNHSTLOG GROUP BY " + columnList;

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("host_table"));
        }
        return returnList;
    }

    public int getMessageInfoCount(MessageInfoHead head) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHSTLOG WHERE transfer_datetime BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getMessageInfoListSQL(MessageInfoHead head) {
        String columnList = "transfer_datetime,host_table,data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHSTLOG WHERE transfer_datetime BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY transfer_datetime DESC,host_table";

        return queryString;
    }

    public List getMessageInfoList(MessageInfoHead head) throws YKKSQLException {
        String columnList = "transfer_datetime,host_table,data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHSTLOG WHERE transfer_datetime BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY transfer_datetime DESC,host_table";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            MessageInfoEntity entity = new MessageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTime(row.getValueByColumnName("transfer_datetime"));
            entity.setMessageType(row.getValueByColumnName("host_table"));
            entity.setMessage(row.getValueByColumnName("data"));

            returnList.add(entity);
        }
        return returnList;
    }

    public List getMessageInfoList(MessageInfoHead head, int beginningPos,
                                   int count) throws YKKSQLException {
        String columnList = "transfer_datetime,host_table,data";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHSTLOG WHERE transfer_datetime BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getDateFrom()
                        + head.getTimeFrom()
        )
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getDateTo()
                        + head.getTimeTo()
        );
        if (!head.getMessageType().equals("全部")) {
            queryString += " AND host_table = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getMessageType()
            );
        }
        queryString += " ORDER BY transfer_datetime DESC,host_table";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            MessageInfoEntity entity = new MessageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTime(row.getValueByColumnName("transfer_datetime"));
            entity.setMessageType(row.getValueByColumnName("host_table"));
            entity.setMessage(row.getValueByColumnName("data"));

            returnList.add(entity);
        }
        return returnList;
    }

    public int getOvertimeStorageInfoCount(OvertimeStorageInfoHead head)
            throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.reception_datetime,FNZAIKO.koshinhiji,FNZAIKO.zaikey,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.systemid,FNZAIKO.zaikosu,FNZAIKO.storage_place_flag";
        String queryString = "SELECT COUNT(*) FROM (SELECT " + columnList
                + " FROM FNZAIKO WHERE ";
        if (head.getBenchmarkObject().equals("1")) {
            queryString += " FNZAIKO.nyukohiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else if (head.getBenchmarkObject().equals("2")) {
            queryString += " FNZAIKO.reception_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else {
            queryString += " FNZAIKO.koshinhiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        }
        if (head.getDepositoryType().equals(DBFlags.StoragePlaceFlag.AUTO)
                || head.getDepositoryType().equals(
                DBFlags.StoragePlaceFlag.FLAT
        )) {
            queryString += " AND storage_place_flag = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getDepositoryType()
            );
        }
        queryString += " GROUP BY " + columnList + ")";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getOvertimeStorageInfoListSQL(
            OvertimeStorageInfoHead head) {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.reception_datetime,FNZAIKO.koshinhiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.systemid,FNZAIKO.zaikosu,FNZAIKO.storage_place_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepositoryType().equals(DBFlags.StoragePlaceFlag.AUTO)
                || head.getDepositoryType().equals(
                DBFlags.StoragePlaceFlag.FLAT
        )) {
            queryString += " AND storage_place_flag = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getDepositoryType()
            );
        }
        if (head.getBenchmarkObject().equals("1")) {
            queryString += " AND FNZAIKO.nyukohiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else if (head.getBenchmarkObject().equals("2")) {
            queryString += " AND FNZAIKO.reception_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else {
            queryString += " AND FNZAIKO.koshinhiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        }
        if (head.getOrderBy().equals("1")) {
            if (head.getBenchmarkObject().equals("1")) {
                queryString += " ORDER BY FNZAIKO.nyukohiji";
            } else if (head.getBenchmarkObject().equals("2")) {
                queryString += " ORDER BY FNZAIKO.reception_datetime";
            } else {
                queryString += " ORDER BY FNZAIKO.koshinhiji";
            }
        } else {
            queryString += " ORDER BY FNZAIKO.zaikey";
        }

        return queryString;
    }

    public List getOvertimeStorageInfoList(OvertimeStorageInfoHead head)
            throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.reception_datetime,FNZAIKO.koshinhiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.systemid,FNZAIKO.zaikosu,FNZAIKO.storage_place_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepositoryType().equals(DBFlags.StoragePlaceFlag.AUTO)
                || head.getDepositoryType().equals(
                DBFlags.StoragePlaceFlag.FLAT
        )) {
            queryString += " AND storage_place_flag = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getDepositoryType()
            );
        }
        if (head.getBenchmarkObject().equals("1")) {
            queryString += " AND FNZAIKO.nyukohiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else if (head.getBenchmarkObject().equals("2")) {
            queryString += " AND FNZAIKO.reception_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else {
            queryString += " AND FNZAIKO.koshinhiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        }
        if (head.getOrderBy().equals("1")) {
            if (head.getBenchmarkObject().equals("1")) {
                queryString += " ORDER BY FNZAIKO.nyukohiji";
            } else if (head.getBenchmarkObject().equals("2")) {
                queryString += " ORDER BY FNZAIKO.reception_datetime";
            } else {
                queryString += " ORDER BY FNZAIKO.koshinhiji";
            }
        } else {
            queryString += " ORDER BY FNZAIKO.zaikey";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            OvertimeStorageInfoEntity entity = new OvertimeStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            if (head.getBenchmarkObject().equals("1")) {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("nyukohiji")
                        )
                );
            } else if (head.getBenchmarkObject().equals("2")) {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("reception_datetime")
                        )
                );
            } else {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("koshinhiji")
                        )
                );
            }
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));

            if (row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.FLAT
            )) {
                entity.setLocationNo("平库");
            } else {
                entity.setLocationNo(getLocationNoBySystemid(row
                                        .getValueByColumnName("systemid")
                        )
                );
            }
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getOvertimeStorageInfoList(OvertimeStorageInfoHead head,
                                           int beginningPos, int count) throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.reception_datetime,FNZAIKO.koshinhiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.systemid,FNZAIKO.zaikosu,FNZAIKO.storage_place_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepositoryType().equals(DBFlags.StoragePlaceFlag.AUTO)
                || head.getDepositoryType().equals(
                DBFlags.StoragePlaceFlag.FLAT
        )) {
            queryString += " AND storage_place_flag = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getDepositoryType()
            );
        }
        if (head.getBenchmarkObject().equals("1")) {
            queryString += " AND FNZAIKO.nyukohiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else if (head.getBenchmarkObject().equals("2")) {
            queryString += " AND FNZAIKO.reception_datetime <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        } else {
            queryString += " AND FNZAIKO.koshinhiji <= "
                    + StringUtils.surroundWithSingleQuotes(StringPadder
                            .rightPad(head.getBenchmarkDate(), "0", 14)
            );
        }

        queryString += " GROUP BY " + columnList;

        if (head.getOrderBy().equals("1")) {
            if (head.getBenchmarkObject().equals("1")) {
                queryString += " ORDER BY FNZAIKO.nyukohiji";
            } else if (head.getBenchmarkObject().equals("2")) {
                queryString += " ORDER BY FNZAIKO.reception_datetime";
            } else {
                queryString += " ORDER BY FNZAIKO.koshinhiji";
            }
        } else {
            queryString += " ORDER BY FNZAIKO.zaikey";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            OvertimeStorageInfoEntity entity = new OvertimeStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            if (head.getBenchmarkObject().equals("1")) {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("nyukohiji")
                        )
                );
            } else if (head.getBenchmarkObject().equals("2")) {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("reception_datetime")
                        )
                );
            } else {
                entity.setDateTime(StringUtils.getDateFromDateTime(row
                                        .getValueByColumnName("koshinhiji")
                        )
                );
            }
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));

            if (row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.FLAT
            )) {
                entity.setLocationNo("平库");
            } else {
                entity.setLocationNo(getLocationNoBySystemid(row
                                        .getValueByColumnName("systemid")
                        )
                );
            }
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getPickingStationNo() throws YKKSQLException {
        String columnList = "stno, stname";
        String queryString = "SELECT "
                + columnList
                + " FROM FNSTATION WHERE FNSTATION.station_type = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StationType.PICKING_STATION);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            StationEntity entity = new StationEntity();
            entity.setStno(row.getValueByColumnName("stno"));
            entity.setStname(row.getValueByColumnName("stname"));
            returnList.add(entity);
        }

        return returnList;
    }

    public String getPrinterName(String ip) throws YKKSQLException {
        String columnList = "FNPRINTER.printer_name";
        String queryString = "SELECT "
                + columnList
                + " FROM FNPRINTER,TERMINAL WHERE FNPRINTER.printer_no = TERMINAL.printer_no AND TERMINAL.ipaddress = "
                + StringUtils.surroundWithSingleQuotes(ip);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("printer_name"));
        }

        if (returnList.size() > 0) {
            return (String) returnList.get(0);
        } else {
            return "";
        }
    }

    public List getPrinterNoAndName() throws YKKSQLException {
        String columnList = "printer_no,printer_name";
        String queryString = "SELECT " + columnList
                + " FROM FNPRINTER ORDER BY printer_no";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            PrinterNoAndNameEntity entity = new PrinterNoAndNameEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setPrinterNo(row.getValueByColumnName("printer_no"));
            entity.setPrinterName(row.getValueByColumnName("printer_name"));

            returnList.add(entity);
        }

        return returnList;
    }

    public List getRejectStationNo() throws YKKSQLException {
        String columnList = "stno, stname";
        String queryString = "SELECT "
                + columnList
                + " FROM FNSTATION WHERE FNSTATION.station_type = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StationType.REJECT_STATION)
                + " AND nyusyumode = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyumode.BLANK_BUCKET_MODE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            StationEntity entity = new StationEntity();
            entity.setStno(row.getValueByColumnName("stno"));
            entity.setStname(row.getValueByColumnName("stname"));
            if (entity.getStno().equals("1208")) {
                continue;
            }
            returnList.add(entity);
        }

        return returnList;
    }

    public RetrievalStationEntity getRetrievalStation(String stno)
            throws YKKSQLException {
        // RetrievalStationEntity defaultEntity = getStation(stno);

        // if (defaultEntity == null)
        // {
        // return new RetrievalStationEntity();
        // }
        String columnList = "retrieval_st_name";
        // String queryString = "SELECT "
        // + columnList
        // + " FROM FNRETRIEVAL_ST WHERE default_pickstno = "
        // + StringUtils.surroundWithSingleQuotes(defaultEntity
        // .getPickStation())
        // + " AND default_unit_stno = "
        // + StringUtils.surroundWithSingleQuotes(defaultEntity
        // .getUnitStation());

        String queryString = "SELECT " + columnList
                + " FROM FNRETRIEVAL_ST WHERE retrieval_station = "
                + StringUtils.surroundWithSingleQuotes(stno);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalStationEntity entity = new RetrievalStationEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setRetrievalStationName(row
                            .getValueByColumnName("retrieval_st_name")
            );

            returnList.add(entity);
        }

        if (returnList.size() > 0) {
            return (RetrievalStationEntity) returnList.get(0);
        } else {
            return new RetrievalStationEntity();
        }
    }

    public String getRoleIdByUserId(String userId) throws YKKSQLException {
        String queryString = "select roleid from loginuser where userid = "
                + StringUtils.surroundWithSingleQuotes(userId);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String roleId = "";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            roleId = row.getValueByColumnName("roleid");
            break;
        }
        return roleId;
    }

    public List getStartStopAllSettingList() throws YKKSQLException {
        String columnList = "arcno,systemflg";
        String queryString = "SELECT " + columnList + " FROM FNAREA ";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StartStopAllSettingEntity entity = new StartStopAllSettingEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setControllerNo(row.getValueByColumnName("arcno"));
            entity.setSystemStatus(DBFlags.Systemflg.parseDBToPage(row
                                    .getValueByColumnName("systemflg")
                    )
            );
            setWorkCount(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public List getStationNo() throws YKKSQLException {
        String columnList = "stno,stname";
        String queryString = "SELECT " + columnList + " FROM FNSTATION ORDER BY STNO";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            StationEntity entity = new StationEntity();
            entity.setStno(row.getValueByColumnName("stno"));
            entity.setStname(row.getValueByColumnName("stname"));
            returnList.add(entity);
        }

        return returnList;
    }

    public String getStationNyusyumode(String stno) throws YKKSQLException {
        String columnList = "nyusyumode";
        String queryString = "SELECT " + columnList
                + " FROM FNSTATION WHERE stno = "
                + StringUtils.surroundWithSingleQuotes(stno);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(DBFlags.Nyusyumode.parseDBToPage(row
                                    .getValueByColumnName("nyusyumode")
                    )
            );
        }

        if (returnList.size() > 0) {
            return (String) returnList.get(0);
        } else {
            return "";
        }
    }

    public List getStationStatusList() throws YKKSQLException {
        String columnList = "FNSTATION.stno,FNSTATION.stname,FNSTATION.modechgkbn,FNSTATION.nyusyumode,FNUNIT.unitstat,FNSTATION.chudanflg";
        String queryString = "SELECT " + columnList
                + " FROM FNSTATION,FNUNIT WHERE FNSTATION.stno = FNUNIT.unitno";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StationStatusEntity entity = new StationStatusEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStationNo(row.getValueByColumnName("stno"));
            entity.setStationName(row.getValueByColumnName("stname"));
            entity.setTransferMode(DBFlags.Modechgkbn.parseDBToPage(row
                                    .getValueByColumnName("modechgkbn")
                    )
            );
            entity.setWorkMode(DBFlags.Nyusyumode.parseDBToPage(row
                                    .getValueByColumnName("nyusyumode")
                    )
            );
            entity.setWorkStatus(DBFlags.Chudanflg.parseDBToPage(row
                                    .getValueByColumnName("chudanflg")
                    )
            );
            entity.setMachineStatus(DBFlags.UnitStat.parseDBToPage(row
                                    .getValueByColumnName("unitstat")
                    )
            );

            setWorkCount(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public String getStatus(String stno) throws YKKSQLException {
        String columnList = "bucket_no";

        String queryString = "SELECT " + columnList
                + " FROM FNTOUCYAKU WHERE stno = "
                + StringUtils.surroundWithSingleQuotes(stno);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String status = StringUtils.stStatus.NoData;
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils.IsNullOrEmpty(row
                            .getValueByColumnName("bucket_no")
            )
                    || !row.getValueByColumnName("bucket_no").equals("BR")) {
                status = StringUtils.stStatus.Normal;
            } else {
                status = StringUtils.stStatus.NoReadWaiting;
            }
        }

        return status;
    }

    private int getStatus(String zaijyoflg, String accessflg, String tanaflg) {
        int status = 0;
        if (zaijyoflg.equals(DBFlags.ZaijyoFlg.ERRO_LOCATION)) {
            status = 4;
        } else if (tanaflg.equals(DBFlags.Tanaflg.FORBID_LOCAT)) {
            status = 7;
        } else if (accessflg.equals(DBFlags.AccessFlag.UNASSIGNABLE)) {
            status = 8;
        } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKIN_ORDER)) {
            status = 2;
        } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUT_ORDER)) {
            status = 3;
        } else if (zaijyoflg.equals(DBFlags.ZaijyoFlg.STOCKOUTING)) {
            status = 6;
        } else if (tanaflg.equals(DBFlags.Tanaflg.USED_LOCAT)) {
            status = 5;
        } else {
            status = 1;
        }
        return status;
    }

    public String getStNameByStno(String stno) throws YKKSQLException {
        String columnList = "stname";
        String queryString = "SELECT " + columnList
                + " FROM FNSTATION WHERE stno = "
                + StringUtils.surroundWithSingleQuotes(stno);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("stname"));
        }
        if (returnList.size() > 0) {
            return returnList.get(0).toString();
        } else {
            return "";
        }
    }

    public int getStockoutCount(String searchMode, List sections, String line,
                                String lineDivision, String stockoutStation1,
                                String stockoutStation2, String displayFinishedRetrieval, String showShortageCondition, String startDate, String startDateFlag)
            throws YKKSQLException {
//        String queryString = "SELECT COUNT(*) FROM FNSYOTEI ";
//        queryString += "WHERE retrieval_station NOT IN('23','25','26') ";
//        if (displayFinishedRetrieval.equals("0"))
//        {
//            queryString += "AND proc_flag =  "
//                    + StringUtils
//                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
//        }
//        else
//        {
//            queryString += "AND proc_flag IN( "
//                    + StringUtils
//                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
//                    + ","
//                    + StringUtils
//                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
//                    + ")";
//        }
//        if (searchMode.equals("1"))
//        {
//            queryString += " AND section = "
//                    + StringUtils.surroundWithSingleQuotes(section);
//            if (!StringUtils.IsNullOrEmpty(line))
//            {
//                queryString += " AND line = "
//                        + StringUtils.surroundWithSingleQuotes(line);
//            }
//        }
//        else if (searchMode.equals("2"))
//        {
//            queryString += " AND line_type = "
//                    + StringUtils.surroundWithSingleQuotes(lineDivision);
//            queryString += " AND retrieval_station = "
//                    + StringUtils.surroundWithSingleQuotes("24");
//        }
//        else
//        {
//            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
//                    && StringUtils.IsNullOrEmpty(stockoutStation2))
//            {
//                queryString += " AND retrieval_station = "
//                        + StringUtils
//                        .surroundWithSingleQuotes(stockoutStation1);
//            }
//            else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
//                    && StringUtils.IsNullOrEmpty(stockoutStation1))
//            {
//                queryString += " AND retrieval_station = "
//                        + StringUtils
//                        .surroundWithSingleQuotes(stockoutStation2);
//            }
//            else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
//                    && !StringUtils.IsNullOrEmpty(stockoutStation2))
//            {
//                queryString += " AND retrieval_station IN ("
//                        + StringUtils
//                        .surroundWithSingleQuotes(stockoutStation1)
//                        + ","
//                        + StringUtils
//                        .surroundWithSingleQuotes(stockoutStation2)
//                        + ")";
//            }
//        }
        StringBuffer b = new StringBuffer();
        b.append("	SELECT COUNT(*)\n" +
                "  FROM (SELECT proc_flag,\n" +
                "               section,\n" +
                "               retrieval_qty,\n" +
                "               retrieval_alloc_qty,\n" +
                "               line,\n" +
                "               line_type,\n" +
                "               retrieval_station,\n" +
                "               start_date,\n" +
                "               start_timing_flag,\n" +
                "               complete_date,\n" +
                "               complete_timing_flag,\n" +
                "               fnsyotei.zaikey,\n" +
                "               retrieval_no,\n" +
                "               zkname1,\n" +
                "               zkname2,\n" +
                "               zkname3,\n" +
                "               color_code,\n" +
                "               necessary_qty,\n" +
                "               allocation_qty,\n" +
                "               retrieval_qty - retrieval_alloc_qty\n" +
                "                  AS need_qty,\n" +
                "               retrieval_plankey,\n" +
                "               (SELECT NVL (TRIM (SUM (skanosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE (    NOT EXISTS\n" +
                "                                   (SELECT 1\n" +
                "                                      FROM fnforbidretrieval\n" +
                "                                     WHERE     fnzaiko.zaikey =\n" +
                "                                                  fnforbidretrieval.zaikey\n" +
                "                                           AND (   fnzaiko.color_code =\n" +
                "                                                      fnforbidretrieval.color_code\n" +
                "                                                OR TRIM (\n" +
                "                                                      fnforbidretrieval.color_code)\n" +
                "                                                      IS NULL)\n" +
                "                                           AND fnforbidretrieval.from_ticketno <=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.to_ticketno >=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.from_stock_datetime <=\n" +
                "                                                  fnzaiko.nyukohiji\n" +
                "                                           AND fnforbidretrieval.to_stock_datetime >=\n" +
                "                                                  fnzaiko.nyukohiji)\n" +
                "                        AND EXISTS\n" +
                "                               (SELECT 1\n" +
                "                                  FROM fnlocat\n" +
                "                                 WHERE     fnlocat.ailestno IN\n" +
                "                                              (SELECT DISTINCT\n" +
                "                                                      fnunit.ailestno\n" +
                "                                                 FROM fnunit\n" +
                "                                                WHERE fnunit.unitstat IN\n" +
                "                                                         ('1', '2', '4'))\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.zaijyoflg NOT IN\n" +
                "                                              ('0', '5', '8')\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.shikiflg = '0'\n" +
                "                                       AND fnlocat.systemid =\n" +
                "                                              fnzaiko.systemid)\n" +
                "                        AND fnzaiko.skanosu > '0'\n" +
                "                        AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                        AND fnzaiko.manage_item_flag = '0'\n" +
                "                        AND fnzaiko.storage_place_flag = '0'\n" +
                "                        AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                        AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                               NVL (TRIM (FNSYOTEI.color_code), ' ')))\n" +
                "                  AS available_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (zaikosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS real_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (DECODE (TRIM (memo), NULL, 0, 1))), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS memo_flag,\n" +
                "               (SELECT NVL (TRIM (SUM (plan_qty)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.weight_report_complete_flag IN ('0', '1')\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS plan_qty\n" +
                "          FROM fnsyotei, fmzkey\n" +
                "         WHERE     fnsyotei.zaikey = fmzkey.zaikey(+)\n" +
                "               AND fmzkey.manage_item_flag = '0'\n" +
                "               AND fnsyotei.retrieval_station NOT IN ('23', '25', '26'))\n" +
                " WHERE 1 = 1");

        String queryString = b.toString();

        if (displayFinishedRetrieval.equals("0")) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }

        if (showShortageCondition.equals("1")) {
            queryString += " AND AVAILABLE_STOCK_QTY >= (necessary_qty - retrieval_alloc_qty)  ";
        } else if (showShortageCondition.equals("2")) {
            queryString += " AND AVAILABLE_STOCK_QTY < (necessary_qty - retrieval_alloc_qty)  ";
        }

        if (searchMode.equals("1")) {
            queryString += " AND (";
            for (int i = 0; i < sections.size(); i++) {
                String section = (String) sections.get(i);
                if (i != 0) {
                    queryString += " or ";
                }
                if (section.length() == 3) {
                    queryString += " section = " + StringUtils.surroundWithSingleQuotes(section);
                } else {
                    queryString += " section like " + StringUtils.surroundWithSingleQuotes(section + "%");
                }
            }
            queryString += " )";
            if (!StringUtils.IsNullOrEmpty(line)) {
                queryString += " AND line = "
                        + StringUtils.surroundWithSingleQuotes(line);
            }
        } else if (searchMode.equals("2")) {
            queryString += " AND line_type = "
                    + StringUtils.surroundWithSingleQuotes(lineDivision);
            queryString += " AND retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes("24");
        } else {
            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
                    && StringUtils.IsNullOrEmpty(stockoutStation1)) {
                queryString += " AND retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && !StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND retrieval_station IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2)
                        + ")";
            }
        }

        if (!StringUtils.IsNullOrEmpty(startDate)) {
            queryString += " AND start_date = "
                    + StringUtils
                    .surroundWithSingleQuotes(startDate);
        }

        if (!StringUtils.IsNullOrEmpty(startDateFlag)) {
            queryString += " AND start_timing_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(startDateFlag);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }
        return Integer.parseInt(count);
    }

    public int getStockoutDetailCount(String itemNo, String color)
            throws YKKSQLException {

        String queryString = "SELECT COUNT(*) FROM (SELECT fnzaiko.systemid, fnzaiko.ticket_no, fnzaiko.nyukohiji FROM FNLOCAT,FNZAIKO,FNUNIT WHERE FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ("
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) " + " AND FNZAIKO.skanosu > 0 ";
        queryString += " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo);
        queryString += " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(color);
        queryString += " group by fnzaiko.systemid, fnzaiko.ticket_no, fnzaiko.nyukohiji) WHERE 0 = (SELECT COUNT(*) FROM FNFORBIDRETRIEVAL WHERE FNFORBIDRETRIEVAL.zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo)
                + " AND (FNFORBIDRETRIEVAL.color_code = "
                + StringUtils.surroundWithSingleQuotes(color)
                + " OR TRIM(FNFORBIDRETRIEVAL.color_code) IS NULL) AND FNFORBIDRETRIEVAL.from_ticketno <= ticket_no AND FNFORBIDRETRIEVAL.to_ticketno >= ticket_no AND FNFORBIDRETRIEVAL.from_stock_datetime <= nyukohiji AND FNFORBIDRETRIEVAL.to_stock_datetime >= nyukohiji)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }
        return Integer.parseInt(count);
    }

    public List getStockoutDetailList(String itemNo, String color,
                                      int beginningPos, int count) throws YKKSQLException {
        String columnList = "nyukohiji,syozaikey,hikiflg,skanosu,memo,ticket_no,FNZAIKO.systemid,FNZAIKO.sainyukbn";

        String queryString = "SELECT * FROM (SELECT "
                + columnList
                + " FROM FNLOCAT,FNZAIKO,FNUNIT WHERE FNLOCAT.systemid = FNZAIKO.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")"
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ("
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) " + " AND FNZAIKO.skanosu > 0 ";
        queryString += " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo);
        queryString += " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(color);
        queryString += " group by "
                + columnList
                + ") WHERE 0 = (SELECT COUNT(*) FROM FNFORBIDRETRIEVAL WHERE FNFORBIDRETRIEVAL.zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemNo)
                + " AND (FNFORBIDRETRIEVAL.color_code = "
                + StringUtils.surroundWithSingleQuotes(color)
                + " OR TRIM(FNFORBIDRETRIEVAL.color_code) IS NULL) AND FNFORBIDRETRIEVAL.from_ticketno <= ticket_no AND FNFORBIDRETRIEVAL.to_ticketno >= ticket_no AND FNFORBIDRETRIEVAL.from_stock_datetime <= nyukohiji AND FNFORBIDRETRIEVAL.to_stock_datetime >= nyukohiji)";
        queryString += " ORDER BY memo DESC, sainyukbn DESC, nyukohiji ASC";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutDetailEntity entity = new StockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStockinTime(row.getValueByColumnName("nyukohiji"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setStatus(StringUtils.formatHikiFlg(row
                                    .getValueByColumnName("hikiflg")
                    )
            );
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setEnableToStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("skanosu")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }
            entity.setRemark(row.getValueByColumnName("memo"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));

            returnList.add(entity);
        }
        return returnList;
    }

    // old version
    // public List getStockoutList(String searchMode, String orderMode,
    // String section, String line, String lineDivision,
    // String stockoutStation1, String stockoutStation2,
    // String displayFinishedRetrieval, int beginningPos, int count)
    // throws YKKSQLException
    // {
    // String columnList =
    // "FNSYOTEI.retrieval_plankey,FNSYOTEI.start_date,
    // FNSYOTEI.start_timing_flag, FNSYOTEI.complete_date,
    // FNSYOTEI.complete_timing_flag, FNSYOTEI.zaikey, FNSYOTEI.color_code,
    //FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty,FNSYOTEI.retrieval_no,
    // FNSYOTEI
    // .customer_code,necessary_qty,allocation_qty,FNSYOTEI.retrieval_qty
    // ,FNSYOTEI.retrieval_alloc_qty,proc_flag
    // "
    // ;
    //
    // String queryString = "SELECT " + columnList + " FROM FNSYOTEI ";
    // queryString += "WHERE retrieval_station NOT IN('23','25','26') ";
    //
    // if (displayFinishedRetrieval.equals("0"))
    // {
    // queryString += "AND proc_flag = "
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
    // } else
    // {
    // queryString += "AND proc_flag IN( "
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
    // + ","
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
    // + ")";
    // }
    //
    // if (searchMode.equals("1"))
    // {
    // queryString += " AND section = "
    // + StringUtils.surroundWithSingleQuotes(section);
    // if (!StringUtils.IsNullOrEmpty(line))
    // {
    // queryString += " AND line = "
    // + StringUtils.surroundWithSingleQuotes(line);
    // }
    // } else if (searchMode.equals("2"))
    // {
    // queryString += " AND line_type = "
    // + StringUtils.surroundWithSingleQuotes(lineDivision);
    // queryString += " AND retrieval_station = "
    // + StringUtils.surroundWithSingleQuotes("24");
    // } else
    // {
    // if (!StringUtils.IsNullOrEmpty(stockoutStation1)
    // && StringUtils.IsNullOrEmpty(stockoutStation2))
    // {
    // queryString += " AND retrieval_station = "
    // + StringUtils
    // .surroundWithSingleQuotes(stockoutStation1);
    // } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
    // && StringUtils.IsNullOrEmpty(stockoutStation1))
    // {
    // queryString += " AND retrieval_station = "
    // + StringUtils
    // .surroundWithSingleQuotes(stockoutStation2);
    // } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
    // && !StringUtils.IsNullOrEmpty(stockoutStation2))
    // {
    // queryString += " AND retrieval_station IN ("
    // + StringUtils
    // .surroundWithSingleQuotes(stockoutStation1)
    // + ","
    // + StringUtils
    // .surroundWithSingleQuotes(stockoutStation2)
    // + ")";
    // }
    // }
    // queryString += " GROUP BY " + columnList;
    //
    // if (orderMode.equals("1"))
    // {
    // queryString += " ORDER BY start_date,start_timing_flag";
    // } else if (orderMode.equals("2"))
    // {
    // queryString += " ORDER BY complete_date,complete_timing_flag";
    // } else if (orderMode.equals("3"))
    // {
    // queryString += " ORDER BY zaikey";
    // } else
    // {
    // queryString += " ORDER BY retrieval_no";
    // }
    //
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString, beginningPos, count);
    //
    // List returnList = new ArrayList();
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // StockoutEntity entity = new StockoutEntity();
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
    // entity.setWhenNextWorkBeginTiming(row
    // .getValueByColumnName("start_timing_flag"));
    // entity.setWhenThisWorkFinishInPlan(row
    // .getValueByColumnName("complete_date"));
    // entity.setWhenThisWorkFinishInPlanTiming(row
    // .getValueByColumnName("complete_timing_flag"));
    // entity.setRetrievalPlankey(row
    // .getValueByColumnName("retrieval_plankey"));
    // entity.setItemCode(row.getValueByColumnName("zaikey"));
    // entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
    // entity.setExternalCode(row.getValueByColumnName("customer_code"));
    // try
    // {
    // entity
    // .setStockoutCount(Integer
    // .parseInt(row
    //.getValueByColumnName("FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty"
    // )));
    // } catch (Exception ex)
    // {
    // entity.setStockoutCount(0);
    // }
    // entity.setEnableToStockoutCount(getEnableToStockoutCount(row
    // .getValueByColumnName("zaikey"), row
    // .getValueByColumnName("color_code")));
    // entity.setColor(row.getValueByColumnName("color_code"));
    // entity.setMemo(getHasMemo(row.getValueByColumnName("zaikey"), row
    // .getValueByColumnName("color_code")));
    // try
    // {
    // entity.setStockoutNecessaryQty(Long.parseLong(row
    // .getValueByColumnName("necessary_qty")));
    // } catch (Exception ex)
    // {
    // entity.setStockoutNecessaryQty(0);
    // }
    // try
    // {
    // entity.setWavesRetrievalQty(Long.parseLong(row
    // .getValueByColumnName("allocation_qty")));
    // } catch (Exception ex)
    // {
    // entity.setWavesRetrievalQty(0);
    // }
    // try
    // {
    // entity.setManagementRetrievalQty(Long.parseLong(row
    // .getValueByColumnName("retrieval_qty")));
    // } catch (Exception ex)
    // {
    // entity.setManagementRetrievalQty(0);
    // }
    // try
    // {
    // entity.setOutQty(Long.parseLong(row
    // .getValueByColumnName("retrieval_alloc_qty")));
    // } catch (Exception ex)
    // {
    // entity.setOutQty(0);
    // }
    //
    // entity.setretrieved(row.getValueByColumnName("proc_flag").equals(
    // DBFlags.ProcFlag.DEALT));
    // setItemName(entity);
    // setTotalCountInstock(entity);
    //
    // returnList.add(entity);
    //
    // }
    //
    // return returnList;
    // }

    // updated version of this function
    public List getStockoutList(String searchMode, String orderMode,
                                List sections, String line, String lineDivision,
                                String stockoutStation1, String stockoutStation2,
                                String displayFinishedRetrieval, String showShortageCondition, String startDate, String startDateFlag, int beginningPos, int count)
            throws YKKSQLException {
        StringBuffer b = new StringBuffer();
        b.append("	SELECT *\n" +
                "  FROM (SELECT proc_flag,\n" +
                "               section,\n" +
                "               retrieval_qty,\n" +
                "               retrieval_alloc_qty,\n" +
                "               line,\n" +
                "               line_type,\n" +
                "               retrieval_station,\n" +
                "               start_date,\n" +
                "               start_timing_flag,\n" +
                "               complete_date,\n" +
                "               complete_timing_flag,\n" +
                "               fnsyotei.zaikey,\n" +
                "               retrieval_no,\n" +
                "               zkname1,\n" +
                "               zkname2,\n" +
                "               zkname3,\n" +
                "               color_code,\n" +
                "               necessary_qty,\n" +
                "               allocation_qty,\n" +
                "               retrieval_qty - retrieval_alloc_qty\n" +
                "                  AS need_qty,\n" +
                "               retrieval_plankey,\n" +
                "               (SELECT NVL (TRIM (SUM (skanosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE (    NOT EXISTS\n" +
                "                                   (SELECT 1\n" +
                "                                      FROM fnforbidretrieval\n" +
                "                                     WHERE     fnzaiko.zaikey =\n" +
                "                                                  fnforbidretrieval.zaikey\n" +
                "                                           AND (   fnzaiko.color_code =\n" +
                "                                                      fnforbidretrieval.color_code\n" +
                "                                                OR TRIM (\n" +
                "                                                      fnforbidretrieval.color_code)\n" +
                "                                                      IS NULL)\n" +
                "                                           AND fnforbidretrieval.from_ticketno <=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.to_ticketno >=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.from_stock_datetime <=\n" +
                "                                                  fnzaiko.nyukohiji\n" +
                "                                           AND fnforbidretrieval.to_stock_datetime >=\n" +
                "                                                  fnzaiko.nyukohiji)\n" +
                "                        AND EXISTS\n" +
                "                               (SELECT 1\n" +
                "                                  FROM fnlocat\n" +
                "                                 WHERE     fnlocat.ailestno IN\n" +
                "                                              (SELECT DISTINCT\n" +
                "                                                      fnunit.ailestno\n" +
                "                                                 FROM fnunit\n" +
                "                                                WHERE fnunit.unitstat IN\n" +
                "                                                         ('1', '2', '4'))\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.zaijyoflg NOT IN\n" +
                "                                              ('0', '5', '8')\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.shikiflg = '0'\n" +
                "                                       AND fnlocat.systemid =\n" +
                "                                              fnzaiko.systemid)\n" +
                "                        AND fnzaiko.skanosu > '0'\n" +
                "                        AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                        AND fnzaiko.manage_item_flag = '0'\n" +
                "                        AND fnzaiko.storage_place_flag = '0'\n" +
                "                        AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                        AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                               NVL (TRIM (FNSYOTEI.color_code), ' ')))\n" +
                "                  AS available_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (zaikosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS real_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (DECODE (TRIM (memo), NULL, 0, 1))), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS memo_flag,\n" +
                "               (SELECT NVL (TRIM (SUM (plan_qty)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.weight_report_complete_flag IN ('0', '1')\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS plan_qty\n" +
                "          FROM fnsyotei, fmzkey\n" +
                "         WHERE     fnsyotei.zaikey = fmzkey.zaikey(+)\n" +
                "               AND fmzkey.manage_item_flag = '0'\n" +
                "               AND fnsyotei.retrieval_station NOT IN ('23', '25', '26'))\n" +
                " WHERE 1 = 1");

        String queryString = b.toString();

        if (displayFinishedRetrieval.equals("0")) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }

        if (showShortageCondition.equals("1")) {
            queryString += " AND AVAILABLE_STOCK_QTY >= (necessary_qty - retrieval_alloc_qty)  ";
        } else if (showShortageCondition.equals("2")) {
            queryString += " AND AVAILABLE_STOCK_QTY < (necessary_qty - retrieval_alloc_qty) ";
        }

        if (searchMode.equals("1")) {
            queryString += " AND (";
            for (int i = 0; i < sections.size(); i++) {
                String section = (String) sections.get(i);
                if (i != 0) {
                    queryString += " or ";
                }
                if (section.length() == 3) {
                    queryString += " section = " + StringUtils.surroundWithSingleQuotes(section);
                } else {
                    queryString += " section like " + StringUtils.surroundWithSingleQuotes(section + "%");
                }
            }
            queryString += " )";
            if (!StringUtils.IsNullOrEmpty(line)) {
                queryString += " AND line = "
                        + StringUtils.surroundWithSingleQuotes(line);
            }
        } else if (searchMode.equals("2")) {
            queryString += " AND line_type = "
                    + StringUtils.surroundWithSingleQuotes(lineDivision);
            queryString += " AND retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes("24");
        } else {
            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
                    && StringUtils.IsNullOrEmpty(stockoutStation1)) {
                queryString += " AND retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && !StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND retrieval_station IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2)
                        + ")";
            }
        }

        if (!StringUtils.IsNullOrEmpty(startDate)) {
            queryString += " AND start_date = "
                    + StringUtils
                    .surroundWithSingleQuotes(startDate);
        }

        if (!StringUtils.IsNullOrEmpty(startDateFlag)) {
            queryString += " AND start_timing_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(startDateFlag);
        }

        int totalCount = getStockoutCount(searchMode, sections, line,
                lineDivision, stockoutStation1, stockoutStation2,
                displayFinishedRetrieval, showShortageCondition,
                startDate, startDateFlag
        );

        if (totalCount <= 8000) {
            if (orderMode.equals("1")) {
                queryString += " ORDER BY start_date,start_timing_flag";
            } else if (orderMode.equals("2")) {
                queryString += " ORDER BY complete_date,complete_timing_flag,start_date,start_timing_flag";
            } else if (orderMode.equals("3")) {
                queryString += " ORDER BY zaikey,start_date,start_timing_flag";
            } else {
                queryString += " ORDER BY retrieval_no,start_date,start_timing_flag";
            }
        } else {
            queryString += " ORDER BY start_date DESC, start_timing_flag DESC";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutEntity entity = new StockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setWhenThisWorkFinishInPlan(row
                            .getValueByColumnName("complete_date")
            );
            entity.setWhenThisWorkFinishInPlanTiming(row
                            .getValueByColumnName("complete_timing_flag")
            );
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("need_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            try {
                entity.setEnableToStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }

            entity.setColor(row.getValueByColumnName("color_code"));

            entity
                    .setMemo(row.getValueByColumnName("memo_flag").equals("0") ? ""
                                    : "有"
                    );
            try {
                entity.setStockoutNecessaryQty(Long.parseLong(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }

            entity.setRetrieved(row.getValueByColumnName("proc_flag").equals(
                            DBFlags.ProcFlag.DEALT
                    )
            );

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            try {
                entity.setTotalCountInstock(Long.parseLong(row
                                        .getValueByColumnName("real_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalCountInstock(0);
            }

            try {
                entity.setPlanQty(Long.parseLong(row
                                        .getValueByColumnName("plan_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setPlanQty(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getStockoutList()
            throws YKKSQLException {
        StringBuffer b = new StringBuffer();
        b.append("	SELECT *\n" +
                "  FROM (SELECT proc_flag,\n" +
                "               section,\n" +
                "               retrieval_qty,\n" +
                "               retrieval_alloc_qty,\n" +
                "               line,\n" +
                "               line_type,\n" +
                "               retrieval_station,\n" +
                "               start_date,\n" +
                "               start_timing_flag,\n" +
                "               complete_date,\n" +
                "               complete_timing_flag,\n" +
                "               fnsyotei.zaikey,\n" +
                "               retrieval_no,\n" +
                "               zkname1,\n" +
                "               zkname2,\n" +
                "               zkname3,\n" +
                "               color_code,\n" +
                "               necessary_qty,\n" +
                "               allocation_qty,\n" +
                "               retrieval_qty - retrieval_alloc_qty\n" +
                "                  AS need_qty,\n" +
                "               retrieval_plankey,\n" +
                "               (SELECT NVL (TRIM (SUM (skanosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE (    NOT EXISTS\n" +
                "                                   (SELECT 1\n" +
                "                                      FROM fnforbidretrieval\n" +
                "                                     WHERE     fnzaiko.zaikey =\n" +
                "                                                  fnforbidretrieval.zaikey\n" +
                "                                           AND (   fnzaiko.color_code =\n" +
                "                                                      fnforbidretrieval.color_code\n" +
                "                                                OR TRIM (\n" +
                "                                                      fnforbidretrieval.color_code)\n" +
                "                                                      IS NULL)\n" +
                "                                           AND fnforbidretrieval.from_ticketno <=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.to_ticketno >=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.from_stock_datetime <=\n" +
                "                                                  fnzaiko.nyukohiji\n" +
                "                                           AND fnforbidretrieval.to_stock_datetime >=\n" +
                "                                                  fnzaiko.nyukohiji)\n" +
                "                        AND EXISTS\n" +
                "                               (SELECT 1\n" +
                "                                  FROM fnlocat\n" +
                "                                 WHERE     fnlocat.ailestno IN\n" +
                "                                              (SELECT DISTINCT\n" +
                "                                                      fnunit.ailestno\n" +
                "                                                 FROM fnunit\n" +
                "                                                WHERE fnunit.unitstat IN\n" +
                "                                                         ('1', '2', '4'))\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.zaijyoflg NOT IN\n" +
                "                                              ('0', '5', '8')\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.shikiflg = '0'\n" +
                "                                       AND fnlocat.systemid =\n" +
                "                                              fnzaiko.systemid)\n" +
                "                        AND fnzaiko.skanosu > '0'\n" +
                "                        AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                        AND fnzaiko.manage_item_flag = '0'\n" +
                "                        AND fnzaiko.storage_place_flag = '0'\n" +
                "                        AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                        AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                               NVL (TRIM (FNSYOTEI.color_code), ' ')))\n" +
                "                  AS available_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (zaikosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS real_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (DECODE (TRIM (memo), NULL, 0, 1))), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS memo_flag,\n" +
                "               (SELECT NVL (TRIM (SUM (plan_qty)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.weight_report_complete_flag IN ('0', '1')\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS plan_qty\n" +
                "          FROM fnsyotei, fmzkey\n" +
                "         WHERE     fnsyotei.zaikey = fmzkey.zaikey(+)\n" +
                "               AND fmzkey.manage_item_flag = '0'\n" +
                "               AND fnsyotei.retrieval_station NOT IN ('23', '25', '26'))\n" +
                " WHERE 1 = 1");

        String queryString = b.toString();


        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, 0, 8000);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutEntity entity = new StockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setWhenThisWorkFinishInPlan(row
                            .getValueByColumnName("complete_date")
            );
            entity.setWhenThisWorkFinishInPlanTiming(row
                            .getValueByColumnName("complete_timing_flag")
            );
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("need_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            try {
                entity.setEnableToStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }

            entity.setColor(row.getValueByColumnName("color_code"));

            entity
                    .setMemo(row.getValueByColumnName("memo_flag").equals("0") ? ""
                                    : "有"
                    );
            try {
                entity.setStockoutNecessaryQty(Long.parseLong(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }

            entity.setRetrieved(row.getValueByColumnName("proc_flag").equals(
                            DBFlags.ProcFlag.DEALT
                    )
            );

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            try {
                entity.setTotalCountInstock(Long.parseLong(row
                                        .getValueByColumnName("real_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalCountInstock(0);
            }

            try {
                entity.setPlanQty(Long.parseLong(row
                                        .getValueByColumnName("plan_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setPlanQty(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public int getStockoutStartCount(String searchMode, String section,
                                     String line, String lineDivision, String stockoutStation1,
                                     String stockoutStation2, String retrievalNo) throws YKKSQLException {
        // String queryString =
        // "SELECT COUNT(*) FROM (SELECT * FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI
        // WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND
        // FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid =
        // FNLOCAT.systemid(+) AND FNHANSO.hjyotaiflg = "
        String queryString = "SELECT COUNT(DISTINCT(FNLOCAT.syozaikey))AS c FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND FNHANSO.hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNLOCAT.zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) AND TRIM(FNLOCAT.syozaikey) IS NOT NULL "
                + " AND fnsyotei.retrieval_station NOT IN ('23', '25', '26')";
        if (searchMode.equals("1")) {
            queryString += " AND FNSIJI.section = "
                    + StringUtils.surroundWithSingleQuotes(section);
            if (!StringUtils.IsNullOrEmpty(line)) {
                queryString += " AND FNSIJI.line = "
                        + StringUtils.surroundWithSingleQuotes(line);
            }
        } else if (searchMode.equals("2")) {
            queryString += " AND FNSIJI.line_type = "
                    + StringUtils.surroundWithSingleQuotes(lineDivision);
            queryString += " AND FNSYOTEI.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes("24");
        } else {
            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
                    && StringUtils.IsNullOrEmpty(stockoutStation1)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && !StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2)
                        + ")";
            }
        }
        queryString += " AND FNSYOTEI.retrieval_no LIKE "
                + StringUtils.surroundWithSingleQuotes(retrievalNo
                        + StringUtils.SinglePercentageMark
        );
        // queryString += " GROUP BY syozaikey)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils.IsNullOrEmpty(row.getValueByColumnName("c"))) {
                count = row.getValueByColumnName("c");
            }
        }
        return Integer.parseInt(count);
    }

    public List getStockoutStartList(String searchMode, String orderMode,
                                     String section, String line, String lineDivision,
                                     String stockoutStation1, String stockoutStation2,
                                     String retrievalNo, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNLOCAT.syozaikey,FNZAIKO.zaikosu,FNHANSO.systemid,FNZAIKO.ticket_no";

        String queryString = "SELECT "
                + columnList
                + ",SUM(FNSIJI.nyusyusu),SUM(nvl(trim(FNSIJI.subdivide_flag),0)) AS subdivision"
                + " FROM FNSIJI,FNHANSO,FNZAIKO,FNLOCAT,FMZKEY,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNLOCAT.zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) AND TRIM(FNLOCAT.syozaikey) IS NOT NULL "
                + " AND fnsyotei.retrieval_station NOT IN ('23', '25', '26')";
        if (searchMode.equals("1")) {
            queryString += " AND FNSIJI.section = "
                    + StringUtils.surroundWithSingleQuotes(section);
            if (!StringUtils.IsNullOrEmpty(line)) {
                queryString += " AND FNSIJI.line = "
                        + StringUtils.surroundWithSingleQuotes(line);
            }
        } else if (searchMode.equals("2")) {
            queryString += " AND FNSIJI.line_type = "
                    + StringUtils.surroundWithSingleQuotes(lineDivision);
            queryString += " AND FNSYOTEI.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes("24");
        } else {
            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
                    && StringUtils.IsNullOrEmpty(stockoutStation1)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && !StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2)
                        + ")";
            }

        }
        queryString += " AND FNSYOTEI.retrieval_no LIKE "
                + StringUtils.surroundWithSingleQuotes(retrievalNo
                        + StringUtils.SinglePercentageMark
        );
        queryString += " GROUP BY " + columnList;

        if (orderMode.equals("1")) {
            queryString += " ORDER BY FNZAIKO.zaikey";
        } else {
            queryString += " ORDER BY FNZAIKO.nyukohiji";
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutStartEntity entity = new StockoutStartEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            setMckey(entity, searchMode, section, line, lineDivision,
                    stockoutStation1, stockoutStation2, retrievalNo
            );
            if ((Integer.parseInt(row.getValueByColumnName("subdivision"))) > 0) {
                entity.setSubDivide(StringUtils.Circle);
            } else {
                entity.setSubDivide("");
            }
            int nyusyusu = 0;
            int zaikosu = 0;
            try {
                nyusyusu = Integer.parseInt(row
                                .getValueByColumnName("SUM(FNSIJI.nyusyusu)")
                );
                zaikosu = Integer.parseInt(row.getValueByColumnName("zaikosu"));
                entity.setStockoutCount(nyusyusu);
            } catch (Exception ex) {
                entity.setU("Erro");
                entity.setP("Erro");
            }

            if (nyusyusu < zaikosu) {
                entity.setP(StringUtils.Circle);
                entity.setU("");
            } else {
                entity.setU(StringUtils.Circle);
                entity.setP("");
            }

            returnList.add(entity);

        }

        return returnList;
    }

    public List getStockoutStartList()
            throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNLOCAT.syozaikey,FNZAIKO.zaikosu,FNHANSO.systemid,FNZAIKO.ticket_no";

        String queryString = "SELECT "
                + columnList
                + ",SUM(FNSIJI.nyusyusu),SUM(nvl(trim(FNSIJI.subdivide_flag),0)) AS subdivision"
                + " FROM FNSIJI,FNHANSO,FNZAIKO,FNLOCAT,FMZKEY,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNLOCAT.zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) AND TRIM(FNLOCAT.syozaikey) IS NOT NULL "
                + " AND fnsyotei.retrieval_station NOT IN ('23', '25', '26')";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, 0, 8000);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutStartEntity entity = new StockoutStartEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            setMckey(entity);
            if ((Integer.parseInt(row.getValueByColumnName("subdivision"))) > 0) {
                entity.setSubDivide(StringUtils.Circle);
            } else {
                entity.setSubDivide("");
            }
            int nyusyusu = 0;
            int zaikosu = 0;
            try {
                nyusyusu = Integer.parseInt(row
                                .getValueByColumnName("SUM(FNSIJI.nyusyusu)")
                );
                zaikosu = Integer.parseInt(row.getValueByColumnName("zaikosu"));
                entity.setStockoutCount(nyusyusu);
            } catch (Exception ex) {
                entity.setU("Erro");
                entity.setP("Erro");
            }

            if (nyusyusu < zaikosu) {
                entity.setP(StringUtils.Circle);
                entity.setU("");
            } else {
                entity.setU(StringUtils.Circle);
                entity.setP("");
            }

            returnList.add(entity);

        }

        return returnList;
    }

    public StopPrintLabelSettingEntity getStopPrintLabelSettingList(
            String station) throws YKKSQLException {
        String columnList = "unify_ticket_printflg,ticket_printflg,cart_ticket_printflg";
        String queryString = "SELECT " + columnList
                + " FROM FNRETRIEVAL_ST WHERE retrieval_station = "
                + StringUtils.surroundWithSingleQuotes(station);
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StopPrintLabelSettingEntity entity = new StopPrintLabelSettingEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setCart_ticket_printflg(row
                            .getValueByColumnName("cart_ticket_printflg")
            );
            entity.setTicket_printflg(row
                            .getValueByColumnName("ticket_printflg")
            );
            entity.setUnify_ticket_printflg(row
                            .getValueByColumnName("unify_ticket_printflg")
            );
            returnList.add(entity);
        }

        if (returnList.size() > 0) {
            return (StopPrintLabelSettingEntity) returnList.get(0);
        } else {
            return null;
        }
    }

    public int getStorageInfoCount(StorageInfoHead head) throws YKKSQLException {
        String columnList = "FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code";
        String queryString = "SELECT COUNT(*) FROM (SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (!StringUtils.IsNullOrEmpty(head.getItemCode())) {
            queryString += "AND FNZAIKO.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND FNZAIKO.color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }
        queryString += " GROUP BY " + columnList + ")";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getStorageInfoListSQL(StorageInfoHead head) {
        StringBuffer b = new StringBuffer();
        b.append("	WITH stock_info_temp	");
        b.append("	       AS (  SELECT   zaikey,	");
        b.append("	                      color_code,	");
        b.append("	                      manage_item_flag,	");
        b
                .append("	                      nvl(trim(SUM (auto_total)),0) AS auto_total,	");
        b
                .append("	                      nvl(trim(SUM (flat_total)),0) AS flat_total,	");
        b
                .append("	                      (nvl(trim(SUM (auto_total)),0) + nvl(trim(SUM (flat_total)),0)) AS all_total,	");
        b
                .append("	                      nvl(trim(SUM (not_in_total)),0) AS not_in_total	");
        b
                .append("	               FROM   (                                            /* 自动库*/	");
        b.append("	                       SELECT     zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b
                .append("	                                  SUM (zaikosu) AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /*平库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b
                .append("	                                  SUM (zaikosu) AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '1'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /* 未入库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b
                .append("	                                  SUM (plan_qty) AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag)	");
        b
                .append("	           GROUP BY   zaikey, color_code, manage_item_flag)	");
        b.append("	SELECT   stock_info_temp.zaikey,	");
        b.append("	         fmzkey.zkname1,	");
        b.append("	         fmzkey.zkname2,	");
        b.append("	         fmzkey.zkname3,	");
        b.append("	         stock_info_temp.color_code,	");
        b.append("	         stock_info_temp.auto_total,	");
        b.append("	         stock_info_temp.flat_total,	");
        b.append("	         stock_info_temp.all_total,	");
        b.append("	         stock_info_temp.not_in_total	");
        b.append("	  FROM   stock_info_temp, fmzkey	");
        b.append("	 WHERE   stock_info_temp.zaikey = fmzkey.zaikey(+)	");
        b
                .append("	         AND stock_info_temp.manage_item_flag = fmzkey.manage_item_flag(+)	");

        String queryString = b.toString();
        if (!StringUtils.IsNullOrEmpty(head.getItemCode())) {
            queryString += " AND stock_info_temp.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND stock_info_temp.color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }

        queryString += " ORDER BY stock_info_temp.zaikey,stock_info_temp.color_code";

        return queryString;
    }

    public List getStorageInfoList(StorageInfoHead head) throws YKKSQLException {
        // String columnList =
        // "FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code"
        // ;
        // String queryString = "SELECT "
        // + columnList
        // +
        // " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) "
        // ;

        StringBuffer b = new StringBuffer();
        b.append("	WITH stock_info_temp	");
        b.append("	       AS (  SELECT   zaikey,	");
        b.append("	                      color_code,	");
        b.append("	                      manage_item_flag,	");
        b
                .append("	                      nvl(trim(SUM (auto_total)),0) AS auto_total,	");
        b
                .append("	                      nvl(trim(SUM (flat_total)),0) AS flat_total,	");
        b
                .append("	                      (nvl(trim(SUM (auto_total)),0) + nvl(trim(SUM (flat_total)),0)) AS all_total,	");
        b
                .append("	                      nvl(trim(SUM (not_in_total)),0) AS not_in_total	");
        b
                .append("	               FROM   (                                            /* 自动库*/	");
        b.append("	                       SELECT     zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b
                .append("	                                  SUM (zaikosu) AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /*平库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b
                .append("	                                  SUM (zaikosu) AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '1'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /* 未入库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b
                .append("	                                  SUM (plan_qty) AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag)	");
        b
                .append("	           GROUP BY   zaikey, color_code, manage_item_flag)	");
        b.append("	SELECT   stock_info_temp.zaikey,	");
        b.append("	         fmzkey.zkname1,	");
        b.append("	         fmzkey.zkname2,	");
        b.append("	         fmzkey.zkname3,	");
        b.append("	         stock_info_temp.color_code,	");
        b.append("	         stock_info_temp.auto_total,	");
        b.append("	         stock_info_temp.flat_total,	");
        b.append("	         stock_info_temp.all_total,	");
        b.append("	         stock_info_temp.not_in_total	");
        b.append("	  FROM   stock_info_temp, fmzkey	");
        b.append("	 WHERE   stock_info_temp.zaikey = fmzkey.zaikey(+)	");
        b
                .append("	         AND stock_info_temp.manage_item_flag = fmzkey.manage_item_flag(+)	");

        String queryString = b.toString();
        if (!StringUtils.IsNullOrEmpty(head.getItemCode())) {
            queryString += " AND stock_info_temp.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND stock_info_temp.color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }

        // queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY stock_info_temp.zaikey,stock_info_temp.color_code";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StorageInfoEntity entity = new StorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setAutoCount(Long.parseLong(row
                                    .getValueByColumnName("auto_total")
                    )
            );
            entity.setFlatCount(Long.parseLong(row
                                    .getValueByColumnName("flat_total")
                    )
            );
            try {
                entity.setTotalInstockCount(Long.parseLong(row
                                        .getValueByColumnName("all_total")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalInstockCount(0);
            }
            try {
                entity.setNotStockinCount(Long.parseLong(row
                                        .getValueByColumnName("not_in_total")
                        )
                );
            } catch (Exception ex) {
                entity.setNotStockinCount(0);
            }

            // setAutoCount(entity);
            // setFlatCount(entity);
            // setTotalCount(entity);
            // setUninstockCount(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public List getStorageInfoList(StorageInfoHead head, int beginningPos,
                                   int count) throws YKKSQLException {
        // String columnList =
        // "FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code"
        // ;
        // String queryString = "SELECT "
        // + columnList
        // +
        // " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) "
        // ;
        // if (!StringUtils.IsNullOrEmpty(head.getItemCode()))
        // {
        // queryString += "AND FNZAIKO.zaikey = "
        // + StringUtils.surroundWithSingleQuotes(head.getItemCode());
        // }
        // if (!StringUtils.IsNullOrEmpty(head.getColorCode()))
        // {
        // queryString += " AND FNZAIKO.color_code = "
        // + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        // }
        // queryString += " GROUP BY " + columnList;
        // queryString += " ORDER BY FNZAIKO.zaikey,FNZAIKO.color_code";

        StringBuffer b = new StringBuffer();
        b.append("	WITH stock_info_temp	");
        b.append("	       AS (  SELECT   zaikey,	");
        b.append("	                      color_code,	");
        b.append("	                      manage_item_flag,	");
        b
                .append("	                      nvl(trim(SUM (auto_total)),0) AS auto_total,	");
        b
                .append("	                      nvl(trim(SUM (flat_total)),0) AS flat_total,	");
        b
                .append("	                      (nvl(trim(SUM (auto_total)),0) + nvl(trim(SUM (flat_total)),0)) AS all_total,	");
        b
                .append("	                      nvl(trim(SUM (not_in_total)),0) AS not_in_total	");
        b
                .append("	               FROM   (                                            /* 自动库*/	");
        b.append("	                       SELECT     zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b
                .append("	                                  SUM (zaikosu) AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /*平库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b
                .append("	                                  SUM (zaikosu) AS flat_total,	");
        b.append("	                                  0 AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag <> '0'	");
        b
                .append("	                                  AND storage_place_flag = '1'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag	");
        b.append("	                       UNION	");
        b.append("	                         /* 未入库*/	");
        b.append("	                         SELECT   zaikey,	");
        b.append("	                                  color_code,	");
        b.append("	                                  manage_item_flag,	");
        b.append("	                                  0 AS auto_total,	");
        b.append("	                                  0 AS flat_total,	");
        b
                .append("	                                  SUM (plan_qty) AS not_in_total	");
        b.append("	                           FROM   FNZAIKO	");
        b
                .append("	                          WHERE   weight_report_complete_flag = '0'	");
        b
                .append("	                       GROUP BY   zaikey, color_code, manage_item_flag)	");
        b
                .append("	           GROUP BY   zaikey, color_code, manage_item_flag)	");
        b.append("	SELECT   stock_info_temp.zaikey,	");
        b.append("	         fmzkey.zkname1,	");
        b.append("	         fmzkey.zkname2,	");
        b.append("	         fmzkey.zkname3,	");
        b.append("	         stock_info_temp.color_code,	");
        b.append("	         stock_info_temp.auto_total,	");
        b.append("	         stock_info_temp.flat_total,	");
        b.append("	         stock_info_temp.all_total,	");
        b.append("	         stock_info_temp.not_in_total	");
        b.append("	  FROM   stock_info_temp, fmzkey	");
        b.append("	 WHERE   stock_info_temp.zaikey = fmzkey.zaikey(+)	");
        b
                .append("	         AND stock_info_temp.manage_item_flag = fmzkey.manage_item_flag(+)	");

        String queryString = b.toString();
        if (!StringUtils.IsNullOrEmpty(head.getItemCode())) {
            queryString += " AND stock_info_temp.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND stock_info_temp.color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }

        // queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY stock_info_temp.zaikey,stock_info_temp.color_code";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StorageInfoEntity entity = new StorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setAutoCount(Long.parseLong(row
                                    .getValueByColumnName("auto_total")
                    )
            );
            entity.setFlatCount(Long.parseLong(row
                                    .getValueByColumnName("flat_total")
                    )
            );
            entity.setTotalInstockCount(Long.parseLong(row
                                    .getValueByColumnName("all_total")
                    )
            );
            entity.setNotStockinCount(Long.parseLong(row
                                    .getValueByColumnName("not_in_total")
                    )
            );
            // setAutoCount(entity);
            // setFlatCount(entity);
            // setTotalCount(entity);
            // setUninstockCount(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public int getTerminalViewCount(TerminalViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNRANGE WHERE termno LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(entity.getTerminalNo())
        )
                + " AND made_section LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(entity.getSection())
        )
                + " AND made_line LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(entity.getLine())
        );

        if (entity.getViewType() == 1) {
            queryString += " AND trim(termno) is null";
        } else if (entity.getViewType() == 2) {
            queryString += " AND trim(made_section) is null"
                    + " AND trim(made_line) is null";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getTerminalViewList(TerminalViewEntity en, int beginningPos,
                                    int count) throws YKKSQLException {
        String columnList = "termno,made_section,made_line,unit_weight_upper,unit_weight_lower,storage_upper,storage_lower,ship_upper,ship_lower";
        String queryString = "SELECT "
                + columnList
                + " FROM FNRANGE WHERE termno LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(en.getTerminalNo())
        )
                + " AND made_section LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(en.getSection())
        )
                + " AND made_line LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(en.getLine())
        );

        if (en.getViewType() == 1) {
            queryString += " AND trim(termno) is null";
        } else if (en.getViewType() == 2) {
            queryString += " AND trim(made_section) is null"
                    + " AND trim(made_line) is null";
        }

        queryString += " ORDER BY termno, made_section, made_line";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TerminalViewEntity entity = new TerminalViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTerminalNo(row.getValueByColumnName("termno"));
            entity.setSection(row.getValueByColumnName("made_section"));
            entity.setLine(row.getValueByColumnName("made_line"));

            try {
                entity.setUnitWeightUpper(new BigDecimal(row
                                .getValueByColumnName("unit_weight_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setUnitWeightUpper(new BigDecimal(0));
            }
            try {
                entity.setUnitWeightLower(new BigDecimal(row
                                .getValueByColumnName("unit_weight_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setUnitWeightLower(new BigDecimal(0));
            }
            try {
                entity.setStorageUpper(new BigDecimal(row
                                .getValueByColumnName("storage_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setStorageUpper(new BigDecimal(0));
            }
            try {
                entity.setStorageLower(new BigDecimal(row
                                .getValueByColumnName("storage_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setStorageLower(new BigDecimal(0));
            }
            try {
                entity.setShipUpper(new BigDecimal(row
                                .getValueByColumnName("ship_upper")
                        )
                );
            } catch (Exception ex) {
                entity.setShipUpper(new BigDecimal(0));
            }
            try {
                entity.setShipLower(new BigDecimal(row
                                .getValueByColumnName("ship_lower")
                        )
                );
            } catch (Exception ex) {
                entity.setShipLower(new BigDecimal(0));
            }
            returnList.add(entity);
        }
        return returnList;
    }

    public TicketNoViewEntity getTicketNoEntity(String ticketNo)
            throws YKKSQLException {
        String columnList = "FNZAIKO.ticket_no,FNZAIKO.zaikey,FNZAIKO.zaikosu,FNZAIKO.color_code,FMZKEY.zkname1";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.ticket_no = "
                + StringUtils.surroundWithSingleQuotes(ticketNo)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND FNZAIKO.weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TicketNoViewEntity entity = new TicketNoViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname1"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }

        if (returnList.size() > 0) {
            return (TicketNoViewEntity) returnList.get(0);
        } else {
            return null;
        }
    }

    public int getTicketNoItemStorageInfoCount(TicketNoItemStorageInfoHead head)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepo().equals("自动仓库")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);
        } else if (head.getDepo().equals("平置")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT);
        } else if (head.getDepo().equals("未入库仓库")) {
            queryString += " AND weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
        }
        if (head.getDivision().equals("生产传票")) {
            if (head.isRangeSet()
                    && !StringUtils.IsNullOrEmpty(head.getTicketNoTo())) {
                queryString += " AND ticket_no BETWEEN "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                )
                        + " AND "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoTo()
                );
            } else if (head.isRangeSet()) {
                queryString += " AND ticket_no >= "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            } else {
                queryString += " AND ticket_no = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            }
        } else if (head.getDivision().equals("物料编号")) {
            queryString += " AND FNZAIKO.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
            if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getColorCode()
                );
            }
        } else {
            queryString += " AND FNZAIKO.bucket_no = "
                    + StringUtils.surroundWithSingleQuotes(head.getBucketNo());
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public static String getTicketNoItemStorageInfoListSQL(
            TicketNoItemStorageInfoHead head) {
        String columnList = "FNLOCAT.syozaikey,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.plan_qty,FNZAIKO.nyukohiji,FNZAIKO.storage_place_flag,FNZAIKO.weight_report_complete_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepo().equals("自动仓库")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);
        } else if (head.getDepo().equals("平置")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT);
        } else if (head.getDepo().equals("未入库仓库")) {
            queryString += " AND weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
        }
        if (head.getDivision().equals("生产传票")) {
            if (head.isRangeSet()
                    && !StringUtils.IsNullOrEmpty(head.getTicketNoTo())) {
                queryString += " AND ticket_no BETWEEN "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                )
                        + " AND "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoTo()
                );
            } else if (head.isRangeSet()) {
                queryString += " AND ticket_no >= "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            } else {
                queryString += " AND ticket_no = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            }
            queryString += " ORDER BY ticket_no,zaikey,color_code,nyukohiji";
        } else if (head.getDivision().equals("物料编号")) {
            queryString += " AND FNZAIKO.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
            if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getColorCode()
                );
            }
            queryString += " ORDER BY zaikey,color_code,nyukohiji,ticket_no";
        } else {
            queryString += " AND FNZAIKO.bucket_no = "
                    + StringUtils.surroundWithSingleQuotes(head.getBucketNo());
        }

        return queryString;
    }

    public List getTicketNoItemStorageInfoList(TicketNoItemStorageInfoHead head)
            throws YKKSQLException {
        String columnList = "FNLOCAT.syozaikey,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.plan_qty,FNZAIKO.nyukohiji,FNZAIKO.storage_place_flag,FNZAIKO.weight_report_complete_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepo().equals("自动仓库")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);
        } else if (head.getDepo().equals("平置")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT);
        } else if (head.getDepo().equals("未入库仓库")) {
            queryString += " AND weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
        }
        if (head.getDivision().equals("生产传票")) {
            if (head.isRangeSet()
                    && !StringUtils.IsNullOrEmpty(head.getTicketNoTo())) {
                queryString += " AND ticket_no BETWEEN "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                )
                        + " AND "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoTo()
                );
            } else if (head.isRangeSet()) {
                queryString += " AND ticket_no >= "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            } else {
                queryString += " AND ticket_no = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            }
            queryString += " ORDER BY ticket_no,zaikey,color_code,nyukohiji";
        } else if (head.getDivision().equals("物料编号")) {
            queryString += " AND FNZAIKO.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
            if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getColorCode()
                );
            }
            queryString += " ORDER BY zaikey,color_code,nyukohiji,ticket_no";
        } else {
            queryString += " AND FNZAIKO.bucket_no = "
                    + StringUtils.surroundWithSingleQuotes(head.getBucketNo());
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TicketNoItemStorageInfoEntity entity = new TicketNoItemStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            int planQty = Integer.parseInt(row
                    .getValueByColumnName("plan_qty"));

            if (row.getValueByColumnName("weight_report_complete_flag").equals(
                    DBFlags.WeightReportCompleteFlag.UNCOMPLETED
            ) && planQty != 0) {
                entity.setLocationNo("未入库仓库");
            } else if (row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.FLAT
            )) {
                entity.setLocationNo("平库");
            } else {
                entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            }
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setMessageDateTime(row.getValueByColumnName("nyukohiji"));
            try {
                if (row.getValueByColumnName("weight_report_complete_flag").equals(
                        DBFlags.WeightReportCompleteFlag.UNCOMPLETED
                ) && planQty != 0) {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("plan_qty")
                            )
                    );
                } else {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );
                }
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getTicketNoItemStorageInfoList(
            TicketNoItemStorageInfoHead head, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNLOCAT.syozaikey,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNZAIKO.ticket_no,FNZAIKO.bucket_no,FNZAIKO.zaikosu,FNZAIKO.plan_qty,FNZAIKO.nyukohiji,FNZAIKO.storage_place_flag,FNZAIKO.weight_report_complete_flag";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";
        if (head.getDepo().equals("自动仓库")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);
        } else if (head.getDepo().equals("平置")) {
            queryString += " AND weight_report_complete_flag <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                    + " AND storage_place_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT);
        } else if (head.getDepo().equals("未入库仓库")) {
            queryString += " AND weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED);
        }
        if (head.getDivision().equals("生产传票")) {
            if (head.isRangeSet()
                    && !StringUtils.IsNullOrEmpty(head.getTicketNoTo())) {
                queryString += " AND ticket_no BETWEEN "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                )
                        + " AND "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoTo()
                );
            } else if (head.isRangeSet()) {
                queryString += " AND ticket_no >= "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            } else {
                queryString += " AND ticket_no = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getTicketNoFrom()
                );
            }
            queryString += " ORDER BY ticket_no,zaikey,color_code,nyukohiji";
        } else if (head.getDivision().equals("物料编号")) {
            queryString += " AND FNZAIKO.zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItemCode());
            if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
                queryString += " AND FNZAIKO.color_code = "
                        + StringUtils.surroundWithSingleQuotes(head
                                .getColorCode()
                );
            }
            queryString += " ORDER BY zaikey,color_code,nyukohiji,ticket_no";
        } else {
            queryString += " AND FNZAIKO.bucket_no = "
                    + StringUtils.surroundWithSingleQuotes(head.getBucketNo());
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TicketNoItemStorageInfoEntity entity = new TicketNoItemStorageInfoEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            int planQty = Integer.parseInt(row
                    .getValueByColumnName("plan_qty"));

            if (row.getValueByColumnName("weight_report_complete_flag").equals(
                    DBFlags.WeightReportCompleteFlag.UNCOMPLETED
            ) && planQty != 0) {
                entity.setLocationNo("未入库仓库");
            } else if (row.getValueByColumnName("storage_place_flag").equals(
                    DBFlags.StoragePlaceFlag.FLAT
            )) {
                entity.setLocationNo("平库");
            } else {
                entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            }
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setMessageDateTime(row.getValueByColumnName("nyukohiji"));
            try {
                if (row.getValueByColumnName("weight_report_complete_flag").equals(
                        DBFlags.WeightReportCompleteFlag.UNCOMPLETED
                ) && planQty != 0) {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("plan_qty")
                            )
                    );
                } else {
                    entity.setInstockCount(Integer.parseInt(row
                                            .getValueByColumnName("zaikosu")
                            )
                    );
                }
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public int getTicketNoViewCount(String ticketNo) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.ticket_no LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(ticketNo)
        )
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND FNZAIKO.weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getTicketNoViewList(String ticketNo, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNZAIKO.ticket_no,FNZAIKO.zaikey,FNZAIKO.zaikosu,FNZAIKO.color_code,FMZKEY.zkname1";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.ticket_no LIKE "
                + StringUtils.surroundWithSingleQuotes(StringUtils
                        .surroundWithPercentageMarks(ticketNo)
        )
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND FNZAIKO.manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND FNZAIKO.weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            TicketNoViewEntity entity = new TicketNoViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname1"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    private String getTransferCount(String hansokey) throws YKKSQLException {
        String queryString = "SELECT SUM(nyusyusu) FROM FNSIJI WHERE FNSIJI.hansokey = "
                + StringUtils.surroundWithSingleQuotes(hansokey);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("SUM(nyusyusu)"));
        }
        if (returnList.size() > 0) {
            return String.valueOf(returnList.get(0));
        } else {
            return "0";
        }

    }

    public List getUnmanagedItemNameList(String itemCode)
            throws YKKSQLException {
        String columnList = "zkname1,zkname2,zkname3";

        String queryString = "SELECT "
                + columnList
                + " FROM FMZKEY WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ItemViewEntity entity = new ItemViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            returnList.add(entity);
        }

        return returnList;
    }

    public int getUnmanagedStockoutDetailCount(
            UnmanagedStockoutEntity unmanagedStockoutEntity)
            throws YKKSQLException {
        String columnList = "FMZKEY.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNLOCAT.syozaikey,FNZAIKO.reception_datetime,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid";

        String queryString = "SELECT COUNT(*) FROM (SELECT * FROM FMZKEY,FNZAIKO,FNLOCAT,FNUNIT ";

        if (unmanagedStockoutEntity.getStockoutMode() == 1) {
            queryString += " WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + " AND FNZAIKO.zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(unmanagedStockoutEntity
                                    .getItemCode()
                    );
        } else {
            queryString += " WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + "AND FNLOCAT.zaijyoflg NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                    + " ) " + " AND FNLOCAT.syozaikey ";
            if (unmanagedStockoutEntity.isAfterThisLocation()) {

                queryString += " >= ";
            } else {
                queryString += " = ";
            }
            queryString += StringUtils
                    .surroundWithSingleQuotes(unmanagedStockoutEntity
                                    .getLocationNo()
                    );
        }

        queryString += " AND FNLOCAT.zaijyoflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.hikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.HikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNUNIT.unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")" + " AND FNZAIKO.skanosu > 0 ";
        queryString += " GROUP BY " + columnList + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getUnmanagedStockoutDetailList(
            UnmanagedStockoutEntity unmanagedStockoutEntity, int beginningPos,
            int count) throws YKKSQLException {
        String columnList = "FMZKEY.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNLOCAT.syozaikey,FNZAIKO.reception_datetime,FNZAIKO.color_code,FNZAIKO.zaikosu,FNZAIKO.systemid";

        String queryString = "SELECT " + columnList
                + " FROM FMZKEY,FNZAIKO,FNLOCAT,FNUNIT ";
        if (unmanagedStockoutEntity.getStockoutMode() == 1) {
            queryString += " WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + " AND FNZAIKO.zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(unmanagedStockoutEntity
                                    .getItemCode()
                    );
        } else {
            queryString += " WHERE FNZAIKO.zaikey = FMZKEY.zaikey AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag AND FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.ailestno = FNUNIT.ailestno AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + "AND FNLOCAT.zaijyoflg NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                    + " ) " + " AND FNLOCAT.syozaikey ";
            if (unmanagedStockoutEntity.isAfterThisLocation()) {

                queryString += " >= ";
            } else {
                queryString += " = ";
            }
            queryString += StringUtils
                    .surroundWithSingleQuotes(unmanagedStockoutEntity
                                    .getLocationNo()
                    );
        }

        queryString += " AND FNLOCAT.zaijyoflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.INSTOCK)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.hikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.HikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNUNIT.unitstat IN ("
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.NORMAL)
                + ","
                + StringUtils.surroundWithSingleQuotes(DBFlags.UnitStat.STOP)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.UnitStat.TROUBLE)
                + ")" + " AND FNZAIKO.skanosu > 0 ";
        queryString += " GROUP BY " + columnList;

        if (unmanagedStockoutEntity.getOrderMode() == 1) {
            queryString += " ORDER BY syozaikey";
        } else if (unmanagedStockoutEntity.getOrderMode() == 2) {
            queryString += " ORDER BY zaikey";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            UnmanagedStockoutDetailEntity entity = new UnmanagedStockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setStockinDateTime(row
                            .getValueByColumnName("reception_datetime")
            );
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));

            returnList.add(entity);
        }

        return returnList;
    }

    public UseRateInfoEntity getUseRateInfoEntity(String rm, int rmNo)
            throws YKKSQLException {
        UseRateInfoEntity entity = new UseRateInfoEntity();

        entity.setRmNo(rm);
        setUsedLocat(entity, rm, rmNo);
        setEmptyLocat(entity, rm, rmNo);
        setErrorLocat(entity, rm, rmNo);
        setForbidLocat(entity, rm, rmNo);
        setUnassignableLocat(entity, rm, rmNo);
        setTotalLocat(entity, rm, rmNo);
        if (!entity.getTotalLocat().equals(new BigDecimal(0))) {
            entity
                    .setUseRate((
                                    entity.getTotalLocat().subtract(
                                            entity.getEmptyLocat()
                                    ).multiply(
                                            new BigDecimal(100)
                                    ).divide(entity.getTotalLocat(),
                                            1, BigDecimal.ROUND_HALF_UP
                                    )
                            )
                    );
        }

        return entity;
    }

    public List getUserMasterList(String userID) throws YKKSQLException {
        String columnList = "LOGINUSER.userid,USERATTRIBUTE.username,ROLE.roleid";
        String queryString = "SELECT "
                + columnList
                + " FROM USERATTRIBUTE,LOGINUSER,ROLE WHERE USERATTRIBUTE.userid = LOGINUSER.userid AND LOGINUSER.roleid = ROLE.roleid AND LOGINUSER.userid = "
                + StringUtils.surroundWithSingleQuotes(userID);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            UserViewEntity entity = new UserViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUserID(row.getValueByColumnName("userid"));
            entity.setUserName(row.getValueByColumnName("username"));
            entity.setAuthorization(row.getValueByColumnName("roleid"));

            returnList.add(entity);
        }
        return returnList;
    }

    private String getUserName(String userid) throws YKKSQLException {
        String queryString = "SELECT username FROM userattribute WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(userid);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            returnList.add(row.getValueByColumnName("username"));
        }

        if (returnList.size() > 0) {
            return (String) returnList.get(0);
        } else {
            return "";
        }
    }

    public int getUserViewCount(String userID, String looseSearchMode)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM userattribute WHERE userid LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(userID
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(userID)
            );
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getUserViewList(String userID, String looseSearchMode,
                                int beginningPos, int count) throws YKKSQLException {
        String columnList = "USERATTRIBUTE.userid,USERATTRIBUTE.username,ROLE.rolename";
        String queryString = "SELECT "
                + columnList
                + " FROM USERATTRIBUTE,LOGINUSER,ROLE WHERE USERATTRIBUTE.userid = LOGINUSER.userid AND LOGINUSER.roleid = ROLE.roleid AND USERATTRIBUTE.userid LIKE ";
        if (looseSearchMode.equals("1")) {
            queryString += StringUtils.surroundWithSingleQuotes(userID
                            + StringUtils.SinglePercentageMark
            );
        } else {
            queryString += StringUtils.surroundWithSingleQuotes(StringUtils
                            .surroundWithPercentageMarks(userID)
            );
        }
        queryString += " ORDER BY ROLE.roleid,USERATTRIBUTE.userid";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            UserViewEntity entity = new UserViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUserID(row.getValueByColumnName("userid"));
            entity.setUserName(row.getValueByColumnName("username"));
            entity.setRoleName(row.getValueByColumnName("rolename"));

            returnList.add(entity);
        }
        return returnList;
    }

    public int getWorkMaintenanceCount(String mckey) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNSIJI WHERE FNSIJI.mckey = "
                + StringUtils.surroundWithSingleQuotes(mckey);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getWorkMaintenanceList(String mckey, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNSYOTEI.retrieval_no,FNSYOTEI.section,FNSYOTEI.customer_code,FNSYOTEI.line,FNSYOTEI.pr_no,FNSYOTEI.allocation_qty";
        String queryString = "SELECT "
                + columnList
                + " FROM FNSIJI,FNSYOTEI WHERE FNSIJI.retrieval_plan_key = FNSYOTEI.retrieval_plankey(+) AND FNSIJI.mckey = "
                + StringUtils.surroundWithSingleQuotes(mckey);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            WorkMaintenanceEntity entity = new WorkMaintenanceEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setSectionExternalCode(row.getValueByColumnName("section")
                            + "/" + row.getValueByColumnName("customer_code")
            );
            entity.setLinePrno(row.getValueByColumnName("line") + "/"
                            + row.getValueByColumnName("pr_no")
            );
            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public int getWorkMaintenancePopupCount(String transferType,
                                            String station, String division) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHANSO WHERE ";

        if (division.equals("1")) {
            queryString += " FNHANSO.endstno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        } else {
            queryString += " FNHANSO.sakistno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        }

        if (transferType.equals("1")) {
            queryString += " AND FNHANSO.nyusyukbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ")";
        } else if (transferType.equals("2")) {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);

    }

    public static String getWorkMaintenancePopupListSQL(String transferType,
                                                        String station, String division) {
        String columnList = "FNHANSO.hansokey,FNHANSO.nyusyukbn,FNHANSO.hjyotaiflg,FNHANSO.mckey,FNHANSO.startstno,FNHANSO.endstno,FNHANSO.motostno,FNHANSO.sakistno,FNLOCAT.syozaikey,FNZAIKO.bucket_no,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNHANSO.sijisyosai,FNZAIKO.zaikosu,FNZAIKO.ticket_no,FNZAIKO.color_code, FNHANSO.GROUPNO";
        String queryString = "SELECT "
                + columnList
                + ",SUM(nyusyusu) FROM FNHANSO,FNSIJI,FNZAIKO,FNLOCAT,FMZKEY WHERE FNHANSO.mckey = FNSIJI.mckey AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey ";

        if (division.equals("1")) {
            queryString += " AND FNHANSO.endstno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        } else {
            queryString += " AND FNHANSO.motostno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        }

        if (transferType.equals("1")) {
            queryString += " AND FNHANSO.nyusyukbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ")";
        } else if (transferType.equals("2")) {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        }
        queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY FNHANSO.hjyotaiflg DESC , FNHANSO.GROUPNO ASC,FNHANSO.mckey ASC";

        return queryString;
    }

    public List getWorkMaintenancePopupList(String transferType,
                                            String station, String division) throws YKKSQLException {
        String columnList = "FNHANSO.hansokey,FNHANSO.nyusyukbn,FNHANSO.hjyotaiflg,FNHANSO.mckey,FNHANSO.startstno,FNHANSO.endstno,FNHANSO.motostno,FNHANSO.sakistno,FNLOCAT.syozaikey,FNZAIKO.bucket_no,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNHANSO.sijisyosai,FNZAIKO.zaikosu,FNZAIKO.ticket_no,FNZAIKO.color_code, FNHANSO.GROUPNO";
        String queryString = "SELECT "
                + columnList
                + ",SUM(nyusyusu) FROM FNHANSO,FNSIJI,FNZAIKO,FNLOCAT,FMZKEY WHERE FNHANSO.mckey = FNSIJI.mckey AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey ";

        if (division.equals("1")) {
            queryString += " AND FNHANSO.endstno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        } else {
            queryString += " AND FNHANSO.motostno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        }

        if (transferType.equals("1")) {
            queryString += " AND FNHANSO.nyusyukbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ")";
        } else if (transferType.equals("2")) {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        }
        queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY FNHANSO.hjyotaiflg DESC , FNHANSO.GROUPNO ASC,FNHANSO.mckey ASC";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            WorkMaintenancePopupEntity entity = new WorkMaintenancePopupEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTransferType(DBFlags.Nyusyukbn.parseDBToPage(row
                                    .getValueByColumnName("nyusyukbn")
                    )
            );
            entity.setStatus(DBFlags.HjyotaiFlg.parseDBToPage(row
                                    .getValueByColumnName("hjyotaiflg")
                    )
            );
            entity.setMckey(row.getValueByColumnName("mckey"));
            entity.setStationNo(row.getValueByColumnName("startstno"));
            entity.setStationName(getStNameByStno(row
                                    .getValueByColumnName("startstno")
                    )
            );
            entity.setMotoStationNo(row.getValueByColumnName("motostno"));
            entity.setSakiStationNo(row.getValueByColumnName("sakistno"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            try {
                entity.setTransferCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(nyusyusu)")
                        )
                );
            } catch (Exception ex) {
                entity.setTransferCount(0);
            }
            entity.setDispatchDetail(DBFlags.DispatchDetail.parseDBToPage(row
                                    .getValueByColumnName("sijisyosai")
                    )
            );
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));

            returnList.add(entity);
        }

        return returnList;
    }

    public List getWorkMaintenancePopupList(String transferType,
                                            String station, String division, int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNHANSO.hansokey,FNHANSO.nyusyukbn,FNHANSO.hjyotaiflg,FNHANSO.mckey,FNHANSO.startstno,FNHANSO.endstno,FNHANSO.motostno,FNHANSO.sakistno,FNLOCAT.syozaikey,FNZAIKO.bucket_no,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNHANSO.sijisyosai,FNZAIKO.zaikosu,FNZAIKO.ticket_no,FNZAIKO.color_code,FNHANSO.GROUPNO";
        String queryString = "SELECT "
                + columnList
                + ",SUM(nyusyusu) FROM FNHANSO,FNSIJI,FNZAIKO,FNLOCAT,FMZKEY WHERE FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) ";

        if (division.equals("1")) {
            queryString += " AND FNHANSO.endstno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        } else {
            queryString += " AND FNHANSO.sakistno = "
                    + StringUtils.surroundWithSingleQuotes(station);
        }

        if (transferType.equals("1")) {
            queryString += " AND FNHANSO.nyusyukbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ")";
        } else if (transferType.equals("2")) {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else {
            queryString += " AND FNHANSO.nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        }
        queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY FNHANSO.hjyotaiflg DESC , FNHANSO.GROUPNO ASC,FNHANSO.mckey ASC";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            WorkMaintenancePopupEntity entity = new WorkMaintenancePopupEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTransferType(DBFlags.Nyusyukbn.parseDBToPage(row
                                    .getValueByColumnName("nyusyukbn")
                    )
            );
            entity.setStatus(DBFlags.HjyotaiFlg.parseDBToPage(row
                                    .getValueByColumnName("hjyotaiflg")
                    )
            );
            entity.setMckey(row.getValueByColumnName("mckey"));
            entity.setStationNo(row.getValueByColumnName("startstno"));
            entity.setStationName(getStNameByStno(row
                                    .getValueByColumnName("startstno")
                    )
            );
            entity.setMotoStationNo(row.getValueByColumnName("motostno"));
            entity.setSakiStationNo(row.getValueByColumnName("sakistno"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            try {
                entity.setTransferCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(nyusyusu)")
                        )
                );
            } catch (Exception ex) {
                entity.setTransferCount(0);
            }
            entity.setDispatchDetail(DBFlags.DispatchDetail.parseDBToPage(row
                                    .getValueByColumnName("sijisyosai")
                    )
            );
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));

            returnList.add(entity);
        }

        return returnList;
    }

    public List getWorkStartStopSettingList() throws YKKSQLException {
        String columnList = "arcno,systemflg";
        String queryString = "SELECT " + columnList + " FROM FNAREA ";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            WorkStartStopSettingEntity entity = new WorkStartStopSettingEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setControllerNo(row.getValueByColumnName("arcno"));
            entity.setSystemStatus(DBFlags.Systemflg.parseDBToPage(row
                                    .getValueByColumnName("systemflg")
                    )
            );
            setWorkCount(entity);

            returnList.add(entity);
        }
        return returnList;
    }

    public int getWorkViewCount() throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHANSO ";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getWorkViewList(int beginningPos, int count)
            throws YKKSQLException {
        String columnList = "FNHANSO.hansokey,FNHANSO.nyusyukbn,FNHANSO.hjyotaiflg,FNHANSO.mckey,FNHANSO.startstno,FNHANSO.endstno,FNHANSO.motostno,FNHANSO.sakistno,FNLOCAT.syozaikey,FNZAIKO.bucket_no,FNZAIKO.zaikey,FNHANSO.sijisyosai,FNZAIKO.zaikosu,FNZAIKO.ticket_no,FNZAIKO.color_code,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNHANSO.groupno";
        String queryString = "SELECT "
                + columnList
                + " FROM FNHANSO,FNSIJI,FNZAIKO,FNLOCAT,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) ";

        queryString += " GROUP BY " + columnList;
        queryString += " ORDER BY FNHANSO.endstno ASC,FNHANSO.hjyotaiflg DESC , FNHANSO.GROUPNO ASC,FNHANSO.mckey ASC";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            WorkViewEntity entity = new WorkViewEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTransferType(DBFlags.Nyusyukbn.parseDBToPage(row
                                    .getValueByColumnName("nyusyukbn")
                    )
            );
            entity.setStatus(DBFlags.HjyotaiFlg.parseDBToPage(row
                                    .getValueByColumnName("hjyotaiflg")
                    )
            );
            entity.setMckey(row.getValueByColumnName("mckey"));
            entity.setStationNo(row.getValueByColumnName("startstno"));
            entity.setMotoStationNo(row.getValueByColumnName("motostno"));
            entity.setSakiStationNo(row.getValueByColumnName("sakistno"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setBucketNo(row.getValueByColumnName("bucket_no"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            try {
                entity.setTransferCount(Integer.parseInt(getTransferCount(row
                                                .getValueByColumnName("hansokey")
                                )
                        )
                );
            } catch (Exception ex) {
                entity.setTransferCount(0);
            }
            entity.setDispatchDetail(DBFlags.DispatchDetail.parseDBToPage(row
                                    .getValueByColumnName("sijisyosai")
                    )
            );
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setColorCode(row.getValueByColumnName("color_code"));

            returnList.add(entity);
        }

        return returnList;
    }

    private void insertInmanageFnjiseki(LocationViewEntity entity,
                                        String workCount, String userid, String workMode, String modiMode)
            throws YKKSQLException {
        String time = StringUtils.getCurrentDate()
                + StringUtils.getCurrentTime();

        String sqlString = "INSERT INTO FNJISEKI (zaikey,zkname,zkname2,zkname3,sakuseihiji,nyusyukbn,sagyokbn,sakukbn,nyusyustno,ticket_no,bucket_no,color_code,nyusyusu,real_work_number,depot_code,userid,username,startstno,endstno,systemid,syozaikey,backup_flg,manage_item_flag)VALUES((SELECT zaikey FROM FNZAIKO WHERE ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + "),(SELECT zkname1 FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND ROWNUM = 1),(SELECT zkname2 FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND ROWNUM = 1),(SELECT zkname3 FROM FNZAIKO,FMZKEY WHERE FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND ROWNUM = 1),"
                + StringUtils.surroundWithSingleQuotes(time)
                + ","
                + StringUtils.surroundWithSingleQuotes(workMode)
                + ",'9',"
                + StringUtils.surroundWithSingleQuotes(modiMode)
                + ",(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo())
                + " AND ROWNUM = 1),"
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + ","
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo())
                + ",(SELECT color_code FROM FNZAIKO WHERE ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND ROWNUM = 1),"
                + StringUtils.surroundWithSingleQuotes(workCount)
                + ","
                + StringUtils.surroundWithSingleQuotes(workCount)
                + ",'02',"
                + StringUtils.surroundWithSingleQuotes(userid)
                + ",(SELECT username FROM USERATTRIBUTE WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(userid)
                + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo())
                + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo())
                + " AND ROWNUM = 1),(SELECT systemid FROM FNZAIKO WHERE ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND ROWNUM = 1),"
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo())
                + ",'0','0')";

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    private void insertUnmanageFnjiseki(LocationViewEntity entity,
                                        String workCount, String userid, String workMode, String modiMode)
            throws YKKSQLException {
        String time = StringUtils.getCurrentDate()
                + StringUtils.getCurrentTime();

        String sqlString = "";
        if (StringUtils.IsNullOrEmpty(entity.getItemCode())) {
            sqlString = "INSERT INTO FNJISEKI (zaikey,zkname,zkname2,zkname3,sakuseihiji,nyusyukbn,sagyokbn,sakukbn,nyusyustno,bucket_no,color_code,nyusyusu,real_work_number,depot_code,userid,username,startstno,endstno,systemid,syozaikey,backup_flg,manage_item_flag)VALUES("
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + ",' ',' ',' ',"
                    + StringUtils.surroundWithSingleQuotes(time)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workMode)
                    + ",'9',"
                    + StringUtils.surroundWithSingleQuotes(modiMode)
                    + ",(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),"
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getColorCode()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workCount)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workCount)
                    + ",'02',"
                    + StringUtils.surroundWithSingleQuotes(userid)
                    + ",(SELECT username FROM USERATTRIBUTE WHERE userid = "
                    + StringUtils.surroundWithSingleQuotes(userid)
                    + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),(SELECT systemid FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),"
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            ) + ",'0','1')";
        } else {
            sqlString = "INSERT INTO FNJISEKI (zaikey,zkname,zkname2,zkname3,sakuseihiji,nyusyukbn,sagyokbn,sakukbn,nyusyustno,bucket_no,color_code,nyusyusu,real_work_number,depot_code,userid,username,startstno,endstno,systemid,syozaikey,backup_flg,manage_item_flag)VALUES("
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + ",(SELECT zkname1 FROM FMZKEY WHERE  manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + " AND zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + " AND ROWNUM = 1),(SELECT zkname2 FROM FMZKEY WHERE manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + " AND zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + " AND ROWNUM = 1),(SELECT zkname3 FROM FMZKEY WHERE manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE)
                    + " AND zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + " AND ROWNUM = 1),"
                    + StringUtils.surroundWithSingleQuotes(time)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workMode)
                    + ",'9',"
                    + StringUtils.surroundWithSingleQuotes(modiMode)
                    + ",(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),"
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + ","
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getColorCode()
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workCount)
                    + ","
                    + StringUtils.surroundWithSingleQuotes(workCount)
                    + ",'02',"
                    + StringUtils.surroundWithSingleQuotes(userid)
                    + ",(SELECT username FROM USERATTRIBUTE WHERE userid = "
                    + StringUtils.surroundWithSingleQuotes(userid)
                    + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),(SELECT ailestno FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),(SELECT systemid FROM FNLOCAT WHERE syozaikey = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            )
                    + " AND ROWNUM = 1),"
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getLocationNo()
            ) + ",'0','1')";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public boolean isEmptyLocation(String locationNo) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT)
                + " AND syozaikey = "
                + StringUtils.surroundWithSingleQuotes(locationNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count) > 0;
    }

    private boolean isFitFmzkeyStatus(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FMZKEY WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return !count.equals("0");
    }

    private boolean isFitFnzaikoStatus(LocationViewEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO WHERE ticket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getTicketNo())
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return !count.equals("0");
    }

    public boolean isUsedLocation(String locationNo, boolean isInManage)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA,FNLOCAT,FNZAIKO WHERE FNAKITANA.syozaikey = FNLOCAT.syozaikey AND FNLOCAT.systemid = FNZAIKO.systemid AND FNAKITANA.tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT)
                + " AND FNAKITANA.syozaikey = "
                + StringUtils.surroundWithSingleQuotes(locationNo);
        if (isInManage) {
            queryString += " AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                    + " AND FNZAIKO.weight_report_complete_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);
        } else {
            queryString += " AND FNZAIKO.manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);
        }
        queryString += " AND FNZAIKO.storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count) > 0;
    }

    public void modiBucket(BucketViewEntity entity) throws YKKSQLException {
        String sqlString = "UPDATE FMBUCKET SET packing_weight = "
                + String.valueOf(entity.getPackingWeight())
                + ", height_flag = "
                + StringUtils.surroundWithSingleQuotes(entity.getHightFlag())
                + ", lastupdate_datetime = "
                + StringUtils.surroundWithSingleQuotes(entity
                        .getLastUpdateDateTime()
        ) + "WHERE bucket_no = "
                + StringUtils.surroundWithSingleQuotes(entity.getBucketNo());

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void modiFnrange(String terminal, String line1, String line2,
                            ErrorRangeEntity entity) throws YKKSQLException {
        String sqlString = "UPDATE FNRANGE SET unit_weight_upper = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getUnitWeightUpper()
                )
        )
                + ", unit_weight_lower = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getUnitWeightLower()
                )
        )
                + ", storage_upper = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getStorageUpper()
                )
        )
                + ", storage_lower = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getStorageLower()
                )
        )
                + ", ship_upper = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getShipUpper()
                )
        )
                + ", ship_lower = "
                + StringUtils.surroundWithSingleQuotes(String.valueOf(entity
                                .getShipLower()
                )
        );
        if (!StringUtils.IsNullOrEmpty(terminal)) {
            sqlString += " WHERE termno = "
                    + StringUtils.surroundWithSingleQuotes(terminal);
        } else {
            sqlString += " WHERE made_section = "
                    + StringUtils.surroundWithSingleQuotes(line1)
                    + " AND made_line = "
                    + StringUtils.surroundWithSingleQuotes(line2);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void modiItem(ItemViewEntity entity) throws YKKSQLException {
        String sqlString = "UPDATE FMZKEY SET ";
        if (entity.getManageItemFlag().equals(DBFlags.ManageItemFlag.INMANAGE)) {
            sqlString += "measure_qty = "
                    + String.valueOf(entity.getUnitQty())
                    + ",jogensuj = "
                    + String.valueOf(entity.getAutoDepoMaxCount())
                    + ",kagensuj = "
                    + String.valueOf(entity.getAutoDepoMinCount())
                    + ",measure_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getUnitFlag())
                    + ",remove_convent_flag = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRemoveConventFlag()
            ) + ",bag_flag = "
                    + StringUtils.surroundWithSingleQuotes(entity.getBagFlag());
        } else {
            sqlString += "zkname1 = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName1()
            )
                    + ",zkname2 = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName2()
            )
                    + ",zkname3 = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getItemName3()
            );
        }
        sqlString += " WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode());
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public boolean modiLocation(LocationViewEntity entity, String userid,
                                Message message) throws YKKSQLException {
        if (getBucketNoInFmbucketCount(entity) == 0) {
            message.setMsgResourceKey("7000021");
            return true;
        }
        if (getBucketNoInFnzaikoCountForModi(entity) != 0) {
            message.setMsgResourceKey("7000022");
            return true;
        }

        String time = StringUtils.getCurrentDate()
                + StringUtils.getCurrentTime();

        String sqlString = "UPDATE FNZAIKO SET ";
        if (entity.getManageDivision().equals(DBFlags.ManageItemFlag.INMANAGE)) {
            insertInmanageFnjiseki(entity, "0", userid, "2", "0");

            sqlString += " bucket_no = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getBucketNo())
                    + " ,nyukohiji = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getStockinDate()
                            + entity.getStockinTime()
            ) + " ,koshinhiji = "
                    + StringUtils.surroundWithSingleQuotes(time) + " ,memo = "
                    + StringUtils.surroundWithSingleQuotes(entity.getMemo());
        } else {
            if (!isFitFmzkeyStatus(entity)
                    && !StringUtils.IsNullOrEmpty(entity.getItemCode())) {
                message.setMsgResourceKey("7000023");
                return true;
            }

            String workMode = "";
            String modiMode = "";
            String workCount = "";
            int instockCount = 0;
            try {
                instockCount = Integer.parseInt(getInstockCount(entity
                                        .getLocationNo()
                        )
                );
            } catch (Exception ex) {
                instockCount = 0;
            }
            if (instockCount - entity.getInstockCount() > 0) {
                workMode = "2";
                modiMode = "2";
                workCount = String.valueOf(instockCount
                                - entity.getInstockCount()
                );
            } else if (instockCount - entity.getInstockCount() == 0) {
                workMode = "2";
                modiMode = "0";
                workCount = "0";
            } else {
                workMode = "1";
                modiMode = "1";
                workCount = String.valueOf(entity.getInstockCount()
                                - instockCount
                );
            }

            insertUnmanageFnjiseki(entity, workCount, userid, workMode,
                    modiMode
            );

            sqlString += " zaikey = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getItemCode())
                    + " ,color_code = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getColorCode()
            )
                    + " ,nyukohiji = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getStockinDate()
                            + entity.getStockinTime()
            ) + " ,koshinhiji = "
                    + StringUtils.surroundWithSingleQuotes(time) + " ,memo = "
                    + StringUtils.surroundWithSingleQuotes(entity.getMemo())
                    + " ,zaikosu = " + String.valueOf(entity.getInstockCount())
                    + " ,skanosu = " + String.valueOf(entity.getInstockCount());
        }
        sqlString += " WHERE systemid = (SELECT FNZAIKO.systemid FROM FNZAIKO,FNLOCAT WHERE FNZAIKO.systemid = FNLOCAT.systemid AND FNLOCAT.syozaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getLocationNo())
                + ")";
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
        return false;

    }

    public void modiUser(UserViewEntity entity) throws YKKSQLException {
        String sqlString = "UPDATE LOGINUSER SET ";
        if (!StringUtils.IsNullOrEmpty(entity.getPassword())) {
            sqlString += "password = "
                    + StringUtils
                    .surroundWithSingleQuotes(entity.getPassword())
                    + ", ";
        }
        sqlString += "roleid = "
                + StringUtils.surroundWithSingleQuotes(entity
                        .getAuthorization()
        ) + " WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(entity.getUserID());

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

        sqlString = "UPDATE USERATTRIBUTE SET username = "
                + StringUtils.surroundWithSingleQuotes(entity.getUserName())
                + " WHERE userid = "
                + StringUtils.surroundWithSingleQuotes(entity.getUserID());
        ;
        handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void reprintLabelSetting(String printerNo, String labelKey)
            throws YKKSQLException {
        String sqlString = "UPDATE FNLABEL SET printer_no = "
                + StringUtils.surroundWithSingleQuotes(printerNo)
                + " ,printing_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.PritingFlag.TO_PRINT)
                + " WHERE label_key = "
                + StringUtils.surroundWithSingleQuotes(labelKey);

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    private void setAutoCount(StorageInfoEntity entity) throws YKKSQLException {
        String queryString = " SELECT SUM(zaikosu) FROM FNZAIKO WHERE weight_report_complete_flag <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(entity.getColor());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setAutoCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(zaikosu)")
                        )
                );
            } catch (Exception ex) {
                entity.setAutoCount(0);
            }
        }

    }

    private void setEmptyLocat(UseRateInfoEntity entity, String rm, int rmNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE ";
        if (rm != null) {
            queryString += " bankno IN ("
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(rmNo * 2)
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf((rmNo * 2) - 1)
            ) + ")" + " AND ";
        }
        queryString += " tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.EMPTY_LOCAT);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setEmptyLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setEmptyLocat(new BigDecimal(0));
            }
        }

    }

    private void setErrorLocat(UseRateInfoEntity entity, String rm, int rmNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNLOCAT WHERE ";
        if (rm != null) {
            queryString += " ailestno = "
                    + StringUtils.surroundWithSingleQuotes(entity.getRmNo())
                    + " AND ";
        }
        queryString += " zaijyoflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setErrorLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setErrorLocat(new BigDecimal(0));
            }
        }
    }

    private void setFlatCount(StorageInfoEntity entity) throws YKKSQLException {
        String queryString = " SELECT SUM(zaikosu) FROM FNZAIKO WHERE weight_report_complete_flag <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(entity.getColor());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setFlatCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(zaikosu)")
                        )
                );
            } catch (Exception ex) {
                entity.setFlatCount(0);
            }
        }

    }

    private void setForbidLocat(UseRateInfoEntity entity, String rm, int rmNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE ";
        if (rm != null) {
            queryString += " bankno IN ("
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(rmNo * 2)
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf((rmNo * 2) - 1)
            ) + ")" + " AND ";
        }
        queryString += " tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.FORBID_LOCAT);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setForbidLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setForbidLocat(new BigDecimal(0));
            }
        }

    }

    private void setItemName(ExternalStockoutEntity entity)
            throws YKKSQLException {
        String columnList = "zkname1, zkname2, zkname3";
        String queryString = "SELECT "
                + columnList
                + " FROM FMZKEY WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
        }

    }

    // private void setItemName(StockoutEntity entity) throws YKKSQLException
    // {
    // String columnList = "zkname1, zkname2, zkname3";
    // String queryString = "SELECT "
    // + columnList
    // + " FROM FMZKEY WHERE zaikey = "
    // + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
    // + " AND manage_item_flag = "
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);
    //
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString);
    //
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // entity.setItemName1(row.getValueByColumnName("zkname1"));
    // entity.setItemName2(row.getValueByColumnName("zkname2"));
    // entity.setItemName3(row.getValueByColumnName("zkname3"));
    // }
    //
    // }

    private void setMckey(StockoutStartEntity entity, String searchMode,
                          String section, String line, String lineDivision,
                          String stockoutStation1, String stockoutStation2, String retrievalNo)
            throws YKKSQLException {
        String queryString = "SELECT DISTINCT FNHANSO.mckey FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) "
                + " AND fnsyotei.retrieval_station NOT IN ('23', '25', '26')";
        if (searchMode.equals("1")) {
            queryString += " AND FNSIJI.section = "
                    + StringUtils.surroundWithSingleQuotes(section);
            if (!StringUtils.IsNullOrEmpty(line)) {
                queryString += " AND FNSIJI.line = "
                        + StringUtils.surroundWithSingleQuotes(line);
            }
        } else if (searchMode.equals("2")) {
            queryString += " AND FNSIJI.line_type = "
                    + StringUtils.surroundWithSingleQuotes(lineDivision);
            queryString += " AND FNSYOTEI.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes("24");
        } else {
            if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation2)
                    && StringUtils.IsNullOrEmpty(stockoutStation1)) {
                queryString += " AND FNSYOTEI.retrieval_station = "
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2);
            } else if (!StringUtils.IsNullOrEmpty(stockoutStation1)
                    && !StringUtils.IsNullOrEmpty(stockoutStation2)) {
                queryString += " AND FNSYOTEI.retrieval_station IN ("
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation1)
                        + ","
                        + StringUtils
                        .surroundWithSingleQuotes(stockoutStation2)
                        + ")";
            }

        }
        queryString += " AND FNSYOTEI.retrieval_no LIKE "
                + StringUtils.surroundWithSingleQuotes(retrievalNo
                        + StringUtils.SinglePercentageMark
        );
        queryString += " AND FNLOCAT.systemid = "
                + StringUtils.surroundWithSingleQuotes(entity.getSystemId());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String mckey = "";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            mckey += row.getValueByColumnName("mckey") + ",";
        }
        entity.setMckey(mckey);

    }

    private void setMckey(StockoutStartEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT DISTINCT FNHANSO.mckey FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) "
                + " AND fnsyotei.retrieval_station NOT IN ('23', '25', '26')";

        queryString += " AND FNLOCAT.systemid = "
                + StringUtils.surroundWithSingleQuotes(entity.getSystemId());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String mckey = "";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            mckey += row.getValueByColumnName("mckey") + ",";
        }
        entity.setMckey(mckey);

    }

    private void setTotalCount(StorageInfoEntity entity) throws YKKSQLException {
        String queryString = " SELECT SUM(zaikosu) FROM FNZAIKO WHERE weight_report_complete_flag <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(entity.getColor());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setTotalInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(zaikosu)")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalInstockCount(0);
            }
        }

    }

    // private void setTotalCountInstock(StockoutEntity entity)
    // throws YKKSQLException
    // {
    // String columnList = "SUM(zaikosu)";
    // String queryString = "SELECT "
    // + columnList
    // + " FROM FNZAIKO WHERE zaikey = "
    // + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
    // + " AND color_code = "
    // + StringUtils.surroundWithSingleQuotes(entity.getColor())
    // + " AND manage_item_flag = "
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
    // + " AND weight_report_complete_flag = "
    // + StringUtils
    // .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);
    // DBHandler handler = new DBHandler(conn);
    // handler.executeQuery(queryString);
    //
    // RecordSet recordSet = handler.getRecordSet();
    // List rowList = recordSet.getRowList();
    // Iterator it = rowList.iterator();
    // while (it.hasNext())
    // {
    // RecordSetRow row = (RecordSetRow) it.next();
    //
    // try
    // {
    // entity.setTotalCountInstock(Integer.parseInt(row
    // .getValueByColumnName("SUM(zaikosu)")));
    // }
    // catch (Exception ex)
    // {
    // entity.setTotalCountInstock(0);
    // }
    // }
    //
    // }

    private void setTotalLocat(UseRateInfoEntity entity, String rm, int rmNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA ";
        if (rm != null) {
            queryString += " WHERE bankno IN ("
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(rmNo * 2)
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf((rmNo * 2) - 1)
            ) + ")";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setTotalLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalLocat(new BigDecimal(0));
            }
        }
    }

    private void setTotlaCountInstock(ExternalStockoutEntity entity)
            throws YKKSQLException {
        String columnList = "SUM(zaikosu)";
        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO WHERE zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(entity.getColor())
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED);
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setTotalCountInstock(Integer.parseInt(row
                                        .getValueByColumnName("SUM(zaikosu)")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalCountInstock(0);
            }
        }

    }

    private void setUnassignableLocat(UseRateInfoEntity entity, String rm,
                                      int rmNo) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE ";
        if (rm != null) {
            queryString += " bankno IN ("
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(rmNo * 2)
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf((rmNo * 2) - 1)
            ) + ")" + " AND ";
        }
        queryString += " FNAKITANA.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.UNASSIGNABLE);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setCanNotCallLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setCanNotCallLocat(new BigDecimal(0));
            }
        }

    }

    private void setUninstockCount(StorageInfoEntity entity)
            throws YKKSQLException {
        String queryString = " SELECT SUM(plan_qty) FROM FNZAIKO WHERE weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.UNCOMPLETED)
                + " AND zaikey = "
                + StringUtils.surroundWithSingleQuotes(entity.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(entity.getColor());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setNotStockinCount(Integer.parseInt(row
                                        .getValueByColumnName("SUM(plan_qty)")
                        )
                );
            } catch (Exception ex) {
                entity.setNotStockinCount(0);
            }
        }

    }

    private void setUsedLocat(UseRateInfoEntity entity, String rm, int rmNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNAKITANA WHERE ";
        if (rm != null) {
            queryString += " bankno IN ("
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf(rmNo * 2)
            )
                    + ","
                    + StringUtils.surroundWithSingleQuotes(String
                            .valueOf((rmNo * 2) - 1)
            ) + ")" + " AND ";
        }
        queryString += " tanaflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Tanaflg.USED_LOCAT);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setUsedLocat(new BigDecimal(row
                                .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setUsedLocat(new BigDecimal(0));
            }
        }
    }

    private void setWorkCount(StartStopAllSettingEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHANSO ";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
        }

    }

    private void setWorkCount(StationStatusEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHANSO WHERE endstno = "
                + StringUtils.surroundWithSingleQuotes(entity.getStationNo());
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
        }
    }

    private void setWorkCount(WorkStartStopSettingEntity entity)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNHANSO";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                entity.setWorkCount(Integer.parseInt(row
                                        .getValueByColumnName("COUNT(*)")
                        )
                );
            } catch (Exception ex) {
                entity.setWorkCount(0);
            }
        }
    }

    public void startStation(String stationNo) throws YKKSQLException {
        String sqlString = "UPDATE FNSTATION SET chudanflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Chudanflg.StopFlgOff)
                + " WHERE stno = "
                + StringUtils.surroundWithSingleQuotes(stationNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

    }

    public void stopPrintLabelSetting(String stno, boolean isGeneralLabelStop,
                                      boolean isLabelStop, boolean isRMLabelStop) throws YKKSQLException {
        String sqlString = "UPDATE FNRETRIEVAL_ST SET unify_ticket_printflg = ";
        if (isGeneralLabelStop) {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP);
        } else {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP_RELEASE);
        }
        sqlString += ", ticket_printflg = ";
        if (isLabelStop) {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP);
        } else {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP_RELEASE);
        }
        sqlString += ", cart_ticket_printflg = ";
        if (isRMLabelStop) {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP);
        } else {
            sqlString += StringUtils
                    .surroundWithSingleQuotes(DBFlags.Fnretrieval_st_Flags.STOP_RELEASE);
        }

        sqlString += " WHERE retrieval_station = "
                + StringUtils.surroundWithSingleQuotes(stno);
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public void stopStation(String stationNo) throws YKKSQLException {
        String sqlString = "UPDATE FNSTATION SET chudanflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Chudanflg.StopFlgOn)
                + " WHERE stno = "
                + StringUtils.surroundWithSingleQuotes(stationNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);

    }

    public List getPendingTransferDataCencelOrPrioritizeEntityList(
            String section, String line, String retrievalNo, String orderMode,
            int beginningPos, int count) throws YKKSQLException {
        String columnList = "FNSYOTEI.START_DATE,FNSYOTEI.START_TIMING_FLAG,FNSYOTEI.RETRIEVAL_NO,FNSYOTEI.ZAIKEY,FNSYOTEI.COLOR_CODE,FMZKEY.ZKNAME1,FMZKEY.ZKNAME2,FMZKEY.ZKNAME3,FNSYOTEI.RETRIEVAL_PLANKEY";

        String queryString = "SELECT " + columnList
                + " FROM FNSYOTEI,FMZKEY,FNSIJI,FNHANSO ";
        queryString += "WHERE FNSYOTEI.zaikey = FMZKEY.zaikey AND FNSYOTEI.RETRIEVAL_PLANKEY = FNSIJI.RETRIEVAL_PLAN_KEY AND FNSIJI.HANSOKEY = FNHANSO.HANSOKEY AND FNHANSO.HJYOTAIFLG <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH);
        if (!StringUtils.IsNullOrEmpty(section)) {
            queryString += " AND FNSYOTEI.SECTION =  "
                    + StringUtils.surroundWithSingleQuotes(section);
        }

        if (!StringUtils.IsNullOrEmpty(line)) {
            queryString += " AND FNSYOTEI.LINE = "
                    + StringUtils.surroundWithSingleQuotes(line);
        }
        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND FNSYOTEI.RETRIEVAL_NO = "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo);
        }

        queryString += " GROUP BY " + columnList;

        if (orderMode.equals("1")) {
            queryString += " ORDER BY start_date,start_timing_flag";
        } else {
            queryString += " ORDER BY zaikey";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            PendingTransferDataCencelOrPrioritizeEntity entity = new PendingTransferDataCencelOrPrioritizeEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("RETRIEVAL_PLANKEY")
            );

            columnList = "stockoutTransferQty,startedBucketCount,waitingBucketCount";

            queryString = "SELECT "
                    + columnList
                    + " FROM (SELECT SUM(NYUSYUSU) as stockoutTransferQty FROM FNSIJI,FNHANSO where FNSIJI.HANSOKEY = FNHANSO.HANSOKEY AND FNSIJI.RETRIEVAL_PLAN_KEY = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRetrievalPlankey()
            )
                    + " AND FNHANSO.HJYOTAIFLG <> "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                    + " ),(SELECT COUNT(distinct(FNHANSO.MCKEY)) as startedBucketCount FROM FNSIJI,FNHANSO where FNSIJI.HANSOKEY = FNHANSO.HANSOKEY AND FNSIJI.RETRIEVAL_PLAN_KEY = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRetrievalPlankey()
            )
                    + "AND (FNHANSO.HJYOTAIFLG NOT IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.START)
                    + ")OR(FNHANSO.HJYOTAIFLG != "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                    + "AND FNHANSO.HJYOTAIFLG = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.START)
                    + "AND FNHANSO.MOTOSTNO < '9000'"
                    + "))),(SELECT COUNT(distinct(FNHANSO.MCKEY)) as waitingBucketCount FROM FNSIJI,FNHANSO where FNSIJI.HANSOKEY = FNHANSO.HANSOKEY AND FNSIJI.RETRIEVAL_PLAN_KEY = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRetrievalPlankey()
            )
                    + "AND FNHANSO.MOTOSTNO >= '9000'"
                    + "AND FNHANSO.HJYOTAIFLG = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.START)
                    + ")";

            handler = new DBHandler(conn);
            handler.executeQuery(queryString);

            RecordSet recordSet2 = handler.getRecordSet();
            List rowList2 = recordSet2.getRowList();
            Iterator it2 = rowList2.iterator();

            while (it2.hasNext()) {
                row = (RecordSetRow) it2.next();

                if (!StringUtils.IsNullOrEmpty(row
                                .getValueByColumnName("stockoutTransferQty")
                )) {
                    entity.setStockoutTransferQty(Integer.parseInt(row
                                            .getValueByColumnName("stockoutTransferQty")
                            )
                    );
                }
                entity.setStartedBucketCount(Integer.parseInt(row
                                        .getValueByColumnName("startedBucketCount")
                        )
                );
                entity.setWaitingBucketCount(Integer.parseInt(row
                                        .getValueByColumnName("waitingBucketCount")
                        )
                );
            }

            queryString = "SELECT distinct(FNHANSO.MCKEY) FROM FNHANSO,FNSIJI WHERE FNHANSO.HANSOKEY = FNSIJI.HANSOKEY AND FNSIJI.RETRIEVAL_PLAN_KEY = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRetrievalPlankey()
            )
                    + "AND FNHANSO.MOTOSTNO >= '9000'"
                    + "AND FNHANSO.HJYOTAIFLG = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.START);
            handler = new DBHandler(conn);
            handler.executeQuery(queryString);

            RecordSet recordSet3 = handler.getRecordSet();
            List rowList3 = recordSet3.getRowList();
            Iterator it3 = rowList3.iterator();

            String mckey = "";
            while (it3.hasNext()) {
                row = (RecordSetRow) it3.next();

                mckey += row.getValueByColumnName("mckey") + ",";
            }
            entity.setMcKey(mckey);

            returnList.add(entity);

        }

        return returnList;
    }

    public int getPendingTransferDataCencelOrPrioritizeEntityCount(
            String section, String line, String retrievalNo)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(distinct(FNSYOTEI.RETRIEVAL_PLANKEY)) FROM FNSYOTEI,FNSIJI,FNHANSO ";
        queryString += "WHERE FNSYOTEI.RETRIEVAL_PLANKEY = FNSIJI.RETRIEVAL_PLAN_KEY AND FNSIJI.HANSOKEY = FNHANSO.HANSOKEY AND FNHANSO.HJYOTAIFLG <> "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH);
        if (!StringUtils.IsNullOrEmpty(section)) {
            queryString += " AND FNSYOTEI.SECTION =  "
                    + StringUtils.surroundWithSingleQuotes(section);
        }

        if (!StringUtils.IsNullOrEmpty(line)) {
            queryString += " AND FNSYOTEI.LINE = "
                    + StringUtils.surroundWithSingleQuotes(line);
        }
        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND FNSYOTEI.RETRIEVAL_NO = "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils
                    .IsNullOrEmpty(row
                                    .getValueByColumnName("COUNT(distinct(FNSYOTEI.RETRIEVAL_PLANKEY))")
                    )) {
                count = row
                        .getValueByColumnName("COUNT(distinct(FNSYOTEI.RETRIEVAL_PLANKEY))");
            }
        }
        return Integer.parseInt(count);
    }

    public WavesStatusSurveillanceEntity getWavesStatusSurveillanceEntity()
            throws YKKSQLException {
        String columnList = "work_file_flg,batch_flag,check_date,trz00_flg,try00_flg,trx00_flg,retrieval_cancel_flg";
        String queryString = "SELECT " + columnList
                + " FROM FNSYSTEM WHERE ROWNUM = 1";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();

        WavesStatusSurveillanceEntity entity = null;
        while (it.hasNext()) {
            entity = new WavesStatusSurveillanceEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWorkFile(row.getValueByColumnName("work_file_flg"));
            entity.setBatch(row.getValueByColumnName("batch_flag"));
            entity.setCheckDate(row.getValueByColumnName("check_date"));
            entity.setTrz00(row.getValueByColumnName("trz00_flg"));
            entity.setTry00(row.getValueByColumnName("try00_flg"));
            entity.setTrx00(row.getValueByColumnName("trx00_flg"));
            entity.setRetrievalCancel(row
                            .getValueByColumnName("retrieval_cancel_flg")
            );
        }
        return entity;
    }

    public void releaseFnsystem(WavesStatusSurveillanceEntity entity)
            throws YKKSQLException {
        String sqlString = "UPDATE FNSYSTEM SET ";
        if (!StringUtils.IsNullOrEmpty(entity.getTrz00())) {
            sqlString += " trz00_flg = "
                    + StringUtils.surroundWithSingleQuotes(entity.getTrz00());
        }
        if (!StringUtils.IsNullOrEmpty(entity.getTry00())) {
            sqlString += " try00_flg = "
                    + StringUtils.surroundWithSingleQuotes(entity.getTry00());
        }
        if (!StringUtils.IsNullOrEmpty(entity.getTrx00())) {
            sqlString += " trx00_flg = "
                    + StringUtils.surroundWithSingleQuotes(entity.getTrx00());
        }
        if (!StringUtils.IsNullOrEmpty(entity.getRetrievalCancel())) {
            sqlString += " retrieval_cancel_flg = "
                    + StringUtils.surroundWithSingleQuotes(entity
                            .getRetrievalCancel()
            );
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(sqlString, true);
    }

    public List getRetrievalStation() throws YKKSQLException {
        String columnList = "retrieval_station,retrieval_st_name";

        String queryString = "SELECT " + columnList
                + " FROM FNRETRIEVAL_ST WHERE FNRETRIEVAL_ST.retrieval_station not in ('31','32','41','42') order by retrieval_station";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalStationEntity entity = new RetrievalStationEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setRetrievalStationName(row
                            .getValueByColumnName("retrieval_st_name")
            );
            entity.setRetrievalStation(row
                            .getValueByColumnName("retrieval_station")
            );

            returnList.add(entity);
        }

        return returnList;
    }

    public List getFlatStockoutList(FlatStockoutEntity en, int beginningPos,
                                    int count) throws YKKSQLException {
        String columnList = "FNSYOTEI.zaikey,FNSYOTEI.section,FNSYOTEI.start_date,FNSYOTEI.start_timing_flag,FNSYOTEI.retrieval_no,FNSYOTEI.color_code,FNSYOTEI.necessary_qty,FNSYOTEI.allocation_qty,FNSYOTEI.retrieval_qty,FNSYOTEI.retrieval_alloc_qty,FNSYOTEI.retrieval_plankey";

        String queryString = "SELECT " + columnList
                + " FROM FNSYOTEI WHERE FNSYOTEI.zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode());
        if (!StringUtils.IsNullOrEmpty(en.getColor())) {
            queryString += " AND FNSYOTEI.color_code = "
                    + StringUtils.surroundWithSingleQuotes(en.getColor());
        }
        if (!StringUtils.IsNullOrEmpty(en.getSection())) {
            queryString += " AND FNSYOTEI.section = "
                    + StringUtils.surroundWithSingleQuotes(en.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(en.getStartDate())) {
            queryString += " AND FNSYOTEI.start_date >= "
                    + StringUtils.surroundWithSingleQuotes(en.getStartDate());
        }
        queryString += " AND FNSYOTEI.proc_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);

        if (en.getOrderBy().equals("1")) {
            queryString += " ORDER BY FNSYOTEI.start_date, FNSYOTEI.start_timing_flag,FNSYOTEI.section, FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        } else if (en.getOrderBy().equals("2")) {
            queryString += " ORDER BY FNSYOTEI.section, FNSYOTEI.start_date, FNSYOTEI.start_timing_flag, FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        } else {
            queryString += " ORDER BY FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            FlatStockoutEntity entity = new FlatStockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStartDate(row.getValueByColumnName("start_date"));
            entity.setStartDateTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setColor(row.getValueByColumnName("color_code"));
            try {
                entity.setStockoutNecessaryQty(Long.parseLong(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }
            entity.setSection(row.getValueByColumnName("section"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );

            returnList.add(entity);
        }

        return returnList;
    }

    public int getFlatStockoutCount(FlatStockoutEntity en)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNSYOTEI WHERE FNSYOTEI.zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode());
        if (!StringUtils.IsNullOrEmpty(en.getColor())) {
            queryString += " AND FNSYOTEI.color_code = "
                    + StringUtils.surroundWithSingleQuotes(en.getColor());
        }
        if (!StringUtils.IsNullOrEmpty(en.getSection())) {
            queryString += " AND FNSYOTEI.section = "
                    + StringUtils.surroundWithSingleQuotes(en.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(en.getStartDate())) {
            queryString += " AND FNSYOTEI.start_date >= "
                    + StringUtils.surroundWithSingleQuotes(en.getStartDate());
        }
        queryString += " AND FNSYOTEI.proc_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getFlatStockoutDetailList(FlatStockoutEntity en,
                                          int beginningPos, int count) throws YKKSQLException {
        String columnList = "ticket_no, skanosu, reception_datetime, systemid,zaikosu";

        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO Where  zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(en.getColor())
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND skanosu > 0 " + " ORDER BY ticket_no";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            FlatStockoutDetailEntity entity = new FlatStockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("skanosu")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }
            entity.setReceptionDatetime(row
                            .getValueByColumnName("reception_datetime")
            );
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public int getFlatStockoutDetailCount(FlatStockoutEntity en)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO Where  zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode())
                + " AND color_code = "
                + StringUtils.surroundWithSingleQuotes(en.getColor())
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " AND skanosu > 0 ";
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public long getStockoutRemainCount(String retrievalPlankey)
            throws YKKSQLException {
        String columnList = "FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty";

        String queryString = "SELECT " + columnList
                + " FROM FNSYOTEI Where  retrieval_plankey = "
                + StringUtils.surroundWithSingleQuotes(retrievalPlankey);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        long stockoutRemainCount = 0;
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                stockoutRemainCount = Long
                        .parseLong(row
                                        .getValueByColumnName("FNSYOTEI.retrieval_qty-FNSYOTEI.retrieval_alloc_qty")
                        );
            } catch (Exception ex) {
                stockoutRemainCount = 0;
            }

        }
        return stockoutRemainCount;
    }

    public List getFlatStockoutDetailList(String ticketNo)
            throws YKKSQLException {
        String columnList = "ticket_no, skanosu, reception_datetime, systemid";

        String queryString = "SELECT "
                + columnList
                + " FROM FNZAIKO Where  ticket_no = "
                + StringUtils.surroundWithSingleQuotes(ticketNo)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.FLAT)
                + " AND weight_report_complete_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.WeightReportCompleteFlag.COMPLETED)
                + " AND manage_item_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE)
                + " ORDER BY ticket_no";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            FlatStockoutDetailEntity entity = new FlatStockoutDetailEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            try {
                entity.setStorageCount(Integer.parseInt(row
                                        .getValueByColumnName("skanosu")
                        )
                );
            } catch (Exception ex) {
                entity.setStorageCount(0);
            }
            entity.setReceptionDatetime(row
                            .getValueByColumnName("reception_datetime")
            );
            entity.setSystemId(row.getValueByColumnName("systemid"));
            try {
                entity.setInstockCount(Integer.parseInt(row
                                        .getValueByColumnName("zaikosu")
                        )
                );
            } catch (Exception ex) {
                entity.setInstockCount(0);
            }

            returnList.add(entity);
        }
        return returnList;

    }

    public long getOutQty(String retrievalPlankey) throws YKKSQLException {
        String columnList = "FNSYOTEI.retrieval_alloc_qty";

        String queryString = "SELECT " + columnList
                + " FROM FNSYOTEI Where  retrieval_plankey = "
                + StringUtils.surroundWithSingleQuotes(retrievalPlankey);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        long outQty = 0;
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            try {
                outQty = Long.parseLong(row
                                .getValueByColumnName("retrieval_alloc_qty")
                );
            } catch (Exception ex) {
                outQty = 0;
            }

        }
        return outQty;
    }

    public List getExternalStockoutStartList(String orderMode,
                                             String retrievalStation, String retrievalNo, String orderNo,
                                             int beginningPos, int count) throws YKKSQLException {
        String columnList = "FNZAIKO.nyukohiji,FNZAIKO.zaikey,FMZKEY.zkname1,FMZKEY.zkname2,FMZKEY.zkname3,FNZAIKO.color_code,FNLOCAT.syozaikey,FNZAIKO.zaikosu,FNHANSO.systemid,FNZAIKO.ticket_no";

        String queryString = "SELECT "
                + columnList
                + ",SUM(FNSIJI.nyusyusu),SUM(nvl(trim(FNSIJI.subdivide_flag),0)) AS subdivision"
                + " FROM FNSIJI,FNHANSO,FNZAIKO,FNLOCAT,FMZKEY,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNZAIKO.systemid(+) AND FNZAIKO.systemid = FNLOCAT.systemid(+) AND FNZAIKO.zaikey = FMZKEY.zaikey(+) AND FNZAIKO.manage_item_flag = FMZKEY.manage_item_flag(+) AND FNHANSO.hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNLOCAT.zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) AND TRIM(FNLOCAT.syozaikey) IS NOT NULL ";

        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        } else {
            queryString += " AND fnsyotei.retrieval_station IN ('23', '25', '26')";
        }

        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(orderNo
                            + StringUtils.SinglePercentageMark
            );
        }

        queryString += " GROUP BY " + columnList;

        if (orderMode.equals("1")) {
            queryString += " ORDER BY FNZAIKO.zaikey";
        } else {
            queryString += " ORDER BY FNZAIKO.nyukohiji";
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ExternalStockoutStartEntity entity = new ExternalStockoutStartEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStockinDateTime(row.getValueByColumnName("nyukohiji"));
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));
            entity.setColor(row.getValueByColumnName("color_code"));
            entity.setLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setOriginalLocationNo(row.getValueByColumnName("syozaikey"));
            entity.setSystemId(row.getValueByColumnName("systemid"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            setMckey(entity, retrievalStation, retrievalNo, orderNo);
            int nyusyusu = 0;
            int zaikosu = 0;
            try {
                nyusyusu = Integer.parseInt(row
                                .getValueByColumnName("SUM(FNSIJI.nyusyusu)")
                );
                zaikosu = Integer.parseInt(row.getValueByColumnName("zaikosu"));
                entity.setStockoutCount(nyusyusu);
            } catch (Exception ex) {
                entity.setU("Erro");
                entity.setP("Erro");
            }

            if (nyusyusu < zaikosu) {
                entity.setP(StringUtils.Circle);
                entity.setU("");
            } else {
                entity.setU(StringUtils.Circle);
                entity.setP("");
            }

            returnList.add(entity);

        }

        return returnList;
    }

    private void setMckey(ExternalStockoutStartEntity entity,
                          String retrievalStation, String retrievalNo, String orderNo)
            throws YKKSQLException {
        String queryString = "SELECT DISTINCT FNHANSO.mckey FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) ";
        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        } else {
            queryString += " AND fnsyotei.retrieval_station IN ('23', '25', '26')";
        }

        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo);
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no = "
                    + StringUtils.surroundWithSingleQuotes(orderNo);
        }

        queryString += " AND FNLOCAT.systemid = "
                + StringUtils.surroundWithSingleQuotes(entity.getSystemId());

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String mckey = "";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            mckey += row.getValueByColumnName("mckey") + ",";
        }
        entity.setMckey(mckey);

    }

    public int getExternalStockoutStartCount(String retrievalStation,
                                             String retrievalNo, String orderNo) throws YKKSQLException {
        String queryString = "SELECT COUNT(DISTINCT(FNLOCAT.syozaikey))AS c FROM FNSIJI,FNHANSO,FNLOCAT,FNSYOTEI WHERE FNSYOTEI.retrieval_plankey = FNSIJI.retrieval_plan_key AND FNHANSO.mckey = FNSIJI.mckey(+) AND FNHANSO.systemid = FNLOCAT.systemid(+) AND FNHANSO.hjyotaiflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.HjyotaiFlg.DISPATCH)
                + " AND FNLOCAT.shikiflg = "
                + StringUtils.surroundWithSingleQuotes(DBFlags.ShikiFlg.UNUSED)
                + " AND FNLOCAT.accessflg = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.AccessFlag.ASSIGNABLE)
                + " AND FNLOCAT.zaijyoflg NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.STINSTOCK)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.ERRO_LOCATION)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.ZaijyoFlg.FORBID_LOCATION)
                + " ) AND TRIM(FNLOCAT.syozaikey) IS NOT NULL ";

        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        } else {
            queryString += " AND fnsyotei.retrieval_station IN ('23', '25', '26')";
        }

        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(orderNo
                            + StringUtils.SinglePercentageMark
            );
        }
        // queryString += " GROUP BY syozaikey)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils.IsNullOrEmpty(row.getValueByColumnName("c"))) {
                count = row.getValueByColumnName("c");
            }
        }
        return Integer.parseInt(count);
    }

    public List getExternalStockoutViewList(String orderMode,
                                            String retrievalStation, String retrievalNo, String orderNo,
                                            String displayFinishedRetrieval, int beginningPos, int count)
            throws YKKSQLException {
        StringBuffer b = new StringBuffer();
        b.append("	WITH stock_info_temp	");
        b.append("	       AS (  SELECT   zaikey,	");
        b.append("	                      color_code,	");
        b
                .append("	                      SUM (real_stock_qty) AS real_stock_qty,	");
        b
                .append("	                      SUM (available_stock_qty) AS available_stock_qty,	");
        b.append("	                      SUM (memo_flag) AS memo_flag	");
        b.append("	               FROM   (  SELECT   fnzaiko.zaikey,	");
        b.append("	                                  fnzaiko.color_code,	");
        b.append("	                                  0 AS real_stock_qty,	");
        b
                .append("	                                  SUM (skanosu) AS available_stock_qty,	");
        b.append("	                                  0 AS memo_flag	");
        b.append("	                           FROM   fnzaiko	");
        b.append("	                          WHERE   fnzaiko.ROWID NOT IN	");
        b
                .append("	                                        (SELECT   fnzaiko.ROWID AS r	");
        b
                .append("	                                           FROM   fnzaiko, fnforbidretrieval	");
        b
                .append("	                                          WHERE   fnzaiko.zaikey =	");
        b
                .append("	                                                     fnforbidretrieval.zaikey	");
        b.append("	                                                  AND 	(");
        b
                .append("	                                                        fnzaiko.color_code	");
        b.append("	                                                      =	");
        b
                .append("	                                                           fnforbidretrieval.color_code	 OR ");
        b
                .append("                                                    TRIM(fnforbidretrieval.color_code) IS NULL )");
        b
                .append("	                                                  AND fnforbidretrieval.from_ticketno <=	");
        b
                .append("	                                                        fnzaiko.ticket_no	");
        b
                .append("	                                                  AND fnforbidretrieval.to_ticketno >=	");
        b
                .append("	                                                        fnzaiko.ticket_no	");
        b
                .append("	                                                  AND fnforbidretrieval.from_stock_datetime <=	");
        b
                .append("	                                                        fnzaiko.nyukohiji	");
        b
                .append("	                                                  AND fnforbidretrieval.to_stock_datetime >=	");
        b
                .append("	                                                        fnzaiko.nyukohiji)	");
        b.append("	                                  AND fnzaiko.systemid IN	");
        b
                .append("	                                           (SELECT   DISTINCT fnlocat.systemid	");
        b
                .append("	                                              FROM   fnlocat	");
        b
                .append("	                                             WHERE   fnlocat.ailestno IN	");
        b
                .append("	                                                           (SELECT   DISTINCT	");
        b
                .append("	                                                                     fnunit.ailestno	");
        b
                .append("	                                                              FROM   fnunit	");
        b
                .append("	                                                             WHERE   FNUNIT.unitstat IN	");
        b
                .append("	                                                                           ('1',	");
        b
                .append("	                                                                            '2',	");
        b
                .append("	                                                                            '4'))	");
        b
                .append("	                                                     AND fnlocat.accessflg = '0'	");
        b
                .append("	                                                     AND fnlocat.zaijyoflg NOT IN	");
        b
                .append("	                                                              ('0', '5', '8')	");
        b
                .append("	                                                     AND fnlocat.accessflg = '0'	");
        b
                .append("	                                                     AND fnlocat.shikiflg = '0')	");
        b
                .append("	                                  AND fnzaiko.skanosu > '0'	");
        b
                .append("	                                  AND fnzaiko.weight_report_complete_flag = '2'	");
        b
                .append("	                                  AND fnzaiko.manage_item_flag = '0'	");
        b
                .append("	                                  AND fnzaiko.storage_place_flag = '0'	");
        b.append("	                       GROUP BY   zaikey, color_code	");
        b.append("	                       UNION	");
        b.append("	                         SELECT   fnzaiko.zaikey,	");
        b.append("	                                  fnzaiko.color_code,	");
        b
                .append("	                                  SUM (zaikosu) AS real_stock_qty,	");
        b
                .append("	                                  0 AS available_stock_qty,	");
        b
                .append("	                                  SUM (DECODE (TRIM (memo), NULL, 0, 1))	");
        b.append("	                                     AS memo_flag	");
        b.append("	                           FROM   fnzaiko	");
        b
                .append("	                          WHERE       fnzaiko.zaikosu > '0'	");
        b
                .append("	                                  AND fnzaiko.weight_report_complete_flag = '2'	");
        b
                .append("	                                  AND fnzaiko.manage_item_flag = '0'	");
        b.append("	                       GROUP BY   zaikey, color_code)	");
        b.append("	           GROUP BY   zaikey, color_code)	");
        b
                .append("	SELECT   NVL (TRIM (stock_info_temp.memo_flag), 0) AS memo_flag,	");

        b.append("	         fnsyotei.retrieval_no,	");
        b.append("	         fnsyotei.customer_code,	");
        b.append("	         fnsyotei.zaikey,	");
        b.append("	         fmzkey.zkname1,	");
        b.append("	         fmzkey.zkname2,	");
        b.append("	         fmzkey.zkname3,	");
        b.append("	         fnsyotei.color_code,	");
        b.append("	         fnsyotei.necessary_qty,	");
        b.append("	         fnsyotei.allocation_qty,	");
        b.append("	         fnsyotei.retrieval_qty,	");
        b.append("	         fnsyotei.retrieval_alloc_qty,	");
        b
                .append("	         fnsyotei.retrieval_qty - fnsyotei.retrieval_alloc_qty AS need_qty,	");
        b
                .append("	         NVL (TRIM (stock_info_temp.available_stock_qty), 0)	");
        b.append("	            AS available_stock_qty,	");
        b
                .append("	         NVL (TRIM (stock_info_temp.real_stock_qty), 0) AS real_stock_qty,	");
        b.append("	         fnsyotei.retrieval_plankey,	");
        b.append("	         fnsyotei.start_date,	");
        b.append("	         fnsyotei.start_timing_flag,	");
        b.append("	         fnsyotei.complete_date,	");
        b.append("	         fnsyotei.complete_timing_flag,	");
        b.append("	         fnsyotei.proc_flag	");
        b.append("	  FROM   stock_info_temp, fnsyotei, fmzkey	");
        b.append("	 WHERE   fnsyotei.zaikey = stock_info_temp.zaikey(+)	");
        b.append("	         AND NVL (TRIM(fnsyotei.color_code), ' ') =	");
        b
                .append("	               NVL (TRIM (stock_info_temp.color_code(+)), ' ')	");
        b.append("	         AND fnsyotei.zaikey = fmzkey.zaikey(+)	");
        b.append(" AND fmzkey.manage_item_flag = '0' ");

        String queryString = b.toString();

        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        } else {
            queryString += " AND fnsyotei.retrieval_station IN ('23', '25', '26')";
        }

        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(orderNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (displayFinishedRetrieval.equals("0")) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }

        int totalCount = getExternalStockoutViewCount(retrievalStation,
                retrievalNo, orderNo, displayFinishedRetrieval
        );

        if (totalCount <= 200) {
            if (orderMode.equals("1")) {
                queryString += " ORDER BY zaikey,start_date,start_timing_flag";
            } else if (orderMode.equals("2")) {
                queryString += " ORDER BY start_date,start_timing_flag";
            } else if (orderMode.equals("3")) {
                queryString += " ORDER BY complete_date,complete_timing_flag,start_date,start_timing_flag";

            } else {
                queryString += " ORDER BY retrieval_no";
            }
        } else {
            queryString += " ORDER BY start_date DESC,start_timing_flag DESC";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            ExternalStockoutEntity entity = new ExternalStockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setWhenThisWorkFinishInPlan(row
                            .getValueByColumnName("complete_date")
            );
            entity.setWhenThisWorkFinishInPlanTiming(row
                            .getValueByColumnName("complete_timing_flag")
            );
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setExternalCode(row.getValueByColumnName("customer_code"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("need_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            try {
                entity.setEnableToStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }

            entity.setColor(row.getValueByColumnName("color_code"));

            entity
                    .setMemo(row.getValueByColumnName("memo_flag").equals("0") ? ""
                                    : "有"
                    );
            try {
                entity.setStockoutNecessaryQty(Integer.parseInt(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Integer.parseInt(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Integer.parseInt(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }

            entity.setRetrieved(row.getValueByColumnName("proc_flag").equals(
                            DBFlags.ProcFlag.DEALT
                    )
            );

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            try {
                entity.setTotalCountInstock(Integer.parseInt(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalCountInstock(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public int getExternalStockoutViewCount(String retrievalStation,
                                            String retrievalNo, String orderNo, String displayFinishedRetrieval)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNSYOTEI, FMZKEY ";
        queryString += "WHERE fnsyotei.zaikey = fmzkey.zaikey(+)";
        queryString += " AND fmzkey.manage_item_flag = '0'";
        queryString += " AND fnsyotei.retrieval_station IN('23','25','26')";

        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        }
        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(orderNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (displayFinishedRetrieval.equals("0")) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }
        return Integer.parseInt(count);
    }

    public String[] getExternalStockoutViewNecessaryQtyAndOutQty(
            String retrievalStation, String retrievalNo, String orderNo,
            String displayFinishedRetrieval) throws YKKSQLException {
        String queryString = "SELECT NVL(TRIM(SUM(necessary_qty)),0) AS necessaryQty , NVL(TRIM(SUM(retrieval_alloc_qty)),0) AS outQty FROM FNSYOTEI ";
        queryString += "WHERE retrieval_station IN('23','25','26') ";
        if (!StringUtils.IsNullOrEmpty(retrievalStation)) {
            queryString += " AND fnsyotei.retrieval_station = "
                    + StringUtils.surroundWithSingleQuotes(retrievalStation);
        }
        if (!StringUtils.IsNullOrEmpty(retrievalNo)) {
            queryString += " AND fnsyotei.retrieval_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(retrievalNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (!StringUtils.IsNullOrEmpty(orderNo)) {
            queryString += " AND fnsyotei.order_no LIKE "
                    + StringUtils.surroundWithSingleQuotes(orderNo
                            + StringUtils.SinglePercentageMark
            );
        }
        if (displayFinishedRetrieval.equals("0")) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String[] qty = new String[2];
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            qty[0] = row.getValueByColumnName("necessaryQty");

            qty[1] = row.getValueByColumnName("outQty");

        }
        return qty;
    }

    public List getRetrievalQtyMaintenanceList(
            RetrievalQtyMaintenanceEntity en, int beginningPos, int count)
            throws YKKSQLException {
        StringBuffer b = new StringBuffer();
        b.append("	WITH stock_info_temp	");
        b.append("	       AS (  SELECT   zaikey,	");
        b.append("	                      color_code,	");
        b
                .append("	                      SUM (real_stock_qty) AS real_stock_qty,	");
        b
                .append("	                      SUM (available_stock_qty) AS available_stock_qty,	");
        b.append("	                      SUM (memo_flag) AS memo_flag	");
        b.append("	               FROM   (  SELECT   fnzaiko.zaikey,	");
        b.append("	                                  fnzaiko.color_code,	");
        b.append("	                                  0 AS real_stock_qty,	");
        b
                .append("	                                  SUM (skanosu) AS available_stock_qty,	");
        b.append("	                                  0 AS memo_flag	");
        b.append("	                           FROM   fnzaiko	");
        b.append("	                          WHERE   fnzaiko.ROWID NOT IN	");
        b
                .append("	                                        (SELECT   fnzaiko.ROWID AS r	");
        b
                .append("	                                           FROM   fnzaiko, fnforbidretrieval	");
        b
                .append("	                                          WHERE   fnzaiko.zaikey =	");
        b
                .append("	                                                     fnforbidretrieval.zaikey	");
        b.append("	                                                  AND 	(");
        b
                .append("	                                                        fnzaiko.color_code	");
        b.append("	                                                      =	");
        b
                .append("	                                                           fnforbidretrieval.color_code	 OR ");
        b
                .append("                                                    TRIM(fnforbidretrieval.color_code) IS NULL )");
        b
                .append("	                                                  AND fnforbidretrieval.from_ticketno <=	");
        b
                .append("	                                                        fnzaiko.ticket_no	");
        b
                .append("	                                                  AND fnforbidretrieval.to_ticketno >=	");
        b
                .append("	                                                        fnzaiko.ticket_no	");
        b
                .append("	                                                  AND fnforbidretrieval.from_stock_datetime <=	");
        b
                .append("	                                                        fnzaiko.nyukohiji	");
        b
                .append("	                                                  AND fnforbidretrieval.to_stock_datetime >=	");
        b
                .append("	                                                        fnzaiko.nyukohiji)	");
        b.append("	                                  AND fnzaiko.systemid IN	");
        b
                .append("	                                           (SELECT   DISTINCT fnlocat.systemid	");
        b
                .append("	                                              FROM   fnlocat	");
        b
                .append("	                                             WHERE   fnlocat.ailestno IN	");
        b
                .append("	                                                           (SELECT   DISTINCT	");
        b
                .append("	                                                                     fnunit.ailestno	");
        b
                .append("	                                                              FROM   fnunit	");
        b
                .append("	                                                             WHERE   FNUNIT.unitstat IN	");
        b
                .append("	                                                                           ('1',	");
        b
                .append("	                                                                            '2',	");
        b
                .append("	                                                                            '4'))	");
        b
                .append("	                                                     AND fnlocat.accessflg = '0'	");
        b
                .append("	                                                     AND fnlocat.zaijyoflg NOT IN	");
        b
                .append("	                                                              ('0', '5', '8')	");
        b
                .append("	                                                     AND fnlocat.accessflg = '0'	");
        b
                .append("	                                                     AND fnlocat.shikiflg = '0')	");
        b
                .append("	                                  AND fnzaiko.skanosu > '0'	");
        b
                .append("	                                  AND fnzaiko.weight_report_complete_flag = '2'	");
        b
                .append("	                                  AND fnzaiko.manage_item_flag = '0'	");
        b
                .append("	                                  AND fnzaiko.storage_place_flag = '0'	");
        b.append("	                       GROUP BY   zaikey, color_code	");
        b.append("	                       UNION	");
        b.append("	                         SELECT   fnzaiko.zaikey,	");
        b.append("	                                  fnzaiko.color_code,	");
        b
                .append("	                                  SUM (zaikosu) AS real_stock_qty,	");
        b
                .append("	                                  0 AS available_stock_qty,	");
        b
                .append("	                                  SUM (DECODE (TRIM (memo), NULL, 0, 1))	");
        b.append("	                                     AS memo_flag	");
        b.append("	                           FROM   fnzaiko	");
        b
                .append("	                          WHERE       fnzaiko.zaikosu > '0'	");
        b
                .append("	                                  AND fnzaiko.weight_report_complete_flag = '2'	");
        b
                .append("	                                  AND fnzaiko.manage_item_flag = '0'	");
        b.append("	                       GROUP BY   zaikey, color_code)	");
        b.append("	           GROUP BY   zaikey, color_code)	");
        b
                .append("	SELECT   NVL (TRIM (stock_info_temp.memo_flag), 0) AS memo_flag,	");
        b.append("	         fnsyotei.retrieval_no,	");
        b.append("	         fnsyotei.section,	");
        b.append("	         fnsyotei.start_date,	");
        b.append("	         fnsyotei.start_timing_flag,	");
        b.append("	         fnsyotei.color_code,	");
        b.append("	         fnsyotei.necessary_qty,	");
        b.append("	         fnsyotei.allocation_qty,	");
        b.append("	         fnsyotei.retrieval_qty,	");
        b.append("	         fnsyotei.retrieval_alloc_qty,	");
        b
                .append("	         NVL (TRIM (stock_info_temp.available_stock_qty), 0)	");
        b.append("	            AS available_stock_qty,	");
        b
                .append("	         NVL (TRIM (stock_info_temp.real_stock_qty), 0) AS real_stock_qty,	");
        b.append("	         fnsyotei.retrieval_plankey,	");
        b.append("	         fnsyotei.proc_flag	");
        b.append("	  FROM   stock_info_temp, fnsyotei	");
        b.append("	 WHERE   fnsyotei.zaikey = stock_info_temp.zaikey(+)	");
        b.append("	         AND NVL (TRIM(fnsyotei.color_code), ' ') =	");
        b
                .append("	               NVL (TRIM (stock_info_temp.color_code(+)), ' ')	");

        String queryString = b.toString();

        queryString += " AND fnsyotei.zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode());
        if (!en.isRetrieved()) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }
        if (!StringUtils.IsNullOrEmpty(en.getColor())) {
            queryString += " AND fnsyotei.color_code = "
                    + StringUtils.surroundWithSingleQuotes(en.getColor());
        }
        if (!StringUtils.IsNullOrEmpty(en.getSection())) {
            queryString += " AND fnsyotei.section = "
                    + StringUtils.surroundWithSingleQuotes(en.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(en.getStartDate())) {
            queryString += " AND FNSYOTEI.start_date >= "
                    + StringUtils.surroundWithSingleQuotes(en.getStartDate());
        }

        if (en.getOrderBy().equals("1")) {
            queryString += " ORDER BY FNSYOTEI.start_date, FNSYOTEI.start_timing_flag,FNSYOTEI.section, FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        } else if (en.getOrderBy().equals("2")) {
            queryString += " ORDER BY FNSYOTEI.section, FNSYOTEI.start_date, FNSYOTEI.start_timing_flag, FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        } else {
            queryString += " ORDER BY FNSYOTEI.retrieval_no,FNSYOTEI.color_code";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalQtyMaintenanceEntity entity = new RetrievalQtyMaintenanceEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setStartDate(row.getValueByColumnName("start_date"));
            entity.setStartDateTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            entity.setColor(row.getValueByColumnName("color_code"));
            try {
                entity.setStockoutNecessaryQty(Long.parseLong(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOriginalManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOriginalManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }
            try {
                entity.setStorageQty(Integer.parseInt(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }
            entity.setSection(row.getValueByColumnName("section"));
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setRetrieved(row.getValueByColumnName("proc_flag").equals(
                            DBFlags.ProcFlag.DEALT
                    )
            );

            returnList.add(entity);
        }

        return returnList;
    }

    public int getRetrievalQtyMaintenanceCount(RetrievalQtyMaintenanceEntity en)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNSYOTEI WHERE FNSYOTEI.zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode());
        if (!en.isRetrieved()) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }
        if (!StringUtils.IsNullOrEmpty(en.getColor())) {
            queryString += " AND FNSYOTEI.color_code = "
                    + StringUtils.surroundWithSingleQuotes(en.getColor());
        }
        if (!StringUtils.IsNullOrEmpty(en.getSection())) {
            queryString += " AND FNSYOTEI.section = "
                    + StringUtils.surroundWithSingleQuotes(en.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(en.getStartDate())) {
            queryString += " AND FNSYOTEI.start_date >= "
                    + StringUtils.surroundWithSingleQuotes(en.getStartDate());
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getIOHistoryRetrievalStation() throws YKKSQLException {
//		StringBuffer b = new StringBuffer();		
//		b.append("	select retrieval_station,	 "	);
//		b.append("	       (retrieval_station || ' : ' || decode(retrieval_station,	 "	);
//		b.append("	       '41,42', TRIM (stname),	 "	);
//		b.append("	       trim(stname) || ' Unit')) as stname,	 "	);
//		b.append("	       stno	 "	);
//		b.append("	from(	 "	);
//		b.append("	select ltrim(max(sys_connect_by_path(retrieval_station, ',')),',') as	 "	);
//		b.append("	      retrieval_station,	 "	);
//		b.append("	　　 ltrim(max(sys_connect_by_path(stname, '·')),'·') as stname,	 "	);
//		b.append("	     stno	 "	);
//		b.append("	     from(	 "	);
//		b.append("	SELECT   retrieval_station,	 "	);
//		b.append("	         stname,	 "	);
//		b.append("	         stno,	 "	);
//		b.append("	         rnFirst,	 "	);
//		b.append("	         LEAD (rnFirst) OVER (PARTITION BY stno ORDER BY rnFirst) rnNext	 "	);
//		b.append("	  FROM   (SELECT   retrieval_station,	 "	);
//		b.append("	                   stname,	 "	);
//		b.append("	                   stno,	 "	);
//		b.append("	                   ROW_NUMBER () OVER (ORDER BY stno, retrieval_station desc)	 "	);
//		b.append("	      rnFirst	 "	);
//		b.append("	            FROM   (SELECT   retrieval_station,	 "	);
//		b.append("	                             (REPLACE (TRIM (default_retrieval_st_name),	 "	);
//		b.append("	                                       '出库',	 "	);
//		b.append("	                                       ''))	 "	);
//		b.append("	                                AS stname,	 "	);
//		b.append("	                             ('''' || default_unit_stno || '''') AS stno	 "	);
//		b.append("	                      FROM   fnretrieval_st	 "	);
//		b.append("	                     WHERE   retrieval_station NOT IN ('22', '31', '32'))))	 "	);
//		b.append("	                     start with rnNext is null	 "	);
//		b.append("	　　connect by rnNext = prior rnFirst	 "	);
//		b.append("	group by stno)	 "	);
//		b.append("	union	 "	);
//		b.append("	select retrieval_station,	 "	);
//		b.append("	       (retrieval_station || ' : ' || decode(retrieval_station,	 "	);
//		b.append("	       '31', TRIM (stname),	 "	);
//		b.append("	       '32', TRIM (stname),	 "	);
//		b.append("	       trim(stname) || ' Picking')) as stname,	 "	);
//		b.append("	       stno	 "	);
//		b.append("	from(	 "	);
//		b.append("	select ltrim(max(sys_connect_by_path(retrieval_station, ',')),',') as	 "	);
//		b.append("	      retrieval_station,	 "	);
//		b.append("	　　 ltrim(max(sys_connect_by_path(stname, '·')),'·') as stname,	 "	);
//		b.append("	     stno	 "	);
//		b.append("	     from(	 "	);
//		b.append("	SELECT   retrieval_station,	 "	);
//		b.append("	         stname,	 "	);
//		b.append("	         stno,	 "	);
//		b.append("	         rnFirst,	 "	);
//		b.append("	         LEAD (rnFirst) OVER (PARTITION BY stno ORDER BY rnFirst) rnNext	 "	);
//		b.append("	  FROM   (SELECT   retrieval_station,	 "	);
//		b.append("	                   stname,	 "	);
//		b.append("	                   stno,	 "	);
//		b.append("	                   ROW_NUMBER () OVER (ORDER BY stno, retrieval_station desc)	 "	);
//		b.append("	      rnFirst	 "	);
//		b.append("	            FROM   (SELECT   retrieval_station,	 "	);
//		b.append("	                             (REPLACE (TRIM (default_retrieval_st_name),	 "	);
//		b.append("	                                       '出库',	 "	);
//		b.append("	                                       ''))	 "	);
//		b.append("	                                AS stname,	 "	);
//		b.append("	                             ('''' || default_pickstno || ''',''' ||	 "	);
//		b.append("	      default_rev_pickstno || ''',''' || remove_stno || '''') AS stno	 "	);
//		b.append("	                      FROM   fnretrieval_st	 "	);
//		b.append("	                     WHERE   retrieval_station NOT IN ('41', '42'))))	 "	);
//		b.append("	                     start with rnNext is null	 "	);
//		b.append("	　　connect by rnNext = prior rnFirst	 "	);
//		b.append("	group by stno)	 "	);
//		b.append("	order by retrieval_station	 "	);
//
//		String queryString = b.toString();

//		DBHandler handler = new DBHandler(conn);
//		handler.executeQuery(queryString);
        List returnList = new ArrayList();
//		RecordSet recordSet = handler.getRecordSet();
//		List rowList = recordSet.getRowList();
//		Iterator it = rowList.iterator();
//		while (it.hasNext())
//		{
//			RetrievalStationEntity entity = new RetrievalStationEntity();
//			RecordSetRow row = (RecordSetRow) it.next();
//
//			entity.setRetrievalStationName(row.getValueByColumnName("stname"));
//			entity.setRetrievalStation(row.getValueByColumnName("stno"));
//
//			returnList.add(entity);
//		}

        RetrievalStationEntity entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("11 : 电镀工程 Picking");
        entity.setRetrievalStation("'1202','1105','1203'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("11 : 电镀工程 Unit");
        entity.setRetrievalStation("'1205'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("12 : MF 工程");
        entity.setRetrievalStation("'1108','1219'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("13 : PF 工程");
        entity.setRetrievalStation("'1106','1213'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("14 : VF 工程");
        entity.setRetrievalStation("'1107','1216'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("21 : SI工程 Unit");
        entity.setRetrievalStation("'2204'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("21,22 : SI工程·喷漆工程 Picking");
        entity.setRetrievalStation("'2206','2103','2207'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("23 : 外部 Unit");
        entity.setRetrievalStation("'2216'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("23,24,25,26 : 外部·组装工程·福永·客户 Picking");
        entity.setRetrievalStation("'2209','2104','2210'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("24 : 组装工程 Unit");
        entity.setRetrievalStation("'2214'");
        returnList.add(entity);

        entity = new RetrievalStationEntity();
        entity.setRetrievalStationName("25,26 : 福永·客户 Unit");
        entity.setRetrievalStation("'2217'");
        returnList.add(entity);

//        entity = new RetrievalStationEntity();
//        entity.setRetrievalStationName("31 : 4F 加工工程1");
//        entity.setRetrievalStation("'3202','3101','3203'");
//        returnList.add(entity);
//
//        entity = new RetrievalStationEntity();
//        entity.setRetrievalStationName("32 : 4F 加工工程2");
//        entity.setRetrievalStation("'3205','3102','3206'");
//        returnList.add(entity);
//
//        entity = new RetrievalStationEntity();
//        entity.setRetrievalStationName("41,42 : 3F 加工工程1·3F 加工工程2");
//        entity.setRetrievalStation("'4206'");
//        returnList.add(entity);

        return returnList;
    }

    // Added By WangYiYun inYKKSZ 2008/12/17
    // change count method of BUCKET in fnjiseki
    public int getBucketCount_head(IOHistoryInfoHead head)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) as c FROM (SELECT MCKEY, substr(SAKUSEIHIJI,1,8),TICKET_NO,NYUSYUKBN FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " AND sagyokbn NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                + ")";
        queryString += " GROUP BY MCKEY, substr(SAKUSEIHIJI,1,8),TICKET_NO,NYUSYUKBN)";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils.IsNullOrEmpty(row.getValueByColumnName("c"))) {
                count = row.getValueByColumnName("c");
            }
        }

        return Integer.parseInt(count);
    }

    public int getBucketCount(IOHistoryInfoHead head) throws YKKSQLException {
        String queryString = "SELECT COUNT(TRIM(bucket_no)) as c FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getUserId())) {
            queryString += " AND userid = "
                    + StringUtils.surroundWithSingleQuotes(head.getUserId());
        }
        if (head.getWorkType().equals("入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("通常入库")) {
            queryString += " AND sagyokbn IN ("
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("再入库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);
        } else if (head.getWorkType().equals("出库")) {
            queryString += " AND ((sagyokbn NOT IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                    + ") OR (sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                    + "))";
        } else if (head.getWorkType().equals("通常出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + ")"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("直行出库")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST);
        } else if (head.getWorkType().equals("例外出库")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ZAIKEY_DESIGNATE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.LOCATION_DESIGNATE)
                    + " )"
                    + " AND nyusyukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        } else if (head.getWorkType().equals("维护")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE);
        } else if (head.getWorkType().equals("维护增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + ")";
        } else if (head.getWorkType().equals("维护减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.MAINTENANCE)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("盘点")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT);
        } else if (head.getWorkType().equals("盘点增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("盘点减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("异常排出")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT);
        } else if (head.getWorkType().equals("在库差异信息")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL);
        } else if (head.getWorkType().equals("差异增")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.INCREASE)
                    + " , "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.NOCHANGE)
                    + " )";
        } else if (head.getWorkType().equals("差异减")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                    + " AND sakukbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sakukbn.DECREASE);
        } else if (head.getWorkType().equals("进入")) {
            queryString += " AND sagyokbn IN ( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                    + ")";
        } else if (head.getWorkType().equals("进入禁止")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN);
        } else if (head.getWorkType().equals("进入许可")) {
            queryString += " AND sagyokbn = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN);
        }
        if (!StringUtils.IsNullOrEmpty(head.getRetrievalNo())) {
            queryString += " AND retrieval_no = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getRetrievalNo()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockinStation())) {
            queryString += " AND startstno = "
                    + StringUtils.surroundWithSingleQuotes(head
                            .getStockinStation()
            );
        }
        if (!StringUtils.IsNullOrEmpty(head.getStockoutStation())) {
            queryString += " AND endstno IN ( " + head.getStockoutStation()
                    + ")";
        }
        queryString += " AND sagyokbn NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.FORBID_IN)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.ALLOW_IN)
                + ")";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils.IsNullOrEmpty(row.getValueByColumnName("c"))) {
                count = row.getValueByColumnName("c");
            }
        }

        return Integer.parseInt(count);
    }

    public int getBucketInstockCount(String bucketNo) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO WHERE storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO)
                + " AND bucket_no = "
                + StringUtils.surroundWithSingleQuotes(bucketNo);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public int getItemInstockCount(String itemCode, boolean isInManage)
            throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNZAIKO WHERE zaikey = "

                + StringUtils.surroundWithSingleQuotes(itemCode)
                + " AND storage_place_flag = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.StoragePlaceFlag.AUTO);

        if (isInManage) {
            queryString += " AND manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.INMANAGE);
        } else {
            queryString += " AND manage_item_flag = "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ManageItemFlag.WITHOUTMANAGE);
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public String[] getRetrievalQtyMaintenanceRetrievalQtyAndOutQty(
            RetrievalQtyMaintenanceEntity en) throws YKKSQLException {
        String queryString = "SELECT NVL(TRIM(SUM(retrieval_qty)),0) AS retrievalQty , NVL(TRIM(SUM(retrieval_alloc_qty)),0) AS outQty , NVL(TRIM(SUM(retrieval_qty - retrieval_alloc_qty)),0) AS resultQty FROM FNSYOTEI WHERE FNSYOTEI.zaikey = "
                + StringUtils.surroundWithSingleQuotes(en.getItemCode());
        if (!en.isRetrieved()) {
            queryString += " AND proc_flag =  "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT);
        } else {
            queryString += " AND proc_flag IN( "
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.UNDEALT)
                    + ","
                    + StringUtils
                    .surroundWithSingleQuotes(DBFlags.ProcFlag.DEALT)
                    + ")";
        }
        if (!StringUtils.IsNullOrEmpty(en.getColor())) {
            queryString += " AND FNSYOTEI.color_code = "
                    + StringUtils.surroundWithSingleQuotes(en.getColor());
        }
        if (!StringUtils.IsNullOrEmpty(en.getSection())) {
            queryString += " AND FNSYOTEI.section = "
                    + StringUtils.surroundWithSingleQuotes(en.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(en.getStartDate())) {
            queryString += " AND FNSYOTEI.start_date >= "
                    + StringUtils.surroundWithSingleQuotes(en.getStartDate());
        }
        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String[] qty = new String[3];
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            qty[0] = row.getValueByColumnName("retrievalQty");

            qty[1] = row.getValueByColumnName("outQty");

            qty[2] = row.getValueByColumnName("resultQty");

        }

        return qty;
    }

    public List getRetrievalOrderPrintList(RetrievalOrderPrintHead head, int beginningPos, int count) throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.start_date,FNJISEKI.nyusyusu,FNJISEKI.measure_unit_weight,FNJISEKI.section,FNJISEKI.line,FNJISEKI.retrieval_no";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo())
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getSection())) {
            queryString += " AND section = "
                    + StringUtils.surroundWithSingleQuotes(head.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(head.getLine())) {
            queryString += " AND line = "
                    + StringUtils.surroundWithSingleQuotes(head.getLine());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getNextWorkBeginDate())) {
            queryString += " AND start_date <= "
                    + StringUtils.surroundWithSingleQuotes(head.getNextWorkBeginDate());
        }


        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,color_code,start_date";
        } else {
            queryString += "start_date";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString, beginningPos, count);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalOrderPrintEntity entity = new RetrievalOrderPrintEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setStockoutTime(row.getValueByColumnName("sakuseihiji"));
            entity.setItemNo(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBoxNo(row.getValueByColumnName("bucket_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setSection(row.getValueByColumnName("section"));
            entity.setLine(row.getValueByColumnName("line"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }


    public int getRetrievalOrderPrintCount(RetrievalOrderPrintHead head) throws YKKSQLException {
        String queryString = "SELECT COUNT(*) FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo())
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getSection())) {
            queryString += " AND section = "
                    + StringUtils.surroundWithSingleQuotes(head.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(head.getLine())) {
            queryString += " AND line = "
                    + StringUtils.surroundWithSingleQuotes(head.getLine());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getNextWorkBeginDate())) {
            queryString += " AND start_date <= "
                    + StringUtils.surroundWithSingleQuotes(head.getNextWorkBeginDate());
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        String count = "0";
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            if (!StringUtils
                    .IsNullOrEmpty(row.getValueByColumnName("COUNT(*)"))) {
                count = row.getValueByColumnName("COUNT(*)");
            }
        }

        return Integer.parseInt(count);
    }

    public List getRetrievalOrderPrintList(RetrievalOrderPrintHead head, String ticketNo) throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.start_date,FNJISEKI.nyusyusu,FNJISEKI.measure_unit_weight,FNJISEKI.section,FNJISEKI.line,FNJISEKI.retrieval_no";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo())
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getSection())) {
            queryString += " AND section = "
                    + StringUtils.surroundWithSingleQuotes(head.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(head.getLine())) {
            queryString += " AND line = "
                    + StringUtils.surroundWithSingleQuotes(head.getLine());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getNextWorkBeginDate())) {
            queryString += " AND start_date <= "
                    + StringUtils.surroundWithSingleQuotes(head.getNextWorkBeginDate());
        }
        if (!StringUtils.IsNullOrEmpty(ticketNo)) {
            queryString += " AND ticket_no = "
                    + StringUtils.surroundWithSingleQuotes(ticketNo);
        }


        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,color_code,start_date";
        } else {
            queryString += "start_date";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalOrderPrintEntity entity = new RetrievalOrderPrintEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setStockoutTime(row.getValueByColumnName("sakuseihiji"));
            entity.setItemNo(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBoxNo(row.getValueByColumnName("bucket_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setSection(row.getValueByColumnName("section"));
            entity.setLine(row.getValueByColumnName("line"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getRetrievalOrderPrintList(RetrievalOrderPrintHead head) throws YKKSQLException {
        String columnList = "FNJISEKI.sakuseihiji,FNJISEKI.zaikey,FNJISEKI.zkname,FNJISEKI.ticket_no,FNJISEKI.bucket_no,FNJISEKI.color_code,FNJISEKI.start_date,FNJISEKI.nyusyusu,FNJISEKI.measure_unit_weight,FNJISEKI.section,FNJISEKI.line,FNJISEKI.retrieval_no";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo())
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);
        if (!StringUtils.IsNullOrEmpty(head.getItem())) {
            queryString += " AND zaikey = "
                    + StringUtils.surroundWithSingleQuotes(head.getItem());
        }
        if (!StringUtils.IsNullOrEmpty(head.getSection())) {
            queryString += " AND section = "
                    + StringUtils.surroundWithSingleQuotes(head.getSection());
        }
        if (!StringUtils.IsNullOrEmpty(head.getLine())) {
            queryString += " AND line = "
                    + StringUtils.surroundWithSingleQuotes(head.getLine());
        }
        if (!StringUtils.IsNullOrEmpty(head.getColorCode())) {
            queryString += " AND color_code = "
                    + StringUtils.surroundWithSingleQuotes(head.getColorCode());
        }
        if (!StringUtils.IsNullOrEmpty(head.getNextWorkBeginDate())) {
            queryString += " AND start_date <= "
                    + StringUtils.surroundWithSingleQuotes(head.getNextWorkBeginDate());
        }


        queryString += " ORDER BY ";
        if (head.getOrderBy().equals("1")) {
            queryString += "zaikey,color_code,start_date";
        } else {
            queryString += "start_date";
        }

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RetrievalOrderPrintEntity entity = new RetrievalOrderPrintEntity();
            RecordSetRow row = (RecordSetRow) it.next();
            entity.setStockoutTime(row.getValueByColumnName("sakuseihiji"));
            entity.setItemNo(row.getValueByColumnName("zaikey"));
            entity.setItemName(row.getValueByColumnName("zkname"));
            entity.setColorCode(row.getValueByColumnName("color_code"));
            entity.setTicketNo(row.getValueByColumnName("ticket_no"));
            entity.setBoxNo(row.getValueByColumnName("bucket_no"));
            entity.setMeasureUnitWeight(new BigDecimal(row
                            .getValueByColumnName("measure_unit_weight")
                    )
            );
            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setSection(row.getValueByColumnName("section"));
            entity.setLine(row.getValueByColumnName("line"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Integer.parseInt(row
                                        .getValueByColumnName("nyusyusu")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public RetrievalStationBufferSettingEntity getRetrievalStationBufferSettingEntity() throws YKKSQLException {
        RetrievalStationBufferSettingEntity entity = new RetrievalStationBufferSettingEntity();
        String queryString = "SELECT FNSTATION.STNO,FNSTATION.HANSOMAX FROM FNSTATION WHERE FNSTATION.STNO IN " +
                "('1201','1211','1214','1217','2203','2205','2208','2211','2212','2213')";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();
            String stationNo = row.getValueByColumnName("STNO");
            int buffer = 0;
            try {
                buffer = Integer.parseInt(row.getValueByColumnName("HANSOMAX"));
            } catch (Exception ex) {
                buffer = 0;
            }

            if (stationNo.equals("1201")) {
                entity.setBuffer1201(buffer);
            } else if (stationNo.equals("1211")) {
                entity.setBuffer1211(buffer);
            } else if (stationNo.equals("1214")) {
                entity.setBuffer1214(buffer);
            } else if (stationNo.equals("1217")) {
                entity.setBuffer1217(buffer);
            } else if (stationNo.equals("2203")) {
                entity.setBuffer2203(buffer);
            } else if (stationNo.equals("2205")) {
                entity.setBuffer2205(buffer);
            } else if (stationNo.equals("2208")) {
                entity.setBuffer2208(buffer);
            } else if (stationNo.equals("2211")) {
                entity.setBuffer2211(buffer);
            } else if (stationNo.equals("2212")) {
                entity.setBuffer2212(buffer);
            } else if (stationNo.equals("2213")) {
                entity.setBuffer2213(buffer);
            }
        }

        return entity;
    }

    public void setStationBuffer(RetrievalStationBufferSettingEntity entity) throws YKKSQLException {
        if (entity.getBuffer1201() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer1201() + ",SIJIMAX = "
                    + entity.getBuffer1201() + " WHERE STNO = '1201'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer1211() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer1211() + ",SIJIMAX = "
                    + entity.getBuffer1211() + " WHERE STNO = '1211'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer1214() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer1214() + ",SIJIMAX = "
                    + entity.getBuffer1214() + " WHERE STNO = '1214'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer1217() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer1217() + ",SIJIMAX = "
                    + entity.getBuffer1217() + " WHERE STNO = '1217'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2203() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2203() + ",SIJIMAX = "
                    + entity.getBuffer2203() + " WHERE STNO = '2203'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2205() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2205() + ",SIJIMAX = "
                    + entity.getBuffer2205() + " WHERE STNO = '2205'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2208() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2208() + ",SIJIMAX = "
                    + entity.getBuffer2208() + " WHERE STNO = '2208'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2211() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2211() + ",SIJIMAX = "
                    + entity.getBuffer2211() + " WHERE STNO = '2211'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2212() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2212() + ",SIJIMAX = "
                    + entity.getBuffer2212() + " WHERE STNO = '2212'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
        if (entity.getBuffer2213() != 0) {
            String queryString = "UPDATE FNSTATION SET HANSOMAX = "
                    + entity.getBuffer2213() + ",SIJIMAX = "
                    + entity.getBuffer2213() + " WHERE STNO = '2213'";
            DBHandler handler = new DBHandler(conn);
            handler.executeUpdate(queryString);
        }
    }

    public List getStockoutEntity(String prNo)
            throws YKKSQLException {
        StringBuffer b = new StringBuffer();
        b.append("	SELECT *\n" +
                "  FROM (SELECT proc_flag,\n" +
                "               section,\n" +
                "               retrieval_qty,\n" +
                "               retrieval_alloc_qty,\n" +
                "               line,\n" +
                "               line_type,\n" +
                "               retrieval_station,\n" +
                "               start_date,\n" +
                "               start_timing_flag,\n" +
                "               complete_date,\n" +
                "               complete_timing_flag,\n" +
                "               fnsyotei.zaikey,\n" +
                "               retrieval_no,\n" +
                "               zkname1,\n" +
                "               zkname2,\n" +
                "               zkname3,\n" +
                "               color_code,\n" +
                "               necessary_qty,\n" +
                "               allocation_qty,\n" +
                "               retrieval_qty - retrieval_alloc_qty\n" +
                "                  AS need_qty,\n" +
                "               retrieval_plankey,\n" +
                "               pr_no,\n" +
                "               manage_item_flag,\n" +
                "               (SELECT NVL (TRIM (SUM (skanosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE (    NOT EXISTS\n" +
                "                                   (SELECT 1\n" +
                "                                      FROM fnforbidretrieval\n" +
                "                                     WHERE     fnzaiko.zaikey =\n" +
                "                                                  fnforbidretrieval.zaikey\n" +
                "                                           AND (   fnzaiko.color_code =\n" +
                "                                                      fnforbidretrieval.color_code\n" +
                "                                                OR TRIM (\n" +
                "                                                      fnforbidretrieval.color_code)\n" +
                "                                                      IS NULL)\n" +
                "                                           AND fnforbidretrieval.from_ticketno <=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.to_ticketno >=\n" +
                "                                                  fnzaiko.ticket_no\n" +
                "                                           AND fnforbidretrieval.from_stock_datetime <=\n" +
                "                                                  fnzaiko.nyukohiji\n" +
                "                                           AND fnforbidretrieval.to_stock_datetime >=\n" +
                "                                                  fnzaiko.nyukohiji)\n" +
                "                        AND EXISTS\n" +
                "                               (SELECT 1\n" +
                "                                  FROM fnlocat\n" +
                "                                 WHERE     fnlocat.ailestno IN\n" +
                "                                              (SELECT DISTINCT\n" +
                "                                                      fnunit.ailestno\n" +
                "                                                 FROM fnunit\n" +
                "                                                WHERE fnunit.unitstat IN\n" +
                "                                                         ('1', '2', '4'))\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.zaijyoflg NOT IN\n" +
                "                                              ('0', '5', '8')\n" +
                "                                       AND fnlocat.accessflg = '0'\n" +
                "                                       AND fnlocat.shikiflg = '0'\n" +
                "                                       AND fnlocat.systemid =\n" +
                "                                              fnzaiko.systemid)\n" +
                "                        AND fnzaiko.skanosu > '0'\n" +
                "                        AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                        AND fnzaiko.manage_item_flag = '0'\n" +
                "                        AND fnzaiko.storage_place_flag = '0'\n" +
                "                        AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                        AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                               NVL (TRIM (FNSYOTEI.color_code), ' ')))\n" +
                "                  AS available_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (zaikosu)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS real_stock_qty,\n" +
                "               (SELECT NVL (TRIM (SUM (DECODE (TRIM (memo), NULL, 0, 1))), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.zaikosu > '0'\n" +
                "                       AND fnzaiko.weight_report_complete_flag = '2'\n" +
                "                       AND fnzaiko.manage_item_flag = '0'\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS memo_flag,\n" +
                "               (SELECT NVL (TRIM (SUM (plan_qty)), 0)\n" +
                "                  FROM fnzaiko\n" +
                "                 WHERE     fnzaiko.weight_report_complete_flag IN ('0', '1')\n" +
                "                       AND fnzaiko.zaikey = FNSYOTEI.zaikey\n" +
                "                       AND NVL (TRIM (fnzaiko.color_code), ' ') =\n" +
                "                              NVL (TRIM (FNSYOTEI.color_code), ' '))\n" +
                "                  AS plan_qty\n" +
                "          FROM fnsyotei, fmzkey\n" +
                "         WHERE     fnsyotei.zaikey = fmzkey.zaikey(+)\n" +
                "               AND fmzkey.manage_item_flag = '0'\n" +
                "               AND fnsyotei.retrieval_station NOT IN ('23', '25', '26'))\n" +
                " WHERE ");
        b.append("  pr_no = " + StringUtils
                .surroundWithSingleQuotes(prNo));

        String queryString = b.toString();

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            StockoutEntity entity = new StockoutEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setWhenNextWorkBegin(row.getValueByColumnName("start_date"));
            entity.setWhenNextWorkBeginTiming(row
                            .getValueByColumnName("start_timing_flag")
            );
            entity.setWhenThisWorkFinishInPlan(row
                            .getValueByColumnName("complete_date")
            );
            entity.setWhenThisWorkFinishInPlanTiming(row
                            .getValueByColumnName("complete_timing_flag")
            );
            entity.setRetrievalPlankey(row
                            .getValueByColumnName("retrieval_plankey")
            );
            entity.setItemCode(row.getValueByColumnName("zaikey"));
            entity.setRetrievalNo(row.getValueByColumnName("retrieval_no"));
            try {
                entity.setStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("need_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutCount(0);
            }

            try {
                entity.setEnableToStockoutCount(Long.parseLong(row
                                        .getValueByColumnName("available_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setEnableToStockoutCount(0);
            }

            entity.setColor(row.getValueByColumnName("color_code"));

            entity
                    .setMemo(row.getValueByColumnName("memo_flag").equals("0") ? ""
                                    : "有"
                    );
            try {
                entity.setStockoutNecessaryQty(Long.parseLong(row
                                        .getValueByColumnName("necessary_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setStockoutNecessaryQty(0);
            }
            try {
                entity.setWavesRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("allocation_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setWavesRetrievalQty(0);
            }
            try {
                entity.setManagementRetrievalQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setManagementRetrievalQty(0);
            }
            try {
                entity.setOutQty(Long.parseLong(row
                                        .getValueByColumnName("retrieval_alloc_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setOutQty(0);
            }

            entity.setRetrieved(row.getValueByColumnName("proc_flag").equals(
                            DBFlags.ProcFlag.DEALT
                    )
            );

            entity.setItemName1(row.getValueByColumnName("zkname1"));
            entity.setItemName2(row.getValueByColumnName("zkname2"));
            entity.setItemName3(row.getValueByColumnName("zkname3"));

            try {
                entity.setTotalCountInstock(Long.parseLong(row
                                        .getValueByColumnName("real_stock_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setTotalCountInstock(0);
            }

            try {
                entity.setPlanQty(Long.parseLong(row
                                        .getValueByColumnName("plan_qty")
                        )
                );
            } catch (Exception ex) {
                entity.setPlanQty(0);
            }
            entity.setItemManageFlag(row.getValueByColumnName("manage_item_flag"));
            entity.setRetrievalStation(row.getValueByColumnName("retrieval_station"));

            returnList.add(entity);
        }
        return returnList;
    }

    public void systemMaintenance(SystemMaintenanceEntity entity) throws YKKSQLException {
        String queryString = "UPDATE WARENAVI_SYSTEM SET FIFO_DAYS = "
                + entity.getFifoDays();
        DBHandler handler = new DBHandler(conn);
        handler.executeUpdate(queryString);
    }

    public int getSystemData() throws YKKSQLException {
        int fifoDays = 0;
        String columnList = "fifo_days";
        String queryString = "SELECT " + columnList + " FROM WARENAVI_SYSTEM ";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);

        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            RecordSetRow row = (RecordSetRow) it.next();

            fifoDays = Integer.parseInt(row.getValueByColumnName("fifo_days"));
        }
        return fifoDays;
    }

    public List getIOHistorySummaryUpList(IOHistorySummaryHead head) throws YKKSQLException {

        String columnList = "SUM (CASE WHEN startstno = '1101' THEN 1 ELSE 0 END) AS ST1101, " +
                "SUM (CASE WHEN startstno = '1102' THEN 1 ELSE 0 END) AS ST1102, " +
                "SUM (CASE WHEN startstno = '2101' THEN 1 ELSE 0 END) AS ST2101, " +
                "SUM (CASE WHEN startstno = '2105' THEN 1 ELSE 0 END) AS ST2105 ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());

        queryString += " AND sagyokbn IN ("
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.AUTO)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                + ")"
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKIN);

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistorySummaryStationUpEntity entity = new IOHistorySummaryStationUpEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUnitBox("小计");
            try {
                entity.setSt1101(Integer.parseInt(row.getValueByColumnName("ST1101")));
            } catch (Exception ex) {
                entity.setSt1101(0);
            }
            try {
                entity.setSt1102(Integer.parseInt(row.getValueByColumnName("ST1102")));
            } catch (Exception ex) {
                entity.setSt1102(0);
            }
            try {
                entity.setSt2101(Integer.parseInt(row.getValueByColumnName("ST2101")));
            } catch (Exception ex) {
                entity.setSt2101(0);
            }
            try {
                entity.setSt2105(Integer.parseInt(row.getValueByColumnName("ST2105")));
            } catch (Exception ex) {
                entity.setSt2105(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getIOHistorySummaryMidList(IOHistorySummaryHead head) throws YKKSQLException {
        String columnList = "SUM (CASE WHEN endstno = '1205' THEN 1 ELSE 0 END) AS ST1205, " +
                "SUM (CASE WHEN endstno = '2204' THEN 1 ELSE 0 END) AS ST2204, " +
                "SUM (CASE WHEN endstno = '2216' THEN 1 ELSE 0 END) AS ST2216, " +
                "SUM (CASE WHEN endstno = '2217' THEN 1 ELSE 0 END) AS ST2217, " +
                "SUM (CASE WHEN endstno = '2214' THEN 1 ELSE 0 END) AS ST2214 ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());

//        queryString += " AND sagyokbn IN ( "
//                + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
//                + ","
//                + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
//                + ")"
//                + " AND nyusyukbn = "
//                + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);

        queryString += " AND ((sagyokbn NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                + " )"
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                + ") OR (sagyokbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                + "))";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistorySummaryStationMidEntity entity = new IOHistorySummaryStationMidEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUnitBox("小计");
            try {
                entity.setSt1205(Integer.parseInt(row.getValueByColumnName("ST1205")));
            } catch (Exception ex) {
                entity.setSt1205(0);
            }
            try {
                entity.setSt2204(Integer.parseInt(row.getValueByColumnName("ST2204")));
            } catch (Exception ex) {
                entity.setSt2204(0);
            }
            try {
                entity.setSt2216(Integer.parseInt(row.getValueByColumnName("ST2216")));
            } catch (Exception ex) {
                entity.setSt2216(0);
            }
            try {
                entity.setSt2217(Integer.parseInt(row.getValueByColumnName("ST2217")));
            } catch (Exception ex) {
                entity.setSt2217(0);
            }
            try {
                entity.setSt2214(Integer.parseInt(row.getValueByColumnName("ST2214")));
            } catch (Exception ex) {
                entity.setSt2214(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }

    public List getIOHistorySummaryLowList(IOHistorySummaryHead head) throws YKKSQLException {
        List returnList = new ArrayList();

        String columnList = "SUM (CASE WHEN startstno = '1105' AND endstno = '1203' THEN 1 ELSE 0 END) AS ST1202, " +
                "SUM (CASE WHEN endstno in ('1106','1213') THEN 1 ELSE 0 END) AS ST1212, " +
                "SUM (CASE WHEN endstno in ('1107','1216') THEN 1 ELSE 0 END) AS ST1215, " +
                "SUM (CASE WHEN endstno in ('1108','1219') THEN 1 ELSE 0 END) AS ST1218, " +
                "SUM (CASE WHEN endstno in ('2206','2103','2207') THEN 1 ELSE 0 END) AS ST2206, " +
                "SUM (CASE WHEN endstno in ('2209','2104','2210') THEN 1 ELSE 0 END) AS ST2209 ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());

//        queryString += " AND sagyokbn IN ("
//                + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
//                + "," + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
//                + "," + StringUtils
//                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT) + ")";

        queryString += " AND ((sagyokbn NOT IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.CYCLECOUNT)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.NOT_EQUAL)
                + " , "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                + " )"
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT)
                + ") OR (sagyokbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.ROUTING)
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.ST_TO_ST)
                + "))";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        while (it.hasNext()) {
            IOHistorySummaryStationLowEntity entity = new IOHistorySummaryStationLowEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUnitBox("出库");
            try {
                entity.setSt1202(Integer.parseInt(row.getValueByColumnName("ST1202")));
            } catch (Exception ex) {
                entity.setSt1202(0);
            }
            try {
                entity.setSt1212(Integer.parseInt(row.getValueByColumnName("ST1212")));
            } catch (Exception ex) {
                entity.setSt1212(0);
            }
            try {
                entity.setSt1215(Integer.parseInt(row.getValueByColumnName("ST1215")));
            } catch (Exception ex) {
                entity.setSt1215(0);
            }
            try {
                entity.setSt1218(Integer.parseInt(row.getValueByColumnName("ST1218")));
            } catch (Exception ex) {
                entity.setSt1218(0);
            }
            try {
                entity.setSt2206(Integer.parseInt(row.getValueByColumnName("ST2206")));
            } catch (Exception ex) {
                entity.setSt2206(0);
            }
            try {
                entity.setSt2209(Integer.parseInt(row.getValueByColumnName("ST2209")));
            } catch (Exception ex) {
                entity.setSt2209(0);
            }
            returnList.add(entity);
        }

        columnList = "SUM (CASE WHEN startstno = '1105' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST1202, " +
                "SUM (CASE WHEN startstno = '1106' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST1212, " +
                "SUM (CASE WHEN startstno = '1107' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST1215, " +
                "SUM (CASE WHEN startstno = '1108' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST1218, " +
                "SUM (CASE WHEN startstno = '2103' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST2206, " +
                "SUM (CASE WHEN startstno = '2104' AND endstno LIKE '9%' THEN 1 ELSE 0 END) AS ST2209 ";
        queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());

        queryString += " AND sagyokbn IN ("
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                + "," + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                + "," + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT) + ")";

        handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        recordSet = handler.getRecordSet();
        rowList = recordSet.getRowList();
        it = rowList.iterator();
        while (it.hasNext()) {
            IOHistorySummaryStationLowEntity entity = new IOHistorySummaryStationLowEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setUnitBox("回库");
            try {
                entity.setSt1202(Integer.parseInt(row.getValueByColumnName("ST1202")));
            } catch (Exception ex) {
                entity.setSt1202(0);
            }
            try {
                entity.setSt1212(Integer.parseInt(row.getValueByColumnName("ST1212")));
            } catch (Exception ex) {
                entity.setSt1212(0);
            }
            try {
                entity.setSt1215(Integer.parseInt(row.getValueByColumnName("ST1215")));
            } catch (Exception ex) {
                entity.setSt1215(0);
            }
            try {
                entity.setSt1218(Integer.parseInt(row.getValueByColumnName("ST1218")));
            } catch (Exception ex) {
                entity.setSt1218(0);
            }
            try {
                entity.setSt2206(Integer.parseInt(row.getValueByColumnName("ST2206")));
            } catch (Exception ex) {
                entity.setSt2206(0);
            }
            try {
                entity.setSt2209(Integer.parseInt(row.getValueByColumnName("ST2209")));
            } catch (Exception ex) {
                entity.setSt2209(0);
            }
            returnList.add(entity);
        }

        return returnList;
    }

    public List getOutHistorySummaryList(OutHistorySummaryHead head) throws YKKSQLException {
        String columnList = "section,count(1) as box ";
        String queryString = "SELECT " + columnList
                + " FROM FNJISEKI WHERE sakuseihiji BETWEEN "
                + StringUtils.surroundWithSingleQuotes(head.getTimeFrom())
                + " AND "
                + StringUtils.surroundWithSingleQuotes(head.getTimeTo());

        queryString += " AND sagyokbn IN ( "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PLAN)
                + ","
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.PICKOUT)
                + "," + StringUtils
                .surroundWithSingleQuotes(DBFlags.Sagyokbn.RESTOCKIN)
                + ")"
                + " AND nyusyukbn = "
                + StringUtils
                .surroundWithSingleQuotes(DBFlags.Nyusyukbn.STOCKOUT);

        queryString += " AND trim(section) is not null AND section like "
                + StringUtils
                .surroundWithSingleQuotes(head.getSection() + "%");
        queryString += "GROUP BY section ORDER BY section";

        DBHandler handler = new DBHandler(conn);
        handler.executeQuery(queryString);
        List returnList = new ArrayList();
        RecordSet recordSet = handler.getRecordSet();
        List rowList = recordSet.getRowList();
        Iterator it = rowList.iterator();
        int i = 1;
        while (it.hasNext()) {
            OutHistorySummarySectionEntity entity = new OutHistorySummarySectionEntity();
            RecordSetRow row = (RecordSetRow) it.next();

            entity.setNo(String.valueOf(i++));
            entity.setSection(row.getValueByColumnName("section"));
            try {
                entity.setBox(Integer.parseInt(row.getValueByColumnName("box")));
            } catch (Exception ex) {
                entity.setBox(0);
            }

            returnList.add(entity);
        }
        return returnList;
    }
}