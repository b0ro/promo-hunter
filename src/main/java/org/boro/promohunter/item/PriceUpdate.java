package org.boro.promohunter.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.boro.jpa.AuditableEntity;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PriceUpdate extends AuditableEntity {

    @DecimalMin("0.0")
    @Digits(integer = 13, fraction = 4)
    private BigDecimal value;

    public PriceUpdate(BigDecimal value) {
        this.value = value;
    }

    public PriceUpdate(BigDecimal value, LocalDateTime createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }
}
