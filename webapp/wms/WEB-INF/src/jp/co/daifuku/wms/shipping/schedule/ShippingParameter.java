package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.Parameter;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * <CODE>ShippingParameter</CODE>クラスは、出荷パッケージ内の画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出荷パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ShippingParameter</CODE>クラスが保持する項目<BR>
 * <DIR>
 *     ボタン種別<BR>
 *     作業者コード <BR>
 *     パスワード <BR>
 *     作業者名称 <BR>
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     出荷予定日 <BR>
 *     発注日 <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     商品コード <BR>
 *     商品コード(リストセル) <BR>
 *     商品名称 <BR>
 *     表示順１ <BR>
 *     表示順２ <BR>
 * 	   伝票No.<BR>
 *     行No.<BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     予定ケース数(作業･ホスト)<BR>
 *     予定ピース数(作業･ホスト)<BR>
 *     出荷ケース数<BR>
 *     出荷ピース数<BR>
 *     欠品区分<BR>
 *     賞味期限 <BR>
 *     開始出荷予定日<BR>
 *     終了出荷予定日<BR>
 *     開始伝票No.<BR>
 *     終了伝票No.<BR>
 *     作業状態<BR>
 *     作業状態(リストセル)<BR>
 *     作業状態名称<BR>
 *     仕入先コード<BR>
 *     仕入先名称<BR>
 *     入荷伝票No.<BR>
 *     入荷行No.<BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     開始出荷日<BR>
 *     終了出荷日<BR>
 *     出荷日<BR>
 *     欠品ケース数 <BR>
 *     欠品ピース数 <BR> 
 *     ＴＣ/ＤＣ区分<BR>
 *     ＴＣ/ＤＣ区分名称<BR>
 *     出荷予定削除一覧リスト発行区分 <BR>
 *     予定総数(作業･ホスト)<BR>
 *     実績総数<BR>
 *     ためうち行No.<BR> 
 *     出荷予定一意キー<BR>
 *     ケース/ピース区分<BR>
 *     バッチNo.(スケジュールNo.)<BR>
 *     登録区分<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時<BR>
 *     最終更新処理名<BR>
 *     作業No.<BR>
 *     画面機能フラグ<BR>
 *     検索フラグ<BR>
 *     選択Box有効/無効フラグ<BR>
 *     選択BoxON/OFF<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingParameter extends Parameter
{
	// Class feilds ------------------------------------------------
	/**
	 * 表示順(伝票No.順)
	 */
	public static final String DSPORDER_TICKET = "0";

	/**
	 * 表示順(商品コード順)
	 */
	public static final String DSPORDER_ITEM = "1";

	/**
	 * 表示順(出荷日順)
	 */
	public static final String DSPORDER_SHIPPINGDATE = "0";

	/**
	 * 表示順(出荷予定日順)
	 */
	public static final String DSPORDER_SHIPPINGPLANDATE = "1";

	/**
	 * ボタン種別(修正登録)
	 */
	public static final String BUTTON_MODIFYSUBMIT = "0";

	/**
	 * ボタン種別(一括作業中解除)
	 */
	public static final String BUTTON_ALLWORKINGCLEAR = "1";

	/**
	 * 作業状態(全て)
	 */
	public static final String WORKSTATUS_ALL = "9";

	/**
	 * 作業状態(未開始)
	 */
	public static final String WORKSTATUS_UNSTARTING = "0";

	/**
	 * 作業状態(開始済)
	 */
	public static final String WORKSTATUS_START = "1";

	/**
	 * 作業状態(作業中)
	 */
	public static final String WORKSTATUS_NOWWORKING = "2";
	
	/**
	 * 作業状態(一部完了)
	 */
	public static final String WORKSTATUS_RECEPTION_IN_PART = "3";

	/**
	 * 作業状態(完了)
	 */
	public static final String WORKSTATUS_FINISH = "4";

	/**
	 * 作業状態ためうち(未開始)
	 */
	public static final String WORKSTATUS_S_UNSTARTING = "0";

	/**
	 * 作業状態ためうち(開始済)
	 */
	public static final String WORKSTATUS_S_START = "1";

	/**
	 * 作業状態ためうち(作業中)
	 */
	public static final String WORKSTATUS_S_NOWWORKING = "2";

	/**
	 * 作業状態ためうち(完了)
	 */
	public static final String WORKSTATUS_S_FINISH = "3";

	/**
	 * 検索フラグ(予定)
	 */
	public static final String SEARCHFLAG_PLAN = "1";

	/**
	 * 検索フラグ(実績)
	 */
	public static final String SEARCHFLAG_RESULT = "2";
	
	/**
	 * TC/DC区分 DC
	 */
	public static final String TCDC_FLAG_DC = "0" ;
	/**
	 * TC/DC区分 クロスドック
	 */
	public static final String TCDC_FLAG_CROSSTC = "1" ;
	/**
	 * TC/DC区分 TC
	 */
	public static final String TCDC_FLAG_TC = "2" ;
	/**
	 * TC/DC区分 全て
	 */
	public static final String TCDC_FLAG_ALL = "99" ;
	
	/**
	 * 実績報告フラグ（未報告）
	 */
	public static final String REPORT_FLAG_NOT_SENT = "0";

	/**
	 * 実績報告フラグ（報告済）
	 */
	public static final String REPORT_FLAG_SENT = "1";

	/**
	 * 選択Box有効/無効フラグ（有効）
	 */
	public static final boolean SELECT_BOX_ENABLE = true;

	/**
	 * 選択Box有効/無効フラグ（無効）
	 */
	public static final boolean SELECT_BOX_DISNABLE = false;

	/**
	 * 選択BoxON/OFF（ON）
	 */
	public static final boolean SELECT_BOX_ON = true;

	/**
	 * 選択BoxON/OFF（OFF）
	 */
	public static final boolean SELECT_BOX_OFF = false;

	/**
	 * 範囲フラグ（開始）
	 */
	public static final String RANGE_START = "0";

	/**
	 * 範囲フラグ（終了）
	 */
	public static final String RANGE_END = "1";

	/**
	 * データ変更フラグ（変更なし）
	 */
	public static final String UPDATEFLAG_NO = "0";

	/**
	 * データ変更フラグ（変更あり）
	 */
	public static final String UPDATEFLAG_YES = "1";

	// Class variables -----------------------------------------------
	/**
	 * ボタン種別
	 */
	private String wButtonType;

	/**
	 * 作業者コード
	 */
	private String wWorkerCode;

	/**
	 * パスワード
	 */
	private String wPassword;

	/**
	 * 作業者名称
	 */
	private String wWorkerName;

	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 荷主名称
	 */
	private String wConsignorName;

	/**
	 * 出荷予定日
	 */
	private String wPlanDate;

	/**
	 * 発注日
	 */
	private String wOrderingDate;

	/**
	 * 出荷先コード
	 */
	private String wCustomerCode;

	/**
	 * 出荷先名称
	 */
	private String wCustomerName;

	/**
	 * 商品コード
	 */
	private String wItemCode;

	/**
	 * 商品コード(リストセル)
	 */
	private String wItemCodeL;

	/**
	 * 商品名称
	 */
	private String wItemName;

	/**
	 * 表示順１
	 */
	private String wDspOrder;

	/**
	 * 表示順２
	 */
	private String wDspOrder2;
	
	/**
	 * 伝票No.
	 */
	private String wShippingTicketNo;

	/**
	 * 行No.
	 */
	private int wShippingLineNo;

	/**
	 * ケース入数
	 */
	private int wEnteringQty;

	/**
	 * ボール入数
	 */
	private int wBundleEnteringQty;

	/**
	 * 予定ケース数(作業･ホスト)
	 */
	private int wPlanCaseQty;

	/**
	 * 予定ピース数(作業･ホスト)
	 */
	private int wPlanPieceQty;

	/**
	 * 出荷ケース数
	 */
	private int wResultCaseQty;

	/**
	 * 出荷ピース数
	 */
	private int wResultPieceQty;

	/**
	 * 欠品区分
	 */
	private boolean wShortage = false;

	/**
	 * 賞味期限
	 */
	private String wUseByDate;

	/**
	 * 開始出荷予定日
	 */
	private String wFromPlanDate;

	/**
	 * 終了出荷予定日
	 */
	private String wToPlanDate;

	/**
	 * 開始伝票No.
	 */
	private String wFromTicketNo;

	/**
	 * 終了伝票No.
	 */
	private String wToTicketNo;

	/**
	 * 作業状態
	 */
	private String wStatusFlag;

	/**
	 * 作業状態(リストセル)
	 */
	private String wStatusFlagL;

	/**
	 * 作業状態名称
	 */
	private String wStatusName;

	/**
	 * 仕入先コード
	 */
	private String wSupplierCode;

	/**
	 * 仕入先名称
	 */
	private String wSupplierName;

	/**
	 * 入荷伝票No.
	 */
	private String wInstockTicketNo;

	/**
	 * 入荷行No.
	 */
	private int wInstockLineNo;

	/**
	 * ケースITF
	 */
	private String wITF;

	/**
	 * ボールITF
	 */
	private String wBundleITF;

	/**
	 * 開始出荷日
	 */
	private String wFromShippingDate;

	/**
	 * 終了出荷日
	 */
	private String wToShippingDate;

	/**
	 * 出荷日
	 */
	private String wShippingDate;

	/**
	 * 欠品ケース数
	 */
	private int wShortageCaseQty;

	/**
	 * 欠品ピース数
	 */
	private int wShortagePieceQty;

	/**
	 * ＴＣ/ＤＣ区分
	 */
	private String wTcdcFlag;
	
	/**
	 * ＴＣ/ＤＣ区分名称
	 */
	private String wTcdcFlagName;

	/**
	 * 出荷予定削除一覧リスト発行区分
	 */
	private boolean wShippingDeleteListFlg = false;

	/**
	 * 予定総数
	 */
	private int wTotalPlanQty;

	/**
	 * 実績総数
	 */
	private int wTotalResultQty;

	/**
	 * ためうち行No.
	 */
	private int wRowNo;

	/**
	 * 出荷予定一意キー
	 */
	private String wShippingPlanUkey;

	/**
	 * ケース/ピース区分
	 */
	private String wCasePieceFlag;

	/**
	 * バッチNo.(スケジュールNo.)
	 */
	private String wBatchNo;

	/**
	 * 登録区分
	 */
	private String wRegistKbn;

	/**
	 * 登録日時
	 */
	private java.util.Date wRegistDate;

	/**
	 * 登録処理名
	 */
	private String wRegistPName;

	/**
	 * 最終更新日時
	 */
	private java.util.Date wLastUpdateDate;

	/**
	 * 最終更新処理名
	 */
	private String wLastUpdatePName;

	/**
	 * 作業No.
	 */
	private String wJobNo;

	/**
	 * 検索フラグ(予定／実績)
	 */
	private String wSearchFlag;

	/**
	 * 選択Box有効/無効フラグ
	 */
	private boolean wSelectBoxFlag = true;

	/**
	 * 選択BoxON/OFF
	 */
	private boolean wSelectBoxCheck = true;

	/**
	 * 作業状態配列
	 */
	private String[] wSearchStatus;
	
	/**
	 * 実績報告区分
	 */
	private String wReportFlag;

	/**
	 * 実績報告区分名称
	 */
	private String wReportFlagName;

	private boolean wLineCheckFlag = true;
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingParameter()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * ボタン種別をセットします。
	 * @param arg セットするボタン種別
	 * @throws InvalidStatusException 定義されていないボタン種別がセットされた場合に通知します。
	 */
	public void setButtonType(String arg) throws InvalidStatusException
	{
		if ((arg.equals(BUTTON_MODIFYSUBMIT)) || (arg.equals(BUTTON_ALLWORKINGCLEAR)))
		{
			wButtonType = arg;
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wBeginningFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + MessageResource.DELIM + tObj[0] + MessageResource.DELIM + tObj[1] + MessageResource.DELIM + tObj[2]));
		}
	}

	/**
	 * ボタン種別を取得します。
	 * @return ボタン種別
	 */
	public String getButtonType()
	{
		return wButtonType;
	}

	/**
	 * 作業者コードをセットします。
	 * @param arg セットする作業者コード
	 */
	public void setWorkerCode(String arg)
	{
		wWorkerCode = arg;
	}

	/**
	 * 作業者コードを取得します。
	 * @return 作業者コード
	 */
	public String getWorkerCode()
	{
		return wWorkerCode;
	}

	/**
	 * パスワードをセットします。
	 * @param arg セットするパスワード
	 */
	public void setPassword(String arg)
	{
		wPassword = arg;
	}

	/**
	 * パスワードを取得します。
	 * @return パスワード
	 */
	public String getPassword()
	{
		return wPassword;
	}

	/**
	 * 作業者名称をセットします。
	 * @param arg セットする作業者名称
	 */
	public void setWorkerName(String arg)
	{
		wWorkerName = arg;
	}

	/**
	 * 作業者名称を取得します。
	 * @return 作業者名称
	 */
	public String getWorkerName()
	{
		return wWorkerName;
	}

	/**
	 * 荷主コードをセットします。
	 * @param arg セットする荷主コード
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	/**
	 * 荷主コードを取得します。
	 * @return 荷主コード
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * 荷主名称をセットします。
	 * @param arg セットする荷主名称
	 */
	public void setConsignorName(String arg)
	{
		wConsignorName = arg;
	}

	/**
	 * 荷主名称を取得します。
	 * @return 荷主名称
	 */
	public String getConsignorName()
	{
		return wConsignorName;
	}

	/**
	 * 出荷予定日をセットします。
	 * @param arg セットする出荷予定日
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	/**
	 * 出荷予定日を取得します。
	 * @return 出荷予定日
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 発注日をセットします。
	 * @param arg セットする発注日
	 */
	public void setOrderingDate(String arg)
	{
		wOrderingDate = arg;
	}

	/**
	 * 発注日を取得します。
	 * @return 発注日
	 */
	public String getOrderingDate()
	{
		return wOrderingDate;
	}

	/**
	 * 出荷先コードをセットします。
	 * @param arg セットする出荷先コード
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	/**
	 * 出荷先コードを取得します。
	 * @return 出荷先コード
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	/**
	 * 出荷先名称をセットします。
	 * @param arg セットする荷主名称
	 */
	public void setCustomerName(String arg)
	{
		wCustomerName = arg;
	}

	/**
	 * 出荷先名称を取得します。
	 * @return 荷主名称
	 */
	public String getCustomerName()
	{
		return wCustomerName;
	}

	/**
	 * 商品コードをセットします。
	 * @param arg セットする商品コード
	 */
	public void setItemCode(String arg)
	{
		wItemCode = arg;
	}

	/**
	 * 商品コードを取得します。
	 * @return 商品コード
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	/**
	 * 商品コード(リストセル)をセットします。
	 * @param arg セットする商品コード(リストセル)
	 */
	public void setItemCodeL(String arg)
	{
		wItemCodeL = arg;
	}

	/**
	 * 商品コード(リストセル)を取得します。
	 * @return 商品コード(リストセル)
	 */
	public String getItemCodeL()
	{
		return wItemCodeL;
	}

	/**
	 * 商品名称をセットします。
	 * @param arg セットする商品名称
	 */
	public void setItemName(String arg)
	{
		wItemName = arg;
	}

	/**
	 * 商品名称を取得します。
	 * @return 商品名称
	 */
	public String getItemName()
	{
		return wItemName;
	}

	/**
	 * 表示順１をセットします。
	 * @param arg セットする表示順
	 */
	public void setDspOrder(String arg)
	{
		wDspOrder = arg;
	}

	/**
	 * 表示順１を取得します。
	 * @return 表示順
	 */
	public String getDspOrder()
	{
		return wDspOrder;
	}

	/**
	 * 表示順２をセットします。
	 * @param arg セットする表示順２
	 */
	public void setDspOrder2(String arg)
	{
		wDspOrder2 = arg;
	}

	/**
	 * 表示順２を取得します。
	 * @return 表示順２
	 */
	public String getDspOrder2()
	{
		return wDspOrder2;
	}
	
	/**
	 * 伝票No.をセットします。
	 * @param arg セットする伝票No.
	 */
	public void setShippingTicketNo(String arg)
	{
		wShippingTicketNo = arg;
	}

	/**
	 * 伝票No.を取得します。
	 * @return 伝票No.
	 */
	public String getShippingTicketNo()
	{
		return wShippingTicketNo;
	}

	/**
	 * 行No.をセットします。
	 * @param arg セットする行No.
	 */
	public void setShippingLineNo(int arg)
	{
		wShippingLineNo = arg;
	}

	/**
	 * 行No.を取得します。
	 * @return 行No.
	 */
	public int getShippingLineNo()
	{
		return wShippingLineNo;
	}

	/**
	 * ケース入数をセットします。
	 * @param arg セットするケース入数
	 */
	public void setEnteringQty(int arg)
	{
		wEnteringQty = arg;
	}

	/**
	 * ケース入数を取得します。
	 * @return ケース入数
	 */
	public int getEnteringQty()
	{
		return wEnteringQty;
	}

	/**
	 * ボール入数をセットします。
	 * @param arg セットするボール入数
	 */
	public void setBundleEnteringQty(int arg)
	{
		wBundleEnteringQty = arg;
	}

	/**
	 * ボール入数を取得します。
	 * @return ボール入数
	 */
	public int getBundleEnteringQty()
	{
		return wBundleEnteringQty;
	}

	/**
	 * 予定ケース数(作業･ホスト)をセットします。
	 * @param arg セットする予定ケース数(作業･ホスト)
	 */
	public void setPlanCaseQty(int arg)
	{
		wPlanCaseQty = arg;
	}

	/**
	 * 予定ケース数(作業･ホスト)を取得します。
	 * @return 予定ケース数(作業･ホスト)
	 */
	public int getPlanCaseQty()
	{
		return wPlanCaseQty;
	}

	/**
	 * 予定ピース数(作業･ホスト)をセットします。
	 * @param arg セットする予定ピース数(作業･ホスト)
	 */
	public void setPlanPieceQty(int arg)
	{
		wPlanPieceQty = arg;
	}

	/**
	 * 予定ピース数(作業･ホスト)を取得します。
	 * @return 予定ピース数(作業･ホスト)
	 */
	public int getPlanPieceQty()
	{
		return wPlanPieceQty;
	}

	/**
	 * 出荷ケース数をセットします。
	 * @param arg セットする出荷ケース数
	 */
	public void setResultCaseQty(int arg)
	{
		wResultCaseQty = arg;
	}

	/**
	 * 出荷ケース数を取得します。
	 * @return 出荷ケース数
	 */
	public int getResultCaseQty()
	{
		return wResultCaseQty;
	}

	/**
	 * 出荷ピース数をセットします。
	 * @param arg セットする出荷ピース数
	 */
	public void setResultPieceQty(int arg)
	{
		wResultPieceQty = arg;
	}

	/**
	 * 出荷ピース数を取得します。
	 * @return 出荷ピース数
	 */
	public int getResultPieceQty()
	{
		return wResultPieceQty;
	}

	/**
	 * 欠品区分をセットします。
	 * @param arg セットする欠品区分
	 */
	public void setShortage(boolean arg)
	{
		wShortage = arg;
	}

	/**
	 * 欠品区分を取得します。
	 * @return 欠品区分
	 */
	public boolean getShortage()
	{
		return wShortage;
	}

	/**
	 * 賞味期限をセットします。
	 * @param arg セットする賞味期限
	 */
	public void setUseByDate(String arg)
	{
		wUseByDate = arg;
	}

	/**
	 * 賞味期限を取得します。
	 * @return 賞味期限
	 */
	public String getUseByDate()
	{
		return wUseByDate;
	}

	/**
	 * 開始出荷予定日をセットします。
	 * @param arg セットする開始出荷予定日
	 */
	public void setFromPlanDate(String arg)
	{
		wFromPlanDate = arg;
	}

	/**
	 * 開始出荷予定日を取得します。
	 * @return 開始出荷予定日
	 */
	public String getFromPlanDate()
	{
		return wFromPlanDate;
	}

	/**
	 * 終了出荷予定日をセットします。
	 * @param arg セットする終了出荷予定日
	 */
	public void setToPlanDate(String arg)
	{
		wToPlanDate = arg;
	}

	/**
	 * 終了出荷予定日を取得します。
	 * @return 終了出荷予定日
	 */
	public String getToPlanDate()
	{
		return wToPlanDate;
	}

	/**
	 * 開始伝票No.をセットします。
	 * @param arg セットする開始伝票No.
	 */
	public void setFromTicketNo(String arg)
	{
		wFromTicketNo = arg;
	}

	/**
	 * 開始伝票No.を取得します。
	 * @return セットする開始伝票No.
	 */
	public String getFromTicketNo()
	{
		return wFromTicketNo;
	}

	/**
	 * 終了伝票No.をセットします。
	 * @param arg セットする終了伝票No.
	 */
	public void setToTicketNo(String arg)
	{
		wToTicketNo = arg;
	}

	/**
	 * 終了伝票No.を取得します。
	 * @return セットする終了伝票No.
	 */
	public String getToTicketNo()
	{
		return wToTicketNo;
	}

	/**
	 * 作業状態をセットします。
	 * @param arg セットする作業状態
	 */
	public void setStatusFlag(String arg)
	{
		wStatusFlag = arg;
	}

	/**
	 * 作業状態を取得します。
	 * @return 作業状態
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}

	/**
	 * 作業状態(リストセル)をセットします。
	 * @param arg セットする作業状態(リストセル)
	 */
	public void setStatusFlagL(String arg)
	{
		wStatusFlagL = arg;
	}

	/**
	 * 作業状態(リストセル)を取得します。
	 * @return 作業状態(リストセル)
	 */
	public String getStatusFlagL()
	{
		return wStatusFlagL;
	}
	
	/**
	 * 作業状態名称をセットします。
	 * @param arg セットする作業状態名称
	 */
	public void setStatusName(String arg)
	{
		wStatusName = arg;
	}

	/**
	 * 作業状態名称を取得します。
	 * @return 作業状態名称
	 */
	public String getStatusName()
	{
		return wStatusName;
	}

	/**
	 * 仕入先コードをセットします。
	 * @param arg セットする仕入先コード
	 */
	public void setSupplierCode(String arg)
	{
		wSupplierCode = arg;
	}

	/**
	 * 仕入先コードを取得します。
	 * @return 仕入先コード
	 */
	public String getSupplierCode()
	{
		return wSupplierCode;
	}

	/**
	 * 仕入先名称をセットします。
	 * @param arg セットする仕入先名称
	 */
	public void setSupplierName(String arg)
	{
		wSupplierName = arg;
	}

	/**
	 * 仕入先名称を取得します。
	 * @return 仕入先名称
	 */
	public String getSupplierName()
	{
		return wSupplierName;
	}

	/**
	 * 入荷伝票No.をセットします。
	 * @param arg セットする入荷伝票No.
	 */
	public void setInstockTicketNo(String arg)
	{
		wInstockTicketNo = arg;
	}

	/**
	 * 入荷伝票No.を取得します。
	 * @return 入荷伝票No.
	 */
	public String getInstockTicketNo()
	{
		return wInstockTicketNo;
	}

	/**
	 * 入荷行No.をセットします。
	 * @param arg セットする入荷行No.
	 */
	public void setInstockLineNo(int arg)
	{
		wInstockLineNo = arg;
	}

	/**
	 * 入荷行No.を取得します。
	 * @return 入荷行No.
	 */
	public int getInstockLineNo()
	{
		return wInstockLineNo;
	}

	/**
	 * ケースITFをセットします。
	 * @param arg セットするケースITF
	 */
	public void setITF(String arg)
	{
		wITF = arg;
	}

	/**
	 * ケースITFを取得します。
	 * @return ケースITF
	 */
	public String getITF()
	{
		return wITF;
	}

	/**
	 * ボールITFをセットします。
	 * @param arg セットするボールITF
	 */
	public void setBundleITF(String arg)
	{
		wBundleITF = arg;
	}

	/**
	 * ボールITFを取得します。
	 * @return ボールITF
	 */
	public String getBundleITF()
	{
		return wBundleITF;
	}

	/**
	 * 開始出荷日をセットします。
	 * @param arg セットする開始出荷日
	 */
	public void setFromShippingDate(String arg)
	{
		wFromShippingDate = arg;
	}

	/**
	 * 開始出荷日を取得します。
	 * @return 開始出荷日
	 */
	public String getFromShippingDate()
	{
		return wFromShippingDate;
	}

	/**
	 * 終了出荷日をセットします。
	 * @param arg セットする終了出荷日
	 */
	public void setToShippingDate(String arg)
	{
		wToShippingDate = arg;
	}

	/**
	 * 終了出荷日を取得します。
	 * @return 終了出荷日
	 */
	public String getToShippingDate()
	{
		return wToShippingDate;
	}

	/**
	 * 出荷日をセットします。
	 * @param arg セットする出荷日
	 */
	public void setShippingDate(String arg)
	{
		wShippingDate = arg;
	}

	/**
	 * 出荷日を取得します。
	 * @return 出荷日
	 */
	public String getShippingDate()
	{
		return wShippingDate;
	}

	/**
	 * 欠品ケース数をセットします。
	 * @param arg セットする欠品ケース数
	 */
	public void setShortageCaseQty(int arg)
	{
		wShortageCaseQty = arg;
	}

	/**
	 * 欠品ケース数を取得します。
	 * @return 欠品ケース数
	 */
	public int getShortageCaseQty()
	{
		return wShortageCaseQty;
	}

	/**
	 * 欠品ピース数をセットします。
	 * @param arg セットする欠品ピース数
	 */
	public void setShortagePieceQty(int arg)
	{
		wShortagePieceQty = arg;
	}

	/**
	 * 欠品ピース数を取得します。
	 * @return 欠品ピース数
	 */
	public int getShortagePieceQty()
	{
		return wShortagePieceQty;
	}

	/**
	 * ＴＣ/ＤＣ区分をセットします。
	 * @param arg セットするＴＣ/ＤＣ区分
	 */
	public void setTcdcFlag(String arg)
	{
		wTcdcFlag = arg;
	}

	/**
	 * ＴＣ/ＤＣ区分を取得します。
	 * @return ＴＣ/ＤＣ区分
	 */
	public String getTcdcFlag()
	{
		return wTcdcFlag;
	}
	
	/**
	 * ＴＣ/ＤＣ区分名称を取得します。
	 * @return ＴＣ/ＤＣ区分名称
	 */
	public void setTcdcFlagName(String string)
	{
		wTcdcFlagName = string;
	}
	
	/**
	 * ＴＣ/ＤＣ区分名称をセットします。
	 */
	public String getTcdcFlagName()
	{
		return wTcdcFlagName;
	}

	/**
	 * 出荷予定削除一覧リスト発行区分をセットします。
	 * @param arg セットする出荷予定削除一覧リスト発行区分
	 */
	public void setStorageListFlg(boolean arg)
	{
		wShippingDeleteListFlg = arg;
	}

	/**
	 * 出荷予定削除一覧リスト発行区分を取得します。
	 * @return 出荷予定削除一覧リスト発行区分
	 */
	public boolean getStorageListFlg()
	{
		return wShippingDeleteListFlg;
	}

	/**
	 * 予定総数をセットします。
	 * @param arg セットする予定総数
	 */
	public void setTotalPlanQty(int arg)
	{
		wTotalPlanQty = arg;
	}

	/**
	 * 予定総数を取得します。
	 * @return 予定総数
	 */
	public int getTotalPlanQty()
	{
		return wTotalPlanQty;
	}

	/**
	 * 実績総数をセットします。
	 * @param arg セットする実績総数
	 */
	public void setTotalResultQty(int arg)
	{
		wTotalResultQty = arg;
	}

	/**
	 * 実績総数を取得します。
	 * @return 実績総数
	 */
	public int getTotalResultQty()
	{
		return wTotalResultQty;
	}

	/**
	 * ためうち行No.をセットします。
	 * @param arg セットするためうち行No.
	 */
	public void setRowNo(int arg)
	{
		wRowNo = arg;
	}

	/**
	 * ためうち行No.を取得します。
	 * @return ためうち行No.
	 */
	public int getRowNo()
	{
		return wRowNo;
	}

	/**
	 * 出荷予定一意キーをセットします。
	 * @param arg セットする出荷予定一意キー
	 */
	public void setShippingPlanUkey(String arg)
	{
		wShippingPlanUkey = arg;
	}

	/**
	 * 出荷予定一意キーを取得します。
	 * @return 出荷予定一意キー
	 */
	public String getShippingPlanUkey()
	{
		return wShippingPlanUkey;
	}

	/**
	 * ケース/ピース区分をセットします。
	 * @param arg セットするケース/ピース区分
	 */
	public void setCasePieceFlag(String arg)
	{
		wCasePieceFlag = arg;
	}

	/**
	 * ケース/ピース区分を取得します。
	 * @return ケース/ピース区分
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	/**
	 * バッチNo.(スケジュールNo.)をセットします。
	 * @param arg セットするバッチNo.(スケジュールNo.)
	 */
	public void setBatchNo(String arg)
	{
		wBatchNo = arg;
	}

	/**
	 * バッチNo.(スケジュールNo.)を取得します。
	 * @return バッチNo.(スケジュールNo.)
	 */
	public String getBatchNo()
	{
		return wBatchNo;
	}

	/**
	 * 登録区分をセットします。
	 * @param arg セットする登録区分
	 */
	public void setRegistKbn(String arg)
	{
		wRegistKbn = arg;
	}

	/**
	 * 登録区分を取得します。
	 * @return 登録区分
	 */
	public String getRegistKbn()
	{
		return wRegistKbn;
	}

	/**
	 * 登録日時をセットします。
	 * @param arg セットする登録日時
	 */
	public void setRegistDate(java.util.Date arg)
	{
		wRegistDate = arg;
	}

	/**
	 * 登録日時を取得します。
	 * @return 登録日時
	 */
	public java.util.Date getRegistDate()
	{
		return wRegistDate;
	}

	/**
	 * 登録処理名をセットします。
	 * @param arg セットする登録処理名
	 */
	public void setRegistPName(String arg)
	{
		wRegistPName = arg;
	}

	/**
	 * 登録処理名を取得します。
	 * @return 登録処理名
	 */
	public String getRegistPName()
	{
		return wRegistPName;
	}

	/**
	 * 最終更新日時をセットします。
	 * @param arg セットする最終更新日時
	 */
	public void setLastUpdateDate(java.util.Date arg)
	{
		wLastUpdateDate = arg;
	}

	/**
	 * 最終更新日時を取得します。
	 * @return 最終更新日時
	 */
	public java.util.Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	/**
	 * 最終更新処理名をセットします。
	 * @param arg セットする最終更新処理名
	 */
	public void setLastUpdatePName(String arg)
	{
		wLastUpdatePName = arg;
	}

	/**
	 * 最終更新処理名を取得します。
	 * @return 最終更新処理名
	 */
	public String getLastUpdatePName()
	{
		return wLastUpdatePName;
	}

	/**
	 * 作業No.をセットします。
	 * @param arg セットする作業No.
	 */
	public void setJobNo(String arg)
	{
		wJobNo = arg;
	}

	/**
	 * 作業No.を取得します。
	 * @return 作業No.
	 */
	public String getJobNo()
	{
		return wJobNo;
	}

	/**
	 * 検索フラグ(予定／実績)をセットします。
	 * @param arg セットする検索フラグ
	 */
	public void setSearchFlag(String arg)
	{
		wSearchFlag = arg;
	}

	/**
	 * 検索フラグ(予定／実績)を取得します。
	 * @return 検索フラグ
	 */
	public String getSearchFlag()
	{
		return wSearchFlag;
	}
	
	/**
	 * 選択Box有効/無効フラグをセットします。
	 * @param arg セットする有効/無効フラグ
	 */
	public void setSelectBoxFlag(boolean arg)
	{
		wSelectBoxFlag = arg;
	}

	/**
	 * 選択Box有効/無効フラグを取得します。
	 * @return 有効/無効フラグ
	 */
	public boolean getSelectBoxFlag()
	{
		return wSelectBoxFlag;
	}
	
	/**
	 * 選択BoXON/OFFをセットします。
	 * @param arg セットするON/OFF
	 */
	public void setSelectBoxCheck(boolean arg)
	{
		wSelectBoxCheck = arg;
	}

	/**
	 * 選択BoxON/OFFを取得します。
	 * @return ON/OFF
	 */
	public boolean getSelectBoxCheck()
	{
		return wSelectBoxCheck;
	}

	/**
	 * 作業状態をセットします。
	 * @param arg セットする作業状態
	 */
	public void setSearchStatus(String[] arg)
	{
		wSearchStatus = arg;
	}

	/**
	 * 作業状態を取得します。
	 * @return 作業状態
	 */
	public String[] getSearchStatus()
	{
		return wSearchStatus;
	}
	
	/**
	 * 実績報告区分をセットします。
	 * @param arg セットする実績報告区分
	 */
	public void setReportFlag(String arg)
	{
		wReportFlag = arg;
	}

	/**
	 * 実績報告区分を取得します。
	 * @return 実績報告区分
	 */
	public String getReportFlag()
	{
		return wReportFlag;
	}

	/**
	 * 実績報告区分名称をセットします。
	 * @param arg セットする実績報告区分名称
	 */
	public void setReportFlagName(String arg)
	{
		wReportFlagName = arg;
	}

	/**
	 * 実績報告区分名称を取得します。
	 * @return 実績報告区分名称
	 */
	public String getReportFlagName()
	{
		return wReportFlagName;
	}

    /**
     * 桁数チェック有無フラグを取得する。
     * @return Returns the wLineCheckFlag.
     */
    public boolean getLineCheckFlag()
    {
        return wLineCheckFlag;
    }
    
    /**
     * 桁数チェック有無フラグをセットする。
     * @param lineCheckFlag The wLineCheckFlag to set.
     */
    public void setLineCheckFlag(boolean lineCheckFlag)
    {
        wLineCheckFlag = lineCheckFlag;
    }
    /* 2006/6/8 END */
}
