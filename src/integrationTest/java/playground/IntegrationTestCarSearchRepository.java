package playground;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import playground.dao.CarSearchDAO;
import playground.dao.DefaultCarSearchDAO;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CarSearchDAO.class,
        DefaultCarSearchDAO.class,
        LettuceConnectionFactory.class
})
public class IntegrationTestCarSearchRepository {
    @Test
    public void testGetBrands() {
        assertEquals(1, 2);
    }
}
