package company;

import store.Storable;

public class Path extends Storable{
    private String path;
    private int companyId;

    public Path(int id, String path, int companyId) {
        super.setId(id);
        this.path = path;
        this.companyId = companyId;
    }
    
    public Path(String path, int companyId) {
    	this.path = path;
    	this.companyId = companyId;
    }

    public String getPath() {
        return this.path;
    }

    public int getCompanyId() {
        return this.companyId;
    }
}