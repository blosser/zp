package ru.ittrans.zp.io;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 18.10.2010
 * Time: 19:42:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class ZpAbstract
        implements Serializable, ZpAbstractInterface
{
    @Override
    public String toString()
    {
        return getName();
    }
}
