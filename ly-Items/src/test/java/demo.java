
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import entity.Result;

@SpringBootTest
public class demo {



    @Test
    public void testSelect() {
        Result r = new Result();
        r.setMessage("hyf");
        System.out.println(r);

    }

}
