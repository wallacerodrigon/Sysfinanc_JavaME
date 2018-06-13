package dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author admin
 */
public class DataBase {

    private String nameBD;
    private RecordStore bd;
    
    public final static int SUCESSO = 1;
    public final static int FALHA = 2;
    public final static int EXISTE = 3;
    public final static int NAO_EXISTE = 4;
            
    public DataBase(String name) throws Exception {
        if (name.equals(""))
            throw new Exception("Nome do RecordStore não informado.");
        
        this.nameBD = name;
    }
    
    public void conectar() throws Exception {
        
        if (bd == null)
            bd = RecordStore.openRecordStore(nameBD, true);

    }
    
    public void desconectar() throws Exception {
        bd.closeRecordStore();
    }
    
    public int inserir(byte[] dados) throws RecordStoreNotOpenException, 
            RecordStoreFullException, RecordStoreException{
        int ret = bd.addRecord(dados, 0, dados.length);
        if (ret > 0)
            return SUCESSO;
        else 
            return FALHA;
    } 

    public int alterar(byte[] dados, int record){
        try {
            byte[] dado = bd.getRecord(record);

            bd.setRecord(record, dado, 0, dado.length);
            return SUCESSO;
        } catch (Exception e){
            return FALHA;
        }
    }
    
    public int excluir(int record){
        try {
            byte[] dado = bd.getRecord(record);

            if (dado == null) {
                return NAO_EXISTE;
            } else {
                bd.deleteRecord(record);
                return SUCESSO;
            }
        } catch (Exception e){
            return FALHA;
        }
        
    }
    
    public RecordEnumeration listAll() throws RecordStoreNotOpenException{
        return bd.enumerateRecords(null, null, true);
    }

    public RecordEnumeration listAll(RecordFilter filtro) throws RecordStoreNotOpenException{
        return bd.enumerateRecords(filtro, null, true);
    }

    public RecordEnumeration listAll(RecordComparator ordenacao) throws RecordStoreNotOpenException{
        return bd.enumerateRecords(null, ordenacao, true);
    }

    public RecordEnumeration listAll(RecordFilter filtro, RecordComparator ordenacao) throws RecordStoreNotOpenException{
        return bd.enumerateRecords(filtro, ordenacao, true);
    }

    public byte[] find(int key) throws Exception{
        return bd.getRecord(key);
    }
    public int getQtdRegistros(){
        try {
            return bd.getNumRecords();
        } catch (RecordStoreNotOpenException ex) {
            return 0;
        }
    }

    public int nextId(){
        try {
            return bd.getNextRecordID();
        } catch (Exception e){
            return 0;
        }
    }
    
    public void limparBanco(){
        try {
            //desconectar();
            //RecordStore.deleteRecordStore(nameBD);
            //bd = null;
            //this.conectar();
            for (int i = 0; i < bd.getNumRecords(); i++){
                int recordId = bd.getNextRecordID();
                if (recordId > 0)
                   bd.deleteRecord(recordId);
            }

            System.out.println( bd.getNumRecords() );

        } catch (Exception ex) {
        }
    }
}
