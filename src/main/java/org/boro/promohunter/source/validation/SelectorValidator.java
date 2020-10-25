package org.boro.promohunter.source.validation;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.ItemImporter;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class SelectorValidator implements ConstraintValidator<Selector, String> {

    private final ItemImporter itemImporter;

    @Override
    public boolean isValid(String selector, ConstraintValidatorContext context) {
        if (selector == null || selector.isEmpty()) {
            return true;
        }
        return itemImporter.isSelectorValid(selector);
    }
}
