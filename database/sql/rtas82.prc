DROP PROCEDURE RTAS82;

CREATE OR REPLACE PROCEDURE
	RTAS82
	(
		IN_SOKOKBN		IN		VARCHAR2,
		IN_HARDZONE		IN		VARCHAR2,
		IN_SOFTZONE		IN		VARCHAR2,
		IN_STNOFROM		IN		VARCHAR2,
		IO_STNOTO		IN OUT	VARCHAR2,
		IO_HARDZONE		IN OUT	VARCHAR2,
		IO_SOFTZONE		IN OUT	VARCHAR2,
		IO_SYOZAIKEY	IN OUT	VARCHAR2,
		IO_RC			IN OUT	NUMBER,
		IO_MSGDAT		IN OUT	VARCHAR2
	) IS







































































DEF_MYNAME		CONSTANT VARCHAR2(10)	:= 'RTAS82' ;

DEF_MYNAMEJP	CONSTANT VARCHAR2(60)	:= '?£¿I???£¿???' ;





HARDZONE_LOOP_CNT	NUMBER	(  5);
AILE_TOTAL_CNT		NUMBER	(  5);
BANK_TOTAL_CNT		NUMBER	(  5);
WORK_CNT			NUMBER	(  5);
WK_MSGNO			NUMBER	(  9);
WK_AILEORDER		FNSOKO.AILEORDER%TYPE;
WK_AILEORDER1F		FNSOKO.AILEORDER%TYPE;
WK_AILEORDER2F		FNSOKO.AILEORDER2%TYPE;
WK_AILEORDER3F		FNSOKO.AILEORDER3%TYPE;
WK_SAILE			FNSOKO.SAILE%TYPE;
WK_EAILE			FNSOKO.EAILE%TYPE;
WK_FNSTATION		FNSTATION%ROWTYPE;
WK_FNAILCTL			FNAILCTL%ROWTYPE;
WK_FNSOKO_ROWID		ROWID;
WK_BANKORDER		FNBANKCTL.BANKORDER%TYPE;
WK_CLEARFLG_1F		BOOLEAN := FALSE;
WK_CLEARFLG_2F		BOOLEAN := FALSE;
WK_CLEARFLG_3F		BOOLEAN := FALSE;

WK_MSGMAKE		BOOLEAN := FALSE;


WK_MSGBUFF		VARCHAR2(32767);






CURSOR	FNSOFTZONEYUSEN_GET( IN_SOFTZONE IN VARCHAR2 ) IS
	SELECT
		FNSOFTZONEYUSEN.YUSENSOFTZONE,
		DECODE( FNSOFTZONEYUSEN.AKIKENKBN, ' ', FNSOFTZONE.AKIKENKBN,
												  FNSOFTZONEYUSEN.AKIKENKBN ) AS AKIKENKBN
	FROM	FNSOFTZONE, FNSOFTZONEYUSEN
	WHERE	FNSOFTZONE.SOFTZONE			=	FNSOFTZONEYUSEN.SOFTZONE
	AND		FNSOFTZONEYUSEN.SOFTZONE	=	IN_SOFTZONE
	ORDER BY FNSOFTZONEYUSEN.SOFTZONEORDER;

DATA_FNSOFTZONEYUSEN_GET	FNSOFTZONEYUSEN_GET%ROWTYPE;





