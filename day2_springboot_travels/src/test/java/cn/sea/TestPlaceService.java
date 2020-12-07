package cn.sea;

import cn.sea.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestPlaceService {

    @Autowired
    private PlaceService placeService;

    @Test
    public void test() {
        System.out.println(placeService.findByProvinceIdWithPage(1,2,"2"));
    }

}
