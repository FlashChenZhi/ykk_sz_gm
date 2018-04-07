DROP PROCEDURE YKK.STAS80;

CREATE OR REPLACE PROCEDURE YKK.STAS80
(
	IN_SOKOKBN		IN		VARCHAR2,
	IN_BANKORDER	IN		NUMBER,
	IN_HARDZONE		IN		VARCHAR2,
	IN_SOFTZONE		IN		VARCHAR2,
	IN_AILESTNO		IN		VARCHAR2,
	IN_AKIKENKBN	IN		VARCHAR2,
	IO_SYOZAIKEY	IN OUT	VARCHAR2,
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2,
	IN_TANAKANFLG	IN		BOOLEAN :=	FALSE
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
	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STAS80';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'Search Vacant Location';


	/*==========================================================================================================================
	||	???R?[?h?^?£¿£¿
	==========================================================================================================================*/

	TYPE EMP_GET_REC IS RECORD
	(
		FNAKITANA_SYOZAIKEY   FNAKITANA.SYOZAIKEY%TYPE
	);
	DATA_EMP_GET	EMP_GET_REC;


	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/
	WK_CUR_HANDLE	INTEGER;
	EXECUTE_RC		INTEGER;
	WK_DEEPKBN		FNBANKCTL.DEEPKBN%TYPE;

	WK_MSGMAKE		BOOLEAN := FALSE;


	WK_MSGBUFF		VARCHAR2(32767);


	/*==========================================================================================================================
	||	?£¿??v???V?[?W???£¿£¿
	==========================================================================================================================*/



	PROCEDURE	EMPSEARCH_CURSOLMAKE
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IN_BANKORDER	IN		NUMBER,
		IN_HARDZONE		IN		VARCHAR2,
		IN_SOFTZONE		IN		VARCHAR2,
		IN_AILESTNO		IN		VARCHAR2,
		IN_AKIKENKBN	IN		VARCHAR2,
		IO_CUR_HANDLE	IN OUT	INTEGER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_SQL_STATEMENT	VARCHAR2	(32000);
		WK_COLUMN_ID		NUMBER		  (  3);
		WK_COLUMN_LEN		NUMBER		  (  3);

	BEGIN
		/*======================================================================================================================
		||	?£¿I???£¿pSELECT?£¿£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	'SELECT FNAKITANA.SYOZAIKEY '
							||		'FROM FNAKITANA '
							||		'WHERE FNAKITANA.SOKOKBN = :B_SOKOKBN '
							||		  'AND FNAKITANA.HARDZONE = :B_HARDZONE '
							||		  'AND FNAKITANA.SOFTZONE = :B_SOFTZONE '
							||		  'AND FNAKITANA.TANAFLG = '	|| PA_AWC_DEFINE.TANAFLG_MISIYOU
							||		  'AND FNAKITANA.ACCESSFLG = '	|| PA_AWC_DEFINE.ACCESSFLG_OK;


		/*======================================================================================================================
		||	?A?C???X?e?[?V????No?£¿w?£¿£¿??£¿i?A?C???£¿£¿j?£¿??A?£¿??£¿u?Y???o???N?£¿£¿v?£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		IF IN_AILESTNO IS NOT NULL AND PA_AWC_DEFINE.STATION_MINYURYOKU != IN_AILESTNO THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'AND FNAKITANA.BANKNO IN '
								||		'(SELECT UNIQUE FNBANKCTL.BANKNO '
								||			'FROM FNBANKCTL '
								||			'WHERE FNBANKCTL.STNO = :B_AILESTNO ';
			IF IN_BANKORDER IS NOT NULL AND  IN_BANKORDER != 0 THEN
				WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||			'AND FNBANKCTL.BANKORDER = :B_BANKORDER '
								||		') ';
			ELSE
				WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||		') ';
			END IF	;

		END IF;

		/*======================================================================================================================
		||	?£¿I???£¿p?^?[???£¿]?£¿A?\?[?g???£¿£¿W?£¿£¿B
		======================================================================================================================*/





















		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.BAYNO, FNAKITANA.LEVELNO, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.BAYNO, FNAKITANA.LEVELNO DESC, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.BAYNO DESC, FNAKITANA.LEVELNO, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.BAYNO DESC, FNAKITANA.LEVELNO DESC, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.LEVELNO, FNAKITANA.BAYNO, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.LEVELNO, FNAKITANA.BAYNO DESC, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.LEVELNO DESC, FNAKITANA.BAYNO, FNAKITANA.BANKNO ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY FNAKITANA.LEVELNO DESC, FNAKITANA.BAYNO DESC, FNAKITANA.BANKNO ';


		END IF;

DBMS_OUTPUT.PUT_LINE( 'AKITANA KENSAKU CURSOR SAKUSEI [' || LENGTHB(WK_SQL_STATEMENT) || ']' );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT, 1, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT, 101, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT, 201, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT, 301, 100) );
DBMS_OUTPUT.PUT_LINE( SUBSTRB(WK_SQL_STATEMENT, 401, 100) );



		/*======================================================================================================================
		||	?£¿ISQL?p?n???h???£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		IO_CUR_HANDLE	:=	DBMS_SQL.OPEN_CURSOR;


		/*======================================================================================================================
		||	?£¿£¿£¿£¿ELECT?£¿£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		BEGIN
			DBMS_SQL.PARSE( IO_CUR_HANDLE, WK_SQL_STATEMENT, DBMS_SQL.NATIVE );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40316;

				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of SELECT existence check.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE;
		END IF;


		/*======================================================================================================================
		||	?£¿I???£¿ELECT?£¿£¿o?C???h?£¿??£¿o?C???h?£¿£¿B
		======================================================================================================================*/
		DBMS_SQL.BIND_VARIABLE( IO_CUR_HANDLE,	':B_SOKOKBN',	IN_SOKOKBN	);
		DBMS_SQL.BIND_VARIABLE( IO_CUR_HANDLE,	':B_HARDZONE',	IN_HARDZONE   );
		DBMS_SQL.BIND_VARIABLE( IO_CUR_HANDLE,	':B_SOFTZONE',	IN_SOFTZONE   );

		IF IN_AILESTNO IS NOT NULL AND PA_AWC_DEFINE.STATION_MINYURYOKU != IN_AILESTNO THEN
			DBMS_SQL.BIND_VARIABLE( IO_CUR_HANDLE,	':B_AILESTNO',	IN_AILESTNO   );
			IF IN_BANKORDER IS NOT NULL AND IN_BANKORDER != 0 THEN
				DBMS_SQL.BIND_VARIABLE( IO_CUR_HANDLE,	':B_BANKORDER',	IN_BANKORDER   );
			END IF	;
		END IF;


		/*======================================================================================================================
		||	?£¿I???£¿ELECT?£¿£¿£¿£¿£¿£¿??£¿Z?b?g?£¿£¿£¿£¿`?£¿£¿B
		======================================================================================================================*/
		BEGIN
			WK_COLUMN_ID := 1;
			SELECT USER_TAB_COLUMNS.DATA_LENGTH
				INTO WK_COLUMN_LEN
				FROM	USER_TAB_COLUMNS
				WHERE	USER_TAB_COLUMNS.TABLE_NAME		=	'FNAKITANA'
				  AND	USER_TAB_COLUMNS.COLUMN_NAME	=	'SYOZAIKEY';
			DBMS_SQL.DEFINE_COLUMN( IO_CUR_HANDLE, WK_COLUMN_ID, DATA_EMP_GET.FNAKITANA_SYOZAIKEY, WK_COLUMN_LEN );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40317;

				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A result was not chosen at the time of SELECT processing execution.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE;
		END IF;


	<<ENDEMPSEARCH_CURSOLMAKE>>
		NULL;

	/*==========================================================================================================================
	||	?£¿O?n???h??
	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			IO_RC		:= 40318;

			IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= 'An error occurred at the time of Inside function of Cursor edit.'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END EMPSEARCH_CURSOLMAKE;





	PROCEDURE	EMPSEARCH_CURSOLMAKE2
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IN_HARDZONE		IN		VARCHAR2,
		IN_SOFTZONE		IN		VARCHAR2,
		IN_AILESTNO		IN		VARCHAR2,
		IN_AKIKENKBN	IN		VARCHAR2,
		IO_CUR_HANDLE	IN OUT	INTEGER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_SQL_STATEMENT	VARCHAR2	(32000);
		WK_COLUMN_ID		NUMBER		  (  3);
		WK_COLUMN_LEN		NUMBER		  (  3);
		WK_SYOZAIKEY_OKU	FNLOCAT.SYOZAIKEY%TYPE;
		WK_SYOZAIKEY_TEMAE	FNLOCAT.SYOZAIKEY%TYPE;

	BEGIN
		/*======================================================================================================================
		||	?w?£¿]?[???£¿£¿u?£¿y?A?£¿??£¿£¿£¿B
		======================================================================================================================*/
		BEGIN
			SELECT TEMAE.SYOZAIKEY, OKU.SYOZAIKEY
				INTO WK_SYOZAIKEY_TEMAE, WK_SYOZAIKEY_OKU
				FROM	FNAKITANA TEMAE,
						(SELECT MAX(FNAKITANA.SYOZAIKEY) AS SYOZAIKEY
							FROM	FNBANKCTL, FNAKITANA
							WHERE	FNBANKCTL.BANKNO	=	FNAKITANA.BANKNO
							  AND	FNAKITANA.SOKOKBN	=	IN_SOKOKBN
							  AND	FNAKITANA.HARDZONE	=	IN_HARDZONE
							  AND	FNAKITANA.SOFTZONE	=	IN_SOFTZONE
							  AND	FNAKITANA.TANAFLG	=	PA_AWC_DEFINE.TANAFLG_MISIYOU
							  AND	FNAKITANA.ACCESSFLG =	PA_AWC_DEFINE.ACCESSFLG_OK
							  AND	FNBANKCTL.DEEPKBN	=	PA_AWC_DEFINE.DEEPKBN_OKU
							  AND	(FNAKITANA.SOKOKBN, FNBANKCTL.PAREBANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO) IN
									(SELECT FNAKITANA.SOKOKBN, FNAKITANA.BANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO
										FROM	FNAKITANA
										WHERE	TANAFLG		=	PA_AWC_DEFINE.TANAFLG_MISIYOU
										  AND	ACCESSFLG	=	PA_AWC_DEFINE.ACCESSFLG_OK
									)
							  AND	FNBANKCTL.STNO		= IN_AILESTNO
						) OKU
				WHERE	TEMAE.SOKOKBN	=	SUBSTR( OKU.SYOZAIKEY, 1, 1 )
				  AND	TEMAE.BANKNO	=	( SELECT FNBANKCTL.PAREBANKNO
													FROM	FNBANKCTL
													WHERE	FNBANKCTL.SOKOKBN	=	SUBSTR( OKU.SYOZAIKEY, 1, 1 )
													  AND	FNBANKCTL.BANKNO	=	TO_NUMBER( SUBSTR( OKU.SYOZAIKEY, 2, 3 ))
												)
				  AND	TEMAE.BAYNO		=	SUBSTR( OKU.SYOZAIKEY, 5, 3 )
				  AND	TEMAE.LEVELNO	=	SUBSTR( OKU.SYOZAIKEY, 8, 3 );


		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					DBMS_OUTPUT.PUT_LINE( '?£¿u?£¿y?A?I?£¿£¿£¿£¿£¿£¿£¿B' );
					WK_SYOZAIKEY_OKU	:=	NULL;
					WK_SYOZAIKEY_TEMAE	:=	NULL;


				ELSE
					IO_RC		:= 40353;

					IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
								|| SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred at the time of  shelf acquisition.'
								|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
								|| 'SQLERRM=['	|| SQLERRM			|| ']';
				END IF;
		END;


		IF 0 != IO_RC THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE2;
		END IF;

