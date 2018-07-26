package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpOwnerMan;

import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public class ManPrint
{
    public static synchronized void printMan(int reportType, ZpMan man)
    {
        Timestamp fd = Connect.getClient().getSession().getFd();
        Date dt = new Date(fd.getTime());
        String strDate = DefCO.dateToStrDoc(dt);

        if (reportType == -1)
        {
            printPartner(man, strDate);
            printRait(man, strDate);
            printPerspektiva(man, strDate);
            printEnergy(man, strDate);
            printLegion(man, strDate);
            printPenalty(man, strDate);
            printOtkaz(man, strDate);
        } else if (reportType == 0)
        {
            printPartner(man, strDate);
        } else if (reportType == 1)
        {
            printRait(man, strDate);
        } else if (reportType == 2)
        {
            printPerspektiva(man, strDate);
        } else if (reportType == 3)
        {
            printEnergy(man, strDate);
        } else if (reportType == 4)
        {
            printLegion(man, strDate);
        } else if (reportType == 5)
        {
            printPenalty(man, strDate);
        } else if (reportType == 6)
        {
            printOtkaz(man, strDate);
        }
    }

    public static synchronized void printPartner(ZpMan man, String strDate)
    {
        printDogovor("partner2", man, strDate);
    }

    public static synchronized void printRait(ZpMan man, String strDate)
    {
        printDogovor("rait2", man, strDate);
    }

    public static synchronized void printPerspektiva(ZpMan man, String strDate)
    {
        printDogovor("perspektiva2", man, strDate);
    }

    public static synchronized void printEnergy(ZpMan man, String strDate)
    {
        printDogovor("energy", man, strDate);
    }

    public static synchronized void printLegion(ZpMan man, String strDate)
    {
        printDogovor("legion", man, strDate);
    }

    public static synchronized void printDogovor(String fileType, ZpMan man, String strDate)
    {
        List<ZpOwnerMan> omList = man.getOwnerManList();

        String zodog1 = "<___>_____________";
        String zodog2 = zodog1;
        String docNum = "";
        if (!omList.isEmpty())
        {
            ZpOwnerMan om = omList.get(0);
            zodog1 = DefCO.dateToStrDoc(om.getSd());
            zodog2 = DefCO.dateToStrDoc(om.getFd());
            docNum = om.getName();
        }
        printFile(fileType,
                new String[]{"ZPDATE", "ZPFIO",
                        "ZPDOGSD", "ZPDOGFD",
                        "ZPPASSPORTNUMBER",
                        "ZPPASSPORTWHERE",
                        "ZPPASSPORTADDRESS",
                        "ZPPENSIONNUMBER",
                        "ZPINN", "ZPDOGNUM"},
                new String[]{strDate, man.getName1(),
                        zodog1, zodog2,
                        man.getPassportNumber(),
                        man.getPassportWhere(),
                        man.getPassportAddress(),
                        man.getPensionNumber(),
                        man.getInn(),
                        docNum
                });
    }

    public static synchronized void printPenalty(ZpMan man, String strDate)
    {
        printFile("penalty2",
                new String[]{"ZPDATE", "ZPFIO"},
                new String[]{strDate, man.getName1()});
    }

    public static synchronized void printOtkaz(ZpMan man, String strDate)
    {
        printFile("otkaz",
                new String[]{"ZPDATE", "ZPFIO"},
                new String[]{strDate, man.getName1()});
    }

    public static synchronized void printFile(String fileType,
                                              String[] inStr, String[] outStr)
    {
        try
        {
            String fullpath = DefCO.JAVA_TMP_DIR + DefCO.SPLIT +
                    fileType + System.currentTimeMillis() + DefCO.EXT_RTF;

            InputStream fis = ManPrint.class.getResourceAsStream("/" + fileType + DefCO.EXT_RTF);
            if (fis == null)
                fis = new FileInputStream(
                        new File("C:\\projects\\zp\\resource\\" + fileType + DefCO.EXT_RTF));


            if (fis != null)
            {
                String output = new Scanner(fis).useDelimiter("\\Z").next();

                for (int i = 0; i < inStr.length; i++)
                {
                    String str = inStr[i];
                    if (output.contains(str))
                    {
                        output = output.replaceAll(str, outStr[i]);
                    }
                }
                OutputStream out = new FileOutputStream(new File(fullpath));
                out.write(output.getBytes());

                fis.close();
                out.flush();
                out.close();

                Desktop.getDesktop().open(new File(fullpath));
            }

        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
