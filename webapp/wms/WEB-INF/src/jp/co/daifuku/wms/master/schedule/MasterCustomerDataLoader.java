//$Id: MasterCustomerDataLoader.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.schedule;


/*
* Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
*
* This software is the proprietary information of DAIFUKU Co.,Ltd.
* Use is subject to license terms.
*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;

/**
* Designer : hota <BR>
* Maker : hota <BR>
* <BR>
* <CODE>MasterCustomerDataLoader</CODE>クラスは、出荷先マスタデータ取込み処理を行うクラスです。<BR>
* <CODE>AbstractExternalDataLoader</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
* このクラスが持つメソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないます。<BR>
* 取り込みデータから登録情報を作成します。<BR>
* このクラスでは以下の処理を行います。<BR>
* <BR>
* 1.データ取込み処理（<CODE>load(Connection conn, Locale locale, Parameter searchParam)</CODE>メソッド）<BR>
* <BR>
* <DIR>
*   ConnectionオブジェクトとWareNaviSystemオブジェクトと、Parameterオブジェクトをパラメータとして受け取り、
*   出荷先マスタファイル(CSVファイル)から<BR>
*   データベースにデータを登録します。(出庫予定情報、在庫情報（在庫パッケージなしの場合のみ）) <BR>
*   また、登録した出庫予定情報から引当処理によって作業情報を作成し、在庫情報を更新します。
*   メソッド内部でエラーが発生した場合はFalseを返します。<BR>
*   入力情報が異常で取込みを行うことのできないデータがあった場合、ファイル名、行数をログに出力します。<BR>
*   入力情報が既に登録済みで未開始、削除以外の場合は、スキップしその旨をログに出力します<BR>
*   入力情報が既に登録済みで未開始のデータであった場合は上書き取り込みを行いその旨をログに出力します<BR>
*   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
*   処理の開始時に作業情報、在庫情報を排他処理のためロックします。<BR>
*   <BR>
*   ＜パラメータ＞ 必須入力<BR>
*   Connectionオブジェクト <BR>
*   WareNaviSystemオブジェクト <BR>
*   SystemParameterオブジェクト <BR>
*   <BR>
*   ＜処理詳細＞ <BR>
*   1.取込履歴ファイル作成<BR>
*     <DIR>
*     ファイル名は日付(YYYYMMDD)＋時刻（HHMISS）＋取込ファイル名となる<BR>
*     </DIR>
*   2.データ件数初期化<BR>
*     <DIR>
*     全読み取りデータ件数、登録データ件数、更新データ件数、スキップデータ件数<BR>
*     </DIR>
*   3.データ登録フラグ を宣言<BR>
*     <DIR>
*     Falseで宣言します。１件でも登録データがある場合にTrueとします<BR>
*     </DIR>
*   4.クラスインスタンス作成<BR>
*     <DIR>
*     ･DataLoadCsvReaderインスタンス作成<BR>
*     環境ファイル（EnvironmentInformation）に定義された予定ファイルからデータを<BR>
*     読み込むクラスです。<BR>
*     ･取込みエラーファイル作成クラスインスタンス作成<BR>
*     (フォーマットは取込ファイルと同じフォーマット+エラー内容とする。)<BR>
*     ･SequenceHandlerインスタンス作成<BR>
*     </DIR>
*   5.予定ファイルから対応する予定日の次の１行を取得<BR>
*     <DIR>
*     取得できなくなるまでLoopします<BR>
*     <BR>
*     - LOOP開始 -<BR>
*       <DIR>
*       ・同一データチェック<BR>
*         <DIR>
*         ・同一データがない場合、新規登録*<BR>
*         ・同一データがあって、未開始以外の場合<BR>
*          エラーとして処理中断<BR>
*         ・同一データがあって、未開始の場合<BR>
*          該当同一データの引当を解除する*<BR>
*          該当同一データの予定一意キーに一致する欠品情報がある場合、削除する<BR>
*         </DIR>
*       ・同一データチェックで*の場合、以下の順で新規登録を行う<BR>
*        予定情報<BR>
*        作業情報<BR>
*        在庫情報<BR>
*        欠品情報(欠品が発生した場合のみ)<BR>
*       ・欠品情報が作成されている場合、引当を解除し、欠品情報を登録する。
*       </DIR>
* 
*       - LOOP終了 -<BR>
*     <BR>
*     </DIR>
*   </DIR>
* <BR>
* <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
* <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
* <TR><TD>2004/10/21</TD><TD>M.Inoue</TD><TD>新規作成</TD></TR>
* </TABLE>
* <BR>
* @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
* @author  $Author: mori $
*/
public class MasterCustomerDataLoader extends AbstractExternalDataLoader
{
	// Class fields --------------------------------------------------
	/** 処理結果 (OK) */
    private static final int RET_OK = 0 ;

	/** 処理結果 (NG) */
    private static final int RET_NG = -1 ;

	/** 処理結果 (整合性エラー: 名称の重複) */
    private static final int RET_CONSIST_NAME_EXIST = 3 ;
    
	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
    protected final String pName = "MasterCustomerDataLoader";
    
