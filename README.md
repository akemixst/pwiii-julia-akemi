# 🌐 Programação Web III

Repositório com os projetos feitos na disciplina de **PWIII**.

Reúne exercícios práticos em stacks diferentes (React, Laravel e Spring Boot), cada um em sua própria pasta, e também uma explicação dos conceitos de Spring Boot estudados ao longo da disciplina.

---

## 🗂️ Projetos

| Projeto | Descrição | Pasta |
|---|---|---|
| 🏢 Cliente CNPJ API | API REST em Spring Boot que consulta CNPJ na BrasilAPI e salva no H2 | [cliente-cnpj-api](cliente-cnpj-api) |
| 🎮 Tic-Tac-Toe | Jogo da Velha feito com React (Create React App) | [tictactoe](tictactoe) |
| 🔄 App Migration | Aplicação Laravel para estudo de migrations | [app-migration](app-migration) |
| 🐘 PW3 Laravel | Projeto Laravel da disciplina | [pw3-laravel](pw3-laravel) |
| 🧪 Teste | Projeto Laravel de testes e experimentos | [teste](teste) |

---

# 🌱 Criando um Projeto Java com Spring Initializr

O [Spring Initializr](https://start.spring.io) é o jeito mais simples de gerar a estrutura inicial de um projeto Spring Boot: tudo é feito pelo navegador, sem instalar nada.

![Spring Initializr](docs/assets/spring-initializr.png)

## Passo a passo

1. Entre em **start.spring.io**
2. Em **Project**, escolha Maven ou Gradle
3. Em **Language**, escolha Java, Kotlin ou Groovy
4. Selecione a versão do **Spring Boot**
5. Preencha o **Project Metadata** (Group, Artifact, Package name, Packaging e Java)
6. Clique em **ADD DEPENDENCIES** e marque as bibliotecas que o projeto vai usar
7. Clique em **GENERATE** para baixar o projeto em `.zip`

> 💡 No projeto [cliente-cnpj-api](cliente-cnpj-api) foram usados **Maven**, **Java 21** e **Spring Boot 3.3.5**, com as dependências Spring Web, Spring Data JPA, Validation, H2 e OpenFeign.

---

## 📦 Dependências mais comuns

### Lombok
Biblioteca que diminui o código repetitivo (*boilerplate*) por meio de anotações como `@Getter`, `@Setter`, `@Data`, `@Builder` e `@AllArgsConstructor`. Os métodos que normalmente seriam escritos à mão são gerados automaticamente durante a compilação.

### Spring Web
Módulo para criar aplicações web e APIs REST com Spring MVC. Já inclui o **Tomcat** embutido como servidor padrão, então a aplicação sobe sem precisar instalar um servidor à parte.

### Spring Boot DevTools
Ferramenta que agiliza o desenvolvimento: reinicia a aplicação sozinha quando o código muda (restart rápido) e ativa o **LiveReload** no navegador.

---

## 🔧 Ferramentas de Build

### Maven
Gerenciador de dependências e ferramenta de build configurada por XML (`pom.xml`). É a opção mais tradicional e madura do ecossistema Java.

### Gradle
Alternativa mais moderna e flexível, configurada por scripts em Groovy ou Kotlin (`build.gradle` / `build.gradle.kts`). Costuma ser mais rápido que o Maven porque usa cache incremental.

---

## 🗣️ Linguagens

### Kotlin
Linguagem moderna que roda na JVM e é interoperável com Java. É mais enxuta, tem *null-safety* nativo e é oficialmente suportada pelo Google no Android. No Spring, funciona como uma alternativa ao Java com sintaxe mais concisa.

---

## 🌀 Versões do Spring Boot e Snapshots

- Versões numeradas normalmente (por exemplo `3.3.5`) são **estáveis** e indicadas para produção.
- Versões marcadas como **SNAPSHOT** são builds em desenvolvimento, ainda instáveis, usadas para experimentar recursos que não foram lançados oficialmente. Não devem ir para produção.

---

## 🏷️ Project Metadata

### Group
Identifica a organização dona do projeto, no padrão de domínio invertido (por exemplo `com.example` ou `br.com`). É o "namespace raiz" do projeto.

### Artifact
Nome da aplicação em si (por exemplo `demo` ou `cliente-cnpj-api`). Dele saem o nome do arquivo final (jar/war) e o da pasta do projeto.

### Package Name
Nome completo do pacote Java em que o código-fonte é organizado, normalmente formado por Group + Artifact (por exemplo `com.example.demo`).

### Packaging
Formato do arquivo gerado no build:
- **Jar** → aplicação com servidor embutido, que roda sozinha (`java -jar app.jar`). É o padrão nas aplicações Spring Boot atuais.
- **War** → formato tradicional, feito para ser implantado em um servidor de aplicação externo (Tomcat, JBoss etc.).

### Configuration
Formato do arquivo de configuração da aplicação:
- **Properties** → `application.properties`, no formato chave=valor, simples e direto.
- **YAML** → `application.yml`, hierárquico (indentado), mais legível quando a configuração é grande ou aninhada.

---

## ☕ Versões do Java e LTS

No Java, nem toda versão é **LTS (Long-Term Support)**:

| Versão | Tipo | Observação |
|---|---|---|
| 17 | LTS | Suporte de longo prazo, muito usada em produção |
| 21 | LTS | LTS estável e bastante adotada, traz as Virtual Threads (usada no projeto Cliente CNPJ API) |
| 25 | LTS | LTS mais recente do ciclo |
| 26 | Não-LTS | Versão de curto prazo, voltada a novidades e testes |

### Qual é a diferença?

- **Versões LTS** recebem correções e atualizações de segurança por vários anos e são as recomendadas para produção.
- **Versões não-LTS** (lançadas a cada 6 meses) trazem as novidades mais cedo, mas têm suporte curto. Servem para testar recursos novos, não para produção.

---

## 🧩 Annotations (Anotações)

Annotations são **metadados** colocados no código Java com a sintaxe `@NomeDaAnotação`. Elas não mudam a lógica do programa diretamente: servem de instrução para o compilador, para ferramentas (como o Lombok) ou, no Spring, para o **container de injeção de dependência**, que lê as anotações em tempo de execução para saber como montar e conectar os componentes da aplicação.

Exemplos comuns no Spring:

| Annotation | Para que serve |
|---|---|
| `@SpringBootApplication` | Marca a classe principal e ativa a auto-configuração e o escaneamento de componentes |
| `@RestController` | Declara um controlador REST, cujos métodos devolvem dados (JSON) direto no corpo da resposta |
| `@Service` | Declara uma classe da camada de regras de negócio |
| `@Repository` | Declara uma classe da camada de acesso a dados |
| `@Autowired` | Pede ao Spring que injete uma dependência automaticamente |
| `@GetMapping` / `@PostMapping` | Ligam uma rota HTTP a um método |

> 💡 No projeto [cliente-cnpj-api](cliente-cnpj-api) aparecem, entre outras, `@RestController`, `@Service`, `@RestControllerAdvice` (tratamento global de erros), `@Entity` e `@FeignClient`.

---

## 🫘 Beans

Um **Bean** é qualquer objeto **criado, gerenciado e controlado pelo Spring**, em vez de ser instanciado manualmente com `new` pelo desenvolvedor. Os beans ficam registrados no **Spring Container (ApplicationContext)**, que cria as instâncias, injeta as dependências entre elas e cuida do ciclo de vida (criação, uso e destruição).

Formas mais comuns de declarar um Bean:

- Anotar uma classe com `@Component`, `@Service`, `@Repository` ou `@Controller` — o Spring a encontra sozinho durante o escaneamento de componentes
- Anotar um método com `@Bean` dentro de uma classe `@Configuration` — o valor retornado pelo método passa a ser um Bean gerenciado

É isso que permite a **Injeção de Dependência (DI)**: em vez de a classe criar as próprias dependências, ela só as declara (em geral no construtor) e o Spring entrega o Bean pronto.

> 💡 Por padrão cada Bean é um **Singleton**, ou seja, existe uma única instância dele no container. É o que acontece com `EmpresaFacade` e `SalvarEmpresaStrategy` no projeto Cliente CNPJ API.

---

## 👩‍💻 Autora

Projeto desenvolvido por **Julia Akemi**
