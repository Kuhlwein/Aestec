package aestec;

//import java.util.List;

public interface Plate {//extends Iterable<Point> {

    void set(int x, int y,int value);
    void remove(int x, int y);
    Integer get(int x, int y);



    //void expand();

    //boolean contains(int x,int y);

    //void move();

    //int size();

    //List<Point> getOverlapWith(Plate plate);

    //int getAge();

}
