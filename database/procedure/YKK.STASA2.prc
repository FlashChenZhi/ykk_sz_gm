DROP PROCEDURE YKK.STASA2;

CREATE OR REPLACE PROCEDURE YKK.STASA2
(
    IN_FROMSTNO     IN      VARCHAR2,
    IN_TOSTNO       IN      VARCHAR2,
    IO_RC           IN OUT  NUMBER,
    IO_MSGNO        IN OUT  NUMBER,
    IO_MSGDAT       IN OUT  VARCHAR2
)
/*==============================================================================================================================
//## Route check
||
||  Name       	:STASA2
||
||  Date		:2005/10/26
||
||  Argument    :IN     IN_FROMSTNO     : HANSO-MOTO STNo
||                      IN_TOSTNO       : HANSO-SAKI STNo
||
||            OUT
||
||            IN OUT    IO_MSGDAT       : Error message parameter data
||                      IO_RC           : Error message No housing area
||
==============================================================================================================================*/
/*==============================================================================================================================
|| PL/SQL block
==============================================================================================================================*/
/*==============================================================================================================================
|| Variable definition department
==============================================================================================================================*/
IS
    /*==========================================================================================================================
    ||  Fixed number declaration
    ==========================================================================================================================*/
    DEF_MYNAME      CONSTANT VARCHAR2   ( 10) := 'STASA2';
    DEF_MYNAMEJP    CONSTANT VARCHAR2   ( 60) := 'Route check';

    /*==========================================================================================================================
    ||  Variable declaration
    ==========================================================================================================================*/
    WK_MSGMAKE      BOOLEAN := FALSE;
    WK_MSGBUFF      VARCHAR2(255);

	D_FNSTATION     FNSTATION%ROWTYPE ;
	WK_CHKSTNO 	    FNSTATION.STNO%TYPE ;


    /*==========================================================================================================================
    ||  Cursor declaration
    ==========================================================================================================================*/
    CURSOR  FNSTATION_GET   IS
        SELECT FNSTATION.STNO
            FROM    FNSTATION
            WHERE   FNSTATION.DAIHYOSTNO    =   WK_CHKSTNO	 ;


/*==============================================================================================================================
|| Control part
==============================================================================================================================*/
BEGIN
    /*==========================================================================================================================
    ||  Initialization
    ==========================================================================================================================*/
    IO_RC       := 0;
    IO_MSGNO    := 0;
    IO_MSGDAT   := ' ';
    Wk_MSGBUFF  := ' ';
    WK_MSGMAKE  := FALSE;


    /*==========================================================================================================================
    ||  FROM ni BUTURI Station ga TEIGI SARETE IRUMOTOSITE Route Check
    ==========================================================================================================================*/



    STASA0( IN_FROMSTNO, IN_TOSTNO, IO_RC, IO_MSGDAT );
    IF FUKKI.RET_ROUTENG != IO_RC THEN
        GOTO ENDLABEL;
    END IF;





        GOTO ENDLABEL;


    IO_RC       := 0;
    IO_MSGDAT   := ' ';
    WK_MSGMAKE  := FALSE;

	WK_CHKSTNO	:= IN_FROMSTNO ;

    OPEN FNSTATION_GET;
    LOOP
        FETCH FNSTATION_GET INTO D_FNSTATION.STNO;
        IF FNSTATION_GET%NOTFOUND = TRUE THEN
            EXIT;
        END IF;



        /*======================================================================================================================
        ||  SYUTOKU SITA BUTURI Station de Route Check wo SURU
        ======================================================================================================================*/
        STASA0( D_FNSTATION.STNO, IN_TOSTNO, IO_RC, IO_MSGDAT );

        IF 0 = IO_RC THEN
            IO_RC       := 0;
            IO_MSGDAT   := ' ';
            WK_MSGMAKE  := FALSE;
            GOTO	ENDLABEL ;
        END IF;

    END LOOP;

    CLOSE FNSTATION_GET;

	WK_CHKSTNO	:= IN_TOSTNO ;

    OPEN FNSTATION_GET;
    LOOP
        FETCH FNSTATION_GET INTO D_FNSTATION.STNO;
        IF FNSTATION_GET%NOTFOUND = TRUE THEN
            IF FNSTATION_GET%ROWCOUNT = 0 THEN
                IO_RC       := FUKKI.RET_NORUTE;
                IO_MSGDAT   := IN_FROMSTNO || PA_AWC_DEFINE.MSGSECTION || IN_TOSTNO;
                WK_MSGMAKE  := TRUE;
                WK_MSGBUFF  := 'Station No. does not exist in a carrying route file.'
                            || 'FromSTNo=[' || IN_FROMSTNO  || '] '
                            || 'ToSTNo=['   || IN_TOSTNO    || ']';
            END IF;
            EXIT;
        END IF;



        /*======================================================================================================================
        ||  SYUTOKU SITA BUTURI Station de Route Check wo SURU
        ======================================================================================================================*/
        STASA0( IN_FROMSTNO, D_FNSTATION.STNO, IO_RC, IO_MSGDAT );

        IF 0 = IO_RC THEN
            IO_RC       := 0;
            IO_MSGDAT   := ' ';
            WK_MSGMAKE  := FALSE;
            GOTO	ENDLABEL ;
        END IF;

    END LOOP;

    CLOSE FNSTATION_GET;

    IF IO_RC != 0 THEN
        GOTO ENDLABEL;
    END IF;


<<ENDLABEL>>
    IF FNSTATION_GET%ISOPEN = TRUE THEN
        CLOSE FNSTATION_GET;
    END IF;


    IF IO_RC != 0 THEN
		IO_MSGNO := IO_RC	;
        IF WK_MSGMAKE = TRUE THEN
            IF IO_MSGDAT IS NULL THEN
                IO_MSGDAT := DEF_MYNAME;
            ELSE
                IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || IO_MSGDAT;
            END IF;

        ELSE
            IF IO_MSGDAT IS NULL THEN
                IO_MSGDAT := DEF_MYNAME;
            ELSE
                IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION2 || IO_MSGDAT;
            END IF;
        END IF;
    ELSE
		IO_MSGNO := 0	;
		IF IO_MSGDAT IS NULL THEN
			IO_MSGDAT := ' ';
		END IF;
    END IF;


    IF WK_MSGBUFF IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
    END IF;


EXCEPTION
    WHEN OTHERS THEN
        IF FNSTATION_GET%ISOPEN = TRUE THEN
            CLOSE FNSTATION_GET;
        END IF;

        IO_RC       := FUKKI.RET_PROERR;
		IO_MSGNO	:= IO_RC	;
        IO_MSGDAT   := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
        WK_MSGBUFF  := DEF_MYNAMEJP || 'An error occurred with a procedure.'
                    || 'SQLCODE=['  || TO_CHAR(SQLCODE) || '] '
                    || 'SQLERRM=['  || SQLERRM          || ']';
        DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASA2;
/


