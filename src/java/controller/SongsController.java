/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Songs;
import org.hibernate.Session;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.Controller;
import util.HibernateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Avaneesh
 */
@Controller
@RequestMapping("/rest")
public class SongsController{

   @RequestMapping(value ="/songs", method=RequestMethod.GET)
   public @ResponseBody List getAllSongs(){
       try {
           Session session = HibernateUtil.getSessionFactory().openSession();
           session.beginTransaction();
           List songsList = session.createQuery("from Songs").list();    
           return songsList;
           
       } catch (Exception e) {
           e.printStackTrace();
       }      
        return null;
   }
   
    
}
