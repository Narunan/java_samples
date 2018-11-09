package ru.mail.polis.lambda.messaging;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
public class User {
    private int id;
    private String name;
    private String city;
    private int age;
    private Sex sex;

    public User(int id, String name, String city, int age, Sex sex) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.age = age;
        this.sex = sex;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getCity() { return city; }

    public int getAge() { return age; }

    public Sex getSex() { return sex; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
