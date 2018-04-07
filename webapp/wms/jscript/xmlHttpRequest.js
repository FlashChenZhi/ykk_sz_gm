var PLUS          = "_PLUS_";
var AMPERSAND     = "_AMPERSAND_";
var VISIBLE_PROGRESS = true;

var READY_STATE_UNINITIALIZATION = 0;
var READY_STATE_READING = 1;
var READY_STATE_HASREAD = 2;
var READY_STATE_COMPLETION = 4;

var RESPONSE_OK = 200;

var xmlhttpRequest = null;
var tramsmissionId = null;
var progressBar = null;
var iFiled = null;

var timeOutId = null;

var FOCUS_OFFSET_TIME = 1;

var asyncFocusIs = "";
var asyncBallon = "";

/** <jp>
 * XMLHttpRequest生成
 * @return 生成したXMLHttpRequest
 </jp> */
/** <en>
 * XMLHttpRequest generation
 * @return Generated XMLHttpRequest
 </en> */
function createXmlHttp()
{
	// <jp>IE</jp>
	// <en>IE</en>
	if ( document.all )
	{
		return new ActiveXObject( "Microsoft.XMLHTTP" );
	}
	// <jp>IE以外</jp>
	// <en>Excluding IE</en>
	else if ( document.implementation )
	{
		return new XMLHttpRequest();
	}
	else
	{
		return null;
	}
}

/** <jp>
 * 非同期通信PostBack
 </jp> */
/** <en>
 * Asynchronous communication PostBack
 </en> */
function ajaxPostBack()
{
	// <jp>PostBack用のパラメータの設定</jp>
	// <en>Setting of parameter for PostBack</en>
	var args = ajaxPostBack.arguments;
	var eventArgs = "";

	// <jp>ファイルブラウズ対応</jp>
	// <en>For file browse</en>
	var enc = document.forms[0].enctype;
	if ( enc == 'multipart/form-data' )
	{
		var postBackArgs = "";
		for ( var i = 0; i < args.length ; i++ )
		{
			postBackArgs += ",'" + args[i] + "'";
		}
		postBackArgs = postBackArgs.substring(1);
		eval("postBack(" +  postBackArgs + " )" );
		return;
	}

	for ( var i = 1; i < args.length ; i++ )
	{
		if ( i != 1 )
		{
			eventArgs += ",";
		}
		eventArgs += args[i];
	}

	if (!canSend(args[0], eventArgs))
	{
		return;
	}
	
	
	// Mod K.fukumori 2006.11.08
	var eventElement = document.all(args[0]);
	if(event != null && event.srcElement != null)
	{
		eventElement = event.srcElement;
	}
	if ( eventElement != null &&
		 eventElement.tagName=='INPUT' && 
		 eventElement.type=='text' &&
		 eventElement.format == 'true' &&
		 eventElement.style.visibility != 'hidden' &&
		 eventElement.readOnly != true )
	{
		enterKeyEvent = true;
		isChangeBackColor = false;
		isFocusSelect = false;
		eventElement.onblur();
	}
	// Mod END

	if ( xmlhttpRequest == null )
	{
		xmlhttpRequest = createXmlHttp();
	}
	else
	{
		switch( xmlhttpRequest.readyState )
		{
			case READY_STATE_UNINITIALIZATION :
				xmlhttpRequest = createXmlHttp();
				return;
			case READY_STATE_READING :
				return;
			case READY_STATE_HASREAD :
				return;
			case READY_STATE_COMPLETION :
			default:
				// <jp>通信をキャンセル</jp>
				// <en>The communication is canceled.</en>
				xmlhttpRequest.abort();
				break;
		}
	}

	try
	{
		try
		{
			// <jp>定期送信が設定されている場合はキャンセル</jp>
			// <en>Cancellation when regular transmission is set</en>
			if ( tramsmissionId != null )
			{
				clearTimeout( tramsmissionId );
				tramsmissionId = null;
			}
		}
		catch(e)
		{
		}

		document.all("__EVENTTARGET").value = args[0];
		document.all("__EVENTARGS").value = eventArgs;

		asyncFocusIs = "";
		asyncBallon = "";

		// <jp>レスポンス取得イベント処理</jp>
		// <en>Response acquisition event processing</en>
		xmlhttpRequest.onreadystatechange = xmlhttpResponseHandler;

		xmlhttpRequest.open( "POST", document.forms[0].action );
		xmlhttpRequest.setRequestHeader( "Content-Type", "application/x-www-form-urlencoded" );

		// <jp>パラメータ構築</jp>
		// <en>Parameter construction</en>
		var param = createParameter();

		if ( param != null && param != "" )
		{
			if ( VISIBLE_PROGRESS )
			{
				// <jp>プログレスバー表示</jp>
				// <en>Progress bar display</en>
				if ( progressBar == null )
				{
					progressBar = new ProgressBar();
				}
				progressBar.start();
			}
			else
			{
				progressBar = null;
			}

			clearExistingBdMsg();

			iFiled = new IField();
			iFiled.generate();

//			timeOutId = setTimeout( "communicationTimeOut()", ( 5 * 60 * 1000 ) );

			// <jp>送信</jp>
			// <en>send</en>
			xmlhttpRequest.send( param );

			// <jp>フラグ初期化</jp>
			// <en>Flag initialization</en>
			enterKeyEvent = true;
		}
		else
		{
			try
			{
				xmlhttpRequest.abort();
				xmlhttpRequest = null;
			}
			catch(e)
			{
			}
			clearSendFlag();
		}
	}
	catch( e )
	{
		followin();
		alert( "ajaxPostBack : " + e.message );
	}
}

