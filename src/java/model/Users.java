package model;
// Generated 07-Nov-2014 18:54:07 by Hibernate Tools 3.2.1.GA



/**
 * Users generated by hbm2java
 */
public class Users  implements java.io.Serializable {


     private Integer userId;
     private String username;
     private String access_token;
     private String location;
     private String email;
     

    public Users() {
    }

    public Users(String username, String access_token, String location, String email) {
       this.username = username;
       this.access_token = access_token;
       this.location = location;
       this.email = email;
    }
   
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAccess_token() {
        return this.access_token;
    }
    
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }




}


