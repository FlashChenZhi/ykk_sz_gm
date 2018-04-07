using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.IO.Ports;
using System.IO;
using System.Windows.Forms;

namespace YkkWms
{
    class Weighter_Big
    {
        private static Weighter_Big weighter = null;
        private static int latency = int.Parse(AppConfig.Get("Latency"));
        public static Weighter_Big Instance
        {
            get
            {
                if (weighter == null)
                {
                    weighter = new Weighter_Big();
                }
                return weighter;
            }
        }

        SerialPort sp = new SerialPort(AppConfig.Get("ComPort_Big"));
        private Weighter_Big()
        {
            InitSerialPort();
        }

        public bool SendItemWeight(decimal itemWeight)
        {
            try
            {
                if (SendENQ())
                {
                    byte[] weightCommand = WeighterCommand.ITEM_WEIGHT;
                    byte[] weightBytes = MakeItemData(itemWeight);
                    for (int i = 0; i < 8; i++)
                    {
                        weightCommand[weightCommand.Length - 9 + i] = weightBytes[i];
                    }
                    Write(weightCommand);
                    Thread.Sleep(latency);
                    byte[] bytes;
                    try
                    {
                        bytes = Read();
                    }
                    catch (Exception)
                    {
                        if (SendENQ1())
                        {
                            SendEOT();
                            return true;
                        }
                        else
                        {
                            if (SendENQ1())
                            {
                                SendEOT();
                                return true;
                            }
                            else
                            {
                                throw new Exception("读取称重机超时");
                            }                            
                        }
                        
                    }
                    if (IsEqual(bytes, WeighterCommand.ACK1))
                    {
                        SendEOT();
                    }
                }
            }
            finally
            {
                sp.Close();
            }
            return false;
        }

        public bool SendBucketWeight(decimal bucketWeight)
        {
            try
            {
                if (SendENQ())
                {
                    byte[] weightCommand = WeighterCommand.BUCKUT_WEIGHT;
                    byte[] weightBytes = MakeBucketData(bucketWeight);
                    //weightBytes[5] = 48;
                    for (int i = 0; i < 8; i++)
                    {
                        weightCommand[weightCommand.Length - 9 + i] = weightBytes[i];
                    }
                    Write(weightCommand);
                    Thread.Sleep(latency);
                    byte[] bytes;
                    try
                    {
                        bytes = Read();
                    }
                    catch (Exception)
                    {
                        if (SendENQ1())
                        {
                            SendEOT();
                            return true;
                        }
                        else
                        {
                            if (SendENQ1())
                            {
                                SendEOT();
                                return true;
                            }
                            else
                            {
                                throw new Exception("读取称重机超时");
                            }
                        }
                    }
                    
                    if (IsEqual(bytes, WeighterCommand.ACK1))
                    {
                        SendEOT();
                    }
                }
            }
            finally
            {
                sp.Close();
            }
            return false;
        }


        public WeightData GetWeightData()
        {
            try
            {
                if (SendENQ())
                {
                    if (SendWeightRequest())
                    {
                        SendEOT();

                        Thread.Sleep(latency);
                        byte[] bytes = Read();
                        if (IsEqual(bytes, WeighterCommand.ENQ))
                        {
                            Write(WeighterCommand.ACK0);
                            //Thread.Sleep(latency);
                            bytes = ReadLong();
                            if (bytes.Length >= 32)
                            {
                                try
                                {
                                    Write(WeighterCommand.ACK1);
                                    Read();
                                }
                                finally
                                {
                                    sp.Close();
                                }
                                WeightData data = DoWeightData(bytes);
                                //ThreadPool.QueueUserWorkItem(new WaitCallback(DoTheEnd));
                                return data;
                            }
                         
                        }

                    }
                }
            }
            catch (Exception)
            {
                sp.Close();
                throw;
            }

            return null;
        }

        private void DoTheEnd(object a)
        {
            try
            {
                Read();
                Write(WeighterCommand.ACK1);
                Read();
            }
            catch (Exception ex)
            {
                string b = ex.Message;
            }
            finally
            {
                sp.Close();
            }

        }

        private byte[] MakeBucketData(decimal weight)
        {
            string wStr = weight.ToString("F1");    //保留1位小数
            wStr = wStr.Remove(wStr.Length - 2,1);    //去除小数点
            wStr = wStr.PadLeft(6, '0');
            byte[] bytes = new byte[wStr.Length + 2];
            for (int i = 0; i < 6; i++)
            {
                bytes[i] = Convert.ToByte(wStr[i]);
            }
            bytes[6] = 48;  //g
            bytes[7] = 49;  //1位小数
            return bytes;
        }

        private byte[] MakeItemData(decimal weight)
        {
            string wStr = weight.ToString("F4");    //保留4位小数
            wStr = wStr.Remove(wStr.Length - 5, 1);    //去除小数点
            wStr = wStr.PadLeft(6, '0');
            byte[] bytes = new byte[wStr.Length + 2];
            for (int i = 0; i < 6; i++)
            {
                bytes[i] = Convert.ToByte(wStr[i]);
            }
            bytes[6] = 48;  //g
            bytes[7] = 52;  //4位小数
            return bytes;
        }

