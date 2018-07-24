function ProgressBar()
{
	// <jp>関数のプロトタイプ宣言</jp>
	// <en>Prototype declaration of function</en>
	this.start   = start;
	this.passage = passage
	this.wait    = wait;
	this.end     = end;

	// <jp>プライベート変数</jp>
	// <en>Private variable</en>
	var PROGRESSBAR_ID = "___xmlHttpRequestProgressBar";
	var PROGRESSBAR_COUNTER_ID = "___xmlHttpRequestProgressBarCounter";

	var PROGRESSBAR_WIDTH = 200;
	var PROGRESSBAR_MAXCOUNT = 10;
	var PROGRESSBAR_LOADCOUNT = 8;
	var PROGRESSBAR_HEIGHT_OFFSET = 45;
	var PROGRESSBAR_WIDTH_OFFSET = 20;
	var PROGRESSBAR_CREATE_TIMER = 0.2 * 1000;
	var PROGRESSBAR_ENDWAIT_TIMER = 0.8 * 1000;

	var PROGRESS_COLOR    = "#000099";
	var PROGRESS_BGCOLOR  = "#FBFDFF";
	var PROGRESS_MSG      = "#00BFFF";
	var PROGRESS_MSG_LEFT = ( PROGRESSBAR_WIDTH / 2 ) - 5;

	var ID = "id"
	var PROGRESSBAR_TAG = "div";
	var SIZE_SUFFIX = "px";

	var prgTimerId = "";
	var progress = "";
	var count = -1;

	// <jp>公開関数</jp>
	// <en>Function of opening to the public</en>

	/** <jp>
	 * プログレスバーの描画
	</jp> */
	/** <en>
	 * Drawing of progress bar
	</en> */
	function start()
	{
		if ( progress != "" || prgTimerId != "" )
		{
			removeProgress();
		}

		progress = document.createElement( PROGRESSBAR_TAG );
		progress.setAttribute( ID, PROGRESSBAR_ID );

		var prgStyle = progress.style;

		prgStyle.width = PROGRESSBAR_WIDTH;

		prgStyle.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET;
		prgStyle.position = 'absolute';
		prgStyle.filter = "alpha(opacity=90)";

		var top = parseInt( document.body.scrollTop );
		prgStyle.top = ( document.body.clientHeight + top ) - PROGRESSBAR_HEIGHT_OFFSET + SIZE_SUFFIX;

		var left = parseInt( document.body.scrollLeft );
		prgStyle.left  = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET + left;

		document.body.appendChild( progress );

		document.all( PROGRESSBAR_ID ).innerHTML = createProgressBarHTML();
		document.all( PROGRESSBAR_COUNTER_ID ).innerHTML = '';
		createProgressbar( PROGRESSBAR_LOADCOUNT );

		hiddenSelect( document.all( PROGRESSBAR_ID ) );

		window.onscroll = progressScrollFixed;
		window.onresize = progressResizeFixed;

		prgTimerId = setInterval( function(){ createProgressbar( PROGRESSBAR_LOADCOUNT ) }, PROGRESSBAR_CREATE_TIMER );
	}

	/** <jp>
	 * 途中経過
	 </jp> */
	/** <en>
	 * process
	 </en> */
	function passage()
	{
		clearProgressInterval();

		for ( var i = count; i < PROGRESSBAR_LOADCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_LOADCOUNT );
		}
	}

	/** <jp>
	 * 待機
	 </jp> */
	/** <en>
	 * standby
	 </en> */
	function wait()
	{
		clearProgressInterval();

		// <jp>終了用数合わせ</jp><en>For end.</en>
		for ( var i = count; i < PROGRESSBAR_MAXCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_MAXCOUNT );
		}
	}

	/** <jp>
	 * プログレスバーの終了
	 </jp> */
	/** <en>
	 * End of progress bar
	 </en> */
	function end()
	{
		clearProgressInterval();

		for ( var  i = count; i < PROGRESSBAR_MAXCOUNT; i++ )
		{
			createProgressbar( PROGRESSBAR_MAXCOUNT );
		}
		// <jp>プログレスバー削除</jp><en>Remove progress bar.</en>
		removeProgress();
	}

	// <jp>非公開関数</jp>
	// <en>progress bar deletion</en>

	/** <jp>
	 * バーの進捗表示
	 * 
	 * @param maxCount 最大進捗率
	 </jp> */
	/** <en>
	 * Progress display of bar
	 * 
	 * @param maxCount The maximum progress rate
	 </en> */
	function createProgressbar( maxCount )
	{
		if ( progress != "" )
		{
			if ( count < maxCount )
			{
				var distance = ( ( count + 1 ) * ( PROGRESSBAR_WIDTH / PROGRESSBAR_MAXCOUNT ) );

				if ( count >= PROGRESSBAR_MAXCOUNT )
				{
					distance = PROGRESSBAR_WIDTH;
				}

				var html  = "<div style='height:100%; width:" + distance + "px;background-color:" + PROGRESS_COLOR + "'align='center'>";
					// html += "<span style='position:absolute; top:0px; left:" + PROGRESS_MSG_LEFT + "px; color:"+ PROGRESS_MSG + ";'>";
					// html += ( ( count + 1 ) * 10 ) + "%";
					// html += "</span>";
					html += "</div>";

				document.getElementById( PROGRESSBAR_COUNTER_ID ).innerHTML = html;
				count++;
			}
		}
	}

	/** <jp>
	 * プログレスバーのインターバル解除
	 </jp> */
	/** <en>
	 * Release at intervals of progress bar
	 </en> */
	function clearProgressInterval()
	{
		try
		{
			if ( prgTimerId != "" )
			{
				clearInterval( prgTimerId );
			}
		}
		catch(e)
		{
		}
		prgTimerId = "";
	}

	/** <jp>
	 * プログレスバーの遅延削除処理
	 </jp> */
	/** <en>
	 * Delay deletion processing of progress bar
	 </en> */
	function removeProgressTimer()
	{
		try
		{
			setTimeout( function(){ removeProgress() }, PROGRESSBAR_ENDWAIT_TIMER );
		}
		catch(e)
		{
			removeProgress();
		}
	}

	/** <jp>
	 * プログレスバーの削除処理
	 </jp> */
	/** <en>
	 * Deletion processing of progress bar
	 </en> */
	function removeProgress()
	{
		count = -1;

		// <jp>Selectの非表示化</jp><en>Hide select</en>
		visibleSelect( progress );

		if ( progress != "" )
		{
			document.body.removeChild( progress );
			progress = "";
		}
	}

	/** <jp>
	 * プログレスバーのフローティング化
	 </jp> */
	/** <en>
	 * Making of progress bar floating
	 </en> */
	function progressScrollFixed()
	{
		if ( progress != "" )
		{
			if( document.body.scrollTop >= 0 )
			{
				var top = parseInt( document.body.scrollTop );
				document.getElementById( PROGRESSBAR_ID ).style.top = document.body.clientHeight - PROGRESSBAR_HEIGHT_OFFSET + top;
			}
			if( document.body.scrollLeft >= 0 )
			{
				var left = parseInt( document.body.scrollLeft );
				document.getElementById( PROGRESSBAR_ID ).style.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET + left;
			}
		}
	}

	/** <jp>
	 * プログレスバーのフローティング処理
	 </jp> */
	/** <en>
	 * Floating processing of progress bar
	 </en> */
	function progressResizeFixed()
	{
		if ( progress != "" )
		{
			document.getElementById( PROGRESSBAR_ID ).style.top  = document.body.clientHeight - PROGRESSBAR_HEIGHT_OFFSET;
			document.getElementById( PROGRESSBAR_ID ).style.left = document.body.clientWidth - PROGRESSBAR_WIDTH - PROGRESSBAR_WIDTH_OFFSET;
		}
	}

	/** <jp>
	 * 進捗用オブジェクトの構築
	 * @return 構築したオブジェクト
	 </jp> */
	/** <en>
	 * Construction of object for progress
	 * @return Constructed object
	 </en> */
	function createProgressBarHTML()
	{
		var html = "<div id='" + PROGRESSBAR_COUNTER_ID + "' style =\"height:20px;border:2px inset; background-color:" + PROGRESS_BGCOLOR + ";\"></div>";
		return html;
	}

	return this;
}
