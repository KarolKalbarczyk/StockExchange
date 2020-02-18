package StockExchange.StockExchange.controllers;

import StockExchange.StockExchange.stringCriteria.Criteria;
import StockExchange.StockExchange.stringCriteria.Filter;
import StockExchange.StockExchange.stringCriteria.QueryConstructor;
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

    private final QueryConstructor constructor;

    @Autowired
    public FilterController(QueryConstructor constructor) {
        this.constructor = constructor;
    }

    @PostMapping()
    public ResponseEntity<?> filter(@RequestBody @Valid Filter filter,
                                    @RequestParam @Min(0) int offset,
                                    @RequestParam @Min(0) int limit) {
        var criteria = filter.buildCriteria();
        Class<?> clazz = filter.getPrimary().getClazz();
        var query = constructor.createQuery(clazz, criteria.toArray(new Criteria[0]));
        var result = constructor.executeQuery(query, offset, limit);
        return ResponseEntity.ok(result);
    }

}
