DROP PROCEDURE YKK.MAKZAI;

CREATE OR REPLACE PROCEDURE YKK.MAKZAI
		(
			IN_SYOZAIKEY	IN		FNLOCAT.SYOZAIKEY%TYPE,
			IN_ZAIKEY		IN		FNZAIKO.ZAIKEY%TYPE,
			IO_RC			IN OUT	NUMBER,
			IO_MSGDAT		IN OUT	VARCHAR2
		) IS
































DEF_MYNAME		CONSTANT VARCHAR2(10)	:= 'MAKZAI' ;

SYORIKBN_NYUKO	CONSTANT VARCHAR2(1)	:= '1' ;
SYORIKBN_ICHIJI	CONSTANT VARCHAR2(1)	:= '2' ;
SYORIKBN_CHOKO	CONSTANT VARCHAR2(1)	:= '3' ;




WK_MSGBUFF		VARCHAR2(255) ;
WK_LOGMSG		VARCHAR2(255) ;


D_FNZAIKO		FNZAIKO%ROWTYPE ;
D_FNLOCAT		FNLOCAT%ROWTYPE ;
D_FMZKEY		FMZKEY%ROWTYPE ;
D_FMBUCKET		FMBUCKET%ROWTYPE ;
D_FNAKITANA		FNAKITANA%ROWTYPE ;


WK_TICKET_NO	FNZAIKO.TICKET_NO%TYPE ;
WK_BUCKET_NO	FMBUCKET.BUCKET_NO%TYPE ;
WK_SYSTEMID		FNZAIKO.SYSTEMID%TYPE ;


WK_RC			NUMBER(9,0) ;
WK_COUNT		NUMBER(9, 0) ;
WK_SYSNICHIJI	VARCHAR2(14) ;


























PROCEDURE MAKZAI_A0
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		UPDATE  FNAKITANA
		SET
			FNAKITANA.TANAFLG		=	'1'
		WHERE
			FNAKITANA.SYOZAIKEY		=	IN_SYOZAIKEY ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:=	FUKKI.RET_DBERR ;
			WK_MSGBUFF	:=	'IN_SYOZAIKEY[' || IN_SYOZAIKEY || ']';
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNAKITANA', WK_MSGBUFF)),
										1, PA_AWC_DEFINE.DEF_MSGLENG) ;
			WK_RC		:=	LOGOUT('03', DEF_MYNAME, 'A0', WK_LOGMSG) ;
			IO_MSGDAT	:= WK_LOGMSG  ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				WK_MSGBUFF	:=	'IN_SYOZAIKEY[' || IN_SYOZAIKEY || ']';
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNAKITANA', WK_MSGBUFF)),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, 'A1', WK_LOGMSG) ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNAKITANA')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, 'A2', WK_LOGMSG) ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNAKITANA', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, 'A3', WK_LOGMSG) ;
			END IF ;

			IO_MSGDAT	:= WK_LOGMSG  ;
	END ;


EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '1Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG  ;

END MAKZAI_A0 ;


























