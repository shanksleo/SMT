package me.jessyan.mvparms.demo.mvp.model.entity;

/**
 * class description
 *
 * @author shankshu
 * Create on 2018-09-21
 */
public class Dad {
    int age ;


    int gender;

    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dad{" +
                "age=" + age +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }
}
