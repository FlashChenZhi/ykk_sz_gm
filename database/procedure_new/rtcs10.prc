DROP PROCEDURE AWC.RTCS10;

CREATE OR REPLACE PROCEDURE AWC.RTCS10
		(
			IN_MCKEY		IN	FNHANSO.MCKEY%TYPE,
			IO_PICFLG		IN OUT	VARCHAR2,
			IO_RC			IN OUT	NUMBER,
			IO_MSGNO		IN OUT	NUMBER,
			IO_MSGDAT		IN OUT	VARCHAR2
		) IS













































DEF_MYNAME			CONSTANT VARCHAR2(10)	:= 'RTCS10' ;
PICFLG_SEI			CONSTANT VARCHAR2(1)	:= '0' ;
PICFLG_GYAKU		CONSTANT VARCHAR2(1)	:= '1' ;
PICFLG_ZEN			CONSTANT VARCHAR2(1)	:= '2' ;
PICFLG_NYUKO		CONSTANT VARCHAR2(1)	:= '3' ;
PICFLG_REJECT		CONSTANT VARCHAR2(1)	:= '4' ;
PICFLG_ZAIKAK		CONSTANT VARCHAR2(1)	:= '5' ;
PICFLG_KOWAKE		CONSTANT VARCHAR2(1)	:= '6' ;




WK_MSGBUFF		VARCHAR2(255) ;
WK_LOGMSG		VARCHAR2(512) ;
WK_MSGNO		NUMBER(9,0);


D_FNZAIKO		FNZAIKO%ROWTYPE ;
D_FNHANSO		FNHANSO%ROWTYPE ;
D_FNSIJI		FNSIJI%ROWTYPE ;
D_FNSYSTEM		FNSYSTEM%ROWTYPE ;
D_FNPICK_CTL	FNPICK_CTL%ROWTYPE ;




WK_SYSDATE		VARCHAR2(8) ;
WK_SYSNICHIJI	VARCHAR2(14) ;
WK_RC			NUMBER(9,0) ;
WK_COUNT		NUMBER(9,0) ;
WK_SUMNSSU		NUMBER(9,0) ;
WK_REVPICSU		NUMBER(9,0) ;

WK_SYOKAI_KBN	VARCHAR2(1) ;

WK_ERRFLG		NUMBER(9,0) ;


WK_ERRNO		NUMBER(9,0) ;
WK_ERRKBN		VARCHAR2(2) ;
WK_ERRDATA		VARCHAR2(256) ;
WK_ERRINF		VARCHAR2(256) ;





CURSOR
	FNSIJI01_CUR
	IS
	SELECT
		FNSIJI.SECTION,
		FNSIJI.RETRIEVAL_STATION,
		SUM(FNSIJI.NYUSYUSU) AS PICKSU
	FROM
		FNSIJI
	WHERE
		FNSIJI.HANSOKEY	=	D_FNHANSO.HANSOKEY
	GROUP BY
		FNSIJI.RETRIEVAL_STATION, FNSIJI.SECTION
	ORDER BY
		PICKSU ASC ;


























PROCEDURE RTCS10_60
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN

	WK_COUNT := 0	;





	BEGIN
		SELECT
			COUNT(*)
		INTO
			WK_COUNT
		FROM
			FNHANSO, FNSIJI
		WHERE
			FNHANSO.HANSOKEY		=	D_FNHANSO.HANSOKEY	AND
			FNSIJI.HANSOKEY			=	FNHANSO.HANSOKEY	AND
			FNSIJI.SUBDIVIDE_FLAG	=	'1'					;

		IF WK_COUNT <= 0 THEN
			IO_RC		:=	FUKKI.RET_DATNOT ;
			GOTO RTCS10_60ENDLABEL ;
		ELSE
			IO_RC		:=	FUKKI.RET_OK ;
		END IF	;
		IF WK_COUNT > 1 THEN
			GOTO RTCS10_60ENDLABEL ;
		END IF	;






		SELECT
			COUNT(*)
		INTO
			WK_COUNT
		FROM
			FNHANSO, FNSIJI
		WHERE
			FNHANSO.HANSOKEY		=	D_FNHANSO.HANSOKEY	AND
			FNSIJI.HANSOKEY			=	FNHANSO.HANSOKEY	AND
			FNSIJI.SUBDIVIDE_FLAG	=	'0'					;

		IF WK_COUNT <= 0 THEN
			IO_RC		:=	FUKKI.RET_OK ;
			IF D_FNZAIKO.ZAIKOSU = WK_SUMNSSU THEN
				IO_RC	:=	FUKKI.RET_DATNOT ;
			END IF	;
		ELSE
			IO_RC		:=	FUKKI.RET_OK ;
		END IF	;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				GOTO	RTCS10_60ENDLABEL ;


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				IO_MSGNO	:=	44002 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNSIJI')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '62', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '56'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNSIJI' ;

				GOTO	RTCS10_60ENDLABEL ;


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_MULTI ;
				IO_MSGNO	:=	44004 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNSIJI', IN_MCKEY)),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '63', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '63'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNSIJI'
								|| PA_AWC_DEFINE.MSGSECTION || IN_MCKEY ;

				GOTO	RTCS10_60ENDLABEL ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				IO_MSGNO	:=	44001 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNHANSO, FNSIJI', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '64', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '64'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNSIJI'
								|| PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

				GOTO	RTCS10_60ENDLABEL ;
			END IF ;

	END ;


