package es.uam.eps.ads.p4.Classes;

import java.util.ArrayList;
import java.util.Collections;

public class Recomendacion {

	Long usuario;
	List<Tupla> recomendaciones;

	public Recomendacion(Long u) {
		this.usuario = u;
		this.recomendaciones = new ArrayList<Tupla>();
	}

	public void insertRecommendation(Long id, Double scr) {
		recomendaciones.add(new Tupla(id, scr));
	}

	public void onlyBest(int i){
		Collections.sort(recomendaciones);
		ArrayList<Tupla> nueva = new ArrayList<Tupla>();

		for(int j=0; j<i; j++){
			nueva.add(recomendaciones.remove(recomendaciones.size()-1));
		}
	}
}