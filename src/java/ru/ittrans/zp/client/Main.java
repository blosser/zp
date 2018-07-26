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

            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Отмена");

            UIManager.put("FileChooser.openButtonText", "Открыть");
            UIManager.put("FileChooser.cancelButtonText", "Отмена");
            UIManager.put("FileChooser.lookInLabelText", "Смотреть в");
            UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
            UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла");

            UIManager.put("FileChooser.saveButtonText", "Сохранить");
            UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
            UIManager.put("FileChooser.openButtonText", "Открыть");
            UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
            UIManager.put("FileChooser.cancelButtonText", "Отмена");
            UIManager.put("FileChooser.cancelButtonToolTipText", "Отмена");

            UIManager.put("FileChooser.lookInLabelText", "Папка");
            UIManager.put("FileChooser.saveInLabelText", "Папка");
            UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
            UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файлов");

            UIManager.put("FileChooser.upFolderToolTipText", "На один уровень вверх");
            UIManager.put("FileChooser.newFolderToolTipText", "Создание новой папки");
            UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
            UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
            UIManager.put("FileChooser.fileNameHeaderText", "Имя");
            UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
            UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
            UIManager.put("FileChooser.fileDateHeaderText", "Изменен");
            UIManager.put("FileChooser.fileAttrHeaderText", "Атрибуты");

            UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");

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
            // пока в книге смотрим первый лист
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

                // декабрь
                List<String> l122010 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("AC") + i);
                    l122010.add(d);
                }
                // штраф, дорога, коррекия, ведомость, комментарий
                l122010.add("");
                l122010.add("");
                l122010.add("");
                l122010.add(CO.getValueStr(evaluator, row, CO.getColIndex("BO")));
                l122010.add("");

                // январь
                List<String> l012011 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("BP") + i);
                    l012011.add(d);
                }
                // штраф, дорога, коррекия, ведомость, комментарий
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("CW")));
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("CX")));
                l012011.add("");
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("DA")));
                l012011.add(CO.getValueStr(evaluator, row, CO.getColIndex("DB")));

                // февраль
                List<String> l022011 = new ArrayList<String>();
                for (int i = 0; i < 28; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("DC") + i);
                    l022011.add(d);
                }
                // штраф, дорога, коррекия, ведомость, комментарий
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EG")));
                l022011.add("");
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EH")));
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EJ")));
                l022011.add(CO.getValueStr(evaluator, row, CO.getColIndex("EK")));

                // март
                List<String> l032011 = new ArrayList<String>();
                for (int i = 0; i < 31; i++)
                {
                    String d = CO.getValueStr(row, CO.getColIndex("EL") + i);
                    l032011.add(d);
                }
                // штраф, дорога, коррекия, ведомость, комментарий
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
                                "с".equals(second_job.toLowerCase())) ?
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
