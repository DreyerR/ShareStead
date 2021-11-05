package za.co.technetic.ss.translator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.repo.persistence.PhotoRepository;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.webservice.filestore.FileStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class ImageTranslatorImpl implements ImageTranslator {

    private final FileStore fileStore;
    private final PhotoRepository photoRepository;

    @Autowired
    public ImageTranslatorImpl(FileStore fileStore, PhotoRepository photoRepository) {
        this.fileStore = fileStore;
        this.photoRepository = photoRepository;
    }

    @Override
    public void uploadImageToS3(String path, String fileName, Optional<Map<String, Object>> metadata,
                                MultipartFile file) {
        try {
            fileStore.uploadImage(path, fileName, metadata, file.getInputStream());
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadImage(Long memberId, String keyName) throws IOException {
        return fileStore.downloadImage(memberId, keyName);
    }

    @Override
    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }
}
