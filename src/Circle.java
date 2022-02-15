import java.util.Date;

public class Circle {
    private int id;
    private String name;
    private String creator;
    private String[] members;
    private String description;
    private Date starttime;
    private Date stoptime;

//linkedlist of type movie

    public Circle(String name, String creator, String description, Date starttime, Date stoptime) {
        this.name = name;
        this.creator = creator;
        /*for (int i = 0; i < members.length; i++) {
            this.members[i] = members[i];
        }*/
        this.description = description;
        this.starttime = starttime;
        this.stoptime = stoptime;
    }

    public Date get_starttime() {
        return this.starttime;
    }

    public Date get_stoptime() {
        return this.stoptime;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }
}
