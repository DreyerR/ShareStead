package za.co.technetic.ss.translator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.repo.persistence.MemberPhotoRepository;
import za.co.technetic.ss.translator.MemberPhotoTranslator;

@Component
public class MemberPhotoImpl implements MemberPhotoTranslator {

    private final MemberPhotoRepository memberPhotoRepository;

    @Autowired
    public MemberPhotoImpl(MemberPhotoRepository memberPhotoRepository) {
        this.memberPhotoRepository = memberPhotoRepository;
    }

    @Override
    public MemberPhoto saveMemberPhoto(MemberPhoto memberPhoto) {
        return memberPhotoRepository.save(memberPhoto);
    }
}
