package com.gymmasterapppartners.app.repository;

import com.gymmasterapppartners.app.domain.Partners;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Partners entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnersRepository extends JpaRepository<Partners, Long> {
	Partners findByUserName(String username);
}
