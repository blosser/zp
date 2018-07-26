package ru.ittrans.zp.client.def;

import ru.ittrans.zp.client.ErrorDialog;
import ru.ittrans.zp.client.FilterPanel;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpSession;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:45:04
 * To change this template use File | Settings | File Templates.
 */
public class DefCO
{
    public static final String MSG_INPUT = "�������";
    public static final String MSG_SELECTED = "��������";

    public static final String MSG_SELECT = "�������";

    public static final String MSG_SUPPLIER = "���������";
    public static final String MSG_SUPPLIERS = MSG_SUPPLIER + "�";

    public static final String MSG_CUSTOMER = "��������";
    public static final String MSG_CUSTOMERS = MSG_CUSTOMER + "�";

    public static final String MSG_ADMIN = "�������������";

    public static final String MSG_PURCHASE = "�������";
    public static final String MSG_PURCHASES = "�������";

    public static final String MSG_MAN = "��������";
    public static final String MSG_MANS = "���������� ����";

    public static final String MSG_ADD = "��������";
    public static final String MSG_UPD = "��������";
    public static final String MSG_DEL = "�������";

    public static final String MSG_CLIENT = "������������";

    public static final String MSG_OWNER = "�����������";

    public static final String MSG_OWNER_MAN = "�������";

    public static final String MSG_QUOTE = "������������ ������";
    public static final String MSG_QUOTES = "������������ ������";

    public static final String MSG_DOC = "��������";
    public static final String MSG_DOCS = MSG_DOC + "�";

    public static final String MSG_DOCS_USTAV = "�������� ���������";

    public static final String MSG_MSG_PURCHASE_MAIN = "����� ��������";
    public static final String MSG_MSG_PURCHASE_ADDITIONAL = "�������������";

    public static final String MSG_POSITION = "�������";

    public static final String MSG_ITOG = "����� ������";
    public static final String MSG_CATEGORY = "���������";

    public static final String MSG_SHOP = "�������";

    public static final String MSG_VICTORY = "����������";

    public static final String MSG_STATUS = "������";

    public static final String MSG_VIEW = "��������";

    public static final String MSG_REPORTS = "������";

    public static final String MSG_MAN_SCORE = "����";

    public static final String MSG_SMENA = "�����";

    public static final String MSG_SMENA_PAY = "C���� � �������";

    public static final String MSG_PLAN_CNT = "���� �� �����";

    public static final String MSG_PURCHASE_ACTIVE = "����� ������ ���� �������.";

    public static final String MSG_EXEC = "���������";

    public static final String MSG_ALL = "���";

    public static final String MSG_SESSIONS = "������ ������";

    public static final String MSG_DOC_EDIT = "�������� ����� ������ ���� ���������.";

    public static final String MSG_SPAM = "��������";

    public static final String MSG_SAVE = "���������";

    public static final String MSG_SAVE_AS = "��������� ���...";

    public static final String MSG_IMPORT = "������";

    public static final String MSG_LIB = "����������";

    public static final String MSG_IMPORT_DATA = "������ ������";

    public static final String MSG_BATCH = "�����";
    public static final String MSG_FILE = "����";

    public static final String MSG_ATTR_VALUE = "�������-��������";

    public static final String MSG_SHOP_SHIFT = "����� �� ���������";

    public static final String MSG_PlAN = "����";

    public static final int INS = 5;

    public static final Insets INSETS = new Insets(DefCO.INS, DefCO.INS, DefCO.INS, DefCO.INS);
    public static final Insets INSETS0 = new Insets(0, 0, 0, 0);
    public static final Insets INSETS_0LBR = new Insets(0, DefCO.INS, DefCO.INS, DefCO.INS);
    public static final Insets INSETS_0L0R = new Insets(0, DefCO.INS, 0, DefCO.INS);

    public static final String JAVA_TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String SPLIT = System.getProperty("file.separator");

    public static final String EXT_RTF = ".rtf";

    public static final int MIN_LENGHT_PASS = 6;

