// $Id: control.js,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * �T�[�o�R���g���[���̃J�X�^���^�O���g�p����JavaScript�ł��B
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
 * @author  $Author: mori $
 */
/*--------------------------------------------------------------------------*/
/* �O���[�o���ϐ�															*/
/*--------------------------------------------------------------------------*/
/**
 * �_�C�A���O�E�E�B���h�EObject���i�[����܂��B
 */
var dialogWindow;
var menu_num = new Array();


/*--------------------------------------------------------------------------*/
/* �֐�																		*/
/*--------------------------------------------------------------------------*/
/**
 * [�֐��̐���]
 * �_�C�A���O����e��ʂւ̃p�����[�^�n���Ɏg�p���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * array �e��ʂ֓n���p�����[�^�̔z��
 */
function setParameters(array)
{
}

/**
 * [�֐��̐���]
 * �G���^�[�L�[�̃t�H�[�J�X�ړ��Ŏg�p���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * obj �t�H�[�J�X�ړ����ԕ�����
 *     �R���g���[��Id������؂蕶��(�J���})�łȂ���������
 */
function focusEnterMove(obj)
{
}

/**
 * [�֐��̐���]
 * �t�@���N�V�����L�[�Ή��Ŏg�p���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * functionObjects Id���A�t�@���N�V�����L�[�A�C�x���g�����i�[����Object�̔z��
 *                 �t�@���N�V�����L�[�̐������K�v
 * systemParams    ApplicationConfig�ɒ�`����Ă���t�@���N�V�����L�[�̓���̔z��
 *                 �t�@���N�V�����L�[�̐������K�v
 */
function functionKeyEvent(functionObjects, systemParams)
{
}

/**
 * [�֐��̐���]
 * �_�C�A���O�E�B���h�E�idialogWindow�j�ɑ΂��N���[�Y���s���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * �Ȃ�
 */
function closeDialog()
{
}

/**
 * [�֐��̐���]
 * �E�B���h�E�̑O�ʕ\�����s���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * windowObject �O�ʕ\�����s���E�B���h�EObject
 */
function setFocusWindow(windowObject)
{
}

/**
 * [�֐��̐���]
 * �E�B���h�E�����\�����s���܂��B
 * [�g�p����J�X�^���^�O��]
 * PageTag
 * [�����̐���]
 * windowObject �����\�����s���E�B���h�EObject
 */
function moveToCenter(windowObject)
{
}

/** <jp>
 * [�֐��̐���]
 * �����F���w��F�ɕύX���܂��B
 *   �������Type��"text"�̂Ƃ��̂ݕ����F��ς��Ă��܂��B
 *     �����d�l�������checkbox�Aradio�Ȃǂ������F��ς���K�v���o�Ă����Ƃ��̂��߂�
 *     if���ŕ��򂳂��Ă��܂����A���̂Ƃ���return��Ԃ��Ă��܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * itemname  �����F��؂�ւ���Object���܂���Object
 * col       �����F�Ɏw�肷��F
 </jp> */
/** <en>
 * [Explanation of a function]
 * A character color is changed into a specification color.
 * 
 * 
 * 
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
		textbox�Ȃ�Object���������݂��Ȃ��ꍇ���邢��SELECT�^�O�̂Ƃ�
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
					checkbox����������݂��Ȃ��ꍇ �w�i�F�̎w��͏o���܂���
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
			checkbox�Aradio�Aselect�Ȃǂ̓��ꖼ�̂ŕ������݂���ꍇ�A�w�i�F�̎w��͏o���܂���
		</jp> */
		/*<en>
			A background color cannot be specified when two or more same names exist.
		</en> */
		return;
	}

	obj.style.setAttribute("color", col);
}


