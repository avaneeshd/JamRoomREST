/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.List;
import model.Songs;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author avaneeshdesai
 */
public class LocationRecommendation {
    
    String locationString;
    double latitude;
    double longitude;
    List<Songs> songsList;
    
    public List<Songs> getSongs(String location, double distance, String units){
        this.locationString = location;
        String[] arr = this.locationString.split(",");
        latitude = Double.valueOf(arr[0]);
        longitude = Double.valueOf(arr[1]);
        
        double b1,b2,b3,b4;
        double[][] b = bound(latitude,longitude, distance, units);
        if(b[0][0] > b[2][0]){
             b1 = b[2][0];
             b2= b[0][0];
         }
        else{
             b1 = b[0][0];
             b2= b[2][0];
        }
        
        if(b[1][1] > b[3][1]){
             b3 = b[3][1];
             b4 = b[1][1];
         }
        else{
             b3 = b[1][1];
             b4= b[3][1];
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
           
        String hql = "From Songs S WHERE"
                        +" latitude BETWEEN "+ b1 +" AND "+ b2 +" AND"
                        +" longitude BETWEEN "+ b3 +" AND "+ b4 +"";
        System.out.println("Query"+ hql);
        Query query = session.createQuery(hql);
        List results = query.list();
       return results;
    /*
    $locations = array();
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) {
    $dist = distance($latitude,$lonitude, $row["latitude"],$row["longitude"], $units);
    if ($dist <= $distance) {
         $locations[] = array("name"     => $row["location_name"],
                              "address"  => $row["address"],
                              "city"     => $row["city"],
                              "state"    => $row["state"],
                              "zip_code" => $row["zip_code"],
                              "distance" => round($dist, 2));
        */
    }
    

    
    // calculate destination lat/lon given a starting point, bearing, and distance
    private double[] destination(double lat, double lon, double bearing, double distance, String units) {
        double radius = units.equals("km")? 3963.19 : 6378.137;
        double rLat = Math.toRadians(lat);
        double rLon = Math.toRadians(lon);
        double rBearing = Math.toRadians(bearing);
        double rAngDist = distance / radius;

        double rLatB = Math.asin(Math.sin(rLat) * Math.cos(rAngDist) + 
            Math.cos(rLat) * Math.sin(rAngDist) * Math.cos(rBearing));

        double rLonB = rLon + Math.atan2(Math.sin(rBearing) * Math.sin(rAngDist) * Math.cos(rLat), 
                               Math.cos(rAngDist) - Math.sin(rLat) * Math.sin(rLatB));
      
        System.out.println("rLon + "+ Math.atan2(Math.sin(rBearing) * Math.sin(rAngDist) * Math.cos(rLat), 
Math.cos(rAngDist) - Math.sin(rLat) * Math.sin(rLatB)));
        return new double[]{Math.toDegrees(rLatB), Math.toDegrees(rLonB)};
    }

        
    
    private double[][] bound(double lat, double lon,  double distance, String units) {
    return new double[][]{destination(lat,lon,   0, distance,units), //North
                          destination(lat,lon,  90, distance,units), //East
                          destination(lat,lon, 180, distance,units), //South
                          destination(lat,lon, 270, distance,units)}; //West
    }
    
    
            // calculate distance between two lat/lon coordinates
    private double distance(double latA, double lonA, double latB, double lonB, String units) {
        double radius = units.equals("km") ? 3963.19 : 6378.137;
        double rLatA = Math.toRadians(latA);
        double rLatB = Math.toRadians(latB);
        double rHalfDeltaLat = Math.toRadians((latB - latA) / 2);
        double rHalfDeltaLon = Math.toRadians((lonB - lonA) / 2);

        return 2 * radius * Math.asin(Math.sqrt(Math.pow(Math.sin(rHalfDeltaLat), 2) +
            Math.cos(rLatA) * Math.cos(rLatB) * Math.pow(Math.sin(rHalfDeltaLon), 2)));
    }
}
