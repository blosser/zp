package ru.ittrans.zp.io;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 06.10.2010
 * Time: 13:43:56
 * To change this template use File | Settings | File Templates.
 */

@javax.ejb.Remote
public interface ZpBeanRemote
{
    public static final String JNDI_NAME = "ZpBeanRemote/remote";

    public static final String BIND_URL_NAME = "http://0.0.0.0:8080/servlet-invoker/ServerInvokerServlet";

    ZpAbstract mergeEntity(ZpAbstract o) throws Exception;

    ZpAbstract removeEntity(ZpAbstract o) throws Exception;

//    void exportMans(List<ZpMan> manList) throws Exception;

    List<ZpShop> getShop() throws Exception;

    List<ZpShop> getShop(Long shopId) throws Exception;

    List<ZpMan> getMan(Date period, Long shopId,
                       Long paidId, Long batchId,
                       Long manId, String name1,
                       Long activeMan, Long showHide,
                       String manScore, Long card,
                       Long secondJob, Long dogovorId,
                       String tabNumber, String passportNumber,
                       Long territClientId, Long tabClientId,
                       Long parentShopId, Long planFact,
                       Long paidD) throws Exception;

    List<ZpPaid> getPaid(Date period) throws Exception;

    List<ZpBatch> getBatch(Date period) throws Exception;

    List<ZpAttr> getAttr() throws Exception;

    List<ZpAttrValue> getAttrValue(Date period) throws Exception;

    ZpClient isLogin(ZpClient client) throws Exception;

    ZpSession setSession(ZpSession session) throws Exception;

    ZpSession sessionMessage(ZpSession session) throws Exception;

    List<ZpSession> getSessions(Timestamp sd, Timestamp fd) throws Exception;

    void mergeManValue(Date period, ZpMan man) throws Exception;

    void importMan(ZpBatch batch, List<ZpManImport> miList) throws Exception;

    void removeBatch(ZpBatch batch) throws Exception;

    List<ZpClient> getClient() throws Exception;

    List<ZpOwner> getOwner() throws Exception;

    String mergeMan(ZpMan man) throws Exception;

    List<List> getReportSecondJob(Date period) throws Exception;

    List<List> getReportVt11(Date period, String manScore) throws Exception;

    List<List> getReportSmena(Date period) throws Exception;

    List<List> getReportSmenaPay(Date period) throws Exception;

    List<List> getReportPlanCnt(Date period) throws Exception;

    ZpSession findOneSession(Long sessionId) throws Exception;

    List<ZpShopShift> getShopShift(Date period) throws Exception;

    List<ZpRole> getRole() throws Exception;

    void mergeClient(ZpClient client) throws Exception;

    String mergeShop(ZpShop shop) throws Exception;

    List<ZpManValue> getPlan(Date period, Long dayNum,
                             Long metroId, Long priority,
                             Long active, Long territClientId) throws Exception;

    List<ZpRegion> getRegion() throws Exception;

    List<ZpMetro> getMetro() throws Exception;

    void removeEmptyBatch(Date period) throws Exception;
}