/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.Vector;

/**
 *
 * @author 9906
 */
public interface IDataModel {

    public boolean salvar();
    public boolean alterar();
    public boolean excluir();
    public Vector listarTudo();
    public Object find(int recordId);
}
