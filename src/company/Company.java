package company;

import java.util.ArrayList;

import store.Storable;

public class Company extends Storable{
    private String name;
    private String location;
    private String type;
    private String description;
    private ArrayList<Path> paths;

    public Company(int id, String name, String location, String type, String description, ArrayList<Path> paths) {
    	setId(id);
        this.name = name;
        this.location = location;
        this.type = type;
        this.description = description;
        if(paths == null) this.paths = new ArrayList<Path>();
        else this.paths = paths;
    }
    
    public Company(String name, String location, String type, String description) {
    	this.name = name;
    	this.location = location;
    	this.type = type;
    	this.description = description;
    	this.paths = new ArrayList<Path>();
    }

    public String getName() {
        return this.name;
    }
    
    public String getLocation() {
    	return this.location;
    }
    
    public String getType() {
    	return this.type;
    }

    public ArrayList<Path> getPaths() {
        return this.paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }
    
    public void addPath(Path path) {
    	this.paths.add(path);
    }
    
    public String getDescription() {
    	return this.description;
    }
}