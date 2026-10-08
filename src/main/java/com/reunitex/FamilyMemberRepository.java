package com.reunitex;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyMemberRepository
        extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findByFamilyId(String familyId);

    List<FamilyMember> findByNameContainingIgnoreCase(String name);
}
