package StockExchange.StockExchange.Validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = KeysAreArraysOfSizeValidator.class)
public @interface KeysAreArraysOfSize {
    String message() default "{Range does not meet requirements}";
    int size() default 2;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
