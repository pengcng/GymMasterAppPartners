package com.gymmasterapppartners.app.service.mapper;


import com.gymmasterapppartners.app.domain.*;
import com.gymmasterapppartners.app.service.dto.PartnersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partners} and its DTO {@link PartnersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PartnersMapper extends EntityMapper<PartnersDTO, Partners> {


    @Mapping(target = "partnerslocs", ignore = true)
    @Mapping(target = "removePartnersloc", ignore = true)
    Partners toEntity(PartnersDTO partnersDTO);

    default Partners fromId(Long id) {
        if (id == null) {
            return null;
        }
        Partners partners = new Partners();
        partners.setId(id);
        return partners;
    }
}