/** <jp>
 * XMLHttpオブジェクトのステータスを受けるコールバック関数
 </jp> */
/** <en>
 * Callback function that receives status of XMLHttp object
 </en> */
function xmlhttpResponseHandler()
{
	try
	{
		// <jp>ステータス読み込み終了</jp>
		// <en>Status reading end</en>
		switch( xmlhttpRequest.readyState )
		{
			case READY_STATE_HASREAD :

				isChangeBackColor = true;

				if ( progressBar != null )
				{
					progressBar.passage();
				}
				break;

			// <jp>ステータス送信完了</jp>
			// <en>Status transmission completion</en>
			case READY_STATE_COMPLETION :

				// <jp>正常終了である場合</jp>
				// <en>When it is a normal termination</en>
				if ( xmlhttpRequest.status == RESPONSE_OK )
				{
					var xml = xmlhttpRequest.responseXML;

					// <jp>XMLによるレスポンスの場合</jp>
					// <en>At the response by XML</en>
					if ( xml.documentElement != null )
					{
						parseXML( xml );
					}
					// <jp>Textによるレスポンスの場合</jp>
					// <en>At the response by Text</en>
					else
					{
						var text = xmlhttpRequest.responseText;
						createErrorWindow( text );
					}
				}
				// <jp>異常終了である場合</jp>
				// <en>When it is abnormal termination</en>
				else
				{
					var text = xmlhttpRequest.responseText;
					createErrorWindow( text );
				}

				if ( progressBar != null )
				{
					progressBar.wait();
				}

				try
				{
					xmlhttpRequest.abort();
					xmlhttpRequest = null;
				}
				catch(e)
				{
				}

				followinTimer();
//followin();
				break;
		}
	}
	catch( e )
	{
		// <jp>プログレスバー終了</jp>
		// <en>Progress bar end</en>
		if ( progressBar != null )
		{
			progressBar.end();
		}
		followin();
		alert( "xmlhttpResponseHandler : " + e.message );
	}
}

/** <jp>
 * 送信後の後処理
 </jp> */
/** <en>
 * Postprocessing after it transmits
 </en> */
