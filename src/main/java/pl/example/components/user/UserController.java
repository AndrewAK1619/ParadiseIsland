package pl.example.components.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.example.components.user.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("")
    public List<UserDto> findAll(@RequestParam(required = false) String lastName) {
        if(lastName != null)
            return userService.findByLastName(lastName);
        else
            return userService.findAll();
    }
    
    @PostMapping("")
    public ResponseEntity<UserDto> save(@RequestBody UserDto user) {
        if(user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The object being saved cannot be overwritten with an existing ID");
        UserDto savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto user) {
        if(!id.equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The object being updated must have an id that matches the id in the resource path");
        UserDto updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }
}
