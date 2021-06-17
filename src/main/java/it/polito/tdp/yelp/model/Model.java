package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	Map<String,Business> businessIDMap;
	private Graph<Business, DefaultWeightedEdge> grafo;
	
	public Model() {
		businessIDMap = new HashMap<>();
		YelpDao dao = new YelpDao();
		List<Business> business = new ArrayList<>(dao.getAllBusiness());
		for(Business b: business) {
			businessIDMap.put(b.getBusinessId(), b);
		}
	}
	
	public String doCreaGrafo(String citta, int anno) {
		YelpDao dao = new YelpDao();
		grafo =  new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		List<String> bid= new ArrayList<>(dao.getVertici(citta, anno));
		List<Business> b = new ArrayList<>();
		for(String s : bid)
			b.add(businessIDMap.get(s));
		Graphs.addAllVertices(grafo, b);
		List<EdgeAndWeight> archi = new ArrayList<>(dao.getArchi(citta, anno));
		for(EdgeAndWeight e: archi)
			Graphs.addEdge(grafo, businessIDMap.get(e.getId2()), businessIDMap.get(e.getId1()), e.getPeso());
		
		String result = "";
		if(this.grafo==null) {
			result ="Grafo non creato";
			return result;
		}
		result = "Grafo creato con :\n# "+this.grafo.vertexSet().size()+" VERTICI\n# "+this.grafo.edgeSet().size()+" ARCHI\n";
		return result;
	}
	
	public String doLocaleMigliore(){
		double pesoMax = 0.0;
		List<Business> bs = new ArrayList<>(businessIDMap.values());
		Business busi = bs.get(0);
		for(Business b: grafo.vertexSet()) {
			double entranti = 0.0;
			double uscenti = 0.0;
			for(DefaultWeightedEdge e: grafo.incomingEdgesOf(b)) {
				entranti+=grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: grafo.outgoingEdgesOf(b)) {
				uscenti+=grafo.getEdgeWeight(e);
			}
			double differenza = entranti - uscenti;
			if(differenza>pesoMax) {
				pesoMax = differenza;
				busi = b;
			}
		}
		return "LOCALE MIGLIORE : "+busi.getBusinessName()+"\n\n";
	}
	public List<String> getCities(){
		YelpDao dao = new YelpDao();
		return dao.getCities();
	}
	
}
