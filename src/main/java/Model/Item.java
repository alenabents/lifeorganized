package Model;

public class Item {

    private int id;
    private String label;
    private String date;
    private String time;
    private int check;
    private String info;

    public Item(int id, String label, String date, String time, int check) {
        super();
        this.id = id;
        this.label = label;
        this.date = date;
        this.time = time;
        this.check = check;
    }

    public Item(){
        super();
    }

    public Item(String label, String date, String time, int check) {
        super();
        this.label = label;
        this.date = date;
        this.time = time;
        this.check = check;
    }

    public Item(int id, String label, String date, String time, int check, String response) {
        super();
        this.id = id;
        this.label = label;
        this.date = date;
        this.time = time;
        this.check = check;
        this.info = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getInformFriend() {
        return info;
    }
}