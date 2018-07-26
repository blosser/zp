package ru.ittrans.zp.client.def;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 20.10.2010
 * Time: 16:07:06
 * To change this template use File | Settings | File Templates.
 */
public class DefStatusChange// implements ActionListener
{
//    private MainFrame mf;
//    private DefTable table;
//    private TpStatus status;
//
//    public DefStatusChange(MainFrame mf, DefTable table, TpStatus status)
//    {
//        this.mf = mf;
//        this.table = table;
//        this.status = status;
//    }
//
//    public void actionPerformed(ActionEvent e)
//    {
//        if (table.isSelected())
//            try
//            {
//                Object object = table.getSelObj();
//                if (object instanceof TpAbstract)
//                {
//                    TpAbstract tpObject = (TpAbstract) object;
//                    Long oldStatusId = tpObject.getStatusId();
//                    tpObject.setStatusId(status.getId());
//                    boolean isChanged = true;
//                    if (tpObject instanceof TpQuote)
//                    {
//                        String mes = Connect.getTpRemote().mergeQuote((TpQuote) tpObject, true);
//                        if (mes != null && mes.length() > 0)
//                        {
//                            DefCO.showInf(mf, mes);
//                            tpObject.setStatusId(oldStatusId);
//                            isChanged = false;
//                        }
//                    } else
//                    {
//                        Connect.getTpRemote().mergeEntity(tpObject);
//                    }
//                    if (isChanged)
//                    {
//                        int row = table.getSelectedRow();
//                        ((DefTableModel) table.getModel()).fireTableRowsUpdated(row, row);
//                        if (tpObject instanceof TpMan)
//                        {
//                            TpMan man = (TpMan) tpObject;
//                            Connect.getTpRemote().sendEmail(man);
//                        }
//                    }
//                }
//            } catch (Exception ex)
//            {
//                DefCO.showError(mf, ex);
//            }
//        else DefCO.showSelected(mf);
//    }
}
