package test;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
@Controller
@SpringBootApplication

public class TestApplication {
	@RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world testttttt！";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


}
