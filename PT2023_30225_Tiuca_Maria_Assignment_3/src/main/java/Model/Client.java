package Model;
/**
 * The Client class represents a client entity in the system.
 * It contains information such as the client's name, ID, address, and age.
 */
public class Client {
    private String name;
    private int id;
    private String address;
    private int age;
    /**
     * Constructs a new instance of the Client class with default values.
     */
    public Client(){
    }
    /**
     * Constructs a new instance of the Client class with the specified values.
     *
     * @param name    the client's name
     * @param id      the client's ID
     * @param address the client's address
     * @param age     the client's age
     */
    public Client(String name,int id,String address,int age){
        this.name=name;
        this.id=id;
        this.address=address;
        this.age=age;
    }
    /**
     * Retrieves the client's name.
     *
     * @return the client's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the client's name.
     *
     * @param name the client's name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Retrieves the client's ID.
     *
     * @return the client's ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the client's ID.
     *
     * @param id the client's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Retrieves the client's address.
     *
     * @return the client's address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the client's address.
     *
     * @param address the client's address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Retrieves the client's age.
     *
     * @return the client's age
     */
    public int getAge() {
        return age;
    }
    /**
     * Sets the client's age.
     *
     * @param age the client's age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * Returns a string representation of the Client object.
     *
     * @param c the Client object
     * @return a string representation of the Client object
     */
    public String toString(Client c){
        return "id: "+c.getId()+" name: "+c.getName()+" age: "+c.getAge()+" adresa: "+c.getName();
    }
}