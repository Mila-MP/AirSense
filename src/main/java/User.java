public class User {
    String name;
    String age;
    String sex;
    Inhaler myInhaler;
    Location currentLoc;

    public User(String name, String age, String sex, Inhaler myInhaler, Location currentLoc){
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.myInhaler = myInhaler;
        this.currentLoc = currentLoc;
    }
    public String getCurrentLoc(){
        return currentLoc.location;
    }

}
