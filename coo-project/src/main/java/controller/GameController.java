package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exception.BadRequestException;
import exception.DeadException;
import exception.GuidNotFoundException;
import exception.NotSameRoomException;
import exception.WallException;
import game.Player;
import game.Room;
import service.GameService;

@RestController
public class GameController {

	private int idSuivant=0;       //Id à attribué au joueur connecté
	private boolean begin=false;   //vérification du début ou non du jeu 
	private Room startroom;  // mémorisation de la salle du debut de jeu
	
	
@Autowired	
GameService gs;


@PostMapping(value="/connect",produces = "application/json")
public Player generatePlayer(){
	
	
	Player p =new Player();
	p.setId(String.valueOf(idSuivant));
	p.setPointsVie(30);
	p.setPointsForce(20);
	//vérification du début ou non du jeu 
	if(begin==false) {
		//initialisation de la grille des salles
		gs.initializeGrid();
		begin=true;
		startroom = gs.getRoomByName("room0");
	}
	
	p.setSalle(startroom);
	gs.addPlayer(p);
	startroom.addEntite(String.valueOf(idSuivant));
	startroom.setDescription("Bienvenue au jeu");
	idSuivant++;
	
	return p;
}



@GetMapping("/{id}/regarder")
public Room regarder(@PathVariable("id") String id){
	if(gs.getPlayer(id) == null) {
		throw new GuidNotFoundException();
	}
	if(gs.getPlayer(id).getPointsVie()<1) {
		throw new DeadException();
	}
	gs.getPlayer(id).getSalle().setDescription("Vous venez de regarder dans la salle");
	return gs.getPlayer(id).getSalle();
}


@PostMapping(value="/{id}/deplacement",produces = "application/json")
public Room deplacer(@PathVariable("id") String id,@RequestBody Map<String,String> json) {
	String direction = json.get("direction");
	if(gs.getPlayer(id).getPointsVie()<1) {
		throw new DeadException();
	}
	if(gs.getPlayer(id) == null) {
		throw new GuidNotFoundException();
	}
	List<String> passages =gs.getPlayer(id).getSalle().getPassages();
	if(direction == null) {
		throw new BadRequestException();
	}
	if(!passages.contains(direction)) {
		throw new WallException();
	}
	return gs.getNextRoom(id, direction);
	
	
}



@GetMapping(value="/{idsource}/examiner/{iddest}",produces = "application/json")
public HashMap<String,String> examiner(@PathVariable("idsource") String idsource,@PathVariable("iddest") String iddest) {
	HashMap<String,String> returnvalue = new HashMap<String,String>();
	returnvalue.put("Description", "vous avez examiné une entité");
	if(gs.getPlayer(idsource).getPointsVie()<1) {
		throw new DeadException();
	}
	if(gs.getPlayer(idsource) == null) {
		throw new GuidNotFoundException();
	}else if(!gs.getPlayer(idsource).getSalle().getEntites().contains(iddest)) {
		throw new NotSameRoomException();
	}
	if (gs.getPlayer(iddest) == null) {
		returnvalue.put("Type","MONSTRE");
		returnvalue.put("Vie",String.valueOf(gs.getMonstres().get(iddest).getPointsVie()));
		
	}else if (gs.getPlayer(iddest) !=null) {
		returnvalue.put("Type","JOUEUR");
		returnvalue.put("Vie",String.valueOf(gs.getPlayer(iddest).getPointsVie()));
		  
	}
	
	return returnvalue;
}


@PostMapping(value="/{idsource}/taper",produces = "application/json")
public HashMap<String,String> taper(@PathVariable("idsource") String idsource,@RequestBody Map<String,String> json){
	HashMap<String,String> returnvalue = new HashMap<String,String>();
	String cible = json.get("cible");
	
	if(gs.getPlayer(idsource).getPointsVie()<1) {
		throw new DeadException();
	}
	boolean notdead = gs.play(idsource,cible);
	if(notdead) {
		returnvalue.put("Resultat","vous avez attaqué l'adversaire "+cible);
	}else {
		returnvalue.put("Resultat","l'adversaire "+cible+" vous a battu");
	
	}
	
	return returnvalue;
}







}
