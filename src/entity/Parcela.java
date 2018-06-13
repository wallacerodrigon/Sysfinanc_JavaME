package entity;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import lib.ClassFuncoes;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 9906
 */
public class Parcela {


    private int codConta;
    private int codParcela;
    private Date dataVencto;
    private Date dataPagto;
    private int qtdParcela;
    private String descricao;
    private String classificacao;
    private double valorVencto;
    private double valorPagto;
    private boolean pago;
    private boolean baixaManual;
    private boolean enviado;
    private int recordId;
    private int ultimaPosicao = 0;

    public static final String DESPESA = "D";
    public static final String RECEITA = "R";

    public Parcela(){
        pago = false;
        baixaManual = false;
        enviado = false;
        recordId = -1;
    }

    public String getClassificacao() {
        return classificacao.toUpperCase();
    }

    public int getCodConta() {
        return codConta;
    }

    public int getCodParcela() {
        return codParcela;
    }

    public Date getDataPagto() {
        return dataPagto;
    }

    public Date getDataVencto() {
        return dataVencto;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQtdParcela() {
        return qtdParcela;
    }

    public double getValorPagto() {
        return valorPagto;
    }

    public double getValorVencto() {
        return valorVencto;
    }

    public boolean isPago() {
        return pago;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao.toUpperCase();
    }

    public void setCodConta(int codConta) {
        this.codConta = codConta;
    }

    public void setCodParcela(int codParcela) {
        this.codParcela = codParcela;
    }

    public void setDataPagto(Date dataPagto) {
        this.dataPagto = dataPagto;
    }

    public void setDataVencto(Date dataVencto) {
        this.dataVencto = dataVencto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public void setQtdParcela(int qtdParcela) {
        this.qtdParcela = qtdParcela;
    }

    public void setValorPagto(double valorPagto) {
        this.valorPagto = valorPagto;
    }

    public void setValorVencto(double valorVencto) {
        this.valorVencto = valorVencto;
    }

    public String toString(){
        return this.getCodConta()+"-"+this.getCodParcela()+"/"+getQtdParcela()+"-"+getDescricao()+"-"+getValorVencto()+" ["+getClassificacao()+"] ==>"+isPago();
    }

    /**
     * @return the baixaManual
     */
    public boolean isBaixaManual() {
        return baixaManual;
    }

    /**
     * @param baixaManual the baixaManual to set
     */
    public void setBaixaManual(boolean baixaManual) {
        this.baixaManual = baixaManual;
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeInt(codConta);
        dos.writeInt(codParcela);
        dos.writeUTF( ClassFuncoes.dateToStr(dataVencto)  );
      //  dos.writeUTF( ClassFuncoes.dateToStr(dataPagto)  );
        dos.writeInt(qtdParcela);
        dos.writeUTF(descricao);
        dos.writeUTF(classificacao);
        dos.writeDouble(valorVencto);
        //dos.writeDouble(valorPagto);
        dos.writeBoolean(pago);
        dos.writeBoolean(baixaManual);
        dos.writeBoolean(enviado);

        return bos.toByteArray();
    }

    public Parcela byteToParcela(byte[] array) throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bis);

        Parcela p = new Parcela();

        p.setCodConta( dis.readInt() );
        p.setCodParcela(dis.readInt());
        p.setDataVencto(  ClassFuncoes.strToDate(dis.readUTF())  );
      //  p.setDataPagto(ClassFuncoes.strToDate(dis.readUTF()));
        p.setQtdParcela(dis.readInt());
        p.setDescricao(dis.readUTF());
        p.setClassificacao(dis.readUTF());
        p.setValorVencto(dis.readDouble());
        //p.setValorPagto(dis.readDouble());
        p.setPago(dis.readBoolean());
        p.setBaixaManual(dis.readBoolean());
        p.setEnviado(dis.readBoolean());

        return p;
    }

    public void getByteArrayFromLayout(byte[] bytes){
        String s = new String(bytes);
        //047402prestacao do palio - conserto                     124114680000162.0Dfalse
        ultimaPosicao = 0;
        String codConta   = quebrarString(s, 4);
        String codParcela = quebrarString(s, 2);
        String qtdParcela = quebrarString(s, 2);
        String descricao  = quebrarString(s, 50);
        String dataVenc   = quebrarString(s, 10);
        String valorVenc  = quebrarString(s, 10);
        String classific  = quebrarString(s, 1);
        String estaPago   = quebrarString(s, 5);
        valorVenc         = valorVenc.replace(',', '.');


        setCodConta(Integer.parseInt(codConta));
        setCodParcela(Integer.parseInt(codParcela));
        setQtdParcela(Integer.parseInt(qtdParcela));
        setDescricao(descricao);

        setDataVencto( ClassFuncoes.strToDate(dataVenc) );
        setValorVencto( Double.valueOf(valorVenc).doubleValue() );
        setClassificacao(classific);
        setPago( estaPago.equals("false") ? false : true );
        setEnviado(false);
        setBaixaManual(false);
    }

    private String quebrarString(String texto, int tamanho){

        String x = texto.substring(ultimaPosicao, ultimaPosicao+tamanho);
        ultimaPosicao += tamanho;
        return x.trim();
    }



    /**
     * @return the enviado
     */
    public boolean isEnviado() {
        return enviado;
    }

    /**
     * @param enviado the enviado to set
     */
    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    /**
     * @return the recordId
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

   /* public boolean save() {

        try {
            PersistableManager.getInstance().save(this);
            return true;
        } catch (FloggyException ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    //implementar os outros métodos: find, findall(class),
    public Parcela get(int codConta, int codParcela){
        FiltroById filtroById = new FiltroById(codConta, codParcela);
        Parcela p = null;
        try {
             p = (Parcela)PersistableManager.getInstance().find(Parcela.class, filtroById, null);
        } catch (FloggyException ex) {
           // ex.printStackTrace();
        }
        return p;
    }

    public Vector listAll(){
        Vector lista = new Vector();
        try {
            ObjectSet os = PersistableManager.getInstance().find(Parcela.class, null, null);
            for(int i = 0; i < os.size(); i++){
                lista.addElement((Parcela)os.get(i));
            }
        } catch (FloggyException ex) {
          //  ex.printStackTrace();
        }
        return lista;
    }*/

    public static void main(String[] args){
     /*   String x = "047402prestacao do palio - conserto                     124114680000162.0Dfalse";
        byte[] b = x.getBytes();

        Parcela p = new Parcela();
        p.getByteArrayFromLayout(b);*/

    }



}
