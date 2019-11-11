package StockExchange.StockExchange.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.stream.Stream;

public class KeysAreArraysOfSizeValidator  implements ConstraintValidator<KeysAreArraysOfSize, Map<String,double[]>> {

    int size;

    @Override
    public boolean isValid(Map<String, double[]> map, ConstraintValidatorContext constraintValidatorContext) {
        return map.values().stream().allMatch(k -> k.length == size);
    }

    @Override
    public void initialize(KeysAreArraysOfSize constraint){
        size = constraint.size();
    }

}
