package org.tensorflow.lite.examples.classification;




public class UserProfile {

    public String fullname, fullage, fullemail;

    public UserProfile(){

    }
    public UserProfile(String fullname,String fullage,String fullemail){
        this.fullname= fullname;
        this.fullage= fullage;
        this.fullemail= fullemail;
    }
}