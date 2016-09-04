package in.cakemporos.logistics.cakemporoslogistics.web.webmodels;

import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Baker;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.entities.Rider;
import in.cakemporos.logistics.cakemporoslogistics.web.webmodels.enums.UserType;

/**
 * Created by maitr on 15-Aug-16.
 */
public class UserInfo {

    private String email;

    private String name;

    private Long phone;

    //private UserType userType;

    private Baker baker;

    private Rider rider;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }

    public Baker getBaker() {
        return baker;
    }

    public void setBaker(Baker baker) {
        this.baker = baker;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }
}
