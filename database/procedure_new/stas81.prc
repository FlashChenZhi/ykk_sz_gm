DROP PROCEDURE AWC.STAS81;

CREATE OR REPLACE PROCEDURE AWC.STAS81
(
    IN_SOKOKBN      IN      VARCHAR2,
    IN_AILESTNO     IN      VARCHAR2,
    IN_HARDZONE     IN      VARCHAR2,
    IN_SOFTZONE     IN      VARCHAR2,
    IO_SYOZAIKEY    IN OUT  VARCHAR2,
    IO_HARDZONE     IN OUT  VARCHAR2,
    IO_SOFTZONE     IN OUT  VARCHAR2,
    IO_RC           IN OUT  NUMBER,
    IO_MSGDAT       IN OUT  VARCHAR2,
    IN_TANAKANFLG   IN      BOOLEAN :=  FALSE
)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?��??��`??
==============================================================================================================================*/
IS
    /*==========================================================================================================================
    ||  ?��??����    ==========================================================================================================================*/
    DEF_MYNAME      CONSTANT VARCHAR2   ( 10) := 'STAS81';
    DEF_MYNAMEJP    CONSTANT VARCHAR2   ( 60) := 'Search Vacant Location?iModify-Location?j';


    /*==========================================================================================================================
    ||  ?��??����
    ==========================================================================================================================*/
    ZONE_LOOP_CNT       NUMBER  (  5);
    AILE_TOTAL_CNT      NUMBER  (  5);
    WK_AILESTNO         FNAILCTL.STNO%TYPE;
    WK_SAILE            FNSOKO.SAILE%TYPE;
    WK_EAILE            FNSOKO.EAILE%TYPE;
    WK_AILEORDER        FNSOKO.AILEORDER%TYPE;
    WK_FNSOKO_ROWID     ROWID;

    WK_MSGMAKE      BOOLEAN := FALSE;


    WK_MSGBUFF      VARCHAR2(32767);


    /*==========================================================================================================================
    ||  ?J?[?\???����
    ==========================================================================================================================*/

    CURSOR  FNSOFTZONEYUSEN_GET( IN_SOFTZONE IN VARCHAR2 ) IS
        SELECT FNSOFTZONEYUSEN.YUSENSOFTZONE, DECODE( FNSOFTZONEYUSEN.AKIKENKBN, ' ', FNSOFTZONE.AKIKENKBN,
                                                                                      FNSOFTZONEYUSEN.AKIKENKBN ) AS AKIKENKBN
            FROM    FNSOFTZONE, FNSOFTZONEYUSEN
            WHERE   FNSOFTZONE.SOFTZONE         =   FNSOFTZONEYUSEN.SOFTZONE
              AND   FNSOFTZONEYUSEN.SOFTZONE    =   IN_SOFTZONE
            ORDER BY FNSOFTZONEYUSEN.SOFTZONEORDER;
    DATA_FNSOFTZONEYUSEN_GET    FNSOFTZONEYUSEN_GET%ROWTYPE;


/*==============================================================================================================================
|| ?����??��
==============================================================================================================================*/
BEGIN
    /*==========================================================================================================================
    ||  ?����G???[?��������������??A?��I?��B
    ==========================================================================================================================*/
    IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
        WK_MSGBUFF := 'The error has already occurred.'
                   || 'IO_RC=[' || IO_RC || ']';
        GOTO ENDLABEL;
    END IF;


    /*==========================================================================================================================
    ||  ?o?��p?????[?^?������??b?Z?[?W?����t???O?��????��
    ==========================================================================================================================*/
    IO_RC           := 0;
    IO_MSGDAT       := NULL;
    WK_MSGMAKE      := FALSE;
    IO_SYOZAIKEY    := NULL;
    IO_HARDZONE     := NULL;
    IO_SOFTZONE     := NULL;


    /*==========================================================================================================================
    ||  ?\?t?g?]?[???D?��??����J?[?\???��I?[?v??
    ==========================================================================================================================*/
    OPEN FNSOFTZONEYUSEN_GET( IN_SOFTZONE );
    LOOP
        /*======================================================================================================================
        ||  ?\?t?g?]?[???D?��??����J?[?\???����??��
        ======================================================================================================================*/
        FETCH FNSOFTZONEYUSEN_GET INTO DATA_FNSOFTZONEYUSEN_GET;

        IF FNSOFTZONEYUSEN_GET%NOTFOUND = TRUE THEN

            IF FNSOFTZONEYUSEN_GET%ROWCOUNT = 0 THEN
                IO_RC       := 40386;

                IO_MSGDAT   := IN_SOFTZONE;
                WK_MSGMAKE  := TRUE;
                WK_MSGBUFF  := 'A record does not exist in a softzone priority file.'
                            || 'SOFTZONE =['  || IN_SOFTZONE  || ']';
            END IF;
            EXIT;
        END IF;


        /*======================================================================================================================
        ||  ?G???[???b?Z?[?W?????��i?����G???[???��??[?v?��J?����������j
        ======================================================================================================================*/
        IO_RC       := 0;
        IO_MSGDAT   := NULL;
        WK_MSGMAKE  := FALSE;


        /*======================================================================================================================
        ||  ?��������\?t?g?]?[???����??��B
        ======================================================================================================================*/
        IO_SOFTZONE := DATA_FNSOFTZONEYUSEN_GET.YUSENSOFTZONE;


        /*======================================================================================================================
        ||  ?w?��q?������{?n?[?h?]?[???��n?[?h?]?[?????��????i???[?v?��??j?����������B
        ======================================================================================================================*/
        STAS21( IN_SOKOKBN, IN_HARDZONE, ZONE_LOOP_CNT, IO_RC, IO_MSGDAT );


        IF IO_RC != 0 THEN
            GOTO ENDLABEL;
        END IF;


        /*======================================================================================================================
        ||  ?w?��q?������{?n?[?h?]?[???��n?[?h?]?[?????��????��J?������B
        ======================================================================================================================*/
        FOR ZONE_CNT IN 1..ZONE_LOOP_CNT LOOP
            /*==================================================================================================================
            ||  ?G???[???b?Z?[?W?????��i?����G???[???��??[?v?��J?����������j
            ==================================================================================================================*/
            IO_RC       := 0;
            IO_MSGDAT   := NULL;
            WK_MSGMAKE  := FALSE;


            STAS20( IN_SOKOKBN, IN_HARDZONE, ZONE_CNT, IO_HARDZONE, IO_RC, IO_MSGDAT );

            IF IO_RC != 0 THEN
                EXIT;
            END IF;


            /*==================================================================================================================
            ||  ?A?C??STNo?��w?����L?��?(?A?C???����r?A?C???????��A?C??STNo?w?��
            ==================================================================================================================*/
            IF IN_AILESTNO IS NOT NULL AND PA_AWC_DEFINE.STATION_MINYURYOKU != IN_AILESTNO THEN
                /*==============================================================================================================
                ||  ???������A?C???����w?��]?[???����I?����??������������??A?G???[?������B
                ==============================================================================================================*/
                STAS85( IN_SOKOKBN, IO_HARDZONE, IO_SOFTZONE, NULL, IN_AILESTNO, IO_RC, IO_MSGDAT );
                IF 0 != IO_RC THEN
                    GOTO ZONE_LOOP_ENDLABEL;
                END IF;


                /*==============================================================================================================
                ||  ?��K?������I???������{?����B
                ==============================================================================================================*/
                STAS80( IN_SOKOKBN, 0, IO_HARDZONE, IO_SOFTZONE, IN_AILESTNO, DATA_FNSOFTZONEYUSEN_GET.AKIKENKBN, IO_SYOZAIKEY,
                        IO_RC, IO_MSGDAT, IN_TANAKANFLG );
                IF 0 != IO_RC THEN
                    GOTO ZONE_LOOP_ENDLABEL;
                END IF;


            /*==================================================================================================================
            ||  ?A?C??STNo?��w?��������?(?A?C??????)
            ==================================================================================================================*/
            ELSE
                /*==================================================================================================================
                ||  ?A?C???I?[?_?[?m???������i?w?��q?������������j
                ||  ?��������F?������OR UPDATE?��������H
                ||          ?����??Z?b?V?????����??��??��X?P?W???[???����{?������A?����A?C???��????��A?������U?o???��������A?r?����
                ||            ?����A?C?????����K?��T?C?N???b?N?��������������X?B
                ==================================================================================================================*/
                BEGIN
                    SELECT FNSOKO.AILEORDER, FNSOKO.SAILE, FNSOKO.EAILE, FNSOKO.ROWID
                        INTO WK_AILEORDER, WK_SAILE, WK_EAILE, WK_FNSOKO_ROWID
                        FROM    FNSOKO
                        WHERE   FNSOKO.SOKOKBN  =   IN_SOKOKBN
                        FOR UPDATE;


                EXCEPTION
                    WHEN OTHERS THEN

                        IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
                            IO_RC       := 30001;
                            WK_MSGBUFF  := 'The deadlock occurred in warehouse control at the time of Aile Order acquisition.';
                            IO_MSGDAT   := WK_MSGBUFF;
                            WK_MSGMAKE  := TRUE;


                        ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
                            IO_RC       := 40023;

                            IO_MSGDAT   := IN_SOKOKBN;
                            WK_MSGMAKE  := TRUE;
                            WK_MSGBUFF  := 'A record does not exist in a warehouse control file.'
                                        || 'Warehouse=[' || IN_SOKOKBN || ']';

                        ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
                            IO_RC       := 40024;

                            IO_MSGDAT   := IN_SOKOKBN;
                            WK_MSGMAKE  := TRUE;
                            WK_MSGBUFF  := 'A record exists in a warehouse control file a plural.'
                                        || 'Warehouse=[' || IN_SOKOKBN || ']';

                        ELSE
                            IO_RC       := 40025;

                            IO_MSGDAT   := IN_SOKOKBN       || PA_AWC_DEFINE.MSGSECTION
                                        || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
                                        || SQLERRM;
                            WK_MSGMAKE  := TRUE;
                            WK_MSGBUFF  := 'An error occurred at the time of warehouse control file searching.'
                                        || 'Warehouse=['  || IN_SOKOKBN       || '] '
                                        || 'SQLCODE:['       || TO_CHAR(SQLCODE) || '] '
                                        || 'SQLERRM=['       || SQLERRM          || ']';
                        END IF;
                END;


                IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
                    GOTO ZONE_LOOP_ENDLABEL;
                END IF;

                /*==============================================================================================================
                ||  ?S?A?C?????��������A???[?v?J?E???g?��Z?b?g?B
                ==============================================================================================================*/
                AILE_TOTAL_CNT := ( WK_EAILE - WK_SAILE ) +1;

                /*==============================================================================================================
                ||  ?A?C?????��???
                ==============================================================================================================*/
                /*==============================================================================================================
                ||  ?g?[?^???A?C?????��??[?v
                ==============================================================================================================*/
                FOR LOOP_CNT IN 1..AILE_TOTAL_CNT LOOP
                    /*==============================================================================================================
                    ||  ?A?C??STNo?����������B
                    ||  ?��������F?������TAS75?��A?C?????��������H
                    ||          ?��TAS75?������������������o?������X?P?W???[???{?����g?p?����������B?������������o?����??��A?C????
                    ||            ?��������A?u?w?��X?e?[?V?????������??��\?��A?C???v?��??������K?v?��L?��ASTAS75?������??��TNo?��K
                    ||            ?{?����B?������AAGC?����n?��{STAS81?i?����I???��p?j?����A?��������??��TNo?��K?v?����??��������B
                    ||            ?������p?????[?^?s?������A?������A?C??STNo?����������������B
                    ==============================================================================================================*/
                    BEGIN
                        SELECT FNAILCTL.STNO
                            INTO    WK_AILESTNO
                            FROM    FNAILCTL
                            WHERE   FNAILCTL.SOKOKBN    =   IN_SOKOKBN
                              AND   FNAILCTL.AILEORDER  =   WK_AILEORDER;


                    EXCEPTION
                        WHEN OTHERS THEN

                            IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
                                IO_RC       := 40359;


                                IO_MSGDAT   := IN_SOKOKBN               || PA_AWC_DEFINE.MSGSECTION
                                            || TO_CHAR(WK_AILEORDER);
                                WK_MSGMAKE  := TRUE;
                                WK_MSGBUFF  := 'Aisle order no does not exist in an aisle control file.'
                                            || 'Warehouse=['         || IN_SOKOKBN               || '] '
                                            || 'Aisle order no=[' || TO_CHAR(WK_AILEORDER)    || ']';


                            ELSE
                                IO_RC       := 40361;


                                IO_MSGDAT   := IN_SOKOKBN       || PA_AWC_DEFINE.MSGSECTION
                                            || WK_AILEORDER     || PA_AWC_DEFINE.MSGSECTION
                                            || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
                                            || SQLERRM;
                                WK_MSGMAKE  := TRUE;
                                WK_MSGBUFF  := 'An error occurred at the time of aisle control file searching.'
                                            || 'Warehouse=['         || IN_SOKOKBN               || '] '
                                            || 'Aisle order no=['   || TO_CHAR(WK_AILEORDER)    || '] '
                                            || 'SQLCODE=['              || TO_CHAR(SQLCODE)         || '] '
                                            || 'SQLERRM=['              || SQLERRM                  || ']';
                            END IF;
                    END;


                    IF IO_RC != 0 THEN
                        GOTO AILE_LOOP_ENDLABEL;
                    END IF;


                    /*==========================================================================================================
                    ||  ???������A?C???����w?��]?[???����I?����??������������??A?G???[?������B
                    ==========================================================================================================*/
                    STAS85( IN_SOKOKBN, IO_HARDZONE, IO_SOFTZONE, NULL, WK_AILESTNO, IO_RC, IO_MSGDAT );
                    IF 0 != IO_RC THEN
                        GOTO AILE_LOOP_ENDLABEL;
                    END IF;


                    /*==========================================================================================================
                    ||  ?��K?������I???������{?����B
                    ==========================================================================================================*/
                    STAS80( IN_SOKOKBN, 0, IO_HARDZONE, IO_SOFTZONE, WK_AILESTNO, DATA_FNSOFTZONEYUSEN_GET.AKIKENKBN,
                            IO_SYOZAIKEY, IO_RC, IO_MSGDAT, IN_TANAKANFLG );

                    IF IO_RC != 0 THEN
                        GOTO AILE_LOOP_ENDLABEL;
                    END IF;


                <<AILE_LOOP_ENDLABEL>>
                    /*==========================================================================================================
                    ||  ?��O?G???[?��??A?��I?��i?����G???[?����??����??[?v?��j
                    ==========================================================================================================*/
                    IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
                        EXIT;

                    ELSE
                        IO_RC       := 0;
                        IO_MSGDAT   := NULL;
                        WK_MSGMAKE  := FALSE;

                    END IF;

                    /*==========================================================================================================
                    || ?A?C???I?[?_?[?m???C???N???????g
                    ==========================================================================================================*/
                    WK_AILEORDER := WK_AILEORDER +1;

                    IF WK_AILEORDER > AILE_TOTAL_CNT THEN
                        WK_AILEORDER := 1;
                    END IF;


                    IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL THEN
                        EXIT;
                    END IF;

                END LOOP;

            END IF;

        <<ZONE_LOOP_ENDLABEL>>
            /*==================================================================================================================
            ||  ?��O?G???[?��??A?��I?��i?����G???[?����??����??[?v?��j
            ==================================================================================================================*/
            IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
                GOTO ENDLABEL;
            END IF;


            IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL THEN
                EXIT;
            END IF;

        END LOOP;


        IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL THEN
            EXIT;
        END IF;

    END LOOP;

    CLOSE FNSOFTZONEYUSEN_GET;


    IF 0 != IO_RC THEN
        GOTO ENDLABEL;
    END IF;


<<ENDLABEL>>
    /*==========================================================================================================================
    ||  ?������A?G???[?����������������A???��L?[?��??������������??A?G???[???b?Z?[?W?��Z?b?g?����B
    ==========================================================================================================================*/
    IF 0 = IO_RC AND IO_SYOZAIKEY IS NULL THEN
        IO_RC       := 20169;


        IO_MSGDAT   := IN_SOKOKBN       || PA_AWC_DEFINE.MSGSECTION
                    || IN_AILESTNO      || PA_AWC_DEFINE.MSGSECTION
                    || IN_SOFTZONE      || PA_AWC_DEFINE.MSGSECTION
                    || IN_HARDZONE;
        WK_MSGMAKE  := TRUE;
        WK_MSGBUFF  := 'It failed to the decision of an Empty Location.'
                    || 'Warehouse=['         || IN_SOKOKBN   || '] '
                    || 'AILSTNO=['       || IN_AILESTNO  || '] '
                    || 'SOFTZONE=[' || IN_SOFTZONE  || '] '
                    || 'HARDZONE=[' || IN_HARDZONE  || ']';
    END IF;


    /*==========================================================================================================================
    ||  ?A?C??STNo?��w?��������?(?A?C??????)?A?����G???[?����������������??A?A?C???I?[?_?[?��X?V?����B
    ==========================================================================================================================*/
    IF (IN_AILESTNO IS NULL OR PA_AWC_DEFINE.STATION_MINYURYOKU = IN_AILESTNO) AND 0 = IO_RC THEN
        /*======================================================================================================================
        ||  ???��A?C???I?[?_?[No?��X?V?����B
        ======================================================================================================================*/
        BEGIN
            UPDATE FNSOKO SET
                    FNSOKO.AILEORDER    =   WK_AILEORDER
                WHERE   FNSOKO.ROWID    =   WK_FNSOKO_ROWID

                  AND   FNSOKO.AILEORDER    !=  WK_AILEORDER;


        EXCEPTION
            WHEN OTHERS THEN

                NULL;
        END;

    END IF;


    /*==========================================================================================================================
    ||  ?J?[?\???��N???[?Y???��������s?��B
    ==========================================================================================================================*/

    IF FNSOFTZONEYUSEN_GET%ISOPEN = TRUE THEN
        CLOSE FNSOFTZONEYUSEN_GET;
    END IF;


    /*==========================================================================================================================
    ||  ?G???[???b?Z?[?W?��������������??A?������������v???V?[?W???������W?����B
    ==========================================================================================================================*/
    IF IO_RC != 0 THEN

        IF WK_MSGMAKE = TRUE THEN

            IF IO_MSGDAT IS NULL THEN
                IO_MSGDAT := DEF_MYNAME;

            ELSE
                IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || IO_MSGDAT;
            END IF;

        /* ?������A?����\?[?X?����??b?Z?[?W?����W?����������i?����o?����T?u???[?`???��??b?Z?[?W?��������������j?������t?@?C??
           ?������������??��n?C?t???������B */
        ELSE

            IF IO_MSGDAT IS NULL THEN
                IO_MSGDAT := DEF_MYNAME;

            ELSE
                IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION2 || IO_MSGDAT;
            END IF;
        END IF;
    ELSE
        IO_MSGDAT := NULL;
    END IF;


    /*==========================================================================================================================
    ||  ?��??G???[???b?Z?[?W?��\?������B
    ==========================================================================================================================*/
    IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
    END IF;


/*==============================================================================================================================
|| ?��O?n???h??
==============================================================================================================================*/
EXCEPTION
    /*==========================================================================================================================
    ||  ???[?U?[?��`?��O?n???h????
    ==========================================================================================================================*/

    /*==========================================================================================================================
    ||  ?I???N???��`?��O?n???h????
    ==========================================================================================================================*/

    WHEN OTHERS THEN
        /*======================================================================================================================
        ||  ?��O?G???[?����??������???
        ======================================================================================================================*/

        IF FNSOFTZONEYUSEN_GET%ISOPEN = TRUE THEN
            CLOSE FNSOFTZONEYUSEN_GET;
        END IF;

        /*======================================================================================================================
        ||  ?��O?????��`
        ======================================================================================================================*/
        IO_RC       := 40001;

        IO_MSGDAT   := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
        WK_MSGBUFF  := DEF_MYNAMEJP || 'An error occurred with a procedure.'
                    || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                    || 'SQLERRM=['  || SQLERRM          || ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STAS81;
/
