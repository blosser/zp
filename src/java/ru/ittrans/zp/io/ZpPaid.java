package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:12:54
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_PAID")
public class ZpPaid extends ZpAbstract
{
    private Long id;
    private String name;
    private Date period;
    private Date d;

    public ZpPaid()
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

    public Date getPeriod()
    {
        return period;
    }

    public void setPeriod(Date period)
    {
        this.period = period;
    }

    public Date getD()
    {
        return d;
    }

    public void setD(Date d)
    {
        this.d = d;
    }
}
