package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Thread{
	
	private ServerSocket servidor;
	private Socket cliente;
	private static ArrayList<Socket> socketConectados = new ArrayList<Socket>();
	
	public Servidor(int puerto){
		
		Thread hiloServidor = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					servidor = new ServerSocket(puerto);
					
					while(true){
						cliente = servidor.accept();
						System.out.println("Se ha conectado un cliente");
						socketConectados.add(cliente);
						new EscuchaCliente(cliente, socketConectados).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
		            try {
		                servidor.close();
		            } catch (IOException e) {
		            	e.printStackTrace();
		            }
				}
			}
		});
		
		hiloServidor.start();
	}
	
	public static void eliminarCliente(Socket c){
		socketConectados.remove(c);
	}

}
