/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import entity.Parcela;
import java.util.Calendar;
import java.util.Date;
import lib.ClassFuncoes;

/**
 *
 * @author 9906
 */
public class Pacote {

    private int tamanho;
    private String dado;

    private final String SEPARADOR = "@";
    private final String SEPARADOR_DADOS = "%";
    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * @return the dado
     */
    public String getDado() {
        return dado;
    }

    /**
     * @param dado the dado to set
     */
    public void setDado(String dado) {
        this.dado = dado;
        this.tamanho = dado.length();
    }

    public boolean isValid(){
        return dado.length() == this.tamanho;
    }

    public String toString(){
        return tamanho + "-" + dado;
    }


    public Pacote empacotar(Parcela parcela){
        Pacote p = new Pacote();
        StringBuffer buffer = new StringBuffer();
        buffer.append(parcela.getCodConta()).append(SEPARADOR);
        buffer.append(parcela.getCodParcela()).append(SEPARADOR);
        buffer.append(parcela.getDescricao()).append(SEPARADOR);
        buffer.append(parcela.getDataVencto()).append(SEPARADOR);
        buffer.append(parcela.getValorVencto()).append(SEPARADOR);
        buffer.append(parcela.getDataPagto()).append(SEPARADOR);
        buffer.append(parcela.getClassificacao()).append(SEPARADOR);
        buffer.append(parcela.isPago()).append(SEPARADOR);
        p.setDado(buffer.toString());
        return p;
    }

    public Parcela desempacotar(Pacote pacote) {
        //55-394@10@Celular da Sandra@2009-04-12@29.08@null@D@false@

        Calendar dataVenc = Calendar.getInstance();
        Calendar dataPagto = Calendar.getInstance();

        String dados[] = ClassFuncoes.split(pacote.getDado(), this.SEPARADOR_DADOS);

        Parcela p = new Parcela();
        p.setCodConta( Integer.parseInt(dados[0]) );
        p.setCodParcela( Integer.parseInt(dados[1]) );
        p.setDescricao( dados[2] );

        String[] camposData = ClassFuncoes.split( dados[3], "-");
        dataVenc.set(Calendar.DATE, Integer.parseInt(camposData[2]) );
        dataVenc.set(Calendar.MONTH, Integer.parseInt(camposData[1])-1 );
        dataVenc.set(Calendar.YEAR, Integer.parseInt(camposData[0]) );
        p.setDataVencto( dataVenc.getTime() );
        p.setValorVencto( Double.parseDouble(dados[4]) );

        if (!dados[5].equals("null") ){
            camposData = ClassFuncoes.split(dados[5], "-");
            dataPagto.set(Calendar.DATE, Integer.parseInt(camposData[2]) );
            dataPagto.set(Calendar.MONTH, Integer.parseInt(camposData[1])-1 );
            dataPagto.set(Calendar.YEAR, Integer.parseInt(camposData[0]) );

            p.setDataPagto( dataPagto.getTime() );
        }

        p.setClassificacao( dados[6] );
        p.setPago( dados[7].equalsIgnoreCase("true") );

        return p;
    }

    public Pacote toPacote(byte[] bytes){
        Pacote p = new Pacote();
        //p.setDado( new String(bytes) );
        String sBytes = new String(bytes);
        String x[] = new String[2];
        //ClassFuncoes.split( p.getDado(), SEPARADOR_DADOS);
        int posSeparador = sBytes.indexOf(SEPARADOR);

        if (posSeparador == 0) return null;
        x[0] = sBytes.substring(0, posSeparador-1);
        x[1] = sBytes.substring(posSeparador+1);
        p.setTamanho( Integer.parseInt(x[0].trim()) );
        p.setDado(x[1]);
        return p;
    }

    public byte[] toBytes(Pacote p){
        String x = p.getTamanho()+SEPARADOR_DADOS+p.getDado();
        return x.getBytes();
    }

}
