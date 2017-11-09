package app;

// User model to map to DB user table and committing change to ticket #42
public class User {

    private long id;
    private String username;
    private String password;
    private String user_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUserType(){
        return user_type;
    }

    public void setUserType(){
        this.user_type = user_type;
    }

}
