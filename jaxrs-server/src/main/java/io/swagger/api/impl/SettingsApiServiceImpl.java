package io.swagger.api.impl;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import io.swagger.api.*;
import io.swagger.model.*;

import com.sun.jersey.multipart.FormDataParam;

import io.swagger.model.Setting;
import java.util.Date;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.bson.Document;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-04-01T17:42:32.367Z")
public class SettingsApiServiceImpl extends SettingsApiService {
    
    @Override
    public Response settingsGet(Integer size, Date date, String algorithm, String barcode, SecurityContext securityContext)
    throws NotFoundException {
        
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("barcodes");
        
        ArrayList<String> settings = new ArrayList();
        Document query = new Document();
        
        
        FindIterable<Document> search = db.getCollection("settings").find(query);
        
        if(search == null){
            return null;
        }
        for(Document current : search){
            String tmp = "{id:" + "2";
            tmp += ", algorithm:" + current.getString("algorithm");
            tmp += ", created_at:" + current.getString("created_at");
            settings.add(tmp);
        }
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, settings.get(0))).build();
    }
    
    /*
    *
    *   Stores a new setting in the database
    *
    */
    @Override
    public Response settingsPost(Setting setting, SecurityContext securityContext)
    throws NotFoundException {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        mongoClient.setWriteConcern(WriteConcern.UNACKNOWLEDGED);
        
        MongoDatabase db = mongoClient.getDatabase("barcodes");
        MongoCollection coll = db.getCollection("settings");
        
        Document new_setting = new Document();
        Map map = setting.getBarcode().toMap();
        Document barcode_params = new Document();
        for (Map.Entry entry : ((Set<Map.Entry>) map.entrySet())) {
            barcode_params.append(entry.getKey().toString(), entry.getValue());
        }
        
        new_setting.append("algorithm", setting.getAlgorithm().toString());
        new_setting.append("barcode", barcode_params);
        
        DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        new_setting.append("created_at", date_format.format(date));
        new_setting.append("active", true);
        
        coll.insertOne(new_setting);
        
        ResponseBuilder resp = Response.ok().status(201);
        
        return resp.build();
        //return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "DONE")).build();
    }
    
    @Override
    public Response settingsSettingIdDelete(Long settingId, SecurityContext securityContext)
    throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    
    @Override
    public Response settingsSettingIdGet(Long settingId, SecurityContext securityContext)
    throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    
    @Override
    public Response settingsSettingIdPut(Long settingId, Setting setting, SecurityContext securityContext)
    throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    
}