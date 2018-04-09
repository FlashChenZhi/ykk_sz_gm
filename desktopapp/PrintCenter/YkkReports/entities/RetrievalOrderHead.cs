using System;
using System.Collections.Generic;
using System.Text;

namespace YkkReports.entities
{
    public class RetrievalOrderHead
    {
        private string bucketNo = string.Empty;

        public string BucketNo
        {
            get { return bucketNo; }
            set { bucketNo = value; }
        }

        private string userId = string.Empty;

        public string UserId
        {
            get { return userId; }
            set { userId = value; }
        }

    }
}