DBMS_OUTPUT.PUT_LINE('?£¿£¿£¿u?£¿??I?mo??['		|| WK_SYOZAIKEY_OKU		|| ']');
DBMS_OUTPUT.PUT_LINE('?£¿£¿£¿u?£¿£¿O?I?mo??['	|| WK_SYOZAIKEY_TEMAE	|| ']');



		/*==========================================================================================================================
		||	?£¿£¿£¿y?A?£¿£¿u?£¿I?£¿£¿£¿£¿£¿£¿£¿£¿£¿??A?£¿O?I?£¿£¿£¿????D?£¿??£¿£¿£¿I?Q?£¿£¿£¿u?£¿I?£¿£¿£¿B
		==========================================================================================================================*/
		IF WK_SYOZAIKEY_TEMAE IS NULL THEN
			BEGIN
				SELECT EMP1.SYOZAIKEY, EMP2.SYOZAIKEY
					INTO WK_SYOZAIKEY_TEMAE, WK_SYOZAIKEY_OKU
					FROM	FNAKITANA EMP1, FNBANKCTL,
							 (SELECT MAX(FNAKITANA.SYOZAIKEY) AS SYOZAIKEY
								FROM  FNBANKCTL, FNAKITANA
								WHERE	FNBANKCTL.SOKOKBN	= FNAKITANA.SOKOKBN
								  AND	FNBANKCTL.BANKNO	= FNAKITANA.BANKNO
								  AND	FNAKITANA.SOKOKBN	= IN_SOKOKBN
								  AND	FNAKITANA.HARDZONE	= IN_HARDZONE
								  AND	FNAKITANA.SOFTZONE	= IN_SOFTZONE
								  AND	FNAKITANA.TANAFLG	= PA_AWC_DEFINE.TANAFLG_MISIYOU
								  AND	FNAKITANA.ACCESSFLG = PA_AWC_DEFINE.ACCESSFLG_OK
								  AND	FNBANKCTL.DEEPKBN	= PA_AWC_DEFINE.DEEPKBN_TEMAE
								  AND	FNBANKCTL.STNO		= IN_AILESTNO
							 ) EMP2
				WHERE	FNBANKCTL.SOKOKBN	=	EMP1.SOKOKBN
				  AND	FNBANKCTL.BANKNO	=	EMP1.BANKNO
				  AND	EMP1.SOKOKBN		=	IN_SOKOKBN
				  AND	EMP1.HARDZONE		=	IN_HARDZONE
				  AND	EMP1.SOFTZONE		=	IN_SOFTZONE
				  AND	EMP1.TANAFLG		=	PA_AWC_DEFINE.TANAFLG_MISIYOU
				  AND	EMP1.ACCESSFLG		=	PA_AWC_DEFINE.ACCESSFLG_OK
				  AND	FNBANKCTL.DEEPKBN	=	PA_AWC_DEFINE.DEEPKBN_TEMAE
				  AND	EMP1.SYOZAIKEY		<	EMP2.SYOZAIKEY
				  AND	FNBANKCTL.STNO		=	IN_AILESTNO
				  AND	ROWNUM = 1;


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						WK_MSGBUFF	:= 'Empty location does not exist. Search is stopped.';
						WK_SYOZAIKEY_OKU	:=	NULL;
						WK_SYOZAIKEY_TEMAE	:=	NULL;


					ELSE
						IO_RC		:= 40354;

						IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
									|| SQLERRM;
						WK_MSGMAKE	:= TRUE;
						WK_MSGBUFF	:= 'An error occurred at the time of temporary rack search.'
									|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
									|| 'SQLERRM=['	|| SQLERRM			|| ']';
					END IF;
			END;


			IF 0 != IO_RC THEN
				GOTO ENDEMPSEARCH_CURSOLMAKE2;
			END IF;