/** <jp>
 * [�֐��̐���]
 * �w�i�F���w��F�ɕύX���܂��B
 *   �������Type��"text"�̂Ƃ��̂ݔw�i�F��ς��Ă��܂��B
 *     �����d�l�������checkbox�Aradio�Ȃǂ��w�i�F��ς���K�v���o�Ă����Ƃ��̂��߂�
 *     if���ŕ��򂳂��Ă��܂����A���̂Ƃ���return��Ԃ��Ă��܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * itemname  �w�i�F��؂�ւ���Object���܂���Object
 * col       �w�i�F�Ɏw�肷��F
 </jp> */
/** <en>
 * [Explanation of a function]
 * A background color is changed into a specification color.
 * 
 * 
 * 
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * itemname  The Object name or Object which changes a background color
 * col       The color specified to be a background color
 </en> */
function changeBackcolor(itemname, col)
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
		textbox�Ȃ�Object���������݂��Ȃ��ꍇ���邢��SELECT�^�O�̂Ƃ�
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
					checkbox����������݂��Ȃ��ꍇ �w�i�F�̎w��͏o���܂���
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
			checkbox�Aradio�Aselect�Ȃǂ̓��ꖼ�̂ŕ������݂���ꍇ�A�w�i�F�̎w��͏o���܂���
		</jp> */
		/*<en>
			A background color cannot be specified when two or more same names exist.
		</en> */
		return;
	}

	obj.style.setAttribute("background", col);
	/*<jp>
		�e�L�X�g�{�b�N�X�̃n�C���C�g�\�����c�����܂܂ɂȂ邱�Ƃ̖h�~��ł��B
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
 * [�֐��̐���]
 * �w�肳�ꂽObject���z�u���Ă���TD�ATR�ATABLE��̔w�i�F���擾���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * obj �w�i�F���擾����Object�����w�肵�܂��B
 * [�߂�]
 * TD�ATR�ATABLE��̔w�i�F��Ԃ��܂��B
 </jp> */
/** <en>
 * [Explanation of a function]
 * The background color of TD, TR, and a TABLE phrase which arranges specified Object is acquired.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * obj The Object name which acquires a background color is specified.
 * [Return]
 * A background color is returned.
 </en> */
