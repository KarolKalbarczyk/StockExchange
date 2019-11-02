package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.StringCriteria.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
@CrossOrigin("*")
public class FilterController {
    @GetMapping()
    public String filter(@RequestParam("offer") List<Criteria<Offer>> criteria){
        return criteria.get(0).where().get(0);
    }
}