<< RTCS10_60ENDLABEL >>
	NULL ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '6Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10_60 ;




























PROCEDURE RTCS10_50
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN

	WK_COUNT := 0	;





	BEGIN
		SELECT
			COUNT(*)
		INTO
			WK_COUNT
		FROM
			FNHANSO, FNSIJI
		WHERE
			FNHANSO.HANSOKEY	=	D_FNHANSO.HANSOKEY	AND
			FNSIJI.HANSOKEY		=	FNHANSO.HANSOKEY	AND
			(
				FNSIJI.SECTION				!=	D_FNSIJI.SECTION		OR
				FNSIJI.RETRIEVAL_STATION	!=	D_FNSIJI.RETRIEVAL_STATION
			) ;

/*===< DELETE START 2008.02.13 By K.Hashio >===
			FNHANSO.HANSOKEY	=	D_FNHANSO.HANSOKEY	AND
			FNSIJI.HANSOKEY		=	FNHANSO.HANSOKEY	AND
			FNSIJI.SECTION		!=	D_FNSIJI.SECTION	;
  ===< DELETE  END  2008.02.13 By K.Hashio >===*/

		IF WK_COUNT <= 0 THEN
			IO_RC		:=	FUKKI.RET_DATNOT ;
		ELSE
			IO_RC		:=	FUKKI.RET_OK ;
		END IF	;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				GOTO	RTCS10_50ENDLABEL ;


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				IO_MSGNO	:=	44002 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNZAIKO')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '52', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '52'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO' ;

				GOTO	RTCS10_50ENDLABEL ;


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_MULTI ;
				IO_MSGNO	:=	44004 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNZAIKO', IN_MCKEY)),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '53', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '53'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO'
								|| PA_AWC_DEFINE.MSGSECTION || IN_MCKEY ;

				GOTO	RTCS10_50ENDLABEL ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				IO_MSGNO	:=	44001 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNHANSO, FNZAIKO', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '54', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '54'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO'
								|| PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

				GOTO	RTCS10_50ENDLABEL ;
			END IF ;

	END ;


<< RTCS10_50ENDLABEL >>
	NULL ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '5Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10_50 ;

























