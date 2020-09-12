package org.boro.promohunter.adapters.api.item.dto;

import lombok.Builder;
import lombok.Value;
import org.boro.promohunter.item.PriceUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Value
@Builder
public class PriceUpdateResource {

    private static final PriceUpdateResourceMapper MAPPER = Mappers.getMapper(PriceUpdateResourceMapper.class);

    Double value;
    String createdAt;

   public static PriceUpdateResource of(PriceUpdate priceUpdate) {
       return MAPPER.toResource(priceUpdate);
    }

    @Mapper
    interface PriceUpdateResourceMapper {

        @Mapping(source = "value", target = "value", numberFormat = "#.##")
        @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
        PriceUpdateResource toResource(PriceUpdate priceUpdate);
    }
}
