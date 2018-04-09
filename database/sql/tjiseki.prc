DROP PROCEDURE TJISEKI;

CREATE OR REPLACE PROCEDURE
	TJISEKI
		(
			IN_KENSU		IN		NUMBER,
			IN_SAKUSEIHIJI	IN		FNJISEKI.SAKUSEIHIJI%TYPE,
			IO_RC			IN OUT	NUMBER,
			IO_MSGDAT		IN OUT	VARCHAR2
		) IS
































DEF_MYNAME		CONSTANT VARCHAR2(10)	:= 'TJISEKI' ;




WK_MSGBUFF		VARCHAR2(255) ;
WK_LOGMSG		VARCHAR2(255) ;


D_FNJISEKI		FNJISEKI%ROWTYPE ;


WK_SYSTEMID		FNJISEKI.SYSTEMID%TYPE ;
WK_BUCKET_NO	FNJISEKI.BUCKET_NO%TYPE ;
WK_NYUSYUKBN 	FNJISEKI.NYUSYUKBN%TYPE ;


WK_RC			NUMBER(9,0) ;
WK_COUNT		NUMBER(9, 0) ;
WK_SYSNICHIJI	VARCHAR2(14) ;

























PROCEDURE MAKZAI_70
(
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
IS
BEGIN




	D_FNJISEKI.ZAIKEY				:=	'1283397' ;

	D_FNJISEKI.ZKNAME				:=	'05 CN DSY3-050 H6' ;

	D_FNJISEKI.ZKNAME2				:=	' ' ;

	D_FNJISEKI.ZKNAME3				:=	' ' ;

	D_FNJISEKI.SAKUSEIHIJI			:=	IN_SAKUSEIHIJI ;

	D_FNJISEKI.NYUSYUKBN			:=	WK_NYUSYUKBN ;

	D_FNJISEKI.SAGYOKBN				:=	'T' ;

	IF WK_NYUSYUKBN  =  '1'	THEN

		D_FNJISEKI.MCKEY			:=	'00000001' ;

		D_FNJISEKI.SAKUKBN			:=	'1' ;

		D_FNJISEKI.NYUSYUSTNO		:=	'1101' ;

		D_FNJISEKI.STARTSTNO		:=	'1101' ;

		D_FNJISEKI.ENDSTNO			:=	'9001' ;
	ELSE

		D_FNJISEKI.MCKEY			:=	'50000001' ;

		D_FNJISEKI.SAKUKBN			:=	'2' ;

		D_FNJISEKI.NYUSYUSTNO		:=	'1203' ;

		D_FNJISEKI.STARTSTNO		:=	'9001' ;

		D_FNJISEKI.ENDSTNO			:=	'1203' ;
	END IF ;

	D_FNJISEKI.TICKET_NO			:=	'TICKET0001' ;

	D_FNJISEKI.BUCKET_NO			:=	'B999999' ;

	D_FNJISEKI.COLOR_CODE			:=	'COLOR99' ;

	D_FNJISEKI.NYUSYUSU				:=	100 ;

	D_FNJISEKI.RETRIEVAL_NO			:=	' ' ;

	D_FNJISEKI.REAL_WORK_NUMBER		:=	100 ;

	D_FNJISEKI.SERIAL_NO			:=	1 ;

	D_FNJISEKI.ORDER_NO				:=	' ' ;

	D_FNJISEKI.ORDER_SERIAL_NO		:=	0 ;

	D_FNJISEKI.START_DATE			:=	' ' ;

	D_FNJISEKI.START_TIMING_FLAG	:=	' ' ;

	D_FNJISEKI.COMPLETE_DATE		:=	' ' ;

	D_FNJISEKI.COMPLETE_TIMING_FLAG	:=	' ' ;

	D_FNJISEKI.DEPOT_CODE			:=	' ' ;

	D_FNJISEKI.SECTION				:=	' ' ;

	D_FNJISEKI.LINE					:=	' ' ;

	D_FNJISEKI.LINE_TYPE			:=	' ' ;

	D_FNJISEKI.CUSTOMER_CODE		:=	' ' ;

	D_FNJISEKI.CUSTOMER_NAME1		:=	' ' ;

	D_FNJISEKI.CUSTOMER_NAME2		:=	' ' ;

	D_FNJISEKI.PR_NO				:=	' ' ;

	D_FNJISEKI.RETRIEVAL_PLANKEY	:=	' ' ;

	D_FNJISEKI.RETRIEVAL_QTY		:=	100 ;

	D_FNJISEKI.USERID				:=	'DAIFUKU' ;

	D_FNJISEKI.USERNAME				:=	'DAIFUKU' ;

	D_FNJISEKI.SYSTEMID				:=	WK_SYSTEMID ;

	D_FNJISEKI.SYOZAIKEY			:=	'1099099099' ;

	D_FNJISEKI.BACKUP_FLG			:=	'2' ;





	BEGIN
		INSERT INTO FNJISEKI
		(
			FNJISEKI.MCKEY,
			FNJISEKI.ZAIKEY,
			FNJISEKI.ZKNAME,
			FNJISEKI.ZKNAME2,
			FNJISEKI.ZKNAME3,
			FNJISEKI.SAKUSEIHIJI,
			FNJISEKI.NYUSYUKBN,
			FNJISEKI.SAGYOKBN,
			FNJISEKI.SAKUKBN,
			FNJISEKI.NYUSYUSTNO,
			FNJISEKI.TICKET_NO,
			FNJISEKI.BUCKET_NO,
			FNJISEKI.COLOR_CODE,
			FNJISEKI.NYUSYUSU,
			FNJISEKI.RETRIEVAL_NO,
			FNJISEKI.REAL_WORK_NUMBER,
			FNJISEKI.SERIAL_NO,
			FNJISEKI.ORDER_NO,
			FNJISEKI.ORDER_SERIAL_NO,
			FNJISEKI.START_DATE,
			FNJISEKI.START_TIMING_FLAG,
			FNJISEKI.COMPLETE_DATE,
			FNJISEKI.COMPLETE_TIMING_FLAG,
			FNJISEKI.DEPOT_CODE,
			FNJISEKI.SECTION,
			FNJISEKI.LINE,
			FNJISEKI.LINE_TYPE,
			FNJISEKI.CUSTOMER_CODE,
			FNJISEKI.CUSTOMER_NAME1,
			FNJISEKI.CUSTOMER_NAME2,
			FNJISEKI.PR_NO,
			FNJISEKI.RETRIEVAL_PLANKEY,
			FNJISEKI.RETRIEVAL_QTY,
			FNJISEKI.USERID,
			FNJISEKI.USERNAME,
			FNJISEKI.STARTSTNO,
			FNJISEKI.ENDSTNO,
			FNJISEKI.SYSTEMID,
			FNJISEKI.SYOZAIKEY,
			FNJISEKI.BACKUP_FLG
		)
		VALUES
		(
			D_FNJISEKI.MCKEY,
			D_FNJISEKI.ZAIKEY,
			D_FNJISEKI.ZKNAME,
			D_FNJISEKI.ZKNAME2,
			D_FNJISEKI.ZKNAME3,
			D_FNJISEKI.SAKUSEIHIJI,
			D_FNJISEKI.NYUSYUKBN,
			D_FNJISEKI.SAGYOKBN,
			D_FNJISEKI.SAKUKBN,
			D_FNJISEKI.NYUSYUSTNO,
			D_FNJISEKI.TICKET_NO,
			D_FNJISEKI.BUCKET_NO,
			D_FNJISEKI.COLOR_CODE,
			D_FNJISEKI.NYUSYUSU,
			D_FNJISEKI.RETRIEVAL_NO,
			D_FNJISEKI.REAL_WORK_NUMBER,
			D_FNJISEKI.SERIAL_NO,
			D_FNJISEKI.ORDER_NO,
			D_FNJISEKI.ORDER_SERIAL_NO,
			D_FNJISEKI.START_DATE,
			D_FNJISEKI.START_TIMING_FLAG,
			D_FNJISEKI.COMPLETE_DATE,
			D_FNJISEKI.COMPLETE_TIMING_FLAG,
			D_FNJISEKI.DEPOT_CODE,
			D_FNJISEKI.SECTION,
			D_FNJISEKI.LINE,
			D_FNJISEKI.LINE_TYPE,
			D_FNJISEKI.CUSTOMER_CODE,
			D_FNJISEKI.CUSTOMER_NAME1,
			D_FNJISEKI.CUSTOMER_NAME2,
			D_FNJISEKI.PR_NO,
			D_FNJISEKI.RETRIEVAL_PLANKEY,
			D_FNJISEKI.RETRIEVAL_QTY,
			D_FNJISEKI.USERID,
			D_FNJISEKI.USERNAME,
			D_FNJISEKI.STARTSTNO,
			D_FNJISEKI.ENDSTNO,
			D_FNJISEKI.SYSTEMID,
			D_FNJISEKI.SYOZAIKEY,
			D_FNJISEKI.BACKUP_FLG

		) ;




		IF SQL%ROWCOUNT <= 0 THEN
			IO_RC		:= FUKKI.RET_DBERR ;
			WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001, 'FNJISEKI',
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
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4002, 'FNJISEKI')),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
				WK_RC		:=	LOGOUT('03', DEF_MYNAME, '72', WK_LOGMSG) ;
				IO_MSGDAT	:= WK_LOGMSG ;


			ELSE
				IO_RC		:=	FUKKI.RET_DBERR ;
				WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG4001,
														'FNJISEKI', TO_CHAR(SQLCODE))),
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
	IO_MSGDAT		:= ' ' ;
	WK_LOGMSG		:= ' ' ;
	WK_MSGBUFF		:=	RPAD(' ', 255, ' ') ;




	WK_SYSNICHIJI	:= SYSDATE14_GET() ;






	WK_NYUSYUKBN	:=	'1' ;

	FOR WK_LOOP IN 1 .. IN_KENSU
	LOOP



		WK_SYSTEMID		:= '1' || SUBSTRB (IN_SAKUSEIHIJI, 1, 8) || SUBSTRB( TO_CHAR( WK_LOOP, '000000000' ), -9, 9 );




		MAKZAI_70 (IO_RC, WK_LOGMSG) ;
		IF IO_RC != FUKKI.RET_OK THEN
			GOTO	TJISEKI_ENDLABEL ;
		END IF ;

		IF WK_NYUSYUKBN	=	'1'		THEN
			WK_NYUSYUKBN	:=	'2' ;
		ELSE
			WK_NYUSYUKBN	:=	'1' ;
		END IF ;
	END LOOP;





<<TJISEKI_ENDLABEL>>
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

END TJISEKI ;

/
