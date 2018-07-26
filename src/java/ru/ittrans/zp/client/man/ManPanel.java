package ru.ittrans.zp.client.man;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.client.lib.ManDogovor;
import ru.ittrans.zp.client.lib.ManEditDialog;
import ru.ittrans.zp.client.lib.ManTableCellRenderer;
import ru.ittrans.zp.client.lib.TmMan;
import ru.ittrans.zp.client.report.PrintPopupMenu;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 15.10.2010
 * Time: 16:24:35
 * To change this template use File | Settings | File Templates.
 */
public class ManPanel extends JPanel
        implements ButtonEditInterface, ButtonSaveInterface
{
    private ManEditDialog manEditDialog = null;

    private JSplitPane splitPane = new JSplitPane();

    private JPanel pnlLeft = new JPanel(new GridBagLayout());
    private JPanel pnlLeftMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private ButtonEditPanel bepLeft = null;
    private JLabel lbSummaryLeft = new JLabel(" ");
    private DefTable tblMan =
            new DefTable(new TmMan(Connect.isRole(ZpRole.ROLE_NOT_FIO_ONLY)), true);
    private JScrollPane spMan = new JScrollPane(tblMan);

    private JPanel pnlRight = new JPanel(new GridBagLayout());
    private JPanel pnlRightMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private ButtonEditPanel bepRight = null;
    private ButtonSavePanel bsp = new ButtonSavePanel(this);
    private JLabel lbColWidht = new JLabel("Ширина");
    private DefNumberTextField edtColWidht = new DefNumberTextField();
    private DefCheckBox cbxShopName = new DefCheckBox("Имя м-на", true);
    private DefButton btnActive = new DefButton("Уд.");
    private JLabel lbSummaryRight = new JLabel(" ");
    private DefTable tblManValue = new DefTable(new TmManValue(), true, tblMan);
    private JScrollPane spManValue = new JScrollPane(tblManValue);

    private MainFrame mf;

    private DefTextField edtManPaidComment = new DefTextField();
    private DefaultCellEditor dceManPaidComment = new DefaultCellEditor(edtManPaidComment);
    private DefNumberTextField edtManValue = new DefNumberTextField(10);
    private DefCellEditor dceManValue = new DefCellEditor(edtManValue);
    private DefComboBox<ZpPaid> cbPaidCellEditor = new DefComboBox<ZpPaid>();
    private DefComboBox<ZpShop> cbShopCellEditor = new DefComboBox<ZpShop>();

    private WhoChangeJButton whoChangeJButtonLeft = new WhoChangeJButton();
    private ManPrintJButton btnManPrint = new ManPrintJButton();
    private WhoChangeJButton whoChangeJButtonRight = new WhoChangeJButton();

    private PrintPopupMenu printPopupMenu = new PrintPopupMenu();

    public ManPanel(MainFrame mf)
    {
        super(new GridBagLayout());
        this.mf = mf;
        if (manEditDialog == null)
            manEditDialog = new ManEditDialog(mf);
        jbInit();
    }

    public void changeOrientation(int view)
    {
        if (view == 0)
            splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        else if (view == 1)
            splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }

    private void initCellEditor()
    {
        tblManValue.setDefaultEditor(ZpManPaid.class, new DefCellEditor(edtManPaidComment));
        tblManValue.setDefaultEditor(ZpManValue.class, dceManValue);

        tblManValue.setDefaultEditor(ZpPaid.class, new DefCellEditor(cbPaidCellEditor));
        tblManValue.setDefaultEditor(ZpShop.class, new DefCellEditor(cbShopCellEditor));

        edtManPaidComment.setBackground(DefTable.GREEN_COLOR);
        edtManPaidComment.setBorder(BorderFactory.createLineBorder(Color.black));
        dceManPaidComment.setClickCountToStart(0);

        edtManValue.setBackground(DefTable.GREEN_COLOR);
        edtManValue.setBorder(BorderFactory.createLineBorder(Color.black));
        edtManValue.setHorizontalAlignment(SwingConstants.RIGHT);


        cbPaidCellEditor.setBackground(DefTable.GREEN_COLOR);
        cbShopCellEditor.setBackground(DefTable.GREEN_COLOR);
    }

    private void jbInit()
    {
        splitPane.setLeftComponent(pnlLeft);
        splitPane.setRightComponent(pnlRight);
        splitPane.setResizeWeight(0.2);
        changeOrientation(0);

        //pnlLeft.add(pnlLeftMenu, BorderLayout.NORTH);
        //pnlLeft.add(spMan, BorderLayout.CENTER);
        pnlLeft.add(lbSummaryLeft, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS_0L0R, 0, 0));
        pnlLeft.add(pnlLeftMenu, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        pnlLeft.add(spMan, new GridBagConstraints(0, 2, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS_0LBR, 0, 0));

        if (Connect.isRole(ZpRole.ROLE_LIB_MAN))
        {
            bepLeft = new ButtonEditPanel(this);
        } else
        {
            bepLeft = new ButtonEditPanel(this, false, false, false);
        }

        pnlLeftMenu.add(bepLeft);

        bepLeft.addButton(btnManPrint, 0);
        bepLeft.addButton(whoChangeJButtonLeft, 0);

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

        whoChangeJButtonLeft.addActionListener(new ActionListener()
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

        if (Connect.isRole(ZpRole.ROLE_CHANGE_SMEN) ||
                Connect.isRole(ZpRole.ROLE_CHANGE_PLAN))
        {
            bepRight = new ButtonEditPanel(this, false, true, false);
            bepRight.addButton(bsp.btnSave);
            bepRight.addButton(bsp.btnCancel);
        } else
        {
            bepRight = new ButtonEditPanel(this, false, false, false);
        }

        edtColWidht.setText("50");
        edtColWidht.setPreferredSize(new Dimension(25, 20));
        bepRight.addButton(lbColWidht);
        bepRight.addButton(edtColWidht);
        bepRight.addButton(cbxShopName);

        bepRight.addButton(whoChangeJButtonRight, 0);
        whoChangeJButtonRight.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (tblManValue.isSelected())
                {
                    ZpManValue mv = getSelManValue();
                    if (mv != null)
                        mf.findOneSession(mv.getSessionId());
                } else DefCO.showSelected(mf);
            }
        });

        //pnlRight.add(pnlRightMenu, BorderLayout.NORTH);
        //pnlRight.add(spManValue, BorderLayout.CENTER);
        pnlRight.add(lbSummaryRight, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS_0L0R, 0, 0));
        pnlRight.add(pnlRightMenu, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        pnlRight.add(spManValue, new GridBagConstraints(0, 2, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS_0LBR, 0, 0));

        if (Connect.isEvild())
        {
            bepRight.add(btnActive);
        }
        pnlRightMenu.add(bepRight);

        add(splitPane, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));

        tblMan.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                changeSelection(tblMan, tblManValue);
            }
        });

        tblManValue.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                changeSelection(tblManValue, tblMan);
            }
        });

        ChangeListener cl = new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                if (splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
                {
                    JViewport vpMan = spMan.getViewport();
                    JViewport vpValue = spManValue.getViewport();

                    if (e.getSource() == vpMan)
                        setScroll(vpMan, 1, vpValue);
                    else if (e.getSource() == vpValue)
                        setScroll(vpValue, 1, vpMan);
                }
            }
        };

        JViewport vpMan = spMan.getViewport();
        vpMan.addChangeListener(cl);
        JViewport vpManValue = spManValue.getViewport();
        vpManValue.addChangeListener(cl);

        initCellEditor();

        btnActive.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnActivePressed();
            }
        });

        tblMan.setDefaultRenderer(ManDogovor.class, new ManTableCellRenderer());
        tblManValue.setDefaultRenderer(ZpShop.class, new ManValueTableCellRenderer());

        setButtonEnabledManValue(false);
    }

    private ZpManValue getSelManValue()
    {
        Object o = tblManValue.getSelObj();
        int rowIndex = tblManValue.getSelectedRow();
        int columnIndex = tblManValue.getSelectedColumn();
        if (o instanceof ZpMan && rowIndex != -1 && columnIndex != -1)
        {
            ZpManValue mv = ((TmManValue) tblManValue.getModel()).
                    getManValue(rowIndex, columnIndex);
            return mv;
        }
        return null;
    }

    private void btnActivePressed()
    {
        boolean isShowMessage = true;
        ZpManValue mv = getSelManValue();
        if (mv != null &&
                ZpManValue.VALUE_TYPE1.equals(mv.getValueType()) &&
                mv.getShopId() != null)
        {
            mv.setActive(ZpMan.ONE.equals(mv.getActive()) ? ZpMan.ZERO : ZpMan.ONE);
            int rowIndex = tblManValue.getSelectedRow();
            ((TmManValue) tblManValue.getModel()).fireTableRowsUpdated(rowIndex, rowIndex);
            isShowMessage = false;
        }

        if (isShowMessage)
            DefCO.showInf(mf, DefCO.MSG_SELECTED + " одну смену.");
    }

    private void setButtonEnabledManValue(boolean isEditable)
    {
        if (Connect.isRole(ZpRole.ROLE_CHANGE_SMEN) ||
                Connect.isRole(ZpRole.ROLE_CHANGE_PLAN))
        {
            bepRight.setMyEnabled(!isEditable);
            bsp.setMyEnabled(isEditable);
        }

        if (Connect.isEvild())
        {
            btnActive.setEnabled(isEditable);
        }

        mf.getPnlFilter().setFilterEnabled(!isEditable);
    }

    private void btnMenuPressedManValue(int index)
    {
        int row = tblManValue.getSelectedRow();
        boolean isEditable = index == 0;
        if (isEditable && row == -1)
        {
            DefCO.showSelected(mf);
        } else
        {
            setButtonEnabledManValue(isEditable);
            if (isEditable)
            {
                DefComboBox.cbAddItem(cbPaidCellEditor, DicManager.getPaidMap(), true, false);
                DefComboBox.cbAddItem(cbShopCellEditor, DicManager.getShopMap(), true, false);
                ((TmManValue) tblManValue.getModel()).setRowEditable(row);
            } else
            {
                if (index == 1)
                {
                    try
                    {
                        ZpMan man = (ZpMan) tblMan.getSelObj();
                        Connect.getZpRemote().mergeManValue(mf.getPnlFilter().getPeriod(), man);
                    } catch (Exception ex)
                    {
                        DefCO.showError(mf, ex);
                    }
                }
                refresh();
            }

            tblManValue.updateUI();
        }
    }

    private void refresh()
    {
        ((TmManValue) tblManValue.getModel()).setRowEditable(-1);
        int rowMan = tblMan.getSelectedRow();
        mf.btnSearchPressed();
        //getMans(period, shopId, paidId, batchId, name1, showHide);
        tblMan.getSelectionModel().setSelectionInterval(rowMan, rowMan);
    }

    public void getMans(Date period, Long shopId,
                        Long paidId, Long batchId,
                        String name1, Long showHide,
                        String manScore, Long card,
                        Long territClientId, Long tabClientId,
                        Long parentShopId, Long planFact,
                        Long paidD)
    {
        try
        {
            List<ZpMan> mList = Connect.getZpRemote().getMan(period, shopId,
                    paidId, batchId, ZpMan.ALL, name1, ZpMan.ONE,
                    showHide, manScore, card,
                    ZpMan.ALL, ZpMan.ALL,
                    "", "", territClientId, tabClientId, parentShopId,
                    planFact, paidD);
            ((TmMan) tblMan.getModel()).setData(mList);
            tblMan.setColumntWidth();

            boolean isShopNameVisible = cbxShopName.isSelected();
            ((TmManValue) tblManValue.getModel()).setShopNameVisible(isShopNameVisible);
            ((TmManValue) tblManValue.getModel()).setData(mList);
            int w = edtColWidht.getValue().intValue();
            ((TmManValue) tblManValue.getModel()).setW(w);
            tblManValue.setColumntWidth();

            long paySum = 0;
            long prePaySum = 0;
            long smenaSum = 0;

            List<ZpMan> zpManList = ((TmManValue) tblManValue.getModel()).getList();
            for (ZpMan man : zpManList)
            {
                List<Long> shopList = new ArrayList<Long>();

                for (ZpManValue mv : man.getMvList())
                {
                    if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()))
                    {
                        if (ZpMan.ONE.equals(mv.getActive()))
                        {
                            if ((ZpMan.ALL.equals(shopId) && mv.getShopId() != null) ||
                                    (shopId != null && shopId.equals(mv.getShopId())))
                                smenaSum++;
                            if (mv.getShopId() != null &&
                                    !shopList.contains(mv.getShopId()))
                                shopList.add(mv.getShopId());
                        }
                    } else if (ZpManValue.VALUE_TYPE10.equals(mv.getValueType()) &&
                            mv.getValue() != null)
                    {
                        paySum += mv.getValue().longValue();
                    }
                }

                if (!ZpMan.ONE.equals(man.getSecondJob()))
                {
                    if (shopList.size() > 2)
                        man.setStatus("М");
                    else
                        man.setStatus("Н");
                }
            }
            for (int i = 0; i < tblManValue.getRowCount(); i++)
            {
                //todo column number
//                Object mvPay = tblManValue.getModel().getValueAt(i, 3);
//                if (mvPay instanceof ZpManValue)
//                {
//                    if (((ZpManValue) mvPay).getValue() != null)
//                        paySum += ((ZpManValue) mvPay).getValue().longValue();
//                }
                Object mvPrePay = tblManValue.getModel().getValueAt(i, 3);
                if (mvPrePay instanceof ZpManValue)
                {
                    if (((ZpManValue) mvPrePay).getValue() != null)
                        prePaySum += ((ZpManValue) mvPrePay).getValue().longValue();
                }
            }

            Long planSmenaSum = null;
            if (shopId != null && !ZpMan.ALL.equals(shopId))
            {
                List<ZpShopShift> shopShiftList = Connect.getZpRemote().getShopShift(period);
                for (ZpShopShift s : shopShiftList)
                    if (shopId.equals(s.getShopId()))
                    {
                        planSmenaSum = s.getValue();
                        break;
                    }
            }

            String summaryRight = "Всего: " + mList.size() +
                    "   Выплачено: " + paySum +
                    "   К выплате: " + prePaySum;

            if (Connect.isEvild())
                summaryRight += "   Смен: " +
                        (planSmenaSum == null ? "[-" + smenaSum + "]" :
                                planSmenaSum + " [" + (planSmenaSum - smenaSum) + "]");

            if (Connect.isInna())
                summaryRight += "   Смен: [0]";

            lbSummaryRight.setText(summaryRight);
        } catch (Exception ex)
        {
            DefCO.showError(mf, ex);
        }
    }

    private void changeSelection(DefTable t1, DefTable t2)
    {
        int row = t1.getSelectedRow();
        if (row == -1)
            t2.getSelectionModel().clearSelection();
        else
        {
            t2.getSelectionModel().setSelectionInterval(row, row);
            Rectangle rect = t2.getCellRect(row, 0, true);
            t2.scrollRectToVisible(rect);
        }
    }

    void setScroll(JViewport vpSource, int all, JViewport... vpOuts)
    {
        if (vpSource != null && vpOuts != null)
        {
            Point pSource = vpSource.getViewPosition();
            for (JViewport vp : vpOuts)
            {
                if (all == -1)
                {
                    vp.setViewPosition(pSource);
                } else if (all == 0)
                {
                    Point pOld = vp.getViewPosition();
                    Point pNew = new Point(pSource.x, pOld.y);
                    vp.setViewPosition(pNew);
                } else if (all == 1)
                {
                    Point pOld = vp.getViewPosition();
                    Point pNew = new Point(pOld.x, pSource.y);
                    vp.setViewPosition(pNew);
                }
            }
        }
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpMan man = new ZpMan();
        man.setActive(ZpMan.ONE);
        manEditDialog.showMan(man);
        if (manEditDialog.modalResult)
            refresh();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        boolean isTop = bepLeft.equals(((DefButton) o).getParent());
        if (isTop)
        {
            if (tblMan.isSelected())
            {
                ZpMan man = (ZpMan) tblMan.getSelObj();
                manEditDialog.showMan(man);
                if (manEditDialog.modalResult)
                    refresh();
            } else DefCO.showSelected(mf);
        } else
        {
            btnMenuPressedManValue(0);
        }
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
                    refresh();
                }
            } catch (Exception ex)
            {
                DefCO.showError(mf, ex);
            }
        } else DefCO.showSelected(mf);
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        btnMenuPressedManValue(1);
    }

    public void btnCancel_actionPerformed(ActionEvent e)
    {
        btnMenuPressedManValue(2);
    }
}

