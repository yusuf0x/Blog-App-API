package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
//@Setter
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
