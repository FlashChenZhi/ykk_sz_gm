// $Id: control.js 2707 2007-04-05 09:00:09Z kajiwara $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
<jp> * サーバコントロールのカスタムタグが使用するJavaScriptです。</jp><en> * It is JavaScript that the custom tag of the server control uses. </en>
 * @version $Revision: 2707 $
 * @author  $Author: kajiwara $
 */
/*--------------------------------------------------------------------------*/
/*<jp> グローバル変数															  </jp><en> Global variable														  </en>*/
/*--------------------------------------------------------------------------*/
/**
<jp> * ダイアログ・ウィンドウObjectが格納されます。</jp><en> * Dialog window Object is stored. </en>
 */
var dialogWindow;
var menu_num = new Array();
var ptn = new RegExp('[ -~｡-ﾟ]');
var isFocusSelect = true;

/*--------------------------------------------------------------------------*/
/*<jp> 関数																	  </jp><en> Function																  </en>*/
/*--------------------------------------------------------------------------*/
/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ダイアログから親画面へのパラメータ渡しに使用します。</jp><en> * It uses it for the parameter delivery from the dialog to the parents screen. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * array 親画面へ渡すパラメータの配列</jp><en> * Array of parameter passed to array parents screen. </en>
 */
function setParameters(array)
{
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * エンターキーのフォーカス移動で使用します。</jp><en> * It uses it by moving 'Enter' focus. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * obj フォーカス移動順番文字列</jp><en> * The order of moving obj focus character string. </en>
<jp> *     コントロールId名を区切り文字(カンマ)でつなげた文字列</jp><en> * Character string to which control Id name ties by delimiter (comma). </en>
 */
function focusEnterMove(obj)
{
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ファンクションキー対応で使用します。</jp><en> * It uses it by the function key correspondence. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * functionObjects Id名、ファンクションキー、イベント名を格納したObjectの配列</jp><en> * Array of Object that stores functionObjects Id name, function key, and event name. </en>
<jp> *                 ファンクションキーの数だげ必要</jp><en> * The number of necessity of function key. </en>
<jp> * systemParams    ApplicationConfigに定義されているファンクションキーの動作の配列</jp><en> * Array of movement of function key defined in systemParams ApplicationConfig. </en>
<jp> *                 ファンクションキーの数だげ必要</jp><en> * The number of necessity of function key. </en>
 */
function functionKeyEvent(functionObjects, systemParams)
{
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ダイアログウィンドウ（dialogWindow）に対しクローズを行います。</jp><en> * It closes to dialog window (dialogWindow). </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * なし</jp><en> * None. </en>
 */
function closeDialog()
{
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ウィンドウの前面表示を行います。</jp><en> * The front side of the window is displayed. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * windowObject 前面表示を行うウィンドウObject</jp><en> * Window Object where the windowObject front side is displayed. </en>
 */
function setFocusWindow(windowObject)
{
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ウィンドウ中央表示を行います。</jp><en> * The window displays the center. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * PageTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * windowObject 中央表示を行うウィンドウObject</jp><en> * Window Object where windowObject center is displayed. </en>
 */
function moveToCenter(windowObject)
{
}

/** <jp>
 * [関数の説明]
 * 文字色を指定色に変更します。
 *   ※現状はTypeが"text"のときのみ文字色を変えています。
 *     もし仕様がかわりcheckbox、radioなども文字色を変える必要が出てきたときのために
 *     if文で分岐させていますが、今のところreturnを返しています。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * itemname  文字色を切り替えるObject名またはObject
 * col       文字色に指定する色
 </jp> */
/** <en>
 * [Explanation of a function]
 * A character color is changed into a specification color.
 *   * When Type is only "Text", the current state has changed the character color.
 *     At present, return is returned though the specification has separated by the if sentence
 *     so that the necessity for change's such as checkbox and radio changing the character color may come out.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * itemname  The Object name or Object which changes a character color
 * col       The color specified to be a character color
 </en> */
function changeColor(itemname, col)
{
	if( itemname == "" ) return;
	if( col == "" ) return;

	var obj;
	if (typeof itemname == 'object') {
		obj = itemname;
	} else {
		obj = document.all.item(itemname);
	}

	/*<jp>
		textboxなどObjectが複数存在しない場合あるいはSELECTタグのとき
	</jp> */
	/*<en>
		In the case of TEXTBOX or a SELECT tag.
	</en> */
	if (!obj.length || obj.tagName == "SELECT") {
		if (obj.tagName == "INPUT") {
			if (obj.type == "button") {
				return;
			} else if (obj.type == "checkbox") {
				/*<jp>
					checkboxが一つしか存在しない場合 背景色の指定は出来ません
				</jp> */
				/*<en>
					A CHECKBOX can't specify a background color, when it is only one.
				</en> */
				return;
			}
		} else if (obj.tagName == "SELECT") {
			return;
		}
	} else {
		/*<jp>
			checkbox、radio、selectなどの同一名称で複数存在する場合、背景色の指定は出来ません
		</jp> */
		/*<en>
			A background color cannot be specified when two or more same names exist.
		</en> */
		return;
	}

	obj.style.setAttribute("color", col);
}


/** <jp>
 * [関数の説明]
 * 背景色を指定色に変更します。
 *   ※現状はTypeが"text"のときのみ背景色を変えています。
 *     もし仕様がかわりcheckbox、radioなども背景色を変える必要が出てきたときのために
 *     if文で分岐させていますが、今のところreturnを返しています。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * itemname  背景色を切り替えるObject名またはObject
 * col       背景色に指定する色
 </jp> */
/** <en>
 * [Explanation of a function]
 * A background color is changed into a specification color.
 *   * When Type is only "Text", the current state has changed the background color.
 *     At present, return is returned though the specification has separated by the if sentence
 *     so that the necessity for change's such as checkbox and radio changing the background color may come out.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * itemname  The Object name or Object which changes a background color
 * col       The color specified to be a background color
 </en> */
var isChangeBackColor = true;
function changeBackcolor(itemname, col)
{
	if ( !isChangeBackColor ) return;
	if( itemname == "" ) return;
	if( col == "" ) return;

	var obj;

	if (typeof itemname == 'object') {
		obj = itemname;
	} else {
		obj = document.all.item(itemname);
	}

	/*<jp>
		textboxなどObjectが複数存在しない場合あるいはSELECTタグのとき
	</jp> */
	/*<en>
		In the case of TEXTBOX or a SELECT tag.
	</en> */
	if (!obj.length || obj.tagName == "SELECT") {
		if (obj.tagName == "INPUT") {
			if (obj.type == "button") {
				return;
			} else if (obj.type == "checkbox") {
				/*<jp>
					checkboxが一つしか存在しない場合 背景色の指定は出来ません
				</jp> */
				/*<en>
					A CHECKBOX can't specify a background color, when it is only one.
				</en> */
				return;
			}
		} else if (obj.tagName == "SELECT") {
			return;
		}
	} else {
		/*<jp>
			checkbox、radio、selectなどの同一名称で複数存在する場合、背景色の指定は出来ません
		</jp> */
		/*<en>
			A background color cannot be specified when two or more same names exist.
		</en> */
		return;
	}

	obj.style.setAttribute("background", col);
	/*<jp>
		テキストボックスのハイライト表示が残ったままになることの防止策です。
		N.Sawa add 2004/08/10
	</jp> */
	/*<en>
		This description prevents the climax indication of the text box from being left. 
		N.Sawa add 2004/08/10
	</en> */
	var tmp = obj.value;
	obj.value = tmp;
}


/** <jp>
 * [関数の説明]
 * 指定されたObjectが配置してあるTD、TR、TABLE句の背景色を取得します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * id 背景色を取得するObjectのIDを指定します。
 * [戻り]
 * TD、TR、TABLE句の背景色を返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The background color of TD, TR, and a TABLE phrase which arranges specified Object is acquired.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * id ID of Object to acquire the background color is specified.
 * [Return]
 * A background color is returned.
 </en> */
function getBgColor(id)
{
	if( id == "" ) return null;

	var obj = document.all(id);

	if( !obj ) return;

	var tagarray = new Array("TD", "TR", "TBODY", "TABLE");

	for(i in tagarray) {
		var parent = obj.parentElement;
		if( parent.tagName == tagarray[i] ) {
			if(parent.bgColor != "") {
				return parent.bgColor;
			}
		}
		obj = parent;
	}
	return null;
}

/** <jp>
 * [関数の説明]
 * 書式化されたデータから区切り文字を外します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str       書式化されたデータ
 * splitChar 区切り文字
 * [戻り]
 * 区切り文字を外したデータを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It divides from data and a character is removed.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str       Data
 * splitChar Pause character
 * [Return]
 * The data which removed the pause character is returned.
 </en> */
function getString(str,splitChar)
{
	if( str == "" ) return str;
	if( splitChar == "" ) return str;

	while (str.indexOf(splitChar) > -1){
		str = str.replace( splitChar, "" );
	}
	return str;
}

/** <jp>
 * [関数の説明]
 * カンマ編集します。
 * データの少数点以下桁数が、指定された桁数より小さい場合に、
 * 小数点以下の桁数を調整します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * pos 少数部の桁数
 * [戻り]
 * カンマ編集されたデータを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Comma edit of the data is carried out.
 * When the number of below a small number of point beams of data is smaller than the specified number of beams,
 * the number of beams below a decimal point is adjusted.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * pos The number of beams below a a small number of point
 * [Return]
 * The data by which comma edit was carried out is returned.
 </en> */
function getComma(str,pos)
{
	var strlen = 0;
	var i = 0;
	var minus = false;

	if( str == "" ) return "";
	if( pos == "" ) pos = 0;
	
	if( str.indexOf("-") == 0 ) {
		minus = true;
		str = str.substring(1);
	}
	if( str.match(/[^0123456789.]/) ) return "";

	if( str.indexOf(".") >= 0 ) {
		if( str.indexOf( ".", (str.indexOf(".")+1)) >= 0 ) return "";
	}

	if( str.indexOf(".") == -1 ) {
		str = parseInt(str, 10).toString();
	} else {
		str = parseFloat(str, 10).toString();
	}

	strlen = str.indexOf(".");
	if( strlen < 0 ) strlen = str.length;
	reStr = "";

	for( i = strlen; i > 0; i-- ) {
		if( i != 0 & i % 3 == 0 & i < strlen ) {
			reStr += ","; 
		}
		reStr += str.charAt(strlen - i);
	}

	reStr += str.substring( strlen, str.length );

	if( pos > 0 && pos > (str.length - (strlen + 1)) ) {
		i = 0;
		if( (str.length - strlen) > 0 ) {
			i = str.length - (strlen + 1);
		} else {
			reStr += ".";
		}
		for( ; i < pos; i++ ) {
			reStr += "0";
		}
	}

	if(minus) {
		reStr = "-" + reStr;
	}

	return reStr;
}

/** <jp>
 * [関数の説明]
 * 指定された書式に書式化します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * pause  区切り文字
 * [戻り]
 * 書式編集されたデータを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Form edit of the data is carried out.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str    Data
 * format Form
 * pause  Pause character
 * [Return]
 * The data by which form edit was carried out is returned.
 </en> */
function getFormat(str,format,pause)
{
	if( str == "" ) return str;
	if( format == "" ) return str;
	
	var sidx = 0;
	var eidx = 0;
	var value_sidx = 0;
	var value_eidx = 0;
	var value = "";
	var pos = 0;

	while( format.indexOf( pause, sidx ) != -1 ) {
		eidx = format.indexOf( pause, sidx );
		value_eidx = eidx - pos;
		value = value + str.substring( value_sidx, value_eidx ) + pause;
		sidx = eidx + pause.length;
		value_sidx = value_eidx;
		pos += pause.length;
	}

	value = value + str.substring( value_sidx, str.length );

	return value;
}

/** <jp>
 * [関数の説明]
 * データが指定されたバイト数を超えていないかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str  データ
 * byte バイト数
 * [戻り]
 * 指定されたバイト数を超えていたら false、超えていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It confirms whether to be over the number of bytes data was specified to be.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str  Data
 * byte The number of bytes
 * [Return]
 * FALSE will be returned if it is over the specified number of bytes. TRUE is returned if it has not exceeded.
 </en> */
function isByteCheck(str,byte)
{
	if( str == "" ) return true;
	if( byte == "" ) return true;

	if( getByte( str ) > byte ) {
		return false;
	} else {
		return true;
	}
}


/** <jp>
 * [関数の説明]
 * データの桁数が指定された桁数を超えていないかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str  データ
 * numlen 整数部桁数
 * decimallen 少数部桁数
 * [戻り]
 * 指定されたバイト数を超えていたら false、超えていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * I confirms whether to be over the number of digit the number of digit of data was specified to be.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str  Data
 * numlen The number of integer part figures
 * decimallen The number of a small number of part figures
 * [Return]
 * FALSE will be returned if it is over the specified number of bytes. TRUE is returned if it has not exceeded.
 </en> */
function isNumberOfDigitCheck(str,numlen,decimallen)
{
	if( str == "" ) return true;

	if( numlen >= 0 ){
		if( str.indexOf(".") == -1 ) {
			if( !isByteCheck( str, numlen ) ) return false;
		} else {
			if( !isByteCheck( str.substring( 0, str.indexOf(".")), numlen ) ) return false;
		}
	}

	if( decimallen >= 0 ){
		if( str.indexOf(".") >= 0 ) {
			var idx = str.indexOf(".") + 1;
			if( !isByteCheck( str.substring( idx, str.length ), decimallen ) ) return false;
		}
	}

	return true;
}

/** <jp>
 * [関数の説明]
 * 数値の範囲チェックを行います。
 * [使用するカスタムタグ名]
 * NumberTextBoxTag
 * [引数の説明]
 * val  データ
 * min  最小値
 * max  最大値
 * [戻り]
 * 指定されてる範囲外の数値ならfalse、範囲内の数値ならtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The range of the numerical value is checked. 
 * [The custom-made tag name to be used]
 * NumberTextBoxTag
 * [Explanation of an argument]
 * val  Data
 * min  The minimum value
 * max  The maximum value
 * [Return]
 * If it is a numerical value within false and the range when outside the specified range the numerical value, true is returned. 
 </en> */
function isNumberCheckRange(val, min, max)
{
	var in_val = replaceAll(replaceAll(val, ',', ''), ' ', '');

	if (in_val == '')
	{
		return true;
	}

	if (isNaN(in_val))
	{
		return false;
	}

	if (!isNaN(max) && (max != ''))
	{
		var num_max = new Number(max);
		if (in_val > num_max)
		{
			return false;
		}
	}
	
	if (!isNaN(min) && (min != ''))
	{
		var num_min = new Number(min);
		if (in_val < num_min)
		{
			return false;
		}
	}
	return true;
}

/** <jp>
 * [関数の説明]
 * データのバイト数を取得します。
 * 全角文字は2byte、半角カナの場合は1byteとして計算します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * バイト数を返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The number of bytes of data is acquired.
 * A full-size character is calculated as 2 bytes. Half-width kana is calculated as 1 byte.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * [Return]
 * The number of bytes is returned.
 </en> */
function getByte( str )
{
	str = RTrim( str );
	var i, cnt =0;
	var CLetter;
	
	for (i=0; i<str.length; i++) {
		
		CLetter = str.charAt(i);
		
		if (CLetter.match(ptn)) {
			cnt++;
    	}else{
			cnt += 2;
    	}
	}
	return cnt;
}

/** <jp>
 * [関数の説明]
 * 引数を右TRIMします。
 * TRIMされる文字列は半角スペースと全角スペースです。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 右TRIMされた文字列を返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The argument is right TRIM done. 
 * The character string that TRIM is done is half angle space and em-size space. 
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * [Return]
 * The character string that right TRIM is done is returned. 
 </en> */
function RTrim(str){
    str = str.replace(/[ 　]+$/,"");
    return str; 
}

/** <jp>
 * [関数の説明]
 * 禁止文字が含まれているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * taboo 禁止文字
 * [戻り]
 * 禁止文字が含まれていれば false、含まれていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is confirmed whether the prohibition character is contained.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * taboo Prohibition character
 * [Return]
 * FALSE will be returned if contained except the prohibition character. TRUE is returned if not contained.
 </en> */
function isTabooCheck(str,taboo)
{
	if( str == "" ) return true;
	if( taboo == "" ) return true;

	if( str.match("[" + taboo + "]")) {
		return false;
	} else {
		return true;
	}
}


/** <jp>
 * [関数の説明]
 * 禁止文字が含まれているかチェックします。このメソッドでは16進数に変換して比較します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * taboo 禁止文字
 * [戻り]
 * 禁止文字が含まれていれば true、含まれていなければfalseを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is confirmed whether the prohibition character is contained.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * taboo Prohibition character
 * [Return]
 * true will be returned if contained except the prohibition character. false is returned if not contained.
 </en> */
function isProhibitedCharacter(str,taboo)
{
	rObj = new RegExp(taboo);

	if( str == "" ) return false;
	if( taboo == "" ) return false;

	oText= "";
	for (i=0; i<str.length; i++) {
		oText = str.charCodeAt(i).toString(16).toUpperCase();
		if( oText.match(rObj)) {
			return true;
		} else {
		
		}
	}
	return false;
}

/** <jp>
 * [関数の説明]
 * 16進数に変換した値を返します。
 * 
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * １６進数に変換した値（スペース区切り）
 </jp> */
/** <en>
 * [Explanation of a function]
 * Return the value shift into the hexadecimal.
 *
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * [Return]
 * hexadecimal value(space delimited)
 </en> */

function textConv16(str) {
	oText= "";
	for (i=0; i<str.length; i++) {
		oText += str.charCodeAt(i).toString(16);
		oText +=" ";
	}
	return oText;
}


/** <jp>
 * [関数の説明]
 * データが数字(0-9)のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 数字以外が含まれていたら false、含まれていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is confirmed whether data is constituted only numerically (0-9).
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * [Return]
 * FALSE will be returned if contained except the number. TRUE is returned if not contained.
 </en> */
function isNumberCheck(str)
{
	if( str == "" ) return true;

	if( str.match(/[^0-9]/) ) return false;
	
	return true;
}


/** <jp>
 * [関数の説明]
 * データが数値入力テキストボックスで使用できる文字のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * decimal 少数部桁数
 * [戻り]
 * 使用できる文字以外が含まれていればfalse。含まれていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Data confirms whether to consist of only characters which can be used with a NumberTextBox.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * decimal The number of a small number of part figures
 * [Return]
 * FALSE will be returned if contained except the character which can be used. TRUE is returned if not contained.
 </en> */
function isNumberTextBoxCheck(str, decimal)
{
	if( str == "" ) return true;
	//Add 2004/07/20 Kawashima
	if(str == "." || str == "-") return false;
	//End 2004/07/20 Kawashima

	if( str.indexOf(".") >= 0 ) {
		if( str.indexOf( ".", (str.indexOf(".")+1)) >= 0 ) return false;
	}
	if( str.indexOf("-") >= 0 ) {
		if( str.indexOf( "-", (str.indexOf("-")+1)) >= 0 ) return false;
	}
	
	//Mod 2004/09/10 Kawashima
	if(decimal == 0)
	{
		if( str.match(/[^0123456789-]/) ) return false;
	}	
	else
	{
		if( str.match(/[^0123456789.-]/) ) return false;
	}
	//End 2004/09/10 Kawashima

	if( isNaN(str) )
	{
		return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが数値入力テキストボックスで使用できる文字のみで構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 使用できる文字以外が含まれていればfalse。含まれていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Data confirms whether to consist of only characters which can be used with a NumberTextBox.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str Data
 * [Return]
 * FALSE will be returned if contained except the character which can be used. TRUE is returned if not contained.
 </en> */
function isBarCodeTextBoxCheck(str)
{
	if( str == "" ) return true;

	if( !isHalfWidthCharacter(str) ) return false;

	if( str.match(/[^0-9]/) && str.match(/[^A-Z]/i) && str.match(/[^@_]/) )
	{
		var i = 0;
		for( i = 0; i<str.length; i++ ) {
			CLetter = escape(str.charAt(i)) ;
			if (( CLetter >= "%5B" && CLetter <= "%5E" ) || ( CLetter >= "%7B" && CLetter <= "%7E" )) {
			}else{
					return false;
			}
		}
	}
	return true;
	
}


/** <jp>
 * [関数の説明]
 * 指定範囲の文字列を抜き出します。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str 対象となる文字列
 * start 抜き出す先頭位置（ゼロが先頭）
 * end 抜き出す終了位置
 * [戻り]
 * 抜き出した文字列
 </jp> */
/** <en>
 * [Explanation of a function]
 * The character sequence of the specification range is extracted.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str The target character sequence.
 * start The head position to extract. (zero are a head)
 * end The end position to extract.
 * [Return]
 * The extracted character sequence.
 </en> */
 function getSubString(str, start, end)
{
	return str.substring( start, end );
}



/** <jp>
 * [関数の説明]
 * データが指定された文字種で構成されているかチェックします。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str   データ
 * kind  文字種
 *       1:全角文字 2:半角カナ 3:半角英字
 *       4:半角数字 5:半角記号 6:半角スペース
 *       7:禁止文字チェック
 * taboo 禁止文字
 * [戻り]
 * 指定された文字種以外が含まれていれば false、含まれていなければtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It confirms whether to consist of character kinds by which data was specified.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str  Data
 * kind Character kind
 *      1:full-width character 2:half-width kana 3:half-width alphabetic character 
 *      4:half-width number    5:half-width sing 6:half-width space
 *      7:prohibited character check
 * taboo Prohibition character
 * [Return]
 * FALSE will be returned if contained except the specified character kind. TRUE is returned if not contained.
 </en> */
function isCharacterCheck(str,kind,taboo)
{
	if( str == "" ) return true;
	if( kind == "" ) return true;
	
	/*<jp>
		禁止文字を含んでいないかチェックします
	</jp> */
	/*<en>
		It is confirmed whether the prohibition character is included.
	</en> */
	if( kind.indexOf("7") >= 0 ) {
		if( isProhibitedCharacter(str,taboo) ) return false;
	}

	/*<jp>
		文字種に全角文字が含まれていればチェックはしません
	</jp> */
	/*<en>
		A check is not carried out when full size is contained.
	</en> */
	if( kind.indexOf("1") != -1 ) return true;

	/*<jp>
		全角が含まれていないかチェックします
	</jp> */
	/*<en>
		It is confirmed whether full size is contained.
	</en> */
	if( !isHalfWidthCharacter(str) ) return false;
	
	/*<jp>
		扱えない文字が含まれていないかチェック
	</jp> */
	/*<en>
		It is confirmed whether the character which cannot be treated is contained.
	</en> */
	if( isContainInvalidCharacter(str) ) return false;

	/*<jp>
		半角カナを含んでいないかチェック
	</jp> */
	/*<en>
		It is confirmed whether half-width kana is included.
	</en> */
	if( kind.indexOf("2") < 0 ) {
		if( isContainHalfWidthKANA(str) ) return false;
	}

	/*<jp>
		半角英字を含んでいないかチェック
	</jp> */
	/*<en>
		It is confirmed whether the half-width alphabetic character is included.
	</en> */
	if( kind.indexOf("3") < 0 ) {
		if( isContainAlphabet(str) ) return false;
	}

	/*<jp>
		半角数字を含んでいないかチェック
	</jp> */
	/*<en>
		It confirms whether to include the half-width number.
	</en> */
	if( kind.indexOf("4") < 0 ) {
		if( isContainHalfWidthNumber(str) ) return false;
	}

	/*<jp>
		半角記号を含んでいないかチェック
	</jp> */
	/*<en>
		It confirms whether to include the half-width sign.
	</en> */
	if( kind.indexOf("5") < 0 ) {
		if( isContainHalfWidthSign(str) ) return false;
	}

	/*<jp>
		半角スペースを含んでいないかチェック
	</jp> */
	/*<en>
		It is confirmed whether the half-width space is included.
	</en> */
	if( kind.indexOf("6") < 0 ) {
		if( isContainHalfWidthSpace(str) ) return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが日付として有効かチェックします。
 * 日付範囲:1970/1/1～2049/12/31
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * [戻り]
 * 日付として無効ならば false、有効ならばtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is confirmed whether data is effective as date.
 * Date range:1970/1/1-2049/12/31
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str    Data
 * format Form
 * [Return]
 * FALSE will be returned if invalid as date. TRUE is returned if effective.
 </en> */
function isDateCheck(str, format)
{
	if( str == "" ) return true;
	if( format == "" ) return true;

	var y = "";
	var m = "";
	var d = "";
	var y_i = 0;
	var m_i = 1;
	var d_i = 1;
	var uru = 28;

	if( str.match(/[^0-9]/) ) return false;
	if( str.length != format.length ) return false;
	for( i=0; i<str.length; i++) {
		if( format.charAt(i) == 'Y' || format.charAt(i) == 'y' ) {
			y += str.charAt(i);
		} else if( format.charAt(i) == 'M' || format.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else {
			d += str.charAt(i);
		}
	}

	if( format.match(/[yY]/) ) {
		if( y == "" ) return false;
		if( parseInt(y, 10) < 0 ) {
			return false;
		}

		y_i =  parseInt(y, 10);

		if( parseInt(y, 10) < 100 ) {
			if( parseInt(y, 10) < 70 ) {
				y_i += 2000;
			} else {
				y_i += 1900;
			}
		}

		if( y_i < 1970 || y_i > 2049 ) {
			return false;
		}

	} else {
		DATE = new Date();
		y_i = DATE.getFullYear();
	}

	if( format.match(/[mM]/) ) {
		if( m == "" ) return false;
		m_i = parseInt(m, 10);
		if( m_i < 1 || m_i > 12 ) return false;
	}

	if( format.match(/[dD]/) ) 
	{
		if( d == "" )
		{
			return false;
		}
		d_i = parseInt(d, 10);
	} 
	else 
	{
		if( d == "" ) 
		{	
			return true;
		}
		else
		{
			return false;
		}
	}


	if( m_i == 2 ) {
		if( y_i % 4 == 0 ) {
			if( y_i % 100 == 0 ) {
				if( (y_i % 400)  == 0 ) {
					uru = 29;
				}
			} else {
				uru = 29;
			}
		}
		if( d < 1 || d > uru ) 
		{
			return false;
		}
	} 

	else if( m_i == 4 || m_i == 6 || m_i == 9 || m_i == 11 ) 
	{
		if( d < 1 || d > 30 ) 
		{
			return false;
		}
	} 
	else 
	{
		if( d < 1 || d > 31 ) 
		{
			return false;
		}
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * データが時間として有効かチェックします。
 * 時間範囲:00:00:00～23:59:59
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * format 書式
 * [戻り]
 * 時間として無効ならば false、有効ならばtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is confirmed whether data is effective as time.
 * Time range:00:00:00-23:59:59
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str    Data
 * format Form
 * [Return]
 * FALSE will be returned if invalid as time. TRUE is returned if effective.
 </en> */
function isTimeCheck(str, format)
{
	if( str == "" ) return true;
	if( format == "" ) return true;

	var h = "";
	var m = "";
	var s = "";

	if( str.match(/[^0-9]/) ) return false;
	if( str.length != format.length ) return false;

	for( i = 0; i<str.length; i++ ) {
		if( format.charAt(i) == 'H' || format.charAt(i) == 'h' ) {
			h += str.charAt(i);
		} else if( format.charAt(i) == 'M' || format.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else {
			s += str.charAt(i);
		}
	}

	if( format.match(/[hH]/) ) {
		if( h == "" ) return false;
		if( parseInt(h, 10) < 0 || parseInt(h, 10) > 23 ) return false;
	}

	if( format.match(/[mM]/) ) {
		if( m == "" ) return false;
		if( parseInt(m, 10) < 0 || parseInt(m, 10) > 59 ) return false;
	}

	if( format.match(/[sS]/) ) {
		if( s == "" ) return false;
		if( parseInt(s, 10) < 0 || parseInt(s, 10) > 59 ) return false;
	} else {
		if( s != "" ) return false;
	}

	return true;
}


/** <jp>
 * [関数の説明]
 * 全角文字列精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str データ
 * [戻り]
 * 全角文字列のみであればtrue、それ以外はfalseを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Full-size character sequence scrutinization.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * TRUE is returned when it consists of only full-size characters. FALSE is returned when except it is included.
 </en> */
function isFullWidthCharacter( str )
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				return false;
			} else {
			}
		} else {
			return false;
		}
	}
	return true;
}


/** <jp>
 * [関数の説明]
 * 半角文字列精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角文字列のみであればtrue。それ以外はfalseを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * Half-width character sequence scrutinization.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * TRUE is returned when it consists of only half-width characters. FALSE is returned when except it is included.
 </en> */
function isHalfWidthCharacter(str)
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
			} else {
				return false;
			}
		}
	}
	return true;
}


/** <jp>
 * [関数の説明]
 * 半角カナ文字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角カナ文字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether half-width kana character is contained.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when a half-width kana character is not contained. true is returned when contained.
 </en> */
function isContainHalfWidthKANA(str)
{
	var i = 0;
	for( i = 0; i<str.length; i++ ) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				return true;
			}
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 半角英字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角英字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether a half-width alphabetic character is contained.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when a half-width alphabetic character is not contained. true is returned when contained.
 </en> */
function isContainAlphabet(str)
{
	var i = 0;

	if( str.match(/[A-Z]/i) || str.match(/[@_]/) ) return true;

	for( i = 0; i<str.length; i++ ) {
		CLetter = escape(str.charAt(i)) ;
		if (( CLetter >= "%5B" && CLetter <= "%5E" ) || ( CLetter >= "%7B" && CLetter <= "%7E" )) {
				return true;
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 半角数字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角数字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether a half-width number character is included.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when a half-width number character is not contained. true is returned when contained.
 </en> */
function isContainHalfWidthNumber(str)
{
	if( str.match(/[0-9]/) ) return true;
	
	return false;
}


/** <jp>
 * [関数の説明]
 * 半角記号が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角記号が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether a half-width sign is included.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when a half-width sign is not contained. true is returned when contained.
 </en> */
function isContainHalfWidthSign(str)
{
	if( str.match(/[*+-./]/) ) return true;

	for( i = 0; i<str.length; i++ ) {
		CLetter = escape(str.charAt(i)) ;
		if (( CLetter >= "%21" && CLetter <= "%29" ) || CLetter == "%2C" || ( CLetter >= "%3A" && CLetter <= "%3F" )) {
				return true;
		}
	}

	return false;
}


/** <jp>
 * [関数の説明]
 * 半角スペースが含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 半角スペースが含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether a half-width space is included.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when a half-width space is not contained. true is returned when contained.
 </en> */
function isContainHalfWidthSpace(str)
{
	if( str.match(/[ ]/) ) return true;

	return false;
}


/** <jp>
 * [関数の説明]
 * 扱えない文字が含まれるかどうかを精査。
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str    データ
 * [戻り]
 * 扱えない文字が含まれない場合はfalse、含まれる場合はtrueを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It scrutinizes whether the character which cannot be treated is contained.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str   Data
 * [Return]
 * false is returned when the character which cannot be treated is not contained. true is returned when contained.
 </en> */
function isContainInvalidCharacter(str)
{
	if( str.match(/[^A-Z]/i) && str.match(/[^@_]/) && str.match(/[^0-9]/) && str.match(/[^ ]/) && str.match(/[^*+-./]/) ) {
		for( i = 0; i<str.length; i++ ) {
			CLetter = escape(str.charAt(i)) ;

			if( escape(str.charAt(i)).length >= 4 ) {
				CLetter = escape(str.charAt(i)) ;
				if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				} else {
					return true;
				}
			} else {
				if( str.charAt(i).match(/[A-Z]/i) || str.charAt(i).match(/[@_]/) || str.charAt(i).match(/[0-9]/) || str.charAt(i).match(/[ ]/) || str.charAt(i).match(/[*+-./]/) ) {
				} else if (( CLetter >= "%21" && CLetter <= "%29" ) || CLetter == "%2C" || ( CLetter >= "%3A" && CLetter <= "%3F" ) ||
					( CLetter >= "%5B" && CLetter <= "%5E" ) || ( CLetter >= "%7B" && CLetter <= "%7E" || CLetter == "%A0")) {
				} else {
					return true;
				}
			}
		}
	}
	return false;
}


/** <jp>
 * [関数の説明]
 * 日付のフォーマットを変更します。
 * 変更前書式のデータを変更後書式に変更します。
 * 変更後の書式で必要な情報が、変更前の書式に無い場合は、
 * 現在日時で補います。
 * 日付範囲:1970/1/1～2049/12/31
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str     データ
 * bformat 変更前書式
 * aformat 変更後書式
 * delim   書式内の日付区切り文字（"/","-"等）
 * [戻り]
 * 変更後書式に変更したデータを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The format of the date is changed.
 * The data of a change preface formula is changed into a change postscript formula.
 * When there is no information required of the form after change in the form before change,
 * it compensates with the present time.
 * Date range:1970/1/1-2049/12/31
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str     Data
 * bformat The form before change
 * aformat The form before change
 * delim   Date delimiter in the format (Example: "/", "-")
 * [Return]
 * The data changed into the change postscript formula is returned.
 </en> */
function changeDateFormat(str, bformat, aformat, delim)
{

	if( str == "" || aformat == "" ) return str;

	if( bformat == "" ){
		if( aformat.match(/[/]/) ){
			return getFormat(str,aformat,"/");
		} else {
			return getString(str,"/");
		}
	}

	for( i = 0; i<str.length; i++ ) {
		if( str.charAt(i) != delim && str.charAt(i).match(/[^0123456789]/) ) {
			return str;
		}
	}
	if( str.length != bformat.length ) return str;

	var dtoday = new Date();

	var y = "";
	var m = "";
	var d = "";
	var y_i = dtoday.getYear();
	var m_i = dtoday.getMonth() + 1;
	var d_i = dtoday.getDate();
	var y_len = 0;
	var y_flg = false;
	var reStr = "";

	for( i=0; i<str.length; i++) {
		if( bformat.charAt(i) == 'Y' || bformat.charAt(i) == 'y' ) {
			y += str.charAt(i);
		} else if( bformat.charAt(i) == 'M' || bformat.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else if( bformat.charAt(i) == 'D' || bformat.charAt(i) == 'd' ) {
			d += str.charAt(i);
		}
	}

	if( y != "" ) y_i = parseInt(y, 10);
	if( m != "" ) m_i = parseInt(m, 10);
	if( d != "" ) d_i = parseInt(d, 10);

	if( parseInt(y, 10) < 100 ) {
		if( parseInt(y, 10) < 70 ) {
			y_i += 2000;
		} else {
			y_i += 1900;
		}
	}

	y = new String( y_i );
	m = new String( m_i );
	d = new String( d_i );

	for( i=0; i<aformat.length; i++) {
		if( aformat.charAt(i) == 'Y' || aformat.charAt(i) == 'y' ) {
			y_len++;
			y_flg = true;
		} else if( aformat.charAt(i) == 'M' || aformat.charAt(i) == 'm' ) {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			m = "0" + m;
			reStr += m.substring( (m.length - 2), m.length );
			i += 1;
		} else if( aformat.charAt(i) == 'D' || aformat.charAt(i) == 'd' ) {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			d = "0" + d;
			reStr += d.substring( (d.length - 2), d.length );
			i += 1;
		} else {
			if( y_flg && y_len > 0 ){
				reStr += y.substring( (4 - y_len), y.length );
				y_len = 0;
			}
			reStr += aformat.charAt(i);
		}
	}

	if( y_flg && y_len > 0 ){
//		reStr += y.substring( (4 - y_len), y_len );
		reStr += y.substring( (4 - y_len), 4 );
	}

	return reStr;
}


/** <jp>
 * [関数の説明]
 * 時間のフォーマットを変更します。
 * 変更前書式のデータを変更後書式に変更します。
 * 変更後の書式で必要な情報が、変更前の書式に無い場合は、
 * 現在日時で補います。
 * 時間範囲:00:00:00-23:59:59
 * [使用するカスタムタグ名]
 * Common
 * [引数の説明]
 * str     データ
 * bformat 変更前書式
 * aformat 変更後書式
 * delim   書式内の時間区切り文字（:等）
 * [戻り]
 * 変更後書式に変更したデータを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The format of the time is changed.
 * The data of a change preface formula is changed into a change postscript formula.
 * When there is no information required of the form after change in the form before change,
 * it compensates with the present time.
 * Time range:00:00:00-23:59:59
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str     Data
 * bformat The form before change
 * aformat The form before change
 * delim   Time delimiter in the format (Example: ":")
 * [Return]
 * The data changed into the change postscript formula is returned.
 </en> */
function changeTimeFormat(str, bformat, aformat, delim)
{
	if( str == "" || aformat == "" ) return str;

	if( bformat == "" ){
		if( str.match(/[^0123456789]/) ){
			return getFormat(str,aformat,delim);
		} else {
			return getString(str,delim);
		}
	}

	for( i = 0; i<str.length; i++ ) {
		if( str.charAt(i) != delim && str.charAt(i).match(/[^0123456789]/) ) {
			return str;
		}
	}
	if( str.length != bformat.length ) return str;

	var dtoday = new Date();

	var h = "";
	var m = "";
	var s = "";
	var h_i = dtoday.getHours();
	var m_i = dtoday.getMinutes();
	var s_i = dtoday.getSeconds();
	var reStr = "";

	for( i=0; i<str.length; i++) {
		if( bformat.charAt(i) == 'H' || bformat.charAt(i) == 'h' ) {
			h += str.charAt(i);
		} else if( bformat.charAt(i) == 'M' || bformat.charAt(i) == 'm' ) {
			m += str.charAt(i);
		} else if( bformat.charAt(i) == 'S' || bformat.charAt(i) == 's' ) {
			s += str.charAt(i);
		}
	}

	if( h != "" ) h_i = parseInt(h, 10);
	if( m != "" ) m_i = parseInt(m, 10);
	if( s != "" ) s_i = parseInt(s, 10);

	h = new String( h_i );
	m = new String( m_i );
	s = new String( s_i );

	for( i=0; i<aformat.length; i++) {
		if( aformat.charAt(i) == 'H' || aformat.charAt(i) == 'h' ) {
			h = "0" + h;
			reStr += h.substring( (h.length - 2), h.length );
			i += 1;
		} else if( aformat.charAt(i) == 'M' || aformat.charAt(i) == 'm' ) {
			m = "0" + m;
			reStr += m.substring( (m.length - 2), m.length );
			i += 1;
		} else if( aformat.charAt(i) == 's' || aformat.charAt(i) == 's' ) {
			s = "0" + s;
			reStr += s.substring( (s.length - 2), s.length );
			i += 1;
		} else {
			reStr += aformat.charAt(i);
		}
	}

	return reStr;
}


/** <jp>
 * [関数の説明]
 * エレメント要素をたどりコントロールの色をハイライト表示します。
 *
 * @param el - コントロールの色を変更する必要のあるテーブルエレメント 
 </jp> */
/** <en>
 * Explanation of function.
 * Highlighting the element element is displayed and the color of the tracing control is displayed.
 *
 * @param el - Table element to which color of control should be changed
 </en> */
function changeHighlight(el)
{
	var hilightId     = "SpanFlag0";

	if ( el != null || el != undefined )
	{
		var elementList = el.childNodes;

		if ( elementList.length != undefined )
		{
			for (var i = 0; i < elementList.length; i++ )
			{
				var element = elementList[i];
				var elem = element.lastChild;

				if ( element != null && element != undefined &&
				     ( element.tagName == "TD" ) &&
				     (( element.id.indexOf( hilightId ) == -1 ) && 
				      ( element.id != "" )
				     )
				   )
				{
					continue;
				}
				else if ( ( element.fixed != null && element.fixed != undefined ) &&
						  ( element.fixed == 'true' || element.fixed == true )
						)
				{
					continue;
				}

				cellColors[cellColors.length] = element.bgColor;

				element.bgColor = _rowBgColor;

				if ( elem != null && elem != undefined &&
				   ( elem.tagName == "SPAN" || elem.tagName == "A" ) )
				{
					var elementId = elem.id;
					cssArray[elementId] = elem.className;
					styleColorArray[elementId] = elem.style.color;
					elem.bgColor = _rowBgColor;
					elem.style.color = _rowStyleColor;
				}
			}
		}
	}
}

/** <jp>
 * [関数の説明]
 * 矢印キーによる移動と判断するフラグをクリアします。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The flag judged to be a movement with the arrow key is cleared.
 </en> */
function clearArrowMove()
{
	arrowMove = false;
}

var defaultBgColor = "";
var defaultSytleColor = "";
var cssArray = new Array();
var styleColorArray = new Array();
var cellColors = new Array();
var cellColorNum = 0;

var nowRowId = "";
var arrowMove = false;
var isArrow = false;

/** <jp>
 * [関数の説明]
 * 矢印キーによる移動と判断するフラグをクリアします。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The flag judged to be a movement with the arrow key is cleared.
 </en> */
function checkHighlight( which, target , scrollFlag )
{
	if( arrowMove )
	{
		return;
	}

	var el = getElement(event.srcElement, "TR", "TD");
	var el_target="";

	if(target!=null)
	{
		el_target = document.getElementById(target);
	}

	if (el == null)
	{
		return;
	}

	if (which && el.tagName == "TD")
	{
		var row = getElement(el, "TR");

		setHighlightColor( row, el_target , scrollFlag );
	}
}


/** <jp>
 * [関数の説明]
 * リストセルでのハイライト表示を元に戻します。
 * 矢印キーからの操作であれば、Timerを使用せずrestoreを行うのでarrowMoveフラグをクリアします。
 * 
 * [引数の説明]
 * isArrow 矢印キーの移動であればtrue
 *         テーブルのonmouse関数であればfalse
 * target  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 * listcellId  対象のリストセルのID
 </jp> */
/** <en>
 * [Explanation of a function]
 * It returns it based on the highlight display in the ListCell.
 * Because Timer is not used and restore is done if it is an operation from the arrow key, 
 * the arrowMove flag is cleared.
 * [Explanation of an argument]
 * isArrow  If it is a movement of the arrow key, it is true. 
 *          If it is onmouse function of the table, it is false. 
 * target  Details table that becomes pair by type3
 * scrollFlag  If it is leftdetails, it is left.
 *             If it is rightdetails, it is right.
 * listcellId  ID of list cell of object
 </en> */
function restoreHighlight( isArrow , target , scrollFlag , listcellId )
{
	if( isArrow )
	{
		clearArrowMove();
	}
	else
	{
		cellColorNum=0;
	}
	if( arrowMove)
	{

		return;
	}
	if( nowRowId == "" )
	{
		return;
	}
	var el_target ="";
	if(target != null)
	{
		if( listcellId == '' )
		{
			el_target = document.getElementById(target);
		}
		else
		{
			var tmpNowRowId  = document.getElementById( listcellId +'__TABLE__LEFT__DETAIL' );
			for(var i = 0 ; i < tmpNowRowId.rows.length ; i ++ )
			{
				if( nowRowId.id == tmpNowRowId.rows[ i ].id )
				{
					nowRowId = tmpNowRowId.rows[ i ];
					break;
				}
			}
			el_target = document.getElementById( listcellId + '__TABLE__MAIN__DETAIL' );
		}
	}

	var row = nowRowId;

	var table = getElement(row, "TABLE");
	var elm = row.parentElement;

	if ( table == null )
	{
		return;
	}

	if ( (row.tagName == "TR") && 
		 ( row.parentElement != null &&
		   row.parentElement.tagName == "THEAD" 
		 )
	   )
	{
		return;
	}

	var rowArray = getRow( row.id, table );
	var rowArrayTarget = "";

	if(el_target!="")
	{
		rowArrayTarget = getRow( row.id , el_target);
	}

	if (rowArray == undefined)
	{
		return;
	}

	for (var i = 0; i < rowArray.length; i++)
	{
		if( scrollFlag == 'left')
		{
			changeDefault(rowArray[i], i);

			changeDefault(rowArrayTarget[i], cellColorNum );

		}
		else if( scrollFlag == 'right')
		{
			changeDefault(rowArray[i], i);
			changeDefault(rowArrayTarget[i], cellColorNum );
		}
		else
		{

			changeDefault(rowArray[i], i);

		}
	}

	cellColors = new Array();
	cellColorNum = 0;
}


/** <jp>
 * [関数の説明]
 * リストセルでハイライト表示します。
 * [引数の説明]
 * target 対象となるリストセル
 * tablePos  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 </jp> */
/** <en>
 * [Explanation of a function]
 * The highlight display is done in the ListCell.
 * [Explanation of an argument]
 * target  ListCell that becomes object. 
 * tablePos  Details table that becomes pair by type3
 * scrollFlag  If it is leftdetails, it is left.
 *             If it is rightdetails, it is right.
 </en> */
function focusHighlight( target , tablePos , scrollFlag )
{
	var el = getElement(target, "TR", "TD");

	if (el == null)
	{
		return;
	}

	if (el.tagName == "TD")
	{
		var row = getElement(el, "TR");
		
		setHighlightColor( row, tablePos , scrollFlag );
	}
}

/** <jp>
 * [関数の説明]
 * リストセルでハイライト表示します。
 * [引数の説明]
 * row  対象となる行
 * el_target  type3で、対となる明細テーブル
 * scrollFlag  type3で、左の明細であれば'left'
 *                      右の明細であれば'right'
 </jp> */
/** <en>
 * [Explanation of a function]
 * The highlight display is done in the ListCell.
 * [Explanation of an argument]
 * row  Line that becomes object. 
 * el_target  Details table that becomes pair by type3
 * scrollFlag  If it is leftdetails, it is left.
 *             If it is rightdetails, it is right.
 </en> */
function setHighlightColor( row, el_target , scrollFlag )
{
	var table = getElement(row, "TABLE");
	var elm = row.parentElement;

	if ( table == null )
	{
		return;
	}

	if ( row.id =="" )
	{
		return;
	}

	if ( (row.tagName == "TR") && 
		 ( row.parentElement != null &&
		   row.parentElement.tagName == "THEAD" 
		 )
	   )
	{
		return;
	}
		
	if ( row.id != "" )
	{
		nowRowId = row;
	}

	var rowArray = getRow( row.id, table );
	var rowArrayTarget = "";

	if(el_target!="")
	{
		rowArrayTarget = getRow( row.id , el_target);
	}

	if (rowArray == undefined)
	{
		return;
	}

	for (var i = 0; i < rowArray.length; i++)
	{
		if( scrollFlag == 'left')
		{
			changeHighlight(rowArray[i], i);

			if( el_target!="")
			{
				changeHighlight(rowArrayTarget[i], i);
			}
		}
		else if( scrollFlag == 'right')
		{

			if(el_target!="")
			{
				changeHighlight(rowArrayTarget[i], i);
			}
			changeHighlight(rowArray[i], i);
		}
		else
		{
			changeHighlight(rowArray[i], i);

		}
	}	
}

/**
<jp> * [関数の説明]</jp><en> * [Explanation of function] </en>
<jp> * テーブルの行情報を取得します。</jp><en> * Line information on the table is acquired.  </en>
<jp> * [引数の説明]</jp><en> * [Explanation of argument] </en>
<jp> * rowId 対象の行のID</jp><en> * rowId ID of line of object </en>
<jp> * table 対象のテーブル</jp><en> * table Table of object </en>
<jp> * [戻り]</jp><en> * [Return] </en>
<jp> * テーブルの行情報</jp><en> * Line information on table </en>
 */
function getRow( rowId, table )
{
	var ROWMAX = table.rows.length;
	var ret = new Array();

	for ( var i = 0; i < ROWMAX; i++ )
	{
		if ( table.rows[i].id == rowId )
		{
			ret[ret.length] = table.rows[i];
		}
	}

	return ret;
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * エレメント要素をたどりコントロールの色を元にもどします。</jp><en> * The element element is returned based on the color of the tracing control. </en>
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * el 対象のエレメント</jp><en> * el Element of object </en>
<jp> * rowNum 対象の行</jp><en> * rowNum Line of object </en>
 */
function changeDefault(el, rowNum)
{
	var hilightId     = "SpanFlag0";

	if ( el != null || el != undefined )
	{
		var elementList = el.childNodes;

		if( rowNum == 0 )
		{
			cellColorNum = rowNum;
		}

		if ( elementList.length != undefined )
		{
			var k = 0

			if( rowNum > 0 )
			{
				k = cellColorNum;
			}
			for (var i = 0; i < elementList.length; i++ )
			{
				var element = elementList[i];
				var elem = element.lastChild;

				if ( element != null && element != undefined &&
				     ( element.tagName == "TD" ) &&
				     (( element.id.indexOf( hilightId ) == -1 ) &&
				      ( element.id != "" ) )
				   )
				{
					continue;
				}
				else if ( ( element.fixed != null && element.fixed != undefined ) &&
						  ( element.fixed == 'true' || element.fixed == true )
						)
				{
					continue;
				}

				element.bgColor = cellColors[k];
				k++;

				if ( elem != null && elem != undefined &&
				   ( elem.tagName == "SPAN" || elem.tagName == "A" ) )
				{
					var elementId = elem.id;
					elem.className = cssArray[elementId];
					elem.style.color = styleColorArray[elementId];
				}
			}
			cellColorNum = k;
		}
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * エレメント要素をたどり親エレメントを取得します。</jp><en> * The element element is acquired and the tracing parents element is acquired. </en>
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * el 対象のエレメント</jp><en> * el Element of object </en>
 */
function getElement(el)
{
    var tagList = new Object;
    for (var i = 1; i < arguments.length; i++)
    {
        tagList[arguments[i]] = true;
    }
    while ((el != null) && (tagList[el.tagName] == null))
    {
        el = el.parentElement;
    }
    return el;
}

/** <jp>
 * [関数の説明]
 * Pagerの画像変更用関数
 * [引数の説明]
 * event       マウスイベント
 * id          Id
 * btnno       Pagerイベント（ff, prev, nxt, last)
 * contextPath ドキュメントルート
 </jp> */
/** <en>
 * [Explanation of a function]
 * Change the gif files of the pager control.
 * [Explanation of an argument]
 * event       Mouse events
 * id          Id
 * btnno       Pager event（ff, prev, nxt, last)
 * contextPath Document root
 </en> */
function change_PageBtn(event, id, btnno, contextPath)
{
	switch(event)
	{
		case 'over':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_mo.gif";
			break;
		}
		case 'out':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_rglr.gif";
			break;
		}
		case 'down':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_oc.gif";
			break;
		}
		case 'up':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_rglr.gif";
			break;
		}
		case 'click':
		{
			document.images[id].src = contextPath + "/img/control/pager/tbtn_" + btnno + "_oc.gif";
			break;
		}
	}
}


/** <jp>
 * [関数の説明]
 * ショートカットキー入力を制御します。
 * [戻り]
 * ショートカットキーを無効にする場合falseを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * The key input is controlled.
 * [return]
 * When the shortcut key is invalidated, false is returned. 
 </en> */
function ignoreKeyEvent()
{
	// keycode : key
	// 32      : space
	// 36      : Home
	// 37      : <-
	// 39      : ->
	// 115     : F4
	var invalidateAltKey = new Array("32", "36", "37", "39", "115");

	// keycode : key
	// 9       : Tab
	// 53,101  : 5
	// 65      : A
	// 66      : B
	// 67      : C
	// 68      : D
	// 69      : E
	// 70      : F
	// 72      : H
	// 73      : I
	// 76      : L
	// 78      : N
	// 79      : O
	// 80      : P
	// 82      : R
	// 86      : V
	// 87      : W
	// 88      : X
	// 116     : F5
	var invalidateCtrlKey = new Array("9","53","101","65","66","68","69","70","72",
	"73","76","78","79","80","82","87", "116");
	
	//114      : F3
	//115      : F4
	//116      : F5
	//117      : F6
	//121      : F10
	//122      : F11
	var invalidateFunctionKey = new Array("114","115","116","117","121","122");
	//ESC key
	if( window.event.keyCode  == '27' )
	{
		return false;
	}

	// ALT key down
	if (window.event.altKey)
	{
		return invalidateKeyOperation(invalidateAltKey);
	}
	// Ctrl key down
	if (window.event.ctrlKey)
	{
		return invalidateKeyOperation(invalidateCtrlKey);
	}
	
	//BackSpaceKey
	if( window.event.keyCode  == '8' )
	{
		//<jp> インプットタグ、テキストエリアタグ上の場合</jp><en> On the input tag and tag of the text area. </en>
		if( event.srcElement.tagName == "INPUT" || event.srcElement.tagName == "TEXTAREA" )
		{
			//<jp> テキスト、パスワード、ファイルの場合</jp><en> At the text, the password, and the file. </en>
			if( event.srcElement.type == "text" || event.srcElement.type == "password" ||
				event.srcElement.type == "file" || event.srcElement.type == "textarea" )
			{
				//<jp> readOnlyで無い場合</jp><en> When not is in readOnly. </en>
				if( event.srcElement.readOnly == false )
				{
					//<jp> イベント有効</jp><en> Event effective. </en>
					return true;
				}
				else
				{
					//<jp> イベント無効</jp><en> Event invalidity. </en>
					window.event.keyCode = 37;
					return false;
				}
			}
			else
			{
					//<jp> イベント無効</jp><en> Event invalidity. </en>
					window.event.keyCode = 37;
					return false;
			}
		}
		else
		{
			//<jp> イベント無効</jp><en> Event invalidity. </en>
			window.event.keyCode = 53;
			return false;
		}
	}

	//Function Key
	return invalidateKeyOperation(invalidateFunctionKey);
}


/** <jp>
 * [関数の説明]
 * ショートカットキー入力を制御するための関数で、ignoreKeyEventからコールされます。
 * [引数の説明]
 * invalidateKey 無効にしたショートカットキー一覧
 * [戻り]
 * ショートカットキーを無効にする場合falseを返します。
 </jp> */
/** <en>
 * [Explanation of a function]
 * It is called from ignoreKeyEvent by the function to control the key input. 
 * [Explanation of an argument]
 * invalidateKey invalidated key
 * [return]
 * When the shortcut key is invalidated, false is returned. 
 </en> */
function invalidateKeyOperation(invalidateKey)
{
	for (var i = 0; i < invalidateKey.length; i++)
	{
		if (window.event.keyCode == invalidateKey[i])
		{
			try
			{
				window.event.keyCode = 37;
				return false;
			}
			catch(e)
			{
			}
		}
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * メッセージエリアをクリアします</jp><en> * The message area is cleared. </en>
 */
function clearMessage()
{
	message.innerText = "";
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * isValueInput関数から呼ばれます。</jp><en> * It is called from the isValueInput function. </en>
<jp> * 引数の文字列から半角空白を取り除き、文字列のサイズが0の場合はfalseを返します。</jp><en> * The normal-width blank is removed from the character string of the argument, and when the size of the character string is 0, false is returned. </en>
<jp> * 全角空白は1文字と数えるためその場合、trueを返します。</jp><en> * In that case, true is returned with one character to count at the em-size blank. </en>
<jp> * [使用するカスタムタグ名]</jp><en> * Custom tag name used. </en>
 * SubmitButtonTag
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * value チェックを行う値</jp><en> * Value in which value is checked. </en>
 */
function validate(value)
{
	while (value.indexOf(" ") > -1)
	{
		value = value.replace(" ", "");
	}
	if (value.length == 0)
	{
		return false;
	}
	return true;
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 連想配列文字列をArray型にして返します。</jp><en> * The association array character string is made Array type and it returns it. </en>
 * string                         Array
 * --------------------------     ----------------------------
 * "key1:value1,key2:value2"  ->  array[0][0] = "key1"
 *                                array[0][1] = "value1"
 *                                array[1][0] = "key2"
 *                                array[1][1] = "value2"
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * str 連想配列文字列</jp><en> * Str association array character string. </en>
 */
function toArray(str)
{
	var item = str.split(",");
	var array = new Array(item.length);
	for (i in item)
	{
		var relation = item[i].split(":");
		if (relation.length == 2)
		{
			array[i] = new Array(relation[0], relation[1]);
		}
		else if (relation.length == 3)
		{
			array[i] = new Array(relation[0], relation[1], relation[2]);
		}
	}
	return array;
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * メッセージエリアへメッセージをセットします。</jp><en> * The message is set in the message area. </en>
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * MessageResourceのメッセージ</jp><en> * Message of MessageResource. </en>
 */
function setMessage(msg)
{
		if(!document.all.message)return;
		document.all.message.innerHTML = 
		"<img  src=\"" + contextPath + "/img/control/message/Spacer.gif\" "+
		"width=\"4\" height=\"19\">" +
		msg;
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 画面のサイズを変更します。Window.open時にサイズを指定しても、指定したサイズよりも</jp><en> * The size of the screen is changed. From the specified size even if you specify the size at Window.open o'clock. </en>
<jp> * 大きなサイズで開くため、resizeTo関数を使用する必要があります。</jp><en> * It is necessary to use the resizeTo function to open by a big size. </en>
<jp> * サイズの定義はuser.jsにて行います。定義が無い場合は幅1024、高さ768でリサイズします。</jp><en> * The size is defined with user.js. It resizes it by 1024 in width and 768 in height when there is no definition. </en>
 */
function windowResize()
{
	if (typeof _mainWindowWidth == "undefined"){
		_mainWindowWidth = 1280;
	}
	if (typeof _mainWindowHeight == "undefined"){
		_mainWindowHeight = 1024;
	}
	var w = _mainWindowWidth;
	var h = _mainWindowHeight;
	var x = (screen.availWidth  - w) / 2;
	var y = (screen.availHeight - h) / 2;
	// If the width is less than 1024,
	if( screen.availWidth < w )
	{
		x = 0;
		w = screen.availWidth;
	}
	// If the height is less than 768,
	if( screen.availHeight < h )
	{
		y = 0;
		h = screen.availHeight;
	}

	try
	{
		window.resizeTo(w,h);
		window.moveTo(x,y);
	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * JSPに定義されたDynamicAction関数を呼び出します。JSPに定義されていない場合はエラーとせずに、そのまま抜けます。</jp><en> * The DynamicAction function defined in JSP is called. It comes off as it is without doing to make an error when not defined in JSP. </en>
 */
function callDynamicAction()
{
	if(typeof dynamicAction != 'undefined'){ 
		if(typeof targetElement != 'undefined'){
			if (targetElement == "")
			{
				targetElement = event.srcElement.id;
			}
		}
		if(dynamicAction()) event.returnValue = false;
		
		if(typeof targetElement != 'undefined'){
			targetElement = "";
		}
	}
	return true;
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。</jp><en> * Header is corrected, and it synchronizes, and the position of scroll is corrected as the width of the table of detail. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * header ヘッダテーブル</jp><en> * Header header table. </en>
<jp> * detail 明細テーブル</jp><en> * Detail details table. </en>
<jp> * scroll スクロールバー用<div></jp><en> * scroll : scroll bar.<div> </en>
 */
function synchronizedTable( header, detail, scroll )
{
	try
	{
		//<jp> IEでない場合は処理を行いません。</jp><en> When it is not IE, it doesn't process it. </en>
		if( navigator.userAgent.indexOf("MSIE") == -1 )
		{
			retrun;
		}

		//<jp> テーブルの表示状態の同期</jp><en> Synchronization that table displays. </en>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' )
		{
			if ( document.all( detail ) != null )
			{
				document.all( detail ).style.visibility = 'hidden';
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}

		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			if ( document.all( header ) != null )
			{
				document.all( header ).style.visibility = 'hidden';
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}

		//<jp> 同期処理の開始</jp><en> Beginning of synchronous processing. </en>
		var headerCells = document.all( header ).rows[0].cells;
		var detailCells = document.all( detail ).rows[0].cells;

		//<jp> セルの幅を一つずつ比較してWidthを補正していきます。</jp><en> The width of the cell is compared one by one and Width is corrected. </en>
		for ( var i = headerCells.length-1; i >= 0; i-- )
		{
			var headerWidth = headerCells[i].offsetWidth;
			var detailWidth = detailCells[i].offsetWidth;
			var maxWidth = 0;

			//<jp> 同じ場合は次のセルへ</jp><en> To the following cell when it is the same. </en>
			if ( headerWidth  == detailWidth )
			{
				continue;
			}

			//<jp> headerのwidthが大きい場合。</jp><en> When width of header is large. </en>
			if ( headerWidth > detailWidth )
			{
				maxWidth = headerWidth;
			}
			//<jp> detailのwidthが大きい場合。</jp><en> When width of detail is large. </en>
			else
			{
				maxWidth = detailWidth;
			}

			//<jp> 補正値の割り当て</jp><en> Allocation of correction value. </en>
			document.all( header ).rows[0].cells[i].width = maxWidth;
			document.all( detail ).rows[0].cells[i].width = maxWidth;
		}

		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp><en>Synchronization of the maximum size of table. (matches to the small size) </en>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp><en> When width of header is large. </en>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp><en> Width of header is matched to width of detail. </en>
			document.all( header ).style.width = detailTableWidth;
		}
		//<jp> detailのwidthが大きい場合。</jp><en> When width of detail is large. </en>
		else if ( headerTableWidth < detailTableWidth )
		{
			//<jp>detailのwidthをheaderのwidthに合わせる。</jp><en> Width of detail is matched to width of header. </en>
			document.all( detail ).style.width = headerTableWidth;
		}

		//<jp> スクロールバーの幅の取得</jp><en> Width of details and correction of position of scroll bar. </en>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp><en>Physical size - Drawing size in client</en>
		var scrollbarOffSet = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;

		//<jp> スクロール用DIVの幅の修正</jp><en> Correction of width of DIV for scroll. </en>
		//<jp> detailのwidth + スクロールバーの幅</jp><en> Width of detail + width of scrollbar. </en>
		document.all( scroll ).style.width = document.all( detail ).offsetWidth + scrollbarOffSet + "px";
	}
	catch( e )
	{
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * userAgentからWinodwsXPSP2か判断します。</jp><en> * The WinodwsXPSP2 size is determined from userAgent. </en>
 *
<jp> * WinodwsXPSP2の場合はtrueを返却します。それ以外の場合はfalseを返却します。</jp><en> * True is returned for WinodwsXPSP2. False is returned besides. </en>
 */
function isWindowsXPSP2()
{
	return ( navigator.userAgent.indexOf("SV1") != -1 );

}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 1ミリ秒後に指定されたidのコントロールからフォーカスはずします。</jp><en> * Focus is removed from the control of id specified after one millisecond. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * elemId フォーカスをはずしたいエレメントのid</jp><en> * Id of element that wants to remove elemId focus. </en>
 */
function timeoutBlur( elemId )
{
	try
	{
		setTimeout("setBlurTimer('"+elemId+"')",1 );
	}
	catch(e)
	{
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 1ミリ秒後に指定されたidのコントロールからフォーカスはずします。</jp><en> * Focus is removed from the control of id specified after one millisecond. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * elemId フォーカスをはずしたいエレメントのid</jp><en> * Id of element that wants to remove elemId focus. </en>
 */
function setBlurTimer( elemId )
{
    document.all(elemId).blur();
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp><en> *Header is corrected with ListCellType3, and it synchronizes, and the position of div is corrected as the width of the table of detail. </en>
 * 
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * detail スクロール明細 テーブルID</jp><en> * detail Scroll details table ID</en>
<jp> * scroll スクロール明細DIV ID</jp><en> * scroll Scroll details DIV ID</en>
 */
function synchronizedScrollBarSizeFixedSide( detail, scroll )
{
	//<jp> スクロールバーの幅の取得</jp><en> Width of details and correction of position of scroll bar. </en>
	//<jp> 物理サイズ - クライアントでの描画サイズ</jp><en>Physical size - Drawing size in client</en>
	var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
	//<jp> スクロールバーの高さの取得</jp><en> Height of details and correction of position of scroll bar. </en>
	var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;

	//<jp> スクロール用DIVの幅の修正</jp><en> Correction of width of DIV for scroll. </en>
	//<jp> detailのwidth + スクロールバーの幅</jp><en> Width of detail + width of scrollbar. </en>
	document.all( scroll ).style.width = document.all( scroll ).offsetWidth + scrollbarOffSetWidth + "px";
	document.all( scroll ).style.height = document.all( scroll ).offsetHeight + scrollbarOffSetHeight + "px";

	if( document.all( scroll ).offsetWidth > document.all( detail ).offsetWidth )
	{
		document.all( scroll ).style.width = 0;
		document.all( scroll ).style.overflowX="hidden";
	}
	else
	{
		document.all( scroll ).style.width = document.all( detail ).offsetWidth
	}

	if( document.all( scroll ).offsetHeight > document.all( detail ).offsetHeight )
	{
		document.all( scroll ).style.height = 0;
		document.all( scroll ).style.overflowY="hidden";
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp><en> *Header is corrected with ListCellType3, and it synchronizes, and the position of div is corrected as the width of the table of detail. </en>
 * 
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * header 列固定ヘッダ テーブルID</jp><en> * Header row fixation header table ID</en>
<jp> * detail 列固定明細 テーブルID</jp><en> * detail Row fixation header table ID</en>
<jp> * scrollHeader スクロールヘッダ テーブルID</jp><en> * scrollHeader Scroll header table ID</en>
<jp> * scrollDetail スクロール明細 テーブルID</jp><en> * scrollDetail Scroll details table ID</en>
<jp> * divLeftHeader 列固定ヘッダDIV ID</jp><en> * divLeftHeader Row fixation header DIV ID</en>
<jp> * divLeft 列固定明細DIV ID</jp><en> * divLeft  Row fixation details DIV ID</en>
<jp> * divHeader スクロールヘッダDIV ID</jp><en> * divHeader Scroll header DIV ID</en>
<jp> * divDetail スクロール明細DIV ID</jp><en> * divDetail Scroll details DIV ID</en>
<jp> * fixedBank 固定明細位置</jp><en> * fixedBank Fixed details position</en>
<jp> * displayWidth DIVの幅</jp><en> * displayWidth Width of DIV</en>
<jp> * displayHeight DIVの高さ</jp><en> * displayHeight Height of DIV</en>
 */
function synchronizedScrollBarSizeScrollSide( header , detail , scrollHeader, scrollDetail ,
	divLeftHeader, divLeft ,divHeader , divDetail, fixedBank, displayWidth, displayHeight )
{
	try
	{
		var headerStatus = true;
		var detailStatus = true;
		var scrollHeaderStatus = true;
		var scrollDetailStatus = true;

		if( navigator.userAgent.indexOf("MSIE") == -1 )
		{
			retrun;
		}

		// <jp>固定ヘッダの出力状態の確認を行います。</jp><en>It confirms it with a fixed header outputs it. </en>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' ||
		     fixedBank <= 0 )
		{
			headerStatus = false ;
		}

		// <jp>スクロールヘッダの出力状態の確認を行います。</jp><en>It confirms it with the scroll header outputs it. </en>
		if ( document.all( scrollHeader ) == null ||
		    document.all( scrollHeader ).style.visibility == 'hidden' )
		{
			scrollHeaderStatus = false ;
		}

		// <jp>固定明細の出力状態の確認を行います。</jp><en>It confirms it with a fixed detail outputs it. </en>
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			detailStatus = false ;
		}

		if( ( document.all( detail ).rows.length == 0 ))//&& document.all( scrollDetail ).rows.length == 0 ) )
		{
			detailStatus = false ;
		}

		// <jp>スクロール明細の出力状態の確認を行います。</jp><en>It confirms it with the scroll detail outputs it. </en>
		if ( document.all( scrollDetail ) == null ||
		     document.all( scrollDetail ).style.visibility == 'hidden' )
		{
			scrollDetailStatus = false ;
		}
		if(document.all( scrollDetail ).rows.length == 0 )
		{
			scrollDetailStatus = false ;
		}
		else if(document.all( scrollDetail ).rows[0].childNodes.length == 0 )
		{
			scrollDetailStatus = false ;
		}

		// <jp>固定ヘッダ・明細が出力されている場合は同期処理を行います。</jp><en>When a fixed header and details are output, the same period is processed. </en>
		if( headerStatus && detailStatus )
		{
			//<jp> 固定ヘッダ、明細の同期処理を行います。</jp><en>Fixed header and details are synchronously processed.  </en>
			synchronizeDIVScroll( header, detail, divLeft, -1, -1 );
		}
		// <jp>スクロールヘッダ・明細が出力されている場合は同期処理を行います。</jp><en>When a scroll header and details are output, the same period is processed. </en>
		if( scrollHeaderStatus && scrollDetailStatus )
		{
			//<jp> スクロールヘッダ、明細の同期処理を行います。</jp><en>Scroll header and details are synchronously processed.  </en>
			synchronizeDIVScroll( scrollHeader, scrollDetail, divDetail, displayWidth, displayHeight );
		}

		// <jp>スクロールヘッダ、明細を出力しない場合はスクロールバーを非表示にします。</jp><en>When neither the scroll header nor details are output, the scroll bar is made non-display. </en>
		if( !scrollHeaderStatus && !scrollDetailStatus )
		{
			document.all( divDetail ).style.overflow="hidden";
		}

		//<jp> 補正値を計算し、再度スクロールバーの表示位置を修正します。</jp><en>The correction value is calculated, and the position where the scroll bar is displayed is corrected again.  </en>
		var scrollyFlg = false;
		var scrollxFlg = false;

		var divWidth= document.all( divDetail ).offsetWidth;
		var divHeight= document.all( divDetail ).offsetHeight;
		var scrollBarWidth = 0;
		var scrollBarHeight = 0;

		scrollBarWidth = document.all( divDetail ).offsetWidth - document.all( divDetail ).clientWidth;
		scrollBarHeight = document.all( divDetail ).offsetHeight - document.all( divDetail ).clientHeight;

		if( scrollDetailStatus )
		{
			//<jp> テーブルの幅よりDIVタグの幅が大きければ、テーブルの幅にあわせます。</jp><en>If the width of the DIV tag is larger than the width of the table, it puts it together on the width of the table. </en>
			if(divWidth > document.all( scrollDetail ).offsetWidth)
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				document.all( divHeader ).style.width = document.all( scrollDetail ).offsetWidth;

				//<jp> ヘッダDIVの幅を明細に合わせます。</jp><en> The width of header DIV is matched to details. </en>
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				if(divHeight < document.all( scrollDetail ).offsetHeight)
				{
					//<jp> 明細の幅とスクロールバーの位置の補正。</jp><en>Width of details and correction of position of scroll bar. </en>
					document.all( divDetail ).style.width = (document.all( scrollDetail ).offsetWidth + scrollBarWidth);
				}

				//<jp> 横スクロールが表示されないため、スクロールバーの幅をマイナスします。</jp><en>Because a horizontal scroll is not displayed, the width of the scroll bar is subtracted.</en>
				document.all( divDetail ).style.height = document.all( divDetail ).offsetHeight - scrollBarHeight ;
				document.all( divDetail ).style.overflowX="hidden";
			}
			else
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				if(divHeight > document.all( scrollDetail ).offsetHeight)
				{
					document.all( divDetail ).style.width = divWidth + "px";
				}

				// <jp>ヘッダの同期処理を行っていなければ、スクロールバーの同期処理を行います。</jp><en>If the header is not synchronously processed, the scroll bar is synchronously processed. </en>
				if( !scrollHeaderStatus )
				{
					document.all( divDetail ).style.height = ( document.all( divDetail ).offsetHeight + scrollBarHeight );
				}
			}

			var nextDivHeight = ( document.all( scrollDetail ).offsetHeight > document.all( detail ).offsetHeight ) ? document.all( scrollDetail ).offsetHeight : document.all( detail ).offsetHeight;

			//<jp> テーブルの高さよりDIVタグの高さが大きければ、テーブルの高さにあわせます。</jp><en>If the height of the DIV tag is larger than the height of the table, it puts it together on the height of the table. </en>
			if(divHeight > nextDivHeight )
			{
				document.all( divLeft ).style.height = nextDivHeight;
				document.all( divDetail ).style.height =( document.all( scrollDetail ).offsetHeight + scrollBarHeight );

				//<jp>スクロールバーを出さないのでDIVタグの幅よりスクロールバーの分マイナスする。</jp><en>Because the scroll bar is not put out, the amount of the scroll bar is subtracted more than the width of the DIV tag.  </en>
				document.all( divDetail ).style.width = ( document.all( divDetail ).offsetWidth - scrollBarWidth );
				document.all( divDetail ).style.overflowY="hidden";
				
			}
		}
		else
		{
			if( detailStatus )
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp><en>Hide scroll bar when detail don't exist.</en>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、スクロールDIVタグの高さ・幅をゼロにする</jp><en>Resize scroll div tag to zero when detail don't exist.</en>
				document.all( divHeader ).style.height = 0;
				document.all( divHeader ).style.width  = 0;
				document.all( divDetail ).style.height = 0;
				document.all( divDetail ).style.width  = 0;

				if ( displayHeight > 0 && displayHeight < document.all( detail ).offsetHeight )
				{
					document.all( divLeft ).style.overflowY="scroll";
					var nextHeight = ( displayHeight > document.all( detail ).offsetHeight ) ? document.all( detail ).offsetHeight : displayHeight;

					document.all( divLeft ).style.height = nextHeight;
					document.all( divLeft ).style.overflowY = "scroll";

					document.all( divLeft ).style.width =
						document.all( divLeft ).offsetWidth + ( document.all( divLeft ).offsetWidth - document.all( divLeft ).clientWidth );
				}
				
			}
			else
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp><en>Hide scroll bar when detail don't exist.</en>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、DIVタグの高さをゼロにする</jp><en>Resize div tag to zero when detail don't exist.</en>
				document.all( divLeft ).style.height = 0;
				document.all( divDetail ).style.height = 0;

				if ( displayWidth > 0 && displayWidth < document.all( scrollHeader ).offsetWidth )
				{
					var nextWidth = ( displayWidth > document.all( scrollHeader ).offsetWidth ) ? document.all( scrollHeader ).offsetWidth : displayWidth;

					document.all( divHeader ).style.width = nextWidth;
					document.all( divHeader ).style.overflowX="scroll";
				}

			}
		}
	}
	catch( e )
	{
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。(type3用)</jp><en> * Header is corrected, and it synchronizes, and the position of scroll is corrected as the width of the table of detail. (for type3)</en>
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * header ヘッダテーブル</jp><en> * Header header table. </en>
<jp> * detail 明細テーブル</jp><en> * Detail details table. </en>
<jp> * scroll スクロールバー用<div></jp><en> * scroll : scroll bar.<div> </en>
<jp> * displayWidth DIVの幅</jp><en> * displayWidth Width of DIV</en>
<jp> * displayHeight DIVの高さ</jp><en> * displayHeight Height of DIV</en>
 */
function synchronizeDIVScroll( header, detail, scroll, displayWidth, displayHeight )
{
	try
	{
		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp><en>Synchronization of the maximum size of table. (matches to the small size) </en>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp><en> When width of header is large. </en>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp><en> Width of header is matched to width of detail. </en>
			document.all( header ).style.width = detailTableWidth;
			document.all( detail ).style.width = detailTableWidth;
		}
		//<jp> detailのwidthが大きい場合。</jp><en> When width of detail is large. </en>
		else if ( headerTableWidth < detailTableWidth )
		{
			//<jp> detailのwidthをheaderのwidthに合わせる。</jp><en> Width of detail is matched to width of header. </en>
			document.all( detail ).style.width = headerTableWidth;
			document.all( header ).style.width = headerTableWidth;
		}

		var dispW = ( displayWidth == -1 ) ? document.all( scroll ).offsetWidth : displayWidth;
		var dispH = ( displayHeight == -1 ) ? document.all( scroll ).offsetHeight : displayHeight;

		if ( parseInt( dispW ) > document.all( detail ).offsetWidth )
		{
			dispW = document.all( scroll ).offsetWidth;
		}

		if ( parseInt( dispH ) > document.all( detail ).offsetHeight )
		{
			dispH = document.all( scroll ).offsetHeight;
		}

		//<jp> スクロールバーの幅の取得</jp><en> Width of details and correction of position of scroll bar. </en>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp><en>Physical size - Drawing size in client</en>
		var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
		//<jp> スクロールバーの高さの取得</jp><en> Height of details and correction of position of scroll bar. </en>
		var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;

		//<jp> スクロール用DIVの幅の修正</jp><en> Correction of width of DIV for scroll. </en>
		//<jp> detailのwidth + スクロールバーの幅</jp><en> Width of detail + width of scrollbar. </en>
		document.all( scroll ).style.width = dispW + scrollbarOffSetWidth + "px";
		document.all( scroll ).style.height = dispH + scrollbarOffSetHeight + "px";

	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ListCellType3でheaderとdetailのテーブルの幅を同期させ、divの位置を修正します。</jp><en> *Header is corrected with ListCellType3, and it synchronizes, and the position of div is corrected as the width of the table of detail. </en>
 * 
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * header 列固定ヘッダ テーブルID</jp><en> * Header row fixation header table ID</en>
<jp> * detail 列固定明細 テーブルID</jp><en> * detail Row fixation header table ID</en>
<jp> * scrollHeader スクロールヘッダ テーブルID</jp><en> * scrollHeader Scroll header table ID</en>
<jp> * scrollDetail スクロール明細 テーブルID</jp><en> * scrollDetail Scroll details table ID</en>
<jp> * divLeftHeader 列固定ヘッダDIV ID</jp><en> * divLeftHeader Row fixation header DIV ID</en>
<jp> * divLeft 列固定明細DIV ID</jp><en> * divLeft  Row fixation details DIV ID</en>
<jp> * divHeader スクロールヘッダDIV ID</jp><en> * divHeader Scroll header DIV ID</en>
<jp> * divDetail スクロール明細DIV ID</jp><en> * divDetail Scroll details DIV ID</en>
 */
function synchronizedScrollTable( header , detail , scrollHeader, scrollDetail ,divLeftHeader, divLeft ,divHeader , divDetail)
{
	try
	{
		var headerStatus = true;
		var detailStatus = true;
		var scrollHeaderStatus = true;
		var scrollDetailStatus = true;

		if( navigator.userAgent.indexOf("MSIE") == -1 )
		{
			retrun;
		}

		// <jp>固定ヘッダの出力状態の確認を行います。</jp><en>It confirms it with a fixed header outputs it. </en>
		if ( document.all( header ) == null ||
		     document.all( header ).style.visibility == 'hidden' )
		{
			headerStatus = false ;
		}

		// <jp>スクロールヘッダの出力状態の確認を行います。</jp><en>It confirms it with the scroll header outputs it. </en>
		if ( document.all( scrollHeader ) == null ||
		    document.all( scrollHeader ).style.visibility == 'hidden' )
		{
			scrollHeaderStatus = false ;
		}

		// <jp>固定明細の出力状態の確認を行います。</jp><en>It confirms it with a fixed detail outputs it. </en>
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			detailStatus = false ;
		}

		if( ( document.all( detail ).rows.length == 0 ))//&& document.all( scrollDetail ).rows.length == 0 ) )
		{
			detailStatus = false ;
		}

		// <jp>スクロール明細の出力状態の確認を行います。</jp><en>It confirms it with the scroll detail outputs it. </en>
		if ( document.all( scrollDetail ) == null ||
		     document.all( scrollDetail ).style.visibility == 'hidden' )
		{
			scrollDetailStatus = false ;
		}
		if(document.all( scrollDetail ).rows.length == 0 )
		{
			scrollDetailStatus = false ;
		}
		else if(document.all( scrollDetail ).rows[0].childNodes.length == 0 )
		{
			scrollDetailStatus = false ;
		}

		// <jp>固定ヘッダ・明細が出力されている場合は同期処理を行います。</jp><en>When a fixed header and details are output, the same period is processed. </en>
		if( headerStatus && detailStatus )
		{
			//<jp> 固定ヘッダ、明細の同期処理を行います。</jp><en>Fixed header and details are synchronously processed.  </en>
			synchronizedTableType3( header, detail, divLeft );
		}
		// <jp>スクロールヘッダ・明細が出力されている場合は同期処理を行います。</jp><en>When a scroll header and details are output, the same period is processed. </en>
		if( scrollHeaderStatus && scrollDetailStatus )
		{
			//<jp> スクロールヘッダ、明細の同期処理を行います。</jp><en>Scroll header and details are synchronously processed.  </en>
			synchronizedTableType3( scrollHeader, scrollDetail, divDetail );
		}

		// <jp>スクロールヘッダ、明細を出力しない場合はスクロールバーを非表示にします。</jp><en>When neither the scroll header nor details are output, the scroll bar is made non-display. </en>
		if( !scrollHeaderStatus && !scrollDetailStatus  ) 
		{
			document.all( divDetail ).style.overflow="hidden";
		}

		//<jp> 補正値を計算し、再度スクロールバーの表示位置を修正します。</jp><en>The correction value is calculated, and the position where the scroll bar is displayed is corrected again.  </en>
		var scrollyFlg = false;
		var scrollxFlg = false;

		var divWidth= document.all( divDetail ).offsetWidth;
		var divHeight= document.all( divDetail ).offsetHeight;
		var scrollBarWidth = 0;
		var scrollBarHeight = 0;

		scrollBarWidth = document.all( divDetail ).offsetWidth - document.all( divDetail ).clientWidth;
		scrollBarHeight = document.all( divDetail ).offsetHeight - document.all( divDetail ).clientHeight;

		if( scrollDetailStatus )
		{
			//<jp> テーブルの幅よりDIVタグの幅が大きければ、テーブルの幅にあわせます。</jp><en>If the width of the DIV tag is larger than the width of the table, it puts it together on the width of the table. </en>
			if(divWidth > document.all( scrollDetail ).offsetWidth)
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				document.all( divHeader ).style.width = document.all( scrollDetail ).offsetWidth;

				//<jp> ヘッダDIVの幅を明細に合わせます。</jp><en> The width of header DIV is matched to details. </en>
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				if(divHeight < document.all( scrollDetail ).offsetHeight)
				{
					//<jp> 明細の幅とスクロールバーの位置の補正。</jp><en>Width of details and correction of position of scroll bar. </en>
					document.all( divDetail ).style.width = (document.all( scrollDetail ).offsetWidth + scrollBarWidth);
				}

				//<jp> 横スクロールが表示されないため、スクロールバーの幅をマイナスします。</jp><en>Because a horizontal scroll is not displayed, the width of the scroll bar is subtracted.</en>
				document.all( divDetail ).style.height = document.all( divDetail ).offsetHeight - scrollBarHeight ;
				document.all( divDetail ).style.overflowX="hidden";
			}
			else
			{
				//<jp> テーブルの高さよりDIVタグの高さが大きければ、スクロールバーを出さないため、スクロール幅をプラスしません。</jp><en>If the height of the DIV tag is larger than the height of the table, the width of the scroll is not added because the scroll bar is not put out. </en>
				if(divHeight > document.all( scrollDetail ).offsetHeight)
				{
					document.all( divDetail ).style.width = divWidth + "px";
				}

				// <jp>ヘッダの同期処理を行っていなければ、スクロールバーの同期処理を行います。</jp><en>If the header is not synchronously processed, the scroll bar is synchronously processed. </en>
				if( !scrollHeaderStatus )
				{
					document.all( divDetail ).style.height = ( document.all( divDetail ).offsetHeight + scrollBarHeight );
				}
			}

			var nextDivHeight = ( document.all( scrollDetail ).offsetHeight > document.all( detail ).offsetHeight ) ? document.all( scrollDetail ).offsetHeight : document.all( detail ).offsetHeight;

			//<jp> テーブルの高さよりDIVタグの高さが大きければ、テーブルの高さにあわせます。</jp><en>If the height of the DIV tag is larger than the height of the table, it puts it together on the height of the table. </en>
			if(divHeight > nextDivHeight )
			{
				document.all( divLeft ).style.height = nextDivHeight;
				document.all( divDetail ).style.height =( document.all( scrollDetail ).offsetHeight + scrollBarHeight );

				//<jp>スクロールバーを出さないのでDIVタグの幅よりスクロールバーの分マイナスする。</jp><en>Because the scroll bar is not put out, the amount of the scroll bar is subtracted more than the width of the DIV tag.  </en>
				document.all( divDetail ).style.width = ( document.all( divDetail ).offsetWidth - scrollBarWidth );
				document.all( divDetail ).style.overflowY="hidden";
			}
		}
		else
		{
			if( detailStatus )
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp><en>Hide scroll bar when detail don't exist.</en>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、スクロールDIVタグの高さ・幅をゼロにする</jp><en>Resize scroll div tag to zero when detail don't exist.</en>

				document.all( divHeader ).style.height = 0;
				document.all( divHeader ).style.width  = 0;
				document.all( divDetail ).style.height = 0;
				document.all( divDetail ).style.width  = 0;

			}
			else
			{
				// <jp>明細が表示されないので、スクロールバーを非表示にする</jp><en>Hide scroll bar when detail don't exist.</en>
				document.all( divDetail ).style.overflowX="hidden";
				document.all( divDetail ).style.overflowY="hidden";

				// <jp>明細が表示されないので、DIVタグの高さをゼロにする</jp><en>Resize div tag to zero when detail don't exist.</en>
				document.all( divLeft ).style.height = 0;
				document.all( divDetail ).style.height = 0;

				// <jp>DIVのサイズによりヘッダが隠れてしまうため、ヘッダDIVにはTABLEタグのサイズを適用する。</jp><en>Resize head div to table tag size.</en>
				document.all( divHeader ).style.width = document.all( scrollHeader ).offsetWidth;
			}
		}
	}
	catch( e )
	{
	}
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * headerとdetailのテーブルの幅を同期させ、scrollの位置を修正します。(type3用)</jp><en> * Header is corrected, and it synchronizes, and the position of scroll is corrected as the width of the table of detail. (for type3)</en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * header ヘッダテーブル</jp><en> * Header header table. </en>
<jp> * detail 明細テーブル</jp><en> * Detail details table. </en>
<jp> * scroll スクロールバー用<div></jp><en> * scroll : scroll bar.<div> </en>
 */
function synchronizedTableType3( header, detail, scroll )
{
	try
	{
		//<jp> 同期処理の開始</jp><en> Beginning of synchronous processing. </en>
		var headerCells = document.all( header ).rows[0].cells;
		var detailCells = document.all( detail ).rows[0].cells;
		var maxWidth = 0;

		//<jp> セルの幅を一つずつ比較してWidthを補正していきます。</jp><en> The width of the cell is compared one by one and Width is corrected. </en>
		for ( var i = headerCells.length-1; i >= 0; i-- )
		{
			var headerWidth = headerCells[i].offsetWidth;
			var detailWidth = detailCells[i].offsetWidth;

			var headerPadding = distillNumber(headerCells[i].currentStyle.paddingRight) + distillNumber(headerCells[i].currentStyle.paddingLeft);
			var detailPadding = distillNumber(detailCells[i].currentStyle.paddingRight) + distillNumber(detailCells[i].currentStyle.paddingLeft);

			headerWidth += headerPadding;
			detailWidth += detailPadding;

			//<jp> 同じ場合は次のセルへ</jp><en> To the following cell when it is the same. </en>
			if ( headerWidth  == detailWidth )
			{
				maxWidth = headerWidth;
			}

			var isHeaderWidth = false;
			//<jp> headerのwidthが大きい場合。</jp><en> When width of header is large. </en>
			if ( headerWidth > detailWidth )
			{
				maxWidth = headerWidth;
				isHeaderWidth = true;
			}
			//<jp> detailのwidthが大きい場合。</jp><en> When width of detail is large. </en>
			else
			{
				maxWidth = detailWidth;
			}

			//<jp> 補正値の割り当て</jp><en> Allocation of correction value. </en>
			document.all( header ).rows[0].cells[i].width = maxWidth;
			document.all( detail ).rows[0].cells[i].width = maxWidth;

//			if ( isHeaderWidth )
//			{
				for ( var j = 0; j < document.all( detail ).rows.length; j++ )
				{
					document.all( detail ).rows[j].cells[i].width = maxWidth;
				}
//			}
		}

		//<jp> テーブル最大サイズの同期（小さいほうに合わせる）</jp><en>Synchronization of the maximum size of table. (matches to the small size) </en>
		var headerTableWidth = document.all( header ).offsetWidth;
		var detailTableWidth = document.all( detail ).offsetWidth;

		//<jp> headerのwidthが大きい場合。</jp><en> When width of header is large. </en>
		if ( headerTableWidth > detailTableWidth )
		{
			//<jp> headerのwidthをdetailのwidthに合わせる。</jp><en> Width of header is matched to width of detail. </en>
			document.all( header ).style.width = detailTableWidth;
			document.all( detail ).style.width = detailTableWidth;
		}
		//<jp> detailのwidthが大きい場合。</jp><en> When width of detail is large. </en>
		else if ( headerTableWidth < detailTableWidth )
		{
			//<jp> detailのwidthをheaderのwidthに合わせる。</jp><en> Width of detail is matched to width of header. </en>
			document.all( detail ).style.width = headerTableWidth;
			document.all( header ).style.width = headerTableWidth;
		}

		//<jp> スクロールバーの幅の取得</jp><en> Width of details and correction of position of scroll bar. </en>
		//<jp> 物理サイズ - クライアントでの描画サイズ</jp><en>Physical size - Drawing size in client</en>
		var scrollbarOffSetWidth = document.all( scroll ).offsetWidth - document.all( scroll ).clientWidth;
		//<jp> スクロールバーの高さの取得</jp><en> Height of details and correction of position of scroll bar. </en>
		var scrollbarOffSetHeight = document.all( scroll ).offsetHeight - document.all( scroll ).clientHeight;

		//<jp> スクロール用DIVの幅の修正</jp><en> Correction of width of DIV for scroll. </en>
		//<jp> detailのwidth + スクロールバーの幅</jp><en> Width of detail + width of scrollbar. </en>
		document.all( scroll ).style.width = document.all( scroll ).offsetWidth + scrollbarOffSetWidth + "px";
		document.all( scroll ).style.height = document.all( scroll ).offsetHeight + scrollbarOffSetHeight + "px";
	}
	catch( e )
	{
	}
}



/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 数値の単位などの場合、数字以外の文字が登場するまでの数値を返します。</jp><en> * The numerical value until characters other than the figure appear is returned for the unit etc. of the numerical value. </en>
 * 
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * value 数値＋文字列 </jp><en> * value Numerical value + character string </en>

<jp> * [戻り]</jp><en> * Return</en>
<jp> * 数値のみ</jp><en> * Only the numerical value</en>
 */
function distillNumber( value )
{
	var ret = "";
	for( var i = 0; i < value.length; i++ )
	{
		if( value.charAt(i).match(/[0-9]/g ) )
		{
			ret += value.charAt(i);
		}
		else
		{
			break;
		}
	}
	if ( ret.length == 0 )
	{
		ret = 0;
	}
	return parseInt( ret );
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 伸縮リストセルの展開、縮小処理。</jp><en> * Development and reduction processing of expansion and contraction list cell.  </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * tableId テーブルId</jp><en> * tableId table Id</en>
<jp> * divId DIVId</jp><en> * divId DIV Id </en>
<jp> * fixedBank 表示位置</jp><en> * fixedBank View Point</en>
 */
function expandTable( tableId, divId, fixedBank )
{
	var divOffSet = 16;
	var table = document.all( tableId );
	var div = document.all( divId );
	var isExpand = document.all( tableId + "__EXPAND" ).value;

	if ( table == null || table.style.visibility == 'hidden' || fixedBank <= 0 )
	{
		return;
	}

	if ( table.rows == null || table.rows.length <= 0 )
	{
		return;
	}

	var headerCells = table.rows[0].cells;

// TODO Fukumori 条件が違う
//	if ( isExpand == "true" ||  fixedBank >= headerCells.length )
	if ( isExpand == "true" ||  fixedBank > headerCells.length )
	{
		expandVisibility( table, fixedBank, "block" );

		document.all( tableId + "__right" ).style.visibility = "visible";
		document.all( tableId + "__EXPAND" ).value = "false";
		document.all( tableId + "__left" ).src = contextPath + "/img/control/listcell/1_1.gif"
	}
	else
	{
		document.all( tableId + "__right" ).style.visibility = "hidden";
		document.all( tableId + "__left" ).src = contextPath + "/img/control/listcell/1_2.gif"

		var width = 0;

		for ( var i = 0; i < fixedBank; i++ )
		{
			width += headerCells[i].offsetWidth;
			width += distillNumber(headerCells[i].currentStyle.paddingRight) + distillNumber(headerCells[i].currentStyle.paddingLeft);
		}

		expandVisibility( table, fixedBank, "none" );

		document.all( tableId + "__EXPAND" ).value = "true";
	}

	if ( div.offsetHeight > table.offsetHeight )
	{
		div.style.height = table.offsetHeight;
	}
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 伸縮リストセルの非表示化</jp><en> * Hodden and reduction processing of expansion and contraction list cell.  </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * table テーブル</jp><en> * tableId table Id</en>
<jp> * fixedBank 表示位置</jp><en> * fixedBank View Point</en>
<jp> * visible 表示</jp><en> * visible View</en>
 */
function expandVisibility( table, fixedBank, visible )
{
	var rowLength = table.rows.length; 
	var rowsize = 0;
	var trId = "___";
	
	// １レコード分の行数の取得
	for(var i=0; i<rowLength; i++)
	{
		var cell = table.rows[i].cells[0];
		if(i==0)
		{
			trId = cell.parentNode.id;
		}
		else if(trId != cell.parentNode.id)
		{	
			rowsize = i;
			break;
		}
	}
	
	/** １レコードの rowSpan によるズレの補正用配列 */
	var ajastArray = new Array(rowsize);
	
	/** １レコードの各行に対する実際の FixedBank */
	var limitArray = new Array(rowsize);
	
	// 配列の初期化
	for(var i=0; i<rowsize; i++)
	{
		ajastArray[i] = 0;
		limitArray[i] = 0;
	}
	
	// １レコードの各行に対する実際の FixedBank の計算
	for(var i=0; i<rowsize; i++)
	{
		/** colSpan, rowSpan を含めた実際の列位置 */
		var realColCount = ajastArray[i];

		var colLength = table.rows[i].cells.length;
		limitArray[i] = colLength;
		for(var j=0; j<colLength; j++)
		{
			var cell = table.rows[i].cells[j];
			
			// colSpan の補正
			realColCount += cell.colSpan;
			for(var k=1; k<cell.rowSpan; k++)
			{
				// rowSpan の分以降の行を補正する。
				ajastArray[k+i] += cell.colSpan;
			}
			
			// FixedBank を超えたら境界とする。
			if(realColCount >= fixedBank)
			{
				limitArray[i] = j;
				break;
			}
		}
	}

	// 表示・非表示の書換え
	for ( var i = 0; i < rowLength; i++ )
	{
		var colLength = table.rows[i].cells.length;
		var index = (i==0) ? 0 : i%rowsize
		for(var j = limitArray [index] + 1; j<colLength; j++)
		{
			var cellStyle = table.rows[i].cells[j].style;
			// 表示に差分があるときのみ書換え
			if( cellStyle.display != visible)
			{
				cellStyle.display = visible;
			}
		}
	}

	return;


// 以下変更前

	var headers = table.rows;
	var rowSpans = new Array();

	for ( var i = 0; i < headers.length; i++ )
	{
		rowSpans[i] = new Array();

		var cells = headers[i].cells;
		var offsetIndex = 0;
		var end = fixedBank;//( fixedBank < cells.length ) ? fixedBank : cells.length;

		for ( var j = 0; j < cells.length; j++ )
		{
			var cell = cells[j];

			var rowspan = parseInt( cell.rowSpan );
			var colspan = parseInt( cell.colSpan );

			rowSpans[i][j] = rowspan - 1;

			for ( var n = 0; n < end; n++ )
			{
				for ( var k = i-1; k >= 0; k-- )
				{
					var offsetRowSpan = rowSpans[ k ];
					if ( offsetRowSpan != null )
					{
						var offsetSpan = offsetRowSpan[ offsetIndex ];
						if ( offsetSpan != null )
						{
							var offset = parseInt( offsetSpan );
							if ( k + offset >= i )
							{
								offsetIndex++;
							}
						}
					}
				}
			}

			if ( offsetIndex >= end )
			{
				cell.style.display = visible;
			}

			if ( colspan > 1 )
			{
				offsetIndex += ( colspan -1 );
			}

			offsetIndex++;

		}
	}
}

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * リストセルType3の固定明細とスクロール明細のテーブルの高さを同期させます。</jp><en> * Fixed details of list cell Type3 are synchronized with the height of the table of the scroll details. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * leftDetail 固定明細テーブル</jp><en> * leftDetail Fixed details table</en>
<jp> * rightDetail スクロール明細テーブル</jp><en> * rightDetail Scroll details table</en>
 */
function synchronizedTableDetail( leftDetail, rightDetail )
{
	try
	{
		if( navigator.userAgent.indexOf("MSIE") == -1 )
		{
			return;
		}

		var leftBody = document.all( leftDetail );
		var rightBody = document.all( rightDetail );
		var array = new Array();

		for ( var i = 0 ; i < rightBody.rows.length ; i++ )
		{
			var maxHeight = 0;
			var leftDetailHeight = 0;
			var rightDetailHeight = 0;

			// <jp>固定明細の最大の高さを取得します。結合されているセルについてはチェックを行いません。</jp>
			// <en>The maximum height of fixed details is acquired. United cells are not checked. </en>
			for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
			{
				var tmpHeight = leftBody.rows[ i ].childNodes[ j ].offsetHeight;
				var span = leftBody.rows[ i ].childNodes[ j ].rowSpan ;
				if ( leftDetailHeight < tmpHeight 
					&& span == 1 )
				{
					leftDetailHeight = tmpHeight;
				}
				// <jp>結合されているセルである場合</jp>
				// <en>When it is united cells</en>
				if ( span > 1 )
				{
					// <jp>セルの位置と、結合数を取得します。</jp>
					// <en>The position and the number of uniting of cells are acquired. </en>
					array[array.length] = new Array( i , j , span , true)
				}
			}

			// <jp>スクロール明細の最大の高さを取得します。結合されているセルについてはチェックを行いません。</jp>
			// <en>The maximum height of the scroll details is acquired. United cells are not checked. </en>
			for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
			{
				var tmpHeight = rightBody.rows[ i ].childNodes[ j ].offsetHeight;
				var span = rightBody.rows[ i ].childNodes[ j ].rowSpan ;
				if ( rightDetailHeight < tmpHeight 
					&& rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
				{
					rightDetailHeight = tmpHeight;
				}
				// <jp>結合されているセルである場合</jp>
				// <en>When it is united cells</en>
				if ( span > 1 )
				{
					array[array.length] = new Array( i , j , span , false )
				}
			}

			// <jp>固定のheightが明細より大きい場合。</jp>
			// <en>When fixed height is larger than details. </en>
			if ( leftDetailHeight > rightDetailHeight )
			{
				for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						rightBody.rows[ i ].cells[ j ].height = leftDetailHeight;
						// <jp>最大の高さのセルがあれば、他のセルはそれにあわすため後続処理を行わない</jp>
						// <en>Other cells do not do the following processing to make it encounter it if there is a cell of the maximum height. </en>
						break;
					}
				}
				
				for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( leftBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						leftBody.rows[ i ].cells[ j ].height = leftDetailHeight;
					}
				}
			}
			// <jp>明細のheightが固定より大きい場合。</jp>
			// <en>When height of details is larger than that of fixation. </en>
			else
			{
				for ( var j = 0 ; j < leftBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( leftBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						leftBody.rows[ i ].cells[ j ].height = rightDetailHeight;
						// <jp>最大の高さのセルがあれば、他のセルはそれにあわすため後続処理を行わない</jp>
						// <en>Other cells do not do the following processing to make it encounter it if there is a cell of the maximum height. </en>
						break;
					}
				}

				for ( var j = 0 ; j < rightBody.rows[ i ].childNodes.length ; j ++ )
				{
					if( rightBody.rows[ i ].childNodes[ j ].rowSpan == 1 )
					{
						rightBody.rows[ i ].cells[ j ].height = rightDetailHeight;
					}
				}
			}
		}

		// <jp>結合されているセルがある場合は、全ての補正が終了後に再度補正を行います。</jp>
		// <en>After all the corrections end, it corrects it again when there is united cells. </en>
		if( array.length != 0 )
		{
			// <jp>結合されているセルを順番に参照します。</jp>
			// <en>Refer to united cells sequentially. </en>
			for ( var i =( array.length -1 ); i >= 0 ; i-- )
			{
				var totalHeight = 0;
				var heightChange = false;

				// <jp>一行の最大値を取得します。左側のテーブルが全結合している場合のみ右側のテーブルを採用します。</jp>
				// <en>The maximum value of one line is acquired. Only when left tables unite, all a right table is adopted. </en>
				var max = 0;
				max = array[i][2] + array[i][0];


				// <jp>該当する行の合計の高さを計算します。</jp>
				// <en>The height of the total of the corresponding line is calculated. </en>
				// <jp>結合している行数ぶん繰り返します。</jp>
				// <en>It repeats by several uniting line. </en>

				for ( var j = array[i][0] ; j < max ; j++ )
				{
					// <jp>１行の中で、結合していないセルの高さを取得します。</jp>
					// <en>The height of the cell that doesn't unite in one line is acquired. </en>
					for ( var k = 0 ; k < leftBody.rows[ j ].childNodes.length ; k++ )
					{
						if( leftBody.rows[ j ].cells[ k ] != undefined )
						{
							if( leftBody.rows[ j ].cells[ k ].rowSpan <= 1 )
							{
								totalHeight += leftBody.rows[ j ].childNodes[ k ].offsetHeight;
								heightChange = true;
								break;
							}
						}
					}

					if( !heightChange )
					{
						// <jp>１行の中で、結合していないセルの高さを取得します。</jp>
						// <en>The height of the cell that doesn't unite in one line is acquired. </en>
						for ( var k = 0 ; k < rightBody.rows[ j ].childNodes.length ; k++ )
						{
							if( rightBody.rows[ j ].cells[ k ].rowSpan <= 1 )
							{
								totalHeight += rightBody.rows[ j ].cells[ k ].offsetHeight;
								break;
							}
						
						}
					}
				
				}

				if ( array[i][3] )
				{
					var nowHeight = leftBody.rows[ array[i][0] ].cells[ array[i][1] ].offsetHeight;

					if(totalHeight != nowHeight )
					{
						// <jp>結合しているセルが左側（固定明細）の場合</jp>
						// <en>When the cell that unites is the left side (fixed details)</en>
						leftBody.rows[ array[i][0] ].cells[ array[i][1] ].height = totalHeight;
					}
				}
				else
				{
					var nowHeight = rightBody.rows[ array[i][0] ].cells[ array[i][1] ].offsetHeight;

					if(totalHeight != nowHeight )
					{
						// <jp>結合しているセルが右側（スクロール明細）の場合</jp>
						// <en>When the cell that unites is the right side (scroll details)</en>
						rightBody.rows[ array[i][0] ].cells[ array[i][1] ].height = totalHeight;
					}
				}
			}
		}
	}
	catch( e )
	{
	}
}

/** <jp>
 * [関数の説明]
 * detailとscrollの表示状態の同期を取ります。
 *
 * [引数の説明]
 * detail 明細テーブル
 * scroll 水平スクロールバー用<div>
</jp> */
/** <en>
 * synchronize detail and scroll.
 *
 * detail detail tabale
 * scroll <div> for horizontal scrollbar
</en> */
function synchronizedHorizonScrollBar( detail, scroll )
{
	try
	{
		if ( document.all( detail ) == null ||
		     document.all( detail ).style.visibility == 'hidden' )
		{
			if ( document.all( scroll ) != null )
			{
				document.all( scroll ).style.visibility = 'hidden';
			}
			return;
		}
	}
	catch(e)
	{
	}
}

var w3c = (document.getElementById) ? true : false;
var ie = ( document.all ) ? true : false;
var N = -1;
var bars = new Array();

/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * プログレスバーを描画します。</jp><en> * It draws in the progress bar. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * width プログレスバーのwidth</jp><en> * Width of width progress bar. </en>
<jp> * heiht プログレスバーのheigh</jp><en> * Heigh of heiht progress bar. </en>
<jp> * bgcolor プログレスバーのbgcolor</jp><en> * Bgcolor of bgcolor progress bar. </en>
<jp> * brdwidth プロブレスバーのボーダーのwidth</jp><en> * Width of border in brdwidth professional breath bar. </en>
<jp> * brdcolor プロブレスバーのボーダーのcolor</jp><en> * Color of border in brdcolor professional breath bar. </en>
<jp> * blkcolor ブロックのcolor</jp><en> * Color of blkcolor block. </en>
<jp> * speed ブロックの描画速度</jp><en> * Drawing speed of speed block. </en>
<jp> * blocks ブロック数</jp><en> * Number of blocks blocks. </en>
<jp> * id innerHTMLで描画するコントロールのid</jp><en> * Id of control where it draws with id innerHTML. </en>
 *
 */
function createBar( width, height, bgcolor, brdwidth, brdcolor, blkcolor, speed, blocks, id )
{
	if( ie || w3c )
	{
		//<jp> プログレスバーのバックグランドの設定</jp><en> Setting of background of progress bar. </en>
		var bar='<div style="position:relative; overflow:hidden; width:' + width + 'px; ' + 
				'height:' + height + 'px; ' + 
				'background-color:' + bgcolor + "; " +
				'border-color:' + brdcolor + '; ' + 
				'border-width:' + brdwidth + 'px; ' + 
				'border-style:solid; font-size:1px;">';

		//<jp> プロブレスバーのブロックの設定</jp><en> Setting of block of professional breath bar. </en>
		bar += '<span id = "blocks' + ( ++N ) + '" style="left:-' + ( height * 2 + 1 ) + 'px; position:absolute; font-size:1px">';

		//<jp> ブロックを引数で指定された数だけ設定します。</jp><en> Only the number for which the block is specified by the argument is set. </en>
		for( i = 0; i < blocks; i++ )
		{
			//<jp> ブロック用タグの作成</jp><en> Making of tag for block. </en>
			bar+='<span style="background-color:' + blkcolor + '; ' + 
				 'left:-' + ( ( height * i ) + i ) + 'px; '+
				 'font-size:1px; position:absolute; ' + 
				 'width:' + height + 'px; ' + 
				 'height:' + height +'px; ';

			//<jp> IEである場合</jp><en> When it is IE. </en>
			if ( ie )
			{
				bar += 'filter:alpha( opacity=' + ( 100 - i * ( 100 / blocks ) ) + ')';
			}
			else
			{
				bar += '-Moz-opacity:' + ( ( 100 - i * ( 100 / blocks ) ) /100 );
			}
			bar += '"></span>';
		}

		bar += '</span></div>';

		//<jp> プログレスバーの描画</jp><en> Drawing of progress bar. </en>
		document.all( id ).innerHTML = bar;

		//<jp> ブロックの取得</jp><en> Acquisition of block. </en>
		var bA = null;

		//<jp> IEである場合</jp><en> When it is IE. </en>
		if ( ie )
		{
			bA = document.all[ 'blocks' + N ];
		}
		else
		{
			bA = document.getElementById( 'blocks' + N );
		}

		bA.blocks = blocks;
		bA.width = width;
		bA.height = height;
		bars[ bars.length ] = bA;
		setInterval( 'startBar(' + N +')', speed );
	}
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * ブロック描画を補正します。</jp><en> * Drawing in the block is corrected. </en>
 *
<jp> * [引数の説明]</jp><en> * Explanation of argument. </en>
<jp> * bn ブロックNo.</jp><en> * Bn block No.. </en>
 *
 */
function startBar( bn )
{
	var bar = bars[ bn ];
	var position = -1;

	if ( parseInt( bar.style.left ) + bar.height + 1 - ( bar.blocks * bar.height + bar.blocks ) > bar.width )
	{
		position = -( bar.height * 2 + 1 );
	}
	else
	{
		position = ( parseInt( bar.style.left ) + bar.height + 1 );
	}
	bar.style.left = position  + 'px';
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * 1ミリ秒毎にcheckOpenerFlg()を実行します。</jp><en> * CheckOpenerFlg() is executed every one millisecond. </en>
 */
function timer()
{
  myTim = setInterval("checkOpenerFlg()",100); 
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * openerのisOnloadフラグを確認し、</jp><en> * The isOnload flag of opener is confirmed, and the screen is shut for false. </en>
<jp> * falseの場合は自画面を閉じます。</jp><en> </en>
 */
function checkOpenerFlg()
{
  try
  {
    if(window.opener.closed || !window.opener.isOnload)
    {
       this.close();
    }
    window.focus();
  }
  catch(e)
  {
  }
}


/**
<jp> * [関数の説明]</jp><en> * Explanation of function. </en>
<jp> * プログレスバーにメッセージを表示します。</jp><en> * The message is displayed in the progress bar. </en>
 *
 */
function setProgressMessage()
{
  try
  {
    if(window.opener.__PROGRESS_MESSAGE != null && window.opener.__PROGRESS_MESSAGE != undefined)
    {
      document.all("msg").innerText = window.opener.__PROGRESS_MESSAGE;
    }
    else
    {
      document.all("msg").innerText = "";
    }
  }
  catch(e)
  {
    document.all("msg").innerText = "";
  }
}


/** <jp>
 * [関数の説明]
 * 文字列の置換を行います
 * [使用するカスタムタグ名]
 * NumberTextBoxTag
 * [引数の説明]
 * val     データ
 * oldval  置換対象文字列
 * newval  置換文字列
 * [戻り]
 * 置換された文字列
 </jp> */
/** <en>
 * [Explanation of a function]
 * The character string is substituted. 
 * [The custom-made tag name to be used]
 * NumberTextBoxTag
 * [Explanation of an argument]
 * val     Data
 * oldval  Character string for substitution
 * newval  Substitution character string
 * [Return]
 * Substituted character string
 </en> */
function replaceAll(val, oldval, newval) {

    if (val==null ||val.length==0 || oldval==null || oldval.length==0 || newval==null)
    {
        return val;
    }
    var sub1 = "";
    var sub2 = val;

    while (true) { //<jp> break条件に入るまでループ。</jp><en> It loops until entering the break condition. </en>
        index = sub2.indexOf(oldval, 0);
       
        if (index == -1) {
            break;
        }

        val = sub2.replace(oldval, newval);

        sub1 += val.substring(0, index + newval.length);
        sub2 =  val.substring(index + newval.length, val.length);

    }
    return sub1 + sub2;
}

/** <jp>
 * [関数の説明]
 * プログレスウィンドウを中央表示させます。
 * [引数の説明]
 * progressWindow     中央表示させるウィンドウオブジェクト
 </jp> */
/** <en>
 * [Explanation of a function]
 * The progress window is centring displayed. 
 * [Explanation of an argument]
 * progressWindow     Window object to display center
 </en> */
  function progressMove( progressWindow )
  {
    try
    {
        var windowWidth = progressWindow.document.body.clientWidth;
        var windowHeight = progressWindow.document.body.clientHeight;
        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var centerPointWidth = (screenWidth - windowWidth) / 2;
        var centerPointHeight = (screenHeight - windowHeight) /2;

        progressWindow.moveTo(centerPointWidth, centerPointHeight);
    }
    catch(e)
    {
    }
  }

/** <jp>
 * [関数の説明]
 * プログレスウィンドウの初期化処理を行います。
 * [引数の説明]
 * obj     中央表示させるウィンドウオブジェクト
 </jp> */
/** <en>
 * [Explanation of a function]
 * The initialization of the progress window is done. 
 * [Explanation of an argument]
 * obj     Window object to display center
 </en> */
  function progressInit(obj)
  {
    setProgressMessage();
    progressMove(obj);
    timer();
  }

