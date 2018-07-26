package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpOwnerMan;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:33:44
 * To change this template use File | Settings | File Templates.
 */
public class ManEditDialog extends DefDialog<ZpMan>
        implements ButtonSaveInterface, ButtonEditInterface
{
    public static String[] COL_NAMES_MAN =
            new String[]{
                    "Cовместитель", "Факт. магазин", "ФИО", "Телефон(11)", "Дата рождения", "Табель. №",
                    "Серия, № паспорта", "Прописка", "Регистрация", "Кем, когда выдан", "ИНН(12)",
                    "ПФР(11)", "Активен", "Черный список", /*"Факт. магазин",*/ "Счёт",//14
                    "Примечание", "На карту", "ЗДМ", "ТВП", "СК", "Служба без-ти",//20
                    "Дата", "Договор принят", "Дата", "Спец. моб.", "Моб.", "Зло", "ID"};

    private JLabel lbShop = new JLabel(COL_NAMES_MAN[1]);
    private DefComboBox<ZpShop> cbShop = new DefComboBox<ZpShop>();

    private JLabel lbName1 = new JLabel(COL_NAMES_MAN[2]);
    private DefTextField edtName1 = new DefTextField();

    private JLabel lbPhone = new JLabel(COL_NAMES_MAN[3]);
    private DefMaskFormatter mfPhone = null;
    private DefFormattedTextField edtPhone = null;

    private JLabel lbBirthday = new JLabel(COL_NAMES_MAN[4]);
    private DefDatePicker dpBirthday = new DefDatePicker();

    private JLabel lbTabNumber = new JLabel(COL_NAMES_MAN[5]);
    private DefNumberTextField edtTabNumber = new DefNumberTextField();
    private JLabel lbTabNumber2 = new JLabel(COL_NAMES_MAN[5] + "2");
    private DefNumberTextField edtTabNumber2 = new DefNumberTextField();

    private static final String[] PASSPORT_TYPE = new String[]{"RUS", "BLR", "ДР."};

    private static final String[] PASSPORT_BLR_SER = new String[]
            {"AB", "BM", "HB", "KH", "MP", "MC", "KB", "PP"};

    private JLabel lbPassportNumber = new JLabel(COL_NAMES_MAN[6]);
    private DefComboBox<String> cbPassportType = new DefComboBox<String>();
    private DefComboBox<String> cbPassportBlrSer = new DefComboBox<String>();
    private DefMaskFormatter mfPassportRus = null;
    private DefMaskFormatter mfPassportBlr = null;
    private DefTextField edtPassportDr = new DefTextField();
    private DefFormattedTextField edtPassportRus = null;
    private DefFormattedTextField edtPassportBlr = null;

    private JLabel lbPassportAddress = new JLabel(COL_NAMES_MAN[7]);
    private DefTextField edtPassportAddress = new DefTextField();

    private JLabel lbRegAddress = new JLabel(COL_NAMES_MAN[8]);
    private DefTextField edtRegAddress = new DefTextField();

    private JLabel lbPassportWhere = new JLabel(COL_NAMES_MAN[9]);
    private DefTextField edtPassportWhere = new DefTextField();

    private JLabel lbInn = new JLabel(COL_NAMES_MAN[10]);
    private DefMaskFormatter mfInn = null;
    private DefFormattedTextField edtInn = null;

    private JLabel lbPensionNumber = new JLabel(COL_NAMES_MAN[11]);
    private DefMaskFormatter mfPensionNumber = null;
    private DefFormattedTextField edtPensionNumber = null;

    private DefCheckBox cbSecondJob = new DefCheckBox(COL_NAMES_MAN[0], false);

    private DefCheckBox cbActive = new DefCheckBox(COL_NAMES_MAN[12], false);

    private DefCheckBox cbBlackList = new DefCheckBox(COL_NAMES_MAN[13], false);

    private DefCheckBox cbCard = new DefCheckBox(COL_NAMES_MAN[16], false);

    private DefCheckBox cbZdm = new DefCheckBox(COL_NAMES_MAN[17], false);
    private DefCheckBox cbTvp = new DefCheckBox(COL_NAMES_MAN[18], false);
    private DefCheckBox cbSk = new DefCheckBox(COL_NAMES_MAN[19], false);
    private DefCheckBox cbSpecMobile = new DefCheckBox(COL_NAMES_MAN[24], false);
    private DefCheckBox cbMobile = new DefCheckBox(COL_NAMES_MAN[25], false);
    private DefCheckBox cbZlo = new DefCheckBox(COL_NAMES_MAN[26], false);
    private DefCheckBox cbSb = new DefCheckBox(COL_NAMES_MAN[20], false);
    private DefDatePicker dpSb = new DefDatePicker();
    private DefCheckBox cbDog = new DefCheckBox(COL_NAMES_MAN[22], false);
    private DefDatePicker dpDog = new DefDatePicker();

    private JLabel lbManScore = new JLabel(COL_NAMES_MAN[14]);
    private DefTextField edtManScore = new DefTextField();

    private JLabel lbComment = new JLabel(COL_NAMES_MAN[15]);
    private DefTextField edtComment = new DefTextField();

    private DefTable tblOwnerMan = new DefTable(new TmOwnerMan(), false);

    private JScrollPane spOwnerMan = new JScrollPane(tblOwnerMan);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    private OwnerManEditDialog ownerManEditDialog = new OwnerManEditDialog(this);

    public ManEditDialog(JFrame f)
    {
        super(f, DefCO.MSG_MAN);
        jbInit();
    }

    public ManEditDialog(DefDialog f)
    {
        super(f, DefCO.MSG_MAN);
        jbInit();
    }

    protected void jbInit()
    {
        try
        {
            mfPhone = new DefMaskFormatter("#-###-###-##-##");
            edtPhone = new DefFormattedTextField(mfPhone);

            mfInn = new DefMaskFormatter("############");
            edtInn = new DefFormattedTextField(mfInn);

            mfPensionNumber = new DefMaskFormatter("###-###-###-##");
            edtPensionNumber = new DefFormattedTextField(mfPensionNumber);

            mfPassportRus = new DefMaskFormatter("####-######");
            mfPassportBlr = new DefMaskFormatter("#######");
            edtPassportRus = new DefFormattedTextField(mfPassportRus);
            edtPassportBlr = new DefFormattedTextField(mfPassportBlr);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlMan = new JPanel(new GridBagLayout());

        pnlMan.setBorder(BorderFactory.createTitledBorder("Контактное лицо"));

        ///////////////////////////////////////////////////////////////////////////

        pnlMain.add(pnlMan, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        pnlMan.add(lbTabNumber, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtTabNumber, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(lbTabNumber2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtTabNumber2, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbShop, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbShop, new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbName1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName1, new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPassportNumber, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        JPanel pnlPassport = new JPanel(new GridBagLayout());
        pnlPassport.add(cbPassportType, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS_0L0R, 0, 0));
        pnlPassport.add(cbPassportBlrSer, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS0, 0, 0));
        pnlPassport.add(edtPassportRus, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS_0L0R, 0, 0));
        pnlPassport.add(edtPassportBlr, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS_0L0R, 0, 0));
        pnlPassport.add(edtPassportDr, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS_0L0R, 0, 0));

        pnlMan.add(pnlPassport, new GridBagConstraints(1, 3, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, DefCO.INSETS0, 0, 0));

        pnlMan.add(lbPassportWhere, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPassportWhere, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbBirthday, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpBirthday, new GridBagConstraints(1, 5, 3, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPassportAddress, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPassportAddress, new GridBagConstraints(1, 6, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbRegAddress, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtRegAddress, new GridBagConstraints(1, 7, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPhone, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPhone, new GridBagConstraints(1, 8, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbInn, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtInn, new GridBagConstraints(3, 8, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPensionNumber, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPensionNumber, new GridBagConstraints(1, 10, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbManScore, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtManScore, new GridBagConstraints(3, 10, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbComment, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtComment, new GridBagConstraints(1, 12, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        JPanel pnlCb1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCb1.add(cbActive);
        pnlCb1.add(cbSecondJob);
        pnlCb1.add(cbCard);
        pnlCb1.add(cbBlackList);

        JPanel pnlCb12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCb12.add(cbZdm);
        pnlCb12.add(cbTvp);
        pnlCb12.add(cbSk);
        pnlCb12.add(cbMobile);
        pnlCb12.add(cbSpecMobile);
        pnlCb12.add(cbZlo);

        pnlMan.add(pnlCb1, new GridBagConstraints(0, 13, 4, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));
        pnlMan.add(pnlCb12, new GridBagConstraints(0, 14, 4, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        JPanel pnlCb2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCb2.add(cbSb);
        pnlCb2.add(dpSb);
        pnlCb2.add(cbDog);
        pnlCb2.add(dpDog);

        pnlMan.add(pnlCb2, new GridBagConstraints(0, 15, 4, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        pnlMan.add(spOwnerMan, new GridBagConstraints(0, 16, 4, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(bep, new GridBagConstraints(0, 17, 4, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        cbPassportType.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                boolean isRus = cbPassportType.getSelectedIndex() == 0;
                boolean isBlr = cbPassportType.getSelectedIndex() == 1;
                boolean isDr = cbPassportType.getSelectedIndex() == 2;
                cbPassportBlrSer.setVisible(isBlr);
                edtPassportRus.setVisible(isRus);
                edtPassportBlr.setVisible(isBlr);
                edtPassportDr.setVisible(isDr);
                edtPassportRus.setValue(null);
                edtPassportBlr.setValue(null);
                edtPassportDr.setText("");
            }
        });

        setSize(600, 680);
        setLocationRelativeTo(getOwner());
    }

    public boolean checkString(String string) {
         if (string == null || string.length() == 0) return false;

         int i = 0;
         if (string.charAt(0) == '-') {
            if (string.length() == 1) {
               return false;
            }
            i = 1;
         }

         char c;
         for (; i < string.length(); i++) {
             c = string.charAt(i);
             if (!(c >= '0' && c <= '9')) {
                 return false;
             }
         }
         return true;
     }

    public void showMan(ZpMan man)
    {
        setObject(man);

        DefComboBox.cbAddItem(cbShop, DicManager.getShopMap(), true, false);
        DefComboBox.cbAddItem(cbPassportType, PASSPORT_TYPE);
        DefComboBox.cbAddItem(cbPassportBlrSer, PASSPORT_BLR_SER);

        cbSecondJob.setValue(man.getSecondJob());
        edtName1.setText(man.getName1());
        edtPhone.setMyText(man.getPhone());
        dpBirthday.setDate(man.getBirthday());
        edtTabNumber.setText(man.getTabNumber());
        edtTabNumber2.setText(man.getTabNumber2());

        boolean isExistsBlr = false;
        if (man.getPassportNumber() != null)
        {
            for (String str : PASSPORT_BLR_SER)
                if (man.getPassportNumber().contains(str))
                {
                    cbPassportType.setSelectedIndex(1);
                    cbPassportBlrSer.setSelectedItem(str);
                    edtPassportBlr.setMyText(man.getPassportNumber().replaceAll(str, ""));
                    isExistsBlr = true;
                    break;
                }
        }

        if (!isExistsBlr)
        {
            if (man.getPassportNumber() == null ||
                    (man.getPassportNumber().length() == 10 && checkString(man.getPassportNumber())))
            {
                cbPassportType.setSelectedIndex(0);
                edtPassportRus.setMyText(man.getPassportNumber());
            } else
            {
                cbPassportType.setSelectedIndex(2);
                edtPassportDr.setText(man.getPassportNumber());
            }
        }

        edtPassportAddress.setText(man.getPassportAddress());
        edtRegAddress.setText(man.getRegAddress());
        edtPassportWhere.setText(man.getPassportWhere());
        edtInn.setMyText(man.getInn());
        edtPensionNumber.setMyText(man.getPensionNumber());
        cbActive.setValue(man.getActive());
        cbBlackList.setValue(man.getBlackList());
        cbCard.setValue(man.getCard());
        cbZdm.setValue(man.getpZdm());
        cbTvp.setValue(man.getpTvp());
        cbSk.setValue(man.getpSk());
        cbSpecMobile.setValue(man.getSpecMobile());
        cbMobile.setValue(man.getMobile());
        cbZlo.setValue(man.getZlo());
        cbSb.setValue(man.getpSb());
        dpSb.setDate(man.getpSbDate());
        cbDog.setValue(man.getpDog());
        dpDog.setDate(man.getpDogDate());
        cbShop.setSelectedItem(DicManager.getShopMap().get(man.getShopId()));
        edtManScore.setText(man.getManScore());
        edtComment.setText(man.getComment());
        ((TmOwnerMan) tblOwnerMan.getModel()).setData(man.getOwnerManList());

        DefCO.requestFocus(edtTabNumber);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
        if (edtName1.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbName1.getText());
            return false;
        }

        if (!cbSecondJob.isSelected() && !cbSb.isSelected())
        {
            DefCO.showInf(this, "Не совместителю требуется проверка службы безопасности");
            return false;
        }

        if (cbSb.isSelected() && dpSb.getDate() == null)
        {
            DefCO.inputMessage(this, cbSb.getText());
            return false;
        }

        if (cbDog.isSelected() && dpDog.getDate() == null)
        {
            DefCO.inputMessage(this, cbDog.getText());
            return false;
        }

//        String resTabNumber = DefCO.isDigit(edtTabNumber);
//        if (resTabNumber != null)
//        {
//            DefCO.inputMessage(this, lbTabNumber.getText(), resTabNumber);
//            return false;
//        }

//        String resPassportNumber = DefCO.isDigit(edtPassportNumber);
//        if (resPassportNumber != null)
//        {
//            DefCO.inputMessage(this, lbPassportNumber.getText(), resPassportNumber);
//            return false;
//        }

//        String resPhone = DefCO.isDigit(edtPhone);
//        if (resPhone != null)
//        {
//            DefCO.inputMessage(this, lbPhone.getText(), resPhone);
//            return false;
//        }

//        String resInn = DefCO.isDigit(edtInn);
//        if (resInn != null)
//        {
//            DefCO.inputMessage(this, lbInn.getText(), resInn);
//            return false;
//        }

//        String resPensionNumber = DefCO.isDigit(edtPensionNumber);
//        if (resPensionNumber != null)
//        {
//            DefCO.inputMessage(this, lbPensionNumber.getText(), resPensionNumber);
//            return false;
//        }

//        String resManScore = DefCO.isDigit(edtManScore);
//        if (resManScore != null)
//        {
//            DefCO.inputMessage(this, lbManScore.getText(), resManScore);
//            return false;
//        }

        if (dpBirthday.getDate() != null && dpSb.getDate() != null)
        {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(dpBirthday.getDate().getTime());
            c.add(Calendar.YEAR, 18);
            c.add(Calendar.DATE, 1);
            if (dpSb.getDate().before(new Date(c.getTimeInMillis())))
            {
                DefCO.inputMessage(this, "На момент проверки нет 18 лет.");
                return false;
            }
        }

        return true;
    }

    private ZpMan getZpMan()
    {
        ZpMan man = getObject();

        man.setSecondJob(cbSecondJob.getValue());
        man.setName1(edtName1.getText());
        man.setPhone(edtPhone.getMyText());
        man.setBirthday(dpBirthday.getDate());
        man.setTabNumber(edtTabNumber.getText());
        man.setTabNumber2(edtTabNumber2.getText());
        if (cbPassportType.getSelectedIndex() == 0)
        {
            man.setPassportNumber(edtPassportRus.getMyText());
        } else if (cbPassportType.getSelectedIndex() == 1)
        {
            String passportNumber = edtPassportBlr.getMyText();
            if (passportNumber != null && !passportNumber.equals(""))
                passportNumber = cbPassportBlrSer.getSelectedItem() +
                        passportNumber;
            man.setPassportNumber(passportNumber);
        } else if (cbPassportType.getSelectedIndex() == 2)
        {
            man.setPassportNumber(edtPassportDr.getText());
        }

        man.setPassportAddress(edtPassportAddress.getText());
        man.setRegAddress(edtRegAddress.getText());
        man.setPassportWhere(edtPassportWhere.getText());
        man.setInn(edtInn.getMyText());
        man.setPensionNumber(edtPensionNumber.getMyText());
        man.setActive(cbActive.getValue());
        man.setpZdm(cbZdm.getValue());
        man.setpTvp(cbTvp.getValue());
        man.setpSk(cbSk.getValue());
        man.setSpecMobile(cbSpecMobile.getValue());
        man.setMobile(cbMobile.getValue());
        man.setZlo(cbZlo.getValue());
        man.setpSb(cbSb.getValue());
        man.setpSbDate(dpSb.getDate());
        man.setpDog(cbDog.getValue());
        man.setpDogDate(dpDog.getDate());
        man.setBlackList(cbBlackList.getValue());
        man.setCard(cbCard.getValue());
        man.setShopId(cbShop.getSelectedId());
        man.setManScore(edtManScore.getText());
        man.setComment(edtComment.getText());
        man.setOwnerManList(((TmOwnerMan) tblOwnerMan.getModel()).getList());
        man.setSessionId(Connect.getSessionId());

        return man;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpMan man = getZpMan();
                String str = Connect.getZpRemote().mergeMan(man);
                if (str != null)
                {
                    DefCO.showInf(this, str);
                } else
                    super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpOwnerMan ownerMan = new ZpOwnerMan();
        ZpMan man = getZpMan();
        Timestamp sd = Connect.getClient().getSession().getSd();
        String dogNum = man.getTabNumber() + "-" +
                DefCO.dateMMYYYYToStr(new Date(sd.getTime()));
        ownerMan.setName(dogNum);
        ownerManEditDialog.showOwnerMan(ownerMan);
        if (ownerManEditDialog.modalResult)
        {
            ((TmOwnerMan) tblOwnerMan.getModel()).addObj(ownerManEditDialog.getOwnerMan());
        }
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblOwnerMan.isSelected())
        {
            int row = tblOwnerMan.getSelectedRow();
            ZpOwnerMan ownerMan = (ZpOwnerMan) tblOwnerMan.getSelObj();
            ownerManEditDialog.showOwnerMan(ownerMan);
            if (ownerManEditDialog.modalResult)
            {
                ((TmOwnerMan) tblOwnerMan.getModel()).setObj(row, ownerManEditDialog.getOwnerMan());
            }
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblOwnerMan.isSelected())
        {
            try
            {
                ZpOwnerMan ownerMan = (ZpOwnerMan) tblOwnerMan.getSelObj();
                //if (DefCO.isDel())
                {
                    ((TmOwnerMan) tblOwnerMan.getModel()).removeObj(ownerMan);
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }
}
