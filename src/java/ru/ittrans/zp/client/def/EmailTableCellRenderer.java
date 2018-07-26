package ru.ittrans.zp.client.def;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.io.ZpManValue;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 11.07.12
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class EmailTableCellRenderer extends DefaultTableCellRenderer
        implements MouseMotionListener, MouseListener
{
    private static final Cursor DEF_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);
        if (value instanceof DefEmail)
        {
            DefEmail email = (DefEmail) value;

            String str = "<html><u>" + email.getEmail() + "</u></html>";

            l.setForeground(Color.BLUE);
            l.setText(str);
            l.setHorizontalAlignment(SwingConstants.LEFT);
            l.setBorder(BorderFactory.createEmptyBorder());
        }

        return l;
    }

    private boolean isMouseInDoc(MouseEvent e)
    {
        if (e.getSource() instanceof DefTable)
        {
            DefTable table = (DefTable) e.getSource();
            Point pt = e.getPoint();
            //int row = table.rowAtPoint(pt);
            int col = table.columnAtPoint(pt);
            if (table.getColumnClass(col).equals(DefEmail.class))
            {
                return true;
            }
        }
        return false;
    }

    public void mouseClicked(MouseEvent e)
    {
        if (e.getSource() instanceof DefTable)
        {
            DefTable table = (DefTable) e.getSource();
            Point pt = e.getPoint();
            int row = table.rowAtPoint(pt);
            int col = table.columnAtPoint(pt);
            Object object = table.getValueAt(row, col);

            Object obj = table.getSelObj();
            ZpManValue mv = null;
            if (obj instanceof ZpManValue)
                mv = (ZpManValue) obj;

            if (table.getColumnClass(col).equals(DefEmail.class) &&
                    (object instanceof DefEmail))
                try
                {
                    DefEmail email = (DefEmail) object;
                    Desktop desktop;
                    if (Desktop.isDesktopSupported()
                            && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL))
                    {
                        String strSubject = "";
                        String strBody = "";
                        if (mv != null)
                        {
                            String strD = DefCO.dateToStr(mv.getSd());
                            String strMan = mv.getMan() == null ? "" : mv.getMan().toString();
                            ZpShop shop = DicManager.getShopMap().get(mv.getShopId());
                            strSubject = DefCO.convertStringToWeb("Подтверждение на " +
                                    strD + " в магазин " + shop.getCode() + " кассира " + strMan);
                            strBody = DefCO.convertStringToWeb("Нажмите Ответить->Отправить, если вы " +
                                    "подтверждаете выход на работу " + strD +
                                    " в магазин " + shop.getCode() + " кассира " + strMan);
                            strSubject = "?subject=" + strSubject;
                            strBody = "&body=" + strBody;
                        }

                        String strMail = "mailto:" + email.getEmail() +
                                strSubject + strBody;

                        System.out.println(strMail);
                        URI mailto = new URI(strMail);
                        //"mailto:name@yourdomain.ru?subject=Тема письма&body=Тeкст сообщения"
                        desktop.mail(mailto);
                    } else
                    {
                        System.err.println("desktop doesn't support mailto;");
                    }
                } catch (Exception ex)
                {
                    System.err.println(ex);
                }
        }
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
        if (e.getSource() instanceof DefTable)
        {
            DefTable tb = (DefTable) e.getSource();
            tb.setCursor(DEF_CURSOR);
        }
    }

    public void mouseDragged(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
        if (e.getSource() instanceof DefTable)
        {
            DefTable tb = (DefTable) e.getSource();
            if (isMouseInDoc(e))
                tb.setCursor(HAND_CURSOR);
            else
                tb.setCursor(DEF_CURSOR);
        }
    }
}
