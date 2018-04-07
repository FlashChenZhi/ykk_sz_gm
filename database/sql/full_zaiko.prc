DROP PROCEDURE FULL_ZAIKO;

CREATE OR REPLACE PROCEDURE
	FULL_ZAIKO
		(
			IN_KENSU		IN		NUMBER,
			IN_ZAIKEY		IN		FNZAIKO.ZAIKEY%TYPE,
			IO_COUNT		IN OUT	NUMBER,
			IO_RC			IN OUT	NUMBER,
			IO_MSGDAT		IN OUT	VARCHAR2
		) IS
































DEF_MYNAME		CONSTANT VARCHAR2(10)	:= 'FULL_ZAIKO' ;




WK_MSGBUFF		VARCHAR2(255) ;
WK_LOGMSG		VARCHAR2(255) ;


D_FNZAIKO		FNZAIKO%ROWTYPE ;
D_FNLOCAT		FNLOCAT%ROWTYPE ;
D_FNAKITANA		FNAKITANA%ROWTYPE ;


WK_TICKET_NO	FNZAIKO.TICKET_NO%TYPE ;
WK_BUCKET_NO	FNZAIKO.BUCKET_NO%TYPE ;
WK_SYSTEMID		FNZAIKO.SYSTEMID%TYPE ;


WK_RC			NUMBER(9,0) ;
WK_COUNT		NUMBER(9, 0) ;
WK_SYSNICHIJI	VARCHAR2(14) ;











CURSOR
	FNAKITANA01_CUR
	IS
	SELECT
		FNAKITANA.SOKOKBN,
		FNAKITANA.HARDZONE,
		FNAKITANA.SOFTZONE,
		FNAKITANA.BANKNO,
		FNAKITANA.BAYNO,
		FNAKITANA.LEVELNO,
		FNAKITANA.TANAFLG,
		FNAKITANA.ACCESSFLG,
		FNAKITANA.SYOZAIKEY

	FROM
		FNAKITANA
	WHERE
		FNAKITANA.TANAFLG		=	'0'			AND
		FNAKITANA.ACCESSFLG		=	'0'
	ORDER BY
		FNAKITANA.BAYNO,
		FNAKITANA.LEVELNO,
		FNAKITANA.BANKNO ;


























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
			FNAKITANA.SYOZAIKEY		=	D_FNAKITANA.SYOZAIKEY ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:=	FUKKI.RET_DBERR ;
			WK_MSGBUFF	:=	'D_FNAKITANA.SYOZAIKEY[' || D_FNAKITANA.SYOZAIKEY || ']';
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4003, 'FNAKITANA', WK_MSGBUFF)),
										1, PA_AWC_DEFINE.DEF_MSGLENG) ;
			WK_RC		:=	LOGOUT('03', DEF_MYNAME, 'A0', WK_LOGMSG) ;
			IO_MSGDAT	:= WK_LOGMSG  ;
		END IF ;

	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:=	FUKKI.RET_DATNOT ;
				WK_MSGBUFF	:=	'D_FNAKITANA.SYOZAIKEY[' || D_FNAKITANA.SYOZAIKEY || ']';
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
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, 'AZ', WK_LOGMSG) ;
		IO_MSGDAT	:= WK_LOGMSG  ;

END MAKZAI_A0 ;


























PROCEDURE MAKZAI_90
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN





	D_FNLOCAT.SYOZAIKEY			:=	D_FNAKITANA.SYOZAIKEY ;

	D_FNLOCAT.HANSOKEY			:=	' ' ;

	D_FNLOCAT.SOKOKBN			:=	SUBSTRB (D_FNAKITANA.SYOZAIKEY, 1, 1) ;

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

	D_FNLOCAT.AILESTNO			:=	'90' || SUBSTRB(TO_CHAR(TRUNC((TO_NUMBER (SUBSTRB(D_FNAKITANA.SYOZAIKEY, 2, 3)) + 1) / 2), '00'),  -2, 2)	;





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


























PROCEDURE MAKZAI_70
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	STASE0 (WK_SYSTEMID, IO_RC, WK_MSGBUFF) ;
	IF IO_RC != FUKKI.RET_OK THEN

		IO_MSGDAT	:=	WK_MSGBUFF  ;
		IO_RC		:=	FUKKI.RET_ERR ;
		GOTO	MAKZAI_70ENDLABEL ;
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

	D_FNZAIKO.REAL_UNIT_WEIGHT				:=	100 * 0.001191 ;

	D_FNZAIKO.WEIGHT_REPORT_COMPLETE_FLAG	:=	'1' ;

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








BEGIN



	IO_RC			:=	FUKKI.RET_OK ;
	IO_COUNT		:=	0 ;
	IO_MSGDAT		:= ' ' ;
	WK_LOGMSG		:= ' ' ;
	WK_MSGBUFF		:=	RPAD(' ', 255, ' ') ;




	WK_SYSNICHIJI	:= SYSDATE14_GET() ;




	OPEN  FNAKITANA01_CUR ;







	FOR WK_I IN 1 .. IN_KENSU
	LOOP
		FETCH
			FNAKITANA01_CUR
		INTO
			D_FNAKITANA.SOKOKBN,
			D_FNAKITANA.HARDZONE,
			D_FNAKITANA.SOFTZONE,
			D_FNAKITANA.BANKNO,
			D_FNAKITANA.BAYNO,
			D_FNAKITANA.LEVELNO,
			D_FNAKITANA.TANAFLG,
			D_FNAKITANA.ACCESSFLG,
			D_FNAKITANA.SYOZAIKEY ;


		IF FNAKITANA01_CUR%NOTFOUND  =  TRUE		THEN
			GOTO	FULL_ZAIKO_ENDLABEL ;
		END IF ;







		WK_TICKET_NO	:= 'S' || SUBSTRB( TO_CHAR( WK_I, '000000000' ), -9, 9 );







		WK_BUCKET_NO	:= 'S' || SUBSTRB( TO_CHAR( WK_I, '0000000' ), -6, 6 );







		WK_SYSTEMID		:= 'SYUKKA_TEST' || SUBSTRB( TO_CHAR( WK_I, '00000000' ), -7, 7 );







		MAKZAI_70 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	FULL_ZAIKO_ENDLABEL ;
		END IF ;




		MAKZAI_90 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	FULL_ZAIKO_ENDLABEL ;
		END IF ;




		MAKZAI_A0 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	FULL_ZAIKO_ENDLABEL ;
		END IF ;

	END LOOP;





<<FULL_ZAIKO_ENDLABEL>>
	IF IO_RC != FUKKI.RET_OK THEN
		IO_MSGDAT	:= WK_LOGMSG ;
	ELSE
		IO_COUNT	:=	FNAKITANA01_CUR%ROWCOUNT ;
		IO_MSGDAT	:= ' ' ;
	END IF ;


	IF FNAKITANA01_CUR%ISOPEN = TRUE THEN
		CLOSE FNAKITANA01_CUR ;
	END IF ;

EXCEPTION
	WHEN OTHERS THEN

		IO_RC		:=	FUKKI.RET_PROERR ;

		WK_LOGMSG	:= SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
									1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:= LOGOUT('04', DEF_MYNAME, 'EN', WK_LOGMSG) ;

		IO_MSGDAT	:=	WK_LOGMSG ;


		IF FNAKITANA01_CUR%ISOPEN = TRUE THEN
			CLOSE FNAKITANA01_CUR ;
		END IF ;

END FULL_ZAIKO ;

/
