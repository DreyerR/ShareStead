package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.FetchMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Transactional
@Component
public class FetchMemberImageFlowImpl implements FetchMemberImageFlow {

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;

    @Autowired
    public FetchMemberImageFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
    }

    @Override
    public ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException {
        return imageTranslator.downloadImage(memberId, keyName);
    }

    @Override
    public GeneralResponse<List<PhotoDto>> fetchMemberPhotos(String email) {
        Member member = memberTranslator.findMemberByEmail(email);

        GeneralResponse<List<PhotoDto>> generalResponse = new GeneralResponse<>(
            HttpStatus.OK.value(),
            "Successfully fetched all photos"
        );

        if (null != member) {
            generalResponse.setPayload(imageTranslator.fetchMemberPhotos(member));
        }
        else {
            generalResponse.setCode(HttpStatus.NOT_FOUND.value());
            generalResponse.setMessage(String.format("Member with email '%s' not found", email));
        }

        return generalResponse;
    }
}
