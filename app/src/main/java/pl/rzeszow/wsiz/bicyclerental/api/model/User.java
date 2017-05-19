package pl.rzeszow.wsiz.bicyclerental.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roman Savka on
 * 29-Mar-15.
 */
public class User {

    @SerializedName("UserId")
    private long Id;
    @SerializedName("Login")
    private String login;
    @SerializedName("Password")
    private String password;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("Email")
    private String email;
    @SerializedName("ContactPhone")
    private String contactPhone;

    public User(String login, String password, String lastName, String firstName, String email, String contactPhone) {
        this.login = login;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.contactPhone = contactPhone;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}
