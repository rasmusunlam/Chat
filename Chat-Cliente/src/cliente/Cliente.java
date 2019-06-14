package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTextField enviarTextfield;
	private JTextArea areaTexto;
	private JLabel nickLabel;
	private JLabel miNickLabel;
	private JButton enviarButton;
	
	private Socket cliente;
	private PaqueteEnvio paquete;
	private DataInputStream lectura;
	private DataOutputStream escritura;
	private String nickIngresado;
	
	//private ObjectInputStream entrada;
	//private ObjectOutputStream salida;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente("localhost",10000);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cliente(String ip, int puerto) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 281, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		enviarTextfield = new JTextField();
		enviarTextfield.setBounds(10, 277, 146, 20);
		contentPane.add(enviarTextfield);
		enviarTextfield.setColumns(10);
		areaTexto = new JTextArea();
		areaTexto.setEnabled(false);
		areaTexto.setBounds(10, 63, 245, 203);
		contentPane.add(areaTexto);
		
		nickLabel = new JLabel("Nick:");
		nickLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nickLabel.setBounds(112, 11, 46, 14);
		contentPane.add(nickLabel);
		
		miNickLabel = new JLabel("");
		miNickLabel.setHorizontalAlignment(SwingConstants.LEFT);
		miNickLabel.setBounds(168, 11, 87, 14);
		contentPane.add(miNickLabel);
		
		paquete = new PaqueteEnvio();
		try {
			cliente = new Socket(ip, puerto);
			new EscuchaServidor(cliente, lectura,this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.nickIngresado = JOptionPane.showInputDialog("Nick: ");
		miNickLabel.setText(nickIngresado);
		
		enviarButton = new JButton("Enviar");
		
		enviarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				enviarMensaje();
				
			}
		});
		enviarButton.setBounds(166, 276, 89, 23);
		contentPane.add(enviarButton);
		
		enviarTextfield.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					enviarMensaje();
				}
			}
			
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				cerrarConexion();
				
			}	
			
		});
				
	}
	
	public void enviarMensaje(){
		
		//paquete.setNick(nickTextField.getText());
		//paquete.setMensaje(enviarTexto.getText());
		
		areaTexto.append("\n"+"YO: "+enviarTextfield.getText());
		try {
			
			escritura = new DataOutputStream(cliente.getOutputStream());
			escritura.writeUTF(nickIngresado+": " + enviarTextfield.getText());
			
			//salida = new ObjectOutputStream(cliente.getOutputStream());
			//salida.writeObject(paquete);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		enviarTextfield.setText("");
	}
	
	/*public void escribirMensaje(PaqueteEnvio mensaje){
	
		areaTexto.append("\n"+mensaje.getNick()+": "+mensaje.getMensaje());
	}*/

	public void escribirMensaje(String mensaje){

		areaTexto.append("\n"+mensaje);
	}
	
	public void cerrarConexion() {
		
       try {
		cliente.close();
       } catch (IOException e) {
		
		e.printStackTrace();
       }
    }
	
}