PROCEDURE MAKZAI_90
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	D_FNLOCAT.SYOZAIKEY			:=	IN_SYOZAIKEY ;

	D_FNLOCAT.HANSOKEY			:=	' ' ;

	D_FNLOCAT.SOKOKBN			:=	SUBSTRB (IN_SYOZAIKEY, 1, 1) ;

	D_FNLOCAT.BUCKET_NO			:=	WK_BUCKET_NO ;

	D_FNLOCAT.JYURITSU			:=	0 ;

	D_FNLOCAT.KONSAISU			:=	1 ;

	D_FNLOCAT.ZAIJYOFLG			:=	'2' ;

	D_FNLOCAT.SHIKIFLG			:=	'0' ;

	D_FNLOCAT.HIKIFLG			:=	'0' ;

	D_FNLOCAT.ACCESSFLG			:=	'0' ;

	D_FNLOCAT.YSHUBETSU			:=	' ' ;

	D_FNLOCAT.SYSTEMID			:=	WK_SYSTEMID ;

	D_FNLOCAT.SCHNO				:=	' ' ;

	D_FNLOCAT.IKISAKI			:=	' ' ;

	D_FNLOCAT.AILESTNO			:=	'90' || SUBSTRB(TO_CHAR(TRUNC((TO_NUMBER (SUBSTRB(IN_SYOZAIKEY, 2, 3)) + 1) / 2), '00'),  -2, 2)	;





	BEGIN
		INSERT INTO FNLOCAT
		(
			FNLOCAT.SYOZAIKEY,
			FNLOCAT.HANSOKEY,
			FNLOCAT.SOKOKBN,
			FNLOCAT.BUCKET_NO,
			FNLOCAT.JYURITSU,
			FNLOCAT.KONSAISU,
			FNLOCAT.ZAIJYOFLG,
			FNLOCAT.SHIKIFLG,
			FNLOCAT.HIKIFLG,
			FNLOCAT.ACCESSFLG,
			FNLOCAT.YSHUBETSU,
			FNLOCAT.SYSTEMID,
			FNLOCAT.SCHNO,
			FNLOCAT.IKISAKI,
			FNLOCAT.AILESTNO

		)
		VALUES
		(
			D_FNLOCAT.SYOZAIKEY,
			D_FNLOCAT.HANSOKEY,
			D_FNLOCAT.SOKOKBN,
			D_FNLOCAT.BUCKET_NO,
			D_FNLOCAT.JYURITSU,
			D_FNLOCAT.KONSAISU,
			D_FNLOCAT.ZAIJYOFLG,
			D_FNLOCAT.SHIKIFLG,
			D_FNLOCAT.HIKIFLG,
			D_FNLOCAT.ACCESSFLG,
			D_FNLOCAT.YSHUBETSU,
			D_FNLOCAT.SYSTEMID,
			D_FNLOCAT.SCHNO,
			D_FNLOCAT.IKISAKI,
			D_FNLOCAT.AILESTNO

		) ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:= FUKKI.RET_DBERR ;
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001, 'FNLOCAT',
										TO_CHAR(SQLCODE))), 1, PA_AWC_DEFINE.DEF_MSGLENG) ;
			WK_RC		:=	LOGOUT('03', DEF_MYNAME, '91', WK_LOGMSG) ;
			IO_MSGDAT	:= WK_LOGMSG ;
			GOTO	MAKZAI_90ENDLABEL ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL	;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNLOCAT')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '92', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNLOCAT', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '93', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;

			GOTO	 MAKZAI_90ENDLABEL ;
	END ;


<< MAKZAI_90ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '9Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_90 ;





























PROCEDURE MAKZAI_80
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		WK_COUNT	:=	0 ;
		SELECT
			COUNT (*)
		INTO
			WK_COUNT
		FROM
			FNZAIKO
		WHERE
			FNZAIKO.TICKET_NO	=	WK_TICKET_NO ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNZAIKO')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '82', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNZAIKO', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '83', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;
	END ;


<< MAKZAI_80ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '8Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_80 ;



























PROCEDURE MAKZAI_70
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	FOR WK_I IN 9100 .. 999999999
    LOOP



		WK_TICKET_NO	:= 'T' || SUBSTRB( TO_CHAR( WK_I, '000000000' ), -9, 9 );




		MAKZAI_80 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	MAKZAI_70ENDLABEL ;
		END IF ;




		IF WK_COUNT  <=  0	THEN
			GOTO	MAKZAI_70NEXTLABEL ;
		END IF ;

	END LOOP ;

	IO_MSGDAT	:=	'Could not get ticket no.' ;
	IO_RC		:=	FUKKI.RET_ERR ;
	GOTO	MAKZAI_70ENDLABEL ;


