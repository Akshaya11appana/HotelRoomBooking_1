
package com.ey.service;

import com.ey.dto.UserDto;
import com.ey.dto.common.UpdateAck;
import com.ey.dto.user.EmailUpdate;
import com.ey.domain.User;
import com.ey.repo.UserRepo;
import com.ey.security.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService#updateEmail.
 * No Spring context. Mocks: UserRepo, CurrentUser, PasswordEncoder.
 */
class UserServiceTest {

    private UserRepo userRepo;
    private CurrentUser currentUser;
    private PasswordEncoder encoder;
    private UserService service;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        currentUser = mock(CurrentUser.class);
        encoder = mock(PasswordEncoder.class);
        service = new UserService(userRepo, currentUser, encoder);
    }

    private User user() {
        User u = new User();
        u.setId(1L);
        u.setFirstName("Akshaya");
        u.setLastName("Appana");
        u.setEmail("old@mail.com");
        u.setPhone("9999999999");
        u.setPasswordHash("$2a$10$anything");
        return u;
    }

    @Test
    void updateEmail_changes_only_email_and_returns_ack() {
        when(currentUser.email()).thenReturn("old@mail.com");
        when(userRepo.findByEmail("old@mail.com")).thenReturn(Optional.of(user()));
        when(userRepo.existsByEmail("new@mail.com")).thenReturn(false);

        EmailUpdate req = new EmailUpdate();
        req.setEmail("new@mail.com");

        UpdateAck ack = service.updateEmail(req);

        assertThat(ack.getMessage()).containsIgnoringCase("Email updated");
        ArgumentCaptor<User> saved = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(saved.capture());
        assertThat(saved.getValue().getEmail()).isEqualTo("new@mail.com");
        assertThat(saved.getValue().getFirstName()).isEqualTo("Akshaya");
        assertThat(saved.getValue().getLastName()).isEqualTo("Appana");
    }

    @Test
    void updateEmail_throws_when_duplicate_email() {
        when(currentUser.email()).thenReturn("old@mail.com");
        when(userRepo.findByEmail("old@mail.com")).thenReturn(Optional.of(user()));
        when(userRepo.existsByEmail("dup@mail.com")).thenReturn(true);

        EmailUpdate req = new EmailUpdate();
        req.setEmail("dup@mail.com");

        assertThatThrownBy(() -> service.updateEmail(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already in use");
        verify(userRepo, never()).save(any(User.class));
    }
}
