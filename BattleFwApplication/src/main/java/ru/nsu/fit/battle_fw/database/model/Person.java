package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persona", schema = "public")
@Deprecated
public class Person {
    @Id
    @Column(name = "id_profile")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "persona_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