BEGIN



	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		WK_MSGBUFF := 'The error has already occurred.'
				   || 'IO_RC=[' || IO_RC || ']';
		GOTO ENDLABEL;
	END IF;





	IO_RC			:= 0;
	IO_MSGDAT		:= ' ';
	WK_MSGMAKE		:= FALSE;
	IO_STNOTO		:= ' ';
	IO_HARDZONE		:= ' ';
	IO_SOFTZONE		:= ' ';
	IO_SYOZAIKEY	:= ' ';
	WK_AILEORDER	:= 0;





	STASN0( IN_STNOFROM, WK_FNSTATION, IO_RC, IO_MSGDAT );

	IF IO_RC IS NOT NULL AND 0 != IO_RC THEN
		GOTO ENDLABEL;
	END IF;






	IF PA_AWC_DEFINE.AISLETYPE_LINK = WK_FNSTATION.LAYKBN AND WK_AILEORDER = 0 THEN







		BEGIN
			SELECT FNSOKO.AILEORDER, FNSOKO.AILEORDER2, FNSOKO.AILEORDER3,
						FNSOKO.SAILE, FNSOKO.EAILE, FNSOKO.ROWID
			INTO WK_AILEORDER1F, WK_AILEORDER2F, WK_AILEORDER3F,
						WK_SAILE, WK_EAILE, WK_FNSOKO_ROWID
			FROM	FNSOKO
			WHERE	FNSOKO.SOKOKBN	=	IN_SOKOKBN
			FOR UPDATE;


		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
					IO_RC		:= 30001;
					WK_MSGBUFF	:= 'The deadlock occurred in warehouse control at the time of Aile Order acquisition.';
					IO_MSGDAT	:= WK_MSGBUFF;
					WK_MSGMAKE	:= TRUE;


				ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC	   := 40023;

					IO_MSGDAT  := IN_SOKOKBN;
					WK_MSGMAKE := TRUE;
					WK_MSGBUFF := 'A record does not exist in a warehouse control file.'
							   || 'Warehouse=[' || IN_SOKOKBN || ']';


				ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
					IO_RC	   := 40024;

					IO_MSGDAT  := IN_SOKOKBN;
					WK_MSGMAKE := TRUE;
					WK_MSGBUFF := 'A record exists in a warehouse control file a plural.'
							   || 'Warehouse=[' || IN_SOKOKBN || ']';


				ELSE
					IO_RC	   := 40025;

					IO_MSGDAT  := IN_SOKOKBN || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
							   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
					WK_MSGMAKE := TRUE;
					WK_MSGBUFF := 'An error occurred at the time of warehouse control file searching.'
							   || 'Warehouse=['	|| IN_SOKOKBN	 || '] '
							   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
							   || 'SQLERRM=['		|| SQLERRM				|| ']';
				END IF;
				GOTO ENDLABEL;
		END;




		AILE_TOTAL_CNT := ( WK_EAILE - WK_SAILE ) + 1;




		IF SUBSTRB(IN_STNOFROM, 1, 1) = '1' THEN
			WK_AILEORDER := WK_AILEORDER1F	;
		ELSIF SUBSTRB(IN_STNOFROM, 1, 1) = '2' THEN
			WK_AILEORDER := WK_AILEORDER2F	;
		ELSE
			WK_AILEORDER := WK_AILEORDER3F	;
		END IF	;




	ELSIF PA_AWC_DEFINE.AISLETYPE_INDEPENDENCE = WK_FNSTATION.LAYKBN THEN



		AILE_TOTAL_CNT := 1;
		WK_AILEORDER := 0	;

	END IF;






	OPEN FNSOFTZONEYUSEN_GET( IN_SOFTZONE );
	LOOP



		FETCH FNSOFTZONEYUSEN_GET INTO DATA_FNSOFTZONEYUSEN_GET;

		IF FNSOFTZONEYUSEN_GET%NOTFOUND = TRUE THEN

			IF FNSOFTZONEYUSEN_GET%ROWCOUNT = 0 THEN
				IO_RC		:= 40386;

				IO_MSGDAT	:= IN_SOFTZONE;
				WK_MSGMAKE	:= TRUE;
				WK_MSGBUFF	:= 'A record does not exist in a softzone priority file.'
							|| 'SOFTZONE=['	|| IN_SOFTZONE	|| ']';
			END IF;
			EXIT;
		END IF;





		IO_RC		:= 0;
		IO_MSGDAT	:= ' ';
		WK_MSGMAKE	:= FALSE;





		IO_SOFTZONE := DATA_FNSOFTZONEYUSEN_GET.YUSENSOFTZONE;




		STAS21( IN_SOKOKBN, IN_HARDZONE, HARDZONE_LOOP_CNT, IO_RC, IO_MSGDAT );


		IF IO_RC != 0 THEN
			GOTO ENDLABEL;
		END IF;






		FOR HARDZONE_CNT IN 1..HARDZONE_LOOP_CNT LOOP



			IO_RC		:= 0;
			IO_MSGDAT	:= NULL;
			WK_MSGMAKE	:= FALSE;


			STAS20( IN_SOKOKBN, IN_HARDZONE, HARDZONE_CNT, IO_HARDZONE, IO_RC, IO_MSGDAT );

			IF IO_RC != 0 THEN
				EXIT;
			END IF;









			FOR LOOP_CNT IN 1..AILE_TOTAL_CNT LOOP









				STAS75( IN_SOKOKBN, IN_STNOFROM, WK_FNSTATION.LAYKBN, WK_AILEORDER, PA_AWC_DEFINE.NASI,
						IO_STNOTO, IO_RC, IO_MSGDAT );

				IF IO_RC != 0 THEN
					GOTO AILE_LOOP_ENDLABEL;
				END IF;







				STAS85( IN_SOKOKBN, IO_HARDZONE, IO_SOFTZONE, NULL, IO_STNOTO, IO_RC, IO_MSGDAT, FALSE, TRUE );
				IF 0 != IO_RC THEN
					GOTO AILE_LOOP_ENDLABEL;
				END IF;




				STASA2( IN_STNOFROM, IO_STNOTO, IO_RC, WK_MSGNO, IO_MSGDAT );

				IF 0 != IO_RC THEN

					IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO > IO_RC THEN

						IF PA_AWC_DEFINE.AISLETYPE_LINK = WK_FNSTATION.LAYKBN THEN



							GOTO AILE_LOOP_ENDLABEL;


						ELSE



							EXIT;

						END IF;


					ELSIF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
						EXIT;

					END IF;

				ELSE



					BEGIN
						SELECT
							FNAILCTL.AILE_STR_1F,
							FNAILCTL.AILE_STR_2F,
							FNAILCTL.AILE_STR_3F,
							FNAILCTL.SEARCH_BKORDER_1F,
							FNAILCTL.SEARCH_BKORDER_2F,
							FNAILCTL.SEARCH_BKORDER_3F,
							FNAILCTL.AILE_CAPA_SIZE_1F,
							FNAILCTL.AILE_CAPA_SIZE_2F,
							FNAILCTL.AILE_CAPA_SIZE_3F
						INTO
							WK_FNAILCTL.AILE_STR_1F,
							WK_FNAILCTL.AILE_STR_2F,
							WK_FNAILCTL.AILE_STR_3F,
							WK_FNAILCTL.SEARCH_BKORDER_1F,
							WK_FNAILCTL.SEARCH_BKORDER_2F,
							WK_FNAILCTL.SEARCH_BKORDER_3F,
							WK_FNAILCTL.AILE_CAPA_SIZE_1F,
							WK_FNAILCTL.AILE_CAPA_SIZE_2F,
							WK_FNAILCTL.AILE_CAPA_SIZE_3F
						FROM
							FNAILCTL
						WHERE
							FNAILCTL.STNO	=	IO_STNOTO;


					EXCEPTION
						WHEN OTHERS THEN

							IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
								IO_RC		:= 30001;
								WK_MSGBUFF	:= 'The deadlock occurred in aile control at the time of Aile Order acquisition.';
								IO_MSGDAT	:= WK_MSGBUFF;
								WK_MSGMAKE	:= TRUE;


							ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
								IO_RC	   := 40026;

								IO_MSGDAT  := IO_STNOTO;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'A record does not exist in a aile control file.'
										   || 'Aile Stno=[' || IO_STNOTO || ']';


							ELSIF PA_AWC_DEFINE.MORE_THAN_REQUESTED_NG = SQLCODE THEN
								IO_RC	   := 40027;

								IO_MSGDAT  := IO_STNOTO;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'A record exists in a aile control file a plural.'
										   || 'Aile Stno=[' || IO_STNOTO || ']';


							ELSE
								IO_RC	   := 40028;

								IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
										   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'An error occurred at the time of aile control file searching.'
										   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
										   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
										   || 'SQLERRM=['		|| SQLERRM				|| ']';
							END IF;
							GOTO AILE_LOOP_ENDLABEL;
					END;




					BEGIN
						SELECT
							COUNT(*)
						INTO
							WORK_CNT
						FROM
							FNHANSO
						WHERE
							FNHANSO.HJYOTAIFLG	<=	PA_AWC_DEFINE.HJYOTAIFLG_SIJIZUMI	AND
							SUBSTRB(FNHANSO.MOTOSTNO, 1, 1)	=	SUBSTRB(IN_STNOFROM, 1, 1)	AND
							FNHANSO.ENDSTNO	=	IO_STNOTO	;


					EXCEPTION
						WHEN OTHERS THEN

							IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
								IO_RC		:= 30001;
								WK_MSGBUFF	:= 'The deadlock occurred in Conveyance data .';
								IO_MSGDAT	:= WK_MSGBUFF;
								WK_MSGMAKE	:= TRUE;

								GOTO AILE_LOOP_ENDLABEL;

							ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
								WORK_CNT := 0	;


							ELSE
								IO_RC	   := 48003;

								IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
										   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'An error occurred at the time of Conveyance file searching.'
										   || 'End Stno=['	|| IO_STNOTO	 || '] '
										   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
										   || 'SQLERRM=['		|| SQLERRM				|| ']';
								GOTO AILE_LOOP_ENDLABEL;
							END IF;
					END;





					IF SUBSTRB(IN_STNOFROM, 1, 1) = '1' THEN
						IF WORK_CNT >= WK_FNAILCTL.AILE_STR_1F	THEN
							GOTO AILE_LOOP_ENDLABEL;
						END IF ;
					ELSIF SUBSTRB(IN_STNOFROM, 1, 1) = '2' THEN
						IF WORK_CNT >= WK_FNAILCTL.AILE_STR_2F	THEN
							GOTO AILE_LOOP_ENDLABEL;
						END IF ;
					ELSE
						IF WORK_CNT >= WK_FNAILCTL.AILE_STR_3F	THEN
							GOTO AILE_LOOP_ENDLABEL;
						END IF	;
					END IF	;




					IF SUBSTRB(IN_STNOFROM, 1, 1) = '1' THEN
						WK_BANKORDER := WK_FNAILCTL.SEARCH_BKORDER_1F	;
					ELSIF SUBSTRB(IN_STNOFROM, 1, 1) = '2' THEN
						WK_BANKORDER := WK_FNAILCTL.SEARCH_BKORDER_2F	;
					ELSE
						WK_BANKORDER := WK_FNAILCTL.SEARCH_BKORDER_3F	;
					END IF	;




					BEGIN
						SELECT
							COUNT(*)
						INTO
							BANK_TOTAL_CNT
						FROM
							FNBANKCTL
						WHERE
							FNBANKCTL.STNO	=	IO_STNOTO	;


					EXCEPTION
						WHEN OTHERS THEN

							IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
								IO_RC		:= 30001;
								WK_MSGBUFF	:= 'The deadlock occurred in bank control at the time of Aile Order acquisition.';
								IO_MSGDAT	:= WK_MSGBUFF;
								WK_MSGMAKE	:= TRUE;


							ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
								IO_RC	   := 48001;

								IO_MSGDAT  := IO_STNOTO;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'A record does not exist in a bank control file.'
										   || 'Aile Stno=[' || IO_STNOTO || ']';


							ELSE
								IO_RC	   := 48002;

								IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
										   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
								WK_MSGMAKE := TRUE;
								WK_MSGBUFF := 'An error occurred at the time of bank control file searching.'
										   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
										   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
										   || 'SQLERRM=['		|| SQLERRM				|| ']';
							END IF;
							GOTO AILE_LOOP_ENDLABEL;
					END;





					FOR LOOP_CNT2 IN 1..BANK_TOTAL_CNT LOOP



						STAS80( IN_SOKOKBN, WK_BANKORDER, IO_HARDZONE, IO_SOFTZONE,
							IO_STNOTO, DATA_FNSOFTZONEYUSEN_GET.AKIKENKBN, IO_SYOZAIKEY,
								IO_RC, IO_MSGDAT );

						IF IO_RC != 0 THEN
							GOTO BANK_LOOP_ENDLABEL;
						END IF;