function followin()
{
	// Add K.Fukumori 2006/11/08
	// Unlock the Enterkey focus move.
	canEnterkeyFocusMove = true;
	// Add END

	// <jp>プログレスバー終了</jp>
	// <en>Progress bar end</en>
	if ( progressBar != null )
	{
		progressBar.end();
	}

	// <jp>送信フラグを落とします。</jp>
	// <en>The transmission flag is dropped.</en>
	clearSendFlag();

	try
	{
//		if ( timeOutId != null )
//		{
//			clearTimeout( timeOutId );
//			timeOutId = null;
//		}
	}
	catch(e)
	{
	}

	isFocusSelect = true;

	if ( asyncFocusIs != null && asyncFocusIs != "" )
	{
		var elem = document.all( asyncFocusIs );
		if ( elem != null )
		{
			elem.focus();

			if ( document.activeElement.id == null ||
				 document.activeElement.id == "" )
			{
				setFocus( asyncFocusIs );
			}
		}
	}

	if ( asyncBallon != null && asyncBallon != "" )
	{
		try
		{
			setTimeout( asyncBallon , FOCUS_OFFSET_TIME );
		}
		catch(e)
		{
			eval( asyncBallon );
		}
	}

	asyncFocusIs = "";
	asyncBallon = "";

	if ( iFiled != null )
	{
		iFiled.destroy();
	}

	// <jp>フラグ初期化</jp>
	// <en>Flag initialization</en>
	enterKeyEvent = false;

}

/** <jp>
 * 時間差の送信後の後処理
 </jp> */
/** <en>
 * Postprocessing after time difference is transmitted
 </en> */
function followinTimer()
{
	try
	{
		setTimeout("followin()",1 );
	}
	catch(e)
	{
		followin();
	}
}

/** <jp>
 * 非同期通信用エラーウィンドウを作成します。
 *
 * @param text エラーウィンドウに出力するHTML文字列
 </jp> */
/** <en>
 * The error window for the asynchronous communication is made.
 *
 * @param text HTML character string output to error window
 </en> */
function createErrorWindow( text )
{
	try
	{
		if ( text != null && text != "" )
		{
			var hwind = window.open( "", "", "directories=no,status=no,toolbar=no" );
			hwind.document.open();
			hwind.document.write( text );
			hwind.document.close();
		}
		else
		{
			alert( "Connection Error." );
		}
	}
	catch(e)
	{
	}
}

/** <jp>
 * 送信データの構築
 * @return 構築された送信データ
 </jp> */
/** <en>
 * Construction of transmission data
 * @return Constructed transmission data
 </en> */
function createParameter()
{
	var frm = document.forms[0];
	var postdata = "";

	for (var i = 0 ; i < frm.elements.length ; i++)
	{
		var element = frm.elements[i];
		if ( !element.disabled && element.name != "" &&
			 ( 
			   (
			     ( element.tagName=='INPUT' && element.type=='text' ) ||
			     ( element.tagName=='INPUT' && element.type=='password' ) ||
			     ( element.tagName=='INPUT' && element.type=='hidden' ) ||
			     ( element.tagName=='INPUT' && element.type=='radio' ) ||
			     ( element.tagName=='INPUT' && element.type=='checkbox' )
			   ) ||
			   ( element.tagName=='SELECT' ) ||
			   ( element.tagName=='TEXTAREA' )
			 )
		   )
		{

			if ( element.type=='checkbox' && !element.checked )
			{
				continue;
			}

			if ( element.type=='radio' && !element.checked )
			{
				continue;
			}

			if ( element.tagName=='SELECT' && element.multiple )
			{
				for ( var j = 0; j < element.options.length; j++)
				{
					if ( element.options[j].selected )
					{
						var data = encodeURI( element.options[j].value );
						data = encode( data );

						postdata = postdata.concat("&");
						postdata = postdata.concat( element.name );
						postdata = postdata.concat("=");
						postdata = postdata.concat( data );
					}
				}
			}

			var data = encodeURI( element.value );

			if ( element.name != "__VIEWSTATE" )
			{
				data = encode( data );
			}

			postdata = postdata.concat("&");
			postdata = postdata.concat( element.name );
			postdata = postdata.concat("=");
			postdata = postdata.concat( data );
		}
	}

	postdata = postdata.concat("&");
	postdata = postdata.concat( httpSyncMode );
	postdata = postdata.concat("=");
	postdata = postdata.concat( httpSyncModeAsync );

	postdata = postdata.substring(1);

	return postdata;
}

