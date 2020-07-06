package pl.example.components.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.example.components.user.UserService;
import pl.example.components.validation.ValidationService;
import pl.example.components.user.UserDto;

@RestController
@RequestMapping("/account")
public class AccountController {

    private UserService userService;

    @Autowired
    AccountController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> save(@Valid @RequestBody UserDto user, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(ValidationService.valid(result));
		}
        if(user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The object being saved cannot be overwritten with an existing ID");
        UserDto savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/success/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
    
    @GetMapping("/register/success/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/profile")
    public List<String> getEmail() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	List<String> email = new ArrayList<>();
    	email.add(authentication.getName());
        return email;
    }
    
    @PostMapping("/profile/email")
    public ResponseEntity<?> saveUserEmail(@RequestBody String email) {
    	UserDto userDto = userService.changeUserEmial(email);
        return ResponseEntity.ok(userDto);
    }
}
