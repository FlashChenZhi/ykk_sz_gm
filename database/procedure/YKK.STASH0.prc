DROP PROCEDURE YKK.STASH0;

CREATE OR REPLACE PROCEDURE YKK.STASH0
(
	IN_SAIBANID		IN		NUMBER,
	IO_SAIBANNO		IN OUT	NUMBER,
	IO_RC			IN OUT	NUMBER,
	IO_MSGDAT		IN OUT	VARCHAR2,





	IN_FORMAT		IN		VARCHAR2	:= NULL,
	IN_SAIBANLEN	IN		NUMBER		:= NULL




)
/*==============================================================================================================================
|| ?o?k?^?r?p?k?u???b?N
==============================================================================================================================*/
/*==============================================================================================================================
|| ?£¿??£¿`??
==============================================================================================================================*/
IS
	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/

	DEF_MYNAME		CONSTANT VARCHAR2	( 10) := 'STASH0';
	DEF_MYNAMEJP	CONSTANT VARCHAR2	( 60) := 'SAIBAN SYUTOKU SYORI';


	/*==========================================================================================================================
	||	?£¿??£¿£¿
	==========================================================================================================================*/
	WK_FNSAIBAN_ROWID	ROWID;
	WK_FNSAIBAN			FNSAIBAN%ROWTYPE;


	WK_SAIBANMAX		FNSAIBAN.SAIBANNO%TYPE;



	WK_MSGMAKE			BOOLEAN := FALSE;


	WK_MSGBUFF			VARCHAR2(32767);