<< MAKZAI_70NEXTLABEL >>




	STASE0 (WK_SYSTEMID, IO_RC, WK_MSGBUFF) ;
	IF IO_RC != FUKKI.RET_OK THEN

		IO_MSGDAT	:=	WK_MSGBUFF  ;
		IO_RC		:=	FUKKI.RET_ERR ;
		GOTO	MAKZAI_70NEXTLABEL ;
	END IF ;





	D_FNZAIKO.TICKET_NO						:=	WK_TICKET_NO ;

	D_FNZAIKO.ZAIKEY						:=	IN_ZAIKEY ;

	D_FNZAIKO.COLOR_CODE					:=	'RED0001' ;

	D_FNZAIKO.DEPOT_CODE					:=	' ' ;

	D_FNZAIKO.LOTNO							:=	' ' ;

	D_FNZAIKO.ZAIKOSU						:=	100 ;

	D_FNZAIKO.NYOTEISU						:=	0 ;

	D_FNZAIKO.SKANOSU						:=	100 ;

	D_FNZAIKO.SAINYUSU						:=	0 ;

	D_FNZAIKO.NYUKOHIJI						:=	WK_SYSNICHIJI ;

	D_FNZAIKO.KAKUNINHIJI					:=	' ' ;

	D_FNZAIKO.KOSHINHIJI					:=	WK_SYSNICHIJI ;

	D_FNZAIKO.SAINYUKBN						:=	'1' ;

	D_FNZAIKO.TSUMIKBN						:=	'1' ;

	D_FNZAIKO.KENSAKBN						:=	'1' ;

	D_FNZAIKO.SYSTEMID						:=	WK_SYSTEMID ;

	D_FNZAIKO.PLAN_QTY						:=	0 ;

	D_FNZAIKO.PLAN_WEIGHT					:=	0.0000 ;

	D_FNZAIKO.MOVE_FLAG						:=	'0' ;

	D_FNZAIKO.MADE_LINE						:=	' ' ;

	D_FNZAIKO.MADE_SECTION					:=	' ' ;

	IF (100 * D_FMZKEY.MASTER_UNIT_WEIGHT)	>	10	THEN
		D_FNZAIKO.REAL_UNIT_WEIGHT			:=	9.9999999 ;
	ELSE
		D_FNZAIKO.REAL_UNIT_WEIGHT			:=	100 * D_FMZKEY.MASTER_UNIT_WEIGHT ;
	END IF ;


	D_FNZAIKO.WEIGHT_REPORT_COMPLETE_FLAG	:=	'2' ;

	D_FNZAIKO.STORAGE_PLACE_FLAG			:=	'0' ;

	D_FNZAIKO.BUCKET_NO						:=	WK_BUCKET_NO ;

	D_FNZAIKO.MEMO							:=	' ' ;

	D_FNZAIKO.MANAGE_ITEM_FLAG				:=	'0' ;

	D_FNZAIKO.RECEPTION_DATETIME			:=	' ' ;

	D_FNZAIKO.USERID						:=	' ' ;

	D_FNZAIKO.USERNAME						:=	' ' ;

	D_FNZAIKO.REMEASURE_FLAG				:=	' ' ;






	BEGIN
		INSERT INTO FNZAIKO
		(
			FNZAIKO.TICKET_NO,
			FNZAIKO.ZAIKEY,
			FNZAIKO.COLOR_CODE,
			FNZAIKO.DEPOT_CODE,
			FNZAIKO.LOTNO,
			FNZAIKO.ZAIKOSU,
			FNZAIKO.NYOTEISU,
			FNZAIKO.SKANOSU,
			FNZAIKO.SAINYUSU,
			FNZAIKO.NYUKOHIJI,
			FNZAIKO.KAKUNINHIJI,
			FNZAIKO.KOSHINHIJI,
			FNZAIKO.SAINYUKBN,
			FNZAIKO.TSUMIKBN,
			FNZAIKO.KENSAKBN,
			FNZAIKO.SYSTEMID,
			FNZAIKO.PLAN_QTY,
			FNZAIKO.PLAN_WEIGHT,
			FNZAIKO.MOVE_FLAG,
			FNZAIKO.MADE_SECTION,
			FNZAIKO.MADE_LINE,
			FNZAIKO.REAL_UNIT_WEIGHT,
			FNZAIKO.WEIGHT_REPORT_COMPLETE_FLAG,
			FNZAIKO.STORAGE_PLACE_FLAG,
			FNZAIKO.BUCKET_NO,
			FNZAIKO.MEMO,
			FNZAIKO.MANAGE_ITEM_FLAG,
			FNZAIKO.RECEPTION_DATETIME,
			FNZAIKO.USERID,
			FNZAIKO.USERNAME,
			FNZAIKO.REMEASURE_FLAG

		)
		VALUES
		(
			D_FNZAIKO.TICKET_NO,
			D_FNZAIKO.ZAIKEY,
			D_FNZAIKO.COLOR_CODE,
			D_FNZAIKO.DEPOT_CODE,
			D_FNZAIKO.LOTNO,
			D_FNZAIKO.ZAIKOSU,
			D_FNZAIKO.NYOTEISU,
			D_FNZAIKO.SKANOSU,
			D_FNZAIKO.SAINYUSU,
			D_FNZAIKO.NYUKOHIJI,
			D_FNZAIKO.KAKUNINHIJI,
			D_FNZAIKO.KOSHINHIJI,
			D_FNZAIKO.SAINYUKBN,
			D_FNZAIKO.TSUMIKBN,
			D_FNZAIKO.KENSAKBN,
			D_FNZAIKO.SYSTEMID,
			D_FNZAIKO.PLAN_QTY,
			D_FNZAIKO.PLAN_WEIGHT,
			D_FNZAIKO.MOVE_FLAG,
			D_FNZAIKO.MADE_SECTION,
			D_FNZAIKO.MADE_LINE,
			D_FNZAIKO.REAL_UNIT_WEIGHT,
			D_FNZAIKO.WEIGHT_REPORT_COMPLETE_FLAG,
			D_FNZAIKO.STORAGE_PLACE_FLAG,
			D_FNZAIKO.BUCKET_NO,
			D_FNZAIKO.MEMO,
			D_FNZAIKO.MANAGE_ITEM_FLAG,
			D_FNZAIKO.RECEPTION_DATETIME,
			D_FNZAIKO.USERID,
			D_FNZAIKO.USERNAME,
			D_FNZAIKO.REMEASURE_FLAG
		) ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:= FUKKI.RET_DBERR ;
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001, 'FNZAIKO',
										TO_CHAR(SQLCODE))), 1, PA_AWC_DEFINE.DEF_MSGLENG) ;
			WK_RC		:=	LOGOUT('03', DEF_MYNAME, '71', WK_LOGMSG) ;
			IO_MSGDAT	:= WK_LOGMSG ;
			GOTO	MAKZAI_70ENDLABEL ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL	;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNZAIKO')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '72', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNZAIKO', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '73', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;

			GOTO	 MAKZAI_70ENDLABEL ;
	END ;


