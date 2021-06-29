package it.polito.tdp.yelp.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private YelpDao dao;
	private SimpleDirectedWeightedGraph<Business,DefaultWeightedEdge> grafo;
	private Map<String, Business> idMap;
	private List<Business> listaMigliore;
	boolean flag;
	
	public Model() {
		dao=new YelpDao();
		idMap=new HashMap<String,Business>();
		
		
	}
	
	public List<Business>getCity() {
		return dao.getAllBusiness();
	}
	
	public void creaGrafo(String citta,int anno) {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici(citta, anno,idMap));
		List<Adiacenza> lista=dao.getAdiacenza(citta, anno, idMap);
		
		for(Adiacenza aa:lista) {
			if(aa.getP1()-aa.getP2()!=0) {
				if (aa.getP1()>aa.getP2()) {
				Graphs.addEdge(this.grafo, aa.getB2(), aa.getB1(), aa.getP1()-aa.getP2() );
			 } else {
					Graphs.addEdge(this.grafo, aa.getB1(), aa.getB2(), aa.getP2()-aa.getP1() );
			 }
			}
		
					
				
		
		
	}

		
}

	public int getVertexsize() {
		return this.grafo.vertexSet().size();
	}

	public int getArchiSize() {
		return this.grafo.edgeSet().size();
	}
	
	public Business getMigliore() {
		String s="";
		double max = 0.0 ;
		Business result = null ;
		
		for(Business b: this.grafo.vertexSet()) {
			double val = 0.0 ;
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(b))
				val += this.grafo.getEdgeWeight(e) ;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(b))
				val -= this.grafo.getEdgeWeight(e) ;
			
			if(val>max) {
				max = val ;
				result = b ;
			}
		}
		
		return result; 
	}
	public Set<Business> getVertici(){
		return this.grafo.vertexSet();
	}

	public List<Business> calcolaPercorso(double i, Business b) {
		flag=false;
		listaMigliore= new ArrayList<Business>(this.grafo.vertexSet());
		List<Business> parziale = new ArrayList<Business>() ;
		parziale.add(b) ;
		
		cerca(parziale,b, this.getMigliore(), i) ;
		if(flag==false) {
			return new LinkedList<Business>();
		}
		return this.listaMigliore ;
		
	}

	private void cerca(List<Business> parziale,Business partenza, Business arrivo, double i2) {
		
		if(parziale.get(parziale.size()-1).equals(arrivo)) {
			if(listaMigliore.size()>parziale.size()) {
				flag=true;
				listaMigliore=new ArrayList<>(parziale);
			}
			
			
		}else {
			for(DefaultWeightedEdge dd:this.grafo.outgoingEdgesOf(partenza)) {
				if(grafo.getEdgeWeight(dd)>=i2 && !parziale.contains(grafo.getEdgeTarget(dd))) {
					parziale.add(grafo.getEdgeTarget(dd));
					cerca(parziale,grafo.getEdgeTarget(dd),arrivo,i2);
					parziale.remove(grafo.getEdgeTarget(dd));
					
				}
			}
		}
		
		
	}
	
}