/*==============================================================================================================================
|| ?£¿£¿??£¿
==============================================================================================================================*/
BEGIN
	/*==========================================================================================================================
	||	?£¿£¿G???[?£¿£¿£¿£¿£¿£¿£¿??A?£¿I?£¿B
	==========================================================================================================================*/
	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.IO_RC = [' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;

	IO_RC := 0;
	IO_MSGDAT := ' ';
	WK_MSGMAKE := FALSE;






	/*==========================================================================================================================
	||	?w?£¿£¿£¿D?£¿£¿£¿????£¿£¿£¿£¿£¿B
	||	?£¿£¿£¿£¿F?£¿£¿£¿r?£¿£¿£¿£¿£¿£¿H???b?N?£¿??P?O?O???£¿£¿£¿H
	||			?£¿£¿[?£¿£¿£¿£¿£¿A?d???£¿£¿£¿??£¿£¿B?I???N???£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿??£¿A?r?£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿f?[?^?£¿R?£¿?
	||			  ?£¿??£¿£¿£¿B?£¿£¿£¿????t?£¿ELECT?£¿£¿£¿£¿£¿£¿£¿??A?£¿£¿£¿??j?[?N?£¿??£¿K?v?£¿L?£¿A?£¿£¿£¿??£¿r?£¿£¿£¿£¿£¿£¿?
	||			  ?£¿£¿£¿£¿B
	==========================================================================================================================*/
	BEGIN
		SELECT FNSAIBAN.SAIBANID, FNSAIBAN.TOPSAINO, FNSAIBAN.ENDSAINO, FNSAIBAN.SAIBANNO, FNSAIBAN.SAIBANHIJI, ROWID
			INTO WK_FNSAIBAN.SAIBANID, WK_FNSAIBAN.TOPSAINO, WK_FNSAIBAN.ENDSAINO, WK_FNSAIBAN.SAIBANNO, WK_FNSAIBAN.SAIBANHIJI,
				WK_FNSAIBAN_ROWID
			FROM	FNSAIBAN
			WHERE	FNSAIBAN.SAIBANID	=	IN_SAIBANID
			FOR UPDATE;


	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:= 40057;

				IO_MSGDAT	:= IN_SAIBANID;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An SAIBAN information record does not exist. '
							|| 'SAIBANID=['	||	IN_SAIBANID || ']';


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:= 30001;
				WK_MSGBUFF	:= 'A dead lock occurred.'
							|| 'SAIBANID=['	|| IN_SAIBANID	|| ']';
				IO_MSGDAT	:= WK_MSGBUFF;
				WK_MSGMAKE	:= TRUE;


			ELSE
				IO_RC		:= 40055;

				IO_MSGDAT	:= IN_SAIBANID		|| PA_AWC_DEFINE.MSGSECTION
							|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION
							|| SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred in an SAIBAN information file.?B'
							|| 'SAIBANID=['	|| IN_SAIBANID		|| '] '
							|| 'SQLCODE:['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
			END IF;
	END;


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;






	/*==========================================================================================================================
	||	?£¿£¿????£¿I?u?W?F?N?g?i?£¿?Key?A?V?X?e??ID?AMCKEY?£¿??j?A?£¿£¿A?????£¿£¿£¿£¿????t?£¿V?X?e?????t?£¿£¿£¿£¿??A?Y???v?£¿£¿
	||	?£¿£¿S?£¿£¿£¿£¿????£¿£¿£¿I?u?W?F?N?g?£¿£¿£¿£¿????£¿£¿£¿B
	||		?????£¿£¿£¿£¿????t?£¿A?p?\?R???£¿V?X?e?????t?£¿£¿r?£¿£¿£¿£¿£¿??A?w?£¿£¿£¿h?c?£¿£¿£¿£¿£¿t?@?C???£¿s???b?N?£¿£¿A?V?X
	||		?e???h?c?£¿£¿??L?[?A?l?b?j?d?x?£¿£¿£¿£¿£¿£¿£¿£¿????£¿£¿A?Y???£¿£¿£¿£¿????t?£¿X?V?£¿£¿B???A?V?X?e???£¿????£¿????l?£¿
	||		?£¿£¿£¿£¿£¿????t?£¿ULL?£¿??£¿????£¿£¿£¿£¿£¿£¿£¿£¿B
	==========================================================================================================================*/

	IF ( IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_NYU		OR
		 IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_SYU		OR
		 IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_TANAKAN OR
		 IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_SYSID	)	AND
	   ( WK_FNSAIBAN.SAIBANHIJI != SYSDATE08_GET()						)		  THEN

/*
		 IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_MCKEY_NYU		OR
		 IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_MCKEY_SYU	)		AND
	   ( WK_FNSAIBAN.SAIBANHIJI != SYSDATE08_GET()						)		  THEN
*/

		/*======================================================================================================================
		||	?£¿£¿£¿£¿£¿A?£¿£¿o?£¿£¿£¿£¿£¿o?£¿Z?b?g?B
		======================================================================================================================*/
		WK_FNSAIBAN.SAIBANNO := WK_FNSAIBAN.TOPSAINO;
		WK_SAIBANMAX := NULL;

		/*======================================================================================================================
		||	?£¿?Key?i???£¿j?£¿?
		||	?£¿?Key?i?o?£¿j?£¿?
		||	?£¿?Key?i?I?£¿£¿£¿j?£¿?
		======================================================================================================================*/
		IF IN_SAIBANID	  =   PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_NYU		OR
		   IN_SAIBANID	  =   PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_SYU		OR
		   IN_SAIBANID	  =   PA_AWC_DEFINE.FNSAI_ID_HANSOKEY_TANAKAN	THEN
			BEGIN



				SELECT TO_NUMBER( SUBSTR( MAX(FNHANSO.HANSOKEY), -(IN_SAIBANLEN-1), IN_SAIBANLEN-1 ) )


					INTO WK_SAIBANMAX
					FROM	FNHANSO
					WHERE	FNHANSO.HANSOKEY
							BETWEEN








							( SUBSTR( TO_CHAR( WK_FNSAIBAN.TOPSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, 1 )
						   || IN_FORMAT
						   || SUBSTR( TO_CHAR( WK_FNSAIBAN.TOPSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-(IN_SAIBANLEN-1), IN_SAIBANLEN-1 ) )
							AND

							( SUBSTR( TO_CHAR( WK_FNSAIBAN.ENDSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, 1 )
						   || IN_FORMAT
						   || SUBSTR( TO_CHAR( WK_FNSAIBAN.ENDSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-(IN_SAIBANLEN-1), IN_SAIBANLEN-1 ) );


				WK_SAIBANMAX := WK_SAIBANMAX + WK_FNSAIBAN.TOPSAINO -1;




			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						NULL;


					ELSE
						IO_RC		:= 40374;

						IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE	:= TRUE;
						WK_MSGBUFF	:= 'An error occurred in an SAIBAN information file.'
									|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
									|| 'SQLERRM=['	|| SQLERRM			|| ']';
					END IF;
			END;


			IF IO_RC != 0 THEN
				GOTO ENDLABEL;
			END IF;

		/*======================================================================================================================
		||	?V?X?e??ID?i?v?£¿j?£¿?
		||	?V?X?e??ID?i?v?£¿O?j?£¿?
		======================================================================================================================*/
		ELSIF IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_SYSID		THEN
			BEGIN
				SELECT TO_NUMBER( SUBSTR( MAX(FNLOCAT.SYSTEMID), -IN_SAIBANLEN, IN_SAIBANLEN ) )
					INTO WK_SAIBANMAX
					FROM	FNLOCAT
					WHERE	FNLOCAT.SYSTEMID
							BETWEEN
							( IN_FORMAT || SUBSTR( TO_CHAR( WK_FNSAIBAN.TOPSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, IN_SAIBANLEN ) )
							AND
							( IN_FORMAT || SUBSTR( TO_CHAR( WK_FNSAIBAN.ENDSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, IN_SAIBANLEN ) );


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						NULL;


					ELSE
						IO_RC		:= 40375;

						IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE	:= TRUE;
						WK_MSGBUFF	:= '[%1!s!]:An error occurred in an SAIBAN information file.SQLCODE=[%2!s!] SQLERRM=[%3!s!]'
									|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
									|| 'SQLERRM=['	|| SQLERRM			|| ']';
					END IF;
			END;


			IF IO_RC != 0 THEN
				GOTO ENDLABEL;
			END IF;

		/*======================================================================================================================
		||	MCKey?i?v?£¿E???£¿j?£¿?
		||	MCKey?i?v?£¿E?o?£¿j?£¿?
		======================================================================================================================*/
		ELSIF IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_MCKEY_NYU	OR
			  IN_SAIBANID	=	PA_AWC_DEFINE.FNSAI_ID_MCKEY_SYU	THEN
			BEGIN
				SELECT TO_NUMBER( SUBSTR( MAX(FNHANSO.MCKEY), -IN_SAIBANLEN, IN_SAIBANLEN ) )
					INTO WK_SAIBANMAX
					FROM	FNHANSO
					WHERE	FNHANSO.MCKEY
							BETWEEN
							( IN_FORMAT || SUBSTR( TO_CHAR( WK_FNSAIBAN.TOPSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, IN_SAIBANLEN ) )
							AND
							( IN_FORMAT || SUBSTR( TO_CHAR( WK_FNSAIBAN.ENDSAINO, PA_AWC_DEFINE.DEF_FORMAT_SAIBANNO ),
											-IN_SAIBANLEN, IN_SAIBANLEN ) );


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						NULL;


					ELSE
						IO_RC		:= 40376;

						IO_MSGDAT	:= TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE	:= TRUE;
						WK_MSGBUFF	:= 'An error occurred in an SAIBAN information file.'
									|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
									|| 'SQLERRM=['	|| SQLERRM			|| ']';
					END IF;
			END;


			IF IO_RC != 0 THEN
				GOTO ENDLABEL;
			END IF;

		END IF;

		/*======================================================================================================================
		||	?£¿£¿£¿A???£¿Y???t?H?[?}?b?g?£¿f?[?^?£¿£¿£¿£¿£¿??A?£¿£¿????£¿l?£¿£¿£¿£¿£¿£¿£¿£¿£¿1?£¿Z?b?g?£¿£¿B
		======================================================================================================================*/
		IF WK_SAIBANMAX IS NOT NULL THEN
			WK_FNSAIBAN.SAIBANNO := WK_SAIBANMAX + 1;
			IF WK_FNSAIBAN.SAIBANNO > WK_FNSAIBAN.ENDSAINO THEN
				WK_FNSAIBAN.SAIBANNO := WK_FNSAIBAN.TOPSAINO;
			END IF;

		END IF;


		/*==================================================================================================================
		||	?£¿£¿£¿£¿£¿????£¿£¿£¿£¿£¿£¿£¿????t?£¿X?V
		||		?£¿£¿£¿£¿F?X?V?£¿£¿??£¿£¿£¿u?£¿£¿£¿????t?£¿????£¿£¿£¿£¿????t?£¿£¿£¿£¿£¿v?£¿£¿£¿£¿£¿£¿£¿A?£¿£¿£¿£¿£¿£¿£¿H
		||				?£¿£¿£¿£¿£¿£¿X?V?£¿v?£¿X?P?W???[???£¿v?£¿O?X?P?W???[???£¿£¿£¿£¿??£¿£¿£¿??b?N?£¿£¿??£¿£¿£¿£¿A?£¿£¿£¿£¿		||				  ?v?£¿O?X?P?W???[???£¿£¿??£¿£¿£¿£¿??£¿£¿£¿£¿A?£¿£¿£¿v?£¿£¿£¿£¿X?P?W???[???£¿£¿£¿??£¿£¿£¿£¿£¿\?z?£¿£¿		||				  ?£¿B?£¿£¿£¿q?£¿£¿£¿A?£¿??£¿v?£¿O?X?P?W???[???£¿£¿£¿£¿£¿£¿Y???£¿??R?[?h?£¿??b?N?£¿£¿B???£¿£¿£¿£¿??£¿		||				  ???£¿£¿£¿£¿v?£¿O?X?P?W???[???£¿£¿£¿£¿£¿£¿??b?N?£¿£¿£¿£¿£¿B?£¿£¿£¿A?£¿??£¿v?£¿O?X?P?W???[???£¿????£¿		||				  ?I?£¿£¿OMMIT?£¿£¿£¿A???b?N?£¿£¿£¿£¿£¿£¿??£¿X?P?W???[???£¿£¿£¿o?£¿£¿A?£¿£¿??u?£¿£¿£¿????t?£¿????£¿
		||				  ?£¿£¿£¿????t?£¿£¿£¿£¿£¿v?£¿£¿£¿£¿??£¿£¿£¿£¿A?£¿£¿£¿£¿£¿£¿t?@?C???£¿??R?[?h?£¿X?V?£¿£¿£¿£¿£¿B
		||				  ?£¿£¿£¿£¿£¿V?X?e??ID?£¿£¿£¿_?u?£¿£¿£¿£¿£¿£¿£¿£¿£¿??£¿B?£¿£¿£¿£¿£¿£¿??£¿£¿£¿£¿£¿£¿£¿£¿£¿A???b?N?£¿£¿		||				  ?£¿£¿£¿£¿X?P?W???[???£¿£¿£¿£¿£¿£¿£¿£¿????t?£¿X?V?£¿£¿£¿£¿£¿£¿£¿A?u?w?£¿£¿Y???s?£¿£¿v?£¿£¿£¿£¿£¿£¿£¿		||				  ?£¿A?X?V?£¿_?u?£¿£¿h?£¿£¿£¿£¿£¿£¿£¿B?£¿£¿??£¿£¿£¿£¿£¿£¿£¿m???£¿£¿£¿£¿£¿£¿B
		||				  ?£¿£¿A?£¿£¿??b?N?£¿£¿£¿£¿£¿£¿£¿£¿£¿??£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿B
		||		?£¿L	?£¿£¿£¿£¿£¿e?[?u???£¿??b?N?£¿£¿??£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿A?e?£¿£¿??£¿??b?N?iUPDATE?j?£¿£¿£¿£¿
		||				  ?B?£¿£¿£¿A?f?b?h???b?N?£¿£¿£¿£¿£¿£¿£¿A?£¿£¿????£¿£¿£¿£¿£¿£¿£¿£¿£¿£¿v?£¿£¿£¿£¿A?£¿£¿X?V???£¿v???O
		||				  ?????£¿g???A?^?C?~???O???£¿£¿??b?N?£¿£¿£¿£¿£¿£¿£¿B?v???£¿B
		==================================================================================================================*/
		BEGIN










			WK_FNSAIBAN.SAIBANHIJI	:=	SYSDATE08_GET();






		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
					IO_RC		:= 30001;
					WK_MSGBUFF	:= '?£¿£¿£¿£¿e?[?u???£¿£¿??Z?b?g???£¿f?b?h???b?N?£¿£¿£¿£¿£¿£¿£¿B'
								|| '?£¿£¿D=['	|| IN_SAIBANID	|| ']';
					IO_MSGDAT	:= WK_MSGBUFF;
					WK_MSGMAKE	:= TRUE;


				ELSE
					IO_RC		:= 40076;

					IO_MSGDAT	:= IN_SAIBANID || PA_AWC_DEFINE.MSGSECTION|| TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
					WK_MSGMAKE	:= TRUE;
					WK_MSGBUFF	:= 'An error occurred in an SAIBAN information file.'
								|| '?£¿£¿D=['	|| IN_SAIBANID		|| '] '
								|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
								|| 'SQLERRM=['	|| SQLERRM			|| ']';
				END IF;
		END;

		IF IO_RC != 0 THEN
			GOTO ENDLABEL;
		END IF;



























































	END IF;


	/*==========================================================================================================================
	||	?w?£¿£¿£¿D?£¿£¿£¿o?£¿£¿£¿£¿A?£¿£¿£¿J?E???g?A?b?v?£¿£¿B?i???b?N?£¿£¿j
	==========================================================================================================================*/
	BEGIN














		IO_SAIBANNO := WK_FNSAIBAN.SAIBANNO;


		WK_FNSAIBAN.SAIBANNO := WK_FNSAIBAN.SAIBANNO + 1;


		IF WK_FNSAIBAN.SAIBANNO > WK_FNSAIBAN.ENDSAINO THEN
			WK_FNSAIBAN.SAIBANNO := WK_FNSAIBAN.TOPSAINO;
		END IF;


		UPDATE FNSAIBAN SET
				FNSAIBAN.SAIBANNO	= WK_FNSAIBAN.SAIBANNO,




				FNSAIBAN.SAIBANHIJI = WK_FNSAIBAN.SAIBANHIJI




			WHERE FNSAIBAN.ROWID = WK_FNSAIBAN_ROWID;


	EXCEPTION
		WHEN OTHERS THEN

			IF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
				IO_RC		:= 40057;

				IO_MSGDAT	:= IN_SAIBANID;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An SAIBAN information record does not exist. '
							|| 'SAIBANID=[' || IN_SAIBANID || ']';


			ELSIF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
				IO_RC		:= 30001;
				WK_MSGBUFF	:= 'A dead lock occurred.'
							|| 'SAIBANID=['	|| IN_SAIBANID	|| ']';
				IO_MSGDAT	:= WK_MSGBUFF;
				WK_MSGMAKE	:= TRUE;


			ELSE
				IO_RC		:= 40055;

				IO_MSGDAT	:= IN_SAIBANID || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'An error occurred in an SAIBAN information file. '
							|| 'SAIBANID=['	|| IN_SAIBANID		|| '] '
							|| 'SQLCODE:['	|| TO_CHAR(SQLCODE) || '] '
							|| 'SQLERRM=['	|| SQLERRM			|| ']';
			END IF;
	END;


	IF IO_RC != 0 THEN
		GOTO ENDLABEL;
	END IF;


<<ENDLABEL>>
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
		IO_MSGDAT := NULL;
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

		IO_MSGDAT := DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF := DEF_MYNAMEJP || 'An error occurred with a procedure.SQLCODE:['
					  || TO_CHAR(SQLCODE) || '] ' || SQLERRM;
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END STASH0;
/


