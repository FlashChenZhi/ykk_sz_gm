using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace YkkWms
{
    class Weigher
    {
        SerialPortHelper sp;
        public Weigher(string comPort)
        {
            sp = new SerialPortHelper(comPort);
        }

        public decimal GetWeight()
        {
            sp.Open();
            if (SendENQ())
            {
                if (SendWeightRequest())
                {
                    try
                    {
                        byte[] bytes = sp.Read();
                        if (bytes[0] == WeigherCommandByte.ENQ)
                        {
                            bytes[0] = WeigherCommandByte.ACK;
                            sp.Send(bytes);
                            bytes = sp.Read();
                            //Todo
                        }
                    }
                    catch (Exception)
                    {
                    }
                }
            }
            return 1;
        }

        private bool SendENQ()
        {
            sp.ClearBuffer();
            byte[] enqBytes = new byte[1];
            enqBytes[0] = WeigherCommandByte.ENQ;
            sp.Send(enqBytes);
            sp.SetReadTimeOut(2);
            try
            {
                byte[] bytes = sp.Read();
                if (bytes[0] == WeigherCommandByte.ACK)
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
            return true;
            //ToDo
        }
    }
}
