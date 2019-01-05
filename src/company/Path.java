package company;

import store.Storable;

public class Path extends Storable{
    private String path;
    private String name;
    private int companyId;

    public Path(int id, String path, String name, int companyId) {
        super.setId(id);
        this.path = path;
        this.name = name;
        this.companyId = companyId;
    }
    
    public Path(String path, String name, int companyId) {
    	this.path = path;
    	this.name = name;
    	this.companyId = companyId;
    }

    public String getPath() {
        return this.path;
    }
    
    public String getName() {
    	return this.name;
    }

    public int getCompanyId() {
        return this.companyId;
    }
}