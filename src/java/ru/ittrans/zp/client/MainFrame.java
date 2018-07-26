package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.ButtonEditPanel;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.imp.ImportDialog;
import ru.ittrans.zp.client.lib.*;
import ru.ittrans.zp.client.man.ManPanel;
import ru.ittrans.zp.client.report.ChangePassDialog;
import ru.ittrans.zp.client.report.ReportDialog;
import ru.ittrans.zp.client.report.SessionDialog;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpRole;
import ru.ittrans.zp.io.ZpSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 07.10.2010
 * Time: 16:02:29
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame
{
    private JPanel pnlMain = new JPanel(new BorderLayout());
    private FilterPanel pnlFilter = null;
    private ManPanel pnlMan = null;

    //private ReportDialog reportDialog = null;
    private SessionDialog sessionDialog = null;
    private ChangePassDialog changePassDialog = null;

    private ImportDialog importDialog = null;

    private ManDialog manDialog = null;

    private PaidDialog paidDialog = null;

    private ShopDialog shopDialog = null;

    private BatchDialog batchDialog = null;

    private AttrValueDialog attrValueDialog = null;

    private ClientDialog clientDialog = null;

    private OwnerDialog ownerDialog = null;

    private ReportDialog reportDialog = null;

    private ShopShiftDialog shopShiftDialog = null;

    private PlanDialog planDialog = null;

    public MainFrame()
    {
        super("2011 - " + DefCO.getClientText(Connect.getClient()));

        sessionDialog = new SessionDialog(this);
        importDialog = new ImportDialog(this);
        manDialog = new ManDialog(this);
        paidDialog = new PaidDialog(this);
        shopDialog = new ShopDialog(this);
        batchDialog = new BatchDialog(this);
        attrValueDialog = new AttrValueDialog(this);
        clientDialog = new ClientDialog(this);
        ownerDialog = new OwnerDialog(this);
        reportDialog = new ReportDialog(this);
        shopShiftDialog = new ShopShiftDialog(this);
        planDialog = new PlanDialog(this);

        ActionListener al = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSearchPressed();
            }
        };
        pnlFilter = new FilterPanel(al);
        pnlMan = new ManPanel(this);

        jbInit();
    }

    public FilterPanel getPnlFilter()
    {
        return pnlFilter;
    }

    //    public void checkPass()
