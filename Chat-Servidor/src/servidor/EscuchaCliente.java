package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class EscuchaCliente extends Thread {
	
	private Socket cliente;
	private ArrayList<Socket> lista;
	private DataInputStream lectura;
	private DataOutputStream escritura;
	private PaqueteEnvio paquete;
	private String mensaje;
	
	//private ObjectInputStream entrada;
	//private ObjectOutputStream salida;
	
	public EscuchaCliente(Socket cliente, ArrayList<Socket> lista){
		this.cliente = cliente;
		this.lista = lista;
		paquete = new PaqueteEnvio();
	}
	
	@Override
	public void run(){
		
		try {
			
			lectura = new DataInputStream(cliente.getInputStream()); //FLUJO DE ENTRADA
			mensaje = lectura.readUTF();
			
			while(!(mensaje.contains("Salir"))){
				
				System.out.println(mensaje);
				
				for(Socket listaCliente : lista){
					if(!(listaCliente.equals(cliente))){ 
						//Le envio el mensaje a todos menos al que lo envió.
						escritura = new DataOutputStream(listaCliente.getOutputStream());
						escritura.writeUTF(mensaje);
						escritura.flush();
					}
				}
				
				lectura = new DataInputStream(cliente.getInputStream());
				mensaje = lectura.readUTF();
			}
			
			System.out.println("El cliente se ha desconectado");
			cliente.close();
			Servidor.eliminarCliente(cliente);
			
			
			/**
			 * ESTO QUE ESTA COMENTADO RECIBIA Y ENVIABA UN PAQUETE EN VEZ DE UNA STRING
			 */
			/*entrada = new ObjectInputStream(cliente.getInputStream());
			paquete = (PaqueteEnvio) entrada.readObject();
			
			while(!(paquete.getMensaje().equals("Salir"))){
				
				System.out.println(paquete.getNick());
				System.out.println(paquete.getMensaje());
				
				for(Socket listaCliente : lista){
					if(!(listaCliente.equals(cliente))){ 
						//Le envio el mensaje a todos menos al que lo envió.
						salida = new ObjectOutputStream(listaCliente.getOutputStream());
						salida.writeObject(paquete);
						salida.flush();
					}
				}
				
				synchronized (lectura) {
				 	lectura = new ObjectInputStream(cliente.getInputStream());
					enviar = (EnviarPaquete)lectura.readObject();
				}
				
				entrada = new ObjectInputStream(cliente.getInputStream());
				paquete = (PaqueteEnvio) entrada.readObject();
				
			}
			
			System.out.println(paquete.getNick() + "Se ha deconectado ");
			cliente.close();
			entrada.close();
			salida.close();
			Servidor.eliminarCliente(cliente);*/
			
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Se ha desconectado el cliente");
		}
		
	}

}
