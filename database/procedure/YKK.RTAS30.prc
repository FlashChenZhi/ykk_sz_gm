DROP PROCEDURE YKK.RTAS30;

CREATE OR REPLACE PROCEDURE YKK.RTAS30
		(
			IN_PATHNAME		IN		VARCHAR2,
			IN_FILENAME		IN		VARCHAR2,
			IN_MSG			IN		VARCHAR2,
			IO_RC			IN OUT	NUMBER,
			IO_MSGNO		IN OUT	NUMBER,
			IO_MSGDAT		IN OUT	VARCHAR2
		) IS
/*------------------------------------------------------------------*/
/* PROGRAM-Type	:	PROCEDURE										*/
/* PROGRAM-ID	:	RTAS30											*/
/* CREATE-DATE	:	2005/10/26										*/
/* AUTHOR		:	K.Nakazono  									*/
/* SEIBAN		:	Shenzhen Cigarette Factory						*/
/* REMARKS		:	FKICK Kidou Syori								*/
/*------------------------------------------------------------------*/
/*																	*/
/* CALLING SEQUENCE													*/
/*	RTAS30 ( IN_PATHNAME, IN_FILENAME, IN_MSG, IO_RC, 				*/
/*											IO_MSGNO, IO_MSGDAT ) ;	*/
/*																	*/
/*	Parameter:														*/
/*																	*/
/*	(INPUT)															*/
/*		VARCHAR2	IN_PATHNAME		: Path Name						*/
/*		VARCHAR2	IN_FILENAME		: File Name						*/
/*		VARCHAR2	IN_MSG			: Msg							*/
/*																	*/
/*	(OUTPUT)														*/
/*		NUMBER		IO_RC			: Return information   			*/
/*						 0	: Normal   								*/
/*						 1	: Deadlock 								*/
/*						 2	: Data not found						*/
/*						14	: Route NG								*/
/*						19	: Route unregistration					*/
/*						27	: Check Error							*/
/*						-6	: Database error						*/
/*						-7	: Subroutine error						*/
/*						-8	: Procedure error   					*/
/*		NUMBER		IO_MSGNO		: Message No.					*/
/*		VARCHAR2	IO_MSGDAT		: Error message   				*/
/*																	*/
/*------------------------------------------------------------------*/

/*------------------------------------------------------------------*/
/* Fixation item definition area   									*/
/*------------------------------------------------------------------*/
DEF_MYNAME		CONSTANT VARCHAR2(10)	:= 'RTAS30' ;

/*------------------------------------------------------------------*/
/* Work area definition   											*/
/*------------------------------------------------------------------*/
WK_MSGBUFF		VARCHAR2(255) ;
WK_LOGMSG		VARCHAR2(255) ;				-- Message Area (logout)

WK_SYSDATE		VARCHAR2(8) ;
WK_SYSNICHIJI	VARCHAR2(14) ;
WK_MSG			VARCHAR2(20) ;
WK_RC			NUMBER(9,0) ;
WK_LEN			NUMBER(9,0) ;

/*==================================================================*/
/*																	*/
/* Main Process														*/
/*																	*/
/*==================================================================*/

BEGIN
	/*--------------------------------------------------------------*/
	/* Early period value setting   								*/
	/*--------------------------------------------------------------*/
	IO_RC		:=	FUKKI.RET_OK ;
	IO_MSGDAT	:= ' ' ;
	WK_MSGBUFF	:=	RPAD(' ', 255, ' ') ;	-- Clear of the message area
	IO_MSGNO	:=	0 ;						-- Error message No. clear
	WK_LEN		:= 	0 ;						-- Message Length
	WK_MSG		:=	NULL ;					-- Message

	/*----------------------------------------------------------*/
	/* MESSAGE HENSYU              								*/
	/*----------------------------------------------------------*/
	IF IN_MSG IS NULL OR IN_MSG = SUBSTRB(PA_AWC_DEFINE.DEF_SPACE, 1, 1) THEN
		WK_LEN	:=	1 ;
		WK_MSG	:=	' ' ;
	ELSE
		WK_LEN	:=	LENGTH(IN_MSG) ;
		WK_MSG  :=  IN_MSG ;
	END IF ;

	/*----------------------------------------------------------*/
	/* FKICK KIDOU                 								*/
	/*----------------------------------------------------------*/
	WK_RC := FKICK (IN_PATHNAME, IN_FILENAME, WK_MSG, WK_LEN, IO_RC) ;
	IF IO_RC != FUKKI.RET_OK THEN
		IO_MSGNO	:=	45003 ;
		WK_LOGMSG	:=	SUBSTRB((MSGCVT(GSC_MSGDEF.GMSG5003, IN_FILENAME)),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('03', DEF_MYNAME, '01', WK_LOGMSG) ;
		IO_MSGDAT	:= IN_FILENAME ;
		GOTO RTAS30_ENDLABEL ;
	END IF ;

/*------------------------------------------------------------------*/
/* Completion processing   											*/
/*------------------------------------------------------------------*/
<<RTAS30_ENDLABEL>>
	IF IO_RC != FUKKI.RET_OK THEN
		NULL	;
	ELSE
		IO_MSGDAT	:=	' ' ;
	END IF ;

EXCEPTION
	WHEN OTHERS THEN
		-- Procedure error occurrence
		IO_RC		:=	FUKKI.RET_PROERR ;
		IO_MSGNO	:=	45001 ;
		WK_LOGMSG	:=	SUBSTRB ((MSGCVT (GSC_MSGDEF.GMSG5001, DEF_MYNAME, TO_CHAR(SQLCODE))),
												1, PA_AWC_DEFINE.DEF_MSGLENG) ;
		WK_RC		:=	LOGOUT('04', DEF_MYNAME, 'EN', WK_LOGMSG) ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) ;

END RTAS30 ;
/


