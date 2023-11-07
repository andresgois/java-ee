# Java EE parte 3: Finalizando sua loja com REST, JMS, JAAS e WebSockets


### Links
- [Responda à requisições nos formatos XML e JSON](#anc1)
- [Envie e-mails para o usuário](#anc2)
- [Trabalhe Assincronamente com JMS](#anc3)
- [Proteja sua aplicação com JAAS](#anc4)
- [Utilize WebSockets para comunicação Síncrona](#anc5)

- [Site](http://localhost:8080/casadocodigo/index.xhtml)
##

<a name="anc1"></a>

## Responda à requisições nos formatos XML e JSON 
### Disponibilizando dados no formato JSON
- Para disponibilizar os dados no formato JSON, por meio de um acesso a uma URL da aplicação (ou seja, utilizando o método HTTP GET)
- @GET, para indicar qual o método HTTP utilizado; @Path para indicar o caminho do recurso; @Produces({ MediaType.APPLICATION_JSON }, para indicar quando o formato que iremos disponibilizar.

### Problema ao retornar os dados em XML
```
Could not find MessageBodyWriter for response object of type: java.util.ArrayList of media type: application/xml
```
- Precisamos utilizar a anotação @XmlRootElement na classe do tipo que estamos retornando no método ultimosLancamentosXml().

- Como estamos retornando uma ArrayList e não temos como modificar o código da classe, poderíamos criar uma classe que tivesse um ArrayList como atributo e então utilizar a anotação.

- Mas como estamos utilizando o RESTEasy como implementação da JAX-RS, podemos utilizar a anotação @XmlRootElement no tipo do ArrayList. Então podemos utilizar a anotação na classe Livro e o RESTEasy irá resolver isto.

### Elemento raiz
- E elemento raiz do XML gerado, é <collection>
- [Elemento Raiz](../../../asserts/005-collection-raiz.png)
- Mas baseado no padrão que normalmente é encontrado, esse elemento deveria ser <livros>. O que precisamos fazer para realizar esta alteração?
    - É necessário adicionar a anotação @Wrapped(element = "livros"), no método ultimosLancamentosXml().


<a name="anc2"></a>

## Envie e-mails para o usuário

<a name="anc3"></a>

## Trabalhe Assincronamente com JMS

<a name="anc4"></a>

## Proteja sua aplicação com JAAS

<a name="anc5"></a>

## Utilize WebSockets para comunicação Síncrona
