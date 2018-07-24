// $Id: IniFileOperation.java,v 1.1.1.1 2006/08/17 09:33:44 mori Exp $
 package jp.co.daifuku.common.text ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;

/**
 * リソースファイルをINIファイルのように扱うメソッドを提供します。
 * セクション名、キーを指定して読み書きが可能です。<BR>
 * ただし、リソースファイルは指定したフォーマットで作成する必要があります。<BR>
 * セクション名、キー、バリュー、キーのコメントのセットで記述して下さい。<BR>
 * <BR><BR>
 * リソースファイルのフォーマット<BR>
 * <FONT COLOR="RED">[SECTION]</FONT><BR>
 * KEY1 =  VALUE1<BR>
 * KEY2 =  VALUE2<BR>
 * KEY3 =  VALUE3<BR>
 * <FONT COLOR="BLUE">#  KEY1 に対するコメント</FONT><BR>
 * <FONT COLOR="BLUE">#  KEY2 に対するコメント</FONT><BR>
 * <FONT COLOR="BLUE">#  KEY3 に対するコメント</FONT><BR>
 * <BR>
 * コンストラクタでパス＋ファイル名をセットしてset()を呼び出し、行ったあとにはFlash()を呼ぶ必要があります。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/06</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:44 $
 * @author  $Author: mori $
 */
public class IniFileOperation
{

	// Class fields --------------------------------------------------
	/**
	 * セクション区切り(開始)
	 */
	private static final String SEC_START = "[";
	/**
	 * セクション区切り(終了)
	 */
	private static final String SEC_END = "]";
	/**
	 * コメント文字
	 */
	private static final String COMMENT_KEY = "#";
	/**
	 * コメント開始文字
	 */
	private static final String COMMENT_START = ":";

	// Class variables -----------------------------------------------
	/**
	 * リソースファイルパス
	 */
	private String fname;
	/**
	 * KEYの区切り文字。デフォルトは"="です。
	 */
	private String wSeparate = "=";
	/**
	 * リソースファイルをread()メソッドで読み込んだときのそのファイルの最新更新時刻
	 */
	private long wIniModTime;
	/**
	 * ファイル内容格納用Vector
	 */
	private Vector lines = new Vector();

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:33:44 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * ファイル名を取得し、ファイルを読み込みます。
	 * @param fname ファイル名
	 */
	public IniFileOperation(String fname) throws ReadWriteException{
		this.fname = fname;
		read();
		// 最新更新時刻をセット
		setInitialLastModified();
	}

	/**
	 * ファイル名、区切り文字を取得し、ファイルを読み込みます。
	 * KEYとなる項目と値の間が"="ではないときに使用します。
	 * @param fname ファイル名
	 * @param sepa  KEYと値の区切り文字
	 */
	public IniFileOperation(String fname, String sepa) throws ReadWriteException{
		this.fname = fname;
		this.wSeparate = sepa;
		read();
		// ??a?
		setInitialLastModified();
	}

