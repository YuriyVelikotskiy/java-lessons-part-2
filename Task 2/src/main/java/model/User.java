package model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "users", schema = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "created_at")
    private LocalDate created_at;

    public User(){}
    public User(String name, String email, int age){
        this.name = name;
        this.email = email;
        this.age = age;
        this.created_at = LocalDate.now();
    }
}