        private WeightData DoWeightData(byte[] bytes)
        {
            WeightData data = new WeightData();
            int pm = bytes[8] - 48;
            StringBuilder sb = new StringBuilder();
            for (int i = 9; i < 15; i++)
            {
                sb.Append((bytes[i] - 48).ToString());
            }
            data.count = int.Parse(sb.ToString());

            sb = new StringBuilder();
            for (int i = 17; i < 23; i++)
            {
                sb.Append((bytes[i] - 48).ToString());
            }
            decimal weight = decimal.Parse(sb.ToString());  //重量
            int e = bytes[24] - 48;  //指数
            if (e > 0)
            {
                weight = weight / Convert.ToDecimal(Math.Pow(10, e));
            }
            if (pm == 1)
            {
                data.weight = 0 - weight;
            }
            else
            {
                data.weight = weight;
            }
            int anding = bytes[7] - 48;
            if (anding == 0 || anding == 4)
            {
                data.isAnding = true;
            }
            else
            {
                data.isAnding = false;
            }
            return data;
        }


        private bool IsEqual(byte[] bytes1, byte[] bytes2)
        {
            if (bytes1 != null && bytes2 != null)
            {
                if (bytes1.Length >= bytes2.Length)
                {
                    for (int i = 0; i < bytes2.Length; i++)
                    {
                        if (bytes1[i] != bytes2[i])
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        private bool SendENQ()
        {
            Write(WeighterCommand.ENQ);
            Thread.Sleep(latency);
            try
            {
                byte[] bytes = Read();
                if (IsEqual(bytes, WeighterCommand.ACK0))
                {
                    return true;
                }
            }
            catch (Exception)
            {
            }
            return false;
        }

        private bool SendENQ1()
        {
            Write(WeighterCommand.ENQ);
            Thread.Sleep(latency);
            try
            {
                byte[] bytes = Read();
                if (IsEqual(bytes, WeighterCommand.ACK1))
                {
                    return true;
                }
            }
            catch (Exception)
            {
            }
            return false;
        }
        private bool SendWeightRequest()
        {
            Write(WeighterCommand.WEIGHT_REQUEST);
            Thread.Sleep(latency);
            try
            {
                byte[] bytes = Read();
                if (IsEqual(bytes, WeighterCommand.ACK1))
                {
                    return true;
                }
            }
            catch (Exception)
            {
            }
            return false;
        }

        private void SendEOT()
        {
            Write(WeighterCommand.EOT);
        }


        #region SerialPort
        private void InitSerialPort()
        {
            sp.BaudRate = 9600;
            sp.DataBits = 8;
            sp.Parity = Parity.None;
            sp.StopBits = StopBits.One;
            sp.ReadBufferSize = 4096;
            sp.WriteBufferSize = 2048;
            sp.ReadTimeout = 5000;
            sp.RtsEnable = true;
        }

        private byte[] Read()
        {
            byte firstByte = Convert.ToByte(sp.ReadByte());
            int bytesRead = sp.BytesToRead;
            byte[] bytesData = new byte[bytesRead + 1];
            bytesData[0] = firstByte;
            for (int i = 1; i <= bytesRead; i++)
                bytesData[i] = Convert.ToByte(sp.ReadByte());
            WriteLog(bytesData,"in");
            return bytesData;
        }

        private byte[] ReadLong()
        {
            byte firstByte = Convert.ToByte(sp.ReadByte());
            List<byte> bytes = new List<byte>();
            bytes.Add(firstByte);
            do
            {
                byte b = Convert.ToByte(sp.ReadByte());
                bytes.Add(b);
                if (b == 3)
                {
                    break;
                }
            }
            while (true);
            byte[] bytesData = bytes.ToArray();
            WriteLog(bytesData,"in");
            return bytesData;
        }

        private void Write(byte[] bytes)
        {
            if (!sp.IsOpen)
            {
                sp.Open();
            }
            sp.DiscardInBuffer();
            sp.DiscardOutBuffer();
            sp.Write(bytes, 0, bytes.Length);
            WriteLog(bytes,"out");
        }

        private void Hex2Dec(byte[] bytes)
        {
            if (bytes != null)
            {
                for (int i = 0; i < bytes.Length; i++)
                {
                    bytes[i] = byte.Parse(Convert.ToString(bytes[i], 16));
                }
            }
        }

        public void WriteLog(byte[] bytes,String inOut)
        {
            //if (AppConfig.Get("Debug") == "1")
            //{
            //    try
            //    {
            //        StringBuilder sb = new StringBuilder();
            //        for (int i = 0; i < bytes.Length; i++)
            //        {
            //            sb.Append(bytes[i].ToString()+",");
            //        }
            //        StreamWriter stream = new StreamWriter(string.Format("Comlog{0}.txt", DateTime.Now.ToString("yyyyMMdd")), true, System.Text.Encoding.Default);
            //        stream.WriteLine(DateTime.Now.ToString() + "  " +  inOut + " " + sb.ToString());
            //        stream.Flush();
            //        stream.Close();
            //    }
            //    catch
            //    {
            //    }
            //}
            if (AppConfig.Get("Debug") == "1")
            {
                try
                {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < bytes.Length; i++)
                    {
                        int asc = bytes[i];
                        string a = asc.ToString();
                        if (asc >= 48)
                        {
                            a = Convert.ToChar(bytes[i]).ToString();
                        }
                        sb.Append(a + ",");
                    }
                    sb.Replace("16,0,", "10,30,");
                    sb.Replace("16,1,", "10,31,");

                    StreamWriter stream = new StreamWriter(string.Format("Comlog{0}.txt", DateTime.Now.ToString("yyyyMMdd")), true, System.Text.Encoding.Default);
                    stream.WriteLine(DateTime.Now.ToString() + "  " + inOut + " " + sb.ToString());
                    stream.Flush();
                    stream.Close();
                }
                catch
                {
                }
            }
        }


        #endregion

    }
}