	// Public methods ------------------------------------------------
	/**
	 * INIファイルとしてリソースファイルに書き込みます。
	 * set()を呼び出した後は必ずこのメソッドを呼ぶ必要があります。
	 */
	public void flush() throws ReadWriteException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fname));
			for (int i = 0; i < lines.size(); i++) {
				String str = (String)lines.elementAt(i);
				writer.write(str);
				writer.newLine();
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ReadWriteException(e.getMessage());
		}
	}

	/**
	 * セクションは関係なくキーを捜し、設定値を返します。
	 * @param key 検索するKEY
     * @return    設定値
	 */
	public String get(String key) {

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				// "="を検索
				int index = str.indexOf(wSeparate);
				if(index >= 0) {
					// "="までのKeyを取得
					String tempkey = str.substring(0, index).trim();

					// キーを見つけたら、その値を返す。
					if(tempkey.equals(key)) {
						return str.substring(index+1).trim();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 指定したセクションのキーを捜し、設定値を返します。
	 * @param section 検索するセクション名
	 * @param key     検索するKEY
     * @return        設定値
	 */
	public String get(String section, String key) {
		boolean insideSection = false;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					// "="を検索
					int index = str.indexOf(wSeparate);
					if(index >= 0) {
						// "="までのKeyを取得
						String tempkey = str.substring(0, index).trim();

						// セクション内で、キーを見つけたら、その値を返す。
						if(tempkey.equals(key)) {
							return str.substring(index+1).trim();
						}
					}
					// 別のセクションに移ったら、見つからなかった。
					// 該当セクションは２つ以上はないものとする。
					if(str.indexOf(SEC_START)==0) return null;
				}
				else {
					// セクション区切りを捜す。
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションをみつけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * セクションを指定せずにキーのコメントを返します。
	 * @param key     検索するKEY
     * @return        コメント
	 */
	public String getComment( String key ) {

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0)
			{
				int keylen = str.indexOf(COMMENT_START);

				// COMMNET_KEY, COMMENT_STARTを見つけたら、さらにキーを検索
				if(str.indexOf(COMMENT_KEY)==0 && keylen > 0) {

					// COMMENT_KEYから":"までのStringを取得
					String keystr = str.substring(1, keylen-1).trim();
					// キーを見つけたら、そのコメントを返します。
					if(keystr.equals(key)) {
						return str.substring(keylen+1, str.length()).trim();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 指定したセクション、キーのコメントを返します。
	 * @param section 検索するセクション名
	 * @param key     検索するKEY
     * @return        コメント
	 */
	public String getComment(String section, String key) {
		boolean insideSection = false;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0)
			{
				if(insideSection) {
					int keylen = str.indexOf(COMMENT_START);

					// COMMNET_KEY, COMMENT_STARTを見つけたら、さらにキーを検索
					if(str.indexOf(COMMENT_KEY)==0 && keylen > 0) {

						// COMMENT_KEYから":"までのStringを取得
						String keystr = str.substring(1, keylen-1).trim();
						// セクション内で、キーを見つけたら、その値を返す。
						if(keystr.equals(key)) {
							return str.substring(keylen+1, str.length()).trim();
						}
					}

					// 別のセクションに移ったら、見つからなかった。
					// 該当セクションは２つ以上はないものとする。
					if(str.indexOf(SEC_START)==0) return null;
				}
				else {
					// セクション区切りを捜す。
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * セクションの一覧を返します。
	 * @return セクション一覧を配列で返します。
	 */
	public String[] getSections() {
		Vector secVec = new Vector();
		String[] sectionstr;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);
			if(str.length()>0) {
				if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
					secVec.addElement( str.substring(1, str.length()-1).trim() );	
				}
			}
		}

		if(secVec==null) return null;

		sectionstr = new String[secVec.size()];
		secVec.copyInto(sectionstr);

		return sectionstr;
	}

	/**
	 * セクションは関係なくすべてのKEY一覧を返します。
	 * @return セクションのKEY一覧を返します。
	 */
	public String[] getKeys() {
		Vector keyVec = new Vector();
		String[] keystr;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				// "="を検索
				int index = str.indexOf(wSeparate);

				// コメントではなく"="が存在すれは
				if(str.indexOf(COMMENT_KEY) != 0 && index >= 0) {
					// "="までのKeyを取得
					String tempkey = str.substring(0, index).trim();

					// セクション内で、キーを見つけたら、Vectorにセット。
					keyVec.addElement( tempkey );	
				}
			}
		}

		if(keyVec==null) return null;

		keystr = new String[keyVec.size()];
		keyVec.copyInto(keystr);

		return keystr;
	}

	/**
	 * 指定したセクションのKEYの一覧を返します。
	 * @param section セクション
	 * @return セクションのKEY一覧を配列で返します。
	 */
	public String[] getKeys(String section) {
		Vector keyVec = new Vector();
		String[] keystr;
		boolean insideSection = false;

		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					// "="を検索
					int index = str.indexOf(wSeparate);

					// 湖面尾ではなく"="が存在すれば
					if(str.indexOf(COMMENT_KEY) != 0 && index >= 0) {
						// "="までのKeyを取得
						String tempkey = str.substring(0, index).trim();

						// セクション内で、キーを見つけたら、Vectorにセット。
						keyVec.addElement( tempkey );	

					}
					// 別のセクションに移ったら、終了。
					if(str.indexOf(SEC_START)==0) {
						if(keyVec==null) return null;

						keystr = new String[keyVec.size()];
						keyVec.copyInto(keystr);

						return keystr;
					}
				}
				else
				{
					// セクション区切りを捜す
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}

		if(keyVec==null) return null;

		keystr = new String[keyVec.size()];
		keyVec.copyInto(keystr);

		return keystr;
	}

	/** 
	 * セクションは関係なくキーに対する設定値を返す
     * @return  設定値
	 */
	public String[] getValues() {
		Vector strVec = new Vector();
		String[] relatstr;
		for(int i=0; i<lines.size();i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				// "="を検索
				int index = str.indexOf(wSeparate);
				if(str.indexOf(COMMENT_KEY) != 0 && index >= 0) {

					// セクション内で、設定値を見つけたら、Vectorにセット。
					strVec.addElement( str.substring(index+1).trim() );
				}
			}
		}
		
		if(strVec==null) return null;

		relatstr = new String[strVec.size()];
		strVec.copyInto(relatstr);

		return relatstr;
	}

	/**
	 * セクションは関係なく設定値を変更、KEYがなければ新規追加します。
	 * @param key 		検索KEY
	 * @param value 	変更内容
	 */
	public void set(String key, String value) {

		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);
			
			if(str.length()>0)
			{
				// "="を検索
				int index = str.indexOf(wSeparate);

				if(index >= 0) {
					// "="までのKeyを取得
					String tempkey = str.substring(0, index).trim();

					// キーを見つけたら、値を入れ替える。
					if(tempkey.equals(key)) {
						lines.set(i, key+wSeparate+value);	// replace
						return;
					}
				}
			}
		}

		lines.add(key+wSeparate+value);
	}

	/**
	 * 設定値を変更、KEYがなければ新規追加します。
	 * @param section 	セクション
	 * @param key 		KEY
	 * @param value 	変更内容
	 */
	public void set(String section, String key, String value) {
		boolean insideSection = false;

		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					// "="を検索
					int index = str.indexOf(wSeparate);

					if(index >= 0) {
						// "="までのKeyを取得
						String tempkey = str.substring(0, index).trim();

						// セクション内で、キーを見つけたら、値を入れ替える。
						if(tempkey.equals(key)) {
							lines.set(i, key+wSeparate+value);	// replace
							return;
						}


					}

					// 別のセクションに移ったら、その値を挿入する。
					if(str.indexOf(SEC_START)==0) {
						lines.add(i, key+wSeparate+value);	// insert prev
						lines.add(i + 1, "");	
						return;
					}
				}
				else {
					// セクション区切りを捜す
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		// 該当セクションがなければ、セクションを作る
		if(insideSection=false)
		{
			lines.add(SEC_START + section + SEC_END);
		}
		lines.add(key+wSeparate+value);
	}

	/**
	 * key昇順で指定されたセクション内を並び替えます。created by inoue
	 * @param section 	セクション
	 * @param key[] 	全KEY
	 */
	public void sort(String section, String key[]) {
		boolean insideSection = false;
		String str_inside_section[] = new String[lines.size()];
		int inside_count = 0;
		// キー、一つ一つを順に検索していく
		for(int k = 0; k < key.length;k++) {
			insideSection = false;
			for(int i = 0; i<lines.size(); i++) {
				String str = (String)lines.elementAt(i);
				if(str.length()>0) {
					if(insideSection) {
						// "="を検索
						int index = str.indexOf(wSeparate);
						if(index >= 0) {
							// "="までのKeyを取得
							String tempkey = str.substring(0, index).trim();
							// セクション内で、きーを見つけたら、値を入れる。
							if(tempkey.equals(key[k])) {
								str_inside_section[inside_count++] = str;
								if(i!=lines.size())
									lines.remove(i);
							}
						}
						// 別のセクションに移ったら、その値を挿入する。
						if(str.indexOf(SEC_START)==0) {
							i = lines.size();
						}
					}
					else {
						// セクション区切りを探す
						if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
							// セクション名を取得（スペース除去）
							String tempsection = str.substring(1, str.length()-1).trim();
							// 該当セクションを見つけたら
							if(tempsection.equals(section)) {
								insideSection = true;
							}
						}
					}
				}
			}
		}
		// ちゃんとした長さの配列にセットしなおす。
		String []array = new String[inside_count];
		for(int ii = 0;ii< inside_count;ii++)
		{
			array[ii] = str_inside_section[ii];
		}
		// ソートする
		Arrays.sort(array);
		// ソートした配列を同じセクション内にセットする。
		for( int j = 0; j < inside_count; j++) {
			String a = array[j].trim();
			int index_array = a.indexOf(wSeparate);
			if(index_array >= 0) {
				// "="までのKeyを取得
				String tempkey = a.substring(0, index_array).trim();
				String tempvalue = array[j].substring(index_array+1, a.length()).trim();
				set(section, tempkey, tempvalue) ;
			}
		}
		// 該当セクションがなければ、セクションを作る
		if(insideSection=false)
		{
			lines.add(SEC_START + section + SEC_END);
		}
	}


	/**
	 * コメントを変更、該当するKEYのコメントがなければ新規追加します。
	 * @param section 	セクション
	 * @param key 		検索KEY
	 * @param comment 	コメント
	 */
	public void setComment(String section, String key, String comment) {
		boolean insideSection = false;

		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0)
			{
				if(insideSection) {
					// コロンまでの位置を取得
					int keylen = str.indexOf(COMMENT_START);
					// COMMNET_KEYを見つけたら、さらにキーを検索
					if(str.indexOf(COMMENT_KEY)==0 && keylen > 0) {
						// "*"から";"までのStringを取得
						String keystr = str.substring(1,keylen-1).trim();
						// セクション内で、キーを見つけたら、値を入れ替える。
						if(keystr.equals(key)) {

							lines.set(i, COMMENT_KEY+" "+key+" "+COMMENT_START+comment);	// replace
							return;
						}

					}

					// 別のセクションに移ったら、その前に値を挿入する、
					if(str.indexOf(SEC_START)==0) {

						lines.add(i, COMMENT_KEY+" "+key+" "+COMMENT_START+comment);	// comment insert prev
						return;
					}
				}
				else {
					// セクション区切りを探す
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}

		// 該当セクションがなければ、セクションを作る
		if(insideSection=false)
		{
			lines.add(SEC_START + section + SEC_END);
		}
		lines.add(COMMENT_KEY+" "+key+" "+COMMENT_START+comment);	// replace
	}

	/**
	 * 該当セクションを削除します。
	 * @param section 	削除するセクション
	 */
	public void clearSection(String section) {
		boolean insideSection = false;

		for(int i=0; i<lines.size();) {
			String str = (String)lines.elementAt(i);
			if(insideSection) {
				// 別のセクションに移ったら、削除は完了。
				if(str.indexOf(SEC_START)==0) return;
				lines.remove(i);
			}
			else {
				// セクション区切りを探す
				if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
					// セクション名を取得（スペース除去）
					String tempsection = str.substring(1, str.length()-1).trim();

					// 該当セクションを見つけたら
					if(tempsection.equals(section)) {
						insideSection = true;
						lines.remove(i);
					}
				}
			}
		}
	}

	/**
	 * 該当キーを削除します。
	 * @param section 	セクション
	 * @param key 		削除するKEY
	 */
	public void clearKey(String section, String key) {
		boolean insideSection = false;
		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					// "="を検索
					int index = str.indexOf(wSeparate);

					if(index >= 0) {
						// "="までのKeyを取得
						String tempkey = str.substring(0, index).trim();

						// セクション内で、キーを見つけたら、削除。
						if(tempkey.equals(key)) {
							lines.remove(i);
							if ( lines.size() > i) {
								if( ((String)lines.elementAt(i)).length() == 0 ) {
									lines.remove(i);
								}
							}
							return;
						}
					}

					// 別のセクションに移ったら、最初からそのキーはない。
					if(str.indexOf(SEC_START)==0) {
						return;
					}
				}
				else {
					// セクション区切りを探す
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		// 該当セクションがなければ、削除する必要はない
	}

	/**
	 * 指定されたキーのコメントを削除します。
	 * @param section 	セクション
	 * @param key 		削除するコメントのKEY
	 */
	public void clearComment(String section, String key) {
		boolean insideSection = false;

		for(int i=0; i<lines.size(); i++) {
			String str = (String)lines.elementAt(i);

			if(str.length()>0) {
				if(insideSection) {
					int keylen = str.indexOf(COMMENT_START);

					// COMMNET_KEY, COMMENT_STARTを見つけたら、さらにキーを検索
					if(str.indexOf(COMMENT_KEY)==0 && str.indexOf(COMMENT_START) > 0) {

						// COMMENT_KEYから":"までのStringを取得
						String keystr = str.substring(1, keylen-1).trim();
						// セクション内で、キーを見つけたら、その値を返す。
						if(keystr.equals(key)) {
							lines.remove(i);
							return;
						}
					}

					// 別のセクションに移ったら、最初からそのキーはない。
					if(str.indexOf(SEC_START)==0) {
						return;
					}
				}
				else {
					// セクション区切りを探す
					if(str.indexOf(SEC_START)==0 && str.indexOf(SEC_END)==str.length()-1) {
						// セクション名を取得（スペース除去）
						String tempsection = str.substring(1, str.length()-1).trim();

						// 該当セクションを見つけたら
						if(tempsection.equals(section)) {
							insideSection = true;
						}
					}
				}
			}
		}
		// 該当セクションがなければ、削除する必要はない
	}

	/**
	 *  KEY項目と値の間の区切りをセットします。
	 *  @param sep 区切り文字
	 */
	public void setSeparete(String sep) {
		wSeparate = sep ;
	}

	/**
	 *  KEY項目と値の間の区切りを取得します。
	 *  @param sep 区切り文字
	 */
	public String getSeparete() {
		return wSeparate ;
	}

	/**
	 *  ファイル名（パス）をセットします。
	 *  @param fname ファイル名
	 */
	public void setFileName(String file) {
		this.fname = file ;
	}
	/**
	 *  ファイル名（パス）を取得します。
	 *  @return fname ファイル名
	 */
	public String getFileName() {
		return fname ;
	}

	/**
	 * INIファイルが書き込み可能かを判定します。
	 * @return 書き込み可能な場合<code>true</code>、書き込み不可の場合<code>false</code>
	 */
	public boolean canWrite() {
		File inifile = new File( fname ) ;
		return inifile.canWrite();
	}

	/**
	 * INIファイルを最初に読み込んだときの最新更新時刻をセットします。
	 */
	public void setInitialLastModified() {
		// 最新更新時刻を取得
		File filepath= new File(fname);
		wIniModTime = filepath.lastModified();
	}
	/**
	 * INIファイルを最初に読み込んだときの最新更新時刻を返します。
	 * return ファイルの更新時刻を表す long 値
	 */
	public long getInitialLastModified() {
		return wIniModTime;
	}
	/**
	 * INIファイルの最新更新時刻を返します。
	 * return ファイルが最後に変更された時刻を表す long 値
	 */
	public long getLastModified() {
		File inifile = new File( fname ) ;
		return inifile.lastModified();
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * INIファイルとしてリソースファイルを読み込みます。
	 * Java -version 1.3.0_02を使用する場合、<CODE>BufferedReader</CODE>の<CODE>ready()</CODE>メソッドは、
	 * ファイル最後の(EOF)の行とみなしtrueを返します。<BR>
	 * その結果、<CODE>readLine()</CODE>はnullを返しnull<CODE>Vector</CODE>にaddするため、
	 * <CODE>Vector</CODE>の最後に格納された文字列を操作するとNullPointerExceptionが発生します。<BR>
	 * よって<CODE>readLine()</CODE>でnullを返す場合は<CODE>Vector</CODE>にaddしないようにしています。
	 */
	private void read() throws ReadWriteException
	{
		lines.clear();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fname));
			while(reader.ready())
			{
				String str = reader.readLine();
				if (str != null)
				{
					lines.add(str);
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ReadWriteException(e.getMessage());
		}
	}
}
//end of class
