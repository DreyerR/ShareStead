package za.co.technetic.ss.translator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import za.co.technetic.ss.domain.dto.PhotoDto;
import za.co.technetic.ss.domain.persistence.Member;
import za.co.technetic.ss.domain.persistence.MemberPhoto;
import za.co.technetic.ss.domain.persistence.Photo;
import za.co.technetic.ss.repo.persistence.MemberPhotoRepository;
import za.co.technetic.ss.repo.persistence.MemberRepository;
import za.co.technetic.ss.repo.persistence.PhotoRepository;
import za.co.technetic.ss.translator.ImageTranslator;
import za.co.technetic.ss.webservice.filestore.FileStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ImageTranslatorImpl implements ImageTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageTranslatorImpl.class);

    private final FileStore fileStore;
    private final PhotoRepository photoRepository;
    private final MemberPhotoRepository memberPhotoRepository;

    @Autowired
    public ImageTranslatorImpl(FileStore fileStore, PhotoRepository photoRepository,
                               MemberPhotoRepository memberPhotoRepository) {
        this.fileStore = fileStore;
        this.photoRepository = photoRepository;
        this.memberPhotoRepository = memberPhotoRepository;
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

    @Override
    public List<PhotoDto> fetchMemberPhotos(Member member) {
        try {
            if (null != member) {
                List<MemberPhoto> memberPhotoList = memberPhotoRepository.findAllByMember_Id(member.getId());
                List<PhotoDto> photoDtoList = new ArrayList<>();

                memberPhotoList.forEach(memberPhoto -> {
                    photoDtoList.add(new PhotoDto(memberPhoto.getPhoto()));
                });

                return photoDtoList;
            }

            return null;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred with message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePhoto(Long memberId, String keyName) {
        if (photoRepository.existsPhotoByUrl(keyName)) {
            photoRepository.deleteByUrl(keyName);
            LOGGER.info("Deleted photo with name '{}' from the database", keyName);
        }
        else {
            LOGGER.warn("Photo with URL '{}' could not be found in the database", keyName);
            return false;
        }

        return fileStore.deletePhoto(memberId, keyName);
    }

    @Override
    public boolean shareImage(Long ownerId, Member sharedWith, Photo photo, boolean isModifiable) {
        try {
            MemberPhoto memberPhoto = new MemberPhoto(
                    null,
                    sharedWith,
                    photo,
                    ownerId,
                    isModifiable
            );

            memberPhotoRepository.save(memberPhoto);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Photo findPhotoByUrl(String fileName) {
        return photoRepository.findPhotoByUrl(fileName);
    }
}
