import static org.junit.Assert.*;
import org.junit.Test;


public class TestSuite {

    @Test
    public void test1ProduceHugo() {
        assertEquals("Hugo", Hugo.produceHugo(1));
    }

    @Test
    public void test2ProduceHugo() {
        assertEquals("Hugo", Hugo.produceHugo(2));
    }

}
