using System;
using System.Collections.Generic;
using System.Text;
using C1.Win.C1BarCode;
using System.Drawing;
using System.IO;
using System.Drawing.Imaging;

namespace YkkReports
{
    class Barcode
    {
        private C1BarCode barcode = new C1BarCode();
        public bool IsShowText 
        { 
            get
            {
                return barcode.ShowText;
            }
                
            set
            {
                barcode.ShowText = value;
            }
        }

        public int Height
        {
            get
            {
                return barcode.Height;
            }

            set
            {
                barcode.BarHeight = value;
                barcode.Height = value;
            }
        }
        public int Width
        {
            get
            {
                return barcode.Width;
            }

            set
            {
                barcode.BarWide = value;
                barcode.Width = value;
            }
        }

        public Barcode(string value)
        {
            barcode.CodeType = CodeTypeEnum.Code39;
            barcode.Text = value;
            barcode.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Normal;
        }

        public Image ToImage()
        {
            return barcode.Image;
        }

        public Byte[] ToBytes()
        {
            FileStream ms = null;
            try
            {
                //Image image = ToImage();
                //Bitmap bitmap = new Bitmap(image);                
                //ms = new MemoryStream();
                //bitmap.Save(ms, ImageFormat.Bmp);
                //return ms.GetBuffer();

                Image image = ToImage();
                image.Save(@"c:\testtest.bmp");
                ms = new FileStream(@"c:\testtest.bmp", FileMode.Open, FileAccess.Read);
                byte[] photoArray = new byte[(int)ms.Length];
                ms.Read(photoArray, 0, photoArray.Length);
                return photoArray;

                //ImageCodecInfo[] encoderList = ImageCodecInfo.GetImageEncoders();
                //ImageCodecInfo codecInfo = null; ;
                //foreach (ImageCodecInfo ici in encoderList)
                //{
                //    if (ici.MimeType == "image/bmp")
                //    {
                //        codecInfo = ici;
                //        break;
                //    }
                //}
                //EncoderParameters parameters = new EncoderParameters(1);
                //parameters.Param[0] = new EncoderParameter(System.Drawing.Imaging.Encoder.Quality, ((long)90));
                //Image image = ToImage();
                //ms = new MemoryStream();
                //image.Save(ms, codecInfo, parameters);
                //return ms.GetBuffer();
            }
            finally
            {
                if (ms != null)
                {
                    ms.Close();
                }
            }
        }


    }
}
