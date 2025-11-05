# 🧱 Guia Prático: Construindo uma API REST com Spring Boot e Java

> **Objetivo:** Criar uma API REST completa em **Java + Spring Boot** implementando um **CRUD de produtos** para um sistema de delivery de hambúrgueres e pizzas.

---

## 📖 Sumário

- [Introdução](#introdução)  
- [Dependências e Configuração do Projeto](#dependências-e-configuração-do-projeto)  
- [Entidades (Models)](#entidades-models)  
- [Repositórios (Repositories)](#repositórios-repositories)  
- [DTOs e MapStruct](#dtos-e-mapstruct)  
- [Serviço (Service)](#serviço-service)  
- [Controlador REST](#controlador-rest)  
- [Testando com Postman](#testando-com-postman)  
- [Links úteis](#links-úteis)

---

## 🚀 Introdução

A arquitetura da aplicação segue o padrão **camadas (layered architecture)**:

| Camada | Anotação | Responsabilidade |
|--------|-----------|------------------|
| **Controller** | `@RestController` | Expor endpoints HTTP |
| **Service** | `@Service` | Lógica de negócio e regras da aplicação |
| **Repository** | `@Repository` | Persistência e acesso ao banco de dados |

---

## ⚙️ Dependências e Configuração do Projeto

### Requisitos:
- **Java 17+**
- **Spring Boot 3.x.x**
- **Maven**

Crie o projeto no [Spring Initializr](https://start.spring.io/) com as dependências:
- Spring Web  
- Spring Data JPA  
- Lombok  
- H2 Database (ou outro banco relacional)

### Dependências adicionais (no `pom.xml`):

```xml
<dependency>
  <groupId>org.mapstruct</groupId>
  <artifactId>mapstruct</artifactId>
  <version>1.5.5.Final</version>
</dependency>

<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok-mapstruct-binding</artifactId>
  <version>0.2.0</version>
</dependency>
```

E configure o `maven-compiler-plugin` para o processamento de anotações do MapStruct e Lombok.

---

## 🧩 Entidades (Models)

### `Category.java`
```java
public enum Category {
    PIZZA, HAMBURGUER
}
```

### `Product.java`
```java
@Entity
@Table(name = "products")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean available;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariation> productVariations;
}
```

### `ProductVariation.java`
```java
@Entity
@Table(name = "product_variations")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductVariation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sizeName;
    private String description;
    private Boolean available;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
```

---

## 💾 Repositórios (Repositories)

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
```

```java
@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    @Query("select pv from ProductVariation pv where pv.product.id = :productId and pv.id = :productVariationId")
    Optional<ProductVariation> findByProductIdAdProductVariationId(@Param("productId") Long productId,
                                                                   @Param("productVariationId") Long productVariationId);
}
```

---

## 🧱 DTOs e MapStruct

### Exemplos de DTOs

```java
public record CreateProductDto(
    String name,
    String description,
    String category,
    List<CreateProductVariationDto> productVariations,
    Boolean available
) {}
```

```java
public record RecoveryProductDto(
    Long id,
    String name,
    String description,
    String category,
    List<RecoveryProductVariationDto> productVariations,
    Boolean available
) {}
```

### `ProductMapper.java`

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productVariations", qualifiedByName = "mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductDto mapProductToRecoveryProductDto(Product product);

    ProductVariation mapCreateProductVariationDtoToProductVariation(CreateProductVariationDto dto);
}
```

---

## 🧠 Serviço (Service)

A camada de serviço contém a lógica de negócio principal, com operações CRUD para `Product` e `ProductVariation`.

```java
@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductVariationRepository variationRepository;
    @Autowired private ProductMapper mapper;

    public RecoveryProductDto createProduct(CreateProductDto dto) {
        // lógica para mapear, validar e salvar produto e suas variações
    }

    public List<RecoveryProductDto> getProducts() { ... }
    public RecoveryProductDto getProductById(Long id) { ... }
    public void deleteProductId(Long id) { ... }
}
```

---

## 🌐 Controlador REST

```java
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductService service;

    @PostMapping
    public ResponseEntity<RecoveryProductDto> createProduct(@RequestBody CreateProductDto dto) {
        return new ResponseEntity<>(service.createProduct(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecoveryProductDto>> getProducts() {
        return ResponseEntity.ok(service.getProducts());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        service.deleteProductId(productId);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🧪 Testando com Postman

Baixe a collection pronta para testar os endpoints:

📦 [Collection Postman](https://github.com/lipeacelino/crud-spring-boot/blob/master/crud-spring-boot.postman_collection.json)

Importe-a no seu Postman e experimente os métodos `GET`, `POST`, `PATCH`, `PUT` e `DELETE` da API.

---

## 🔗 Links úteis

- 📁 Projeto completo: [crud-spring-boot](https://github.com/lipeacelino/crud-spring-boot)  
- 🍕 Projeto relacionado: [pizzurg-api](https://github.com/lipeacelino/pizzurg-api)

---

## 🧩 Estrutura de pacotes sugerida

```
src/
 ├─ main/java/com/example/
 │   ├─ controller/
 │   ├─ service/
 │   ├─ repository/
 │   ├─ dto/
 │   ├─ mapper/
 │   └─ model/
 └─ resources/
     └─ application.properties
```

---

## 💡 Conclusão

Este guia oferece uma base sólida para entender e implementar APIs REST modernas com **Spring Boot**, **Java 17+**, **MapStruct**, e **Spring Data JPA** — seguindo boas práticas de arquitetura em camadas e padrões de desenvolvimento limpo.