function getBgColor(id)
{
	if( id == "" ) return null;

   var obj = document.all(id);

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
 * [�֐��̐���]
 * ���������ꂽ�f�[�^�����؂蕶�����O���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str       ���������ꂽ�f�[�^
 * splitChar ��؂蕶��
 * [�߂�]
 * ��؂蕶�����O�����f�[�^��Ԃ��܂��B
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
 * [�֐��̐���]
 * �J���}�ҏW���܂��B
 * �f�[�^�̏����_�ȉ��������A�w�肳�ꂽ������菬�����ꍇ�ɁA
 * �����_�ȉ��̌����𒲐����܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * pos �������̌���
 * [�߂�]
 * �J���}�ҏW���ꂽ�f�[�^��Ԃ��܂��B
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
 * [�֐��̐���]
 * �w�肳�ꂽ�����ɏ��������܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * format ����
 * pause  ��؂蕶��
 * [�߂�]
 * �����ҏW���ꂽ�f�[�^��Ԃ��܂��B
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
 * [�֐��̐���]
 * �f�[�^���w�肳�ꂽ�o�C�g���𒴂��Ă��Ȃ����`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str  �f�[�^
 * byte �o�C�g��
 * [�߂�]
 * �w�肳�ꂽ�o�C�g���𒴂��Ă����� false�A�����Ă��Ȃ����true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �f�[�^�̌������w�肳�ꂽ�����𒴂��Ă��Ȃ����`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str  �f�[�^
 * numlen ����������
 * len2 ����������
 * [�߂�]
 * �w�肳�ꂽ�o�C�g���𒴂��Ă����� false�A�����Ă��Ȃ����true��Ԃ��܂��B
 </jp> */
/** <en>
 * [Explanation of a function]
 * I confirms whether to be over the number of digit the number of digit of data was specified to be.
 * [The custom-made tag name to be used]
 * Common
 * [Explanation of an argument]
 * str  Data
 * numlen The number of integer part figures
 * len2 The number of a small number of part figures
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
 * [�֐��̐���]
 * �f�[�^�̃o�C�g�����擾���܂��B
 * �S�p������2byte�A���p�J�i�̏ꍇ��1byte�Ƃ��Čv�Z���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * [�߂�]
 * �o�C�g����Ԃ��܂��B
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
	var i,cnt=0;
	var CLetter;

	for( i=0; i<str.length; i++) {
		if( escape(str.charAt(i)).length >= 4 ) {
			CLetter = escape(str.charAt(i)) ;
			if( CLetter.indexOf("%uFF") != -1 && '0x'+CLetter.substring(2,CLetter.length) > 0xFF60 && '0x'+CLetter.substring(2,CLetter.length) < 0xFFA0 ) {
				cnt++;
			} else if ( CLetter.length == 3 ) {
				cnt++;
			} else {
				cnt += 2;
			}
		} else {
			cnt++;
		}
	}
	return cnt;
}


/** <jp>
 * [�֐��̐���]
 * �֎~�������܂܂�Ă��邩�`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str   �f�[�^
 * taboo �֎~����
 * [�߂�]
 * �֎~�������܂܂�Ă���� false�A�܂܂�Ă��Ȃ����true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �֎~�������܂܂�Ă��邩�`�F�b�N���܂��B���̃��\�b�h�ł�16�i���ɕϊ����Ĕ�r���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str   �f�[�^
 * taboo �֎~����
 * [�߂�]
 * �֎~�������܂܂�Ă���� true�A�܂܂�Ă��Ȃ����false��Ԃ��܂��B
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
 * [�֐��̐���]
 * 16�i���ɕϊ������l��Ԃ��܂��B
 * 
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * [�߂�]
 * �P�U�i���ɕϊ������l�i�X�y�[�X��؂�j
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
 * [�֐��̐���]
 * �f�[�^������(0-9)�݂̂ō\������Ă��邩�`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * [�߂�]
 * �����ȊO���܂܂�Ă����� false�A�܂܂�Ă��Ȃ����true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �f�[�^�����l���̓e�L�X�g�{�b�N�X�Ŏg�p�ł��镶���݂̂ō\������Ă��邩�`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * decimal ����������
 * [�߂�]
 * �g�p�ł��镶���ȊO���܂܂�Ă����false�B�܂܂�Ă��Ȃ����true��Ԃ��܂��B
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

	return true;
}


/** <jp>
 * [�֐��̐���]
 * �f�[�^�����l���̓e�L�X�g�{�b�N�X�Ŏg�p�ł��镶���݂̂ō\������Ă��邩�`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * [�߂�]
 * �g�p�ł��镶���ȊO���܂܂�Ă����false�B�܂܂�Ă��Ȃ����true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �w��͈͂̕�����𔲂��o���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �ΏۂƂȂ镶����
 * start �����o���擪�ʒu�i�[�����擪�j
 * end �����o���I���ʒu
 * [�߂�]
 * �����o����������
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
 * [�֐��̐���]
 * �f�[�^���w�肳�ꂽ������ō\������Ă��邩�`�F�b�N���܂��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str   �f�[�^
 * kind  ������
 *       1:�S�p���� 2:���p�J�i 3:���p�p��
 *       4:���p���� 5:���p�L�� 6:���p�X�y�[�X
 *       7:�֎~�����`�F�b�N
 * taboo �֎~����
 * [�߂�]
 * �w�肳�ꂽ������ȊO���܂܂�Ă���� false�A�܂܂�Ă��Ȃ����true��Ԃ��܂��B
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
		�֎~�������܂�ł��Ȃ����`�F�b�N���܂�
	</jp> */
	/*<en>
		It is confirmed whether the prohibition character is included.
	</en> */
	if( kind.indexOf("7") >= 0 ) {
		if( isProhibitedCharacter(str,taboo) ) return false;
	}

	/*<jp>
		������ɑS�p�������܂܂�Ă���΃`�F�b�N�͂��܂���
	</jp> */
	/*<jp>
		A check is not carried out when full size is contained.
	</jp> */
	if( kind.indexOf("1") != -1 ) return true;

	/*<jp>
		�S�p���܂܂�Ă��Ȃ����`�F�b�N���܂�
	</jp> */
	/*<en>
		It is confirmed whether full size is contained.
	</en> */
	if( !isHalfWidthCharacter(str) ) return false;
	
	/*<jp>
		�����Ȃ��������܂܂�Ă��Ȃ����`�F�b�N
	</jp> */
	/*<en>
		It is confirmed whether the character which cannot be treated is contained.
	</en> */
	if( isContainInvalidCharacter(str) ) return false;

	/*<jp>
		���p�J�i���܂�ł��Ȃ����`�F�b�N
	</jp> */
	/*<en>
		It is confirmed whether half-width kana is included.
	</en> */
	if( kind.indexOf("2") < 0 ) {
		if( isContainHalfWidthKANA(str) ) return false;
	}

	/*<jp>
		���p�p�����܂�ł��Ȃ����`�F�b�N
	</jp> */
	/*<en>
		It is confirmed whether the half-width alphabetic character is included.
	</en> */
	if( kind.indexOf("3") < 0 ) {
		if( isContainAlphabet(str) ) return false;
	}

	/*<jp>
		���p�������܂�ł��Ȃ����`�F�b�N
	</jp> */
	/*<en>
		It confirms whether to include the half-width number.
	</en> */
	if( kind.indexOf("4") < 0 ) {
		if( isContainHalfWidthNumber(str) ) return false;
	}

	/*<jp>
		���p�L�����܂�ł��Ȃ����`�F�b�N
	</jp> */
	/*<en>
		It confirms whether to include the half-width sign.
	</en> */
	if( kind.indexOf("5") < 0 ) {
		if( isContainHalfWidthSign(str) ) return false;
	}

	/*<jp>
		���p�X�y�[�X���܂�ł��Ȃ����`�F�b�N
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
 * [�֐��̐���]
 * �f�[�^�����t�Ƃ��ėL�����`�F�b�N���܂��B
 * ���t�͈�:1970/1/1�`2049/12/31
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * format ����
 * [�߂�]
 * ���t�Ƃ��Ė����Ȃ�� false�A�L���Ȃ��true��Ԃ��܂��B
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

	if( format.match(/[dD]/) ) {
		if( d == "" ) return false;
		d_i = parseInt(d, 10);
	} else {
		if( d != "" ) return false;
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
		if( d < 1 || d > uru ) {
			return false;
		}
	} else if( m_i == 4 || m_i == 6 || m_i == 9 || m_i == 11 ) {
		if( d < 1 || d > 30 ) {
			return false;
		}
	} else {
		if( d < 1 || d > 31 ) {
			return false;
		}
	}

	return true;
}


/** <jp>
 * [�֐��̐���]
 * �f�[�^�����ԂƂ��ėL�����`�F�b�N���܂��B
 * ���Ԕ͈�:00:00:00�`23:59:59
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * format ����
 * [�߂�]
 * ���ԂƂ��Ė����Ȃ�� false�A�L���Ȃ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �S�p�����񐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str �f�[�^
 * [�߂�]
 * �S�p������݂̂ł����true�A����ȊO��false��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���p�����񐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p������݂̂ł����true�B����ȊO��false��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���p�J�i�������܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p�J�i�������܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���p�p�����܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p�p�����܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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

	if( str.match(/[A-Z]/i) || str.match(/[@_]/) ) return false;

	for( i = 0; i<str.length; i++ ) {
		CLetter = escape(str.charAt(i)) ;
		if (( CLetter >= "%5B" && CLetter <= "%5E" ) || ( CLetter >= "%7B" && CLetter <= "%7E" )) {
				return true;
		}
	}
	return false;
}


/** <jp>
 * [�֐��̐���]
 * ���p�������܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p�������܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���p�L�����܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p�L�����܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���p�X�y�[�X���܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * ���p�X�y�[�X���܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * �����Ȃ��������܂܂�邩�ǂ����𐸍��B
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str    �f�[�^
 * [�߂�]
 * �����Ȃ��������܂܂�Ȃ��ꍇ��false�A�܂܂��ꍇ��true��Ԃ��܂��B
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
 * [�֐��̐���]
 * ���t�̃t�H�[�}�b�g��ύX���܂��B
 * �ύX�O�����̃f�[�^��ύX�㏑���ɕύX���܂��B
 * �ύX��̏����ŕK�v�ȏ�񂪁A�ύX�O�̏����ɖ����ꍇ�́A
 * ���ݓ����ŕ₢�܂��B
 * ���t�͈�:1970/1/1�`2049/12/31
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str     �f�[�^
 * bformat �ύX�O����
 * aformat �ύX�㏑��
 * delim   �������̓��t��؂蕶���i"/","-"���j
 * [�߂�]
 * �ύX�㏑���ɕύX�����f�[�^��Ԃ��܂��B
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
 * delim   Date delimiter in the format�iExample: "/", "-"�j
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
 * [�֐��̐���]
 * ���Ԃ̃t�H�[�}�b�g��ύX���܂��B
 * �ύX�O�����̃f�[�^��ύX�㏑���ɕύX���܂��B
 * �ύX��̏����ŕK�v�ȏ�񂪁A�ύX�O�̏����ɖ����ꍇ�́A
 * ���ݓ����ŕ₢�܂��B
 * ���Ԕ͈�:00:00:00-23:59:59
 * [�g�p����J�X�^���^�O��]
 * Common
 * [�����̐���]
 * str     �f�[�^
 * bformat �ύX�O����
 * aformat �ύX�㏑��
 * delim   �������̎��ԋ�؂蕶���i:���j
 * [�߂�]
 * �ύX�㏑���ɕύX�����f�[�^��Ԃ��܂��B
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
 * delim   Time delimiter in the format�iExample: ":"�j
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

/**
 * [�֐��̐���]
 * �G�������g�v�f�����ǂ�R���g���[���̐F���n�C���C�g�\�����܂��B
 */
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
				     ( element.id.indexOf( hilightId ) == -1 )
				   )
				{
					continue;
				}

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


/**
 * [�֐��̐���]
 * ���X�g�Z���ł̃n�C���C�g�\�����s���܂��B
 */
var defaultBgColor = "";
var defaultSytleColor = "";
var cssArray = new Array();
var styleColorArray = new Array();

function checkHighlight(which)
{
	var el = getElement(event.srcElement, "TR", "TD");
	if (el == null)
	{
		return;
	}
	if (el.tagName == "TD")
	{
		var row = getElement(el, "TR");
		var table = getElement(row, "TABLE");
		var elm = row.parentElement;

		if ( (row.tagName == "TR") && 
			 ( row.parentElement.tagName == "THEAD" ) )
		{
			return;
		}

		var rowArray = getRow( row.id, table );

		if (rowArray == undefined)
		{
			return;
		}

		if ( rowArray.length == undefined )
		{
			if ( which ) 
			{
				defaultBgColor = row.bgColor;
				changeHighlight(row);
			}
			else
			{
				changeDefault(row, defaultBgColor);
			}
			return;
		}

		for (var i = 0; i < rowArray.length; i++)
		{
			if ( which )
			{
				defaultBgColor = rowArray[i].bgColor;
				changeHighlight(rowArray[i]);
			}
			else
			{
				changeDefault(rowArray[i], defaultBgColor);
			}
		}
	}
}

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
 * [�֐��̐���]
 * �G�������g�v�f�����ǂ�R���g���[���̐F�����ɂ��ǂ��܂��B
 */
function changeDefault(el, defaultBgColor)
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
				     ( element.id.indexOf( hilightId ) == -1 )
				   )
				{
					continue;
				}

				element.bgColor = defaultBgColor;

				if ( elem != null && elem != undefined &&
				   ( elem.tagName == "SPAN" || elem.tagName == "A" ) )
				{
					var elementId = elem.id;
					elem.className = cssArray[elementId];
					elem.style.color = styleColorArray[elementId];
				}
			}
		}
	}
}

/**
 * [�֐��̐���]
 * �G�������g�v�f�����ǂ�e�G�������g���擾���܂��B
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

/**
 * [�֐��̐���]
 * �������ʃy�[�W�ύX�֐�
 */
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

/**
 * [�֐��̐���]
 * �L�[���͂𐧌䂵�܂��B
 */
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
	"73","76","78","79","80","82","87","88", "116");
	
	//114      : F3
	//115      : F4
	//116      : F5
	//117      : F6
	//121      : F10
	//122      : F11
	var invalidateFunctionKey = new Array("114","115","116","117","121","122");

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

	//Function Key
	return invalidateKeyOperation(invalidateFunctionKey);
}
/**
 * [�֐��̐���]
 * �L�[���͂𐧌䂷�邽�߂̊֐��ŁAignoreKeyEvent����R�[������܂��B
 */
