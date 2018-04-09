DROP PROCEDURE YKK.STASG0;

CREATE OR REPLACE PROCEDURE YKK.STASG0
(
	IN_NYUSYUKBN	IN		VARCHAR2,
	IO_SAGYONO		IN OUT	VARCHAR2,
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
/*==============================================================================================================================
//## SAGYO No SYUTOKU SYORI
||	Attribute  	:SAIBAN JYOUHOU Table SYUTOKU Procedure
||
||	Name	   	:STASG0
||
||	Date		:2005/10/26
||
||	Outline		:SAGYOU No wo SEISEI SURU
||
||	Argument	:IN		IN_NYUSYUKBN	: NYUSYUKO KUBUN
||														PA_AWC_DEFINE.NYUSYUKBN_NYU : NYUKO
||														PA_AWC_DEFINE.NYUSYUKBN_SYU : SYUKO
||
||			  OUT
||
||			  IN OUT	IO_SAGYONO		: Area for work No housing
||						IO_MSGDAT		: Message editing area
||						IO_RC			: Error message No housing area
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
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASG0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'SAGYO NO SYUTOKU';

	/*==========================================================================================================================
	||	Variable declaration
	==========================================================================================================================*/
	WK_SAIBANID		FNSAIBAN.SAIBANID%TYPE;
	WK_SAIBANNO		FNSAIBAN.SAIBANNO%TYPE;
	WK_MSGMAKE		BOOLEAN := FALSE;
	WK_MSGBUFF		VARCHAR2(255);


/*==============================================================================================================================
|| Control part
==============================================================================================================================*/
BEGIN
	IO_RC := 0;
	IO_MSGDAT := NULL;
	WK_MSGBUFF := NULL;
	WK_MSGMAKE := FALSE;


	IF IN_NYUSYUKBN = PA_AWC_DEFINE.NYUSYUKBN_SYU THEN
		WK_SAIBANID		:= PA_AWC_DEFINE.FNSAI_ID_SAGYO_SYU;

	ELSE
		WK_SAIBANID		:= PA_AWC_DEFINE.FNSAI_ID_SAGYO_NYU;
	END IF;

	/*==========================================================================================================================
	||	SITEI SAIBAN ID no SAIBAN wo SYUTOKU SURU
	==========================================================================================================================*/
	STASH0( WK_SAIBANID, WK_SAIBANNO, IO_RC, IO_MSGDAT );
	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;

	/*==========================================================================================================================
	||	SAGYO No wo SEISEI SURU
	==========================================================================================================================*/
	IO_SAGYONO := SUBSTRB( TO_CHAR( WK_SAIBANNO, '00000000'  ), -8, 8 );

<<ENDLABEL>>
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
		IO_RC := FUKKI.RET_PROERR;
		IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF := DEF_MYNAMEJP || 'An error occurred with a procedure.	SQLCODE:['
					  || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASG0;
/


