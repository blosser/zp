package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.10.12
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_METRO")
public class ZpMetro extends ZpAbstract
{
    private Long id;
    private String name;

    public ZpMetro()
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
