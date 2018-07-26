package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:12:21
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_MAN_PAID")
public class ZpManPaid extends ZpAbstract
{
    private Long id;
    private Long manId;
    private Long paidId;
    private String comment;

    public ZpManPaid()
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

    @Column(name = "MAN_ID")
    public Long getManId()
    {
        return manId;
    }

    public void setManId(Long manId)
    {
        this.manId = manId;
    }

    @Column(name = "PAID_ID")
    public Long getPaidId()
    {
        return paidId;
    }

    public void setPaidId(Long paidId)
    {
        this.paidId = paidId;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Transient
    public String getName()
    {
        return getComment();
    }
}
