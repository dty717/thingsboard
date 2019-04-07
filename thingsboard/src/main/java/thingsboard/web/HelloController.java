package thingsboard.web; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import thingsboard.dao.alarm.CassandraAlarmDao;
@Controller
public class HelloController {
    
    @Autowired
    CassandraAlarmDao cassandraAlarmDao;
    
    @RequestMapping(value = { "/hello" ,"/Hello"})
    @ResponseBody
    public String example() {
        System.out.println(cassandraAlarmDao.test());
        return "Hello World";
    }
}
