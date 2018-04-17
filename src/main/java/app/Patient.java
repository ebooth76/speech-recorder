package app;

public class Patient {
    private int age;
    private String gender;
    private String location;
    private String speechinfo;
    private String scriptname;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeechinfo() {
        return speechinfo;
    }

    public void setSpeechinfo(String speechinfo) {
        this.speechinfo = speechinfo;
    }

    public String getScriptname() {
        return scriptname;
    }

    public void setScriptname(String scriptname) {
        this.scriptname = scriptname;
    }
}
