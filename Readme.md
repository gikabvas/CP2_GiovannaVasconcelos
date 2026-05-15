# 🎵 Sistema de Streaming de Música 🎵

Projeto desenvolvido ao longo da disciplina de Programação Orientada a Objetos. A ideia foi simular um serviço de streaming no terminal, com dois tipos de usuário, playlists, downloads e histórico de reprodução.

---

## 📋 Funcionalidades

O sistema roda no terminal e permite criar contas, logar, ouvir músicas e gerenciar playlists. As funcionalidades variam dependendo se a conta é Free ou Premium:
- **Cadastro de usuários** — Free (gratuito) e Premium (pago), com múltiplos planos
- **Sistema multi-usuário** — Login e logoff, cada usuário com seus dados isolados
- **Catálogo de músicas** — Cadastro, listagem e busca por título ou artista
- **Reprodução de músicas** — Free exibe anúncios a cada 3 músicas; Premium reproduz em alta qualidade
- **Histórico de reprodução** — Registro de todas as músicas ouvidas por sessão
- **Playlists personalizadas** — Free limitado a 3 playlists; Premium ilimitado
- **Playlists automáticas** — Geradas por critério: *Top*, *Recomendadas* ou *Recentes*
- **Downloads (Premium)** — Baixar e remover músicas offline com controle de duplicatas
- **Estatísticas pessoais** — Reproduções, playlists, anúncios vistos (Free)
- **Estatísticas do sistema** — Visão geral com percentuais por tipo de usuário (Premium)

---

## 🏗️ ️️Arquitetura

### Estrutura de Pacotes

```
src/
└── br/com/streaming/
    ├── modelo/           # Classes de domínio (entidades do sistema)
    │   ├── ItemReproducao.java     (classe abstrata)
    │   ├── Musica.java
    │   ├── Playlist.java
    │   ├── Usuario.java
    │   ├── UsuarioFree.java
    │   └── UsuarioPremium.java
    ├── servico/          # Interfaces e serviços de negócio
    │   ├── Reproduzivel.java       (interface)
    │   ├── Baixavel.java           (interface)
    │   └── GeradorRecomendacoes.java
    ├── util/             # Utilitários reutilizáveis
    │   ├── Validador.java
    │   └── FormatadorTempo.java
    └── principal/        # Ponto de entrada
        └── StreamingMusica.java
```

### Conceitos de POO usados no projeto

**Encapsulamento** — Todos os atributos são `private` ou `protected`, com getters e setters que validam os dados antes de aceitar.

**Herança** — `Musica` e `Playlist` herdam de `ItemReproducao`. `UsuarioFree` e `UsuarioPremium` herdam de `Usuario`.

**Classe abstrata** — `ItemReproducao` guarda o estado de reprodução compartilhado e obriga as subclasses a implementar `reproduzir()` e `getDuracaoTotal()`.

**Interfaces** — `Reproduzivel` define o contrato pra qualquer coisa que pode ser reproduzida. `Baixavel` define o contrato de download, implementado só pelo `UsuarioPremium`.

**Polimorfismo** — O `ArrayList<Usuario>` guarda Free e Premium juntos. Quando chamo `reproduzirMusica()`, cada tipo executa sua própria versão automaticamente.

**@Override** — Em todos os métodos sobrescritos: `reproduzirMusica()`, `criarPlaylist()`, `reproduzir()`, `pausar()`, `parar()`, `getDuracaoTotal()`

**instanceof e casting** — Uso pra separar comportamentos nos menus e nas estatísticas, quando preciso acessar métodos específicos de Free ou Premium.

**final** — Coloquei em métodos que não devem ser alterados pelas subclasses, como `setEmail()`, `exibirHistorico()` e os métodos de gerenciamento da Playlist.

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 11 ou superior instalado
- Terminal / Prompt de Comando

### Compilação

Na raiz do projeto (onde está a pasta `src`), execute:

```bash
# Compilar todos os arquivos para a pasta out/
javac -d out -sourcepath src src/br/com/streaming/principal/StreamingMusica.java
```

### Execução

```bash
java -cp out br.com.streaming.principal.StreamingMusica
```

### Compilação manual (arquivo por arquivo)

Caso prefira compilar sem a flag `-sourcepath`:

```bash
mkdir -p out
javac -d out src/br/com/streaming/util/Validador.java
javac -d out src/br/com/streaming/util/FormatadorTempo.java
javac -d out -cp out src/br/com/streaming/servico/Reproduzivel.java
javac -d out -cp out src/br/com/streaming/modelo/ItemReproducao.java
javac -d out -cp out src/br/com/streaming/modelo/Musica.java
javac -d out -cp out src/br/com/streaming/servico/Baixavel.java
javac -d out -cp out src/br/com/streaming/modelo/Playlist.java
javac -d out -cp out src/br/com/streaming/modelo/Usuario.java
javac -d out -cp out src/br/com/streaming/modelo/UsuarioFree.java
javac -d out -cp out src/br/com/streaming/modelo/UsuarioPremium.java
javac -d out -cp out src/br/com/streaming/servico/GeradorRecomendacoes.java
javac -d out -cp out src/br/com/streaming/principal/StreamingMusica.java
java -cp out br.com.streaming.principal.StreamingMusica
```
## 👤 Autor

- Nome: Giovanna de Vasconcelos Borges
- RM: 42975212
- Curso: Ciência da Computação - Braz Cubas

---

## 📅 Histórico de Checkpoints

| # | Tema | Conceitos |
|---|---|---|
| CP1 | Classes e Objetos | Criação de classes, atributos, métodos, instâncias |
| CP2 | Encapsulamento | `private`, getters/setters, validações |
| CP3 | Construtores | Construtores padrão e parametrizados, `this()` |
| CP4 | Herança | `extends`, `super`, hierarquia de classes |
| CP5 | Polimorfismo | `@Override`, `instanceof`, casting, `final`, listas polimórficas |
| CP6 | Interfaces e Pacotes | `interface`, classe abstrata, pacotes `br.com.streaming.*`, arquitetura profissional |