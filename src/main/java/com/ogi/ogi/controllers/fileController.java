package com.ogi.ogi.controllers;

import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.parser.pdf.PDFParser;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

public class fileController {
    protected HashMap json;
    protected String std_name;
    public String base64;
    public Parser parser;
    public BodyContentHandler handler;
    public Metadata metadata;
    public File file;
    public InputStream stream;
    public ParseContext context;
    public LanguageIdentifier identifier;
    public  PDFParser pdfparser;
    private  Tika tika;

    public HashMap createJson(){
        try{
            this.json =  new HashMap<String, String>();
            this.json.put("size", String.valueOf(this.handler.toString().length()));
            this.json.put("text", this.handler.toString());
            return this.json;
        }catch (Exception e){
            System.out.println(e);
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    public fileController(){
        this.std_name = "test.txt";
        this.parser = new AutoDetectParser();
        this.handler = new BodyContentHandler(-1);
        this.metadata = new Metadata();
        this.context = new ParseContext();
        try{
            this.file = new File("files/" + std_name);
            this.stream = new FileInputStream(file);
            this.tika = new Tika();
            this.parser.parse(stream, handler, metadata, context);
            this.identifier = new LanguageIdentifier(handler.toString());
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(this.file));
            this.base64 = new String(encoded, StandardCharsets.US_ASCII);
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
        }
    }
    public fileController(String name){
        this.std_name = name;
        this.parser = new AutoDetectParser();
        this.handler = new BodyContentHandler(-1);
        this.metadata = new Metadata();
        this.context = new ParseContext();
        try {
            this.file = new File("files/" + std_name);
            this.stream = new FileInputStream(file);
            this.tika = new Tika();
            this.pdfparser = new PDFParser();
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(this.file));
            this.base64 = new String(encoded, StandardCharsets.US_ASCII);
            String buf = tika.detect(file);
            if (buf.equals("application/pdf")) {
                this.pdfparser.parse(stream, handler, metadata, context);
            } else {
                this.parser.parse(stream, handler, metadata, context);
            }
            this.identifier = new LanguageIdentifier(handler.toString());
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
        }
    }
    public String dataExtract(){
        try{
            return tika.parseToString(file);
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    public String getData(){
        try {
            return handler.toString();
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    public String typeof(){
        try{
            return tika.detect(file);
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    private String[] getMetadataNames(){
        try{
            return metadata.names();
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    public void getLang(){
        try{System.out.println(identifier.getLanguage());}
        catch(Exception e){System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());}
    }
    public String getInfo(){
        try{
            return null;
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
            return null;
        }
    }
    @Override
    public String toString() {
        System.out.println("Metadata: " + metadata.toString());
        for(String i: metadata.names()){
            System.out.println("MetadataNames: "+ i);
        }
        System.out.println("Stream:"+ stream.toString());
        System.out.println("Content: "+handler.toString());
        System.out.println("Context: "+ context.toString());
        return null;
    }
}
