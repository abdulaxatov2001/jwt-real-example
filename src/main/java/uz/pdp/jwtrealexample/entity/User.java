package uz.pdp.jwtrealexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //primary key

    @Column(nullable = false,length = 50)
    private String firstName;//ismi


    @Column(nullable = false,length = 50)
    private String lastName;//familiya


    @Column(unique = true,nullable = false)
    private String email;//example@gmail.com

    @Column(nullable = false)
    private String password;//userning paroli

    @Column(nullable = false,updatable = false)

    @CreationTimestamp
    private Timestamp createdAt;//qacon registratsiya qilgan


    @UpdateTimestamp
    private Timestamp updatedAt;//qacon edit qilingan oxirgi marta

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled=true;


    @ManyToMany
    private Set<Role> roles;

//Quyidagilar UserDetails ning method lari
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
