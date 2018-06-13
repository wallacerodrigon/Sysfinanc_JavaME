/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import dao.IDataModel;
import dao.DataBase;
import entity.Parcela;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author 9906
 */
public class ParcelaDAO implements IDataModel {

    DataBase database;
    private static final String RECORD_STORE_NAME = "DB_Parcela";
    private Parcela parcela;

    public ParcelaDAO(Parcela parcela){
        try {
            database = new DataBase(RECORD_STORE_NAME);
            database.conectar();
            this.parcela = parcela;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean apagarTudo(){
        try {
            //database.limparBanco();
            RecordEnumeration re = database.listAll();
            while(re.hasNextElement()){
                int id = re.nextRecordId();
                if (id == 0) continue;
                database.excluir(id);
            }

            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean salvar() {
        byte[] b;
        boolean ok = false;
        try {
            if ( this.find(getParcela().getCodConta(), getParcela().getCodParcela()) != null ){
               return false;
            }
            b = getParcela().toByteArray();
            int x = database.inserir(b);
            ok = (x == DataBase.SUCESSO);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ok;

    }

    public boolean alterar() {
        byte[] b;
        boolean ok = false;
        try {
            b = getParcela().toByteArray();
            excluir();
            int x = database.inserir(b);
            ok = (x == DataBase.SUCESSO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ok;
    }

    public boolean excluir() {
       return database.excluir(getParcela().getRecordId()) == DataBase.SUCESSO ;
    }

    public Parcela[] listarTodasParcelas() {
        Parcela[] lista = null;
        try {
           //recupera os registros da base
            //criar uma ordenação
           RecordEnumeration re = database.listAll();
           RecordEnumeration reId = re;
           int[] recordsId = new int[database.getQtdRegistros()];
           int i = 0;
           //guarda num array os ids encontrados
           while(reId.hasNextElement()){
              recordsId[i] = reId.nextRecordId();
              i++;
           }
           //varre-os
           i = 0;
           lista = new Parcela[recordsId.length];
           re = database.listAll();
           //while (re.hasNextElement()){
             //  byte[] b = re.nextRecord();
           for(int x = 0 ; x < recordsId.length; x ++){
               //para cada elemento gravado, transforma num objeto Parcela
               if (recordsId[x] == 0) continue;

               //procura pelo ID
               byte b[] = database.find(recordsId[x]);
               Parcela p = new Parcela();
               p = p.byteToParcela(b);
               p.setRecordId(recordsId[x]);
               lista[x] =  p;
               //i++;
           }
        } catch (Exception ex) {
           // ex.printStackTrace();
        }
        return lista;
    }

    public Object find(int recordId) {
        Parcela p = null;
        try {
            byte[] o = database.find(recordId);
            p = p.byteToParcela(o);
            p.setRecordId(recordId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public Parcela find(int codConta, int codParcela){
       Parcela[] lista = this.listarTodasParcelas();
       Parcela p = null;
       if (lista == null || lista.length == 0) return null;
       for(int i = 0; i< lista.length; i++){
          if (lista[i].getCodConta() == codConta &&
              lista[i].getCodParcela() == codParcela ) {
              p = lista[i];
              break;
          }
       }

       return p;
    }

    public Parcela[] listaPagasNaoEnviadas(){
        Vector lista = new Vector();
        Parcela[] listaTudo = this.listarTodasParcelas();
        for(int i = 0; i < listaTudo.length; i++){
           Parcela p = listaTudo[i];
           if ( p.isPago() && p.isEnviado() == false ){
              lista.addElement(p);
           }
        }
        return ParcelaDAO.vectorToArray(lista);

    }

    public static Parcela[] vectorToArray(Vector v){
       Enumeration e = v.elements();
       Parcela p[] = new Parcela[v.size()];
       int i = 0;
       while(e.hasMoreElements()){
          Parcela parc = (Parcela)e.nextElement();
          p[i] = parc;
          i++;
       }
       return p;
    }


    public Vector listarTudo() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    /**
     * @return the parcela
     */
    public Parcela getParcela() {
        return parcela;
    }

    /**
     * @param parcela the parcela to set
     */
    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }
}

