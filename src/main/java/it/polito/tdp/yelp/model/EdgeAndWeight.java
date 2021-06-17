package it.polito.tdp.yelp.model;

public class EdgeAndWeight {
	String id1;
	String id2;
	Integer peso;
	public EdgeAndWeight(String id1, String id2,Integer peso ) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.peso = peso;
	}
	
	public String getId1() {
		return id1;
	}
	public void setId1(String id1) {
		this.id1 = id1;
	}
	public String getId2() {
		return id2;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
;
	
}
