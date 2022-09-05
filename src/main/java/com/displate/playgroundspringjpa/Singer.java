package com.displate.playgroundspringjpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Singer {

    @Id
    @GeneratedValue
    @Column(name = "singer_id", nullable = false)
    private Long singerId;

    @Version
    private Long entityVersion;

    private String name;

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Song> songs = new java.util.LinkedHashSet<>();

    @ManyToMany(mappedBy = "singers")
    Set<Playlist> playlists = new java.util.LinkedHashSet<>();
}
