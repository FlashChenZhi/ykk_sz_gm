// $Id: user.js,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ユーザが使用するJavaScriptです。
 * 関数や変数を追加するときは"_"を関数名の先頭に付加して下さい。
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
 * @author  $Author: mori $
 */
/*--------------------------------------------------------------------------*/
/* グローバル変数															*/
/*--------------------------------------------------------------------------*/

	/*リストセル　ハイライト表示の背景色*/
	var _rowBgColor    = "#A4A4FF";
	/*リストセル　ハイライト表示の文字色*/
	var _rowStyleColor = "black";

	/*メイン画面サイズ　高さ*/
	var _mainWindowWidth = 1024;
	/*メイン画面サイズ　横幅*/
	var _mainWindowHeight = 768;
/*--------------------------------------------------------------------------*/
/* 関数																		*/
/*--------------------------------------------------------------------------*/
/** <jp>
 * [関数の説明]
 *  objで指定したオブジェクトのvalueが引数minより小さいかどうかを確認します。小さい場合はmsgnoで指定したメッセージ
 * をアラートで表示します。
 * [引数の説明]
 * min   最小値
 * msgno Message No.(例：MSG-0001)
 * obj thisを指定
 * [戻り]
 * minより値が小さい場合にアラートを表示します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It confirms whether value of the object specified with obj is smaller than an argument min. When it is small, the message specified with msgno is indicated by the alert.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * min   minimum value
 * msgno Message No.(Ex:MSG-0001)
 * obj Set "this" pointer.
 * [Return]
 * An alert is indicated when value is smaller than min.
 </en> */
function _checkMinValue(min, msgno, obj)
{
 if(obj.value == "") return true;
 /* Remove comma. */
 if(getString(obj.value, ',') < min)
 {
  alert(msgno);
  obj.focus();
 }
}

/**
 * [関数の説明]
 * メッセージエリアへメッセージをセットします。
 * 引数のflagを変更することで、アイコンを使い分けることができます。
 * flag : 0 無し
 *        1 Information
 *        2 Attention
 *        3 Warning
 *        4 Error
 * [引数の説明]
 * msg : メッセージエリアで表示するメッセージ
 * flag : アイコンの種類
 */
function _setMessage(msg, flag)
{
	if(!document.all.message)return;
	var source = "";
	if(flag==1)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Information.gif\" ";
	}
	else if(flag==2)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Attention.gif\" ";
	}
	else if(flag==3)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Warning.gif\" ";
	}
	else if(flag==4)
	{
		source = "<img src=\"" + contextPath + "/img/control/message/Error.gif\" " ;
	}
	else
	{
		source = "<img src=\"" + contextPath + "/img/control/message/None.gif\" " ;
	}
	document.all.message.innerHTML = source + 
			"width=\"15\" height=\"15\" style=\"vertical-align : text-top;\">"    +
			"<img src=\"" + contextPath + "/img/control/message/Spacer.gif\" "    +
			"width=\"4\"  height=\"19\">" + msg;
}

/**
 * [関数の説明]
 * リストセルにデータがあるかを判断します。
 * リストセルにデータが無い場合、trueを返します。
 * [引数の説明]
 * lstID : リストセルのID
 */
function _isListcellEmpty(lstID)
{
	if (document.all(lstID + "$1$1") == null)
	{
		return true;
	}
	return false;
}

/**
 * [関数の説明]
 * 引数で与えられたコントロールに値が入力されているかを確認し、値が無い場合
 * falseを返します。
 * 半角空白を取り除き、文字列のサイズが0の場合はfalseを返します。
 * 全角空白は1文字と数えるためその場合、trueを返します。
 * [使用するカスタムタグ名]
 * SubmitButtonTag
 * [引数の説明]
 * txtID チェックを行うコントロールのID
 */
function _isValueInput(txtID)
{
	if(!validate(document.all(txtID).value))
	{
		return false;
	}
	else
	{
		return true;
	}
}


/**
 * [関数の説明]
 * リストセルに配置されたチェックボックスのチェック状態を調べます。
 * チェックボックスは１列目に配置されている必要があります。
 * チェックが１つでも入っている場合はtrueを返します。
 * 全てのチェックがはずれているときfalseを返します。
 * [引数の説明]
 * lstID : リストセルのID
 */
function _isListcellChecked(lstID)
{
	var i = 1;
	while(document.all(lstID + '$' + i + '$1' ) != null)
	{
		if(document.all(lstID + '$' + i + '$1' ).checked) return true;
		i++;
	}
	return false;
}

