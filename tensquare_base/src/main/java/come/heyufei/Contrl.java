package come.heyufei;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.IdWorker;

@Controller
public class Contrl {

    @RequestMapping("uss")
    @ResponseBody
    public Long dem(){
        return new IdWorker().nextId();
    }
}
