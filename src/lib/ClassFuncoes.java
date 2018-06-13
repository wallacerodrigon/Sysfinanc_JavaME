/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ClassFuncoes {

    public static String padRight(String value, int tamanho, char caracter){
        String ret = value;
        if (ret.length() > tamanho)
            ret = ret.substring(0, tamanho-1);
        int compl = tamanho - ret.length();
        if (compl <= 0) return value;
        for(int i = 0; i < compl; i++){
            ret += caracter;
        }

        return ret;
    }

    public static String padLeft(String value, int tamanho, char caracter){
        String ret = value;
        if (ret.length() > tamanho)
            ret = ret.substring(0, tamanho-1);
        int compl = tamanho - ret.length();
        if (compl <= 0) return value;
        
        for(int i = 0; i < compl; i++){
            ret = caracter + ret;
        }

        return ret;
    }

    
    public static String formataValor(double valor){
        //250.090909
        String x = String.valueOf(valor);
        
        int pos = x.indexOf("."); //4
        String y = x.substring(0, pos)+","; //0-4 = 250,00
        String dec = x.substring(pos+1, pos+2);
        dec = ClassFuncoes.padRight(dec, 2, '0');
        return y+dec;
    }

    public static String formataData(String valor){
        if (valor == null || valor.equals("")) return "";
        //2008-12-05 --> inicio e fim no substring.
        String x = valor.substring(8, 10)+"/"+
                   valor.substring(5, 7)+"/"+ 
                   valor.substring(0, 4);
        return x;
    }
    
    public static String dateToStr(Date valor){
        if (valor == null) return "";

        Calendar c = Calendar.getInstance();
        c.setTime(valor);
        
        int mes = c.get(Calendar.MONTH)+1;
        int ano = c.get(Calendar.YEAR);
        int dia = c.get(Calendar.DATE);
        
        StringBuffer x = new StringBuffer();
        x.append(String.valueOf(ano)).append("-");        
        x.append(mes<10?"0"+mes:String.valueOf(mes)).append("-");
        x.append(dia<10?"0"+dia:String.valueOf(dia));
        //retorno: ano-mes-dia
        return x.toString();
    }
    
    public static Date strToDate(String value){
        Calendar c = Calendar.getInstance();
        //vou passar uma data no formato yyyy-mm-dd ou yyyy/mm/dd
        //2008-07-02
        String dia = value.substring(8,10);
        String mes = value.substring(5,7);
        String ano = value.substring(0,4);
        c.set(Calendar.DATE, Integer.valueOf(dia).intValue());
        c.set(Calendar.MONTH, Integer.valueOf(mes).intValue()-1);
        c.set(Calendar.YEAR, Integer.valueOf(ano).intValue());        
        return c.getTime();
    }

public static String[] split(String str, String ch){
  java.util.Vector v=new java.util.Vector();
  while(str.indexOf(ch) != -1){
    String tmp=str.substring(0, str.indexOf(ch)).trim();
    if(tmp.length()>0)v.addElement(tmp);
    str=str.substring(str.indexOf(ch)+1,str.length());
  }
  String[] returned=new String[v.size()];
  for (int i=0;i<v.size();i ++) {
    returned[i]=(String)v.elementAt(i);
  }
  return returned;
}
        
}
