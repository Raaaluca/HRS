package ro.siit.HRS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;
import ro.siit.HRS.service.impl.ManagerServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private ManagerServiceImpl managerService;

    @Test
    void createManager() {

        ManagerCreateDto managerCreateDto = new ManagerCreateDto();
        managerCreateDto.setName("Tudor Dumars");
        managerCreateDto.setEmail("tudor_d@yahoo.com");
        managerCreateDto.setCity("Bucuresti");
        managerCreateDto.setPhoneNumber("0733222111");
        managerCreateDto.setNationalId("112211");
        managerCreateDto.setGender("male");
        managerCreateDto.setAddress("Str. Domnica");
        managerCreateDto.setStartDate(LocalDate.of(2023, 12, 3));
        managerCreateDto.setEndDate(LocalDate.of(2027, 11, 11));

        ManagerReturnDto expectedManagerReturnDto = new ManagerReturnDto();
        expectedManagerReturnDto.setAddress("Str. Domnica");
        expectedManagerReturnDto.setEmail("tudor_d@yahoo.com");
        expectedManagerReturnDto.setCity("Bucuresti");
        expectedManagerReturnDto.setGender("male");
        expectedManagerReturnDto.setName("Tudor Dumars");
        expectedManagerReturnDto.setStartDate(LocalDate.of(2023, 12, 3));
        expectedManagerReturnDto.setEndDate(LocalDate.of(2027, 11, 11));
        expectedManagerReturnDto.setNationalId("112211");
        expectedManagerReturnDto.setPhoneNumber("0733222111");

        User user = new User();
        user.setRole("MANAGER");
        user.setUsername(managerCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(managerCreateDto.getNationalId()));

        Manager manager = new Manager();
        manager.setAddress("Str. Domnica");
        manager.setEmail("tudor_d@yahoo.com");
        manager.setCity("Bucuresti");
        manager.setGender("male");
        manager.setName("Tudor Dumars");
        manager.setStartDate(LocalDate.of(2023, 12, 3));
        manager.setEndDate(LocalDate.of(2027, 11, 11));
        manager.setNationalId("112211");
        manager.setPhoneNumber("0733222111");

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        ManagerReturnDto resultedManagerReturnDto = managerService.createManager(managerCreateDto);
        assertEquals(expectedManagerReturnDto, resultedManagerReturnDto);
    }

    @Test
    void updateManager() {

        ManagerUpdateDto managerUpdateDto = new ManagerUpdateDto();
        managerUpdateDto.setId(1L);
        managerUpdateDto.setPhoneNumber("0711000888");
        managerUpdateDto.setAddress("Str. Dambovicioarei");
        managerUpdateDto.setName("Catalin Gruia");
        managerUpdateDto.setCity("Bucuresti");
        managerUpdateDto.setEndDate(LocalDate.of(2029,12,1));

        ManagerReturnDto expectedUpdateManager = new ManagerReturnDto();
        expectedUpdateManager.setId(1L);
        expectedUpdateManager.setPhoneNumber("0711000888");
        expectedUpdateManager.setCity("Bucuresti");
        expectedUpdateManager.setName("Catalin Gruia");
        expectedUpdateManager.setAddress("Str. Dambovicioarei");
        expectedUpdateManager.setEndDate(LocalDate.of(2029,12,1));

        User user = new User();

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setPhoneNumber("0711000888");
        manager.setEndDate(LocalDate.of(2029,12,1));
        manager.setCity("Bucuresti");
        manager.setName("Catalin Gruia");
        manager.setAddress("Str. Dambovicioarei");
        manager.setUser(user);

        Mockito.when(managerRepository.findById(any())).thenReturn(Optional.of(manager));
        Mockito.when(managerRepository.save(any())).thenReturn(manager);

        ManagerReturnDto resultedManagerReturnDto = managerService.updateManager(managerUpdateDto);
        assertEquals(expectedUpdateManager, resultedManagerReturnDto);

    }

    @Test
    void deleteManager() {

        Long managerId = 1L;
        String expectedMessage = "This manager has been deleted!";

        User user = new User();

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setUser(user);

        Mockito.when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));

        String resultedMessage = managerService.deleteManager(managerId);
        assertEquals(expectedMessage,resultedMessage );
    }

    @Test
    void getManagerRemainingDays() {

        String username = "maria_u@yahoo.com";
        Integer expectedManagerRemainingDays = 21;

        User user = new User();

        Manager manager = new Manager();
        manager.setAnnualLeaveDays(21);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(managerRepository.findByUser(user)).thenReturn(Optional.of(manager));

        Integer resultedManagerRemainingDays = managerService.getManagerRemainingDays(username);

        assertEquals(expectedManagerRemainingDays, resultedManagerRemainingDays);
    }
}