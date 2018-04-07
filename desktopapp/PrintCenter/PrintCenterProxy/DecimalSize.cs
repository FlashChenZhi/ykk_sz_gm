using System;
using System.Collections.Generic;
using System.Text;

namespace PrintCenterProxy
{
    [Serializable]
    public class DecimalSize
    {
        decimal? height = null;
        decimal? width = null;

        public decimal? Width
        {
            get { return width; }
            set { width = value; }
        }

        public decimal? Height
        {
            get { return height; }
            set { height = value; }
        }   
    }
}