<< MAKZAI_70ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '7Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_70 ;


























PROCEDURE MAKZAI_60
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	D_FMBUCKET.BUCKET_NO			:=	WK_BUCKET_NO ;

	D_FMBUCKET.PACKING_WEIGHT		:=	10.0000001 ;

	D_FMBUCKET.HEIGHT_FLAG			:=	'1' ;

	D_FMBUCKET.LASTUPDATE_DATETIME	:=	WK_SYSNICHIJI ;






	BEGIN
		INSERT INTO FMBUCKET
		(
			FMBUCKET.BUCKET_NO,
			FMBUCKET.PACKING_WEIGHT,
			FMBUCKET.HEIGHT_FLAG,
			FMBUCKET.LASTUPDATE_DATETIME

		)
		VALUES
		(
			D_FMBUCKET.BUCKET_NO,
			D_FMBUCKET.PACKING_WEIGHT,
			D_FMBUCKET.HEIGHT_FLAG,
			D_FMBUCKET.LASTUPDATE_DATETIME
		) ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:= FUKKI.RET_DBERR ;
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001, 'FMBUCKET',
										TO_CHAR(SQLCODE))), 1, PA_AWC_DEFINE.DEF_MSGLENG) ;
			WK_RC		:=	LOGOUT('03', DEF_MYNAME, '61', WK_LOGMSG) ;
			IO_MSGDAT	:= WK_LOGMSG ;
			GOTO	MAKZAI_60ENDLABEL ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL	;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FMBUCKET')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '62', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FMBUCKET', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '63', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;

			GOTO	 MAKZAI_60ENDLABEL ;
	END ;


