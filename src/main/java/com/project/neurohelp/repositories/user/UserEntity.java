package com.project.neurohelp.repositories.user;

import com.project.neurohelp.platform.constants.Roles;
import com.project.neurohelp.platform.constants.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles roles = Roles.USER;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String citizenShip;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "password_reset_otp_hash")
    private String passwordResetOtpHash;

    @Column(name = "password_reset_otp_expires_at")
    private Instant passwordResetOtpExpiresAt;
    public UserEntity() {
    }

    public UserEntity(String name, String email, String password, Roles roles, String address, String citizenShip, Status status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles == null ? Roles.USER : roles;
        this.status = status == null ? Status.INACTIVE : status;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCitizenShip() {
        return citizenShip;
    }
    public void setCitizenShip(String citizenShip) {
        this.citizenShip = citizenShip;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPasswordResetOtpHash() {
        return passwordResetOtpHash;
    }

    public void setPasswordResetOtpHash(String passwordResetOtpHash) {
        this.passwordResetOtpHash = passwordResetOtpHash;
    }

    public Instant getPasswordResetOtpExpiresAt() {
        return passwordResetOtpExpiresAt;
    }

    public void setPasswordResetOtpExpiresAt(Instant passwordResetOtpExpiresAt) {
        this.passwordResetOtpExpiresAt = passwordResetOtpExpiresAt;
    }
}
