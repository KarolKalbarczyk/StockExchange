package StockExchange.StockExchange.validators;

import StockExchange.StockExchange.entities.Attributes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class KeysAreArraysOfSizeValidator implements ConstraintValidator<KeysAreArraysOfSize, Map<Attributes, double[]>> {

    int size;

    @Override
    public boolean isValid(Map<Attributes, double[]> map, ConstraintValidatorContext constraintValidatorContext) {
        return map.values().stream().allMatch(k -> k.length == size);
    }

    @Override
    public void initialize(KeysAreArraysOfSize constraint) {
        size = constraint.size();
    }
}
