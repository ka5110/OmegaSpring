package com.project.omega.bean.dao;

import com.project.omega.enums.RoleEnum;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DynamicUpdate(true)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;


    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id", referencedColumnName = "id")
    private Industry industry;

    public User() {

    }

    public User(Long id, @NotBlank String email, @NotBlank String password, RoleEnum role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder {

        private Long id;
        private String email;
        private String password;
        private RoleEnum role;
        private Industry industry;

        public UserBuilder() {
        }

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(RoleEnum role) {
            this.role = role;
            return this;
        }

        public UserBuilder setIndustry(Industry industry) {
            this.industry = industry;
            return this;
        }

        public User build() {
            return new User(id, email, password, role);
        }
    }
}
