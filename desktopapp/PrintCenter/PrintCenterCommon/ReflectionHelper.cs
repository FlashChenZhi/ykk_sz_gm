using System;
using System.Collections.Generic;
using System.Text;
using System.Reflection;

namespace PrintCenterCommon
{
    static public class ReflectionHelper
    {
        static public Type getType(string assemblyPath, string typeFullName)
        {
            try
            {
                return Assembly.LoadFile(assemblyPath).GetType(typeFullName);
            }
            catch
            {
                throw new Exception(string.Format("Failed to get type of class '{0}'.", typeFullName));
            }
        }
    }
}
