package ru.ittrans.zp.ejb;

import org.jboss.annotation.ejb.RemoteBinding;
import ru.ittrans.zp.io.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.Timer;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 06.10.2010
 * Time: 13:41:39
 * To change this template use File | Settings | File Templates.
 */

@PermitAll
@Stateless(name = "ZpBeanRemote")
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote(ru.ittrans.zp.io.ZpBeanRemote.class)
@RemoteBinding(jndiBinding = ZpBeanRemote.JNDI_NAME,
        clientBindUrl = ZpBeanRemote.BIND_URL_NAME)
public class ZpBean implements ZpBeanRemote
{
    @PersistenceContext(unitName = "ZpBean")
    private EntityManager em;

    @Resource
    UserTransaction utx;

    @Resource(mappedName = "java:zpDS", type = javax.sql.DataSource.class)
    private static javax.sql.DataSource DB;

    private static final int TIME_OUT = 60 * 1000; // 1 минута

    private static ActionListener purchaseListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            sendPing();
        }
    };

    private static Timer timer =
            new Timer(TIME_OUT, purchaseListener);
    //
//    // торги завершены если идут
//    private static final String SQL_UPDATE_PURCHASE_FD =
//            "update tp_purchase p set p.status_id = (select if(count(*) = 0,4,3) from tp_quote q where p.id = q.purchase_id and q.status_id = 1) where p.status_id = 1 and p.fd <= CURRENT_TIMESTAMP";
//
    // сессия убивается если отвалилась
    private static final String SQL_UPDATE_SESSION =
            "update zp_session set status_id = 3 where status_id = 1 and CURRENT_TIMESTAMP - fd > 10000";

    //
