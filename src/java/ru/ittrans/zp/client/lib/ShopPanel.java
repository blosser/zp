package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpMetro;
import ru.ittrans.zp.io.ZpRegion;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 12.07.12
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class ShopPanel extends JPanel
{
    public static String[] COL_NANES_SHOP =
            new String[]{"Базовый", "Код", "Адрес", "Как добраться", "Метро",
                    "Телефон1", "Телефон2", "Примечание", "Территория", "Сбор табелей",
                    "E-mail", "Регион", "Директор", "Развоз ЗП", "Приоритет"};

    public static final String[] MES_DELIVER = new String[] {"1-5", "20-25"};

    private JLabel lbParentShop = new JLabel(COL_NANES_SHOP[0]);
    private DefComboBox<ZpShop> cbParentShop = new DefComboBox<ZpShop>();

    private JLabel lbCode = new JLabel(COL_NANES_SHOP[1]);
    private DefTextField edtCode = new DefTextField();

    private JLabel lbAddress = new JLabel(COL_NANES_SHOP[2]);
    private DefTextField edtAddress = new DefTextField();

    private JLabel lbHowToGet = new JLabel(COL_NANES_SHOP[3]);
    private DefTextArea taHowToGet = new DefTextArea();
    private JScrollPane spHowToGet = new JScrollPane(taHowToGet);

    private JLabel lbMetro = new JLabel(COL_NANES_SHOP[4]);
    private DefComboBox<ZpMetro> cbMetro = new DefComboBox<ZpMetro>();

    private JLabel lbPhone = new JLabel(COL_NANES_SHOP[5]);
    private DefMaskFormatter mfPhone = null;
    private DefFormattedTextField edtPhone = null;

    private JLabel lbPhone2 = new JLabel(COL_NANES_SHOP[6]);
    private DefMaskFormatter mfPhone2 = null;
    private DefFormattedTextField edtPhone2 = null;

    private JLabel lbComment = new JLabel(COL_NANES_SHOP[7]);
    private DefTextField edtComment = new DefTextField();

    private JLabel lbTerritClient = new JLabel(COL_NANES_SHOP[8]);
    private DefComboBox<ZpClient> cbTerritClient = new DefComboBox<ZpClient>();

    private JLabel lbTabClient = new JLabel(COL_NANES_SHOP[9]);
    private DefComboBox<ZpClient> cbTabClient = new DefComboBox<ZpClient>();

    private JLabel lbEmail = new JLabel(COL_NANES_SHOP[10]);
    private DefTextField edtEmail = new DefTextField();

    private JLabel lbRegion = new JLabel(COL_NANES_SHOP[11]);
    private DefComboBox<ZpRegion> cbRegion = new DefComboBox<ZpRegion>();

    private JLabel lbDirector = new JLabel(COL_NANES_SHOP[12]);
    private DefTextField edtDirector = new DefTextField();

    private JLabel lbDeliver = new JLabel(COL_NANES_SHOP[13]);
    private DefComboBox<String> cbDeliver = new DefComboBox<String>();

    private DefCheckBox cbxPriority = new DefCheckBox(COL_NANES_SHOP[14], false);

    private ZpShop shop;

    public ShopPanel(boolean isEnabled)
    {
        super();
        jbInit();
        setMyEditable(isEnabled);
    }

    protected void jbInit()
    {
        try
        {
            mfPhone = new DefMaskFormatter("#-###-###-##-##");
            edtPhone = new DefFormattedTextField(mfPhone);

            mfPhone2 = new DefMaskFormatter("#-###-###-##-##");
            edtPhone2 = new DefFormattedTextField(mfPhone2);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        JPanel pnlMan = this;
        pnlMan.setLayout(new GridBagLayout());

        pnlMan.add(lbParentShop, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbParentShop, new GridBagConstraints(1, 0, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbCode, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtCode, new GridBagConstraints(3, 0, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbAddress, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtAddress, new GridBagConstraints(1, 3, 3, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbHowToGet, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(spHowToGet, new GridBagConstraints(1, 4, 3, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbMetro, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbMetro, new GridBagConstraints(1, 5, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        cbMetro.setPreferredSize(new Dimension(200, 23));

        pnlMan.add(lbComment, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtComment, new GridBagConstraints(3, 5, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPhone, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPhone, new GridBagConstraints(1, 7, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPhone2, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPhone2, new GridBagConstraints(3, 7, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbTerritClient, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbTerritClient, new GridBagConstraints(1, 9, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbTabClient, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbTabClient, new GridBagConstraints(3, 9, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbEmail, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtEmail, new GridBagConstraints(1, 10, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbRegion, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbRegion, new GridBagConstraints(3, 10, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbDirector, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtDirector, new GridBagConstraints(1, 12, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbDeliver, new GridBagConstraints(2, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbDeliver, new GridBagConstraints(3, 12, 1, 1, 0.5, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(cbxPriority, new GridBagConstraints(0, 13, 4, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));
    }

    public void showShop(ZpShop shop)
    {
        setShop(shop);

        DefComboBox.cbAddItem(cbParentShop, DicManager.getParentShopMap(), true, false);
        DefComboBox.cbAddItem(cbTerritClient, DicManager.getClientMap(), true, false);
        DefComboBox.cbAddItem(cbTabClient, DicManager.getClientMap(), true, false);
        DefComboBox.cbAddItem(cbRegion, DicManager.getRegionMap(), true, false);
        DefComboBox.cbAddItem(cbDeliver, DicManager.getDeliverMap(), true, false);
        DefComboBox.cbAddItem(cbMetro, DicManager.getMetroMap(), true, false);

        cbParentShop.setSelectedItem(DicManager.getParentShopMap().get(shop.getParentId()));
        edtCode.setText(shop.getCode());
//        edtName.setText(shop.getName());
        edtAddress.setText(shop.getAddress());
        taHowToGet.setText(shop.getHowToGet());
        edtPhone.setMyText(shop.getPhone());
        edtPhone2.setMyText(shop.getPhone2());
        edtComment.setText(shop.getComment());
        cbTerritClient.setSelectedItem(DicManager.getClientMap().get(shop.getTerritClientId()));
        cbTabClient.setSelectedItem(DicManager.getClientMap().get(shop.getTabClientId()));
        edtEmail.setText(shop.getEmail());
        cbRegion.setSelectedItem(DicManager.getRegionMap().get(shop.getRegionId()));
        edtDirector.setText(shop.getDirector());
        cbDeliver.setSelectedItem(DicManager.getDeliverMap().get(shop.getDeliver()));
        cbMetro.setSelectedItem(DicManager.getMetroMap().get(shop.getMetroId()));
        cbxPriority.setValue(shop.getPriority());
    }

    protected boolean isNextEnabled()
    {
        if (edtCode.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbCode.getText());
            return false;
        }

        return true;
    }

    public ZpShop getZpShop()
    {
        ZpShop shop = getShop();

        shop.setParentId(cbParentShop.getSelectedId());
        shop.setCode(edtCode.getText());
//        shop.setName(edtName.getText());
        shop.setAddress(edtAddress.getText());
        shop.setHowToGet(taHowToGet.getText());
        shop.setPhone(edtPhone.getMyText());
        shop.setPhone2(edtPhone2.getMyText());
        shop.setComment(edtComment.getText());
        shop.setTerritClientId(cbTerritClient.getSelectedId());
        shop.setTabClientId(cbTabClient.getSelectedId());
        shop.setEmail(edtEmail.getText());
        shop.setRegionId(cbRegion.getSelectedId());
        shop.setDirector(edtDirector.getText());
        shop.setDeliver(cbDeliver.getSelectedId());
        shop.setMetroId(cbMetro.getSelectedId());
        shop.setPriority(cbxPriority.getValue());

        return shop;
    }

    public ZpShop getShop()
    {
        return shop;
    }

    public void setShop(ZpShop shop)
    {
        this.shop = shop;
    }

    public void setMyEditable(boolean isEnabled)
    {
        cbParentShop.setMyEditable(isEnabled);
        edtCode.setMyEditable(isEnabled);
        edtAddress.setMyEditable(isEnabled);;
        taHowToGet.setMyEditable(isEnabled);;
        edtPhone.setMyEditable(isEnabled);;
        edtPhone2.setMyEditable(isEnabled);;
        edtComment.setMyEditable(isEnabled);;
        cbTerritClient.setMyEditable(isEnabled);
        cbTabClient.setMyEditable(isEnabled);
        edtEmail.setMyEditable(isEnabled);;
        cbRegion.setMyEditable(isEnabled);;
        edtDirector.setMyEditable(isEnabled);;
        cbDeliver.setMyEditable(isEnabled);;
        cbMetro.setMyEditable(isEnabled);;
        cbxPriority.setMyEditable(isEnabled);;
    }
}
