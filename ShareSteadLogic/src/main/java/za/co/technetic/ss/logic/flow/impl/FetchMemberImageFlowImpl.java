package za.co.technetic.ss.logic.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.technetic.ss.logic.flow.FetchMemberImageFlow;
import za.co.technetic.ss.translator.ImageTranslator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Transactional
@Component
public class FetchMemberImageFlowImpl implements FetchMemberImageFlow {

    private final ImageTranslator imageTranslator;

    @Autowired
    public FetchMemberImageFlowImpl(ImageTranslator imageTranslator) {
        this.imageTranslator = imageTranslator;
    }

    @Override
    public ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException {
        return imageTranslator.downloadImage(memberId, keyName);
    }
}