//    private static final String SQL_GET_SUPPLIER_EMAIL =
//            "select distinct m.email\n" +
//                    "  from tp_supplier s, tp_document d, tp_man m\n" +
//                    " where s.id = d.object_id\n" +
//                    "   and d.classname = 'SUPPLIER'\n" +
//                    "   and d.fd is not null\n" +
//                    "   and d.fd <= CURRENT_TIMESTAMP\n" +
//                    "   and s.status_id = 1\n" +
//                    "   and s.id = m.object_id\n" +
//                    "   and m.classname = 'SUPPLIER'";
//
//    private static final String SQL_UPDATE_SUPPLIER =
//            "update tp_supplier s, tp_document d\n" +
//                    "   set s.status_id = 0\n" +
//                    " where s.id = d.object_id\n" +
//                    "   and d.classname = 'SUPPLIER'\n" +
//                    "   and d.fd is not null\n" +
//                    "   and d.fd <= CURRENT_TIMESTAMP\n" +
//                    "   and s.status_id = 1";
//
    public ZpBean()
    {
        super();
        System.out.println("create ZpBean");
        System.out.println("TimeZone " + java.util.TimeZone.getDefault());
        java.util.TimeZone.setDefault(TimeZone.getTimeZone(ZpSession.GMT));
        System.out.println("TimeZone " + java.util.TimeZone.getDefault());
    }

    @PostConstruct
    public void postConstruct()
    {
        System.out.println("postConstruct");
        if (!timer.isRunning())
        {
            timer.start();
        }
        sendPing();
    }

    @PreDestroy
    public void preDestroy()
    {
        System.out.println("preDestroy");

//        if (timer.isRunning())
//            timer.stop();
    }

    private static void sendPing()
    {
        System.out.println("purchase udpate " + new Timestamp(System.currentTimeMillis()));
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            // todo 26102014
//            conn = DB.getConnection();
//            if (conn != null)
//            {
//                conn.setAutoCommit(false);
//
//                ps = conn.prepareStatement(SQL_UPDATE_SESSION);
//                ps.executeUpdate();
//
//                conn.commit();
//            }
        } catch (Exception ex)
        {
            System.err.println(ex);
            ex.printStackTrace();
            try
            {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException exsql)
            {
                System.err.println(ex);
            }
        } finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException exsql)
            {
                System.err.println(exsql);
            }
        }
    }

    public void commit() throws Exception
    {
        try
        {
            utx.commit();
        } catch (Exception ex)
        {
            System.err.println(ex);
            rollback(ex);
        }
    }

    public void rollback(Exception ex) throws Exception
    {
        if (utx.getStatus() == Status.STATUS_ACTIVE)
        {
            utx.rollback();
        }
        throw new Exception(ex);
    }

    private ZpAbstract mergeEntityNoTrans(ZpAbstract o) throws Exception
    {
        if (o != null)
        {
            ZpAbstract newObject = em.merge(o);
//            System.out.println("merge " + newObject.getClass().toString() +
//                    " id=" + newObject.getId());
            return newObject;
        }
        return null;
    }

    public ZpAbstract mergeEntity(ZpAbstract o) throws Exception
    {
        ZpAbstract newObject = null;
        try
        {
            utx.begin();
            newObject = mergeEntityNoTrans(o);
            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }
        return newObject;
    }

    private void removeEntityNoTrans(ZpAbstract o) throws Exception
    {
        if (o != null)
        {
            em.remove(em.merge(o));
            //em.remove(o);
        }
    }

    static final String SQL_GET_SHOP =
            "select * from zp_shop where :shopId = -1 or id = :shopId order by parent_id, cast(code as unsigned)";

    public List<ZpShop> getShop(Long shopId) throws Exception
    {
        List<ZpShop> sList = em.createNativeQuery(SQL_GET_SHOP,
                ZpShop.class).
                setParameter("shopId", shopId).
                getResultList();
        return sList;
    }

    public List<ZpShop> getShop() throws Exception
    {
        return getShop(ZpMan.ALL);
    }

    static final String SQL_GET_MAN =
            "select m.* from zp_man m where (':name1'='' or lower(m.name1) like '%:name1%')\n" +
                    "and (:activeMan = -1 or (:activeMan = 1 and m.active = :activeMan) or (:activeMan = 0 and (m.active <> 1 or m.active is null)))\n" +
                    "and (:manId = -1 or m.id = :manId)\n" +
                    "and (:shopId = -1 or exists (select 1 from zp_man_value mv\n" +
                    "where m.id = mv.man_id and mv.shop_id = :shopId and date_format(mv.sd,'%Y-%m-01') = :period))\n" +
                    "and (:paidId = -1 or exists (select 1 from zp_man_paid mp\n" +
                    "where m.id = mp.man_id and mp.paid_id = :paidId))\n" +
                    "and (:paidD = -1 or exists (select 1 from zp_man_paid mp\n" +
                    "where m.id = mp.man_id and mp.paid_id in (select p1.id from zp_paid p1 where p1.d = (select p2.d from zp_paid p2 where p2.id = :paidD))))\n" +
                    "and (:batchId = -1 or exists (select 1 from zp_man_value mv\n" +
                    "where m.id = mv.man_id and mv.batch_id = :batchId))\n" +
                    "and (:manScore = '' or m.man_score = :manScore)\n" +
                    "and (:card = -1 or ((:card = 1 and m.card = :card) or (:card = 0 and (m.card = :card or m.card is null))))\n" +
                    "and (:secondJob = -1 or ((:secondJob = 1 and m.second_job = :secondJob) or (:secondJob = 0 and (m.second_job = :secondJob or m.second_job is null))))\n" +
                    "and (:dogovorId = -1 or (:dogovorId = 0 and not exists (select 1 from zp_owner_man om where om.man_id = m.id))\n" +
                    "                     or (:dogovorId = 1 and exists (select 1 from zp_owner_man om where om.man_id = m.id group by null having max(fd)>date_add(current_date,interval 14 day)))\n" +
                    "                     or (:dogovorId = 2 and exists (select 1 from zp_owner_man om where om.man_id = m.id group by null having max(fd)<current_date))\n" +
                    "                     or (:dogovorId = 3 and exists (select 1 from zp_owner_man om where om.man_id = m.id group by null having max(fd) between current_date and date_add(current_date,interval 14 day))))\n" +
                    "and (':tabNumber'='' or lower(m.tab_number) like '%:tabNumber%')\n" +
                    "and (':passportNumber'='' or lower(m.passport_number) like '%:passportNumber%')\n" +
                    "and (:territClientId = -1 or exists (select 1 from zp_shop s, zp_man_value mv\n" +
                    "where s.territ_client_id = :territClientId and m.id = mv.man_id and mv.shop_id = s.id and date_format(mv.sd,'%Y-%m-01') = :period))\n" +
                    "and (:tabClientId = -1 or exists (select 1 from zp_shop s, zp_man_value mv\n" +
                    "where s.tab_client_id = :tabClientId and m.id = mv.man_id and mv.shop_id = s.id and date_format(mv.sd,'%Y-%m-01') = :period))\n" +
                    "and (:parentShopId = -1 or exists (select 1 from zp_shop s, zp_man_value mv\n" +
                    "where s.parent_id = :parentShopId and m.id = mv.man_id and mv.shop_id = s.id and date_format(mv.sd,'%Y-%m-01') = :period))\n" +
                    "union\n" +
                    "select m.* from zp_man m where :showHide = 1 and (:activeMan = -1 or m.active = :activeMan)\n" +
                    "and exists (select 1 from zp_man_value mv\n" +
                    "where m.id = mv.man_id and date_format(mv.sd,'%Y-%m-01') = :period and mv.active = 0)\n" +
                    " order by name1";

    static final String SQL_GET_MAN_VALUE =
            "select c.d as sd,if(mv.id is null,-100*rank + c.value_type,mv.id) id,mv.shop_id,:manId man_id,c.value_type,\n"+
                    "if(c.value_type = 12,(select mv2.value from zp_man_value mv2\n" +
                    "                       where date_format(DATE_ADD(:period, INTERVAL -1 MONTH),'%Y-%m-01') = date_format(mv2.sd,'%Y-%m-01')\n" +
                    "                         and mv2.man_id = :manId\n" +
                    "                         and mv2.value_type = 10\n" +
                    "                         and c.value_type = 12),mv.value) value,\n"+
                    "mv.batch_id,if(mv.active is null,1,mv.active) active,mv.session_id\n" +
                    "  from (\n" +
                    "        select rank, d, 1 value_type from zp_cal where period = :period and (:planFact = -1 or :planFact = 1)\n" +
                    "        union all\n" +
                    "        select rank, d, 2 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 3 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 4 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 5 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 6 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 7 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 8 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 9 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 10 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    //"        union all\n" +
                    //"        select rank, d, 11 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 12 value_type from zp_cal where d = date_format(:period,'%Y-%m-01')\n" +
                    "        union all\n" +
                    "        select rank, d, 13 value_type from zp_cal where period = :period and (:planFact = -1 or :planFact = 0)\n" +
                    "       ) c\n" +
                    "  left join zp_man_value mv\n" +
                    "    on c.d = mv.sd\n" +
                    "   and mv.man_id = :manId\n" +
                    "   and c.value_type = mv.value_type\n" +
                    "   and (:batchId = -1 or exists (select 1 from zp_man_value mv1\n" +
                    "                                         where mv1.batch_id = :batchId\n" +
                    "                                           and mv1.man_id = :manId\n" +
                    "                                           and date_format(:period,'%Y-%m-01') = date_format(mv1.sd,'%Y-%m-01')))\n" +
