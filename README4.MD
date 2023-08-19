# JAVA EE

### Módulo 3 JSF III
- [Integração do CDI com JSF 2 Ver primeiro vídeo](#anc1)
- [Injeção de dependências com CDI](#anc2)
- [Gerenciando transações com CDI](#anc3)
- [Completando a aplicação](#anc4)
- [Integração com outras tecnologias: Maven, Spring e EJB](#anc5)


# Módulo 7

<a name="anc1"></a>

## Integração do CDI com JSF 2 Ver primeiro vídeo
### Configuração do CDI
- O CDI é uma especificação relativamente nova, mas os conceitos, principalmente a inversão de controle, são discutidos e usados há muito tempo na plataforma Java.
- O primeiro passo é baixar o JAR. A implementação do CDI se chama WELD e como estamos dentro de um servlet container, usaremos o: 
- [weld-servlet.jar](https://mvnrepository.com/artifact/org.jboss.weld.servlet/weld-servlet/2.3.2.Final)

- O CDI também precisa de uma configuração inicial, na verdade são duas pequenas configurações. 
    - A primeira é o arquivo context.xml, que fica no diretório WebContent/META-INF:

```
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource name="BeanManager"
        auth="Container"
        type="javax.enterprise.inject.spi.BeanManager"
        factory="org.jboss.weld.resources.ManagerObjectFactory"/>
</Context>
```
 - A segunda configuração é o arquivo especifico do CDI. 
    - A especificação servlet tem web.xml, JSF tem o seu faces-config.xml e o CDI tem o seu beans.xml.
    - Esse arquivo deve ser criado na pasta WebContent/WEB-INF (ao lado dos "irmãos" web.xml e faces-config.xml):

```
<!-- WebContent/WEB-INF/beans.xml -->
<beans>
</beans>
```
- Queremos utilizar o CDI na nossa aplicação para gerenciar os Beans.
```
Trocando @ManagedBean por @Named, @ViewScoped passa a vir de javax.faces.view.ViewScoped e @SessionScoped passa a vir de javax.enterprise.context.SessionScoped.
```

<a name="anc2"></a>

## Injeção de dependências com CDI

### Gerenciando DAO e EntityManager com CDI
- O CDI já está tomando conta dos nosso beans, que eram responsabilidade do JSF, mas a nossa motivação para usar o CDI era na verdade a camada de persistência.
- Como o EntityManager é dependência de todos os métodos, dizemos que ele é uma dependência da classe. E o que fazemos com essa dependência? Como a classe precisa dela, vamos adicioná-la ao seu construtor;
- O CDI vai injetar o DAO! Injetar significa passar uma instância pronta para usar. Para isso, vamos utilizar a anotação @Inject no atributo, quando o CDI vê essa anotação, ele saberá que o bean "quer" ou "precisa" de um DAO e consequentemente criará uma instância dele e a disponibilizará para nós:

```
public class AutorBean implements Serializable {

    @Inject
    private DAO<Autor> dao;

    // restante do código, agora sem o construtor
}
```
- O CDI inverte o controle, inverter significa resolver a dependência e injetá-la. Não é mais a classe AutorBean que dá um new no DAO. O CDI está no controle, busca e injeta a dependência.

- Essa forma de inversão de controle é chamado de injeção de dependências.

### Ensinando o CDI a criar um EntityManager
- Queremos que o CDI tome conta desse EntityManager. Então vamos "dizer" para o CDI que o método getEntityManager produz um EntityManager! Vamos devagar, o método getEntityManager devolve um EntityManager novo, então sabe criá-lo, certo? Olhando para os padrões de projeto, podemos dizer que o método é uma fábrica, aplicando o padrão factory method. Só que o CDI chama esses métodos de fábrica de Producer. É apenas um outro nome para algo muito comum.

- Para deixar claro que o método sabe criar um EntityManager, devemos usar a anotação @Produces em cima do método:
- Com isso, o CDI conhece o método e sabe que ele devolve um EntityManager. Mas ele terá uma dúvida, o CDI desejará saber quantas vezes queremos criar um EntityManager dentro da aplicação. Iremos produzir um um novo EntityManager a cada requisição, para dizer isso ao CDI basta adicionar a anotação @RequestScoped:
- Produzimos um EntityManager a cada requisição, mas ainda temos um último problema. Veja na classe DAO que todos os métodos fecham o EntityManager! Ou seja, se removermos um autor, não conseguiremos gravar um novo na mesma requisição, o que não pode acontecer! Só devemos fechar o EntityManager depois da requisição. Então vamos apagar todas linhas que fecham o EntityManager (em.close()) na classe DAO.

- Agora basta avisarmos ao CDI como fechar o EntityManager. Já temos um método close em JPAUtil. E para chamar um método quando a requisição acaba, o CDI possui uma anotação @Disposes:

```
// AutorDAO
@Inject
private EntityManager em;
// Chama o método produtor desse método
// JPAUtil
@Produces
@RequestScoped  // Abre em cada requisição
public EntityManager getEntityManager() {
    return emf.createEntityManager();
}
// fecha em cada requisição
public void close(@Disposes EntityManager em) {
    em.close();
}
```
- Com injeção de dependência temos menos acoplamento no código.
- Com injeção de dependências não estamos criando os objetos, o container assume essa responsabilidade.
> Source > Delegate method `Eclipse`

![Sem CDI](./asserts/sem_cdi.png)

![Com CDI 1](./asserts/com_cdi1.png)

![Com CDI 2](./asserts/com_cdi2.png)

<a name="anc3"></a>

## Gerenciando transações com CDI
### Centralizar o gerenciamento da transação
- Vamos tentar criar essa classe, que se chamará GerenciadorDeTransacao, no pacote br.com.caelum.livraria.tx, com um método executaTX, que executa o begin e commit:

- Quando criamos uma anotação, deve ficar claro que ela representa uma configuração e não uma implementação. A anotação @Transacional existe para configurar no método que é preciso ter uma transação. Nada mais do que isso, quem realmente vai chamar begin e commit é a nossa classe GerenciadorDeTransacao.

- O CDI passará um parâmetro no método executaTX, que guarda a informação de quem precisa da transação. Esse objeto tem um nome “bonito”: contexto de invocação ou em inglês InvocationContext. Através dele, podemos pedir para chamar o método, mas como não sabemos diretamente o nome do método foi dado um nome bem genérico, proceed():

- Mas como o CDI, ao ver a anotação @Transacional, saberá qual método deve executar? É executado algo antes e depois da chamada do método. O mundo do CDI chama o antes e depois de Around (ao redor), por isso devemos usar a anotação @AroundInvoke em cima do método:

```
@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object executaTX(InvocationContext context) throws Exception {
		em.getTransaction().begin();
		// chama o dao
		Object obj = context.proceed();
		em.getTransaction().commit();
		return obj;
	}
}
```
### Criando a anotação @Transacional
- Quando definimos uma nova anotação, algumas outras configurações genéricas são necessárias. A JVM deve saber onde esse anotação pode ser utilizada. Existem anotações que podem ser utilizadas em cima da classe (por exemplo @Named), existem outras que funcionam em cima do atributo (como @Inject). A nossa anotação deve funcionar em cima do método, certo? Então vamos deixar isso explicito:
- A nossa anotação é diferente e não há um impacto no compilador, ela deve funcionar na hora de executar (RUNTIME):

```
@InterceptorBinding
@Target( {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transacional {

}
```
### Associar a anotação com a transação
- É essa a tarefa do CDI, ajudar a criar essa ligação. Uma vez feito, o CDI sabe que, ao encontrar a anotação @Transacional, deve chamar o método executaTX do GerenciadorDeTransacao

### Configuração do interceptador no beans.xml
- A implementação do método já está perfeita, mas não esqueça de anotar também com @Transacional os métodos gravar e remover de AutorBean.

- Por fim, precisamos configurar o configurar o GerenciadorDeTransacao no arquivo WebContent/WEB-INF/beans.xml:

```
<beans>
    <interceptors>
        <class>br.com.caelum.livraria.tx.GerenciadorDeTransacao</class>
    </interceptors>
</beans>
```

### Interceptadores
- O Interceptador é um dos padrões de projetos (Design Pattern) bem importante do mundo Java e não é exclusividade do CDI. Eles também existem para EJB, Spring e vários outros frameworks do mundo Java!

- Os Interceptadores ajudam a separar as responsabilidades da cada classe. Por exemplo, em vez de o DAO cuidar das chamadas de persistência e transação, tiramos a parte de transação e centralizamos dentro de um interceptador. Isso facilita a manutenção das nossas classes.

- Há desvantagens: podemos dizer que nosso código se torna mais difícil de entender, só olhando no DAO não é suficiente para saber como a transação está sendo gerenciada.

![Interceptadores](./asserts/interceptador.png)

<a name="anc4"></a>

## Completando a aplicação

### Produzindo e injetando o FacesContext
- Classe da injeção
```
 @Inject
FacesContext context;
```
- Classe produtora
```
public class JsfUtil {

    @Produces
    @RequestScoped // javax.enterprise.context.RequestScoped
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
```

### Relacionamento 

#### EAGER
- Automaticamente carrega os autores
```
@ManyToMany(fetch=FetchType.EAGER) 
```
#### LAZY
- Padrão, não trás os autores
```
@ManyToMany(fetch=FetchType.LAZY)
```
- Erro ao carregar autores quando o LAZY ativado
```
Type Exception Report
Message could not initialize proxy - no Session
Description The server encountered an unexpected condition that prevented it from fulfilling the request.
Exception
```

- Para utilizar o Lazy e deixar em uma condição melhor, quando se clicar em `Alterar` na página de livros, ele chama o método carregar no `livroBean`, a partir dai podemos fazer uma busca pelo id do autor enviado.
```
public void carregar(Livro livro) {
    this.livro = dao.buscaPorId(livro.getId());
    System.out.println("Carregando livro " + livro.getTitulo());
}
```
- Quando tornamos uma de nossa classes uma entidade JPA precisamos no mínimo:
    - Anotar a classe com @Entity e definir algum de seus atributos como chave através da anotação @Id. Além disso, precisamos adicionar a classe no arquivo persistence.xml. O uso de @GeneratedValue é opcional, mas altamente recomendado para atributos numéricos que anotados com @Id

<a name="anc5"></a>

## Integração com outras tecnologias: Maven, Spring e EJB

### Integração com o Maven
- O Maven vem para ajudar no gerenciamento do projeto, não é perfeito, porém é muito utilizado no mercado. Para aprender melhor a como fazer essa integração e a integração com outros projetos

#### Criando um novo projeto
- Vamos criar um novo projeto, que terá a integração com o Maven. Para isso, iremos em New -> Project -> Maven Project. Selecionamos Create a simple project (skip archetype selection), pois iremos criar tudo do zero.

- Algumas configurações na próxima página e depois Finish:
    - Group Id: Esse é o nosso pacote, que no caso é br.com.caelum;
    - Artifact Id: Esse é o nome do projeto. Já temos livraria no workspace, então vamos - colocar livraria-maven;
    - Version: 0.0.1-SNAPSHOT;
    - Packaging: war;
    - Name: Livraria Alura;
#### pom.xml
- O pom.xml é o arquivo XML do Maven. Vamos lembrar que queremos que o Maven gerencie nossos .jar e acabe com o trabalho manual. Para isso, precisamos informar ao Maven quais dependências ele irá gerenciar. Dentro do XML, nós informamos isso ao Maven através da tag `<dependencies></dependencies>`

#### Rodando com o Maven
- Como falado no início, podemos também rodar a aplicação através do Maven, para assim não ficarmos tão ligados ao Eclipse. Para isso, botão direito no projeto, e Run As... -> Maven Build. Em goals, ou seja, quando executarmos com o Maven, queremos que ele, por exemplo, dê um clean, compile o projeto e gere um WAR para deploy, para isso, colocamos:
```
clean compile package
```



### Caso der erro de bloqueio no no repository do maven
- Adicione isso ao arquivo `settings.xml` que fica na pasta .m2
```
<mirrors>
        <mirror>
            <id>maven-default-http-blocker</id>
            <mirrorOf>dummy</mirrorOf>
            <name>Dummy mirror to override default blocking mirror that blocks http</name>
            <url>http://0.0.0.0/</url>
        </mirror>
</mirrors>
```
- Se tiver esse erro:
```
You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
```
- Pra resolver basta adicionar isso no persistence.xml, na conexão
```
<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/livraria?autoReconnect=true&amp;useSSL=false" />
```