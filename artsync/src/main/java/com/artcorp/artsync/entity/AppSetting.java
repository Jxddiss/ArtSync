package com.artcorp.artsync.entity;

import jakarta.persistence.*;

@Entity
public class AppSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean boiteIdeeActive;
    @Column(nullable = false)
    private boolean forgeImageActive;

    public AppSetting() {
    }

    public AppSetting(Long id, boolean boiteIdeeActive, boolean forgeImageActive) {
        this.id = id;
        this.boiteIdeeActive = boiteIdeeActive;
        this.forgeImageActive = forgeImageActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isBoiteIdeeActive() {
        return boiteIdeeActive;
    }

    public void setBoiteIdeeActive(boolean boiteIdeeActive) {
        this.boiteIdeeActive = boiteIdeeActive;
    }

    public boolean isForgeImageActive() {
        return forgeImageActive;
    }

    public void setForgeImageActive(boolean forgeImageActive) {
        this.forgeImageActive = forgeImageActive;
    }
}