PROCEDURE RTCS10_40
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN

	IO_RC			:=	FUKKI.RET_OK ;




	WK_REVPICSU := TRUNC((D_FNSYSTEM.PICKING_PER * D_FNZAIKO.ZAIKOSU) / 100) ;




	OPEN  FNSIJI01_CUR ;







	LOOP
		FETCH
			FNSIJI01_CUR
		INTO
			D_FNSIJI.SECTION,
			D_FNSIJI.RETRIEVAL_STATION,
			WK_SUMNSSU;


		IF FNSIJI01_CUR%NOTFOUND  =  TRUE		THEN


			IF FNSIJI01_CUR%ROWCOUNT  <=  0	THEN
				IO_PICFLG	:=	PICFLG_NYUKO ;
			END IF ;

			GOTO	RTCS10_40ENDLABEL ;
		END IF ;




		IF D_FNPICK_CTL.BUCKETREADING_FLG = PA_AWC_DEFINE.BUCKET_READ_NG THEN
			IO_PICFLG	:=	PICFLG_REJECT ;
			GOTO	RTCS10_40ENDLABEL ;
		END IF	;




		IF D_FNHANSO.SAGYOKBN = PA_AWC_DEFINE.SAGYOKBN_ZAIKAKU THEN
			IO_PICFLG	:=	PICFLG_ZAIKAK ;
			GOTO	RTCS10_40ENDLABEL ;
		END IF	;





		RTCS10_60 (IO_RC, IO_MSGDAT) ;
		IF IO_RC = FUKKI.RET_OK		THEN
			IO_PICFLG	:=	PICFLG_KOWAKE ;
			GOTO	RTCS10_40ENDLABEL ;
		END IF ;
		IO_RC	:=	FUKKI.RET_OK ;





		IF D_FNZAIKO.ZAIKOSU = WK_SUMNSSU THEN
			IO_PICFLG	:=	PICFLG_ZEN ;
			GOTO	RTCS10_40ENDLABEL ;
		END IF	;




		IF D_FNPICK_CTL.BUCKETREADING_FLG = PA_AWC_DEFINE.BUCKET_READ_NOREAD THEN



			RTCS10_50 (IO_RC, IO_MSGDAT) ;
			IF IO_RC = FUKKI.RET_DATNOT		THEN
				IO_RC	:=	FUKKI.RET_OK ;
				IO_PICFLG	:=	PICFLG_GYAKU ;

			ELSIF IO_RC = FUKKI.RET_OK		THEN
				IO_PICFLG	:=	PICFLG_SEI ;
			END IF ;

			GOTO	RTCS10_40ENDLABEL ;
		END IF	;




		IF WK_REVPICSU > WK_SUMNSSU THEN
			IO_PICFLG	:=	PICFLG_SEI ;




		ELSE



			IF D_FNSYSTEM.REV_PICKING_LOWLIMIT <= D_FNZAIKO.ZAIKOSU THEN
				IO_PICFLG	:=	PICFLG_GYAKU ;
			ELSE
				IO_PICFLG	:=	PICFLG_SEI ;
			END IF	;
		END IF;

		EXIT ;

<<RTCS10_40NEXTLABEL>>
		NULL ;
	END LOOP ;


<<RTCS10_40ENDLABEL>>




	IF FNSIJI01_CUR%ISOPEN = TRUE THEN
		CLOSE FNSIJI01_CUR ;
	END IF ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '4Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;




		IF FNSIJI01_CUR%ISOPEN = TRUE THEN
			CLOSE FNSIJI01_CUR ;
		END IF ;

END RTCS10_40 ;


























PROCEDURE RTCS10_30
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	BEGIN
		SELECT
			FNHANSO.HANSOKEY,
			FNHANSO.SAGYOKBN,
			FNZAIKO.ZAIKOSU
		INTO
			D_FNHANSO.HANSOKEY,
			D_FNHANSO.SAGYOKBN,
			D_FNZAIKO.ZAIKOSU
		FROM
			FNHANSO, FNZAIKO
		WHERE
			FNHANSO.MCKEY		=	IN_MCKEY	AND
			FNZAIKO.SYSTEMID	=	FNHANSO.SYSTEMID	;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_CHKERR ;
				IO_MSGNO	:=  44003 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNHANSO, FNZAIKO', IN_MCKEY)),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '31', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '31'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO'
								|| PA_AWC_DEFINE.MSGSECTION || IN_MCKEY ;
				GOTO	RTCS10_30ENDLABEL ;


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				IO_MSGNO	:=	44002 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNZAIKO')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '32', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '32'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO' ;

				GOTO	RTCS10_30ENDLABEL ;


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_MULTI ;
				IO_MSGNO	:=	44004 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNHANSO, FNZAIKO', IN_MCKEY)),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '33', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '33'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO'
								|| PA_AWC_DEFINE.MSGSECTION || IN_MCKEY ;

				GOTO	RTCS10_30ENDLABEL ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				IO_MSGNO	:=	44001 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNHANSO, FNZAIKO', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '34', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '34'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNHANSO, FNZAIKO'
								|| PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

				GOTO	RTCS10_30ENDLABEL ;
			END IF ;

	END ;


