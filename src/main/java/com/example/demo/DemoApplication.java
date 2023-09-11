package com.example.demo;

import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
//		Role role1 = new Role();
//		Role role2 = new Role();
//		Role role3 = new Role();
//		role1.setName(ERole.ROLE_ADMIN);
//		role2.setName(ERole.ROLE_USER);
//		role3.setName(ERole.ROLE_MODERATOR);
//		roleRepository.save(role1);
//		roleRepository.save(role2);
//		roleRepository.save(role3);
//		User admin = new User();
//		admin.setEmail("yusuf1@gmail.com");
//		admin.setPassword(passwordEncoder.encode("yusuf1@gmail.com"));
//		admin.setName("yusuf 1");
//		admin.setAbout("my bio 1");
//		Set<Role> roleSet = new HashSet<>();
//		roleSet.add(role1);
//		roleSet.add(role2);
//		admin.setRoles(roleSet);
//		userRepository.save(admin);
	}


}