/** <jp>
 * レスポンスデータ反映
 *
 * @param xml レスポンスXML
 </jp>*/
/** <en>
 * Response data reflection
 *
 * @param xml Response XML
 </en>*/
function parseXML( xml )
{
	// <jp>変更対象の属性が存在しているか確認します。</jp>
	// <en>Whether the attribute to be changed exists is confirmed.</en>
	if ( xml.documentElement.hasChildNodes() )
	{
		// <jp>コントロール毎の処理の開始</jp>
		// <en>Beginning of processing at each control</en>
		for ( var i = 0; i < xml.documentElement.childNodes.length; i++ )
		{
			var elem = xml.documentElement.childNodes.item( i );

			// <jp>controlNodeである場合</jp>
			// <en>When it is controlNode</en>
			if ( elem.nodeName == "control" )
			{
				// <jp>コントロール情報の取得</jp>
				// <en>Acquisition of control information</en>
				var control = elem;

				// <jp>対象ID</jp>
				// <en>Object ID</en>
				var target = control.getAttributeNode( "id" ).value;
				
				// K.Fukumori mod
				// <jp>対象コントロールが存在しないときエラーとしない。</jp>
				// <en>If target is not exist, continue next target. </en>
				if(document.all(target) == null)
				{
					continue;
				}
				
				// <jp>属性毎の処理</jp>
				// <en>Processing of each attribute</en>
				for ( var j = 0; j < control.childNodes.length; j++ )
				{
					// <jp>属性のノードの取得</jp>
					// <en>Acquisition of node of attribute</en>
					var node = control.childNodes.item( j );

					// <jp>タグ変更である場合</jp>
					// <en>When it is a tag change</en>
					if ( node.nodeName == "tag" )
					{
						// <jp>処理タイプの取得</jp>
						// <en>Acquisition of processing type</en>
						var type = node.getAttributeNode( "type" ).value;

						// <jp>タグの書き換え</jp>
						// <en>Rewriting of tag</en>
						eval( "document.all('" + target + "')." + type + "=\"" + node.text + "\"" );

						if ( document.all( target ).tagName=='SELECT' && 
							 document.all( target ).style.visibility != "hidden" )
						{
							var pgorgressSelects = deActiveSelects[ "___xmlHttpRequestProgressBar" ];

							if ( pgorgressSelects != null && pgorgressSelects.length > 0 )
							{
								for ( var sl = 0; sl < pgorgressSelects.length; sl++ )
								{
									if ( target == pgorgressSelects[sl] )
									{
										document.all( target ).style.visibility = "hidden";
									}
								}
							}
						}
					}
					// <jp>関数の変更である場合</jp>
					// <en>When it is a change in the function</en>
					else if ( node.nodeName == "function" )
					{
						// <jp>書き換え関数の取得</jp>
						// <en>Acquisition of rewriting function</en>
						var target = node.getAttributeNode( "target" ).value;
						// <jp>関数書き換え</jp>
						// <en>Function rewriting</en>
						eval( target + "= new Function( node.getAttributeNode( 'value' ).value )" );
					}
					// <jp>関数実行である場合</jp>
					// <en>When it is function execution</en>
					else if ( node.nodeName == "execute" )
					{
						// <jp>実行関数の取得</jp>
						// <en>Acquisition of execution function</en>
						var target = node.getAttributeNode( "func" ).value;
						// <jp>関数の実行</jp>
						// <en>Execution of function</en>
						eval( target );
					}
				}
			}
			// <jp>pageNodeである場合</jp>
			// <en>When it is pageNode</en>
			else if ( elem.nodeName == "page" )
			{
				// <jp>ページ情報の取得</jp>
				// <en>Acquisition of page information</en>
				var page = elem;

				// <jp>属性毎の処理</jp>
				// <en>Processing of each attribute</en>
				for ( var j = 0; j < page.childNodes.length; j++ )
				{
					// <jp>属性のノードの取得</jp>
					// <en>Acquisition of node of attribute</en>
					var node = page.childNodes.item( j );
				
					// <jp>確認ダイアログ表示である場合</jp>
					// <en>When it is a confirmation dialog display</en>
					if ( node.nodeName == "dispConfirm" )
					{
						// <jp>確認ダイアログ表示</jp>
						// <en>Confirmation dialog display</en>
						dispConfirm( node.getAttributeNode( "msg" ).value, 
									 node.getAttributeNode( "flag" ).value, 
									 node.getAttributeNode( "async" ).value );
					}
					// <jp>メッセージダイアログ表示である場合</jp>
					// <en>When it is a message dialog display</en>
					else if ( node.nodeName == "onloadAlert" )
					{
						// <jp>メッセージダイアログ表示</jp>
						// <en>Message dialog display</en>
						onloadAlert( node.getAttributeNode( "msg" ).value );
					}
					// <jp>フォーカスのセットである場合</jp>
					// <en>When it is a set of focus</en>
					else if ( node.nodeName == "focus" )
					{
						// <jp>フォーカスをセット</jp>
						// <en>Focus is set</en>
						// setFocus( node.getAttributeNode( "id" ).value );
						asyncFocusIs = node.getAttributeNode( "id" ).value;
					}
					// <jp>エンターオーダーである場合</jp>
					// <en>When it is Entarordar</en>
					else if ( node.nodeName == "enterKeyFocus" )
					{
						replaceEnterOders( node.getAttributeNode( "value" ).value );
					}
					// <jp>リサイズである場合</jp>
					// <en>When it is a resize</en>
					else if ( node.nodeName == "resizeTo" )
					{
						// <jp>リサイズ実行</jp>
						// <en>Resize execution</en>
						execResizeToCenter( node.getAttributeNode( "width" ).value,
											node.getAttributeNode( "height" ).value,
											node.getAttributeNode( "moveTo" ).value );
					}
					// <jp>定期送信である場合</jp>
					// <en>When it is a regular transmission</en>
					else if ( node.nodeName == "regulartransmission" )
					{
						exceuteTramsmission( node.getAttributeNode( "time" ).value );
					}
					// <jp>viewStateMapである場合</jp>
					// <en>When it is viewStateMap</en>
					else if ( node.nodeName == "viewStateMap" )
					{
						// <jp>viewStateMap書き換え</jp>
						// <en>viewStateMap rewriting</en>
						document.all( node.getAttributeNode( "id" ).value ).value = 
							node.text;
					}
					// <jp>onloadscriptsである場合</jp>
					// <en>When it is onloadscripts</en>
					else if ( node.nodeName == "onloadscripts" )
					{
						// <jp>関数毎の処理</jp>
						// <en>Processing of each function</en>
						for ( var k = 0; k < node.childNodes.length; k++ )
						{
							eval( node.childNodes.item( k ).text );
						}
					}
					// <jp>ballonmessageである場合</jp>
					// <en>When it is ballonmessage</en>
					else if ( node.nodeName == "ballonmessage" )
					{
						// setTimeout( node.text , FOCUS_OFFSET_TIME );
						asyncBallon = node.text;
					}
				}
			}
		}
	}
}

