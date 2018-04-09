DROP PROCEDURE YKK.STASF0;

CREATE OR REPLACE PROCEDURE YKK.STASF0
(
	IN_NYUSYUKBN	IN		VARCHAR2,
	IO_HANSOKEY		IN OUT	VARCHAR2,
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?��??��`??
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	?��??����
	==========================================================================================================================*/

	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASF0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'HANSO Key SYUTOKU';



	DEF_MYSAIBANLEN CONSTANT NUMBER		(  1) := 7;




	/*==========================================================================================================================
	||	?��??����
	==========================================================================================================================*/
	WK_SAIBANID		FNSAIBAN.SAIBANID%TYPE;
	WK_SAIBANNO		FNSAIBAN.SAIBANNO%TYPE;



	WK_FORMAT		VARCHAR2	( 16);


	WK_MCKEY        FNHANSO.MCKEY%TYPE;

	WK_MSGMAKE		BOOLEAN := FALSE;


	WK_MSGBUFF		VARCHAR2(32767);


/*==============================================================================================================================
|| ?����??��
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?����G???[?��������������??A?��I?��B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred. IO_RC = [' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;

	IO_RC := 0;
	IO_MSGDAT := ' ';
	WK_MSGMAKE := FALSE;


	LOOP
	/*==========================================================================================================================
	||	?��?Key?p?��Y???����D?y?��������????t?����������B
	==========================================================================================================================*/

		IF IN_NYUSYUKBN = PA_AWC_DEFINE.NYUSYUKBN_SYU THEN
			WK_SAIBANID   := PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_SYU;










		ELSIF PA_AWC_DEFINE.NYUSYUKBN_TANA = IN_NYUSYUKBN THEN
			WK_SAIBANID   := PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_TANAKAN;








		ELSE
			WK_SAIBANID   := PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_NYU;






		END IF;




	/*==========================================================================================================================
	||	?��?Key?����??����W?����B
	==========================================================================================================================*/
		WK_FORMAT	:= SYSDATE08_GET();





	/*==========================================================================================================================
	||	?��������???
	==========================================================================================================================*/



		STASH0( WK_SAIBANID, WK_SAIBANNO, IO_RC, IO_MSGDAT, WK_FORMAT, DEF_MYSAIBANLEN );




		IF IO_RC != 0 THEN
			GOTO ENDLABEL;
		END IF;


	/*==========================================================================================================================
	||	?����������������??A?��?Key?����������B
	==========================================================================================================================*/













		IO_HANSOKEY := WK_FORMAT
					|| SUBSTR( TO_CHAR( WK_SAIBANNO, '000000000' ), -8, 8 );





		/*======================================================================================================================
		||  Location File KENSAKU
		======================================================================================================================*/
		BEGIN
		SELECT
			FNHANSO.MCKEY
		INTO
			WK_MCKEY
		FROM
			FNHANSO
		WHERE
			FNHANSO.HANSOKEY	=	IO_HANSOKEY ;

		EXCEPTION
			WHEN OTHERS THEN
				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC		:= FUKKI.RET_OK ;
					EXIT ;
				ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
					IO_RC		:= FUKKI.SQLDEADLOCK ;
					EXIT ;
				ELSE
					IO_RC		:= FUKKI.RET_DBERR ;
					EXIT ;
				END IF ;
		END ;
	END LOOP ;

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

END STASF0;
/


