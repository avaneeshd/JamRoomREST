package common;


import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author avaneeshdesai
 */
public class AuthenticateUser {
 
  public static boolean isAuthenticated(String Token){
           Session session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           String hql = "FROM Users U WHERE U.access_token = '"+ Token+"'";
           Query query = session.createQuery(hql);
           List results = query.list();
           if(results.size()>0){
               return true;
           }
           else {
               return false;
           }
  }
}
