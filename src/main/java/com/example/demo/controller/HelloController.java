package com.example.demo.controller;


import com.example.demo.entity.Imagetest;
import com.example.demo.entity.Item;
import com.example.demo.python.JythonCaller;
import jep.JepException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import sun.misc.BASE64Encoder;
//
//import javax.imageio.ImageIO;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HelloController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/catalogs/getList")
    public ResponseEntity<List<Item>> index() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1,"1"));
        list.add(new Item(2,"2"));
        list.add(new Item(3,"3"));
//        System.out.println(list);
        return new ResponseEntity<>( list ,HttpStatus.OK);
    }

    @PostMapping("/testString")
    public ResponseEntity<String> addItem(@RequestBody Imagetest s){
        return new ResponseEntity(s.getNamefoto(), HttpStatus.OK);
    }


//    @GetMapping("/list")
//    public ResponseEntity<List<String>> getListCustomer() {
//        List<String> A = null;
//        JythonCaller app = new JythonCaller();
//        try {
//            A = app.searchImage("323");
//        } catch (JepException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            List<String> itemList = A;
//            if (itemList.size() == 0) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(itemList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


//    @GetMapping("/test")
//    public ResponseEntity<List<String>> getList(@RequestParam("chuoi") String chuoi) {
//
//
//        List<String> itemList = new ArrayList<String>();
//        itemList.add("sadsd");
//        itemList.add("asdasd");
//        try {
//
//            if (itemList.size() == 0) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(itemList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }





//    @PostMapping("/test")
//    public ResponseEntity<List<Imagetest>> post3Layer(@RequestBody Imagetest imagetest) {
//        try {
//
//
//            List<String> A = new ArrayList<>();
//            JythonCaller app = new JythonCaller();
//            try {
//                A = app.searchImage( imagetest.getNamefoto());
//                System.out.println(  A);
//            } catch (JepException e) {
//                e.printStackTrace();
//            }
//
//
//            List<Imagetest> result = new ArrayList<>();
//            List<String> itemList = A;
//            if (itemList.size() == 0) {
//                System.out.println("Khong co anh nao");
//                return new ResponseEntity<>(  HttpStatus.NO_CONTENT);
//            }
//            else{
////                String pathToImage = "C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\resources\\static\\FootwearImg\\";
//                String pathToImage = "src/main/resources/static/FootwearImg/";
//                for ( String nameImage : itemList
//                     ) {
////                    System.out.println(nameImage);
//                    byte[] bytes = Files.readAllBytes(Paths.get(pathToImage + nameImage));
//                    String base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);
////                    System.out.println(base64EncodedImageBytes);
////                    System.out.println(base64EncodedImageBytes);
//                    Imagetest tmp = new Imagetest(nameImage, base64EncodedImageBytes, "png");
//                    result.add(tmp);
//                }
//
//            }
////            System.out.println(result);
//            return new ResponseEntity<>( result ,HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }



    @PostMapping("/postResNetTest152")
    public ResponseEntity<List<Imagetest>> postResNetTest152(@RequestBody Imagetest imagetest) {
        try {
            System.out.println(imagetest.getImage());
            List<String> A = new ArrayList<>();
            JythonCaller app = new JythonCaller();
            try {
                A = app.searchImageNesNet152( imagetest.getNamefoto());
                System.out.println(  A );
            } catch (JepException e) {
                e.printStackTrace();
            }
            List<Imagetest> result = new ArrayList<>();
            List<String> itemList = A;
            if (itemList.size() == 0) {
                System.out.println("Khong co anh nao");
                return new ResponseEntity<>(  HttpStatus.NO_CONTENT);
            }
            else{
                String pathToImage = "src/main/resources/static/FootwearImg/";
                for ( String nameImage : itemList
                     ) {
                    byte[] bytes = Files.readAllBytes(Paths.get(pathToImage + nameImage));
                    String base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);
                    Imagetest tmp = new Imagetest(nameImage, base64EncodedImageBytes, "png");
                    result.add(tmp);
                }
            }
            return new ResponseEntity<>( result ,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



        @PostMapping("/test1")
    public ResponseEntity<List<Imagetest>> postResNet50(@RequestBody Imagetest imagetest) {
        try {

            List<String> A = new ArrayList<>();
            List<String> itemList = new ArrayList<>();
            System.out.println(imagetest.getImage());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Imagetest> requestEntity = new HttpEntity<>(imagetest, headers);
            System.out.println(requestEntity);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<String>> response = restTemplate.exchange("http://127.0.0.1:5000",HttpMethod.POST,  requestEntity, new ParameterizedTypeReference<List<String>>() {} );


            itemList = response.getBody();
            System.out.println(  itemList );


            List<Imagetest> result = new ArrayList<>();
//            List<String> itemList = A;
            if (itemList.size() == 0) {
                System.out.println("Khong co anh nao");
                return new ResponseEntity<>(  HttpStatus.NO_CONTENT);
            }
            else{
                String pathToImage = "src/main/resources/static/FootwearImg/";
                for ( String nameImage : itemList
                ) {
//                    byte[] bytes = Files.readAllBytes(Paths.get(pathToImage + nameImage));
//                    String base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);
                    Imagetest tmp = new Imagetest(nameImage, nameImage, "png");
                    result.add(tmp);
                }
            }
            return new ResponseEntity<>( result ,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
