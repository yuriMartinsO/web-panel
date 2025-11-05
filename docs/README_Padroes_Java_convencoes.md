# 🧭 Java Standards & Examples (Equivalents to PHP PSRs) - Spring Boot + Copilot-friendly

**Autor**: Yuri Martins

> Explicação em português; exemplos de código, comentários, nomes de classes e convenções em inglês.
>
> Este README reúne padrões, convenções e exemplos práticos em Java usando **Spring Boot**. Foi formatado para que o **GitHub Copilot** possa ler e usar como contexto: headings sem quebras, código claro, e comentários/names em inglês.

---

## Introdução
Este documento apresenta os **principais padrões e convenções do Java** (equivalentes aos PSRs do PHP), com **exemplos de código reais** em Spring Boot. As explicações textuais estão em **português**; todo o código, comentários e nomes seguem o **inglês técnico** para máxima compatibilidade com ferramentas como Copilot.

---

## 1. Convenções de Código (Code Conventions)
> **Nota sobre convenções oficiais**: O Java possui um documento oficial de convenções criado pela Oracle/Sun ("Code Conventions for the Java Programming Language"), que cobre principalmente:
> - Nomes de arquivos
> - Indentação e formatação
> - Nomenclatura (classes, métodos, variáveis)
> - Organização de arquivos
> 
> As convenções de documentação (Javadoc) também são oficiais e parte da especificação Java. Os exemplos neste documento combinam as convenções oficiais com boas práticas amplamente aceitas pela comunidade Java e pelo Spring Framework.

Explicação: siga as convenções oficiais para manter consistência em equipe e permitir que ferramentas automáticas sugiram código correto.

```java
// Class names: PascalCase
public class CustomerService { ... }

// Method and variable names: camelCase
public void processOrder() { int totalAmount = 0; ... }

// Constants: UPPER_SNAKE_CASE
public static final int MAX_RETRIES = 3;

// Packages: lower case, reverse domain
package com.example.project;

// Indentation: 4 spaces, no tabs
if (value > 0) {
    process();
}
```

---

## 1.1 Convenções de Comentários (Documentation)
Explicação: use Javadoc para documentar classes, métodos e campos públicos. Comentários internos apenas quando necessário para explicar lógica complexa.

```java
/**
 * Represents a customer order in the system.
 * This class is immutable and thread-safe.
 *
 * @author Yuri Martins
 * @since 1.0
 */
public class Order {
    /** Maximum number of items allowed per order */
    public static final int MAX_ITEMS = 100;
    
    private final String id;
    
    /**
     * Creates a new order with the specified details.
     *
     * @param id    unique identifier for the order
     * @throws IllegalArgumentException if id is null or empty
     */
    public Order(String id) {
        // Validação interna - comentário em português ok
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }
    
    /**
     * Returns the order's unique identifier.
     *
     * @return the order ID, never null
     */
    public String getId() {
        return id;
    }
    
    /*
     * Block comment example for implementation notes
     * Use this style for longer internal comments
     */
    
    // Line comment for brief explanations
    // Prefer English for all comments, but Portuguese is OK for internal comments
}
```

### Regras para Comentários

1. **Javadoc obrigatório para**:
   - Classes públicas
   - Métodos públicos
   - Campos públicos
   - APIs expostas

2. **Tags Javadoc comuns**:
   - `@param` - parâmetros do método
   - `@return` - valor retornado
   - `@throws` - exceções lançadas
   - `@since` - versão de introdução
   - `@deprecated` - marca como obsoleto
   - `@author` - autor(es)
   - `@see` - referências relacionadas

3. **Boas práticas**:
   - Escreva documentação pensando no usuário da API
   - Explique o "porquê" nos comentários internos
   - Mantenha comentários atualizados com o código
   - Use português apenas em comentários internos
   - Evite comentários óbvios

---

## 2. Clean Code & Best Practices (Explicação)
Explicação: escreva métodos pequenos, responsabilidades únicas e nomes descritivos. Testes e tratamento de exceções são obrigatórios para código de produção.