/** <jp>
 * 文字列の置換処理
 *
 * @param text 対象文字列
 * @param sText 置換文字列
 * @param rText 置換後の文字列
 * @return 置換後の文字列
 *
 </jp>*/
/** <en>
 * Substitution processing of character string
 *
 * @param text Object character string
 * @param sText Substitution character string
 * @param rText Character string after it substitutes it
 * @return Character string after it substitutes it
 *
 </en>*/
function replace( text, sText, rText )
{
	var dummy1 = "";
	var dummy2 = text;

	// <jp>無限ループ</jp>
	// <en>Infinite loop</en>
	while ( true )
	{
		// <jp>検索</jp>
		// <en>search</en>
		index = dummy2.indexOf( sText, 0 );
		if ( index == -1 )
		{
			// <jp>検索文字列がなければループを抜ける</jp>
			// <en>It comes off the loop if there is no retrieval character string</en>
			break;
		}

		// <jp>置換</jp>
		// <en>Substitution</en>
		text = dummy2.replace( sText, rText );
		dummy1 += text.substring( 0, index + rText.length );
		dummy2 =  text.substring( index + rText.length, text.length );
	}

	// <jp>置換後の文字列を返却</jp>
	// <en>The character string after it substitutes it is returned.</en>
	return dummy1 + dummy2;
}

