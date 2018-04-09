using System;
using System.Collections.Generic;
using System.Text;

namespace YkkWms
{
    class Consts
    {

    }

    public class Sagyokbn
    {
        public static string Plan = "1";
        public static string Auto = "2";
        public static string Key_Spe = "3";
        public static string Loc_Spe = "4";
        public static string InvChk = "5";
        public static string ReInput = "6";
        public static string Direct = "7";
    }

    public class Nyusyumode
    {
        public static string Normal = "1";
        public static string Empty_Bucket = "2";
    }

    public class WeighterCommand
    {
        public static byte[] STX = new byte[] { 02 };
        public static byte[] ETX = new byte[] { 03 };
        public static byte[] EOT = new byte[] { 04 };
        public static byte[] ENQ = new byte[] { 05 };
        public static byte[] ACK0 = new byte[] { 16,48 };
        public static byte[] ACK1 = new byte[] { 16,49 };
        public static byte[] WEIGHT_REQUEST = new byte[] { 02,53,49,03 };
        public static byte[] ITEM_WEIGHT = new byte[] { 02, 52, 52, 48, 48, 51, 50, 49, 48, 48, 48, 48, 54, 48, 48, 48, 48, 48, 49, 49, 50, 48, 48, 48, 56, 48, 48, 48, 48, 48, 48, 48, 48, 03 };
        public static byte[] BUCKUT_WEIGHT = new byte[] { 02, 52, 52, 48, 48, 51, 50, 49, 48, 48, 48, 48, 54, 48, 48, 48, 48, 48, 49, 49, 56, 48, 48, 48, 56, 48, 48, 48, 48, 48, 48, 48, 48, 03 };

    }

    public class PickingType
    {
        public static string Normal = "0";
        public static string Reverse = "1";
        public static string Total = "2";
        public static string Return = "3";
        public static string Abnormal = "4";
        public static string Cycle = "5";
        public static string Subdivided = "6";
    }

    public class StockInMode
    {
        public static string Normal = "0";
        public static string EmptyBucket = "1";     
    }

    public class LightType
    {
        public static string Data_Error = "02";
        public static string Height_Error = "03";
        public static string Range_Error = "05";
    }
}
