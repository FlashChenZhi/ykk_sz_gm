DROP PROCEDURE STASA0;

CREATE OR REPLACE PROCEDURE STASA0
(
	IN_FROMSTNO		  IN	  VARCHAR2,
	IN_TOSTNO		  IN	  VARCHAR2,
	IO_RC			  IN OUT  NUMBER,
	IO_MSGDAT		  IN OUT  VARCHAR2
)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?��??��`??
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	?��??����	==========================================================================================================================*/

	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASA0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Route check module';



	WK_CNT				NUMBER(3);
	WK_MSGMAKE			BOOLEAN := FALSE;


	WK_MSGBUFF			VARCHAR2(32767);


/*==============================================================================================================================
|| ?����??��
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?����G???[?��������������??A?��I?��B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.IO_RC = [' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;

	IO_RC := 0;
	IO_MSGDAT := ' ';
	WK_MSGMAKE := FALSE;


	/*==========================================================================================================================
	||	?w?����??[?g?h?c?��??[?g?������m?f?����������������������`?F?b?N?����B
	==========================================================================================================================*/
	BEGIN










		SELECT COUNT(*)
			INTO WK_CNT
			FROM	FNRUTEST
			WHERE	FNRUTEST.RUTEID IN ( SELECT FNRUTE.RUTEID
										   FROM FNRUTE
										  WHERE FNRUTE.STNOFROM = IN_FROMSTNO
											AND FNRUTE.STNOTO	= IN_TOSTNO )
			  AND	FNRUTEST.STATUS IN ( PA_AWC_DEFINE.RUTE_STATUS_NG,
			  								PA_AWC_DEFINE.RUTE_STATUS_EMG )
			  AND	ROWNUM			= 1;



	EXCEPTION
		WHEN OTHERS THEN
			IO_RC := 40064;


			IO_MSGDAT := TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
			WK_MSGMAKE := TRUE;
			WK_MSGBUFF := 'An error occurred in a carrying route file.'
					   || 'SQLCODE:[' || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
	END;


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


	IF 0 != WK_CNT THEN
		IO_RC := FUKKI.RET_ROUTENG;


		IO_MSGDAT := IN_FROMSTNO || PA_AWC_DEFINE.MSGSECTION || IN_TOSTNO;
		WK_MSGMAKE := TRUE;
		WK_MSGBUFF := 'A route check error.Carrying former STNo=['|| IN_FROMSTNO || ']	Carrying tip STNo=[' || IN_TOSTNO ||']';

		GOTO ENDLABEL;

	END IF;


<<ENDLABEL>>
	/*==========================================================================================================================
	||	?G???[???b?Z?[?W?��������������??A?������������v???V?[?W???������W?����B
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
		IO_MSGDAT := ' ';
	END IF;


	/*==========================================================================================================================
	||	?��??G???[???b?Z?[?W?��\?������B
	==========================================================================================================================*/
	IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
	END IF;


/*==============================================================================================================================
|| ?��O?n???h??
==============================================================================================================================*/
EXCEPTION
	/*==========================================================================================================================
	||	???[?U?[?��`?��O?n???h????
	==========================================================================================================================*/

	/*==========================================================================================================================
	||	?I???N???��`?��O?n???h????
	==========================================================================================================================*/

	WHEN OTHERS THEN
		/*======================================================================================================================
		||	?��O?G???[?����??������???
		======================================================================================================================*/

		/*======================================================================================================================
		||	?��O?????��`
		======================================================================================================================*/
		IO_RC := 40001;

		IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF := DEF_MYNAMEJP || 'An error occurred with a procedure. SQLCODE:['
					  || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASA0;

/
