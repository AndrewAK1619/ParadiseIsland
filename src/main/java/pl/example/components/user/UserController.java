package pl.example.components.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.example.components.user.UserDto;

@RestController
@RequestMapping("")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public List<UserDto> findAll() {
            return userService.findAll();
    }
}