    public static final String MES_SIMPLE_PASS = "��� ������ ������� �������. ������������ ������� ���.\n" +
            "� ������� ���� ��������� �������� ����� ���� �������->����� ������...";

    private static void showMessage(Component parentComponent,
                                    String message, int messageType)
    {
        JOptionPane.showMessageDialog(parentComponent, message,
                "��������", messageType);
    }

    public static void showError(DefDialog d, Exception ex)
    {
        showError(d, ex.getMessage(), stackToString(ex));
    }

    private static void showError(DefDialog d, String msg, String txt)
    {
        System.out.println(txt);
        ErrorDialog ed = new ErrorDialog(d);
        ed.setObject(msg, txt);
    }

    public static void showError(JFrame d, Exception ex)
    {
        showError(d, ex.getMessage(), stackToString(ex));
    }

    private static void showError(JFrame d, String msg, String txt)
    {
        System.out.println(txt);
        ErrorDialog ed = new ErrorDialog(d);
        ed.setObject(msg, txt);
    }

    public static void showSelected(Component parentComponent)
    {
        showInf(parentComponent, MSG_SELECTED + " ������.");
    }

    public static void showInf(Component parentComponent, String msg)
    {
        showMessage(parentComponent, msg, JOptionPane.INFORMATION_MESSAGE);
    }

