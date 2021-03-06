package com.fsoft.quizsystem.object.entity.jpa;

import com.fsoft.quizsystem.object.constant.SystemRole;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Audited
@Getter
@Setter
@NoArgsConstructor
@ToString
@SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SystemRole name;
}