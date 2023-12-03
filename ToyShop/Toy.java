import java.util.Random;

abstract class Toy {
    private Integer id;         
    private String name;       
    private Integer count;
    private String type;
    private static int newID;

    static {
        newID = new Random().nextInt(100,900);
    }

    public Toy(String name, Integer count) {
        id = newID++;
        this.name = name;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public void decreaseCount(){
        this.count --;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toUpperCase() + ": " +
                "ID " + getId() + ", " +
                "Имя " + getName();
    }
}
