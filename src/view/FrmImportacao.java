/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Parcela;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.TimeZone;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import lib.ClassFuncoes;
import model.ParcelaDAO;
import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.entity.Device;
import server.Pacote;
import util.Constantes;

/**
 *
 * @author 9906
 */
public class FrmImportacao extends Form implements CommandListener, CommunicationListener {

    private Command btnIniciarImpDados, btnVoltarMenuIniciarDados;
    private StringItem siResumo, siSaldo, siReceita, siDespesa, stringItem, siDado;
    private Spacer spacer, spacer1, spacer7;
    private Gauge gauge;
    private MidletME midlet;

    public boolean isServidor = false;
    public boolean importando = false;
    private Vector listaParcelas = null;

    private final String SEPARADOR = "@";
    private final String SEPARADOR_DADOS = "%";
    private Device device;

    int qtdRegistros = 0;
    boolean apagaTudo = false;

    public FrmImportacao(MidletME midlet){
            super("Importa\u00E7\u00E3o de Dados");

            this.midlet = midlet;

         //   append(getStringItem());
            append(getSpacer());
            append(getGauge());
            append(getSpacer1());
            append(getSiResumo());
            append(getSiReceita());
            append(getSiDespesa());
            append(getSiSaldo());
            append(getSpacer7());
            //append(getSiDado());
            listaParcelas = new Vector();
            addCommand(getBtnIniciarImpDados());
            addCommand(getBtnVoltarMenuIniciarDados());
            setCommandListener(this);
    }

    public Spacer getSpacer() {
        if (spacer == null) {
            // write pre-init user code here
            spacer = new Spacer(16, 1);
            // write post-init user code here
        }
        return spacer;
    }