<< MAKZAI_60ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '6Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_60 ;





























PROCEDURE MAKZAI_50
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		WK_COUNT	:=	0 ;
		SELECT
			COUNT (*)
		INTO
			WK_COUNT
		FROM
			FMBUCKET
		WHERE
			FMBUCKET.BUCKET_NO	=	WK_BUCKET_NO ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FMBUCKET')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '52', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FMBUCKET', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '53', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;
	END ;


<< MAKZAI_50ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '5Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_50 ;





























PROCEDURE MAKZAI_40
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		WK_COUNT	:=	0 ;
		SELECT
			COUNT (*)
		INTO
			WK_COUNT
		FROM
			FNZAIKO
		WHERE
			FNZAIKO.BUCKET_NO	=	WK_BUCKET_NO ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				NULL ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNZAIKO')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '42', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNZAIKO', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '43', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;
			END IF ;
	END ;


<< MAKZAI_40ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '4Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG ;

END MAKZAI_40 ;



























PROCEDURE MAKZAI_30
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	FOR WK_I IN 9100 .. 999999
    LOOP



		WK_BUCKET_NO	:= 'T' || SUBSTRB( TO_CHAR( WK_I, '0000000' ), -6, 6 );




		MAKZAI_40 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	MAKZAI_30ENDLABEL ;
		END IF ;




		IF WK_COUNT  <=  0	THEN
			GOTO	MAKZAI_30NEXTLABEL ;
		END IF ;

	END LOOP ;

	IO_MSGDAT	:=	'Could not get bucket no.' ;
	IO_RC		:=	FUKKI.RET_ERR ;
	GOTO	MAKZAI_30ENDLABEL ;

<< MAKZAI_30NEXTLABEL >>




	MAKZAI_50 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_30ENDLABEL ;
	END IF ;




	IF WK_COUNT  <=  0	THEN
		MAKZAI_60 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	MAKZAI_30ENDLABEL ;
		END IF ;
	END IF ;

<< MAKZAI_30ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '3Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG  ;

END MAKZAI_30 ;



























PROCEDURE MAKZAI_20
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		SELECT
			FMZKEY.ZAIKEY,
			FMZKEY.ZKNAME1,
			FMZKEY.ZKKBN,
			FMZKEY.SEKISAISU,
			FMZKEY.YSHIKIRI,
			FMZKEY.YSHUBETSU,
			FMZKEY.SOTOSU,
			FMZKEY.UCHISU,
			FMZKEY.MENTEHIJI,
			FMZKEY.JOGENSUJ,
			FMZKEY.KAGENSUJ,
			FMZKEY.SOFTZONE,
			FMZKEY.ZKNAME2,
			FMZKEY.ZKNAME3,
			FMZKEY.MASTER_UNIT_WEIGHT,
			FMZKEY.ITEM_RANK,
			FMZKEY.LIMIT_QTY,
			FMZKEY.MEASURE_FLAG,
			FMZKEY.REMOVE_CONVENT_FLAG,
			FMZKEY.MEASURE_QTY,
			FMZKEY.MANAGE_ITEM_FLAG

		INTO
			D_FMZKEY.ZAIKEY,
			D_FMZKEY.ZKNAME1,
			D_FMZKEY.ZKKBN,
			D_FMZKEY.SEKISAISU,
			D_FMZKEY.YSHIKIRI,
			D_FMZKEY.YSHUBETSU,
			D_FMZKEY.SOTOSU,
			D_FMZKEY.UCHISU,
			D_FMZKEY.MENTEHIJI,
			D_FMZKEY.JOGENSUJ,
			D_FMZKEY.KAGENSUJ,
			D_FMZKEY.SOFTZONE,
			D_FMZKEY.ZKNAME2,
			D_FMZKEY.ZKNAME3,
			D_FMZKEY.MASTER_UNIT_WEIGHT,
			D_FMZKEY.ITEM_RANK,
			D_FMZKEY.LIMIT_QTY,
			D_FMZKEY.MEASURE_FLAG,
			D_FMZKEY.REMOVE_CONVENT_FLAG,
			D_FMZKEY.MEASURE_QTY,
			D_FMZKEY.MANAGE_ITEM_FLAG
		FROM
			FMZKEY
		WHERE
			FMZKEY.ZAIKEY		=	IN_ZAIKEY			AND
			ROWNUM				=	1 ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				WK_MSGBUFF	:=	'ZAIKEY[' || IN_ZAIKEY || ']';
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FMZKEY', WK_MSGBUFF)),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '21', WK_LOGMSG) ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FMZKEY')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '22', WK_LOGMSG) ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FMZKEY', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '23', WK_LOGMSG) ;
			END IF ;

			IO_MSGDAT	:= WK_LOGMSG  ;
	END ;


