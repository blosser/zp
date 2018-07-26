package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.client.report.PrintPopupMenu;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class ManDialog extends DefDialog
        implements ButtonEditInterface, ButtonSaveInterface
{
/*
Право "Не только ФИО"
Добавление метро, с простановкой где возможно
Изменение плана по письму
У каждого магазина св-во приоритет (да\нет). Где период фильтр по приоритетам.
Фильтр по метро.
Отображается не только период(месяц) но и конкретная дата(фильтр рядом с периодом) а то много фамилий выскакивает при работе за целый месяц.
Отправка кассира не нужна.
В основной базе, когда я смотрю план дожны считаться смены у кассиров. Показываются только кассиры взявшие заявку, а не весь список
Новый отчет по плану за месяц: номер магазина, кол-во заявок, кол-во выполненных заявок.(если можно с раскладкой по дням)

Добавление договоров Перспектива
Исправление договоров Перспектива

Проверка загрузки перекрестков
Возможность манипулирования сессиями
План — фильтр чья территория
В главном окне ширина поле ведомость * 2
Возможность скрыть названия магазина в главном окне, с подсветкой названия при выделении
Импорт: если «С» или «Н» проверка фактического магазина
Импорт: добавить просмотр фактических смен
Поле фактический магазин перед ФИО
Импорт
Оптимизация импорта, углубленный поиск по фактическому магазину
Добавление поля "Соответствие"
Добавление счетчика при поиске соответствия

Новые расценки совместители остаюттся также,
а все остальные до 21 смены 1250, с 21 - 1350
2) нужно ввести признак у кассира мобильный и спец мобильный.
3) У мобильных до 21 смены 1300, с 21 - 1350
4) У спец мобильных 1550 за смену.










после 12 ноября
импорт табельного номера 2 из файла и изменене отчета по совместителям
в плане ибрал емаил, добавил примечение
в плане не требуется ввод фио для подтверждения магазина
автоматическое удаление пустых пачек

* */

    private ManEditDialog manEditDialog = null;

    private static final String[] DOGOVOR_LIST = new String[]{"Все", "Нет", "Активен", "Окончен", "Истекает"};

    public static final String[] SECOND_JOB_LIST = new String[]{"Все", "Да", "Нет"};

    private static final String[] ACTIVE_LIST = SECOND_JOB_LIST;

    private JLabel lbDogovor = new JLabel(DefCO.MSG_OWNER_MAN);
    private DefComboBox<String> cbDogovor = new DefComboBox<String>();

    private JLabel lbSecondJob = new JLabel(ManEditDialog.COL_NAMES_MAN[0]);
    private DefComboBox<String> cbSecondJob = new DefComboBox<String>();

    private JLabel lbActive = new JLabel(ManEditDialog.COL_NAMES_MAN[12]);
    private DefComboBox<String> cbActive = new DefComboBox<String>();

    private JLabel lbFio = new JLabel(ManEditDialog.COL_NAMES_MAN[2]);
    private DefTextField edtFio = new DefTextField();

    private JLabel lbTabNumber = new JLabel(ManEditDialog.COL_NAMES_MAN[5]);
    private DefTextField edtTabNumber = new DefTextField();

    private JLabel lbPassportNumber = new JLabel(ManEditDialog.COL_NAMES_MAN[6]);
    private DefTextField edtPassportNumber = new DefTextField();

    private DefButton btnSearch = new DefButton("Поиск");

    public static final String MSG_ALL_CNT = "Всего записей: ";
    private JLabel lbCnt = new JLabel(MSG_ALL_CNT);

    private DefTable tblMan =
            new DefTable(new TmMan(Connect.isRole(ZpRole.ROLE_NOT_FIO_ONLY)), true);
    private JScrollPane spMan = new JScrollPane(tblMan);

    private ButtonEditPanel bep = new ButtonEditPanel(this);
    private ButtonSavePanel bsp = null;
    private WhoChangeJButton whoChangeJButton = new WhoChangeJButton();
    private ManPrintJButton btnManPrint = new ManPrintJButton();
    private MainFrame mf;

    private PrintPopupMenu printPopupMenu = new PrintPopupMenu();

    private ZpMan man;
    private boolean isSelected;

    public ManDialog(MainFrame mf)
    {
        this(mf, false);
    }

    public ManDialog(MainFrame mf, boolean isSelected)
    {
        super(mf, DefCO.MSG_MAN);
        this.mf = mf;
        this.isSelected = isSelected;
        if (isSelected)
            bsp = new ButtonSavePanel(this, ButtonSavePanel.BSP_TYPE.BSP_SELECT);
        manEditDialog = new ManEditDialog(this);
        setData();
        jbInit();
    }

    private void setData()
    {
        List<String> dList = new ArrayList<String>();
        for (int i = 0; i < DOGOVOR_LIST.length; i++)
            dList.add(DOGOVOR_LIST[i]);
        DefComboBox.cbAddItem(cbDogovor, dList);

        List<String> sList = new ArrayList<String>();
        for (int i = 0; i < SECOND_JOB_LIST.length; i++)
            sList.add(SECOND_JOB_LIST[i]);
        DefComboBox.cbAddItem(cbSecondJob, sList);

        List<String> aList = new ArrayList<String>();
        for (int i = 0; i < ACTIVE_LIST.length; i++)
            aList.add(ACTIVE_LIST[i]);
        DefComboBox.cbAddItem(cbActive, aList);
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlNorth1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNorth1.add(lbDogovor);
        pnlNorth1.add(cbDogovor);
        pnlNorth1.add(lbSecondJob);
        pnlNorth1.add(cbSecondJob);
        pnlNorth1.add(lbActive);
        pnlNorth1.add(cbActive);
        pnlNorth1.add(lbFio);
        pnlNorth1.add(edtFio);
        pnlNorth1.add(btnSearch);

        JPanel pnlNorth2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNorth2.add(lbTabNumber);
        pnlNorth2.add(edtTabNumber);
        pnlNorth2.add(lbPassportNumber);
        pnlNorth2.add(edtPassportNumber);
        pnlNorth2.add(lbCnt);

        pnlMain.add(pnlNorth1, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS, 0, 0));
        pnlMain.add(pnlNorth2, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS, 0, 0));
        pnlMain.add(spMan, new GridBagConstraints(0, 2, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 3, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        if (bsp != null)
            pnlMain.add(bsp, new GridBagConstraints(0, 4, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    DefCO.INSETS0, 0, 0));

        bep.addButton(btnManPrint, 0);
        bep.addButton(whoChangeJButton, 0);

        btnManPrint.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (tblMan.isSelected())
                {
                    ZpMan man = (ZpMan) tblMan.getSelObj();
                    printPopupMenu.setMan(man);
                    printPopupMenu.show(btnManPrint, 0, btnManPrint.getHeight());
                } else DefCO.showSelected(mf);
            }
        });

        whoChangeJButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (tblMan.isSelected())
                {
                    ZpMan man = (ZpMan) tblMan.getSelObj();
                    mf.findOneSession(man.getSessionId());
                } else DefCO.showSelected(mf);
            }
        });

        tblMan.setDefaultRenderer(ManDogovor.class, new ManTableCellRenderer());

        edtFio.setPreferredSize(new Dimension(200, 20));
        edtTabNumber.setPreferredSize(new Dimension(100, 20));
        edtPassportNumber.setPreferredSize(new Dimension(100, 20));

        btnSearch.setIcon(DefCO.loadIcon(DefCO.PNG_EXEC));
        btnSearch.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                getMans();
            }
        });

        getRootPane().setDefaultButton(btnSearch);

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpMan man = new ZpMan();
        man.setActive(ZpMan.ONE);
        manEditDialog.showMan(man);
        if (manEditDialog.modalResult)
            getMans();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblMan.isSelected())
        {
            ZpMan man = (ZpMan) tblMan.getSelObj();
            manEditDialog.showMan(man);
            if (manEditDialog.modalResult)
                getMans();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblMan.isSelected())
        {
            try
            {
                ZpMan man = (ZpMan) tblMan.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(man);
                    getMans();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getMans()
    {
        try
        {
            String name1 = edtFio.getText();

            int cbActiveIndex = cbActive.getSelectedIndex();
            Long activeMan = ZpMan.ALL;
            if (cbActiveIndex == 1) activeMan = ZpMan.ONE;
            else if (cbActiveIndex == 2) activeMan = ZpMan.ZERO;

            Long secondJob = ZpMan.ALL;
            int cbSecondJobIndex = cbSecondJob.getSelectedIndex();
            if (cbSecondJobIndex == 1) secondJob = ZpMan.ONE;
            else if (cbSecondJobIndex == 2) secondJob = ZpMan.ZERO;

            Long dogovorId = ZpMan.ALL;
            int cbDogovorIndex = cbDogovor.getSelectedIndex();
            if (cbDogovorIndex == 1) dogovorId = ZpMan.DOGOVOR_NONE;
            else if (cbDogovorIndex == 2) dogovorId = ZpMan.DOGOVOR_ACTIVE;
            else if (cbDogovorIndex == 3) dogovorId = ZpMan.DOGOVOR_END;
            else if (cbDogovorIndex == 4) dogovorId = ZpMan.DOGOVOR_END_SOON;

            String tabNumber = edtTabNumber.getText();
            String passportNumber = edtPassportNumber.getText();

            List<ZpMan> mList = Connect.getZpRemote().getMan(null, ZpMan.ALL,
                    ZpMan.ALL, ZpMan.ALL, ZpMan.ALL, name1, activeMan,
                    ZpMan.ZERO, "", ZpMan.ALL,
                    secondJob, dogovorId,
                    tabNumber, passportNumber, ZpMan.ALL, ZpMan.ALL,
                    ZpMan.ALL, ZpMan.ONE, ZpMan.ALL);

            ((TmMan) tblMan.getModel()).setData(mList);
            tblMan.setColumntWidth();

            lbCnt.setText(MSG_ALL_CNT + (mList == null ? 0 : mList.size()));
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public ZpMan getMan()
    {
        ZpMan man = (ZpMan) tblMan.getSelObj();
        return man;
    }

    public void setMan(ZpMan man)
    {
        if (man != null)
        {
            edtFio.setText(man.toString());
            btnSearch.doClick();
        }
    }

    protected boolean isNextEnabled()
    {
        ZpMan man = (ZpMan) tblMan.getSelObj();
        if (man == null)
        {
            DefCO.showSelected(this);
            return false;
        }

        return true;
    }
}
