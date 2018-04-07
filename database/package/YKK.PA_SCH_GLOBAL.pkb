DROP PACKAGE BODY YKK.PA_SCH_GLOBAL;

CREATE OR REPLACE PACKAGE BODY YKK.PA_SCH_GLOBAL
IS




    CURSOR FNSOKO_GET IS
        SELECT FNSOKO.* FROM FNSOKO;
    DATA_FNSOKO_GET FNSOKO_GET%ROWTYPE;


    CURSOR FNSTATION_GET IS
        SELECT FNSTATION.* FROM FNSTATION;
    DATA_FNSTATION_GET FNSTATION_GET%ROWTYPE;





    WK_I            NUMBER      (  3);
    WK_TABLE_NAME   VARCHAR2    ( 10);
    WK_COLUMN_NAME  VARCHAR2    ( 20);





BEGIN



















































































    WK_TABLE_NAME   :=  'FNLOCAT';
    WK_COLUMN_NAME  :=  'JYURITSU';
    BEGIN
        SELECT DECODE( USER_TAB_COLUMNS.DATA_TYPE,  'NUMBER',   USER_TAB_COLUMNS.DATA_PRECISION,
                                                                USER_TAB_COLUMNS.DATA_LENGTH )
            INTO JYURITSU_LEN
            FROM    USER_TAB_COLUMNS
            WHERE   USER_TAB_COLUMNS.TABLE_NAME     =   WK_TABLE_NAME
              AND   USER_TAB_COLUMNS.COLUMN_NAME    =   WK_COLUMN_NAME;


    EXCEPTION
        WHEN OTHERS THEN

            IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');


            ELSE
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            END IF;
        GOTO ENDLABEL;
    END;





    WK_COLUMN_NAME  :=  'KONSAISU';
    BEGIN
        SELECT DECODE( USER_TAB_COLUMNS.DATA_TYPE,  'NUMBER',   USER_TAB_COLUMNS.DATA_PRECISION,
                                                                USER_TAB_COLUMNS.DATA_LENGTH )
            INTO KONSAISU_LEN
            FROM    USER_TAB_COLUMNS
            WHERE   USER_TAB_COLUMNS.TABLE_NAME     =   WK_TABLE_NAME
              AND   USER_TAB_COLUMNS.COLUMN_NAME    =   WK_COLUMN_NAME;


    EXCEPTION
        WHEN OTHERS THEN

            IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');


            ELSE
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            END IF;
        GOTO ENDLABEL;
    END;





    WK_TABLE_NAME   :=  'FNHARDZONE';
    WK_COLUMN_NAME  :=  'HARDZONESEQ';
    BEGIN
        SELECT DECODE( USER_TAB_COLUMNS.DATA_TYPE,  'NUMBER',   USER_TAB_COLUMNS.DATA_PRECISION,
                                                                USER_TAB_COLUMNS.DATA_LENGTH )
            INTO HARDZONESEQ_LEN
            FROM    USER_TAB_COLUMNS
            WHERE   USER_TAB_COLUMNS.TABLE_NAME     =   WK_TABLE_NAME
              AND   USER_TAB_COLUMNS.COLUMN_NAME    =   WK_COLUMN_NAME;


    EXCEPTION
        WHEN OTHERS THEN

            IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');


            ELSE
                DBMS_OUTPUT.PUT_LINE( 'USER_TAB_COLUMNS ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            END IF;
        GOTO ENDLABEL;
    END;












    BEGIN
        SELECT COUNT(FNSOKO.SOKOKBN) INTO DATA_FNSOKO_TBL_NUM
            FROM    FNSOKO;


    EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE( 'ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            GOTO FNSOKO_TBL_GET_END;
    END;


    IF DATA_FNSOKO_TBL_NUM = 0 THEN
        GOTO FNSOKO_TBL_GET_END;
    END IF;


    OPEN FNSOKO_GET;
    WK_I := 0;
    LOOP



        FETCH FNSOKO_GET INTO DATA_FNSOKO_GET;

        IF FNSOKO_GET%NOTFOUND = TRUE THEN
            EXIT;
        END IF;


        DATA_FNSOKO_TBL(WK_I) := DATA_FNSOKO_GET;
        WK_I := (WK_I + 1);

    END LOOP;
    CLOSE FNSOKO_GET;

<<FNSOKO_TBL_GET_END>>






    BEGIN
        SELECT COUNT(FNSTATION.STNO) INTO DATA_FNSTATION_TBL_NUM
            FROM    FNSTATION;


    EXCEPTION
        WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE( 'ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            GOTO FNSTATION_TBL_GET_END;
    END;


    IF DATA_FNSTATION_TBL_NUM = 0 THEN
        GOTO FNSTATION_TBL_GET_END;
    END IF;


    OPEN FNSTATION_GET;
    WK_I := 0;
    LOOP



        FETCH FNSTATION_GET INTO DATA_FNSTATION_GET;

        IF FNSTATION_GET%NOTFOUND = TRUE THEN
            EXIT;
        END IF;


        DATA_FNSTATION_TBL(WK_I) := DATA_FNSTATION_GET;
        WK_I := (WK_I + 1);

    END LOOP;
    CLOSE FNSTATION_GET;

<<FNSTATION_TBL_GET_END>>





    BEGIN
        SELECT FNSYSTEM.* INTO FNSYSTEM_TBL
            FROM    FNSYSTEM;


    EXCEPTION
        WHEN OTHERS THEN

            IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL	;


            ELSE
                DBMS_OUTPUT.PUT_LINE( 'ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');
            END IF;

            GOTO FNSYSTEM_TBL_GET_END;
    END;

<<FNSYSTEM_TBL_GET_END>>





    SELECT COUNT(*)
        INTO    WK_I
        FROM    FNSTATION
        WHERE   FNSTATION.NIDAKAKBN =   PA_AWC_DEFINE.NIDAKAKBN_ARI
          AND   ROWNUM              =   1;


    IF 0 != WK_I THEN
        SYS_NIDAKAKBN := TRUE;


    ELSE
        SYS_NIDAKAKBN := FALSE;

    END IF;






















<<ENDLABEL>>

    IF FNSOKO_GET%ISOPEN = TRUE THEN
        CLOSE FNSOKO_GET;
    END IF;


    IF FNSTATION_GET%ISOPEN = TRUE THEN
        CLOSE FNSTATION_GET;
    END IF;





EXCEPTION
    WHEN OTHERS THEN

        IF FNSOKO_GET%ISOPEN = TRUE THEN
            CLOSE FNSOKO_GET;
        END IF;

        IF FNSTATION_GET%ISOPEN = TRUE THEN
            CLOSE FNSTATION_GET;
        END IF;

                DBMS_OUTPUT.PUT_LINE( 'ERROR'
                                     || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                                     || 'SQLERRM=['  || SQLERRM          || ']');

END PA_SCH_GLOBAL;
/