    public static final String stackToString(Exception ex)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return "can not stackToString";
        }
    }

    public static final void inputMessage(Component parentComponent, String msg)
    {
        inputMessage(parentComponent, msg, null);
    }

    public static final void inputMessage(Component parentComponent,
                                          String msg1,
                                          String msg2)
    {
        showInf(parentComponent, MSG_INPUT + " ���� \"" +
                msg1 + "\"" + (msg2 == null ? "" : "\n" + msg2));
    }

    public static final void selectedMessage(Component parentComponent, String msg)
    {
        showInf(parentComponent, MSG_SELECTED + " " + msg);
    }

    public static boolean isDel(String mes)
    {
        return JOptionPane.showConfirmDialog(null,
                mes,
                "��������",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static boolean isDel()
    {
        return isDel("�� ������������� ������ ������� ������� ������?");
    }

//    public static final Long getObjectId(String classname, Object object)
//    {
//        Long objectId = null;
//        if (object == null) return null;
//        if (TpCustomer.NAME.equals(classname))
//        {
//            if (object instanceof TpCustomer)
//                objectId = ((TpCustomer) object).getId();
//        } else if (TpSupplier.NAME.equals(classname))
//        {
//            if (object instanceof TpSupplier)
//                objectId = ((TpSupplier) object).getId();
//        } else if (TpPurchase.NAME.equals(classname) ||
//                TpDocument.PROTOCOL.equals(classname))
//        {
//            if (object instanceof TpPurchase)
//                objectId = ((TpPurchase) object).getId();
//        }
//        return objectId;
//    }

    public static final void debugException(Exception ex)
    {
        System.err.println(stackToString(ex));
    }

    public static final void debugInfo(String str)
    {
        System.out.println(str);
    }

    public static String DATE_FORMAT_STR = "dd.MM.yyyy";
    private static SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat(DATE_FORMAT_STR);

    public static String DATE_FORMAT_MM_YYYY_STR = "MMyyyy";
    private static SimpleDateFormat DATE_FORMAT_MM_YYYY =
            new SimpleDateFormat(DATE_FORMAT_MM_YYYY_STR);

    public static String dateToStr(Date d)
    {
        if (d == null) return null;
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
        return DATE_FORMAT.format(d);
    }

    public static String dateMMYYYYToStr(Date d)
    {
        if (d == null) return null;
        DATE_FORMAT_MM_YYYY.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
        return DATE_FORMAT_MM_YYYY.format(d);
    }

    private static final String[] MONTH_OF_YEAR = new String[]
            {"������", "�������", "�����", "������", "���", "����",
                    "����", "�������", "��������", "�������", "������", "�������"};

    public static String dateToStrDoc(Date d)
    {
        if (d == null) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(d.getTime()));

        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yyyy = calendar.get(Calendar.YEAR);

        String str = "<" + dd + "> " + MONTH_OF_YEAR[mm] + " " + yyyy;

        return str;
    }

    public static Date strToDate(String str)
    {
        if (str == null || str.trim().length() == 0) return null;
        try
        {
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
            return new Date(DATE_FORMAT.parse(str).getTime());
        } catch (Exception ex)
        {
            debugException(ex);
        }
        return null;
    }

    public static void requestFocus(final JComponent c)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                c.requestFocus();
            }
        });
    }

    public static final int BTN_SMALL_SIZE = 20;

    public static void setComponentSize(JComponent btn, Dimension d)
    {
        btn.setPreferredSize(d);
        btn.setMinimumSize(d);
        btn.setMaximumSize(d);
    }

    public static final String RESOURCE_IMAGES = "/images/";

    public static final String MY_ICON_PATH =
            "..//resource//images//";

    public static String GIF_DOT3_16 = "dot3.gif";
    public static String PNG_FLAG_W = "flag_w.png";
    public static String PNG_EMAIL_Y = "email_y.png";
    //public static String PNG_MAN_A = "man_a.png";
    public static String PNG_MAN_S = "man_s.png";
    public static String PNG_MAN_C = "man_c.png";
    public static String PNG_MANS = "mans.png";
    public static String PNG_PEN = "pen.png";
    public static String PNG_BADD = "badd.png";
    public static String PNG_BUPDATE = "bupdate.png";
    public static String PNG_BDELETE = "bdelete.png";
    public static String PNG_UNRELIABLE = "unreliable.png";
    public static String PNG_NEW = "new.png";
    public static String PNG_ACTIVE = "active.png";
    public static String PNG_CANCEL = "cancel.png";
    public static String PNG_BAG = "bag.png";
    public static String PNG_STATUS = "status.png";
    public static String PNG_REFRESH = "refresh.png";
    //public static String PNG_BLANK = "blank.png";
    public static String PNG_ALL_PURCHASE = "all_purchase.png";
    public static String PNG_EXEC = "exec.png";
    public static String PNG_SAVE = "save.png";
    public static String PNG_EXCEL = "excel.gif";
    public static String PNG_SESSION = "user.png";
    public static String PNG_RIGHT = "right.png";
    public static String PNG_DOWN = "down.png";

    public static ImageIcon loadIcon(final String fileName)
    {
        if (fileName == null) return null;
        URL url = DefCO.class.getResource(RESOURCE_IMAGES + fileName);
        ImageIcon ii = null;
        if (url == null)
        {
            try
            {
                ii = new ImageIcon(MY_ICON_PATH + fileName);
            } catch (Exception ex)
            {
                debugException(ex);
            }
        } else
        {
            ii = new ImageIcon(url);
        }
        return ii;
    }

    /*
        public static synchronized Icon getIcon(TpStatus status)
        {
            String iconName = null;
            Long grId = status.getGroupId();
            Long id = status.getId();
            if (TpGroup.GROUP_SESSION.equals(grId))
            {
                if (TpSession.ST_FINISH.equals(id))
                    iconName = PNG_FLAG_W;
                else if (TpSession.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
                else if (TpSession.ST_BLOCK.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpSession.ST_LOST.equals(id))
                    iconName = PNG_CANCEL;
            } else if (TpGroup.GROUP_QUOTE.equals(grId))
            {
                if (TpQuote.ST_CANCEL.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpQuote.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
            } else if (TpGroup.GROUP_PURCHASE.equals(grId))
            {
                if (TpPurchase.ST_CANCEL.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpPurchase.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
                else if (TpPurchase.ST_PREPEAR.equals(id))
                    iconName = PNG_NEW;
                else if (TpPurchase.ST_FINISH.equals(id))
                    iconName = PNG_FLAG_W;
                else if (TpPurchase.ST_FINISH_CANCEL.equals(id))
                    iconName = PNG_UNRELIABLE;
                else if (TpStatus.ALL.equals(id))
                    iconName = PNG_ALL_PURCHASE;
            } else if (TpGroup.GROUP_MAN.equals(grId))
            {
                if (TpMan.ST_BLOCK.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpMan.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
                else if (TpMan.ST_NEW.equals(id))
                    iconName = PNG_NEW;
                else if (TpMan.ST_CHECK_EMAIL.equals(id))
                    iconName = PNG_EMAIL_Y;
            } else if (TpGroup.GROUP_CUSTOMER.equals(grId))
            {
                if (TpCustomer.ST_BLOCK.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpCustomer.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
            } else if (TpGroup.GROUP_SUPPLIER.equals(grId))
            {
                if (TpSupplier.ST_NEW.equals(id))
                    iconName = PNG_NEW;
                else if (TpSupplier.ST_BLOCK.equals(id))
                    iconName = PNG_CANCEL;
                else if (TpSupplier.ST_ACTIVE.equals(id))
                    iconName = PNG_ACTIVE;
                else if (TpSupplier.ST_UNRELIABLE.equals(id))
                    iconName = PNG_UNRELIABLE;
            }

            return DefCO.loadIcon(iconName);
        }

        public static synchronized Icon getManIcon(TpManType mt)
        {
            String iconName = null;
            String classname = mt.getClassname();
            if (TpCustomer.NAME.equals(classname))
            {
                iconName = PNG_MAN_C;
            } else if (TpSupplier.NAME.equals(classname))
            {
                iconName = PNG_MAN_S;
            }

            return DefCO.loadIcon(iconName);
        }

        public static synchronized String getManType(TpManType mt)
        {
            String res = "";
            String classname = mt.getClassname();
            if (TpCustomer.NAME.equals(classname))
            {
                res = MSG_CUSTOMER;
            } else if (TpSupplier.NAME.equals(classname))
            {
                res = MSG_SUPPLIER;
            }

            return res;
        }
    */
    public static synchronized String getClientText(ZpClient client)
    {
        String res = client.getName();

        return res;
    }

    /*
        public static String nvl(String str)
        {
            return str == null ? "" : str;
        }

        private static final String[] KEY_ARRAY = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2",
                "3", "4", "5", "6", "7", "8", "9"};

        public static String getActiveKey()
        {
            String res = "";

            for (int i = 0; i < 40; i++)
            {
                res = res + KEY_ARRAY[(int) (Math.random() * KEY_ARRAY.length)];
            }

            return res;
        }
    */
    public static Dimension getMaxWindowsSize(Window d)
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        GraphicsConfiguration config = d.getGraphicsConfiguration();
        Insets insets = kit.getScreenInsets(config);
        screenSize.width -= (insets.left + insets.right);
        screenSize.height -= (insets.top + insets.bottom);
        return screenSize;
    }

    public static void setMaxWindowsSize(Window d)
    {
        Dimension dm = getMaxWindowsSize(d);
        d.setSize(dm);
    }

    public static final String longToStr(Long value)
    {
        return value == null ? "" : value.toString();
    }

    public static final Long strToLong(String value)
    {
        return value == null || value.trim().length() == 0 ? null : Long.valueOf(value);
    }

    public static String DATE_TIME_FORMAT_STR = "dd.MM.yyyy HH:mm";
    private static SimpleDateFormat DATE_TIME_FORMAT = null;
    //new SimpleDateFormat(DATE_TIME_FORMAT_STR);

    public static String dateTimeToStr(Timestamp d)
    {
        if (d == null) return "";
        if (DATE_TIME_FORMAT == null)
        {
            DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STR);
            DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
        }
        return DATE_TIME_FORMAT.format(d);
    }

    public static String DATE_TIME_SEC_FORMAT_STR = "dd.MM.yyyy HH:mm:ss";
    private static SimpleDateFormat DATE_TIME_SEC_FORMAT = null;

    public static String dateTimeSecToStr(Timestamp d)
    {
        if (d == null) return "";
        if (DATE_TIME_SEC_FORMAT == null)
        {
            DATE_TIME_SEC_FORMAT = new SimpleDateFormat(DATE_TIME_SEC_FORMAT_STR);
            DATE_TIME_SEC_FORMAT.setTimeZone(TimeZone.getTimeZone(ZpSession.GMT));
        }
        return DATE_TIME_SEC_FORMAT.format(d);
    }

    public static String isDigit(DefNumberTextField numberTextField)
    {
        String str = numberTextField.getText();
        int limit = numberTextField.getLimit();
        if (limit == -1 || str == null || str.length() == 0) return null;

        String res = "������ ������ �������� �� " + limit + " ����.";
        if (str.length() != limit)
            return res;

        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i)))
                return res;

        return null;
    }

    public static synchronized void setPeriodData(DefComboBox cbPeriod, int periodIndex)
    {
        List<String> myList = new ArrayList<String>();
        for (int y = FilterPanel.start_year; y < FilterPanel.start_year + 15; y++)
            for (int m = 1; m <= 12; m++)
            {
                if (y == FilterPanel.start_year && m < FilterPanel.start_month) continue;
                String str = (m < 10 ? "0" : "") + m + "." + y;
                myList.add(str);
            }
        DefComboBox.cbAddItem(cbPeriod, myList);
        cbPeriod.setSelectedIndex(periodIndex);
    }

    public static synchronized Date getCbPeriod(DefComboBox cbPeriod, int dd)
    {
        String mmyyyy = cbPeriod.getSelectedItem().toString();
        return DefCO.strToDate((dd < 10 ? "0" : "") + String.valueOf(dd) + "." + mmyyyy);
    }

    public static synchronized Date getCbPeriod(DefComboBox cbPeriod)
    {
        return getCbPeriod(cbPeriod, 1);
    }

    public static String convertStringToWeb(String str) throws java.io.UnsupportedEncodingException
    {
        String res = java.net.URLEncoder.encode(str.toString().replaceAll(" ", "Z"), "windows-1251");
        res = res.replaceAll("Z", "%20");
        return res;
    }
}
/*
���
������������
�������������
�������������
��������������� ���
������������
���������
������
��������� (��������-���������� �����)
��������� (��������� �����)
��������
������������
���������������
�����������
����������
�������
�����������
�������
��������
���������� ����� ������
���������� ����
�����������
����������
������������ ���
�������������
������� �������� �������
������� ������� ��������
��������� �����
����������
����
���������
������ �������
����������
������������� ��������
��������
�������������
��������� ����
�����������
������
������� �����
������
�����������
������������
�������������
�����������
��������
��������
���������
������������
���������
��������������
���������
���������
��������
�����-�����
�����������
�����������
�������������
��������
�����������������
�����������������
��������������
������� ������
������������ �������
�������������
����������
��������� ����
���������
����������
�������
�����������
��������� ��������
�������
�������
������������
������� ����
�������
����������
����������
�������������
�������������
������
����������
��������
�����������
��������
����������� ��������
�����������
�������������
��������������
��������������
����� ���������
�����������
����������� ����
�������
��������
����������
����������
���� ��������
���� ������
������������
������������
������
���������-�����������
���������
����������
���������
������� ������
������� ���������
������������
�������
��������
�������������� �������
������������
�������� �����������
�������� ����
�����������
����������
������ ������
�������
�������
��������� ��������
�����������
��������
���������������
�����������
������������
���������� �������
���������� (��������-���������� �����)
���������� (��������� �����)
�����
����������
����������
���������� �������
��������
������������
�����������
�����������
���������
��������
�����������
������������
����������
�������������
�������������
�������
��������
������������
���������
����� 1905����
����� ��������� ������
����� ���������
����� ������������
����� ������������
����� ����������������
�����������
��������� ����
����
�����������
��������
������� �������
������������
������������
���������
������ �����
����������
�����������
�����������
����� �����������
����������
���������
����������������
���-��������
�����
�������
* */