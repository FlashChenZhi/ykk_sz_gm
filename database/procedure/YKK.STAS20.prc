DROP PROCEDURE YKK.STAS20;

CREATE OR REPLACE PROCEDURE YKK.STAS20
(
	IN_SOKOKBN			IN		VARCHAR2,
	IN_START_HARDZONE	IN		VARCHAR2,
	IN_ACCESS_CNT		IN		NUMBER,
	IO_HARDZONE			IN OUT	VARCHAR2,
	IO_RC				IN OUT	NUMBER,
	IO_MSGDAT			IN OUT	VARCHAR2
)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?£¿??£¿`??
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/

	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STAS20';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Hard zone determination processing';


	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/
	WK_MSGMAKE		BOOLEAN := FALSE;


	WK_MSGBUFF		VARCHAR2(32767);
	WK_HARDZONESEQ	FNHARDZONE.HARDZONESEQ%TYPE;


/*==============================================================================================================================
|| ?£¿£¿??£¿
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?£¿£¿G???[?£¿£¿£¿£¿£¿£¿£¿??A?£¿I?£¿B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.';
		GOTO ENDLABEL;
	END IF;

	IO_RC := 0;
	IO_MSGDAT := NULL;
	WK_MSGMAKE := FALSE;


	/*==========================================================================================================================
	||	?w?£¿q?£¿£¿£¿y?£¿w?£¿n?[?h?]?[???£¿£¿Y???£¿n?[?h?]?[???£¿??t?@?C?????R?[?h?£¿n?[?h?]?[?????£¿??£¿£¿£¿
	==========================================================================================================================*/
	BEGIN
		SELECT FNHARDZONE.HARDZONESEQ
			INTO WK_HARDZONESEQ
			FROM	FNHARDZONE
			WHERE	FNHARDZONE.SOKOKBN	=	IN_SOKOKBN
			  AND	FNHARDZONE.HARDZONE =	IN_START_HARDZONE;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:= 40379;


				IO_MSGDAT	:= IN_SOKOKBN			|| PA_AWC_DEFINE.MSGSECTION
							|| IN_START_HARDZONE;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A record does not exist in a zone control file.'
							|| 'Warehouse=['	 || IN_SOKOKBN		   || '] '
							|| 'Zoneno=[' || IN_START_HARDZONE	|| ']';


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:= 40380;


				IO_MSGDAT	:= IN_SOKOKBN			|| PA_AWC_DEFINE.MSGSECTION
							|| IN_START_HARDZONE;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A record exists in a zone control file a plural.'
							|| 'Warehouse=['	 || IN_SOKOKBN			 || '] '
							|| 'Zoneno=[' || IN_START_HARDZONE	  || ']';


			ELSE
				IO_RC		:= 40381;


				IO_MSGDAT	:= IN_SOKOKBN			|| PA_AWC_DEFINE.MSGSECTION
							|| IN_START_HARDZONE	|| PA_AWC_DEFINE.MSGSECTION
							|| TO_CHAR(SQLCODE)		|| PA_AWC_DEFINE.MSGSECTION
							|| SQLERRM;
				WK_MSGMAKE := TRUE;
				WK_MSGBUFF := 'An error occurred at the time of zone control file searching.'
						   || 'Warehouse=['		 || IN_SOKOKBN			 || '] '
						   || 'Zoneno=['  || IN_START_HARDZONE	  || '] '
						   || 'SQLCODE:['			|| TO_CHAR(SQLCODE)		|| '] '
						   || 'SQLERRM=['			|| SQLERRM				|| ']';
			END IF;
	END;

	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?n?[?h?]?[?????£¿??£¿£¿n?[?h?]?[???£¿m?£¿£¿£¿£¿
	==========================================================================================================================*/
	BEGIN
		IO_HARDZONE := SUBSTR( WK_HARDZONESEQ, IN_ACCESS_CNT, 1 );

	EXCEPTION
		WHEN OTHERS THEN
			IF PA_AWC_DEFINE.NUMERIC_OR_VALUE_NG = SQLCODE THEN
				WK_MSGBUFF := 'It is error generating at the time of substitution of a variable.';
			END IF;
	END;


<<ENDLABEL>>
	/*==========================================================================================================================
	||	?G???[???b?Z?[?W?£¿£¿£¿£¿£¿£¿£¿??A?£¿£¿£¿£¿£¿£¿v???V?[?W???£¿£¿£¿W?£¿£¿B
	==========================================================================================================================*/
	IF IO_RC != 0 THEN

		IF WK_MSGMAKE = TRUE THEN

			IF IO_MSGDAT IS NULL THEN
				IO_MSGDAT := DEF_MYNAME;

			ELSE
				IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || IO_MSGDAT;
			END IF;

		/* ?£¿£¿£¿A?£¿£¿\?[?X?£¿£¿??b?Z?[?W?£¿£¿W?£¿£¿£¿£¿£¿i?£¿£¿o?£¿£¿T?u???[?`???£¿??b?Z?[?W?£¿£¿£¿£¿£¿£¿£¿j?£¿£¿£¿t?@?C??
		   ?£¿£¿£¿£¿£¿£¿??£¿n?C?t???£¿£¿£¿B */
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
	||	?£¿??G???[???b?Z?[?W?£¿\?£¿£¿£¿B
	==========================================================================================================================*/
	IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
	END IF;


/*==============================================================================================================================
|| ?£¿O?n???h??
==============================================================================================================================*/
EXCEPTION
	/*==========================================================================================================================
	||	???[?U?[?£¿`?£¿O?n???h????
	==========================================================================================================================*/

	/*==========================================================================================================================
	||	?I???N???£¿`?£¿O?n???h????
	==========================================================================================================================*/

	WHEN OTHERS THEN
		/*======================================================================================================================
		||	?£¿O?G???[?£¿£¿??£¿£¿£¿???
		======================================================================================================================*/

		/*======================================================================================================================
		||	?£¿O?????£¿`
		======================================================================================================================*/
		IO_RC := 40001;

		IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF := DEF_MYNAMEJP || 'An error occurred with a procedure. SQLCODE:['
					  || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STAS20;
/