   public Gauge getGauge() {
        if (gauge == null) {
            // write pre-init user code here
            gauge = new Gauge("Progresso...", false, 100, 0);
            // write post-init user code here
        }
        return gauge;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer1 ">
    /**
     * Returns an initiliazed instance of spacer1 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer1() {
        if (spacer1 == null) {
            // write pre-init user code here
            spacer1 = new Spacer(16, 1);
            // write post-init user code here
        }
        return spacer1;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: siResumo ">
    /**
     * Returns an initiliazed instance of siResumo component.
     * @return the initialized component instance
     */
    public StringItem getSiResumo() {
        if (siResumo == null) {
            // write pre-init user code here
            siResumo = new StringItem("Resumo:", "-");
            // write post-init user code here
        }
        return siResumo;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: siSaldo ">
    /**
     * Returns an initiliazed instance of siSaldo component.
     * @return the initialized component instance
     */
    public StringItem getSiSaldo() {
        if (siSaldo == null) {
            // write pre-init user code here
            siSaldo = new StringItem("Saldo (R$)", "0,00");
            // write post-init user code here
        }
        return siSaldo;
    }
    //</editor-fold>

   public StringItem getSiReceita() {
        if (siReceita == null) {
            // write pre-init user code here
            siReceita = new StringItem("Receitas (R$)", "0,00");
            // write post-init user code here
        }
        return siReceita;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: siDespesa ">
    /**
     * Returns an initiliazed instance of siDespesa component.
     * @return the initialized component instance
     */
    public StringItem getSiDespesa() {
        if (siDespesa == null) {
            // write pre-init user code here
            siDespesa = new StringItem("Despesas (R$)", "0,00");
            // write post-init user code here
        }
        return siDespesa;
    }
    //</editor-fold>

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer7 ">
    /**
     * Returns an initiliazed instance of spacer7 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer7() {
        if (spacer7 == null) {
            // write pre-init user code here
            spacer7 = new Spacer(16, 1);
            // write post-init user code here
        }
        return spacer7;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: siDado ">
    /**
     * Returns an initiliazed instance of siDado component.
     * @return the initialized component instance
     */
    public StringItem getSiDado() {
        if (siDado == null) {
            // write pre-init user code here
            siDado = new StringItem("stringItem10", null);
            // write post-init user code here
        }
        return siDado;
    }
    //</editor-fold>

    public Command getBtnIniciarImpDados() {
        if (btnIniciarImpDados == null) {
            // write pre-init user code here
            btnIniciarImpDados = new Command("Iniciar", Command.OK, 0);
            // write post-init user code here
        }
        return btnIniciarImpDados;
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: btnVoltarMenuIniciarDados ">
    /**
     * Returns an initiliazed instance of btnVoltarMenuIniciarDados component.
     * @return the initialized component instance
     */
    public Command getBtnVoltarMenuIniciarDados() {
        if (btnVoltarMenuIniciarDados == null) {
            // write pre-init user code here
            btnVoltarMenuIniciarDados = new Command("Menu", Command.BACK, 0);
            // write post-init user code here
        }
        return btnVoltarMenuIniciarDados;
    }

    public void commandAction(Command command, Displayable d) {
         if (command == btnIniciarImpDados) {
                /*try {
                    //Alert a = new Alert("Base de dados", "Apagar os dados atuais?", null, AlertType.CONFIRMATION);
                    //this.midlet.switchDisplayable(a, null);
                   /* if (this.device == null || this.device.getDeviceName().equals("")) {
                        Alert a = new Alert("Sem dispositivo", "Nenhum dispositivo encontrado!", null, AlertType.ERROR);
                        this.midlet.switchDisplayable(a, this.midlet.getLstMenu());
                        return;
                    }
                } catch (BluetoothStateException ex) {
                }*/

                apagaTudo = false;
                gauge.setValue(0);
                gauge.setLayout(gauge.CONTINUOUS_RUNNING);
                gauge.setLabel("Importando...Aguarde!");
                //this.device.setReadInterval(1000);
                sendMessage(Constantes.TAG_LISTA);
                qtdRegistros = 0;
                //cadastrarVarios();
                //mostrarErro("Importado!");
         } else if (command == btnVoltarMenuIniciarDados ){
             leave(); //liberar
         }

    }




    private void sendMessage(String s){
        this.getDevice().send(s.getBytes());
        listaParcelas = new Vector();
        //getSiDado().setText("Enviando:" + s);
    }

    private void criarStringItem(String s){
        append( new StringItem("prc", s.substring(0, 20)) );
    }

    public void receiveMessage(byte[] msgBytes) {
        //tratar as mensagens recebidas...
        String s = new String(msgBytes);
        
        //
        try{
            if (s.indexOf(Constantes.TAG_FIM) > -1){
                gauge.setValue(0);
                gauge.setLayout(gauge.INCREMENTAL_UPDATING);

                try {
                   incluirParcelas();
                }catch(Exception e){
                    mostrarErro("Erro na inclusão das parcelas. Erro: " + e.getMessage());
                }
            } else if (s.indexOf(Constantes.TAG_BAIXA) < 0){
                listaParcelas.addElement(msgBytes);
                gauge.setValue(gauge.getValue()+1);
            }
        }catch(Exception e){
            mostrarErro("Erro:" + e.getMessage());
        }
  
    }

    private void incluirParcelas(){
           
            Enumeration e = listaParcelas.elements();
            ParcelaDAO pDao = new ParcelaDAO(null);
            boolean finalizou = true;
            //pDao.apagarTudo();
            gauge.setMaxValue(listaParcelas.size());
            gauge.setValue(0);
            gauge.setLabel("A importar " + gauge.getMaxValue() + " parcelas.");
            int i = 0;
            double valReceita = 5.00;
            double valDespesa = 4.00;
            entity.Parcela prc = new Parcela();

            while(e.hasMoreElements()) {
                byte[] b = (byte[])e.nextElement();
                prc = new Parcela();
                prc.getByteArrayFromLayout(b); //(Parcela)e.nextElement();
                pDao.setParcela(prc);
                gauge.setValue(i);
                ++i;
                
                if (pDao.salvar()){
                   finalizou = true;
                   if (prc.getClassificacao().equals(Parcela.DESPESA))
                      valDespesa += prc.getValorVencto();
                  else
                       valReceita += prc.getValorVencto();
                } else {
                    finalizou = false;
                    break;
                }
                
            }

            if (! finalizou ){
                mostrarErro(" A parcela " + prc.getDescricao()+"/"+prc.getCodParcela()+" gerou um erro!");
                this.midlet.switchDisplayable(null, this.midlet.getLstMenu());
            }else {
               this.midlet.mostrarMensagem(i + " Parcelas importadas com sucesso!");
               this.midlet.mostrarForm(true);
            }
            getSiReceita().setText( ClassFuncoes.formataValor(valReceita)  );
            getSiDespesa().setText( ClassFuncoes.formataValor(valDespesa)  );
            getSiSaldo().setText(   ClassFuncoes.formataValor(valReceita-valDespesa)  );

    }

    public void errorOnReceiving(IOException arg0) {
        leave();
    }

    public void errorOnSending(IOException arg0) {
        leave();
    }
    //</editor-fold>


    public void mostrarErro(String mensagem) {
        Alert alertaErro = new Alert(mensagem);
        switchDisplayable(alertaErro, null);
    }

    public void mostrarErro(Exception e) {
        Alert alertaErro = new Alert("Erro: " + e.getMessage());
        switchDisplayable(alertaErro, null);
    }

    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
        // write pre-switch user code here
        Display display = this.midlet.getDisplay();
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }
        // write post-switch user code here
    }

    /**
     * @return the device
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    public void leave() {
        //this.deleteAll();
        try{
           this.device.close();
        }catch(Exception e){}
        this.midlet.switchDisplayable(null, this.midlet.getLstMenu());
    }

    private void cadastrarVarios() {
        Parcela[] lista = new Parcela[5];
        ParcelaDAO p = new ParcelaDAO(null);
        boolean ok = true;

        DateField dtfVencto = new DateField("DAta Venc", DateField.DATE, TimeZone.getDefault());
        dtfVencto.setDate( Calendar.getInstance().getTime()  );
        String[] testes = new String[5];
        testes[0] = "03941112Celular da Sandra                                 2009-05-120000029.08Dfalse";
        testes[1] = "04420625pagamento de salario                              2009-05-0800002404.0Rfalse";
        testes[2] = "04690319Deposito para a prestacao do apartamento          2009-05-0800000560.0Dfalse";
        testes[3] = "04710248Prestacao do Gol                                  2009-05-080000465.07Dfalse";
        testes[4] = "04790336abastecimento - gas ou alcool                     2009-05-1000000050.0Dtrue ";
        for(int i = 0; i < lista.length; i++){
            lista[i] = new Parcela();
            lista[i].getByteArrayFromLayout(testes[i].getBytes());

           /* lista[i].setCodConta(i);
            lista[i].setCodParcela(1);
            lista[i].setDescricao("DescriÃ§Ã£o " + i);
            lista[i].setBaixaManual(i % 2 == 0);
            lista[i].setPago(i % 2 == 0);
            lista[i].setClassificacao("D");
            lista[i].setDataVencto( dtfVencto.getDate() );
            lista[i].setQtdParcela(1);
            lista[i].setValorVencto( (i+1)*100.00 );*/
            this.listaParcelas.addElement(testes[i].getBytes());
        }

        incluirParcelas();

        if (ok){
            getSiDado().setText("ok");
            //this.midlet.getAltSucesso().setString("Parcelas cadastradas com sucesso!");
            //switchDisplayable(this.midlet.getAltSucesso(), this.getFrmContas());
        } else {
            getSiDado().setText("nao ok");
        }
    }


}



/*opção para gerar alert com confirmação

Alert alerta = new Alert("", "teste", null, AlertType.CONFIRMATION);
alerta.addCommand(get_okCommand());
alerta.addCommand(get_cancelCommand());
alerta.setCommandListener(new CommandListener() {
    public void commandAction(Command command, Displayable displayable) {
        if (command == okCommand) {
            helloStringItem.setText("OK");
        } else if (command == cancelCommand) {
            helloStringItem.setText("Cancelou");
        }
        getDisplay().setCurrent(helloForm);
    }
});
getDisplay().setCurrent(alerta); */