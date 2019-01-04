package servlet;

import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private String location;
    private String type;
    private ArrayList<Path> paths;

    public Company(int id, String name, String location, String type, ArrayList<Path> paths) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        if(paths == null) paths = new ArrayList<Path>();
        else this.paths = paths;
    }
    
    public Company(String name, String location, String type, ArrayList<Path> paths) {
    	//this.id = id;
    	this.name = name;
    	this.location = location;
    	this.type = type;
    	if(paths == null) paths = new ArrayList<Path>();
    	else this.paths = paths;
    }
   

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
    	this.id = id;
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
}