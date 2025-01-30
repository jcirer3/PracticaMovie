package com.esliceu.movie;

import com.esliceu.movie.filters.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MovieApplication implements WebMvcConfigurer {
	@Autowired
	LoginInterceptor loginInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				loginInterceptor
		).addPathPatterns("/companys", "/countrys", "/departments", "/genders", "/genres", "/keywords",
				"/languageroles", "/languages", "/managePermissions", "/moviecreate", "/moviescrud",
				"/permission", "/persons", "/updatecompany", "/updatecountry", "/updatedepartment",
				"/updategender", "/updategenre", "/updatekeyword", "/updatelanguage", "/updatelanguagerole",
				"/updatemovie", "/updatemoviecrew", "/updatemoviegenre", "/updatemoviekeyword", "/updatemovielanguage",
				"/updateperson");
	}
}