```java
// Bad example
public void process(int a, int b) { // what are a and b? 
    // long method doing multiple things
}

// Good example
public void processOrder(Order order) { // clear intent
    validateOrder(order);
    calculateTotal(order);
    persistOrder(order);
}
```

---

## 3. Design Patterns (Com exemplos de código)
Explicação: padrões clássicos com implementações simples em Java. Use-os onde fazem sentido — não por moda.

### Singleton
```java
public final class Configuration {
    private static final Configuration INSTANCE = new Configuration();
    private Configuration() { }
    public static Configuration getInstance() {
        return INSTANCE;
    }
    // getters...
}
```

### Factory Method
```java
public interface Notification {
    void send(String message);
}

public class EmailNotification implements Notification {
    @Override
    public void send(String message) { /* send email */ }
}

public class SmsNotification implements Notification {
    @Override
    public void send(String message) { /* send sms */ }
}

public class NotificationFactory {
    public static Notification create(String type) {
        switch (type) {
            case "EMAIL": return new EmailNotification();
            case "SMS":   return new SmsNotification();
            default: throw new IllegalArgumentException("Unknown type");
        }
    }
}
```

### Builder (useful for complex objects)
```java
public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private User(Builder b) {
        this.firstName = b.firstName; this.lastName = b.lastName; this.email = b.email;
    }
    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        public Builder withFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder withLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder withEmail(String email) { this.email = email; return this; }
        public User build() { return new User(this); }
    }
}
```

### Strategy (behavioral)
```java
public interface PaymentStrategy {
    void pay(Order order);
}

public class CreditCardPayment implements PaymentStrategy {
    @Override public void pay(Order order) { /* process credit card */ }
}

public class PaypalPayment implements PaymentStrategy {
    @Override public void pay(Order order) { /* process paypal */ }
}

public class PaymentService {
    private PaymentStrategy strategy;
    public PaymentService(PaymentStrategy strategy) { this.strategy = strategy; }
    public void executePayment(Order order) { strategy.pay(order); }
}
```

### Observer (event dispatch)
```java
public interface EventListener {
    void onEvent(Event e);
}

public class EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();
    public void register(EventListener l) { listeners.add(l); }
    public void publish(Event e) { listeners.forEach(l -> l.onEvent(e)); }
}
```

### Adapter (bridge between interfaces)
```java
// Adaptee
class LegacyPaymentService {
    public void makePayment(String jsonPayload) { /* ... */ }
}

// Target
interface PaymentClient {
    void pay(PaymentRequest request);
}

// Adapter
class LegacyPaymentAdapter implements PaymentClient {
    private final LegacyPaymentService adaptee;
    public LegacyPaymentAdapter(LegacyPaymentService adaptee) { this.adaptee = adaptee; }
    @Override public void pay(PaymentRequest request) {
        String json = convertToJson(request);
        adaptee.makePayment(json);
    }
}
```

---

## 4. Arquitetura em camadas com Spring Boot (Controller → Service → Repository)
Explicação: código de exemplo minimal, mostrando DTO, Entity, Repository, Service e Controller.

### Entity
```java
package com.example.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    // getters and setters omitted for brevity
}
```

### Repository
```java
package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom queries if needed
}
```

### DTO
```java
package com.example.demo.dto;
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    // getters and setters
}
```

### Mapper (MapStruct example)
```java
// MapStruct interface (requires annotation processor)
// Mapper will implement mapping at compile time
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto toDto(Product product);
    Product toEntity(ProductDto dto);
}
```

### Service
```java
package com.example.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.ProductRepository;
import com.example.demo.dto.ProductDto;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public ProductDto create(ProductDto dto) {
        Product entity = mapper.toEntity(dto);
        Product saved = repository.save(entity);
        return mapper.toDto(saved);
    }
    public ProductDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }
}
```

### Controller
```java
package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @PostMapping
    public ProductDto create(@RequestBody ProductDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        return service.findById(id);
    }
}
```

---