/*
                    "  left join zp_man_value mv2\n" +
                    "    on date_format(DATE_ADD(:period, INTERVAL -1 MONTH),'%Y-%m-01') = date_format(mv2.sd,'%Y-%m-01')\n" +
                    "   and mv2.man_id = :manId\n" +
                    "   and mv2.value_type = 10\n" +
                    "   and c.value_type = 12\n" +
*/
                    " order by case when c.value_type = 1 then 12 \n" +
                    "               when c.value_type = 2 then 9 \n" +
                    "               when c.value_type = 3 then 8 \n" +
                    "               when c.value_type = 4 then 7 \n" +
                    "               when c.value_type = 5 then 5 \n" +
                    "               when c.value_type = 6 then 2 \n" +
                    "               when c.value_type = 7 then 1 \n" +
                    "               when c.value_type = 8 then 10 \n" +
                    "               when c.value_type = 9 then 11 \n" +
                    "               when c.value_type = 10 then 4 \n" +
                    //"               when c.value_type = 11 then 3 \n" +
                    "               when c.value_type = 12 then 6 \n" +
                    "               when c.value_type = 13 then 12 \n" +
                    "          end, c.d, c.value_type desc";

    static final String SQL_GET_MAN_PAID =
            "select mp.* from zp_man_paid mp, zp_paid p where mp.paid_id = p.id and mp.man_id = :manId and p.period = :period";

    public List<ZpMan> getMan(Date period, Long shopId,
                              Long paidId, Long batchId,
                              Long manId, String name1,
                              Long activeMan, Long showHide,
                              String manScore, Long card,
                              Long secondJob, Long dogovorId,
                              String tabNumber, String passportNumber,
                              Long territClientId, Long tabClientId,
                              Long parentShopId, Long planFact,
                              Long paidD) throws Exception
    {
        System.out.print("start getMan period=" + period + " shopId=" + shopId +
                " batchId=" + batchId + " manId=" + manId + " name1=" + name1 +
                " activeMan=" + activeMan + " showHide=" + showHide +
                " manScore=" + manScore + " card=" + card +
                " secondJob=" + secondJob + " dogovorId=" + dogovorId +
                " tabNumber=" + tabNumber + " passportNumber=" + passportNumber +
                " territClientId=" + territClientId + " tabClientId=" + tabClientId +
                " parentShopId=" + parentShopId + " planFact=" + planFact +
                " paidD=" + paidD
        );
        List<ZpMan> mList = em.createNativeQuery(
                SQL_GET_MAN.replaceAll(":name1", name1.toLowerCase()).
                        replaceAll(":tabNumber", tabNumber.toLowerCase()).
                        replaceAll(":passportNumber", passportNumber.toLowerCase()),
                ZpMan.class).
                setParameter("shopId", shopId).
                setParameter("period", period).
                setParameter("paidId", paidId).
                setParameter("manId", manId).
                setParameter("batchId", batchId).
                setParameter("activeMan", activeMan).
                setParameter("showHide", showHide).
                setParameter("manScore", manScore).
                setParameter("card", card).
                setParameter("secondJob", secondJob).
                setParameter("dogovorId", dogovorId).
                setParameter("territClientId", territClientId).
                setParameter("tabClientId", tabClientId).
                setParameter("parentShopId", parentShopId).
                setParameter("paidD", paidD).
                getResultList();
        System.out.print("exec getMan");
        Timestamp tDb = getTimestamp();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tDb.getTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Timestamp t = new Timestamp(c.getTimeInMillis());

        for (ZpMan man : mList)
        {
            if (period != null)
            {
                List<ZpManValue> mvList = em.createNativeQuery(SQL_GET_MAN_VALUE,
                        ZpManValue.class).
                        setParameter("manId", man.getId()).
                        setParameter("period", period).
                        setParameter("batchId", batchId).
                        setParameter("planFact", planFact).
                        getResultList();
                man.setMvList(mvList);

                List<ZpManPaid> mpList = em.createNativeQuery(SQL_GET_MAN_PAID,
                        ZpManPaid.class).
                        setParameter("manId", man.getId()).
                        setParameter("period", period).
                        getResultList();

                if (mpList.size() > 0)
                {
                    ZpManPaid manPaid = mpList.get(0);
                    man.setManPaid(manPaid);
                } else
                {
                    ZpManPaid manPaid = new ZpManPaid();
                    manPaid.setManId(man.getId());
                    man.setManPaid(manPaid);
                }
            }

            List<ZpOwnerMan> omList = getOwnerMan(man.getId());
            man.setOwnerManList(omList);
            Long dogovor = ZpMan.DOGOVOR_NONE;
            // определение активности договора теперь в запросе выше и здесь
            if (omList != null && ZpMan.ONE.equals(man.getpDog()))
                for (ZpOwnerMan om : omList)
                {
                    if (om.getFd() == null ||
                            t.compareTo(new Timestamp(om.getFd().getTime() - 14 * 1000 * 86400l)) < 0)
                    {
                        dogovor = ZpMan.DOGOVOR_ACTIVE;
                    } else if (t.compareTo(om.getFd()) <= 0)
                    {
                        dogovor = ZpMan.DOGOVOR_END_SOON;
                    } else if (t.compareTo(om.getFd()) > 0)
                    {
                        dogovor = ZpMan.DOGOVOR_END;
                    }

                    break;
                }

            man.setDogovor(dogovor);
        }

        System.out.print("end getMan");
        return mList;
    }

    static final String SQL_GET_PAID =
            "select p.* from zp_paid p where p.period = :period order by p.d, p.name";

    public List<ZpPaid> getPaid(Date period) throws Exception
    {
        List<ZpPaid> pList = em.createNativeQuery(SQL_GET_PAID,
                ZpPaid.class).
                setParameter("period", period).
                getResultList();
        return pList;
    }

    static final String SQL_GET_BATCH =
            "select p.* from zp_batch p where p.period = :period order by p.name";

    public List<ZpBatch> getBatch(Date period) throws Exception
    {
        List<ZpBatch> pList = em.createNativeQuery(SQL_GET_BATCH,
                ZpBatch.class).
                setParameter("period", period).
                getResultList();
        return pList;
    }

    static final String SQL_GET_ATTR =
            "select av.* from zp_attr av order by av.id";

    public List<ZpAttr> getAttr() throws Exception
    {
        List<ZpAttr> aList = em.createNativeQuery(SQL_GET_ATTR,
                ZpAttr.class).
                getResultList();
        return aList;
    }

    static final String SQL_GET_ATTR_VALUE =
            "select av.* from zp_attr_value av where (:period is null or :period between av.sd and av.fd) order by av.attr_id, av.sd, av.fd";

    public List<ZpAttrValue> getAttrValue(Date period) throws Exception
    {
        List<ZpAttrValue> avList = em.createNativeQuery(SQL_GET_ATTR_VALUE,
                ZpAttrValue.class).
                setParameter("period", period).
                getResultList();
        return avList;
    }


    public ZpAbstract removeEntity(ZpAbstract o) throws Exception
    {
        try
        {
            System.out.println("remove " +
                    o.getClass().toString() +
                    " id=" + o.getId());
            utx.begin();
            if (o instanceof ZpMan)
            {
                ZpMan m = (ZpMan) o;
                List<ZpOwnerMan> omList = getOwnerMan(m.getId());
                if (omList != null)
                    for (ZpOwnerMan d : omList)
                        removeEntityNoTrans(d);
                removeEntityNoTrans(m);
            } else
            {
                removeEntityNoTrans(o);
            }
            commit();
        } catch (Exception ex)
        {
            rollback(ex);
            throw new Exception(ex);
        }
        return o;
    }

    static final String SQL_GET_CLIENT =
            "select * from zp_client where lower(login)=lower(:login) and lower(password)=lower(:password)";

    public ZpClient isLogin(ZpClient client) throws Exception
    {
        List<ZpClient> tpNewClient = em.createNativeQuery(SQL_GET_CLIENT,
                ZpClient.class).
                setParameter("login", client.getLogin()).
                setParameter("password", client.getPassword()).
                getResultList();

        if (tpNewClient.size() == 1)
        {
            ZpClient dbClient = getClient(tpNewClient.get(0).getId());

            ZpSession session = client.getSession();
            session.setClientId(dbClient.getId());
            session.setStatusId(ZpSession.ST_ACTIVE);
            Timestamp t = getTimestamp();
            session.setSd(t);
            session.setFd(t);
            ZpSession newSession = (ZpSession) mergeEntity(session);
            dbClient.setSession(newSession);
            return dbClient;
        }
        return null;
    }

    public Timestamp getTimestamp()
    {
        Timestamp t = (Timestamp) em.createNativeQuery("select CURRENT_TIMESTAMP from dual").
                getSingleResult();
        return t;
    }


    public ZpSession setSession(ZpSession session) throws Exception
    {
        try
        {
            utx.begin();
            Timestamp t = getTimestamp();
            session.setFd(t);
            mergeEntityNoTrans(session);
            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }

        return session;
    }

    public ZpSession sessionMessage(ZpSession session) throws Exception
    {
        ZpSession dbSession = session.getId() == null ?
                null : em.find(ZpSession.class, session.getId());

        Timestamp d = getTimestamp();
        session.setFd(d);

        if (dbSession != null)
        {
            if (ZpSession.ST_ACTIVE.equals(dbSession.getStatusId()))
            {
            } else
            {
                session.setStatusId(dbSession.getStatusId());
            }
        }

        ZpSession resSession = (ZpSession) mergeEntity(session);

        return resSession;
    }

    private boolean isValueEmpty(String value)
    {
        return value == null || value.trim().length() == 0;
    }