<<BANK_LOOP_ENDLABEL>>



						IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
							EXIT;

						ELSE
							IO_RC		:= 0;
							IO_MSGDAT	:= ' ';
							WK_MSGMAKE	:= FALSE;

						END IF;




						WK_BANKORDER := WK_BANKORDER +1;

						IF WK_BANKORDER > BANK_TOTAL_CNT THEN
							WK_BANKORDER := 1;
						END IF;


						IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL AND IO_SYOZAIKEY != ' ' THEN



							IF SUBSTRB(IN_STNOFROM, 1, 1) = '1' THEN
								WK_FNAILCTL.SEARCH_BKORDER_1F := WK_BANKORDER ;
								WK_FNAILCTL.AILE_CAPA_SIZE_1F :=  WK_FNAILCTL.AILE_CAPA_SIZE_1F + 1;
								IF WK_FNAILCTL.AILE_CAPA_SIZE_1F >= 2 THEN
									WK_CLEARFLG_1F := TRUE	;
								END IF	;
							ELSIF SUBSTRB(IN_STNOFROM, 1, 1) = '2' THEN
								WK_FNAILCTL.SEARCH_BKORDER_2F := WK_BANKORDER ;
								WK_FNAILCTL.AILE_CAPA_SIZE_2F :=  WK_FNAILCTL.AILE_CAPA_SIZE_2F + 1;
								IF WK_FNAILCTL.AILE_CAPA_SIZE_2F >= 2 THEN
									WK_CLEARFLG_2F := TRUE	;
								END IF	;
							ELSE
								WK_FNAILCTL.SEARCH_BKORDER_3F := WK_BANKORDER ;
								WK_FNAILCTL.AILE_CAPA_SIZE_3F :=  WK_FNAILCTL.AILE_CAPA_SIZE_3F + 1;
								IF WK_FNAILCTL.AILE_CAPA_SIZE_3F >= 2 THEN
									WK_CLEARFLG_3F := TRUE	;
								END IF	;
							END IF	;




							BEGIN
								UPDATE FNAILCTL
								SET
									FNAILCTL.SEARCH_BKORDER_1F	=	WK_FNAILCTL.SEARCH_BKORDER_1F,
									FNAILCTL.SEARCH_BKORDER_2F	=	WK_FNAILCTL.SEARCH_BKORDER_2F,
									FNAILCTL.SEARCH_BKORDER_3F	=	WK_FNAILCTL.SEARCH_BKORDER_3F,
									FNAILCTL.AILE_CAPA_SIZE_1F	=	WK_FNAILCTL.AILE_CAPA_SIZE_1F,
									FNAILCTL.AILE_CAPA_SIZE_2F	=	WK_FNAILCTL.AILE_CAPA_SIZE_2F,
									FNAILCTL.AILE_CAPA_SIZE_3F	=	WK_FNAILCTL.AILE_CAPA_SIZE_3F
								WHERE
									FNAILCTL.STNO	=	IO_STNOTO	;


							EXCEPTION
								WHEN OTHERS THEN

									IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
										IO_RC		:= 30001;
										WK_MSGBUFF	:= 'The deadlock occurred in aile control at the time of Aile Order acquisition.';
										IO_MSGDAT	:= WK_MSGBUFF;
										WK_MSGMAKE	:= TRUE;


									ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
										IO_RC	   := 40026;

										IO_MSGDAT  := IO_STNOTO;
										WK_MSGMAKE := TRUE;
										WK_MSGBUFF := 'A record does not exist in a aile control file.'
												   || 'Aile Stno=[' || IO_STNOTO || ']';


									ELSE
										IO_RC	   := 40028;

										IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
												   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
										WK_MSGMAKE := TRUE;
										WK_MSGBUFF := 'An error occurred at the time of aile control file searching.'
												   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
												   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
												   || 'SQLERRM=['		|| SQLERRM				|| ']';
									END IF;
									GOTO AILE_LOOP_ENDLABEL;
							END;

							EXIT;

						END IF;

					END LOOP;

				END IF;

