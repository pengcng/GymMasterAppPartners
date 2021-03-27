package com.gymmasterapppartners.app.repository;

import com.gymmasterapppartners.app.domain.Partnersloc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Partnersloc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerslocRepository extends JpaRepository<Partnersloc, Long> {
}
