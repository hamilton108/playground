package playground;


import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import playground.dao.CarSearchDAO;
import playground.dao.DefaultCarSearchDAO;
import playground.dto.VehicleBrand;
import playground.dao.MockCarSearchDAO;
import playground.dto.VehicleListModel;
import playground.dto.VehicleVariant;
import playground.repository.CarSearchRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CarSearchDAO.class,
        DefaultCarSearchDAO.class,
        LettuceConnectionFactory.class
})
public class TestCarSearchRepository {
    private CarSearchRepository carSearchRepository;

    @Autowired
    private CarSearchDAO carSearchDAO;
    //private DefaultCarSearchDAO defaultCarSearchDAO;

    @BeforeEach
    public void init() {
        carSearchRepository = new CarSearchRepository(carSearchDAO);
        //carSearchRepository = new CarSearchRepository(new MockCarSearchDAO());
    }

    @Test
    public void testGetBrands() {
        List<VehicleBrand> brands = carSearchRepository.getBrands();
        int expected = 154;
        assertEquals(expected, brands.size(), () -> String.format("Car brands size != %d", expected));
    }

    @Test
    public void testGetModels_empty_brandid_list() {
        List<VehicleListModel> models = carSearchRepository.getModels(Collections.emptyList());
        assertEquals(0, models.size(), () -> "Car models size != 0");
    }

    @Test
    public void testGetModels_single_brandid() {
        List<Integer> brandIds = new ArrayList<>();
        brandIds.add(1650);
        List<VehicleListModel> models = carSearchRepository.getModels(brandIds);
        int expected = 44;
        assertEquals(expected, models.size(), () -> String.format("Car models size != %d", expected));

        //VehicleListModel model = models.get(0);
        //assertEquals("Corvette Model", model.getModelName(), () -> "Car model name != Corvette Model");
    }

    @Test
    public void testGetVariants_single_modelid() {
        int brandId = 1650;
        int modelId = 16;

        List<VehicleVariant> variants = carSearchRepository.getVariants(brandId, modelId);

        int expected = 43;
        assertEquals(expected, variants.size(), () -> String.format("Car variants size != %d", expected));

    }

    /*
    @Test
    public void testGetModels_muliple_brandids() {
        List<Integer> brandIds = new ArrayList<>();
        brandIds.add(1);
        brandIds.add(2);
        List<VehicleListModel> models = carSearchRepository.getModels(brandIds);
        assertEquals(3, models.size(), () -> "Car models size != 3");
    }
     */
}
