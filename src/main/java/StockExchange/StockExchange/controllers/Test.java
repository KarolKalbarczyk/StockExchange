package StockExchange.StockExchange.controllers;

import StockExchange.StockExchange.entities.Share;
import StockExchange.StockExchange.entities.StockTransaction;
import StockExchange.StockExchange.repositories.ShareRepository;
import StockExchange.StockExchange.repositories.TransactionRepository;
import StockExchange.StockExchange.services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aaa")
public class Test {
    int count = 0;

    @Autowired
    ShareService shareService;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    TransactionRepository repository;

    long id;
    @GetMapping("/d")
    public ResponseEntity<?> a(){
        var a = shareRepository.save(new Share(2));
        id = a.getId();
        repository.save(new StockTransaction(a));
        return ResponseEntity.ok("a");
    }

    @GetMapping("/c")
    public ResponseEntity<Share> b(){
        return ResponseEntity.ok(shareRepository.findOneById(id).get());
    }

    @GetMapping("/f")
    public ResponseEntity<?> f(@RequestParam int tesst ,@RequestParam int test) throws Exception {
        if (count++ == 1)
            throw new IllegalCallerException();
        return ResponseEntity.ok(shareRepository.findAll());
    }


}
