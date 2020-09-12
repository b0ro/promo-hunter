package org.boro.promohunter.adapters.api.item.dto;

import lombok.Builder;
import lombok.Value;
import org.boro.promohunter.item.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Value
@Builder
public class ItemResource {

    private static final ItemResourceMapper MAPPER = Mappers.getMapper(ItemResourceMapper.class);

    Integer id;
    String name;
    String description;
    String url;
    Double price;
    String createdAt;
    String lastModifiedAt;
    Set<PriceUpdateResource> priceUpdates;

    public static ItemResource of(Item item) {
        return MAPPER.toResource(item);
    }

    @Mapper(uses = PriceUpdateResource.PriceUpdateResourceMapper.class)
    public interface ItemResourceMapper {

        @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
        @Mapping(source = "lastModifiedAt", target = "lastModifiedAt", dateFormat = "yyyy-MM-dd")
        ItemResource toResource(Item item);
    }
}
