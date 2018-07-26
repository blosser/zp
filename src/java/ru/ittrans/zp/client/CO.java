package ru.ittrans.zp.client;

import org.apache.poi.ss.usermodel.*;
import ru.ittrans.zp.client.def.DefCO;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 22.04.11
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class CO
{
    public static int LENGTH_ALPH = (int) 'Z' - (int) 'A' + 1;

    public static synchronized int getColIndex(String cellName)
    {
        if (cellName.length() > 1)
            return LENGTH_ALPH * ((int) cellName.charAt(0) - (int) 'A' + 1) +
                    (int) cellName.charAt(1) - (int) 'A';
        else
            return (int) cellName.charAt(0) - (int) 'A';
    }

    public static synchronized String cellToString(FormulaEvaluator evaluator, Cell cell)
    {
        if (cell == null) return "";

        switch (cell.getCellType())
        {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    return String.valueOf(DefCO.dateToStr(new Date(cell.getDateCellValue().getTime())));
                else
                    return Long.toString((long) cell.getNumericCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                if (evaluator != null)
                {
                    CellValue cv = evaluator.evaluate(cell);
                    return Long.toString((long) cv.getNumberValue());
                }
                return cell.getCellFormula().toString();
            default:
                return "";
        }
    }

    public static synchronized String getValueStr(final FormulaEvaluator evaluator, final Row row, final int index)
    {
        Cell cellValue = row.getCell(index);
        String cellValueStr = CO.cellToString(evaluator, cellValue);
        return cellValueStr;
    }

    public static synchronized String getValueStr(final Row row, final int index)
    {
        return getValueStr(null, row, index);
    }

    public static final int INDEX_A = getColIndex("A");
    public static final int INDEX_B = getColIndex("B");
    public static final int INDEX_C = getColIndex("C");
    public static final int INDEX_D = getColIndex("D");
    public static final int INDEX_E = getColIndex("E");
    public static final int INDEX_F = getColIndex("F");
    public static final int INDEX_G = getColIndex("G");
    public static final int INDEX_H = getColIndex("H");
    public static final int INDEX_I = getColIndex("I");
    public static final int INDEX_J = getColIndex("J");
    public static final int INDEX_K = getColIndex("K");
    public static final int INDEX_L = getColIndex("L");
    public static final int INDEX_M = getColIndex("M");
    public static final int INDEX_N = getColIndex("N");
    public static final int INDEX_O = getColIndex("O");
    public static final int INDEX_P = getColIndex("P");
    public static final int INDEX_Q = getColIndex("Q");
    public static final int INDEX_R = getColIndex("R");
    public static final int INDEX_S = getColIndex("S");
    public static final int INDEX_T = getColIndex("T");
    public static final int INDEX_U = getColIndex("U");
    public static final int INDEX_V = getColIndex("V");
    public static final int INDEX_W = getColIndex("W");
    public static final int INDEX_X = getColIndex("X");
    public static final int INDEX_Y = getColIndex("Y");
    public static final int INDEX_Z = getColIndex("Z");

    public static boolean myEquals(String v1, String v2)
    {
        if (v1 == null)
        {
            return v2 == null;
        } else
        {
            return v1.equals(v2);
        }
    }

    public static boolean myEquals(Long v1, Long v2)
    {
        if (v1 == null)
        {
            return v2 == null;
        } else
        {
            return v1.equals(v2);
        }
    }
}
