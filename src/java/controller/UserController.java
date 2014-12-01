/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.AuthenticateUser;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import model.Songs;
import model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.HibernateUtil;

/**
 *
 * @author avaneeshdesai
 */

@Controller
@RequestMapping("/rest")

public class UserController {
    SessionIdentifierGenerator IDGenerator = new SessionIdentifierGenerator();
    
   @RequestMapping(value ="/authenticate", method=RequestMethod.POST)
   public @ResponseBody String addUser(@RequestParam("name") String name, @RequestParam("email") String email){
       try {
           
           System.out.println("In authenticate");
           Session session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           String hql = "FROM Users U WHERE U.email = '"+ email+"'";
           Query query = session.createQuery(hql);
           List results = query.list();
           if(results.size()>0){
                Users u = (Users)results.get(0);
                return u.getAccess_token();
           }
           else{
                Users newUser = new Users();
                newUser.setEmail(email);
                newUser.setUsername(name);
                newUser.setAccess_token(IDGenerator.nextSessionId());
                newUser.setLocation("");
                
                session.save(newUser);
                session.getTransaction().commit();
                
                return newUser.getAccess_token();
           }
           
       } catch (Exception e) {
           e.printStackTrace();
       }      
        return null;
   }
   
    public final class SessionIdentifierGenerator {
      private SecureRandom random = new SecureRandom();

      public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
      }
    }
  
}
    
