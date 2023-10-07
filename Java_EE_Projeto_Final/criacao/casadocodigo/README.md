# Java EE parte 1: crie sua loja online com CDI, JSF, JPA

- [Java EE parte 2: Sua loja online com HTML, REST e Cache](./README2.MD)

- [Java EE parte 3: Finalizando sua loja com REST, JMS, JAAS e WebSockets](./README3.MD)

### Links
- [Criando e Configurando o Projeto](#anc1)
- [Relacionando Livro com Autores](#anc2)
- [Validando e Exibindo Mensagens no Formulário](#anc3)
- [Data de Publicação e Converters](#anc4)
- [Adicionando e Exibindo a Capa do Livro](#anc5)
- [Criando a Home e o Detalhe do Livro](#anc6)

##

<a name="anc1"></a>

## Criando e Configurando o Projeto
- Abra o seu Terminal e navegue até seu workspace. Dentro do seu workspace, aponte o terminal para o local onde você descompactou o forge. 
- No meu caso: `../forge/bin/forge`

- Para criar o projeto: `project-new --named casadocodigo`
- Passos a seguir
    - digite br.com.casadocodigo.
    - Enter na versão
    - Enter no final name
    - Enter na Location
    - Type project `war`
    - Build system `maven`
    - Aparecerá no terminal o nome do seu projeto
        - Digite:
            - **Arquivo de configuração do JSF**
            - faces-setup --facesVersion 2.2
            - **Arquivo de configuração do CDI**
            - cdi-setup --cdiVersion 1.1
- Importe o projeto no eclipse
- Adicione o wildfly 10
    - Configure-o para usar o standalone-full, pois tem mais recursos como gerenciamentos do EJB's.
    - adicione a lib do wildfly ao build path do projeto.

- [Acesse](http://localhost:8080/casadocodigo)

> Para que serve o JBoss Forge?
- Ele gera toda a estrutura base do projeto integrada com o Maven. Configuração de dependências (bibliotecas e frameworks), estrutura de pastas e gera configurações iniciais que geralmente envolve arquivos XML.

### Ferramentas utilizadas
- [JBOSS Forge](https://forge.jboss.org/download)
- [Wildfly](https://www.wildfly.org/downloads/)

<a name="anc2"></a>

## Relacionando Livro com Autores

<a name="anc3"></a>

## Validando e Exibindo Mensagens no Formulário

<a name="anc4"></a>

## Data de Publicação e Converters

<a name="anc5"></a>

## Adicionando e Exibindo a Capa do Livro

<a name="anc6"></a>

## Criando a Home e o Detalhe do Livro