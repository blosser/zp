package ru.ittrans.zp.client.def;


import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:08:12
 * To change this template use File | Settings | File Templates.
 */
public class DefTable extends JTable
{
    public static final Color GREEN_COLOR = new Color(0xCCFFBF);

    private DefTableCellRenderer tcr = new DefTableCellRenderer();

    private JPopupMenu popupMenu = new JPopupMenu();

    private DefTable tblParent;

    public DefTable(DefTableModel dm, boolean isAutoResizeOff)
    {
        this(dm, isAutoResizeOff, null);
    }

    public DefTable(DefTableModel dm, boolean isAutoResizeOff, DefTable tblParent)
    {
        super(dm);
        this.tblParent = tblParent;
        //setAutoCreateRowSorter(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (isAutoResizeOff)
            setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);

        setDefaultRenderer(ZpShop.class, tcr);
        setDefaultRenderer(ZpManValue.class, tcr);
        setDefaultRenderer(ZpManPaid.class, tcr);
        setDefaultRenderer(ZpPaid.class, tcr);
        setDefaultRenderer(ZpMan.class, tcr);
        setDefaultRenderer(String.class, tcr);
        setDefaultRenderer(Date.class, tcr);
        setDefaultRenderer(Timestamp.class, tcr);
        setDefaultRenderer(DefTimestamp.class, tcr);
        setDefaultRenderer(DefEmail.class, new EmailTableCellRenderer());

        putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        setRowHeight(20);
        addKeyListener(new DefTableKeyAdapter());

        if (Connect.isRole(ZpRole.ROLE_IMPORT_EXCEL))
        {
            if (tblParent != null)
            {
                addItemMenu(null);
                popupMenu.addSeparator();
            }

            addItemMenu(tblParent);

            MouseListener popupListener = new PopupListener();
            addMouseListener(popupListener);
        }
    }

    public DefTable getTblParent()
    {
        return tblParent;
    }

    private void addItemMenu(final DefTable tblParent)
    {
        String str = "Импорт в Excel" + (tblParent != null ? " 2" : "");
        JMenuItem miExcel = new JMenuItem(str);
        miExcel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                miExcelPressed(tblParent);
            }
        });
        miExcel.setIcon(DefCO.loadIcon(DefCO.PNG_EXCEL));
        popupMenu.add(miExcel);
    }

    private void miExcelPressed(DefTable tblParent)
    {
        try
        {
            ExcelExporter exp = new ExcelExporter();
            String fullpath = DefCO.JAVA_TMP_DIR + DefCO.SPLIT +
                    "zp" + System.currentTimeMillis() + ".xls";

            File f = new File(fullpath);
            exp.exportTable(this, f, tblParent);

            Desktop.getDesktop().open(f);
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean isSelected()
    {
        int row = getSelectedRow();
        return row != -1;
    }

    public Object getSelObj()
    {
        int viewRow = getSelectedRow();
        if (viewRow == -1) return null;
        int row = convertRowIndexToModel(viewRow);
        return (row == -1) ? null : ((DefTableModel) getModel()).getObj(row);
    }

    public static Color cEditable =
            (Color) UIManager.get("TextField.background");
    public static Color cNotEditable =
            (Color) UIManager.get("ComboBox.disabledBackground");

    public void setMyEditable(boolean editable)
    {
        setEnabled(editable);
        setBackground(editable ? cEditable : cNotEditable);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
    {
        Component c = super.prepareRenderer(renderer, row, column);
        if (c instanceof JComponent)
        {
            JComponent jc = (JComponent) c;
            Object obj = getValueAt(row, column);
            if (obj != null)
                jc.setToolTipText(obj.toString());
        }
        return c;
    }

    protected JTableHeader createDefaultTableHeader()
    {
        return new JTableHeader(columnModel)
        {
            public String getToolTipText(MouseEvent e)
            {
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                TableColumn tc = columnModel.getColumn(index);
                return tc.getHeaderValue().toString();
            }
        };
    }

    public void setColumntWidth()
    {
        int[] columnWidth = ((DefTableModel) getModel()).getColumntWidth();
        if (columnWidth != null)
        {
            for (int i = 0; i < getColumnCount(); i++)
            {
                TableColumn tc = getColumnModel().getColumn(i);
                //tc.setResizable(true);
                if (i < columnWidth.length)
                {
                    int width = columnWidth[i];
                    tc.setPreferredWidth(width);
                    //tc.setMinWidth(width);
                    //tc.setMaxWidth(width);
                }
            }
        }
    }

    public Point getCurrCell()
    {
        int row = getSelectedRow();
        int col = getSelectedColumn();
        return new Point(row, col);
    }

    public Point getNextCell()
    {
        Point currCell = getCurrCell();
//        int row = getSelectedRow();
//        int col = getSelectedColumn();
        //if ((currCell.x != -1) && (currCell.y != -1))
        Point nextCell = new Point(currCell);

        TableModel tm = getModel();
        int colCount = tm.getColumnCount();
        int rowCount = tm.getRowCount();

        boolean nextExist = false;
        for (int j = currCell.y + 1; j < colCount; j++)
        {
            if (tm.isCellEditable(currCell.x, j))
            {
                nextCell.y = j;
                nextExist = true;
                break;
            }
        }
        if (!nextExist)
        {
            for (int i = currCell.x + 1; i < rowCount; i++)
            {
                for (int j = 0; j < colCount; j++)
                {
                    if (tm.isCellEditable(i, j))
                    {
                        nextCell.x = i;
                        nextCell.y = j;
                        nextExist = true;
                        break;
                    }
                }
                if (nextExist)
                {
                    break;
                }
            }
        }
        if (!nextExist)
        {
            for (int i = 0; i <= currCell.x; i++)
            {
                for (int j = 0; j < colCount; j++)
                {
                    if (tm.isCellEditable(i, j))
                    {
                        nextCell.x = i;
                        nextCell.y = j;
                        nextExist = true;
                        break;
                    }
                }
                if (nextExist)
                {
                    break;
                }
            }
        }

        return nextCell;
    }

    class PopupListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            showPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            showPopup(e);
        }

        private void showPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}


