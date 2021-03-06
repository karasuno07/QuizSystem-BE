package com.fsoft.quizsystem.object.entity.jpa;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?")
@Where(clause = "active=true")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "varchar(80)", nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String fullName;

    @Column(columnDefinition = "varchar(255)", unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(12)")
    private String phoneNumber;

    @Column(columnDefinition = "text")
    private String image;

    @ManyToOne @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "instructor") @ToString.Exclude
    private Set<Quiz> quizzes;

    @ManyToMany
    @JoinTable(name = "user_favorite_categories",
            joinColumns = @JoinColumn(name = "user_id", columnDefinition = "bigint"),
            inverseJoinColumns = @JoinColumn(name = "category_id", columnDefinition = "bigint"))
    @ToString.Exclude
    private Set<Category> favoriteCategories;

    @Column(name = "accept_email")
    private Boolean notification;

    private Boolean active = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                ObjectUtils.isEmpty(role)
                ? new SimpleGrantedAuthority("ROLE_USER")
                : new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}