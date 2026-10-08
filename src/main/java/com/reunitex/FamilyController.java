package com.reunitex;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/families")
@CrossOrigin
public class FamilyController {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyController(
            FamilyRepository familyRepository,
            FamilyMemberRepository familyMemberRepository) {

        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerFamily(
            @RequestBody FamilyRegistrationRequest request) {

        if (familyRepository.findByMobile(request.mobile).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "success", false,
                            "message", "This mobile number is already registered."
                    ));
        }

        String familyId = generateFamilyId();

        Family family = new Family(
                familyId,
                request.mobile,
                request.identityType,
                request.identityReference
        );

        familyRepository.save(family);

        if (request.members != null) {
            for (MemberRequest member : request.members) {

                FamilyMember familyMember = new FamilyMember(
                        familyId,
                        member.name,
                        member.age,
                        member.gender
                );

                familyMemberRepository.save(familyMember);
            }
        }

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Family Registration Successful",
                        "familyId", familyId
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFamily(
            @RequestParam String query) {

        query = query.trim();

        Optional<Family> familyById =
                familyRepository.findById(query);

        if (familyById.isPresent()) {

            Family family = familyById.get();

            List<FamilyMember> members =
                    familyMemberRepository
                            .findByFamilyId(family.getFamilyId());

            return ResponseEntity.ok(
                    createSearchResponse(family, members)
            );
        }

        List<FamilyMember> matchingMembers =
                familyMemberRepository
                        .findByNameContainingIgnoreCase(query);

        if (!matchingMembers.isEmpty()) {

            FamilyMember matchedMember =
                    matchingMembers.get(0);

            Optional<Family> family =
                    familyRepository.findById(
                            matchedMember.getFamilyId()
                    );

            if (family.isPresent()) {

                List<FamilyMember> members =
                        familyMemberRepository
                                .findByFamilyId(
                                        family.get().getFamilyId()
                                );

                return ResponseEntity.ok(
                        createSearchResponse(
                                family.get(),
                                members
                        )
                );
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "success", false,
                        "message", "No matching family record found."
                ));
    }

    private Map<String, Object> createSearchResponse(
            Family family,
            List<FamilyMember> members) {

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put("success", true);
        response.put("familyId", family.getFamilyId());
        response.put("mobile", family.getMobile());
        response.put("identityType", family.getIdentityType());
        response.put(
                "identityReference",
                family.getIdentityReference()
        );
        response.put("members", members);

        return response;
    }

    private String generateFamilyId() {

        String familyId;

        do {
            familyId =
                    "FAM-" +
                    (10000 +
                    new Random().nextInt(90000));

        } while (familyRepository.existsById(familyId));

        return familyId;
    }

    public static class FamilyRegistrationRequest {

        public String mobile;
        public String identityType;
        public String identityReference;
        public List<MemberRequest> members;
    }

    public static class MemberRequest {

        public String name;
        public int age;
        public String gender;
    }
}