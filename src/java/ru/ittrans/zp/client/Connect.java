package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefDialog;
import ru.ittrans.zp.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 06.10.2010
 * Time: 14:52:19
 * To change this template use File | Settings | File Templates.
 */
public class Connect
{
    private static ZpBeanRemote zpRemote;
    private static ZpClient client;
    private static final String MES_NO_CONNECTION = "Ќе удалось установить соединение.";

    public static ZpClient getClient()
    {
        return client;
    }

    public static Long getParentShopId()
    {
        return getClient().getShopId();
    }

    public static Long getSessionId()
    {
        return client.getSession().getId();
    }

    private static Context getInitialContext() throws NamingException
    {
        Properties properties = new Properties();
        if (System.getProperty(Context.PROVIDER_URL) == null)
        {
            properties.put(Context.PROVIDER_URL,
                    "http://zp1981.dyndns.org:8080/invoker/JNDIFactory");
        }

        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.HttpNamingContextFactory");
        properties.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming");

        return new InitialContext(properties);
    }

    public static void tprInit() throws Exception
    {
        Context context = getInitialContext();
        Object remoteRef = context.lookup(ZpBeanRemote.JNDI_NAME);
        context.close();
        if (remoteRef instanceof ZpBeanRemote)
            zpRemote = (ZpBeanRemote) remoteRef;
        else
            throw new Exception(MES_NO_CONNECTION);
    }

    public static ZpBeanRemote getZpRemote() throws Exception
    {
        if (zpRemote == null)
        {
            throw new Exception(MES_NO_CONNECTION);
        }
        return zpRemote;
    }

    public static String getCompName() throws Exception
    {
        return InetAddress.getLocalHost().getCanonicalHostName();
    }

    private static ZpSession getSession() throws Exception
    {
        ZpSession session = new ZpSession();
        InetAddress ia = InetAddress.getLocalHost();
        String compName = ia.getCanonicalHostName();
        String compIp = ia.getHostAddress();
        String compUser = System.getProperty("user.name");
        String compVm = System.getProperty("java.version");
        session.setCompName(compName);
        session.setCompIp(compIp);
        session.setCompUser(compUser);
        session.setCompJava(compVm);
        return session;
    }

    public static boolean isLogin(DefDialog df,
                                  String login,
                                  String password)
    {
        try
        {
            ZpClient candidate = new ZpClient();
            candidate.setLogin(login);
            candidate.setPassword(password);
            candidate.setSession(getSession());

            client = getZpRemote().isLogin(candidate);
            if (client == null)
            {
                DefCO.showInf(df, "Ќеверное им€ пользовател€ или пароль.");
                return false;
            }


            initPing();
            return true;

        } catch (Exception ex)
        {
            DefCO.showError(df, ex);
        }
        return false;
    }

    private static final int TIME_OUT = (60 * 1000); // 60 сек
    private static Timer timer = null;

    private static void initPing()
    {
        timer = new Timer(TIME_OUT, new Ping());
        timer.start();
    }

    private static class Ping implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sendPing();
        }
    }

    private static void sendPing()
    {
        try
        {
            ZpSession currSession = client.getSession();
            ZpSession freshSession = getZpRemote().sessionMessage(currSession);
            client.setSession(freshSession);
            if (!(ZpSession.ST_ACTIVE.equals(freshSession.getStatusId())))
            {
                timer.stop();
                disconnect();
            }
        } catch (Exception ex)
        {
            System.err.println(ex);
        }
    }

    final static void disconnect() throws Exception
    {
        System.out.println("disconnect");
        zpRemote = null;
    }

    public static boolean isAdmin()
    {
        return "admin".equals(client.getLogin().toLowerCase());
    }

    public static boolean isEvild()
    {
        return "evild".equals(client.getLogin().toLowerCase());
    }

    public static boolean isInna()
    {
        return "inna".equals(client.getLogin().toLowerCase());
    }

    public static final boolean isRole(Long roleType)
    {
        ZpClient client = getClient();

        if (ZpMan.ONE.equals(client.getAdmin())) return true;

        if (client.getMrList() != null && roleType != null)
            for (ZpClientRole mr : client.getMrList())
                if (roleType.equals(mr.getRoleId()))
                    return true;

        return false;
    }
}
