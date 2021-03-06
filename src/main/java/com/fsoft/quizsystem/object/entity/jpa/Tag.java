package com.fsoft.quizsystem.object.entity.jpa;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "tags_id_seq", sequenceName = "tags_id_seq", allocationSize = 1)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag") @ToString.Exclude
    private Set<Question> questions;
}