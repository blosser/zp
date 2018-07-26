package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 18.07.12
 * Time: 9:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_REGION")
public class ZpRegion extends ZpAbstract
{
    public static final Long REGION_MOSCOW = 1l;
    public static final Long REGION_MOS_OBL = 2l;

    private Long id;
    private String name;

    public ZpRegion()
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

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
