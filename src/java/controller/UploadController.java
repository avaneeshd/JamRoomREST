/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author avaneeshdesai
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import model.Songs;
import model.Users;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import util.HibernateUtil;

@Controller
@RequestMapping("/rest")
public class UploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file, @RequestParam("title") String title,
            @RequestParam("artist") String artist, @RequestParam("genre") String genre,
            @RequestParam("location") String location, @RequestParam String uploadedBy){
        if (!file.isEmpty()) {
            try {
                System.out.println("new Connection");
                String FILE_PATH = "/Users/avaneeshdesai/Documents/";
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(FILE_PATH + name)));
                stream.write(bytes);
                stream.close();
                // Enter in database
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                String hql = "FROM Users U WHERE U.email = '"+ uploadedBy+"'";
                Query query = session.createQuery(hql);
                List results = query.list();
                int upBy = ((Users)results.get(0)).getUserId();
                
                String[] arr = location.split(",");
                double latitude = Double.valueOf(arr[0]);
                double longitude = Double.valueOf(arr[1]);
                //Add new Employee object
                Songs song = new Songs();
                song.setName(title);
                song.setArtist(artist);
                song.setLatitude(latitude);
                song.setLongitude(longitude);
                song.setUploadedOn(new Date());
                song.setGenere(genre);
                song.setUploadedBy(upBy);
                song.setPath("/resources/" + name);
                //Save the song in database
                session.save(song);

                //Commit the transaction
                session.getTransaction().commit();
                
        
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

}

