package com.pi4.wayclient.controller;

import com.pi4.wayclient.dto.*;
import com.pi4.wayclient.model.*;
import com.pi4.wayclient.repository.*;
import com.pi4.wayclient.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TokenService tokenService;
    @Value("${recaptcha.secret.key}")
    private String secretKey;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String recaptchaToken = data.recaptchaToken();

        var token = tokenService.generateToken((User) auth.getPrincipal());

        String userType = "";
        UUID userId = null;

        if (!validateRecaptcha(recaptchaToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("reCAPTCHA inválido.");
        }

        if (adminRepository.findByEmail(data.email()).isPresent()) {
            userType = "ADMIN";
            userId = adminRepository.findByEmail(data.email()).get().getId();
        } else if (employeeRepository.findByEmail(data.email()).isPresent()) {
            userType = "EMPLOYEE";
            userId = employeeRepository.findByEmail(data.email()).get().getId();
        } else if (customerRepository.findByEmail(data.email()).isPresent()){
            userType = "CUSTOMER";
            userId = customerRepository.findByEmail(data.email()).get().getId();
        }

        return ResponseEntity.ok(new LoginResponseDTO(token, userType, userId));
    }

    @PostMapping("/signup_admin")
    public ResponseEntity signUp(@RequestBody @Valid SignUpDTO data) {
        if(userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Admin newAdmin = new Admin(data.email(), data.name(), encryptedPassword, data.role());
        this.adminRepository.save(newAdmin);

        return ResponseEntity.ok().build();
    }

    @PostMapping("signup_customer")
    public ResponseEntity signUp(@RequestBody @Valid SignUpCustomerDTO data) {
        if(userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Customer newCustomer = new Customer(data.email(), data.name(), encryptedPassword, data.role(), data.phone());
        this.customerRepository.save(newCustomer);

        return ResponseEntity.ok().build();
    }

    @PostMapping("signup_employee")
    public ResponseEntity signUp(@RequestBody @Valid SignUpEmployeeDTO data) {
        if(userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        Department department = departmentRepository.findByName(data.departmentName());
        if(department == null) return ResponseEntity.badRequest().body("Departamento não existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Employee newEmployee = new Employee(data.email(), data.name(), encryptedPassword, data.role(), department);
        this.employeeRepository.save(newEmployee);

        return ResponseEntity.ok().build();
    }

    public boolean validateRecaptcha(String token) {
        String url = "https://www.google.com/recaptcha/api/siteverify";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "secret=" + secretKey + "&response=" + token;
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && (boolean) responseBody.get("success")) {
            return true;
        } else {
            System.err.println("Erro na validação do reCAPTCHA: " + responseBody);
            return false;
        }
    }
}
