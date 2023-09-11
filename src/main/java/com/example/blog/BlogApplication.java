package com.example.blog;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
//		System.out.println(this.passwordEncoder.encode("p@ssword@user3"));
		try {
			Role savedRole = roleRepository.save(new Role(AppConstants.ROLE_ADMIN, "ADMIN"));
			System.out.println(savedRole.getName() + " Role Added");

			savedRole = roleRepository.save(new Role(AppConstants.ROLE_USER, "USER"));
			System.out.println(savedRole.getName() + " Role Added");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
