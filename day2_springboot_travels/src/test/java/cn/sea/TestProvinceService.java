package cn.sea;

import cn.sea.entity.Province;
import cn.sea.service.ProvinceService;
import cn.sea.vo.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestProvinceService {

    @Autowired
    private ProvinceService provinceService;

    @Test
    public void testFingByPage(){
        PageInfo<Province> pageInfo = provinceService.findByPage(1, 2);
        System.out.println(pageInfo);
        pageInfo.getData().forEach( d -> System.out.println(d));
    }

}
