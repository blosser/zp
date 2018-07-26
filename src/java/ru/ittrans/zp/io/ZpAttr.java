package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 26.11.2010
 * Time: 12:36:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_ATTR")
public class ZpAttr extends ZpAbstract
{
    public static final Long ATTR_SECOND_JOB = 1l;
    public static final Long ATTR_SMENA = 2l;
    public static final Long ATTR_SMENA_20 = 3l;
    public static final Long ATTR_SMENA_25 = 4l;
    public static final Long ATTR_SMENA_MOBILE = 5l;
    public static final Long ATTR_SECOND_JOB_MOSCOW = 6l;
    public static final Long ATTR_SMENA_15 = 7l;
    public static final Long ATTR_SMENA_MOBILE_DO15 = 8l;
    public static final Long ATTR_SMENA_MOBILE_C16 = 9l;
    public static final Long ATTR_ZLO = 10l;

    private Long id;
    private String name;

    public ZpAttr()
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

