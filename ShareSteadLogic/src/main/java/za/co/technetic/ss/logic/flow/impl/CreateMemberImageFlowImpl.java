package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.logic.flow.CreateMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;

import java.util.List;

@Transactional
@Component
public class CreateMemberImageFlowImpl implements CreateMemberImageFlow {

    private final ImageTranslator imageTranslator;

    @Autowired
    public CreateMemberImageFlowImpl(ImageTranslator imageTranslator) {
        this.imageTranslator = imageTranslator;
    }

    @Override
    public void uploadImageToS3(Long memberId, MultipartFile file) {
        imageTranslator.uploadImageToS3(memberId, file);
    }
}
