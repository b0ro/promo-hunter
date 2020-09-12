package org.boro.promohunter.adapters.api.bundle.dto;

import lombok.Builder;
import lombok.Value;
import org.boro.promohunter.adapters.api.item.dto.ItemResource;
import org.boro.promohunter.bundle.Bundle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Value
@Builder
public class BundleResource {

    private static final BundleResourceMapper MAPPER = Mappers.getMapper(BundleResourceMapper.class);

    Integer id;
    String name;
    String description;
    Double price;
    String createdAt;
    String lastModifiedAt;

    @Builder.Default
    Set<ItemResource> items = new HashSet<>();

    public static BundleResource of(Bundle bundle) {
        return MAPPER.toResource(bundle);
    }

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ItemResource.ItemResourceMapper.class)
    interface BundleResourceMapper {

        @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
        @Mapping(source = "lastModifiedAt", target = "lastModifiedAt", dateFormat = "yyyy-MM-dd")
        BundleResource toResource(Bundle bundle);
    }
}
