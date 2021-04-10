package com.gymmasterapppartners.app.service.mapper;


import com.gymmasterapppartners.app.domain.*;
import com.gymmasterapppartners.app.service.dto.PartnerslocDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partnersloc} and its DTO {@link PartnerslocDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartnersMapper.class})
public interface PartnerslocMapper extends EntityMapper<PartnerslocDTO, Partnersloc> {

    @Mapping(source = "partners.id", target = "partnersId")
    @Mapping(source = "partners.companyName", target = "partnersCompanyName")
    @Mapping(source = "partners.userName", target = "partnersUserName")
    PartnerslocDTO toDto(Partnersloc partnersloc);

    @Mapping(source = "partnersId", target = "partners")
    Partnersloc toEntity(PartnerslocDTO partnerslocDTO);

    default Partnersloc fromId(Long id) {
        if (id == null) {
            return null;
        }
        Partnersloc partnersloc = new Partnersloc();
        partnersloc.setId(id);
        return partnersloc;
    }
}
