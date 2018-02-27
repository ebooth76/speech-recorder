package app;

public class User {

    private long id;
    private String username;
    private String password;
    private String usertype;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    private String valid;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsertype(){
        return usertype;
    }

    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getValid(){ return valid; }

    public void setValid(String valid){ this.valid = valid; }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }
}
