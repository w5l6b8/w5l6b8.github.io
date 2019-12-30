package net.ebaolife.husqvarna.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Author     ：Wu JianTao
 * @ Date       ：Created in 3:59 PM 2019/12/30
 * @ Description：${description}
 * @ Modified By：
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/tests")
    public String test()  {
        //String jsonstr1="{\"id\":\"1\",\"name\":\"aijquery\"}";//严格要求这个格式
        return "test";
    }

}
