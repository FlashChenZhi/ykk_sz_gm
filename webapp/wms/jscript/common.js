// $Id: common.js 2008 2006-11-07 01:43:00Z fukumori $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * JavaScript for Main Menu
 * @version $Revision: 2008 $, $Date: 2006-11-07 10:43:00 +0900 (Tue, 07 Nov 2006) $
 * @author  $Author: fukumori $
 */

var menu_num = new Array();
var context_Path;
var menu_count = 0;

/*************************************************/
/*<jp> コンテキストパスを保持します。 </jp>      */
/*<jp> @param str 保持するコンテキストパス </jp> */
/*<en> set a context path  </en>                 */
/*<en> @param str Maintained context path </en>  */
/*************************************************/
function setContextPath(str)
{
	context_Path = str;
}
/********************************************************/
/*<jp> 表示するメニューアイコンの数を保持します。 </jp> */
/*<jp> @param val 保持する数 </jp>                      */
/*<en> set a Menu Count  </en>                          */
/*<en> @param val Maintained number  </en>              */
/********************************************************/
function setMenuCount(val)
{
	menu_count = val;
}
/*************************************************/
/*<jp> メニュー画像変更関数 </jp>                */
/*<jp> @param event 発生したイベント </jp>       */
/*<jp> @param id 対象の画像のID </jp>            */
/*<jp> @param menuno メニューのナンバー </jp>    */
/*<en> Function: Modification menu screen </en>  */
/*<en> @param event Generated event </en>        */
/*<en> @param id ID of image of object </en>     */
/*<en> @param menudo Number of menus </en>       */
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
			var temp_menu_id;

			for (var i = 0 ; i < document.images.length; i++) {
				temp_menu_id = document.images[i].id;
				if (temp_menu_id.indexOf("menu_") == 0) {
					document.images[temp_menu_id].src = context_Path + "/img/project/menu/typea/icon_" + temp_menu_id.substring(5) + "_0.gif";
				}
				if (document.getElementById("menut_" + temp_menu_id.substring(5))) {
					document.getElementById("menut_" + temp_menu_id.substring(5)).style.color = "#FFFFFF";
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
/*<jp> @param event 発生したイベント </jp>               */
/*<jp> @param id 対象の画像のID </jp>                    */
/*<jp> @param menuno メニューのナンバー </jp>            */
/*<jp> @param lang  言語（日本語:ja、英語:en など）</jp> */
/*<en> Function: Modification of menu screen image </en> */
/*<en> @param event Generated event </en>                */
/*<en> @param id ID of image of object </en>             */
/*<en> @param menudo Number of menus </en>               */
/*<en> @param lang  language(ja, en)</en>                */
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
			var temp_menu_id;

			for (var i = 0 ; i < document.images.length; i++) {
				temp_menu_id = document.images[i].id;
				if (temp_menu_id.indexOf("menu_") == 0) {
					document.images[temp_menu_id].src = context_Path + "/img/project/menu/typeb/icon2_" + temp_menu_id.substring(5) + "_0_" + lang + ".gif";
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
