using System;
using System.Collections.Generic;
using System.Text;
using System.IO.Ports;
namespace YkkWms
{
    class SerialPortHelper
    {
        private SerialPort sPort;

        public int ReadBufferSize { get; set; }
        public int WriteBufferSize { get; set; }
        public int BaudRate { get; set; }
        public int DataBits { get; set; }
        public Parity Parity { get; set; }
        public StopBits StopBits { get; set; }
        public byte[] ReceivedBytes { get; set; }

        public bool IsReceived { get; set; }
        public SerialPortHelper(string portName)
        {
            sPort = new SerialPort(portName);
            BaudRate = 9600;
            DataBits = 8;
            Parity = Parity.None;
            StopBits = StopBits.One;
            ReadBufferSize = 1024;
            WriteBufferSize = 1024;
        }

        public byte[] Read()
        {
            byte firstByte = Convert.ToByte(sPort.ReadByte());
            int bytesRead = sPort.BytesToRead;
            byte[] bytesData = new byte[bytesRead + 1];
            bytesData[0] = firstByte;
            for (int i = 1; i <= bytesRead; i++)
                bytesData[i] = Convert.ToByte(sPort.ReadByte());
            return bytesData;
        }

        public void Send(byte[] bytes)
        {
            if (!sPort.IsOpen)
            {
                Open();
            }
            sPort.Write(bytes, 0, bytes.Length);
        }

        public void Open()
        {
            if (!sPort.IsOpen)
            {
                sPort.BaudRate = BaudRate;
                sPort.DataBits = DataBits;
                sPort.Parity = Parity;
                sPort.StopBits = StopBits;
                sPort.ReadBufferSize = ReadBufferSize;
                sPort.WriteBufferSize = WriteBufferSize;
                //sPort.ReceivedBytesThreshold = 1;
                //sPort.DataReceived += new SerialDataReceivedEventHandler(DataReceived);
                sPort.Open();
            }
        }

        public void Close()
        {
            sPort.Close();
        }

        public void ClearBuffer()
        {
            sPort.DiscardInBuffer();
        }

        public void SetReadTimeOut(int seconds)
        {
            sPort.ReadTimeout = 1000 * seconds;
        }

        private void DataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            IsReceived = true;
            ReceivedBytes = new byte[ReadBufferSize];
            sPort.Read(ReceivedBytes, 0, ReadBufferSize);
        }
    }
}
