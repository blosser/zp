package ru.ittrans.zp.io;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class ZpClientRolePK implements Serializable
{
    private Long clientId;
    private Long roleId;

    public ZpClientRolePK()
    {
    }

    public ZpClientRolePK(Long clientId, Long roleId)
    {
        this.clientId = clientId;
        this.roleId = roleId;
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

    public boolean equals(Object other)
    {
        if (other instanceof ZpClientRolePK)
        {
            final ZpClientRolePK otherZpClientRolePK = (ZpClientRolePK) other;
            final boolean areEqual =
                    (otherZpClientRolePK.clientId.equals(clientId) && otherZpClientRolePK.roleId.equals(roleId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }
}