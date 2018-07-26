package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_ROLE")
public class ZpRole extends ZpAbstract
{
    public static final Long ROLE_IMPORT = 1l;
    public static final Long ROLE_CHANGE_SMEN = 2l;
    public static final Long ROLE_REPORT = 3l;
    public static final Long ROLE_LIB_MAN = 4l;
    public static final Long ROLE_LIB_PAID = 5l;
    public static final Long ROLE_LIB_SHOP = 6l;
    public static final Long ROLE_LIB_BATCH = 7l;
    public static final Long ROLE_LIB_ATTR_VALUE = 8l;
    public static final Long ROLE_LIB_CLIENT = 9l;
    public static final Long ROLE_LIB_OWNER = 10l;
    public static final Long ROLE_LIB_SHOP_SHIFT = 11l;
    public static final Long ROLE_IMPORT_EXCEL = 12l;
    public static final Long ROLE_CHANGE_PLAN = 13l;
    public static final Long ROLE_NOT_FIO_ONLY = 14l;

    private Long id;
    private String name;

    public ZpRole()
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
}

