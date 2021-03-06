package es.uam.eps.ads.p3.Classes;

/**
 * Clase que define la persona que viaja por los caminos
 * 
 * @author Juan Martin (juan.martinp@estudiante.uam.es) y Pablo Sanchez (pablo.sanchezredondo@estudiante.uam.es)
 */
public class Explorador{

	/**
	 * Nombre del explorador
	 */
	private String nombre;

	/**
	 * Cantidad de energia disponible
	 */
	protected int stamina;

	/**
	 * Posada donde se encuentra el explorador
	 */
	protected Posada lugarActual;

	/**
	 * Constructor de clase
	 * 
	 * @param nombre Como se llama nuestro explorador
	 * @param energia Energia con la que comienza
	 * @param start Desde donde comienza la aventura
	 */
	public Explorador(String nombre, int energia, Posada start){
		this.nombre = nombre;
		this.stamina = energia;
		this.lugarActual = start;

	}

	/**
	 * Nombre del explorador
	 * 
	 * @return como se llama
	 */
	public String getNombre(){
		
		return this.nombre;
	}

	/**
	 * Energia actual
	 * 
	 * @return stamina
	 */
	public int getStamina(){
		
		return this.stamina;
	}

	/**
	 * Donde se encuentra el explorador
	 * 
	 * @return Posada
	 */
	public Posada getLugar(){
		
		return this.lugarActual;
	}

	/**
	 * Redefinicion del toString
	 * 
	 * @return String
	 */
	public String toString(){
		
		return this.nombre + "(e:" + this.stamina + ") en " + this.lugarActual.getNombre();
	}

	/**
	 * El explorador recorre este camino
	 * 
	 * @param camino a recorrer
	 * 
	 * @return true si lo ha recorrido
	 */
	public Boolean recorre(Camino camino){

		Posada destino = camino.getDestino();

		if(this.puedeRecorrerCamino(camino) && this.puedeAlojarseEn(destino) && 
			this.lugarActual.getCamino(destino) == camino){
			
			this.lugarActual.bookOut(this);
			this.stamina -=  camino.costeReal(); 
			this.stamina += destino.getEnergia();
			this.lugarActual = camino.getDestino();
			this.lugarActual.bookIn(this);
			return true;
		}

		return false;
	}

	/**
	 * Recorre varias posadas
	 * 
	 * @param posadas array de posadas
	 * 
	 * @return true si ha podido recorrer todas
	 */
	public Boolean recorre(Posada ...posadas){
		
		Boolean flag = true;

		for(Posada posada : posadas){
			Camino camino = this.lugarActual.getCamino(posada);
			if(camino == null || !recorre(camino))
				flag = false;
		}
		
		return flag;
	}

	/**
	 * Comprueba que el explorador tenga energia para recorrer el camino
	 * 
	 * @param camino a comprobar si puede recorrer
	 * 
	 * @return boolean
	 */
	public Boolean puedeRecorrerCamino(Camino camino){
		
		return this.stamina >= camino.costeReal();
	}

	/**
	 * Comprueba si el explorador puede alojarse en posada
	 * 
	 * @param posada a comprobar si se puede alojar

	 * @return true por ahora
	 */
	public Boolean puedeAlojarseEn(Posada posada){
		
		posada.llamar(this);

		return true;
	}

	/**
	 * Te marca que tipo de mago es
	 * 
	 * @return Un string en mayusculas del tipo de mago que es
	 */
	public String tipoDeMago(){
		return "NONE";
	}
	
}