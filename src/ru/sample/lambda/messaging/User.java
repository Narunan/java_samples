package ru.sample.lambda.messaging;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/2017.
 */
@SuppressWarnings("unused")
public class User {
    private final int id;
    private final String name;
    private final String city;
    private final int age;
    private final Sex sex;

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
