import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import aestec.*;

public class TestPlate {
    private Plate plate;

  @Before
  public void setUp() {
      plate = new PlateImpl(50,50);
  }

  @Test
  public void shouldBeThatPlatesCanBeChanged() {
      plate.set(1,2,5);
      assertThat(plate.get(1,2),is(5));

      plate.remove(1,2);
      assertNull(plate.get(1,2));
  }

  @Test
    public void shouldBeThatPlatesCanMove() {
      plate.set(0,0,5);
      plate.accelerate(1,1);
      plate.move();
      assertThat(plate.get(1,1),is(5));
      plate.accelerate(-3,-3);
      plate.move();
      assertThat(plate.get(49,49),is(5));
  }



}
