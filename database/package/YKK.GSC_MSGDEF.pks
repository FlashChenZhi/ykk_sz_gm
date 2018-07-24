DROP PACKAGE YKK.GSC_MSGDEF;

CREATE OR REPLACE PACKAGE YKK.GSC_MSGDEF
IS










	GMSG1001	CONSTANT	CHAR(120) := 'PROC   There is not a carrying route   ST-NO[%s]-[%s]' ;
	GMSG1002	CONSTANT	CHAR(120) := 'PROC   Carrying route NG. ST-NO[%s]-[%s]' ;
	GMSG1003    CONSTANT    CHAR(120) := 'PROC   There is not an Empty Location in the warehouse. warehouse[%s] Aisle[%s]' ;
	GMSG1004    CONSTANT    CHAR(120) := 'PROC   Empty Location existence. Dead lock occurrence. Warehouse[%s] Aisle[%s]' ;
	GMSG1005    CONSTANT    CHAR(120) := 'PROC   failed to Empty Location searching. Warehouse[%s] Aisle[%s]' ;
	GMSG1006    CONSTANT    CHAR(120) := 'PROC   Due to Storage aisle FULL. Empty Location searching impossible' ;
	GMSG1007    CONSTANT    CHAR(120) := 'PROC   Because the carrying data of the same carrying ID exists Storage impossible  ID[%.8s]' ;
	GMSG1008    CONSTANT    CHAR(120) := 'PROC   In addition the for, setting in work impossible' ;
	GMSG1009    CONSTANT    CHAR(120) := 'PROC   %s It is insufficient a stock' ;
	GMSG1010    CONSTANT    CHAR(120) := 'PROC   Paletiz stataion Change!! [%s] -> [%s]' ;


	GMSG2001	CONSTANT	CHAR(120) := 'PROC   Absence article occurred [%s]' ;



	GMSG4001	CONSTANT	CHAR(120) := 'PROC   Database error.  File:[%s] SQLCODE = %s' ;
	GMSG4002	CONSTANT	CHAR(120) := 'PROC   Dead lock occurrence [%s]' ;
	GMSG4003	CONSTANT	CHAR(120) := 'PROC   [%s] Data without existing [%s]' ;
	GMSG4004	CONSTANT	CHAR(120) := 'PROC   [%s] Data that doubles exists [%s]' ;
	GMSG4005	CONSTANT	CHAR(120) := 'PROC   [%s] Line lock of data impossible [%s]' ;
	GMSG4006	CONSTANT	CHAR(120) := 'PROC   [%s] Data was changed [%s]' ;


	GMSG5001	CONSTANT	CHAR(120) := 'PROC   %s Error occurred with the procedure. SQLCODE[%s]' ;
	GMSG5002	CONSTANT	CHAR(120) := 'PROC   %s Error occurred with the function.  SQLCODE[%s]' ;
	GMSG5003	CONSTANT	CHAR(120) := 'PROC   FKICK Triggering failure. [%s]' ;


	GMSG6001	CONSTANT	CHAR(120) := 'PROC   %s Error occurred in the subroutine.  [%s] RET[%s]' ;
	GMSG6002	CONSTANT	CHAR(120) := 'PROC   %s There is not a vacancy in the SAIBAN file.  ID=[%s]' ;


	GMSG7001	CONSTANT	CHAR(120) := 'PROC   Since it is not a superuser, it cannot set up. ' ;


	LMSG9999    CONSTANT    CHAR(120) := 'PROC   [%s] DATA CAN[%s]' ;

END GSC_MSGDEF ;
/


