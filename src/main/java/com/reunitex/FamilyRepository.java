package com.reunitex;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository
        extends JpaRepository<Family, String> {

    Optional<Family> findByMobile(String mobile);
}