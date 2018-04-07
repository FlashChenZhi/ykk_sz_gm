DROP PROCEDURE STAS75;

CREATE OR REPLACE PROCEDURE STAS75
(
	IN_SOKOKBN		IN		VARCHAR2,
	IN_STNOFROM		IN		VARCHAR2,
	IN_AILETYPE		IN		VARCHAR2,
	IN_AILEORDER	IN		NUMBER,
	IN_NIDAKAUMU	IN		VARCHAR2,
	IO_STNOTO		IN OUT	VARCHAR2,
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

	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STAS75';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Aisle determination processing';

	/*==========================================================================================================================
	||	?£¿??£¿£¿	==========================================================================================================================*/
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
		WK_MSGBUFF := 'The error has already occurred. IO_RC = [' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?????£¿£¿???
	==========================================================================================================================*/
	IO_RC		:= 0;
	IO_MSGDAT	:= ' ';
	WK_MSGMAKE	:= FALSE;


	/*==========================================================================================================================
	||	?A?C???£¿£¿£¿£¿?
	==========================================================================================================================*/
	IF PA_AWC_DEFINE.AISLETYPE_INDEPENDENCE = IN_AILETYPE THEN
		/*======================================================================================================================
		||	?£¿??£¿X?e?[?V?????£¿£¿
		======================================================================================================================*/
		BEGIN
			SELECT FNAILCTL.STNO
				INTO IO_STNOTO
				FROM	FNAILCTL
				WHERE	FNAILCTL.STNO
					IN	( SELECT FNRUTE.STNOTO
							FROM	FNRUTE
							WHERE	FNRUTE.STNOFROM

								IN	(	SELECT IN_STNOFROM
											FROM	DUAL
										UNION
										SELECT FNSTATION.STNO
											FROM	FNSTATION
											WHERE	FNSTATION.IDOSTNO	=	IN_STNOFROM
									)
						);


		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC		:= 40013;


					IO_MSGDAT	:= IN_STNOFROM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'Station No. does not exist in a carrying route file.'
								|| 'Station No=['	|| IN_STNOFROM	|| ']';


				ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
					IO_RC		:= 40014;


					IO_MSGDAT	:= IN_STNOFROM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'There is a plural definition to carrying former Station.'
								|| 'Station No=['	|| IN_STNOFROM	|| ']';


				ELSE
					IO_RC		:= 40015;

					IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
								|| SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred at the time of carrying route file searching.'
								|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
								|| 'SQLERRM=['	|| SQLERRM			|| ']';

				END IF;
		END;


		IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
			GOTO ENDLABEL;
		END IF;


	/*==========================================================================================================================
	||	?A?C?????????£¿??L?£¿£¿£¿?
	==========================================================================================================================*/
	ELSIF PA_AWC_DEFINE.AISLETYPE_LINK = IN_AILETYPE AND PA_AWC_DEFINE.ARI = IN_NIDAKAUMU THEN
		/*======================================================================================================================
		||	?w?£¿??£¿£¿TNo?£¿£¿£¿??£¿\?£¿£¿£¿£¿A?C??STNo?£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		BEGIN
			SELECT FNAILCTL.STNO
				INTO IO_STNOTO
				FROM	FNAILCTL
				WHERE	FNAILCTL.STNO
					IN	( SELECT FNRUTE.STNOTO
							FROM	FNRUTE
							WHERE	FNRUTE.STNOFROM IN

									( SELECT IN_STNOFROM
										FROM	DUAL
									  UNION
									  SELECT FNSTATION.STNO
										FROM	FNSTATION
										WHERE	FNSTATION.IDOSTNO	=	IN_STNOFROM
									)








								AND   FNRUTE.RUTEID IN
									(

									SELECT FNRUTE.RUTEID
										FROM	FNRUTE

									MINUS
									SELECT DISTINCT FNRUTEST.RUTEID



										FROM	FNRUTEST
										WHERE  FNRUTEST.STATUS !=	PA_AWC_DEFINE.RUTE_STATUS_OK

									)


						)
				  AND	ROWNUM	=	1;

		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC		:= 20113;

					IO_MSGDAT	:= IN_STNOFROM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'Station No. does not exist in a carrying route file.'
								|| 'Station No=[' || IN_STNOFROM	|| ']';


				ELSE
					IO_RC		:= 40015;

					IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
								|| SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred at the time of carrying route file searching.'
								|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
								|| 'SQLERRM=['	|| SQLERRM			|| ']';

				END IF;
		END;


		IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
			GOTO ENDLABEL;
		END IF;


	/*==========================================================================================================================
	||	?A?C?????????£¿??£¿£¿£¿£¿?
	==========================================================================================================================*/
	ELSIF PA_AWC_DEFINE.AISLETYPE_LINK = IN_AILETYPE AND PA_AWC_DEFINE.NASI = IN_NIDAKAUMU THEN
		/*======================================================================================================================
		||	?A?C???I?[?_?[?m???£¿??v?£¿£¿A?C??STNo?£¿£¿£¿
		======================================================================================================================*/
		BEGIN
			SELECT FNAILCTL.STNO
				INTO IO_STNOTO
				FROM	FNAILCTL
				WHERE	FNAILCTL.SOKOKBN	=	IN_SOKOKBN
				  AND	FNAILCTL.AILEORDER	=	IN_AILEORDER;

		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC		:= 40359;


					IO_MSGDAT	:= IN_SOKOKBN				|| PA_AWC_DEFINE.MSGSECTION
								|| TO_CHAR(IN_AILEORDER);
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'Aisle order no does not exist in an aisle control file.'
								|| 'Warehouse=['			|| IN_SOKOKBN				|| '] '
								|| 'Aisle order no=[' || TO_CHAR(IN_AILEORDER)	|| ']';


				ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
					IO_RC		:= 40360;


					IO_MSGDAT	:= IN_SOKOKBN				|| PA_AWC_DEFINE.MSGSECTION
								|| TO_CHAR(IN_AILEORDER)	|| PA_AWC_DEFINE.MSGSECTION
								|| IO_STNOTO;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'There is the definition of plural Aisle order no in an aisle control file.'
								|| 'Warehouse=['				|| IN_SOKOKBN				|| '] '
								|| 'Aisle order no=['	|| TO_CHAR(IN_AILEORDER)	|| '] '
								|| 'Stno=['					|| IO_STNOTO				|| ']';


				ELSE
					IO_RC		:= 40361;


					IO_MSGDAT	:= IN_SOKOKBN				|| PA_AWC_DEFINE.MSGSECTION
								|| TO_CHAR(IN_AILEORDER)	|| PA_AWC_DEFINE.MSGSECTION
								|| TO_CHAR(SQLCODE)			|| PA_AWC_DEFINE.MSGSECTION
								|| SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred at the time of aisle control file searching. '
								|| 'Warehouse=['			|| IN_SOKOKBN				|| '] '
								|| 'Aisle order no=['	|| TO_CHAR(IN_AILEORDER)	|| '] '
								|| 'SQLCODE=['				|| TO_CHAR(SQLCODE)			|| '] '
								|| 'SQLERRM=['				|| SQLERRM					|| ']';

				END IF;
		END;


		IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
			GOTO ENDLABEL;
		END IF;


	/*==========================================================================================================================
	||	?F?£¿£¿£¿£¿£¿A?C???£¿£¿£¿?
	==========================================================================================================================*/
	ELSE
		IO_RC		:= 40364;

		IO_MSGDAT	:= IN_AILETYPE	|| PA_AWC_DEFINE.MSGSECTION
					|| IN_NIDAKAUMU;
		WK_MSGMAKE	:= TRUE;
		WK_MSGBUFF	:= 'Aisle control Type without existing.'
					|| 'Aisle Type=['	|| IN_AILETYPE	|| '] '
					|| 'NIDAKAUMU='		|| IN_NIDAKAUMU || ']';
		GOTO ENDLABEL;

	END IF;


<<ENDLABEL>>
	/*==========================================================================================================================
	||	?J?[?\???N???[?Y
	==========================================================================================================================*/

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

		IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || DEF_MYNAMEJP || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
				  || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF := DEF_MYNAMEJP || 'An error occurred with a procedure. SQLCODE:['
					  || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STAS75;

/
