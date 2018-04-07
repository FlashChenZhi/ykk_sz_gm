DROP PACKAGE YKK.PA_SCH_GLOBAL;

CREATE OR REPLACE PACKAGE YKK.PA_SCH_GLOBAL
IS
/*==============================================================================================================================
||  PL/SQL?\?£¿£¿
==============================================================================================================================*/


















TYPE FNSOKO_TBL IS TABLE OF FNSOKO%ROWTYPE
    INDEX BY BINARY_INTEGER;
DATA_FNSOKO_TBL         FNSOKO_TBL;
DATA_FNSOKO_TBL_NUM     NUMBER  (  2);


TYPE FNSTATION_TBL IS TABLE OF FNSTATION%ROWTYPE
    INDEX BY BINARY_INTEGER;
DATA_FNSTATION_TBL      FNSTATION_TBL;
DATA_FNSTATION_TBL_NUM  NUMBER  (  3);








TYPE EMPPB_NON_AILEST_REC IS RECORD
(
    STNO    FNAILCTL.STNO%TYPE
);
TYPE EMPPB_NON_AILEST_TBL IS TABLE OF EMPPB_NON_AILEST_REC
    INDEX BY BINARY_INTEGER;
DATA_EMPPB_NON_AILEST_TBL       EMPPB_NON_AILEST_TBL;
DATA_EMPPB_NON_AILEST_TBL_NUM   NUMBER  (  3);





/*==============================================================================================================================
|| ?O???[?o???£¿??£¿£¿
==============================================================================================================================*/
























FNSYSTEM_TBL    FNSYSTEM%ROWTYPE;



JYURITSU_LEN    NUMBER;


KONSAISU_LEN    NUMBER;


HARDZONESEQ_LEN NUMBER;





SYS_NIDAKAKBN       BOOLEAN;













END PA_SCH_GLOBAL;
/


