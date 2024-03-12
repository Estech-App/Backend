package com.estech.EstechAppBackend;

import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import com.estech.EstechAppBackend.service.RoleService;
import com.estech.EstechAppBackend.service.UserService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EstechAppBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Value("${admin.pass}")
	private String adminPass;

	public static void main(String[] args) {
		SpringApplication.run(EstechAppBackendApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Estech APP")
						.version("0.0.1")
						.description("--- Description ---"));
	}

	private void createRoles() {
		Role role = new Role();

		role.setId(1);
		role.setRolName(RoleEnum.ADMIN);
		role.setDescription("It will have unlimited access to the whole APP");
		roleService.saveRole(role);

		role.setId(2);
		role.setRolName(RoleEnum.SECRETARY);
		role.setDescription("It will have access to stock module, free-usages management and files management");
		roleService.saveRole(role);

		role.setId(3);
		role.setRolName(RoleEnum.TEACHER);
		role.setDescription("It will have access to checkins module, mentoring module, groups and files");
		roleService.saveRole(role);

		role.setId(4);
		role.setRolName(RoleEnum.STUDENT);
		role.setDescription("It will have access to mentoring module, free-usages, groups and files");
		roleService.saveRole(role);
	}

	private void createFirstAdminUser() throws Exception {
		UserEntity admin = new UserEntity();

		admin.setEmail("jefatura@escuelaestech.es");
		admin.setPassword(adminPass); // Ver como añadir la password al entorno
		admin.setName("JEFATURA");
		admin.setLastname("Escuela ESTECH");
		admin.setIsActive(true);
		Role adminRole = roleService.getRoleByRoleName(RoleEnum.ADMIN);
		if (adminRole == null) {
			throw new Exception();
		}
		admin.setRole(adminRole);
		userService.saveFirstAdminUser(admin);
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleService.getAllRoles().isEmpty()) {
			createRoles();
		}
		if (userService.getAllUsers().isEmpty()) {
			createFirstAdminUser();
		}
	}
}
