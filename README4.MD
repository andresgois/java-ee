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
- Source > Delegate method `Eclipse`
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
