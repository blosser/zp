package ru.ittrans.zp.client.def;

import ru.ittrans.zp.io.ZpManValue;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 30.07.11
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
public class ExcelExporter
{
    public void exportTable(JTable table, File file, JTable tblParent) throws IOException
    {
        TableModel model = table.getModel();
        TableModel modelParent = null;
        if (tblParent != null)
            modelParent = tblParent.getModel();
        FileWriter out = new FileWriter(file);

        if (modelParent != null)
            for (int i = 0; i < modelParent.getColumnCount(); i++)
                out.write(modelParent.getColumnName(i) + "\t");

        for (int i = 0; i < model.getColumnCount(); i++)
            out.write(model.getColumnName(i) + "\t");

        out.write("\n");

        for (int i = 0; i < model.getRowCount(); i++)
        {
            if (modelParent != null)
                for (int j = 0; j < modelParent.getColumnCount(); j++)
                {
                    Object o = modelParent.getValueAt(i, j);
                    if (o instanceof Boolean)
                        out.write((((Boolean) o).booleanValue() ? "Да" : "Нет") + "\t");
                    else if (o instanceof ZpManValue)
                        out.write((((ZpManValue) o).getValue() == null ? "" : ((ZpManValue) o).getValue()) + "\t");
                    else
                        out.write((o == null || o.toString() == null ? "" : o.toString()) + "\t");
                }

            for (int j = 0; j < model.getColumnCount(); j++)
            {
                Object o = model.getValueAt(i, j);
                if (o instanceof Boolean)
                    out.write((((Boolean) o).booleanValue() ? "Да" : "Нет") + "\t");
                else if (o instanceof ZpManValue)
                    out.write((((ZpManValue) o).getValue() == null ? "" : ((ZpManValue) o).getValue()) + "\t");
                else
                    out.write((o == null || o.toString() == null ? "" : o.toString()) + "\t");
            }

            out.write("\n");
        }

        out.close();
        System.out.println("write out to: " + file);
    }
}
