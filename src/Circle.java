import java.util.Date;
import java.util.LinkedList;

public class Circle {
    private int id;
    private String name;
    private String creator;
    private LinkedList<String> members =  new LinkedList<String>();
    private String description;
    private Date starttime;
    private Date stoptime;
    private float score = 0;

//linkedlist of type movie
    public Circle(){};

    public Circle(String name, String creator, String description, Date starttime, Date stoptime) {
        this.name = name;
        this.creator = creator;
        this.members.add(creator);
        this.description = description;
        this.starttime = starttime;
        this.stoptime = stoptime;
        for(int i = 0; i < 4; i++){
            String str = Integer.toString(i);
            members.add(str);
        }

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

    public void setMembers(String m){
        this.members.add(m);
    }

    public LinkedList<String> getMembers(){
        return this.members;
    }

    public String getCreator(){
        return this.creator;
    }

    public void setScore(float s){
        this.score = s;
    }
    public float getScore(){
        return this.score;
    }

    public String getDescription(){
        return this.description;
    }
}
