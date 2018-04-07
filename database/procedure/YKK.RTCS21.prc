DROP PROCEDURE YKK.RTCS21;

CREATE OR REPLACE PROCEDURE YKK.RTCS21
(
	IO_QTY			IN OUT	NUMBER,
	IO_RC			IN OUT	NUMBER,
	IO_MSGNO		IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2
)
/*==============================================================================================================================

==============================================================================================================================*/
/*==============================================================================================================================

==============================================================================================================================*/
IS
	/*==========================================================================================================================

	==========================================================================================================================*/
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'RTCS21';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Empty Location existence check';


	DEF_STTYPE_AILE CONSTANT CHAR		(  1) := '1';
	DEF_STTYPE_NORM CONSTANT CHAR		(  1) := '2';


	/*==========================================================================================================================

	==========================================================================================================================*/
	WK_AKITANA_CNT		NUMBER	(  5);
	WK_STTYPE			CHAR	(  1);
	WK_FNSTATION		FNSTATION%ROWTYPE;


	WK_DEEPKBN			BOOLEAN;







	WK_COUNT			NUMBER	(  1);

	WK_MSGMAKE			BOOLEAN := FALSE;


	WK_MSGBUFF			VARCHAR2(32767);

	STIME	NUMBER;



	/*==========================================================================================================================

	==========================================================================================================================*/



	FUNCTION	ZONE_WHEREMAKE
	(
		IN_HARDZONE		IN		VARCHAR2,
		IN_SOFTZONE		IN		VARCHAR2,
		IN_GROSSCHK_FLG IN		BOOLEAN
	) RETURN VARCHAR2
	IS
		WK_BUFF		VARCHAR2(32767);

	BEGIN
		/*======================================================================================================================

		======================================================================================================================*/
		WK_BUFF := NULL;


		/*======================================================================================================================

		======================================================================================================================*/
		IF TRUE = IN_GROSSCHK_FLG THEN
			/*==================================================================================================================

			==================================================================================================================*/
			IF IN_HARDZONE IS NOT NULL AND PA_AWC_DEFINE.HARDZONE_MINYURYOKU != IN_HARDZONE THEN
				WK_BUFF :=	WK_BUFF
						||	'AND FNAKITANA.HARDZONE IN '
						||		 '(SELECT FV_HARDZONEYUSEN.YUSENHARDZONE '
						||			 'FROM FV_HARDZONEYUSEN '
						||			 'WHERE FV_HARDZONEYUSEN.HARDZONE = '''   || IN_HARDZONE  || ''' '
						||		 ') ';

			END IF;


			/*==================================================================================================================

			==================================================================================================================*/
			WK_BUFF :=	WK_BUFF
					||	'AND FNAKITANA.SOFTZONE IN '
					||		'(SELECT FNSOFTZONEYUSEN.YUSENSOFTZONE '
					||			 'FROM FNSOFTZONEYUSEN '
					||			'WHERE FNSOFTZONEYUSEN.SOFTZONE = '''	|| IN_SOFTZONE	|| ''' '
					||		') ';


		/*======================================================================================================================

		======================================================================================================================*/
		ELSE
			WK_BUFF :=	WK_BUFF
					||	'AND FNAKITANA.HARDZONE = ''' || IN_HARDZONE  || ''' '
					||	'AND FNAKITANA.SOFTZONE = ''' || IN_SOFTZONE  || ''' ';

		END IF;


		/*======================================================================================================================

		======================================================================================================================*/
		RETURN	WK_BUFF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/
			DBMS_OUTPUT.PUT_LINE( 'An error occurred at the time of Inside function of zone condition edit.'
										|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
										|| 'SQLERRM=['	|| SQLERRM			|| ']' );

	END ZONE_WHEREMAKE;





	FUNCTION	STNO_WHEREMAKE1
	(
		IN_STNO		IN		VARCHAR2,
		IN_SAGYOBA	IN		VARCHAR2,
		IN_STTYPE	IN		VARCHAR2
	) RETURN VARCHAR2
	IS
		WK_BUFF		VARCHAR2(32767);

	BEGIN
		/*======================================================================================================================

		======================================================================================================================*/
		WK_BUFF := NULL;


		/*======================================================================================================================

		======================================================================================================================*/
		IF IN_STNO IS NOT NULL AND PA_AWC_DEFINE.STATION_MINYURYOKU != IN_STNO THEN

			IF DEF_STTYPE_AILE = IN_STTYPE THEN
				WK_BUFF :=	WK_BUFF
						||	'AND FNBANKCTL.STNO = '''	|| IN_STNO	|| ''' ';


			ELSE
				WK_BUFF :=	WK_BUFF
						||	'AND FNBANKCTL.STNO IN '
						||		'(SELECT FNRUTE.STNOTO '
						||			'FROM FNRUTE '
						||			'WHERE FNRUTE.STNOFROM = '''	|| IN_STNO	|| ''' '

						||			'AND FNRUTE.RUTEID IN '
						||			'(SELECT DISTINCT FNRUTEST.RUTEID '
						||				'FROM FNRUTEST '
						||				'MINUS '
						||				'SELECT DISTINCT FNRUTEST.RUTEID '
						||					'FROM FNRUTEST '
						||					'WHERE FNRUTEST.STATUS = ''' || PA_AWC_DEFINE.RUTE_STATUS_EMG || ''' '
						||			') '

						||		') ';
				/*==============================================================================================================





				==============================================================================================================*/

			END IF;

		/*======================================================================================================================

		======================================================================================================================*/
		ELSE
			NULL;

		END IF;


		/*======================================================================================================================

		======================================================================================================================*/
		RETURN	WK_BUFF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/
			DBMS_OUTPUT.PUT_LINE( 'An error occurred at the time of Inside function of station condition edit.'
										|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
										|| 'SQLERRM=['	|| SQLERRM			|| ']' );

	END STNO_WHEREMAKE1;









	/*==========================================================================================================================





	==========================================================================================================================*/
	FUNCTION	STNO_WHEREMAKE2
	(
		IN_NYUSYUKBN	IN		VARCHAR2,
		IN_STNO			IN		VARCHAR2,
		IN_SAGYOBA		IN		VARCHAR2,
		IN_STTYPE		IN		VARCHAR2
	) RETURN VARCHAR2
	IS
		WK_BUFF		VARCHAR2(32767);

	BEGIN
		/*======================================================================================================================

		======================================================================================================================*/
		WK_BUFF := NULL;

		/*======================================================================================================================

		======================================================================================================================*/
		IF DEF_STTYPE_AILE = IN_STTYPE THEN

			IF PA_AWC_DEFINE.NYUSYUKBN_NYU = IN_NYUSYUKBN THEN
				WK_BUFF :=	WK_BUFF

						||	'AND FNHANSO.SAKISTNO = '''   || IN_STNO  || ''' '

						||	'AND FNHANSO.MOTOSTNO IN '
						||	'( SELECT FNSTATION.STNO '
						||		'FROM FNSTATION '
						||		'WHERE FNSTATION.LAYKBN = '''	|| PA_AWC_DEFINE.AISLETYPE_INDEPENDENCE || ''' '
						||	') ';


			ELSE
				WK_BUFF :=	WK_BUFF

						||	'AND FNHANSO.MOTOSTNO = '''   || IN_STNO  || ''' '

						||	'AND FNHANSO.SAKISTNO IN '
						||	'( SELECT FNSTATION.STNO '
						||		'FROM FNSTATION '
						||		'WHERE FNSTATION.LAYKBN = '''	|| PA_AWC_DEFINE.AISLETYPE_INDEPENDENCE || ''' '
						||	') ';

			END IF;

		/*======================================================================================================================

		======================================================================================================================*/
		ELSE

			IF PA_AWC_DEFINE.NYUSYUKBN_NYU = IN_NYUSYUKBN THEN
				WK_BUFF :=	WK_BUFF
						||	'AND FNHANSO.SAKISTNO IN ';

			ELSE
				WK_BUFF :=	WK_BUFF
						||	'AND FNHANSO.MOTOSTNO IN ';

			END IF;

			/*==================================================================================================================

			==================================================================================================================*/
			IF IN_STNO IS NOT NULL AND PA_AWC_DEFINE.STATION_MINYURYOKU != IN_STNO THEN
				WK_BUFF :=	WK_BUFF
						||	'( SELECT FNRUTE.STNOTO '
						||		'FROM FNRUTE '
						||		'WHERE FNRUTE.STNOFROM = ''' || IN_STNO ||''' '
						||	') ';

			/*==================================================================================================================

			==================================================================================================================*/
			ELSE
				NULL;

			END IF;

		END IF;



		/*======================================================================================================================

		======================================================================================================================*/
		RETURN	WK_BUFF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/
			DBMS_OUTPUT.PUT_LINE( 'An error occurred at the time of Inside function2 of station condition edit.'
										|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
										|| 'SQLERRM=['	|| SQLERRM			|| ']' );

	END STNO_WHEREMAKE2;










	FUNCTION	AKITANA_WHEREMAKE
		RETURN VARCHAR2
	IS
		WK_BUFF		VARCHAR2(32767);

	BEGIN
		/*======================================================================================================================

		======================================================================================================================*/
		WK_BUFF := NULL;


		/*======================================================================================================================

		======================================================================================================================*/
		WK_BUFF :=	WK_BUFF
				||	'AND FNAKITANA.TANAFLG = '''	|| PA_AWC_DEFINE.TANAFLG_MISIYOU	|| ''' '
				||	'AND FNAKITANA.ACCESSFLG = '''	|| PA_AWC_DEFINE.ACCESSFLG_OK		|| ''' ';


		/*======================================================================================================================

		======================================================================================================================*/
		RETURN	WK_BUFF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/
			DBMS_OUTPUT.PUT_LINE( 'An error occurred at the time of Inside function of empty location condition edit.'
										|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
										|| 'SQLERRM=['	|| SQLERRM			|| ']' );

	END AKITANA_WHEREMAKE;


	/*==========================================================================================================================

	==========================================================================================================================*/



	PROCEDURE	AKITANASU_GET
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IO_AKITANA_CNT	IN OUT	NUMBER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_WARNING_EMPTY	NUMBER		(  6);




		WK_SQL_STATEMENT	VARCHAR2   (32767);
		WK_CURSOR_HANDLE	INTEGER;
		WK_COLUMN_ID		NUMBER		(  3);
		WK_EXECUTE_RC		INTEGER;



	BEGIN

STIME := DBMS_UTILITY.GET_TIME();

		/*======================================================================================================================

		======================================================================================================================*/
		/*======================================================================================================================

		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	'SELECT  '
							||		'COUNT(*) '
							||	'FROM '
							||		'FNAKITANA '
							||	'WHERE '
							||		'FNAKITANA.SOKOKBN = '''	|| IN_SOKOKBN	|| ''' '
							||	'AND FNAKITANA.TANAFLG = '''	|| PA_AWC_DEFINE.TANAFLG_MISIYOU	|| ''' '
							||	'AND FNAKITANA.ACCESSFLG = '''	|| PA_AWC_DEFINE.ACCESSFLG_OK		|| ''' '
							||	'AND FNAKITANA.BANKNO IN '
							||	'('
							||		'SELECT DISTINCT '
							||			'FNBANKCTL.BANKNO '
							||		'FROM '
							||			'FNBANKCTL '
							||		'WHERE '
							||			'FNBANKCTL.STNO IN '
							||			'( '
							||				'SELECT '
							||					'FNRUTE.STNOTO '
							||				'FROM '
							||					'FNRUTE '
							||				'WHERE '
							||					'FNRUTE.RUTEID IN '
							||					'( '
							||						'SELECT DISTINCT '
							||							'FNRUTEST.RUTEID '
							||						'FROM '
							||							'FNRUTEST '
							||						'WHERE '
							||							'FNRUTEST.STNO IN '
							||							'( '
							||								'SELECT '
							||									'FNUNIT.STNO '
							||								'FROM '
							||									'FNUNIT '
							||								'WHERE '
							||									'FNUNIT.UNITCODE = ''' || PA_AWC_DEFINE.UNITCODE_RM || ''' '
							||								'MINUS '
							||								'SELECT '
							||									'FNUNIT.STNO '
							||								'FROM '
							||									'FNUNIT '
							||								'WHERE '
							||									'FNUNIT.UNITCODE = ''' || PA_AWC_DEFINE.UNITCODE_RM || ''' '
							||								'AND FNUNIT.UNITSTAT IN '
							||								'( '
							||									' ''' || PA_AWC_DEFINE.UNITSTAT_KIRIHANASI || ''', '
							||									' ''' || PA_AWC_DEFINE.UNITSTAT_KOSYO || ''', '
							||									' ''' || PA_AWC_DEFINE.UNITSTAT_OFFLINE || ''', '
							||									' ''' || PA_AWC_DEFINE.UNITSTAT_MISETSUZOKU || ''' '
							||								') '
							||							') '
							||					') '
							||			') '
							||	') ';


DBMS_OUTPUT.PUT_LINE( '???????��I???����pSELECT?������I[' || LENGTHB(WK_SQL_STATEMENT) || ']'  );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,1 ,100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,101, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,201, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,301, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,401, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,501, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,601, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT,701, 100) );



		/*======================================================================================================================

		======================================================================================================================*/
		WK_CURSOR_HANDLE	:=	DBMS_SQL.OPEN_CURSOR;


		/*======================================================================================================================

		======================================================================================================================*/
		BEGIN
			DBMS_SQL.PARSE( WK_CURSOR_HANDLE, WK_SQL_STATEMENT, DBMS_SQL.NATIVE );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40389;

				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of Empty Location existence check.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO AKITANASU_GET_END;
		END IF;


		/*======================================================================================================================


		======================================================================================================================*/

		/*======================================================================================================================

		======================================================================================================================*/
		BEGIN
			WK_COLUMN_ID := 1;
			DBMS_SQL.DEFINE_COLUMN( WK_CURSOR_HANDLE, WK_COLUMN_ID, IO_AKITANA_CNT );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40390;

				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of Empty Location existence check.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO AKITANASU_GET_END;
		END IF;


		/*======================================================================================================================


		======================================================================================================================*/
		WK_EXECUTE_RC	:=	DBMS_SQL.EXECUTE( WK_CURSOR_HANDLE );

		/*======================================================================================================================

		======================================================================================================================*/

		IF 0 = DBMS_SQL.FETCH_ROWS( WK_CURSOR_HANDLE ) THEN
			IO_RC		:= 40391;

			IO_MSGDAT	:= NULL;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= 'A result was not chosen at the time of SELECT processing execution.';


		ELSE
			DBMS_SQL.COLUMN_VALUE( WK_CURSOR_HANDLE, 1, IO_AKITANA_CNT );

DBMS_OUTPUT.PUT_LINE( '?����������I??=[' || IO_AKITANA_CNT || ']' );
DBMS_OUTPUT.PUT_LINE( '???????��[' || ((DBMS_UTILITY.GET_TIME - STIME)*10)  || ']m?b'  );


		END IF;

		/*======================================================================================================================

		======================================================================================================================*/
		DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );


		IF 0 != IO_RC THEN
			GOTO AKITANASU_GET_END;
		END IF;


		IF 0 >= IO_AKITANA_CNT THEN
			GOTO AKITANASU_GET_END;
		END IF;


		WK_WARNING_EMPTY := 0;

		/*======================================================================================================================

		======================================================================================================================*/
		IO_AKITANA_CNT := IO_AKITANA_CNT - WK_WARNING_EMPTY;

		IF 0 > IO_AKITANA_CNT THEN
			IO_AKITANA_CNT := 0;
			DBMS_OUTPUT.PUT_LINE( '?��O?����??��I???����I???������Z?������A?��??��l?������������A?��I???O?��????������????����s?������B');
		END IF;


	<<AKITANASU_GET_END>>
		/*======================================================================================================================

		======================================================================================================================*/

		IF TRUE = DBMS_SQL.IS_OPEN( WK_CURSOR_HANDLE ) THEN
			DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );
		END IF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/

			IF TRUE = DBMS_SQL.IS_OPEN( WK_CURSOR_HANDLE ) THEN
				DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );
			END IF;

			/*==================================================================================================================

			==================================================================================================================*/
			IO_RC		:= 40001;

			IO_MSGDAT	:= DEF_MYNAME || ':AKITANASU_GET' || PA_AWC_DEFINE.MSGSECTION
						|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
						|| SQLERRM;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= DEF_MYNAME || 'An error occurred at the time of Inside procedure of Empty Location qty get.'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END AKITANASU_GET;





	PROCEDURE	HANSODATASU_GET
	(
		IN_SOFTZONE		IN		VARCHAR2,
		IN_SAGYOBA		IN		VARCHAR2,
		IN_STNO			IN		VARCHAR2,
		IN_STTYPE		IN		VARCHAR2,
		IN_DEEPKBN		IN		BOOLEAN,
		IO_HANSO_CNT	IN OUT	NUMBER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2,
		IN_GROSSCHK_FLG IN		BOOLEAN
	)
	IS

		WK_SQL_STATEMENT	VARCHAR2	(32767);
		WK_CURSOR_HANDLE	INTEGER;
		WK_COLUMN_ID		NUMBER		(  3);
		WK_EXECUTE_RC		INTEGER;



	BEGIN

STIME := DBMS_UTILITY.GET_TIME();

		/*======================================================================================================================

		======================================================================================================================*/
		/*======================================================================================================================

		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	'SELECT COUNT(*) '
							||		'FROM ('

							||			'SELECT  '
							||				'FNHANSO.HANSOKEY '
							||				'FROM FNHANSO, FNLOCAT '
							||				'WHERE FNLOCAT.SYSTEMID = FNHANSO.SYSTEMID '
							||				  'AND FNHANSO.NYUSYUKBN = '''	|| PA_AWC_DEFINE.NYUSYUKBN_NYU	|| ''' ';


		/*======================================================================================================================





		======================================================================================================================*/
		WK_SQL_STATEMENT := WK_SQL_STATEMENT || STNO_WHEREMAKE2( PA_AWC_DEFINE.NYUSYUKBN_NYU, IN_STNO, IN_SAGYOBA, IN_STTYPE );


		/*======================================================================================================================

		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT

							||	'AND SUBSTR( FNLOCAT.SYOZAIKEY, 2, 1 ) = '' '' '
							||	'AND FNLOCAT.ZAIJYOFLG != ''' || PA_AWC_DEFINE.ZAIJYOFLG_ST   || ''' '


							||	'AND FNHANSO.SOFTZONE IN '
							||		'(SELECT FNSOFTZONEYUSEN.YUSENSOFTZONE '
							||			 'FROM FNSOFTZONEYUSEN '
							||			'WHERE FNSOFTZONEYUSEN.SOFTZONE = '''	|| IN_SOFTZONE	|| ''' '
							||		') ';


		/*======================================================================================================================


		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
							||	'UNION ALL '
							||	'SELECT  '
							||		'FNHANSO.HANSOKEY '
							||		'FROM FNHANSO, FNLOCAT '
							||		'WHERE FNHANSO.SYSTEMID = FNLOCAT.SYSTEMID '

							||		  'AND FNHANSO.NYUSYUKBN = ''' || PA_AWC_DEFINE.NYUSYUKBN_SYU || ''' '

							||		  'AND ( FNHANSO.SIJISYOSAI = ''' || PA_AWC_DEFINE.SIJISYOSAI_TUMI || ''' '
							||		   'OR	 FNHANSO.SIJISYOSAI = ''' || PA_AWC_DEFINE.SIJISYOSAI_PIC  || ''' ) '

							||		  'AND ( FNHANSO.HJYOTAIFLG = ''' || PA_AWC_DEFINE.HJYOTAIFLG_KANRYO || ''' '
							||		   'OR	 FNHANSO.HJYOTAIFLG = ''' || PA_AWC_DEFINE.HJYOTAIFLG_TOUCHAKU || ''' ) ';





		/*======================================================================================================================

		======================================================================================================================*/
		WK_SQL_STATEMENT := WK_SQL_STATEMENT
							||	'AND FNHANSO.SOFTZONE IN '
							||		'(SELECT FNSOFTZONEYUSEN.YUSENSOFTZONE '
							||			 'FROM FNSOFTZONEYUSEN '
							||			'WHERE FNSOFTZONEYUSEN.SOFTZONE = ''' || IN_SOFTZONE  || ''' '
							||		') ';





		/*======================================================================================================================





		======================================================================================================================*/
		WK_SQL_STATEMENT := WK_SQL_STATEMENT || STNO_WHEREMAKE2( PA_AWC_DEFINE.NYUSYUKBN_SYU, IN_STNO, IN_SAGYOBA, IN_STTYPE );


		/*======================================================================================================================

		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
							||		') A ';

DBMS_OUTPUT.PUT_LINE('???????��I?��??����??f?[?^???������ISQL?������I[' || LENGTHB(WK_SQL_STATEMENT) || ']' );
DBMS_OUTPUT.PUT_LINE( WK_SQL_STATEMENT );



		/*======================================================================================================================

		======================================================================================================================*/
		WK_CURSOR_HANDLE	:=	DBMS_SQL.OPEN_CURSOR;


		/*======================================================================================================================

		======================================================================================================================*/
		BEGIN
			DBMS_SQL.PARSE( WK_CURSOR_HANDLE, WK_SQL_STATEMENT, DBMS_SQL.NATIVE );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40392;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred the number acquisition of undecided conveyance data.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO HANSODATASU_GET_END;
		END IF;


		/*======================================================================================================================


		======================================================================================================================*/

		/*======================================================================================================================

		======================================================================================================================*/
		BEGIN
			WK_COLUMN_ID := 1;
			DBMS_SQL.DEFINE_COLUMN( WK_CURSOR_HANDLE, WK_COLUMN_ID, IO_HANSO_CNT );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40393;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred the number acquisition of undecided conveyance data.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO HANSODATASU_GET_END;
		END IF;


		/*======================================================================================================================


		======================================================================================================================*/
		WK_EXECUTE_RC	:=	DBMS_SQL.EXECUTE( WK_CURSOR_HANDLE );

		/*======================================================================================================================

		======================================================================================================================*/

		IF 0 = DBMS_SQL.FETCH_ROWS( WK_CURSOR_HANDLE ) THEN
			IO_RC		:= 40394;

			IO_MSGDAT	:= NULL;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= 'A result was not chosen at the time of SELECT processing execution.';


		ELSE
			DBMS_SQL.COLUMN_VALUE( WK_CURSOR_HANDLE, 1, IO_HANSO_CNT );

		END IF;

		/*======================================================================================================================

		======================================================================================================================*/
		DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );



		IF 0 != IO_RC THEN
			GOTO HANSODATASU_GET_END;
		END IF;


	<<HANSODATASU_GET_END>>

		IF TRUE = DBMS_SQL.IS_OPEN( WK_CURSOR_HANDLE ) THEN
			DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );
		END IF;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/

			IF TRUE = DBMS_SQL.IS_OPEN( WK_CURSOR_HANDLE ) THEN
				DBMS_SQL.CLOSE_CURSOR( WK_CURSOR_HANDLE );
			END IF;

			/*==================================================================================================================

			==================================================================================================================*/
			IO_RC		:= 40001;

			IO_MSGDAT	:= DEF_MYNAME || ':HANSODATASU_GET' || PA_AWC_DEFINE.MSGSECTION
						|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
						|| SQLERRM;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= DEF_MYNAME || 'An error occurred  the number acquisition processing of conveyance data .'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END HANSODATASU_GET;




	PROCEDURE	AKIZAN_GET
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IO_AKIZAN_CNT	IN OUT	NUMBER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_AKITANA_CNT		NUMBER	(  6);
		WK_HANSO_CNT		NUMBER	(  6);
		WK_KARIOKI_CNT		NUMBER	(  6);

	BEGIN
		/*======================================================================================================================

		======================================================================================================================*/
		AKITANASU_GET( IN_SOKOKBN, WK_AKITANA_CNT, IO_RC, IO_MSGDAT );
		IF 0 != IO_RC THEN
			GOTO AKIZAN_GET_END;
		END IF;

		/*======================================================================================================================

		======================================================================================================================*/

















		/* ?��p???m?��������??����I?��??����??f?[?^?��������A?����`?F?b?N?��??��w?��n?[?h?]?[???����??f?[?^?��i?��������������		   ?s?\?������A?��I?��??����??f?[?^?????������������B */

			WK_HANSO_CNT := 0;




		WK_KARIOKI_CNT := 0;

		/*======================================================================================================================


		======================================================================================================================*/
		IO_AKIZAN_CNT := WK_AKITANA_CNT - WK_HANSO_CNT - WK_KARIOKI_CNT;

DBMS_OUTPUT.PUT_LINE('???????��I?c??=[' || IO_AKIZAN_CNT || ']' );



	<<AKIZAN_GET_END>>
		/*======================================================================================================================

		======================================================================================================================*/
		NULL;


	/*==========================================================================================================================

	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			/*==================================================================================================================

			==================================================================================================================*/

			/*==================================================================================================================

			==================================================================================================================*/
			IO_RC		:= 40001;

			IO_MSGDAT	:= DEF_MYNAME || ':AKIZAN_GET' || PA_AWC_DEFINE.MSGSECTION
						|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
						|| SQLERRM;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= DEF_MYNAME || 'An error occurred  the number acquisition processing of Empty location  .'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END AKIZAN_GET;


/*==============================================================================================================================

==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================

	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred. '
				   || 'IO_RC=[' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;

	/*==========================================================================================================================

	==========================================================================================================================*/
	IO_RC		:= 0;
	IO_QTY		:= 0;
	IO_MSGDAT	:= ' ';
	WK_MSGMAKE	:= FALSE;

	/*==========================================================================================================================

	==========================================================================================================================*/
	WK_STTYPE := DEF_STTYPE_NORM;

	WK_DEEPKBN := FALSE;

	/*==========================================================================================================================

	==========================================================================================================================*/

DBMS_OUTPUT.PUT_LINE('????????????????AKIZAN_GET(?w?��p?????[?^?��f?����`?F?b?N)');

	AKIZAN_GET( PA_AWC_DEFINE.SOKOKBN_DEFAULT, IO_QTY, IO_RC, IO_MSGDAT);


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


<<ENDLABEL>>
	/*==========================================================================================================================

	==========================================================================================================================*/


	/*==========================================================================================================================

	==========================================================================================================================*/
	IF IO_RC != 0 THEN

		IF WK_MSGMAKE = TRUE THEN

			IF IO_MSGDAT IS NULL THEN
				IO_MSGDAT := DEF_MYNAME;

			ELSE
				IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || IO_MSGDAT;
			END IF;

		/* ?������A?����\?[?X?����??b?Z?[?W?����W?����������i?����o?����T?u???[?`???��??b?Z?[?W?��������������j?������t?@?C??
		   ?������������??��n?C?t???������B */
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

	IO_MSGNO := IO_RC ;

	/*==========================================================================================================================

	==========================================================================================================================*/
	IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
	END IF;


/*==============================================================================================================================

==============================================================================================================================*/
EXCEPTION
	/*==========================================================================================================================

	==========================================================================================================================*/

	/*==========================================================================================================================

	==========================================================================================================================*/

	WHEN OTHERS THEN
		/*======================================================================================================================

		======================================================================================================================*/

		/*======================================================================================================================

		======================================================================================================================*/
		IO_RC		:= 40001;

		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END RTCS21;
/

