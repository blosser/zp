package ru.ittrans.zp.client.def;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 11.07.12
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class DefEmail
{
    private String email;

    public DefEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    public String toString()
    {
        return getEmail();
    }
}
