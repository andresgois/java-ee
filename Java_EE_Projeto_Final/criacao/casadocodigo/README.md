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
- [Form](http://localhost:8080/casadocodigo/livro/form.xhtml)

> Para que serve o JBoss Forge?
- Ele gera toda a estrutura base do projeto integrada com o Maven. Configuração de dependências (bibliotecas e frameworks), estrutura de pastas e gera configurações iniciais que geralmente envolve arquivos XML.

#### As tags padrão de propriedades, que seguem o formato chava / valor, ficando

- hibernate.hbm2ddl.auto onde pedimos para o Hibernate criar e manter nosso banco atualizado de acordo com as entidades, através do valor update;
- hibernate.show_sql pedimos para o Hibernate exibir o SQL que ele gera, através do valor true;
- hibernate.format_sql pedimos para o SQL exibido na configuração anterior, ser formatdo bonitinho através do valor true;
- hibernate.dialect dizemos ao Hiberante o dialeto do banco, para ele criar Queries próprias para aquele banco de dados;

### Configurando o Datasource
```
<datasource jndi-name="java:jboss/datasources/casadocodigoDS" pool-name="casadocodigoDS">
    <connection-url>jdbc:mysql://localhost:3306/casadocodigo_javaee</connection-url>
    <connection-property name="DatabaseName">
        casadocodigo_javaee
    </connection-property>
    <driver>mysql</driver>
    <pool>
        <min-pool-size>10</min-pool-size>
        <max-pool-size>20</max-pool-size>
    </pool>
    <security>
        <user-name>root</user-name>
    </security>
</datasource>
```

- Começando pela tag <datasource>, temos o atributo jndi-name, que é a ligação entre o datasource que estamos criando e a nossa aplicação;
- Mais abaixo, temo a tag <connection-url> que recebe o endereço do nosso banco de dados;
- Em seguida temos a tag <connection-property>, que obrigatoriamente precisamos adicionar quando usamos o Wildfly 10. Perceba que estamos informando algo que já está na tag connection-url, porém não será levado em cosideração - lá, apenas aqui na tag <connection-property>;
Na tag <pool> nós informamos o número mínimo e máximo de conexões que o datasource poderá criar com o banco de dados;
- Por fim, temos a tag <security>, onde informamos o nome do usuário do banco de dados na tag <user-name>, no nosso caso root; Caso você possua uma senha para acessar seu mysql, basta adicionar a tag <password> abaixo de user-name;

### Driver
- Parece que pulamos uma tag, que foi a <driver>. Essa tag referencia outra área especifica dentro da configuração, que fica logo abaixo de <datasource>, é a tag <drivers>. Dentro dela temos a tag <driver> de exemplo, porém, assim como em <datasource>, precisamos criar a nossa configuração de driver. Ficando nosso código, assim:
```
<driver name="mysql" module="com.mysql">
    <datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlDataSource</datasource-class>
</driver>
```
- Essa configuração da tag <driver>, serve principalmente para informar a classe do datasource, o qual fazemos através da tag <datasource-class> do código acima. Mas ainda dentro da tag, temos um atributo um atributo importante, é o module. Todo driver JDBC, precisa de um JAR. Esse JAR não fica armazenado no projeto e sim no servidor, para ser mais especifico, no Wildfly os drivers ficam armazenados na pasta: wildfly/modules/. Devemos 
colocar nosso JAR dentro desta pasta, com a estrutura a seguir:

```
com/
    mysql/
        main/
            mysql-connector-java-5.1.35.jar
```

- [Link driver Mysql](http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.35/mysql-connector-java-5.1.35.jar)

- Para finalizar, só precisamos criar o banco de dados, e faça os seguintes comandos:
    - acesse o Mysql: no terminal faça : mysql -u root
    - create database casadocodigo_javaee; para criar o banco.

> Qual a vantagem de utilizarmos o CDI no JavaEE? O que ganhamos com seu uso no projeto?
- Com o CDI ganhamos injeção de dependências entre as classes da aplicação e também o controle de escopo, que nos permite manter uma classe viva pelo tempo que precisarmos.

### Ferramentas utilizadas
- [JBOSS Forge](https://forge.jboss.org/download)
- [Wildfly](https://www.wildfly.org/downloads/)

<a name="anc2"></a>

## Relacionando Livro com Autores
- Adicione a div e o <h:selectManyListbox> referente a seleção dos autores, exibindo o nome do autor e relacionando o valor do select pelo id do autor. Também já vamos colocar o converter javax.faces.Integer para que os ID sejam enviados como Integer e não String.

- Insert de autores
```
insert into Autor (nome) values ('Paulo Silveira'), ('Sérgio Lopes'), ('Guilherme Silveira'), ('Alberto Souza');
```

### Converters
- Todos os valores que são enviados da tela para o ManagedBean são enviados como texto. O JSF não consegue converter automaticamente, o que seria possível fazer em uma lista. O generics não é identificado pelo JSF, o que nos obriga a fazer uso de conversores que explicitamente convertem o valor de texto para inteiro. Assim, sempre que utilizamos uma lista é importante converter os valores para o tipo que precisamos com os converters do JSF.

- Lembre-se de implementar o converter na sua view, ele vai ficar parecido com isso:

```
<h:selectManyListbox value="#{adminLivrosBean.autoresId}" converter="javax.faces.Integer">
    <f:selectItems value="#{adminLivrosBean.autores}"
         var="autor"
         itemValue="#{autor.id}"
         itemLabel="#{autor.nome}" />
</h:selectManyListbox>
```

- [Doc Converters](https://docs.oracle.com/javaee/7/tutorial/jsf-page-core001.htm)

<a name="anc3"></a>

## Validando e Exibindo Mensagens no Formulário

<a name="anc4"></a>

## Data de Publicação e Converters

<a name="anc5"></a>

## Adicionando e Exibindo a Capa do Livro

<a name="anc6"></a>

## Criando a Home e o Detalhe do Livro