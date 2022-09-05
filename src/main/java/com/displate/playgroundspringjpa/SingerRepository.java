
package com.displate.playgroundspringjpa;

import org.springframework.data.repository.CrudRepository;

public interface SingerRepository extends CrudRepository<SingerEntity, Long> {

    SingerEntity findByName(String name);

}
