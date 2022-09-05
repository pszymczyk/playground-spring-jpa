package com.displate.playgroundspringjpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
class Song {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long plays;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    private SingerEntity singer;

}
