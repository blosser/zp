package ru.ittrans.zp.client.man;

import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefTableKeyAdapter;
import ru.ittrans.zp.io.ZpManValue;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 03.05.11
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class DefCellEditor extends DefaultCellEditor
{
    public DefCellEditor(final JTextField textField)
    {
        super(textField);
        editorComponent = textField;
        this.clickCountToStart = 0;
        delegate = new EditorDelegate()
        {
            public void setValue(Object value)
            {
                if (value instanceof ZpManValue)
                {
                    ZpManValue mv = (ZpManValue) value;
                    textField.setText(DefCO.longToStr(mv.getValue()));
                } else
                    textField.setText(value != null ? value.toString() : "");
            }

            public Object getCellEditorValue()
            {
                return textField.getText();
            }
        };
        textField.addActionListener(delegate);
        textField.addKeyListener(new DefTableKeyAdapter());
    }

    public DefCellEditor(JComboBox comboBox)
    {
        super(comboBox);
    }
}
