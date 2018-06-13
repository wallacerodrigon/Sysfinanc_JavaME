/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import entity.Parcela;
import java.io.IOException;
import javax.microedition.rms.RecordComparator;

/**
 *
 * @author 9906
 */
public class OrderByStatusDescricao implements javax.microedition.rms.RecordComparator {

    public int compare(byte[] rec1, byte[] rec2) {
        Parcela p1 = new Parcela();
        Parcela p2 = new Parcela();

        try {
            p1.byteToParcela(rec1);
            p2.byteToParcela(rec2);
        } catch (IOException ex) {
            return RecordComparator.EQUIVALENT;
        }

        if (p1 == null || p2 == null)
            return RecordComparator.EQUIVALENT;

        int ret = RecordComparator.EQUIVALENT;
        if (!p1.isPago() && p2.isPago())
            ret = RecordComparator.PRECEDES;

        if (p1.getDescricao().compareTo(p2.getDescricao()) > 0)
            ret = RecordComparator.FOLLOWS;


        return ret;
    }



}
