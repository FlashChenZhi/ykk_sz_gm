// $Id: user.js,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ���[�U���g�p����JavaScript�ł��B
 * �֐���ϐ���ǉ�����Ƃ���"_"���֐����̐擪�ɕt�����ĉ������B
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
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
	var _mainWindowWidth = 1024;
	/*���C����ʃT�C�Y�@����*/
	var _mainWindowHeight = 768;
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
  alert(msgno);
  obj.focus();
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

