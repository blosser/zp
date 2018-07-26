package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:12:03
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_BATCH")
public class ZpBatch extends ZpAbstract
{
    private Long id;
    private String name;
    private Date period;

    public ZpBatch()
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
}
