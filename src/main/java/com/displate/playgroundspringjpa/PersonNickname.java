

package com.displate.playgroundspringjpa;

import org.springframework.beans.factory.annotation.Value;

interface PersonNickname {

    @Value("#{'trololo-' + target.firstname + '-' + target.lastname}")
    String getNickname();

}
