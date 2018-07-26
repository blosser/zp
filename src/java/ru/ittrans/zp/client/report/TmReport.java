package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.def.DefTableModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.10.11
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class TmReport extends DefTableModel<List>
{
    public static final String[] COLUMN_SECOND_JOB =
            new String[]{"����������� �������", "��� ���������", "�����", "���. �����2", "���", "���", "���", "��"};

    public static final String[] COLUMN_SMENA =
            new String[]{"���", "C�����������", "�����", "�������", "���� ��������", "������.",
                    "�������", "����������", "����. �������"};

    public static final String[] COLUMN_SMENA_PAY =
            new String[]{"���", "C�����������", "���������� ����",
                    "������", "����������� ����� �������", "����������� ����� �������"};

    public static final String[] COLUMN_PLAN_CNT =
            new String[]{"����", "�������", "���������� ������", "�����������"};

    private String[] colNames;

    public TmReport(String[] colNames)
    {
        super();
        this.colNames = colNames;
    }

    public String[] getColumnNames()
    {
        return colNames;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        List dList = getObj(rowIndex);
        Object obj = dList.get(columnIndex);
        return obj;
    }
}