//    {
//        ZpClient client = Connect.getClient();
//        if (client.getLogin().equals(client.getPassword()) ||
//                client.getPassword().length() < DefCO.MIN_LENGHT_PASS)
//            DefCO.showInf(this, DefCO.MES_SIMPLE_PASS);
//    }

    private void jbInit()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(getOwner());
        setJMenuBar(createMenu());
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent winEvt)
            {
                exitPerformed();
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlMain, BorderLayout.CENTER);

        pnlMain.add(pnlFilter, BorderLayout.NORTH);
        pnlMain.add(pnlMan, BorderLayout.CENTER);

        setIconImage(DefCO.loadIcon(DefCO.PNG_BAG).getImage());

        getRootPane().setDefaultButton(pnlFilter.btnSearch);
    }

    public void btnSearchPressed()
    {
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Date period = pnlFilter.getPeriod();
            Long shopId = pnlFilter.getShopId();
            Long paidId = pnlFilter.getPaidId();
            Long paidD = pnlFilter.getPaidD();
            Long batchId = pnlFilter.getBatchId();
            String fio = pnlFilter.getFio();
            Long showHide = pnlFilter.getShowHide();
            String manScore = pnlFilter.getManScore();
            Long card = pnlFilter.getCard();
            Long territClientId = pnlFilter.getTerritClientId();
            Long tabClientId = pnlFilter.getTabClientId();
            Long parentShopId = Connect.getParentShopId();
            if (parentShopId == null) parentShopId = ZpMan.ALL;
            Long planFact = pnlFilter.getPlantFactId();
            pnlMan.getMans(period, shopId, paidId, batchId, fio, showHide,
                    manScore, card, territClientId, tabClientId, parentShopId,
                    planFact, paidD);
        } catch (Exception ex)
        {
            DefCO.debugException(ex);
        } finally
        {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private JMenuBar createMenu()
    {
        JMenuBar mb = new JMenuBar();

        JMenu menuMain = new JMenu("Главная");
        JMenuItem miChangePass = new JMenuItem(ChangePassDialog.CHANGE_PASS + "...");
        miChangePass.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                changePassPerformed();
            }
        });
        menuMain.add(miChangePass);

        menuMain.addSeparator();

        JMenuItem miExit = new JMenuItem("Выход");
        miExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                exitPerformed();
            }
        });
        menuMain.add(miExit);
        mb.add(menuMain);

        JMenu menuView = new JMenu("Вид");
        JRadioButtonMenuItem miView0 = new JRadioButtonMenuItem("Слева направо", true);
        miView0.setIcon(DefCO.loadIcon(DefCO.PNG_RIGHT));
        miView0.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pnlMan.changeOrientation(0);
            }
        });
        menuView.add(miView0);
        JRadioButtonMenuItem miView1 = new JRadioButtonMenuItem("Сверху вниз");
        miView1.setIcon(DefCO.loadIcon(DefCO.PNG_DOWN));
        miView1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pnlMan.changeOrientation(1);
            }
        });
        menuView.add(miView1);
        ButtonGroup bg = new ButtonGroup();
        bg.add(miView0);
        bg.add(miView1);
        mb.add(menuView);


        if (Connect.isRole(ZpRole.ROLE_IMPORT))
        {
            JMenu menuImport = new JMenu(DefCO.MSG_IMPORT);
            JMenuItem miImportData = new JMenuItem(DefCO.MSG_IMPORT_DATA);
            miImportData.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    importDialog.setData(pnlFilter.getPeriodIndex());
                    importDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuImport.add(miImportData);
            mb.add(menuImport);
        }

        JMenu menuLib = new JMenu(DefCO.MSG_LIB);
        if (Connect.isRole(ZpRole.ROLE_LIB_MAN))
        {
            JMenuItem miLibMan = new JMenuItem(DefCO.MSG_MAN);
            miLibMan.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    manDialog.getMans();
                    manDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibMan);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_PAID))
        {
            JMenuItem miLibPaid = new JMenuItem(PaidEditDialog.COL_NAMES_PAID[0]);
            miLibPaid.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    paidDialog.setData(pnlFilter.getPeriodIndex());
                    paidDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibPaid);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_SHOP))
        {
            JMenuItem miLibShop = new JMenuItem(DefCO.MSG_SHOP);
            miLibShop.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    shopDialog.getShops();
                    shopDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibShop);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_BATCH))
        {
            JMenuItem miLibBatch = new JMenuItem(DefCO.MSG_BATCH);
            miLibBatch.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    batchDialog.setData(pnlFilter.getPeriodIndex());
                    batchDialog.getBatchs();
                    batchDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibBatch);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_ATTR_VALUE))
        {
            JMenuItem miLibAv = new JMenuItem(DefCO.MSG_ATTR_VALUE);
            miLibAv.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    attrValueDialog.getAttrValue();
                    attrValueDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibAv);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_CLIENT))
        {
            JMenuItem miLibClient = new JMenuItem(DefCO.MSG_CLIENT);
            miLibClient.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    clientDialog.getClients();
                    clientDialog.setVisible(true);
                }
            });
            menuLib.add(miLibClient);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_OWNER))
        {
            JMenuItem miLibOwner = new JMenuItem(DefCO.MSG_OWNER);
            miLibOwner.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    ownerDialog.getOwners();
                    ownerDialog.setVisible(true);
                    pnlFilter.cbPeriodPressed();
                }
            });
            menuLib.add(miLibOwner);
        }

        if (Connect.isRole(ZpRole.ROLE_LIB_SHOP_SHIFT))
        {
            JMenuItem miLibShopShift = new JMenuItem(DefCO.MSG_SHOP_SHIFT);
            miLibShopShift.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    shopShiftDialog.setData(pnlFilter.getPeriodIndex());
                    shopShiftDialog.setVisible(true);
                }
            });
            menuLib.add(miLibShopShift);
        }

        if (Connect.isRole(ZpRole.ROLE_CHANGE_PLAN))
        {
            JMenuItem miLibPlan = new JMenuItem(DefCO.MSG_PlAN);
            miLibPlan.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    planDialog.setData(pnlFilter.getPeriodIndex());
                    planDialog.getPlan(null);
                    planDialog.setVisible(true);
                }
            });
            menuLib.add(miLibPlan);
        }

        mb.add(menuLib);

        if (Connect.isRole(ZpRole.ROLE_REPORT))
        {
            JMenu menuReport = new JMenu(DefCO.MSG_REPORTS);

            if (Connect.isEvild() || Connect.isAdmin())
            {
                JMenuItem miReportSession = new JMenuItem(DefCO.MSG_SESSIONS);
                miReportSession.setActionCommand(miReportSession.getText());
                miReportSession.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        sessionDialog.setObject();
                        sessionDialog.setVisible(true);
                    }
                });
                menuReport.add(miReportSession);
            }

            JMenuItem miReportSocondJob = new JMenuItem(ManEditDialog.COL_NAMES_MAN[0]);
            miReportSocondJob.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    reportDialog.setData(pnlFilter.getPeriod(), ManEditDialog.COL_NAMES_MAN[0], null);
                    reportDialog.setVisible(true);
                }
            });
            menuReport.add(miReportSocondJob);

            JMenuItem miReportVt11 = new JMenuItem(DefCO.MSG_MAN_SCORE);
            miReportVt11.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    reportDialog.setData(pnlFilter.getPeriod(),
                            DefCO.MSG_MAN_SCORE, pnlFilter.getManScore());
                    reportDialog.setVisible(true);
                }
            });
            menuReport.add(miReportVt11);

            JMenuItem miReportSmena = new JMenuItem(DefCO.MSG_SMENA);
            miReportSmena.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    reportDialog.setData(pnlFilter.getPeriod(), DefCO.MSG_SMENA, null);
                    reportDialog.setVisible(true);
                }
            });
            menuReport.add(miReportSmena);

            JMenuItem miReportSmenaPay = new JMenuItem(DefCO.MSG_SMENA_PAY);
            miReportSmenaPay.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    reportDialog.setData(pnlFilter.getPeriod(), DefCO.MSG_SMENA_PAY, null);
                    reportDialog.setVisible(true);
                }
            });
            menuReport.add(miReportSmenaPay);

            JMenuItem miReportPlanCnt = new JMenuItem(DefCO.MSG_PLAN_CNT);
            miReportPlanCnt.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    reportDialog.setData(pnlFilter.getPeriod(), DefCO.MSG_PLAN_CNT, null);
                    reportDialog.setVisible(true);
                }
            });
            menuReport.add(miReportPlanCnt);


            mb.add(menuReport);
        }

        return mb;
    }

    private void changePassPerformed()
    {
        if (changePassDialog == null)
            changePassDialog = new ChangePassDialog(this);
        changePassDialog.showMan(Connect.getClient());
    }

    //
