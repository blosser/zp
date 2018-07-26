package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:13:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_OWNER_MAN")
public class ZpOwnerMan extends ZpAbstract
{
    private Long id;
    private Long ownerId;
    private Long manId;
    private String name;
    private Date sd;
    private Date fd;

    public ZpOwnerMan()
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

    @Column(name = "OWNER_ID")
    public Long getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(Long ownerId)
    {
        this.ownerId = ownerId;
    }

    @Column(name = "MAN_ID")
    public Long getManId()
    {
        return manId;
    }

    public void setManId(Long manId)
    {
        this.manId = manId;
    }

    public Date getSd()
    {
        return sd;
    }

    public void setSd(Date sd)
    {
        this.sd = sd;
    }

    public Date getFd()
    {
        return fd;
    }

    public void setFd(Date fd)
    {
        this.fd = fd;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
