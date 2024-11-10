package RestaurantSys;

public class Customer {

    private String name;
    private String contactNumber;
    private String gender;
    public Customer() {
    }

    public Customer(String name, String contactNumber, String gender) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }
    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name;
    }
}
