package model.models;

public class Path extends Storable{
    private String path;
    private String name;
    private int company_Id;
    
    public Path() {
    	
    }

    public Path(int id, String path, String name, int companyId) {
        super.setId(id);
        this.path = path;
        this.name = name;
        this.company_Id = companyId;
    }
    
    public Path(String path, String name, int companyId) {
    	this.path = path;
    	this.name = name;
    	this.company_Id = companyId;
    }

    public String getPath() {
        return this.path;
    }
    
    public String getName() {
    	return this.name;
    }

    public int getCompanyId() {
        return this.company_Id;
    }
    
    public void setPath(String path) {
    	this.path = path;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setCompanyId(int companyId) {
    	this.company_Id = companyId;
    }
    
    public void setCompany_id(int companyId) {
    	this.company_Id = companyId;
    }
    
    public int getCompany_id() {
    	return this.company_Id;
    }
}