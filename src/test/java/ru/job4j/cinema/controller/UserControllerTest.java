package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.user.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenGetRegistrationPageThenPageWithUsersRegistration() {
        assertThat(userController.getRegistrationPage()).isEqualTo("users/register");
    }

    @Test
    public void whenGetLoginPageThenPageWithUsersLogin() {
        assertThat(userController.getLoginPage()).isEqualTo("users/login");
    }

    @Test
    public void whenRegisterThenPageWithSessions() {
        User user = new User(1, "email", "name", "password");
        when(userService.save(user)).thenReturn(Optional.of(user));

        assertThat(userController.register(user, new ConcurrentModel())).isEqualTo("redirect:/sessions");
    }

    @Test
    public void whenUserExistWithExceptionThenErrorPageWithMessage() {
        Model model = new ConcurrentModel();

        assertThat(userController.register(new User(), model)).isEqualTo("users/register");
        assertThat(model.getAttribute("error")).isEqualTo("Пользователь с такой почтой уже существует");
    }
}