## 5. Validação e Segurança (exemplos)
Explicação: use Bean Validation, Spring Security, hashing and prepared statements.

### Bean Validation example
```java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class UserRegistrationDto {
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
    @Email(message = "invalid email")
    private String email;
    // getters and setters
}
```

### PreparedStatement to avoid SQL injection (JDBC)
```java
String sql = "SELECT * FROM users WHERE email = ?";
try (PreparedStatement ps = connection.prepareStatement(sql)) {
    ps.setString(1, email);
    try (ResultSet rs = ps.executeQuery()) {
        // process results
    }
}
```

### Password hashing (BCrypt)
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashed = encoder.encode(rawPassword);
boolean matches = encoder.matches(rawPassword, hashed);
```

### JWT skeleton (simplified)
```java
// Generate token (pseudocode)
String token = Jwts.builder()
    .setSubject(userId)
    .setExpiration(expirationDate)
    .signWith(secretKey)
    .compact();
```

---

## 6. Logging (SLF4J example)
Explicação: use SLF4J facade with a concrete implementation like Logback.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    public void process(Order order) {
        log.info("Processing order {}", order.getId());
        try {
            // do work
        } catch (Exception e) {
            log.error("Failed to process order {}", order.getId(), e);
            throw e;
        }
    }
}
```

---

## 7. Testing (JUnit 5 + Mockito examples)
Explicação: escreva testes unitários e de integração para garantir comportamento.

### Unit test example
```java
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    @Test
    public void createShouldSaveProduct() {
        ProductRepository repository = mock(ProductRepository.class);
        ProductMapper mapper = ProductMapper.INSTANCE; // or mock
        ProductService service = new ProductService(repository, mapper);
        ProductDto dto = new ProductDto();
        dto.setName("Test");
        dto.setPrice(10.0);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        ProductDto result = service.create(dto);
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }
}
```

---

## 8. Useful JSRs and Specs (lista curta)
Explicação: referências formais que definem comportamento e APIs Java.

- JSR 175 - Annotations
- JSR 299 - CDI
- JSR 330 - Dependency Injection
- JSR 338 - JPA
- JSR 340 - Servlet
- JSR 380 - Bean Validation
- JSR 376 - Module System

---

## 9. Mapping PHP PSRs to Java (Resumo rápido)
Explicação: tabela de correspondência para referência de quem conhece PSRs.

| PHP PSR | Purpose | Java Equivalent |
| PSR-1/PSR-12 | Coding Style | Java Code Conventions |
| PSR-3 | Logger | SLF4J / Logback |
| PSR-4 | Autoload | Java packages / modules |
| PSR-6/PSR-16 | Caching | Spring Cache / Ehcache |
| PSR-7/PSR-15 | HTTP / Middleware | Servlet API / Filters / Spring MVC |
| PSR-11 | Container | Spring Context / CDI |
| PSR-14 | Event Dispatcher | ApplicationEventPublisher |
| PSR-19 | DocBlock | Javadoc |

---

## 10. Checklist for repository (how to place this file)
Explicação: recomendações práticas para que Copilot e colegas aproveitem o conteúdo.

- Place this README at repository root as `README.md` or in `/docs/` and link from root README.
- Keep examples small and focused (like in this file).
- Add `pom.xml` or `build.gradle` sample in repo to assist Copilot in dependency resolution.
- Enable annotation processors for MapStruct in build config.

---

## 11. Recursos adicionais
Explicação: links para referência e leitura avançada.

- Oracle Java Code Conventions: https://www.oracle.com/java/technologies/javase/codeconventions-contents.html
- JCP JSR list: https://jcp.org/en/jsr/all
- Clean Code - Robert C. Martin
- Effective Java - Joshua Bloch
- Spring Documentation: https://spring.io/docs
- MapStruct: https://mapstruct.org/

---

## Observações finais
Explicação: este README foi formatado para leitura humana em português e para alimentar o contexto do Copilot. Se desejar, posso gerar também exemplos de `pom.xml` e `application.yml` prontos para uso em um projeto Spring Boot minimal.

---
