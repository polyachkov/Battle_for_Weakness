package ru.nsu.fit.battle_fw.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles", schema = "public")
public class Role {
    @Id
    @Column(name = "role_id")
    @SequenceGenerator(name = "roleIdSeq", sequenceName = "role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleIdSeq")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "name")
    private ERole name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Role() {}

    public Role(ERole name) {
        this.name = name;
    }
}
