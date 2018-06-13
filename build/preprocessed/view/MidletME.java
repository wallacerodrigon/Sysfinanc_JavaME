/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import com.mechart.chart.BarChart;
import com.mechart.chart.PieChart;
import entity.Parcela;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import lib.ClassFuncoes;
import model.ParcelaDAO;
import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.communication.ConnectionListener;
import net.java.dev.marge.entity.Device;
import net.java.dev.marge.entity.ServerDevice;
import net.java.dev.marge.factory.CommunicationFactory;
import net.java.dev.marge.factory.RFCOMMCommunicationFactory;
import org.netbeans.microedition.lcdui.LoginScreen;
import org.netbeans.microedition.lcdui.SimpleTableModel;
import org.netbeans.microedition.lcdui.SplashScreen;
import org.netbeans.microedition.lcdui.TableItem;
import util.Constantes;


/**
 * @author 9906
 */
public class MidletME extends MIDlet implements CommandListener,
        ConnectionListener {

    private boolean midletPaused = false;

    private Device device;
    private boolean importando = false;
    private Vector devices;
    public boolean isServidor = false;
    private Vector listaParcelas = null;
    private Parcela[] listaExibicao = null;

    private final String SEPARADOR = "@";
    private final String SEPARADOR_DADOS = "%";
    private FrmImportacao frmImportacao;

    private final String LOGIN = "wal";
    private final String SENHA = "rod";

    private final static int TOTAL_RECEITA = 0;
    private final static int TOTAL_DESPESA = 1;

    private final static int TOTAL_RECEITA_PAGA = 2;
    private final static int TOTAL_RECEITA_NAOPAGA = 3;

    private final static int TOTAL_DESPESA_PAGA = 4;
    private final static int TOTAL_DESPESA_NAOPAGA = 5;

    private final static int MAIOR_VALOR_LISTA = 6;

    
    public FrmImportacao getFrmImportacao() {
        if (frmImportacao == null)
            frmImportacao = new FrmImportacao(this);
        return frmImportacao;
    }

    private int idRegistro = 0;
    private int indexRegAtual = 0;

    private static final int INICIAL = 9;
    private static final int BEFORE = 99;
    private static final int AFTER  = 999;


    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private List lstMenu;
    private List lstCadastrados;
    private Form frmProcurar;
    private TextField tfFindCodParc;
    private TextField tfFindCodConta;
    private Form frmContas;
    private TextField tfFormRegistro;
    private TextField tfFormVencimento;
    private TextField tfFormParcela;
    private TextField tfFormDescricao;
    private Spacer spacer;
    private TextField tfFormResumo;
    private TextField tfFormValor;
    private StringItem stringItem;
    private Alert altErro;
    private Alert altSucesso;
    private Form frmResumoExportacao;
    private StringItem stringItem1;
    private Spacer spacer4;
    private TextField tfExportaRecPagas;
    private TextField tfExportaSaldoPagas;
    private TextField tfExportaDesPagas;
    private Spacer spacer1;
    private TextField tfExportaRecNaoPagas;
    private TextField tfExportaDesNaoPagas;
    private TextField tfExportaSaldoNaoPagas;
    private Form frmTabela;
    private TableItem tbiParcelas;
    private SplashScreen splashScreen;
    private Form frmLogin;
    private TextField tfSenha;
    private TextField tfUsuario;
    private List lstRelatorios;
    private Form frmExportacao2;
    private Gauge ggExportacao;
    private Form frmGrafRecDesp;
    private List lstAdministracao;
    private Form frmGrafPagamentos;
    private List lstSincronismos;
    private Command cmdFindVoltar;
    private Command cmdFindProcurar;
    private Command cmdFormProximo;
    private Command cmdFormVoltar;
    private Command cmdFormBaixar;
    private Command cmdFormAnterior;
    private Command backCommand;
    private Command cmdExpIniciar;
    private Command cmdExpVoltar;
    private Command cmdTabelaVisualizar;
    private Command cmdTabelaVoltar;
    private Command cmdTabelaExcluir;
    private Command cmdFindTabela;
    private Command cmdFormTabela;
    private Command cmdLoginCancelar;
    private Command cmdLoginEntrar;
    private Command cmdLoginEntrar1;
    private Command cmdLoginCancelar1;
    private Command cmdExportacaoSair;
    private Command okCommand;
    private Command cancelCommand;
    private Command cmdGrafRecDespVoltar;
    private Command cmdGrafPagtosVoltar;
    private Command cmdExportarIniciar;
    private Command cmdTabelaBaixar;
    private Font font1;
    private SimpleTableModel tableModel1;
    private Image image1;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The MidletME constructor.
     */
    public MidletME() {
       listaParcelas = new Vector();
    }


    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        try{
            switchDisplayable(null, getSplashScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
        }catch(Exception e){
            mostrarErro(e);
        }
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
        try {
            LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.LIAC);
        } catch (BluetoothStateException ex) {
            mostrarErro(ex);
        }
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        //testar teclado
        /*if (Canvas.LEFT){

        }*/

        if (displayable == frmContas) {//GEN-BEGIN:|7-commandAction|1|53-preAction
            if (command == cmdFormAnterior) {//GEN-END:|7-commandAction|1|53-preAction
                // write pre-action user code here
                doNavegacao(this.BEFORE);

//GEN-LINE:|7-commandAction|2|53-postAction
                // write post-action user code here
            } else if (command == cmdFormBaixar) {//GEN-LINE:|7-commandAction|3|55-preAction
                // write pre-action user code here
                Parcela p = listaExibicao[indexRegAtual];
                //p.setDataPagto(getDataAtual());
                p.setBaixaManual(true);
                p.setEnviado(false);
                //p.setValorPagto(p.getValorVencto());
                p.setPago(true);

                ParcelaDAO pdao = new ParcelaDAO(p);

                if (pdao.alterar()){
                   mostrarMensagem("Parcela baixada com sucesso!");
                   listaExibicao[indexRegAtual] = p;
                   carregarDados(p, listaExibicao.length, indexRegAtual);
                } else {
                   mostrarErro("NÃ£o foi possÃ­vel baixar esta parcela. Ocorreu um erro!");
                }
//GEN-LINE:|7-commandAction|4|55-postAction
                // write post-action user code here
            } else if (command == cmdFormProximo) {//GEN-LINE:|7-commandAction|5|51-preAction
                // write pre-action user code here
                doNavegacao(this.AFTER);

//GEN-LINE:|7-commandAction|6|51-postAction
                // write post-action user code here
            } else if (command == cmdFormTabela) {//GEN-LINE:|7-commandAction|7|105-preAction
                // write pre-action user code here
                mostrarTabela();
                switchDisplayable(null, getFrmTabela());

//GEN-LINE:|7-commandAction|8|105-postAction
                // write post-action user code here
            } else if (command == cmdFormVoltar) {//GEN-LINE:|7-commandAction|9|49-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|7-commandAction|10|49-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|156-preAction
        } else if (displayable == frmExportacao2) {
            if (command == cmdExportacaoSair) {//GEN-END:|7-commandAction|11|156-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|12|156-postAction
                // write post-action user code here
            } else if (command == cmdExportarIniciar) {//GEN-LINE:|7-commandAction|13|192-preAction
                // write pre-action user code here

//GEN-LINE:|7-commandAction|14|192-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|166-preAction
        } else if (displayable == frmGrafPagamentos) {
            if (command == cmdGrafPagtosVoltar) {//GEN-END:|7-commandAction|15|166-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstRelatorios());//GEN-LINE:|7-commandAction|16|166-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|164-preAction
        } else if (displayable == frmGrafRecDesp) {
            if (command == cmdGrafRecDespVoltar) {//GEN-END:|7-commandAction|17|164-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstRelatorios());//GEN-LINE:|7-commandAction|18|164-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|19|129-preAction
        } else if (displayable == frmLogin) {
            if (command == cmdLoginCancelar1) {//GEN-END:|7-commandAction|19|129-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|20|129-postAction
                // write post-action user code here
            } else if (command == cmdLoginEntrar1) {//GEN-LINE:|7-commandAction|21|127-preAction
                // write pre-action user code here
                if (LOGIN.equalsIgnoreCase(getTfUsuario().getString()) &&
                    SENHA.equalsIgnoreCase(getTfSenha().getString())
                        ) {
                    switchDisplayable(null, getLstMenu());
                }else{
                    getAltErro().setString("Login ou senha inválidos!");
                    switchDisplayable(altErro, getFrmLogin());
                }

//GEN-LINE:|7-commandAction|22|127-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|23|33-preAction
        } else if (displayable == frmProcurar) {
            if (command == cmdFindProcurar) {//GEN-END:|7-commandAction|23|33-preAction
                // write pre-action user code here
                String conta, parcela;
                conta = getTfFindCodConta().getString();
                parcela = getTfFindCodParc().getString();

                if (conta.equals("") || parcela.equals("")){
                    altErro.setString("Conta ou parcela não informados!");
                    switchDisplayable(altErro, null);
                    return;
                }

                int codConta = Integer.parseInt(conta);
                int codParc  = Integer.parseInt(parcela);

                showParcela(codConta, codParc);
//GEN-LINE:|7-commandAction|24|33-postAction
                // write post-action user code here
            } else if (command == cmdFindTabela) {//GEN-LINE:|7-commandAction|25|107-preAction
                // write pre-action user code here
                mostrarTabela();
                switchDisplayable(null, getFrmTabela());
//GEN-LINE:|7-commandAction|26|107-postAction
                // write post-action user code here
            } else if (command == cmdFindVoltar) {//GEN-LINE:|7-commandAction|27|35-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|7-commandAction|28|35-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|29|85-preAction
        } else if (displayable == frmResumoExportacao) {
            if (command == cmdExpIniciar) {//GEN-END:|7-commandAction|29|85-preAction
                // write pre-action user code here
                if (this.device == null){
                    getAltErro().setString("Nenhum dispositivo configurado para conexão. Efetua uma nova conexão.");
                    switchDisplayable(getAltErro(), getLstSincronismos());
                    return;
                }
                FrmExportacao f = new FrmExportacao(this.device, this);
                switchDisplayable(null, f);
//GEN-LINE:|7-commandAction|30|85-postAction
                // write post-action user code here
            } else if (command == cmdExpVoltar) {//GEN-LINE:|7-commandAction|31|83-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|7-commandAction|32|83-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|33|179-preAction
        } else if (displayable == frmTabela) {
            if (command == cmdTabelaBaixar) {//GEN-END:|7-commandAction|33|179-preAction
                // write pre-action user code here
                int linha = getTbiParcelas().getSelectedCellRow();
                String o = getTableModel1().getValue(6, linha).toString();
                if ( o != null && !o.equals("") ){
                    int recordId = Integer.parseInt(o);
                    boolean achou = false;
                    int i = 0 ;

                    if (listaExibicao == null || listaExibicao.length == 0)
                        mostrarForm(false);

                    for(i = 0; i < listaExibicao.length; i ++){
                        if (listaExibicao[i].getRecordId() == recordId){
                            achou = true;
                            break;
                        }
                    }
                    if (achou){
                        listaExibicao[i].setPago(true);
                        listaExibicao[i].setBaixaManual(true);
                        listaExibicao[i].setEnviado(false);
                        boolean ok = new ParcelaDAO(listaExibicao[i]).alterar();
                        if (ok){
                           getTableModel1().setValue(4, linha, "SIM");
                           getTableModel1().fireTableModelChanged();
                           mostrarMensagem("Parcela baixada com sucesso!");
                        } else {
                            mostrarErro("Parcela não baixada!");
                        }
                    }else{
                        getAltErro().setString("Parcela não encontrada!");
                        switchDisplayable(altErro, getFrmTabela());
                    }
                }else{
                        getAltErro().setString("ID Inválido!");
                        switchDisplayable(altErro, getFrmTabela());
                }

//GEN-LINE:|7-commandAction|34|179-postAction
                // write post-action user code here
            } else if (command == cmdTabelaVisualizar) {//GEN-LINE:|7-commandAction|35|102-preAction
                // write pre-action user code here
                int linha = getTbiParcelas().getSelectedCellRow();
                String o = getTableModel1().getValue(6, linha).toString();

                if ( o != null && !o.equals("") ){
                    int recordId = Integer.parseInt(o);
                    boolean achou = false;
                    int i = 0 ;

                   // if (listaExibicao == null || listaExibicao.length == 0)
                     //   mostrarForm(false);

                    Parcela p = null;
                    for(i = 0; i < listaExibicao.length; i ++){
                        if (listaExibicao[i].getRecordId() == recordId){
                            achou = true;
                            p = listaExibicao[i];
                            break;
                        }
                    }

                    if (achou){
                       carregarDados(p, listaExibicao.length, i+1);
                       switchDisplayable(null, getFrmContas());
                    }else{
                        getAltErro().setString("Parcela não encontrada!");
                        switchDisplayable(altErro, getFrmTabela());
                    }
                }else{
                        getAltErro().setString("ID Inválido!");
                        switchDisplayable(altErro, getFrmTabela());
                }
//GEN-LINE:|7-commandAction|36|102-postAction
                // write post-action user code here
            } else if (command == cmdTabelaVoltar) {//GEN-LINE:|7-commandAction|37|100-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|7-commandAction|38|100-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|39|171-preAction
        } else if (displayable == lstAdministracao) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|39|171-preAction
                // write pre-action user code here
                lstAdministracaoAction();//GEN-LINE:|7-commandAction|40|171-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|41|20-preAction
        } else if (displayable == lstCadastrados) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|41|20-preAction
                // write pre-action user code here
                lstCadastradosAction();//GEN-LINE:|7-commandAction|42|20-postAction
                // write post-action user code here
            } else if (command == backCommand) {//GEN-LINE:|7-commandAction|43|63-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|7-commandAction|44|63-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|45|16-preAction
        } else if (displayable == lstMenu) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|45|16-preAction
                // write pre-action user code here
                lstMenuAction();//GEN-LINE:|7-commandAction|46|16-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|47|134-preAction
        } else if (displayable == lstRelatorios) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|47|134-preAction
                // write pre-action user code here
                lstRelatoriosAction();//GEN-LINE:|7-commandAction|48|134-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|49|181-preAction
        } else if (displayable == lstSincronismos) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|49|181-preAction
                // write pre-action user code here
                lstSincronismosAction();//GEN-LINE:|7-commandAction|50|181-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|51|111-preAction
        } else if (displayable == splashScreen) {
            if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|51|111-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmLogin());//GEN-LINE:|7-commandAction|52|111-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|53|7-postCommandAction
        }//GEN-END:|7-commandAction|53|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|54|
    //</editor-fold>//GEN-END:|7-commandAction|54|




    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstMenu ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of lstMenu component.
     * @return the initialized component instance
     */
    public List getLstMenu() {
        if (lstMenu == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            lstMenu = new List("Menu Principal", Choice.IMPLICIT);//GEN-BEGIN:|14-getter|1|14-postInit
            lstMenu.append("Sincronismos", null);
            lstMenu.append("Procurar Conta", null);
            lstMenu.append("Visual em Formul\u00E1rio", null);
            lstMenu.append("Ver toda lista", null);
            lstMenu.append("Relat\u00F3rios", null);
            lstMenu.append("Administra\u00E7\u00E3o", null);
            lstMenu.append("Sair", null);
            lstMenu.setCommandListener(this);
            lstMenu.setSelectedFlags(new boolean[] { false, false, false, false, false, false, false });//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return lstMenu;
    }
    //</editor-fold>//GEN-END:|14-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstMenuAction ">//GEN-BEGIN:|14-action|0|14-preAction
    /**
     * Performs an action assigned to the selected list element in the lstMenu component.
     */
    public void lstMenuAction() {//GEN-END:|14-action|0|14-preAction
        // enter pre-action user code here
        String __selectedString = getLstMenu().getString(getLstMenu().getSelectedIndex());//GEN-BEGIN:|14-action|1|188-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Sincronismos")) {//GEN-END:|14-action|1|188-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstSincronismos());//GEN-LINE:|14-action|2|188-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Procurar Conta")) {//GEN-LINE:|14-action|3|25-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmProcurar());//GEN-LINE:|14-action|4|25-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Visual em Formul\u00E1rio")) {//GEN-LINE:|14-action|5|26-preAction
                // write pre-action user code here
                mostrarForm(false);
                if (listaExibicao.length == 0) return;
                switchDisplayable(null, getFrmContas());//GEN-LINE:|14-action|6|26-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Ver toda lista")) {//GEN-LINE:|14-action|7|61-preAction
                // write pre-action user code here
                mostrarTabela();
                switchDisplayable(null, getFrmTabela());//GEN-LINE:|14-action|8|61-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Relat\u00F3rios")) {//GEN-LINE:|14-action|9|132-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstRelatorios());//GEN-LINE:|14-action|10|132-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Administra\u00E7\u00E3o")) {//GEN-LINE:|14-action|11|169-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstAdministracao());//GEN-LINE:|14-action|12|169-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Sair")) {//GEN-LINE:|14-action|13|28-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|14-action|14|28-postAction
                // write post-action user code here
            }//GEN-BEGIN:|14-action|15|14-postAction
        }//GEN-END:|14-action|15|14-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|14-action|16|
    //</editor-fold>//GEN-END:|14-action|16|





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmProcurar ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of frmProcurar component.
     * @return the initialized component instance
     */
    public Form getFrmProcurar() {
        if (frmProcurar == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            frmProcurar = new Form("Procurar Conta", new Item[] { getTfFindCodConta(), getTfFindCodParc() });//GEN-BEGIN:|18-getter|1|18-postInit
            frmProcurar.addCommand(getCmdFindProcurar());
            frmProcurar.addCommand(getCmdFindVoltar());
            frmProcurar.addCommand(getCmdFindTabela());
            frmProcurar.setCommandListener(this);//GEN-END:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return frmProcurar;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFindCodConta ">//GEN-BEGIN:|30-getter|0|30-preInit
    /**
     * Returns an initiliazed instance of tfFindCodConta component.
     * @return the initialized component instance
     */
    public TextField getTfFindCodConta() {
        if (tfFindCodConta == null) {//GEN-END:|30-getter|0|30-preInit
            // write pre-init user code here
            tfFindCodConta = new TextField("C\u00F3digo da Conta", null, 6, TextField.NUMERIC);//GEN-LINE:|30-getter|1|30-postInit
            // write post-init user code here
        }//GEN-BEGIN:|30-getter|2|
        return tfFindCodConta;
    }
    //</editor-fold>//GEN-END:|30-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFindCodParc ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of tfFindCodParc component.
     * @return the initialized component instance
     */
    public TextField getTfFindCodParc() {
        if (tfFindCodParc == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            tfFindCodParc = new TextField("N\u00FAmero da Parcela", null, 6, TextField.NUMERIC);//GEN-LINE:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return tfFindCodParc;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstCadastrados ">//GEN-BEGIN:|19-getter|0|19-preInit
    /**
     * Returns an initiliazed instance of lstCadastrados component.
     * @return the initialized component instance
     */
    public List getLstCadastrados() {
        if (lstCadastrados == null) {//GEN-END:|19-getter|0|19-preInit
            // write pre-init user code here
            lstCadastrados = new List("Lista Geral", Choice.IMPLICIT);//GEN-BEGIN:|19-getter|1|19-postInit
            lstCadastrados.addCommand(getBackCommand());
            lstCadastrados.setCommandListener(this);//GEN-END:|19-getter|1|19-postInit
            // write post-init user code here
        }//GEN-BEGIN:|19-getter|2|
        return lstCadastrados;
    }
    //</editor-fold>//GEN-END:|19-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstCadastradosAction ">//GEN-BEGIN:|19-action|0|19-preAction
    /**
     * Performs an action assigned to the selected list element in the lstCadastrados component.
     */
    public void lstCadastradosAction() {//GEN-END:|19-action|0|19-preAction
        // enter pre-action user code here
        String __selectedString = getLstCadastrados().getString(getLstCadastrados().getSelectedIndex());//GEN-LINE:|19-action|1|19-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|19-action|2|
    //</editor-fold>//GEN-END:|19-action|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmContas ">//GEN-BEGIN:|38-getter|0|38-preInit
    /**
     * Returns an initiliazed instance of frmContas component.
     * @return the initialized component instance
     */
    public Form getFrmContas() {
        if (frmContas == null) {//GEN-END:|38-getter|0|38-preInit
            // write pre-init user code here
            frmContas = new Form("Formul\u00E1rio de Contas", new Item[] { getTfFormRegistro(), getTfFormParcela(), getTfFormDescricao(), getTfFormValor(), getTfFormVencimento(), getStringItem(), getSpacer(), getTfFormResumo() });//GEN-BEGIN:|38-getter|1|38-postInit
            frmContas.addCommand(getCmdFormVoltar());
            frmContas.addCommand(getCmdFormProximo());
            frmContas.addCommand(getCmdFormAnterior());
            frmContas.addCommand(getCmdFormBaixar());
            frmContas.addCommand(getCmdFormTabela());
            frmContas.setCommandListener(this);//GEN-END:|38-getter|1|38-postInit
            // write post-init user code here
        }//GEN-BEGIN:|38-getter|2|
        return frmContas;
    }
    //</editor-fold>//GEN-END:|38-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormRegistro ">//GEN-BEGIN:|39-getter|0|39-preInit
    /**
     * Returns an initiliazed instance of tfFormRegistro component.
     * @return the initialized component instance
     */
    public TextField getTfFormRegistro() {
        if (tfFormRegistro == null) {//GEN-END:|39-getter|0|39-preInit
            // write pre-init user code here
            tfFormRegistro = new TextField("Registro / Conta:", "1 de 55 Conta 10", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|39-getter|1|39-postInit
            // write post-init user code here
        }//GEN-BEGIN:|39-getter|2|
        return tfFormRegistro;
    }
    //</editor-fold>//GEN-END:|39-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormParcela ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of tfFormParcela component.
     * @return the initialized component instance
     */
    public TextField getTfFormParcela() {
        if (tfFormParcela == null) {//GEN-END:|40-getter|0|40-preInit
            // write pre-init user code here
            tfFormParcela = new TextField("Parcela:", "1 de 11 - NAO PAGA", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|40-getter|1|40-postInit
            // write post-init user code here
        }//GEN-BEGIN:|40-getter|2|
        return tfFormParcela;
    }
    //</editor-fold>//GEN-END:|40-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormDescricao ">//GEN-BEGIN:|41-getter|0|41-preInit
    /**
     * Returns an initiliazed instance of tfFormDescricao component.
     * @return the initialized component instance
     */
    public TextField getTfFormDescricao() {
        if (tfFormDescricao == null) {//GEN-END:|41-getter|0|41-preInit
            // write pre-init user code here
            tfFormDescricao = new TextField("Descri\u00E7\u00E3o:", "pagamento do carro", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|41-getter|1|41-postInit
            // write post-init user code here
        }//GEN-BEGIN:|41-getter|2|
        return tfFormDescricao;
    }
    //</editor-fold>//GEN-END:|41-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormVencimento ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of tfFormVencimento component.
     * @return the initialized component instance
     */
    public TextField getTfFormVencimento() {
        if (tfFormVencimento == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            tfFormVencimento = new TextField("Data de Vencimento:", "15/05/2009", 12, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return tfFormVencimento;
    }
    //</editor-fold>//GEN-END:|42-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormValor ">//GEN-BEGIN:|44-getter|0|44-preInit
    /**
     * Returns an initiliazed instance of tfFormValor component.
     * @return the initialized component instance
     */
    public TextField getTfFormValor() {
        if (tfFormValor == null) {//GEN-END:|44-getter|0|44-preInit
            // write pre-init user code here
            tfFormValor = new TextField("Valor:", "465,07", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|44-getter|1|44-postInit
            // write post-init user code here
        }//GEN-BEGIN:|44-getter|2|
        return tfFormValor;
    }
    //</editor-fold>//GEN-END:|44-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|45-getter|0|45-preInit
    /**
     * Returns an initiliazed instance of stringItem component.
     * @return the initialized component instance
     */
    public StringItem getStringItem() {
        if (stringItem == null) {//GEN-END:|45-getter|0|45-preInit
            // write pre-init user code here
            stringItem = new StringItem("Resumo:", "");//GEN-LINE:|45-getter|1|45-postInit
            // write post-init user code here
        }//GEN-BEGIN:|45-getter|2|
        return stringItem;
    }
    //</editor-fold>//GEN-END:|45-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer ">//GEN-BEGIN:|46-getter|0|46-preInit
    /**
     * Returns an initiliazed instance of spacer component.
     * @return the initialized component instance
     */
    public Spacer getSpacer() {
        if (spacer == null) {//GEN-END:|46-getter|0|46-preInit
            // write pre-init user code here
            spacer = new Spacer(16, 1);//GEN-LINE:|46-getter|1|46-postInit
            // write post-init user code here
        }//GEN-BEGIN:|46-getter|2|
        return spacer;
    }
    //</editor-fold>//GEN-END:|46-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfFormResumo ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of tfFormResumo component.
     * @return the initialized component instance
     */
    public TextField getTfFormResumo() {
        if (tfFormResumo == null) {//GEN-END:|47-getter|0|47-preInit
            // write pre-init user code here
            tfFormResumo = new TextField("Rec - Desp = Saldo", "465,00 - 300,00 = 100,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|47-getter|1|47-postInit
            // write post-init user code here
        }//GEN-BEGIN:|47-getter|2|
        return tfFormResumo;
    }
    //</editor-fold>//GEN-END:|47-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFindProcurar ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of cmdFindProcurar component.
     * @return the initialized component instance
     */
    public Command getCmdFindProcurar() {
        if (cmdFindProcurar == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
            cmdFindProcurar = new Command("Procurar", Command.OK, 0);//GEN-LINE:|32-getter|1|32-postInit
            // write post-init user code here
        }//GEN-BEGIN:|32-getter|2|
        return cmdFindProcurar;
    }
    //</editor-fold>//GEN-END:|32-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFindVoltar ">//GEN-BEGIN:|34-getter|0|34-preInit
    /**
     * Returns an initiliazed instance of cmdFindVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdFindVoltar() {
        if (cmdFindVoltar == null) {//GEN-END:|34-getter|0|34-preInit
            // write pre-init user code here
            cmdFindVoltar = new Command("Voltar", Command.BACK, 1);//GEN-LINE:|34-getter|1|34-postInit
            // write post-init user code here
        }//GEN-BEGIN:|34-getter|2|
        return cmdFindVoltar;
    }
    //</editor-fold>//GEN-END:|34-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFormVoltar ">//GEN-BEGIN:|48-getter|0|48-preInit
    /**
     * Returns an initiliazed instance of cmdFormVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdFormVoltar() {
        if (cmdFormVoltar == null) {//GEN-END:|48-getter|0|48-preInit
            // write pre-init user code here
            cmdFormVoltar = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|48-getter|1|48-postInit
            // write post-init user code here
        }//GEN-BEGIN:|48-getter|2|
        return cmdFormVoltar;
    }
    //</editor-fold>//GEN-END:|48-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFormProximo ">//GEN-BEGIN:|50-getter|0|50-preInit
    /**
     * Returns an initiliazed instance of cmdFormProximo component.
     * @return the initialized component instance
     */
    public Command getCmdFormProximo() {
        if (cmdFormProximo == null) {//GEN-END:|50-getter|0|50-preInit
            // write pre-init user code here
            cmdFormProximo = new Command("Pr\u00F3ximo", Command.OK, 0);//GEN-LINE:|50-getter|1|50-postInit
            // write post-init user code here
        }//GEN-BEGIN:|50-getter|2|
        return cmdFormProximo;
    }
    //</editor-fold>//GEN-END:|50-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFormAnterior ">//GEN-BEGIN:|52-getter|0|52-preInit
    /**
     * Returns an initiliazed instance of cmdFormAnterior component.
     * @return the initialized component instance
     */
    public Command getCmdFormAnterior() {
        if (cmdFormAnterior == null) {//GEN-END:|52-getter|0|52-preInit
            // write pre-init user code here
            cmdFormAnterior = new Command("Anterior", Command.OK, 0);//GEN-LINE:|52-getter|1|52-postInit
            // write post-init user code here
        }//GEN-BEGIN:|52-getter|2|
        return cmdFormAnterior;
    }
    //</editor-fold>//GEN-END:|52-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFormBaixar ">//GEN-BEGIN:|54-getter|0|54-preInit
    /**
     * Returns an initiliazed instance of cmdFormBaixar component.
     * @return the initialized component instance
     */
    public Command getCmdFormBaixar() {
        if (cmdFormBaixar == null) {//GEN-END:|54-getter|0|54-preInit
            // write pre-init user code here
            cmdFormBaixar = new Command("Baixar", Command.OK, 0);//GEN-LINE:|54-getter|1|54-postInit
            // write post-init user code here
        }//GEN-BEGIN:|54-getter|2|
        return cmdFormBaixar;
    }
    //</editor-fold>//GEN-END:|54-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: altErro ">//GEN-BEGIN:|59-getter|0|59-preInit
    /**
     * Returns an initiliazed instance of altErro component.
     * @return the initialized component instance
     */
    public Alert getAltErro() {
        if (altErro == null) {//GEN-END:|59-getter|0|59-preInit
            // write pre-init user code here
            altErro = new Alert("alert");//GEN-BEGIN:|59-getter|1|59-postInit
            altErro.setTimeout(Alert.FOREVER);//GEN-END:|59-getter|1|59-postInit
            // write post-init user code here
        }//GEN-BEGIN:|59-getter|2|
        return altErro;
    }
    //</editor-fold>//GEN-END:|59-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: altSucesso ">//GEN-BEGIN:|60-getter|0|60-preInit
    /**
     * Returns an initiliazed instance of altSucesso component.
     * @return the initialized component instance
     */
    public Alert getAltSucesso() {
        if (altSucesso == null) {//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
            altSucesso = new Alert("alert1");//GEN-BEGIN:|60-getter|1|60-postInit
            altSucesso.setTimeout(Alert.FOREVER);//GEN-END:|60-getter|1|60-postInit
            // write post-init user code here
        }//GEN-BEGIN:|60-getter|2|
        return altSucesso;
    }
    //</editor-fold>//GEN-END:|60-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|62-getter|0|62-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|62-getter|0|62-preInit
            // write pre-init user code here
            backCommand = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|62-getter|1|62-postInit
            // write post-init user code here
        }//GEN-BEGIN:|62-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|62-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmResumoExportacao ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initiliazed instance of frmResumoExportacao component.
     * @return the initialized component instance
     */
    public Form getFrmResumoExportacao() {
        if (frmResumoExportacao == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            frmResumoExportacao = new Form("Exporta\u00E7\u00E3o de Dados - Bluetooth", new Item[] { getStringItem1(), getSpacer4(), getTfExportaRecPagas(), getTfExportaDesPagas(), getTfExportaSaldoPagas(), getSpacer1(), getTfExportaRecNaoPagas(), getTfExportaDesNaoPagas(), getTfExportaSaldoNaoPagas() });//GEN-BEGIN:|67-getter|1|67-postInit
            frmResumoExportacao.addCommand(getCmdExpVoltar());
            frmResumoExportacao.addCommand(getCmdExpIniciar());
            frmResumoExportacao.setCommandListener(this);//GEN-END:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return frmResumoExportacao;
    }
    //</editor-fold>//GEN-END:|67-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem1 ">//GEN-BEGIN:|73-getter|0|73-preInit
    /**
     * Returns an initiliazed instance of stringItem1 component.
     * @return the initialized component instance
     */
    public StringItem getStringItem1() {
        if (stringItem1 == null) {//GEN-END:|73-getter|0|73-preInit
            // write pre-init user code here
            stringItem1 = new StringItem("Estat\u00EDstica dos dados", "");//GEN-LINE:|73-getter|1|73-postInit
            // write post-init user code here
        }//GEN-BEGIN:|73-getter|2|
        return stringItem1;
    }
    //</editor-fold>//GEN-END:|73-getter|2|



















    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdExpVoltar ">//GEN-BEGIN:|82-getter|0|82-preInit
    /**
     * Returns an initiliazed instance of cmdExpVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdExpVoltar() {
        if (cmdExpVoltar == null) {//GEN-END:|82-getter|0|82-preInit
            // write pre-init user code here
            cmdExpVoltar = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|82-getter|1|82-postInit
            // write post-init user code here
        }//GEN-BEGIN:|82-getter|2|
        return cmdExpVoltar;
    }
    //</editor-fold>//GEN-END:|82-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdExpIniciar ">//GEN-BEGIN:|84-getter|0|84-preInit
    /**
     * Returns an initiliazed instance of cmdExpIniciar component.
     * @return the initialized component instance
     */
    public Command getCmdExpIniciar() {
        if (cmdExpIniciar == null) {//GEN-END:|84-getter|0|84-preInit
            // write pre-init user code here
            cmdExpIniciar = new Command("Exportar", Command.OK, 0);//GEN-LINE:|84-getter|1|84-postInit
            // write post-init user code here
        }//GEN-BEGIN:|84-getter|2|
        return cmdExpIniciar;
    }
    //</editor-fold>//GEN-END:|84-getter|2|











    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer4 ">//GEN-BEGIN:|92-getter|0|92-preInit
    /**
     * Returns an initiliazed instance of spacer4 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer4() {
        if (spacer4 == null) {//GEN-END:|92-getter|0|92-preInit
            // write pre-init user code here
            spacer4 = new Spacer(16, 1);//GEN-LINE:|92-getter|1|92-postInit
            // write post-init user code here
        }//GEN-BEGIN:|92-getter|2|
        return spacer4;
    }
    //</editor-fold>//GEN-END:|92-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmTabela ">//GEN-BEGIN:|93-getter|0|93-preInit
    /**
     * Returns an initiliazed instance of frmTabela component.
     * @return the initialized component instance
     */
    public Form getFrmTabela() {
        if (frmTabela == null) {//GEN-END:|93-getter|0|93-preInit
            // write pre-init user code here
            frmTabela = new Form("", new Item[] { getTbiParcelas() });//GEN-BEGIN:|93-getter|1|93-postInit
            frmTabela.addCommand(getCmdTabelaVoltar());
            frmTabela.addCommand(getCmdTabelaVisualizar());
            frmTabela.addCommand(getCmdTabelaBaixar());
            frmTabela.setCommandListener(this);//GEN-END:|93-getter|1|93-postInit
            // write post-init user code here
        }//GEN-BEGIN:|93-getter|2|
        return frmTabela;
    }
    //</editor-fold>//GEN-END:|93-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tbiParcelas ">//GEN-BEGIN:|94-getter|0|94-preInit
    /**
     * Returns an initiliazed instance of tbiParcelas component.
     * @return the initialized component instance
     */
    public TableItem getTbiParcelas() {
        if (tbiParcelas == null) {//GEN-END:|94-getter|0|94-preInit
            // write pre-init user code here
            tbiParcelas = new TableItem(getDisplay(), "Lista de Parcelas");//GEN-BEGIN:|94-getter|1|94-postInit
            tbiParcelas.setTitle("");
            tbiParcelas.setModel(getTableModel1());
            tbiParcelas.setTitleFont(getFont1());
            tbiParcelas.setHeadersFont(getFont1());
            tbiParcelas.setValuesFont(getFont1());//GEN-END:|94-getter|1|94-postInit
            // write post-init user code here
        }//GEN-BEGIN:|94-getter|2|
        return tbiParcelas;
    }
    //</editor-fold>//GEN-END:|94-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: font1 ">//GEN-BEGIN:|95-getter|0|95-preInit
    /**
     * Returns an initiliazed instance of font1 component.
     * @return the initialized component instance
     */
    public Font getFont1() {
        if (font1 == null) {//GEN-END:|95-getter|0|95-preInit
            // write pre-init user code here
            font1 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);//GEN-LINE:|95-getter|1|95-postInit
            // write post-init user code here
        }//GEN-BEGIN:|95-getter|2|
        return font1;
    }
    //</editor-fold>//GEN-END:|95-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tableModel1 ">//GEN-BEGIN:|97-getter|0|97-preInit
    /**
     * Returns an initiliazed instance of tableModel1 component.
     * @return the initialized component instance
     */
    public SimpleTableModel getTableModel1() {
        if (tableModel1 == null) {//GEN-END:|97-getter|0|97-preInit
            // write pre-init user code here
            tableModel1 = new SimpleTableModel(new java.lang.String[][] {//GEN-BEGIN:|97-getter|1|97-postInit
                new java.lang.String[] { "", "", "", "", "", "", "" }}, new java.lang.String[] { "Descri\u00E7\u00E3o", "Parcela", "Vencto", "Valor", "Pago", "Tipo", "Id" });//GEN-END:|97-getter|1|97-postInit
            // write post-init user code here
        }//GEN-BEGIN:|97-getter|2|
        return tableModel1;
    }
    //</editor-fold>//GEN-END:|97-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdTabelaVoltar ">//GEN-BEGIN:|99-getter|0|99-preInit
    /**
     * Returns an initiliazed instance of cmdTabelaVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdTabelaVoltar() {
        if (cmdTabelaVoltar == null) {//GEN-END:|99-getter|0|99-preInit
            // write pre-init user code here
            cmdTabelaVoltar = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|99-getter|1|99-postInit
            // write post-init user code here
        }//GEN-BEGIN:|99-getter|2|
        return cmdTabelaVoltar;
    }
    //</editor-fold>//GEN-END:|99-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdTabelaVisualizar ">//GEN-BEGIN:|101-getter|0|101-preInit
    /**
     * Returns an initiliazed instance of cmdTabelaVisualizar component.
     * @return the initialized component instance
     */
    public Command getCmdTabelaVisualizar() {
        if (cmdTabelaVisualizar == null) {//GEN-END:|101-getter|0|101-preInit
            // write pre-init user code here
            cmdTabelaVisualizar = new Command("Ver + Dados", Command.OK, 0);//GEN-LINE:|101-getter|1|101-postInit
            // write post-init user code here
        }//GEN-BEGIN:|101-getter|2|
        return cmdTabelaVisualizar;
    }
    //</editor-fold>//GEN-END:|101-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFormTabela ">//GEN-BEGIN:|104-getter|0|104-preInit
    /**
     * Returns an initiliazed instance of cmdFormTabela component.
     * @return the initialized component instance
     */
    public Command getCmdFormTabela() {
        if (cmdFormTabela == null) {//GEN-END:|104-getter|0|104-preInit
            // write pre-init user code here
            cmdFormTabela = new Command("Ver tabela", Command.OK, 0);//GEN-LINE:|104-getter|1|104-postInit
            // write post-init user code here
        }//GEN-BEGIN:|104-getter|2|
        return cmdFormTabela;
    }
    //</editor-fold>//GEN-END:|104-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdFindTabela ">//GEN-BEGIN:|106-getter|0|106-preInit
    /**
     * Returns an initiliazed instance of cmdFindTabela component.
     * @return the initialized component instance
     */
    public Command getCmdFindTabela() {
        if (cmdFindTabela == null) {//GEN-END:|106-getter|0|106-preInit
            // write pre-init user code here
            cmdFindTabela = new Command("Tabela", Command.OK, 0);//GEN-LINE:|106-getter|1|106-postInit
            // write post-init user code here
        }//GEN-BEGIN:|106-getter|2|
        return cmdFindTabela;
    }
    //</editor-fold>//GEN-END:|106-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdTabelaExcluir ">//GEN-BEGIN:|108-getter|0|108-preInit
    /**
     * Returns an initiliazed instance of cmdTabelaExcluir component.
     * @return the initialized component instance
     */
    public Command getCmdTabelaExcluir() {
        if (cmdTabelaExcluir == null) {//GEN-END:|108-getter|0|108-preInit
            // write pre-init user code here
            cmdTabelaExcluir = new Command("Excluir", Command.OK, 0);//GEN-LINE:|108-getter|1|108-postInit
            // write post-init user code here
        }//GEN-BEGIN:|108-getter|2|
        return cmdTabelaExcluir;
    }
    //</editor-fold>//GEN-END:|108-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: splashScreen ">//GEN-BEGIN:|110-getter|0|110-preInit
    /**
     * Returns an initiliazed instance of splashScreen component.
     * @return the initialized component instance
     */
    public SplashScreen getSplashScreen() {
        if (splashScreen == null) {//GEN-END:|110-getter|0|110-preInit
            // write pre-init user code here
            splashScreen = new SplashScreen(getDisplay());//GEN-BEGIN:|110-getter|1|110-postInit
            splashScreen.setTitle("SysContasME");
            splashScreen.setCommandListener(this);
            splashScreen.setFullScreenMode(true);
            splashScreen.setImage(getImage1());
            splashScreen.setText("Controle de Contas Mobile");
            splashScreen.setTextFont(getFont1());
            splashScreen.setTimeout(3000);//GEN-END:|110-getter|1|110-postInit
            // write post-init user code here
        }//GEN-BEGIN:|110-getter|2|
        return splashScreen;
    }
    //</editor-fold>//GEN-END:|110-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdLoginEntrar ">//GEN-BEGIN:|117-getter|0|117-preInit
    /**
     * Returns an initiliazed instance of cmdLoginEntrar component.
     * @return the initialized component instance
     */
    public Command getCmdLoginEntrar() {
        if (cmdLoginEntrar == null) {//GEN-END:|117-getter|0|117-preInit
            // write pre-init user code here
            cmdLoginEntrar = new Command("Entrar", Command.OK, 0);//GEN-LINE:|117-getter|1|117-postInit
            // write post-init user code here
        }//GEN-BEGIN:|117-getter|2|
        return cmdLoginEntrar;
    }
    //</editor-fold>//GEN-END:|117-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdLoginCancelar ">//GEN-BEGIN:|119-getter|0|119-preInit
    /**
     * Returns an initiliazed instance of cmdLoginCancelar component.
     * @return the initialized component instance
     */
    public Command getCmdLoginCancelar() {
        if (cmdLoginCancelar == null) {//GEN-END:|119-getter|0|119-preInit
            // write pre-init user code here
            cmdLoginCancelar = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|119-getter|1|119-postInit
            // write post-init user code here
        }//GEN-BEGIN:|119-getter|2|
        return cmdLoginCancelar;
    }
    //</editor-fold>//GEN-END:|119-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: image1 ">//GEN-BEGIN:|115-getter|0|115-preInit
    /**
     * Returns an initiliazed instance of image1 component.
     * @return the initialized component instance
     */
    public Image getImage1() {
        if (image1 == null) {//GEN-END:|115-getter|0|115-preInit
            // write pre-init user code here
            try {//GEN-BEGIN:|115-getter|1|115-@java.io.IOException
                image1 = Image.createImage("/res/conta3.jpg");
            } catch (java.io.IOException e) {//GEN-END:|115-getter|1|115-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|115-getter|2|115-postInit
            // write post-init user code here
        }//GEN-BEGIN:|115-getter|3|
        return image1;
    }
    //</editor-fold>//GEN-END:|115-getter|3|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmLogin ">//GEN-BEGIN:|123-getter|0|123-preInit
    /**
     * Returns an initiliazed instance of frmLogin component.
     * @return the initialized component instance
     */
    public Form getFrmLogin() {
        if (frmLogin == null) {//GEN-END:|123-getter|0|123-preInit
            // write pre-init user code here
            frmLogin = new Form("Login", new Item[] { getTfUsuario(), getTfSenha() });//GEN-BEGIN:|123-getter|1|123-postInit
            frmLogin.addCommand(getCmdLoginEntrar1());
            frmLogin.addCommand(getCmdLoginCancelar1());
            frmLogin.setCommandListener(this);//GEN-END:|123-getter|1|123-postInit
            // write post-init user code here
        }//GEN-BEGIN:|123-getter|2|
        return frmLogin;
    }
    //</editor-fold>//GEN-END:|123-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfUsuario ">//GEN-BEGIN:|124-getter|0|124-preInit
    /**
     * Returns an initiliazed instance of tfUsuario component.
     * @return the initialized component instance
     */
    public TextField getTfUsuario() {
        if (tfUsuario == null) {//GEN-END:|124-getter|0|124-preInit
            // write pre-init user code here
            tfUsuario = new TextField("Usu\u00E1rio:", null, 8, TextField.ANY);//GEN-LINE:|124-getter|1|124-postInit
            // write post-init user code here
        }//GEN-BEGIN:|124-getter|2|
        return tfUsuario;
    }
    //</editor-fold>//GEN-END:|124-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfSenha ">//GEN-BEGIN:|125-getter|0|125-preInit
    /**
     * Returns an initiliazed instance of tfSenha component.
     * @return the initialized component instance
     */
    public TextField getTfSenha() {
        if (tfSenha == null) {//GEN-END:|125-getter|0|125-preInit
            // write pre-init user code here
            tfSenha = new TextField("Senha:", null, 8, TextField.ANY | TextField.PASSWORD);//GEN-LINE:|125-getter|1|125-postInit
            // write post-init user code here
        }//GEN-BEGIN:|125-getter|2|
        return tfSenha;
    }
    //</editor-fold>//GEN-END:|125-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdLoginEntrar1 ">//GEN-BEGIN:|126-getter|0|126-preInit
    /**
     * Returns an initiliazed instance of cmdLoginEntrar1 component.
     * @return the initialized component instance
     */
    public Command getCmdLoginEntrar1() {
        if (cmdLoginEntrar1 == null) {//GEN-END:|126-getter|0|126-preInit
            // write pre-init user code here
            cmdLoginEntrar1 = new Command("Ok", Command.OK, 0);//GEN-LINE:|126-getter|1|126-postInit
            // write post-init user code here
        }//GEN-BEGIN:|126-getter|2|
        return cmdLoginEntrar1;
    }
    //</editor-fold>//GEN-END:|126-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdLoginCancelar1 ">//GEN-BEGIN:|128-getter|0|128-preInit
    /**
     * Returns an initiliazed instance of cmdLoginCancelar1 component.
     * @return the initialized component instance
     */
    public Command getCmdLoginCancelar1() {
        if (cmdLoginCancelar1 == null) {//GEN-END:|128-getter|0|128-preInit
            // write pre-init user code here
            cmdLoginCancelar1 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|128-getter|1|128-postInit
            // write post-init user code here
        }//GEN-BEGIN:|128-getter|2|
        return cmdLoginCancelar1;
    }
    //</editor-fold>//GEN-END:|128-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstRelatorios ">//GEN-BEGIN:|133-getter|0|133-preInit
    /**
     * Returns an initiliazed instance of lstRelatorios component.
     * @return the initialized component instance
     */
    public List getLstRelatorios() {
        if (lstRelatorios == null) {//GEN-END:|133-getter|0|133-preInit
            // write pre-init user code here
            lstRelatorios = new List("Relat\u00F3rios Diversos", Choice.IMPLICIT);//GEN-BEGIN:|133-getter|1|133-postInit
            lstRelatorios.append("Gr\u00E1fico de receitas e despesas", null);
            lstRelatorios.append("Gr\u00E1fico de Pagamentos", null);
            lstRelatorios.append("Voltar", null);
            lstRelatorios.setCommandListener(this);
            lstRelatorios.setSelectedFlags(new boolean[] { false, false, false });//GEN-END:|133-getter|1|133-postInit
            // write post-init user code here
        }//GEN-BEGIN:|133-getter|2|
        return lstRelatorios;
    }
    //</editor-fold>//GEN-END:|133-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstRelatoriosAction ">//GEN-BEGIN:|133-action|0|133-preAction
    /**
     * Performs an action assigned to the selected list element in the lstRelatorios component.
     */
    public void lstRelatoriosAction() {//GEN-END:|133-action|0|133-preAction
        // enter pre-action user code here
        String __selectedString = getLstRelatorios().getString(getLstRelatorios().getSelectedIndex());//GEN-BEGIN:|133-action|1|136-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Gr\u00E1fico de receitas e despesas")) {//GEN-END:|133-action|1|136-preAction
                // write pre-action user code here
                gerarGrafDespesasReceitas();
                switchDisplayable(null, getFrmGrafRecDesp());//GEN-LINE:|133-action|2|136-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Gr\u00E1fico de Pagamentos")) {//GEN-LINE:|133-action|3|137-preAction
                // write pre-action user code here
                gerarGrafPagamentos();
                switchDisplayable(null, getFrmGrafPagamentos());//GEN-LINE:|133-action|4|137-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Voltar")) {//GEN-LINE:|133-action|5|138-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|133-action|6|138-postAction
                // write post-action user code here
            }//GEN-BEGIN:|133-action|7|133-postAction
        }//GEN-END:|133-action|7|133-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|133-action|8|
    //</editor-fold>//GEN-END:|133-action|8|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaRecPagas ">//GEN-BEGIN:|141-getter|0|141-preInit
    /**
     * Returns an initiliazed instance of tfExportaRecPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaRecPagas() {
        if (tfExportaRecPagas == null) {//GEN-END:|141-getter|0|141-preInit
            // write pre-init user code here
            tfExportaRecPagas = new TextField("Receitas Pagas:", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|141-getter|1|141-postInit
            // write post-init user code here
        }//GEN-BEGIN:|141-getter|2|
        return tfExportaRecPagas;
    }
    //</editor-fold>//GEN-END:|141-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaDesPagas ">//GEN-BEGIN:|142-getter|0|142-preInit
    /**
     * Returns an initiliazed instance of tfExportaDesPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaDesPagas() {
        if (tfExportaDesPagas == null) {//GEN-END:|142-getter|0|142-preInit
            // write pre-init user code here
            tfExportaDesPagas = new TextField("Despesas Pagas:", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|142-getter|1|142-postInit
            // write post-init user code here
        }//GEN-BEGIN:|142-getter|2|
        return tfExportaDesPagas;
    }
    //</editor-fold>//GEN-END:|142-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaSaldoPagas ">//GEN-BEGIN:|143-getter|0|143-preInit
    /**
     * Returns an initiliazed instance of tfExportaSaldoPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaSaldoPagas() {
        if (tfExportaSaldoPagas == null) {//GEN-END:|143-getter|0|143-preInit
            // write pre-init user code here
            tfExportaSaldoPagas = new TextField("Saldo (recebimentos - pagtos)", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|143-getter|1|143-postInit
            // write post-init user code here
        }//GEN-BEGIN:|143-getter|2|
        return tfExportaSaldoPagas;
    }
    //</editor-fold>//GEN-END:|143-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer1 ">//GEN-BEGIN:|144-getter|0|144-preInit
    /**
     * Returns an initiliazed instance of spacer1 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer1() {
        if (spacer1 == null) {//GEN-END:|144-getter|0|144-preInit
            // write pre-init user code here
            spacer1 = new Spacer(16, 1);//GEN-LINE:|144-getter|1|144-postInit
            // write post-init user code here
        }//GEN-BEGIN:|144-getter|2|
        return spacer1;
    }
    //</editor-fold>//GEN-END:|144-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaRecNaoPagas ">//GEN-BEGIN:|145-getter|0|145-preInit
    /**
     * Returns an initiliazed instance of tfExportaRecNaoPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaRecNaoPagas() {
        if (tfExportaRecNaoPagas == null) {//GEN-END:|145-getter|0|145-preInit
            // write pre-init user code here
            tfExportaRecNaoPagas = new TextField("Receitas N\u00E3o Pagas:", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|145-getter|1|145-postInit
            // write post-init user code here
        }//GEN-BEGIN:|145-getter|2|
        return tfExportaRecNaoPagas;
    }
    //</editor-fold>//GEN-END:|145-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaDesNaoPagas ">//GEN-BEGIN:|147-getter|0|147-preInit
    /**
     * Returns an initiliazed instance of tfExportaDesNaoPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaDesNaoPagas() {
        if (tfExportaDesNaoPagas == null) {//GEN-END:|147-getter|0|147-preInit
            // write pre-init user code here
            tfExportaDesNaoPagas = new TextField("Despesas N\u00E3o Pagas:", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|147-getter|1|147-postInit
            // write post-init user code here
        }//GEN-BEGIN:|147-getter|2|
        return tfExportaDesNaoPagas;
    }
    //</editor-fold>//GEN-END:|147-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: tfExportaSaldoNaoPagas ">//GEN-BEGIN:|148-getter|0|148-preInit
    /**
     * Returns an initiliazed instance of tfExportaSaldoNaoPagas component.
     * @return the initialized component instance
     */
    public TextField getTfExportaSaldoNaoPagas() {
        if (tfExportaSaldoNaoPagas == null) {//GEN-END:|148-getter|0|148-preInit
            // write pre-init user code here
            tfExportaSaldoNaoPagas = new TextField("Total de pend\u00EAncias:", "0,00", 32, TextField.ANY | TextField.UNEDITABLE);//GEN-LINE:|148-getter|1|148-postInit
            // write post-init user code here
        }//GEN-BEGIN:|148-getter|2|
        return tfExportaSaldoNaoPagas;
    }
    //</editor-fold>//GEN-END:|148-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|149-getter|0|149-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|149-getter|0|149-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|149-getter|1|149-postInit
            // write post-init user code here
        }//GEN-BEGIN:|149-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|149-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand ">//GEN-BEGIN:|151-getter|0|151-preInit
    /**
     * Returns an initiliazed instance of cancelCommand component.
     * @return the initialized component instance
     */
    public Command getCancelCommand() {
        if (cancelCommand == null) {//GEN-END:|151-getter|0|151-preInit
            // write pre-init user code here
            cancelCommand = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|151-getter|1|151-postInit
            // write post-init user code here
        }//GEN-BEGIN:|151-getter|2|
        return cancelCommand;
    }
    //</editor-fold>//GEN-END:|151-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmExportacao2 ">//GEN-BEGIN:|153-getter|0|153-preInit
    /**
     * Returns an initiliazed instance of frmExportacao2 component.
     * @return the initialized component instance
     */
    public Form getFrmExportacao2() {
        if (frmExportacao2 == null) {//GEN-END:|153-getter|0|153-preInit
            // write pre-init user code here
            frmExportacao2 = new Form("Exporta\u00E7\u00E3o de Dados", new Item[] { getGgExportacao() });//GEN-BEGIN:|153-getter|1|153-postInit
            frmExportacao2.addCommand(getCmdExportacaoSair());
            frmExportacao2.addCommand(getCmdExportarIniciar());
            frmExportacao2.setCommandListener(this);//GEN-END:|153-getter|1|153-postInit
            // write post-init user code here
        }//GEN-BEGIN:|153-getter|2|
        return frmExportacao2;
    }
    //</editor-fold>//GEN-END:|153-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ggExportacao ">//GEN-BEGIN:|154-getter|0|154-preInit
    /**
     * Returns an initiliazed instance of ggExportacao component.
     * @return the initialized component instance
     */
    public Gauge getGgExportacao() {
        if (ggExportacao == null) {//GEN-END:|154-getter|0|154-preInit
            // write pre-init user code here
            ggExportacao = new Gauge("", false, 100, 0);//GEN-LINE:|154-getter|1|154-postInit
            // write post-init user code here
        }//GEN-BEGIN:|154-getter|2|
        return ggExportacao;
    }
    //</editor-fold>//GEN-END:|154-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdExportacaoSair ">//GEN-BEGIN:|155-getter|0|155-preInit
    /**
     * Returns an initiliazed instance of cmdExportacaoSair component.
     * @return the initialized component instance
     */
    public Command getCmdExportacaoSair() {
        if (cmdExportacaoSair == null) {//GEN-END:|155-getter|0|155-preInit
            // write pre-init user code here
            cmdExportacaoSair = new Command("Sair", Command.OK, 0);//GEN-LINE:|155-getter|1|155-postInit
            // write post-init user code here
        }//GEN-BEGIN:|155-getter|2|
        return cmdExportacaoSair;
    }
    //</editor-fold>//GEN-END:|155-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmGrafRecDesp ">//GEN-BEGIN:|159-getter|0|159-preInit
    /**
     * Returns an initiliazed instance of frmGrafRecDesp component.
     * @return the initialized component instance
     */
    public Form getFrmGrafRecDesp() {
        if (frmGrafRecDesp == null) {//GEN-END:|159-getter|0|159-preInit
            // write pre-init user code here
            frmGrafRecDesp = new Form("Gr\u00E1fico de Receitas e despesas");//GEN-BEGIN:|159-getter|1|159-postInit
            frmGrafRecDesp.addCommand(getCmdGrafRecDespVoltar());
            frmGrafRecDesp.setCommandListener(this);//GEN-END:|159-getter|1|159-postInit
            // write post-init user code here
        }//GEN-BEGIN:|159-getter|2|
        return frmGrafRecDesp;
    }
    //</editor-fold>//GEN-END:|159-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmGrafPagamentos ">//GEN-BEGIN:|160-getter|0|160-preInit
    /**
     * Returns an initiliazed instance of frmGrafPagamentos component.
     * @return the initialized component instance
     */
    public Form getFrmGrafPagamentos() {
        if (frmGrafPagamentos == null) {//GEN-END:|160-getter|0|160-preInit
            // write pre-init user code here
            frmGrafPagamentos = new Form("Gr\u00E1fico de Pagamentos");//GEN-BEGIN:|160-getter|1|160-postInit
            frmGrafPagamentos.addCommand(getCmdGrafPagtosVoltar());
            frmGrafPagamentos.setCommandListener(this);//GEN-END:|160-getter|1|160-postInit
            // write post-init user code here
        }//GEN-BEGIN:|160-getter|2|
        return frmGrafPagamentos;
    }
    //</editor-fold>//GEN-END:|160-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdGrafRecDespVoltar ">//GEN-BEGIN:|163-getter|0|163-preInit
    /**
     * Returns an initiliazed instance of cmdGrafRecDespVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdGrafRecDespVoltar() {
        if (cmdGrafRecDespVoltar == null) {//GEN-END:|163-getter|0|163-preInit
            // write pre-init user code here
            cmdGrafRecDespVoltar = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|163-getter|1|163-postInit
            // write post-init user code here
        }//GEN-BEGIN:|163-getter|2|
        return cmdGrafRecDespVoltar;
    }
    //</editor-fold>//GEN-END:|163-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdGrafPagtosVoltar ">//GEN-BEGIN:|165-getter|0|165-preInit
    /**
     * Returns an initiliazed instance of cmdGrafPagtosVoltar component.
     * @return the initialized component instance
     */
    public Command getCmdGrafPagtosVoltar() {
        if (cmdGrafPagtosVoltar == null) {//GEN-END:|165-getter|0|165-preInit
            // write pre-init user code here
            cmdGrafPagtosVoltar = new Command("Voltar", Command.BACK, 0);//GEN-LINE:|165-getter|1|165-postInit
            // write post-init user code here
        }//GEN-BEGIN:|165-getter|2|
        return cmdGrafPagtosVoltar;
    }
    //</editor-fold>//GEN-END:|165-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstAdministracao ">//GEN-BEGIN:|170-getter|0|170-preInit
    /**
     * Returns an initiliazed instance of lstAdministracao component.
     * @return the initialized component instance
     */
    public List getLstAdministracao() {
        if (lstAdministracao == null) {//GEN-END:|170-getter|0|170-preInit
            // write pre-init user code here
            lstAdministracao = new List("Administra\u00E7\u00E3o do sistema", Choice.IMPLICIT);//GEN-BEGIN:|170-getter|1|170-postInit
            lstAdministracao.append("Limpar base de dados - Contas", null);
            lstAdministracao.append("Cadastra parcelas para teste", null);
            lstAdministracao.append("Voltar", null);
            lstAdministracao.setCommandListener(this);
            lstAdministracao.setSelectedFlags(new boolean[] { false, false, false });//GEN-END:|170-getter|1|170-postInit
            // write post-init user code here
        }//GEN-BEGIN:|170-getter|2|
        return lstAdministracao;
    }
    //</editor-fold>//GEN-END:|170-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstAdministracaoAction ">//GEN-BEGIN:|170-action|0|170-preAction
    /**
     * Performs an action assigned to the selected list element in the lstAdministracao component.
     */
    public void lstAdministracaoAction() {//GEN-END:|170-action|0|170-preAction
        // enter pre-action user code here
        String __selectedString = getLstAdministracao().getString(getLstAdministracao().getSelectedIndex());//GEN-BEGIN:|170-action|1|173-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Limpar base de dados - Contas")) {//GEN-END:|170-action|1|173-preAction
                // write pre-action user code here
                try{
                    boolean b = new ParcelaDAO(null).apagarTudo();

                    if (b){
                       getAltSucesso().setString("Base de dados apagada com sucesso!");
                       switchDisplayable(altSucesso, getLstMenu());
                    } else {
                       getAltErro().setString("Erro ao limpar base de dados!");
                       switchDisplayable(altErro, getLstMenu());

                    }
                }catch(Exception e){
                    mostrarErro(e);
                }
//GEN-LINE:|170-action|2|173-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Cadastra parcelas para teste")) {//GEN-LINE:|170-action|3|177-preAction
                // write pre-action user code here
                cadastrarVarios();
//GEN-LINE:|170-action|4|177-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Voltar")) {//GEN-LINE:|170-action|5|174-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|170-action|6|174-postAction
                // write post-action user code here
            }//GEN-BEGIN:|170-action|7|170-postAction
        }//GEN-END:|170-action|7|170-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|170-action|8|
    //</editor-fold>//GEN-END:|170-action|8|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdTabelaBaixar ">//GEN-BEGIN:|178-getter|0|178-preInit
    /**
     * Returns an initiliazed instance of cmdTabelaBaixar component.
     * @return the initialized component instance
     */
    public Command getCmdTabelaBaixar() {
        if (cmdTabelaBaixar == null) {//GEN-END:|178-getter|0|178-preInit
            // write pre-init user code here
            cmdTabelaBaixar = new Command("Baixar", Command.OK, 0);//GEN-LINE:|178-getter|1|178-postInit
            // write post-init user code here
        }//GEN-BEGIN:|178-getter|2|
        return cmdTabelaBaixar;
    }
    //</editor-fold>//GEN-END:|178-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: lstSincronismos ">//GEN-BEGIN:|180-getter|0|180-preInit
    /**
     * Returns an initiliazed instance of lstSincronismos component.
     * @return the initialized component instance
     */
    public List getLstSincronismos() {
        if (lstSincronismos == null) {//GEN-END:|180-getter|0|180-preInit
            // write pre-init user code here
            lstSincronismos = new List("Sincronismos", Choice.IMPLICIT);//GEN-BEGIN:|180-getter|1|180-postInit
            lstSincronismos.append("Listar Dispositivos", null);
            lstSincronismos.append("Importar", null);
            lstSincronismos.append("Exportar", null);
            lstSincronismos.append("Voltar", null);
            lstSincronismos.setCommandListener(this);
            lstSincronismos.setSelectedFlags(new boolean[] { false, false, false, false });//GEN-END:|180-getter|1|180-postInit
            // write post-init user code here
        }//GEN-BEGIN:|180-getter|2|
        return lstSincronismos;
    }
    //</editor-fold>//GEN-END:|180-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: lstSincronismosAction ">//GEN-BEGIN:|180-action|0|180-preAction
    /**
     * Performs an action assigned to the selected list element in the lstSincronismos component.
     */
    public void lstSincronismosAction() {//GEN-END:|180-action|0|180-preAction
        // enter pre-action user code here
        String __selectedString = getLstSincronismos().getString(getLstSincronismos().getSelectedIndex());//GEN-BEGIN:|180-action|1|186-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Listar Dispositivos")) {//GEN-END:|180-action|1|186-preAction
                // write pre-action user code here
                this.performInquiry(new RFCOMMCommunicationFactory(), this);

//GEN-LINE:|180-action|2|186-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Importar")) {//GEN-LINE:|180-action|3|183-preAction
                // write pre-action user code here
                switchDisplayable(null, getFrmImportacao());
//GEN-LINE:|180-action|4|183-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Exportar")) {//GEN-LINE:|180-action|5|184-preAction
                // write pre-action user code here
                mostrarResumoExportacao();
                switchDisplayable(null, getFrmResumoExportacao());

//GEN-LINE:|180-action|6|184-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Voltar")) {//GEN-LINE:|180-action|7|185-preAction
                // write pre-action user code here
                switchDisplayable(null, getLstMenu());//GEN-LINE:|180-action|8|185-postAction
                // write post-action user code here
            }//GEN-BEGIN:|180-action|9|180-postAction
        }//GEN-END:|180-action|9|180-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|180-action|10|
    //</editor-fold>//GEN-END:|180-action|10|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdExportarIniciar ">//GEN-BEGIN:|191-getter|0|191-preInit
    /**
     * Returns an initiliazed instance of cmdExportarIniciar component.
     * @return the initialized component instance
     */
    public Command getCmdExportarIniciar() {
        if (cmdExportarIniciar == null) {//GEN-END:|191-getter|0|191-preInit
            // write pre-init user code here
            cmdExportarIniciar = new Command("Iniciar", Command.OK, 0);//GEN-LINE:|191-getter|1|191-postInit
            // write post-init user code here
        }//GEN-BEGIN:|191-getter|2|
        return cmdExportarIniciar;
    }
    //</editor-fold>//GEN-END:|191-getter|2|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        try{
           device.close();
        }catch(Exception e){}
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;

    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    private void cadastrarVarios() {
        Parcela[] lista = new Parcela[5];
        ParcelaDAO p = new ParcelaDAO(null);
        p.apagarTudo();
        Calendar ano1900 = Calendar.getInstance();
        ano1900.set(Calendar.DATE, 1);
        ano1900.set(Calendar.MONTH, 0);
        ano1900.set(Calendar.YEAR, 1900);
        boolean ok = true;

        DateField dtfVencto = new DateField("DAta Venc", DateField.DATE, TimeZone.getDefault());
        dtfVencto.setDate( Calendar.getInstance().getTime()  );

        for(int i = 0; i < lista.length; i++){
            lista[i] = new Parcela();
            lista[i].setCodConta(i);
            lista[i].setCodParcela(1);
            lista[i].setDescricao("DescriÃ§Ã£o " + i);
            lista[i].setBaixaManual(i % 2 == 0);
            lista[i].setPago(i % 2 == 0);
            lista[i].setClassificacao("D");
            //lista[i].setDataPagto( dtfVencto.getDate() );
            lista[i].setDataVencto( dtfVencto.getDate() );
            lista[i].setQtdParcela(1);
            lista[i].setValorVencto( (i+1)*100.00 );
            //lista[i].setValorPagto( (i+1)*100.00 );

            //if (lista[i].isPago() == false)
              //  lista[i].setDataPagto(ano1900.getTime());

            p.setParcela(lista[i]);
            p.salvar();
        }

        if (ok){
            mostrarForm(false);
            getAltSucesso().setString("Parcelas cadastradas com sucesso!");
            switchDisplayable(altSucesso, getFrmContas());
        } else {
            getAltErro().setString("Erro ao cadastrar parcelas!");
            switchDisplayable(altErro, null);
        }
    }

    private void doNavegacao(int posicao) {

        if (posicao == INICIAL)
            indexRegAtual = 0;
        else if (posicao == BEFORE)
            --indexRegAtual;
        else
            ++indexRegAtual;

        if ( indexRegAtual < 0 )
            indexRegAtual = 0;
        else if ( indexRegAtual > (listaExibicao.length-1))
            indexRegAtual = listaExibicao.length - 1;

        Parcela p = listaExibicao[indexRegAtual];
        carregarDados(p, listaExibicao.length, indexRegAtual+1);
    }

    private java.util.Date getDataAtual() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    private double[] getResumoValores() {
       double[] values = new double[2];

       for(int i = 0; i < listaExibicao.length; i++){
          Parcela p = listaExibicao[i];
          if (p.getClassificacao().equals(Parcela.RECEITA))
              values[0] += p.getValorVencto(); //receita
          else if (p.getClassificacao().equals(Parcela.DESPESA))
              values[1] += p.getValorVencto();  //despesa
       }

       return values;
    }

    private void carregarDados(Parcela parcela, int qtdRegistros, int regAtual) {

        getTfFormRegistro().setString( regAtual +" de " + qtdRegistros +"->"+
                (parcela.getClassificacao().equals(Parcela.DESPESA)?"DESP":"REC") + "/Conta: "+
                 parcela.getCodConta() );
        String pago = parcela.isPago()?"PAGA":"NAO PAGA";
        getTfFormParcela().setString( parcela.getCodParcela() + " de " + parcela.getQtdParcela() + " => " + pago);
        getTfFormDescricao().setString( parcela.getDescricao() );
        Calendar g = Calendar.getInstance();
        g.set(Calendar.DATE, 1);
        g.set(Calendar.MONTH, 0);
        g.set(Calendar.YEAR, 1900);
        getTfFormVencimento().setString(  ClassFuncoes.formataData( ClassFuncoes.dateToStr(parcela.getDataVencto()) ) );
        getTfFormValor().setString( ClassFuncoes.formataValor(parcela.getValorVencto())  );
        
        double[] x = getResumoValores();
        double saldo = x[0] - x[1];
        getTfFormResumo().setString( ClassFuncoes.formataValor(x[0]) + "-"+
                                     ClassFuncoes.formataValor(x[1])+ "= "+
                                     ClassFuncoes.formataValor(saldo) );
    }

  private void doListaParcela(List lstTodasParcelas) {
        ParcelaDAO pDao = new ParcelaDAO(null);
        Parcela[] v = pDao.listarTodasParcelas();
        String linha = "";
        lstTodasParcelas.deleteAll();
        for(int i = 0; i < v.length; i++){
           linha = String.valueOf(v[i].getCodConta());
           linha+= v[i].getDescricao();
           linha+= ClassFuncoes.formataValor(v[i].getValorVencto());
           linha+= String.valueOf(v[i].isPago());
           lstTodasParcelas.append(linha, null);
        }
    }

    private void mostrarTabela() {
        ParcelaDAO pDao = new ParcelaDAO(null);
        Parcela[] v = pDao.listarTodasParcelas();

        if (v == null || v.length == 0){
           Alert a = new Alert("ExibiÃ§Ã£o de parcelas", "NÃ£o hÃ¡ parcelas para exibir!", null, AlertType.INFO);
           switchDisplayable(a, getLstMenu());
           return;
        }

        String[][] linha = new String[v.length][7];
        int tam = v.length-1;
        /*for(int i = tam; i >=0; i--){

            linha[i][0] = v[i].getDescricao();
            linha[i][1] = String.valueOf(v[i].getCodParcela());
            linha[i][2] = ClassFuncoes.formataData(ClassFuncoes.dateToStr(v[i].getDataVencto()));
            linha[i][3] = ClassFuncoes.formataValor(v[i].getValorVencto()) ;
            linha[i][4] = v[i].isPago()?"SIM":"NAO";
            linha[i][5] = v[i].getClassificacao();
            linha[i][6] = String.valueOf(v[i].getRecordId());
        }*/
        getTableModel1().setValues(linha);
        getTableModel1().fireTableModelChanged();
        for(int i = tam; i >= 0; i--){

            tableModel1.setValue(0, i, v[i].getDescricao());
            tableModel1.setValue(1, i, String.valueOf(v[i].getCodParcela()));
            tableModel1.setValue(2, i, ClassFuncoes.formataData(ClassFuncoes.dateToStr(v[i].getDataVencto())));
            tableModel1.setValue(3, i, ClassFuncoes.formataValor(v[i].getValorVencto())) ;
            tableModel1.setValue(4, i, v[i].isPago()?"SIM":"NAO");
            tableModel1.setValue(5, i, v[i].getClassificacao());
            tableModel1.setValue(6, i, String.valueOf(v[i].getRecordId()));
        }
        getTableModel1().fireTableModelChanged();
        getTbiParcelas().setTitle("Qtd. Reg.:" + v.length);
    }

  private void showParcela(int codConta, int codParcela){
        ParcelaDAO pDao = new ParcelaDAO(null);
        Parcela p = pDao.find(codConta, codParcela);
        if (p == null) {
           mostrarMensagem("Parcela nao encontrada!");
        } else {
           listaExibicao = new Parcela[1];
           listaExibicao[0] = p;
           carregarDados(p, 1, 1);
           
           switchDisplayable(null, getFrmContas() );
        }

  }

    public void mostrarErro(String mensagem) {
        getAltErro().setString(mensagem);
        getDisplay().setCurrent(altErro);
    }

    public void mostrarErro(Exception e) {
        getAltErro().setString("Erro: " + e.getMessage());
        getDisplay().setCurrent(altErro);
    }

    public void mostrarMensagem(String e) {
        getAltSucesso().setString(e);
        getDisplay().setCurrent(altSucesso);
    }

    public void mostrarForm(boolean exibe){
        ParcelaDAO p = new ParcelaDAO(null);
        listaExibicao = p.listarTodasParcelas();
        indexRegAtual = 0;
        if (listaExibicao.length == 0){
           Alert a = new Alert("ExibiÃ§Ã£o de parcelas", "NÃ£o hÃ¡ parcelas para exibir!", null, AlertType.INFO);
           switchDisplayable(a, getLstMenu());
           return;
        }
        doNavegacao(INICIAL);

        if (exibe)
          switchDisplayable(null, getFrmContas());
    }

    //exportação
     

    //bluetooth
    public void connectionEstablished(ServerDevice device, RemoteDevice remote) {
        device.startListening();
        device.setEnableBroadcast(true);
        frmImportacao.setDevice(device);
        mostrarForm(device);
    }

    public void errorOnConnection(IOException arg0) {
        //throw new UnsupportedOperationException("Erro na conexão com o bluetooth.");
    }


    public void performInquiry(CommunicationFactory factory, MidletME midlet) {
        this.getDisplay().setCurrent(new ListaDispositivos(factory, midlet));
        this.isServidor = false;
    }

    public void mostrarForm(Device device){
        this.device = device;
        getFrmImportacao().setDevice(device);
        device.startListening();
        switchDisplayable(null, getLstSincronismos() );

    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public double[] getValores(){
        ParcelaDAO pDao = new ParcelaDAO(null);
        Parcela[] v = pDao.listarTodasParcelas();

        //variáveis para os diversos valores
        double totalRecPaga  = .0;
        double totalDesPaga  = .0;
        double totalRecNPaga = .0;
        double totalDesNPaga = .0;
        double totalMaior    = .0;
        double[] tabela = new double[7];

        for(int i = 0; i < v.length; i++){
            Parcela p = v[i];

            if (p.getClassificacao().equals(Parcela.DESPESA)){
                if (p.isPago())
                    totalDesPaga += p.getValorVencto();
                else
                    totalDesNPaga+= p.getValorVencto();
            } else if (p.getClassificacao().equals(Parcela.RECEITA)){
                if (p.isPago())
                    totalRecPaga += p.getValorVencto();
                else
                    totalRecNPaga+= p.getValorVencto();
            }
        }

        tabela[TOTAL_RECEITA] = new Double(totalRecNPaga+totalRecPaga).doubleValue();
        tabela[TOTAL_DESPESA] = new Double(totalDesNPaga+totalDesPaga).doubleValue();

        tabela[TOTAL_RECEITA_PAGA] = new Double(totalRecPaga).doubleValue();
        tabela[TOTAL_DESPESA_PAGA] = new Double(totalDesPaga).doubleValue();

        tabela[TOTAL_RECEITA_NAOPAGA] = new Double(totalRecNPaga).doubleValue();
        tabela[TOTAL_DESPESA_NAOPAGA] = new Double(totalDesNPaga).doubleValue();

        totalMaior = tabela[TOTAL_RECEITA];
        if (tabela[TOTAL_DESPESA] > totalMaior)
            totalMaior = tabela[TOTAL_DESPESA];


        tabela[MAIOR_VALOR_LISTA]     = totalMaior;

        return tabela;
    }

    private String formataValorString(String value){
        double d = Double.parseDouble(value);
        return ClassFuncoes.formataValor(d);
    }

    private void mostrarResumoExportacao(){
        double[] tabelaValores = getValores();
        double saldoPagamentos = 0.00;
        double saldoNaoPagamentos = 0.00;

        getTfExportaRecPagas().setString( ClassFuncoes.formataValor(tabelaValores[TOTAL_RECEITA_PAGA]) );
        getTfExportaDesPagas().setString( ClassFuncoes.formataValor(tabelaValores[TOTAL_DESPESA_PAGA]) );
        getTfExportaRecNaoPagas().setString( ClassFuncoes.formataValor(tabelaValores[TOTAL_RECEITA_NAOPAGA]) );
        getTfExportaDesNaoPagas().setString( ClassFuncoes.formataValor(tabelaValores[TOTAL_DESPESA_NAOPAGA]) );

        saldoPagamentos = tabelaValores[TOTAL_RECEITA_PAGA] -
                          tabelaValores[TOTAL_DESPESA_PAGA];

        saldoNaoPagamentos = tabelaValores[TOTAL_RECEITA_NAOPAGA] -
                             tabelaValores[TOTAL_DESPESA_NAOPAGA];

        getTfExportaSaldoNaoPagas().setString(ClassFuncoes.formataValor(saldoNaoPagamentos));
        getTfExportaSaldoPagas().setString(ClassFuncoes.formataValor(saldoPagamentos));

    }


    private void gerarGrafDespesasReceitas(){
        double[] tabelaValores = getValores();
        double saldoPagamentos = 0.00;
        double saldoNaoPagamentos = 0.00;

        if (tabelaValores[TOTAL_DESPESA] == 0 ||
                tabelaValores[TOTAL_RECEITA] == 0
           )
        {
            getAltErro().setString("Nao ha dados para gerar o grafico!");
            switchDisplayable(altErro, getLstRelatorios());
            return;
        }


        String receita = ClassFuncoes.formataValor(tabelaValores[TOTAL_RECEITA]);
        String despesa = ClassFuncoes.formataValor(tabelaValores[TOTAL_DESPESA]);

        Vector dados = new Vector();
        dados.addElement(new String[]{"Receitas", receita.substring(0, receita.indexOf(","))});
        dados.addElement(new String[]{"Despesas", despesa.substring(0, receita.indexOf(","))} );
        GrafCanvas canvas = new GrafCanvas();
        //parâmetros: largura, altura, dados, tamanho da fonte e cores dos dados.
        PieChart pieChart = new PieChart(canvas.getWidth(), 160, dados, 1,
                new byte[]{PieChart.BLUE, PieChart.RED});
        getFrmGrafRecDesp().append(pieChart);
    }

    private void gerarGrafPagamentos(){
        double[] tabelaValores = getValores();
        double saldoPagamentos = 0.00;
        double saldoNaoPagamentos = 0.00;

        //retira as virgulas para ir somente valores inteiros para o gráfico.
        String totalReceita = ClassFuncoes.formataValor(tabelaValores[TOTAL_RECEITA]);
        totalReceita = totalReceita.substring(0, totalReceita.indexOf(","));
        String totalRecPaga = ClassFuncoes.formataValor(tabelaValores[TOTAL_RECEITA_PAGA]);
        totalRecPaga = totalRecPaga.substring(0, totalRecPaga.indexOf(","));
        String totalDespesa = ClassFuncoes.formataValor(tabelaValores[TOTAL_DESPESA]);
        totalDespesa = totalDespesa.substring(0, totalDespesa.indexOf(","));
        String totalDespPaga = ClassFuncoes.formataValor(tabelaValores[TOTAL_DESPESA_PAGA]);
        totalDespPaga = totalDespPaga.substring(0, totalDespPaga.indexOf(","));

        if (tabelaValores[TOTAL_DESPESA] == 0 ||
                tabelaValores[TOTAL_RECEITA] == 0
           )
        {
            getAltErro().setString("Nao ha dados para gerar o grafico!");
            switchDisplayable(altErro, getLstRelatorios());
            return;
        }

        Vector dados = new Vector();
        dados.addElement(new String[]{"Total Receita", totalReceita});
        dados.addElement(new String[]{"Receitas Recebidas", totalRecPaga});
        dados.addElement(new String[]{"Total Despesas", totalDespesa});
        dados.addElement(new String[]{"Despesas Pagas", totalDespPaga});
        byte[] cores = new byte[]{BarChart.BLUE, BarChart.GREEN, BarChart.RED, BarChart.WHITE};
        GrafCanvas canvas = new GrafCanvas();
        //parametros: a largura total do gráfico,
        //a altura total do gráfico,
        //dados que formam o gráfico (detalhe, os dados são passados em um Vector,
        //                            e cada elemento do vetor é uma das "barras" do gráfico),
        //tamanho da fonte,
        //vetor de bytes representando as cores usadas (mesma lógica do PieChart),
        //um byte que representa a cor de fundo do gráfico,
        //e o valor máximo que o gráfico irá receber
        double maior = tabelaValores[MAIOR_VALOR_LISTA];
        String maiorStr = ClassFuncoes.formataValor(maior);
        maiorStr = maiorStr.substring(0, maiorStr.indexOf(","));
        BarChart chartBar = new BarChart(canvas.getWidth(), 140, dados,
                                         1, cores, BarChart.SILVER, Short.parseShort(maiorStr));
        chartBar.fullChart(false);
        chartBar.sizeBar(20);
        getFrmGrafPagamentos().append(chartBar);
    }


}
class GrafCanvas extends Canvas{

    protected void paint(Graphics g) {
        
    }


}