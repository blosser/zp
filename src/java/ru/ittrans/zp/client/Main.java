package ru.ittrans.zp.client;

import ru.ittrans.zp.io.ZpSession;

import javax.naming.Context;
import javax.swing.*;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 22.04.11
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class Main
{
    public static void m(String[] args)
    {
        try
        {
            for (String a : args)
            {
                System.out.println("get " + a);
                if (a.contains(Context.PROVIDER_URL))
                {
                    String k = Context.PROVIDER_URL;
                    String v = a.replace(Context.PROVIDER_URL + "=", "");
                    System.out.println("set " + k + "=" + v);
                    System.setProperty(k, v);
                }
            }

            UIManager.put("OptionPane.yesButtonText", "��");
            UIManager.put("OptionPane.noButtonText", "���");
            UIManager.put("OptionPane.cancelButtonText", "������");

            UIManager.put("FileChooser.openButtonText", "�������");
            UIManager.put("FileChooser.cancelButtonText", "������");
            UIManager.put("FileChooser.lookInLabelText", "�������� �");
            UIManager.put("FileChooser.fileNameLabelText", "��� �����");
            UIManager.put("FileChooser.filesOfTypeLabelText", "��� �����");

            UIManager.put("FileChooser.saveButtonText", "���������");
            UIManager.put("FileChooser.saveButtonToolTipText", "���������");
            UIManager.put("FileChooser.openButtonText", "�������");
            UIManager.put("FileChooser.openButtonToolTipText", "�������");
            UIManager.put("FileChooser.cancelButtonText", "������");
            UIManager.put("FileChooser.cancelButtonToolTipText", "������");

            UIManager.put("FileChooser.lookInLabelText", "�����");
            UIManager.put("FileChooser.saveInLabelText", "�����");
            UIManager.put("FileChooser.fileNameLabelText", "��� �����");
            UIManager.put("FileChooser.filesOfTypeLabelText", "��� ������");

            UIManager.put("FileChooser.upFolderToolTipText", "�� ���� ������� �����");
            UIManager.put("FileChooser.newFolderToolTipText", "�������� ����� �����");
            UIManager.put("FileChooser.listViewButtonToolTipText", "������");
            UIManager.put("FileChooser.detailsViewButtonToolTipText", "�������");
            UIManager.put("FileChooser.fileNameHeaderText", "���");
            UIManager.put("FileChooser.fileSizeHeaderText", "������");
            UIManager.put("FileChooser.fileTypeHeaderText", "���");
            UIManager.put("FileChooser.fileDateHeaderText", "�������");
            UIManager.put("FileChooser.fileAttrHeaderText", "��������");

            UIManager.put("FileChooser.acceptAllFileFilterText", "��� �����");

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            System.out.println("TimeZone " + java.util.TimeZone.getDefault());
            java.util.TimeZone.setDefault(TimeZone.getTimeZone(ZpSession.GMT));
            System.out.println("TimeZone " + java.util.TimeZone.getDefault());

            LoginDialog ld = new LoginDialog();
            ld.setVisible(true);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(final String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                m(args);
            }
        });

/*
        System.out.println("begin");
        try
        {
            FileInputStream fileIn = new FileInputStream("C:\\projects\\zp\\resource\\s11.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(fileIn);
            // ���� � ����� ������� ������ ����
            HSSFSheet sheet = workbook.getSheetAt(1);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            List<ZpMan> manList = new ArrayList<ZpMan>();

            for (Row row : sheet)
            {
                if (row.getRowNum() < 2 || row.getRowNum() > 5000) continue;

                String second_job = CO.getValueStr(row, CO.getColIndex("A"));
                String tab_number = CO.getValueStr(row, CO.getColIndex("B"));
                String name1 = CO.getValueStr(row, CO.getColIndex("F"));
                if (name1 == null || name1.trim().length() == 0) continue;
                String phone = CO.getValueStr(row, CO.getColIndex("G"));
                String birthday = CO.getValueStr(row, CO.getColIndex("H"));

                String owner_man_name = CO.getValueStr(row, CO.getColIndex("J"));
                String owner = CO.getValueStr(row, CO.getColIndex("L"));
                String owner_man_sd = CO.getValueStr(row, CO.getColIndex("M"));
                String owner_man_fd = CO.getValueStr(row, CO.getColIndex("N"));

                // �������
                List<String> l122010 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("AC") + i);
                    l122010.add(d);
                }
                // �����, ������, ��������, ���������, �����������
                l122010.add("");
                l122010.add("");
                l122010.add("");
                l122010.add(CO.getValueStr(evaluator, row, CO.getColIndex("BO")));
                l122010.add("");

                // ������
                List<String> l012011 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("BP") + i);
                    l012011.add(d);
                }
                // �����, ������, ��������, ���������, �����������
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("CW")));
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("CX")));
                l012011.add("");
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("DA")));
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("DB")));

                // �������
                List<String> l022011 = new ArrayList<String>();
                for (int i = 0; i < 28; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("DC") + i);
                    l022011.add(d);
                }
                // �����, ������, ��������, ���������, �����������
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EG")));
                l022011.add("");
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EH")));
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EJ")));
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EK")));

                // ����
                List<String> l032011 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("EL") + i);
                    l032011.add(d);
                }
                // �����, ������, ��������, ���������, �����������
                l032011.add(CO.getValueStr(evaluator, row, CO.getColIndex("FS")));
                l032011.add(CO.getValueStr(evaluator, row, CO.getColIndex("FT")));
                l032011.add(CO.getValueStr(evaluator, row, CO.getColIndex("FU")));
                l032011.add(CO.getValueStr(evaluator, row, CO.getColIndex("FW")));
                l032011.add("");

                System.out.print(second_job + "|" +
                        tab_number + "|" +
                        name1 + "|" +
                        phone + "|" +
                        birthday + "|" +
                        owner_man_name + "|" +
                        owner + "|" +
                        owner_man_sd + "|" +
                        owner_man_fd + "|");

                for (String d : l022011)
                    System.out.print(d + "|");

                System.out.println("");

                ZpMan man = new ZpMan();
                man.setName1(name1);
                man.setPhone(phone);
                man.setTabNumber(tab_number);
                man.setBirthday(DefCO.strToDate(birthday));
                man.setSecondJob(second_job != null &&
                        ("c".equals(second_job.toLowerCase()) ||
                                "�".equals(second_job.toLowerCase())) ?
                        ZpMan.ONE : ZpMan.ZERO);
                man.setActive(ZpMan.ONE);

                ZpOwnerMan ownerMan = new ZpOwnerMan();
                ownerMan.setName(owner_man_name);
                if (ZpOwner.OWNER_PARTNER.equals(owner))
                    ownerMan.setOwnerId(ZpOwner.OWNER_PARTNER_CODE);
                else if (ZpOwner.OWNER_RAIT.equals(owner))
                    ownerMan.setOwnerId(ZpOwner.OWNER_RAIT_CODE);
                ownerMan.setSd(DefCO.strToDate(owner_man_sd));
                ownerMan.setFd(DefCO.strToDate(owner_man_fd));
                man.setOwnerMan(ownerMan);

                man.l122010 = l122010;
                man.l012011 = l012011;
                man.l022011 = l022011;
                man.l032011 = l032011;

                manList.add(man);
            }
            fileIn.close();
            Connect.tprInit();
            Connect.getZpRemote().exportMans(manList);
        } catch (Exception ex)
        {
            System.err.println(ex);
        }
        System.out.println("end");
*/
    }
}