//    private void exportManList(List<String> lmmyyyy,
//                               ZpMan man, String month, String year) throws Exception
//    {
//        Long manId = man.getId();
//        try
//        {
//            if (lmmyyyy != null)
//            {
//                // штраф, дорога, коррекия, ведомость, комментарий
//                String comment = lmmyyyy.get(lmmyyyy.size() - 1);
//                String batchName = lmmyyyy.get(lmmyyyy.size() - 2);
//                String vt4 = lmmyyyy.get(lmmyyyy.size() - 3);
//                String vt3 = lmmyyyy.get(lmmyyyy.size() - 4);
//                String vt2 = lmmyyyy.get(lmmyyyy.size() - 5);
//                Date d = strToDate("01." + month + "." + year);
//
//                for (int i = 0; i < lmmyyyy.size() - 5; i++)
//                {
//                    String value = lmmyyyy.get(i);
//                    if (isValueEmpty(value)) continue;
//                    ZpManValue mv = new ZpManValue();
//                    mv.setManId(manId);
//                    mv.setSd(strToDate((i + 1) + "." + month + "." + year));
//                    mv.setValueType(ZpManValue.VALUE_TYPE1);
//                    mv.setShopCode(value);
//                    mv.setActive(ZpMan.ONE);
//                    mergeEntityNoTrans(mv);
//                }
//
//                if (!isValueEmpty(vt2))
//                {
//                    ZpManValue mv2 = new ZpManValue();
//                    mv2.setManId(manId);
//                    mv2.setSd(d);
//                    mv2.setValueType(ZpManValue.VALUE_TYPE2);
//                    mv2.setValue(new Long(vt2));
//                    mv2.setActive(ZpMan.ONE);
//                    mergeEntityNoTrans(mv2);
//                }
//
//                if (!isValueEmpty(vt3))
//                {
//                    ZpManValue mv3 = new ZpManValue();
//                    mv3.setManId(manId);
//                    mv3.setSd(d);
//                    mv3.setValueType(ZpManValue.VALUE_TYPE3);
//                    mv3.setValue(new Long(vt3));
//                    mv3.setActive(ZpMan.ONE);
//                    mergeEntityNoTrans(mv3);
//                }
//
//                if (!isValueEmpty(vt4))
//                {
//                    ZpManValue mv4 = new ZpManValue();
//                    mv4.setManId(manId);
//                    mv4.setSd(d);
//                    mv4.setValueType(ZpManValue.VALUE_TYPE4);
//                    mv4.setValue(new Long(vt4));
//                    mv4.setActive(ZpMan.ONE);
//                    mergeEntityNoTrans(mv4);
//                }
//
//                if (!isValueEmpty(batchName))
//                {
//                    ZpManValue mv5 = new ZpManValue();
//                    mv5.setManId(manId);
//                    mv5.setSd(d);
//                    mv5.setValueType(ZpManValue.VALUE_TYPE5);
//                    mv5.setBatchName(batchName);
//                    mv5.setComment(comment);
//                    mv5.setActive(ZpMan.ONE);
//                    mergeEntityNoTrans(mv5);
//                }
//            }
//        } catch (Exception ex)
//        {
//            System.out.println("ERROR " + man.getName1());
//            System.out.println(ex);
//            throw new Exception(ex);
//        }
//    }

//    public void exportMans(List<ZpMan> manList) throws Exception
//    {
//        try
//        {
//            System.out.println("exportMans begin");
//            utx.begin();
//            for (ZpMan man : manList)
//            {
//                ZpMan newMan = (ZpMan) mergeEntityNoTrans(man);
//                Long manId = newMan.getId();
//
//                List<ZpOwnerMan> ownerManList = man.getOwnerManList();
//                if (ownerManList != null)
//                    for (ZpOwnerMan ownerMan : ownerManList)
//                        if (ownerMan != null && ownerMan.getOwnerId() != null)
//                        {
//                            ownerMan.setManId(manId);
//                            mergeEntityNoTrans(ownerMan);
//                        }
//
//                exportManList(man.l122010, newMan, "12", "2010");
//                exportManList(man.l012011, newMan, "01", "2011");
//                exportManList(man.l022011, newMan, "02", "2011");
//                exportManList(man.l032011, newMan, "03", "2011");
//                exportManList(man.l042011, newMan, "04", "2011");
//            }
//            commit();
//            System.out.println("exportMans end");
//        } catch (Exception ex)
//        {
//            rollback(ex);
//            System.out.println(ex);
//        }
//    }

    public static String DATE_FORMAT_STR = "dd.MM.yyyy";
    private static SimpleDateFormat DATE_FORMAT = null;
