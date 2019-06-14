import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Cliente {
	
	private Socket cliente;
	
	public Cliente(int puerto, String ip) {
		try {
			cliente = new Socket(ip, puerto);
			new ClienteHilo(cliente).start();			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escribe() throws IOException, NullPointerException {
		InetAddress address = InetAddress.getLocalHost();
		String ip = address.getHostAddress();
		InputStreamReader leer = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(leer);
		System.out.print(">: ");
		String texto = buffer.readLine();
		while(!texto.equals("Salir")) {
			new DataOutputStream(cliente.getOutputStream()).writeUTF(ip + ": " + texto);
			System.out.print(">: ");
			texto = buffer.readLine();
		}
		cliente.close();
	}

	public static void main(String[] args) {
		try {
			new Cliente(9999, "localhost").escribe();
		} catch (Exception e) {
			System.err.println("Se cerro la conexión");
		}
	} 

}
