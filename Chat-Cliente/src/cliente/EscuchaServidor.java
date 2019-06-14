package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class EscuchaServidor extends Thread{
	
	private Socket cliente;
	private DataInputStream lectura;
	private PaqueteEnvio paquete;
	private Cliente c;
	private String mensaje;
	//private ObjectInputStream lectura;
	
	public EscuchaServidor(Socket cliente, DataInputStream lectura, Cliente c) {
		this.cliente = cliente;
		this.lectura = lectura;
		paquete = new PaqueteEnvio();
		this.c = c;
		
		//this.lectura = lectura;
	}
	
	@Override
	public void run(){
		
		try {
			
			//lectura = new DataInputStream(cliente.getInputStream());
			//mensaje = lectura.readUTF();
			
			while(true){
				
				lectura = new DataInputStream(cliente.getInputStream());
				mensaje = lectura.readUTF();
				c.escribirMensaje(mensaje);
			}
			
			
			/**
			 * LO MISMO ACA, RECIBIA UN OBJETO EN VEZ DE UN STRING.
			 */
			
			/*
			while(true){
				
				lectura = new ObjectInputStream(cliente.getInputStream());
				paquete = (PaqueteEnvio)lectura.readObject();
				c.escribirMensaje(paquete);	
			}
			
			cliente.close();*/
				
		} catch (IOException e) {
			
			//e.printStackTrace();
			System.out.println("Se ha desconectado el cliente");
		}
		
		
	}

}
