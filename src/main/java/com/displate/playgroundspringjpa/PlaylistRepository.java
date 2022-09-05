package com.displate.playgroundspringjpa;

import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

    Playlist findByName(String name);

}