<< MAKZAI_20ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '2Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG  ;

END MAKZAI_20 ;



























PROCEDURE MAKZAI_10
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	BEGIN
		SELECT
			FNAKITANA.TANAFLG,
			FNAKITANA.ACCESSFLG
		INTO
			D_FNAKITANA.TANAFLG,
			D_FNAKITANA.ACCESSFLG
		FROM
			FNAKITANA
		WHERE
			SYOZAIKEY			=	IN_SYOZAIKEY ;




		IF  D_FNAKITANA.TANAFLG != '0'		THEN
			IO_MSGDAT	:=	'No empty location ['|| IN_SYOZAIKEY ||']' ;
			IO_RC		:=	FUKKI.RET_ERR ;
			GOTO	MAKZAI_10ENDLABEL ;
		END IF ;




		IF  D_FNAKITANA.ACCESSFLG != '0'	THEN
			IO_MSGDAT	:=	'No access location ['|| IN_SYOZAIKEY ||']' ;
			IO_RC		:=	FUKKI.RET_ERR ;
			GOTO	MAKZAI_10ENDLABEL ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				WK_MSGBUFF	:=	'SYOZAIKEY[' || IN_SYOZAIKEY || ']';
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNAKITANA', WK_MSGBUFF)),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '11', WK_LOGMSG) ;

			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.SQLDEADLOCK ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNAKITANA')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '12', WK_LOGMSG) ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNAKITANA', TO_CHAR(SQLCODE))),
											1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('04', DEF_MYNAME, '13', WK_LOGMSG) ;
			END IF ;

			IO_MSGDAT	:= WK_LOGMSG  ;
	END ;


<< MAKZAI_10ENDLABEL >>
	NULL ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, '1Z', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG  ;

END MAKZAI_10 ;








BEGIN



	IO_RC			:=	FUKKI.RET_OK ;
	IO_MSGDAT		:= ' ' ;
	WK_LOGMSG		:= ' ' ;
	WK_MSGBUFF		:=	RPAD(' ', 255, ' ') ;




	WK_SYSNICHIJI	:= SYSDATE14_GET() ;




	MAKZAI_10 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




	MAKZAI_20 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




	MAKZAI_30 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




	MAKZAI_70 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




	MAKZAI_90 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




	MAKZAI_A0 (IO_RC, WK_LOGMSG) ;
	IF IO_RC != FUKKI.RET_OK THEN
		GOTO	MAKZAI_ENDLABEL ;
	END IF ;




<<MAKZAI_ENDLABEL>>
	IF IO_RC != FUKKI.RET_OK THEN
		IO_MSGDAT := WK_LOGMSG ;
	ELSE
		IO_MSGDAT := ' ' ;
	END IF ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;

		WK_LOGMSG	:= SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
									1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:= LOGOUT('04', DEF_MYNAME, 'EN', WK_LOGMSG) ;

		IO_MSGDAT	:=	WK_LOGMSG ;

END MAKZAI ;
/