<< RTCS10_30ENDLABEL >>
	NULL ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '3Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10_30 ;


























PROCEDURE RTCS10_20
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	BEGIN
		SELECT
			BUCKETREADING_FLG
		INTO
			D_FNPICK_CTL.BUCKETREADING_FLG
		FROM
			FNPICK_CTL
		WHERE
			FNPICK_CTL.MCKEY	=	IN_MCKEY;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_CHKERR ;
				IO_MSGNO	:=  44003 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNPICK_CTL')),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '21', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '21'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNPICK_CTL'
								|| PA_AWC_DEFINE.MSGSECTION || ' ' ;
				GOTO	RTCS10_20ENDLABEL ;


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				IO_MSGNO	:=	44002 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNPICK_CTL')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '22', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '22'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNPICK_CTL' ;

				GOTO	RTCS10_20ENDLABEL ;


			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_MULTI ;
				IO_MSGNO	:=	44004 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4004, 'FNPICK_CTL', IN_MCKEY)),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '23', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '23'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNPICK_CTL'
								|| PA_AWC_DEFINE.MSGSECTION || IN_MCKEY ;

				GOTO	RTCS10_20ENDLABEL ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				IO_MSGNO	:=	44001 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNPICK_CTL', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '24', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '24'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNPICK_CTL'
								|| PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

				GOTO	RTCS10_20ENDLABEL ;
			END IF ;

	END ;


<< RTCS10_20ENDLABEL >>
	NULL ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '2Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10_20 ;


























PROCEDURE RTCS10_10
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	BEGIN
		SELECT
			PICKING_PER,
			REV_PICKING_LOWLIMIT
		INTO
			D_FNSYSTEM.PICKING_PER,
			D_FNSYSTEM.REV_PICKING_LOWLIMIT
		FROM
			FNSYSTEM ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_CHKERR ;
				IO_MSGNO	:=  44003 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNSYSTEM')),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '11', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '11'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNSYSTEM'
								|| PA_AWC_DEFINE.MSGSECTION || ' ' ;
				GOTO	RTCS10_10ENDLABEL ;


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				IO_MSGNO	:=	44002 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNSYSTEM')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '12', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '12'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNSYSTEM' ;

				GOTO	RTCS10_10ENDLABEL ;

			ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_MULTI ;
				IO_MSGNO	:=	44004 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNSYSTEM')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '13', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '13'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNSYSTEM'
								|| PA_AWC_DEFINE.MSGSECTION || ' ' ;

				GOTO	RTCS10_10ENDLABEL ;

			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				IO_MSGNO	:=	44001 ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNSYSTEM', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '14', WK_LOGMSG) ;
				IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || '14'
								|| PA_AWC_DEFINE.MSGSECTION || 'FNSYSTEM'
								|| PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

				GOTO	RTCS10_10ENDLABEL ;
			END IF ;

	END ;


<< RTCS10_10ENDLABEL >>
	NULL ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '1Z', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10_10 ;








BEGIN



	IO_RC			:=	FUKKI.RET_OK ;
	IO_MSGDAT		:= ' ' ;
	WK_MSGBUFF		:=	RPAD(' ', 255, ' ') ;
	WK_SYOKAI_KBN	:=	'0' ;




	WK_SYSNICHIJI	:= SYSDATE14_GET() ;
	WK_SYSDATE		:= SYSDATE08_GET() ;




	RTCS10_10 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	RTCS10_ENDLABEL ;
	END IF ;




	RTCS10_20 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	RTCS10_ENDLABEL ;
	END IF ;




	RTCS10_30 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	RTCS10_ENDLABEL ;
	END IF ;




	RTCS10_40 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	RTCS10_ENDLABEL ;
	END IF ;






<<RTCS10_ENDLABEL>>
	IF IO_RC != FUKKI.RET_OK THEN
		IO_MSGDAT 	:= SUBSTRB (WK_LOGMSG, 1, PA_AWC_DEFINE.DEF_MSGLENG) ;
	ELSE
		IO_MSGDAT := ' ' ;
	END IF ;






EXCEPTION
	WHEN OTHERS THEN

		IO_MSGNO	:=	45001 ;
		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:= SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
									1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:= LOGOUT('04', DEF_MYNAME, 'EN', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTCS10 ;
/
