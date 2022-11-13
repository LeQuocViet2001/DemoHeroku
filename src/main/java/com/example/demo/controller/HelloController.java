package com.example.demo.controller;


import com.example.demo.entity.Imagetest;
import com.example.demo.entity.Item;
import com.example.demo.python.JythonCaller;
import jep.JepException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.misc.BASE64Encoder;
//
//import javax.imageio.ImageIO;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HelloController {


    @GetMapping("/catalogs/getList")
    public ResponseEntity<List<Item>> index() {

        List<Item> list = new ArrayList<>();
        list.add(new Item(1,"1"));
        list.add(new Item(2,"2"));
        list.add(new Item(3,"3"));
//        System.out.println(list);
        return new ResponseEntity<>( list ,HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<List<String>> getListCustomer() {

        List<String> A = null;
        JythonCaller app = new JythonCaller();
        try {
            A = app.searchImage("323");
        } catch (JepException e) {
            e.printStackTrace();
        }

        try {
            List<String> itemList = A;
            if (itemList.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/test")
    public ResponseEntity<List<String>> getList(@RequestParam("chuoi") String chuoi) {


        List<String> itemList = new ArrayList<String>();
        itemList.add("sadsd");
        itemList.add("asdasd");
        try {

            if (itemList.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





    @PostMapping("/test")
    public ResponseEntity<List<Imagetest>> post3Layer(@RequestBody Imagetest imagetest) {
        try {


            List<String> A = new ArrayList<>();
            JythonCaller app = new JythonCaller();
            try {
                A = app.searchImage( imagetest.getNamefoto());
                System.out.println(  A);
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
//                String pathToImage = "C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\resources\\static\\FootwearImg\\";
                String pathToImage = "src/main/resources/static/FootwearImg/";
                for ( String nameImage : itemList
                     ) {
//                    System.out.println(nameImage);
                    byte[] bytes = Files.readAllBytes(Paths.get(pathToImage + nameImage));
                    String base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);
//                    System.out.println(base64EncodedImageBytes);
//                    System.out.println(base64EncodedImageBytes);
                    Imagetest tmp = new Imagetest(nameImage, base64EncodedImageBytes, "png");
                    result.add(tmp);
                }

            }
//            System.out.println(result);
            return new ResponseEntity<>( result ,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/test1")
    public ResponseEntity<List<Imagetest>> postResNet50(@RequestBody Imagetest imagetest) {
        try {


            List<String> A = new ArrayList<>();
            JythonCaller app = new JythonCaller();
            try {
                A = app.searchImageNesNet50( imagetest.getNamefoto());
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
//                String pathToImage = "C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\resources\\static\\FootwearImg\\";
                String pathToImage = "src/main/resources/static/FootwearImg/";
                for ( String nameImage : itemList
                     ) {
//                    System.out.println(nameImage);
                    byte[] bytes = Files.readAllBytes(Paths.get(pathToImage + nameImage));
                    String base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);

                    Imagetest tmp = new Imagetest(nameImage, base64EncodedImageBytes, "png");
                    result.add(tmp);
                }

            }
//            System.out.println(result);
            return new ResponseEntity<>( result ,HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
