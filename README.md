# pwiii-julia-akemi
programação web 3, joao siles najara cardoso

## 🚀 Primeiros Passos com Laravel

O **Laravel** é um framework PHP expressivo e elegante, projetado para facilitar o desenvolvimento de aplicações web seguindo o padrão **MVC** (*Model-View-Controller*) de forma organizada e eficiente.

---

## 💠 Pré-requisitos

Antes de começar, garanta que você tem as seguintes ferramentas instaladas:

*   **PHP 8.1+** (Linguagem base)
*   **Composer** (Gerenciador de dependências PHP)
*   **Node.js & NPM** (Para compilar CSS e JavaScript via Vite)
*   **Banco de Dados** (MySQL, PostgreSQL ou SQLite)

---

# Passo a Passo

### 1. Criar o Projeto
No seu terminal, execute o comando para gerar a estrutura inicial:
```bash
laravel new example-app
```
- Este comando cria uma nova pasta chamada `example-app` com todo o esqueleto do projeto Laravel configurado.
- Ele baixa todos os arquivos necessários, bibliotecas e configurações básicas.
- O instalador pode perguntar algumas configurações, como o banco que você vai usar, framework de testes e kits iniciais, que são conjuntos prontos para ajudar no começo do projeto.

---

### 2. Acesse a pasta do projeto
```bash
cd example-app
```
- Agora você entra na pasta do projeto para executar comandos dentro dela.

---

### 3. Instale as dependências frontend e compile os assets
No seu terminal, execute o comando para gerar a estrutura inicial:
```bash
npm install && npm run build
```

- `npm install` instala as bibliotecas JavaScript necessárias para seu frontend (CSS, JS, etc).
- `<npm run build` compila esses arquivos para produção, transformando código moderno em algo que todos os navegadores entendem.

---

### 4. Inicie o ambiente completo de desenvolvimento
```bash
composer run dev
```
- Este comando inicia vários serviços que você vai precisar enquanto desenvolve:
  - Servidor local Laravel (`php artisan serve`): roda a aplicação no endereço `http://localhost:8000.`
  - Queue worker: processa tarefas em segundo plano (exemplo: envio de e-mails).
  - Servidor Vite: atualiza automaticamente seu frontend quando você salva mudanças nos arquivos CSS ou JS.
 
--- 

### 5. Configuração do ambiente
```bash
cp .env.example .env
php artisan key:generate
```
- `.env` é o arquivo de configuração do seu projeto (dados sensíveis, configurações de banco, etc).
- Você copia o arquivo de exemplo para `.env` para começar a personalizar.
- `php artisan key:generate` cria uma chave única para sua aplicação, usada para proteger sessões e dados criptografados.

---

### 6. Configure seu banco de dados
Edite o arquivo `.env` para informar os dados do seu banco, por exemplo:
```bash
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=exemplo
DB_USERNAME=root
DB_PASSWORD=secret
```
- Essas informações dizem ao Laravel onde e como se conectar ao banco de dados.

---

### Execute as migrations (opcional)
```bash
php artisan migrate
```
- Migrations são como "controladores de versão" para o banco de dados.
- Esse comando cria as tabelas no banco que Laravel precisa para funcionar (exemplo: tabela de usuários).

---

### Teste no navegador
- Acesse: `http://localhost:8000`
- Você verá a página inicial padrão do Laravel, que confirma que seu projeto está funcionando.

---

### Para criar um controller (exemplo simples)
```bash
php artisan make:controller HelloController
```
- Esse comando gera uma classe Controller, onde você vai colocar a lógica para responder às requisições web.
