# 1. Data Validation in Spring Boot

Spring Boot supports data validation using annotations from `jakarta.validation`.

Some of the common validation annotations are: 
| Annotation      | Purpose                                                            |
| --------------- | ------------------------------------------------------------------ |
| `@NotNull`      | Field must not be null                                             |
| `@NotBlank`     | Field must not be null or empty (for Strings)                      |
| `@Size`         | Restrict min/max length                                            |
| `@Min` / `@Max` | Numeric range validation                                           |
| `@Email`        | Validate email format                                              |
| `@Pattern`      | Validate using regex                                               |
| `@Past`         | Date must be in the past                                           |
| `@Future`       | Date must be in the future                                         |

### Example of the above annotations

```java
public class EmployeeDTO {
    @NotBlank(message = "first name can't be null")
    @Size(max = 30)
    private String firstName;

    @NotNull(message = "last name can't be null.")
    private String lastName;

    @NotBlank(message = "Department name must be provided")
    private String department;

    @Range(min = 0, message = "salary can't be negative")
    private BigDecimal salary;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10}$",message = "Invalid phone number")
    private String phone;

    @NotBlank
    @Size(max = 50, message = "Provide the correct location")
    private String location;
}
```

#### Note:
Unique Constraint is not demonstrated here because it is typically enforced at the entity (database) level, not at the request validation level.

All other validation annotations shown here are stateless and operate purely on the incoming request data.

In contrast, a unique constraint requires a database lookup to verify whether a value already exists, making it stateful and dependent on persistence logic.

```java
@Entity
@Table(name="employee",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email"),
            @UniqueConstraint(columnNames = "phone")
        }
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private BigDecimal salary;
    private String email;
    private String phone;
    private String location;
}
```
Validation is triggered using @Valid in the controller:
```java
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveEmployee(employeeDTO));
    }
```

# 2. Global Exception Handling

Instead of handling exceptions inside every controller, Spring Boot allows centralized exception handling using @ControllerAdvice.

### Handling Validation Errors

When validation fails, Spring throws MethodArgumentNotValidException. We handle it globally as shown below:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage())
          );

        return ResponseEntity.badRequest().body(errors);
    }
}
```

Sample Error Response
```json
{
  "firstName": "first name can't be empty",
  "email": "Invalid email format",
  "salary": "salary can't be negative"
}
```


This makes the API:
- Easy to debug
- Consistent across endpoints
- Client-friendly
