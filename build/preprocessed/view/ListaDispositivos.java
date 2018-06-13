/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.entity.Device;
import net.java.dev.marge.entity.ClientDevice;
import net.java.dev.marge.entity.config.ClientConfiguration;
import net.java.dev.marge.factory.CommunicationFactory;
import net.java.dev.marge.inquiry.DeviceDiscoverer;
import net.java.dev.marge.inquiry.InquiryListener;
import net.java.dev.marge.inquiry.ServiceDiscoverer;
import net.java.dev.marge.inquiry.ServiceSearchListener;
import util.Constantes;

/**
 *
 * @author 9906
 */
public class ListaDispositivos extends List implements CommandListener,
        ServiceSearchListener, InquiryListener  {
    private Vector devices;
    private CommunicationFactory factory;
    private Command stopOrBack;
    private MidletME midlet;
    private Command conectar;

    public ListaDispositivos(CommunicationFactory factory, MidletME midlet){
        super("Buscando...", List.IMPLICIT);
        this.factory = factory;
        this.midlet  = midlet;
        iniciarBusca();

    }

    private void iniciarBusca(){
        setTitle("Buscando...");

        this.devices = new Vector(5);
        this.addCommand(this.stopOrBack = new Command("Parar",
                Command.CANCEL, 0));
        this.setCommandListener(this);
        new Thread() {

            //inicia a procura, propriamente dita.
            public void run() {
                try {
                    DeviceDiscoverer.getInstance().startInquiryGIAC(ListaDispositivos.this);
                } catch (BluetoothStateException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public void deviceDiscovered(RemoteDevice device, DeviceClass deviceClass) {
        //o dispositivo que achar, vai colocando num VECTOR e mostra seu nome na lista
        this.devices.addElement(device);
        setTitle("Buscando... " + this.devices.size());
        try {
            this.append(device.getFriendlyName(false), null);
        } catch (IOException     e) {
            this.append(device.getBluetoothAddress(), null);
            //this.midlet.mostrarErro(e);
        }
    }

    public void inquiryCompleted(RemoteDevice[] devices) {
        //finalizando a procura, mostra quantos foram encontrados e troca o label do botão "Parar" por "Voltar"
        this.setTitle(Integer.toString(this.devices.size()) + " encontrados");
        this.removeCommand(this.stopOrBack);
        this.stopOrBack = new Command("Voltar", Command.BACK, 0);
        this.addCommand(this.stopOrBack);

        if (this.devices.size() > -1) {
           this.conectar = new Command("Conectar", Command.OK, 1);
           this.addCommand(this.conectar);
        } else {
           this.conectar = new Command("Inquiry Dev", Command.OK, 1);
           this.addCommand(this.conectar);

        }
    }

    public void inquiryError() {
        this.midlet.mostrarErro("Erro na procura de dispositivos.");
    }

    //se algum dispositivo foi encontrado...
   public void serviceSearchCompleted(RemoteDevice remoteDevice,
            ServiceRecord[] services) {
        try {
            //após conectar-se, é acionado este evento
            //verificar o que significa esta linha...
            //acho que é aqui que é conectado com o servidor e depois enviado para a tela do segundo parâmetro.
            ClientConfiguration config = new ClientConfiguration(services[0], this.midlet.getFrmImportacao() );
            ClientDevice clientDevice = this.factory.connectToServer(config);
            //clientDevice.setReadInterval(1000);
           // this.midlet.mostrarMensagem("Conectado!");
            //this.midlet.mostrarMensagem("Você está conectado a : " + remoteDevice.getFriendlyName(false));
            this.midlet.mostrarForm(clientDevice);

        } catch (Exception e) {
            this.midlet.mostrarErro(e);
        }
    }

    public void deviceNotReachable() {
        this.midlet.mostrarErro("Nenhum dispositivo encontrado.");
    }

    public void serviceSearchError() {
        this.midlet.mostrarErro("Erro na busca de serviços.");
    }

    public void commandAction(Command c, Displayable d) {
        //tratar a conexão com o servidor...
          if (c == this.stopOrBack) {
                if (c.getCommandType() == Command.CANCEL) {
                    try {
                        DeviceDiscoverer.getInstance().cancelInquiry();
                        this.removeCommand(c);
                        this.stopOrBack = new Command("Voltar", Command.BACK, 1);
                        this.addCommand(this.stopOrBack);
                    } catch (BluetoothStateException ex) {
                        this.midlet.mostrarErro(ex);
                    }
                } else {
                    this.midlet.getDisplay().setCurrent( this.midlet.getLstSincronismos() );
                    
                }
          }else if( c == this.conectar){

              //conecta-se no dispositivo selecionado...
              if ( this.conectar.getLabel().equalsIgnoreCase("conectar")){
                    try {
                        DeviceDiscoverer.getInstance().cancelInquiry();
                        RemoteDevice servidor = null;
                        servidor = (RemoteDevice) this.devices.elementAt(this.getSelectedIndex());
                        setTitle("Conectando-se a " + servidor.getFriendlyName(false));
                        ServiceDiscoverer.getInstance().startSearch(servidor, this);
                    } catch (Exception e) {
                        this.midlet.mostrarErro(e);
                    }
              } else {
                 iniciarBusca();

              }


          }
     }



}
