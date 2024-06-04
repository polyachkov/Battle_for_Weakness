package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Entity
@Table(name = "user_roles", schema = "public")
public class UserRole {
    @Id
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "role_id")
    private Integer role_id;

}