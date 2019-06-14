package servidor;

import java.io.Serializable;

public class PaqueteEnvio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
	private String mensaje;
	
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nombre) {
		this.nick = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