<<AILE_LOOP_ENDLABEL>>



				IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
					EXIT;

				ELSE
					IO_RC		:= 0;
					IO_MSGDAT	:= ' ';
					WK_MSGMAKE	:= FALSE;

				END IF;













				IF PA_AWC_DEFINE.AISLETYPE_LINK = WK_FNSTATION.LAYKBN THEN



					WK_AILEORDER := WK_AILEORDER +1;

					IF WK_AILEORDER > AILE_TOTAL_CNT THEN
						WK_AILEORDER := 1;
					END IF;

				END IF;


				IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL AND IO_SYOZAIKEY != ' ' THEN
					EXIT;
				END IF;

			END LOOP;





			IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
				EXIT;
			END IF;


			IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL AND IO_SYOZAIKEY != ' ' THEN
				EXIT;
			END IF;

		END LOOP;





		IF PA_AWC_DEFINE.EMERGENCY_STOP_MSGNO < IO_RC THEN
			EXIT;
		END IF;


		IF IO_RC = 0 AND IO_SYOZAIKEY IS NOT NULL AND IO_SYOZAIKEY != ' ' THEN
			EXIT;
		END IF;

	END LOOP;


	CLOSE FNSOFTZONEYUSEN_GET;


	IF 0 != IO_RC THEN
		GOTO ENDLABEL;
	END IF;





	IF WK_CLEARFLG_1F = TRUE OR WK_CLEARFLG_2F = TRUE OR WK_CLEARFLG_3F = TRUE THEN



		IF SUBSTRB(IN_STNOFROM, 1, 1) = '1' THEN
			WK_AILEORDER1F := WK_AILEORDER ;
		ELSIF SUBSTRB(IN_STNOFROM, 1, 1) = '2' THEN
			WK_AILEORDER2F := WK_AILEORDER ;
		ELSE
			WK_AILEORDER3F := WK_AILEORDER ;
		END IF	;




		BEGIN
			UPDATE FNSOKO
			SET
				FNSOKO.AILEORDER	=	WK_AILEORDER1F,
				FNSOKO.AILEORDER2	=	WK_AILEORDER2F,
				FNSOKO.AILEORDER3	=	WK_AILEORDER3F
			WHERE
				FNSOKO.ROWID	=	WK_FNSOKO_ROWID

			AND	(FNSOKO.AILEORDER	!=	WK_AILEORDER1F
			OR	FNSOKO.AILEORDER2	!=	WK_AILEORDER2F
			OR	FNSOKO.AILEORDER3	!=	WK_AILEORDER3F);


		EXCEPTION
			WHEN OTHERS THEN

				IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
					IO_RC		:= 30001;
					WK_MSGBUFF	:= 'The deadlock occurred in warehouse control at the time of Aile Order acquisition.';
					IO_MSGDAT	:= WK_MSGBUFF;
					WK_MSGMAKE	:= TRUE;


				ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
					IO_RC	   := 40023;

					IO_MSGDAT  := IN_SOKOKBN;
					WK_MSGMAKE := TRUE;
					WK_MSGBUFF := 'A record does not exist in a warehouse control file.'
							   || 'Warehouse=[' || IN_SOKOKBN || ']';


				ELSE
					IO_RC	   := 40025;

					IO_MSGDAT  := IN_SOKOKBN || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
							   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
					WK_MSGMAKE := TRUE;
					WK_MSGBUFF := 'An error occurred at the time of warehouse control file searching.'
							   || 'Warehouse=['	|| IN_SOKOKBN	 || '] '
							   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
							   || 'SQLERRM=['		|| SQLERRM				|| ']';
				END IF;
		END;


		IF IO_RC != 0 THEN
			GOTO ENDLABEL;
		END IF;

		IF WK_CLEARFLG_1F = TRUE THEN



			BEGIN
				UPDATE FNAILCTL
				SET
					FNAILCTL.AILE_CAPA_SIZE_1F	=	0	;


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
						IO_RC		:= 30001;
						WK_MSGBUFF	:= 'The deadlock occurred in aile control at the time of Aile Order acquisition.';
						IO_MSGDAT	:= WK_MSGBUFF;
						WK_MSGMAKE	:= TRUE;


					ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						IO_RC	   := 40026;

						IO_MSGDAT  := IO_STNOTO;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'A record does not exist in a aile control file.'
								   || 'Aile Stno=[' || IO_STNOTO || ']';


					ELSE
						IO_RC	   := 40028;

						IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
								   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'An error occurred at the time of aile control file searching.'
								   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
								   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
								   || 'SQLERRM=['		|| SQLERRM				|| ']';
					END IF;
					GOTO ENDLABEL;
			END;
		END IF	;

		IF WK_CLEARFLG_2F = TRUE THEN



			BEGIN
				UPDATE FNAILCTL
				SET
					FNAILCTL.AILE_CAPA_SIZE_2F	=	0	;


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
						IO_RC		:= 30001;
						WK_MSGBUFF	:= 'The deadlock occurred in aile control at the time of Aile Order acquisition.';
						IO_MSGDAT	:= WK_MSGBUFF;
						WK_MSGMAKE	:= TRUE;


					ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						IO_RC	   := 40026;

						IO_MSGDAT  := IO_STNOTO;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'A record does not exist in a aile control file.'
								   || 'Aile Stno=[' || IO_STNOTO || ']';


					ELSE
						IO_RC	   := 40028;

						IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
								   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'An error occurred at the time of aile control file searching.'
								   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
								   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
								   || 'SQLERRM=['		|| SQLERRM				|| ']';
					END IF;
					GOTO ENDLABEL;
			END;
		END IF	;

		IF WK_CLEARFLG_3F = TRUE THEN



			BEGIN
				UPDATE FNAILCTL
				SET
					FNAILCTL.AILE_CAPA_SIZE_3F	=	0	;


			EXCEPTION
				WHEN OTHERS THEN

					IF PA_AWC_DEFINE.DEADLOCK_NG = SQLCODE THEN
						IO_RC		:= 30001;
						WK_MSGBUFF	:= 'The deadlock occurred in aile control at the time of Aile Order acquisition.';
						IO_MSGDAT	:= WK_MSGBUFF;
						WK_MSGMAKE	:= TRUE;


					ELSIF PA_AWC_DEFINE.NOTFOUND_NG = SQLCODE THEN
						IO_RC	   := 40026;

						IO_MSGDAT  := IO_STNOTO;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'A record does not exist in a aile control file.'
								   || 'Aile Stno=[' || IO_STNOTO || ']';


					ELSE
						IO_RC	   := 40028;

						IO_MSGDAT  := IO_STNOTO || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE)
								   || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
						WK_MSGMAKE := TRUE;
						WK_MSGBUFF := 'An error occurred at the time of aile control file searching.'
								   || 'Aile Stno=['	|| IO_STNOTO	 || '] '
								   || 'SQLCODE:['		|| TO_CHAR(SQLCODE)		|| '] '
								   || 'SQLERRM=['		|| SQLERRM				|| ']';
					END IF;
					GOTO ENDLABEL;
			END;
		END IF	;
	END IF	;


