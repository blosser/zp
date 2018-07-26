package ru.ittrans.zp.client.imp;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import ru.ittrans.zp.client.CO;
import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.client.lib.ManDogovor;
import ru.ittrans.zp.client.lib.ManEditDialog;
import ru.ittrans.zp.client.lib.ManTableCellRenderer;
import ru.ittrans.zp.client.lib.TmMan;
import ru.ittrans.zp.client.man.DefCellEditor;
import ru.ittrans.zp.client.man.ManValueTableCellRenderer;
import ru.ittrans.zp.client.man.TmManValue;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 05.05.11
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class ImportDialog extends DefDialog
        implements ButtonEditInterface, ButtonSaveInterface
{
    private JLabel lbFormat =
            new JLabel("<html>Файл Excel 2003 (XLS). Пример: A Магазин (866-Пятерочка); " +
                    "B Должность (Кассир); " +
                    "C ФИО (Аширбекова К); " +
                    "D Поставщик (Райт); " +
                    "E Даты (01.01.2011) F (02.01.2011) и т.д.<br>"+
                    "Соответствие: А - автоматическое, Ф - фактический магазин, Р - ручное<html>"
            );

    private ManEditDialog manEditDialog = null;

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();
    private JLabel lbBase = new JLabel("Базовый");
    private JRadioButton rbPyaterochka = new JRadioButton("Пятерочка");
    private JRadioButton rbPerekrestok = new JRadioButton("Перекресток");

    private JLabel lbBatch = new JLabel(DefCO.MSG_BATCH);
    private DefTextField edtBatch = new DefTextField();

    private JLabel lbFileName = new JLabel(DefCO.MSG_FILE);
    private DefTextField edtFileName = new DefTextField();
    private DefButton btnDot = new DefButton("...");
    private DefButton btnDownLoad = new DefButton("Загрузить");
    private JFileChooser fc = new JFileChooser();

    private JLabel lbFio = new JLabel(ManEditDialog.COL_NAMES_MAN[2]);
    private DefTextField edtFio = new DefTextField();
    private DefButton btnSearch = new DefButton("Поиск");
    private ButtonEditPanel bepMan = new ButtonEditPanel(this, true, true, false);

    private DefTable tblManImport = new DefTable(new TmManImport(), false);
    private JScrollPane spManImport = new JScrollPane(tblManImport);

    private DefButton btnLeft = new DefButton("<<<");
    private DefButton btnRight = new DefButton(">>>");

    private DefTable tblMan =
            new DefTable(new TmMan(Connect.isRole(ZpRole.ROLE_NOT_FIO_ONLY)), true);
    private JScrollPane spMan = new JScrollPane(tblMan);

    private DefButton btnAvtomat = new DefButton("Авт. соотв.");
    private DefButton btnImport = new DefButton("Импорт");
    private JPanel pnlProgress = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel lbProgress = new JLabel();

    private DefComboBox<ZpShop> cbShopCellEditor = new DefComboBox<ZpShop>();
    private DefTable tblManImportValue = new DefTable(new TmManValue(), false);
    private JScrollPane spManImportValue = new JScrollPane(tblManImportValue);

    private DefTable tblManValuePlan = new DefTable(new TmManValue(), false);
    private JScrollPane spManValuePlan = new JScrollPane(tblManValuePlan);

    private DefTable tblManValueFact = new DefTable(new TmManValue(), false);
    private JScrollPane spManValueFact = new JScrollPane(tblManValueFact);

    private ButtonSavePanel bsp = new ButtonSavePanel(this, ButtonSavePanel.BSP_TYPE.BSP_CLOSE);

    public ImportDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_IMPORT_DATA);
        if (manEditDialog == null)
            manEditDialog = new ManEditDialog(this);
        setData(-1);
        jbInit();
    }

    public void setData(int periodIndex)
    {
        DefCO.setPeriodData(cbPeriod, periodIndex);

        edtFio.setText("");
        ((TmManImport) tblManImport.getModel()).setData(null);
        ((TmMan) tblMan.getModel()).setData(null);
    }

    protected void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlTop = new JPanel(new GridBagLayout());
        //JPanel pnlCenter = new JPanel(new GridBagLayout());
        JPanel pnlCenterL = new JPanel(new GridBagLayout());
        JPanel pnlCenterR = new JPanel(new GridBagLayout());
        JPanel pnlBottom = new JPanel(new GridBagLayout());

        JSplitPane splitPaneCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneCenter.setLeftComponent(pnlCenterL);
        splitPaneCenter.setRightComponent(pnlCenterR);
        splitPaneCenter.setResizeWeight(0.2);

        ///////////////////////////////////////////////////////////////////////////

        pnlMain.add(pnlTop, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(splitPaneCenter, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.9,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(pnlBottom, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////
        pnlTop.setBorder(BorderFactory.createTitledBorder("Параметры загрузки"));
        pnlTop.add(lbFormat, new GridBagConstraints(0, 0, 4, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlTop.add(lbPeriod, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        JPanel pnlPeriod = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlPeriod.add(cbPeriod);
        pnlPeriod.add(lbBase);
        pnlPeriod.add(rbPyaterochka);
        pnlPeriod.add(rbPerekrestok);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbPyaterochka);
        bg.add(rbPerekrestok);
        pnlTop.add(pnlPeriod, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlTop.add(lbBatch, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlTop.add(edtBatch, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, DefCO.INSETS, 200, 0));

        pnlTop.add(lbFileName, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlTop.add(edtFileName, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS, 200, 0));
        pnlTop.add(btnDot, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlTop.add(btnDownLoad, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        ///////////////////////////////////////////////////////////////////////////


        //pnlCenter.setBorder(BorderFactory.createTitledBorder("Соответсвие людей, загружаемых из файла и базы"));
        pnlCenterL.add(new JLabel("Соответсвие людей, загружаемых из файла и базы"), new GridBagConstraints(0, 0, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlCenterL.add(spManImport, new GridBagConstraints(0, 1, 2, 2, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlCenterL.add(btnLeft, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.5,
                GridBagConstraints.SOUTH, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlCenterL.add(btnRight, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.5,
                GridBagConstraints.NORTH, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        JPanel pnlFio = new JPanel(new GridBagLayout());
        pnlFio.add(lbFio, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlFio.add(edtFio, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS, 0, 0));
        pnlFio.add(btnSearch, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlFio.add(bepMan, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS0, 0, 0));

        btnSearch.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Date period = DefCO.getCbPeriod(cbPeriod);
                String name1 = edtFio.getText();
                btnSearchPressed(period, name1, false, null);
            }
        });

        btnSearch.setIcon(DefCO.loadIcon(DefCO.PNG_EXEC));

        pnlCenterR.add(pnlFio, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, DefCO.INSETS0, 0, 0));
        pnlCenterR.add(spMan, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        JPanel pnlExec = new JPanel(new GridBagLayout());
        pnlExec.add(btnAvtomat, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlExec.add(btnImport, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlExec.add(pnlProgress, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlProgress.add(lbProgress);

        pnlCenterL.add(pnlExec, new GridBagConstraints(0, 3, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        pnlBottom.setBorder(BorderFactory.createTitledBorder("Значения, загружаемые из файла, план, факт базы"));

        pnlBottom.add(spManImportValue, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.6,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));
        pnlBottom.add(spManValuePlan, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.2,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));
        pnlBottom.add(spManValueFact, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.2,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        btnDot.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDotPressed();
            }
        });

        btnDownLoad.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDownLoadPressed();
            }
        });

        btnAvtomat.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnAvtomatPressed();
            }
        });

        btnImport.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnImportPressed();
            }
        });

        btnLeft.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnLeftPressed();
            }
        });

        btnRight.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnRightPressed();
            }
        });

        edtFileName.setMyEditable(false);
        //edtFileName.setText("C:\\projects\\zp\\resource\\s21.xls");

        tblManImport.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                changeManImportSelection();
            }
        });

        tblMan.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                changeManSelection();
            }
        });

        tblMan.setDefaultRenderer(ManDogovor.class, new ManTableCellRenderer());

        tblManImportValue.setDefaultEditor(ZpShop.class, new DefCellEditor(cbShopCellEditor));
        tblManImportValue.setDefaultRenderer(ZpShop.class, new ManValueTableCellRenderer());

        tblManValuePlan.setDefaultRenderer(ZpShop.class, new ManValueTableCellRenderer());
        tblManValueFact.setDefaultRenderer(ZpShop.class, new ManValueTableCellRenderer());

        tblManValuePlan.getTableHeader().setPreferredSize(new Dimension(0, 0));
        tblManValueFact.getTableHeader().setPreferredSize(new Dimension(0, 0));

        cbShopCellEditor.setBackground(DefTable.GREEN_COLOR);

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    public int getCountDays(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int cnt = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return cnt;
    }

    public ZpMan btnSearchPressed(Date period, String name1, boolean isCheckOnly, Long factShopId)
    {
        try
        {
            // факт
            List<ZpMan> mList = Connect.getZpRemote().getMan(period, ZpMan.ALL,
                    ZpMan.ALL, ZpMan.ALL, ZpMan.ALL, name1,
                    ZpMan.ONE, ZpMan.ZERO, "", ZpMan.ALL,
                    ZpMan.ALL, ZpMan.ALL, "", "", ZpMan.ALL, ZpMan.ALL,
                    ZpMan.ALL, ZpMan.ALL, ZpMan.ALL);

            List<ZpMan> mFact = new ArrayList<ZpMan>();
            //int nCnt = -1;
            for (ZpMan man : mList)
            {
                //nCnt++;
                List<ZpManValue> mvList = man.getMvList();
                List<ZpManValue> mvPlanList = new ArrayList<ZpManValue>();
                List<ZpManValue> mvFactList = new ArrayList<ZpManValue>();
                for (ZpManValue mv : mvList)
                    if (ZpManValue.VALUE_TYPE13.equals(mv.getValueType()))
                        mvPlanList.add(mv);
                    else if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()))
                        mvFactList.add(mv);

                man.setMvPlanList(mvPlanList);
                man.setMvFactList(mvFactList);

                if (isCheckOnly && factShopId != null)
                {
                    if (factShopId.equals(man.getShopId()))
                    {
                        boolean isFind = false;
                        for (ZpMan m:mFact)
                        {
                            if (m.getId().equals(man.getId()))
                            {
                                isFind = true;
                                break;
                            }
                        }
                        if (!isFind)
                        mFact.add(man);
                    }
                }
            }

            if (isCheckOnly)
            {
                if (mList.size() == 1) return mList.get(0);
                if (mFact.size() == 1) return mFact.get(0);
                return null;
            }

            ((TmMan) tblMan.getModel()).setData(mList);
            tblMan.setColumntWidth();
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
        return null;
    }

    public void changeManImportSelection()
    {
        try
        {
            ZpManImport mi = (ZpManImport) tblManImport.getSelObj();
            if (mi == null)
            {
                ((TmManValue) tblManImportValue.getModel()).setData(null);
                ((TmManValue) tblManImportValue.getModel()).setRowEditable(-1);
            } else
            {
                List<ZpMan> mList = new ArrayList<ZpMan>();
                mList.add(mi.getMan());
                ((TmManValue) tblManImportValue.getModel()).setData(mList);
                DefComboBox.cbAddItem(cbShopCellEditor, DicManager.getShopMap(), true, false);
                ((TmManValue) tblManImportValue.getModel()).setRowEditable(0);

                String fname = getFname(mi.getMan());
                edtFio.setText(fname);
                Date period = DefCO.getCbPeriod(cbPeriod);
                btnSearchPressed(period, fname, false, null);
                if (tblMan.getRowCount() > 0)
                {
                    if (mi.getDbMan() == null)
                        tblMan.getSelectionModel().setSelectionInterval(0, 0);
                    else
                    {
                        List<ZpMan> mSelList = ((TmMan) tblMan.getModel()).getList();
                        int row = 0;
                        for (ZpMan mSel : mSelList)
                        {
                            if (mSel.getId().equals(mi.getDbMan().getId()))
                            {
                                tblMan.getSelectionModel().setSelectionInterval(row, row);
                                break;
                            }
                            row++;
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    private String getFname(ZpMan man)
    {
        String name1 = man.getName1().trim();
        String fname = name1;
        if (name1.contains(" "))
        {
            int index = name1.indexOf(" ");
            int searchIndex = name1.length() > index ? index + 2 : index;
            fname = name1.substring(0, searchIndex);
        }
        return fname;
    }

    public void changeManSelection()
    {
        ZpMan man = (ZpMan) tblMan.getSelObj();
        if (man == null)
        {
            ((TmManValue) tblManValuePlan.getModel()).setData(null);
            ((TmManValue) tblManValueFact.getModel()).setData(null);
        } else
        {
            List<ZpMan> mPlanList = new ArrayList<ZpMan>();
            ZpMan manPlan = new ZpMan();
            manPlan.setMvList(man.getMvPlanList());
            mPlanList.add(manPlan);
            ((TmManValue) tblManValuePlan.getModel()).setData(mPlanList);
            List<ZpMan> mFactList = new ArrayList<ZpMan>();
            ZpMan manFact = new ZpMan();
            manFact.setMvList(man.getMvFactList());
            mFactList.add(manFact);
            ((TmManValue) tblManValueFact.getModel()).setData(mFactList);
        }
    }

    private void btnDotPressed()
    {
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            edtFileName.setText(file.getPath());
        }
    }

    private void btnDownLoadPressed()
    {
        if (!rbPyaterochka.isSelected() && !rbPerekrestok.isSelected())
            DefCO.showInf(this, DefCO.MSG_SELECT + lbBase.getText());
        else
        {
            //System.out.println("begin downLoad");
            boolean isAllShopFind = true;
            boolean isFindDigit = rbPyaterochka.isSelected();
            List<ZpManImport> manImportList = new ArrayList<ZpManImport>();

            try
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                FileInputStream fileIn = new FileInputStream(edtFileName.getText());
                HSSFWorkbook workbook = new HSSFWorkbook(fileIn);
                // пока в книге смотрим первый лист
                HSSFSheet sheet = workbook.getSheetAt(0);
                List<String> shopMap = new ArrayList<String>();

                int dayCnt = getCountDays(DefCO.getCbPeriod(cbPeriod));

                // вставляем сначала все новые магазины
                for (Row row : sheet)
                {
                    if (!isFindDigit && row.getRowNum() == 0) continue;
                    //int rowNum = row.getRowNum() + 1;
                    String cellNameStr = CO.getValueStr(row, CO.INDEX_A);
                    //System.out.println(rowNum + ". " + cellNameStr);

                    if (cellNameStr != null &&
                            cellNameStr.trim().length() > 0/* &&
                        cellNameStr.toLowerCase().contains("пятерочка")*/)
                    {
                        int digitIndex = 0;
                        for (int i = 0; i < cellNameStr.length(); i++)
                        {
                            if (!Character.isDigit(cellNameStr.charAt(i)))
                                break;
                            digitIndex = i + 1;
                        }

                        if (isFindDigit && digitIndex == 0) continue;
                        String codefull = cellNameStr;
                        String code = codefull;
                        if (isFindDigit)
                        {
                            codefull = cellNameStr.substring(0, digitIndex);// напр 010
                            code = String.valueOf(Integer.valueOf(codefull));
                        }

                        if (!shopMap.contains(code) && DicManager.isShopCodeExist(code) == null)
                        {
                            System.out.println("new shop code=" + code);
                            shopMap.add(code);
                            ZpShop shop = new ZpShop();
                            if (rbPyaterochka.isSelected())
                                shop.setParentId(ZpShop.BASE_PYATEROCHKA);
                            else if (rbPerekrestok.isSelected())
                                shop.setParentId(ZpShop.BASE_PEREKRESTOK);
                            shop.setCode(code);
                            Connect.getZpRemote().mergeEntity(shop);
                        }
                    }
                }

                DicManager.getShopMap(true);

                for (Row row : sheet)
                {
                    if (!isFindDigit && row.getRowNum() == 0) continue;
//                int rowNum = row.getRowNum() + 1;
                    String cellNameStr = CO.getValueStr(row, CO.INDEX_A);

                    if (cellNameStr != null &&
                            cellNameStr.trim().length() > 0/* &&
                        cellNameStr.toLowerCase().contains("пятерочка")*/)
                    {
                        int digitIndex = 0;
                        for (int i = 0; i < cellNameStr.length(); i++)
                        {
                            if (!Character.isDigit(cellNameStr.charAt(i)))
                                break;
                            digitIndex = i + 1;
                        }

                        if (isFindDigit && digitIndex == 0) continue;
                        String codefull = cellNameStr;
                        String code = codefull;
                        if (isFindDigit)
                        {
                            codefull = cellNameStr.substring(0, digitIndex);// напр 010
                            code = String.valueOf(Integer.valueOf(codefull));
                        }

                        Long shopId = DicManager.isShopCodeExist(code);

                        String name1 = CO.getValueStr(row, CO.getColIndex("C"));
                        ZpMan man = new ZpMan();
                        man.setName1(name1);
                        ZpManImport mi = new ZpManImport();
                        mi.setShopId(shopId);
                        mi.setMan(man);
                        manImportList.add(mi);

                        List<ZpManValue> mvList = new ArrayList<ZpManValue>();
                        for (int i = 0; i < dayCnt; i++)
                        {
                            Date day = DefCO.getCbPeriod(cbPeriod, i + 1);
                            ZpManValue mv = new ZpManValue();
                            mv.setSd(day);
                            mv.setValueType(ZpManValue.VALUE_TYPE1);

                            String d = CO.getValueStr(row, CO.getColIndex("E") + i);
                            if (d != null && d.length() > 0)
                            {
                                mv.setShopId(shopId);
                                mv.setSd(day);
                                mv.setValueType(ZpManValue.VALUE_TYPE1);
                                mv.setValue(ZpMan.ONE);
                                mv.setActive(ZpMan.ONE);
                                mv.setSessionId(Connect.getSessionId());
                            }
                            mvList.add(mv);
                        }
                        man.setMvList(mvList);
                    }
                }
                fileIn.close();
            } catch (Exception ex)
            {
                System.err.println(ex);
            } finally
            {
                setCursor(Cursor.getDefaultCursor());
            }
            //System.out.println("end downLoad");
            if (isAllShopFind)
                ((TmManImport) tblManImport.getModel()).setData(manImportList);
        }
    }

    private static final String[] LABEL_STRINGS =
            {
                    "Sunday", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday"
            };

    protected static final int TIMER_DELAY = 600;

    private void btnAvtomatPressed()
    {
        new Thread()
        {
            public void run()
            {
                btnAvtomatDetail();
            }
        }.start();
    }

    private synchronized void btnAvtomatDetail()
    {
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            List<ZpManImport> miList = ((TmManImport) tblManImport.getModel()).getList();
            String sAllCnt = String.valueOf(miList.size());
            int nCnt = 0;
            for (ZpManImport mi : miList)
            {
                nCnt++;
                String strLog = String.valueOf(nCnt) + " из " + sAllCnt;
                System.out.println(strLog);
                lbProgress.setText(strLog);

                if (mi.getDbMan() != null) continue;
                String fname = getFname(mi.getMan());
                Date period = DefCO.getCbPeriod(cbPeriod);
                ZpMan dbMan = btnSearchPressed(period, fname, true, mi.getShopId());
                if (dbMan != null)
                {
                    mi.setDbMan(dbMan);
                    if (mi.getShopId().equals(dbMan.getShopId()))
                        mi.setSearchType(ZpManImport.SEARCH_TYPE_F);
                    else
                        mi.setSearchType(ZpManImport.SEARCH_TYPE_A);
//                    List<ZpManValue> mvList = mi.getMan().getMvList();
//                    List<ZpManValue> mvDbList = dbMan.getMvList();
//                    for (int i = 0; i < mvList.size(); i++)
//                    {
//                        ZpManValue mv = mvList.get(i);
//                        ZpManValue mvDb = mvDbList.get(i);
//                        if (mv.getShopId() != null &&
//                                mvDb.getShopId() != null &&
//                                !mv.getShopId().equals(mvDb.getShopId()))
//                            errorCnt++;
//                    }
//                    if (errorCnt > 0)
//                        mi.setErrorCnt(new Long(errorCnt));
                }
            }
            ((TmManImport) tblManImport.getModel()).fireTableDataChanged();
        } catch (Exception ex)
        {
            System.err.println(ex);
            DefCO.showError(this, ex);
        } finally
        {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void btnImportPressed()
    {
        Date period = DefCO.getCbPeriod(cbPeriod);
        String batchName = edtBatch.getText();
        if (batchName.trim().length() == 0)
            DefCO.showInf(this, DefCO.MSG_INPUT + " имя пачки.");
        else
            try
            {
                List<ZpManImport> miList = ((TmManImport) tblManImport.getModel()).getList();
                for (ZpManImport mi : miList)
                    if (mi.getDbMan() == null)
                        if (DefCO.isDel("Соответствие установлено не полностью. Продолжить?"))
                        {
                            break;
                        } else
                        {
                            return;
                        }
                ZpBatch batch = new ZpBatch();
                batch.setName(batchName);
                batch.setPeriod(period);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Connect.getZpRemote().importMan(batch, miList);
                DefCO.showInf(this, "Импорт прошел успешно.");
            } catch (Exception ex)
            {
                System.err.println(ex);
                DefCO.showError(this, ex);
            } finally
            {
                setCursor(Cursor.getDefaultCursor());
            }
    }

    private void btnLeftPressed()
    {
        ZpManImport mi = (ZpManImport) tblManImport.getSelObj();
        ZpMan man = (ZpMan) tblMan.getSelObj();
        if (mi == null || man == null)
        {
            DefCO.showInf(this, DefCO.MSG_SELECTED + " соответствие человека из левой и правой таблицы.");
        } else
        {
            int row = tblManImport.getSelectedRow();
            mi.setDbMan(man);
            mi.setSearchType(ZpManImport.SEARCH_TYPE_P);
            ((TmManImport) tblManImport.getModel()).fireTableRowsUpdated(row, row);
        }
    }

    private void btnRightPressed()
    {
        ZpManImport mi = (ZpManImport) tblManImport.getSelObj();
        if (mi == null)
        {
            DefCO.showSelected(this);
        } else
        {
            int row = tblManImport.getSelectedRow();
            mi.setDbMan(null);
            mi.setSearchType(null);
            ((TmManImport) tblManImport.getModel()).fireTableRowsUpdated(row, row);
        }
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpMan man = new ZpMan();
        man.setName1(edtFio.getText());
        man.setActive(ZpMan.ONE);
        manEditDialog.showMan(man);
        if (manEditDialog.modalResult)
            btnSearch.doClick();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblMan.isSelected())
        {
            ZpMan man = (ZpMan) tblMan.getSelObj();
            manEditDialog.showMan(man);
            if (manEditDialog.modalResult)
                btnSearch.doClick();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
    }
}
