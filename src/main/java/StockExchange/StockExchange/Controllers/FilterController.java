package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.Entities.Entities;
import StockExchange.StockExchange.Entities.Offer;
import StockExchange.StockExchange.StringCriteria.Criteria;
import StockExchange.StockExchange.StringCriteria.QueryConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnegative;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filter")
@CrossOrigin("*")
@Validated
public class FilterController {

    @Autowired
    QueryConstructor constructor;

    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> filter(@RequestBody @Valid Filter filter,
                                    @RequestParam @Min(0) int offset,
                                    @RequestParam @Min(0) int limit){
        var criteria = filter.buildCriteria();
        Class<?> clazz =Entities.valueOf(filter.getPrimary()).getClazz();
        var query = constructor.createQuery(clazz,criteria.toArray(new Criteria[0]));
        var result = constructor.executeQuery(query,offset,limit);
        return ResponseEntity.ok(result);
    }

}
