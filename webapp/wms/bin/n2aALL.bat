rem $Id: n2aALL.bat,v 1.1.1.1 2008/01/02 08:29:39 administrator Exp $
rem native2ascii for windows
native2ascii -encoding UTF-8 LocaleMapping.properties             ../classes/LocaleMapping.properties
native2ascii -encoding UTF-8 DispResource.properties              ../classes/DispResource.properties
native2ascii -encoding UTF-8 DispResource_ja_JP.properties        ../classes/DispResource_ja_JP.properties
native2ascii -encoding UTF-8 MessageResource.properties           ../classes/MessageResource.properties
native2ascii -encoding UTF-8 MessageResource_ja_JP.properties     ../classes/MessageResource_ja_JP.properties
native2ascii -encoding UTF-8 WMSParam.properties                  ../classes/WMSParam.properties
native2ascii -encoding UTF-8 CommonParam.properties               ../classes/CommonParam.properties
native2ascii -encoding UTF-8 DebugParam.properties                ../classes/DebugParam.properties
native2ascii -encoding UTF-8 OperationLog.properties 		  ../classes/OperationLog.properties
native2ascii -encoding UTF-8 ASRSParam.properties                 ../classes/ASRSParam.properties
native2ascii -encoding UTF-8 ToolParam                            ../classes/ToolParam.properties
native2ascii -encoding UTF-8 DispResource_zh_CN.properties        ../classes/DispResource_zh_CN.properties
native2ascii -encoding UTF-8 MessageResource_zh_CN.properties     ../classes/MessageResource_zh_CN.properties

