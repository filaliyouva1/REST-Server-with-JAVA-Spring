package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {

	private String description;
	private List<String> passages = new ArrayList<String>();
	private List<String> entites = new ArrayList<String>();
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getPassages() {
		return passages;
	}
	public void setPassages(List<String> passages) {
		this.passages = passages;
	}
	public List<String> getEntites() {
		return entites;
	}
	public void setEntites(List<String> entites) {
		this.entites = entites;
	}
	
	
	public void addCharacter(Character c) {
		this.entites.add(c.getId());
	}
	
	
	public void addEntite(String guid) {
		this.entites.add(guid);
	}
	
	public void removeEntite(String guid) {
		this.entites.remove(guid);
	}
	public void addPassage(String d) {
		this.passages.add(d);
	}
	
	
}
