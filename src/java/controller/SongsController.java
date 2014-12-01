/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.AuthenticateUser;
import common.LocationRecommendation;
import java.util.List;
import model.Songs;
import model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Avaneesh
 */
@Controller
@RequestMapping("/rest")
public class SongsController{
   
   
   @RequestMapping(value ="/songs", method=RequestMethod.GET)
   public @ResponseBody List getAllSongs(@RequestHeader("x-auth-token") String Token){
       try {
           
           boolean isAuthenticated = AuthenticateUser.isAuthenticated(Token);
           if(isAuthenticated){
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                List songsList = session.createQuery("from Songs").list();  

                return songsList;
           }
           
       } catch (Exception e) {
           e.printStackTrace();
       }      
        return null;
   }
   
   @RequestMapping (method = RequestMethod.GET, value = "/songs/{songId}")
   public @ResponseBody Songs getSongById(@RequestHeader("x-auth-token") String Token, @PathVariable int songId){
       try{
            boolean isAuthenticated = AuthenticateUser.isAuthenticated(Token);
            if(isAuthenticated){     
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Songs s = (Songs)session.get(Songs.class, songId);
                return s;
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }
   
   @RequestMapping (method = RequestMethod.GET, value = "/songs/location/{locationString}")
   public @ResponseBody List getSongByLocation(@RequestHeader("x-auth-token") String Token, @PathVariable String locationString){
       try{
            boolean isAuthenticated = AuthenticateUser.isAuthenticated(Token);
            if(isAuthenticated){     
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                LocationRecommendation locationReco = new LocationRecommendation();
                return locationReco.getSongs(locationString, 1000, "mi");
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }
   
   @RequestMapping (method = RequestMethod.GET, value = "/songs/manage/")
   public @ResponseBody List getSongByUser(@RequestHeader("x-auth-token") String Token){
       try{ System.out.println("USREID");
            boolean isAuthenticated = AuthenticateUser.isAuthenticated(Token);
            System.out.println(isAuthenticated);
            if(isAuthenticated){     
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                 String hql = "FROM Users U WHERE U.access_token = '"+ Token+ "'";
                 Query query = session.createQuery(hql);
                 List users = query.list();
                 if(users.size()>0){
                    Users u = (Users)users.get(0);
                    int userID = u.getUserId();
                     System.out.println("USREID "+u.getUserId());
                    String managehql = "FROM Songs S WHERE S.uploadedBy = "+ userID;
                    Query manageQuery = session.createQuery(managehql);
                    List results = manageQuery.list();
                     System.out.println("RESULTS "+ results.size());
                    return results;
                }
              
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }
}