DBMS_OUTPUT.PUT_LINE('?£¿£¿£¿u?£¿£¿£¿P??['	|| WK_SYOZAIKEY_OKU		|| ']');
DBMS_OUTPUT.PUT_LINE('?£¿£¿£¿u?£¿£¿£¿Q??['	|| WK_SYOZAIKEY_TEMAE	|| ']');


		END IF;


		/*==========================================================================================================================
		||	?_?u???f?B?[?v?£¿I???£¿pSELECT?£¿£¿£¿£¿£¿£¿B
		==========================================================================================================================*/
		BEGIN
		WK_SQL_STATEMENT := 'SELECT A.SYOZAIKEY '
						 ||		'FROM ('
						 ||		'SELECT FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, '
						 ||			'FNAKITANA.BAYNO, FNAKITANA.BAYNO AS BAYORDER, '
						 ||			'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO AS LEVELORDER, '
						 ||			'FNAKITANA.SYOZAIKEY '
						 ||			'FROM FNAKITANA '
						 ||			'WHERE FNAKITANA.SOKOKBN = '''	 || IN_SOKOKBN					  || ''' '
						 ||			  'AND FNAKITANA.HARDZONE = '''  || IN_HARDZONE					  || ''' '
						 ||			  'AND FNAKITANA.SOFTZONE = '''  || IN_SOFTZONE					  || ''' '
						 ||			  'AND FNAKITANA.TANAFLG = '''	 || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||			  'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	  || ''' '
						 ||			  'AND FNAKITANA.BANKNO IN (SELECT FNBANKCTL.BANKNO '
						 ||											'FROM FNBANKCTL '
						 ||											'WHERE FNBANKCTL.STNO = ''' || IN_AILESTNO || ''' '
						 ||									  ') '

						 ||		'MINUS '
						 ||		'SELECT FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, '
						 ||			   'FNAKITANA.BAYNO, FNAKITANA.BAYNO AS BAYORDER, '
						 ||			   'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO AS LEVELORDER, '
						 ||			   'FNAKITANA.SYOZAIKEY '
						 ||			'FROM FNAKITANA '
						 ||			'WHERE SYOZAIKEY IN(''' || WK_SYOZAIKEY_OKU || ''',''' || WK_SYOZAIKEY_TEMAE || ''') '

						 ||		'MINUS '
						 ||		'SELECT FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, '
						 ||			   'FNAKITANA.BAYNO, FNAKITANA.BAYNO AS BAYORDER, '
						 ||			   'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO AS LEVELORDER, '
						 ||			   'FNAKITANA.SYOZAIKEY '
						 ||			'FROM FNBANKCTL, FNAKITANA '
						 ||			'WHERE FNBANKCTL.BANKNO = FNAKITANA.BANKNO '
						 ||			  'AND FNAKITANA.SOKOKBN = '''	 || IN_SOKOKBN					  || ''' '
						 ||			  'AND FNAKITANA.HARDZONE = '''  || IN_HARDZONE					  || ''' '
						 ||			  'AND FNAKITANA.SOFTZONE = '''  || IN_SOFTZONE					  || ''' '
						 ||			  'AND FNAKITANA.TANAFLG = '''	 || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||			  'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	  || ''' '
						 ||			  'AND FNBANKCTL.DEEPKBN = '''	 || PA_AWC_DEFINE.DEEPKBN_OKU	  || ''' '
						 ||			  'AND (FNAKITANA.SOKOKBN, FNBANKCTL.PAREBANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO) IN '
						 ||				  '(SELECT FNAKITANA.SOKOKBN, FNAKITANA.BANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO '
						 ||						'FROM FNAKITANA '
						 ||						'WHERE FNAKITANA.SOKOKBN = '''	  || IN_SOKOKBN					   || ''' '
						 ||						  'AND FNAKITANA.HARDZONE = '''   || IN_HARDZONE				   || ''' '
						 ||						  'AND FNAKITANA.SOFTZONE = '''   || IN_SOFTZONE				   || ''' '
						 ||						  'AND (FNAKITANA.TANAFLG != '''  || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||						   'OR FNAKITANA.ACCESSFLG != ''' || PA_AWC_DEFINE.ACCESSFLG_OK    || ''') '
						 ||				  ') '

						 ||		'MINUS '


						 ||		'SELECT  '
						 ||			'FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, '

						 ||			   'FNAKITANA.BAYNO, FNAKITANA.BAYNO AS BAYORDER, '
						 ||			   'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO AS LEVELORDER, '
						 ||			   'FNAKITANA.SYOZAIKEY '
						 ||			'FROM FNBANKCTL, FNAKITANA '
						 ||			'WHERE FNBANKCTL.BANKNO = FNAKITANA.BANKNO '
						 ||			  'AND FNAKITANA.SOKOKBN = '''	 || IN_SOKOKBN					  || ''' '
						 ||			  'AND FNAKITANA.HARDZONE = '''  || IN_HARDZONE					  || ''' '
						 ||			  'AND FNAKITANA.SOFTZONE = '''  || IN_SOFTZONE					  || ''' '
						 ||			  'AND FNAKITANA.TANAFLG = '''	 || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||			  'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	  || ''' '
						 ||			  'AND FNBANKCTL.DEEPKBN = '''	 || PA_AWC_DEFINE.DEEPKBN_TEMAE   || ''' '
						 ||			  'AND (FNAKITANA.SOKOKBN, FNBANKCTL.PAREBANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO) IN '
						 ||				  '(SELECT FNAKITANA.SOKOKBN, FNAKITANA.BANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO '
						 ||						'FROM FNHANSO, FNLOCAT, FNAKITANA '
						 ||						'WHERE FNHANSO.SYSTEMID = FNLOCAT.SYSTEMID '



						 ||						  'AND (( FNLOCAT.SYOZAIKEY = FNAKITANA.SYOZAIKEY '
						 ||						  'AND FNHANSO.NYUSYUKBN = '''	|| PA_AWC_DEFINE.NYUSYUKBN_SYU || ''') '
						 ||						  'OR ( FNHANSO.SAKITANANO = FNAKITANA.SYOZAIKEY '
						 ||						  'AND FNHANSO.NYUSYUKBN = '''	|| PA_AWC_DEFINE.NYUSYUKBN_TANA || ''')) '

						 ||				  ') '
						 ||			  'AND FNBANKCTL.STNO = ''' || IN_AILESTNO || ''' '


						 ||		'UNION ALL '


						 ||		'SELECT  '
						 ||			'FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, ';



		/*======================================================================================================================
		||	?£¿I???£¿p?^?[???£¿]?£¿£¿A?x?C?I?[?_?[?A???x???I?[?_?[?£¿£¿£¿H?£¿??£¿AORDER BY ?£¿o?£¿£¿£¿??I?£¿D?£¿??£¿??£¿£¿B
		======================================================================================================================*/





















		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN	OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP	OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.BAYNO, FNAKITANA.BAYNO+FNSOKO.EBAY AS BAYORDER, ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.BAYNO, FNAKITANA.BAYNO-FNSOKO.EBAY AS BAYORDER, ';

		END IF;


		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN	 OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO+FNSOKO.ELEVEL AS LEVELORDER, ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO-FNSOKO.ELEVEL AS LEVELORDER, ';


		END IF;


		/*======================================================================================================================
		||	?£¿£¿??£¿£¿W
		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
						 ||			   'FNAKITANA.SYOZAIKEY '
						 ||			'FROM FNSOKO, FNBANKCTL, FNAKITANA '
						 ||			'WHERE FNSOKO.SOKOKBN = FNAKITANA.SOKOKBN '
						 ||			  'AND FNBANKCTL.BANKNO = FNAKITANA.BANKNO '
						 ||			  'AND FNAKITANA.SOKOKBN = '''	 || IN_SOKOKBN					  || ''' '
						 ||			  'AND FNAKITANA.HARDZONE = '''  || IN_HARDZONE					  || ''' '
						 ||			  'AND FNAKITANA.SOFTZONE = '''  || IN_SOFTZONE					  || ''' '
						 ||			  'AND FNAKITANA.TANAFLG = '''	 || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||			  'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	  || ''' '
						 ||			  'AND FNBANKCTL.DEEPKBN = '''	 || PA_AWC_DEFINE.DEEPKBN_TEMAE   || ''' '
						 ||			  'AND (FNAKITANA.SOKOKBN, FNBANKCTL.PAREBANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO) IN '
						 ||				  '(SELECT FNAKITANA.SOKOKBN, FNAKITANA.BANKNO, FNAKITANA.BAYNO, FNAKITANA.LEVELNO '
						 ||					  'FROM FNHANSO, FNLOCAT, FNAKITANA '
						 ||					  'WHERE FNHANSO.SYSTEMID = FNLOCAT.SYSTEMID '



						 ||						  'AND (( FNLOCAT.SYOZAIKEY = FNAKITANA.SYOZAIKEY '
						 ||						  'AND FNHANSO.NYUSYUKBN = '''	|| PA_AWC_DEFINE.NYUSYUKBN_SYU || ''') '
						 ||						  'OR ( FNHANSO.SAKITANANO = FNAKITANA.SYOZAIKEY '
						 ||						  'AND FNHANSO.NYUSYUKBN = '''	|| PA_AWC_DEFINE.NYUSYUKBN_TANA || ''')) '

						 ||				') '
						 ||			  'AND FNBANKCTL.STNO = ''' || IN_AILESTNO || ''' '
						 ||		') A, '

						 ||		'FNBANKCTL '
						 ||			'WHERE A.BANKNO = FNBANKCTL.BANKNO ';

		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40355;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
							|| SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of Inside function of SELECT edit.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;

		IF 0 != IO_RC THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE2;
		END IF;


		/*======================================================================================================================
		||	?£¿I???£¿p?^?[???£¿]?£¿A?\?[?g???£¿£¿W?£¿£¿B
		======================================================================================================================*/





















		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER, A.LEVELORDER, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER, A.LEVELORDER DESC, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER DESC, A.LEVELORDER, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER DESC, A.LEVELORDER DESC, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER, A.BAYORDER, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER, A.BAYORDER DESC, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER DESC, A.BAYORDER, FNBANKCTL.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER DESC, A.BAYORDER DESC, FNBANKCTL.BANKORDER ';


		END IF;

DBMS_OUTPUT.PUT_LINE( '???????_?u???f?B?[?v?£¿I???£¿J?[?\???£¿£¿I??????[' || LENGTHB(WK_SQL_STATEMENT) || ']' );
DBMS_OUTPUT.PUT_LINE( WK_SQL_STATEMENT );



		/*======================================================================================================================
		||	?£¿ISQL?p?n???h???£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		IO_CUR_HANDLE	:=	DBMS_SQL.OPEN_CURSOR;


		/*======================================================================================================================
		||	?£¿£¿£¿£¿ELECT?£¿£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		BEGIN
			DBMS_SQL.PARSE( IO_CUR_HANDLE, WK_SQL_STATEMENT, DBMS_SQL.NATIVE );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40356;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of SELECT existence check.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE2;
		END IF;


		/*======================================================================================================================
		||	?£¿I???£¿ELECT?£¿£¿£¿£¿£¿£¿??£¿Z?b?g?£¿£¿£¿£¿`?£¿£¿B
		======================================================================================================================*/
		BEGIN
			WK_COLUMN_ID := 1;
			SELECT USER_TAB_COLUMNS.DATA_LENGTH
				INTO WK_COLUMN_LEN
				FROM	USER_TAB_COLUMNS
				WHERE	USER_TAB_COLUMNS.TABLE_NAME		=	'FNAKITANA'
				  AND	USER_TAB_COLUMNS.COLUMN_NAME	=	'SYOZAIKEY';
			DBMS_SQL.DEFINE_COLUMN( IO_CUR_HANDLE, WK_COLUMN_ID, DATA_EMP_GET.FNAKITANA_SYOZAIKEY, WK_COLUMN_LEN );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40357;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A result was not chosen at the time of SELECT processing execution.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE2;
		END IF;


	<<ENDEMPSEARCH_CURSOLMAKE2>>
		NULL;

	/*==========================================================================================================================
	||	?£¿O?n???h??
	==========================================================================================================================*/
	EXCEPTION
		WHEN OTHERS THEN
			IO_RC		:= 40358;


			IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
			WK_MSGMAKE	:= TRUE;
			WK_MSGBUFF	:= 'An error occurred at the time of Inside function of Cursor edit.'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END EMPSEARCH_CURSOLMAKE2;





	PROCEDURE	EMPSEARCH_CURSOLMAKE3
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IN_HARDZONE		IN		VARCHAR2,
		IN_SOFTZONE		IN		VARCHAR2,
		IN_AILESTNO		IN		VARCHAR2,
		IN_AKIKENKBN	IN		VARCHAR2,
		IO_CUR_HANDLE	IN OUT	INTEGER,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_SQL_STATEMENT	VARCHAR2	(32000);
		WK_COLUMN_ID		NUMBER		  (  3);
		WK_COLUMN_LEN		NUMBER		  (  3);

	BEGIN
		/*======================================================================================================================
		||	?_?u???f?B?[?v?I?£¿£¿£¿p?£¿I???£¿ELECT?£¿£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		WK_SQL_STATEMENT := 'SELECT A.SYOZAIKEY '
						 ||		'FROM ('
						 ||			'SELECT FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, '
						 ||				'FNAKITANA.BAYNO, FNAKITANA.BAYNO AS BAYORDER, '
						 ||				'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO AS LEVELORDER, '
						 ||				'FNAKITANA.SYOZAIKEY '
						 ||				'FROM FNBANKCTL, FNAKITANA '
						 ||				'WHERE FNAKITANA.SOKOKBN = FNBANKCTL.SOKOKBN '
						 ||				  'AND FNAKITANA.BANKNO = FNBANKCTL.BANKNO '
						 ||				  'AND FNAKITANA.SOKOKBN = '''	 || IN_SOKOKBN					  || ''' '
						 ||				  'AND FNAKITANA.HARDZONE = '''  || IN_HARDZONE					  || ''' '
						 ||				  'AND FNAKITANA.SOFTZONE = '''  || IN_SOFTZONE					  || ''' '
						 ||				  'AND FNAKITANA.TANAFLG = '''	 || PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
						 ||				  'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	  || ''' '
						 ||				  'AND FNBANKCTL.STNO = '''		 || IN_AILESTNO					  || ''' '
						 ||				  'AND FNBANKCTL.DEEPKBN = '''	 || PA_AWC_DEFINE.DEEPKBN_OKU	  || ''' '
						 ||			'UNION '
						 ||			'SELECT FNAKITANA.BANKNO, FNAKITANA.BANKNO AS BANKORDER, ';


























		/*======================================================================================================================
		||	?£¿I???£¿p?^?[???£¿]?£¿£¿A?x?C?I?[?_?[?A???x???I?[?_?[?£¿£¿£¿H?£¿??£¿AORDER BY ?£¿o?£¿£¿£¿??I?£¿D?£¿??£¿??£¿£¿B
		======================================================================================================================*/





















		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN	OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP	OR
		   IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.BAYNO, FNAKITANA.BAYNO+FNSOKO.EBAY AS BAYORDER, ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.BAYNO, FNAKITANA.BAYNO-FNSOKO.EBAY AS BAYORDER, ';

		END IF;


		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN	 OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO+FNSOKO.ELEVEL AS LEVELORDER, ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP	OR
			  IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP	THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'FNAKITANA.LEVELNO, FNAKITANA.LEVELNO-FNSOKO.ELEVEL AS LEVELORDER, ';


		END IF;

		/*======================================================================================================================
		||	?£¿£¿??£¿£¿W
		======================================================================================================================*/
		WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
							||			   'FNAKITANA.SYOZAIKEY '
							||			   'FROM FNSOKO, FNBANKCTL, FNAKITANA '
							||			   'WHERE FNSOKO.SOKOKBN = FNBANKCTL.SOKOKBN '
							||				 'AND FNAKITANA.SOKOKBN = FNBANKCTL.SOKOKBN '
							||				 'AND FNAKITANA.BANKNO = FNBANKCTL.BANKNO '
							||				 'AND FNAKITANA.SOKOKBN = '''	|| IN_SOKOKBN					 || ''' '
							||				 'AND FNAKITANA.HARDZONE = '''	|| IN_HARDZONE					 || ''' '
							||				 'AND FNAKITANA.SOFTZONE = '''	|| IN_SOFTZONE					 || ''' '
							||				 'AND FNAKITANA.TANAFLG = '''	|| PA_AWC_DEFINE.TANAFLG_MISIYOU || ''' '
							||				 'AND FNAKITANA.ACCESSFLG = ''' || PA_AWC_DEFINE.ACCESSFLG_OK	 || ''' '
							||				 'AND FNBANKCTL.STNO = '''		|| IN_AILESTNO					 || ''' '
							||				 'AND FNBANKCTL.DEEPKBN = '''	|| PA_AWC_DEFINE.DEEPKBN_TEMAE	 || ''' '
							||	   ') A ';


		/*======================================================================================================================
		||	?£¿I???£¿p?^?[???£¿]?£¿A?\?[?g???£¿£¿W?£¿£¿B
		======================================================================================================================*/





















		IF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER, A.LEVELORDER, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_HP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER, A.LEVELORDER DESC, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_GEDAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER DESC, A.LEVELORDER, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_OP_JYODAN THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.BAYORDER DESC, A.LEVELORDER DESC, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER, A.BAYORDER, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_GEDAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER, A.BAYORDER DESC, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_HP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER DESC, A.BAYORDER, A.BANKORDER ';


		ELSIF IN_AKIKENKBN = PA_AWC_DEFINE.AKIKENKBN_JYODAN_OP THEN
			WK_SQL_STATEMENT	:=	WK_SQL_STATEMENT
								||	'ORDER BY A.LEVELORDER DESC, A.BAYORDER DESC, A.BANKORDER ';


		END IF;

DBMS_OUTPUT.PUT_LINE( '???????_?u???f?B?[?v?I?£¿£¿£¿p?£¿I???£¿J?[?\???£¿£¿I??????[' || LENGTHB(WK_SQL_STATEMENT) || ']' );
DBMS_OUTPUT.PUT_LINE( WK_SQL_STATEMENT );



		/*======================================================================================================================
		||	?£¿ISQL?p?n???h???£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		IO_CUR_HANDLE	:=	DBMS_SQL.OPEN_CURSOR;


		/*======================================================================================================================
		||	?£¿£¿£¿£¿ELECT?£¿£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		BEGIN
			DBMS_SQL.PARSE( IO_CUR_HANDLE, WK_SQL_STATEMENT, DBMS_SQL.NATIVE );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40362;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred at the time of SELECT existence check.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE3;
		END IF;


		/*======================================================================================================================
		||	?£¿I???£¿ELECT?£¿£¿£¿£¿£¿£¿??£¿Z?b?g?£¿£¿£¿£¿`?£¿£¿B
		======================================================================================================================*/
		BEGIN
			WK_COLUMN_ID := 1;
			SELECT USER_TAB_COLUMNS.DATA_LENGTH
				INTO WK_COLUMN_LEN
				FROM	USER_TAB_COLUMNS
				WHERE	USER_TAB_COLUMNS.TABLE_NAME		=	'FNAKITANA'
				  AND	USER_TAB_COLUMNS.COLUMN_NAME	=	'SYOZAIKEY';
			DBMS_SQL.DEFINE_COLUMN( IO_CUR_HANDLE, WK_COLUMN_ID, DATA_EMP_GET.FNAKITANA_SYOZAIKEY, WK_COLUMN_LEN );


		EXCEPTION
			WHEN OTHERS THEN
				IO_RC		:= 40363;


				IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A result was not chosen at the time of SELECT processing execution.'
							|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
		END;


		IF IO_RC != 0 THEN
			GOTO ENDEMPSEARCH_CURSOLMAKE3;
		END IF;


<<ENDEMPSEARCH_CURSOLMAKE3>>
		NULL;

	EXCEPTION
		WHEN OTHERS THEN
			IO_RC		:= 40001;

			IO_MSGDAT	:= DEF_MYNAME || 'EMPSEARCH_CURSOLMAKE3' || PA_AWC_DEFINE.MSGSECTION
						|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
						|| SQLERRM;
			WK_MSGBUFF	:= DEF_MYNAME || 'An error occurred with a procedure.'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END EMPSEARCH_CURSOLMAKE3;





	PROCEDURE FINAL_EMPCHECK
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IN_BANKORDER	IN		NUMBER,
		IN_SYOZAIKEY	IN		VARCHAR2,
		IO_SYOZAIKEY	IN OUT	VARCHAR2,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	)
	IS
		WK_FNAKITANA	FNAKITANA%ROWTYPE;
		WK_TMPAKITANA	FNAKITANA%ROWTYPE;
	BEGIN

		BEGIN
			/*==================================================================================================================
			||	?£¿£¿£¿£¿??£¿ey?£¿£¿I?£¿??t?@?C???£¿s???b?N?£¿s?£¿A???b?N?£¿£¿£¿??£¿n?j?£¿£¿£¿
			==================================================================================================================*/
			SELECT FNAKITANA.*
				INTO WK_FNAKITANA
				FROM	FNAKITANA
				WHERE	FNAKITANA.SYOZAIKEY =	IN_SYOZAIKEY
				  AND	FNAKITANA.TANAFLG	=	PA_AWC_DEFINE.TANAFLG_MISIYOU
				  AND	FNAKITANA.ACCESSFLG =	PA_AWC_DEFINE.ACCESSFLG_OK
				FOR UPDATE NOWAIT;


			IO_SYOZAIKEY	:=	IN_SYOZAIKEY;

		/*==========================================================================================================================
		||	?£¿O?n???h??
		==========================================================================================================================*/
		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.LINELOCK_NG = SQLCODE THEN
					NULL;



				ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					NULL;



				ELSE
					IO_RC := 40011;

					IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred at the time of a data lock.'
								|| 'SQLCODE:[' || TO_CHAR(SQLCODE) || '] '
								|| SQLERRM;
				END IF;
				GOTO ENDFINAL_EMPCHECK;
		END;






		IF MOD(IN_BANKORDER, 2) = 1 THEN
			WK_TMPAKITANA.SOKOKBN := WK_FNAKITANA.SOKOKBN ;
			WK_TMPAKITANA.BANKNO := WK_FNAKITANA.BANKNO ;
			WK_TMPAKITANA.BAYNO := WK_FNAKITANA.BAYNO + 1;
			WK_TMPAKITANA.LEVELNO := WK_FNAKITANA.LEVELNO ;

			BEGIN
				/*==================================================================================================================
				||	?£¿£¿£¿£¿??£¿ey?£¿£¿P?£¿??£¿I?£¿??£¿				==================================================================================================================*/
				SELECT FNAKITANA.*
					INTO WK_TMPAKITANA
					FROM	FNAKITANA
					WHERE	FNAKITANA.SOKOKBN =	WK_TMPAKITANA.SOKOKBN
					  AND	FNAKITANA.BANKNO =	WK_TMPAKITANA.BANKNO
					  AND	FNAKITANA.BAYNO	=	WK_TMPAKITANA.BAYNO
					  AND	FNAKITANA.LEVELNO =	WK_TMPAKITANA.LEVELNO
					FOR UPDATE NOWAIT;

			/*==========================================================================================================================
			||	?£¿O?n???h??
			==========================================================================================================================*/
			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.LINELOCK_NG = SQLCODE THEN
						NULL;



					ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						NULL;



					ELSE
						IO_RC := 40011;

						IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE	:= TRUE;
						WK_MSGBUFF	:= 'An error occurred at the time of a data lock.'
									|| 'SQLCODE:[' || TO_CHAR(SQLCODE) || '] '
									|| SQLERRM;
					END IF;
					GOTO ENDFINAL_EMPCHECK;
			END;

			IF	WK_TMPAKITANA.TANAFLG =	PA_AWC_DEFINE.TANAFLG_MISIYOU AND
					WK_TMPAKITANA.ACCESSFLG = PA_AWC_DEFINE.ACCESSFLG_OK	THEN

				IO_SYOZAIKEY	:=	WK_TMPAKITANA.SYOZAIKEY;
			END IF	;
		END IF	;

<<ENDFINAL_EMPCHECK>>
		NULL;

	EXCEPTION
		WHEN OTHERS THEN
			IO_RC		:= 40001;

			IO_MSGDAT	:= DEF_MYNAME || 'FINAL_EMPCHECK' || PA_AWC_DEFINE.MSGSECTION
						|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
						|| SQLERRM;
			WK_MSGBUFF	:= DEF_MYNAME || 'An error occurred with a procedure.'
						|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
						|| 'SQLERRM=['	|| SQLERRM			|| ']';

	END FINAL_EMPCHECK;


/*==============================================================================================================================
|| ?£¿£¿??£¿
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?£¿£¿G???[?£¿£¿£¿£¿£¿£¿£¿??A?£¿I?£¿B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred. '
				   || 'IO_RC=[' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?o?£¿p?????[?^?£¿£¿£¿??b?Z?[?W?£¿£¿t???O?£¿????£¿
	==========================================================================================================================*/
	IO_RC			:=	0;
	IO_MSGDAT		:=	' ';
	WK_MSGMAKE		:=	FALSE;
	IO_SYOZAIKEY	:=	NULL;


	/*==========================================================================================================================
	||	?A?C??STNo?£¿w?£¿£¿£¿£¿??A?G???[?£¿£¿£¿B
	||	?£¿£¿£¿£¿F?£¿£¿H?A?C???????£¿??£¿S?o???N???£¿@?\?£¿H
	||			?£¿c?O?£¿£¿£¿A?V???O???f?B?[?v?£¿_?u???f?B?[?v?£¿£¿£¿I???£¿??@?£¿£¿£¿£¿£¿£¿£¿£¿B?i?V???O???f?B?[?v?£¿£¿u?£¿m?£¿
	||			  ?£¿s?v?I?j?£¿£¿£¿A?V???O???f?B?[?v?£¿£¿£¿A?_?u???f?B?[?v?£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿A?£¿£¿£¿£¿£¿A?C??STNo?£¿K?v?£¿£¿
	||			  ?£¿£¿£¿B?£¿£¿£¿A?g?£¿p?£¿£¿£¿c?£¿£¿£¿£¿£¿o???N?t???[?£¿£¿I???£¿i?]???£¿A?C???????£¿£¿??£¿??£¿A???[?g?`?F?b?N
	||			  ?£¿£¿£¿£¿£¿£¿£¿£¿A?C?????£¿£¿£¿£¿£¿j?£¿A?d?g?£¿£¿£¿£¿c?£¿A?£¿£¿£¿A?C??STNo?£¿p?????[?^?`?F?b?N?£¿s?£¿??£¿£¿£¿
	||			  ?A???£¿£¿£¿£¿£¿£¿£¿B
	==========================================================================================================================*/
	IF IN_AILESTNO IS NULL OR PA_AWC_DEFINE.STATION_MINYURYOKU = IN_AILESTNO THEN
		IO_RC		:= 20154;
		IO_MSGDAT	:= ' ';
		WK_MSGMAKE	:= TRUE;
		WK_MSGBUFF	:= 'Please specify Aisle data.';
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?w?£¿A?C??STNo?£¿£¿f?B?[?v?£¿£¿£¿£¿£¿£¿£¿B
	==========================================================================================================================*/
	BEGIN
		SELECT	FNBANKCTL.DEEPKBN
			INTO WK_DEEPKBN
			FROM	FNBANKCTL
			WHERE	FNBANKCTL.STNO		=	IN_AILESTNO
			  AND	FNBANKCTL.DEEPKBN	=	PA_AWC_DEFINE.DEEPKBN_OKU
			  AND	ROWNUM				=	1;


	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				WK_DEEPKBN := NULL;


			ELSE
				IO_RC		:= 40352;


				IO_MSGDAT	:= IN_AILESTNO		|| PA_AWC_DEFINE.MSGSECTION
							|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
							|| SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'The error which is not expected at the time of DEEPKBN acquisition occurred.'
							|| 'AILSTNO=[' || IN_AILESTNO	   || '] '
							|| 'SQLCODE=['		|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['		|| SQLERRM			|| ']';
			END IF;
	END;


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?V???O???f?B?[?v?£¿?
	||	?£¿V???O???f?B?[?v?£¿??£¿A?£¿£¿£¿I???£¿£¿I?£¿£¿£¿p?£¿I???£¿£¿£¿£¿£¿£¿£¿B
	||	?£¿£¿£¿£¿F?V???O???f?B?[?v?£¿I?£¿£¿£¿£¿£¿£¿£¿£¿£¿??H
	||			?£¿_?u???f?B?[?v?£¿V???O???f?B?[?v?£¿A?C???????£¿£¿£¿??£¿£¿B
	==========================================================================================================================*/
	IF WK_DEEPKBN IS NULL THEN
		/*======================================================================================================================
		||	?£¿I?r?p?k?£¿g?p?£¿A?????£¿£¿£¿£¿£¿I???£¿J?[?\???£¿£¿I?£¿£¿£¿£¿£¿B
		======================================================================================================================*/
		EMPSEARCH_CURSOLMAKE( IN_SOKOKBN, IN_BANKORDER, IN_HARDZONE, IN_SOFTZONE, IN_AILESTNO, IN_AKIKENKBN, WK_CUR_HANDLE,
				IO_RC, IO_MSGDAT );
		IF 0 != IO_RC THEN
			GOTO ENDLABEL;
		END IF;

	/*==========================================================================================================================
	||	?_?u???f?B?[?v?£¿?
	==========================================================================================================================*/
	ELSE
		/*======================================================================================================================
		||	?£¿£¿£¿£¿I???£¿£¿?
		======================================================================================================================*/
		IF FALSE = IN_TANAKANFLG THEN
			/*==================================================================================================================
			||	?£¿I?r?p?k?£¿g?p?£¿A?????£¿£¿£¿£¿£¿I???£¿J?[?\???£¿£¿I?£¿£¿£¿£¿£¿B
			==================================================================================================================*/
			EMPSEARCH_CURSOLMAKE2( IN_SOKOKBN, IN_HARDZONE, IN_SOFTZONE, IN_AILESTNO, IN_AKIKENKBN, WK_CUR_HANDLE,
					IO_RC, IO_MSGDAT );
			IF 0 != IO_RC THEN
				GOTO ENDLABEL;
			END IF;

		/*======================================================================================================================
		||	?I?£¿£¿£¿p?£¿I???£¿£¿?
		======================================================================================================================*/
		ELSE
			/*==================================================================================================================
			||	?£¿I?r?p?k?£¿g?p?£¿A?????£¿£¿£¿£¿£¿I???£¿J?[?\???£¿£¿I?£¿£¿£¿£¿£¿B
			==================================================================================================================*/
			EMPSEARCH_CURSOLMAKE3( IN_SOKOKBN, IN_HARDZONE, IN_SOFTZONE, IN_AILESTNO, IN_AKIKENKBN, WK_CUR_HANDLE,
					IO_RC, IO_MSGDAT );
			IF 0 != IO_RC THEN
				GOTO ENDLABEL;
			END IF;

		END IF;

	END IF;


	/*==========================================================================================================================
	||	?£¿I???£¿p?£¿ISQL?£¿£¿s?£¿£¿B
	||	?i?£¿£¿£¿£¿£¿£¿A?£¿£¿g?p?£¿£¿J?[?\???£¿I?[?v???£¿C?G?£¿£¿s?£¿j
	==========================================================================================================================*/
	EXECUTE_RC := DBMS_SQL.EXECUTE( WK_CUR_HANDLE );



	/*==========================================================================================================================
	||	?£¿£¿£¿£¿£¿I???£¿p?£¿ISQL?£¿£¿A?£¿I?£¿??£¿£¿£¿B
	==========================================================================================================================*/
	LOOP
		/*======================================================================================================================
		||	?£¿I???£¿p?£¿ISQL?£¿£¿P???£¿£¿£¿£¿B
		======================================================================================================================*/

		IF 0 = DBMS_SQL.FETCH_ROWS( WK_CUR_HANDLE ) THEN
			EXIT;

		END IF;

		/*======================================================================================================================
		||	?£¿`?£¿£¿£¿£¿£¿£¿£¿£¿l?£¿Z?b?g?£¿£¿B
		======================================================================================================================*/
		DBMS_SQL.COLUMN_VALUE( WK_CUR_HANDLE, 1, DATA_EMP_GET.FNAKITANA_SYOZAIKEY );

		/*======================================================================================================================
		||	?£¿I?£¿I???£¿T?u?£¿£¿£¿o?£¿A?{???£¿????£¿£¿£¿£¿I?£¿`?F?b?N?£¿£¿B
		======================================================================================================================*/
		FINAL_EMPCHECK( IN_SOKOKBN, IN_BANKORDER, DATA_EMP_GET.FNAKITANA_SYOZAIKEY, IO_SYOZAIKEY, IO_RC, IO_MSGDAT );
		IF 0 != IO_RC THEN
			EXIT;
		END IF;

		/*======================================================================================================================
		||	???£¿ey?£¿??£¿£¿£¿£¿£¿£¿??[?v?I?£¿£¿£¿B
		======================================================================================================================*/
		IF IO_SYOZAIKEY IS NOT NULL THEN
			EXIT;
		END IF;

	END LOOP;


	/*==========================================================================================================================
	||	?£¿I???£¿p?£¿ISQL?n???h???£¿J???£¿£¿B
	==========================================================================================================================*/
	DBMS_SQL.CLOSE_CURSOR( WK_CUR_HANDLE );


	/*==========================================================================================================================
	||	?£¿I???£¿£¿£¿s?£¿£¿£¿£¿??A?G???[???b?Z?[?W?£¿Z?b?g?£¿£¿B
	==========================================================================================================================*/
	IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO > IO_RC AND IO_SYOZAIKEY IS NULL THEN
		IO_RC		:= 20169;


		IO_MSGDAT	:= IN_SOKOKBN		|| PA_AWC_DEFINE.MSGSECTION
					|| IN_AILESTNO		|| PA_AWC_DEFINE.MSGSECTION
					|| IN_SOFTZONE		|| PA_AWC_DEFINE.MSGSECTION
					|| IN_HARDZONE;
		WK_MSGMAKE	:= TRUE;
		WK_MSGBUFF	:= 'It failed to the decision of an Empty Location.'
					|| 'Warehouse=['		 || IN_SOKOKBN	 || '] '
					|| 'AILSTNO=['		 || IN_AILESTNO  || '] '
					|| 'SOFTZONE=[' || IN_SOFTZONE	|| '] '
					|| 'HARDZONE=[' || IN_HARDZONE	|| ']';
	END IF;


	/*==========================================================================================================================
	||	?£¿£¿G???[?£¿£¿£¿£¿£¿£¿£¿??A?£¿I?£¿B
	==========================================================================================================================*/
	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


	/*==========================================================================================================================
	||	?I?£¿£¿£¿PDATE?B???£¿£¿£¿I?£¿£¿I?£¿£¿X?£¿£¿B
	==========================================================================================================================*/
	UPDATE	FNAKITANA	SET
			FNAKITANA.TANAFLG	=	PA_AWC_DEFINE.TANAFLG_SIYOU
		WHERE	FNAKITANA.SYOZAIKEY =	IO_SYOZAIKEY;


<<ENDLABEL>>
	/*==========================================================================================================================
	||	?J?[?\???£¿N???[?Y???£¿£¿£¿£¿s?£¿B
	==========================================================================================================================*/

	IF TRUE = DBMS_SQL.IS_OPEN( WK_CUR_HANDLE ) THEN
		DBMS_SQL.CLOSE_CURSOR( WK_CUR_HANDLE );
	END IF;


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

		IF TRUE = DBMS_SQL.IS_OPEN( WK_CUR_HANDLE ) THEN
			DBMS_SQL.CLOSE_CURSOR( WK_CUR_HANDLE );
		END IF;

		/*======================================================================================================================
		||	?£¿O?????£¿`
		======================================================================================================================*/
		IO_RC		:= 40001;

		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STAS80;
/


