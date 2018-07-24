DROP PROCEDURE STASS0;

CREATE OR REPLACE PROCEDURE STASS0
(
	IN_NYUSYUKBN	IN		VARCHAR2,
	IO_MCKEY		IN OUT	VARCHAR2,
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
	||	?��??����	==========================================================================================================================*/
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASS0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'MCKEY SYUTOKU';



	DEF_MYSAIBANLEN CONSTANT NUMBER		(  1) := 4;




	/*==========================================================================================================================
	||	?��??����
	==========================================================================================================================*/
	WK_SAIBANID		FNSAIBAN.SAIBANID%TYPE;
	WK_SAIBANNO		FNSAIBAN.SAIBANNO%TYPE;
	WK_MCKEYKBN		CHAR	(  1);
	WK_MCKEYMONTH	CHAR	(  1);
	WK_SYSDATE		CHAR	(  8);



	WK_FORMAT		VARCHAR2	(  8);



	WK_MSGMAKE		BOOLEAN := FALSE;


	WK_MSGBUFF		VARCHAR2(32767);
	WK_HANSOKEY		FNHANSO.HANSOKEY%TYPE;


/*==============================================================================================================================
|| ?����??��
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?����G???[?��������������??A?��I?��B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.'
				   || 'IO_RC=[' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?o?��p?????[?^?������??b?Z?[?W?����t???O?��????��
	==========================================================================================================================*/
	IO_RC		:= 0;
	IO_MSGDAT	:= ' ';
	WK_MSGMAKE	:= FALSE;

	LOOP
	/*==========================================================================================================================
	||	MCKEY?p?��Y???����D?y?��������????t?����������B
	==========================================================================================================================*/

		IF IN_NYUSYUKBN = PA_AWC_DEFINE.NYUSYUKBN_SYU THEN
			WK_SAIBANID		:= PA_AWC_DEFINE.FNSAI_ID_MCKEY_SYU;





			WK_MCKEYKBN		:= '4';


		ELSE
			WK_SAIBANID		:= PA_AWC_DEFINE.FNSAI_ID_MCKEY_NYU;





			WK_MCKEYKBN		:= '3';

		END IF;


	/*==========================================================================================================================
	||	?��������???
	==========================================================================================================================*/



		STASH0( WK_SAIBANID, WK_SAIBANNO, IO_RC, IO_MSGDAT, WK_FORMAT, DEF_MYSAIBANLEN );




		IF IO_RC != 0 THEN
			GOTO ENDLABEL;
		END IF;








































	/*==========================================================================================================================
	||	?��������e?����������CKEY?����������B
	==========================================================================================================================*/























		IO_MCKEY	:= SUBSTRB( TO_CHAR( WK_SAIBANNO, '00000000' ), -8, 8 );

		/*======================================================================================================================
		||	HANSO File KENSAKU
		======================================================================================================================*/
		BEGIN
			SELECT
				FNHANSO.HANSOKEY
			INTO
				WK_HANSOKEY
			FROM
				FNHANSO
			WHERE
				FNHANSO.MCKEY		=	IO_MCKEY ;

		EXCEPTION
			WHEN OTHERS THEN
				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC		:= FUKKI.RET_OK ;
				ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
					IO_RC		:= FUKKI.SQLDEADLOCK ;
				ELSE
					IO_RC		:= FUKKI.RET_DBERR ;
				END IF ;
				EXIT ;
		END ;
	END LOOP ;

<<ENDLABEL>>
	/*==========================================================================================================================
	||	?J?[?\???��N???[?Y???��������s?��B
	==========================================================================================================================*/
	NULL;


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
		IO_RC		:= 40001;

		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASS0;

/
