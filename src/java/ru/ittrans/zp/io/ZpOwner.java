package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:13:15
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_OWNER")
public class ZpOwner extends ZpAbstract
{
    public static final String OWNER_PARTNER = "Партнер";
    public static final String OWNER_RAIT = "Райт Плэйс Инвест";

    public static final Long OWNER_PARTNER_CODE = 1l;
    public static final Long OWNER_RAIT_CODE = 2l;
    private Long id;
    private String name;

    public ZpOwner()
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
