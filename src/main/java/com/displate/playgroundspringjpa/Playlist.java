package com.displate.playgroundspringjpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Setter(value = AccessLevel.PRIVATE)
public class Playlist {

    @Id
    @GeneratedValue
    @Column(name = "playlist_id", nullable = false)
    private Long playlistId;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlist_singers",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id"))
    Set<SingerEntity> singers = new java.util.LinkedHashSet<>();
}
