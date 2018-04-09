// $Id: common.js,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * JavaScript for Main Menu
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:33:06 $
 * @author  $Author: mori $
 */

var menu_num = new Array();
var context_Path;
var menu_count = 0;

/*************************************************/
/*<jp> コンテキストパスを保持します。 </jp>      */
/*<en> set a context path  </en>                 */
/*************************************************/
function setContextPath(str)
{
	context_Path = str;
}
/********************************************************/
/*<jp> 表示するメニューアイコンの数を保持します。 </jp> */
/*<en> set a Menu Count  </en>                          */
/********************************************************/
function setMenuCount(val)
{
	menu_count = val;
}
/*************************************************/
/*<jp> メニュー画像変更関数 </jp>                */
/*<en> Function: Modification menu screen </en>  */
/*************************************************/
function change_Image(event, id, menuno)
{
	switch(event)
	{
		case 'over':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_1.gif";
				document.getElementById("menut_" + menuno).style.color = "#70E6FF";
			}
			break;
		}
		case 'down':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_2.gif";
				document.getElementById("menut_" + menuno).style.color = "#FC8800";
			}
			break;
		}
		case 'out':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_0.gif";
				document.getElementById("menut_" + menuno).style.color = "#FFFFFF";
			}
			break;
		}
		case 'up':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_0.gif";
				document.getElementById("menut_" + menuno).style.color = "#FFFFFF";
			}
			break;
		}
		case 'click':
		{
			var menu_c = new setCookie("u_menu");

			for (var i = 1 ; i < menu_count + 1; i++) {
				if (document.images["menu_" + i.toString()]) {
					document.images["menu_" + i.toString()].src = context_Path + "/img/project/menu/typea/icon_" + i.toString() + "_0.gif";
				}
				if (document.getElementById("menut_" + i.toString())) {
					document.getElementById("menut_" + i.toString()).style.color = "#FFFFFF";
				}
			}

			document.images[id].src = context_Path + "/img/project/menu/typea/icon_" + menuno + "_2.gif";
			document.getElementById("menut_" + menuno).style.color = "#FC8800";
			menu_num = menuno;
			menu_c.menu_s = menu_num;
			menu_c.store();
			break;
		}
	}
}
/*********************************************************/
/*<jp> メニュー画像変更関数 </jp>                        */
/*<en> Function: Modification of menu screen image </en> */
/*********************************************************/
function change_ImageB(event, id, menuno, lang)
{
	switch(event)
	{
		case 'over':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_1_" + lang + ".gif";
			}
			break;
		}
		case 'down':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_2_" + lang + ".gif";
			}
			break;
		}
		case 'out':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_0_" + lang + ".gif";
			}
			break;
		}
		case 'up':
		{
			if (menuno == menu_num) {
				break;
			} else {
				document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_0_" + lang + ".gif";
			}
			break;
		}
		case 'click':
		{
			var menu_c = new setCookie("u_menu");

			for (var i = 1 ; i < menu_count + 1; i++) {
				if (document.images["menu_" + i.toString()]) {
					document.images["menu_" + i.toString()].src = context_Path + "/img/project/menu/typeb/icon2_" + i.toString() + "_0_" + lang + ".gif";
				}
			}

			document.images[id].src = context_Path + "/img/project/menu/typeb/icon2_" + menuno + "_2_" + lang + ".gif";
			menu_num = menuno;
			menu_c.menu_s2 = menu_num;
			menu_c.store();
			break;
		}
	}
}
