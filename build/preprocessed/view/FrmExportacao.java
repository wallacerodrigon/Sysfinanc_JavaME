/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Parcela;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import model.ParcelaDAO;
import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.entity.Device;
import util.Constantes;

/**
 *
 * @author wal
 */
public class FrmExportacao extends Form implements CommandListener, CommunicationListener {

    private Gauge ggExportacao;
    private Command cmdExportacaoSair, cmdExportarIniciar;
    private Device device;
    private MidletME midlet;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }


    public FrmExportacao(Device device, MidletME midlet){
        super("Exporta\u00E7\u00E3o de Dados");
        append(getGgExportacao());
        addCommand(getCmdExportacaoSair());
        addCommand(getCmdExportarIniciar());
        this.device = device;
        this.midlet = midlet;
        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {

        if (c == cmdExportarIniciar){
            //envia flag para o servidor da ação de baixa que será feita
            //e que serão enviados: cod conta e cod parcela das que deverão 
            //ser baixadas com a data de hoje.
             enviar(Constantes.TAG_BAIXA);
             exportarParcelasPagas();
             this.midlet.switchDisplayable(null, this.midlet.getLstMenu());
        }else{
            this.midlet.switchDisplayable(null, this.midlet.getLstMenu());
        }

    }


    private Command getCmdExportacaoSair() {
        if (cmdExportacaoSair == null) {
            // write pre-init user code here
            cmdExportacaoSair = new Command("Sair", Command.OK, 0);
            // write post-init user code here
        }
        return cmdExportacaoSair;
    }

    private Command getCmdExportarIniciar() {
        if (cmdExportarIniciar == null) {
            // write pre-init user code here
            cmdExportarIniciar = new Command("Iniciar", Command.OK, 0);
            // write post-init user code here
        }
        return cmdExportarIniciar;
    }

    private Gauge getGgExportacao() {
        if (ggExportacao == null) {
            // write pre-init user code here
            ggExportacao = new Gauge("Progresso do envio...", false, 100, 0);
            // write post-init user code here
        }
        return ggExportacao;
    }

    private void enviar(String s){
        try {
            Thread.sleep(1000);
            this.device.send(s.getBytes());
        } catch (Exception ex) {

        }
    }

    private void exportarParcelasPagas() {
        ParcelaDAO pDao = new ParcelaDAO(null);
        Parcela[] listaPagasNaoEnviadas = pDao.listaPagasNaoEnviadas();

        if (listaPagasNaoEnviadas == null || listaPagasNaoEnviadas.length == 0){
            this.midlet.mostrarErro("Nao ha parcelas pagas para enviar.");
            this.midlet.switchDisplayable(null, this.midlet.getLstMenu());
            return ;
        }

        ggExportacao.setLayout(Gauge.CONTINUOUS_RUNNING);
        ggExportacao.setValue(0);
        ggExportacao.setLabel("Exportando:" + listaPagasNaoEnviadas.length + " parcelas.");
        ggExportacao.setMaxValue(listaPagasNaoEnviadas.length);
        String strEnvio = "";
        //envia o comando de baixa de parcelas
        for(int i = 0; i< listaPagasNaoEnviadas.length; i++){
            if (listaPagasNaoEnviadas[i].isEnviado()) continue;

            try{
                //monta a string com a conta e a parcela
                strEnvio = listaPagasNaoEnviadas[i].getCodConta()+"&"+
                           listaPagasNaoEnviadas[i].getCodParcela();
                //envia os dados.
                enviar(strEnvio);

                listaPagasNaoEnviadas[i].setEnviado(true);
                new ParcelaDAO(listaPagasNaoEnviadas[i]).alterar();
                //aguarda 1 segundo para enviar a próxima
                //Thread.sleep(1000);
                ggExportacao.setValue(i+1);
            }catch(Exception e){
                this.midlet.mostrarErro(e);
                break;
            }
        }
        enviar(Constantes.TAG_FIM);

    }


//bluetooth
    public void receiveMessage(byte[] arg0) {
        String s = new String(arg0);
        String x = "";
        boolean ok = false;
        if (s.equalsIgnoreCase(Constantes.TAG_BAIXAOK)){
             x  = "Parcelas baixadas com sucesso!";
             ok = true;
        }else if (s.equalsIgnoreCase(Constantes.TAG_BAIXAER)){
            x = "Erro ao exportar os dados para o PC.";
        }

        if (ok){
            ggExportacao.setValue(0);
            this.midlet.getAltSucesso().setString(x);
            this.midlet.switchDisplayable(this.midlet.getAltSucesso(), this.midlet.getLstMenu());
        }else{
            ggExportacao.setValue(0);
            this.midlet.getAltErro().setString(x);
            this.midlet.switchDisplayable(this.midlet.getAltErro(), this.midlet.getLstMenu());

        }
    }

    public void errorOnReceiving(IOException arg0) {
        this.midlet.mostrarErro(arg0);

    }

    public void errorOnSending(IOException arg0) {
        this.midlet.mostrarErro(arg0);
    }

}
