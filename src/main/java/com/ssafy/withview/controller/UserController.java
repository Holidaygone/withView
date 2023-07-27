package com.ssafy.withview.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.withview.repository.dto.JwtDto;
import com.ssafy.withview.repository.dto.LoginDto;
import com.ssafy.withview.service.JwtService;
import com.ssafy.withview.service.LoginService;
import com.ssafy.withview.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("")
	public LoginDto join(@RequestBody LoginDto loginDto) {
		logger.info("UserController: 회원가입 진행");
		logger.info("loginDto: " + loginDto.getId() + ", " + loginDto.getPassword());
		return userService.join(loginDto);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
		logger.info("UserController: 로그인 진행");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			Authentication authentication = loginService.login(loginDto);
			logger.info("authentication" + authentication);
			if (authentication != null) {
				JwtDto jwtDto = jwtService.generateToken2(authentication);
				logger.info("UserController: 로그인 성공");
				logger.info("[JWT] AccessToken: " + jwtDto.getAccessToken()
					+ ", RefreshToken: " + jwtDto.getRefreshToken());
				resultMap.put("success", true);
				resultMap.put("JWT", jwtDto);
				status = HttpStatus.CREATED;
			}
		} catch (Exception e) {
			logger.error("UserController: 로그인 실패", e);
			resultMap.put("success", false);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	@GetMapping("/test")
	public String test() {
		return "유저";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/test2")
	public String test2() {
		return "관리자";
	}
}
