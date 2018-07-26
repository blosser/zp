package ru.ittrans.zp.io;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_CLIENT_ROLE")
@IdClass(ZpClientRolePK.class)
public class ZpClientRole extends ZpAbstract
{
    private Long clientId;
    private Long roleId;

    public ZpClientRole()
    {
    }

    @Id
    @Column(name = "CLIENT_ID", nullable = false)
    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    @Id
    @Column(name = "ROLE_ID", nullable = false)
    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Transient
    public Long getId()
    {
        return null;
    }

    @Transient
    public String getName()
    {
        return null;
    }
}

