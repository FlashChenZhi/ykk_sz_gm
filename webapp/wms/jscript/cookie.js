// $Id: cookie.js 373 2006-07-20 04:04:34Z soura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * JavaScript for Main Menu
 * @version $Revision: 373 $, $Date: 2006-07-20 13:04:34 +0900 (Thu, 20 Jul 2006) $
 * @author  $Author: soura $
 */

/*************************************************/
/*<jp> cookie設定 </jp>                          */
/*<jp> @param name cookieに設定する名前</jp>     */
/*<en> Set cookie </en>                          */
/*<en> @param name Name set to cookie </en>      */
/*************************************************/
function setCookie(name)
{
	xDay = new Date;
	xDay.setDate(xDay.getDate() + 1);
	xDay = xDay.toGMTString();
	this.$document = document;
	this.$name = name;
	this.$expiration = xDay;
	this.$path 	 = "/";
	this.$domain = null;
	this.$secure = false;
	
	this.store 	= storeCookie;
	this.load 	= loadCookie;
	this.remove = removeCookie;
}

/*******************************************************************/
/*<jp> cookie読込 </jp>                                            */
/*<jp> @preturn true cookie読込の正常終了                          */
/*              false cookie読込の異常終了 </jp>                   */
/*<en> Read cookie </en>                                           */
/*<en> @preturn true Normal termination of cookie reading          */
/*              false Abnormal termination of cookie reading </en> */
/*******************************************************************/
function loadCookie()
{
	var allcookies = this.$document.cookie;
	if (allcookies == "") return false;

	var start = allcookies.indexOf(this.$name + '=');
	if (start == -1) return false;
	start += this.$name.length + 1;
	var end = allcookies.indexOf(';', start);
	if (end == -1) end = allcookies.length;
	var cookieval = allcookies.substring(start, end);

	var a = cookieval.split('&');
	for(var i=0; i < a.length; i++) {
	    a[i] = a[i].split(':');
	}
	for(var i = 0; i < a.length; i++) {
	    this[a[i][0]] = unescape(a[i][1]);
	}

	return true;
}


/*************************************************/
/*<jp> cookie保存 </jp>                          */
/*<en> Save cookie </en>                         */
/*************************************************/
function storeCookie()
{
    var cookieval = "";
    
    for(var prop in this) {
        if ((prop.charAt(0) == '$') || ((typeof this[prop]) == 'function')) 
            continue;
        if (cookieval != "") cookieval += '&';
        cookieval += prop + ':' + escape(this[prop]);
    }

    var cookie = this.$name + '=' + cookieval;
    xDay = new Date;
	xDay.setDate(xDay.getDate() + 1);
	xDay = xDay.toGMTString();
    if (this.$expiration)	cookie += '; expires=' + xDay;
    if (this.$path) 		cookie += '; path=' + this.$path;
    if (this.$domain) 		cookie += '; domain=' + this.$domain;
    if (this.$secure) 		cookie += '; secure';

    this.$document.cookie = cookie;
}


/*************************************************/
/*<jp> cookie削除 </jp>                          */
/*<en> Delete cookie </en>                       */
/*************************************************/
function removeCookie()
{
    var cookie = this.$name + '=';
    if (this.$path) 	cookie += '; path=' + this.$path;
    if (this.$domain) 	cookie += '; domain=' + this.$domain;
    cookie += '; expires=Fri, 02-Jan-1970 00:00:00 GMT';
    this.$document.cookie = cookie;
}
