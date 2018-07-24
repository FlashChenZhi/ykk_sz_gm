package jp.co.daifuku.wms.YkkGMAX.Utils;

import java.util.Hashtable;

public class DBFlags
{

	public static class BagFlag
	{
		public static final String YES = "1";
		public static final String NO = "0";
	}

	public static class AccessFlag
	{
		public static final String ASSIGNABLE = "0";

		public static final String UNASSIGNABLE = "1";
	}

	public static class Chudanflg
	{
		public static final String StopFlgOff = "0";

		public static final String StopFlgOn = "1";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(StopFlgOn, "中断中ON");
			ht.put(StopFlgOff, "中断中OFF");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class CompleteTimingFlag
	{
		public static String AM = "1";

		public static String PM = "2";
	}

	public static class DispatchDetail
	{
		public static final String UNIT = "1";

		public static final String PICKING = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(UNIT, "整箱出库");
			ht.put(PICKING, "拣选出库");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class Fnretrieval_st_Flags
	{
		public static final String STOP = "0";

		public static final String STOP_RELEASE = "1";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(STOP, "停止");
			ht.put(STOP_RELEASE, "停止解除");
			return String.valueOf(ht.get(flag));
		}

	}

	public static class HightFlag
	{

		public static final String HIGH = "2";

		public static final String LOW = "1";

	}

	public static class HikiFlg
	{
		public static final String UNUSED = "0";

		public static final String USED = "1";
	}

	public static class HjyotaiFlg
	{
		public static final String DISPATCH = "0";

		public static final String START = "1";

		public static final String WAIT = "2";

		public static final String DIRECTION_OVER = "3";

		public static final String TAKE_OUT_OVER = "4";

		public static final String FINISHED = "5";

		public static final String ARRIVE = "6";

		public static final String ERROR = "9";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(DISPATCH, "分配");
			ht.put(START, "开始");
			ht.put(WAIT, "应答等待");
			ht.put(DIRECTION_OVER, "指示完了");
			ht.put(TAKE_OUT_OVER, "取货完了");
			ht.put(FINISHED, "完了");
			ht.put(ARRIVE, "到达");
			ht.put(ERROR, "异常");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class LabelType
	{
		public static final String NORMAL = "1";

		public static final String FUYONG = "2";

		public static final String EXTERNAL = "3";

		public static final String CUSTOMER = "4";
	}

	public static class ManageItemFlag
	{
		public static final String INMANAGE = "0";

		public static final String WITHOUTMANAGE = "1";
	}

	public static class Modechgkbn
	{
		public static final String NOCHANGE = "0";

		public static final String MANUALCHANGE = "1";

