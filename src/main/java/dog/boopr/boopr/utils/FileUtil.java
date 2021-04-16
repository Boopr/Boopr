package dog.boopr.boopr.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.YearMonth;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.ImageRepository;


@Component
public class FileUtil {

    private static ImageRepository imageDao;
    private static String UPLOAD_PATH;

    @Value("${file-upload-path}")
    private String uploadPath;
    
    @Autowired
    private ImageRepository dao0;

    @PostConstruct     
    private void initStaticDao () {
        imageDao = this.dao0;
        UPLOAD_PATH = this.uploadPath;
    }

    public static Image uploadImage(MultipartFile uploadedImage, User user, Dog dog){

        //increments the file name by one
        
        String extension = FilenameUtils.getExtension(uploadedImage.getOriginalFilename());

        String filename = imageDao.findAll().size() + "." + extension;
        Path path = Paths.get(UPLOAD_PATH);

        if(Files.notExists(path)){
            try{
                Files.createDirectories(path);
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        
        try {
            uploadedImage.transferTo(Paths.get(UPLOAD_PATH + filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Image image = new Image(
            user,
            dog,
            UPLOAD_PATH + filename
        );
        
        imageDao.save(image);

        return image;
    }

}
