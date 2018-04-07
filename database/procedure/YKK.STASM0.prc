DROP PROCEDURE YKK.STASM0;

CREATE OR REPLACE PROCEDURE YKK.STASM0
(
	IO_SCHNO		IN OUT	VARCHAR2,
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
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASM0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Schedule No preparation';


	/*==========================================================================================================================
	||	?��??����
	==========================================================================================================================*/
	WK_SAIBANNO		FNSAIBAN.SAIBANNO%TYPE;
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


	/*==========================================================================================================================
	||	?w?������D?����������������B
	==========================================================================================================================*/
	STASH0( PA_AWC_DEFINE.FNSAI_ID_SCHNO, WK_SAIBANNO, IO_RC, IO_MSGDAT );


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?����������������??A?����m???����������B
	==========================================================================================================================*/
	IO_SCHNO := SUBSTR( TO_CHAR( WK_SAIBANNO, '0000000000'	), -10, 10 );


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

END STASM0;
/