		public static final String AUTOCHANGE = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(NOCHANGE, "无切换");
			ht.put(MANUALCHANGE, "手动切换");
			ht.put(AUTOCHANGE, "自动切换");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class Nyusyukbn
	{
		public static final String STOCKIN = "1";

		public static final String STOCKOUT = "2";

		public static final String ST_TO_ST = "3";

		public static final String LOCAT_TO_LOCAT = "5";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(STOCKIN, "入库");
			ht.put(STOCKOUT, "出库");
			ht.put(ST_TO_ST, "直行");
			ht.put(LOCAT_TO_LOCAT, "库间移动");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class SagyokbnAndNyusyukbn
	{
		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String sagyokbn, String nyusyukbn)
		{
			String flag = sagyokbn + "," + nyusyukbn;
			ht.put(Sagyokbn.PLAN + "," + Nyusyukbn.STOCKIN, "通常入库");
			ht.put(Sagyokbn.AUTO + "," + Nyusyukbn.STOCKIN, "通常入库");
			ht.put(Sagyokbn.ROUTING + "," + Nyusyukbn.STOCKIN, "通常入库");
			ht.put(Sagyokbn.RESTOCKIN + "," + Nyusyukbn.STOCKIN, "再入库");
			ht.put(Sagyokbn.PLAN + "," + Nyusyukbn.STOCKOUT, "通常出库");
			ht.put(Sagyokbn.ROUTING + "," + Nyusyukbn.STOCKOUT, "通常出库");
			ht.put(Sagyokbn.ROUTING + "," + Nyusyukbn.ST_TO_ST, "直行出库");
			ht
					.put(Sagyokbn.ZAIKEY_DESIGNATE + "," + Nyusyukbn.STOCKOUT,
							"例外出库");
			ht.put(Sagyokbn.LOCATION_DESIGNATE + "," + Nyusyukbn.STOCKOUT,
					"例外出库");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class Nyusyumode
	{
		public static final String MIDDLE_MODE = "0";

		public static final String NORMAL_MODE = "1";

		public static final String BLANK_BUCKET_MODE = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(MIDDLE_MODE, "中间模式");
			ht.put(NORMAL_MODE, "入库模式");
			ht.put(BLANK_BUCKET_MODE, "空箱登录模式");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class PritingFlag
	{
		public static final String TO_PRINT = "1";

		public static final String PRINTED = "3";

	}

	public static class ProcFlag
	{
		public static final String UNDEALT = "0";

		public static final String DEALT = "1";
	}

	public static class RemoveConventFlag
	{
		public static final String AUTO_DEPO = "1";

		public static final String FLAT_DEPO = "2";
	}

	public static class RoleID
	{
		public static final String ADMIN = "1";

		public static final String LEADER = "2";

		public static final String WORKER_A = "3";

		public static final String WORKER_B = "4";
	}

	public static class Sagyokbn
	{
		public static final String PLAN = "1";

		public static final String AUTO = "2";

		public static final String ZAIKEY_DESIGNATE = "3";

		public static final String LOCATION_DESIGNATE = "4";

		public static final String RESTOCKIN = "6";

		public static final String ROUTING = "7";

		public static final String CYCLECOUNT = "5";

		public static final String MAINTENANCE = "9";

		public static final String PICKOUT = "D";

		public static final String NOT_EQUAL = "E";

		public static final String FORBID_IN = "G";

		public static final String ALLOW_IN = "F";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(CYCLECOUNT, "盘点");
			ht.put(MAINTENANCE, "维护");
			ht.put(PICKOUT, "异常排出");
			ht.put(NOT_EQUAL, "差异");
			ht.put(FORBID_IN, "进入禁止");
			ht.put(ALLOW_IN, "进入许可");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class Sakukbn
	{
		public static final String INCREASE = "1";

		public static final String DECREASE = "2";

		public static final String NOCHANGE = "0";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(NOCHANGE, "增");
			ht.put(INCREASE, "增");
			ht.put(DECREASE, "减");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class ShikiFlg
	{
		public static final String UNUSED = "0";

		public static final String MOVING = "1";

		public static final String CHECKING = "2";

		public static final String PICKING = "4";
	}

	public static class StartTimingFlag
	{
		public static String AM = "1";

		public static String PM = "2";

		public static String UNKNOWN = "3";
	}

	public static class StationType
	{
		public static final String PICKING_STATION = "1";

		public static final String REJECT_STATION = "3";

		public static final String UNIT_STATION = "2";

		public static final String UNUSED_STATION = "0";
	}

	public static class StoragePlaceFlag
	{
		public static final String AUTO = "0";

		public static final String FLAT = "1";

		public static final String ALL = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(AUTO, "自动仓库");
			ht.put(FLAT, "平库仓库");
			ht.put(ALL, "全部");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class SyoriFlg
	{
		public static final String NOTPROSESSED = "0";
	}

	public static class Systemflg
	{
		public static final String ONLINE = "1";

		public static final String OFFLINE = "0";

		public static final String OFFLINE_RESERVED = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(ONLINE, "OnLine");
			ht.put(OFFLINE, "OffLine");
			ht.put(OFFLINE_RESERVED, "OffLine预约");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class Tanaflg
	{
		public static final String EMPTY_LOCAT = "0";

		public static final String USED_LOCAT = "1";

		public static final String FORBID_LOCAT = "8";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(EMPTY_LOCAT, "空货位");
			ht.put(USED_LOCAT, "实货位");
			ht.put(FORBID_LOCAT, "禁止货位");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class UnitFlag
	{
		public static final String WITH_ACCOUNT = "1";

		public static final String WITHOUT_ACCOUNT = "0";

	}

	public static class UnitStat
	{
		public static final String NORMAL = "1";

		public static final String STOP = "2";

		public static final String CUT_OFF = "3";

		public static final String TROUBLE = "4";

		public static final String OFFLINE = "5";

		public static final String UNCONNECT = "6";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(NORMAL, "运行");
			ht.put(STOP, "停止");
			ht.put(CUT_OFF, "切离");
			ht.put(TROUBLE, "故障");
			ht.put(OFFLINE, "离线");
			ht.put(UNCONNECT, "未连接");
			return String.valueOf(ht.get(flag));
		}
	}

	public static class WeightReportCompleteFlag
	{
		public static final String UNCOMPLETED = "0";

		public static final String REPORTING = "1";

		public static final String COMPLETED = "2";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(UNCOMPLETED, "未报告");
			ht.put(REPORTING, "报告中");
			ht.put(COMPLETED, "报告完成");
			if (ht.get(flag) == null)
			{
				return "";
			}
			return String.valueOf(ht.get(flag));
		}
	}

	public static class ZaijyoFlg
	{
		public static final String STINSTOCK = "0";

		public static final String STOCKIN_ORDER = "1";

		public static final String INSTOCK = "2";

		public static final String STOCKOUT_ORDER = "3";

		public static final String STOCKOUTING = "4";

		public static final String ERRO_LOCATION = "5";

		public static final String FORBID_LOCATION = "8";

		private static Hashtable ht = new Hashtable();

		public static String parseDBToPage(String flag)
		{
			ht.put(STOCKIN_ORDER, "作业货位");
			ht.put(STOCKOUT_ORDER, "作业货位");
			ht.put(STOCKOUTING, "作业货位");
			ht.put(INSTOCK, "实货位");
			ht.put(ERRO_LOCATION, "异常货位");
			ht.put(FORBID_LOCATION, "禁止货位");
			return String.valueOf(ht.get(flag));
		}
	}
}