//    private void execInstruction()
//    {
//        String fileName = "";
//        if (Connect.isCustomer())
//        {
//            if (Connect.isAdmin())
//                fileName = "admin.doc";
//            else fileName = "customer.doc";
//        } else if (Connect.isSupplier()) fileName = "supplier.doc";
//        try
//        {
//            InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
//            if (inputStream != null)
//            {
//                String fullpath = DefCO.JAVA_TMP_DIR +
//                        DefCO.SPLIT + fileName;
//
//                OutputStream out = new FileOutputStream(new File(fullpath));
//
//                int read = 0;
//                byte[] bytes = new byte[1024];
//
//                while ((read = inputStream.read(bytes)) != -1)
//                {
//                    out.write(bytes, 0, read);
//                }
//
//                inputStream.close();
//                out.flush();
//                out.close();
//
//                Desktop.getDesktop().open(new File(fullpath));
//            } else DefCO.showInf(this, "Находится в разработке.");
//        } catch (Exception ex)
//        {
//            DefCO.showError(this, ex);
//        }
//    }
//
    private void exitPerformed()
    {
        try
        {
            ZpSession session = Connect.getClient().getSession();
            session.setStatusId(ZpSession.ST_FINISH);
            ZpSession newSession = Connect.getZpRemote().setSession(session);
            Connect.getClient().setSession(newSession);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }

        System.exit(0);
    }

    public void findOneSession(Long sessionId)
    {
        if (sessionId == null)
        {
            DefCO.showInf(this, "Данные никто не изменял.");
        } else
        {
            try
            {
                ZpSession session = Connect.getZpRemote().findOneSession(sessionId);

                String name = session.getClient().getName();
                String sd = DefCO.dateTimeSecToStr(session.getSd());
                String fd = DefCO.dateTimeSecToStr(session.getFd());
                DefCO.showInf(this, "Данные изменял(а) " + name + "\nв период с " + sd + " по " + fd + ".");
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        }
    }
//
//    private void execReport(ActionEvent e)
//    {
//        if (reportDialog == null)
//            reportDialog = new ReportDialog(this);
//
//        String reportName = e.getActionCommand();
//
//        TpReportParams reportParams =
//                new TpReportParams(reportName);
//
//        if (TpReportParams.REPORT_NAMES[0].equals(reportName))
//        {
//            reportDialog.setObject(reportParams);
//            reportDialog.setVisible(true);
//        } else if (TpReportParams.REPORT_NAMES[1].equals(reportName))
//        {
//            ZpPurchase purchase = pnlPurchase.getSelTpPurchase();
//            if (purchase == null)
//            {
//                DefCO.showInf(this, DefCO.MSG_SELECTED + " закупку.");
//            } else
//            {
//                reportParams.setPurchaseId(purchase.getId());
//                reportDialog.setObject(reportParams);
//                reportDialog.btnSave_actionPerformed(null);
//            }
//        }
//    }
}
