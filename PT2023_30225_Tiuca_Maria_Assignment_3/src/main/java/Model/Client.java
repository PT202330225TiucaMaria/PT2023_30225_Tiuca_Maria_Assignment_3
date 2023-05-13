package Model;
public class Client {
    private String name;
    private int id;
    private String address;
    private int age;
    public Client(){
    }
    public Client(String name,int id,String address,int age){
        this.name=name;
        this.id=id;
        this.address=address;
        this.age=age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String toString(Client c){
        return "id: "+c.getId()+" name: "+c.getName()+" age: "+c.getAge()+" adresa: "+c.getName();
    }
}