/** <jp>
 * 文字列のエンコード
 *
 * @param text 対象文字列
 * @return 置換後の文字列
 *
 </jp>*/
/** <en>
 * encode of character string
 *
 * @param text Object character string
 * @return Character string after it substitutes it
 *
 </en>*/
function encode( text )
{
	if ( text == null || text == "" )
	{
		return "";
	}

	var str  = "";
	var ch = "";
	for ( var i = 0; i < text.length; i++ )
	{
		ch = text.charAt(i);

		switch ( ch )
		{
			case "+" :
				str = str.concat( PLUS );
				break;
			case "&" :
				str = str.concat( AMPERSAND );
				break;
			default :
				str = str.concat( ch );
				break;
		}
	}

	// <jp>置換後の文字列を返却</jp>
	// <en>The character string after it substitutes it is returned.</en>
	return str;
}

/** <jp>
 * エンターオーダー順の書き換え
 *
 * @param orders エンターオーダー順 カンマ区切り
 </jp> */
/** <en>
 * Rewriting of the order of Entarordar
 *
 * @param orders The Entarordar order comma district switching off
 </en> */
function replaceEnterOders( orders )
{
	enterOrders = new Array();

	if ( orders != null && orders != "" )
	{
		enterOrders = orders.split(",");

		if ( enterOrders.length > 0 )
		{
			currentFocusElementId = enterOrders[ enterOrders.length-1 ];
		}
	}
}

/** <jp>
 * アラート表示
 *
 * @param msg 表示するメッセージ
 </jp> */
/** <en>
 * Alert display
 *
 * @param msg Displayed message
 </en> */
function onloadAlert( msg )
{
	alert( msg );
}

/** <jp>
 * フォーカスをセットする
 *
 * @param id フォーカスをセットするコントロールのid
 </jp> */
/** <en>
 * Focus is set
 *
 * @param id Id of control that sets focus
 </en> */
function setFocus( id )
{
	try
	{
		setTimeout("setFoucusTimer('"+ id +"');", FOCUS_OFFSET_TIME );
	}
	catch(e)
	{
	}
}

/** <jp>
 * ウィンドウの中央表示処理
 *
 * @param width 幅
 * @param height 高さ
 * @param moveTo true 中央表示処理を行う false 中央表示処理を行わない
 *
 </jp> */
/** <en>
 * Central display processing of window
 *
 * @param width width
 * @param height height
 * @param moveTo true The central display processing is done false The central display processing is not done
 *
 </en> */
