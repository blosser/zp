package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:12:44
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_SESSION")
public class ZpSession extends ZpAbstract
{
    public static final Long ST_FINISH = 0l;
    public static final Long ST_ACTIVE = 1l;
    public static final Long ST_BLOCK = 2l;
    public static final Long ST_LOST = 3l;

    public static String GMT = "GMT+3";

    private Long id;
    private Long clientId;
    private Long statusId;
    private Timestamp sd;
    private Timestamp fd;
    private String compIp;
    private String compName;
    private String compUser;
    private String compJava;

    private ZpClient client;

    public ZpSession()
    {
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "CLIENT_ID", nullable = false)
    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    @Column(name = "STATUS_ID", nullable = false)
    public Long getStatusId()
    {
        return statusId;
    }

    public void setStatusId(Long statusId)
    {
        this.statusId = statusId;
    }

    public Timestamp getSd()
    {
        return sd;
    }

    public void setSd(Timestamp sd)
    {
        this.sd = sd;
    }

    public Timestamp getFd()
    {
        return fd;
    }

    public void setFd(Timestamp fd)
    {
        this.fd = fd;
    }

    @Column(name = "COMP_IP", nullable = false)
    public String getCompIp()
    {
        return compIp;
    }

    public void setCompIp(String compIp)
    {
        this.compIp = compIp;
    }

    @Column(name = "COMP_NAME", nullable = false)
    public String getCompName()
    {
        return compName;
    }

    public void setCompName(String compName)
    {
        this.compName = compName;
    }

    @Column(name = "COMP_USER", nullable = false)
    public String getCompUser()
    {
        return compUser;
    }

    public void setCompUser(String compUser)
    {
        this.compUser = compUser;
    }

    @Column(name = "COMP_JAVA", nullable = false)
    public String getCompJava()
    {
        return compJava;
    }

    public void setCompJava(String compJava)
    {
        this.compJava = compJava;
    }

    @Transient
    public String getName()
    {
        return null;
    }

    @Transient
    public ZpClient getClient()
    {
        return client;
    }

    public void setClient(ZpClient client)
    {
        this.client = client;
    }
}
