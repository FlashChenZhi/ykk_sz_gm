package com.worgsoft.ykk.persistence;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 15:46:10
 * To change this template use File | Settings | File Templates.
 */
public class SessionFactory
{
    public static Session getSession()  throws Exception
    {
        return new Session(ConnectionManager.getConnection());
    }
}
