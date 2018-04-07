var bdmsg = "";
var timerID;

var ID = "id"
var BDMSG_TAG = "div";
var BDMSG_ID = "__BDMSG_";
var BDMSG_COUNTER_ID = "xmlHttpRequestBDMSGCounter";

var BDMSG_WIDTH         = 200;
var BDMSG_MAXCOUNT      = 10;
var BDMSG_LOADCOUNT     = 8;
var BDMSG_HEIGHT_OFFSET = 38;
var BDMSG_WIDTH_OFFSET  = 20;
var BDMSG_CREATE_TIMER  = 500;
var BDMSG_ENDWAIT_TIMER = 300;

var BDMSG_BGCOLOR       = "#FAFAD2";
var BDMSG_BORDER        = "ridge #FAFAD2 3px";
var BDMSG_PADDING       = "5px";

var FONT_START_TAG      = "<font class='";
var FONT_START_TAG2     = "'>";
var FONT_END_TAG        = "</font>";

var SIZE_SUFFIX = "px";

var BDMSG_WAIT = 30 * 1000;

/** <jp>
 * メッセージの表示
 *
 * @param msg 表示するメッセージ
 </jp> */
/** <en>
 * Display of message
 *
 * @param msg message
 </en> */
function bdMsg( msg, balloonClass, fontClass ) 
{
	// <jp>setTimeout()のスタックをキャンセルし、バルーンが表示されている場合は、除去します。</jp>
	// <en>Cancel stock of setTimeout(), and remove balloon message. </en>
//	if( timerID != undefined )
//	{
//		clearTimeout( timerID );
//		removebdMsg();
//	}
//	clearExistingBdMsg();

	bdmsg = document.createElement( BDMSG_TAG );
	bdmsg.setAttribute( ID, BDMSG_ID );
	document.body.appendChild( bdmsg );
/*
	var prgStyle = bdmsg.style;

	prgStyle.position = 'absolute';
	prgStyle.backgroundColor = BDMSG_BGCOLOR;
	prgStyle.border          = BDMSG_BORDER;
	prgStyle.padding = BDMSG_PADDING;
	prgStyle.whiteSpace = "nowrap";

	document.body.appendChild( bdmsg );

	var scrollTop  = parseInt( document.body.scrollTop );
	var scrollLeft = parseInt( document.body.scrollLeft );

	document.all( BDMSG_ID ).innerHTML = FONT_START_TAG + msg + FONT_END_TAG;
	document.all( BDMSG_ID ).style.top  = ( document.body.clientHeight / 2 ) - document.all( BDMSG_ID ).offsetHeight + scrollTop;
	document.all( BDMSG_ID ).style.left = ( document.body.clientWidth / 2 )  - ( document.all( BDMSG_ID ).offsetWidth / 2 ) + scrollLeft;
	document.all( BDMSG_ID ).style.cursor = "pointer";
	document.all( BDMSG_ID ).style.filter = "alpha(style=1, opacity=100, finishopacity=80, startx=85, starty=0, finishx=85, finishy=140)";
	document.all( BDMSG_ID ).onclick = removebdMsg;
	document.all( BDMSG_ID ).align = "center";
*/

	var scrollTop  = parseInt( document.body.scrollTop );
	var scrollLeft = parseInt( document.body.scrollLeft );

	document.all( BDMSG_ID ).innerHTML = FONT_START_TAG + fontClass + FONT_START_TAG2 + msg + FONT_END_TAG;
	document.all( BDMSG_ID ).style.position = 'absolute';
	document.all( BDMSG_ID ).style.top  = ( document.body.clientHeight / 2 ) - document.all( BDMSG_ID ).offsetHeight + scrollTop;
	document.all( BDMSG_ID ).style.left = ( document.body.clientWidth / 2 )  - ( document.all( BDMSG_ID ).offsetWidth / 2 ) + scrollLeft;
	document.all( BDMSG_ID ).style.left = ( document.body.clientWidth / 2 )  - ( document.all( BDMSG_ID ).offsetWidth / 2 ) + scrollLeft;
	document.all( BDMSG_ID ).onclick = removebdMsg;
	document.all( BDMSG_ID ).className = balloonClass;

	hiddenSelect( document.all( BDMSG_ID ) );

	new Effect.Appear(bdmsg,{duration:0.15});

	timerID = setTimeout( "removebdMsg()", BDMSG_WAIT );
}

/** <jp>
 * 演出ありのメッセージの非表示化
 *
 </jp> */
/** <en>
 * Making of message with production non-display
 *
 </en> */
function effectRemove()
{
	try
	{
		new Effect.Puff( document.all( BDMSG_ID ) );
		setTimeout( "removebdMsg()", 1 * 1000 );
	}
	catch(ex)
	{
		removebdMsg();
	}
}

/** <jp>
 * メッセージの非表示化
 *
 </jp> */
/** <en>
 * Making of message non-display
 *
 </en> */
function removebdMsg()
{
	visibleSelect( document.all( BDMSG_ID ) );
	bdmsg = null;
	if( document.all( BDMSG_ID ) != null )
	{
		document.body.removeChild( document.all( BDMSG_ID ) );
	}
}

/** <jp>
 * 現在表示中のメッセージを除去します。
 *
 </jp> */
/** <en>
 * The message displaying it now is removed
 *
 </en> */
function clearExistingBdMsg()
{
	if( timerID != undefined )
	{
		clearTimeout( timerID );
		removebdMsg();
	}
}