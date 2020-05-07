package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import game.*;




@Service
public class GameService {

	HashMap<String,Player> players = new HashMap<String,Player>();
	HashMap<String,Monstre> monstres= new HashMap<String,Monstre>();
	//structure to develop 
	HashMap<Room,HashMap<String,Room>> neighboors = new HashMap<Room,HashMap<String,Room>>();
	
	
	
    public void initializeGrid() {
    	
    	//création des salles
    	Room room0 = new Room();
    	room0.setDescription("room0");
    	Room room1 = new Room();
    	room1.setDescription("room1");
    	Room room2 = new Room();
    	room2.setDescription("room 2");
    	Room room3 = new Room();
    	room3.setDescription("room 3");
    	Room room4 = new Room();
    	room4.setDescription("room 4");
    	Room room5 = new Room();
    	room5.setDescription("room 5");
    	Room room6 = new Room();
    	room6.setDescription("room 6");
    	Room room7 = new Room();
    	room7.setDescription("room 7");
    	Room exitroom = new Room();
    	exitroom.setDescription("Vous gagnez le jeu, vous sortez de la forteresse");
    	
    	//Ajout des passages aux salles
    	Monstre monstre = new Monstre();
    	monstre.setId("0x0x");
    	monstre.setPointsForce(5);
    	monstre.setPointsVie(20);
    	monstres.put(monstre.getId(), monstre);
    	Monstre monstre1 = new Monstre();
    	monstre1.setId("1x1x");
    	monstre1.setPointsForce(5);
    	monstre1.setPointsVie(30);
    	monstres.put(monstre1.getId(), monstre1);
    	Monstre monstre2 = new Monstre();
    	monstre2.setId("2x2x");
    	monstre2.setPointsForce(5);
    	monstre2.setPointsVie(30);
    	monstres.put(monstre2.getId(), monstre2);
    	Monstre monstre3 = new Monstre();
    	monstre3.setId("3x3x");
    	monstre3.setPointsForce(5);
    	monstre3.setPointsVie(40);
    	monstres.put(monstre3.getId(), monstre3);
    	Monstre monstre4 = new Monstre();
    	monstre4.setId("4x4x");
    	monstre4.setPointsForce(5);
    	monstre4.setPointsVie(40);
    	monstres.put(monstre4.getId(), monstre4);
    	HashMap<String,Room> nextroom0 = new HashMap<String,Room>();
    	nextroom0.put("N", room1);
    	neighboors.put(room0, nextroom0);
        room0.addPassage("N");
        room0.addEntite(monstre.getId());
        
        
        
        
        HashMap<String,Room> nextroom1 = new HashMap<String,Room>();
    	nextroom1.put("S",room0);
    	nextroom1.put("E",room2);
    	nextroom1.put("W",room3);
    	neighboors.put(room1,nextroom1);
    	room1.addPassage("S");
    	room1.addPassage("E");
    	room1.addPassage("w");
    	
    	HashMap<String,Room> nextroom2 = new HashMap<String,Room>();
    	nextroom2.put("N",room4);
    	nextroom2.put("W",room1);
    	neighboors.put(room2,nextroom2);
    	room2.addPassage("N");
    	room2.addPassage("W");
    	room2.addEntite(monstre1.getId());
    	
    	HashMap<String,Room> nextroom3 = new HashMap<String,Room>();
    	nextroom3.put("N",room5);
    	nextroom3.put("E",room1);
    	neighboors.put(room3,nextroom3);
    	room3.addPassage("N");
    	room3.addPassage("E");
    	room3.addEntite(monstre2.getId());
    	
    	HashMap<String,Room> nextroom4 = new HashMap<String,Room>();
    	nextroom4.put("N", room6);
    	nextroom4.put("S", room2);
    	neighboors.put(room4, nextroom4);
    	room4.addPassage("N");
    	room4.addPassage("S");
    	
    	HashMap<String,Room> nextroom5 = new HashMap<String,Room>();
    	nextroom5.put("N", room7);
    	nextroom5.put("S",room3);
    	neighboors.put(room5,nextroom5);
    	room5.addPassage("N");
    	room5.addPassage("S");
    	
    	
    	HashMap<String,Room> nextroom6 = new HashMap<String,Room>();
    	nextroom6.put("S",room4);
    	nextroom6.put("W",exitroom);
    	neighboors.put(room6,nextroom6);
    	room6.addPassage("S");
    	room6.addPassage("W");
    	room6.addEntite(monstre3.getId());
    	
    	HashMap<String,Room> nextroom7 = new HashMap<String,Room>();
    	nextroom7.put("S",room5);
    	nextroom7.put("E",exitroom);
    	neighboors.put(room7,nextroom7);
    	room7.addPassage("S");
    	room7.addPassage("E");
    	room7.addEntite(monstre4.getId());
    	neighboors.put(exitroom,null);
    	
   
    }
	
	
	public List<String> getAll(){
		List<String> valueReturn = new ArrayList<String>();
		for ( String key : players.keySet() ) {
		    valueReturn.add(key);
		}
		return valueReturn;
	}
	
	public Player getPlayer(String id) {
		return players.get(id);
	}
	
	public void addPlayer(Player p) {
		players.put(p.getId(),p);
	}
	
	public HashMap<Room,HashMap<String,Room>> getRooms() {
		return neighboors;
	}
	
	public HashMap<String,Monstre> getMonstres(){
		return monstres;
	}
	
	// Retourne la salle dont le nom passé en parametre
	public Room getRoomByName(String room) {
		Room returnValue = new Room();
		for(Entry<Room, HashMap<String, Room>> entry : neighboors.entrySet()) {
			if(entry.getKey().getDescription().equals(room)) {
				returnValue = entry.getKey();
			}
		}
		return returnValue;
	}
	
	// retourne la room suivant la direction,sinon  la room courante en cas d'action impossible
	public Room getNextRoom(String guid,String direction) {
		Room nextroom = neighboors.get(players.get(guid).getSalle()).get(direction);
		if(players.get(guid).getSalle().getEntites().size()== 1) {
			
		
		if(nextroom != null) {
			players.get(guid).getSalle().removeEntite(guid);
			players.get(guid).setSalle(nextroom);
			nextroom.addEntite(guid);
			nextroom.setDescription("Vous changez de salle");
		}
		}else if(players.get(guid).getSalle().getEntites().size() >1) {
			nextroom = players.get(guid).getSalle();
			nextroom.setDescription("Action impossible, reste des entités dans la salle");
		}
		if(nextroom.getPassages().isEmpty()) {
			nextroom.setDescription("Félicitations, Vous venez de gagnez le jeu");
		}
		
		
		return nextroom;
	}
	
//méthode de l'action Taper
public boolean play(String guid,String cible) {
	Player player = players.get(guid);
	Player playercible = players.get(cible);
	Monstre monstre = monstres.get(cible);
	if(monstre != null) {
	while(monstre.getPointsVie()>0 && player.getPointsVie()>0) {
				
		monstre.setPointsVie(monstre.getPointsVie()-player.getPointsForce());
		if(monstre.getPointsVie()>0) {
			player.setPointsVie(player.getPointsVie()-monstre.getPointsForce());
		}else {
			player.getSalle().removeEntite(monstre.getId());
		}
	}
	
	}else if(playercible !=null && !playercible.equals(player)) {
			playercible.setPointsVie(playercible.getPointsVie()-player.getPointsForce());
			if(playercible.getPointsVie()<1) {
				player.getSalle().removeEntite(playercible.getId());
			}
			
	}
		return player.getPointsVie()>0;
	}
	
	
}
