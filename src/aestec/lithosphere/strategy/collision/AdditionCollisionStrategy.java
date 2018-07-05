package aestec.lithosphere.strategy.collision;

public class AdditionCollisionStrategy implements CollisionStrategy {
    @Override
    public void collide(Collision c) {
        int x = c.p.x, y = c.p.y;
        Integer h1 = c.plate1.get(x,y),h2 = c.plate2.get(x,y);
        if(h1==null||h2==null) return;

        if (c.plate1.getAge()>c.plate2.getAge()) {
            c.plate1.remove(x, y);
            c.plate2.set(x, y, h1 + h2);
        } else {
            c.plate2.remove(x, y);
            c.plate1.set(x, y, h1 + h2);
        }
    }
}
