
package com.displate.playgroundspringjpa;

import org.springframework.data.repository.CrudRepository;

public interface SingerRepository extends CrudRepository<Singer, Long> {

    Singer findByName(String name);

}
