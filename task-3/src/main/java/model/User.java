package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "created_at")
    private LocalDate created_at;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.created_at = LocalDate.now();
    }

    public void print() {
        System.out.printf("ID: %d Имя: %s email: %s Лет: %d создан: %s\n", this.getId(), this.getName(),  this.getEmail(), this.getAge(), this.getCreated_at());
    }
}
