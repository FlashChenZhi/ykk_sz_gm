DROP PROCEDURE YKK.RTASM0;

CREATE OR REPLACE PROCEDURE YKK.RTASM0
(
	IO_SCHNO		IN OUT  VARCHAR2,
	IO_RC			IN OUT	NUMBER,
	IO_MSGNO		IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
/*==============================================================================================================================
//## SCHNo preparation
||	Attribute  	: SAIBAN Information file  SYUTOKU
||
||	Name	   	:RTASM0
||
||	Date		:2007/11/1
||
||	Outline 	:SCHNo wo SAIBAN SURU
||
||	Argument	:IN		   None
||
||			  OUT
||
||			  IN OUT	IO_SCHNO		: SCHNo housing area
||						IO_RC			: Error message No housing area
||						IO_MSGNO		: Message No housing area
||						IO_MSGDAT		: Error message parameter data
||
==============================================================================================================================*/
/*==============================================================================================================================
|| PL/SQL block
==============================================================================================================================*/
/*==============================================================================================================================
|| Variable definition department
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	Fixed number declaration
	==========================================================================================================================*/
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'RTASM0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'SCHNo preparation';

	/*==========================================================================================================================
	||	Variable declaration
	==========================================================================================================================*/
	WK_SAIBANNO		FNSAIBAN.SAIBANNO%TYPE;
	WK_MSGMAKE		BOOLEAN := FALSE;
	WK_MSGBUFF		VARCHAR2(255);


/*==============================================================================================================================
|| Control part
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	Initialization
	==========================================================================================================================*/
	IO_RC		:= 0;
	IO_MSGDAT	:= NULL;
	WK_MSGMAKE	:= FALSE;
	IO_MSGNO	:= 0;

	/*======================================================================================================================
	||	SCHNo wo SYUTOKU SURU
	======================================================================================================================*/
	STASM0( IO_SCHNO, IO_RC, IO_MSGDAT );
	IF IO_RC != 0 THEN
		IO_MSGNO	:=	IO_RC	;
		IO_RC		:=	FUKKI.RET_SUBERR ;
		GOTO ENDLABEL;
	END IF;

<<ENDLABEL>>
	NULL;

	IF IO_RC != 0 THEN
		IF WK_MSGMAKE = TRUE THEN
			IF IO_MSGDAT IS NULL THEN
				IO_MSGDAT := DEF_MYNAME;
			ELSE
				IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || IO_MSGDAT;
			END IF;

		ELSE
			IF IO_MSGDAT IS NULL THEN
				IO_MSGDAT := DEF_MYNAME;
			ELSE
				IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION2 || IO_MSGDAT;
			END IF;
		END IF;
	ELSE
		IF IO_MSGDAT IS NULL THEN
			IO_MSGDAT := ' ';
		END IF;
	END IF;


	IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
	END IF;


EXCEPTION
	WHEN OTHERS THEN
		IO_RC		:= FUKKI.RET_PROERR;
		IO_MSGNO	:= 45001 ;
		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END RTASM0;
/

