package ru.home.ilar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users", schema = "moex")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private long id;

    private String username;

    private String password;
}
