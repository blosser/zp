package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:08:34
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_SHOP")
public class ZpShop extends ZpAbstract
{
    public static final Long BASE_PYATEROCHKA = 1l;
    public static final Long BASE_PEREKRESTOK = 671l;

    private Long id;
    private Long parentId;
    private String name;
    private String code;
    private String address;
    private String phone;
    private String phone2;
    private String comment;
    private Long territClientId;
    private Long tabClientId;
    private String email;
    private String howToGet;
    private Long regionId;
    private String director;
    private Long deliver;
    private Long metroId;
    private Long priority;

    public ZpShop()
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

    @Column(name = "PARENT_ID")
    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return getCode();
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone2()
    {
        return phone2;
    }

    public void setPhone2(String phone2)
    {
        this.phone2 = phone2;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Column(name = "TERRIT_CLIENT_ID")
    public Long getTerritClientId()
    {
        return territClientId;
    }

    public void setTerritClientId(Long territClientId)
    {
        this.territClientId = territClientId;
    }

    @Column(name = "TAB_CLIENT_ID")
    public Long getTabClientId()
    {
        return tabClientId;
    }

    public void setTabClientId(Long tabClientId)
    {
        this.tabClientId = tabClientId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Column(name = "HOW_TO_GET")
    public String getHowToGet()
    {
        return howToGet;
    }

    public void setHowToGet(String howToGet)
    {
        this.howToGet = howToGet;
    }

    @Column(name = "REGION_ID")
    public Long getRegionId()
    {
        return regionId;
    }

    public void setRegionId(Long regionId)
    {
        this.regionId = regionId;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public Long getDeliver()
    {
        return deliver;
    }

    public void setDeliver(Long deliver)
    {
        this.deliver = deliver;
    }

    @Column(name = "METRO_ID")
    public Long getMetroId()
    {
        return metroId;
    }

    public void setMetroId(Long metroId)
    {
        this.metroId = metroId;
    }

    public Long getPriority()
    {
        return priority;
    }

    public void setPriority(Long priority)
    {
        this.priority = priority;
    }
}
