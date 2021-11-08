package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.service.GeneralResponse;
import za.co.technetic.ss.logic.flow.ModifyMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.translator.MemberTranslator;

@Transactional
@Component
public class ModifyMemberImageFlowImpl implements ModifyMemberImageFlow {

    private final ImageTranslator imageTranslator;
    private final MemberTranslator memberTranslator;

    @Autowired
    public ModifyMemberImageFlowImpl(ImageTranslator imageTranslator, MemberTranslator memberTranslator) {
        this.imageTranslator = imageTranslator;
        this.memberTranslator = memberTranslator;
    }

    @Override
    public GeneralResponse<String> deletePhoto(String email, String keyName) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = String.format("Successfully deleted photo '%s'", keyName);
        Member member = memberTranslator.findMemberByEmail(email);

        if (null != member) {
            if (!imageTranslator.deletePhoto(member.getId(), keyName)) {
                httpStatus = HttpStatus.NOT_FOUND;
                message = String.format("Could not delete photo '%s'. See the logs.", keyName);
            }
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;
            message = String.format("Member with email '%s' could not be found", email);
        }

        return new GeneralResponse<>(httpStatus.value(), message, null);
    }
}
