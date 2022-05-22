////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public class User {
    
    private String name;
    private String surname;
    //8
    private int age;
    
    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    //8
    public int getAge() {
        return age;
    }
    

}
