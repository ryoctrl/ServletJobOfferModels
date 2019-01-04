package servlet;

public class Path {
    private int id;
    private String path;
    private int companyId;

    public Path(int id, String path, int companyId) {
        this.id = id;
        this.path = path;
        this.companyId = companyId;
    }
    
    public Path(String path, int companyId) {
    	this.path = path;
    	this.companyId = companyId;
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public int getCompanyId() {
        return this.companyId;
    }
}