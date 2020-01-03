package StockExchange.StockExchange.Controllers;

import StockExchange.StockExchange.StringCriteria.Criteria;
import StockExchange.StockExchange.StringCriteria.Filter;
import StockExchange.StockExchange.StringCriteria.QueryConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/filter")
@CrossOrigin("*")
@Validated
public class FilterController {

    @Autowired
    QueryConstructor constructor;

    @PostMapping()
    public ResponseEntity<?> filter(@RequestBody @Valid Filter filter,
                                    @RequestParam @Min(0) int offset,
                                    @RequestParam @Min(0) int limit){
        var criteria = filter.buildCriteria();
        Class<?> clazz =filter.getPrimary().getClazz();
        var query = constructor.createQuery(clazz,criteria.toArray(new Criteria[0]));
        var result = constructor.executeQuery(query,offset,limit);
        return ResponseEntity.ok(result);
    }

}
