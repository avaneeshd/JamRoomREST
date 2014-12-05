/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.AuthenticateUser;
import common.LocationRecommendation;
import java.util.Arrays;
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
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                String[] fav = getFavList(Token);
                List songsList = session.createQuery("from Songs").list();  
                for(int i =0 ;i< songsList.size(); i++){
                    if(Arrays.asList(fav).contains(((Songs)songsList.get(i)).getSongId().toString())){
                        Songs s = (Songs)songsList.get(i);
                        s.setIsFav(true);
                        songsList.set(i, s);
                    }
                }
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
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Songs s = (Songs)session.get(Songs.class, songId);
                String[] fav = getFavList(Token);
                if(Arrays.asList(fav).contains((s.getSongId().toString()))){
                        s.setIsFav(true);
                }
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
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                String[] fav = getFavList(Token);
                LocationRecommendation locationReco = new LocationRecommendation();
                List songsList = locationReco.getSongs(locationString, 1000, "mi");
                for(int i =0 ;i< songsList.size(); i++){
                    if(Arrays.asList(fav).contains(((Songs)songsList.get(i)).getSongId().toString())){
                        Songs s = (Songs)songsList.get(i);
                        s.setIsFav(true);
                        songsList.set(i, s);
                    }
                }
                return songsList;
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
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
   
    @RequestMapping (method = RequestMethod.GET, value = "/songs/genre/{genreName}")
   public @ResponseBody List getSongsByGenre(@RequestHeader("x-auth-token") String Token, @PathVariable String genreName){
       try{
            boolean isAuthenticated = AuthenticateUser.isAuthenticated(Token);
            if(isAuthenticated){     
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                String[] fav = getFavList(Token);
                List songsList = session.createQuery("from Songs S Where S.genere= '"+ genreName +"'").list();
                for(int i =0 ;i< songsList.size(); i++){
                    if(Arrays.asList(fav).contains(((Songs)songsList.get(i)).getSongId().toString())){
                        Songs s = (Songs)songsList.get(i);
                        s.setIsFav(true);
                        songsList.set(i, s);
                    }
                }
                return songsList;
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return null;
   }
   
   private String[] getFavList(String token){
       String favList[] = new String[0];
       Session session = HibernateUtil.getSessionFactory().openSession();
       session.beginTransaction();
       String hql = "FROM Users U WHERE U.access_token = '"+ token+ "'";
       Query query = session.createQuery(hql);
       List users = query.list();
       if(users.size()>0){
         Users u = (Users)users.get(0);
         String favString = u.getFavList();
         favList = favString.split(",");
       }
       return favList;
   }
}