function invalidateKeyOperation(invalidateKey)
{
	for (var i = 0; i < invalidateKey.length; i++)
	{
		if (window.event.keyCode == invalidateKey[i])
		{
			window.event.keyCode = 37;
			return false;
		}
	}
}

/**
 * [�֐��̐���]
 * ���b�Z�[�W�G���A���N���A���܂�
 */
function clearMessage()
{
	message.innerText = "";
}


/**
 * [�֐��̐���]
 * isValueInput�֐�����Ă΂�܂��B
 * �����̕����񂩂甼�p�󔒂���菜���A������̃T�C�Y��0�̏ꍇ��
 * false��Ԃ��܂��B
 * �S�p�󔒂�1�����Ɛ����邽�߂��̏ꍇ�Atrue��Ԃ��܂��B
 * [�g�p����J�X�^���^�O��]
 * SubmitButtonTag
 * [�����̐���]
 * value �`�F�b�N���s���l
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
 * [�֐��̐���]
 * �A�z�z�񕶎����Array�^�ɂ��ĕԂ��܂��B
 * string                         Array
 * --------------------------     ----------------------------
 * "key1:value1,key2:value2"  ->  array[0][0] = "key1"
 *                                array[0][1] = "value1"
 *                                array[1][0] = "key2"
 *                                array[1][1] = "value2"
 *
 * [�����̐���]
 * str �A�z�z�񕶎���
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
 * [�֐��̐���]
 * ���b�Z�[�W�G���A�փ��b�Z�[�W���Z�b�g���܂��B
 * [�����̐���]
 * MessageResource�̃��b�Z�[�W
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
 * [�֐��̐���]
 * ��ʂ̃T�C�Y��ύX���܂��BWindow.open���ɃT�C�Y���w�肵�Ă��A�w�肵���T�C�Y����
 * �傫�ȃT�C�Y�ŊJ�����߁AresizeTo�֐����g�p����K�v������܂��B
 * �T�C�Y�̒�`��user.js�ɂčs���܂��B��`�������ꍇ�͕�1024�A����768�Ń��T�C�Y���܂��B
 */
function windowResize()
{
	if (typeof _mainWindowWidth == "undefined"){
		_mainWindowWidth = 1024;
	}
	if (typeof _mainWindowHeight == "undefined"){
		_mainWindowHeight = 768;
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
	window.resizeTo(w,h);
	window.moveTo(x,y);	
}

/**
 * [�֐��̐���]
 * JSP�ɒ�`���ꂽDynamicAction�֐����Ăяo���܂��BJSP�ɒ�`����Ă��Ȃ��ꍇ��
 * �G���[�Ƃ����ɁA���̂܂ܔ����܂��B
 */
function callDynamicAction()
{
	if(typeof dynamicAction != 'undefined'){ 
		if(dynamicAction()) event.returnValue = false;
	}
	return true;
}
