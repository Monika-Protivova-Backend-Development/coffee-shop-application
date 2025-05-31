# Backend Development with Kotlin – Course Syllabus

This course focuses on backend software engineering, including testing. The language used is Kotlin.
However, the same principles apply to any language and technology stack.

---

### Lesson 1: Introduction to Backend Engineering
*What makes a good backend system?*
- Overview of backend roles, responsibilities, and architecture styles (monolith, microservices, etc.).
- Key backend concerns: state, performance, security, and API design.
- Course structure and project overview.

---

### Lesson 2: Kotlin Essentials for Backend
*Getting up to speed with Kotlin*
- Kotlin syntax essentials: variables, functions, control flow, classes.
- Brief JVM/Java context (types, memory model).
- First Kotlin project setup using Gradle.

---

### Lesson 3: Object-Oriented Principles & Models
*Kotlin's language power for backend models*
- Object-oriented features: encapsulation, inheritance, polymorphism, abstraction.
- Data classes, simple models for backend domain logic.
- Brief intro to composition and interface use.

---

### Lesson 4: Testing Fundamentals in Kotlin
*How and why we test: philosophy, tools, and setup*
- Testing motivations, pyramid, and test types (unit, integration, functional).
- Tooling: JUnit, Kotest, assertions, mocking.
- Test structure, test coverage, naming conventions.
- Setup CI basics (e.g. GitHub Actions or Gradle test task).

---

### Lesson 5: API Design & REST Controllers
*Designing and testing a RESTful interface*
- REST principles, HTTP methods, URI structure.
- Implementing routes/controllers in Ktor.
- Testing APIs: endpoint tests, status checks, request/response validation.

---

### Lesson 6: Service Layer & Business Logic
*Separating logic with testable services*
- Designing service layers, keeping logic independent from web layer.
- Mocking external dependencies, service-level unit tests.
- Error handling and logging.

---

### Lesson 7: Data Access & Repositories
*Persistence with proper abstraction and tests*
- Introduction to Exposed ORM or JDBC.
- Repository interfaces and test doubles.
- Testing with in-memory databases or containers and transaction rollback patterns.

---

### Lesson 8: Validation, Exceptions, and Robustness
*Making your application resilient to input and logic errors*
- Input validation strategies (manual, Ktor plugins).
- Exception hierarchy, error responses, domain-specific errors.
- Testing edge cases, validation rules, and fault injection.

---

### Lesson 9: Architecture Patterns & SOLID Principles
*Writing maintainable, testable code in backend systems*
- SOLID in Kotlin: examples and pitfalls.
- Layered architecture, dependency inversion.
- Testing for extensibility: interface testing, contract-based tests.

---

### Lesson 10: Concurrency & Coroutines
*Making backends fast and safe with Kotlin coroutines*
- Structured concurrency, coroutine scopes, async processing.
- Race conditions, shared mutable state, thread safety.
- Testing concurrency: timing assertions, stress tests, flakiness handling.

---

### Lesson 11: Security, Authentication & Authorization
*Protecting backend applications and users*
- Security concerns: injection, XSS, CSRF, secure headers.
- Auth strategies: JWT, sessions, roles.
- Security testing: permission tests, input fuzzing, misconfiguration checks.

---

### Lesson 12: Deployment, Observability & Final Review
*From dev to prod: finishing strong*
- 12-factor app principles, config management, CI/CD, database migrations.
- Logging, health checks, metrics.
- System-level testing.