<<ENDLABEL>>



	IF 0 = IO_RC AND (IO_SYOZAIKEY = ' ' OR IO_SYOZAIKEY IS NULL) THEN
		IO_RC		:= 20172;


		IO_MSGDAT	:= IN_SOKOKBN	|| PA_AWC_DEFINE.MSGSECTION
					|| IN_STNOFROM	|| PA_AWC_DEFINE.MSGSECTION
					|| IN_HARDZONE	|| PA_AWC_DEFINE.MSGSECTION
					|| IN_SOFTZONE;
		WK_MSGMAKE	:= TRUE;
		WK_MSGBUFF	:= 'It failed to the decision of an Empty Location.'
					|| 'Warehouse=['			|| IN_SOKOKBN	|| '] '
					|| 'Carrying former STNo=['		|| IN_STNOFROM	|| '] '
					|| 'HARD ZONE=[' || IN_HARDZONE	|| '] '
					|| 'SOFT ZONE=['	|| IN_SOFTZONE	|| ']';
	END IF;






	IF FNSOFTZONEYUSEN_GET%ISOPEN = TRUE THEN
		CLOSE FNSOFTZONEYUSEN_GET;
	END IF;





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





	IF WK_MSGBUFF IS NOT NULL THEN
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );
	END IF;




EXCEPTION








	WHEN OTHERS THEN




		IF FNSOFTZONEYUSEN_GET%ISOPEN = TRUE THEN
			CLOSE FNSOFTZONEYUSEN_GET;
		END IF;




		IO_RC		:= 40001;

		IO_MSGDAT	:= DEF_MYNAME || PA_AWC_DEFINE.MSGSECTION || TO_CHAR(SQLCODE) || PA_AWC_DEFINE.MSGSECTION || SQLERRM;
		WK_MSGBUFF	:= DEF_MYNAMEJP || 'An error occurred with a procedure.'
					|| 'SQLCODE=['	|| TO_CHAR(SQLCODE) || '] '
					|| 'SQLERRM=['	|| SQLERRM			|| ']';
		DBMS_OUTPUT.PUT_LINE( WK_MSGBUFF );

END RTAS82;

/
