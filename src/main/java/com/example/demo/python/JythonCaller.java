package com.example.demo.python;


import jep.*;
import jep.python.PyObject;

import java.util.Arrays;
import java.util.List;


public class JythonCaller {


    public  List<String> searchImage( String stringBase64) throws JepException {

        try(Interpreter interpreter = new SharedInterpreter();) {
//            interpreter.runScript("C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\java\\com\\example\\demo\\python\\test.py");
            interpreter.runScript("src\\main\\java\\com\\example\\demo\\python\\test.py");

            interpreter.exec("a = searchImage( b'" +  stringBase64 +"')" );
            List<String> output = (List<String>) interpreter.getValue("a");
            return output;
        } catch ( JepException e) {
            return null;
        }

    }

    public  List<String> searchImageNesNet50( String stringBase64) throws JepException {
        try(Interpreter interpreter = new SharedInterpreter();) {
//            interpreter.runScript("C:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\testByBase64Resnet152.py");
            interpreter.runScript("C:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\testByBase64Resnet152.py");
            interpreter.exec("a = extract_feature( b'" +  stringBase64 +"')" );
            List<String> output = (List<String>) interpreter.getValue("a");
            return output;
        } catch ( JepException e) {
            return null;
        }

    }



}