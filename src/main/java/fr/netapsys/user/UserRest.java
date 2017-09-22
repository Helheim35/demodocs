package fr.netapsys.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserRest {

    @GetMapping("{id}")
    public User getUser(@PathVariable long id) {
        return User.builder()
                .id(id)
                .firstName("John")
                .lastName("Smith")
                .birthday(new Date())
                .locked(false)
                .build();
    }

}