//
//    public static String dateToStr(Date d)
//    {
//        if (d == null) return "";
//        return DATE_FORMAT.format(d);
//    }
//
//    public static Date strToDate(String str) throws Exception
//    {
//        try
//        {
//            return new Date(DATE_FORMAT.parse(str).getTime());
//        } catch (Exception ex)
//        {
//            throw new Exception(ex);
//        }
//    }

    //
//    public static String DATE_TIME_FORMAT_STR = "dd.MM.yyyy HH:mm";
//    private static SimpleDateFormat DATE_TIME_FORMAT =
//            new SimpleDateFormat(DATE_TIME_FORMAT_STR);
//
//    public static String dateTimeToStr(Timestamp d)
//    {
//        if (d == null) return "";
//        return DATE_TIME_FORMAT.format(d);
//    }
//
//    public static String DATE_TIME_SEC_FORMAT_STR = "dd.MM.yyyy HH:mm:ss";
//    private static SimpleDateFormat DATE_TIME_SEC_FORMAT =
//            new SimpleDateFormat(DATE_TIME_SEC_FORMAT_STR);
//
//    public static String dateTimeSecToStr(Timestamp d)
//    {
//        if (d == null) return "";
//        return DATE_TIME_SEC_FORMAT.format(d);
//    }
//
//    public JasperPrint doReport(TpReportParams reportParams) throws Exception
//    {
//        InputStream reportStream = null;
//        //String report = null;
//
//        String reportName = reportParams.getReportName();
//
//        boolean isReport0 = TpReportParams.REPORT_NAMES[0].equals(reportName);
//        boolean isReport1 = TpReportParams.REPORT_NAMES[1].equals(reportName);
//
//        if (isReport0)
//        {
//            reportStream = getClass().getResourceAsStream("/reports/tp_purchase_list.jrxml");
//            //report = "/home/blohin/projects/tp/resource/reports/tp_purchase_list.jrxml";
//        } else if (isReport1)
//        {
//            reportStream = getClass().getResourceAsStream("/reports/tp_purchase.jrxml");
//            //report = "/home/blohin/projects/tp/resource/reports/tp_purchase.jrxml";
//        }
//
//        JasperDesign jasperDesign = null;
//        if (reportStream != null)
//            jasperDesign = JRXmlLoader.load(reportStream);
////        else if (report != null)
////            jasperDesign = JRXmlLoader.load(report);
//
//        System.out.println("compile");
//
//        JasperReport jasperReport = JasperCompileManager.
//                compileReport(jasperDesign);
//
//        Connection conn = DB.getConnection();
//
//        HashMap parameters = new HashMap();
//        if (isReport0)
//        {
//            String sSd = dateToStr(reportParams.getSd());
//            String sFd = dateToStr(reportParams.getFd());
//            String title = reportName +
//                    "\nс " + sSd +
//                    " по " + sFd;
//            parameters.put("P_REPORT_TITLE", title);
//            parameters.put("P_SD", sSd);
//            parameters.put("P_FD", sFd);
//        } else if (isReport1)
//        {
//            String title = reportName;
//            parameters.put("P_REPORT_TITLE", title);
//            parameters.put("P_PURCHASE_ID", reportParams.getPurchaseId());
//        }
//
//        parameters.put("REPORT_CONNECTION", conn);
//        Timestamp t = getTimestamp();
//        parameters.put("P_TIME", t);
//
//        System.out.println("fill");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(
//                jasperReport, parameters, conn);
//        System.out.println("end fill");
//
//        if (conn != null)
//            conn.close();
//
//        return jasperPrint;
//    }
//
    public static String dateToStr(Timestamp d)
    {
        if (d == null) return "";
        if (DATE_FORMAT == null)
        {
            DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
        }
        return DATE_FORMAT.format(d);
    }

    static final String SQL_GET_SESSIONS =
            "select * from zp_session where DATE(sd) >= str_to_date(:sd,'%d.%m.%Y')\n" +
                    "   and DATE(fd) <= str_to_date(:fd,'%d.%m.%Y') order by id desc";

    public List<ZpSession> getSessions(Timestamp sd, Timestamp fd) throws Exception
    {
        String sSd = dateToStr(sd);
        String sFd = dateToStr(fd);

        List<ZpSession> sList = em.createNativeQuery(SQL_GET_SESSIONS,
                ZpSession.class).
                setParameter("sd", sSd).
                setParameter("fd", sFd).
                getResultList();

        for (ZpSession s : sList)
        {
            ZpClient c = getClient(s.getClientId());
            s.setClient(c);
        }

        return sList;
    }

    public void mergeManValue(Date period, ZpMan man) throws Exception
    {
        System.out.println("mergeManValue" +
                " period=" + period +
                " manId=" + man.getId());
        try
        {
            utx.begin();

            ZpManPaid mp = man.getManPaid();
            if (mp.getPaidId() != null)
                mergeEntityNoTrans(mp);
            else if (mp.getId() != null)
                removeEntityNoTrans(mp);

            for (ZpManValue mv : man.getMvList())
            {
                if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()) ||
                        ZpManValue.VALUE_TYPE13.equals(mv.getValueType()))
                {
                    if (mv.getShopId() != null)
                        mergeEntityNoTrans(mv);
                    else if (mv.getId().longValue() > 0)
                        removeEntityNoTrans(mv);
                } else if (ZpManValue.VALUE_TYPE2.equals(mv.getValueType()) ||
                        ZpManValue.VALUE_TYPE3.equals(mv.getValueType()) ||
                        ZpManValue.VALUE_TYPE4.equals(mv.getValueType()) ||
                        ZpManValue.VALUE_TYPE10.equals(mv.getValueType())/* ||
                        ZpManValue.VALUE_TYPE11.equals(mv.getValueType())*/)
                {
                    if (mv.getValue() != null)
                        mergeEntityNoTrans(mv);
                    else if (mv.getId().longValue() > 0)
                        removeEntityNoTrans(mv);
                }
            }
            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }
    }

    static final String SQL_GET_MAN_VALUE_OLD =
            "select mv.* from zp_man_value mv where mv.sd = :sd and mv.man_id = :manId and mv.value_type = 1";

    public void importMan(ZpBatch batch, List<ZpManImport> miList) throws Exception
    {
        System.out.println("importMan size=" + miList.size());

        try
        {
            utx.begin();
            ZpBatch newBatch = (ZpBatch) mergeEntityNoTrans(batch);

            for (ZpManImport mi : miList)
            {
                if (mi.getDbMan() == null) continue;
                ZpMan importMan = mi.getMan();
                ZpMan dbMan = mi.getDbMan();
                System.out.println("import manId=" + dbMan.getId());
                List<ZpManValue> mvList = importMan.getMvList();
                for (ZpManValue mv : mvList)
                    if (mv.getShopId() != null)
                    {
                        List<ZpManValue> mvOldList = em.createNativeQuery(SQL_GET_MAN_VALUE_OLD,
                                ZpManValue.class).
                                setParameter("sd", mv.getSd()).
                                setParameter("manId", dbMan.getId()).
                                getResultList();

                        for (ZpManValue mvOld : mvOldList)
                            removeEntityNoTrans(mvOld);

                        mv.setManId(dbMan.getId());
                        mv.setBatchId(newBatch.getId());
                        mergeEntityNoTrans(mv);
                    }
            }

            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }
    }

    static final String SQL_GET_MAN_VALUE_BATCH =
            "select mv.* from zp_man_value mv where mv.batch_id = :batchId";

    public void removeBatch(ZpBatch batch) throws Exception
    {
        System.out.println("removeBatch id" + batch.getId() +
                " period=" + batch.getPeriod());
        try
        {
            List<ZpManValue> mvList = em.createNativeQuery(SQL_GET_MAN_VALUE_BATCH,
                    ZpManValue.class).
                    setParameter("batchId", batch.getId()).
                    getResultList();
            utx.begin();

            for (ZpManValue mv : mvList)
                removeEntityNoTrans(mv);

            removeEntityNoTrans(batch);

            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }
    }

    static final String SQL_GET_CLIENT_ROLE =
            "select * from zp_client_role where client_id = :clientId order by role_id";

    private List<ZpClientRole> getClientRole(Long clientId) throws Exception
    {
        List<ZpClientRole> mrList = em.createNativeQuery(SQL_GET_CLIENT_ROLE,
                ZpClientRole.class).
                setParameter("clientId", clientId).
                getResultList();
        return mrList;
    }

    static final String SQL_GET_CLIENTS =
            "select * from zp_client where login<>'evild' and login<>'admin' order by name";

    public List<ZpClient> getClient() throws Exception
    {
        List<ZpClient> clientList = em.createNativeQuery(SQL_GET_CLIENTS,
                ZpClient.class).
                getResultList();
        List<ZpClient> cList = new ArrayList<ZpClient>();
        for (ZpClient client : clientList)
        {
            ZpClient c = getClient(client.getId());
            cList.add(c);
        }
        return cList;
    }

    public ZpClient getClient(Long clientId) throws Exception
    {
        ZpClient client = em.find(ZpClient.class, clientId);

        List<ZpClientRole> mrList = getClientRole(client.getId());
        client.setMrList(mrList);

        return client;
    }

    static final String SQL_GET_OWNER =
            "select * from zp_owner order by name";

    public List<ZpOwner> getOwner() throws Exception
    {
        List<ZpOwner> cList = em.createNativeQuery(SQL_GET_OWNER,
                ZpOwner.class).
                getResultList();
        return cList;
    }

    static final String SQL_GET_OWNER_MAN =
            "select * from zp_owner_man where man_id = :manId order by sd desc";

    public List<ZpOwnerMan> getOwnerMan(Long manId) throws Exception
    {
        List<ZpOwnerMan> cList = em.createNativeQuery(SQL_GET_OWNER_MAN,
                ZpOwnerMan.class).
                setParameter("manId", manId).
                getResultList();
        return cList;
    }

    private boolean isMyEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }

    static final String SQL_GET_TAB_NUMBER =
            "select * from zp_man where tab_number = :num and id <> :manId order by id";

    static final String SQL_GET_PASSPORT =
            "select * from zp_man where passport_number = :num and id <> :manId order by id";

    static final String SQL_GET_PHONE =
            "select * from zp_man where phone = :num and id <> :manId order by id";

    static final String SQL_GET_PENSION_NUMBER =
            "select * from zp_man where pension_number = :num and id <> :manId order by id";

    static final String SQL_GET_INN =
            "select * from zp_man where inn = :num and id <> :manId order by id";

    private String checkNumber(String sql, Long manId, ZpMan man, String num)
    {
        List<ZpMan> mList = em.createNativeQuery(sql,
                ZpMan.class).
                setParameter("manId", manId).
                setParameter("num", num).
                getResultList();

        if (!mList.isEmpty())
        {
            String res = "Дубликаты записей [" + num + "] \n" +
                    man.getName1() + " id=" + man.getId() + "\n";
            for (ZpMan m : mList)
                res += m.getName1() + " id=" + m.getId() + "\n";
            return res;
        }
        return null;
    }

    public String mergeMan(ZpMan man) throws Exception
    {
        System.out.println("mergeMan" +
                " manId=" + man.getId());
        try
        {
            Long manId = ZpMan.ALL;
            if (man.getId() != null) manId = man.getId();

            if (!isMyEmpty(man.getTabNumber()))
            {
                String res = checkNumber(SQL_GET_TAB_NUMBER, manId, man, man.getTabNumber());
                if (res != null) return res;
            }

            if (!isMyEmpty(man.getPassportNumber()))
            {
                String res = checkNumber(SQL_GET_PASSPORT, manId, man, man.getPassportNumber());
                if (res != null) return res;
            }

            if (!isMyEmpty(man.getPhone()))
            {
                String res = checkNumber(SQL_GET_PHONE, manId, man, man.getPhone());
                if (res != null) return res;
            }

            if (!isMyEmpty(man.getPensionNumber()))
            {
                String res = checkNumber(SQL_GET_PENSION_NUMBER, manId, man, man.getPensionNumber());
                if (res != null) return res;
            }

            if (!isMyEmpty(man.getInn()))
            {
                String res = checkNumber(SQL_GET_INN, manId, man, man.getInn());
                if (res != null) return res;
            }

            utx.begin();

            ZpMan manNew = (ZpMan) mergeEntityNoTrans(man);
            if (man.getId() != null)
            {
                List<ZpOwnerMan> omList = getOwnerMan(man.getId());
                for (ZpOwnerMan om : omList)
                    removeEntityNoTrans(om);
            }

            if (man.getOwnerManList() != null)
                for (ZpOwnerMan om : man.getOwnerManList())
                {
                    om.setManId(manNew.getId());
                    mergeEntityNoTrans(om);
                }

            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }

        return null;
    }

    static final String SQL_GET_REPORT_SECOND_JOB =
            "select (select code from zp_shop s where s.id = m.shop_id) shop1,\n" +
                    " (select code from zp_shop s where s.id = mv.shop_id) shop2, count(*) cnt,\n" +
                    " m.tab_number2, m.name1, m.p_zdm, m.p_tvp, m.p_sk\n" +
                    "  from zp_man_value mv, zp_man m\n" +
                    " where date_format(mv.sd,'%Y-%m-01') = :period\n" +
                    "   and mv.active = 1\n" +
                    "   and mv.value is not null\n" +
                    "   and mv.value_type = 1\n" +
                    "   and mv.man_id = m.id\n" +
                    "   and m.second_job = 1\n" +
                    " group by m.shop_id, m.name1, mv.man_id, mv.shop_id\n" +
                    " order by m.name1, mv.shop_id";

    public List<List> getReportSecondJob(Date period) throws Exception
    {
        System.out.print("getReportSecondJob period=" + period);

        return getReport(period, SQL_GET_REPORT_SECOND_JOB);
    }

    //ZpMan, ZpManValue, ZpManValue...
    public List<List> getReportVt11(Date period, String manScore) throws Exception
    {
        System.out.print("getReportVt11 period=" + period + " manScore=" + manScore);

        List<ZpMan> mList = getMan(period, ZpMan.ALL, ZpMan.ALL, ZpMan.ALL, ZpMan.ALL,
                "", ZpMan.ONE, ZpMan.ZERO, manScore, ZpMan.ALL, ZpMan.ALL, ZpMan.ALL,
                "", "", ZpMan.ALL, ZpMan.ALL, ZpMan.ALL, ZpMan.ONE, ZpMan.ALL);

        List<List> resList = new ArrayList<List>();

        for (ZpMan man : mList)
        {
            List dList = new ArrayList();
            dList.add(man);
            for (ZpManValue mv : man.getMvList())
                if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()))
                {
                    dList.add(mv);
                }
            resList.add(dList);
        }

        return resList;
    }

    public static final String SQL_GET_REPORT_SMENA =
            "select * from\n" +
                    "(\n" +
                    "select m.name1, if (second_job = 1,'C','') sj,\n" +
                    "       (select count(*)\n" +
                    "          from zp_man_value mv1\n" +
                    "         where :period = date_format(mv1.sd,'%Y-%m-01')\n" +
                    "           and mv1.active = 1\n" +
                    "           and mv1.man_id = m.id\n" +
                    "           and mv1.value_type = 1\n" +
                    "       ) smena_cnt,\n" +
                    "       m.phone,\n" +
                    "       m.birthday,\n" +
                    "       m.tab_number,\n" +
                    "       m.passport_number,\n" +
                    "       m.pension_number,\n" +
                    "       (select code from zp_shop s where s.id = m.shop_id) shop\n" +
                    "  from zp_man m\n" +
                    " where m.active = 1\n" +
                    ") a where smena_cnt > 0 \n" +
                    "    order by name1";

    public List<List> getReportSmena(Date period) throws Exception
    {
        System.out.print("getReportSmena period=" + period);

        return getReport(period, SQL_GET_REPORT_SMENA);
    }

    public static final String SQL_GET_REPORT_SMENA_PAY =
            "select name1,\n" +
                    "       if (second_job = 1,'C','') sj,\n" +
                    "       smena_cnt, stavka,\n" +
                    "       stavka * smena_cnt prepay_sum,\n" +
                    "       pay_sum\n" +
                    "  from\n" +
                    "(\n" +
                    "select a.*,\n" +
                    "       (\n" +
                    "       select max(value) value\n" +
                    "         from zp_attr_value\n" +
                    "        where :period between sd and fd\n" +
                    "        group by attr_id\n" +
                    "        having attr_id = if (second_job = 1, 1, if (smena_cnt < 20,2,3))\n" +
                    "       ) stavka \n" +
                    "  from (\n" +
                    "       select m.name1, m.second_job,\n" +
                    "              (select count(*)\n" +
                    "                 from zp_man_value mv1\n" +
                    "                where mv.sd = date_format(mv1.sd,'%Y-%m-01')\n" +
                    "                  and mv1.active = 1\n" +
                    "                  and mv1.man_id = m.id\n" +
                    "                  and mv1.value_type = 1\n" +
                    "              ) smena_cnt,\n" +
                    "              mv.value pay_sum\n" +
                    "         from zp_man m, zp_man_value mv\n" +
                    "        where m.active = 1\n" +
                    "          and mv.sd = :period\n" +
                    "          and mv.value_type = 10\n" +
                    "          and m.id = mv.man_id\n" +
                    "       ) a\n" +
                    "   ) b\n" +
                    "   order by name1";

    public List<List> getReportSmenaPay(Date period) throws Exception
    {
        System.out.print("getReportSmenaPay period=" + period);

        return getReport(period, SQL_GET_REPORT_SMENA_PAY);
    }

    public static final String SQL_GET_REPORT_PLAN_CNT =
            "select date_format(mv.sd, '%d.%m.%Y') sd,\n" +
                    " (select code from zp_shop s where s.id = mv.shop_id) shop_name,\n" +
                    "       sum(1) cnt_all,\n" +
                    "       sum(IF(mv.active = 1,1,0)) cnt_active\n" +
                    "  from zp_man_value mv\n" +
                    " where mv.value_type = 13\n" +
                    "   and date_format(mv.sd,'%Y-%m-01') = :period\n" +
                    " group by mv.sd, mv.shop_id\n" +
                    " order by mv.sd, mv.shop_id";

    public List<List> getReportPlanCnt(Date period) throws Exception
    {
        System.out.print("getReportPlanCnt period=" + period);

        return getReport(period, SQL_GET_REPORT_PLAN_CNT);
    }

    public List<List> getReport(Date period, String sql) throws Exception
    {
        List mList = em.createNativeQuery(sql).
                setParameter("period", period).
                getResultList();

        List<List> resList = new ArrayList<List>();

        for (int i = 0; i < mList.size(); i++)
        {
            Object[] oList = (Object[]) mList.get(i);
            List dList = new ArrayList();
            for (int j = 0; j < oList.length; j++)
                dList.add(oList[j]);
            resList.add(dList);
        }

        return resList;
    }

    public ZpSession findOneSession(Long sessionId) throws Exception
    {
        ZpSession session = em.find(ZpSession.class, sessionId);

        ZpClient c = getClient(session.getClientId());
        session.setClient(c);

        return session;
    }

    static final String SQL_GET_SHOP_SHIFT =
            "select ss.* from zp_shop_shift ss, zp_shop s where ss.shop_id = s.id and :period = ss.period order by s.parent_id, cast(s.code as unsigned)";

    public List<ZpShopShift> getShopShift(Date period) throws Exception
    {
        List<ZpShopShift> ssList = em.createNativeQuery(SQL_GET_SHOP_SHIFT,
                ZpShopShift.class).
                setParameter("period", period).
                getResultList();
        return ssList;
    }

    static final String SQL_GET_ROLE =
            "select * from zp_role order by id";

    public List<ZpRole> getRole() throws Exception
    {
        List<ZpRole> rList = em.createNativeQuery(SQL_GET_ROLE,
                ZpRole.class).
                getResultList();
        return rList;
    }

    public void mergeClient(ZpClient client) throws Exception
    {
        try
        {
            utx.begin();
            ZpClient newClient = (ZpClient) mergeEntityNoTrans(client);

            List<ZpClientRole> oldMrList = getClientRole(client.getId());
            if (oldMrList != null && !oldMrList.isEmpty())
                for (ZpClientRole oldMr : oldMrList)
                    removeEntityNoTrans(oldMr);

            List<ZpClientRole> mrList = client.getMrList();

            if (mrList != null && !mrList.isEmpty())
                for (ZpClientRole mr : mrList)
                {
                    mr.setClientId(newClient.getId());
                    mergeEntityNoTrans(mr);
                }
            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }
    }

    static final String SQL_GET_CHEK_SHOP =
            "select * from zp_shop where code = :code and id <> :shopId order by id";

    private String checkShop(String sql, Long shopId, ZpShop man, String code)
    {
        List<ZpShop> mList = em.createNativeQuery(sql,
                ZpShop.class).
                setParameter("shopId", shopId).
                setParameter("code", code).
                getResultList();

        if (!mList.isEmpty())
        {
            String res = "Дубликаты записей [" + code + "] \n" +
                    man.getCode() + " id=" + man.getId() + "\n";
            for (ZpShop m : mList)
                res += m.getCode() + " id=" + m.getId() + "\n";
            return res;
        }
        return null;
    }

    public String mergeShop(ZpShop shop) throws Exception
    {
        try
        {
            Long shopId = ZpMan.ALL;
            if (shop.getId() != null) shopId = shop.getId();

            if (!isMyEmpty(shop.getCode()))
            {
                String res = checkShop(SQL_GET_CHEK_SHOP, shopId, shop, shop.getCode());
                if (res != null) return res;
            }

            utx.begin();
            ZpShop newShop = (ZpShop) mergeEntityNoTrans(shop);

            commit();
        } catch (Exception ex)
        {
            rollback(ex);
        }

        return null;
    }

    static final String SQL_GET_PLAN =
            "select mv.* from zp_man_value mv, zp_shop s\n" +
                    " where mv.value_type = 13\n" +
                    "   and :period = date_format(mv.sd,'%Y-%m-01')\n" +
                    "   and mv.shop_id = s.id\n" +
                    "   and (:dayNum = -1 or DATE_FORMAT(mv.sd,'%d') = :dayNum)\n" +
                    "   and (:metroId = -1 or s.metro_id = :metroId)\n" +
                    "   and (:priority = -1 or (:priority = 1 and s.priority = 1) or (:priority = 0 and (s.priority <> 1 or s.priority is null)))\n" +
                    "   and (:active = -1 or mv.active = :active)\n" +
                    "   and (:territClientId = -1 or exists (select 1 from zp_shop s\n" +
                    "   where s.territ_client_id = :territClientId and mv.shop_id = s.id))\n" +
                    " order by mv.sd, IF(ISNULL(mv.man_id),1,0), mv.man_id, mv.shop_id";

    public List<ZpManValue> getPlan(Date period, Long dayNum,
                                    Long metroId, Long priority,
                                    Long active, Long territClientId) throws Exception
    {
        System.out.println("getPlan start");
        List<ZpManValue> mvList = em.createNativeQuery(SQL_GET_PLAN,
                ZpManValue.class).
                setParameter("period", period).
                setParameter("dayNum", dayNum).
                setParameter("metroId", metroId).
                setParameter("priority", priority).
                setParameter("active", active).
                setParameter("territClientId", territClientId).
                getResultList();

        System.out.println("getPlan middle");

        for (ZpManValue mv : mvList)
            if (mv.getManId() != null)
            {
                ZpMan man = em.find(ZpMan.class, mv.getManId());
                mv.setMan(man);
            }
        System.out.println("getPlan end");
        return mvList;
    }

    static final String SQL_GET_REGION =
            "select * from zp_region order by id";

    public List<ZpRegion> getRegion() throws Exception
    {
        List<ZpRegion> rList = em.createNativeQuery(SQL_GET_REGION,
                ZpRegion.class).
                getResultList();
        return rList;
    }

    static final String SQL_GET_METRO =
            "select * from zp_metro order by name";

    public List<ZpMetro> getMetro() throws Exception
    {
        List<ZpMetro> mList = em.createNativeQuery(SQL_GET_METRO,
                ZpMetro.class).
                getResultList();
        return mList;
    }

    static final String SQL_GET_EMPRY_BATCH =
            "select p.* from zp_batch p where p.period = :period and not exist (select 1 from zp_man_value mv where mv.batch_id = p.id) order by p.name";

    public void removeEmptyBatch(Date period) throws Exception
    {
        List<ZpBatch> pList = em.createNativeQuery(SQL_GET_EMPRY_BATCH,
                ZpBatch.class).
                setParameter("period", period).
                getResultList();
        for (ZpBatch batch : pList)
            removeBatch(batch);
    }

}