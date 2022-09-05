package com.displate.playgroundspringjpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Transactional
@SpringBootTest
class JoinsTest {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    SingerRepository singerRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldCreatePlaylist() {
        SingerEntity singer = new SingerEntity();
        singer.setName("Katy Perry");
//        singerRepository.save(singer);

        Playlist playlist = new Playlist();
        playlist.setName("Best kids songs");
//        playlistRepository.save(playlist);

        playlist.getSingers().add(singer);
        playlistRepository.save(playlist);

        assertThat(playlistRepository.count()).isEqualTo(1);
    }

    /*
     * when set @ManyToMany(cascade = CascadeType.ALL), exception is not thrown
     */
    @Test
    void shouldThrowExceptionWhenSingerIsNotPersisted() {
        SingerEntity singer = new SingerEntity();
        singer.setName("Miley Cyrus");
        Playlist playlist = new Playlist();
        playlist.setName("Best kids songs");
        playlist.getSingers().add(singer);

        playlistRepository.save(playlist);

        assertThatExceptionOfType(InvalidDataAccessApiUsageException.class).isThrownBy(() -> playlistRepository.count());
    }

    @Test
    void lazyLoadingTestAndNPlusOneProblem() {
        Playlist p = new Playlist();
        p.setName("p1");
        playlistRepository.save(p);

        for (int i = 0; i < 10; i++) {
            SingerEntity s = new SingerEntity();
            p.getSingers().add(s);
            s.setName("s" + i);
            for (int j = 0; j < 100; j++) {
                Song song = new Song();
                song.setSinger(s);
                song.setName("s" + i);
                s.getSongs().add(song);
                s.getPlaylists().add(p);
            }
            singerRepository.save(s);
        }

        playlistRepository.save(p);

        entityManager.flush();
        entityManager.clear();

        Playlist p1 = playlistRepository.findByName("p1");

        p1.getSingers().forEach(s -> s.getSongs().forEach(song ->
                song.getName()
        ));

    }
}
