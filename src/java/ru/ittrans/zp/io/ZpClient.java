package ru.ittrans.zp.io;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:12:34
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_CLIENT")
public class ZpClient extends ZpAbstract
{
    private Long id;
    private String name;
    private String login;
    private String password;
    private Long admin;
    private ZpSession session;
    private List<ZpClientRole> mrList;
    private Long shopId;

    public ZpClient()
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return login;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public Long getAdmin()
    {
        return admin;
    }

    public void setAdmin(Long admin)
    {
        this.admin = admin;
    }

    @Transient
    public ZpSession getSession()
    {
        return session;
    }

    public void setSession(ZpSession session)
    {
        this.session = session;
    }

    @Transient
    public List<ZpClientRole> getMrList()
    {
        return mrList;
    }

    public void setMrList(List<ZpClientRole> mrList)
    {
        this.mrList = mrList;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }
}
