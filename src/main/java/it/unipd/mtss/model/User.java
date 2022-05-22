////////////////////////////////////////////////////////////////////
// [Ruth Genevieve] [Bousapnamene] [1192088]
// [Michele] [Gatto] [1224458]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public class User {
    
    private String name;
    private String surname;
    
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;

    }
    
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }


}