    /**
     * 荷主マスタ用オペレータ
     */
	protected ConsignorOperator wConsigOperator = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Comment for constructor (コンストラクタのコメント)
	 */
	public MasterCustomerDataLoader()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * ConnectionオブジェクトとLocaleオブジェクト、SystemParameterオブジェクトをパラメータとして受け取り、出庫予定データを取込みます。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param wns  WareNaviSystemオブジェクト
	 * @param searchParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @return Comment for return value
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		WareNaviSystemManager wms = new WareNaviSystemManager(conn);

		wConsigOperator = new ConsignorOperator(conn);
		
		return creaCustomerMaster(conn, wns, searchParam);

	}
	// Package methods -----------------------------------------------

	
	/**
	 * 出荷先マスタファイルのパスとファイル名を取得します。
	 * 
	 * @return String
	 * @throws ScheduleException
	 */
	protected String GetRetrievalFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "MASTER_CUSTOMER");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "MASTER_CUSTOMER");
			return DataLoadPath + DataLoadFile;
		}
		catch (ReadWriteException e)
		{
			//  環境情報ファイルが見つかりませんでした。{0}
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), "DataLoadCsvReader", msg);
			throw new ScheduleException();
		}
	}

	/**
	 * ファイルのレコードをパラメータにセットします。
	 * 
	 * @return String
	 * @throws CsvIllegalDataException
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	public MasterParameter setParameter(DataLoadCsvReader reader) throws ScheduleException, CsvIllegalDataException
	{

		MasterParameter mstParam = new MasterParameter();

		// 荷主コード項目が無効の場合と空白SETの場合には、"0"に置換
		if (reader.getEnable("CONSIGNOR_CODE"))
		{
			if (!reader.getAsciiValue("CONSIGNOR_CODE").equals(""))
			{
				mstParam.setConsignorCode(reader.getAsciiValue("CONSIGNOR_CODE"));
			}
			else
			{
				mstParam.setConsignorCode("0");
			}
		}
		else
		{
			mstParam.setConsignorCode("0");
		}
		
		// 出荷先コード
		if (reader.getEnable("CUSTOMER_CODE"))
		{
			if (!reader.getAsciiValue("CUSTOMER_CODE").equals(""))
			{
				mstParam.setCustomerCode(reader.getAsciiValue("CUSTOMER_CODE"));
			}
			else
			{
				mstParam.setCustomerCode("0");
			}
		}
		else
		{
			mstParam.setCustomerCode("0");
		}
		// 出荷先名称
		if (reader.getEnable("CUSTOMER_NAME"))
		{
		    mstParam.setCustomerName(reader.getValue("CUSTOMER_NAME"));
		}
		// 郵便番号
		if (reader.getEnable("POSTAL_CODE"))
		{
		    mstParam.setPostalCode(reader.getValue("POSTAL_CODE"));
		}
		
		// 県名
		if (reader.getEnable("PREFECTURE"))
		{
		    mstParam.setPrefecture(reader.getValue("PREFECTURE"));
		}
		
		// 住所
		if (reader.getEnable("ADRESS1"))
		{
		    mstParam.setAddress(reader.getValue("ADRESS1"));
		}
		
		// ビル名等
		if (reader.getEnable("ADRESS2"))
		{
		    mstParam.setAddress2(reader.getValue("ADRESS2"));
		}
		
		// 連絡先1
		if (reader.getEnable("CONTACT1"))
		{
		    mstParam.setContact1(reader.getValue("CONTACT1"));
		}
		
		// 連絡先2
		if (reader.getEnable("CONTACT2"))
		{
		    mstParam.setContact2(reader.getValue("CONTACT2"));
		}
		
		// 連絡先3
		if (reader.getEnable("CONTACT3"))
		{
		    mstParam.setContact3(reader.getValue("CONTACT3"));
		}


		return mstParam;
	}
	
	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 出荷先マスタ情報を作成します。
	 * 
	 * 
	 * @param conn データベースへのコネクションオブジェクト
	 * @param wns  WareNaviSystemオブジェクト
	 * @param searchParam <CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws ReadWriteException データベースへの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected boolean creaCustomerMaster(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
	    int iRet = 0;
		// パラメータより作業予定日を取得
		SystemParameter param = (SystemParameter) searchParam;
		String consignorCode = param.getConsignorCode();
		String batch_seqno = "";
		String wFileName = "";
		// 既存データスキップフラグ
		boolean existingFlag = false;

		// 件数初期化(全読み取りデータ件数、登録データ件数、更新データ件数、スキップデータ件数)
		InitItemCount();

		// DataLoadCsvReaderインスタンス作成
		// 取込履歴ファイル作成はDataLoadCsvReaderクラスで行う
		DataLoadCsvReader DRR = null;

		// 取込みエラーファイル作成クラスインスタンス作成
		DataLoadStatusCsvFileWriter errlist = new DataLoadStatusCsvFileWriter();

		// データ登録処理
		CustomerOperator conOperator = new CustomerOperator(conn);

		try
		{
			// 出荷先マスタファイル名を取得
			wFileName = GetRetrievalFile();

			// CSVファイルを読み込みクラス設定
			DRR = new DataLoadCsvReader(DataLoadCsvReader.LOADTYPE_MASTERCUSTOMER, consignorCode, "CONSIGNOR_CODE");

			// ファイルサイズチェック
			if (DRR.getFileSize() <= 0)
			{
				// 6023289=レコード数が0件です。ファイル名:{0}
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			// 登録パラメータ
			MasterParameter mstParam = null;
			// 二重登録チェックフラグ
			boolean updateFlag = false;
			boolean record_check_flag = true;
			
			// 出荷先マスタファイルから次の１行を取得
			// 取得できなくなるまでLoopします
			while (DRR.DataNext())
			{
				existingFlag = false;
				updateFlag = false;
				
				// 全読み取りデータ件数をカウントアップ
				wAllItemCount++;
				// エラー用ファイル名称の取得
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}
				// ファイルの1レコードをパラメータにセットする。
				mstParam = setParameter(DRR);
	
				
			    Customer ent = new Customer();
				ent.setConsignorCode(mstParam.getConsignorCode());
				ent.setCustomerCode(mstParam.getCustomerCode());
				ent.setCustomerName1(mstParam.getCustomerName());
				ent.setPostalCode(mstParam.getPostalCode());
				ent.setPrefecture(mstParam.getPrefecture());
				ent.setAddress(mstParam.getAddress());
				ent.setAddress2(mstParam.getAddress2());
				ent.setContact1(mstParam.getContact1());
				ent.setContact2(mstParam.getContact2());
				ent.setContact3(mstParam.getContact3());

				ent.setLastUsedDate(new Date());
				ent.setLastUpdatePname(pName);
				
				if (!check(mstParam, DRR))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}

				if (conOperator.exist(ent)) 
				{
				    // 6022003=２重取り込みのためスキップしました。ファイル:{0} 行:{1}
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6022003, pName, msg);
					setSkipFlag(true);
				    existingFlag = true;
				}
				
				iRet = 	conOperator.checkCustomerName(ent);

				if(iRet == RET_CONSIST_NAME_EXIST)
				{
					// 6023467 = 指定された名称はすでに使用されているため登録できません。
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6023467, pName, msg);
					setSkipFlag(true);
				    existingFlag = true;
				}
				
				// 既存データチェック
				if (!existingFlag)
				{


					// 予定情報の登録処理
					conOperator.create(ent);			
					setRegistFlag(true);
					updateFlag = true;

				}

			}
			if (!record_check_flag )
			{
				// 6023326=異常なデータがあったため、処理を中止しました。ログを参照してください。
				setMessage("6023326");
				return false;
			}
			return true;
			
		}
		catch (FileNotFoundException e)
		{
			// ファイルが見つかりませんでした。{0}
			RmiMsgLogClient.write(new TraceHandler(6003009, e), pName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//6006020 = ファイル入出力エラー
			RmiMsgLogClient.write(new TraceHandler(6006020, e), pName);
			throw new ScheduleException();
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (CsvIllegalDataException e)
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			setMessage("6023326");
			
			// 6026085 = 取り込み情報に異常なデータがありました。詳細:{0}
			Object[] msg = new Object[1]; 
			msg[0] = e.getMessage() ;
			RmiMsgLogClient.write(6026085, pName, msg);
			return false;
		} 
		catch (InvalidDefineException e)
		{
			// 6023326 = 取り込み予定情報に異常なデータがあったため、処理を中止しました。
			setMessage("6023326");
			// 6026006=データフォーマットが不正なデータがありました。ファイル:{0} 行:{1}
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026006, pName, msg);
			return false;
		}
		finally
		{
			// １件でも対象データがあった場合はエラーファイルに書き込む
			if (!errlist.getFileName().equals(""))
			{
				errlist.writeStatusCsvFile();
			}
		}
	}
	
	/**
	 * 指定されたデータ内の項目チェック処理を行います。
	 * エラーメッセージのセット、取込みエラーファイルへの書き込みは呼び出し側で行います。
	 * 
	 * @param param ファイルから読み込んだデータを保持するパラメータクラス
	 * @param DRR DataLoadCsvReaderオブジェクトをを指定します。
	 * @return 正常はtrue, 異常発生時はfalseを返します。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean check(
			MasterParameter param,
			DataLoadCsvReader DRR)
		throws ReadWriteException, ScheduleException
	{
		// 荷主マスタにパラメータの荷主コードが存在するかチェックを行う。
		Consignor consignor = new Consignor();
		consignor.setConsignorCode(param.getConsignorCode());

		if (!wConsigOperator.exist(consignor))
		{
			// 6023463=荷主コードがマスタに登録されていません。ファイル:{0} 行:{1}
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6023463, pName, msg);
			return false;
		}
	
		return true;
	}
}
//end of class
