// $Id: cookie.js,v 1.1.1.1 2006/08/17 09:33:06 mori Exp $

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

/*************************************************/
/*<jp> cookie設定 </jp>                          */
/*<en> Set cookie </en>                          */
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

/*************************************************/
/*<jp> cookie読込 </jp>                          */
/*<en> Read cookie </en>                         */
/*************************************************/
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
