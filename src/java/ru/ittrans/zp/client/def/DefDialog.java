package ru.ittrans.zp.client.def;

import ru.ittrans.zp.io.ZpAbstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:33:54
 * To change this template use File | Settings | File Templates.
 */
public class DefDialog<K> extends JDialog
        implements ButtonSaveInterface
{
    private JPanel pnlMain = new JPanel();
    public boolean modalResult = false;
    private K k;
    private String title;

    public DefDialog(String title)
    {
        super();
        setTitle(title);
        this.title = title;
    }

    public DefDialog(JFrame f, String title)
    {
        super(f, title, true);
        this.title = title;
    }

    public DefDialog(JDialog f, String title)
    {
        super(f, title, true);
        this.title = title;
    }

    protected void jbInit()
    {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlMain, BorderLayout.CENTER);

        setIconImage(DefCO.loadIcon(DefCO.PNG_BAG).getImage());
    }

    protected JPanel getPanel()
    {
        return pnlMain;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
        {
            modalResult = true;
            setVisible(false);
        }
    }

    public void btnCancel_actionPerformed(ActionEvent e)
    {
        modalResult = false;
        setVisible(false);
    }

    protected boolean isNextEnabled()
    {
        return true;
    }

    protected void setObject(K k)
    {
        modalResult = false;
        this.k = k;
        if (k instanceof ZpAbstract)
        {
            Long id = ((ZpAbstract) k).getId();
            String op = "";
            if (id == null)
                op = DefCO.MSG_ADD;
            else
                op = DefCO.MSG_UPD;
            setTitle(op + " " + title);
        }
    }

    protected K getObject()
    {
        return k;
    }
}
