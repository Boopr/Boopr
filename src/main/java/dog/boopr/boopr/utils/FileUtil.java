package dog.boopr.boopr.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.YearMonth;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
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

    @Autowired
    private ImageRepository dao0;

    @Value("${image-upload-path}")
    private String uploadPath;
  
    @PostConstruct     
    private void initStaticDao () {
        imageDao = this.dao0;
        UPLOAD_PATH = this.uploadPath;
    }

    public static Image uploadImage(MultipartFile uploadedImage, User user, Dog dog) throws Exception{
        
        String mime = uploadedImage.getContentType();
        String ext = FilenameUtils.getExtension(uploadedImage.getOriginalFilename());
        String dir = getYearAndMonthUrlFragment();
        String filename = RandomStringUtils.random(7, true, true) + "." + ext;
        Path path = Paths.get(UPLOAD_PATH + dir);
        
        
        //check if image is actually image;
        if( mime.equals("image/jpeg")||
            mime.equals("image/png")||
            mime.equals("image/webp")||
            mime.equals("image/gif")  ){

                if(Files.notExists(path)){
                    try{
                        Files.createDirectories(path);
                    }catch(IOException e){
                        e.printStackTrace();
                        return null;
                    }
                }
                
                try {
                    uploadedImage.transferTo(Paths.get(UPLOAD_PATH + dir + filename));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
        
                Image image = new Image(
                    UPLOAD_PATH + dir + filename,
                    user,
                    dog
                );
                
                //save and return the image to be attached to the user
                imageDao.save(image);
        
                return image;
        }else{
            System.out.println(mime);
            throw new Exception("Image type not valid: " + mime);
        }

        
    }
    
    private static String getYearAndMonthUrlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append(Year.now().getValue());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        sb.append("/");
    
        return sb.toString();
	}

    public static String randomString() {
        //the string is 7 characters long
        byte[] array = new byte[7]; 
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
    
        return generatedString;
    }

}
