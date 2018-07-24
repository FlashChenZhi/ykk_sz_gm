// $Id: user.js,v 1.1.1.1 2006/08/17 09:33:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ユーザが使用するJavaScriptです。
 * 関数や変数を追加するときは"_"を関数名の先頭に付加して下さい。
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:13 $
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
	var _mainWindowWidth = 1280;
	/*メイン画面サイズ　横幅*/
	var _mainWindowHeight = 1024;


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
  alertOutput (msgno);
  obj.focus();
  return false;
 }
 return true;
}



/**
 * [関数の説明]
 *　ボタン押下時にポップアップウィンドウを閉じて
 *  親画面にフォーカスを戻します。
 */
function _closeWindow()
{
	window.close();
	opener.window.focus();
}

/**
 * [関数の説明]
 *　データ報告（予定作業報告）画面で使用する関数です。
 *  チェックボックスにチェックが入ったときに隣のチェックボックスのDisabledを切り替えます。
 */
function _changeDisabled(arg)
{
	var colArray = new Array("chk_ComUseEventSelect","chk_ComUseEventWorkProg","chk_ComUseRupwi");
	var rowArray = new Array("Instk","Strg","Rtrivl","Pick","Shp");
	var col = 0;
	var row = 0;

	for(var i=0;i < colArray.length; i++)
	{
		if(arg.name.indexOf(colArray[i]) >= 0)
		{
			col = i;
			break;
		}
	}
	for(var j=0; j < rowArray.length; j++)
	{
		if(arg.name.indexOf(rowArray[j]) >= 0)
		{
			row = j;
			break;
		}
	}
	
	if(col == 0)
	{
		var obj_1 = colArray[col+1] + rowArray[row];
		var obj_2 = colArray[col+2] + rowArray[row];
		if(arg.checked)
		{
			if(document.all(obj_1))
			{
				document.all(obj_1).disabled=false;
			}
			else
			{
				document.all(obj_2).disabled=false;
			}
		}
		else
		{
			if(document.all(obj_1))
			{
				document.all(obj_1).disabled=true;
				document.all(obj_1).checked = false;
			}
			if(document.all(obj_2))
			{
				document.all(obj_2).disabled=true;
				document.all(obj_2).checked = false;
			}
		}
	}
	else
	{
		var obj_1 = colArray[col+1] + rowArray[row];
		if(arg.checked)
		{
			document.all(obj_1).disabled=false;
		}
		else
		{
			document.all(obj_1).disabled=true;
			document.all(obj_1).checked = false;
		}
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

/**
 * [関数の説明]
 * 範囲指定の大小が正しいかどうかチェックします。
 * 範囲指定の開始と終了を比較し、開始の方が大きい場合はfalseを返します。
 * 開始の方が小さい場合はtrueを返します。
 * [引数の説明]
 * from : 比較対象の開始
 * end  : 比較対象の終了
 */
function _isCorrectRange(from, end)
{
	if (from <= end)
	{
		return true;
	}
	return false;
	
}

/**
 * WindowsXP_SP2環境下でファイルブラウズエラーが発生した場合
 * そのエラー通知がサーバにサブミットされません。
 * その為、エラー発生時なにか処理を行いたい場合は、
 * その処理をこちらに記述してください。
 */
function _doFileBrowsOnError()
{
}
