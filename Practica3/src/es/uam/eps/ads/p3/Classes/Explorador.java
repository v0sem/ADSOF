package es.uam.eps.ads.p3.Classes;

/**
 * Clase que define la persona que viaja por los caminos
 * 
 * @author Juan Martin (juan.martinp@estudiante.uam.es) y Pablo Sanchez (pablo.sanchezredondo@estudiante.uam.es)
 */
public class Explorador{

	/**
	 * Tipo de mago enumeracion
	 */
	private enum MageType{
		NONE,
		HECHICERO,
		HADA
	}

	/**
	 * Nombre del explorador
	 */
	private String nombre;

	/**
	 * Cantidad de energia disponible
	 */
	private int stamina;

	/**
	 * Posada donde se encuentra el explorador
	 */
	private Posada lugarActual;

	/**
	 * Que tipo de mago es
	 */
	private MageType mage;

	/**
	 * Cuanto poder tiene un mago
	 */
	private int magePower;

	/**
	 * Constructor de clase para no magos
	 * 
	 * @param nombre Como se llama nuestro explorador
	 * @param energia Energia con la que comienza
	 * @param start Desde donde comienza la aventura
	 */
	public Explorador(String nombre, int energia, Posada start){
		this(nombre, energia, start, MageType.NONE, 0);
	}

	/**
	 * Constructor de clase para magos
	 * 
	 * @param nombre del mago
	 * @param energia inicial del mago
	 * @param start Podada de comienzo
	 * @param mage tipo de mago
	 * @param magePower nivel de poder del mago
	 */
	public Explorador(String nombre, int energia, Posada start, MageType mage, int magePower){
		this.nombre = nombre;
		this.stamina = energia;
		this.lugarActual = start;
		this.mage = mage;
		this.magePower = magePower;
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
			
			this.stamina -=  camino.costeReal(); 
			this.stamina += destino.getEnergia();
			this.lugarActual = camino.getDestino();
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
	private Boolean puedeRecorrerCamino(Camino camino){

		if(camino.esTrampa() && this.mage != MageType.NONE){
			return false;
		}
		
		return this.stamina >= camino.costeReal();
	}

	/**
	 * Comprueba si el explorador puede alojarse en posada
	 * 
	 * @param posada a comprobar si se puede alojar

	 * @return true por ahora
	 */
	private Boolean puedeAlojarseEn(Posada posada){

	Posada.LightLevel light = posada.getLight();

		switch(this.mage){
			case HADA:
				if(light.ordinal() > Posada.LightLevel.GRIS.ordinal())
					return true;
				break;
			case HECHICERO:
				if(light.ordinal() < Posada.LightLevel.GRIS.ordinal() ||
				light.ordinal() < this.magePower + Posada.LightLevel.TENEBROSA.ordinal())
					return true;
				break;
			default:
				return true;
		}
		
		return false;
	}
	
}