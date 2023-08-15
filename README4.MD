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
- cap 3
<a name="anc4"></a>

## Completando a aplicação
- cap 4
<a name="anc5"></a>

## Integração com outras tecnologias: Maven, Spring e EJB
