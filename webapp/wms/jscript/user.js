// $Id: user.js,v 1.1.1.1 2006/08/17 09:33:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ���[�U���g�p����JavaScript�ł��B
 * �֐���ϐ���ǉ�����Ƃ���"_"���֐����̐擪�ɕt�����ĉ������B
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:13 $
 * @author  $Author: mori $
 */
/*--------------------------------------------------------------------------*/
/* �O���[�o���ϐ�															*/
/*--------------------------------------------------------------------------*/

	/*���X�g�Z���@�n�C���C�g�\���̔w�i�F*/
	var _rowBgColor    = "#A4A4FF";
	/*���X�g�Z���@�n�C���C�g�\���̕����F*/
	var _rowStyleColor = "black";

	/*���C����ʃT�C�Y�@����*/
	var _mainWindowWidth = 1280;
	/*���C����ʃT�C�Y�@����*/
	var _mainWindowHeight = 1024;


/*--------------------------------------------------------------------------*/
/* �֐�																		*/
/*--------------------------------------------------------------------------*/
/** <jp>
 * [�֐��̐���]
 *  obj�Ŏw�肵���I�u�W�F�N�g��value������min��菬�������ǂ������m�F���܂��B�������ꍇ��msgno�Ŏw�肵�����b�Z�[�W
 * ���A���[�g�ŕ\�����܂��B
 * [�����̐���]
 * min   �ŏ��l
 * msgno Message No.(��FMSG-0001)
 * obj this���w��
 * [�߂�]
 * min���l���������ꍇ�ɃA���[�g��\�����܂��B
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
 * [�֐��̐���]
 *�@�{�^���������Ƀ|�b�v�A�b�v�E�B���h�E�����
 *  �e��ʂɃt�H�[�J�X��߂��܂��B
 */
function _closeWindow()
{
	window.close();
	opener.window.focus();
}

/**
 * [�֐��̐���]
 *�@�f�[�^�񍐁i�\���ƕ񍐁j��ʂŎg�p����֐��ł��B
 *  �`�F�b�N�{�b�N�X�Ƀ`�F�b�N���������Ƃ��ɗׂ̃`�F�b�N�{�b�N�X��Disabled��؂�ւ��܂��B
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
 * [�֐��̐���]
 * ���b�Z�[�W�G���A�փ��b�Z�[�W���Z�b�g���܂��B
 * ������flag��ύX���邱�ƂŁA�A�C�R�����g�������邱�Ƃ��ł��܂��B
 * flag : 0 ����
 *        1 Information
 *        2 Attention
 *        3 Warning
 *        4 Error
 * [�����̐���]
 * msg : ���b�Z�[�W�G���A�ŕ\�����郁�b�Z�[�W
 * flag : �A�C�R���̎��
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
 * [�֐��̐���]
 * ���X�g�Z���Ƀf�[�^�����邩�𔻒f���܂��B
 * ���X�g�Z���Ƀf�[�^�������ꍇ�Atrue��Ԃ��܂��B
 * [�����̐���]
 * lstID : ���X�g�Z����ID
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
 * [�֐��̐���]
 * �����ŗ^����ꂽ�R���g���[���ɒl�����͂���Ă��邩���m�F���A�l�������ꍇ
 * false��Ԃ��܂��B
 * ���p�󔒂���菜���A������̃T�C�Y��0�̏ꍇ��false��Ԃ��܂��B
 * �S�p�󔒂�1�����Ɛ����邽�߂��̏ꍇ�Atrue��Ԃ��܂��B
 * [�g�p����J�X�^���^�O��]
 * SubmitButtonTag
 * [�����̐���]
 * txtID �`�F�b�N���s���R���g���[����ID
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
 * [�֐��̐���]
 * ���X�g�Z���ɔz�u���ꂽ�`�F�b�N�{�b�N�X�̃`�F�b�N��Ԃ𒲂ׂ܂��B
 * �`�F�b�N�{�b�N�X�͂P��ڂɔz�u����Ă���K�v������܂��B
 * �`�F�b�N���P�ł������Ă���ꍇ��true��Ԃ��܂��B
 * �S�Ẵ`�F�b�N���͂���Ă���Ƃ�false��Ԃ��܂��B
 * [�����̐���]
 * lstID : ���X�g�Z����ID
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
 * [�֐��̐���]
 * �͈͎w��̑召�����������ǂ����`�F�b�N���܂��B
 * �͈͎w��̊J�n�ƏI�����r���A�J�n�̕����傫���ꍇ��false��Ԃ��܂��B
 * �J�n�̕����������ꍇ��true��Ԃ��܂��B
 * [�����̐���]
 * from : ��r�Ώۂ̊J�n
 * end  : ��r�Ώۂ̏I��
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
 * WindowsXP_SP2�����Ńt�@�C���u���E�Y�G���[�����������ꍇ
 * ���̃G���[�ʒm���T�[�o�ɃT�u�~�b�g����܂���B
 * ���ׁ̈A�G���[�������Ȃɂ��������s�������ꍇ�́A
 * ���̏�����������ɋL�q���Ă��������B
 */
function _doFileBrowsOnError()
{
}
