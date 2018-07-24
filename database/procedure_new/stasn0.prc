DROP PROCEDURE AWC.STASN0;

CREATE OR REPLACE PROCEDURE AWC.STASN0
(
	IN_STNO			IN		VARCHAR2,
	IO_FNSTATION	IN OUT	FNSTATION%ROWTYPE,
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?£¿??£¿`??
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	?£¿??£¿£¿	==========================================================================================================================*/
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASN0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Station information acquisition processing';


	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/
	WK_MSGMAKE		BOOLEAN := FALSE;


	WK_MSGBUFF		VARCHAR2(32767);


/*==============================================================================================================================
|| ?£¿£¿??£¿
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?£¿£¿G???[?£¿£¿£¿£¿£¿£¿£¿??A?£¿I?£¿B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.'
				   || 'IO_RC=[' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?o?£¿p?????[?^?£¿£¿£¿??b?Z?[?W?£¿£¿t???O?£¿????£¿
	==========================================================================================================================*/
	IO_RC			:= 0;
	IO_MSGDAT		:= ' ';
	WK_MSGMAKE		:= FALSE;
	IO_FNSTATION	:= NULL;


	/*==========================================================================================================================
	||	?w?£¿£¿X?e?[?V?????£¿??£¿£¿£¿X?e?[?V?????£¿??£¿£¿i?[PL/SQL?\?£¿£¿£¿£¿£¿£¿B
	||		?£¿£¿£¿£¿FPA_SCH_GLOBAL.DATA_FNSTATION_TBL_NUM-1?£¿-1'?£¿£¿H
	||				?£¿L/SQL?\?£¿£¿Y?£¿?0?£¿£¿Z?b?g?£¿£¿£¿£¿£¿B?£¿£¿£¿A???_NUM?£¿£¿e?[?u???????£¿??£¿£¿£¿£¿£¿B?Y?£¿?0?£¿£¿n?£¿
	||				  ?£¿??A?e?[?u???????£¿OR ¥¥£¿LOOP?£¿s?£¿£¿£¿I?£¿£¿?+1?£¿£¿??[?v?£¿£¿£¿£¿£¿£¿£¿A?£¿£¿£¿£¿£¿£¿£¿A-1?£¿£¿£¿£¿
	||				  ?£¿B
	==========================================================================================================================*/












	BEGIN
		SELECT
			FNSTATION.NIDAKAKBN,
			FNSTATION.STLAYKBN,
			FNSTATION.IDOSTNO,
			FNSTATION.HANSOMAX,
			FNSTATION.SIJIMAX,
			FNSTATION.LINESETKBN,
			FNSTATION.STNO,
			FNSTATION.DAIHYOSTNO,
			FNSTATION.STNAME,
			FNSTATION.LAYKBN,
			FNSTATION.SOKOKBN,
			FNSTATION.AILENO,
			FNSTATION.UNYOUKBN,
			FNSTATION.CHUDANFLG,
			FNSTATION.ARCNO,
			FNSTATION.NYUSYUMODE,
			FNSTATION.MODEREQFLG,
			FNSTATION.MODECHGKBN,
			FNSTATION.HARAIDKBN,
			FNSTATION.ZKAKFLG,
			FNSTATION.TOUCYAKUKBN,
			FNSTATION.TERMKBN,
			FNSTATION.SETKBN,
			FNSTATION.STATION_TYPE

		INTO
			IO_FNSTATION.NIDAKAKBN,
			IO_FNSTATION.STLAYKBN,
			IO_FNSTATION.IDOSTNO,
			IO_FNSTATION.HANSOMAX,
			IO_FNSTATION.SIJIMAX,
			IO_FNSTATION.LINESETKBN,
			IO_FNSTATION.STNO,
			IO_FNSTATION.DAIHYOSTNO,
			IO_FNSTATION.STNAME,
			IO_FNSTATION.LAYKBN,
			IO_FNSTATION.SOKOKBN,
			IO_FNSTATION.AILENO,
			IO_FNSTATION.UNYOUKBN,
			IO_FNSTATION.CHUDANFLG,
			IO_FNSTATION.ARCNO,
			IO_FNSTATION.NYUSYUMODE,
			IO_FNSTATION.MODEREQFLG,
			IO_FNSTATION.MODECHGKBN,
			IO_FNSTATION.HARAIDKBN,
			IO_FNSTATION.ZKAKFLG,
			IO_FNSTATION.TOUCYAKUKBN,
			IO_FNSTATION.TERMKBN,
			IO_FNSTATION.SETKBN,
			IO_FNSTATION.STATION_TYPE

		FROM
			FNSTATION
		WHERE
			FNSTATION.STNO = IN_STNO;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:= 30001;
				WK_MSGBUFF	:= 'The deadlock occurred in Station management information .';
				IO_MSGDAT	:= WK_MSGBUFF;
				WK_MSGMAKE	:= TRUE;


			ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC	   := 48004;

				IO_MSGDAT  := IN_STNO;
				WK_MSGMAKE := TRUE;
				WK_MSGBUFF := 'A record does not exist in a Station management information file.'
						   || 'STNo=[' || IN_STNO || ']';


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC	   := 48005;

				IO_MSGDAT  := IN_STNO;
				WK_MSGMAKE := TRUE;
				WK_MSGBUFF := 'A record exists in a Station management information file a plural.'
						   || 'STNo=[' || IN_STNO || ']';


			ELSE
				IO_RC	   := 48006;

				IO_MSGDAT  := IN_STNO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
						   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE := TRUE;
				WK_MSGBUFF := 'An error occurred at the time of Station management information file searching.'
						   || 'STNo=['	|| IN_STNO	 || '] '
						   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
						   || 'SQLERRM=['		|| SQLERRM				|| ']';
			END IF;

	END ;
















<<ENDLABEL>>
	/*==========================================================================================================================
	||	?J?[?\???£¿N???[?Y???£¿£¿£¿£¿s?£¿B
	==========================================================================================================================*/
	NULL;


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
		IO_RC		:= 40001;

		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASN0;
/