function execResizeToCenter( width, height, moveTo )
{
	try
	{
		if ( window.opener != null && !window.opener.closed )
		{
			var parseWidth = parseInt( width );
			var parseHeight = parseInt( height );

			if ( parseWidth > screen.availWidth )
			{
				parseWidth = screen.availWidth;
			}

			if ( parseHeight > screen.availHeight )
			{
				parseHeight = screen.availHeight;
			}

			if ( parseWidth > 0 && parseHeight > 0 )
			{
				this.resizeTo( width, height );
			}

			//if ( moveTo || moveTo == "true" )
			if ( (typeof moveTo == "boolean" && moveTo) || moveTo == "true" )
			{
				moveToCenter( this );
			}
		}
	}
	catch(e)
	{
	}
}

/** <jp>
 * 定期送信の定義
 *
 * @param time 送信時間(秒)
 </jp> */
/** <en>
 * Definition of regular transmission
 *
 * @param time Transmission time(second)
 </en> */
function exceuteTramsmission( time )
{
	try
	{
		var nTime = parseInt( time );

		if ( nTime <= 0 )
		{
			return;
		}

		tramsmissionId = setTimeout( "prepareTramsmission()", ( nTime * 1000 ) );
	}
	catch(e)
	{
	}
}

/** <jp>
 * 定期送信の実行
 </jp> */
/** <en>
 * Execution of regular transmission
 </en> */
function prepareTramsmission()
{
	ajaxPostBack( 'page', 'clientPull' );
}

/** <jp>
 * 非同期通信のタイムアウト時によびだされます。
 </jp> */
/** <en>
 * It is called at the time-out of the asynchronous communication.
 </en> */
function communicationTimeOut()
{
	alert( communicationTimeOutMessage );

	try
	{
		if ( xmlhttpRequest != null )
		{
			xmlhttpRequest.abort();
			xmlhttpRequest = null;
		}
	}
	catch(e)
	{
	}

	try
	{
		if ( timeOutId != null )
		{
			clearTimeout( timeOutId );
			timeOutId = null;
		}
	}
	catch(e)
	{
	}
}

/** <jp>
 * ユーザの入力を受け付けないフィールドを発生します。
 </jp> */
/** <en>
 * The field that doesn't accept the user's input is generated.
 </en> */
function IField()
{
	// <jp>関数のプロトタイプ宣言</jp>
	// <en>Prototype declaration of function</en>
	this.generate = generate;
	this.destroy  = destroy;

	// <jp>プライベート変数</jp>
	// <en>Private variable</en>
	var ID         = "id";
	var IFiled_TAG = "div";
	var IFiled_ID  = "IFiled";

	/** <jp>
	 * フィールドの発生
	 </jp> */
	/** <en>
	 * Generation of field
	 </en> */
	function generate()
	{
		destroy();

		var lockDiv = document.createElement( IFiled_TAG );
		lockDiv.setAttribute( ID, IFiled_ID );

		var prgStyle = lockDiv.style;

		prgStyle.position = 'absolute';
		prgStyle.backgroundColor = "blue";

		document.body.appendChild( lockDiv );

		var scrollLeft = parseInt( document.body.scrollLeft );
		var scrollTop  = parseInt( document.body.scrollTop );

		document.all( IFiled_ID ).style.top  = 0;
		document.all( IFiled_ID ).style.left = 0;
		document.all( IFiled_ID ).style.width = document.body.clientWidth + scrollLeft;
		document.all( IFiled_ID ).style.height = document.body.clientHeight + scrollTop;
		document.all( IFiled_ID ).style.filter = "alpha(style=1, opacity=0 )";
	}

	/** <jp>
	 * フィールドの破棄
	 </jp> */
	/** <en>
	 * Annulment of field
	 </en> */
	function destroy()
	{
		if( document.all( IFiled_ID ) != null )
		{
			document.body.removeChild( document.all( IFiled_ID ) );
		}
	}

	return this;
}
