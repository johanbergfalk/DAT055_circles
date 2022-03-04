package Circles.Model;

import java.util.Date;
import java.util.LinkedList;

/**
 * Stores all content of a circle.
 * @author Robert Nilsson
 * @version 2022-03-02
 */

public class Circle {
    private int id;
    private String name;
    private String creator;
    private LinkedList<String> members = new LinkedList<>();
    private String description;
    private Date startTime;
    private Date stopTime;
    private float score = 0;

    /**
     * Empty constructor used in DatabaseConn.
     */
    public Circle(){

    }

    /**
     * Constructor for Circle.
     * @param name Circle name
     * @param creator Creator of circle
     * @param description Description of circle content
     * @param startTime Date when the circle start
     * @param stopTime Date when the circle closes, not possible to join circle after this time
     */
    public Circle(String name, String creator, String description, Date startTime, Date stopTime) {
        this.name = name;
        this.creator = creator;
        this.members.add(creator);
        this.description = description;
        this.startTime = startTime;
        this.stopTime = stopTime;


    }
//----Getters and setters------------------------------------------

    /**
     *
     * @return Circle unique id
     */
    public int getId(){
        return this.id;
    }

    /**
     *
     * @param id Circle id, only to be used by DatabaseConn!
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *
     * @return Circle name
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @param name Circle name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @return username of Circle creator
     */
    public String getCreator(){
        return this.creator;
    }

    /**
     *
     * @param creator username of Circle creator
     */
    public void setCreator(String creator){
        this.creator = creator;
    }

    /**
     *
     * @return a LinkedList of all circle members, including creator
     */
    public LinkedList<String> getMembers(){
        return this.members;
    }

    /**
     *
     * @param members add a LinkedList with members
     */
    public void setMembers(LinkedList<String> members){
        this.members = members;
    }

    /**
     *
     * @param m add user as member to a circle
     */
    public void addMember(String m){
        this.members.add(m);
    }

    /**
     *
     * @return circle description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     *
     * @param description set circle description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     *
     * @return the date of when the circle start
     */
    public Date getStartTime(){
        return this.startTime;
    }

    /**
     *
     * @param startTime date when the circle start
     */
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    /**
     *
     * @return date when the circle ends
     *
     */
    public Date getStopTime(){
        return this.stopTime;
    }

    /**
     *
     * @param stopTime date when the circle ends
     */
    public void setStopTime(Date stopTime){
        this.stopTime = stopTime;
    }

    /**
     *
     * @return total score of the circle
     */
    public float getScore(){
        return this.score;
     }

    /**
     *
     * @param score total score of the circle
     */
    public void setScore(float score){
        this.score = score;
     }

}
